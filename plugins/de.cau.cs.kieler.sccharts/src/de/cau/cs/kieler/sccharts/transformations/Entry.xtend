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
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * SCCharts Entry Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Entry {

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                      E N T R Y         A C T I O N                  --
    //-------------------------------------------------------------------------
    // @requires: during actions
    //
    // Idea: Setup or create a firstState and a lastState and place the
    // entry actions of a state in between these two states.
    // Transforming Entry Actions.
    def State transform(State rootState) {
        val targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        for (targetState : targetRootState.getAllStates.immutableCopy) {
            targetState.transformEntry(targetRootState);
        }
        targetRootState.fixAllTextualOrdersByPriorities;
    }

    // Traverse all states and transform macro states that have actions to transform
    def void transformEntry(State state, State targetRootState) {
        if (!state.entryActions.nullOrEmpty) {

            var State firstState
            var State lastState

            if (state.final) {
                val connector = state.parentRegion.createState(GENERATED_PREFIX + "C").uniqueName.setTypeConnector
                for (transition : state.incomingTransitions.immutableCopy) {
                    transition.setTargetState(connector)
                }
                firstState = connector
                lastState = state
            } else if (!state.hasInnerStatesOrRegions) {
                val region = state.createRegion(GENERATED_PREFIX + "Entry")
                firstState = region.createInitialState(GENERATED_PREFIX + "Init")
                lastState = region.createFinalState(GENERATED_PREFIX + "Done")
                val exitState = state.parentRegion.createState(GENERATED_PREFIX + "Exit").uniqueName
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
                    connector = entryRegion.createState(GENERATED_PREFIX + "C").uniqueName.setTypeConnector
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

}
