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
package de.cau.cs.kieler.scg.processors.codegen.promela

import com.google.inject.Inject
import com.google.inject.Injector
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.VariableStore
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.codegen.SCGCodeGeneratorModule
import de.cau.cs.kieler.verification.VerificationPropertyChanged
import java.util.List

import static extension de.cau.cs.kieler.verification.extensions.VerificationContextExtensions.*


/**
 * Root Promela Code Generator Module
 * 
 * @author aas
 * 
 */
class PromelaCodeGeneratorModule extends PromelaCodeGeneratorModuleBase {
    
    @Inject Injector injector
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension PromelaCodeSerializeHRExtensions serializer
    
    protected static val PROPERTY_GUARD= "guard"
    protected static val PROPERTY_PREGUARD= "preGuard"
    protected static val PROMELA_EXTENSION = ".pml"
    
    private var List<SCGCodeGeneratorModule> codeGeneratorModules
    
    override configure() {
        val declarations = createAndConfigureModule(PromelaCodeGeneratorDeclarationModule)
        val tick = createAndConfigureModule(PromelaCodeGeneratorTickModule)
        codeGeneratorModules = #[declarations, tick]
        
        serializer.valuedObjectPrefix = ""
        serializer.prePrefix = PRE_GUARD_PREFIX
        addPreGuardsToVariableStore
        
        // Update current task of verification properties
        val verificationProperties = verificationContext?.verificationProperties
        if(!verificationProperties.isNullOrEmpty) {
            for(propertyAndIndexPair : verificationProperties.indexed) {
                val index= propertyAndIndexPair.key
                val property = propertyAndIndexPair.value
                if(index == 0) {
                    property.runningTaskDescription = "Generating model checker code..."
                } else {
                    property.fail(new Exception("Handling multiple properties using SPIN is not implemented"))
                    processorInstance.compilationContext.notify(new VerificationPropertyChanged(property))
                }
            }
        }
    }
    
    override generateInit() {
        codeGeneratorModules.forEach[it.generateInit]
    }
    
    override generate() {
        codeGeneratorModules.forEach[it.generate]
    }
    
    
    override generateDone() {
        codeGeneratorModules.forEach[it.generateDone]
    }
    
    override generateWrite(CodeContainer codeContainer) {
        val pmlFilename = codeFilename + PROMELA_EXTENSION
        val pmlFile = new StringBuilder

        pmlFile.addHeader
        codeGeneratorModules.forEach[
            pmlFile.append(it.code)
            pmlFile.append("\n")
        ]
        codeContainer.add(pmlFilename, pmlFile.toString)
    }
    
    protected def SCGCodeGeneratorModule createAndConfigureModule(Class<? extends SCGCodeGeneratorModule> clazz) {
        val module = injector.getInstance(clazz)
        val namingProperty = null
        module.configure(baseName, SCGraphs, scg, processorInstance, codeGeneratorModuleMap, codeFilename + PROMELA_EXTENSION, this, namingProperty)
        return module
    }
    
    protected def addPreGuardsToVariableStore() {
        val store = VariableStore.get(processorInstance.environment)
        for( assignment : scg.nodes.filter(Assignment) ) {
            if(assignment.expression !== null && assignment.expression instanceof OperatorExpression) {
                val operatorExpression = assignment.expression as OperatorExpression
                for(preOp : operatorExpression.getPreOperatorExpressions) {
                    val preOpName = preOp.serializeHR
                    val variableInformation = store.add(preOpName, PROPERTY_PREGUARD)
                    variableInformation.type = ValueType.BOOL    
                }
            }
        }
    }
    
    protected def void addHeader(StringBuilder sb) {
        sb.append("/*\n" +
                  " * Automatically generated code by\n" +
                  " * KIELER SCCharts - The Key to Efficient Modeling\n" +
                  " *\n" +
                  " * http://rtsys.informatik.uni-kiel.de/kieler\n" +
                  " */\n\n") 
    }
}