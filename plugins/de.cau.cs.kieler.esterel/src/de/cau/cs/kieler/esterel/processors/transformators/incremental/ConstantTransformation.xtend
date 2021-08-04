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
package de.cau.cs.kieler.esterel.processors.transformators.incremental

import com.google.inject.Inject
import de.cau.cs.kieler.esterel.Constant
import de.cau.cs.kieler.esterel.ConstantDeclaration
import de.cau.cs.kieler.esterel.ConstantExpression
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.scl.Module
import java.util.HashMap
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * @author mrb
 *
 */
class ConstantTransformation extends AbstractSCEstDynamicProcessor<Module> {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    
    public static val ID = "de.cau.cs.kieler.esterel.processors.constant"
    
    override getId() {
        return ID
    }

    override getName() {
        return "Constant"
    }
    
    @Inject
    extension EsterelTransformationExtensions
    
    override transform(Module module) {
        // this map combines an Esterel sensor with the new SCL variable
        val HashMap<Constant, ValuedObject> newVariables = new HashMap<Constant, ValuedObject>()
        val constantDeclarations = module.declarations.filter(ConstantDeclaration).toList
        for (decl: constantDeclarations) {
            for (c : decl.valuedObjects.filter(Constant)) {
                var ValueType newType
                if (c.type.type != ValueType.PURE) {
                    newType = if (c.type.type == ValueType.DOUBLE) ValueType.FLOAT else c.type.type
                }
                else {
                    throw new UnsupportedOperationException(
                    "The following constant doesn't have a valid type for SCL! " + decl)
                }
                val newDecl = createDeclaration(newType, null)
                val variable = createNewUniqueVariable(c.initialValue)
                newVariables.put(c, variable)
                newDecl.valuedObjects.add(variable)
                module.declarations.add(newDecl)
            }
        }
        transformReferences(module, newVariables)
        transformConstantExpressions(module, newVariables)
        module.declarations.removeIf[it instanceof ConstantDeclaration]
    }
    
    def transformReferences(EObject obj, HashMap<Constant, ValuedObject> newVariables) {
        val references = obj.eAllContents.filter(ValuedObjectReference).toList
        // iterate over all valued object references contained in the scope
        // if a reference references a transformed constant then set the reference to the new variable
        for (ref : references) {
            if (ref.valuedObject instanceof Constant) {
                var vObject = ref.valuedObject as Constant
                if (newVariables.containsKey(vObject)) {
                    ref.valuedObject = newVariables.get(vObject)
                }
            }
        }
    }
    
    def transformConstantExpressions(EObject obj, HashMap<Constant, ValuedObject> newVariables) {
        val constantExpr = obj.eAllContents.filter(ConstantExpression).toList
        for (expr : constantExpr) {
            if (expr.constant !== null) {
                val constant = expr.constant as Constant
                // if the constant expression references a transformed constant
                if (newVariables.containsKey(constant)) {
                    expr.replace(createValuedObjectReference(newVariables.get(constant)))
                }
            }
            else { // if (expr.value !== null)
                expr.replace(expr.value)
            }
        }
    }
    
}