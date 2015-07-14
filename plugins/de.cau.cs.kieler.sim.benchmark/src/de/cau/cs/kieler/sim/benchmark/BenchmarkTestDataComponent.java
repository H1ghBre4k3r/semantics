/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2009 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sim.benchmark;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.json.JSONException;
import org.json.JSONObject;

import de.cau.cs.kieler.core.util.Pair;
import de.cau.cs.kieler.sim.kiem.IJSONObjectDataComponent;
import de.cau.cs.kieler.sim.kiem.IKiemEventListener;
import de.cau.cs.kieler.sim.kiem.KiemEvent;
import de.cau.cs.kieler.sim.kiem.KiemExecutionException;
import de.cau.cs.kieler.sim.kiem.KiemInitializationException;
import de.cau.cs.kieler.sim.kiem.properties.KiemProperty;
import de.cau.cs.kieler.sim.kiem.ui.datacomponent.JSONObjectSimulationDataComponent;

/**
 * This BenchmarkTestDataComponent class is supposed to collect benchmark values and compare them to
 * previously recorded ones. It raises an exception if a predefined absolute+relative limit is
 * reached.
 * 
 * @author cmot
 * @kieler.design 2015-06-10 proposed
 * @kieler.rating 2015-06-10 proposed
 * 
 */
public class BenchmarkTestDataComponent extends JSONObjectSimulationDataComponent implements
        IJSONObjectDataComponent , IKiemEventListener {

    public static final String ERRORMESSAGE = "benchError";

    /** The Constant KIEM_PROPERTY_MAX. */
    private static final int KIEM_PROPERTY_MAX = 4;
    private static final String KIEM_PROPERTY_NAME_MARKER = "Benchmark Marker";
    private static final String KIEM_PROPERTY_NAME_ABSOLUTETOLERANCE = "Number of Runs";
    private static final String KIEM_PROPERTY_NAME_RELATIVETOLERANCE = "Benchmark Marker";
    private static final String KIEM_PROPERTY_NAME_CONSOLIDATETICKS = "Consolidate Ticks";
    private static final int KIEM_PROPERTY_MARKER = 0;
    private static final int KIEM_PROPERTY_ABSOLUTETOLERANCE = 1;
    private static final int KIEM_PROPERTY_RELATIVETOLERANCE = 2;
    private static final int KIEM_PROPERTY_CONSOLIDATETICKS = 3;
    private static final String KIEM_PROPERTY_MARKER_DEFAULT =
            "benchTime, benchSource, benchExecutable, benchCompileTime";
    private static final String KIEM_PROPERTY_ABSOLUTETOLERANCE_DEFAULT = "0,0,0,1000";
    private static final String KIEM_PROPERTY_RELATIVETOLERANCE_DEFAULT = "10,10,10,10";
    private static final String KIEM_PROPERTY_CONSOLIDATETICKS_DEFAULT = "1,0,0,0";

    private static final String BENCHMARK_FILE_ENDING = "bench";
    private static final String BENCHMARK_DATA_SEPARATOR = ":";

    private String[] markerArray;
    private double[] absoluteToleranceArray;
    private double[] relativeToleranceArray;
    private boolean[] consolidateTicksArray;

    /** The gathered benchmark data. */
    private double[] benchmarkData;

    /** The current tick. */
    private static long tick = 0;

    /** The benchmark file path. */
    private IPath benchmarkFilePath = null;
    
    /** The tick when benchmark comparison is done. */
    private int benchmarkTick = -1;

    // -------------------------------------------------------------------------

    /**
     * Instantiates a new benchmark data component.
     */
    public BenchmarkTestDataComponent() {
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void initialize() throws KiemInitializationException {
        tick = 0;
        benchmarkTick = -1;
        
        IPath modelFilePath = this.getModelFilePath();
        benchmarkFilePath =
                modelFilePath.removeFileExtension().addFileExtension(BENCHMARK_FILE_ENDING);
        
        // write the comma separated values into it if these values exist
        String markers = this.getProperties()[KIEM_PROPERTY_MARKER + KIEM_PROPERTY_DIFF].getValue();
        markerArray = markers.split(",");

        // make a new array for the benchmark data
        benchmarkData = new double[markerArray.length];

        String absolute =
                this.getProperties()[KIEM_PROPERTY_ABSOLUTETOLERANCE + KIEM_PROPERTY_DIFF]
                        .getValue();
        String[] absoluteToleranceStringArray = absolute.split(",");
        absoluteToleranceArray = new double[absoluteToleranceStringArray.length];
        int i = 0;
        for (String item : absoluteToleranceStringArray) {
            absoluteToleranceArray[i++] = Double.parseDouble(item);
        }

        String relative =
                this.getProperties()[KIEM_PROPERTY_RELATIVETOLERANCE + KIEM_PROPERTY_DIFF]
                        .getValue();
        String[] relativeToleranceStringArray = relative.split(",");
        relativeToleranceArray = new double[relativeToleranceStringArray.length];
        i = 0;
        for (String item : relativeToleranceStringArray) {
            relativeToleranceArray[i++] = Double.parseDouble(item);
        }

        String consolidateTicks =
                this.getProperties()[KIEM_PROPERTY_CONSOLIDATETICKS + KIEM_PROPERTY_DIFF]
                        .getValue();
        String[] consolidateTicksStringArray = consolidateTicks.split(",");
        consolidateTicksArray = new boolean[consolidateTicksStringArray.length];
        i = 0;
        for (String item : consolidateTicksStringArray) {
            consolidateTicksArray[i++] = (Integer.parseInt(item) != 0);
        }
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void wrapup() throws KiemInitializationException {
        System.out.println("+++ wrapup start: " + tick);

        try {
            possiblyWriteBenchmarkFile(benchmarkFilePath, benchmarkData);
        } catch (IOException e) {
            throw new KiemInitializationException("Could not write Benchmark data.", false, e, true);
        } catch (CoreException e) {
            throw new KiemInitializationException("Could not write Benchmark data.", false, e, true);
        }
        System.out.println("+++ wrapup done ");
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public boolean isProducer() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public boolean isObserver() {
        return true;
    }

    // -------------------------------------------------------------------------

    /**
     * Gets the marker index for a marker name.
     * 
     * @param markerName
     *            the marker name
     * @return the marker index
     */
    private int getMarkerIndex(String markerName) {
        int i = 0;
        for (String marker : markerArray) {
            marker = marker.trim();
            if (marker.equals(markerName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public JSONObject doStep(JSONObject jSONObject) throws KiemExecutionException {
        // In the step method open a benchmark file "model.ben" and
        //tick = this.g

        // String benchmarkDataEntry = "";
        // // Run number is the first value
        // benchmarkDataEntry += "" + BENCHMARK_DATA_SEPARATOR;

        // // Tick number is the second value
        // benchmarkDataEntry += "" + (tick++);

        for (String marker : markerArray) {
            // Trim away any white space
            marker = marker.trim();
            // Separation of all values
            // benchmarkDataEntry += BENCHMARK_DATA_SEPARATOR;
            if (jSONObject.has(marker)) {
                Object obj;
                try {
                    obj = jSONObject.get(marker);
                    double newValue = 0;
                    int i = getMarkerIndex(marker);
                    if (obj instanceof String) {
                    }
                    if (obj instanceof Integer) {
                        newValue = (Integer) obj;
                    }
                    if (obj instanceof Double) {
                        newValue = (Double) obj;
                    }
                    if (newValue > 0) {
                        if (consolidateTicksArray[i]) {
                            benchmarkData[i] = benchmarkData[i] + newValue;
                        } else {
                            benchmarkData[i] = newValue;
                        }
                    }
                    
                } catch (JSONException e) {
                    // Ignore errors
                }
            }
        }
        
        JSONObject returnValue = null;
        try {
            returnValue = compareBenchmarkFile(benchmarkFilePath, benchmarkData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (KiemInitializationException e) {
            e.printStackTrace();
        } 
        return returnValue;

    }

    // -------------------------------------------------------------------------

    public JSONObject compareBenchmarkFile(final IPath path, final double[] data) throws IOException,
            CoreException, KiemInitializationException {

        if (benchmarkTick > -1 && benchmarkTick != tick) {
            // The benchmark tick has not been reached
            return null;
        }



        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IFile file = root.getFile(path);

        if (!file.exists()) {
            // Only validate if a *.bench file exists
            System.out.println("+++ NO BENCHMARK FILE EXISTS!");
            return null;
        }
        

        String stringPath = file.getRawLocation().toString();

        // Read in old data
        final double[] oldData = new double[data.length];
        FileInputStream fis = new FileInputStream(stringPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;

        String benchmarkTickString = br.readLine();
        try {
            benchmarkTick = Integer.parseInt(benchmarkTickString);
        } catch (Exception e) {
        }

        while ((line = br.readLine()) != null) {
            String[] lineData = line.split(BENCHMARK_DATA_SEPARATOR);
            if (lineData.length == 2) {
                String dataName = lineData[0];
                String dataValueString = lineData[1];
                try {
                    double dataValue = Double.parseDouble(dataValueString);
                    int i = getMarkerIndex(dataName);
                    oldData[i] = dataValue;
                } catch (Exception e) {
                }
            }
        }
        br.close();
        fis.close();
        
        if (benchmarkTick != tick) {
            // The benchmark tick has not been reached
            return null;
        }
        
        System.out.print("+++ COMPARE TO BENCHMARK FILE ... ");
        
        // Compare old data and current data using tolerance

        // Write header
        for (String marker : markerArray) {
            // Trim away any white space
            marker = marker.trim();
            int i = getMarkerIndex(marker);

            double oldValue = oldData[i];
            double newValue = data[i];

            double relativeToleranceAddition = ((relativeToleranceArray[i] * oldValue) / 100);
            double overallToleranceAddition = absoluteToleranceArray[i] + relativeToleranceAddition;

            double barrierValue = oldValue + overallToleranceAddition;

            JSONObject returnValue = new JSONObject();
            try {
            if (newValue > barrierValue) {
                System.out.println("BENCHMARK FAILED ");
                // This indicates an error
                String message =
                        "Violation of benchmark limit for '" + marker + "': old value is '"
                                + oldValue + "', current value is '" + newValue + "' > '"
                                + barrierValue
                                + "' which is the barrier value including tolerance ("
                                + absoluteToleranceArray[i] + " + " + relativeToleranceArray[i]
                                + "%).";
                    returnValue.accumulate(ERRORMESSAGE, message);
                    return returnValue;
                
            } else {
                System.out.println("BENCHMARK OK ");
                returnValue.accumulate(ERRORMESSAGE, "");
                return returnValue;
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    // -------------------------------------------------------------------------

    public void possiblyWriteBenchmarkFile(final IPath path, final double[] data)
            throws IOException, CoreException, KiemInitializationException {

        System.out.println("+++ WRITE TEXT FILE ");

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IFile file = root.getFile(path);

        if (file.exists()) {
            // We do not want to overwrite *.bench files
            return;
        }

        file.create(null, IResource.NONE, null);

        String stringPath = file.getRawLocation().toString();
        PrintWriter out = new PrintWriter(stringPath);

        // by convention the first number represents the last tick which is used to for evaluation
        out.println(tick-1);

        for (String marker : markerArray) {
            String line = "";

            // Trim away any white space
            marker = marker.trim();
            int i = getMarkerIndex(marker);

            line = marker + BENCHMARK_DATA_SEPARATOR + data[i];
            out.println(line);
        }

        System.out.println("+++ WRITE TEXT FILE: CLOSE ");
        out.close();
    }

    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public KiemProperty[] doProvideProperties() {
        final int nProperties = KIEM_PROPERTY_MAX;
        KiemProperty[] properties = new KiemProperty[nProperties];
        properties[KIEM_PROPERTY_MARKER] =
                new KiemProperty(KIEM_PROPERTY_NAME_MARKER, KIEM_PROPERTY_MARKER_DEFAULT);
        properties[KIEM_PROPERTY_ABSOLUTETOLERANCE] =
                new KiemProperty(KIEM_PROPERTY_NAME_ABSOLUTETOLERANCE,
                        KIEM_PROPERTY_ABSOLUTETOLERANCE_DEFAULT);
        properties[KIEM_PROPERTY_RELATIVETOLERANCE] =
                new KiemProperty(KIEM_PROPERTY_NAME_RELATIVETOLERANCE,
                        KIEM_PROPERTY_RELATIVETOLERANCE_DEFAULT);
        properties[KIEM_PROPERTY_CONSOLIDATETICKS] =
                new KiemProperty(KIEM_PROPERTY_NAME_CONSOLIDATETICKS,
                        KIEM_PROPERTY_CONSOLIDATETICKS_DEFAULT);
        return properties;
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
            tick = ((Pair<Long, Long>) event.getInfo()).getFirst().longValue();
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
}
