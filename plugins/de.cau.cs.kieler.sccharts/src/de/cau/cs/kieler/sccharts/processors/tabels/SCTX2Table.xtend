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
package de.cau.cs.kieler.sccharts.processors.tabels

import com.google.inject.Inject
import de.cau.cs.kieler.kicool.compilation.ExogenousProcessor
import de.cau.cs.kieler.sccharts.SCCharts
import java.util.List

/**
 * @author stu114663
 *
 */
class SCTX2Table extends ExogenousProcessor<SCCharts, List<List<String>>> {
    
    @Inject
    var de.cau.cs.kieler.sccharts.processors.tabels.StateTransitionTableBuilder sttb
    
    override getId() {
        "de.cau.cs.kieler.sccharts.processors.SCTX2Table"
    }
    
    override getName() {
        "SCTX2Table"
    }
    
    override process() {
        sttb.model = getModel
        // TODO check for empty model
        model = sttb.build
    }
}