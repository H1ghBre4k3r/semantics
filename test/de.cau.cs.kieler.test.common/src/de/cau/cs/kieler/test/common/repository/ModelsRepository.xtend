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
package de.cau.cs.kieler.test.common.repository

import com.google.common.collect.HashMultimap
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Properties
import java.util.Set
import org.eclipse.xtend.lib.annotations.Data

import static extension java.lang.Boolean.*

/**
 * Provides the models in the model repositories.
 * 
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
class ModelsRepository {
    
    /* Environment Variable Keys */
    public static val BAMBOO_WD_KEY = "bamboo_working_directory"
    public static val BAMBOO_MODELS_REPOSITORY_KEY = "bamboo_models_repository"
    public static val MODELS_REPOSITORY_KEY = "models_repository"
    
    /** Models retrieved form repository */
    private static val List<TestModelData> models = newLinkedList
    /** Models repositories */
    private static val List<Path> repositories = newLinkedList    
        
    /**
     * Returns an unmodifiable view on the models of the repositories.
     */
    public static def getRepositories() {
        if (ModelsRepository.repositories.empty) {
            findRepositories()
        }
        return ModelsRepository.repositories.unmodifiableView
    }

    /**
     * Retrieves the repositories from the environment.
     */
    private static def findRepositories() {
        ModelsRepository.repositories.clear
        
        // bamboo wd
        val bambooDir = System.getenv(BAMBOO_WD_KEY)
        
        // Find models repository
        var modelsRepositoryEntry = if (System.getenv(MODELS_REPOSITORY_KEY) !== null) {
            System.getenv(MODELS_REPOSITORY_KEY)
        } else {
            System.getenv(BAMBOO_MODELS_REPOSITORY_KEY)
        }
        
        // compose paths
        if (modelsRepositoryEntry !== null) {
            modelsRepositoryEntry = modelsRepositoryEntry.trim
            val paths = if (modelsRepositoryEntry.startsWith("[") && modelsRepositoryEntry.endsWith("]")) {
                modelsRepositoryEntry.substring(1, modelsRepositoryEntry.length - 1).split(",").map[trim].toList
            } else {
                newArrayList(modelsRepositoryEntry)
            }
            for (p : paths) {
                if (bambooDir.nullOrEmpty) {
                    repositories.add(Paths.get(p))
                } else {
                    repositories.add(Paths.get(bambooDir, p))
                }
            }
        } else {
            throw new IllegalArgumentException(
                "Cannot detect the models repository." +
                "Please provide the environment variable \""+MODELS_REPOSITORY_KEY+"\" with the path to the models repository")
        }
    }
    
    /**
     * Returns an unmodifiable view on the test models of the repository
     */
    public static def getModels() {
        if (ModelsRepository.models.empty) {
            findModels()
        }
        return ModelsRepository.models.unmodifiableView
    }
    
    /**
     * Traverses the models repository and creates an index.
     */
    private static def findModels() {
        ModelsRepository.models.clear
        
        for (repo : getRepositories) {
            // check path
            if (!repo.toFile.isDirectory) {
                throw new IllegalArgumentException(repo + " is not an existing directory")
            }
        
            // traverse
            models.addAll((new ModelsRepositoryIndexer).index(repo))
        }
    }

    /**
     * Repository Traverser.
     */
    private static class ModelsRepositoryIndexer extends SimpleFileVisitor<Path> {
        
        static val PathMatcher directoryPropertyFileMatcher = FileSystems.getDefault().getPathMatcher("glob:**/directory.properties")
        static val PathMatcher modelPropertyFileMatcher = FileSystems.getDefault().getPathMatcher("glob:**.properties")
        val directoryPropertyFiles = <Path, Properties>newHashMap
        val modelPropertyFiles = <Path, Properties>newHashMap
        val files = HashMultimap.<Path, Path>create
        val directoryTestModelPropertiesPatterns = <Path, ModelProperties>newHashMap
        
        def index(Path repository) {
            val models = new LinkedList
            // Walk directory
            Files.walkFileTree(repository, this)
            // Calculate directory properties
            for (dir : directoryPropertyFiles.keySet.sortBy[nameCount]) {
                val parentPropertiesPattern = dir.getParentPropertiesPattern(repository)
                directoryTestModelPropertiesPatterns.put(dir, new ModelProperties(parentPropertiesPattern, directoryPropertyFiles.get(dir)))
            }
            
            val resourceSets = <String, Set<Path>>newHashMap
            
            // Create model file index
            for (dir : files.keySet) {
                for (fileGroup : files.get(dir).groupBy[fileName.toString.split("\\.", 2).get(0)].entrySet) {
                    val sameModelFiles = fileGroup.value
                    
                    val modelProperty = sameModelFiles.findFirst[modelPropertyFiles.containsKey(it)]
                    val property = if (modelProperty !== null) {
                        new ModelProperties(sameModelFiles.head.getParentPropertiesPattern(repository), modelPropertyFiles.get(modelProperty))
                    } else {
                        sameModelFiles.head.getParentPropertiesPattern(repository)
                    }
                    if (!property.ignore) {
                        sameModelFiles.remove(modelProperty)
                        for (model : sameModelFiles.filter[ f | property.modelExt.exists[f.fileName.toString.endsWith(it)]]) {
                            val relModelPath = repository.relativize(model)
                            resourceSets.putIfAbsent(property.resourceSetID, newHashSet)
                            val resourceSet = resourceSets.get(property.resourceSetID)
                            if (!property.resourceSetID.nullOrEmpty) resourceSet.add(relModelPath)
                            val traces = sameModelFiles.filter[ f | property.traceExt.exists[f.fileName.toString.endsWith(it)]].sort.toList
                            models.add(new TestModelData(
                                repository,
                                relModelPath,
                                traces.unmodifiableView,
                                property.resourceSetID,
                                resourceSet.unmodifiableView,
                                property.modelProperties.unmodifiableView,
                                property.additionalProperties.unmodifiableView,
                                property.confidential
                            ))
                        }
                    }
                }
            }
            
            return models
        }
        
        private def getParentPropertiesPattern(Path file, Path root) {
            var parent = file.parent
            while (parent.nameCount >= root.nameCount) {
                if (directoryTestModelPropertiesPatterns.containsKey(parent)) {
                    return directoryTestModelPropertiesPatterns.get(parent)
                } else {
                    parent = parent.parent
                }
            }
            return new ModelProperties() // default properties
        }
                
        override visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            super.visitFile(file, attrs)
            if (attrs.isRegularFile) {
                if (directoryPropertyFileMatcher.matches(file)) {
                    directoryPropertyFiles.put(file.parent, new Properties => [load(Files.newInputStream(file))])
                } else if (modelPropertyFileMatcher.matches(file)) {
                    modelPropertyFiles.put(file, new Properties => [load(Files.newInputStream(file))])
                    files.put(file.parent, file)
                } else {
                    files.put(file.parent, file)
                }
            }
            return FileVisitResult.CONTINUE
        }     
    }
    
    /**
     * Internal Data Structure for the models repository files.
     */
    @Data
    private static class ModelProperties {
        
        val boolean ignore
        val boolean confidential
        val Set<String> modelExt
        val Set<String> traceExt
        val String resourceSetID
        val Set<String> modelProperties
        val Map<String, String> additionalProperties
        
        package new() {
            ignore = true
            confidential = false
            modelExt = emptySet
            traceExt = emptySet
            resourceSetID = null
            modelProperties = emptySet
            additionalProperties = emptyMap
        }
        
        package new(ModelProperties parent, Properties propertyFile) {
            ignore = if (propertyFile.containsKey("ignore")) propertyFile.getProperty("ignore").trim.parseBoolean else parent.ignore
            confidential = if (propertyFile.containsKey("confidential")) propertyFile.getProperty("confidential").trim.parseBoolean else parent.confidential
            modelExt = if (propertyFile.containsKey("modelFileExtension")) propertyFile.getProperty("modelFileExtension").split(",").map[trim].map[if (it.startsWith(".")) it else "." + it].toSet else parent.modelExt
            traceExt = if (propertyFile.containsKey("traceFileExtension")) propertyFile.getProperty("traceFileExtension").split(",").map[trim].toSet else parent.traceExt
            resourceSetID = if (propertyFile.containsKey("resourceSetID")) propertyFile.getProperty("resourceSetID").trim else parent.resourceSetID
            // Combine model properties
            modelProperties = newHashSet
            modelProperties.addAll(parent.modelProperties)
            if (propertyFile.containsKey("modelProperties")) {
                for (prop : propertyFile.getProperty("modelProperties").split(",").map[trim].toSet) {
                    if (prop.startsWith("!")) {
                        modelProperties.remove(prop.substring(1).trim)
                    } else {
                        modelProperties.add(prop)
                    }
                }
            }
            additionalProperties = newHashMap      
            additionalProperties.putAll(parent.additionalProperties)
            for (entry : propertyFile.entrySet) {
                additionalProperties.put(entry.key as String, entry.value as String)
            }
        }   
    }
}