/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2011 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sim.kart;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.elk.core.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import de.cau.cs.kieler.core.model.util.ModelUtil;
import de.cau.cs.kieler.sim.kart.engine.DefaultValidationEngine;
import de.cau.cs.kieler.sim.kart.engine.IValidationEngine;
import de.cau.cs.kieler.sim.kiem.IJSONObjectDataComponent;
import de.cau.cs.kieler.sim.kiem.IKiemEventListener;
import de.cau.cs.kieler.sim.kiem.JSONObjectDataComponent;
import de.cau.cs.kieler.sim.kiem.KiemEvent;
import de.cau.cs.kieler.sim.kiem.KiemExecutionException;
import de.cau.cs.kieler.sim.kiem.KiemInitializationException;
import de.cau.cs.kieler.sim.kiem.execution.TimeoutThread;
import de.cau.cs.kieler.sim.kiem.properties.KiemProperty;
import de.cau.cs.kieler.sim.kiem.properties.KiemPropertyException;
import de.cau.cs.kieler.sim.signals.JSONSignalValues;

/**
 * This component implements a KIEM data component to validate signal and state information
 * generated in a simulation against pre-recorded values. If the validation fails, the simulation is
 * stopped and the error is visualized in the SyncChart. Additionally, an explanatory error message
 * is displayed. This component does not provide any means to record or replay signal and state
 * information, refer to {@link DataRecordingComponent} and {@link DataReplayComponent}
 * 
 * @author ssc, cmot
 * @kieler.design 2012-02-23 cmot
 * @kieler.rating 2012-02-23 yellow cmot
 * 
 */
public class DataValidationComponent extends JSONObjectDataComponent implements
        IJSONObjectDataComponent, IKiemEventListener {

    /** The Constant DATA_REPLAY_COMPONENT_ID. */
    public static final String DATA_VALIDATION_COMPONENT_ID =
            "de.cau.cs.kieler.sim.kart.DataValidationComponent";

    /** The number of the current step. */
    private long step;

    /** The name of the ESI/ESO file the trace shall be read/written from/to. */
    private IPath esoFilePath;

    /** Shall we ignore signals generated by the simulation but not present in the ESO file? */
    private boolean ignoreAdditionalSignals;

    /** Are we operating in training mode, i. e. generate an ESO file */
    private boolean trainingMode;

    /** A map of all values of all previously recorded output signals in each step. */
    private List<HashMap<String, Object>> esoOutputs;

    /** A map of all values of all previously recorded special signals in each step. */
    private List<HashMap<String, Object>> esoVariables;

    /**
     * In training mode, this list will hold all simulated output signals and their values for each
     * step.
     */
    private List<HashMap<String, String>> simOutputs;

    /**
     * In training mode, this list will hold all simulated output variables and their values for
     * each step.
     */
    private List<HashMap<String, String>> simVariables;

    /**
     * A map of all values of all simulated or recorded input signals in each step. Pushed here by
     * the DataReplayComponent
     */
    private List<HashMap<String, Object>> recInputs;

    /**
     * A list of special signals from the properties like a state signal that will be compared
     * differently than regular signals and are found in special comments in the ESO file.
     */
    private Set<Pair<String, String>> variables;

    /**
     * The validation engine that will be used to validate signal and variable information.
     */
    private IValidationEngine valEngine;

    /** Name of the variable the replay component will give us our configuration through. */
    private String configVarName;

    /**
     * Name of the variable the replay component will give us the values of the output signals and
     * variables through.
     */
    private String outputVarName;

    /**
     * Name of the variable under which all signals injected into the simulation by components in
     * front of this component will be saved. These will be regarded as input signals when writing
     * an ESO file.
     */
    private String prevInputVar;

    /** Name of the variable used to communicate information about erroneous signals. */
    private String errSignalVar;

    /**
     * Set to true when the end of the trace is reached. At this point no further validation will
     * take place
     */
    private boolean eot;
    
    private int tracenum;

    // -------------------------------------------------------------------------

    /**
     * Initialize the data component. Open an existing ESO file, read the expected signal and state
     * information and store it internally
     * 
     * @throws KiemInitializationException
     *             thrown when reading the file fails
     */
    public void initialize() throws KiemInitializationException {
        step = 0;
        eot = false;
        esoFilePath = null;
        trainingMode = false;
        ignoreAdditionalSignals = false;
        configVarName = KartConstants.DEF_CONFIGVAR;
        outputVarName = KartConstants.DEF_OUTPUTVAR;
        prevInputVar = KartConstants.DEF_PREVINVAR;
        errSignalVar = KartConstants.DEF_SIGNALVAR;
        variables = Utilities.makeSetOfPairs(KartConstants.DEF_VALVAR);

        KiemProperty[] properties = this.getProperties();

        esoOutputs = new LinkedList<HashMap<String, Object>>();
        esoVariables = new LinkedList<HashMap<String, Object>>();
        simOutputs = new LinkedList<HashMap<String, String>>();
        simVariables = new LinkedList<HashMap<String, String>>();
        recInputs = new LinkedList<HashMap<String, Object>>();

        // load properties
        variables = new HashSet<Pair<String, String>>();
        for (KiemProperty prop : properties) {
            if (prop.getKey().equals(KartConstants.IGNOREEXTRA)) {
                ignoreAdditionalSignals = prop.getValueAsBoolean();
            } else if (prop.getKey().equals(KartConstants.VALVAR)) {
                variables = Utilities.makeSetOfPairs(prop.getValue());
            } else if (prop.getKey().equals(KartConstants.CONFIGVAR)) {
                configVarName = prop.getValue();
            } else if (prop.getKey().equals(KartConstants.OUTPUTVAR)) {
                outputVarName = prop.getValue();
            } else if (prop.getKey().equals(KartConstants.PREVINVAR)) {
                prevInputVar = prop.getValue();
            } else if (prop.getKey().equals(KartConstants.SIGNALVAR)) {
                errSignalVar = prop.getValue();
            }
        }

        // Read the file
        if (!trainingMode) {
            valEngine = new DefaultValidationEngine(ignoreAdditionalSignals);
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Wrapup this component. This writes an ESO file in training mode and always resets the state
     * visualization.
     * 
     * @throws KiemInitializationException
     *             when TraceWriter.doWrite() throws it
     */
    public void wrapup() throws KiemInitializationException {
        if (trainingMode) {
            // Ask the user if he wants to overwrite the ESO file, if it exists
            File file = new File(esoFilePath.toString());
            if (file.exists()) {
                possiblyDisplayOverwriteFileDialog(file);
            }

            String txtOutputString = "";
            IPath txtOutputPath = new Path(esoFilePath.toString());
            IFile txtOutputFile = ModelUtil.convertIPathToIFile(txtOutputPath);
            txtOutputString = ModelUtil.getAbsoluteFilePath(txtOutputFile);

            TraceWriter writer =
                    new TraceWriter(recInputs, simOutputs, simVariables, txtOutputString);
            
            writer.doWrite(tracenum);

//            // Set training mode flag to false
//            List<DataComponentWrapper> components =
//                    KiemPlugin.getDefault().getDataComponentWrapperList();
//            Iterator<DataComponentWrapper> it = components.iterator();
//
//            while (it.hasNext()) {
//                DataComponentWrapper c = it.next();
//
//                if (c.getDataComponent().getClass().getName()
//                        .equals(DataReplayComponent.DATA_REPLAY_COMPONENT_ID)) {
//                    KiemProperty[] props = c.getProperties();
//                    for (KiemProperty p : props) {
//                        if (p.getKey().equals(KartConstants.TRAINMODE)) {
//                            p.setValue("false");
//                        }
//                    }
//                }
//            }
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Possibly display the overwrite-file-dialog if ui-plugin is loaded.
     * 
     * @param file
     *            the file
     * @throws KiemInitializationException
     *             the kiem initialization exception
     */
    private void possiblyDisplayOverwriteFileDialog(final File file)
            throws KiemInitializationException {
        IConfigurationElement[] contributors =
                Platform.getExtensionRegistry().getConfigurationElementsFor(
                        KartConstants.KART_EXTENSION_MESSAGEDIALOG);

        if (contributors.length > 0) {
            try {
                TimeoutThread.setAwaitUserRepsonse(true);
                IMessageDialog msg =
                        (IMessageDialog) (contributors[0].createExecutableExtension("class"));
                if (msg.question(KartConstants.OVERWRITE_TITLE, KartConstants.OVERWRITE)) {
                    if (!file.delete()) {
                        throw new KiemInitializationException(KartConstants.ERR_NOTDELETE_TITLE,
                                true, null);
                    }
                }
            } catch (CoreException e) {
                throw new KiemInitializationException(KartConstants.OVERWRITE_TITLE, true, e);
            } finally {
                // MUST enable timeout again
                TimeoutThread.setAwaitUserRepsonse(false);
            }
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Sets the step number according to the button the user pressed. This is needed to correctly
     * handle history steps or jumps.
     * 
     * @param event
     *            the event
     */
    @SuppressWarnings("unchecked")
    @Override
    public void notifyEvent(final KiemEvent event) {
        if (event.isEvent(KiemEvent.STEP_INFO) && event.getInfo() instanceof Pair) {
            step = ((Pair<Long, Long>) event.getInfo()).getFirst().longValue();
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Return the types of events this component listens to.
     * 
     * @return the event types, currently only {@code KiemEvent.STEP_INFO}
     */
    @Override
    public KiemEvent provideEventOfInterest() {
        int[] events = { KiemEvent.STEP_INFO };
        KiemEvent event = new KiemEvent(events);
        return event;
    }

    // -------------------------------------------------------------------------

    /**
     * This component may not be skipped so we tell KIEM that we also produce data, though we
     * actually do not.
     * 
     * @return always true
     */
    public boolean isProducer() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * Tell KIEM that this component does observe data.
     * 
     * @return always true
     */
    public boolean isObserver() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * Tell KIEM that we are also interested in history data.
     * 
     * @return always true
     */
    @Override
    public boolean isHistoryObserver() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * Tell KIEM that we are *not* a delta observer.
     * 
     * @return always false
     */
    @Override
    public boolean isDeltaObserver() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * Provide a list of properties to KIEM so the user can configure this component.
     * 
     * @return the list of properties this component offers
     */
    @Override
    public KiemProperty[] provideProperties() {
        KiemProperty[] properties = new KiemProperty[KartConstants.KIEM_PROPERTY_VALIDATION_NUMBER];
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_CONFIGVAR] =
                new KiemProperty(KartConstants.CONFIGVAR, KartConstants.DEF_CONFIGVAR);
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_OUTPUTVAR] =
                new KiemProperty(KartConstants.OUTPUTVAR, KartConstants.DEF_OUTPUTVAR);
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_PREVINVAR] =
                new KiemProperty(KartConstants.PREVINVAR, KartConstants.DEF_PREVINVAR);
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_VALVAR] =
                new KiemProperty(KartConstants.VALVAR, KartConstants.DEF_VALVAR);
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_SIGNALVAR] =
                new KiemProperty(KartConstants.SIGNALVAR, KartConstants.DEF_SIGNALVAR);
        properties[KartConstants.KIEM_PROPERTY_VALIDATION_IGNOREEXTRA] =
                new KiemProperty(KartConstants.IGNOREEXTRA, false);
        return properties;
    }

    // -------------------------------------------------------------------------

    /**
     * Take a step in the simulation. The component reads its internal state and compares the signal
     * and possibly state information from the ESO file to the signals and state information
     * generated by the simulation.
     * 
     * @param obj
     *            the obj
     * @return always {@code null}
     * @throws KiemExecutionException
     *             when the JSON object with signals from ESI/ESO file could not be built.
     */
    public JSONObject step(final JSONObject obj) throws KiemExecutionException {
        updateConfiguration(obj);
        if (!eot) {
            getEsoData(obj);
            recordDataPool(obj);
        }

        JSONObject retval = new JSONObject();
        if (!trainingMode && !eot) {
            for (Pair<String, String> variable : variables) {
                valEngine.validateVariable(variable,
                        esoVariables.get((int) step - 1).get(variable.getFirst()),
                        obj.optString(variable.getFirst()), isHistoryStep(), retval);
            }

            valEngine.validateSignals(esoOutputs.get((int) step - 1),
                    simOutputs.get((int) step - 1), isHistoryStep(), errSignalVar, retval);
        } else {
            for (Pair<String, String> variable : variables) {
                try {
                    retval.accumulate(variable.getSecond(), "");
                } catch (JSONException e) {
                    // Nothing
                }
            }

            try {
                retval.accumulate(errSignalVar, "");
            } catch (JSONException e) {
                // Nothing
            }
        }
        return retval;
    }

    // -------------------------------------------------------------------------

    /**
     * Update the internal configuration with values received from the replay component through the
     * data pool.
     * 
     * @param json
     *            the data pool JSON object
     * @throws KiemExecutionException
     *             when reading the JSON object fails
     */
    private void updateConfiguration(final JSONObject json) throws KiemExecutionException {
        try {
            JSONObject config = json.getJSONObject(configVarName);
            if (config.has(KartConstants.VAR_ESOFILE)) {
                Object object = config.get(KartConstants.VAR_ESOFILE);
                if (object != null) {
                    if (object instanceof Path) {
                        esoFilePath = (Path) object;
                    } else if (object instanceof String) {
                        esoFilePath = new Path((String) object);
                    }
                }
            }
            trainingMode = ((Boolean) config.get(KartConstants.VAR_TRAINMODE)).booleanValue();
            eot = ((Boolean) config.get(KartConstants.VAR_EOT)).booleanValue();
            tracenum = ((Integer) config.get(KartConstants.VAR_TRACE)).intValue();
        } catch (JSONException e) {
            throw new KiemExecutionException(
                    "Could not update configuration. Are the KART components positioned "
                            + "correctly in the schedule? "
                            + "I. e., the Replay component directly before the simulator "
                            + "and the Validation component " + "directly after the simulator.",
                    true, e);
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Save the contents of the data pool, i. e. signals and variables, for later use. This method
     * automatically filters out KART-internal data pool variables.
     * 
     * @param json
     *            the data pool JSON object
     * @throws KiemExecutionException
     *             when reading the data pool fails
     */
    private void recordDataPool(final JSONObject json) throws KiemExecutionException {
        String[] fieldNames = JSONObject.getNames(json);
        HashMap<String, String> signals = new HashMap<String, String>();
        HashMap<String, String> vars = new HashMap<String, String>();

        for (String field : fieldNames) {
            try {
                Object obj = json.get(field);
                if (obj instanceof JSONObject && JSONSignalValues.isSignalValue(obj)) {
                    // it's a signal
                    if (JSONSignalValues.isPresent(obj) && recInputs.get((int) step - 1) != null
                            && !recInputs.get((int) step - 1).containsKey(field)) {
                        if (JSONSignalValues.getSignalValue(obj) == null) {
                            signals.put(field, null);
                        } else {
                            signals.put(field, JSONSignalValues.getSignalValue(obj).toString());
                        }

                    }
                } else {
                    // we do not want to record our internal variables to the ESO file, that
                    // would most definitely bite us in the ass during replay. We also do not
                    // want variables with no value in the ESO file.
                    if (!(field.equals(configVarName) || field.equals(outputVarName)
                            || field.equals(prevInputVar) || obj.toString().isEmpty())) {
                        vars.put(field, obj.toString());
                    }
                }
            } catch (JSONException e) {
                throw new KiemExecutionException("Cannot read data pool", true, e);
            }
        }

        simOutputs.add(signals);
        simVariables.add(vars);
    }

    // -------------------------------------------------------------------------

    /**
     * Extract the data read from the ESO trace file by the Replay component from the data pool and
     * save it for later use.
     * 
     * @param json
     *            the data pool JSON object
     * @throws KiemExecutionException
     *             when reading the data pool fails
     */
    private void getEsoData(final JSONObject json) throws KiemExecutionException {
        try {
            /*
             * Record previous input signals
             */
            HashMap<String, Object> inputSignals = new HashMap<String, Object>();
            JSONObject prevInput = json.getJSONObject(prevInputVar);

            // this is necessary because the JSON library return an unparameterized Iterator.
            @SuppressWarnings("unchecked")
            Iterator<String> keys = prevInput.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                if (JSONSignalValues.isPresent(prevInput.opt(key))) {
                    inputSignals.put(key, JSONSignalValues.getSignalValue(prevInput.opt(key)));
                }
            }
            recInputs.add(inputSignals);

            if (!trainingMode) {
                /*
                 * Record output signals and variables
                 */
                HashMap<String, Object> outputSignals = new HashMap<String, Object>();
                HashMap<String, Object> outputVariables = new HashMap<String, Object>();
                JSONObject output = json.getJSONObject(outputVarName);

                // again, this is necessary because the JSON library returns an unparameterized
                // Iterator.
                @SuppressWarnings("unchecked")
                Iterator<String> outputKeys = output.keys();

                while (outputKeys.hasNext()) {
                    String outputKey = outputKeys.next();

                    try {
                        JSONObject outputSignal = output.getJSONObject(outputKey);
                        if (outputSignal.has("present") && outputSignal.getBoolean("present")) {
                            // it actually is a present signal
                            outputSignals.put(outputKey, outputSignal.opt("value"));
                        }
                    } catch (JSONException e) {
                        // it probably is a variable
                        outputVariables.put(outputKey, output.opt(outputKey));
                    }
                }

                esoOutputs.add(outputSignals);
                esoVariables.add(outputVariables);
            }
        } catch (JSONException e) {
            throw new KiemExecutionException(KartConstants.ERR_JSON, true, e);
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Check whether the user actually selected an ESI or ESO file or messed up. This does not
     * actually try to read the file, it just checks for the correct extension.
     * 
     * @param properties
     *            the properties
     * @throws KiemPropertyException
     *             when the user messed up and did not select an ESI or ESO file
     */
    @Override
    public void checkProperties(final KiemProperty[] properties) throws KiemPropertyException {
        // noting to check here
    }

    // -------------------------------------------------------------------------
}