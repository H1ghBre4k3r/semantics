/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.scg.processors.codegen.smv

import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.kicool.compilation.VariableStore
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.ControlFlow
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * @author aas
 * 
 */
class SmvCodeGeneratorAssignModule extends SmvCodeGeneratorModuleBase {

    @Inject extension KEffectsExtensions
    @Inject extension SCGControlFlowExtensions

    private val nodeToParentConditional = <Node, ConditionalTree>newHashMap
    private val valuedObjectToAssignments = <ValuedObject, List<Assignment>>newHashMap
    private val nodeStack = <Node> newLinkedList
    
    private static class ConditionalTree {
        @Accessors var Conditional conditional
        @Accessors var boolean branchOfConditional
        @Accessors var ConditionalTree parent
        new(ConditionalTree parent, boolean branchOfParent, Conditional conditional) {
            this.parent = parent
            this.branchOfConditional = branchOfParent
            this.conditional = conditional
        }
        
        override toString() {
            if(parent !== null) {
                return '''ConditionalTree@«hashCode»(«branchOfConditional» branch of «conditional.toString», parent:ConditionalTree@«parent.hashCode»)'''
            } else {
                return '''ConditionalTree@«hashCode»(«branchOfConditional» branch of «conditional.toString», no parent)'''
            }
        }
    }
    
    override getName() {
        return class.simpleName;
    }

    override generateInit() {
        val processedNodes = <Node>newHashSet
        nodeStack.clear
        nodeToParentConditional.clear
        valuedObjectToAssignments.clear
        
        nodeStack.add(scg.nodes.head)
        while(!nodeStack.isEmpty) {
            val node = nodeStack.pop
            if (!processedNodes.contains(node)) {
                node.handleConditionalNestingInInit
                node.generateInInit
                processedNodes.add(node)    
            }
        }
    }

    protected def dispatch void generateInInit(Assignment assignment) {
        if (assignment.valuedObject !== null) {
            var assignments = valuedObjectToAssignments.get(assignment.valuedObject)
            if(assignments === null) {
                assignments = newLinkedList
                valuedObjectToAssignments.put(assignment.valuedObject, assignments)
            }
            assignments.add(assignment)
        }
        if (assignment.next !== null) {
            nodeStack.push(assignment.next.targetNode)
            assignment.assignParentConditionalToNextNode(assignment.next.targetNode)
        }
    }
    
    protected def dispatch void generateInInit(Entry entry) {
        if (entry.next !== null) {
            nodeStack.push(entry.next.targetNode)
            entry.assignParentConditionalToNextNode(entry.next.targetNode)
        }
    }
    
    protected def dispatch void generateInInit(Exit entry) {
        
    }

    protected def void assignParentConditionalToNextNode(Node currentNode, Node nextNode) {
        if(!nodeToParentConditional.containsKey(nextNode)) {
            val currentParentConditional = nodeToParentConditional.get(currentNode)
            nodeToParentConditional.put(nextNode, currentParentConditional)    
        }
    }
    
    protected def dispatch void generateInInit(Conditional conditional) {
        val parentConditional = nodeToParentConditional.get(conditional)
        if (conditional.^else !== null) {
            nodeStack.push(conditional.^else.targetNode)
            nodeToParentConditional.put(conditional.^else.targetNode, new ConditionalTree(parentConditional, false, conditional))
        }
        if (conditional.^then !== null) {
            nodeStack.push(conditional.^then.targetNode)
            nodeToParentConditional.put(conditional.^then.targetNode, new ConditionalTree(parentConditional, true, conditional))
        }
    }
    
    protected def void handleConditionalNestingInInit(Node node) {
        val parentConditional = nodeToParentConditional.get(node)
        val incomingControlFlows = node.incomingLinks.filter(ControlFlow).toList
        if (incomingControlFlows.size > 1 && parentConditional !== null) {
            nodeToParentConditional.put(node, parentConditional.parent)    
        }
    }
    
    override generate() {
        incIndentationLevel
        appendIndentedLine("ASSIGN")
        
        // Define _GO
        appendIndentedLine('''init(«GO_GUARD») := TRUE;''')
        appendIndentedLine('''next(«GO_GUARD») := FALSE;''')
        code.append("\n")
        // Define pre guards
        val smvCodeGeneratorModule = parent as SmvCodeGeneratorModule
        val store = VariableStore.get(processorInstance.environment)
        for(entry : store.variables.entries) {
            val variableInformation = entry.value
            if(variableInformation.properties.contains(SmvCodeGeneratorModule.PROPERTY_PREGUARD)) {
                val predValuedObject = variableInformation.valuedObject
                val initValue = if(variableInformation.type == ValueType.INT) "0" else "FALSE"
                val preVariableName = entry.key
                val origVariableName = smvCodeGeneratorModule.getOriginalVariableName(preVariableName)
                appendIndentedLine('''init(«preVariableName») := «initValue»;''')
                appendIndentedLine('''next(«preVariableName») := «origVariableName»;''')
                code.append("\n")
            }
        }
    }

    override generateDone() {
    }
}