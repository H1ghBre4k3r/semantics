/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.scg.processors.transformators.codegen.c

import de.cau.cs.kieler.scg.SCGraphs
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.scg.SCGraph
import java.util.Map
import de.cau.cs.kieler.scg.codegen.SCGCodeGeneratorModule
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import java.util.List
import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.scg.ControlFlow
import java.util.Deque
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.VectorValue
import de.cau.cs.kieler.scg.ScgFactory
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCreateExtensions

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.IgnoreValue
import de.cau.cs.kieler.kexpressions.TextExpression
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions

/**
 * C Code Generator Logic Module
 * 
 * Handles the creation of the tick logic function.
 * 
 * @author ssm
 * @kieler.design 2017-07-24 proposed 
 * @kieler.rating 2017-07-24 proposed yellow 
 * 
 */
class CCodeGeneratorLogicModule extends SCGCodeGeneratorModule {
    
    @Inject extension KExpressionsCreateExtensions
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension KEffectsExtensions
    @Inject extension CCodeSerializeHRExtensions
    
    static val LOGIC_NAME = "logic"
    
    var CCodeGeneratorStructModule struct 
    var CCodeGeneratorResetModule reset
    var CCodeGeneratorTickModule tick 

    /** Hash for already handles pre variables */    
    protected val preVariables = <String> newHashSet
    /** Conditional Stack that keeps track of the nesting depth of conditionals */
    protected val conditionalStack = <Conditional> newLinkedList
    
    override configure(String baseName, SCGraphs sCGraphs, SCGraph scg, Processor<SCGraphs, CodeContainer> processorInstance, 
        Map<SCGraph, SCGCodeGeneratorModule> codeGeneratorModuleMap, String codeFilename, SCGCodeGeneratorModule parent
    ) {
        super.configure(baseName, sCGraphs, scg, processorInstance, codeGeneratorModuleMap, codeFilename, parent)
        
        struct = (parent as CCodeGeneratorModule).struct as CCodeGeneratorStructModule
        reset = (parent as CCodeGeneratorModule).reset as CCodeGeneratorResetModule
        tick = (parent as CCodeGeneratorModule).tick as CCodeGeneratorTickModule
        
        return this
    }

    
    def getName() {
        LOGIC_NAME + baseName + suffix
    }
    
    override generateInit() {
        preVariables.clear
        
        indent(0)
        code.append("void ").append(getName)
        code.append("(")
        code.append(struct.getName).append("* ").append(struct.getVariableName)
        code.append(")")
        
        struct.forwardDeclarations.append(code).append(";\n")
        
        code.append(" {\n")
    }
    
    override generate() {
        var nodes = newLinkedList => [ it += scg.nodes.head ]
        conditionalStack.clear
        val processedNodes = <Node> newHashSet
        
        // Iterate through all nodes. 
        // However, if the last node was already the actual node, then skip it, because
        // this are two joining control-flows from a conditional. 
        // Consequence: Self-Loops in sequentialized assignments are forbidden.
        while(!nodes.empty) {
            val node = nodes.pop
            if (!processedNodes.contains(node)) {
                node.generate(nodes)
                processedNodes += node
            }
        }
    }
    
    override generateDone() {
        indent(0)
        code.append("}\n")
    }
    
    protected def dispatch void generate(Assignment assignment, Deque<Node> nodes) {
        if (!conditionalStack.empty) {
            // Apparently, we are in a nested conditional. Handle it if necessary. 
            assignment.handleConditionalNesting
        }
        
        if (assignment.valuedObject === null) {
            if (assignment.expression instanceof TextExpression) {
                indent(conditionalStack.size + 1)
                code.append((assignment.expression as TextExpression).text).append("\n")
            } else {
                throw new NullPointerException("Assigned valued object is null")
            }
            return
        }
        
        // Add the assignment.
        valuedObjectPrefix = struct.getVariableName + struct.separator
        prePrefix = CCodeGeneratorStructModule.STRUCT_PRE_PREFIX
        if (assignment.valuedObject.isArray && assignment.expression instanceof VectorValue) {
            for (asgn : assignment.splitAssignment) {
                indent(conditionalStack.size + 1)
                code.append(asgn.serializeHR).append(";\n")    
            }
        } else {
            indent(conditionalStack.size + 1)
            code.append(assignment.serializeHR).append(";\n")
        }
        
        // Handle pre variable if necessary.
        if (assignment.expression != null && assignment.expression instanceof OperatorExpression &&
            (assignment.expression as OperatorExpression).operator == OperatorType.PRE) {
            (assignment.expression as OperatorExpression).addPreVariable                    
        }
        
        // If a new statement follows, add it to the node list.
        if (assignment.next != null) nodes.push(assignment.next.target)
    }
        
    protected def dispatch void generate(Conditional conditional, Deque<Node> nodes) {
        valuedObjectPrefix = struct.getVariableName + struct.separator
        prePrefix = CCodeGeneratorStructModule.STRUCT_PRE_PREFIX

        indent(conditionalStack.size + 1)
        code.append("if (")
        code.append(conditional.condition.serializeHR)
        code.append(") {\n")
        
        conditionalStack.push(conditional)
        
        if (conditional.^else != null) nodes.push(conditional.^else.target)        
        if (conditional.^then != null) nodes.push(conditional.^then.target)
    }
    
    protected def void handleConditionalNesting(Assignment assignment) {
        // There are two cases. The else branch is distinct from the actual control flow or 
        // the actual branch joins with the else branch of the conditional directly. 
        // (In the latter case we can omit the "else" in C.)
        val conditional = conditionalStack.peek
        val incomingControlFlows = assignment.incoming.filter(ControlFlow).toList
        if (conditional.^else != null && conditional.^else.target == assignment) {
            if (incomingControlFlows.size == 1) {
                // Apparently, it is the first assignment of a dedicated else branch. Handle it. 
                indent(conditionalStack.size)
                code.append("} else {\n")
            }        
        }
        // If multiple control-flows are joining here, reduce the nesting depth accordingly.
        if (incomingControlFlows.size > 1) for (i : 2..incomingControlFlows.size) {
            indent(conditionalStack.size)
            code.append("}\n")
            conditionalStack.pop
        }            
    }
    
    protected def dispatch void generate(Entry entry, List<Node> nodes) {
        nodes += entry.next?.target
    }
    
    protected def dispatch void generate(Exit exit, List<Node> nodes) {
        if (!conditionalStack.empty) {
            while(conditionalStack.size > 0) {
                indent(conditionalStack.size)
                code.append("}\n")
                conditionalStack.pop
            }
        }
    }
    
    protected def void addPreVariable(OperatorExpression operatorExpression) {
        valuedObjectPrefix = ""
        prePrefix = CCodeGeneratorStructModule.STRUCT_PRE_PREFIX
        val name = operatorExpression.serializeHR 
        if (preVariables.contains(name)) return;
    
        // Add the pre variable to the variables hashes to mark it handled.    
        preVariables += name.toString
        
        // Add the variable to the data struct.
        struct.code.append(indentation +  "char ")
        struct.code.append(name)
        struct.code.append(";\n")
        
        // Add the initialization in the reset function.
        reset.code.append(indentation).append(struct.getVariableName).append(struct.separator).append(name).append(" = 0;\n")
        
        // Add the "register save" in the tick function.
        prePrefix = "_"
        tick.code.append(indentation)
        tick.code.append(struct.getVariableName).append(struct.separator).append(name).append(" = ")
        tick.code.append(struct.getVariableName).append(struct.separator).append(operatorExpression.serializeHR).append(";\n")
    }
    
    protected def List<Assignment> splitAssignment(Assignment assignment) {
        var vector = assignment.expression as VectorValue
        vector.splitAssignmentHelper(assignment.valuedObject, <Integer> newLinkedList, <Assignment> newLinkedList)
    }
    
    protected def List<Assignment> splitAssignmentHelper(VectorValue vector, ValuedObject vo, Deque<Integer> indexStack, List<Assignment> assignments) {
        var index = 0
        for (v : vector.values) {
            if (!(v instanceof IgnoreValue)) {
                if (v instanceof VectorValue) {
                    indexStack.push(index)     
                    v.splitAssignmentHelper(vo, indexStack, assignments)
                    indexStack.pop           
                } else {
                    val newAssignment = ScgFactory.eINSTANCE.createAssignment
                    newAssignment.valuedObject = vo
                    for (is : indexStack) {
                        newAssignment.indices.add(createIntValue(is))
                    }
                    newAssignment.indices.add(createIntValue(index))
                    newAssignment.expression = v.copy
                    
                    assignments += newAssignment
                }
            }
            index++
        }         
        assignments        
    }
    
}