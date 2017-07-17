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
package de.cau.cs.kieler.prom.builder

import java.util.List
import org.eclipse.core.resources.IFile
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * @author aas
 *
 */
class ModelCompilationResult extends FileGenerationResult {
    @Accessors    
    var SimulationGenerationResult simulationGenerationResult
    
    public def List<IFile> getCreatedSimulationFiles() {
        if(simulationGenerationResult != null) {
            return simulationGenerationResult.createdFiles    
        }
        return newArrayList
    }
}