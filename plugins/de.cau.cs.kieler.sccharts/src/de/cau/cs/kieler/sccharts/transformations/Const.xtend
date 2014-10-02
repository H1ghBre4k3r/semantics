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
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.core.kexpressions.TextExpression
import de.cau.cs.kieler.core.kexpressions.IntValue
import de.cau.cs.kieler.core.kexpressions.BoolValue
import de.cau.cs.kieler.core.kexpressions.FloatValue
import de.cau.cs.kieler.core.kexpressions.DoubleValue

/**
 * SCCharts Const Transformation.
 * 
 * @author ssm
 * @kieler.design 2014-05-22 proposed 
 * @kieler.rating 2014-05-22 proposed yellow
 */
class Const {

	@Inject
	extension AnnotationsExtensions

    @Inject
    extension KExpressionsExtension

    @Inject
    extension SCChartsExtension

    // This prefix is used for naming of all generated signals, states and regions
    static public final String GENERATED_PREFIX = "_"
    
    static private final String HOSTCODE_ANNOTATION = "alterHostcode"

    //-------------------------------------------------------------------------
    //--                           C O N S T                                 --
    //-------------------------------------------------------------------------
    // ...
    def State transform(State rootState) {
        var targetRootState = rootState.fixAllPriorities;

        // Traverse all states
        for (states : targetRootState.getAllStates.immutableCopy) {
            states.transformConst
        }
        targetRootState;
    }

    def void transformConst(State state) {
        val constObjects = state.valuedObjects.filter[
        	isConst && initialValue != null
        ].toList
        
        for (const : constObjects.toList.immutableCopy) {
        	val replacement = const.initialValue
        	state.replaceAllReferencesWithCopy(const, replacement.copy)
        	if (const.declaration.hasAnnotation(HOSTCODE_ANNOTATION)) {
        		state.eAllContents.filter(typeof(TextExpression)).forEach[
        			var replacementString = ""
        			if (replacement instanceof IntValue) replacementString = (replacement as IntValue).value.toString
        			else if (replacement instanceof BoolValue) replacementString = (replacement as BoolValue).value.toString
        			else if (replacement instanceof FloatValue) replacementString = (replacement as FloatValue).value.toString
        			else if (replacement instanceof DoubleValue) replacementString = (replacement as DoubleValue).value.toString
        			else if (replacement instanceof TextExpression) replacementString = (replacement as TextExpression).text
        			text = text.replaceAll(const.name, replacementString)
        		]
        	}
        	const.delete
		}  
		      	
    }

}
