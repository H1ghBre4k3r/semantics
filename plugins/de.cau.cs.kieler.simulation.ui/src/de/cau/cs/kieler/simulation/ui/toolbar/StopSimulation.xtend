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

import de.cau.cs.kieler.prom.console.PromConsole
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException

/**
 * Button to stop the simulation.
 * 
 * @author aas
 *
 */
class StopSimulation extends SimulationToolbarButton {
   
    /**
     * Stops the simulation.
     */ 
    override execute(ExecutionEvent event) throws ExecutionException {
        restartSimulationIfStopped = false
        super.execute(event)
        if(simulation != null && !justRestarted) {
            PromConsole.print("Halt")
            simulation.stop()
        }
        return null
    }
}