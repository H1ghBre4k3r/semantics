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
package de.cau.cs.kieler.simulation.ui.toolbar

import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException

/**
 * Button to perform a macro tick in the simulation.
 * 
 * @author aas
 *
 */
class StepSimulationMacroTick extends SimulationToolbarButton {
    
    /**
     * Performs a macro tick in the simulation.
     */
    override execute(ExecutionEvent event) throws ExecutionException {
        super.execute(event)
        if(simulation != null && !justRestarted) {
            simulation.stepMacroTick()
        }
        return null
    }
}