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
package de.cau.cs.kieler.prom.build

import com.google.common.io.Files
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.CompilationContext
import de.cau.cs.kieler.kicool.compilation.Compile
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorStart
import de.cau.cs.kieler.kicool.environments.Environment
import de.cau.cs.kieler.kicool.environments.MessageObjectReferences
import de.cau.cs.kieler.prom.ModelImporter
import de.cau.cs.kieler.prom.PromPlugin
import de.cau.cs.kieler.prom.templates.TemplateManager
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.iterators.StateIterator
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Collections
import java.util.List
import java.util.Observable
import java.util.Observer
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.StringInputStream
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorFinished

/**
 * @author aas
 *
 */
class KiCoModelCompiler extends ModelCompiler {
    public val outputTemplate = new ConfigurableAttribute("outputTemplate", "")
    public val outputFileExtension = new ConfigurableAttribute("outputFileExtension", ".c")
    public val compilationSystem = new ConfigurableAttribute("compilationSystem", "de.cau.cs.kieler.sccharts.netlist.simple")
    
    private var IFile compiledFile
    
    /**
     * Compile a model file via KiCo. 
     * 
     * @param file the file of the model to be built
     * @param model model to be built
     */
    override doCompile(IFile file, EObject model) {
        // Save reference of the file that should be compiled
        compiledFile = file
        // Prepare result
        val result = new ModelCompilationResult()
        
        // Compile model
        if (model != null) {
            // Compile
            val context = compileWithKiCo(model)
            // Check all intermediate results for errors and warnings
            var boolean noIssues = true
            var Processor<?,?> lastResult
            for (iResult : context.processorInstancesSequence) {
                lastResult = iResult
                val errors = iResult.environment.errors
                val warnings = iResult.environment.warnings
                // Add build problems to result
                if(!errors.isNullOrEmpty) {
                    noIssues = false
                    val errorMessage = "Error in '"+iResult.id+"':"+errors.messages
                    result.addProblem(BuildProblem.createError(file, errorMessage))
                }
                if(!warnings.isNullOrEmpty) {
                    noIssues = false
                    // Add build problem to result
                    val warningMessage = "Warning in '"+iResult.id+"':"+warnings.messages
                    result.addProblem(BuildProblem.createWarning(file, warningMessage))
                }
            }
            // Get final result of compilation
            val resultModel = lastResult.environment.getProperty(Environment.MODEL)
            
            // Save result if no errors and warnings
            if(noIssues) {
                // If fileExtension starts with a letter, add a dot as prefix
                var String fileExt = outputFileExtension.stringValue
                if(fileExt.matches("^\\w.*")) {
                    fileExt = "."+fileExt
                }
                // Flush compilation result to target
                val targetResource = KiCoBuilder.computeTargetResource(file.projectRelativePath.toOSString,
                                                                       outputFolder.stringValue,
                                                                       fileExt,
                                                                       file.project)
                val targetFile = targetResource as IFile
                saveCompilationResult(resultModel, targetFile)
                
                // Add generated file to result
                result.addCreatedFile(targetFile)
                
                // Create simulation code
                if(simulationProcessor != null) {
                    // Get all variables that make up the current state of the model
                    // to add them to the simulation data pool.
                    // These are the variables in the model, as well as the PRE_XXX variables 
                    val interfaceTypes = (simulationProcessor.interfaceTypes.value as List)
                    if(!interfaceTypes.isNullOrEmpty && interfaceTypes.contains("other")) {
                        val registerVariables = context.getRegisterVariables
                        simulationProcessor.additionalVariables.value = #{"other" -> registerVariables.map[it.name]}
                    }
                    
                    // Compute output file of simulation generation
                    var IPath simulationTargetFolder = new Path("")
                    if(!outputFolder.stringValue.isNullOrEmpty) {
                        simulationTargetFolder = new Path(outputFolder.stringValue).append("sim").append("code")
                    }
                    val fileNameWithoutExtension = Files.getNameWithoutExtension(file.name)
                    val simulationFileName = "Sim_" + fileNameWithoutExtension + fileExt
                    val simulationTarget = simulationTargetFolder.append(simulationFileName)
                    // Set model specific variables of simulation template processor
                    simulationProcessor.target.value = simulationTarget.toOSString
                    simulationProcessor.modelPath.value = file.projectRelativePath.toOSString
                    simulationProcessor.compiledModelPath.value = targetFile.projectRelativePath.toOSString
                    simulationProcessor.monitor = monitor
                    simulationProcessor.project = file.project
                    simulationProcessor.model = model
                    // Run processor
                    result.simulationGenerationResult = simulationProcessor.process
                }    
            }
        }
        
        return result
    }
    
    override updateDependencies(DependencyGraph dependencies, List<IFile> files, ResourceSet resourceSet) {
        for(f : files) {
            if(f.fileExtension.equalsIgnoreCase("sct")) {
                val node = dependencies.getOrCreate(f)
                // Remove old dependencies
                node.removeAllDependencies()
                // Add new dependencies
                val model = ModelImporter.getEObject(f, resourceSet)
                if(model instanceof State && model != null) {
                    val state = model as State
                    val iter = StateIterator.sccAllStates(state)
                    while(iter.hasNext) {
                        val s = iter.next
                        if(s.reference != null && s.reference.scope != null) {
                            val refResource = s.reference.scope.eResource
                            val refFile = ModelImporter.toPlatformResource(refResource)
                            val refNode = dependencies.getOrCreate(refFile)
                            node.addDependency(refNode)
                        }
                    }
                }
            }
        }
    }
    
    private def IProject getProject() {
        return compiledFile?.project
    }
    
    private def String getMessages(MessageObjectReferences messageObjectReferences) {
        return messageObjectReferences.map[messageObject |
                     if (messageObject.exception != null) {
                         ((new StringWriter) => [messageObject.exception.printStackTrace(new PrintWriter(it))]).toString()
                     } else {
                        messageObject.message
                     }
                ].join("\n- ")
    }
    
    private def List<ValuedObject> getRegisterVariables(CompilationContext context) {
        // TODO: Search SCG for pre(GUARD) calls and add them to the returned list
        return newArrayList
    }
    
    private def CompilationContext compileWithKiCo(EObject model) {
        val compilationSystemFile = project?.getFile(compilationSystem.stringValue)
        var CompilationContext context
        if(compilationSystemFile != null && compilationSystemFile.exists) {
            val system = ModelImporter.load(compilationSystemFile)
            if(system != null && system instanceof de.cau.cs.kieler.kicool.System) {
                context = Compile.createCompilationContext(system as de.cau.cs.kieler.kicool.System, model)
            } else {
                throw new Exception("Compilation system could not be loaded from resource '"+compilationSystemFile+"'")
            }
        } else {
            val compilationSystemID = compilationSystem.stringValue
            context = Compile.createCompilationContext(compilationSystemID, model)
        }
        context.startEnvironment.setProperty(Environment.INPLACE, true)
        // Add observer to update the progress monitor
        if(monitor != null) {
            context.addObserver(new Observer() {
                val startTimeMap = <String, Long> newHashMap
                val durationTimeMap = <String, Long> newHashMap
                 
                override update(Observable o, Object arg) {
                    val context = o as CompilationContext
                    if(context != null) {
                        if(arg instanceof ProcessorStart) {
                            val currentProcessor = arg.processorInstance
                            val currentProcessorIndex = context.processorInstancesSequence.indexOf(currentProcessor)
                            val processorCount = context.processorInstancesSequence.size
                            monitor.subTask("Compiling '"+compiledFile.name+"' \n"
                                          + "Starting processor "+(currentProcessorIndex+1)+"/"+processorCount+": "
                                          + "'"+currentProcessor.name+"'")
                                          
                            startTimeMap.put(currentProcessor.name, System.currentTimeMillis)
                        }
                        // Check how long the processor took
                        if(arg instanceof ProcessorFinished) {
                            val currentProcessor = arg.processorInstance
                            val currentProcessorIndex = context.processorInstancesSequence.indexOf(currentProcessor)
                            val processorCount = context.processorInstancesSequence.size
                            val startTime = startTimeMap.getOrDefault(currentProcessor.name, -1l)
                            val duration = System.currentTimeMillis - startTime
                            durationTimeMap.put(currentProcessor.name, duration)
                            monitor.subTask("Compiling '"+compiledFile.name+"' \n"
                                          + "Finished processor "+(currentProcessorIndex+1)+"/"+processorCount+": "
                                          + "'"+currentProcessor.name+"'")
                            if(startTime > 0) {
//                                println("'"+currentProcessor.name + "' took " + duration + " ms")
                            }
                        }
                    }
                }
            })
        }
        // Compile the model
        val startTime = System.currentTimeMillis
        context.compile
        val duration = System.currentTimeMillis - startTime
//        System.err.println("KiCo compilation took:"+duration)
        return context
    }
    
    /**
     * Saves the result to the fully qualified target location.
     * If the result string is not empty, this will be saved possibly using the target template for the output.
     * Otherwise the result's EObject will be serialized to the target location.
     * 
     * @param result The KiCo compilation result to be saved
     * @param targetPath File path where the result should be saved
     */
    private def void saveCompilationResult(Object result, IFile targetFile) {
        if(result == null) {
            return;
        } else if(result instanceof CodeContainer) {
            val String resultCode = result?.get(0)
            saveCode(resultCode, targetFile)
        } else if(result instanceof EObject) {
            // Serialize EObject
            saveEObject(result, targetFile)
        } else {
            throw new Exception("Cannot save compilation result:"+result)
        }
    }

    private def void saveCode(String code, IFile targetFile) {
        // Save generated code to file, possibly using a target template
        val resolvedTargetTemplate = PromPlugin.performStringSubstitution(outputTemplate.stringValue, project)
        if (resolvedTargetTemplate.isNullOrEmpty) {
            val inputStream = new StringInputStream(code)
            PromPlugin.createResource(targetFile, inputStream, true)
        } else {
            // Inject compilation result into target template
            val modelName = Files.getNameWithoutExtension(compiledFile.name)
            val generator = new TemplateManager(project)
            val wrapperCode = generator.processTemplate(resolvedTargetTemplate, 
                #{TemplateManager.KICO_GENERATED_CODE_VARIABLE -> code,
                  TemplateManager.MODEL_NAME_VARIABLE -> modelName})
            // Save output
            val inputStream = new StringInputStream(wrapperCode)
            PromPlugin.createResource(targetFile, inputStream, true)
        }
    }

    /**
     * Serializes and saves an EObject in the file system.
     * 
     * @param eobject the EObject
     * @param targetLocation the fully qualified path where it should be saved
     */
    private def void saveEObject(EObject eobject, IFile targetFile) {
        // Create directories
        PromPlugin.createResource(targetFile.parent)
        
        // Remove old resource
        if(targetFile.exists) {
            targetFile.delete(true, null)
        }
        
        // Create new resource
        val resSet = new XtextResourceSet();
        val targetLocation = targetFile.location.toOSString
        val resource = resSet.createResource(URI.createFileURI(targetLocation));
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        resource.getContents().add(eobject);
    
        // now save the content.
        try {
          resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    
    override toString() {
        return "KiCo model compiler"
    }
}