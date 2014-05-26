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
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension

/**
 * SCCharts TriggerEffect Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class TriggerEffect {

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                  T R I G G E R   E F F E C T                        --
    //-------------------------------------------------------------------------
    // For every transition T that has both, a trigger and an effect do the following:
    //   For every effect:
    //     Create a conditional C and add it to the parent of T's source state S_src.
    //     create a new true triggered immediate effect transition T_eff and move all effects of T to T_eff.
    //     Set the T_eff to have T's target state. Set T to have the target C.
    //     Add T_eff to C's outgoing transitions. 
    def State transform(State rootState) {
        var targetRootState = rootState.fixAllPriorities;

        // Traverse all transitions
        for (targetTransition : targetRootState.getAllContainedTransitions) {
            targetTransition.transformTriggerEffect(targetRootState);
        }
        targetRootState.fixAllTextualOrdersByPriorities;
    }

    def void transformTriggerEffect(Transition transition, State targetRootState) {

        // Only apply this to transition that have both, a trigger (or is a termination) and one or more effects 
        if (((transition.trigger != null || !transition.immediate || transition.typeTermination) &&
            !transition.effects.nullOrEmpty) || transition.effects.size > 1) {
            val targetState = transition.targetState
            val parentRegion = targetState.parentRegion
            val transitionOriginalTarget = transition.targetState
            var Transition lastTransition = transition

            val firstEffect = transition.effects.head
            for (effect : transition.effects.immutableCopy) {

                // Optimization: Prevent transitions without a trigger
                if (transition.immediate && transition.trigger == null && firstEffect == effect) {
                    // skip
                } else {
                    val effectState = parentRegion.createState(GENERATED_PREFIX + "S")
                    effectState.uniqueName
                    val effectTransition = createImmediateTransition.addEffect(effect)
                    effectTransition.setSourceState(effectState)
                    lastTransition.setTargetState(effectState)
                    lastTransition = effectTransition
                }
            }

            lastTransition.setTargetState(transitionOriginalTarget)
        }
    }

}
