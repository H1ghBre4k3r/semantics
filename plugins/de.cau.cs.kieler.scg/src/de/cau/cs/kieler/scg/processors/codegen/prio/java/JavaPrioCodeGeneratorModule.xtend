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
package de.cau.cs.kieler.scg.processors.codegen.prio.java

import com.google.inject.Inject
import com.google.inject.Injector
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.annotations.StringPragma
import de.cau.cs.kieler.annotations.registry.PragmaRegistry

import static de.cau.cs.kieler.kicool.compilation.codegen.AbstractCodeGenerator.*
import static de.cau.cs.kieler.kicool.compilation.codegen.CodeGeneratorNames.*
import de.cau.cs.kieler.annotations.Nameable
import de.cau.cs.kieler.scg.processors.codegen.prio.c.CPrioCodeGeneratorModule
import de.cau.cs.kieler.annotations.extensions.PragmaExtensions

/**
 * Root Java Code Generator Module
 * Migrated from SJTransformation
 * 
 * Initializes necessary modules and invokes them in correct order.
 * 
 * @author ssm
 * @kieler.design 2019-06-09 proposed 
 * @kieler.rating 2019-06-09 proposed yellow 
 * 
 */
class JavaPrioCodeGeneratorModule extends CPrioCodeGeneratorModule {
    
    @Inject extension PragmaExtensions
    @Inject Injector injector
    @Inject extension JavaPrioCodeSerializeHRExtensions
    
    protected static val PACKAGE = PragmaRegistry.register("package", StringPragma, "Package name for the generated file(s)")
    
    public static val JAVA_EXTENSION = ".java"
    
    override configure() {
        modifications.clear
        
        struct = injector.getInstance(JavaPrioCodeGeneratorStructModule)
        reset = injector.getInstance(JavaPrioCodeGeneratorResetModule)
        tick = injector.getInstance(JavaPrioCodeGeneratorTickModule)
        logic = injector.getInstance(JavaPrioCodeGeneratorLogicModule)
            
        struct.configure(baseName, SCGraphs, scg, processorInstance, codeGeneratorModuleMap, 
            codeFilename + JAVA_EXTENSION, this, TICKDATA_STRUCT_NAME)
        reset.configure(baseName, SCGraphs, scg, processorInstance, codeGeneratorModuleMap, 
            codeFilename + JAVA_EXTENSION, this, RESET_FUNCTION_NAME)
        tick.configure(baseName, SCGraphs, scg, processorInstance, codeGeneratorModuleMap, 
            codeFilename + JAVA_EXTENSION, this, TICK_FUNCTION_NAME)
        logic.configure(baseName, SCGraphs, scg, processorInstance, codeGeneratorModuleMap, 
            codeFilename + C_EXTENSION, this, LOGIC_FUNCTION_NAME)
    }
    
    override generateDone() {
        struct?.generateDone
        reset?.generateDone
        logic?.generateDone
        tick.code.append(logic.code)
        tick?.generateDone
    }
    
    override generateWrite(CodeContainer codeContainer) {
        val classFilename = codeFilename + JAVA_EXTENSION
        val classFile = new StringBuilder

        classFile.addHeader
        classFile.hostcodeAdditions
        classFile.addClass
        
        classFile.append(struct.code).append("\n")
        classFile.append(tick.code).append("\n")
        classFile.append(reset.code)
        
        classFile.append("}\n");        
        
        naming.put(TICK, tick.getName)
        naming.put(RESET, reset.getName)
        naming.put(LOGIC, logic.getName)
        naming.put(TICKDATA, struct.getName)
        
        codeContainer.addJavaCode(classFilename, classFile.toString) => [
            it.naming.putAll(this.naming)
            modelName = if (moduleObject instanceof Nameable) moduleObject.name else "_default"   
        ]        
    } 
   
    protected def addClass(StringBuilder sb) {
        val programName = getProgramName
        sb.append("public class " + programName + " extends SJLProgramForPriorities<" + programName + ".State> {\n");
    }
    
    def getProgramName() {
        return if (scg.label.nullOrEmpty) "Program" else scg.label;
    }
    
    override void hostcodeAdditions(StringBuilder sb) {
        val includes = modifications.get(JavaPrioCodeSerializeHRExtensions.INCLUDES)
        for (include : includes)  {
            sb.append("import " + include + "\n")
        }
        
        val hostcodePragmas = SCGraphs.getStringPragmas(HOSTCODE)
        for (pragma : hostcodePragmas) {
            sb.append(pragma.values.head + "\n")
        }
        if (hostcodePragmas.size > 0 || includes.size > 0) {
            sb.append("\n")
        }
    }      
}