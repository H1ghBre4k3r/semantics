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

import com.google.common.collect.Sets
import com.google.inject.Inject
import de.cau.cs.kieler.kicool.compilation.ProcessorType
import de.cau.cs.kieler.sccharts.processors.SCChartsProcessor
import de.cau.cs.kieler.kicool.kitt.tracing.Traceable
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.featuregroups.SCChartsFeatureGroup
import de.cau.cs.kieler.sccharts.features.SCChartsFeature
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.extensions.SCChartsScopeExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsCoreExtensions

/**
 * SCCharts Static Transformation.
 * 
 * @author cmot
 * @kieler.design 2013-09-05 proposed 
 * @kieler.rating 2013-09-05 proposed yellow
 */
class Static extends SCChartsProcessor implements Traceable {

    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        "de.cau.cs.kieler.sccharts.processors.static"
    }
    
    override getName() {
        "Static"
    }
 
    override process() {
        setModel(model.transform)
    }


//    override getExpandsFeatureId() {
//        return SCChartsFeature::STATIC_ID
//    }
//
//    override getProducesFeatureIds() {
//        return Sets.newHashSet(SCChartsFeature::INITIALIZATION_ID)
//    }
//
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCChartsFeatureGroup::EXPANSION_ID)
//    }

    // -------------------------------------------------------------------------
    @Inject extension SCChartsCoreExtensions
    @Inject extension SCChartsScopeExtensions
    @Inject extension KExpressionsDeclarationExtensions    
    @Inject extension ValuedObjectRise
    
    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"

    // -------------------------------------------------------------------------
    // --                          S T A T I C                                --
    // -------------------------------------------------------------------------
    // In some local superstate M that has a declaration of a static variable x, move the
    // declaration of the variable to the root state of the SCChart and rename x respecting
    // a proper unique and qualified naming. Within the scope of x (within M) update all
    // references (accesses) to x to the new name. Remove the static keyword from the declaration.
    // This is applied for all superstates that contain static variable declarations.
    //
    def State transform(State rootState) {
        rootState.transformValuedObjectRise
        
        // Traverse all states
        for (targetTransition : rootState.getAllStates.toList) {
            targetTransition.transformStatic(rootState);
        }
        rootState
    }

    def void transformStatic(State state, State targetRootState) {
        val staticDeclarations = state.variableDeclarations.filter[isStatic]
        for (staticDeclaration : staticDeclarations.toList) {
            for (staticValuedObject : staticDeclaration.valuedObjects) {
                staticValuedObject.setName(state.getHierarchicalName(GENERATED_PREFIX) + GENERATED_PREFIX +
                    staticValuedObject.name)
            }
            staticDeclaration.static = false
            targetRootState.declarations += staticDeclaration
        }
    }
    
    def SCCharts transform(SCCharts sccharts) {
        sccharts => [ rootStates.forEach[ transform ] ]
    }
    

}