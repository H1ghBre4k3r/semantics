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
package de.cau.cs.kieler.sccharts.processors.transformators

import com.google.inject.Inject
import de.cau.cs.kieler.annotations.extensions.UniqueNameCache
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsComplexCreateExtensions
import de.cau.cs.kieler.kexpressions.keffects.Assignment
import de.cau.cs.kieler.kexpressions.keffects.Emission
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.kicool.compilation.ProcessorType
import de.cau.cs.kieler.kicool.kitt.tracing.Traceable
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.sccharts.extensions.SCChartsActionExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsControlflowRegionExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsScopeExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsStateExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsTransformationExtension
import de.cau.cs.kieler.sccharts.extensions.SCChartsTransitionExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsUniqueNameExtensions
import de.cau.cs.kieler.sccharts.processors.SCChartsProcessor
import java.util.HashMap
import java.util.Iterator
import java.util.List


import static extension de.cau.cs.kieler.kicool.kitt.tracing.TracingEcoreUtil.*
import static extension de.cau.cs.kieler.kicool.kitt.tracing.TransformationTracing.*

/**
 * SCCharts Pre Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Pre extends SCChartsProcessor implements Traceable {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        "de.cau.cs.kieler.sccharts.processors.transformators.pre"
    }
    
    override getName() {
        "Pre"
    }
 
    override process() {
        setModel(model.transform)
    }

    
//    override getExpandsFeatureId() {
//        return SCChartsFeature::PRE_ID
//    }
//
//    override getProducesFeatureIds() {
//        return Sets.newHashSet(SCChartsFeature::COMPLEXFINALSTATE_ID, SCChartsFeature::INITIALIZATION_ID)
//    }
//
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCChartsFeatureGroup::EXPANSION_ID, SCChartsFeature::SIGNAL_ID)
//    }

    //-------------------------------------------------------------------------

    @Inject extension KExpressionsComplexCreateExtensions
    @Inject extension KEffectsExtensions
    @Inject extension SCChartsScopeExtensions
    @Inject extension SCChartsControlflowRegionExtensions
    @Inject extension SCChartsStateExtensions
    @Inject extension SCChartsActionExtensions
    @Inject extension SCChartsTransitionExtensions
    @Inject extension SCChartsUniqueNameExtensions    
    @Inject extension SCChartsTransformationExtension
    
    private val nameCache = new UniqueNameCache

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    //-------------------------------------------------------------------------
    //--                        P R E -  O P E R A T O R                     --
    //-------------------------------------------------------------------------
    // Transforming PRE Operator.
    def State transform(State rootState) {
        nameCache.clear
                
        val targetRootState = rootState

        // Traverse all states
        for (targetState : targetRootState.getAllStates.toList) {
            targetState.transformPre(targetRootState);
        }

        targetRootState
    }

    // Traverse all states that might declare a valuedObject that is used with the PRE operator
    def void transformPre(State state, State targetRootState) {
        state.setDefaultTrace
        
        // If the state has outgoing terminations, we need to finalize the during
        // actions in case we end the states over these transitions
        val outgoingTerminations = state.outgoingTransitions.filter[ isTermination ]
        val hasOutgoingTerminations = outgoingTerminations.length > 0
        val complexPre = ((hasOutgoingTerminations || state.isRootState) && state.regionsMayTerminate)        

        // The transition with the assignment of the pre variables
        var HashMap<String, Transition> transitions = newHashMap();
        var Transition transInitWait
        
        // This list keeps track of the very last auxiliary pre variable of a nested pre
        // (e.g. some 'int _pre__pre__pre_x') to remove its initialization as optimization.
        var List<ValuedObject> lastPreVariablesOfNestedPre = newArrayList()
        // Filter all valuedObjects and retrieve those that are referenced
        val allActions = state.eAllContents.filter(typeof(Action)).toList
        var hasPre = allActions.map[ eAllContents ].exists[ filter(OperatorExpression).exists[ operator == OperatorType.PRE ] ]
//        hasPre = true
        val allValuedObjects = state.declarations.map[valuedObjects].flatten.toList
        var List<ValuedObject> allPreValuedObjects = null
        // Repeat the next steps until no pre occurs anymore (in case of nested pre)
        if (hasPre)
        do {
//            allPreValuedObjects = allValuedObjects.filter(
//                valuedObject|
//                    allActions.filter(
//                        action|
//                            action.getPreExpression(valuedObject).hasNext ||
//                                action.getPreValExpression(valuedObject).hasNext).size > 0).toList

            // This is a performance workaround.
            // You should really restructure the whole transformation and get rid of all the lists and eAllContent calls.
            // However, even this hot fix is twice as fast.
            allPreValuedObjects = allValuedObjects.filter[ vo |
                allActions.map[ eAllContents ].exists[ filter(OperatorExpression).filter[ operator == OperatorType.PRE ].
                map[ eAllContents ].exists[ filter(ValuedObjectReference).exists[ valuedObject == vo ] ] ]
            ].toList
    
            // Remove pre statement
    		for (preValuedObject : allPreValuedObjects) {
    		    // Is the valued object a variable that was introduced by the pre trafo itself?
    		    val name = preValuedObject.name
                val isValuedObjectOfNestedPre = name.startsWith(GENERATED_PREFIX + "pre")
                
                // Tracing
                preValuedObject.setDefaultTrace
                
                // Initialize sccharts elements that are used instead pre
                if(!isValuedObjectOfNestedPre) {
                    allPreValuedObjects.setDefaultTrace
                    
                    val preRegion = state.createControlflowRegion(GENERATED_PREFIX + "Pre").uniqueName(nameCache)
                    val preInit = preRegion.createInitialState(GENERATED_PREFIX + "Init").uniqueName(nameCache)
                    val preWait = preRegion.createState(GENERATED_PREFIX + "Wait").uniqueName(nameCache)
                    if (complexPre) {
                        preWait.setFinal
                        preInit.setFinal
                    }

                    // Immediate transition for assignments
                    transInitWait = preInit.createImmediateTransitionTo(preWait)
                    transitions.put(name, transInitWait);
                    // Delayed transition back to init state
                    preWait.createTransitionTo(preInit);
                } else {
                    transInitWait = transitions.get(name.replaceAll(GENERATED_PREFIX + "pre" + GENERATED_PREFIX, ""));
                }
                
                // New register variable
                var ValuedObject newAux
                if(!isValuedObjectOfNestedPre) {
                    newAux = state.createVariable(GENERATED_PREFIX + "reg" + GENERATED_PREFIX 
                        + preValuedObject.name).setType(preValuedObject.getType).uniqueName(nameCache)
                    newAux.copyAttributes(preValuedObject)
                    newAux.setDefaultValue()                    
                }
                // New pre variable
                val newPre = state.createVariable(GENERATED_PREFIX + "pre" + GENERATED_PREFIX 
                    + preValuedObject.name).setType(preValuedObject.getType).uniqueName(nameCache)
                newPre.copyAttributes(preValuedObject)
                
                if(isValuedObjectOfNestedPre) {
                    transInitWait.addEffectBefore(newPre.createAssignment(preValuedObject.reference))
                    newPre.initialValue = preValuedObject.reference
                } else {
                    transInitWait.addEffectBefore(newAux.createAssignment(preValuedObject.reference))
                    transInitWait.addEffectBefore(newPre.createAssignment(newAux.reference))                    
                    newPre.initialValue = newAux.reference
                }

                // The previous pre was not the last one.
                lastPreVariablesOfNestedPre.remove(preValuedObject)
                // This could be the last auxiliary pre variable.
                lastPreVariablesOfNestedPre.add(newPre)
    
                // Replace the ComplexExpression Pre(S) by the ValuedObjectReference PreS in all actions            
                // Replace the ComplexExpression Pre(?S) by the OperatorExpression ?PreS in all actions            
                for (action : allActions) {
                    val preExpressions = action.getPreExpression(preValuedObject);
                    val preValExpressions = action.getPreValExpression(preValuedObject);
    
                    while (preExpressions.hasNext) {
                        val preExpression = preExpressions.next
                        val container = preExpression.eContainer;
    
                        if (container instanceof OperatorExpression) {
    
                            // If nested PRE or PRE inside another complex expression
                            val i = (container as OperatorExpression).subExpressions.indexOf(preExpression)
                            (container as OperatorExpression).subExpressions.remove(preExpression);
                            (container as OperatorExpression).subExpressions.add(i, newPre.reference);
                        } else if (container instanceof Action) {
    
                            // If PRE directly a trigger
                            (container as Action).setTrigger(newPre.reference)
                        } else if (container instanceof Assignment) {
    
                            // If PRE directly a assigned value
                            (container as Assignment).expression = newPre.reference
                        } else if (container instanceof Emission) {
    
                            // If PRE directly an emitted value
                            (container as Emission).newValue = newPre.reference
                        }
                    }
    
                    while (preValExpressions.hasNext) {
                        val preValExpression = preValExpressions.next
                        val container = preValExpression.eContainer;
    
                        if ((!preValExpression.subExpressions.nullOrEmpty) &&
                            preValExpression.subExpressions.get(0) instanceof OperatorExpression &&
                            (preValExpression.subExpressions.get(0) as OperatorExpression).operator == OperatorType::VAL) {
    
                            // Transform pre(?V) --> ?PreV
                            val valueExpression = preValExpression.subExpressions.get(0);
                            (valueExpression as OperatorExpression).subExpressions.remove(0);
                            (valueExpression as OperatorExpression).add(newPre.reference);
                            if (container instanceof Emission) {
                                (container as Emission).setNewValue(valueExpression.copy);
                            } else if (container instanceof OperatorExpression) {
    
                                // If nested PRE or PRE inside another complex expression
                                (container as OperatorExpression).subExpressions.remove(preValExpression);
                                (container as OperatorExpression).add(valueExpression.copy);
                            }
                        }
    
                    }
                }
    
            }
        
        }while(!allPreValuedObjects.isNullOrEmpty)
        
        // Remove initialization of last auxilary pre variable as optimization.
        // (It will be initialized anyway as part of the transition.)
        for(valuedObject : lastPreVariablesOfNestedPre) {
            valuedObject.initialValue = null
        }
    }
    
    def SCCharts transform(SCCharts sccharts) {
        sccharts => [ rootStates.forEach[ transform ] ]
    }
    
    //-------------------------------------------------------------------------
    
    // Return a list of Pre Expressions for an action that references the valuedObject
    def Iterator<OperatorExpression> getPreExpression(Action action, ValuedObject valuedObject) {
        //val List<OperatorExpression> returnPreExpressions = <OperatorExpression>newLinkedList
        val preExpressions = action.eAllContents.filter(typeof(OperatorExpression)).filter(
            e|
                (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                    (e.subExpressions.get(0) instanceof ValuedObjectReference) &&
                    ((e.subExpressions.get(0) as ValuedObjectReference).valuedObject == valuedObject)
        )
        preExpressions
    }

    // Return a list of Pre Expressions for an action that references the value of a valuedObject
    def Iterator<OperatorExpression> getPreValExpression(Action action, ValuedObject valuedObject) {
        //val List<OperatorExpression> returnPreValExpressions = <OperatorExpression>newLinkedList
        val preValExpressions = action.eAllContents.filter(typeof(OperatorExpression)).filter(
            e|
                (e.operator == OperatorType::PRE) && (e.subExpressions.size() == 1) &&
                    (e.subExpressions.get(0) instanceof OperatorExpression) &&
                    ((e.subExpressions.get(0) as OperatorExpression).operator == OperatorType::VAL) &&
                    ((e.subExpressions.get(0) as OperatorExpression).subExpressions.size() == 1) &&
                    ((e.subExpressions.get(0) as OperatorExpression).subExpressions.get(0) instanceof ValuedObjectReference) && (((e.
                        subExpressions.get(0) as OperatorExpression).subExpressions.get(0) as ValuedObjectReference).
                        valuedObject == valuedObject)
        )
        preValExpressions
    }

}
