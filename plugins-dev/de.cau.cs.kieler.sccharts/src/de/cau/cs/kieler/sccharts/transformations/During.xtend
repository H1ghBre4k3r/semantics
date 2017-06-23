/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.transformations

import com.google.common.collect.Sets
import com.google.inject.Inject
import de.cau.cs.kieler.kico.transformation.AbstractExpansionTransformation
import de.cau.cs.kieler.kitt.tracing.Traceable
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import de.cau.cs.kieler.sccharts.featuregroups.SCChartsFeatureGroup
import de.cau.cs.kieler.sccharts.features.SCChartsFeature

import static extension de.cau.cs.kieler.kitt.tracing.TracingEcoreUtil.*
import static extension de.cau.cs.kieler.kitt.tracing.TransformationTracing.*

/**
 * SCCharts During Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class During extends AbstractExpansionTransformation implements Traceable {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        return SCChartsTransformation::DURING_ID
    }

    override getName() {
        return SCChartsTransformation::DURING_NAME
    }

    override getExpandsFeatureId() {
        return SCChartsFeature::DURING_ID
    }

    override getProducesFeatureIds() {
        return Sets.newHashSet(SCChartsFeature::COMPLEXFINALSTATE_ID, SCChartsFeature::INITIALIZATION_ID,
            SCChartsFeature::CONNECTOR_ID)
    }

    override getNotHandlesFeatureIds() {
        return Sets.newHashSet(SCChartsFeature::HISTORY_ID, SCChartsFeatureGroup::EXPANSION_ID)
    }

    //-------------------------------------------------------------------------

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                     D U R I N G       A C T I O N                   --
    //-------------------------------------------------------------------------
    //
    // PRODUCES: ComplexFinalStates (in the non-simple case)
    //
    // Transforming During Actions.
    def State transform(State rootState) {
        val targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        targetRootState.getAllStates.toList.forEach [ targetState |
            targetState.transformDuring(targetRootState);
        ]
        targetRootState.fixAllTextualOrdersByPriorities;
    }





    // Traverse all super states with outgoing terminations that have actions to transform. 
    // This default implementation will create / use a complex final state
    def void transformDuring(State state, State targetRootRegion) {

         val outgoingTerminations = state.outgoingTransitions.filter(e|e.typeTermination)
         val hasOutgoingTerminations = outgoingTerminations.length > 0

        // If the state has outgoing terminations, we need to finalize the during
        // actions in case we end the states over these transitions
        val complexDuring = ((hasOutgoingTerminations || state.isRootState) && state.regionsMayTerminate)

        // Create the body of the dummy state - containing the during action
        // For every during action: Create a region
        for (duringAction : state.duringActions.immutableCopy) {
            // Tracing
            duringAction.setDefaultTrace;
            
            val immediateDuringAction = duringAction.isImmediate
            val region = state.createControlflowRegion(GENERATED_PREFIX + "During").uniqueName
            val initialState = region.createInitialState(GENERATED_PREFIX + "I")
            var Transition duringTransition = null
            if (immediateDuringAction) {
                val secondState = region.createState(GENERATED_PREFIX + "S");
                duringTransition = initialState.createTransitionTo(secondState)

                // because we have a second state, we need another transition
                secondState.createTransitionTo(initialState)
                if (complexDuring) {
                    secondState.setFinal
                }
            } else {

                // Self loop in the non-immediate case
                duringTransition = initialState.createTransitionTo(initialState)
            }
    
            if (complexDuring) {
                 initialState.setFinal
            }

            duringTransition.setDelay(duringAction.delay);
            duringTransition.setImmediate(immediateDuringAction);
            duringTransition.setTrigger(duringAction.trigger.copy);
            for (action : duringAction.effects) {
                duringTransition.addEffect(action.copy);
            }

            // After transforming during actions, erase them
            state.localActions.remove(duringAction)
        }

    }
    
    // ------------------------------------------------------------------------
    
}
