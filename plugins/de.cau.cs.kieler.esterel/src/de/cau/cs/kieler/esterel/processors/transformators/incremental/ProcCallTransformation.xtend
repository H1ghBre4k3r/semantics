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
package de.cau.cs.kieler.esterel.processors.transformators.incremental

import com.google.inject.Inject
import de.cau.cs.kieler.kicool.compilation.InplaceProcessor
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.esterel.EsterelProgram
import de.cau.cs.kieler.esterel.ProcedureCall
import org.eclipse.emf.ecore.util.EcoreUtil
import de.cau.cs.kieler.kexpressions.ValueType
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.kicool.compilation.EObjectReferencePropertyData
import org.eclipse.emf.ecore.EObject

/**
 * @author mrb
 *
 */
class ProcCallTransformation extends InplaceProcessor<EsterelProgram> {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    public static val ID = "de.cau.cs.kieler.esterel.processors.proccall"
    
    override getId() {
        return ID
    }

    override getName() {
        return "ProcCall"
    }

    @Inject
    extension EsterelTransformationExtensions
    
    var EObject lastStatement
    
    override process() {
        val nextStatement = environment.getProperty(SCEstIntermediateProcessor.NEXT_STATEMENT_TO_TRANSFORM).getObject
        val isDynamicCompilation = environment.getProperty(SCEstIntermediateProcessor.DYNAMIC_COMPILATION)
        
        if (isDynamicCompilation) {
            if (nextStatement instanceof ProcedureCall) {
                transform(nextStatement)
            }
            else {
                throw new UnsupportedOperationException(
                    "The next statement to transform and this processor do not match.\n" +
                    "This processor ID: " + ID + "\n" +
                    "The statement to transform: " + nextStatement
                )
            }
            environment.setProperty(SCEstIntermediateProcessor.NEXT_STATEMENT_TO_TRANSFORM, new EObjectReferencePropertyData(lastStatement))
        }
        else {
            model.eAllContents.filter(ProcedureCall).toList.forEach[transform]
        }
    }
    
    def transform(ProcedureCall procCall) {
        val function = createFunction(procCall.procedure.name)
        // create 'Parameter' for call by reference parameters of procedure
        for (v : procCall.referenceArguments) {
           function.parameters.add(createParameter(createValuedObjectReference(v), true))
        }
        // create 'Parameter' for call by value parameters of procedure
        for (expr : procCall.valueArguments) {
           function.parameters.add(createParameter(EcoreUtil.copy(expr), false))
        }
        // dummy variable since a function call is an expression and not a statement in SCL
        val variable = createNewUniqueVariable(null)
        val decl = createDeclaration(ValueType.BOOL, variable)
        val scope = createScopeStatement(decl)
        scope.statements.add(createAssignment(variable, function))
        procCall.replace(scope)
        lastStatement = scope
    }
    
}