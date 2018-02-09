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
package de.cau.cs.kieler.simulation.backends

import de.cau.cs.kieler.prom.drafts.PromProjectDrafts

/**
 * @author aas
 *
 */
class JavaSimulationBackend extends OriginBasedSimulationBackend {
    /**
     * {@inheritDoc}
     */
    override getBuildConfigOrigin() {
        "platform:/plugin/de.cau.cs.kieler.prom/resources/java/java-backend.kibuild"
    }
    
    /**
     * {@inheritDoc}
     */
    override getName() {
        "Java"
    }
    
    /**
     * {@inheritDoc}
     */
    override getProjectDraft() {
        return PromProjectDrafts.genericJava
    }
    
    /**
     * {@inheritDoc}
     */
    override getSupportedProcessors() {
        return #["de.cau.cs.kieler.scg.processors.codegen.java",
                 "de.cau.cs.kieler.scg.processors.transformators.sjlp"]
    }
    
    /**
     * {@inheritDoc}
     */
    override getSupportedFileExtensions() {
        return #["java"]
    }
}