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

import com.google.common.io.Files
import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.xtend.lib.annotations.Data

/**
 * Data holder for test models in the models repository.
 * 
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
@Data
class TestModelData {
    /** The system path to the repository root. */
    val Path repositoryPath
    /** Relative path to the model file. */
    val Path modelPath
    /** Relative paths to the trace files. */
    val List<Path> tracePaths
    /** Unique ID for the resource set of the model. */
    val String resourceSetID
    /** All model paths with the same resource set ID, including this model. */
    val Set<Path> resourceSetModels
    /** Set of property String characterizing the model. */
    val Set<String> modelProperties
    /** Set of documentation tags shown by the model. */
    val Set<String> docuTags
    /** Map with all properties specified in the repository for this model. */
    val Map<String, String> additionalProperties
    /** Flag indicating that this model is confidential and its content should not be publish in any way. */
    val boolean confidential
    
    /** The file name (incl. extension) of the loaded model. */
    public def String modelFile() {
        return modelPath.fileName.toString
    }
    
    /** The file name without extension of the loaded model. */
    public def String modelFileBasename() {
        return Files.getNameWithoutExtension(modelFile)
    }
}