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
package de.cau.cs.kieler.sccharts.ui.debug.hooks

import de.cau.cs.kieler.sccharts.ui.synthesis.hooks.SynthesisHook
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.klighd.kgraph.KNode
import com.google.inject.Inject
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.sccharts.ui.debug.actions.SetBreakpointAction
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.klighd.kgraph.KEdge
import de.cau.cs.kieler.sccharts.ui.debug.view.DebugDiagramView
import de.cau.cs.kieler.sccharts.ui.debug.actions.SetCheckBreakpointAction

/**
 * Synthesis hook to add double click actions for setting breakpoints to the SCChart.
 * 
 * @author peu
 *
 */
class SetBreakpointActionHook extends SynthesisHook {
    
    @Inject extension KRenderingExtensions
    
    override processState(State state, KNode node) {
        if (usedContext.diagramWorkbenchPart instanceof DebugDiagramView) {
            node.KRendering.addDoubleClickAction(SetBreakpointAction.ID)
        }
    }
    
    override processTransition(Transition transition, KEdge edge) {
        if (usedContext.diagramWorkbenchPart instanceof DebugDiagramView) {
            // regular double click sets transition taken breakpoint
            edge.KRendering.addDoubleClickAction(SetBreakpointAction.ID, false, false, false)
            // ALT + Double click sets check breakpoint
            edge.KRendering.addDoubleClickAction(SetCheckBreakpointAction.ID, false, false, true)
        }
    }
    
}