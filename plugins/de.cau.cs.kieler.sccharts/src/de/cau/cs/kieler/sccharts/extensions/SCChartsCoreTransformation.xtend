/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.extensions

import com.google.common.collect.ImmutableList
import com.google.inject.Inject
import de.cau.cs.kieler.core.kexpressions.Expression
import de.cau.cs.kieler.core.kexpressions.KExpressionsFactory
import de.cau.cs.kieler.core.kexpressions.OperatorExpression
import de.cau.cs.kieler.core.kexpressions.OperatorType
import de.cau.cs.kieler.core.kexpressions.ValueType
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.ktm.extensions.TransformationMapping // KTM
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.sccharts.Emission
import de.cau.cs.kieler.sccharts.HistoryType
import de.cau.cs.kieler.sccharts.Region
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.StateType
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.TransitionType
import java.util.HashMap
import java.util.List

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.sccharts.EntryAction
import java.util.ArrayList
import de.cau.cs.kieler.sccharts.SuspendAction
import de.cau.cs.kieler.sccharts.Assignment
import com.google.common.collect.HashMultimap
import org.eclipse.emf.ecore.EObject

/**
 * SCCharts CoreTransformation Extensions.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class SCChartsCoreTransformation {

    @Inject
    extension KExpressionsExtension

    @Inject
    extension SCChartsExtension

    // KTM
    @Inject
    extension TransformationMapping

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--     K T M  -  M A P P I N G   A C C E S S  D E L E G A T I O N      --
    //-------------------------------------------------------------------------
    def extractMapping() {
        extractMappingData;
    }

    //-------------------------------------------------------------------------
    //--             E X P O S E   L O C A L   S I G N A L S                 --
    //-------------------------------------------------------------------------
    // Transforming Local ValuedObjects and optionally exposing them as
    // output signals.
    def Region transformExposeLocalValuedObject(Region rootRegion) {

        // Clone the complete SCCharts region 
        val targetRootRegion = rootRegion.copy;

        // Traverse all states
        for (targetState : targetRootRegion.getAllContainedStates) {
            targetState.transformExposeLocalValuedObject(targetRootRegion, true);
        }
        targetRootRegion;
    }

    //-------------------------------------------------------------------------
    //--              N O R M A L   T E R M I N A T I O N                    --
    //-------------------------------------------------------------------------
    // @requires: during actions
    // Edit: 30.11.2012: Normal Terminations are considered to be immediate
    // This means that e.g. test10 with a normal termination self loop can
    // be executed as long as the body not immediately.
    // For this reason ANOTHER valuedObject terminated is introduced not per region
    // but per normal termination, indicating a taken normal termination
    // and preventing the same to be taken again within a tick.
    // Like adding a pause in Esterel to
    // loop
    // [ p || pause ]
    // end loop
    // when p may or may not be instantaneous.
    // Edit: 08.01.2013: Normal Terminations must not be taken after a self
    // loop returns to the macro state that is left by the normal termination
    // like in test147.
    // Therefore a transformed transition must explicitly negate all triggers
    // of other outgoing transitions.
    // Transforming Normal Termination. 
    def Region transformTermination(Region rootRegion) {

        // Clone the complete SCCharts region 
        val targetRootRegion = rootRegion.copy.fixAllPriorities;

        // Traverse all states
        for (targetState : targetRootRegion.getAllContainedStates) {
            targetState.transformTermination(targetRootRegion);
        }
        targetRootRegion.fixAllTextualOrdersByPriorities;
    }

    // Traverse all states and transform outgoing normal termination transitions into weak aborts
    def void transformTermination(State state, Region targetRootRegion) {

        // NORMAL TERMINATION : For every state with normal termination transitions transform these into
        // weak abort transitions. Create a trigger for these new transitions that contains a conjunction
        // of a new termValuedObject, one for every contained region.
        // For every region add an immediate during action to all final state emitting this termValuedObject
        // (belonging to the region).
        // Explicitly negate triggers of other outgoing transitions (see test147)
        // This is the special case where we must taken care of a normal termination 
        val terminationTransition = state.getTerminationTransitions;
        if (terminationTransition != null) {
            val otherTransitions = state.outgoingTransitions.filter(e|e.type != TransitionType::TERMINATION);

            terminationTransition.setType(TransitionType::WEAKABORT);
            val triggerExpression = createAndExpression

            // Setup the auxiliary terminated valuedObject indicating that a normal termination
            // has been taken in the same synchronous tick and must not be taken again.
            val rootState = state.rootState
            val terminatedValuedObject = rootState.createSignal(GENERATED_PREFIX + "terminated").setTypePure.uniqueName;

            val terminatedEmission = terminatedValuedObject.emit

            // Add the prevention of re-run of normal termination within the same tick
            triggerExpression.add(not(terminatedValuedObject.reference));

            // Explicitly prevent that a normal termination is taken when another transition
            // has been taken before (e.g., a weak abort self loop like in test 147)
            for (otherTransition : otherTransitions) {
                if (otherTransition.trigger != null) {
                    triggerExpression.add(not(otherTransition.trigger.copy));
                }
            }

            // Prevent the normal termination to be taken again by emitting this helper valuedObject (test10)
            terminationTransition.addEmission(terminatedEmission);

            // Walk thru all regions that must terminate and create one termination valuedObject per
            // region. For the weak abort create a conjunction of these valuedObjects as the trigger.
            for (region : state.regions) {

                // Setup the auxiliary termination valuedObject indicating that a normal termination
                // should be taken.
                val finishedValuedObject = targetRootRegion.rootState.createSignal(GENERATED_PREFIX + "finished").
                    setTypePure.uniqueName

                val finalStates = region.states.filter(e|e.isFinal == true);

                // For all final states add a during action that emits the termination valuedObject
                for (finalState : finalStates) {
                    finalState.createImmediateDuringAction.addEmission(finishedValuedObject.emit);

                    // Set the final state flag to false
                    finalState.setFinal(false);
                }

                triggerExpression.add(finishedValuedObject.reference);
            }

            // A normal termination should immediately be trigger-able! (test 145) 
            terminationTransition.setImmediate(true);

            // if there is just one valuedObject, we do not need an AND!
            if (triggerExpression != null) {
                terminationTransition.setTrigger(triggerExpression.fixForOperatorExpressionLists.trim);
            }
        } // end if normal termination present

        }

        //-------------------------------------------------------------------------
        //--                             S I G N A L S                           --
        //-------------------------------------------------------------------------
        // TODO: for inputs no during action!
        // TODO: relative writes!!
        private static val String variableValueExtension = GENERATED_PREFIX + "val";

        // @requires: during actions
        // For all states do the following:
        // If the state has a specification, then convert all signals
        // (a) simple signal S to boolean variable S (variablePresent)
        // (b) valued signal S to two boolean variables S and S_val (variableValue)
        //input signal S; --> input boolean S;
        //input signal S:bool; --> input boolean S; input boolean S_val;
        //input signal S:integer; --> input boolean S; input integer S_val;
        // Transforming a signal to a variable. 
        def Region transformSignal(Region rootRegion) {

            // Clone the complete SCCharts region 
            val targetRootRegion = rootRegion.copy.fixAllPriorities;

            // Traverse all states
            for (targetState : targetRootRegion.getAllContainedStates) {
                targetState.transformSignal(targetRootRegion);
            }
            targetRootRegion.fixAllTextualOrdersByPriorities;
        }

        // Traverse all states and transform outgoing normal termination transitions into weak aborts
        def void transformSignal(State state, Region targetRootRegion) {
            val allSignals = state.signals

            // Go thru all signals
            for (ValuedObject signal : allSignals) {
                val isValuedSignal = !signal.pureSignal

                val presentVariable = signal

                // If this is a valued signal we need a second signal for the value
                if (isValuedSignal) {
                    val valueVariable = state.createVariable(signal.name + variableValueExtension)

                    // Copy type and input/output attributes from the original signal
                    valueVariable.applyAttributes(signal)
                    val allActions = state.eAllContents.filter(typeof(Action)).toList
                    for (Action action : allActions) {

                        // Wherever an emission is, create a new assignment right behind
                        val allSignalEmissions = action.getAllContainedEmissions.filter[e|e.valuedObject == signal].
                            toList
                        for (Emission signalEmission : allSignalEmissions.immutableCopy) {
                            if (signalEmission.newValue != null) {

                                // Assign the emitted valued
                                val variableAssignment = valueVariable.assign(signalEmission.newValue)

                                // Put it in right order
                                val index = action.effects.indexOf(signalEmission);
                                action.effects.add(index, variableAssignment);
                            }
                        }

                        // Wherever an val test is, put the valueVariable there instead
                        val allSignalValTests = action.eAllContents.filter(typeof(OperatorExpression)).filter(
                            e|
                                e.operator == OperatorType::VAL &&
                                    e.subExpressions.get(0) instanceof ValuedObjectReference &&
                                    (e.subExpressions.get(0) as ValuedObjectReference).valuedObject == signal).toList
                        for (OperatorExpression signalTest : allSignalValTests.immutableCopy) {

                            // Put a trim-able Operator here
                            signalTest.setOperator(OperatorType::AND)

                            // Replace in valuedObjectReference
                            (signalTest.subExpressions.get(0) as ValuedObjectReference).setValuedObject(valueVariable)

                            //signalTest.add(TRUE)
                            }
                            if (action.trigger != null) {
                                action.setTrigger(action.trigger.trim)
                            }
                        }
                    }

                    // Change signal to variable
                    presentVariable.setIsNotSignal
                    presentVariable.setTypeBool

                    // Reset initial value and combine operator because we want to reset
                    // the signal manually in every
                    presentVariable.setInitialValue(null)
                    presentVariable.setCombineOperator(null)

                    // Modify signal emission & presence test
                    val allActions = state.eAllContents.filter(typeof(Action)).toList
                    for (Action action : allActions) {

                        // Wherever an emission is, modify it to be an assignment of the presentVariable
                        val allSignalEmissions = action.getAllContainedEmissions.filter[e|e.valuedObject == signal].
                            toList
                        for (Emission signalEmission : allSignalEmissions.immutableCopy) {

                            // Assign the emitted valued
                            val variableAssignment = presentVariable.assignRelative(TRUE)

                            // Remove the signal emission value (because it will be the presentValue emission)
                            // Put it in right order
                            val index = action.effects.indexOf(signalEmission)
                            action.effects.add(index, variableAssignment)

                            // Remove emission
                            action.effects.remove(signalEmission)
                        }

                        // Wherever a present test is, put an Operator Expression (presentVariable == TRUE) there instead
                        val allSignalTests = action.eAllContents.filter(typeof(ValuedObjectReference)).filter(
                            e|e.valuedObject == signal).toList
                        for (ValuedObjectReference signalTest : allSignalTests.immutableCopy) {
                            val presentVariableTest = signalTest.valuedObject.reference //.isEqual(TRUE);
                            action.replace(signalTest, presentVariableTest)
                        }
                    }

                    // Add a during reset action for the presentVariable if it is an output or local variable.
                    // Do not do this for only-input-variables.
                    if (!presentVariable.isInput) {
                        val duringAction = state.createDuringAction
                        duringAction.createAssignment(presentVariable, FALSE)
                        duringAction.setImmediate(true)
                    }
                }
            }

            //-------------------------------------------------------------------------
            //--     O P T I M I Z E : Immediate Transitions with no Trigger/Effect  --
            //-------------------------------------------------------------------------
            def Region optimizeSuperflousImmediateTransitions(Region rootRegion) {
                var targetStates = rootRegion.allContainedStates
                for (targetState : targetStates) {
                    targetState.optimizeSuperflousImmediateTransitions(rootRegion);
                }
        rootRegion;
            }

            def void optimizeSuperflousImmediateTransitions(State state, Region targetRootRegion) {
                if (state.outgoingTransitions.size == 1 && !state.hierarchical) {
                    val transition = state.outgoingTransitions.get(0)
                    val targetState = transition.targetState
                    if (transition.immediate2) {
                        if (transition.trigger == null && transition.effects.nullOrEmpty) {
                            targetState.incomingTransitions.remove(transition)
                            targetState.mapParents(transition.mappedParents); // KTM - redirect mapping information
                            transition.unmapAll; // KTM - remove mapping information
                            state.outgoingTransitions.remove(transition)
                            targetState.setInitial(state.initial || targetState.initial)
                            targetState.setFinal(state.final || targetState.final)
                            for (incomingTransition : state.incomingTransitions.immutableCopy) {
                                incomingTransition.setTargetState(targetState)
                            }
                            targetState.setId(state.id)
                            targetState.setLabel(state.label)
                            targetState.parentRegion.states.remove(state)
                            targetState.mapParents(state.mappedParents); // KTM - redirect mapping information
                            state.unmapAll; // KTM - remove mapping information
                        }
                    }
                }

            }

            //-------------------------------------------------------------------------
            //--     O P T I M I Z E :  Conditional  States  --
            //-------------------------------------------------------------------------
            // Optimize states with two outgoing transitions
            def Region optimizeSuperflousConditionalStates(Region rootRegion) {
                var targetStates = rootRegion.allContainedStates
                for (targetState : targetStates) {
                    targetState.optimizeSuperflousConditionalStates(rootRegion);
                }
        rootRegion;
            }

            def void optimizeSuperflousConditionalStates(State state, Region targetRootRegion) {
                if (state.outgoingTransitions.size == 2 && !state.hierarchical) {
                    val transition1 = state.outgoingTransitions.get(0)
                    val transition2 = state.outgoingTransitions.get(1)
                    val targetState2 = transition1.targetState
                    if ((transition1.immediate2) && (transition1.trigger == null)) {
                        targetState2.incomingTransitions.remove(transition2)
                        state.outgoingTransitions.remove(transition2)

                        //targetState2.setInitial(state.initial || targetState2.initial)
                        //targetState2.setFinal(state.final || targetState2.final)
                        //targetState.setId(state.id)
                        //targetState.setLabel(state.label)
                        //targetState.parentRegion.states.remove(state)
                        }
                    }

                }

                //-------------------------------------------------------------------------
                //--                S U R F A C E  &   D E P T H                         --
                //-------------------------------------------------------------------------
                //@requires: abort transformation (there must not be any weak or strong aborts outgoing from
                //                                 macro state, hence we just consider simple states here)
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
                def Region transformSurfaceDepth(Region rootRegion) {
                    clearMapping; // KTM - clear previous mapping information to assure a single consistent mapping

                    // Clone the complete SCCharts region 
                    var targetRootRegion = rootRegion.mappedCopy.fixAllPriorities; // KTM - mapping information (changed copy to mapped copy)

                    var targetStates = targetRootRegion.allContainedStates

                    // Traverse all states
                    for (targetState : targetStates) {
                        targetState.transformSurfaceDepth(targetRootRegion);
                    }
                    
                    val completeness0 = checkMappingCompleteness(rootRegion, targetRootRegion); // KTM - debug
                    targetRootRegion.fixAllTextualOrdersByPriorities.optimizeSuperflousConditionalStates.
                        optimizeSuperflousImmediateTransitions.fixDeadCode;
                    val completeness1 = checkMappingCompleteness(rootRegion, targetRootRegion); // KTM - debug
                    targetRootRegion;
                }

                def void transformSurfaceDepth(State state, Region targetRootRegion) {
                    if (state.outgoingTransitions.size > 0 && state.type == StateType::NORMAL &&
                        !state.outgoingTransitions.get(0).typeTermination &&
                        (state.outgoingTransitions.get(0).trigger != null || !state.outgoingTransitions.get(0).immediate)) {
                        val parentRegion = state.parentRegion;

                        // Duplicate immediate transitions
                        val immediateTransitions = state.outgoingTransitions.filter[isImmediate].sortBy[-priority].
                            toList;
                        for (transition : immediateTransitions) {
                            //-- KTM - manual copy to copy mappings
                            val HashMultimap<EObject, EObject> copyMapping = HashMultimap::create;
                            // KTM, changed from .copy to .mappedCopy(copyMapping)
                            val transitionCopy = transition.mappedCopy(copyMapping);
                            copyMapping.entries.forEach[
                                it.value.mapParents(it.key.mappedParents); // KTM - mapping information
                            ]
                            transitionCopy.setSourceState(transition.sourceState)
                            transitionCopy.setTargetState(transition.targetState)
                            transitionCopy.setHighestPriority
                            transition.setNotImmediate
                        }

                        // Modify surfaceState (the original state)
                        val surfaceState = state
                        var depthState = state
                        surfaceState.setId(GENERATED_PREFIX + "Surface")
                        surfaceState.setLabel2(GENERATED_PREFIX + "Surface")
                        surfaceState.uniqueName

                        // For every state create a number of surface nodes
                        val orderedTransitionList = state.outgoingTransitions.sortBy[priority]

                        var tickBoundaryInserted = false
                        var nextTransitionNotImmediate = false

                        var State previousState = null
                        var State currentState = surfaceState
                        for (transition : orderedTransitionList) {

                            nextTransitionNotImmediate = false
                            if (!(transition.isImmediate) && !tickBoundaryInserted) {
                                tickBoundaryInserted = true
                                nextTransitionNotImmediate = true

                                // Add an additional last state that will become depth State
                                if (previousState == null) { /// IS THIS RIGHT!?!?

                                    //val dummyState = parentRegion.createState("_Dummy").uniqueName
                                    previousState = surfaceState
                                    currentState = null
                                }
                                depthState = parentRegion.createState(GENERATED_PREFIX + "Pause").uniqueName
                                depthState.mapParents(surfaceState.mappedParents); // KTM - mapping information
                                val connect = previousState.createImmediateTransitionTo(depthState)
                                connect.mapParents(transition.mappedParents); // KTM - mapping information
                                previousState = depthState
                                connect.setPriority(2)
                            }

                            if (currentState == null) {

                                // Create a new state
                                currentState = parentRegion.createState(GENERATED_PREFIX + "S").uniqueName
                                currentState.mapParents(surfaceState.mappedParents); // KTM - mapping information

                                // Connect
                                val connect = previousState.createTransitionTo(currentState)
                                connect.setImmediate(!nextTransitionNotImmediate)
                                connect.setPriority(2)

                                // Move transition to this state
                                currentState.outgoingTransitions.add(transition)
                            }

                            // Ensure the transition is immediate
                            transition.setImmediate(true)

                            // We can now set the transition priority to 1 (it is reflected implicitly by the sequential order now)
                            transition.setPriority(1)

                            // Next cycle
                            previousState = currentState
                            currentState = null
                        }

                        // Connect back depth with surface state
                        var T2tmp = previousState.createImmediateTransitionTo(depthState)
                        T2tmp.mapParents(previousState.mappedParents); // KTM - mapping information

                        // Afterwards do the DTO transformation
                        /* Der Knoten S_Depth ist ja besonders ausgezeichnet. Er hat immer zwei
            eingehende Kanten T1 von der surface und T2 von dem feedback aus der depth.
            Gehe beide Kanten T1 und T2 rückwärts zu jeweiligen Source-Knoten K1 und
            K2 entlang und verleiche die ausgehenden Transitionen TK1 und TK2 (die
            nicht T1 oder T2 sind). Wenn diese gleich sind wird K1 der neue S_Depth
            Knoten und die eingehende Kanten von K2 zeigt nun auf den neuen S_Depth.
            K2, T2 und TK2 werden eliminiert.
            Vergleiche nun rekursiv wieder die eingehenden Kanten von neuen S_Depth
            bis TK1 und TK2 ungleich sind.*/
                        var stateAfterDepth = depthState
                        var done = false
                        while (!done) {
                            done = true
                            if (stateAfterDepth.incomingTransitions.size == 2) {

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
                                if (!K1.outgoingTransitions.filter(e|e != T1).nullOrEmpty &&
                                    !K2.outgoingTransitions.filter(e|e != T1).nullOrEmpty) {
                                    val TK1s = K2.outgoingTransitions.filter(e|e != T2)
                                    val TK2s = K2.outgoingTransitions.filter(e|e != T2)
                                    if (TK1s.size > 0 && TK2s.size > 0) {
                                        val TK1 = TK1s.get(0)
                                        val TK2 = TK2s.get(0)
                                        if ((TK1.targetState == TK2.targetState) &&
                                            ((TK1.trigger == TK2.trigger) || (TK1.trigger.equals2(TK2.trigger)))) {
                                            stateAfterDepth = K1
                                            val t = K2.incomingTransitions.get(0)
                                            t.setTargetState(stateAfterDepth)
                                            for (transition : K2.outgoingTransitions) {
                                                transition.targetState.incomingTransitions.remove(transition)
                                                stateAfterDepth.mapParents(transition.mappedParents); // KTM - redirect mapping information
                                                transition.unmapAll; // KTM - remove mapping information
                                            }
                                            K2.parentRegion.states.remove(K2)
                                            stateAfterDepth.mapParents(K2.mappedParents); // KTM - redirect mapping information
                                            K2.unmapAll; // KTM - remove mapping information
                                            done = false
                                            T2tmp = t
                                        }
                                    }
                                }
                            }
                        }

                        // End of DTO transformation
                        // This MUST be highest priority so that the control flow restarts and takes other 
                        // outgoing transition.
                        // There should not be any other outgoing transition.
                        }
                    }

                    //-------------------------------------------------------------------------
                    //--                 S P L I T   T R A N S I T I O N                     --
                    //-------------------------------------------------------------------------
                    // For every transition T that has both, a trigger and an effect do the following:
                    //   For every effect:
                    //     Create a conditional C and add it to the parent of T's source state S_src.
                    //     create a new true triggered immediate effect transition T_eff and move all effects of T to T_eff.
                    //     Set the T_eff to have T's target state. Set T to have the target C.
                    //     Add T_eff to C's outgoing transitions. 
                    def Region transformTriggerEffect(Region rootRegion) {
                        clearMapping; // KTM - clear previous mapping information to assure a single consistent mapping

                        // Clone the complete SCCharts region 
                        var targetRootRegion = rootRegion.mappedCopy.fixAllPriorities; //KTM - mapping information (changed copy to mappedCopy)

                        //var targetRootRegion = rootRegion.copy.fixAllPriorities;
                        // Traverse all transitions
                        for (targetTransition : targetRootRegion.getAllContainedTransitions) {
                            targetTransition.transformTriggerEffect(targetRootRegion);
                        }
                        val completeness = checkMappingCompleteness(rootRegion, targetRootRegion); // KTM -debug
                        targetRootRegion.fixAllTextualOrdersByPriorities;
                    }

                    def void transformTriggerEffect(Transition transition, Region targetRootRegion) {

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
                                    effectState.mapParents(transition.mappedParents); // KTM - Mapping information
                                    effectState.uniqueName
                                    val effectTransition = createImmediateTransition.addEffect(effect)
                                    effectTransition.mapParents(transition.mappedParents); // KTM - Mapping information
                                    effectTransition.setSourceState(effectState)
                                    lastTransition.setTargetState(effectState)
                                    lastTransition = effectTransition
                                }
                            }

                            lastTransition.setTargetState(transitionOriginalTarget)
                        }
                    }

                    //-------------------------------------------------------------------------
                    //--                       C O N N E C T O R S                           --
                    //-------------------------------------------------------------------------
                    // Turn every connector into a simple state and turn all outgoing 
                    // transitions into immediate transitions.
                    def Region transformConnector(Region rootRegion) {

                        // Clone the complete SCCharts region 
                        var targetRootRegion = rootRegion.copy.fixAllPriorities;

                        // Traverse all states
                        for (targetTransition : targetRootRegion.allContainedStates) {
                            targetTransition.transformConnector(targetRootRegion);
                        }

                        targetRootRegion.fixAllTextualOrdersByPriorities;
                    }

                    def void transformConnector(State state, Region targetRootRegion) {
                        if (state.type == StateType::CONNECTOR) {
                            state.setTypeNormal
                            for (transition : state.outgoingTransitions) {
                                transition.setImmediate(true)
                            }
                        }
                    }

                    //-------------------------------------------------------------------------
                    //--             D E F E R R E D     T R A N S I T I O N                 --
                    //-------------------------------------------------------------------------
                    // Add a new deferVariable to the outer state, set it initially to false and
                    // add a during action in the state to reset it to FALSE.
                    // For all incoming deferred transitions, reset the defer flag and add an assignment
                    // setting the deferVariable to true when entering the state. 
                    // Prevent any immediate internal behavior of the state and any immediate outgoing
                    // transition in case deferVariable is set to TRUE, i.e., the state was entered
                    // by a deferred transition.
                    def Region transformDeferred(Region rootRegion) {

                        // Clone the complete SCCharts region 
                        var targetRootRegion = rootRegion.copy.fixAllPriorities;

                        // Traverse all states
                        for (targetTransition : targetRootRegion.allContainedStates) {
                            targetTransition.transformDeferred;
                        }
                        targetRootRegion.fixAllTextualOrdersByPriorities;
                    }

                    def void transformDeferred(State state) {
                        val incomingDeferredTransitions = state.incomingTransitions.filter[deferred];

                        // If there are any such transitions 
                        if (!incomingDeferredTransitions.nullOrEmpty) {

                            // Add a new deferVariable to the outer state, set it initially to FALSE and
                            // add a during action in the state to reset it to FALSE
                            val deferVariable = state.parentRegion.parentState.createVariable(
                                GENERATED_PREFIX + "deferred").setTypeBool.uniqueName
                            deferVariable.setInitialValue(FALSE)
                            val resetDeferSignalAction = state.createDuringAction
                            resetDeferSignalAction.addEffect(deferVariable.assign(FALSE))

                            // For all incoming deferred transitions, reset the defer flag and add an assignment
                            // setting the deferVariable to true when entering the state 
                            for (transition : incomingDeferredTransitions) {
                                transition.setDeferred(false)
                                transition.addEffect(deferVariable.assign(TRUE))
                            }

                            // Prevent any immediate internal behavior of the state and any immediate outgoing
                            // transition in case deferVariable is set to TRUE, i.e., the state was entered
                            // by a deferred transition
                            val allInternalImmediateActions = state.allContainedActions.filter(
                                e|e.immediate || e instanceof EntryAction).toList
                            for (action : allInternalImmediateActions) {
                                val deferTest = not(deferVariable.reference)
                                if (action.trigger != null) {
                                    action.setTrigger(deferTest.and(action.trigger))
                                } else {
                                    action.setTrigger(deferTest)
                                }
                            }
                        }
                    }

                    //       
                    // -- FORMER IMPLEMENTATION --
                    // 
                    // For all deferred transitions T from S1 to S2, create a new State _S
                    // create a new transition _T from _S to S2 and change T's target to _S.
                    // Remove the deferred flag from T.
                    //
                    //     def void transformDeferred(Transition transition) {
                    //         if (transition.deferred) {
                    //             // Create a new state _S
                    //             val _S = transition.targetState.parentRegion.createState(transition.id("_S"))
                    //             // Create a new transition _T
                    //             _S.createTransitionTo(transition.targetState)
                    //             // Re-target transition T
                    //             transition.setTargetState(_S)
                    //             // Remove deferred flag
                    //             transition.setDeferred(false)
                    //         }
                    //     }
                    //-------------------------------------------------------------------------
                    //--          A B O R T S  - 1 -  T R A N S F O R M A T I O N        --
                    //-------------------------------------------------------------------------
                    // Transforming Aborts.
                    def Region transformAborts1(Region rootRegion) {

                        // Clone the complete SCCharts region 
                        val targetRootRegion = rootRegion.copy.fixAllPriorities;

                        // Traverse all states
                        for (targetState : targetRootRegion.getAllContainedStates) {
                            targetState.transformAborts1(targetRootRegion);
                        }
                        targetRootRegion.fixAllTextualOrdersByPriorities;
                    }

                    // For all transitions of a state compute the maximal priority
                    def int maxPriority(State state) {
                        var priority = 0;
                        for (transition : state.outgoingTransitions) {
                            val newPriority = transition.priority;
                            if (newPriority > priority) {
                                priority = newPriority;
                            }
                        }
        priority;
                    }

                    // Traverse all states 
                    def void transformAborts1(State state, Region targetRootRegion) {

                        val stateHasUntransformedTransitions = (!(state.outgoingTransitions.size == 0) || ((state.
                            outgoingTransitions.size == 1) &&
                            state.outgoingTransitions.filter[typeTermination].filter[trigger == null].size == 1))

                        val stateHasUntransformedAborts = (!(state.outgoingTransitions.filter[!typeTermination].
                            nullOrEmpty))

                        //        if (state.hierarchical && stateHasUntransformedAborts && state.label != "WaitAandB") {
                        if (state.hierarchical && stateHasUntransformedTransitions) { // && state.label != "WaitAB") {
                            val transitionTriggerVariableMapping = new HashMap<Transition, ValuedObject>

                            // Remember all outgoing transitions and regions
                            val outgoingTransitions = state.outgoingTransitions.immutableCopy
                            val regions = state.regions.immutableCopy

                            if (stateHasUntransformedAborts) {
                                val ctrlRegion = state.createRegion(GENERATED_PREFIX + "Ctrl").uniqueName
                                val runState = ctrlRegion.createInitialState(GENERATED_PREFIX + "Run").uniqueName
                                val doneState = ctrlRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueName

                                // Build up weak and strong abort triggers
                                var Expression strongAbortTrigger = null;
                                var Expression weakAbortTrigger = null;
                                for (transition : outgoingTransitions) {

                                    // Create a new _transitionTrigger valuedObject
                                    val transitionTriggerVariable = state.parentRegion.parentState.
                                        createVariable(GENERATED_PREFIX + "trig").setTypeBool.uniqueName
                                    transitionTriggerVariable.setInitialValue(FALSE)
                                    transitionTriggerVariableMapping.put(transition, transitionTriggerVariable)
                                    if (transition.typeStrongAbort) {
                                        strongAbortTrigger = strongAbortTrigger.or2(transitionTriggerVariable.reference)
                                    } else if (transition.typeWeakAbort) {
                                        weakAbortTrigger = weakAbortTrigger.or2(transitionTriggerVariable.reference)
                                    }
                                }

                                var Expression terminationTrigger;

                                // Decides whether a _TERM signal and the necessary _Run, _Done state is needed
                                // OPTIMIZATION
                                val terminationHandlingNeeded = !outgoingTransitions.filter[typeTermination].nullOrEmpty

                                // For each region encapsulate it into a _Main state and add a _Term variable
                                // also to the terminationTrigger
                                for (region : regions) {
                                    if (terminationHandlingNeeded) {
                                        val mainRegion = state.createRegion(GENERATED_PREFIX + "Main").uniqueName
                                        val mainState = mainRegion.createInitialState(GENERATED_PREFIX + "Main").
                                            uniqueName
                                        mainState.regions.add(region)
                                        val termState = mainRegion.createFinalState(GENERATED_PREFIX + "Term").
                                            uniqueName
                                        val termVariable = state.createVariable(GENERATED_PREFIX + "term").setTypeBool.
                                            uniqueName
                                        mainState.createTransitionTo(termState).addEffect(termVariable.assign(TRUE)).
                                            setTypeTermination
                                        if (terminationTrigger != null) {
                                            terminationTrigger = terminationTrigger.and(termVariable.reference)
                                        } else {
                                            terminationTrigger = termVariable.reference
                                        }
                                        state.createEntryAction.addEffect(termVariable.assign(FALSE))
                                    }

                                    // Inside every region create a _Aborted
                                    val abortedState = region.createFinalState(GENERATED_PREFIX + "Aborted").uniqueName
                                    for (innerState : region.states.filter[!final]) {
                                        if (innerState != abortedState) {
                                            if (strongAbortTrigger != null) {
                                                val strongAbort = innerState.createTransitionTo(abortedState, 0)
                                                if (innerState.hierarchical) {

                                                    // HERE DIFFERENCE TO ABORT2()
                                                    // We mark the transition as strong abort and handle
                                                    // it later when transforming this hierarchical state.
                                                    // This leads to more variables but avoids more transitions.
                                                    strongAbort.setTypeStrongAbort

                                                    // END OF DIFFERENCE
                                                    }
                                                    strongAbort.setPriority(0)
                                                    strongAbort.setTrigger(strongAbortTrigger.copy)
                                                }
                                                if (weakAbortTrigger != null) {
                                                    val weakAbort = innerState.createTransitionTo(abortedState)
                                                    weakAbort.setTrigger(weakAbortTrigger.copy)
                                                }
                                            }
                                        }
                                    }

                                    for (transition : outgoingTransitions) {

                                        // Get the _transitionTrigger that was created earlier
                                        val transitionTriggerVariable = transitionTriggerVariableMapping.get(transition)

                                        // Create a ctrlTransition in the ctrlRegion
                                        val ctrlTransition = runState.createTransitionTo(doneState)
                                        if (transition.typeTermination) {
                                            if (transition.trigger != null) {
                                                ctrlTransition.setTrigger(
                                                    terminationTrigger.copy.and(transition.trigger))
                                            } else {
                                                ctrlTransition.setTrigger(terminationTrigger.copy)
                                            }
                                        } else {
                                            ctrlTransition.setTrigger(transition.trigger)
                                        }

                                        // ATTENTION: Test for ctrlTransition.immediate2 because transition's trigger has already been moved to ctrlTransition!!!
                                        ctrlTransition.setImmediate(transition.immediate)
                                        if (ctrlTransition.immediate2) {
                                            ctrlTransition.setImmediate(true)
                                        }
                                        ctrlTransition.addEffect(transitionTriggerVariable.assign(TRUE))
                                    }

                                }

                                // Create a single outgoing normal termination to a new connector state
                                val outgoingConnectorState = state.parentRegion.createState(GENERATED_PREFIX + "C").
                                    uniqueName.setTypeConnector
                                state.createTransitionTo(outgoingConnectorState).setTypeTermination

                                for (transition : outgoingTransitions) {

                                    // Modify the outgoing transition
                                    transition.setSourceState(outgoingConnectorState)

                                    // Get the _transitionTrigger that was created earlier
                                    val transitionTriggerVariable = transitionTriggerVariableMapping.get(transition)
                                    if (transitionTriggerVariable != null) {
                                        transition.setTrigger2(transitionTriggerVariable.reference)
                                    } else {

                                        // Fall back to this case when we did not create a trigger variable
                                        // because there where NO strong or weak aborts but one or more triggered
                                        // normal termination transitions.
                                        transition.setTrigger2(transition.trigger)
                                    }

                                    transition.setTypeWeakAbort
                                }

                                // OPTIMIZATION
                                // if the connector has to just one outgoing transition, erase it
                                if (outgoingConnectorState.outgoingTransitions.size == 1) {
                                    val transition = outgoingConnectorState.outgoingTransitions.get(0)
                                    transition.setImmediate(true)
                                    transition.setTypeTermination
                                    transition.setTrigger(null)
                                    val transitionToDelete = outgoingConnectorState.incomingTransitions.get(0)
                                    state.outgoingTransitions.remove(transitionToDelete)
                                    state.outgoingTransitions.add(transition)
                                    state.parentRegion.states.remove(outgoingConnectorState)
                                }

                            }

                        }

                        //-------------------------------------------------------------------------
                        //--          A B O R T S  - 2 -  T R A N S F O R M A T I O N        --
                        //-------------------------------------------------------------------------
                        // Transforming Aborts.
                        def Region transformAborts2(Region rootRegion) {
                            val targetRootRegion = rootRegion.copy.fixAllPriorities;

                            // Traverse all states
                            for (targetState : targetRootRegion.getAllContainedStates) {
                                targetState.transformAborts2(targetRootRegion);
                            }
                            targetRootRegion.fixAllTextualOrdersByPriorities;
                        }

                        // Traverse all states 
                        //    def void preTransformAborts2(State state, Region targetRootRegion) {
                        //        // For all normal hierarchical states, add a single connector
                        //        // add
                        //    }
                        def void transformAborts2(State state, Region targetRootRegion) {

                            val stateHasUntransformedTransitions = (!(state.outgoingTransitions.size == 0) || ((state.
                                outgoingTransitions.size == 1) &&
                                state.outgoingTransitions.filter[typeTermination].filter[trigger == null].size == 1))

                            val stateHasUntransformedAborts = (!(state.outgoingTransitions.filter[!typeTermination].
                                nullOrEmpty))

                            //        if (state.hierarchical && stateHasUntransformedAborts && state.label != "WaitAandB") {
                            if (state.hierarchical && stateHasUntransformedTransitions) { // && state.label != "WaitAB") {
                                val transitionTriggerVariableMapping = new HashMap<Transition, ValuedObject>

                                // Remember all outgoing transitions and regions
                                val outgoingTransitions = state.outgoingTransitions.immutableCopy
                                val regions = state.regions.immutableCopy

                                if (stateHasUntransformedAborts) {
                                    val ctrlRegion = state.createRegion(GENERATED_PREFIX + "Ctrl").uniqueName
                                    val runState = ctrlRegion.createInitialState(GENERATED_PREFIX + "Run").uniqueName
                                    val doneState = ctrlRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueName

                                    // Build up weak and strong abort triggers
                                    var Expression strongAbortTrigger = null;
                                    var Expression weakAbortTrigger = null;
                                    for (transition : outgoingTransitions) {

                                        // Create a new _transitionTrigger valuedObject
                                        val transitionTriggerVariable = state.parentRegion.parentState.
                                            createVariable(GENERATED_PREFIX + "trig").setTypeBool.uniqueName
                                        transitionTriggerVariable.setInitialValue(FALSE)
                                        transitionTriggerVariableMapping.put(transition, transitionTriggerVariable)
                                        if (transition.typeStrongAbort) {
                                            strongAbortTrigger = strongAbortTrigger.or2(
                                                transitionTriggerVariable.reference)
                                        } else if (transition.typeWeakAbort) {
                                            weakAbortTrigger = weakAbortTrigger.or2(transitionTriggerVariable.reference)
                                        }
                                    }

                                    var Expression terminationTrigger;

                                    // Decides whether a _TERM signal and the necessary _Run, _Done state is needed
                                    // OPTIMIZATION
                                    val terminationHandlingNeeded = !outgoingTransitions.filter[typeTermination].
                                        nullOrEmpty // For each region encapsulate it into a _Main state and add a _Term variable
                                    // also to the terminationTrigger
                                    for (region : regions) {
                                        if (terminationHandlingNeeded) {
                                            val mainRegion = state.createRegion(GENERATED_PREFIX + "Main").uniqueName
                                            val mainState = mainRegion.createInitialState(GENERATED_PREFIX + "Main").
                                                uniqueName
                                            mainState.regions.add(region)
                                            val termState = mainRegion.createFinalState(GENERATED_PREFIX + "Term").
                                                uniqueName
                                            val termVariable = state.createVariable(GENERATED_PREFIX + "term").
                                                setTypeBool.uniqueName
                                            mainState.createTransitionTo(termState).addEffect(termVariable.assign(TRUE)).
                                                setTypeTermination
                                            if (terminationTrigger != null) {
                                                terminationTrigger = terminationTrigger.and(termVariable.reference)
                                            } else {
                                                terminationTrigger = termVariable.reference
                                            }
                                            state.createEntryAction.addEffect(termVariable.assign(FALSE))
                                        }

                                        // Inside every region create a _Aborted
                                        val abortedState = region.createFinalState(GENERATED_PREFIX + "Aborted").
                                            uniqueName
                                        for (innerState : region.states.filter[!final]) {
                                            if (innerState != abortedState) {
                                                if (strongAbortTrigger != null) {
                                                    val strongAbort = innerState.createTransitionTo(abortedState, 0)
                                                    if (innerState.hierarchical) {

                                                        // HERE DIFFERENCE TO ABORT1()
                                                        // We dig deep in the hierarchy and connect all states with immediate transitions
                                                        // to a final state.
                                                        // This leads to more transitions but avoids more variables.
                                                        strongAbort.setTypeTermination
                                                        val allInnerSimpleStates = innerState.allContainedStates.filter[
                                                            !hierarchical].filter[!final]
                                                        for (innerSimpleState : allInnerSimpleStates) {
                                                            val innerFinalStates = innerSimpleState.parentRegion.states.
                                                                filter[final]
                                                            var State innerAbortedState;
                                                            if (innerFinalStates.nullOrEmpty) {
                                                                innerAbortedState = innerSimpleState.parentRegion.
                                                                    createFinalState(GENERATED_PREFIX + "Aborted").
                                                                    uniqueName
                                                            } else {
                                                                innerAbortedState = innerFinalStates.get(0)
                                                            }
                                                            val innerStrongAbort = innerSimpleState.
                                                                createTransitionTo(innerAbortedState, 0)
                                                            innerStrongAbort.setPriority(0)
                                                            innerStrongAbort.setTrigger(strongAbortTrigger.copy)
                                                        }

                                                        // END OF DIFFERENCE
                                                        }
                                                        strongAbort.setPriority(0)
                                                        strongAbort.setTrigger(strongAbortTrigger.copy)
                                                    }
                                                    if (weakAbortTrigger != null) {
                                                        val weakAbort = innerState.createTransitionTo(abortedState)
                                                        weakAbort.setTrigger(weakAbortTrigger.copy)
                                                    }
                                                }
                                            }
                                        }

                                        for (transition : outgoingTransitions) {

                                            // Get the _transitionTrigger that was created earlier
                                            val transitionTriggerVariable = transitionTriggerVariableMapping.get(
                                                transition)

                                            // Create a ctrlTransition in the ctrlRegion
                                            val ctrlTransition = runState.createTransitionTo(doneState)
                                            if (transition.typeTermination) {
                                                if (transition.trigger != null) {
                                                    ctrlTransition.setTrigger(
                                                        terminationTrigger.copy.and(transition.trigger))
                                                } else {
                                                    ctrlTransition.setTrigger(terminationTrigger.copy)
                                                }
                                            } else {
                                                ctrlTransition.setTrigger(transition.trigger)
                                            }

                                            // ATTENTION: Test for ctrlTransition.immediate2 because transition's trigger has already been moved to ctrlTransition!!!
                                            ctrlTransition.setImmediate(transition.immediate)
                                            if (ctrlTransition.immediate2) {
                                                ctrlTransition.setImmediate(true)
                                            }
                                            ctrlTransition.addEffect(transitionTriggerVariable.assign(TRUE))
                                        }

                                    }

                                    // Create a single outgoing normal termination to a new connector state
                                    val outgoingConnectorState = state.parentRegion.createState(GENERATED_PREFIX + "C").
                                        uniqueName.setTypeConnector
                                    state.createTransitionTo(outgoingConnectorState).setTypeTermination

                                    for (transition : outgoingTransitions) {

                                        // Modify the outgoing transition
                                        transition.setSourceState(outgoingConnectorState)

                                        // Get the _transitionTrigger that was created earlier
                                        val transitionTriggerVariable = transitionTriggerVariableMapping.get(transition)
                                        if (transitionTriggerVariable != null) {
                                            transition.setTrigger2(transitionTriggerVariable.reference)
                                        } else {

                                            // Fall back to this case when we did not create a trigger variable
                                            // because there where NO strong or weak aborts but one or more triggered
                                            // normal termination transitions.
                                            transition.setTrigger2(transition.trigger)
                                        }

                                        transition.setTypeWeakAbort
                                    }

                                    // OPTIMIZATION
                                    // if the connector has to just one outgoing transition, erase it
                                    if (outgoingConnectorState.outgoingTransitions.size == 1) {
                                        val transition = outgoingConnectorState.outgoingTransitions.get(0)
                                        transition.setImmediate(true)
                                        transition.setTypeTermination
                                        transition.setTrigger(null)
                                        val transitionToDelete = outgoingConnectorState.incomingTransitions.get(0)
                                        state.outgoingTransitions.remove(transitionToDelete)
                                        state.outgoingTransitions.add(transition)
                                        state.parentRegion.states.remove(outgoingConnectorState)
                                    }

                                }
                            }

                            //-------------------------------------------------------------------------
                            //--              C O M P L E X   F I N A L   S T A T E                  --
                            //-------------------------------------------------------------------------
                            // ...
                            // Optimizations: 
                            // (1)   In transitions from one ComplexFinalState (CFS) to another CFS
                            //       optimize: Do not set term to false and to true again later
                            //       (-> .filter[!complexFinalStates.contains(sourceState)])
                            //  ATTENTION: if using this optimization make sure that if there is a
                            //  final&INITIAL-state, the term-variable is initialized with TRUE!!! (not FALSE as usual) ***
                            // (2)   Share a unique final state if possible (-> retrieveFinalState())
                            // (3)   If just one regions: No watcher region is needed, no abort signal and
                            //       only a single term signal 
                            //       (TODO)                
                            def Region transformComplexFinalState(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every state in the SyncChart do the transformation
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformComplexFinalState(rootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            def void transformComplexFinalState(State state, Region targetRootRegion) {
                                val complexFinalStates = state.allContainedStates.filter(
                                    e|
                                        e.parentRegion.parentState == state && e.isFinal && (!e.outgoingTransitions.
                                            nullOrEmpty || e.allContainedStates.size > 0 || e.entryActions.size > 0 ||
                                            e.duringActions.size > 0 || e.exitActions.size > 0)).toList()

                                if (!complexFinalStates.nullOrEmpty) {

                                    var abortFlag = state.createVariable(GENERATED_PREFIX + "abort").setTypeBool.
                                        uniqueName
                                    abortFlag.setInitialValue(FALSE)

                                    var ArrayList<ValuedObject> termVariables = new ArrayList

                                    for (region : state.regions) {
                                        val termVariable = state.createVariable(GENERATED_PREFIX + "term").setTypeBool.
                                            uniqueName
                                        termVariable.setInitialValue(FALSE)
                                        if (region.initialState.final) {

                                            //***
                                            termVariable.setInitialValue(TRUE)
                                        }
                                        termVariables.add(termVariable)

                                        val finalStates = region.states.filter[final && incomingTransitions.size > 0]

                                        for (finalState : finalStates) {
                                            for (transition : finalState.incomingTransitions.filter[
                                                !complexFinalStates.contains(sourceState)]) {
                                                transition.addEffect(termVariable.assign(TRUE))
                                            }
                                            for (transition : finalState.outgoingTransitions.filter[
                                                !complexFinalStates.contains(targetState)]) {
                                                transition.addEffect(termVariable.assign(FALSE))
                                            }
                                        }

                                    }

                                    //Add Watcher Region
                                    val watcherRegion = state.createRegion(GENERATED_PREFIX + "Watch").uniqueName
                                    val watcherTransition = watcherRegion.createInitialState(GENERATED_PREFIX + "Watch").
                                        createImmediateTransitionTo(
                                            watcherRegion.createFinalState(GENERATED_PREFIX + "Aborted"))
                                    watcherTransition.addEffect(abortFlag.assign(TRUE))
                                    for (termVariable : termVariables) {
                                        watcherTransition.setTrigger(
                                            watcherTransition.trigger.and2(termVariable.reference))
                                    }

                                    //Add additional final state
                                    for (complexFinalState : complexFinalStates) {
                                        complexFinalState.setFinal(false)
                                        val finalState = complexFinalState.parentRegion.
                                            retrieveFinalState(GENERATED_PREFIX + "Final")
                                        complexFinalState.createImmediateTransitionTo(finalState).setTrigger(
                                            abortFlag.reference)
                                    }

                                }

                            }

                            //-------------------------------------------------------------------------
                            //--                          S T A T I C                                --
                            //-------------------------------------------------------------------------
                            // In some local superstate M that has a declaration of a static variable x, move the
                            // declaration of the variable to the root state of the SCChart and rename x respecting
                            // a proper unique and qualified naming. Within the scope of x (within M) update all
                            // references (accesses) to x to the new name. Remove the static keyword from the declaration.
                            // This is applied for all superstates that contain static variable declarations.
                            //
                            def Region transformStatic(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every state in the SyncChart do the transformation
                                for (targetTransition : targetRootRegion.getAllContainedStates.immutableCopy) {
                                    targetTransition.transformStatic(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            def void transformStatic(State state, Region targetRootRegion) {
                                val staticValuedObjects = state.valuedObjects.filter[isStatic].toList
                                for (staticValuedObject : staticValuedObjects.immutableCopy) {
                                    staticValuedObject.setName(
                                        state.getHierarchicalName(GENERATED_PREFIX) + GENERATED_PREFIX +
                                            staticValuedObject.name)
                                    state.rootState.valuedObjects.add(staticValuedObject)
                                    staticValuedObject.setStatic(false)
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--          I N P U T   O U T P U T   V A R I A B L E                  --
                            //-------------------------------------------------------------------------
                            // ...
                            def Region transformInputOutputVariable(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every state in the SyncChart do the transformation
                                for (targetTransition : targetRootRegion.getAllContainedStates.immutableCopy) {
                                    targetTransition.transformInputOutputVariable(targetRootRegion);
                                }
        targetRootRegion;
                            }

                            def void transformInputOutputVariable(State state, Region targetRootRegion) {
                                //TODO
                            }

                            //-------------------------------------------------------------------------
                            //--                     W E A K   S U S P E N D                         --
                            //-------------------------------------------------------------------------
                            // ...
                            def Region transformWeakSuspend(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every state in the SyncChart do the transformation
                                for (targetTransition : targetRootRegion.getAllContainedStates.immutableCopy) {
                                    targetTransition.transformWeakSuspend(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            def void transformWeakSuspend(State state, Region targetRootRegion) {

                                val weakSuspends = state.suspendActions.filter[weak].toList

                                if (!weakSuspends.nullOrEmpty) {
                                    val weakSuspendFlag = state.createVariable(GENERATED_PREFIX + "weakSuspend").
                                        setTypeBool.uniqueName
                                    weakSuspendFlag.setInitialValue(FALSE)

                                    for (weakSuspend : weakSuspends.immutableCopy) {
                                        val duringAction = state.createDuringAction
                                        duringAction.setImmediate(weakSuspend.immediate)
                                        duringAction.setTrigger(weakSuspend.trigger.copy)
                                        duringAction.addEffect(weakSuspendFlag.assign(TRUE))
                                        state.localActions.remove(weakSuspend)
                                    }

                                    for (region : state.allContainedRegions.immutableCopy) {
                                        val subStates = region.states.immutableCopy
                                        val wsState = region.createState(GENERATED_PREFIX + "WS").uniqueName
                                        val stateEnum = state.createVariable(GENERATED_PREFIX + "stateEnum").setTypeBool.
                                            uniqueName
                                        var counter = 0

                                        for (subState : subStates) {
                                            val reEnterTransition = wsState.createImmediateTransitionTo(subState)
                                            reEnterTransition.setTrigger(
                                                stateEnum.reference.isEqual(counter.createIntValue))
                                            reEnterTransition.setDeferred(true)
                                            subState.createEntryAction.addEffect(
                                                stateEnum.assign(counter.createIntValue))
                                            counter = counter + 1
                                            val weakSuspendTransition = subState.createImmediateTransitionTo(wsState)
                                            weakSuspendTransition.setTrigger(weakSuspendFlag.reference)
                                        }
                                    }
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                   C O U N T   D E L A Y                             --
                            //-------------------------------------------------------------------------
                            // ...
                            def Region transformCountDelay(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every transition in the SyncChart do the transformation
                                // Iterate over a copy of the list  
                                for (targetTransition : targetRootRegion.getAllContainedTransitions) {

                                    targetTransition.transformCountDelay(targetRootRegion);
                                }

                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // This will encode count delays in transitions.
                            def void transformCountDelay(Transition transition, Region targetRootRegion) {
                                if (transition.delay > 1) {
                                    val parentState = transition.sourceState.parentRegion.parentState

                                    val counter = parentState.createVariable(GENERATED_PREFIX + "counter").setTypeInt.
                                        uniqueName
                                    counter.setInitialValue(0.createIntValue)

                                    // Add during action
                                    val duringAction = parentState.createDuringAction
                                    duringAction.setTrigger(transition.trigger)
                                    duringAction.addEffect(counter.assign((1.createIntValue).add(counter.reference)))

                                    // Modify original trigger
                                    // trigger := (counter == delay)
                                    val trigger = counter.reference.isEqual(transition.delay.createIntValue)
                                    transition.setTrigger(trigger)
                                    transition.setDelay(1)
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                        P R E -  O P E R A T O R                     --
                            //-------------------------------------------------------------------------
                            // Transforming PRE Operator.
                            def Region transformPre(Region rootRegion) {
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformPre(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // Return a list of Pre Expressions for an action that references the valuedObject
                            def List<OperatorExpression> getPreExpression(Action action, ValuedObject valuedObject) {
                                val List<OperatorExpression> returnPreExpressions = <OperatorExpression>newLinkedList;
                                val preExpressions = action.eAllContents.filter(typeof(OperatorExpression)).toList().
                                filter(
                                    e|
                                        (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                                            (e.subExpressions.get(0) instanceof ValuedObjectReference) &&
                                            ((e.subExpressions.get(0) as ValuedObjectReference).valuedObject ==
                                                valuedObject)
                                );
                                returnPreExpressions.addAll(preExpressions);
                                return returnPreExpressions;
                            }

                            // Return a list of Pre Expressions for an action that references the value of a valuedObject
                            def List<OperatorExpression> getPreValExpression(Action action, ValuedObject valuedObject) {
                                val List<OperatorExpression> returnPreValExpressions = <OperatorExpression>newLinkedList;
                                val preValExpressions = action.eAllContents.filter(typeof(OperatorExpression)).toList().
                                filter(
                                    e|
                                        (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                                            (e.subExpressions.get(0) instanceof OperatorExpression) &&
                                            ((e.subExpressions.get(0) as OperatorExpression).operator ==
                                                OperatorType::VAL) &&
                                            ((e.subExpressions.get(0) as OperatorExpression).subExpressions.size() == 1) &&
                                            ((e.subExpressions.get(0) as OperatorExpression).subExpressions.get(0) instanceof ValuedObjectReference) && (((e.
                                                subExpressions.get(0) as OperatorExpression).subExpressions.get(0) as ValuedObjectReference).
                                                valuedObject == valuedObject)
                                );
                                returnPreValExpressions.addAll(preValExpressions);
                                return returnPreValExpressions;
                            }

                            // Traverse all states that might declare a valuedObject that is used with the PRE operator
                            def void transformPre(State state, Region targetRootRegion) {

                                // Filter all valuedObjects and retrieve those that are referenced
                                val allActions = state.eAllContents.filter(typeof(Action)).toList();
                                val allPreValuedObjects = state.valuedObjects.filter(
                                    valuedObject|
                                        allActions.filter(
                                            action|
                                                action.getPreExpression(valuedObject).size > 0 ||
                                                    action.getPreValExpression(valuedObject).size > 0).size > 0);

                                for (preValuedObject : ImmutableList::copyOf(allPreValuedObjects)) {

                                    val newPre = state.createValuedObject(
                                        GENERATED_PREFIX + "pre" + GENERATED_PREFIX + preValuedObject.name).uniqueName
                                    newPre.applyAttributes(preValuedObject)
                                    val newAux = state.createValuedObject(
                                        GENERATED_PREFIX + "aux" + GENERATED_PREFIX + preValuedObject.name).uniqueName
                                    newAux.applyAttributes(preValuedObject)

                                    val preRegion = state.createRegion(GENERATED_PREFIX + "Pre").uniqueName
                                    val preInit = preRegion.createInitialState(GENERATED_PREFIX + "Init").uniqueName.
                                        setFinal
                                    val preWait = preRegion.createFinalState(GENERATED_PREFIX + "Wait").uniqueName

                                    //            val preDone = preRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueName
                                    val transInitWait = preInit.createImmediateTransitionTo(preWait)
                                    transInitWait.addEffect(newAux.assign(preValuedObject.reference))

                                    val transWaitInit = preWait.createTransitionTo(preInit)
                                    transWaitInit.addEffect(newPre.assign(newAux.reference))

                                    //            val transWaitDone = preWait.createTransitionTo(preDone)
                                    //            transWaitDone.setTrigger()
                                    //            val transInitDone = preInit.createTransitionTo(preDone)
                                    // Replace the ComplexExpression Pre(S) by the ValuedObjectReference PreS in all actions            
                                    // Replace the ComplexExpression Pre(?S) by the OperatorExpression ?PreS in all actions            
                                    for (action : allActions) {
                                        val preExpressions = action.getPreExpression(preValuedObject);
                                        val preValExpressions = action.getPreValExpression(preValuedObject);

                                        for (preExpression : preExpressions) {
                                            val container = preExpression.eContainer;

                                            if (container instanceof OperatorExpression) {

                                                // If nested PRE or PRE inside another complex expression
                                                (container as OperatorExpression).subExpressions.remove(preExpression);
                                                (container as OperatorExpression).add(newPre.reference);
                                            } else if (container instanceof Action) {

                                                // If PRE directly a trigger
                                                (container as Action).setTrigger(newPre.reference)
                                            }
                                        }

                                        for (preValExpression : preValExpressions) {
                                            val container = preValExpression.eContainer;

                                            if ((!preValExpression.subExpressions.nullOrEmpty) &&
                                                preValExpression.subExpressions.get(0) instanceof OperatorExpression &&
                                                (preValExpression.subExpressions.get(0) as OperatorExpression).operator ==
                                                    OperatorType::VAL) {

                                                // Transform pre(?V) --> ?PreV
                                                val valueExpression = preValExpression.subExpressions.get(0);
                                                (valueExpression as OperatorExpression).subExpressions.remove(0);
                                                (valueExpression as OperatorExpression).add(newPre.reference);
                                                if (container instanceof Emission) {
                                                    (container as Emission).setNewValue(valueExpression.copy);
                                                } else if (container instanceof OperatorExpression) {

                                                    // If nested PRE or PRE inside another complex expression
                                                    (container as OperatorExpression).subExpressions.remove(
                                                        preValExpression);
                                                    (container as OperatorExpression).add(valueExpression.copy);
                                                }
                                            }

                                        }
                                    }

                                }

                            }

                            //-------------------------------------------------------------------------
                            //--                          S U S P E N D                              --
                            //-------------------------------------------------------------------------
                            //@requires: during
                            //@run-before: entry (because these are considered here)
                            // For a suspend statement of state S create a new top-level region
                            // with two states (NotSuspended (initial) and Suspended). Connect them
                            // with the suspension trigger.
                            // If the trigger is immediate, then connect them immediately and have
                            // the transition back be non-immediate. If it is non immediate then
                            // have the transition back be immediate.
                            // Create an immediate during action of the Suspended state that emits
                            // an auxiliaryDisableValuedObject that is added to all outgoing transitions
                            // (within the disabledExpression) 
                            // Transforming Suspends.
                            def Region transformSuspend(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                var targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // For every state in the SyncChart do the transformation
                                // Iterate over a copy of the list  
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformSuspend(targetRootRegion);
                                }

                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            def void transformSuspend(State state, Region targetRootRegion) {
                                for (suspension : state.suspendActions.filter[!weak].toList.immutableCopy) {
                                    val suspendTrigger = suspension.trigger;
                                    val notSuspendTrigger = not(suspendTrigger)
                                    val immediateSuspension = suspension.isImmediate;

                                    val suspendFlag = state.createVariable(GENERATED_PREFIX + "enabled").setTypeBool.
                                        uniqueName
                                    suspendFlag.setInitialValue(TRUE)

                                    // Do not consider other suspends as actions
                                    val allInnerActions = state.allContainedActions.filter(
                                        e|!(e instanceof SuspendAction))
                                    for (action : allInnerActions) {
                                        action.setTrigger(action.trigger.and2(suspendFlag.reference))
                                    }

                                    // add during action AFTER modifying allInnerActions so that we
                                    // do not modify our new during action
                                    val duringAction = state.createDuringAction
                                    duringAction.setImmediate(immediateSuspension)
                                    duringAction.setTrigger(null)
                                    duringAction.addEffect(suspendFlag.assign(notSuspendTrigger))

                                    // remove suspend action
                                    state.localActions.remove(suspension)
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                     D U R I N G       A C T I O N                   --
                            //-------------------------------------------------------------------------
                            // Transforming During Actions.
                            def Region transformDuring(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // Traverse all states
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformDuring(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // Traverse all states and transform macro states that have actions to transform
                            def void transformDuring(State state, Region targetRootRegion) {

                                // DURING ACTIONS : 
                                // For each action create a separate region in the state. 
                                // Put the action into an transition within the macro state.
                                // Add a loop back to the initial state of the added region.
                                // In case the during action is immediate, the looping transition is non-immediate.
                                // In case the during action is non-immediate, the looping transition is immediate.
                                if (state.duringActions != null && state.duringActions.size > 0) {
                                    val term = state.createVariable(GENERATED_PREFIX + "term").setTypeBool.uniqueName
                                    term.setInitialValue(FALSE)

                                    val mainRegion = state.createRegion(GENERATED_PREFIX + "Main").uniqueName
                                    val mainState = mainRegion.createState(GENERATED_PREFIX + "Main").setInitial
                                    for (region : state.regions.filter(e|e != mainRegion).toList.immutableCopy) {
                                        mainState.regions.add(region)
                                    }
                                    val termTransition = mainState.createTransitionTo(
                                        mainRegion.createState(GENERATED_PREFIX + "Term").setFinal)
                                    termTransition.setTypeTermination
                                    termTransition.addEffect(term.assign(TRUE))

                                    // Create the body of the dummy state - containing the during action
                                    // For every during action: Create a region
                                    for (duringAction : state.duringActions.immutableCopy) {
                                        val region = state.createRegion(GENERATED_PREFIX + "During").uniqueName
                                        val initialState = region.createInitialState(GENERATED_PREFIX + "I")
                                        val middleState = region.createState(GENERATED_PREFIX + "S")
                                        val finalState = region.createFinalState(GENERATED_PREFIX + "F")
                                        val transition1 = initialState.createTransitionTo(middleState)
                                        transition1.setDelay(duringAction.delay);
                                        transition1.setImmediate(duringAction.isImmediate);
                                        transition1.setTrigger(duringAction.trigger.copy);
                                        for (action : duringAction.effects) {
                                            transition1.addEffect(action.copy);
                                        }
                                        val transition2 = middleState.createTransitionTo(initialState)
                                        transition2.setImmediate(!duringAction.isImmediate);
                                        var Transition transition3
                                        if (duringAction.immediate) {
                                            transition3 = middleState.createImmediateTransitionTo(finalState)
                                        } else {
                                            transition3 = initialState.createImmediateTransitionTo(finalState)
                                        }
                                        transition3.setTrigger(term.reference)
                                        transition3.setHighestPriority

                                        // After transforming during actions, erase them
                                        state.localActions.remove(duringAction)
                                    }
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                      E N T R Y         A C T I O N                  --
                            //-------------------------------------------------------------------------
                            // @requires: during actions
                            //
                            // Idea: Setup or create a firstState and a lastState and place the
                            // entry actions of a state in between these two states.
                            // Transforming Entry Actions.
                            def Region transformEntry(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // Traverse all states
                                for (targetState : targetRootRegion.getAllContainedStates.immutableCopy) {
                                    targetState.transformEntry(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // Traverse all states and transform macro states that have actions to transform
                            def void transformEntry(State state, Region targetRootRegion) {
                                if (!state.entryActions.nullOrEmpty) {

                                    var State firstState
                                    var State lastState

                                    if (state.final) {
                                        val connector = state.parentRegion.createState(GENERATED_PREFIX + "C").
                                            uniqueName.setTypeConnector
                                        for (transition : state.incomingTransitions.immutableCopy) {
                                            transition.setTargetState(connector)
                                        }
                                        firstState = connector
                                        lastState = state
                                    } else if (!state.hierarchical) {
                                        val region = state.createRegion(GENERATED_PREFIX + "Entry")
                                        firstState = region.createInitialState(GENERATED_PREFIX + "Init")
                                        lastState = region.createFinalState(GENERATED_PREFIX + "Done")
                                        val exitState = state.parentRegion.createState(GENERATED_PREFIX + "Exit").
                                            uniqueName
                                        for (transition : state.outgoingTransitions.immutableCopy) {
                                            exitState.outgoingTransitions.add(transition)
                                        }
                                        state.createTransitionTo(exitState).setTypeTermination
                                    } else if (state.regions.size == 1) {
                                        val region = state.regions.get(0)
                                        lastState = region.states.filter[initial].get(0) //every region MUST have an initial state
                                        lastState.setNotInitial
                                        firstState = region.createInitialState(GENERATED_PREFIX + "Init").uniqueName
                                    } else { // state has several regions
                                        val region = state.createRegion(GENERATED_PREFIX + "Entry").uniqueName
                                        lastState = region.createState(GENERATED_PREFIX + "Main")
                                        for (mainRegion : state.regions.filter(e|e != region).toList.immutableCopy) {
                                            lastState.regions.add(mainRegion)
                                        }
                                        firstState = region.createInitialState(GENERATED_PREFIX + "Init")
                                        val finalState = region.createFinalState(GENERATED_PREFIX + "Done")
                                        lastState.createTransitionTo(finalState).setTypeTermination
                                    }

                                    val entryRegion = firstState.parentRegion
                                    val lastEntryAction = state.entryActions.last
                                    for (entryAction : state.entryActions.immutableCopy) {
                                        var connector = lastState
                                        if (entryAction != lastEntryAction) {
                                            connector = entryRegion.createState(GENERATED_PREFIX + "C").uniqueName.
                                                setTypeConnector
                                        }
                                        val transition = firstState.createImmediateTransitionTo(connector)
                                        for (effect : entryAction.effects) {
                                            transition.addEffect(effect.copy)
                                        }
                                        if (entryAction.trigger != null) {
                                            transition.setTrigger(entryAction.trigger)

                                            // add default transition
                                            firstState.createImmediateTransitionTo(connector)
                                        }
                                        firstState = connector

                                        // After transforming entry actions, erase them
                                        state.localActions.remove(entryAction)
                                    }
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                      E X I T       A C T I O N S                    --
                            //-------------------------------------------------------------------------
                            // @requires: entry actions
                            // @requires: during actions
                            // @requires: suspend
                            // @requires: valued valuedObjects
                            // Transforming Exit Actions. 
                            def Region transformExit(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // Prepare all states so that each reagion has at most one final state
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.prepareExit(targetRootRegion);
                                }

                                // Traverse all states
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformExit(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            def void prepareExit(State state, Region targetRootRegion) {
                                for (region : state.regions) {
                                    val finalStates = region.finalStates
                                    if (finalStates.size > 1) {
                                        val firstFinalState = finalStates.get(0)
                                        for (finalState : finalStates) {
                                            if (finalState != firstFinalState) {
                                                finalState.setNotFinal
                                                finalState.createImmediateTransitionTo(firstFinalState)
                                            }
                                        }
                                    }
                                }
                            }

                            // Traverse all states and transform macro states that have actions to transform
                            def void transformExit(State state, Region targetRootRegion) {
                                if (!state.exitActions.nullOrEmpty && !state.final) {

                                    val stateOutgoingTransitions = state.outgoingTransitions.size
                                    var State firstState
                                    var State lastState

                                    if (!state.hierarchical) {
                                        val region = state.createRegion(GENERATED_PREFIX + "Exit")
                                        firstState = region.createInitialState(GENERATED_PREFIX + "Init")
                                        lastState = region.createFinalState(GENERATED_PREFIX + "Done")
                                    } else if (state.regions.size == 1 && !state.regions.get(0).finalStates.nullOrEmpty) {
                                        val region = state.regions.get(0)
                                        lastState = region.createFinalState(GENERATED_PREFIX + "Done")

                                        firstState = region.finalStates.get(0) //every region MUST have an initial state
                                        firstState.setNotFinal
                                    } else { // state has several regions (or one region without any final state!)
                                        val region = state.createRegion(GENERATED_PREFIX + "Entry").uniqueName
                                        firstState = region.createFinalState(GENERATED_PREFIX + "Main")
                                        for (mainRegion : state.regions.filter(e|e != region).toList.immutableCopy) {
                                            firstState.regions.add(mainRegion)
                                        }
                                        lastState = region.createFinalState(GENERATED_PREFIX + "Done")
                                    }

                                    // Optimization: "&& state.outgoingTransitions.filter[trigger != null].size > 0"
                                    // Do not create superflous exitOptionStates
                                    if (stateOutgoingTransitions > 0 &&
                                        state.outgoingTransitions.filter[trigger != null].size > 0) {

                                        // Memorize outgoing transition
                                        val region = firstState.parentRegion
                                        var ValuedObject memory
                                        if (stateOutgoingTransitions > 1) {
                                            memory = state.parentRegion.parentState.createVariable(
                                                GENERATED_PREFIX + "exit").setTypeInt.uniqueName
                                        }
                                        val middleState = region.createState(GENERATED_PREFIX + "Memorize").
                                            setTypeConnector
                                        val exitOptionState = state.parentRegion.createState(
                                            GENERATED_PREFIX + "ExitOption").uniqueName.setTypeConnector
                                        var counter = 1
                                        for (transition : state.outgoingTransitions.immutableCopy) {
                                            val exitTransition = exitOptionState.
                                                createImmediateTransitionTo(transition.targetState)
                                            if (stateOutgoingTransitions > 1) {
                                                exitTransition.setTrigger(
                                                    memory.reference.isEqual(counter.createIntValue))
                                            }
                                            for (effect : transition.effects.immutableCopy) {
                                                exitTransition.effects.add(effect)
                                            }

                                            firstState.outgoingTransitions.add(transition)
                                            transition.setImmediate(transition.immediate2) // Ensure termination transitions are interpreted as immediate
                                            transition.setTypeWeakAbort
                                            transition.setTargetState(middleState)
                                            if (stateOutgoingTransitions > 1) {
                                                transition.addEffect(memory.assign(counter.createIntValue))
                                                counter = counter + 1
                                            }
                                        }
                                        state.createImmediateTransitionTo(exitOptionState).setTypeTermination
                                        firstState = middleState
                                    }

                                    val entryRegion = firstState.parentRegion
                                    val lastExitAction = state.exitActions.last
                                    for (exitAction : state.exitActions.immutableCopy) {
                                        var connector = lastState
                                        if (exitAction != lastExitAction) {
                                            connector = entryRegion.createState(GENERATED_PREFIX + "C").uniqueName.
                                                setTypeConnector
                                        }
                                        val transition = firstState.createImmediateTransitionTo(connector)
                                        for (effect : exitAction.effects) {
                                            transition.addEffect(effect.copy)
                                        }
                                        if (exitAction.trigger != null) {
                                            transition.setTrigger(exitAction.trigger)

                                            // add default transition
                                            firstState.createImmediateTransitionTo(connector)
                                        }
                                        firstState = connector

                                        // After transforming exit actions, erase them
                                        state.localActions.remove(exitAction)
                                    }
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                       I N I T I A L I Z A T I O N                   --
                            //-------------------------------------------------------------------------
                            // @requires: entry actions
                            // Transforming Variable Initializations
                            def Region transformInitialization(Region rootRegion) {

                                // Clone the complete SCCharts region 
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;

                                // Traverse all states
                                for (targetState : targetRootRegion.getAllContainedStates.immutableCopy) {
                                    targetState.transformInitialization(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // Traverse all states and transform macro states that have actions to transform
                            def void transformInitialization(State state, Region targetRootRegion) {
                                val valuedObjects = state.valuedObjects.filter[initialValue != null]

                                if (!valuedObjects.nullOrEmpty) {
                                    for (valuedObject : valuedObjects) {
                                        val entryAction = state.createEntryAction
                                        entryAction.addAssignment(valuedObject.assign(valuedObject.initialValue.copy))
                                        valuedObject.setInitialValue(null)
                                    }
                                }
                            }

                            //-------------------------------------------------------------------------
                            //--                        H I S T O R Y                                --
                            //-------------------------------------------------------------------------
                            // @requires: suspend
                            // Transforming History. This is using the concept of suspend so it must
                            // be followed by resolving suspension
                            def Region transformHistory(Region rootRegion) {
                                val targetRootRegion = rootRegion.copy.fixAllPriorities;
                                for (targetState : targetRootRegion.getAllContainedStates) {
                                    targetState.transformHistory(targetRootRegion);
                                }
                                targetRootRegion.fixAllTextualOrdersByPriorities;
                            }

                            // Traverse all states and transform macro states that have connecting
                            // (incoming) history transitions.    
                            def void transformHistory(State state, Region targetRootRegion) {
                                val historyTransitions = ImmutableList::copyOf(
                                    state.incomingTransitions.filter[isHistory])
                                val deepHistoryTransitions = historyTransitions.filter[!isDeepHistory]
                                val nonHistoryTransitions = ImmutableList::copyOf(
                                    state.incomingTransitions.filter[!isHistory])

                                if (historyTransitions != null && historyTransitions.size > 0 && state.regions != null &&
                                    state.regions.size > 0) {
                                    var int initialValue
                                    var List<ValuedObject> stateEnumsAll = new ArrayList
                                    var List<ValuedObject> stateEnumsDeep = new ArrayList

                                    val regions = state.regions.immutableCopy
                                    var regionsDeep = state.regions.immutableCopy
                                    if (!deepHistoryTransitions.nullOrEmpty) {
                                        regionsDeep = state.allContainedRegions
                                    }

                                    for (region : regionsDeep) {
                                        var counter = 0
                                        val stateEnum = state.parentRegion.parentState.createVariable(
                                            GENERATED_PREFIX + state.id).setTypeInt.uniqueName
                                        stateEnumsAll.add(stateEnum)
                                        if (!regions.contains(region)) {
                                            stateEnumsDeep.add(stateEnum)
                                        }
                                        val originalInitialState = region.initialState
                                        originalInitialState.setNotInitial
                                        val subStates = region.states.immutableCopy
                                        val initialState = region.createInitialState(GENERATED_PREFIX + "Init").
                                            uniqueName

                                        for (subState : subStates) {
                                            val transition = initialState.createImmediateTransitionTo(subState)
                                            transition.setTrigger(stateEnum.reference.isEqual(counter.createIntValue))
                                            subState.createEntryAction.addEffect(
                                                stateEnum.assign(counter.createIntValue))
                                            if (subState == originalInitialState) {
                                                initialValue = counter
                                                stateEnum.setInitialValue(counter.createIntValue)
                                            }
                                            counter = counter + 1
                                        }
                                    }

                                    for (transition : historyTransitions) {
                                        if (!transition.deepHistory) {

                                            // Reset deepStateEnums
                                            for (stateEnum : stateEnumsDeep) {
                                                transition.addEffect(stateEnum.assign(initialValue.createIntValue))
                                            }
                                        }
                                        transition.setHistory(HistoryType::RESET)
                                    }

                                    for (transition : nonHistoryTransitions) {
                                        for (stateEnum : stateEnumsAll) {
                                            transition.addEffect(stateEnum.assign(initialValue.createIntValue))
                                        }
                                    }

                                }
                            }

                            ////////////////////////////////////////////////////////////////////////////////////////           
                            ////////////////////////////////////////////////////////////////////////////////////////           
                            ////////////////////////////////////////////////////////////////////////////////////////           
                            //                                                                                    //
                            //                                       O  L  D                                      //
                            //                                                                                    //
                            ////////////////////////////////////////////////////////////////////////////////////////           
                            ////////////////////////////////////////////////////////////////////////////////////////           
                            ////////////////////////////////////////////////////////////////////////////////////////           
                            //    //-------------------------------------------------------------------------
                            //    //--                S U R F A C E  &   D E P T H                         --
                            //    //-------------------------------------------------------------------------
                            //    //@requires: abort transformation (there must not be any weak or strong aborts outgoing from
                            //    //                                 macro state, hence we just consider simple states here)
                            //
                            //    // For every non-hierarchical state S that has outgoing transitions and is of type NORMAL:
                            //    // Create an auxiliary valuedObject isDepth_S that indicates that the state was
                            //    // entered in an earlier tick and add it to the parent state P of the parent region R of S.
                            //    // Modify all triggers of outgoing non-immediate transitions T of S: 1. set them to be
                            //    // immediate and 2. add "isDepth_S &&" to its trigger.
                            //    // Modify state S and make it a conditional.
                            //    // Now walk through all n transitions T_1..n outgoing from S (ordered ascended by their priority):
                            //    // For T_i create a conditional C_i. Connect C_i-1 and C_i with a true triggered immediate transition
                            //    // of priority 2. Set priority of T_i to 1. Note that T_i's original priority is now implicitly encoded
                            //    // by the sequential order of evaluating the conditionals C_1..n.
                            //    // The last conditional C_n connect with a new a normal state D (the explicit depth of S).
                            //    // Connect D with C_1 using a transition that emits isDepth_S.
                            //    
                            //    // Note that conditionals cannot be marked to be initial. Hence, if a state S is marked initial 
                            //    // then an additional initial state I with a true triggered immediate transition to S will
                            //    // be inserted. \code{S} is then marked not to be initial. This is a necessary pre-processing for
                            //    // the above transformation.
                            //    
                            //    def Region transformSurfaceDepth (Region rootRegion) {
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = rootRegion.copy;
                            //
                            //        var targetStates = targetRootRegion.eAllContents().toIterable().filter(typeof(State)).filter(e | !e.hierarchical).toList();
                            //
                            //        // For every state in the SyncChart do the transformation
                            //        // Iterate over a copy of the list  
                            //        for(targetState : targetStates) {
                            //            targetState.transformSurfaceDepth(targetRootRegion);
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //         
                            //     def void transformSurfaceDepth(State state, Region targetRootRegion) {
                            //       if (state.outgoingTransitions.size > 0 && state.type == StateType::NORMAL) {
                            //         val parentRegion = state.parentRegion;
                            //         val stateId = state.id;
                            //         val stateLabel = state.label;
                            //
                            //         // SPECIAL CASE OF INITIAL STATES 
                            //         // Insert a state and a transition here because conditional states cannot be initial
                            //         if (state.isInitial) {
                            //             val initialState  = SCChartsFactory::eINSTANCE.createState();
                            //             initialState.setId(state.id("Initial"));
                            //             initialState.setLabel("I"); 
                            //             initialState.setInitial(true);
                            //             parentRegion.states.add(initialState);
                            //             state.setInitial(false);
                            //             // Connect             
                            //             val connect = SCChartsFactory::eINSTANCE.createTransition();
                            //             connect.setImmediate(true);
                            //             connect.setLabel("#");
                            //             connect.setPriority(1);
                            //             connect.setTargetState(state);
                            //             initialState.outgoingTransitions.add(connect);
                            //         }             
                            //           
                            //         // Create auxiliary valuedObject
                            //         var isDepthValuedObjectUID = "isDepth_" + parentRegion.id + "_" + state.id;
                            //         val isDepthValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //         isDepthValuedObject.setName(isDepthValuedObjectUID);
                            //         isDepthValuedObject.setSignal(true)
                            //         isDepthValuedObject.setInput(false);
                            //         isDepthValuedObject.setOutput(false);
                            //         isDepthValuedObject.setType(ValueType::PURE);
                            //         parentRegion.parentState.valuedObjects.add(isDepthValuedObject);  
                            //
                            //         // Modify triggers of non immediate transitions and make them immediate
                            //         val nonImmediateTransitions = state.outgoingTransitions.filter(e | !e.isImmediate).toList;
                            //         val auxiliaryTrigger =  KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //             auxiliaryTrigger.setValuedObject(isDepthValuedObject);
                            //         for (transition : nonImmediateTransitions) {
                            //             // Make this transition immediate now
                            //             transition.setImmediate(true);
                            //             // Modify trigger if any
                            //             if (transition.trigger != null) {
                            //                 val andAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                 andAuxiliaryTrigger.setOperator(OperatorType::AND);
                            //                 andAuxiliaryTrigger.add(auxiliaryTrigger.copy);
                            //                 andAuxiliaryTrigger.add(transition.trigger);
                            //                 transition.setTrigger(andAuxiliaryTrigger);
                            //             }  else {
                            //                 transition.setTrigger(auxiliaryTrigger.copy);
                            //             }
                            //         } 
                            //
                            //         // Modify surfaceState (the original state)
                            //         val surfaceState = state;
                            //         surfaceState.setType(StateType::CONNECTOR);
                            //         surfaceState.setId(stateId + "Surface");
                            //         surfaceState.setLabel(stateLabel + "Surface");
                            //         
                            //         // For every state create a number of surface nodes
                            //         val orderedTransitionList = state.outgoingTransitions.sort(e1, e2 | if (e1.priority < e2.priority) {-1} else {1});
                            //         
                            //         var previousSurfState = surfaceState;
                            //         var State surfState = null;
                            //         for (transition : orderedTransitionList) {
                            //            surfState  = SCChartsFactory::eINSTANCE.createState();
                            //            surfState.setType(StateType::CONNECTOR);
                            //            surfState.setId(stateId + transition.id("Surface"));
                            //            surfState.setLabel(stateLabel + "Surface");
                            //            parentRegion.states.add(surfState);
                            //            // Move transition to this state
                            //            surfState.outgoingTransitions.add(transition);
                            //            // We can now set the transition priority to 1 (it is reflected implicityly by the sequential order now)
                            //            transition.setPriority(1);
                            //            // Connect
                            //            val connect = SCChartsFactory::eINSTANCE.createTransition();
                            //            connect.setImmediate(true);
                            //            connect.setLabel("#");
                            //            connect.setPriority(2);
                            //            connect.setTargetState(surfState);
                            //            previousSurfState.outgoingTransitions.add(connect);
                            //            // Next cycle
                            //            previousSurfState = surfState; 
                            //         }
                            //         
                            //         // Add an additional last state that will become depth State
                            //         val depthState  = SCChartsFactory::eINSTANCE.createState();
                            //         depthState.setType(StateType::NORMAL);
                            //         depthState.setId(stateId); // + "Depth");
                            //         depthState.setLabel(stateLabel); // + "Depth");
                            //         parentRegion.states.add(depthState);
                            //         // Connect
                            //         val connect = SCChartsFactory::eINSTANCE.createTransition();
                            //         connect.setImmediate(true);
                            //         connect.setLabel("#");
                            //         connect.setPriority(2);
                            //         connect.setTargetState(depthState);
                            //         surfState.outgoingTransitions.add(connect);
                            //         
                            //         // Connect back depth with surface state
                            //         val connectBack = SCChartsFactory::eINSTANCE.createTransition();
                            //         // This MUST be highest priority so that the control flow restarts and takes other 
                            //         // outgoing transition.
                            //         // There should not be any other outgoing transition.
                            //         connectBack.setPriority(1);
                            //         connectBack.setTargetState(surfaceState);
                            //         depthState.outgoingTransitions.add(connectBack);
                            //         val auxiliaryEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //         auxiliaryEmission.setValuedObject(isDepthValuedObject);
                            //         connectBack.addEmission(auxiliaryEmission);
                            //         
                            //       }
                            //     }
                            //
                            //
                            //
                            //    //-------------------------------------------------------------------------
                            //    //--                 S P L I T   T R A N S I T I O N                     --
                            //    //-------------------------------------------------------------------------
                            //
                            //    // For every transition T that has both, a trigger and an effect do the following:
                            //    // Create a conditional C and add it to the parent of T's source state S_src.
                            //    // Create a new true triggered immediate effect transition T_eff and move all effects of T to T_eff.
                            //    // Set the T_eff to have T's target state. Set T to have the target C.
                            //    // Add T_eff to C's outgoing transitions. 
                            //    
                            //    def Region transformTriggerEffect (Region rootRegion) {
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = rootRegion.copy;
                            //
                            //        var targetTransitions = targetRootRegion.eAllContents().toIterable().filter(typeof(Transition)).toList();
                            //
                            //        // For every transition in the SyncChart do the transformation
                            //        // Iterate over a copy of the list  
                            //        for(targetTransition : targetTransitions) {
                            //            targetTransition.transformTriggerEffect(targetRootRegion);
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //         
                            //     def void transformTriggerEffect(Transition transition, Region targetRootRegion) {
                            //         
                            //         // Only apply this to transition that have both, a trigger and effects!
                            //         if (transition.trigger != null && !transition.effects.nullOrEmpty) {
                            //              val targetState = transition.targetState;
                            //              val parentRegion = targetState.parentRegion;
                            //              val triggerTransition = transition;
                            //             
                            //             val triggeredState  = SCChartsFactory::eINSTANCE.createState();
                            //             triggeredState.setId(targetState.id + "_Triggered_" + targetState.id);
                            //             triggeredState.setLabel( targetState.id + "_Triggered"); 
                            //             triggeredState.setType(StateType::CONNECTOR);
                            //             parentRegion.states.add(triggeredState);
                            //             // Create new effect transition             
                            //             val effectTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //             effectTransition.setImmediate(true);
                            //             effectTransition.setLabel("#");
                            //             effectTransition.setPriority(1);
                            //             // Move effect to effect transition
                            //             for (effect : transition.effects) {
                            //                 effectTransition.addEffect(effect.copy);
                            //             } 
                            //             transition.effects.clear;
                            //             // Re-connect
                            //             effectTransition.setTargetState(transition.targetState);
                            //             triggerTransition.setTargetState(triggeredState);
                            //             triggeredState.outgoingTransitions.add(effectTransition);
                            //         }
                            //     }
                            //    //-------------------------------------------------------------------------
                            //    //--           F I N A L   S T A T E S    T R A N S I T I O N            --
                            //    //-------------------------------------------------------------------------
                            //    // For every region R that contains final states with outgoing transitions do the following:
                            //    // Create an Abort valuedObject and add it to R's parent state P.
                            //    // Go through every region of P other then R and search for final states Q_1..n. For all incoming transitions
                            //    // of all Q_1..n add an emission of Abort.
                            //    // Find a common final state F of region R that has no outgoing transition. If no one exists, create one.
                            //    // For every final state S with outgoing transitions inside R do the following:
                            //    // Finally add an immediate transition with maximal (lowest) priority from S to F triggered by Abort.
                            //    // NO, THE FOLLOWING SEEMS TO BE WRONG
                            //    // Optimization: We dont need to watch final states with outgoing transitions! These states are transformed 
                            //    // anyway ***
                            //    def Region transformFinalStateTransition(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = rootRegion.copy;
                            //
                            //        // For every state in the SyncChart do the transformation
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformFinalStateTransition(rootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    def void transformFinalStateTransition(State parentState, Region targetRootRegion) {
                            //        val parentStatesIsConsidered = parentState.eAllContents().toIterable().filter(typeof(State)).filter(
                            //            e|e.parentRegion.parentState == parentState && e.isFinal && !e.outgoingTransitions.nullOrEmpty).toList();
                            //
                            //        if (!parentStatesIsConsidered.nullOrEmpty) {
                            //
                            //            // Auxiliary reset valuedObject
                            //            //var auxiliaryResetValuedObjectUID = parentState.id("Abort");
                            //            val auxiliaryResetValuedObject = parentState.createPureSignal(parentState.id("Abort"))
                            //
                            //            //val auxiliaryResetValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            //auxiliaryResetValuedObject.setName(auxiliaryResetValuedObjectUID);
                            //            //auxiliaryResetValuedObject.setInput(false);
                            //            //auxiliaryResetValuedObject.setOutput(false);
                            //            //auxiliaryResetValuedObject.setType(ValueType::PURE);
                            //            //parentState.valuedObjects.add(auxiliaryResetValuedObject);
                            //            // Auxiliary watch master region with macro WatchMasterState and AbortedState
                            //            val auxiliaryWatchMasterRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //            parentState.regions.add(auxiliaryWatchMasterRegion);
                            //            auxiliaryWatchMasterRegion.setId(parentState.id("Watch"));
                            //            val auxiliaryWatchMasterState = SCChartsFactory::eINSTANCE.createState();
                            //            auxiliaryWatchMasterRegion.states.add(auxiliaryWatchMasterState);
                            //            auxiliaryWatchMasterState.setId(parentState.id("Watch"));
                            //            auxiliaryWatchMasterState.setLabel("Watch");
                            //            auxiliaryWatchMasterState.setInitial(true);
                            //            val auxiliaryAbortedState = SCChartsFactory::eINSTANCE.createState();
                            //            auxiliaryWatchMasterRegion.states.add(auxiliaryAbortedState);
                            //            auxiliaryAbortedState.setId(parentState.id("Aborted"));
                            //            auxiliaryAbortedState.setLabel("Aborted");
                            //            auxiliaryAbortedState.setFinal(true);
                            //
                            //            // Connect WatchMaster and Aborted state
                            //            val abortRegionTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            abortRegionTransition.setPriority(1)
                            //            abortRegionTransition.setType(TransitionType::NORMALTERMINATION);
                            //            abortRegionTransition.setTargetState(auxiliaryAbortedState);
                            //            auxiliaryWatchMasterState.outgoingTransitions.add(abortRegionTransition);
                            //
                            //            // In this normal termination emit reset valuedObject for the region           
                            //            val auxiliaryResetValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //            auxiliaryResetValuedObjectEmission.setValuedObject(auxiliaryResetValuedObject);
                            //            abortRegionTransition.addEmission(auxiliaryResetValuedObjectEmission);
                            //
                            //            // For every parallel region W (that has final states with NO outgoing transitions) ***
                            //            val consideredRegions = parentState.regions // parentState.regions.filter(e | !e.states.filter(ee | ee.isFinal && ee.outgoingTransitions.nullOrEmpty).nullOrEmpty)
                            //            for (parallelRegion : consideredRegions) {
                            //                if (parallelRegion != auxiliaryWatchMasterRegion) {
                            //
                            //                    // Auxiliary term valuedObject - Try to find existing for parallelRegion
                            //                    val auxiliaryRegionTermValuedObjectUID = parallelRegion.id("Term");
                            //                    var ValuedObject auxiliaryRegionTermValuedObject = null;
                            //                    auxiliaryRegionTermValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //                    auxiliaryRegionTermValuedObject.setName(auxiliaryRegionTermValuedObjectUID);
                            //                    auxiliaryRegionTermValuedObject.setInput(false);
                            //                    auxiliaryRegionTermValuedObject.setOutput(false);
                            //                    auxiliaryRegionTermValuedObject.setType(ValueType::PURE);
                            //                    parentState.valuedObjects.add(auxiliaryRegionTermValuedObject);
                            //
                            //                    for (finalState : parallelRegion.states.filter(e|e.isFinal)) {
                            //                        for (finalStateTransition : finalState.incomingTransitions) {
                            //                            val auxiliaryTermValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                            auxiliaryTermValuedObjectEmission.setValuedObject(auxiliaryRegionTermValuedObject);
                            //                            finalStateTransition.addEmission(auxiliaryTermValuedObjectEmission);
                            //                        }
                            //                    }
                            //
                            //                    // Now add regions for all parallelRegions in the auxiliaryWatchMasterState
                            //                    val auxiliaryWatchRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //                    auxiliaryWatchMasterState.regions.add(auxiliaryWatchRegion);
                            //                    auxiliaryWatchRegion.setId(parallelRegion.id("Watch"));
                            //                    val auxiliaryWatchState = SCChartsFactory::eINSTANCE.createState();
                            //                    auxiliaryWatchRegion.states.add(auxiliaryWatchState);
                            //                    auxiliaryWatchState.setId(parallelRegion.id("I"));
                            //                    auxiliaryWatchState.setLabel("I");
                            //                    auxiliaryWatchState.setInitial(true);
                            //                    val auxiliaryTerminatedState = SCChartsFactory::eINSTANCE.createState();
                            //                    auxiliaryWatchRegion.states.add(auxiliaryTerminatedState);
                            //                    auxiliaryTerminatedState.setId(parallelRegion.id("T"));
                            //                    auxiliaryTerminatedState.setLabel("T");
                            //                    auxiliaryTerminatedState.setFinal(true);
                            //
                            //                    // Connect
                            //                    val terminatedRegionTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                    val terminatedRegionTransitionTrigger = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                    terminatedRegionTransitionTrigger.setValuedObject(auxiliaryRegionTermValuedObject);
                            //                    terminatedRegionTransition.setPriority(1)
                            //                    terminatedRegionTransition.setImmediate(true);
                            //                    terminatedRegionTransition.setTrigger(terminatedRegionTransitionTrigger);
                            //                    terminatedRegionTransition.setTargetState(auxiliaryTerminatedState);
                            //                    auxiliaryWatchState.outgoingTransitions.add(terminatedRegionTransition);
                            //                }
                            //            } // end for every parallelRegion
                            //
                            //            for (region : parentState.regions) {
                            //
                            //                // Consider regions that contain final states with outgoing transitions
                            //                val consideredFinalStates = region.states.filter(e|e.isFinal && !e.outgoingTransitions.nullOrEmpty);
                            //
                            //                if (!consideredFinalStates.nullOrEmpty) {
                            //                    val parentRegion = region;
                            //
                            //                    // Find the one final state for the parentRegion to abort to
                            //                    var State finalStateAbortTarget = null;
                            //                    val finalStates = parentRegion.states.filter(e|e.isFinal && e.outgoingTransitions.nullOrEmpty);
                            //                    if (finalStates.nullOrEmpty) {
                            //
                            //                        // Create one 
                            //                        finalStateAbortTarget = SCChartsFactory::eINSTANCE.createState();
                            //                        finalStateAbortTarget.setId(parentRegion.id("final"));
                            //                        finalStateAbortTarget.setLabel("final");
                            //                        finalStateAbortTarget.setInitial(false);
                            //                        finalStateAbortTarget.setFinal(true);
                            //                        parentRegion.states.add(finalStateAbortTarget);
                            //                    } else {
                            //                        finalStateAbortTarget = finalStates.head;
                            //                    }
                            //
                            //                    // Now handle the consider states, make them non-final and add abort transition
                            //                    for (state : consideredFinalStates) {
                            //                        val sourceState = state;
                            //                        val maxPrio = sourceState.outgoingTransitions.size + 1;
                            //
                            //                        // Set source state not to be final any more
                            //                        sourceState.setFinal(false);
                            //
                            //                        // Add aborting transition
                            //                        val resetTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                        val auxiliaryResetTrigger = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                        auxiliaryResetTrigger.setValuedObject(auxiliaryResetValuedObject);
                            //                        resetTransition.setTrigger(auxiliaryResetTrigger);
                            //                        resetTransition.setPriority(maxPrio)
                            //                        resetTransition.setImmediate(true);
                            //                        resetTransition.setLabel("#");
                            //                        resetTransition.setTargetState(finalStateAbortTarget);
                            //                        sourceState.outgoingTransitions.add(resetTransition);
                            //                    } // end for
                            //                } // end if considered states <> null                
                            //            }
                            //
                            //        } // end if considered
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--             E X P O S E   L O C A L   S I G N A L S                 --
                            //    //-------------------------------------------------------------------------
                            //    // @requires: none
                            //    // Transforming Local ValuedObjects.
                            //    def Region transformExposeLocalValuedObject(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // Traverse all states
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformExposeLocalValuedObject(targetRootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    // Traverse all states and transform possible local valuedObjects.
                            //    def void transformExposeLocalValuedObject(State state, Region targetRootRegion) {
                            //
                            //        // EXPOSE LOCAL SIGNALS: For every local valuedObject create a global valuedObject
                            //        // and wherever the local valuedObject is emitted, also emit the new global 
                            //        // valuedObject.
                            //        // Name the new global valuedObjects according to the local valuedObject's hierarchy. 
                            //        // Exclude the top level state
                            //        if (state.parentRegion == targetRootRegion) {
                            //            return;
                            //        }
                            //
                            //        // There are local valuedObjects, raise them
                            //        if (state.valuedObjects != null && state.valuedObjects.size > 0) {
                            //            val hierarchicalStateName = state.getHierarchicalName("LOCAL");
                            //
                            //            for (ValuedObject localValuedObject : ImmutableList::copyOf(state.valuedObjects)) {
                            //                val newValuedObjectName = hierarchicalStateName + "_" + localValuedObject.name
                            //                val globalValuedObject = targetRootRegion.rootState.createValuedObject(newValuedObjectName).setOutput
                            //                globalValuedObject.applyAttributes(localValuedObject)
                            //
                            //                // For every Emission/Assignment of the local ValuedObject add an Emission of the new
                            //                // global ValuedObject
                            //                val allActions = state.eAllContents().toIterable().filter(typeof(Action)).toList();
                            //                val localValuedObjectActions = allActions.filter(
                            //                    e|
                            //                        (e.allContainedEmissions.filter(ee|ee.valuedObject == localValuedObject)
                            //                       ).
                            //                            size > 0);
                            //
                            //                for (localValuedObjectAction : ImmutableList::copyOf(localValuedObjectActions)) {
                            //                    val lastMatchingEmission = localValuedObjectAction.allContainedEmissions.filter(
                            //                        ee|ee == localValuedObject).last as Emission;
                            //                    if (lastMatchingEmission != null) {
                            //                        val newValue = lastMatchingEmission.newValue
                            //                        val emission = localValuedObjectAction.addEmission(globalValuedObject.emit);
                            //                        if (newValue != null) {
                            //                            emission.setNewValue(newValue.copy)
                            //                        }
                            //                    }
                            //                }
                            //
                            //            }
                            //        } // end if local valuedObjects present
                            //
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                   C O U N T   D E L A Y S                           --
                            //    //-------------------------------------------------------------------------
                            //    // For every transition T from state S with a count delay n create a region R. Put the
                            //    // region into S (hence, S may become a macro state). Create a new valuedObject countDelay that is emitted
                            //    // by the last transition of the auxiliary region R. Create n+1 states within R and connect
                            //    // these by the same trigger of T just without the count delay. The n+1's state must be final
                            //    // in order to handle possible outgoing normal terminations of S correctly.
                            //    def Region transformCountDelay(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = rootRegion.copy;
                            //
                            //        var targetTransitions = targetRootRegion.eAllContents().toIterable().filter(typeof(Transition)).toList();
                            //
                            //        // For every transition in the SyncChart do the transformation
                            //        // Iterate over a copy of the list  
                            //        for (targetTransition : targetTransitions) {
                            //
                            //            targetTransition.transformCountDelay(targetRootRegion);
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //
                            //    // This will encode count delays in transitions.
                            //    def void transformCountDelay(Transition transition, Region targetRootRegion) {
                            //        if (transition.delay > 1) {
                            //            val targetRootState = targetRootRegion.states.get(0);
                            //            val sourceState = transition.sourceState;
                            //
                            //            // Optimization: If there is no outgoing normal termination out of S then do not mark states as final
                            //            val existsTermination = !(sourceState.parentRegion.parentState.outgoingTransitions.filter(
                            //                e|e.type == TransitionType::NORMALTERMINATION).nullOrEmpty);
                            //
                            //            // auxiliary trigger valuedObject
                            //            var auxiliaryValuedObjectUID = transition.id("countDelay");
                            //            val auxiliaryValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            auxiliaryValuedObject.setName(auxiliaryValuedObjectUID);
                            //            auxiliaryValuedObject.setInput(false);
                            //            auxiliaryValuedObject.setOutput(false);
                            //            auxiliaryValuedObject.setType(ValueType::PURE);
                            //            targetRootState.valuedObjects.add(auxiliaryValuedObject);
                            //
                            //            val triggerExpression = transition.trigger;
                            //
                            //            // Create auxiliary region R and add it to the source state.
                            //            // Also add the auxiliary valuedObject to this common parent state
                            //            val auxiliaryRegion = SCChartsFactory::eINSTANCE.createRegion()
                            //            sourceState.regions.add(auxiliaryRegion);
                            //
                            //            var delay = 0;
                            //            var State previousAuxiliaryState = null;
                            //            var State auxiliaryState = null;
                            //            while (delay <= transition.delay) {
                            //                delay = delay + 1;
                            //                previousAuxiliaryState = auxiliaryState;
                            //
                            //                auxiliaryState = SCChartsFactory::eINSTANCE.createState();
                            //                auxiliaryState.setId("delay" + delay);
                            //                auxiliaryState.setLabel(delay + "");
                            //                auxiliaryState.setInitial(delay == 1);
                            //                auxiliaryRegion.states.add(auxiliaryState);
                            //
                            //                if (previousAuxiliaryState != null) {
                            //                    val connect = SCChartsFactory::eINSTANCE.createTransition();
                            //                    connect.setPriority(2);
                            //                    connect.targetState = auxiliaryState
                            //                    previousAuxiliaryState.outgoingTransitions.add(connect);
                            //                    connect.setTrigger(triggerExpression.copy);
                            //                    if (delay > transition.delay) {
                            //
                            //                        // Connect last state
                            //                        val auxiliaryEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                        auxiliaryEmission.setValuedObject(auxiliaryValuedObject);
                            //                        connect.addEmission(auxiliaryEmission);
                            //                    }
                            //
                            //                    // Make state final (just in case, because there could be
                            //                    // a normal termination outgoing)
                            //                    if (existsTermination) {
                            //                        auxiliaryState.setFinal(true);
                            //                    }
                            //                }
                            //            }
                            //
                            //            // Modify original trigger
                            //            transition.setDelay(0);
                            //            val auxiliaryTrigger = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //            auxiliaryTrigger.setValuedObject(auxiliaryValuedObject);
                            //            transition.setTrigger(auxiliaryTrigger);
                            //        }
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                   C O U N T   D E L A Y S                           --
                            //    //-------------------------------------------------------------------------
                            //    // @requires: auxiliary (host) variables
                            //     
                            //    // Transforming Count Delays entry function.
                            //    def Region transformCountDelay (Region rootRegion) {
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = CloningExtensions::clone(rootRegion) as Region;
                            //
                            //        var targetTransitions = targetRootRegion.eAllContents().toIterable().filter(typeof(Transition)).toList();
                            //
                            //        // For every transition in the SyncChart do the transformation
                            //        // Iterate over a copy of the list  
                            //        for(targetTransition : targetTransitions) {
                            //            
                            //            targetTransition.transformCountDelay(targetRootRegion);
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //         
                            //     // This will encode count delays in transitions and insert additional counting
                            //     // host code variables plus modifying the trigger of the count delayed transition
                            //     // to be immediate and guarded by a host code expression (with the specific
                            //    // number of ticks).
                            //     def void transformCountDelay(Transition transition, Region targetRootRegion) {
                            //          if (transition.delay > 1) {
                            //               // auxiliary valuedObject
                            //               val auxiliaryVariable = KExpressionsFactory::eINSTANCE.createVariable;
                            //               val auxiliaryVariableName = "countDelay" + transition.hashCode + "";
                            //               auxiliaryVariable.setName(auxiliaryVariableName);
                            //               auxiliaryVariable.setType(ValueType::INT);
                            //               auxiliaryVariable.setInitialValue("0");
                            //
                            //               // add auxiliaryVariable to first (and only) root region state SCCharts main interface
                            //                 targetRootRegion.states.get(0).variables.add(auxiliaryVariable);
                            //                 
                            //               // add self-loop transition, counting up the variable
                            //               val selfLoop = SCChartsFactory::eINSTANCE.createTransition();
                            //               val state = transition.sourceState;
                            //               selfLoop.setTargetState(state);
                            //               selfLoop.setPriority(1);
                            //               val selfLoopEffect = SCChartsFactory::eINSTANCE.createTextEffect;
                            //               selfLoopEffect.setCode(auxiliaryVariableName + "++");
                            //               selfLoop.addEmission(selfLoopEffect);
                            //               selfLoop.setTrigger(transition.trigger);
                            //               state.outgoingTransitions.add(selfLoop);
                            //
                            //               // calculate a new terminating expression, based on the auxiliary variable
                            //               val terminatingExpression = KExpressionsFactory::eINSTANCE.createTextExpression;
                            //               terminatingExpression.setCode(auxiliaryVariableName + " >= " + transition.delay);
                            //               transition.setTrigger(terminatingExpression);
                            //               
                            //               // disable old delay, set it to be immediate
                            //               transition.setDelay(1);
                            //               transition.setImmediate(true);
                            //               
                            //            // reset the variable for all incoming transition
                            //            val resetEffect = SCChartsFactory::eINSTANCE.createTextEffect;
                            //            resetEffect.setCode(auxiliaryVariableName + "= 0");
                            //            for (incomingTransition : state.incomingTransitions) {
                            //                // Add reset text effect of incoming transition
                            //                transition.addEmission(resetEffect);
                            //            }
                            //               
                            //          }
                            //     }
                            //    //-------------------------------------------------------------------------
                            //    //--                          S U S P E N D                              --
                            //    //-------------------------------------------------------------------------
                            //    //@requires: during
                            //    //@run-before: entry (because these are considered here)
                            //    // For a suspend statement of state S create a new top-level region
                            //    // with two states (NotSuspended (initial) and Suspended). Connect them
                            //    // with the suspension trigger.
                            //    // If the trigger is immediate, then connect them immediately and have
                            //    // the transition back be non-immediate. If it is non immediate then
                            //    // have the transition back be immediate.
                            //    // Create an immediate during action of the Suspended state that emits
                            //    // an auxiliaryDisableValuedObject that is added to all outgoing transitions
                            //    // (within the disabledExpression) 
                            //    // Transforming Suspends.
                            //    def Region transformSuspend(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        var targetRootRegion = rootRegion.copy;
                            //
                            //        // For every state in the SyncChart do the transformation
                            //        // Iterate over a copy of the list  
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //
                            //            targetState.transformSuspend(targetRootRegion);
                            //        }
                            //
                            //        // Now delete all suspends
                            //        for (targetState : targetRootRegion.getAllContainedStates.filter(e|e.suspendActions.size > 0)) {
                            //            for (suspendAction : targetState.suspendActions.immutableCopy) {
                            //                targetState.localActions.remove(suspendAction)
                            //            }
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //
                            //    // Tells whether a state is a macro state
                            //    def boolean isHierarchical(State state) {
                            //        (state.regions != null && state.regions.size > 0);
                            //    }
                            //
                            //    // Build a new expression that disables the inExpression if the disabledWhenExpression
                            //    // is enabled. It optimizes not(not(x)) = x.
                            //    def Expression buildDisabledExpression(Expression inExpression, Expression disabledWhenExpression) {
                            //        val andAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //        andAuxiliaryTrigger.setOperator(OperatorType::AND);
                            //        val notAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //        notAuxiliaryTrigger.setOperator(OperatorType::NOT);
                            //        var Expression auxiliaryTrigger;
                            //
                            //        // Add a copy because we want this trigger to be used in possibly several
                            //        // outgoing transitions
                            //        if ((disabledWhenExpression instanceof OperatorExpression) &&
                            //            ((disabledWhenExpression as OperatorExpression).operator == OperatorType::NOT)) {
                            //
                            //            // Optimize not(not(x)) = x
                            //            auxiliaryTrigger = ((disabledWhenExpression as OperatorExpression).subExpressions.get(0)
                            //                            as Expression).
                            //                copy;
                            //        } else {
                            //
                            //            // Default case
                            //            notAuxiliaryTrigger.add(disabledWhenExpression.copy);
                            //            auxiliaryTrigger = notAuxiliaryTrigger;
                            //        }
                            //        if (inExpression != null) {
                            //
                            //            // There is an outgoing transition trigger  
                            //            andAuxiliaryTrigger.add(inExpression);
                            //            andAuxiliaryTrigger.add(auxiliaryTrigger);
                            //            return andAuxiliaryTrigger;
                            //        } else {
                            //
                            //            // The simple case, there is NO outgoing transition trigger yet
                            //            return auxiliaryTrigger;
                            //        }
                            //    }
                            //
                            //    // Encode suspensions by traversing all states and get their
                            //    // hierarchical suspension trigger (if any).
                            //    // In case there is such a trigger, to all outgoing transitions, add 
                            //    // an "(<condition>) && !trigger" to disable ALL these transitions
                            //    // if the suspension trigger is enabled.
                            //    def void transformSuspend(State state, Region targetRootRegion) {
                            //
                            //        for (suspension : state.suspendActions) {
                            //            val suspendTrigger = suspension.trigger;
                            //            val immediateSuspension = suspension.isImmediate;
                            //            val notSuspendTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //            notSuspendTrigger.setOperator(OperatorType::NOT);
                            //            notSuspendTrigger.add(suspendTrigger.copy);
                            //
                            //            // Add SET and RESET valuedObject valuedObject flag 
                            //            val disabledValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            disabledValuedObject.setName("disabled" + state.id);
                            //            disabledValuedObject.setInput(false);
                            //            disabledValuedObject.setOutput(false);
                            //            disabledValuedObject.setType(ValueType::PURE);
                            //            targetRootRegion.rootState.valuedObjects.add(disabledValuedObject);
                            //
                            //            // Add a NonSuspended and Suspended state
                            //            val runningState = SCChartsFactory::eINSTANCE.createState();
                            //            runningState.setId(state.id("NonSuspended"));
                            //            runningState.setLabel(state.id + "Running");
                            //            runningState.setInitial(true);
                            //            val disabledState = SCChartsFactory::eINSTANCE.createState();
                            //            disabledState.setId(state.id("Suspended"));
                            //            disabledState.setLabel(state.id + "Disabled");
                            //
                            //            // Add during action that emits the disable valuedObject 
                            //            val immediateDuringAction = disabledState.createImmediateDuringAction
                            //            immediateDuringAction.setImmediate(true);
                            //            val auxiliaryEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //            auxiliaryEmission.setValuedObject(disabledValuedObject);
                            //            immediateDuringAction.addEmission(auxiliaryEmission);
                            //
                            //            // Create the body of the intermediate state - containing the entry actions
                            //            // as during actions.
                            //            val actionState = SCChartsFactory::eINSTANCE.createState();
                            //            actionState.setId(state.id("Awake"));
                            //            actionState.setLabel("Awake " + state.label);
                            //
                            //            // For every entry action: Create a region
                            //            for (entryAction : state.entryActions) {
                            //                val entryActionCopy = entryAction.copy;
                            //                entryActionCopy.setImmediate(true);
                            //                actionState.localActions.add(entryActionCopy);
                            //            }
                            //
                            //            // Connect Suspended and NonSuspended States with transitions (s.a. for a more detailed explanation)
                            //            val disabled2actionTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            disabled2actionTransition.setTargetState(actionState);
                            //            disabled2actionTransition.setTrigger(notSuspendTrigger.copy);
                            //            disabled2actionTransition.setImmediate(!immediateSuspension);
                            //            disabled2actionTransition.setPriority(1);
                            //            disabledState.outgoingTransitions.add(disabled2actionTransition);
                            //
                            //            // Do not emit the disableValuedObject when the suspend trigger is not true any more!
                            //            disabled2actionTransition.setType(TransitionType::STRONGABORT);
                            //            val action2runningTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            action2runningTransition.setTargetState(runningState);
                            //            action2runningTransition.setLabel("#");
                            //            action2runningTransition.setImmediate(true);
                            //            action2runningTransition.setPriority(1);
                            //            actionState.outgoingTransitions.add(action2runningTransition);
                            //            val running2disabledTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            running2disabledTransition.setTargetState(disabledState);
                            //            running2disabledTransition.setImmediate(immediateSuspension);
                            //            running2disabledTransition.setTrigger(suspendTrigger.copy);
                            //            running2disabledTransition.setPriority(1);
                            //            runningState.outgoingTransitions.add(running2disabledTransition);
                            //
                            //            // Create a region with two states running and disabled and the intermediate entry-action-macro-state
                            //            val suspendActionRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //            suspendActionRegion.setId(state.id("SuspendActionRegion"));
                            //            suspendActionRegion.states.add(actionState);
                            //            suspendActionRegion.states.add(runningState);
                            //            suspendActionRegion.states.add(disabledState);
                            //            targetRootRegion.states.get(0).regions.add(suspendActionRegion);
                            //
                            //            // Add disabled valuedObject  to ALL hierarchically lower (immediate) transitions
                            //            // that appear INSIDE the considered state (in its regions)
                            //            var List<Transition> consideredTransitions = <Transition>newLinkedList;
                            //            for (region : state.regions) {
                            //                if (immediateSuspension) {
                            //
                            //                    // consider ALL transitions (also immediate ones)
                            //                    consideredTransitions.addAll(region.eAllContents().filter(typeof(Transition)).toList());
                            //                } else {
                            //
                            //                    // consider only NON-immediate transitions
                            //                    consideredTransitions.addAll(
                            //                        region.eAllContents().filter(typeof(Transition)).filter(e|!e.isImmediate).toList());
                            //                }
                            //            }
                            //
                            //            for (consideredTransition : ImmutableList::copyOf(consideredTransitions)) {
                            //                val disabledValuedObjectRef = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                disabledValuedObjectRef.setValuedObject(disabledValuedObject);
                            //                val disabledExpression = buildDisabledExpression(consideredTransition.trigger,
                            //                    disabledValuedObjectRef);
                            //                consideredTransition.setTrigger(disabledExpression);
                            //            }
                            //        } // if any suspension there
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                        H I S T O R Y                                --
                            //    //-------------------------------------------------------------------------
                            //    // @requires: suspend
                            //    // Transforming History. This is using the concept of suspend so it must
                            //    // be followed by resolving suspension
                            //    def Region transformHistory(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // For every state in the SyncChart do the transformation
                            //        // Iterate over a copy of the list
                            //        // The following can also be written functional:
                            //        //        ImmutableList::copyOf(targetStates).forEach[
                            //        //             it.transformHistory(targetRootRegion);
                            //        //        ]
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //
                            //            targetState.transformHistory(targetRootRegion);
                            //        }
                            //        
                            //        targetRootRegion;
                            //    }
                            //
                            //
                            //    // Build a new expression that disables the inExpression if the disabledWhenExpression
                            //    // is enabled. It optimizes not(not(x)) = x.
                            //    def Expression buildDisabledExpression(Expression inExpression, Expression disabledWhenExpression) {
                            //        val andAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //        andAuxiliaryTrigger.setOperator(OperatorType::AND);
                            //        val notAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //        notAuxiliaryTrigger.setOperator(OperatorType::NOT);
                            //        var Expression auxiliaryTrigger;
                            //
                            //        // Add a copy because we want this trigger to be used in possibly several
                            //        // outgoing transitions
                            //        if ((disabledWhenExpression instanceof OperatorExpression) &&
                            //            ((disabledWhenExpression as OperatorExpression).operator == OperatorType::NOT)) {
                            //
                            //            // Optimize not(not(x)) = x
                            //            auxiliaryTrigger = ((disabledWhenExpression as OperatorExpression).subExpressions.get(0)
                            //                            as Expression).
                            //                copy;
                            //        } else {
                            //
                            //            // Default case
                            //            notAuxiliaryTrigger.add(disabledWhenExpression.copy);
                            //            auxiliaryTrigger = notAuxiliaryTrigger;
                            //        }
                            //        if (inExpression != null) {
                            //
                            //            // There is an outgoing transition trigger  
                            //            andAuxiliaryTrigger.add(inExpression);
                            //            andAuxiliaryTrigger.add(auxiliaryTrigger);
                            //            return andAuxiliaryTrigger;
                            //        } else {
                            //
                            //            // The simple case, there is NO outgoing transition trigger yet
                            //            return auxiliaryTrigger;
                            //        }
                            //    }
                            //
                            //
                            //    // Traverse all states and transform macro states that have connecting
                            //    // (incoming) history transitions.    
                            //    def void transformHistory(State state, Region targetRootRegion) {
                            //        val historyTransitions = ImmutableList::copyOf(state.incomingTransitions.filter(e|e.isHistory));
                            //        val nonHistoryTransitions = ImmutableList::copyOf(state.incomingTransitions.filter(e|!e.isHistory));
                            //        val allTransitions = ImmutableList::copyOf(state.incomingTransitions);
                            //
                            //        if (historyTransitions != null && historyTransitions.size > 0 && state.regions != null && state.regions.size > 0) {
                            //
                            //            // Put the inside of the state (all inner regions) into
                            //            // a NEW state of a NEW region in parallel to the one before
                            //            val auxiliaryRegion = SCChartsFactory::eINSTANCE.createRegion()
                            //            val auxiliaryState = SCChartsFactory::eINSTANCE.createState();
                            //            auxiliaryState.setId(state.id + "History");
                            //            auxiliaryState.setLabel(state.id + "History");
                            //            auxiliaryState.setInitial(true);
                            //
                            //            // Move local valuedObject declaration to auxiliary state (test 139)
                            //            auxiliaryState.valuedObjects.addAll(state.valuedObjects);
                            //
                            //            // Move all regions to new auxiliary State
                            //            for (region : ImmutableList::copyOf(state.regions)) {
                            //                auxiliaryState.regions.add(region)
                            //            }
                            //            state.regions.removeAll(auxiliaryState.regions);
                            //
                            //            // Auxiliary state gets suspended by NOT auxiliary valuedObject
                            //            val auxiliarySuspendValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            val auxiliarySuspendValuedObjectUID = state.id + "Execute";
                            //            auxiliarySuspendValuedObject.setName(auxiliarySuspendValuedObjectUID);
                            //            auxiliarySuspendValuedObject.setInput(false);
                            //            auxiliarySuspendValuedObject.setOutput(false);
                            //            auxiliarySuspendValuedObject.setType(ValueType::PURE);
                            //
                            //            // Add auxiliaryValuedObject to first (and only) root region state SCCharts main interface
                            //            targetRootRegion.rootState.valuedObjects.add(auxiliarySuspendValuedObject);
                            //
                            //            var Expression suspensionTrigger;
                            //            val notAuxiliaryTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //            notAuxiliaryTrigger.setOperator(OperatorType::NOT);
                            //            val auxiliaryValuedObjectRef = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //            auxiliaryValuedObjectRef.setValuedObject(auxiliarySuspendValuedObject);
                            //            notAuxiliaryTrigger.add(auxiliaryValuedObjectRef);
                            //            if (!state.suspendActions.nullOrEmpty) {
                            //
                            //                // If there already is a suspension trigger than combine it with OR
                            //                val suspensionTrigger2 = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                suspensionTrigger2.setOperator(OperatorType::OR);
                            //                suspensionTrigger2.add(notAuxiliaryTrigger);
                            //                for (suspendAction : state.suspendActions) {
                            //                    suspensionTrigger2.add(suspendAction.trigger);
                            //                }
                            //                suspensionTrigger = suspensionTrigger2.trim;
                            //            } else {
                            //
                            //                // If there is not already a suspension trigger we use the simpler case
                            //                suspensionTrigger = notAuxiliaryTrigger;
                            //            }
                            //
                            //            // Add a dummy suspension action
                            //            val suspensionAction = auxiliaryState.createSuspendAction
                            //            suspensionAction.setTrigger(suspensionTrigger);
                            //
                            //            // Add the NEW state to the NEW region and add the NEW region in parallel 
                            //            auxiliaryRegion.states.add(auxiliaryState);
                            //            state.parentRegion.parentState.regions.add(auxiliaryRegion);
                            //
                            //            // For all history transitions now erase the history marker
                            //            for (historyTransition : historyTransitions) {
                            //                historyTransition.setHistory(HistoryType::RESET);
                            //            }
                            //
                            //            // Add a self loop to the original state that emits the auxiliary valuedObject
                            //            // forcing the internals NOT to suspend
                            //            val selfLoop = SCChartsFactory::eINSTANCE.createTransition();
                            //            selfLoop.setTargetState(state);
                            //            selfLoop.setPriority(state.outgoingTransitions.size + 1);
                            //            val auxiliaryEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //            auxiliaryEmission.setValuedObject(auxiliarySuspendValuedObject);
                            //
                            //            //selfLoop.addEmission(auxiliaryEmission);
                            //            state.outgoingTransitions.add(selfLoop);
                            //
                            //            // Add auxiliary valuedObject forcing internals NOT to suspend to all
                            //            // outgoing WEAK abort transitions (consider NORMAL termination as weak aborts)
                            //            val weakAbortTransitions = ImmutableList::copyOf(
                            //                state.outgoingTransitions.filter(e|e.type != TransitionType::STRONGABORT));
                            //            for (weakAbortTransition : weakAbortTransitions) {
                            //                val auxiliaryEmission2 = SCChartsFactory::eINSTANCE.createEmission();
                            //                auxiliaryEmission2.setValuedObject(auxiliarySuspendValuedObject);
                            //                weakAbortTransition.addEmission(auxiliaryEmission2);
                            //            }
                            //
                            //            //---
                            //            // Re-entry of a history state: Emit a second auxiliaryEntryValuedObject
                            //            // and wait in all inner simple states with an additional self loop on
                            //            // this valuedObject.  
                            //            // Auxiliary suspend re-entry valuedObject
                            //            val auxiliaryEntryValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            val auxiliaryEntryValuedObjectUID = state.id + "Entry";
                            //            auxiliaryEntryValuedObject.setName(auxiliaryEntryValuedObjectUID);
                            //            auxiliaryEntryValuedObject.setInput(false);
                            //            auxiliaryEntryValuedObject.setOutput(false);
                            //            auxiliaryEntryValuedObject.setType(ValueType::PURE);
                            //
                            //            // Add auxiliaryValuedObject to first (and only) root region state SCCharts main interface
                            //            targetRootRegion.rootState.valuedObjects.add(auxiliaryEntryValuedObject);
                            //
                            //            // For all incoming transitions now add a suspendValuedObject emission (to immediately enable the execution of the body)
                            //            // also for all history transitions (re-entry) add an entryValuedObject emission to (in most times) disabled outgoing transitions that are NOT immediate
                            //            for (incomingTransition : allTransitions) {
                            //                val auxiliarySuspendEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                auxiliarySuspendEmission.setValuedObject(auxiliarySuspendValuedObject);
                            //                incomingTransition.addEmission(auxiliarySuspendEmission);
                            //            }
                            //            for (historyTransition : historyTransitions) {
                            //                val auxiliaryEntryEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                auxiliaryEntryEmission.setValuedObject(auxiliaryEntryValuedObject);
                            //                historyTransition.addEmission(auxiliaryEntryEmission);
                            //            }
                            //
                            //            // ...disabled outgoing transitions that are NOT immediate (s.a.)
                            //            val innerStates = ImmutableList::copyOf(auxiliaryState.eAllContents.filter(typeof(State)));
                            //            for (innerState : innerStates) {
                            //                for (outgoingTransition : ImmutableList::copyOf(innerState.outgoingTransitions.filter(e|!e.isImmediate))) {
                            //                    val auxiliaryEntryValuedObjectRef = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                    auxiliaryEntryValuedObjectRef.setValuedObject(auxiliaryEntryValuedObject);
                            //                    val disabledExpression = buildDisabledExpression(outgoingTransition.trigger,
                            //                        auxiliaryEntryValuedObjectRef);
                            //                    outgoingTransition.setTrigger(disabledExpression);
                            //                }
                            //            }
                            //
                            //            //---
                            //            // For resetting the inner states when entering by a normal transition
                            //            // add a reset valuedObject and emit it when entering.
                            //            // On entering also all local (valued) valuedObjects will be reset automatically.
                            //            val auxiliaryResetValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            val auxiliaryResetValuedObjectUID = state.id + "Reset";
                            //            auxiliaryResetValuedObject.setName(auxiliaryResetValuedObjectUID);
                            //            auxiliaryResetValuedObject.setInput(false);
                            //            auxiliaryResetValuedObject.setOutput(false);
                            //            auxiliaryResetValuedObject.setType(ValueType::PURE);
                            //
                            //            // Add auxiliaryValuedObject to first (and only) root region state SCCharts main interface
                            //            targetRootRegion.rootState.valuedObjects.add(auxiliaryResetValuedObject);
                            //
                            //            // Add a self loop to the NEW state that resets it if auxiliary reset valuedObject is present
                            //            val resetSelfLoop = SCChartsFactory::eINSTANCE.createTransition();
                            //            resetSelfLoop.setTargetState(auxiliaryState);
                            //            resetSelfLoop.setPriority(1);
                            //            resetSelfLoop.setType(TransitionType::STRONGABORT);
                            //            val auxiliaryResetValuedObjectRef = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //            auxiliaryResetValuedObjectRef.setValuedObject(auxiliaryResetValuedObject);
                            //            resetSelfLoop.setTrigger(auxiliaryResetValuedObjectRef);
                            //            auxiliaryState.outgoingTransitions.add(resetSelfLoop);
                            //
                            //            // For all non-history transitions now add a resetValuedObject emission
                            //            for (nonHistoryTransition : nonHistoryTransitions) {
                            //                val auxiliaryResetEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                auxiliaryResetEmission.setValuedObject(auxiliaryResetValuedObject);
                            //                nonHistoryTransition.addEmission(auxiliaryResetEmission);
                            //            }
                            //
                            //        }
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                     D U R I N G       A C T I O N S                 --
                            //    //-------------------------------------------------------------------------
                            //    // Transforming During Actions.
                            //    def Region transformDuring(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // Traverse all states
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformDuring(targetRootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    // Traverse all states and transform macro states that have actions to transform
                            //    def void transformDuring(State state, Region targetRootRegion) {
                            //
                            //        // DURING ACTIONS : 
                            //        // For each action create a separate region in the state. 
                            //        // Put the action into an transition within the macro state.
                            //        // Add a loop back to the initial state of the added region.
                            //        // In case the during action is immediate, the looping transition is non-immediate.
                            //        // In case the during action is non-immediate, the looping transition is immediate.
                            //        if (state.duringActions != null && state.duringActions.size > 0) {
                            //
                            //            // Create the body of the dummy state - containing the during action
                            //            // For every during action: Create a region
                            //            for (innerAction : state.duringActions) {
                            //                val dummyInternalState1 = SCChartsFactory::eINSTANCE.createState();
                            //                val dummyInternalState2 = SCChartsFactory::eINSTANCE.createState();
                            //                dummyInternalState1.setId(state.id("During1Internal"));
                            //                dummyInternalState1.setLabel("i");
                            //                dummyInternalState1.setInitial(true);
                            //                dummyInternalState2.setId(state.id("During2Internal"));
                            //                dummyInternalState2.setLabel("f");
                            //
                            //                // Add action dummyTransition
                            //                val dummyInternalTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                dummyInternalTransition.setTargetState(dummyInternalState2);
                            //                dummyInternalTransition.setPriority(1);
                            //                dummyInternalTransition.setDelay(innerAction.delay);
                            //                dummyInternalTransition.setImmediate(innerAction.isImmediate);
                            //                dummyInternalTransition.setTrigger(innerAction.trigger.copy);
                            //                for (action : innerAction.effects) {
                            //                    dummyInternalTransition.addEffect(action.copy);
                            //                }
                            //                dummyInternalState1.outgoingTransitions.add(dummyInternalTransition);
                            //
                            //                // Add self loop
                            //                val dummyInternalLoopTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                dummyInternalLoopTransition.setTargetState(dummyInternalState1);
                            //                dummyInternalLoopTransition.setPriority(1);
                            //                dummyInternalLoopTransition.setImmediate(!innerAction.isImmediate);
                            //                if (!innerAction.isImmediate) {
                            //                    dummyInternalLoopTransition.setLabel("#");
                            //                }
                            //                dummyInternalLoopTransition.setType(TransitionType::WEAKABORT);
                            //                dummyInternalState2.outgoingTransitions.add(dummyInternalLoopTransition);
                            //
                            //                // Add the region to the state
                            //                val dummyInternalRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //                dummyInternalRegion.setId(innerAction.id("DuringDummyRegion"));
                            //                dummyInternalRegion.states.add(dummyInternalState1);
                            //                dummyInternalRegion.states.add(dummyInternalState2);
                            //                state.regions.add(dummyInternalRegion);
                            //            }
                            //
                            //            // After transforming during actions, erase them
                            //            state.duringActions.clear();
                            //        }
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                     E N T R Y         A C T I O N S                 --
                            //    //-------------------------------------------------------------------------
                            //    // @requires: during actions
                            //    // Transforming Entry and During Actions.
                            //    def Region transformEntry(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // Traverse all states
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformEntry(targetRootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    // Traverse all states and transform macro states that have actions to transform
                            //    def void transformEntry(State state, Region targetRootRegion) {
                            //
                            //        // ENTRY ACTIONS :
                            //        // First Idea: Create a macro state for all incoming transitions. Weak abort the
                            //        // macro state and connect it to the original target. Put the action into an
                            //        // transition within the macro state.
                            //        //
                            //        // Charles Andre: "Immediate abortions may cause instantaneous abortion of the state.
                            //        // In this case the status stays IDLE, the transition is taken, DONE is returned: the
                            //        // state is by-passed. If there is not an immediate strong abortion, then the state is 
                            //        // effectively entered, and the entry actions are performed."
                            //        //
                            //        // This means that entry actions must be immediately-strong-abortable by the original outgoing
                            //        // transitions.
                            //        //
                            //        // We create a new macro state EntrySurround around the original state, and connect the incoming and
                            //        // outgoing transitions to it. Within the new macro state, we then create an initial state
                            //        // with the entry actions as during actions. Then we connect this initial state to the
                            //        // original state with an immediate (true triggered) transition.
                            //        if (state.entryActions != null && state.entryActions.size > 0) {
                            //            val surroundState = SCChartsFactory::eINSTANCE.createState();
                            //            surroundState.setId(state.id("EntrySurround"));
                            //            surroundState.setLabel("EntrySurround " + state.label);
                            //            state.parentRegion.states.add(surroundState);
                            //
                            //            // If the original state is an initial stat then the new surround state must also be
                            //            // marked to be initial
                            //            surroundState.setInitial(state.isInitial);
                            //
                            //            // Move transitions to the new state
                            //            for (transition : ImmutableList::copyOf(state.incomingTransitions)) {
                            //                surroundState.incomingTransitions.add(transition);
                            //            }
                            //            for (transition : ImmutableList::copyOf(state.outgoingTransitions)) {
                            //                surroundState.outgoingTransitions.add(transition);
                            //            }
                            //            state.incomingTransitions.clear();
                            //            state.outgoingTransitions.clear();
                            //
                            //            // Move original state into new state
                            //            val surroundRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //            surroundRegion.setId(state.id("EntrySurroundRegion"));
                            //            surroundState.regions.add(surroundRegion);
                            //            surroundRegion.states.add(state);
                            //
                            //            // Create initial state add it to the surround state
                            //            val initialState = SCChartsFactory::eINSTANCE.createState();
                            //            val initialTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            initialState.setId(state.id("init"));
                            //            initialState.setLabel("init " + state.label);
                            //            initialTransition.setLabel("#");
                            //            initialTransition.setTargetState(state);
                            //            initialTransition.setPriority(1);
                            //            initialTransition.setImmediate(true);
                            //            initialState.setInitial(true);
                            //            state.setInitial(false);
                            //            initialState.outgoingTransitions.add(initialTransition);
                            //            surroundRegion.states.add(initialState);
                            //
                            //            // Create the body of the dummy state - containing the entry action
                            //            // For every entry action: Create a region
                            //            for (entryAction : state.entryActions) {
                            //                val entryActionCopy = entryAction.copy;
                            //                entryActionCopy.setImmediate(true);
                            //
                            //                // Create during actions for the initial state
                            //                initialState.localActions.add(entryActionCopy);
                            //            }
                            //
                            //            // After transforming entry actions, erase them
                            //            state.entryActions.clear();
                            //        }
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                      E X I T       A C T I O N S                    --
                            //    //-------------------------------------------------------------------------
                            //    // @requires: entry actions
                            //    // @requires: during actions
                            //    // @requires: suspend
                            //    // @requires: valued valuedObjects
                            //    // Helper function to gather all hierarchically higher outgoing transitions
                            //    // for an inner state.
                            //    def List<Transition> hierarchicallyHigherOutgoingTransitions(State state) {
                            //        val List<Transition> returnTransitions = <Transition>newLinkedList;
                            //
                            //        for (transition : state.outgoingTransitions) {
                            //            returnTransitions.add(transition);
                            //        }
                            //
                            //        if (state.parentRegion != null) {
                            //            if (state.parentRegion.parentState != null) {
                            //                val transitionListFromAbove = state.parentRegion.parentState.hierarchicallyHigherOutgoingTransitions;
                            //                returnTransitions.addAll(transitionListFromAbove);
                            //            }
                            //        }
                            //
                            //        return returnTransitions;
                            //    }
                            //
                            //    // Transforming Exit Actions. 
                            //    def Region transformExit(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // Traverse all states
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformExit(targetRootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    // For a state, have a look at all outgoing transition weak abort triggers and collect them
                            //    // OR them together and do this hierarchically to the outside.        
                            //    def Expression getDisjunctionOfAllHierachicallyOutgoingWeakAborts(State state) {
                            //        val returnExpression = KExpressionsFactory::eINSTANCE.createOperatorExpression();
                            //        val expressionList = state.getDisjunctionOfAllHierachicallyOutgoingWeakAbortsHelper;
                            //        returnExpression.setOperator(OperatorType::OR);
                            //        if (expressionList.size == 0) {
                            //            return null;
                            //        } else if (expressionList.size == 1) {
                            //            return expressionList.head;
                            //        } else {
                            //            for (expression : expressionList) {
                            //                if (expression != null) {
                            //                    returnExpression.add(expression);
                            //                }
                            //            }
                            //            return returnExpression;
                            //        }
                            //    }
                            //
                            //    def List<Expression> getDisjunctionOfAllHierachicallyOutgoingWeakAbortsHelper(State state) {
                            //        var List<Expression> expressionList = <Expression>newLinkedList;
                            //        val outgoingTransitions = state.outgoingTransitions.filter(e|e.type == TransitionType::WEAKABORT);
                            //        for (outgoingTransition : outgoingTransitions) {
                            //            expressionList.add(outgoingTransition.trigger.copy);
                            //        }
                            //
                            //        // collect from higher hierarchy level
                            //        if (state.parentRegion != null && state.parentRegion.parentState != null) {
                            //            expressionList.addAll(
                            //                state.parentRegion.parentState.getDisjunctionOfAllHierachicallyOutgoingWeakAbortsHelper);
                            //        }
                            //       expressionList;
                            //    }
                            //
                            //    // Tries to follow immediate chains of transitions in order to exclude possible self loops
                            //    def boolean isPossibleSelfLoop(Transition transition) {
                            //        return isPossibleSelfLoop(transition, transition.sourceState);
                            //    }
                            //
                            //    def boolean isPossibleSelfLoop(Transition transition, State startState) {
                            //        var boolean isLoop = false;
                            //        for (Transition nextTransition : transition.targetState.outgoingTransitions.filter(e|e.isImmediate)) {
                            //            isLoop = isLoop || nextTransition.isPossibleSelfLoop(startState);
                            //        }
                            //        isLoop = isLoop || (transition.targetState == startState);
                            //        return isLoop;
                            //    }
                            //
                            //    // Traverse all states and transform macro states that have actions to transform
                            //    def void transformExit(State state, Region targetRootRegion) {
                            //
                            //        // EXIT ACTIONS : For every state with exit actions create a new top-level region and
                            //        // create SET and RESET valuedObjects. This region contains a set and reset (inital) state
                            //        // connected from reset to set with an intermediate macro state containing all the
                            //        // exit actions and labeled with SET and not RESET. Another arc from set to reset labeled
                            //        // with RESET. A self-arc from reset labeled with SET and RESET.
                            //        // Every transition considered to be outgoing in any way emits the SET valuedObject.
                            //        // The entry action emits the RESET valuedObject.
                            //        // The state in question must have an immediate during action, resetting (emit RESET), BUT
                            //        // important is that this is triggered and the trigger is excluded hierarchically by ALL
                            //        // possibly outgoing transitions to the outside that are weak (in this case we do not want
                            //        // to reset because we know we leave the state and want to remember the exiting 
                            //        // (and NOT reset!!!) 
                            //        //
                            //        // DEPRECATED IDEA (has drawbacks, see below)
                            //        // Create a macro state for all outgoing non-preempting(!) transitions. 
                            //        // Weak abort the macro state and connect it to the original target. Put the action into an
                            //        // transition within the macro state.
                            //        //
                            //        // From Charles Andre, Semantics of SCCharts: Note that strong and weak abortions have the
                            //        // same effect on exit actions. This explains why exit actions are primitive constructs: they 
                            //        // cannot be expressed by a combination of the already studied constructs.
                            //        //
                            //        // Chris Motika: In other words, if an exit action is inside some hierarchical states and an
                            //        // outer state is left by a strong preemption, surprisingly the exit action is still
                            //        // executed.
                            //        // To resolve this, we need to figure out ALL hierarchically outgoing strong preemption transition
                            //        // and add the action also there. For weak preemption transitions we do not need to do this 
                            //        // because the action from inside is allowed to take place as the "last wish".
                            //        //
                            //        // FLAW I: When re-entering one has to give precedence to reset but when exiting one wants
                            //        // to giv precedence to set. => Valued valuedObjects are too limited, more preciseley the combine
                            //        // function of valued valuedObjects. Better use a two state representation.
                            //        //
                            //        // FLAW II: When transforming a history transition with suspend, when re-entering,
                            //        // entry actions are currently not executed again (propably they should?! FIXME: find out about
                            //        // entry actions & history transitions). If using sustain actions this problem is solved BUT another
                            //        // one arises. In the same state there will be a set and reset. Giving precedence to reset this
                            //        // will result in calling the exit action AGAIN if the state is left outside.
                            //        // 
                            //        if (state.exitActions != null && state.exitActions.size > 0) {
                            //            var List<Transition> consideredTransitions = <Transition>newLinkedList;
                            //            consideredTransitions.addAll(state.hierarchicallyHigherOutgoingTransitions);
                            //
                            //            // Add SET and SETI and RESETI and RESETN valuedObject valuedObject flag 
                            //            val setValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            setValuedObject.setName("Set" + state.id);
                            //            setValuedObject.setInput(false);
                            //            setValuedObject.setOutput(false);
                            //            setValuedObject.setType(ValueType::PURE);
                            //            targetRootRegion.rootState.valuedObjects.add(setValuedObject);
                            //
                            //            // This valuedObject is produced by ALL immediate outputs (also hierarchically higher)
                            //            // it is able to trigger an immediate transition back from reset to set (when entering reset)
                            //            // set ---> reset -#-> set
                            //            val setIValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            setIValuedObject.setName("SetI" + state.id);
                            //            setIValuedObject.setInput(false);
                            //            setIValuedObject.setOutput(false);
                            //            setIValuedObject.setType(ValueType::PURE);
                            //            targetRootRegion.rootState.valuedObjects.add(setIValuedObject);
                            //
                            //            val resetIValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            resetIValuedObject.setName("ResetI" + state.id);
                            //            resetIValuedObject.setInput(false);
                            //            resetIValuedObject.setOutput(false);
                            //            resetIValuedObject.setType(ValueType::PURE);
                            //            targetRootRegion.rootState.valuedObjects.add(resetIValuedObject);
                            //
                            //            val resetNValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            resetNValuedObject.setName("ResetN" + state.id);
                            //            resetNValuedObject.setInput(false);
                            //            resetNValuedObject.setOutput(false);
                            //            resetNValuedObject.setType(ValueType::PURE);
                            //            targetRootRegion.rootState.valuedObjects.add(resetNValuedObject);
                            //
                            //            // Add a Set and Reset state
                            //            val resetState = SCChartsFactory::eINSTANCE.createState();
                            //            resetState.setId(state.id("ExitReset"));
                            //            resetState.setLabel("r");
                            //            val setState = SCChartsFactory::eINSTANCE.createState();
                            //            setState.setId(state.id("ExitSet"));
                            //
                            //            // The Set state has to be the initial state
                            //            setState.setInitial(true);
                            //            setState.setLabel("s");
                            //
                            //            // Connect Set and Reset States with transitions (s.a. for a more detailed explanation)
                            //            val reset2setTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            reset2setTransition.setTargetState(setState);
                            //            resetState.outgoingTransitions.add(reset2setTransition);
                            //            val reset2setITransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            reset2setITransition.setTargetState(setState);
                            //            resetState.outgoingTransitions.add(reset2setITransition);
                            //            val set2resetTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            set2resetTransition.setTargetState(resetState);
                            //            setState.outgoingTransitions.add(set2resetTransition);
                            //            val set2setTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            set2setTransition.setTargetState(setState);
                            //            setState.outgoingTransitions.add(set2setTransition);
                            //
                            //            // Build triggers for transitions 
                            //            // (A) set -- ResetI --> reset
                            //            // (B) set -- Set and ResetI and ResetN --> set (means started in C, ending in C by outputting O)
                            //            // (C) reset -- Set --> set (means starting NOT in C, ending in C by outputting O)
                            //            // (D) reset -- #SetI --> set (possibly a chain coming from inside set and ending in inside set over transient reset)
                            //            val setValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            setValuedObjectReference.setValuedObject(setValuedObject);
                            //            val setIValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            setIValuedObjectReference.setValuedObject(setIValuedObject);
                            //            val resetIValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            resetIValuedObjectReference.setValuedObject(resetIValuedObject);
                            //            val resetNValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            resetNValuedObjectReference.setValuedObject(resetNValuedObject);
                            //            val andExpression = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //            andExpression.setOperator(OperatorType::AND);
                            //
                            //            // (A)
                            //            set2resetTransition.setTrigger(resetIValuedObjectReference.copy);
                            //            set2resetTransition.setPriority(2); // Set a LOWER prio than set to set (B)
                            //
                            //            // (B)
                            //            val set2setTrigger = andExpression.copy;
                            //            val set2setTrigger2 = andExpression.copy;
                            //            set2setTrigger2.add(setValuedObjectReference.copy);
                            //            set2setTrigger2.add(resetIValuedObjectReference.copy);
                            //            set2setTrigger.add(set2setTrigger2);
                            //            set2setTrigger.add(resetNValuedObjectReference.copy);
                            //            set2setTransition.setTrigger(set2setTrigger);
                            //            set2setTransition.setPriority(1); // Set a HIGHER prio than set to reset (A)
                            //
                            //            // (C)
                            //            reset2setTransition.setTrigger(setValuedObjectReference.copy);
                            //            reset2setTransition.setPriority(2);
                            //
                            //            // (D)
                            //            reset2setITransition.setTrigger(setIValuedObjectReference.copy);
                            //            reset2setITransition.setImmediate(true);
                            //            reset2setITransition.setPriority(1);
                            //
                            //            // Create a region with two states set and reset 
                            //            val exitActionRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //            exitActionRegion.setId(state.id("ExitActionRegion"));
                            //            exitActionRegion.states.add(resetState);
                            //            exitActionRegion.states.add(setState);
                            //            targetRootRegion.states.get(0).regions.add(exitActionRegion);
                            //
                            //            // Create conditioned sustain and actions for Set state containing the exit actions
                            //            // For every exit action: Create a during and entry action for Set state
                            //            // the entry action is triggered by Set
                            //            // the during action is triggered by Set and ResetI and ResetN
                            //            for (exitAction : state.exitActions) {
                            //                val entryAction = exitAction.copy;
                            //                entryAction.setImmediate(true);
                            //                var entryActionTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                entryActionTrigger.setOperator(OperatorType::AND);
                            //                entryActionTrigger.add(setValuedObjectReference.copy); // (C)
                            //                if (entryAction.trigger != null) {
                            //                    entryActionTrigger.add(entryAction.trigger);
                            //                    entryAction.setTrigger(entryActionTrigger);
                            //                } else {
                            //                    entryAction.setTrigger(setValuedObjectReference.copy);
                            //                }
                            //                setState.createEntryAction.applyAttributes(entryAction);
                            //
                            //                val duringAction = exitAction.copy;
                            //                duringAction.setImmediate(true);
                            //                var duringActionTrigger = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                duringActionTrigger.setOperator(OperatorType::AND);
                            //                duringActionTrigger.add(set2setTrigger.copy); // (B)
                            //                if (duringAction.trigger != null) {
                            //                    duringActionTrigger.add(duringAction.trigger);
                            //                    duringAction.setTrigger(duringActionTrigger);
                            //                } else {
                            //                    duringAction.setTrigger(set2setTrigger.copy);
                            //                }
                            //                setState.createDuringAction.applyAttributes(duringAction);
                            //            }
                            //
                            //            // CORNER CASE: 78 & 79 (also 80)
                            //            // Execute ExitAction if permanent PREEMPTIVE reset is present.
                            //            // DO NOT execute when was left before.
                            //            val strongAbortSelfLoopPresent = consideredTransitions.filter(
                            //                e|e.type == TransitionType::STRONGABORT && e.isPossibleSelfLoop);
                            //            val cornerCaseTransition = consideredTransitions.filter(e|!e.isPossibleSelfLoop);
                            //            if (strongAbortSelfLoopPresent.size > 0) {
                            //
                            //                // Create SetInner valuedObject only for outgoing transitions that are no self loops
                            //                // this includes also possible chains of immediate transitions
                            //                // Optimization: only consider strong aborts, because for weak aborts 78 is not a problem!
                            //                val setValuedObjectInner = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //                setValuedObjectInner.setName("SetInner" + state.id);
                            //                setValuedObjectInner.setInput(false);
                            //                setValuedObjectInner.setOutput(false);
                            //                setValuedObjectInner.setType(ValueType::PURE);
                            //                targetRootRegion.rootState.valuedObjects.add(setValuedObjectInner);
                            //                val setValuedObjectInnerReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //                setValuedObjectInnerReference.setValuedObject(setValuedObjectInner);
                            //
                            //                //Create In state and Out state
                            //                val inState = SCChartsFactory::eINSTANCE.createState();
                            //                inState.setId(state.id("ExitIn"));
                            //                inState.setInitial(true);
                            //                inState.setLabel("in");
                            //                val outState = SCChartsFactory::eINSTANCE.createState();
                            //                outState.setId(state.id("ExitOut"));
                            //                outState.setLabel("out");
                            //
                            //                // Connect In and Out states with transitions triggered #SetCC
                            //                val in2outTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                in2outTransition.setTargetState(outState);
                            //                in2outTransition.setImmediate(true);
                            //                inState.outgoingTransitions.add(in2outTransition);
                            //                in2outTransition.setTrigger(setValuedObjectInnerReference.copy);
                            //
                            //                // Create InOut region    
                            //                val setInOutRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //                setInOutRegion.setId(state.id("ExitInOutRegion"));
                            //                setInOutRegion.states.add(inState);
                            //                setInOutRegion.states.add(outState);
                            //                setState.regions.add(setInOutRegion);
                            //
                            //                // Add emission to corner case transitions 
                            //                for (transition : cornerCaseTransition) {
                            //                    val setEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                    setEmission.setValuedObject(setValuedObjectInner);
                            //                    transition.addEmission(setEmission);
                            //                }
                            //
                            //                // Add during action for inState
                            //                val duringIActionResetValuedObjectN = inState.createDuringAction
                            //                val resetNEmission2 = SCChartsFactory::eINSTANCE.createEmission();
                            //                resetNEmission2.setValuedObject(resetNValuedObject);
                            //                duringIActionResetValuedObjectN.addEmission(resetNEmission2);
                            //            } // End corner case
                            //
                            //            // Add a during action that resets the exit action
                            //            // more specifically add an immediate during action for resetI
                            //            //                   and a  normal during action for resetN
                            //            val duringIAction = state.createDuringAction
                            //            duringIAction.setImmediate(true);
                            //            val resetIEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //            resetIEmission.setValuedObject(resetIValuedObject);
                            //            duringIAction.addEmission(resetIEmission);
                            //            val duringNAction = state.createDuringAction
                            //            val resetNEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //            resetNEmission.setValuedObject(resetNValuedObject);
                            //            duringNAction.addEmission(resetNEmission);
                            //
                            //            // André says: Do not execute exitActions if the state is bypassed (by an enabled immediate strong abort)
                            //            // Hence, the following is incorrect.                
                            //            //               // For every incoming transitions add a ResetI emission
                            //            //               // (if the state is an initial state, then add another initial state before and
                            //            //               // connect both with an immediate true triggered transition)
                            //            //               if (state.isInitial) {
                            //            //                   val newInitialState = SCChartsFactory::eINSTANCE.createState();
                            //            //                   newInitialState.setId("initial" + state.hashCode);
                            //            //                   newInitialState.setLabel("i");
                            //            //                   newInitialState.setInitial(true);
                            //            //                   state.setInitial(false);
                            //            //                   state.parentRegion.states.add(newInitialState);
                            //            //                   val immediateTransition =  SCChartsFactory::eINSTANCE.createTransition();
                            //            //                   immediateTransition.setImmediate(true);
                            //            //                   immediateTransition.setLabel("#");
                            //            //                   immediateTransition.setTargetState(state);
                            //            //                   newInitialState.outgoingTransitions.add(immediateTransition);
                            //            //               }
                            //            //               for (incomingTransition : ImmutableList::copyOf(state.incomingTransitions)) {
                            //            //                   incomingTransition.addEmission(resetIEmission.copy);
                            //            //               }
                            //            for (transition : consideredTransitions) {
                            //
                            //                // For every considered transition add an emission of the set valuedObject
                            //                // that will result in executing the exit action if it was not
                            //                // previously executed.
                            //                val setEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                setEmission.setValuedObject(setValuedObject);
                            //                transition.addEmission(setEmission);
                            //            }
                            //
                            //            for (transition : consideredTransitions.filter(e|e.isImmediate)) {
                            //
                            //                // For every considered immediate transition add an emission of the setI valuedObject
                            //                val setIEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                setIEmission.setValuedObject(setIValuedObject);
                            //                transition.addEmission(setIEmission);
                            //            }
                            //
                            //            // After transforming exit actions, erase them
                            //            state.exitActions.clear();
                            //        }
                            //
                            //    }
                            //    //-------------------------------------------------------------------------
                            //    //--                        P R E -  O P E R A T O R                     --
                            //    //-------------------------------------------------------------------------
                            //    // Transforming PRE Operator.
                            //    def Region transformPre(Region rootRegion) {
                            //
                            //        // Clone the complete SCCharts region 
                            //        val targetRootRegion = rootRegion.copy;
                            //
                            //        // Traverse all states
                            //        for (targetState : targetRootRegion.getAllContainedStates) {
                            //            targetState.transformPre(targetRootRegion);
                            //        }
                            //        targetRootRegion;
                            //    }
                            //
                            //    // Return a list of Pre Expressions for an action that references the valuedObject
                            //    def List<OperatorExpression> getPreExpression(Action action, ValuedObject valuedObject) {
                            //        val List<OperatorExpression> returnPreExpressions = <OperatorExpression>newLinkedList;
                            //        val preExpressions = action.eAllContents.filter(typeof(OperatorExpression)).toList().filter(
                            //            e|
                            //                (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                            //                    (e.subExpressions.get(0) instanceof ValuedObjectReference) &&
                            //                    ((e.subExpressions.get(0) as ValuedObjectReference).valuedObject == valuedObject)
                            //        );
                            //        returnPreExpressions.addAll(preExpressions);
                            //        return returnPreExpressions;
                            //    }
                            //
                            //    // Return a list of Pre Expressions for an action that references the value of a valuedObject
                            //    def List<OperatorExpression> getPreValExpression(Action action, ValuedObject valuedObject) {
                            //        val List<OperatorExpression> returnPreValExpressions = <OperatorExpression>newLinkedList;
                            //        val preValExpressions = action.eAllContents.filter(typeof(OperatorExpression)).toList().filter(
                            //            e|
                            //                (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                            //                    (e.subExpressions.get(0) instanceof OperatorExpression) &&
                            //                    ((e.subExpressions.get(0) as OperatorExpression).operator == OperatorType::VAL) &&
                            //                    ((e.subExpressions.get(0) as OperatorExpression).subExpressions.size() == 1) &&
                            //                    ((e.subExpressions.get(0) as OperatorExpression).subExpressions.get(0) instanceof ValuedObjectReference) && (((e.
                            //                        subExpressions.get(0) as OperatorExpression).subExpressions.get(0) as ValuedObjectReference).
                            //                        valuedObject == valuedObject)
                            //        );
                            //        returnPreValExpressions.addAll(preValExpressions);
                            //        return returnPreValExpressions;
                            //    }
                            //
                            //    // Traverse all states that might declare a valuedObject that is used with the PRE operator
                            //    def void transformPre(State state, Region targetRootRegion) {
                            //
                            //        // Filter all valuedObjects and retrieve those that are referenced
                            //        val allActions = state.eAllContents.filter(typeof(Action)).toList();
                            //        val allPreValuedObjects = state.valuedObjects.filter(
                            //            valuedObject|
                            //                allActions.filter(
                            //                    action|
                            //                        action.getPreExpression(valuedObject).size > 0 ||
                            //                            action.getPreValExpression(valuedObject).size > 0).size > 0);
                            //
                            //        for (preValuedObject : ImmutableList::copyOf(allPreValuedObjects)) {
                            //
                            //            // Create PreS / PreV
                            //            val explicitPreValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //            explicitPreValuedObject.setName("Pre" + preValuedObject.name);
                            //            explicitPreValuedObject.setInput(false);
                            //            explicitPreValuedObject.setOutput(false);
                            //            explicitPreValuedObject.setType(preValuedObject.type);
                            //            if (preValuedObject.initialValue != null) {
                            //                explicitPreValuedObject.setInitialValue(preValuedObject.initialValue);
                            //            }
                            //
                            //            // Add to the current state
                            //            state.valuedObjects.add(explicitPreValuedObject);
                            //
                            //            // PreValuedObject and ExplicitPreValuedObject References                
                            //            val explicitPreValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            explicitPreValuedObjectReference.setValuedObject(explicitPreValuedObject);
                            //            val preValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference()
                            //            preValuedObjectReference.setValuedObject(preValuedObject);
                            //
                            //            // Add a Pre and NotPre state
                            //            val preState = SCChartsFactory::eINSTANCE.createState();
                            //            preState.setId(preValuedObject.id("Pre"));
                            //            preState.setLabel("Pre");
                            //            val notPreState = SCChartsFactory::eINSTANCE.createState();
                            //            notPreState.setId(preValuedObject.id("NotPre"));
                            //            notPreState.setInitial(true);
                            //            notPreState.setLabel("NotPre");
                            //
                            //            // Add a region     
                            //            val preRegion = SCChartsFactory::eINSTANCE.createRegion();
                            //            preRegion.setId(preValuedObject.id("PreRegion"));
                            //            preRegion.states.add(preState);
                            //            preRegion.states.add(notPreState);
                            //            state.regions.add(preRegion);
                            //
                            //            // Transitions         
                            //            val notPre2PreTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            notPre2PreTransition.setTargetState(preState);
                            //            notPre2PreTransition.setPriority(1);
                            //            notPreState.outgoingTransitions.add(notPre2PreTransition);
                            //            val pre2NotPreTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //            pre2NotPreTransition.setPriority(2);
                            //            pre2NotPreTransition.setTargetState(notPreState);
                            //            preState.outgoingTransitions.add(pre2NotPreTransition);
                            //
                            //            if (preValuedObject.type == ValueType::PURE) {
                            //
                            //                // Simple ValuedObject Case
                            //                notPre2PreTransition.setTrigger(preValuedObjectReference.copy);
                            //                val explicitPreValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                explicitPreValuedObjectEmission.setValuedObject(explicitPreValuedObject);
                            //                val preSelfTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                preSelfTransition.setTargetState(preState);
                            //                preSelfTransition.setPriority(1);
                            //                preState.outgoingTransitions.add(preSelfTransition);
                            //                preSelfTransition.setTrigger(preValuedObjectReference.copy);
                            //
                            //                // PreValuedObject emission must be added as an inner action
                            //                // to be decoupled from deciding for a specific transition (B is present or B is not present)
                            //                val explicitPreValuedObjectEmissionAction = preState.createDuringAction
                            //                explicitPreValuedObjectEmissionAction.addEmission(explicitPreValuedObjectEmission.copy);
                            //
                            //            //preSelfTransition.addEmission(explicitPreValuedObjectEmission.copy);
                            //            //pre2NotPreTransition.addEmission(explicitPreValuedObjectEmission.copy);
                            //            } else {
                            //
                            //                // Valued ValuedObject Case
                            //                // Additional PreB state
                            //                val preBState = SCChartsFactory::eINSTANCE.createState();
                            //                preBState.setId(preValuedObject.id("PreB"));
                            //                preBState.setLabel("PreB");
                            //                preRegion.states.add(preBState);
                            //
                            //                val preB2PreTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                preB2PreTransition.setTargetState(preState);
                            //                preB2PreTransition.setPriority(1);
                            //                preBState.outgoingTransitions.add(preB2PreTransition);
                            //                val preB2NotPreTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                preB2NotPreTransition.setTargetState(notPreState);
                            //                preB2NotPreTransition.setPriority(2);
                            //                preBState.outgoingTransitions.add(preB2NotPreTransition);
                            //                val pre2PreBTransition = SCChartsFactory::eINSTANCE.createTransition();
                            //                pre2PreBTransition.setTargetState(preBState);
                            //                pre2PreBTransition.setPriority(1);
                            //                preState.outgoingTransitions.add(pre2PreBTransition);
                            //
                            //                // Additional ValuedObjects                    
                            //                val explicitPre1ValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //                explicitPre1ValuedObject.setName("Pre1" + preValuedObject.name);
                            //                explicitPre1ValuedObject.setInput(false);
                            //                explicitPre1ValuedObject.setOutput(false);
                            //                explicitPre1ValuedObject.setType(preValuedObject.type);
                            //                if (preValuedObject.initialValue != null) {
                            //                    explicitPre1ValuedObject.setInitialValue(preValuedObject.initialValue);
                            //                }
                            //                state.valuedObjects.add(explicitPre1ValuedObject);
                            //                val explicitPre2ValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                            //                explicitPre2ValuedObject.setName("Pre2" + preValuedObject.name);
                            //                explicitPre2ValuedObject.setInput(false);
                            //                explicitPre2ValuedObject.setOutput(false);
                            //                explicitPre2ValuedObject.setType(preValuedObject.type);
                            //                if (preValuedObject.initialValue != null) {
                            //                    explicitPre2ValuedObject.setInitialValue(preValuedObject.initialValue);
                            //                }
                            //                state.valuedObjects.add(explicitPre2ValuedObject);
                            //
                            //                // Transition triggers & effects
                            //                val explicitPre1ValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                explicitPre1ValuedObjectReference.setValuedObject(explicitPre1ValuedObject);
                            //                val explicitPre2ValuedObjectReference = KExpressionsFactory::eINSTANCE.createValuedObjectReference
                            //                explicitPre2ValuedObjectReference.setValuedObject(explicitPre2ValuedObject);
                            //
                            //                val valPreExpression = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                valPreExpression.setOperator(OperatorType::VAL);
                            //                valPreExpression.add(preValuedObjectReference.copy);
                            //                val valExplicitPre1Expression = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                valExplicitPre1Expression.setOperator(OperatorType::VAL);
                            //                valExplicitPre1Expression.add(explicitPre1ValuedObjectReference.copy);
                            //                val valExplicitPre2Expression = KExpressionsFactory::eINSTANCE.createOperatorExpression;
                            //                valExplicitPre2Expression.setOperator(OperatorType::VAL);
                            //                valExplicitPre2Expression.add(explicitPre2ValuedObjectReference.copy);
                            //
                            //                val explicitPreValuedObjectEmissionFromPre1 = SCChartsFactory::eINSTANCE.createEmission();
                            //                explicitPreValuedObjectEmissionFromPre1.setValuedObject(explicitPreValuedObject);
                            //                explicitPreValuedObjectEmissionFromPre1.setNewValue(valExplicitPre1Expression.copy);
                            //                val explicitPreValuedObjectEmissionFromPre2 = SCChartsFactory::eINSTANCE.createEmission();
                            //                explicitPreValuedObjectEmissionFromPre2.setValuedObject(explicitPreValuedObject);
                            //                explicitPreValuedObjectEmissionFromPre2.setNewValue(valExplicitPre2Expression.copy);
                            //
                            //                val explicitPre1ValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                explicitPre1ValuedObjectEmission.setValuedObject(explicitPre1ValuedObject);
                            //                explicitPre1ValuedObjectEmission.setNewValue(valPreExpression.copy);
                            //                val explicitPre2ValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                            //                explicitPre2ValuedObjectEmission.setValuedObject(explicitPre2ValuedObject);
                            //                explicitPre2ValuedObjectEmission.setNewValue(valPreExpression.copy);
                            //
                            //                // PreValuedObject emission must be added as an inner action
                            //                // to be decoupled from deciding for a specific transition (B is present or B is not present)
                            //                val explicitPreValuedObjectEmissionFromPre1Action = preState.createDuringAction
                            //                explicitPreValuedObjectEmissionFromPre1Action.addEmission(explicitPreValuedObjectEmissionFromPre1);
                            //                val explicitPreValuedObjectEmissionFromPre2Action = preBState.createDuringAction
                            //                explicitPreValuedObjectEmissionFromPre2Action.addEmission(explicitPreValuedObjectEmissionFromPre2);
                            //
                            //                // Fill transitions
                            //                //                pre2NotPreTransition.addEmission(explicitPreValuedObjectEmissionFromPre1.copy);
                            //                pre2PreBTransition.setTrigger(preValuedObjectReference.copy);
                            //
                            //                //                pre2PreBTransition.addEmission(explicitPreValuedObjectEmissionFromPre1.copy);
                            //                pre2PreBTransition.addEmission(explicitPre2ValuedObjectEmission.copy);
                            //
                            //                preB2PreTransition.setTrigger(preValuedObjectReference.copy);
                            //
                            //                //                preB2PreTransition.addEmission(explicitPreValuedObjectEmissionFromPre2.copy);
                            //                preB2PreTransition.addEmission(explicitPre1ValuedObjectEmission.copy);
                            //
                            //                //                preB2NotPreTransition.addEmission(explicitPreValuedObjectEmissionFromPre2.copy);
                            //                notPre2PreTransition.setTrigger(preValuedObjectReference.copy);
                            //                notPre2PreTransition.addEmission(explicitPre1ValuedObjectEmission.copy);
                            //            }
                            //
                            //            // Replace the ComplexExpression Pre(S) by the ValuedObjectReference PreS in all actions            
                            //            // Replace the ComplexExpression Pre(?S) by the OperatorExpression ?PreS in all actions            
                            //            for (action : allActions) {
                            //                val preExpressions = action.getPreExpression(preValuedObject);
                            //                val preValExpressions = action.getPreValExpression(preValuedObject);
                            //
                            //                for (preExpression : preExpressions) {
                            //                    val container = preExpression.eContainer;
                            //
                            //                    if (container instanceof OperatorExpression) {
                            //
                            //                        // If nested PRE or PRE inside another complex expression
                            //                        (container as OperatorExpression).subExpressions.remove(preExpression);
                            //                        (container as OperatorExpression).add(explicitPreValuedObjectReference.copy);
                            //                    } else if (container instanceof Action) {
                            //
                            //                        // If PRE directly a trigger
                            //                        (container as Action).setTrigger(explicitPreValuedObjectReference.copy)
                            //                    }
                            //                }
                            //
                            //                for (preValExpression : preValExpressions) {
                            //                    val container = preValExpression.eContainer;
                            //
                            //                    if ((!preValExpression.subExpressions.nullOrEmpty) &&
                            //                        preValExpression.subExpressions.get(0) instanceof OperatorExpression &&
                            //                        (preValExpression.subExpressions.get(0) as OperatorExpression).operator == OperatorType::VAL) {
                            //
                            //                        // Transform pre(?V) --> ?PreV
                            //                        val valueExpression = preValExpression.subExpressions.get(0);
                            //                        (valueExpression as OperatorExpression).subExpressions.remove(0);
                            //                        (valueExpression as OperatorExpression).add(explicitPreValuedObjectReference.copy);
                            //                        if (container instanceof Emission) {
                            //                            (container as Emission).setNewValue(valueExpression.copy);
                            //                        } else if (container instanceof OperatorExpression) {
                            //
                            //                            // If nested PRE or PRE inside another complex expression
                            //                            (container as OperatorExpression).subExpressions.remove(preValExpression);
                            //                            (container as OperatorExpression).add(valueExpression.copy);
                            //                        }
                            //                    }
                            //
                            //                }
                            //            }
                            //        }
                            //    }
                            //-------------------------------------------------------------------------
                            //--          A B O R T S   O L D    T R A N S F O R M A T I O N        --
                            //-------------------------------------------------------------------------
                            // -- OLD IMPLEMENTATION --
                            // Traverse all states 
                            def void transformSCCAborts_OLD_IMPLEMENTATION_(State state, Region targetRootRegion) {

                                if (state.hierarchical && state.outgoingTransitions.size() > 0) {

                                    // Remember all outgoing transitions
                                    val originalOutgoingTransitions = ImmutableList::copyOf(state.outgoingTransitions);
                                    val outgoingStrongTransitions = ImmutableList::copyOf(
                                        originalOutgoingTransitions.filter(e|e.type == TransitionType::STRONGABORT));
                                    val outgoingWeakTransitions = ImmutableList::copyOf(
                                        originalOutgoingTransitions.filter(e|e.type == TransitionType::WEAKABORT));
                                    val terminationExists = originalOutgoingTransitions.filter(
                                        e|e.type == TransitionType::TERMINATION).size > 0;

                                    // Remember all regions
                                    val originalRegions = ImmutableList::copyOf(state.regions);

                                    // For a hierarchical state:
                                    // 1. for each existing region, create a new Aborted-auxiliary state
                                    // 2. create a watcher region
                                    // 2. Watcher region
                                    // Add a Pre and NotPre state
                                    val runState = SCChartsFactory::eINSTANCE.createState();
                                    runState.setId(state.id("Run"));
                                    runState.setLabel("Run");
                                    runState.setInitial(true);

                                    //runState.setFinal(true);  // DO NOT SET THE RUN STATE AS FINAL! //
                                    val abortState = SCChartsFactory::eINSTANCE.createState();
                                    abortState.setId(state.id("Abort"));
                                    abortState.setLabel("Abort");
                                    abortState.setFinal(true);
                                    val watcherRegion = SCChartsFactory::eINSTANCE.createRegion();
                                    watcherRegion.setId(state.id("Ctrl"));
                                    watcherRegion.states.add(runState);
                                    watcherRegion.states.add(abortState);
                                    val needWatcherRegion = (originalOutgoingTransitions.filter(
                                        e|e.type != TransitionType::TERMINATION).size > 0);
                                    if (needWatcherRegion) {
                                        state.regions.add(watcherRegion);
                                    }

                                    // Add a conditional node outside of the state and connect it with
                                    // a normal termination transition
                                    val conditionalState = SCChartsFactory::eINSTANCE.createState();
                                    conditionalState.setId(state.id("_Conditional"));
                                    conditionalState.setType(StateType::CONNECTOR);
                                    state.parentRegion.states.add(conditionalState);
                                    val terminationTransition = SCChartsFactory::eINSTANCE.createTransition();
                                    terminationTransition.setTargetState(conditionalState);
                                    terminationTransition.setPriority(1);
                                    terminationTransition.setType(TransitionType::TERMINATION);
                                    state.outgoingTransitions.add(terminationTransition);

                                    // Create complex triggers to be filled with auxiliary valuedObjects (sorted strong or weak)                        
                                    var Expression strongTrigger;
                                    val strongTriggerOperatorExpression = KExpressionsFactory::eINSTANCE.
                                        createOperatorExpression;
                                    strongTriggerOperatorExpression.setOperator(OperatorType::OR);
                                    strongTrigger = strongTriggerOperatorExpression;
                                    var Expression weakTrigger;
                                    val weakTriggerOperatorExpression = KExpressionsFactory::eINSTANCE.
                                        createOperatorExpression;
                                    weakTriggerOperatorExpression.setOperator(OperatorType::OR);
                                    weakTrigger = weakTriggerOperatorExpression;

                                    // For every transition 
                                    for (transition : originalOutgoingTransitions) {

                                        // Add transition to watcher region
                                        // ONLY iff this is not a normal termination
                                        if (transition.type != TransitionType::TERMINATION) {

                                            // Create a valuedObject 
                                            val transitionValuedObject = KExpressionsFactory::eINSTANCE.
                                                createValuedObject();
                                            val transitionValuedObjectReference = KExpressionsFactory::eINSTANCE.
                                                createValuedObjectReference()
                                            transitionValuedObjectReference.setValuedObject(transitionValuedObject);
                                            if (transition.type == TransitionType::STRONGABORT) {
                                                transitionValuedObject.setName(
                                                    "_" + state.id + "_S" + transition.priority);
                                                strongTriggerOperatorExpression.add(transitionValuedObjectReference.copy);
                                            } else {
                                                transitionValuedObject.setName(
                                                    "_" + state.id + "_W" + transition.priority);
                                                weakTriggerOperatorExpression.add(transitionValuedObjectReference.copy);
                                            }
                                            transitionValuedObject.setInput(false);
                                            transitionValuedObject.setOutput(false);
                                            state.parentRegion.parentState.valuedObjects.add(transitionValuedObject);

                                            val watcherTransition = SCChartsFactory::eINSTANCE.createTransition();
                                            watcherTransition.setTargetState(abortState);
                                            runState.outgoingTransitions.add(watcherTransition);

                                            // Move trigger from original transition to watcher transition
                                            watcherTransition.setTrigger(transition.trigger.copy);

                                            // Watcher transition gets the same priority / immediate / delay
                                            watcherTransition.setPriority(transition.priority);
                                            watcherTransition.setImmediate(transition.isImmediate);
                                            watcherTransition.setDelay(transition.delay);

                                            // Watcher transition emits the auxiliary valuedObject
                                            val transitionValuedObjectEmission = SCChartsFactory::eINSTANCE.
                                                createEmission();
                                            transitionValuedObjectEmission.setValuedObject(transitionValuedObject);
                                            watcherTransition.addEmission(transitionValuedObjectEmission);

                                            // Change trigger of original transition to transitionValuedObjectReference
                                            transition.setTrigger(transitionValuedObjectReference.copy);
                                        }

                                        // Move original transition from state to conditionalState
                                        // and remove strong, normal termination attributes
                                        // put immediate attribute
                                        conditionalState.outgoingTransitions.add(transition);
                                        transition.setType(TransitionType::WEAKABORT);
                                        transition.setImmediate(true);
                                    }

                                    // Simplify triggers (if they only consist of one valuedObject reference)
                                    if (strongTriggerOperatorExpression.subExpressions.size == 1) {
                                        strongTrigger = strongTriggerOperatorExpression.subExpressions.get(0);
                                    }
                                    if (weakTriggerOperatorExpression.subExpressions.size == 1) {
                                        weakTrigger = weakTriggerOperatorExpression.subExpressions.get(0);
                                    }

                                    // Hotfix for SCCharts
                                    if (strongTrigger instanceof OperatorExpression) {
                                        strongTrigger = (strongTrigger as OperatorExpression).
                                            fixForOperatorExpressionLists;
                                    }
                                    if (weakTrigger instanceof OperatorExpression) {
                                        weakTrigger = (weakTrigger as OperatorExpression).fixForOperatorExpressionLists;
                                    }

                                    // Create an Aborted state for every region
                                    for (region : originalRegions) {

                                        // Remember all outgoing transitions
                                        val regionStates = ImmutableList::copyOf(region.states);

                                        val abortedState = SCChartsFactory::eINSTANCE.createState();
                                        abortedState.setId(state.id("_Aborted"));
                                        abortedState.setLabel("_Aborted");
                                        abortedState.setFinal(true);
                                        val needAbortedState = ((outgoingStrongTransitions.size > 0 ||
                                            outgoingWeakTransitions.size > 0
                                        ) && (regionStates.filter(e|!e.isFinal).size > 0));
                                        if (needAbortedState) {
                                            region.states.add(abortedState);
                                        }

                                        // Do not add the state here, only add the state iff there are any transitions
                                        // ending up in this _Aborted state (within the if-for-constructs below)
                                        // For every inner state, connect with a weak and a strong transition
                                        if (outgoingStrongTransitions.size > 0) {
                                            for (regionState : regionStates) {

                                                // If the state is a final state do NOT connect it with the _Aborted state
                                                if (!regionState.isFinal) {

                                                    // Create a transition ending up in _Aborted
                                                    val strongAbortTransition = SCChartsFactory::eINSTANCE.
                                                        createTransition();
                                                    strongAbortTransition.setTargetState(abortedState);
                                                    strongAbortTransition.setImmediate(true);

                                                    // Now add all strong abort watcher valuedObjects as a trigger
                                                    strongAbortTransition.setTrigger(strongTrigger.copy);

                                                    // Set the highest priority
                                                    strongAbortTransition.setPriority(1);
                                                    for (regionStateOutgoingTransition : regionState.outgoingTransitions) {
                                                        regionStateOutgoingTransition.setPriority(
                                                            regionStateOutgoingTransition.priority + 1);
                                                    }

                                                    // Add transition
                                                    regionState.outgoingTransitions.add(0, strongAbortTransition);
                                                }
                                            }
                                        }

                                        if (outgoingWeakTransitions.size > 0) {
                                            for (regionState : regionStates) {

                                                // If the state is a final state do NOT connect it with the _Aborted state
                                                if (!regionState.isFinal) {

                                                    // Create a transition ending up in _Aborted
                                                    val weakAbortTransition = SCChartsFactory::eINSTANCE.
                                                        createTransition();
                                                    weakAbortTransition.setTargetState(abortedState);
                                                    weakAbortTransition.setImmediate(true);

                                                    // Now add all weak abort watcher valuedObjects as a trigger
                                                    weakAbortTransition.setTrigger(weakTrigger.copy);

                                                    // Set the lowest priority
                                                    weakAbortTransition.setPriority(regionState.maxPriority + 1);

                                                    // Add transition
                                                    regionState.outgoingTransitions.add(weakAbortTransition);
                                                }
                                            }
                                        }

                                    }

                                    // Optimization: If a normal termination is outgoing then the following 
                                    // transformation is necessary to be able to abort the watcher in case
                                    // of triggering the normal termination.
                                    if (terminationExists) {

                                        // If there is at least one such transition then whe need an "_Exit" valuedObject
                                        val exitValuedObject = KExpressionsFactory::eINSTANCE.createValuedObject();
                                        val exitValuedObjectReference = KExpressionsFactory::eINSTANCE.
                                            createValuedObjectReference()
                                        exitValuedObjectReference.setValuedObject(exitValuedObject);
                                        exitValuedObject.setName("_Term_" + state.id);
                                        state.valuedObjects.add(exitValuedObject);

                                        // Add a watcher transition from Run to Abort triggered by _Exit
                                        val watcherTransition = SCChartsFactory::eINSTANCE.createTransition();
                                        watcherTransition.setTargetState(abortState);
                                        runState.outgoingTransitions.add(watcherTransition);
                                        watcherTransition.setTrigger(exitValuedObjectReference);
                                        watcherTransition.setPriority(runState.maxPriority + 1);
                                        watcherTransition.setImmediate(true);
                                        watcherTransition.setDelay(0);

                                        val mainRegion = SCChartsFactory::eINSTANCE.createRegion();
                                        mainRegion.setId(state.id("Body"));
                                        val mainState = SCChartsFactory::eINSTANCE.createState();
                                        mainState.setId(state.id("Main"));
                                        mainState.setLabel("Main");
                                        mainState.setInitial(true);
                                        val termState = SCChartsFactory::eINSTANCE.createState();
                                        termState.setId(state.id("Term"));
                                        termState.setLabel("Term");
                                        termState.setFinal(true);

                                        // Move all inner regions of the state to the mainState
                                        val regions = ImmutableList::copyOf(state.regions);
                                        for (region : regions) {
                                            mainState.regions.add(region);
                                            if (region.id.nullOrEmpty) {
                                                region.setId(region.id("R"));
                                            }
                                        }
                                        mainRegion.states.add(mainState);
                                        mainRegion.states.add(termState);
                                        state.regions.add(mainRegion);

                                        // Create a transition from Main to Term
                                        val transition = SCChartsFactory::eINSTANCE.createTransition();
                                        transition.setTargetState(termState);
                                        transition.setPriority(1);
                                        transition.setType(TransitionType::TERMINATION);
                                        mainState.outgoingTransitions.add(transition);
                                        val exitValuedObjectEmission = SCChartsFactory::eINSTANCE.createEmission();
                                        exitValuedObjectEmission.setValuedObject(exitValuedObject);
                                        transition.addEmission(exitValuedObjectEmission);

                                        // Move the watcher region outside the mainState
                                        state.regions.add(watcherRegion);
                                    }

                                }

                            }

                        }
                        