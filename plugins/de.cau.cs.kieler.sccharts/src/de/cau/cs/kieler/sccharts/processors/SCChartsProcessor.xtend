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
package de.cau.cs.kieler.sccharts.processors

import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.kicool.compilation.ProcessorType

/**
 * @author ssm
 * @kieler.design 2017-07-09 proposed
 * @kieler.rating 2017-07-09 proposed yellow  
 */
abstract class SCChartsProcessor extends Processor<SCCharts, SCCharts> {
    
    override getType() {
        ProcessorType.TRANSFORMATOR
    }
    
}