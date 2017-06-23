/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.compilation

import de.cau.cs.kieler.kicool.System

import static extension de.cau.cs.kieler.kicool.compilation.internal.ContextPopulation.*
import de.cau.cs.kieler.kicool.registration.KiCoolRegistration
import de.cau.cs.kieler.kicool.compilation.internal.AsynchronousCompilation

/**
 * Class for preparing compilations programmatically through creating compilation contexts. 
 * The context itself then has a method to invoke the compilation.
 * 
 * @author ssm
 * @kieler.design 2017-02-19 proposed
 * @kieler.rating 2017-02-19 proposed yellow  
 */
class Compile {
    
    /**
     * Easily create a standard compilation context from a compilation system and a source model.
     */
    static def CompilationContext createCompilationContext(System system, Object sourceModel) {
        new CompilationContext => [
            it.system = system
            it.sourceModel = sourceModel
            it.populateContext
            RuntimeSystems.add(it.getSystem, it)
        ]
    }
    
    /**
     * Create a compilation context from a system id and a source model.
     */
    static def CompilationContext createCompilationContext(String systemID, Object sourceModel) {
        createCompilationContext(KiCoolRegistration.getSystemById(systemID), sourceModel)
    }
    
    /**
     * Start a compilation in an asynchronous job.
     */
    static def asyncronousCompilation(CompilationContext compilationContext) {
        AsynchronousCompilation.compile(compilationContext)
    }
}