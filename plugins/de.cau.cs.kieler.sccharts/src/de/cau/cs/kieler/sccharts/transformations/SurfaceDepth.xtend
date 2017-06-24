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
import de.cau.cs.kieler.sccharts.StateType
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import de.cau.cs.kieler.sccharts.extensions.SCChartsOptimization
import de.cau.cs.kieler.sccharts.features.SCChartsFeature

import static extension de.cau.cs.kieler.kitt.tracing.TracingEcoreUtil.*
import static extension de.cau.cs.kieler.kitt.tracing.TransformationTracing.*
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCompareExtensions

/**
 * SCCharts SurfaceDepth Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class SurfaceDepth extends AbstractExpansionTransformation implements Traceable {

    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCChartsTransformation::SURFACEDEPTH_ID
    }

    override getName() {
        return SCChartsTransformation::SURFACEDEPTH_NAME
    }

    override getExpandsFeatureId() {
        return SCChartsFeature::SURFACEDEPTH_ID
    }

    override getProducesFeatureIds() {
        return Sets.newHashSet();
    }

    override getNotHandlesFeatureIds() {
        return Sets.newHashSet(SCChartsFeature::TRIGGEREFFECT_ID)
    }

    // -------------------------------------------------------------------------
    @Inject
    extension KExpressionsCompareExtensions

    @Inject
    extension SCChartsExtension

    @Inject
    extension SCChartsOptimization

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    // -------------------------------------------------------------------------
    // --                S U R F A C E  &   D E P T H                         --
    // -------------------------------------------------------------------------
    // @requires: abort transformation (there must not be any weak or strong aborts outgoing from
    // macro state, hence we just consider simple states here)
    //
    // For every non-hierarchical state S that has outgoing transitions and is of type NORMAL:
    // Create an auxiliary valuedObject isDepth_S that indicates that the state was
    // entered in an earlier tick and add it to the parent state P of the parent region R of S.
    // Modify all triggers of outgoing non-immediate transitions T of S: 1. set them to be
    // immediate and 2. add "isDepth_S &&" to its trigger.
    // Modify state S and make it a conditional.
    // Now walk through all n transitions T_1..n outgoing from S (ordered ascended by their priority):
    // For T_i create a conditional C_i. Connect C_i-1 and C_i with a true triggered immediate transition
    // of priority 2. Set priority of T_i to 1. Note that T_i's original priority is now implicitly encoded
    // by the sequential order of evaluating the conditionals C_1..n.
    // The last conditional C_n connect with a new a normal state D (the explicit depth of S).
    // Connect D with C_1 using a transition that emits isDepth_S.
    // Note that conditionals cannot be marked to be initial. Hence, if a state S is marked initial 
    // then an additional initial state I with a true triggered immediate transition to S will
    // be inserted. \code{S} is then marked not to be initial. This is a necessary pre-processing for
    // the above transformation.
    def State transform(State rootState) {
        val targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        targetRootState.allStates.toList.forEach [ targetState |
            targetState.transformSurfaceDepth(targetRootState);
        ]

        targetRootState.fixAllTextualOrdersByPriorities.optimizeSuperflousConditionalStates.
            optimizeSuperflousImmediateTransitions.fixDeadCode
//            optimizeSuperflousImmediateTransitions.fixDeadCode;
//        targetRootState.fixAllTextualOrdersByPriorities.fixDeadCode;
    }

    def void transformSurfaceDepth(State state, State targetRootState) {
        state.setDefaultTrace
        val numTransition = state.outgoingTransitions.size
        // root or final state
        if (state.rootState || (numTransition == 0 && state.final)) {
            return
        }
        if (numTransition == 0 && state.isHierarchical) {
            // Do not transform  halt-superstates
            // Rationale: It would be necessary to add a termination transition with a delayed pause
            // self-loop (over an additional auxiliary state)
            // or not a loop but a transition to such a state but
            // this would be dead code.
            // Hence, we decided to not transform such states
            if (!state.regionsMayTerminate) {
                // Optimization according to decision in SYNCHRON meeting 22. Aug 2016
                return
            } else {
                val haltState = state.parentRegion.createState(GENERATED_PREFIX + "HaltState")
                val halt = state.createTransitionTo(haltState)
                halt.setTypeTermination
                haltState.createTransitionTo(haltState)
                state.transformSurfaceDepth(targetRootState)
                return
            }
        }
        if (numTransition == 0) { // && !state.isHierarchical
        // halt state --> create explicit pause (self loop)
            state.createTransitionTo(state)
        }
        if (numTransition > 0) {
            // termination
            if (numTransition == 1 && state.outgoingTransitions.get(0).typeTermination) {
                return
            }
            val immediate0 = state.outgoingTransitions.get(0).immediate2
            val noTrigger0 = state.outgoingTransitions.get(0).trigger == null
            val noEffects0 = state.outgoingTransitions.get(0).effects.nullOrEmpty
            if (numTransition == 1 && noTrigger0) {
                // pause
                if (!immediate0 && noEffects0) {
                    return
                }
                // action
                if (immediate0 && !noEffects0) {
                    return
                }
            }
            if (numTransition > 1) {
                val immediate1 = state.outgoingTransitions.get(1).immediate2
                val noTrigger1 = state.outgoingTransitions.get(1).trigger == null
                val noEffects1 = state.outgoingTransitions.get(1).effects.nullOrEmpty
                // conditional
                if (immediate0 && !noTrigger0 && noEffects0 && immediate1 && noTrigger1 && noEffects1) {
                    return
                }
            }
        }

        // /////////////////////////////////////////
        // O L D     C O N D I T I O N      ///
        // /////////////////////////////////////////
        // FIXME: REMOVE THIS AFTER SYNCHRON MEETING 22. AUG 2016
        //
//        if (!(state.outgoingTransitions.size > 0 && state.type == StateType::NORMAL &&
//            !state.outgoingTransitions.get(0).typeTermination &&
//            (state.outgoingTransitions.get(0).trigger != null || !state.outgoingTransitions.get(0).immediate))) {
//            return
//        }
        val parentRegion = state.parentRegion;

        // Duplicate immediate transitions
        val immediateTransitions = state.outgoingTransitions.filter[isImmediate].sortBy[-priority].toList;
        for (transition : immediateTransitions) {
            val transitionCopy = transition.copy
            transitionCopy.setSourceState(transition.sourceState)
            transitionCopy.setTargetState(transition.targetState)
            transitionCopy.setHighestPriority
            transition.setNotImmediate
        }

        // Modify surfaceState (the original state)
        val surfaceState = state
        var depthState = state
        surfaceState.uniqueName

        // For every state create a number of surface nodes
        val orderedTransitionList = state.outgoingTransitions.sortBy[priority];

        var pauseInserted = false

        var State previousState = surfaceState
        var State currentState = surfaceState
        
        var i = 0;

        surfaceState.setDefaultTrace // All following states etc. will be traced to surfaceState if not traced to transition
        for (transition : orderedTransitionList) {

            if (!(transition.isImmediate) && !pauseInserted) {

                // For the first transition that is NOT immediate (a delay transition)
                // and if we have not inserted a pause yet, then do it now
                // Make sure the next transition is delayed 
                pauseInserted = true

                depthState = parentRegion.createState(GENERATED_PREFIX + "Pause").uniqueName
                previousState.createImmediateTransitionTo(depthState).trace(transition)
                // System.out.println("Connect pause 1:" + previousState.id + " -> " + depthState.id);
                val pauseState = parentRegion.createState(GENERATED_PREFIX + "Depth").uniqueName
                depthState.createTransitionTo(pauseState).trace(transition)

                // Imitate next cycle
                previousState = pauseState
                currentState = null

            // System.out.println("Connect pause 2:" + depthState.id + " -> " + pauseState.id);
            }

            if (currentState == null) {
                // Create a new state
                currentState = parentRegion.createState(GENERATED_PREFIX + "S").uniqueName
                // System.out.println("New currentState := " + currentState.id)
                // Move transition to this state
                // System.out.println("Move transition from " + transition.sourceState.id + " to " + currentState.id)
                currentState.outgoingTransitions.add(transition)

                // Connect
                previousState.createImmediateTransitionTo(currentState)
            // System.out.println("Connect:" + previousState.id + " -> " + currentState.id);
            }

            // Ensure the transition is immediate
            transition.setImmediate(true)

            // We can now set the transition priority to 1 (it is reflected implicitly by the sequential order now)
            transition.setPriority(1)

            // Next cycle
            // System.out.println("Set previousState := " + currentState.id)
            previousState = currentState
            currentState = null
            
            
            //i++;
            //if (i == 2) {
            //    return;
            // }
            
        }


        // Connect back depth with surface state
        var T2tmp = previousState.createImmediateTransitionTo(depthState).trace(previousState)
        // System.out.println("Connect BACK:" + previousState.id + " -> " + depthState.id);
        // Afterwards do the DTO transformation
        /* Der Knoten _Pause ist besonders ausgezeichnet. Er hat meistens zwei
         * eingehende Kanten T1 von der surface und T2 von dem feedback aus der depth.
         * 
         * Falls _Pause KEINE zwei eingehenden Kanten hat, so ist er vermutlich
         * als initial Knoten markiert!
         * 
         * Gehe beide Kanten T1 und T2 rückwärts zu jeweiligen Source-Knoten K1 und
         * K2 entlang und verleiche die ausgehenden Transitionen TK1 und TK2 (die
         * nicht T1 oder T2 sind). 
         *  
         * Wenn diese gleich sind wird K1 der neue Pause
         * Knoten und die eingehende Kanten von K2 zeigt nun auf den neuen _Pause.
         * K2, T2 und TK2 werden eliminiert.
         * 
         * Vergleiche nun rekursiv wieder die eingehenden Kanten von neuen _Pause
         bis TK1 und TK2 ungleich sind.*/
        var stateAfterDepth = depthState
        
         // depthState.setId("DEPTHSTATE")
        //depthState.setLabel("DEPTHSTATE")



        var doDTO = true;
        var cnt = 2;

        if (doDTO) {
//        System.out.println("\n\nDTO START ===================\n");
            var done = false
            while (!done) {
                done = true
                if (cnt > 0 && stateAfterDepth.incomingTransitions.size == 2) {

//                    System.out.println("\nstateAfterDepth:" + stateAfterDepth.id);
                    
                    // T1 is the incoming node from the surface
                    var T1tmp = stateAfterDepth.incomingTransitions.get(0)
                    if (T1tmp == T2tmp) {
                        T1tmp = stateAfterDepth.incomingTransitions.get(1)
                    }
                    val T1 = T1tmp
                    val T2 = T2tmp

                    // T2 is the incoming node from the feedback
                    val K1 = T1.sourceState
                    val K2 = T2.sourceState
                    if (!K1.outgoingTransitions.filter(e|e != T1).nullOrEmpty && !K2.outgoingTransitions.filter(
                        e |
                            e != T2
                    ).nullOrEmpty) {
                        val TK1s = K1.outgoingTransitions.filter(e|e != T1)
                        val TK2s = K2.outgoingTransitions.filter(e|e != T2)
                        if (TK1s.size == 1 && TK2s.size == 1) {
                            val TK1 = TK1s.get(0)
                            val TK2 = TK2s.get(0)

//                                if (TK1.label != null) {
//                                    System.out.println(" > K1: " + K1.id + " (" + K1.label + " )");
//                                } else {
//                                    System.out.println(" > K1:" + K1.id + " (no label)");
//                                }
//                                if (TK2.label != null) {
//                                    System.out.println(" > K2: " + K2.id + " (" + K2.label + " )");
//                                } else {
//                                    System.out.println(" > K2:" + K2.id + " (no label)");
//                                }
                            
//                                K1.setLabel(K1.id + "_SURVIVED") 
//                                K1.setId(K1.id + "*")
//                                K1.setLabel(K1.label + "*")
//                                //stateAfterDepth.setLabel("K1_" + cnt)
//                                K2.setLabel(K2.id + "_DELETED")

                            
                            
                            if ((K1.incomingTransitions.size == 1 && K2.incomingTransitions.size == 1) &&
                                (TK1.isImmediate2 && TK2.isImmediate2) &&
                                (TK1.targetState == TK2.targetState) && // TODO: TK1.trigger.equals2 is currently only implemented for the most trivial triggers
                            ((TK1.trigger == TK2.trigger) ||
                                (TK2.trigger != null && TK1.trigger != null && (TK1.trigger.equals2(TK2.trigger))))) {
                                stateAfterDepth = K1
//                                if (stateAfterDepth.label != null) {
//                                    System.out.println("NEXT stateAfterDepth:" + stateAfterDepth.id + " (" + stateAfterDepth.label + " )");
//                                } else {
//                                    System.out.println("NEXT stateAfterDepth:" + stateAfterDepth.id + " (no label)");
//                                }
//                                cnt--;


//                                //System.out.println("new stateAfterDepth:" + stateAfterDepth.id);
                                val t = K2.incomingTransitions.get(0)
                                t.setTargetState(stateAfterDepth)
//                                System.out.println("t.setTargetState(stateAfterDepth):");
//                                System.out.println("   " + t.sourceState.id + "(" +t.sourceState.label+ ") --> " + t.targetState.id + "(" +t.targetState.label+ ")" );
//                                
//                                
                                for (transition : K2.outgoingTransitions) {
                                    stateAfterDepth.trace(transition) // KITT: Redirect tracing before removing
                                    transition.targetState.incomingTransitions.remove(transition)
                                }
                                stateAfterDepth.trace(K2) // KITT: Redirect tracing before removing
                                K2.parentRegion.states.remove(K2)
                                done = false
                                T2tmp = t
//                                System.out.println("NEXT T2tmp:" + T2tmp.sourceState.id + "(" +T2tmp.sourceState.label+ ") --> " + T2tmp.targetState.id + "(" +T2tmp.targetState.label+ ")" );
                            }
                        }
                    }
                }

            // End of DTO transformation
            // This MUST be highest priority so that the control flow restarts and takes other 
            // outgoing transition.
            // There should not be any other outgoing transition.
            }
//        System.out.println("\nDTO END ===================\n\n");
        }
        
        
    }

}
