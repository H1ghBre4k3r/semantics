/*
 * generated by Xtext
 */
package de.cau.cs.kieler.esterel.scest.scoping;

import de.cau.cs.kieler.esterel.scoping.EsterelScopeProvider
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import java.util.ArrayList
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope

import static de.cau.cs.kieler.esterel.scoping.EsterelScopeProviderUtil.*
import org.eclipse.emf.ecore.EObject
import de.cau.cs.kieler.esterel.SignalReference
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.esterel.SignalRenaming
import de.cau.cs.kieler.esterel.TrapReference
import de.cau.cs.kieler.esterel.TrapExpression
import de.cau.cs.kieler.esterel.VariableReference
import java.util.List
import de.cau.cs.kieler.scl.Scope
import org.eclipse.xtend.expression.Variable
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.naming.QualifiedName
import de.cau.cs.kieler.scl.Module
import de.cau.cs.kieler.esterel.LocalVariableDeclaration
import de.cau.cs.kieler.esterel.EsterelVariableDeclaration
import de.cau.cs.kieler.kexpressions.VariableDeclaration

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class SCEstScopeProvider extends EsterelScopeProvider {

    /**
     * {@inheritDoc}
     */
    override getScope(EObject context, EReference reference) {
        var IScope scope
        switch (context) {
            SignalReference : {
                if ( (context.eContainer instanceof OperatorExpression) && 
                     (context.eContainer as OperatorExpression).operator === OperatorType.VAL) {
                     scope = new SimpleScope(getSignalsAndSensors(context))
                }
                else if (context.eContainer instanceof SignalRenaming) {
                    scope = new SimpleScope(getSignalsAndSensors(context))
                }
                else {
                    scope = new SimpleScope(getAllSignals(context))
                }
            }   
            TrapReference ,
            TrapExpression : {
                scope = new SimpleScope(getLocalTraps(context))
            }
            VariableReference : {
                scope = new SimpleScope(getLocalVariables(context))
            }
            ValuedObjectReference : {
                scope = new SimpleScope(getValuedObjects(context))
            }
            default : {
                scope = polymorphicFindScopeForReferenceName(context, reference);
            }
        }
        if (scope === null) {
            scope = polymorphicFindScopeForClassName(context, reference);
            if (scope === null) {
                return super.getScope(context, reference);
            }
        }
        return scope
    }
    
    
    /**
     * collect everything of type 'valuedObject' local variables, scl declarations and constants
     * 
     * @param context context
     * 
     * @return list with scope elements
     */
    public static def List<IEObjectDescription> getValuedObjects(EObject context) {
        val scopeElems = new ArrayList<IEObjectDescription>();
        var parent = context.eContainer();
        // Go up in the Structure until Module/MainModule
        while (!(parent instanceof Module) && parent !== null) {
            switch (parent) {
                LocalVariableDeclaration : {
                    val decl = (parent as LocalVariableDeclaration).getDeclarations();
                    for (EsterelVariableDeclaration vdecl : decl) {
                        for (varSingle : vdecl.valuedObjects) {
                            scopeElems.add(new EObjectDescription(QualifiedName.create(varSingle
                                    .getName()), varSingle, emptyMap));
                        }
                    }
                }
                Scope : {
                    val decl = (parent as Scope).getDeclarations();
                    for (vdecl : decl) {
                        for (varSingle : vdecl.valuedObjects) {
                            scopeElems.add(new EObjectDescription(QualifiedName.create(varSingle
                                    .getName()), varSingle, emptyMap));
                        }
                    }
                }
            }
            parent = parent.eContainer();
        }
        if (parent instanceof Module) {
            parent.declarations.filter(VariableDeclaration).forEach[ vd |
                vd.valuedObjects.forEach[ v |
                    scopeElems.add(new EObjectDescription(QualifiedName.create(
                                    v.getName()), v, emptyMap));
                ]
            ]
            
        }
        scopeElems.addAll(getAllElements(context, COLLECT_CONSTANTS))
        return scopeElems;
    }

}