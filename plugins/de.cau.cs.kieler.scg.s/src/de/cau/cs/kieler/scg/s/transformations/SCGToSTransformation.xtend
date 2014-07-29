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
package de.cau.cs.kieler.scg.s.transformations

import com.google.inject.Inject
import de.cau.cs.kieler.core.kexpressions.Expression
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.s.s.Instruction
import de.cau.cs.kieler.s.s.Program
import de.cau.cs.kieler.s.s.SFactory
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.extensions.SCGExtensions
import java.util.HashMap
import java.util.List

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.kexpressions.TextExpression
import de.cau.cs.kieler.s.extensions.SExtension
import de.cau.cs.kieler.core.kexpressions.FunctionCall

/**
 * Transform SCG to S
 * 
 * @author ssm
 * @kieler.design 2014-01-08 proposed 
 * @kieler.rating 2014-01-08 proposed yellow
 *  
 */
class SCGToSTransformation {
    
    @Inject
    extension KExpressionsExtension
    
    @Inject
    extension SCGExtensions

    @Inject
    extension SExtension
    
    private static val GOGUARDNAME = "_GO"
    
    private val valuedObjectMapping = new HashMap<ValuedObject, ValuedObject>
    private val processedNodes = <Node> newArrayList
    
    private val nodeList = <Node> newArrayList
    
	def Program transformSCGToS(SCGraph scg) {
		val Program sProgram = SFactory::eINSTANCE.createProgram
		sProgram.priority = 1
		sProgram.name = "S"
		
        val timestamp = System.currentTimeMillis
          
//        for(typeGroup : scg.typeGroups) {
//            val newTypeGroup = createTypeGroup(typeGroup)
            for (valuedObject : scg.valuedObjects) {
//                val newValuedObject = createValuedObject(valuedObject.name).setTypeBool
                val newValuedObject = valuedObject.copy
                newValuedObject.applyAttributes(valuedObject)
            	sProgram.valuedObjects.add(newValuedObject)
	            valuedObjectMapping.put(valuedObject, newValuedObject)
            }
//            sProgram.typeGroups += newTypeGroup 
//        }		
				
        var time = (System.currentTimeMillis - timestamp) as float
        System.out.println("Preparation for S transformation finished (time used: "+(time / 1000)+"s).")  
        
        val tickState = SFactory::eINSTANCE.createState => [
            name = "Tick"
            sProgram.states += it
        ]
        
		nodeList += scg.nodes.head
		
		while(!nodeList.empty) {
			val node = nodeList.head
			nodeList.remove(0)
			node.transform(tickState.instructions)
		}
		
        time = (System.currentTimeMillis - timestamp) as float
        System.out.println("S transformation finished (time used overall: "+(time / 1000)+"s).")  
		
		sProgram
	}
	
	private def dispatch void transform(Entry entry, List<Instruction> instructions) {
	   processedNodes += entry
	   if (entry.next != null) nodeList += entry.next.target 
	}

    private def dispatch void transform(Exit exit, List<Instruction> instructions) {
    }
	
	private def dispatch void transform(Assignment assignment, List<Instruction> instructions) {
	    if (processedNodes.contains(assignment)) return;
        processedNodes += assignment
        
        if (assignment.valuedObject != null && assignment.assignment != null) {
	    	val sAssignment = SFactory::eINSTANCE.createAssignment
	    	sAssignment.variable = valuedObjectMapping.get(assignment.valuedObject)
	    	val expression = assignment.assignment.copyExpression.fix.fixHostCode
            sAssignment.expression = expression
            for(index : assignment.indices) { sAssignment.indices += index.copyExpression }
	    	instructions += sAssignment
    	} else if (assignment.assignment instanceof TextExpression) {
    	     // This is the case when the valuedObject is null
    	     val hostCode = (assignment.assignment as TextExpression).text //.copy.fixHostCode as TextExpression
    	     instructions += hostCode.createHostCode
    	} else if (assignment.assignment instanceof FunctionCall) {
    	    val sAssignment = SFactory::eINSTANCE.createAssignment
    	    sAssignment.expression = assignment.assignment.copyExpression.fix
    	    instructions += sAssignment
    	}
	    
	    if (assignment.next != null) assignment.next.target.transform(instructions)
	}
	
	private def dispatch void transform(Conditional conditional, List<Instruction> instructions) {
        processedNodes += conditional
        
        val sIf = SFactory::eINSTANCE.createIf
        sIf.expression = conditional.condition.copyExpression
        instructions += sIf
        
        if (conditional.^else != null) conditional.^else.target.transform(instructions)     
        if (conditional.then != null) conditional.then.target.transform(sIf.instructions)        
	}
	
	def ValuedObject findValuedObjectByName(Program s, String name) {
	    for (valuedObject : s.valuedObjects) {
	        if (valuedObject.name.equals(name)) {
	            return valuedObject
	        }
	    }
   		return null
    }    
    
    def ValuedObject getValuedObjectCopy(ValuedObject valuedObject) {
        valuedObjectMapping.get(valuedObject)
    }
    
    def Expression copyExpression(Expression expression) {
        // Use the ecore utils to copy the expression. 
        val newExpression = expression.copy
        
        if (newExpression instanceof ValuedObjectReference) {
            // If it is a single object reference, simply replace the reference with the object of the target SCG.
            (newExpression as ValuedObjectReference).valuedObject = 
                (expression as ValuedObjectReference).valuedObject.getValuedObjectCopy                    
        } else {
            // Otherwise, query all references in the expression and replace the object with the new copy
            // in the target SCG.
            newExpression.eAllContents.filter(typeof(ValuedObjectReference)).
                forEach[ valuedObject = valuedObject.getValuedObjectCopy ]        
        }
        
        // Return the new expression.
        newExpression
    }
	
}