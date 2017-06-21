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
package de.cau.cs.kieler.simulation.core

/**
 * @author aas
 *
 */
enum SimulationEventType {
    VARIABLE_CHANGE,
    STOP,
    PAUSE,
    PLAY,
    PLAYING,
    STEP,
    STEP_BACK,
    SUB_STEP,
    APPEND_SIMULATION,
    INITIALIZED
}