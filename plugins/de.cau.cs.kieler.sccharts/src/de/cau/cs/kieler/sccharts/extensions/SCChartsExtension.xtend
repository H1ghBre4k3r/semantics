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
import de.cau.cs.kieler.core.kexpressions.ValueType
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.sccharts.Assignment
import de.cau.cs.kieler.sccharts.DuringAction
import de.cau.cs.kieler.sccharts.Effect
import de.cau.cs.kieler.sccharts.Emission
import de.cau.cs.kieler.sccharts.EntryAction
import de.cau.cs.kieler.sccharts.ExitAction
import de.cau.cs.kieler.sccharts.HistoryType
import de.cau.cs.kieler.sccharts.LocalAction
import de.cau.cs.kieler.sccharts.Region
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.Scope
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.StateType
import de.cau.cs.kieler.sccharts.SuspendAction
import de.cau.cs.kieler.sccharts.TextEffect
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.TransitionType
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.core.kexpressions.Declaration
import org.eclipse.emf.common.util.EList
import de.cau.cs.kieler.sccharts.Binding

/**
 * SCCharts Extensions.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class SCChartsExtension {

    @Inject
    extension KExpressionsExtension

    // This prefix is used for namings of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //    public val Injector i = ActionsStandaloneSetup::doSetup();
    //    public val ActionsScopeProvider scopeProvider = i.getInstance(typeof(ActionsScopeProvider));
    //    public val ISerializer serializer = i.getInstance(typeof(ISerializer));
    //-------------------------------------------------------------------------
    //--             B A S I C   C R E A T I O N   M E T H O D S             --
    //-------------------------------------------------------------------------
    // Create an immutable list copy
    def ImmutableList<Object> immutableCopy(Iterable<Object> object) {
        ImmutableList::copyOf(object)
    }

    //====== GENERAL MODEL ELEMENTS =====
    // Get the single normal termination Transition. Return null if there is 
    // no outgoing normal termination Transition.
    def Transition getTerminationTransitions(State state) {
        val allTerminationTransitions = state.outgoingTransitions.filter(
            e|e.type == TransitionType::TERMINATION);
        if (allTerminationTransitions.size == 0) {
            return null;
        }
        allTerminationTransitions.head;
    }

    // Return the list of all contained States.
    def List<State> getAllContainedStates(Scope scope) {
        scope.eAllContents().filter(typeof(State)).toList()
    }

    // Return the list of all contained States and the root state if the scope is already a state.
    def List<State> getAllStates(Scope scope) {
        scope.getAllContainedStates => [
        	if (scope instanceof State) it += scope as State
        ]
    }

    // Return the list of all contained Regions.
    def List<Region> getAllContainedRegions(Scope scope) {
        scope.eAllContents().filter(typeof(Region)).toList()
    }

    // Return the list of all contained Transitions.
    def List<Transition> getAllContainedTransitions(Scope scope) {
        scope.eAllContents().filter(typeof(Transition)).toList()
    }

    // Return the list of all contained Actions.
    def List<Action> getAllContainedActions(Scope scope) {
        scope.eAllContents().filter(typeof(Action)).toList()
    }

    // Return the list of contained Emissions.
    def List<Emission> getAllContainedEmissions(Action action) {
        action.eAllContents().filter(typeof(Emission)).toList();
    }

    // Return the list of contained Assignments.
    def List<Assignment> getAllContainedAssignments(Action action) {
        action.eAllContents().filter(typeof(Assignment)).toList();
    }

    // Return the list of pure signals of a state.
    def List<ValuedObject> getPureSignals(State state) {
        state.valuedObjects.filter[e|e.isSignal && e.type == ValueType::PURE].toList
    }

    // Return the list of valued signals of a state.
    def List<ValuedObject> getValuedSignals(State state) {
        state.valuedObjects.filter[e|e.isSignal && e.type != ValueType::PURE].toList
    }

    // Return the list of all signals of a state.
    def List<ValuedObject> getSignals(State state) {
        state.valuedObjects.filter[e|e.isSignal].toList
    }

    // Return true if the valued Object is a pure signal
    def boolean isPureSignal(ValuedObject valuedObject) {
        valuedObject.isSignal && valuedObject.type == ValueType::PURE
    }

// FIXME
// Due to the SCCharts grammar changes SCCharts do not have a root region anymore.
// The top-most element is an SCChart which is a State.
// If necessary, these function should be re-implemented.

//    // Return the root region.
//    def Region getRootRegion(Region region) {
//
//        // Recursively find the root region 
//        if (region.parentState == null) {
//            return region;
//        }
//        region.parentState.parentRegion.rootRegion;
//    }
//
//    // Return the root region.
//    def Region getRootRegion(State state) {
//        state.parentRegion.rootRegion;
//    }

	def boolean isRootState(State state) {
		state.parentRegion == null
	}

    def State getRootState(State state) {
        if (state.parentRegion == null) return state;
        state.parentRegion.rootState
    }

    // Return the root state.
    def State getRootState(Region region) {
        // There should exactly be one state in the root region
//        region.rootRegion.states.get(0)
        region.parentState.getRootState
    }

//    // Return the root state.
//    def State getRootState(State state) {
//        state.parentRegion.rootState;
//    }
    
    def State createSCChart() {
         SCChartsFactory::eINSTANCE.createState
    }

    // Gets the list of non-empty regions
    def List<Region> getRegions2(State state) {
        val list2 = state.regions.filter(e | e.allContainedStates.size == 0).toList
        val list = state.regions.filter(e | e.allContainedStates.size > 0).toList
        list
    }
    
    //========== TRANSITIONS ===========
    
    def Transition setTypeTermination(Transition transition) {
        transition.setType(TransitionType::TERMINATION)
        transition
    }

    def Transition setTypeStrongAbort(Transition transition) {
        transition.setType(TransitionType::STRONGABORT)
        transition
    }

    def Transition setTypeWeakAbort(Transition transition) {
        transition.setType(TransitionType::WEAKABORT)
        transition
    }
    
    def boolean isTypeTermination(Transition transition) {
        return transition.type == TransitionType::TERMINATION
    }

    def boolean isTypeStrongAbort(Transition transition) {
        return transition.type == TransitionType::STRONGABORT
    }

    def boolean isTypeWeakAbort(Transition transition) {
        return transition.type == TransitionType::WEAKABORT
    }

    //========== STATES ===========
    def State createState(String id) {
        val state = SCChartsFactory::eINSTANCE.createState();
        state.setId(id)
        state.setLabel("")
        state
    }

    def State createState(Region region, String id, String label) {
        val state = createState(id)
        region.states.add(state)
        state
    }

    def State createState(Region region, String id) {
        val state = createState(id)
        state.setLabel(id)
        region.states.add(state)
        state
    }


    //========== UNIQUE NAMES ===========
    def private dispatch boolean uniqueNameTest(State state, String newName) {
        state.parentRegion.states.filter[it != state && id == newName].size == 0
    }
    def private dispatch boolean uniqueNameTest(Region region, String newName) {
        region.parentState.regions.filter[it != region && id == newName].size == 0
    }
    def private boolean uniqueNameTest(ValuedObject valuedObject, State state, String newName) {
        if (state == null) { //seems wrong!! --> || state.valuedObjects.nullOrEmpty) {
            return true
        }
        val notFoundOtherValuedObjectInState = state.valuedObjects.filter[it != valuedObject && name == newName].size == 0
        return notFoundOtherValuedObjectInState
    }
    def private dispatch boolean uniqueNameTest(ValuedObject valuedObject, String newName) {
        val state = (valuedObject.getEContainer as State);
        val rootState = state.getRootState
        var notFound = valuedObject.uniqueNameTest(rootState, newName)
        for (innerState : rootState.allContainedStates) {
            if (notFound && !valuedObject.uniqueNameTest(innerState, newName)) {
                notFound = false
            }
        }
        notFound
    }
    def private dispatch boolean uniqueNameTest(EObject eObject, String newName) {
        false
    }
    
    def private String uniqueNameHelper(EObject eObject, String originalId) {
        var String newName = null
        var c = 1
        if (eObject.uniqueNameTest(originalId)) {
            return originalId
        }
        while (newName == null) {
            c = c + 1
            val tmpNewName = originalId + c
            if (eObject.uniqueNameTest(tmpNewName)) {
                newName = tmpNewName
            } 
        }
        return newName
    }
    

    def State uniqueName(State state) {
        val originalId = state.id
        var String newName = state.uniqueNameHelper(originalId)
        if (newName != originalId) {
            state.setId(newName)
            state.setLabel2(newName)
        } 
        state
    }

    def Region uniqueName(Region region) {
        val originalId = region.id
        var String newName = region.uniqueNameHelper(originalId)
        if (originalId != newName) {
            region.setId(newName)
            region.setLabel2(newName)
        }
        region
    }

     def ValuedObject uniqueName(ValuedObject valuedObject) {
        val originalId = valuedObject.name
        var String newName = valuedObject.uniqueNameHelper(originalId)
        if (newName != originalId) {
             valuedObject.setName(newName)
        } 
        valuedObject
    }


    def State setInitial(State state) {
        state.setInitial(true)
        state
    }

    def State setNotInitial(State state) {
        state.setInitial(false)
        state
    }

    def State setFinal(State state) {
        state.setFinal(true)
        state
    }

    def State setNotFinal(State state) {
        state.setFinal(false)
        state
    }

    def State createInitialState(String id) {
        createState(id).setInitial
    }

    def State createFinalState(String id) {
        createState(id).setFinal
    }

    def State createInitialState(Region region, String id) {
        region.createState(id).setInitial
    }

    def State createFinalState(Region region, String id) {
        region.createState(id).setFinal
    }
    
    def State getInitialState(Region region) {
        var initialStates = region.states.filter[isInitial]
        if (initialStates.size > 0) {
            return initialStates.get(0)
        }
        return null
    }
    
    def State[] getAllFinalStates(Region region) {
        region.states.filter[isFinal]
    }
    
    def State[] getFinalStates(Region region) {
        region.allFinalStates.filter[outgoingTransitions.size == 0 && !hasInnerStatesOrRegions && entryActions.size == 0 && duringActions.size == 0 && exitActions.size == 0]
    }
    
    // Get the first (simple) final state if the region contains any, otherwise return null.
    def State getFinalState(Region region) {
        val finalStates = region.getFinalStates
        if (finalStates.size > 0)
            return finalStates.get(0)
        else
            return null
    }

    // Get any final state if the region already contains a final state, otherwise create a final state.
    def State retrieveFinalState(Region region, String id) {
        val finalState = region.getFinalState
        if (finalState != null) {
            return finalState
        }
        region.createState(id).setFinal
    }

    def State setLabel2(State state, String label) {
        state.setLabel(label)
        state
    }

    def State setDefaultLabel(State state) {
        state.setLabel2(state.id)
    }

    def State setTypeConnector(State state) {
        state.setType(StateType::CONNECTOR);
        state
    }

    def State setTypeNormal(State state) {
        state.setType(StateType::NORMAL);
        state
    }

    def State setTypeReference(State state) {
        state.setType(StateType::REFERENCE);
        state
    }

    def State setTypeTextual(State state) {
        state.setType(StateType::TEXTUAL);
        state
    }
    
    // REF
    def boolean isReferencedState(State state) {
        state.referencedScope != null
    }

    //========== REGIONS ===========
    def Region createRegion(String id) {
        val region = SCChartsFactory::eINSTANCE.createRegion();
        region.setId(id)
        region.setLabel(id)
        region
    }

    def Region createRegion(State state, String id) {
        val region = createRegion(id)
        // ATTENTION: if this is the first region and there already is an IMPLICIT region
        // e.g. because of inner actions, then return THIS region only!
        if (state.regions.size == 1 && state.regions.get(0).allContainedStates.size == 0) {
            return state.regions.get(0)
        }
        state.regions.add(region)
        region
    }

    def Region setLabel2(Region region, String label) {
        region.setLabel(label)
        region
    }

    def boolean hasInnerStatesOrRegions(State state) {
        return ((state.regions != null && state.regions.size != 0 && state.eAllContents.filter(typeof(State)).size > 0))
    }

    // These are actions that expand to INNER content like during or exit actions.
    def boolean hasInnerActions(State state) {
        return (!state.duringActions.nullOrEmpty || !state.exitActions.nullOrEmpty)
    }


    //========== TRANSITIONS ===========
    // A transition is a history transition if it is not a reset transition.
    def boolean isHistory(Transition transition) {
        transition.history != HistoryType::RESET
    }

    def boolean isDeepHistory(Transition transition) {
        transition.history == HistoryType::DEEP
    }

    def Transition createTransition() {
        val transition = SCChartsFactory::eINSTANCE.createTransition()
        transition.setPriority2(1)
    }

    def Transition createImmediateTransition() {
        createTransition.setImmediate
    }

    def Transition createTransitionTo(State sourceState, State targetState) {
        val transition = createTransition()
        transition.setTargetState(targetState)
        sourceState.outgoingTransitions.add(transition)
//        targetState.incomingTransitions.add(transition)
        //val dummyTransition = createTransition()
        //sourceState.outgoingTransitions.add(dummyTransition)
        //sourceState.outgoingTransitions.remove(dummyTransition)
        transition.trimPriorities
    }
    
    def Transition createTransitionTo(State sourceState, State targetState, int index) {
        val transition = createTransition()
        transition.setTargetState(targetState)
        sourceState.outgoingTransitions.add(index, transition)
//        targetState.incomingTransitions.add(transition)
        transition.trimPriorities
    }    

    def Transition setTargetState2(Transition transition, State targetState) {
//        transition.targetState.incomingTransitions.remove(transition)
        transition.setTargetState(targetState)
//        targetState.incomingTransitions.add(transition)
        transition
    }

    def Transition setSourceState(Transition transition, State sourceState) {
//        transition.sourceState.outgoingTransitions.remove(transition)
        sourceState.outgoingTransitions.add(transition)
        transition.trimPriorities
    }

    def Transition createImmediateTransitionTo(State sourceState, State targetState) {
        sourceState.createTransitionTo(targetState).setImmediate
    }

    def Transition setTrigger2(Transition transition, Expression expression) {
        transition.setTrigger(expression)
        transition
    }

    def Transition addEffect(Transition transition, Effect effect) {
        transition.effects.add(effect)
        transition
    }

    def Transition clearEffects(Transition transition) {
        transition.effects.clear
        transition
    }

    def Transition setPriority2(Transition transition, int priority) {
        transition.setPriority(priority)
        transition
    }

    def Transition setLowestPriority(Transition transition) {
        val maxPriority = transition.sourceState.outgoingTransitions.length
        transition.setPriority2(maxPriority).trimPriorities
    }

    def Transition setHighestPriority(Transition transition) {
        transition.setPriority2(0).trimPriorities
    }

    def State fixAllPriorities(State state) {
        for (containedState : state.allContainedStates) {
            var prio = 1
            for (transition : containedState.outgoingTransitions) {
                transition.setPriority(prio)
                prio = prio + 1
            }
        }
        state
    }

//    def State fixAllEmptyRegions(State rootState) {
//        val regions = rootState.allContainedRegions.filter(e | e.allContainedStates == 0).toList.immutableCopy
//        for (region : regions) {
//            val parent = region.parentState
//            parent.regions.remove(region)
//        }
//        rootState
//    }


    def State fixAllTextualOrdersByPriorities(State state) {
        for (containedState : state.allContainedStates) {
            val transitions = containedState.outgoingTransitions.sortBy[priority].toList.immutableCopy;
            for (transition : transitions) {
                containedState.outgoingTransitions.remove(transition)
                containedState.outgoingTransitions.add(transition)
                transition.setPriority(0)
            }
        }
        state
    }

    def Transition trimPriorities(Transition transition) {
        var prio = 1
        val transitions = transition.sourceState.outgoingTransitions.toList.sortBy[priority]
        for (outgoingTransition : transitions) {
            outgoingTransition.setPriority(prio)
            prio = prio + 1
        }
        transition
    }

    def Transition setImmediate(Transition transition) {
        transition.setImmediate(true)
        transition
    }

    def Transition setNotImmediate(Transition transition) {
        transition.setImmediate(false)
        transition
    }

    //========== STATE ACTIONS ===========
    // Return wether a transition is immediate. This should return the
    // immediate flag of a transition unless
    // 1. the source state is a connector node, then the transition is always (implicityly) immediate OR
    // 2. the transition is a normal termination and has NOT trigger, then it is also (implicityly) immediate.
    def Boolean isImmediate2(Transition transition) {
        (transition.immediate) || 
        (transition.sourceState.type == StateType::CONNECTOR) || 
        (transition.type == TransitionType::TERMINATION && transition.trigger == null
        )
    }

    // Apply attributes from one local action to another
    def LocalAction applyAttributes(LocalAction localAction, LocalAction locationActionWithAttributes) {
        localAction.setImmediate(locationActionWithAttributes.isImmediate)
        localAction.setDelay(locationActionWithAttributes.delay)
        localAction.setLabel(locationActionWithAttributes.label)
        localAction.setTrigger(locationActionWithAttributes.trigger)
        localAction.effects.clear
        for (effect : locationActionWithAttributes.effects) {
            localAction.addEffect(effect.copy)
        }
        localAction
    }

    // Create a during action for a state.
    def DuringAction createDuringAction(State state) {
        val action = SCChartsFactory::eINSTANCE.createDuringAction
        state.localActions.add(action);
        action
    }

    // Create an immediate during action for a state.
    def DuringAction createImmediateDuringAction(State state) {
        val action = state.createDuringAction
        action.setImmediate(true);
        action
    }

    // Create a entry action for a state.
    def EntryAction createEntryAction(State state) {
        val action = SCChartsFactory::eINSTANCE.createEntryAction
        state.localActions.add(action);
        action
    }

    // Create an immediate entry action for a state.
    def EntryAction createImmediateEntryAction(State state) {
        val action = state.createEntryAction
        action.setImmediate(true);
        action
    }

    // Create a exit action for a state.
    def ExitAction createExitAction(State state) {
        val action = SCChartsFactory::eINSTANCE.createExitAction
        state.localActions.add(action);
        action
    }

    // Create an immediate exit action for a state.
    def ExitAction createImmediateExitAction(State state) {
        val action = state.createExitAction
        action.setImmediate(true);
        action
    }

    // Create a suspend action for a state.
    def SuspendAction createSuspendAction(State state) {
        val action = SCChartsFactory::eINSTANCE.createSuspendAction
        state.localActions.add(action);
        action
    }

    // Create an immediate suspend action for a state.
    def SuspendAction createImmediateSuspendAction(State state) {
        val action = state.createSuspendAction
        action.setImmediate(true);
        action
    }

    // Return all EntryAction actions of a state.
    def List<EntryAction> getEntryActions(State state) {
        state.localActions.filter(typeof(EntryAction)).toList
    }

    // Return all DuringAction actions of a state.
    def List<DuringAction> getDuringActions(State state) {
        state.localActions.filter(typeof(DuringAction)).toList
    }

    // Return all ExitAction actions of a state.
    def List<ExitAction> getExitActions(State state) {
        state.localActions.filter(typeof(ExitAction)).toList
    }

    // Return all SuspendAction actions of a state.
    def List<SuspendAction> getSuspendActions(State state) {
        state.localActions.filter(typeof(SuspendAction)).toList
    }

    //========== ASSIGNMENTS ============
   // Create a during action for a state.
    def Emission createEmission() {
        val emission = SCChartsFactory::eINSTANCE.createEmission
        emission
    }
    
    
    // Create an Assignment.
    def Assignment assign(ValuedObject valuedObject) {
        val assignment = SCChartsFactory::eINSTANCE.createAssignment()
        assignment.setValuedObject(valuedObject)
        assignment
    }

    // Create an Assignment and add it sequentially to an action's effects list.
    def Assignment createAssignment(Action action, ValuedObject valuedObject) {
        val assignment = valuedObject.assign
        action.addAssignment(assignment)
        assignment
    }

    // Create an Assignment and add it sequentially to an action's effects list.
    def Assignment addAssignment(Action action, Assignment assignment) {

        // An Assignment is a specialized Effect with a new value and a ValuedObject
        action.addEffect(assignment)
        assignment
    }

    // Create a valued Assignment. 
    def Assignment assign(ValuedObject valuedObject, Expression newValue) {
        val assignment = valuedObject.assign
        assignment.setValuedObject(valuedObject)
        assignment.setExpression(newValue);
        assignment
    }

    // Create a valued relative Assignment. 
    def Assignment assignRelative(ValuedObject valuedObject, Expression newValue) {
        valuedObject.assign(valuedObject.reference.or(newValue))
    }

    // Create a valued Assignment and add it sequentially to an action's effects list. 
    def Assignment createAssignment(Action action, ValuedObject valuedObject, Expression newValue) {
        val assignment = valuedObject.assign(newValue)
        action.addAssignment(assignment)
        assignment
    }

    //=========== EMISSIONS =============
    
    // Create a TextEffect.
    def TextEffect createTextEffect(String text) {
        val extEffect = SCChartsFactory::eINSTANCE.createTextEffect
        extEffect.setText(text)
        extEffect
    }
    
    // Create an Emission.
    def Emission emit(ValuedObject valuedObject) {
        val emission = SCChartsFactory::eINSTANCE.createEmission()
        emission.setValuedObject(valuedObject)
        emission
    }

    // Create an Emission and add it sequentially to an action's effects list.
    def Emission createEmission(Action action, ValuedObject valuedObject) {
        val emission = valuedObject.emit
        action.addEmission(emission)
        emission
    }

    // Create an Emission and add it sequentially to an action's effects list.
    def Emission addEmission(Action action, Emission emission) {

        // An Emission is a specialized Effect with a new value and a ValuedObject
        action.addEffect(emission)
        emission
    }

    // Create an Effect (supertype of Emission) and add it sequentially to an action's effects list.
    def Effect addEffect(Action action, Effect effect) {
        action.effects.add(effect)
        effect
    }

    // Create a valued Emission. 
    def Emission emit(ValuedObject valuedObject, Expression newValue) {
        val emission = valuedObject.emit
        emission.setValuedObject(valuedObject)
        emission.setNewValue(newValue);
        emission
    }

    // Create a valued Emission and add it sequentially to an action's effects list. 
    def Emission createEmission(Action action, ValuedObject valuedObject, Expression newValue) {
        val emission = valuedObject.emit(newValue)
        action.addEmission(emission)
        emission
    }

    //-------------------------------------------------------------------------
    //--                     K E X P R E S S I O N S                         --
    //-------------------------------------------------------------------------
    
    //==  EXPRESSION MODIFICATIONS  ==
    def void replace(Action action, Expression searchExpression, Expression replaceExpression) {
        action.setTrigger(action.trigger.replace(searchExpression, replaceExpression))
    }

    //=========  VALUED OBJECT  =========
    // Creates a new ValuedObject in a scope.
    def ValuedObject createValuedObject(Scope scope, String valuedObjectName) {
        val valuedObject = createValuedObject(valuedObjectName)
        scope.valuedObjects.add(valuedObject)
        valuedObject
    }

    //===========  VARIABLES  ===========
    // Creates a new variable ValuedObject in a Scope.
    def ValuedObject createVariable(Scope scope, String variableName) {
        scope.createValuedObject(variableName)
    }

    //============  SIGNALS  ============
    // Creates a new variable ValuedObject in a Scope.
    def ValuedObject createSignal(Scope scope, String variableName) {
        scope.createValuedObject(variableName).setIsSignal
    }


    //-------------------------------------------------------------------------
    //--                           N A M I N G S                             --
    //-------------------------------------------------------------------------
    // Prefixes a name with the hash code of an eObject
    def int id(EObject eObject) {
        eObject.hashCode
    }

    // Prefixes a name with the hash code of an eObject
    def String id(EObject eObject, String string) {
        string + eObject.id
    }

    // Prefixes a name with the generated prefix string
    def String id(String string) {
        GENERATED_PREFIX + string;
    }

    // For C variables it is necessary to remove special characters, this may lead
    // to name clashes in unlikely cases. 
    def String removeSpecialCharacters(String string) {
        if (string == null) {
            return null;
        }
        return string.replace("-", "").replace("_", "").replace(" ", "").replace("+", "").replace("#", "").
            replace("$", "").replace("?", "").replace("!", "").replace("%", "").replace("&", "").replace("[", "").
            replace("]", "").replace("<", "").replace(">", "").replace(".", "").replace(",", "").replace(":", "").
            replace(";", "").replace("=", "");
    }

    // This helper method returns the hierarchical name of a state considering all hierarchical
    // higher states. A string is formed by the traversed state IDs.
    def String getHierarchicalName(State state) {
        state.getHierarchicalName(null)
    }

    def String getHierarchicalName(State state, String StartSymbol) {
        if (state.parentRegion != null) {
            if (state.parentRegion.parentState != null) {
                var higherHierarchyReturnedName = state.parentRegion.parentState.getHierarchicalName(StartSymbol);
                var regionId = state.parentRegion.id.removeSpecialCharacters;
                var stateId = state.id.removeSpecialCharacters;

                // Region IDs can be empty, state IDs normally aren't but the code generation handles 
                // also this case. 
                if (stateId.nullOrEmpty) {
                    stateId = state.id
                }
                if (regionId.nullOrEmpty) {
                    regionId = "R" + (state.parentRegion.parentState.regions.indexOf(state.parentRegion) + 1)
                }
                if (!higherHierarchyReturnedName.nullOrEmpty) {
                    higherHierarchyReturnedName = higherHierarchyReturnedName + "_";
                }
                if (state.parentRegion.parentState.regions.size > 1) {
                    return higherHierarchyReturnedName + regionId + "_" + stateId;
                } else {

                    // this is the simplified case, where there is just one region and we can
                    // omit the region id
                    return higherHierarchyReturnedName + stateId;
                }
            }
        }
        if (StartSymbol != null) {
            return StartSymbol; // +  "_";
        }
        return ""
    }


    //-------------------------------------------------------------------------
    //--  F I X   F O R   T E R M I N A T I O N S   / W    E F F E C T S     --
    //-------------------------------------------------------------------------
    // This fixes termination transitions that have effects
    def State fixTerminationWithEffects(State rootState) {
        val terminationTransitions = rootState.allContainedTransitions.filter[type == TransitionType::TERMINATION].filter[!effects.nullOrEmpty]
        
        for (terminationTransition : terminationTransitions) {
            val originalSource = terminationTransition.sourceState
            val originalTarget = terminationTransition.targetState
            val region = originalSource.parentRegion
            val auxiliaryState = region.createState("_TE").uniqueName
            val auxliiaryTransition = auxiliaryState.createImmediateTransitionTo(originalTarget)
            for (effect : terminationTransition.effects.immutableCopy) {
                auxliiaryTransition.addEffect(effect)
            }
            terminationTransition.setTargetState(auxiliaryState)
        }
        rootState
    }
    

    //-------------------------------------------------------------------------
    //--                F I X   F O R   H A L T   S T A T E S                --
    //-------------------------------------------------------------------------
    // This fixes halt states and adds an explicit delayed self transition
    def State fixPossibleHaltStates(State rootState) {
        val haltStates = rootState.allContainedStates.filter[!hasInnerStatesOrRegions && outgoingTransitions.nullOrEmpty && !final]
        
        for (haltState : haltStates) {
            haltState.createTransitionTo(haltState)
        }
        rootState
    }



    //-------------------------------------------------------------------------
    //--                F I X   F O R   D E A D    C O D E                   --
    //-------------------------------------------------------------------------
    // This fixes halt states and adds an explicit delayed self transition
    def State fixDeadCode(State rootState) {
        val nonReachabledStates = rootState.allContainedStates.filter[!isStateReachable].toList
        
        for (nonReachabledState : nonReachabledStates.immutableCopy) {
            val parentRegion = (nonReachabledState.eContainer as Region)
            parentRegion.states.remove(nonReachabledState)
        }
        rootState
    }
    def  boolean isStateReachable(State originalState) {
        // Must ensure not to loop forever when having cycles in the model
        val visited = new ArrayList<State>()
        isStateReachable(originalState,  originalState, visited)
    }
    
    def  boolean isStateReachable(State originalState, State state, List<State> visited) {
        if (visited.contains(state) || state == null) {
            return false
        }
        visited.add(state);
        if (originalState.parentRegion == null) {
            // Root states ARE reachable
            return true
        }
        if (state.isInitial()) {
            return true
        }
        else {
            for (Transition transition : state.getIncomingTransitions()) {
                    if (isStateReachable(originalState, transition.getSourceState(), visited)) {
                            return true
                    }
            }
        }
        return false
    }


    //-------------------------------------------------------------------------
    //--               L O C A L   V A L U E D O B J E C T S                 --
    //-------------------------------------------------------------------------
    
    def State transformLocalValuedObject(State rootState) {
        val targetRootState = rootState.copy;

        // Traverse all states
        for (targetState : targetRootState.getAllContainedStates) {
            targetState.transformExposeLocalValuedObject(targetRootState, false);
        }
        targetRootState;
    }
    
    // Traverse all states and transform possible local valuedObjects.
    def void transformExposeLocalValuedObject(State state, State targetRootState, boolean expose) {

        // EXPOSE LOCAL SIGNALS: For every local valuedObject create a global valuedObject
        // and wherever the local valuedObject is emitted, also emit the new global 
        // valuedObject.
        // Name the new global valuedObjects according to the local valuedObject's hierarchy. 
        // Exclude the top level state
        if (state == targetRootState) {
            return;
        }

        // There are local valuedObjects, raise them
        if (state.valuedObjects != null && state.valuedObjects.size > 0) {
            val hierarchicalStateName = state.getHierarchicalName("LOCAL");

            for (ValuedObject localValuedObject : ImmutableList::copyOf(state.valuedObjects)) {
                val newValuedObjectName = hierarchicalStateName + "_" + localValuedObject.name
                
                // Possibly expose
                if (expose) {
                    localValuedObject.setIsOutput
                }

                // Relocate
                targetRootState.valuedObjects.add(localValuedObject)
                
                // Rename
                if (expose) {
                    localValuedObject.setName(newValuedObjectName)
                } else {
                    localValuedObject.uniqueName
                }
                
            }
        } // end if local valuedObjects present

    }

    // -------------------------------------------------------------------------   
    
    def void replaceAllReferences(Scope scope, ValuedObject valuedObject, Expression expression) {
    	for(obj : scope.eAllContents.toList.immutableCopy) {
    		if (obj instanceof ValuedObjectReference
    			&& (obj as ValuedObjectReference).valuedObject == valuedObject
    		) 
    		{
    			obj.replace(expression)
    		}
    	}
    }

    def void replaceAllReferencesWithCopy(Scope scope, ValuedObject valuedObject, Expression expression) {
    	for(obj : scope.eAllContents.toList.immutableCopy) {
    		if (obj instanceof ValuedObjectReference
    			&& (obj as ValuedObjectReference).valuedObject == valuedObject
    		) 
    		{
    			obj.replace(expression.copy)
    		}
    	}
    }
    
    def void replaceAllOccurrences(Scope scope, ValuedObject valuedObject, ValuedObject replacement) {
        val relevantObjects = scope.eAllContents.filter(e | e instanceof ValuedObjectReference || 
                                                            e instanceof Assignment ||
                                                            e instanceof Emission ||
                                                            e instanceof Binding
        ).toList.immutableCopy;
    	for(obj : relevantObjects) {
    		if (obj instanceof ValuedObjectReference
    			&& (obj as ValuedObjectReference).valuedObject == valuedObject
    		)  {
                val valuedObjectReference = (obj as ValuedObjectReference)
    		    val valuedObjectReferenceCopy = valuedObjectReference.copy;
    		    val replacementValuedObjectReference = replacement.reference;
    			obj.replace(replacementValuedObjectReference)
      			replacementValuedObjectReference.indices.clear
      			for (index : valuedObjectReferenceCopy.indices) {
                    replacementValuedObjectReference.indices.add(index.copy);
      			}
    		}

    		else if (obj instanceof Assignment && (obj as Assignment).valuedObject == valuedObject)  {
    		    val assignment = (obj as Assignment)
                val assignmentCopy = assignment.copy;
				assignment.valuedObject = replacement;
                assignment.indices.clear
                for (index : assignmentCopy.indices) {
                    assignment.indices.add(index.copy);
                }
    		}

    		else if (obj instanceof Emission && (obj as Emission).valuedObject == valuedObject)  {
				(obj as Emission).valuedObject = replacement;
    		}

    		else if (obj instanceof Binding) {
    			if ((obj as Binding).formal == valuedObject) (obj as Binding).formal = replacement
    			if ((obj as Binding).actual == valuedObject) (obj as Binding).actual = replacement
    		}
    		
    	}
    } 
    
    def ValuedObject findValuedObjectByName(Declaration declaration, String name) {
    	if (declaration.valuedObjects.filter[ it.name == name].size>0)
    		declaration.valuedObjects.filter[ it.name == name].head
    	else null
    }
    
    def ValuedObject findValuedObjectByName(Scope scope, String name) {
    	var EObject container = scope
    	while (container != null) {
    		var EList<Declaration> declarations = null
    		if (container instanceof State) declarations = (container as State).declarations
    		else if (container instanceof Region) declarations = (container as Region).declarations
    		if (!declarations.nullOrEmpty) for (declaration : declarations) {
    			val valuedObject = declaration.findValuedObjectByName(name)
    			if (valuedObject != null) return valuedObject
    		} 
    		container = container.eContainer
    	}
    	null
    }
    
    
    def State copyState(State state) {
    	createState(state.id) => [ newState |
    		newState.label = state.label
    		newState.type = state.type
    		newState.initial = state.initial
    		newState.^final = state.^final
    		newState.regions += state.regions.copyAll
    		newState.outgoingTransitions += state.outgoingTransitions.copyAll
    		newState.incomingTransitions += state.incomingTransitions.copyAll
    		newState.localActions += state.localActions.copyAll
    		newState.referencedScope = state.referencedScope
    		newState.bindings += state.bindings.copyAll
    		newState.declarations += state.declarations.copyAll
    		newState.^for = state.^for.copy
    		newState.annotations += state.annotations.copyAll
    		
    		// Fix valued object references
    		state.valuedObjects.forEach[
    			val newValuedObject = newState.findValuedObjectByName(it.name)
    			if (newValuedObject != null) {
    				newState.replaceAllOccurrences(it, newValuedObject)
   				}
    		]
    	]
    }
}
