/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2016 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.registration

import com.google.inject.Guice
import de.cau.cs.kieler.kicool.KiCoolStandaloneSetup
import de.cau.cs.kieler.kicool.System
import de.cau.cs.kieler.kicool.compilation.Processor
import java.io.IOException
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.XtextResourceSet

import static com.google.common.base.Preconditions.*

import static extension java.lang.String.format
import de.cau.cs.kieler.kicool.classes.SourceTargetPair

/**
 * Main class for the registration of systems and processors.
 * 
 * @author ssm
 * @kieler.design 2016-10-19 proposed 
 * @kieler.rating 2016-10-19 proposed yellow
 *
 */
class KiCoolRegistration {
    
    public static val EXTENSION_POINT_SYSTEM = "de.cau.cs.kieler.kicool.system"
    public static val EXTENSION_POINT_PROCESSOR = "de.cau.cs.kieler.kicool.processor"
    
    private static val injector = Guice.createInjector
    private static val kicoolXtextInjector = KiCoolStandaloneSetup.doSetup
    
    private static val Map<String, System> modelsMap = new HashMap<String, System>()
    private static val Map<String, System> modelsIdMap = new HashMap<String, System>()
    private static val List<EObject> systemsModels = loadRegisteredSystemModels
    private static val Map<String, System> temporarySystems = <String, System> newHashMap
    
    private static val Map<String, Class<? extends Processor>> processorMap = new HashMap<String, Class<? extends Processor>>()
    private static val Map<String, SourceTargetPair> processorModelTypes = new HashMap<String, SourceTargetPair>()
    private static val List<Class<? extends Processor>> processorList = loadRegisteredProcessors
    
    
    static def getInjector() {
        injector
    }
    
    static def getInstance(Class<?> clazz) {
        injector.getInstance(clazz);
    }
    
    static def getInstance(Object object) {
        injector.getInstance(object.getClass());
    }
    
    static def List<EObject> getSystemModels() {
        val allSystemModels = newArrayList
        if(!temporarySystems.isEmpty) {
            allSystemModels.addAll(temporarySystems.values)
        }
        allSystemModels.addAll(systemsModels)
        return allSystemModels
    }
    
    static def registerTemporarySystem(System system) {
        val id = system.id
        if(modelsIdMap.containsKey(id)) {
            throw new Exception("Cannot register temporary system '"+id+"'. Another system with this id is already registered.")
        }
        temporarySystems.put(id, system)
    }
    
    static def boolean isTemporarySystem(String id) {
        return temporarySystems.containsKey(id)
    }
    
    static def System getSystemByResource(String res) {
        checkArgument(modelsMap.containsKey(res), "No processor system registered for resource: " + res)
        modelsMap.get(res)
    }
    
    static def System getSystemById(String id) {
        if (temporarySystems.containsKey(id)) {
            return temporarySystems.get(id)
        }
        checkArgument(modelsIdMap.containsKey(id), "No processor system registered with id: " + id)
        modelsIdMap.get(id)
    }
    
    static def System getProcessorSystemModel(String locationString) {
        modelsMap.get(locationString) as System
    }
    
    static def loadRegisteredSystemModels() {
        val systems = getRegisteredSystems
        val modelList = <EObject> newArrayList
        modelsMap.clear
        modelsIdMap.clear
        for(system : systems) {
            try {
                val model = loadEObjectFromResourceLocation(system.key, system.value)
                modelList += model
                modelsMap.put(system.key, model as System) 
                modelsIdMap.put((model as System).id, model as System)
            } catch (Exception e) {
                java.lang.System.err.println("There was an error loading the registered processor system " + system.toString)
                e.printStackTrace
            }
        }
        modelList
    }

    static def getRegisteredSystems() {
        val resourceList = <Pair<String, String>> newArrayList
        val systems = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_SYSTEM);
        for(system : systems) {
            resourceList += new Pair<String, String>(system.getAttribute("system"), system.contributor.name)
        }
        resourceList       
    }
    
    static def EObject loadEObjectFromResourceLocation(String resourceLocation, String bundleId) throws IOException {
        val uri = URI.createPlatformPluginURI("/%s/%s".format(bundleId, resourceLocation), false)
        val XtextResourceSet resourceSet = kicoolXtextInjector.getInstance(XtextResourceSet)
        val Resource resource = resourceSet.getResource(uri, true)
        if (resource != null && resource.getContents() != null && resource.getContents().size() > 0) {
            val eobject = resource.getContents().get(0)
            return eobject
        }
        throw new IOException("Could not load resource '" + resourceLocation + "'!");
    }
    
    static def void addProcessor(Processor processor) {
        processorMap.put(processor.id, processor.class)
        processorList += processor.class
    }
    
    static def loadRegisteredProcessors() {
        val processors = getRegisteredProcessors
        processorMap.clear
        processorModelTypes.clear
        for(processor : processors) {
            try {
                val instance = getInstance(processor) as Processor
                processorMap.put(instance.getId, processor)
                processorModelTypes.put(instance.getId, instance.getSourceTargetTypes)
            } catch(Throwable e) {
                java.lang.System.err.println("KiCool: Cannot load processor " + processor.name + " (" + e + ")");
            }
        }
        processors
    }
    
    static def getRegisteredProcessors() {
        val resourceList = <Class<? extends Processor>> newArrayList
        val processors = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_PROCESSOR);
        for(processor : processors) {
            try {
                val instance = processor.createExecutableExtension("class")
                val clazz = instance.getClass
                resourceList += clazz as Class<? extends Processor> 
                //Class.forName(processor.name) as Class<? extends Processor>
            } catch(Throwable e) {
                java.lang.System.err.println("KiCool: Cannot load processor " + processor.getAttribute("class"));
            }
        }
        resourceList       
    }    
    
    static def getProcessorClass(String id) {
        processorMap.get(id)
    }
    
    static def getProcessorClasses() {
        processorList
    }
    
    static def getProcessorIds() {
        processorMap.keySet
    }
    
    static def getProcessorInstance(String id) {
        val clazz = processorMap.get(id)
        if (clazz == null) return null;
        getInstance(clazz) as Processor
    }
    
    static def checkProcessorCompatibility(String source, String target) {
        if (processorModelTypes.keySet.contains(source) && processorModelTypes.keySet.contains(target)) {
            val sPair = processorModelTypes.get(source)
            val tPair = processorModelTypes.get(target)
            if (sPair.target != tPair.source) {
                return false
            }
        } 
        return true
    }
}