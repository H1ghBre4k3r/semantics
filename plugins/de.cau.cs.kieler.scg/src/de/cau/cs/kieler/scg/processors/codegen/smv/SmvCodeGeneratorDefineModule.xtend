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
import de.cau.cs.kieler.kexpressions.VariableDeclaration
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.ControlFlow
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

import static extension de.cau.cs.kieler.verification.codegen.SmvCodeGeneratorExtensions.*

/**
 * @author aas
 * 
 */
class SmvCodeGeneratorDefineModule extends SmvCodeGeneratorModuleBase {

    @Inject extension KEffectsExtensions
    @Inject extension SCGControlFlowExtensions
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension SmvCodeSerializeHRExtensions serializer

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
    
    private static class FullyQualifiedConditionWithComment {
        @Accessors var String condition
        @Accessors var String comment
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
        appendIndentedLine("DEFINE")
        
        // Define valued objects that are no register values (pre of something) and no input to the model
        for (declaration : scg.declarations) {
            if (declaration instanceof VariableDeclaration) {
                for (valuedObject : declaration.valuedObjects) {
                    valuedObject.generateAssignment
                }
            }
        }
    }

    private def void generateAssignment(ValuedObject valuedObject) {
        val assignments = valuedObjectToAssignments.get(valuedObject)
        if(assignments !== null) {
            if(assignments.size == 1) {
                valuedObject.generateUnconditionalAssignment(assignments.head)
            } else {
                valuedObject.generateConditionalAssignments(assignments)
            }
        } else {
            if(!valuedObject.variableDeclaration.isInput && !(valuedObject.name == "_GO")) {
                // TODO: This valued object should not exist in the first place.
                // It is probably a guard that is never set. (See also the comment in DefineUnboundVariablesOfSSA.
                appendIndentedLine('''«valuedObject.name» := FALSE; -- WARNING: This variable is undefined in the SCG''')
            }
        }
    }

    private def void generateUnconditionalAssignment(ValuedObject valuedObject, Assignment assignment) {
        val expression = assignment.expression.serializeHR
            .toSmvExpression
            .useBooleanInsteadIntegerIfNeeded(valuedObject)
        appendIndentedLine('''«valuedObject.name» := «expression»;''')    
    }

    private def void generateConditionalAssignments(ValuedObject valuedObject, List<Assignment> assignments) {
        appendIndentedLine('''«valuedObject.name» :=''')
        incIndentationLevel
        appendIndentedLine('''case''')
        incIndentationLevel
        for(assignment : assignments) {
            val parentConditional = nodeToParentConditional.get(assignment)
            val fullyQualifiedConditionWithComment = getFullyQualifiedConditionWithComment(parentConditional)
            
            val expression = assignment.expression.serializeHR
                .toSmvExpression
                .useBooleanInsteadIntegerIfNeeded(valuedObject)
            
            val comment = if(fullyQualifiedConditionWithComment.comment.isNullOrEmpty)
                              ""
                          else
                              ''' -- «fullyQualifiedConditionWithComment.comment»'''
            
            appendIndentedLine('''«fullyQualifiedConditionWithComment.condition» : «expression»;«comment»''')    
        }
        decIndentationLevel
        appendIndentedLine('''esac;''')
        decIndentationLevel
    }
    
    private def FullyQualifiedConditionWithComment getFullyQualifiedConditionWithComment(ConditionalTree parentConditional) {
        val result = new FullyQualifiedConditionWithComment => [
            condition = "TRUE"
            comment = ""
        ]
        
        if(parentConditional === null) {
            return result
        }
        val conditionForTrueBranch = parentConditional.conditional.condition.serializeHR
            .toSmvExpression
        
        // Select condition or the inverted condition,
        // depending on the control-flow branch this condition is for.
        // For the inverted condition, the SMV switch-case-default TRUE is returned.
        if (parentConditional.branchOfConditional) {
            result.condition = conditionForTrueBranch
        } else {
//          result.condition = '''!(«conditionForTrueBranch»)'''
            result.condition = '''TRUE'''
            result.comment = '''default case for !(«conditionForTrueBranch»)'''    
        }
        
        // Recursively concatenate conditions of parents if needed
        if(parentConditional.parent !== null) {
            val parentResult = getFullyQualifiedConditionWithComment(parentConditional.parent)
            result.condition = parentResult.condition + " & " + result.condition
            result. comment = parentResult.comment + ". " + parentResult.comment
        }
        return result
    }
    
    override generateDone() {
    }
    
    private def boolean isBoolean(ValuedObject valuedObject) {
        val declaration = valuedObject.declaration
        if(declaration instanceof VariableDeclaration) {
            return declaration.type == ValueType.BOOL
        }
        return false
    }
    
    private def String useBooleanInsteadIntegerIfNeeded(String s, ValuedObject valuedObject) {
        // FIXME: This is a dirty fix for assignment of 1 and 0 instead proper booleans
        if(valuedObject.isBoolean) {
            if(s == "0") {
                return "FALSE"
            } else if (s == "1") {
                return "TRUE"
            } else {
                val sNo1 = s.replace("| 1","| TRUE").replace("& 1","& TRUE")
                val sNo1No0 = sNo1.replace("| 0","| FALSE").replace("& 0","& FALSE")
                return sNo1No0
            }
        }
        return s
    }
}