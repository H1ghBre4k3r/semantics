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
import de.cau.cs.kieler.core.kexpressions.Expression
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import java.util.HashMap

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * SCCharts Abort Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Abort {

    @Inject
    extension KExpressionsExtension

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    private val nameCache = <String> newArrayList("_term")
    
    //-------------------------------------------------------------------------
    //--   A B O R T   A L T E R N A T I V E  T R A N S F O R M A T I O N    --
    //-------------------------------------------------------------------------
    // The new DEFAULT abort transformation, previously transformAbortAlternative.
    
    // Transforming Aborts.
    def State transformAbort(State rootState) {
        val targetRootState = rootState.fixAllPriorities;
        
        nameCache.clear

        // Traverse all states
        var done = false;
        for (targetState : targetRootState.getAllContainedStatesList) {
            if (!done) {
                targetState.transformAbort(targetRootState);
            }
            //done = true;
        }
        targetRootState.fixAllTextualOrdersByPriorities;
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
    def void transformAbort(State state, State targetRootState) {
        // (a) more than one transitions outgoing OR
        // (b) ONE outgoing transition AND
        //     + not a termination transition without any trigger
        val stateHasUntransformedTransitions = ((state.outgoingTransitions.size > 1) || ((state.outgoingTransitions.
            size == 1) && (!(state.outgoingTransitions.filter[typeTermination].filter[trigger == null].size == 1))))

        val stateHasUntransformedAborts = (!(state.outgoingTransitions.filter[!typeTermination].nullOrEmpty))

        //        if (state.hierarchical && stateHasUntransformedAborts && state.label != "WaitAandB") {
        if ((state.hasInnerStatesOrRegions || state.hasInnerActions) && stateHasUntransformedTransitions) { // && state.label != "WaitAB") {
            val transitionTriggerVariableMapping = new HashMap<Transition, ValuedObject>

            // Remember all outgoing transitions and regions (important: do not consider regions without inner states! => regions2)
            val outgoingTransitions = state.outgoingTransitions.immutableCopy
            val regions = state.regions2.immutableCopy

            // .. || stateHasUntransformedTransitions : for conditional terminations!
            if (stateHasUntransformedAborts || stateHasUntransformedTransitions) {
                val ctrlRegion = state.createRegion(GENERATED_PREFIX + "Ctrl").uniqueNameCached(nameCache)
                val runState = ctrlRegion.createInitialState(GENERATED_PREFIX + "Run").uniqueNameCached(nameCache)
                val doneState = ctrlRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueNameCached(nameCache)

                // Build up weak and strong abort triggers
                var Expression strongAbortTrigger = null;
                var Expression weakAbortTrigger = null;
                for (transition : outgoingTransitions) {

                    // Create a new _transitionTrigger valuedObject
                    val transitionTriggerVariable = state.parentRegion.parentState.createVariable(
                        GENERATED_PREFIX + "trig").setTypeBool.uniqueNameCached(nameCache)
                    state.createEntryAction.addEffect(transitionTriggerVariable.assign(FALSE))
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
                        val mainRegion = state.createRegion(GENERATED_PREFIX + "Main").uniqueNameCached(nameCache)
                        val mainState = mainRegion.createInitialState(GENERATED_PREFIX + "Main").uniqueNameCached(nameCache)
                        mainState.regions.add(region)
                        val termState = mainRegion.createFinalState(GENERATED_PREFIX + "Term").uniqueNameCached(nameCache)
                        val termVariable = state.createVariable(GENERATED_PREFIX + "termRegion").setTypeBool.uniqueNameCached(nameCache)
                        mainState.createTransitionTo(termState).addEffect(termVariable.assign(TRUE)).setTypeTermination
                        if (terminationTrigger != null) {
                            terminationTrigger = terminationTrigger.and(termVariable.reference)
                        } else {
                            terminationTrigger = termVariable.reference
                        }
                        state.createEntryAction.addEffect(termVariable.assign(FALSE))
                    }

                    // Inside every region create a _Aborted
                    val abortedState = region.retrieveFinalState(GENERATED_PREFIX + "Aborted").uniqueNameCached(nameCache)
                    for (innerState : region.states.filter[!final]) {
                        if (innerState != abortedState) {
                            if (strongAbortTrigger != null) {
                                val strongAbort = innerState.createTransitionTo(abortedState, 0)
                                if (innerState.hasInnerStatesOrRegions || innerState.hasInnerActions) {

                                    // HERE DIFFERENCE TO ABORT2()
                                    // We mark the transition as strong abort and handle
                                    // it later when transforming this hierarchical state.
                                    // This leads to more variables but avoids more transitions.
                                    strongAbort.setTypeStrongAbort

                                // END OF DIFFERENCE
                                }
                                strongAbort.setPriority(0)
                                strongAbort.setTrigger(strongAbortTrigger.copy)
                                strongAbort.setImmediate
                            }
                            if (weakAbortTrigger != null) {
// The following line is responsible for KISEMA 925 to fail                                 
//                                val weakAbort = innerState.createTransitionTo(abortedState) 
//                                val weakAbort = innerState.createTransitionTo(abortedState, 0)
                                val weakAbort = innerState.createTransitionTo(abortedState)
                                weakAbort.setTrigger(weakAbortTrigger.copy)
                                weakAbort.setLowestPriority;
                                // MUST be immediate: Otherwise new aborting transition may never be
                                // taken (e.g., in cyclic behavior like during actions)
                                //
                                // Why is the solution to make all new aborting transitions being immediate? The reason for short is that immediate cycles are forbidden and
                                // once the control rests (which is hence the consequence of forbidding immediate cycles) in one of the states, the new immediate (weak)
                                // aborting transition will be taken, although it has a lower priority than any other existing transitions.
                                weakAbort.setImmediate;
                            }
                        }
                    }
                }
                
                if (terminationTrigger == null) {
                    terminationTrigger = TRUE;
                }

                for (transition : outgoingTransitions) {

                    // Get the _transitionTrigger that was created earlier
                    val transitionTriggerVariable = transitionTriggerVariableMapping.get(transition)

                    // Create a ctrlTransition in the ctrlRegion
                    val ctrlTransition = runState.createTransitionTo(doneState)
                    if (transition.immediate2) {
                        // if the transition was immediate then set the ctrl transition to be immediate
                        ctrlTransition.setImmediate(true)
                    }
                    
                    if (transition.typeTermination) {
                        if (transition.trigger != null) {
                           ctrlTransition.setTrigger(terminationTrigger.copy.and(transition.trigger))
                        } else {
                            ctrlTransition.setTrigger(terminationTrigger.copy)
                        }
                    } else {
                        ctrlTransition.setTrigger(transition.trigger)
                    }

                    ctrlTransition.addEffect(transitionTriggerVariable.assign(TRUE))
                }

            }

            // Create a single outgoing normal termination to a new connector state
            val outgoingConnectorState = state.parentRegion.createState(GENERATED_PREFIX + "C").uniqueNameCached(nameCache).
                setTypeConnector
            state.createTransitionTo(outgoingConnectorState).setTypeTermination
            
            // Be careful to NOT create a trigger for the LAST (lowest priorized) outgoing transition from a connector, this must
            // be the DEFAULT transition that has NO trigger ***
            val defaultTransition = outgoingTransitions.last
            
            for (transition : outgoingTransitions) {

                // Modify the outgoing transition
                transition.setSourceState(outgoingConnectorState)

                if (transition != defaultTransition) {
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
    //--               A B O R T   A L T E R N A T I V E    N O    W T O     --
    //-------------------------------------------------------------------------
    // Transforming Aborts.
    def State transformAbortNoWTO(State rootState) {
        val targetRootState = rootState.fixAllPriorities;
        
        nameCache.clear

        // Traverse all states
        var done = false;
        for (targetState : targetRootState.getAllContainedStatesList) {
            if (!done) {
                targetState.transformAbortNoWTO(targetRootState);
            }
            //done = true;
        }
        targetRootState.fixAllTextualOrdersByPriorities;
    }


    // Traverse all states 
    def void transformAbortNoWTO(State state, State targetRootState) {
        // (a) more than one transitions outgoing OR
        // (b) ONE outgoing transition AND
        //     + not a termination transition without any trigger
        val stateHasUntransformedTransitions = ((state.outgoingTransitions.size > 1) || ((state.outgoingTransitions.
            size == 1) && (!(state.outgoingTransitions.filter[typeTermination].filter[trigger == null].size == 1))))

        val stateHasUntransformedAborts = (!(state.outgoingTransitions.filter[!typeTermination].nullOrEmpty))

        //        if (state.hierarchical && stateHasUntransformedAborts && state.label != "WaitAandB") {
        if ((state.hasInnerStatesOrRegions || state.hasInnerActions) && stateHasUntransformedTransitions) { // && state.label != "WaitAB") {
            val transitionTriggerVariableMapping = new HashMap<Transition, ValuedObject>

            // Remember all outgoing transitions and regions (important: do not consider regions without inner states! => regions2)
            val outgoingTransitions = state.outgoingTransitions.immutableCopy
            val regions = state.regions2.immutableCopy

            // .. || stateHasUntransformedTransitions : for conditional terminations!
            if (stateHasUntransformedAborts || stateHasUntransformedTransitions) {
                val ctrlRegion = state.createRegion(GENERATED_PREFIX + "Ctrl").uniqueNameCached(nameCache)
                val runState = ctrlRegion.createInitialState(GENERATED_PREFIX + "Run").uniqueNameCached(nameCache)
                val doneState = ctrlRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueNameCached(nameCache)

                // Build up weak and strong abort triggers
                var Expression strongAbortTrigger = null;
                var Expression weakAbortTrigger = null;
                var strongAbortImmediate = false;
                var weakAbortImmediate = false;
                for (transition : outgoingTransitions) {
                    if (transition.typeStrongAbort) {
                        strongAbortTrigger = strongAbortTrigger.or2(transition.trigger.copy)
                        strongAbortImmediate = strongAbortImmediate || transition.immediate
                    } else if (transition.typeWeakAbort) {
                        weakAbortTrigger = weakAbortTrigger.or2(transition.trigger.copy)
                        weakAbortImmediate = weakAbortImmediate || transition.immediate
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
                        val mainRegion = state.createRegion(GENERATED_PREFIX + "Main").uniqueNameCached(nameCache)
                        val mainState = mainRegion.createInitialState(GENERATED_PREFIX + "Main").uniqueNameCached(nameCache)
                        mainState.regions.add(region)
                        val termState = mainRegion.createFinalState(GENERATED_PREFIX + "Term").uniqueNameCached(nameCache)
                        val termVariable = state.createVariable(GENERATED_PREFIX + "termRegion").setTypeBool.uniqueNameCached(nameCache)
                        mainState.createTransitionTo(termState).addEffect(termVariable.assign(TRUE)).setTypeTermination
                        if (terminationTrigger != null) {
                            terminationTrigger = terminationTrigger.and(termVariable.reference)
                        } else {
                            terminationTrigger = termVariable.reference
                        }
                        state.createEntryAction.addEffect(termVariable.assign(FALSE))
                    }

                    // Inside every region create a _Aborted
                    val abortedState = region.retrieveFinalState(GENERATED_PREFIX + "Aborted").uniqueNameCached(nameCache)
                    for (innerState : region.states.filter[!final]) {
                        if (innerState != abortedState) {
                            if (strongAbortTrigger != null) {
                                val strongAbort = innerState.createTransitionTo(abortedState, 0)
                                if (innerState.hasInnerStatesOrRegions || innerState.hasInnerActions) {

                                    // HERE DIFFERENCE TO ABORT2()
                                    // We mark the transition as strong abort and handle
                                    // it later when transforming this hierarchical state.
                                    // This leads to more variables but avoids more transitions.
                                    strongAbort.setTypeStrongAbort

                                // END OF DIFFERENCE
                                }
                                strongAbort.setPriority(0)
                                strongAbort.setTrigger(strongAbortTrigger.copy)
                                strongAbort.setImmediate(strongAbortImmediate)
                            }
                            if (weakAbortTrigger != null) {
// The following line is responsible for KISEMA 925 to fail                                 
//                                val weakAbort = innerState.createTransitionTo(abortedState) 
//                                val weakAbort = innerState.createTransitionTo(abortedState, 0)
                                val weakAbort = innerState.createTransitionTo(abortedState)
                                weakAbort.setTrigger(weakAbortTrigger.copy)
                                weakAbort.setLowestPriority;
                                // MUST be immediate: Otherwise new aborting transition may never be
                                // taken (e.g., in cyclic behavior like during actions)
                                //
                                // Why is the solution to make all new aborting transitions being immediate? The reason for short is that immediate cycles are forbidden and
                                // once the control rests (which is hence the consequence of forbidding immediate cycles) in one of the states, the new immediate (weak)
                                // aborting transition will be taken, although it has a lower priority than any other existing transitions.
                                weakAbort.setImmediate(weakAbortImmediate)
                            }
                        }
                    }
                }
                
                if (terminationTrigger == null) {
                    terminationTrigger = TRUE;
                }

                for (transition : outgoingTransitions) {
                    // Create a ctrlTransition in the ctrlRegion
                    val ctrlTransition = runState.createTransitionTo(doneState)
                    if (transition.immediate2) {
                        // if the transition was immediate then set the ctrl transition to be immediate
                        ctrlTransition.setImmediate(true)
                    }
                    
                    if (transition.typeTermination) {
                        if (transition.trigger != null) {
                           ctrlTransition.setTrigger(terminationTrigger.copy.and(transition.trigger.copy))
                        } else {
                            ctrlTransition.setTrigger(terminationTrigger.copy)
                        }
                    } else {
                        ctrlTransition.setTrigger(transition.trigger.copy)
                    }
                }

            }

            // Create a single outgoing normal termination to a new connector state
            val outgoingConnectorState = state.parentRegion.createState(GENERATED_PREFIX + "C").uniqueNameCached(nameCache).
                setTypeConnector
            state.createTransitionTo(outgoingConnectorState).setTypeTermination
            
            // Be careful to NOT create a trigger for the LAST (lowest priorized) outgoing transition from a connector, this must
            // be the DEFAULT transition that has NO trigger ***
            val defaultTransition = outgoingTransitions.last
            
            for (transition : outgoingTransitions) {

                // Modify the outgoing transition
                transition.setSourceState(outgoingConnectorState)

                if (transition != defaultTransition) {
                      // Take the original trigger here (before for the actual ABORT in the main region take a copy, also for the watcher take a copy
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
    //--     A B O R T     S P E C I A L      T R A N S F O R M A T I O N    --
    //-------------------------------------------------------------------------
    // Previously the ABORT DEFAULT transformation, will not work in
    // all cases. It tries to replace DEEP the abort trigger, this may fail
    // if there are weak&strong aborts mixed inside. for abro it works.
    
    // Transforming Aborts.
    def State transformAbortSpecial(State rootState) {
        val targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        var done = false;
        for (targetState : targetRootState.getAllContainedStatesList) {
            if (!done) {
                targetState.transformAbortSpecial(targetRootState);
            }
           //done = true;
        }
        targetRootState.fixAllTextualOrdersByPriorities;
    }

    // Traverse all states 
    //    def void preTransformAborts2(State state, Region targetRootState) {
    //        // For all normal hierarchical states, add a single connector
    //        // add
    //    }
    def void transformAbortSpecial(State state, State targetRootState) {

        // this for example could be several terminations, in this case we do not need the FULL abort transformation
        // and can only combine the terminations (using one termination and a connector node)
        val stateHasUntransformedTransitions = ((state.outgoingTransitions.size > 1) || 
            ( (state.outgoingTransitions.size == 1) 
              && ((!(state.outgoingTransitions.filter[typeTermination].filter[trigger == null].size == 1))
//                  ||
//                  !state.hasInnerActions && !state.hasInnerStatesOrRegions 
              ))
            )

        // in this case we need the FULL abort transformation
        val stateHasUntransformedAborts = (!(state.outgoingTransitions.filter[!typeTermination].nullOrEmpty))

        //        if (state.hierarchical && stateHasUntransformedAborts && state.label != "WaitAandB") {
        if ((state.hasInnerStatesOrRegions || state.hasInnerActions) && stateHasUntransformedTransitions) { // && state.label != "WaitAB") {
            val transitionTriggerVariableMapping = new HashMap<Transition, ValuedObject>

            // Remember all outgoing transitions and regions (important: do not consider regions without inner states! => regions2)
            val outgoingTransitions = state.outgoingTransitions.immutableCopy
            val regions = state.regions2.toList.immutableCopy

            // .. || stateHasUntransformedTransitions : for conditional terminations!
            if (stateHasUntransformedAborts || stateHasUntransformedTransitions) {
                val ctrlRegion = state.createRegion(GENERATED_PREFIX + "Ctrl").uniqueName
                val runState = ctrlRegion.createInitialState(GENERATED_PREFIX + "Run").uniqueName
                val doneState = ctrlRegion.createFinalState(GENERATED_PREFIX + "Done").uniqueName

                // Build up weak and strong abort triggers
                var Expression strongAbortTrigger = null;
                var Expression weakAbortTrigger = null;
                for (transition : outgoingTransitions) {

                    // Create a new _transitionTrigger valuedObject
                    val transitionTriggerVariable = state.parentRegion.parentState.createVariable(
                        GENERATED_PREFIX + "trig").setTypeBool.uniqueName
                    state.createEntryAction.addEffect(transitionTriggerVariable.assign(FALSE))
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
                        val mainState = mainRegion.createInitialState(GENERATED_PREFIX + "Main").uniqueName
                        mainState.regions.add(region)
                        val termState = mainRegion.createFinalState(GENERATED_PREFIX + "Term").uniqueName
                        val termVariable = state.createVariable(GENERATED_PREFIX + "term").setTypeBool.uniqueName
                        mainState.createTransitionTo(termState).addEffect(termVariable.assign(TRUE)).setTypeTermination
                        if (terminationTrigger != null) {
                            terminationTrigger = terminationTrigger.and(termVariable.reference)
                        } else {
                            terminationTrigger = termVariable.reference
                        }
                        state.createEntryAction.addEffect(termVariable.assign(FALSE))
                    }

                    // Inside every region create a _Aborted
                    val abortedState = region.retrieveFinalState(GENERATED_PREFIX + "Aborted").uniqueName
                    for (innerState : region.states.filter[!final]) {
                        if (innerState != abortedState) {
                            if (strongAbortTrigger != null) {
                                val strongAbort = innerState.createTransitionTo(abortedState, 0)
                                if (innerState.hasInnerStatesOrRegions || innerState.hasInnerActions) {

                                    // HERE DIFFERENCE TO ABORT1()
                                    // We dig deep in the hierarchy and connect all states with immediate transitions
                                    // to a final state.
                                    // This leads to more transitions but avoids more variables.
                                    strongAbort.setTypeTermination
                                    val allInnerSimpleStates = innerState.allContainedStatesList.filter[!(hasInnerStatesOrRegions || hasInnerActions)].
                                        filter[!final]
                                    for (innerSimpleState : allInnerSimpleStates) {
                                        val innerFinalStates = innerSimpleState.parentRegion.states.filter[final]
                                        var State innerAbortedState;
                                        if (innerFinalStates.nullOrEmpty) {
                                            innerAbortedState = innerSimpleState.parentRegion.
                                                createFinalState(GENERATED_PREFIX + "Aborted").uniqueName
                                        } else {
                                            innerAbortedState = innerFinalStates.get(0)
                                        }
                                        val innerStrongAbort = innerSimpleState.createTransitionTo(innerAbortedState, 0)
                                        innerStrongAbort.setPriority(0)
                                        innerStrongAbort.setTrigger(strongAbortTrigger.copy)
                                    }

                                // END OF DIFFERENCE
                                }
                                strongAbort.setPriority(0)
                                strongAbort.setTrigger(strongAbortTrigger.copy)
                            }
                            if (weakAbortTrigger != null) {
// The following line is responsible for KISEMA 925 to fail                                 
//                                val weakAbort = innerState.createTransitionTo(abortedState) 
                                val weakAbort = innerState.createTransitionTo(abortedState, 0)
                                weakAbort.setTrigger(weakAbortTrigger.copy)
                            }
                        }
                    }
                }
                
                if (terminationTrigger == null) {
                    terminationTrigger = TRUE;
                }

                for (transition : outgoingTransitions) {

                    // Get the _transitionTrigger that was created earlier
                    val transitionTriggerVariable = transitionTriggerVariableMapping.get(transition)

                    // Create a ctrlTransition in the ctrlRegion
                    val ctrlTransition = runState.createTransitionTo(doneState)
                    if (transition.typeTermination) {
                        if (transition.trigger != null) {
                            ctrlTransition.setTrigger(terminationTrigger.copy.and(transition.trigger))
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
            val outgoingConnectorState = state.parentRegion.createState(GENERATED_PREFIX + "C").uniqueName.
                setTypeConnector
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


}
