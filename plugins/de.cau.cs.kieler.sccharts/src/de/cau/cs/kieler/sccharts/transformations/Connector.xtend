/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.transformations

import com.google.inject.Inject
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.StateType
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension

/**
 * SCCharts Connector Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Connector {

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                       C O N N E C T O R S                           --
    //-------------------------------------------------------------------------
    // Turn every connector into a simple state and turn all outgoing 
    // transitions into immediate transitions.
    def State transform(State rootState) {
        var targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        for (targetTransition : targetRootState.allStates) {
            targetTransition.transformConnector(targetRootState);
        }

        targetRootState.fixAllTextualOrdersByPriorities;
    }

    def void transformConnector(State state, State targetRootState) {
        if (state.type == StateType::CONNECTOR) {
            state.setTypeNormal
            for (transition : state.outgoingTransitions) {
                transition.setImmediate(true)
            }
        }
    }

}
