/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.simulation

import de.cau.cs.kieler.kicool.ProcessorGroup
import de.cau.cs.kieler.kicool.ProcessorReference
import de.cau.cs.kieler.kicool.ProcessorSystem
import de.cau.cs.kieler.kicool.registration.KiCoolRegistration
import de.cau.cs.kieler.prom.KiBuildExtensions
import de.cau.cs.kieler.prom.ModelImporter
import de.cau.cs.kieler.prom.PromPlugin
import de.cau.cs.kieler.prom.build.BuildProblem
import de.cau.cs.kieler.prom.build.FileGenerationResult
import de.cau.cs.kieler.prom.build.KielerModelingBuilder
import de.cau.cs.kieler.prom.build.compilation.KiCoModelCompiler
import de.cau.cs.kieler.prom.build.compilation.ModelCompiler
import de.cau.cs.kieler.prom.build.simulation.SimulationCompiler
import de.cau.cs.kieler.prom.drafts.ProjectDraftData
import de.cau.cs.kieler.prom.templates.ModelAnalyzer
import de.cau.cs.kieler.simulation.backends.SimulationBackend
import de.cau.cs.kieler.simulation.core.SimulationManager
import de.cau.cs.kieler.simulation.core.events.SimulationControlEvent
import de.cau.cs.kieler.simulation.core.events.SimulationEvent
import de.cau.cs.kieler.simulation.core.events.SimulationListener
import de.cau.cs.kieler.simulation.core.events.SimulationOperation
import de.cau.cs.kieler.simulation.handlers.ExecutableSimulator
import de.cau.cs.kieler.simulation.handlers.Redirect
import de.cau.cs.kieler.simulation.handlers.SimulationInputFileHandler
import de.cau.cs.kieler.simulation.handlers.TraceHandler
import de.cau.cs.kieler.simulation.kisim.SimulationConfiguration
import java.util.List
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.preferences.InstanceScope
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.osgi.service.prefs.Preferences
import de.cau.cs.kieler.kicool.ProcessorEntry

/**
 * Class to configure and start simulations.
 * 
 * @author aas
 *
 */
class SimulationContext {
    static extension KiBuildExtensions kiBuildExtensions = new KiBuildExtensions
    
    /**
     * The name of the project in which models are compiled and simulated if none is provided.
     */
    public static val TEMPORARY_PROJECT_NAME = "TEMPORARY_SIM_PROJECT"
    
    /**
     * The name of the attribute that stores the flag to determine if the temporary simulation project should be deleted.
     */
    public static val DELETE_TEMPORARY_PROJECT_ATTR = "deleteTemporarySimulationProject"
    
    // Fields for starting the simulation
    /**
     * The simulation configuration file.
     * If this is set, other fields are not used to configure the simulation.
     */
    public var IFile kisimFile
    
    /**
     * The trace file
     */
    public var IFile traceFile
    /**
     * The number of the trace in the trace file, in case there are multiple traces.
     */
    public var int traceNumber
    
    /**
     * The simulation input file.
     */
    public var IFile simInFile
    
    /**
     * The executables that simulate a model.
     */
    public var List<IFile> executableFiles = newArrayList
    
    // Fields for model compilation to executables, which are used to start the simulation afterwards
    /**
     * The progress monitor.
     */
    @Accessors(PUBLIC_GETTER)
    private var IProgressMonitor monitor
    /**
     * The result of the model compilation.
     */ 
    @Accessors(PUBLIC_GETTER)
    private var FileGenerationResult buildResult
    /**
     * The model to be compiled and simulated
     */
    @Accessors(PUBLIC_GETTER)
    private var EObject model
    /**
     * The file handle of the model to be compiled and simulated
     */
    @Accessors(PUBLIC_GETTER)
    private var IFile modelFile
    /**
     * A suited model analyzer for the model
     */
    @Accessors(PUBLIC_GETTER)
    private var ModelAnalyzer modelAnalyzer
    /**
     * A suited simulation backend for the compile chain that is used to compile the model.
     */
    @Accessors(PUBLIC_GETTER)
    private var SimulationBackend simulationBackend
    
    /**
     * The model compilers from the build configuration that compile the model 
     */
    private var List<ModelCompiler> modelCompilers
    /**
     * The simulation compilers that compile the simulation code from the model compilers.
     */
    private var List<SimulationCompiler> simulationCompilers
    
    /**
     * Flag to indicate that the project was initialized already.
     */
    private var boolean initializedProject
    
    /**
     * Simulation listener to delete the temporary simulation project after the simulation.
     */
    private static val listener = new SimulationListener() {
        override update(SimulationEvent e) {
            // Delete the temporary project if it is configured this way
            if(!deleteTemporaryProject) {
                return
            }
            if(e instanceof SimulationControlEvent) {
                if(e.operation == SimulationOperation.STOP) {
                    val root = ResourcesPlugin.getWorkspace.getRoot
                    val project = root.getProject(SimulationContext.TEMPORARY_PROJECT_NAME)
                    project.delete(false, null)
                }
            }
        }
    }
    
    /**
     * Defines if the temporary simulation project should be deleted after the simulation is done.
     * 
     * @param value True if the temporary project should be deleted, false otherwise.
     */
    public static def setDeleteTemporaryProject(boolean value) {
        preferences.putBoolean(DELETE_TEMPORARY_PROJECT_ATTR, value)
    }
    
    /**
     * Checks if the temporary simulation project should be deleted after the simulation is done.
     * 
     * @return true if the temporary simulation project should be deleted after the simulation is done
     */
    public static def getDeleteTemporaryProject() {
        preferences.getBoolean(DELETE_TEMPORARY_PROJECT_ATTR, true)
    }
    
    /**
     * Constructor
     */
    new() {
        // Stop any running simulations
        SimulationManager.instance?.stop
    }
    
    /**
     * Starts the simulation.
     * If required, the configured model is compiled beforehand.
     */
    public def void start() {
        // Add simulation listener if not done yet
        SimulationManager.addListener(listener)
        // Compile the model if required
        compileModel
        // In case compilation was canceled, don't start simulation
        if(isCanceled) {
            return
        }
        startSimulation
    }
    
    /**
     * Sets the simulation backend.
     */
    public def void setSimulationBackend(SimulationBackend simulationBackend) {
        this.simulationBackend = simulationBackend
        // If the backend changes, the temporary project needs to be re-initialized
        initializedProject = false
        // Model compilers
        val buildConfig = simulationBackend.buildConfig
        modelCompilers = buildConfig.createModelCompilers
        modelCompilers.setProgressMonitor
        // Prepare simulation compilers
        simulationCompilers = buildConfig.createSimulationCompilers
        simulationCompilers.setProgressMonitor
    }
    
    /**
     * Sets the model by using an EObject.
     * 
     * @param value The model
     */
    public def void setModel(EObject value) {
        if(value == null) {
            model = null
            modelFile = null
            return
        }
        // Find a suited model analyzer
        modelAnalyzer = ModelAnalyzer.analyzers.findFirst[it.isSupported(value)]
        if(modelAnalyzer != null) {
            model = value
            // Create a (virtual) file for the source model
            var modelName = modelAnalyzer.getModelName(model)
            if (modelName.isNullOrEmpty) {
                modelName = "model"
            }
            val modelFolder = temporaryProject.getFolder("model")
            modelFile = modelFolder.getFile(modelName + "." + modelAnalyzer.supportedFileExtensions.get(0))
            PromPlugin.createResource(modelFile)
            // Find a suited simulation backend for the compile chain of the model analyzer
            if(simulationBackend == null) {
                setSimulationBackend(getSimulationBackend(modelAnalyzer.compileChain.trim))    
            }
        } else {
            throw new Exception("Cannot create a simulation. No model analyzer was found for "+model)    
        }
    }
    
    /**
     * Sets the model by using a file handle.
     * 
     * @param value The model file
     */
    public def void setModelFile(IFile value) {
        if(value == null) {
            model = null
            modelFile = null
            return
        }
        val loadedModel = ModelImporter.load(value)
        setModel(loadedModel)
    }
    
    /**
     * Sets the progress monitor.
     * 
     * @param monitor The monitor
     */
    public def void setMonitor(IProgressMonitor monitor) {
        this.monitor = monitor
        simulationCompilers.setProgressMonitor
        modelCompilers.setProgressMonitor
    }
    
    /**
     * Creates a new simulation with the current configuration.
     * 
     * Using this method a configured model file is not compiled beforehand.
     * Use the start() method to compile a model if needed and directly start the so created simulation.
     */
    public def void startSimulation() {
        // Check consistency
        if(kisimFile == null && executableFiles.isNullOrEmpty) {
            throw new Exception("No kisim file or executables are set to start a simulation.")
        }
        if(executableFiles.size > 2) {
            throw new Exception("Only one or two executables can be used to start a simulation.\n"
                              + "For more complex simulation configurations, please run a kisim file.")
        }
        if(traceFile != null && executableFiles.size > 1) {
            throw new Exception("A trace file can only be used together with a single executable.\n"
                              + "For more complex simulation configurations, please run a kisim file.")
        }
        
        // Configure simulation
        var sim = new SimulationManager
        // Create simulation input handler
        if(simInFile != null) {
            val simInHandler = new SimulationInputFileHandler
            simInHandler.file = simInFile
            sim.addAction(simInHandler)
        }
        // Add trace handler to set the inputs of the executable
        var TraceHandler traceHandler
        if(traceFile != null) {
            traceHandler = new TraceHandler()
            traceHandler.tracePath.value = traceFile.fullPath.toOSString
            traceHandler.traceNumber.value = traceNumber
            sim.addAction("write", traceHandler)
        }
        // Add executables
        if(!executableFiles.isNullOrEmpty) {
            for(exeFile : executableFiles) {
                // Create handler for this executable
                val exeHandler = new ExecutableSimulator
                exeHandler.executableFile = exeFile
                sim.addAction(exeHandler)
                // Create redirect from this executable to all other executables
                for(exeFile2 : executableFiles) {
                    if(exeFile != exeFile2) {
                        val redirect = new Redirect
                        redirect.from.value = exeFile.name
                        redirect.to.value = exeFile2.name
                        sim.addAction(redirect)
                    }
                }
            }
        }
        // Check the trace after the executables performed their tick
        if(traceHandler != null) {
            sim.addAction("check", traceHandler)
            sim.addAction("loadNextTick", traceHandler)
        }
        // Configure simulation from kisim file. Note that configuration from the other fields is lost here.
        if(kisimFile != null) {
            val kisimModel = ModelImporter.load(kisimFile)
            if(kisimModel instanceof SimulationConfiguration) {
                sim = new SimulationManager(kisimModel)
            }
        }
        // Initialize simulation
        if(isCanceled) {
            sim.stop
            return
        }
        sim.initialize
    }
    
    /**
     * Compiles the model to an executables that is ready for simulation.
     * The created executables are added automatically to the simulation to be created.
     */
    public def void compileModel() {
        buildResult = new FileGenerationResult
        if(model == null) { 
            return
        }
        // Check consistency
        if(simulationBackend == null) {
            throw new Exception("Cannot create a simulation. No backend for compilation was set.")
        }
        
        // Initialize project
        if(!initializedProject) {
            if(projectDraft != null) {
                projectDraft.createInitialResources(modelFile.project)
                initializedProject = true
            }
        }
        
        // Compile model
        for (modelCompiler : modelCompilers) {
            // Set compile chain to the compile chain for the model, which is set in the model analyzer
            if(modelCompiler instanceof KiCoModelCompiler) {
                modelCompiler.setCompileChain(modelAnalyzer.compileChain)
            }
            // Compile the model
            val modelCompilationResult = modelCompiler.compile(modelFile, model)
            if(isCanceled) {
                return
            }
            // Check result for errors
            checkErrors(modelCompilationResult.problems)
            
            // Compile simulation code to executable
            for (simulationCompiler : simulationCompilers) {
                for (simFile : modelCompilationResult.createdSimulationFiles) {
                    val simCompilationResult = simulationCompiler.compile(simFile)
                    if(isCanceled) {
                        return
                    }
                    // Check result for errors
                    checkErrors(simCompilationResult.problems)
                    // Add the created executables to the executables that should be simulated
                    executableFiles.addAll(simCompilationResult.createdFiles)
                    buildResult.createdFiles.addAll(simCompilationResult.createdFiles)
                }
            }
        }
    }
    
    /**
     * Checks if the progress monitor was canceled.
     * 
     * @return true if the progress monitor was canceled, false otherwise.
     */
    private def boolean isCanceled() {
        return monitor != null && monitor.isCanceled
    }
    
    /**
     * Sets the monitor of this context as the monitor of the compilers in the list.
     * 
     * @param compilers a list of ModelCompilers and SimulationCompilers
     */
    private def void setProgressMonitor(List<?> compilers) {
        if(compilers.isNullOrEmpty) {
            return
        }
        for(c : compilers) {
            switch(c) {
                ModelCompiler : c.monitor = monitor
                SimulationCompiler : c.monitor = monitor
            }
        }
    }
    
    /**
     * Checks if there are any problems and if so,
     * shows these to the user and throws an exception.
     * 
     */
    private def void checkErrors(List<BuildProblem> problems) {
        if(problems.isNullOrEmpty) {
            return
        }
        buildResult.problems.addAll(problems)
        KielerModelingBuilder.showBuildProblems(problems)
        if(!problems.isNullOrEmpty) {
            throw new Exception("Errors occured when creating the simulation: \n- "+problems.map[toString].join("\n- "))
        }
    }
    
    /**
     * Returns the temporary project for simulation.
     * If the project does not exist yet, it is created beforehand.
     * 
     * @return The temporary project for simulation
     */
    public def IProject getTemporaryProject() {
        val root = ResourcesPlugin.getWorkspace.getRoot
        val project = root.getProject(TEMPORARY_PROJECT_NAME)
        if (!project.exists) {
            project.create(null)
            project.open(null)
        }
        return project
    }
    
    /**
     * Returns the preferences in which the attributes are stored.
     */
    private static def Preferences getPreferences() {
        return InstanceScope.INSTANCE.getNode(SimulationPlugin.PLUGIN_ID)
    }
    
    /**
     * Returns the project draft from the simulation backend,
     * which is used to initialize the temporary simulation project with resources required for simulation compilation.
     */
    private def ProjectDraftData getProjectDraft() {
        return simulationBackend?.projectDraft
    }
    
    /**
     * Searches for a suited simulation backend for the given compile chain.
     * Relevant for the simulation backend is the last processor that creates the target code for the models.
     * 
     * @param compileChain The compileChain
     * @return A simulation backend for the given compile chain
     * @throws Exception if no suited simulation backend is found.
     */
    private def SimulationBackend getSimulationBackend(String compileChain) {
        val lastProcessorOrSystemId = KiCoModelCompiler.splitCompileChain(compileChain).last
        var String lastProcessorId
        try {
            val system = KiCoolRegistration.getSystemById(lastProcessorOrSystemId)
            // If there was no exception, then this is a system
            lastProcessorId = system.processors.getLastProcessorId    
        } catch (Exception ex) {
            try {
                val processor = KiCoolRegistration.getProcessorClass(lastProcessorOrSystemId)
                // If there was no exception, then this is a processor
                lastProcessorId = lastProcessorOrSystemId
            } catch(Exception e) {
            }
        }
        // Find a suited simulation backend for the processor id
        for(backend : SimulationBackend.backends) {
            if(backend.isSupported(lastProcessorId)) {
                return backend
            }
        }
        throw new Exception("No simulation backend was found for the result of the last processor '"+lastProcessorId+"'")    
    }
    
    /**
     * Returns the last processor id of the given processor entry.
     * The entry may also be a group of processors. The id of the last processor in this group is returned in this case.
     */
    private def String getLastProcessorId(ProcessorEntry processors) {
        if(processors instanceof ProcessorGroup) { 
            return processors.processors.last.lastProcessorId
        } else if (processors instanceof ProcessorReference) {
            return processors.id
        } else if (processors instanceof ProcessorSystem) {
            return processors.id
        }
    } 
}