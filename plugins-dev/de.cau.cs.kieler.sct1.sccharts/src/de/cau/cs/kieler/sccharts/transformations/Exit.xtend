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
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.kico.transformation.AbstractExpansionTransformation
import de.cau.cs.kieler.kitt.tracing.Traceable
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import de.cau.cs.kieler.sccharts.featuregroups.SCChartsFeatureGroup
import de.cau.cs.kieler.sccharts.features.SCChartsFeature

import static extension de.cau.cs.kieler.kitt.tracing.TracingEcoreUtil.*
import static extension de.cau.cs.kieler.kitt.tracing.TransformationTracing.*
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsCreateExtensions
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsValuedObjectExtensions

/**
 * SCCharts Exit Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Exit extends AbstractExpansionTransformation implements Traceable {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        return SCChartsTransformation::EXIT_ID
    }

    override getName() {
        return SCChartsTransformation::EXIT_NAME
    }

    override getExpandsFeatureId() {
        return SCChartsFeature::EXIT_ID
    }

    override getProducesFeatureIds() {
        return Sets.newHashSet(SCChartsFeature::CONNECTOR_ID)
    }

    override getNotHandlesFeatureIds() {
        return Sets.newHashSet(SCChartsFeature::ABORT_ID, SCChartsFeatureGroup::EXPANSION_ID)
    }

    //-------------------------------------------------------------------------

    @Inject
    extension KExpressionsCreateExtensions
    
    @Inject
    extension KExpressionsDeclarationExtensions
    
    @Inject
    extension KExpressionsValuedObjectExtensions    

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                      E X I T       A C T I O N S                    --
    //-------------------------------------------------------------------------
    // @requires: entry actions
    // @requires: during actions
    // @requires: suspend
    // @requires: valued valuedObjects
    // Transforming Exit Actions. 
    def State transform(State rootState) {
        val targetRootState = rootState.fixAllPriorities;

        // Prepare all states so that each reagion has at most one final state
        targetRootState.getAllStates.toList.forEach [ targetState |
            targetState.prepareExit(targetRootState);
        ]

        // Traverse all states
        targetRootState.getAllStates.toList.forEach [ targetState |
            targetState.transformExit(targetRootState);
        ]
        targetRootState.fixAllTextualOrdersByPriorities;
    }

    def void prepareExit(State state, State targetRootState) {
        for (region : state.regions.filter(ControlflowRegion)) {
            val finalStates = region.finalStates
            if (finalStates.size > 1) {
                val firstFinalState = finalStates.get(0)
                for (finalState : finalStates) {
                    finalState.setDefaultTrace //All following states etc. will be traced to state
                    if (finalState != firstFinalState) {
                        finalState.setNotFinal
                        finalState.createImmediateTransitionTo(firstFinalState)
                    }
                }
            }
        }
    }

    // Traverse all states and transform macro states that have actions to transform
    def void transformExit(State state, State targetRootState) {
        if (!state.exitActions.nullOrEmpty && !state.final) {
            
            state.setDefaultTrace //All following states etc. will be traced to state

            val stateOutgoingTransitions = state.outgoingTransitions.size
            var State firstState
            var State lastState

            if (!state.hasInnerStatesOrControlflowRegions) {
                state.regions.clear // FIX: need to erase dummy single region
                val region = state.createControlflowRegion(GENERATED_PREFIX + "Exit")
                firstState = region.createInitialState(GENERATED_PREFIX + "Init")
                lastState = region.createFinalState(GENERATED_PREFIX + "Done")
            } else if (state.regions.filter(ControlflowRegion).size == 1 && !state.regions.filter(ControlflowRegion).get(0).finalStates.nullOrEmpty) {
                val region = state.regions.filter(ControlflowRegion).get(0)
                lastState = region.createFinalState(GENERATED_PREFIX + "Done")

                firstState = region.finalStates.get(0) //every region MUST have an initial state
                firstState.setNotFinal
            } else { // state has several regions (or one region without any final state!)
                val region = state.createControlflowRegion(GENERATED_PREFIX + "Entry").uniqueName
                firstState = region.createInitialState(GENERATED_PREFIX + "Main")
                for (mainRegion : state.regions.filter(e|e != region).toList.immutableCopy) {
                    firstState.regions.add(mainRegion)
                }
                lastState = region.createFinalState(GENERATED_PREFIX + "Done")
            }

            // Optimization: "&& state.outgoingTransitions.filter[trigger != null].size > 0"
            // Do not create superflous exitOptionStates
            if (stateOutgoingTransitions > 0 && state.outgoingTransitions.filter[trigger != null].size > 0) {

                // Memorize outgoing transition
                val region = firstState.parentRegion
                var ValuedObject memory
                if (stateOutgoingTransitions > 1) {
                    memory = state.parentRegion.parentState.createValuedObject(GENERATED_PREFIX + "exit", createIntDeclaration).
                        uniqueName
                }
                val middleState = region.createState(GENERATED_PREFIX + "Memorize").setTypeConnector
                val exitOptionState = state.parentRegion.createState(GENERATED_PREFIX + "ExitOption").uniqueName.
                    setTypeConnector
                var counter = 1
                for (transition : state.outgoingTransitions.immutableCopy) {
                    val exitTransition = exitOptionState.createImmediateTransitionTo(transition.targetState)
                    if (stateOutgoingTransitions > 1) {
                        exitTransition.setTrigger(memory.reference.createEQExpression(counter.createIntValue))
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
                    connector = entryRegion.createState(GENERATED_PREFIX + "C").uniqueName.setTypeConnector
                }
                val transition = firstState.createImmediateTransitionTo(connector)
                
                // In case the starting point of this transition has regions with final states,
                // it is necessary to wait until these final states are reached.
                // Thus a termination transition is needed in this case.  
                if(firstState.hasInnerStatesOrControlflowRegions) { 
                    transition.setTypeTermination
                }
                
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

}