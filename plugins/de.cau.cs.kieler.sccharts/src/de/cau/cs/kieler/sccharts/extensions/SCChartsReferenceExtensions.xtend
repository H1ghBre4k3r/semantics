/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.sccharts.extensions

import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.ControlflowRegion
import com.google.inject.Inject

import static extension de.cau.cs.kieler.kitt.tracing.TracingEcoreUtil.*
import de.cau.cs.kieler.kexpressions.kext.extensions.KExtDeclarationExtensions
import java.util.List
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.sccharts.Scope

/**
 * @author ssm
 * @kieler.design 2017-07-05 proposed 
 * @kieler.rating 2017-07-05 proposed yellow 
 *
 */
class SCChartsReferenceExtensions {
    
    @Inject extension KExpressionsDeclarationExtensions
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension KExtDeclarationExtensions
    @Inject extension SCChartsScopeExtensions
    @Inject extension SCChartsControlflowRegionExtensions
    @Inject extension SCChartsStateExtensions
    @Inject extension SCChartsSerializeHRExtensions
    
    def List<Binding> createBindings(Scope scope) {
        val bindings = <Binding> newArrayList
        val bound = <ValuedObject> newHashSet
        
        if (scope.reference == null) return bindings
        
        val targetState = scope.reference.scope
        val parameters = scope.reference.parameters
        
        if (targetState == null) return bindings
        
        val targetVOs = <ValuedObject> newArrayList
        for (declaration : targetState.variableDeclarations.filter[ input || output]) {
            targetVOs += declaration.valuedObjects
        }

        // explicit bindings
        var targetVOPtr = 0
        for (parameter : parameters) {
            val binding = new Binding => [
                sourceExpression = parameter.expression
            ]
            
            if (parameter.explicitBinding != null) {
                val tmpPtr = targetVOs.indexOf(parameter.explicitBinding)
                if (tmpPtr == -1) {
                    binding.addErrorMessage("Explicit binding could not be resolved! The valued object is unknown! " + 
                        parameter.explicitBinding.toString
                    )    
                } else {
                    targetVOPtr = tmpPtr
                }
            } else {
                binding.type = BindingType.ORDER
            }
            
            if (binding.errors == 0) {
                
                if (targetVOPtr > targetVOs.size - 1) {
                    binding.addErrorMessage("A Valued Object is supposed to be bound, but there is no free Valued Object left in the referenced scope: " + 
                        parameter.expression.serializeHR
                    )
                } else {
                    binding.targetValuedObject = targetVOs.get(targetVOPtr)
                    binding.targetIndices = parameter.explicitBindingIndices
                    bound += binding.targetValuedObject
                    
                    targetVOPtr++
                }
            }
            bindings += binding
            
        }

        
        // implicit bindings
        if (bound.size < targetVOs.size) {
            
            val voNameMap = scope.valuedObjectNameMap
            
            for (vo : targetVOs) {
                if (!bound.contains(vo)) {
                    val binding = new Binding => [
                        targetValuedObject = vo
                        type = BindingType.IMPLICIT
                    ]
                    
                    if (voNameMap.containsKey(vo.name)) {
                        val sourceVO = voNameMap.get(vo.name)
                        binding.sourceExpression = sourceVO.reference
                    } else {
                        binding.addErrorMessage("Valued object in the referenced scope was not bound properly: " + vo.name)
                    }
                    bindings += binding
                                        
                }
            }        
        }
        
        bindings
    }
}