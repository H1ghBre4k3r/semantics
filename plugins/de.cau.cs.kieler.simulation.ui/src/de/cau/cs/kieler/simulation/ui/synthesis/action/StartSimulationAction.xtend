/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.simulation.ui.synthesis.action

import de.cau.cs.kieler.klighd.IAction
import de.cau.cs.kieler.klighd.IAction.ActionResult
import de.cau.cs.kieler.simulation.SimulationContext
import de.cau.cs.kieler.simulation.ide.synthesis.action.AbstractStartSimulationAction
import de.cau.cs.kieler.simulation.ui.SimulationUI
import de.cau.cs.kieler.simulation.ui.view.pool.DataPoolView

/**
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
class StartSimulationAction extends AbstractStartSimulationAction {

    /**
     * {@inheritDoc}
     */
    override ActionResult execute(ActionContext context) {
        val vc = context.viewContext
        val simCtx = vc.getInputModel

        // get model
        if (simCtx instanceof SimulationContext) {
            // Open data view if visible in this perspective
            DataPoolView.bringToTopIfOpen
            
            // Start Simulation
            SimulationUI.startSimulation(simCtx)
        }
        return IAction.ActionResult.createResult(false);
    }

}
