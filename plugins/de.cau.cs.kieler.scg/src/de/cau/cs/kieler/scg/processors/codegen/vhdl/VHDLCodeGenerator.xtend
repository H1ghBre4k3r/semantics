/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2019 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.scg.processors.codegen.vhdl

import com.google.inject.Inject
import de.cau.cs.kieler.annotations.Pragma
import de.cau.cs.kieler.core.properties.IProperty
import de.cau.cs.kieler.core.properties.Property
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.kexpressions.VariableDeclaration
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.kicool.compilation.ProcessorType
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.SCGraphs
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions
import de.cau.cs.kieler.scg.extensions.SCGCoreExtensions
import de.cau.cs.kieler.scg.processors.ssa.SSACoreExtensions
import java.util.List

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension java.lang.Character.*
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.kexpressions.ValuedObject

/**
 * 
 * @author als
 */
class VHDLCodeGenerator extends Processor<SCGraphs, CodeContainer> {
    
    public static val IProperty<String> OUTPUT_POSTFIX = 
        new Property<String>("de.cau.cs.kieler.scg.processors.codegen.vhdl.io.postfix", "_out")
    
    @Inject extension VHDLSerializeExtensions
    @Inject extension KExpressionsDeclarationExtensions
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension KEffectsExtensions
    @Inject extension SSACoreExtensions
    @Inject extension SCGControlFlowExtensions
    @Inject extension SCGCoreExtensions
    
    static val PRE_PREFIX = "_pre"
    
    override getId() {
        "de.cau.cs.kieler.scg.processors.codegen.vhdl"
    }
    
    override getName() {
        "VHDL Code"
    }
    
    override getType() {
        ProcessorType.EXOGENOUS_TRANSFORMATOR
    }
    
    override process() {
        val cc = new CodeContainer

        for (scg : model.scgs) {
            cc.add(scg.name + ".vhd", scg.generateVHDL(model.pragmas).toString)
        }
        
        model = cc
    }
    
    protected def String removeDoubleUnderscore(String s) {
        //if (s.length() ==0) return ""
        //var String out = "" + s.charAt(0) 
        //var char last = s.charAt(0) 
        //for (var int i = 1 ; i< s.length(); i++){
        //  if (s.charAt(i) !='_' || last != '_'){
        //      out+= s.charAt(i)
        //      last= s.charAt(i)
        ////      }  
        //}
        return s.replaceAll("_+","_")
        
    }
    
    
    protected def generateVHDL(SCGraph scg, List<Pragma> pragmas) {
        
        // Split input+output variables
        var splitOutputs = newHashMap
        for (ioDecl : scg.variableDeclarations.filter[input && output && !isSSA].toList) {
            val copy = ioDecl.copy
            ioDecl.output = false
            copy.input = false
            copy.valuedObjects.forEach[name = name + OUTPUT_POSTFIX.property]
            scg.declarations.add(scg.declarations.indexOf(ioDecl) + 1, copy)
            for (voIdx : ioDecl.valuedObjects.indexed) {
                splitOutputs.put(voIdx.value, copy.valuedObjects.get(voIdx.key))
            }
        }
        
        // Find written VOs
        val writtenVOs = scg.nodes.filter(Assignment).map[valuedObject].filterNull.toSet
        val bufferInput = newLinkedHashMap
        val writeOutput = newLinkedHashMap
        val preserveState = newLinkedHashMap
        val preGuards = newLinkedHashMap
        
        // Rename SSAed variables
        for (decl : scg.variableDeclarations.filter[isSSA]) {
            // Change last to original name
            if (!(decl.input || decl.output)) {
                decl.valuedObjects.last.name = decl.ssaOrigVO.name
            } else if (decl.input && decl.valuedObjects.size == 1) { // SSA does not index inputs when only read
                // but we want to do so here (for buffering)
                val name = decl.valuedObjects.head.name
                decl.valuedObjects.head.name = name + if (name.charAt(name.length - 1).isDigit) "_0" else "0"
            }
            if (decl.input && decl.valuedObjects.size > 0 && !writtenVOs.contains(decl.valuedObjects.head)) { // Buffer input
                bufferInput.put(decl.valuedObjects.head, decl.ssaOrigVO)
            }
            if (decl.output && decl.valuedObjects.size > 0) { // Assign output
                if (decl.input) {
                    writeOutput.put(splitOutputs.get(decl.ssaOrigVO), decl.valuedObjects.last)
                } else {
                    writeOutput.put(decl.ssaOrigVO, decl.valuedObjects.last)
                }
            }
            if ((!decl.input || decl.output) && decl.valuedObjects.size > 1 && !writtenVOs.contains(decl.valuedObjects.head)) { // first version is only read
                preserveState.put(decl.valuedObjects.head, decl.valuedObjects.last)
            }
            
            // Keep all variables as local variables
            decl.input = false
            decl.output = false
        }
        
        // Transform pre guards
        for (pre : scg.nodes.filter(Assignment).map[eAllContents.toIterable].flatten.filter(OperatorExpression).filter[operator == OperatorType.PRE].toList) {
            val vo = (pre.subExpressions.head as ValuedObjectReference).valuedObject
            if (!vo.name.startsWith(PRE_PREFIX)) {
                vo.name = PRE_PREFIX + vo.declaration.ssaOrigVO.name
            }
            pre.replace(pre.subExpressions.head)
            preserveState.remove(vo)
            preGuards.put(vo, vo.declaration.valuedObjects.last)
        }
        
        // remove double underscore and remove leading undercore by adding variable type information
        for (ioDecl : scg.variableDeclarations.filter[!input && !output].toList) {
            for (vo : ioDecl.valuedObjects) {
                if (vo.name.startsWith("_")) {
                    vo.name = "local"+ name
                }
                vo.name.removeDoubleUnderscore
            }
        }
        
        // Generate code
        return '''
        library IEEE;
        use IEEE.STD_LOGIC_1164.ALL;
        use IEEE.NUMERIC_STD.ALL;
        
        ENTITY «scg.name» IS
        PORT(
            -- control
            tick: IN std_logic;
            reset: IN boolean;
            -- input/output
            «FOR decl : scg.variableDeclarations.filter[(input || output) && !isSSA]SEPARATOR ';'»
                «decl.serialize»
            «ENDFOR»
        );
        END «scg.name»;
        
        ARCHITECTURE behavior OF «scg.name» IS
            -- control
            signal pre_reset: boolean;
            signal GO : boolean;
            -- local variables
            «FOR decl : scg.variableDeclarations.filter[isSSA && !it.valuedObjects.empty]»
                signal «decl.serialize»;
            «ENDFOR»
            begin
                -- logic
                «FOR asm : scg.allAssignments»
                    «asm.serialize»;
                «ENDFOR»
                -- write outputs
                «FOR kv : writeOutput.entrySet»
                    «kv.key.name» <= «kv.value.name»;
                «ENDFOR»
                
            -- ---------------------
            -- Registers
            registers: process
            begin
            wait until rising_edge(tick);
                if((reset or pre_reset) = true) then
                    «FOR vo : preGuards.keySet»
                        «vo.name» <= false;
                    «ENDFOR»
                    «FOR vo : preserveState.keySet»
                        «vo.name» <= «vo.resetValue»;
                    «ENDFOR»
                else
                    «FOR kv : preGuards.entrySet»
                        «kv.key.name» <= «kv.value.name»;
                    «ENDFOR»
                    «FOR kv : preserveState.entrySet»
                        «kv.key.name» <= «kv.value.name»;
                    «ENDFOR»
                end if;
            end process;
            
            -- Input Buffering
            inputRegister: process
            begin
            wait until rising_edge(tick);
                if(reset = true) then
                    «FOR vo : bufferInput.keySet»
                        «vo.name» <= «vo.resetValue»;
                    «ENDFOR»
                else
                    «FOR kv : bufferInput.entrySet»
                        «kv.key.name» <= «kv.value.name»;
                    «ENDFOR»
                end if;
            end process;
            
            -- GO Register
            GORegister: process
            begin
            wait until rising_edge(tick);
                pre_reset <= reset; -- has no reset input
                if(reset = true) then
                    GO <= false;
                else
                    GO <= pre_reset;
                end if;
            end process;
        end behavior;
        '''
    }
    
    def allAssignments(SCGraph scg) {
        val assignments = newArrayList
        var next = scg.nodes.findFirst[it instanceof Entry].asEntry.next?.target
        while (next instanceof Assignment) {
            assignments += next
            next = next.next?.target
        }
        return assignments
    }
    
    def resetValue(ValuedObject vo) {
        var String int0 = ""
        while (int0.length()< 64) int0 = "0"+ int0
        int0= "\""+int0 +"\""
        return switch(vo.variableDeclaration.type) {
            case BOOL: "false"
            case FLOAT: "0.0"
            case INT: int0
            default: "false"
        }
    }
}