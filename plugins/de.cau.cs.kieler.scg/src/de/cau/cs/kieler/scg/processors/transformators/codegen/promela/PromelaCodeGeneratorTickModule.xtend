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
package de.cau.cs.kieler.scg.processors.transformators.codegen.promela

import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.VariableDeclaration
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.kicool.compilation.VariableStore
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.ControlFlow
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions

/**
 * Adds the code for the tick loop logic.
 * 
 * @author aas
 * 
 */
class PromelaCodeGeneratorTickModule extends PromelaCodeGeneratorModuleBase {

    @Inject extension KEffectsExtensions
    @Inject extension SCGControlFlowExtensions
    @Inject extension PromelaCodeSerializeHRExtensions serializer

    /** Stack of nodes to be serialized to code */
    protected val nodeStack = <Node> newLinkedList
    /** Conditional Stack that keeps track of the nesting depth of conditionals */
    protected val conditionalStack = <Conditional> newLinkedList
    
    override getName() {
        return class.simpleName;
    }

    override generateInit() {
        serializer.valuedObjectPrefix = ""
        serializer.prePrefix = PRE_GUARD_PREFIX
    }

    override generate() {
        appendIndentedLine('''proctype «TICK_LOOP_FUNCTION»() {''')
        incIndentationLevel
        appendIndentedLine("do")
        appendIndentedLine("::")
        
        appendIndentedLine("atomic { ")
        incIndentationLevel
        generateSettingRandomInputs()
        code.append("\n")
        generateSequentialScgLogic()
        code.append("\n")
        generateAfterTickLogic()
        decIndentationLevel
        appendIndentedLine("}")
        
        appendIndentedLine("od")
        decIndentationLevel
        appendIndentedLine("}")
    }

    override generateDone() {
        
    }
    
    private def void generateAfterTickLogic() {
        generateSeparatorComment("after tick logic")
        appendIndentedLine('''«GO_GUARD» = 0;''')
        // Set pre guards
        val store = VariableStore.get(processorInstance.environment)
        for(entry : store.variables.entries) {
            val variableInformation = entry.value
            if(variableInformation.properties.contains(PromelaCodeGeneratorModule.PROPERTY_PREGUARD)) {
                val preGuardName = entry.key
                // The variable of which the previous value should be used comes after the prefix of pre guards
                val predVariableName = "_" + preGuardName.substring(PRE_GUARD_PREFIX.length)
                appendIndentation()
                code.append(preGuardName).append(" = ").append(predVariableName).append(";\n")
            }
        }
    }
    
    private def void generateSettingRandomInputs() {
        generateSeparatorComment("set random inputs")
        for (declaration : scg.declarations) {
            for (valuedObject : declaration.valuedObjects) {
                if (declaration instanceof VariableDeclaration) {
                    if (declaration.isInput) {
                        switch(declaration.type) {
                            case BOOL : generateSettingRandomBool(valuedObject)
                            case INT : generateSettingRandomInt(valuedObject)
                            default : throw new Exception("Unsupported value type in promela code generation: " + declaration.type) 
                        }
                    }
                }
            }
        }
    }
    
    private def void generateSettingRandomBool(ValuedObject valuedObject) {
        appendIndentedLine('''if''')
        appendIndentedLine(''':: «valuedObject.name» = true;''')
        appendIndentedLine(''':: «valuedObject.name» = false;''')
        appendIndentedLine('''fi''')
    }
    
    private def void generateSettingRandomInt(ValuedObject valuedObject) {
        appendIndentedLine('''do''')
        appendIndentedLine(''':: «valuedObject.name»++;''')
        appendIndentedLine(''':: «valuedObject.name»--;''')
        appendIndentedLine(''':: break;''')
        appendIndentedLine('''od''')
    }
    
    private def void generateSequentialScgLogic() {
        generateSeparatorComment("tick logic")
        val processedNodes = <Node> newHashSet
        nodeStack.clear
        nodeStack.add(scg.nodes.head)
        conditionalStack.clear
        
        while(!nodeStack.empty) {
            val node = nodeStack.pop
            if (!processedNodes.contains(node)) {
                if (!conditionalStack.empty) { 
                    node.handleConditionalNesting
                }
                node.generate
                processedNodes.add(node)
            }
        }
    }
    
    protected def dispatch void generate(Assignment assignment) {
        if (assignment.valuedObject !== null) {
            appendIndentation
            code.append(assignment.serializeHR).append(";\n")
        }
    
        if (assignment.next !== null) nodeStack.push(assignment.next.targetNode)
    }
    
    protected def dispatch void generate(Conditional conditional) {
        appendIndentation
        code.append("if :: (").append(conditional.condition.serializeHR).append(") -> \n")
        incIndentationLevel
        
        conditionalStack.push(conditional)
        
        if (conditional.^else !== null) nodeStack.push(conditional.^else.targetNode)        
        if (conditional.^then !== null) nodeStack.push(conditional.^then.targetNode)
    }
    
    protected def void handleConditionalNesting(Node node) {
        val conditional = conditionalStack.peek
        val incomingControlFlows = node.incomingLinks.filter(ControlFlow).toList
        if (conditional.^else !== null && conditional.^else.target == node) {
            if (incomingControlFlows.size == 1) { 
                decIndentationLevel
                appendIndentedLine(":: else -> ")
                incIndentationLevel
            }
        }
        if (incomingControlFlows.size > 1) {
            for (i : 2..incomingControlFlows.size) {
                decIndentationLevel
                appendIndentedLine(":: else -> skip;")
                appendIndentedLine("fi")
                conditionalStack.pop    
            }
        }            
    }
    
    protected def dispatch void generate(Entry entry) {
        nodeStack.add(entry.next?.targetNode)
    }
    
    protected def dispatch void generate(Exit exit) {
    }
}