/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2019 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kivis.providers

import de.cau.cs.kieler.kicool.registration.IProcessorProvider

/**
 * Provider to make processors available to KiCool.
 * 
 * @author Wechselberg
 */
class KiVisProcessorProvider implements IProcessorProvider {
    
    override getProcessors() {
        #[
            de.cau.cs.kieler.kivis.processor.KiVisJSGenerator,
            de.cau.cs.kieler.kivis.processor.VisualizationGenerator,
            de.cau.cs.kieler.kivis.processor.SimulationVisualizationValues
        ]
    }
    
}