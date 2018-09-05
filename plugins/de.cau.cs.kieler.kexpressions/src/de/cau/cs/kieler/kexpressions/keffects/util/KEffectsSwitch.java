/**
 */
package de.cau.cs.kieler.kexpressions.keffects.util;

import de.cau.cs.kieler.annotations.Annotatable;

import de.cau.cs.kieler.kexpressions.Call;
import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.FunctionCall;
import de.cau.cs.kieler.kexpressions.PrintCall;
import de.cau.cs.kieler.kexpressions.RandomizeCall;
import de.cau.cs.kieler.kexpressions.ReferenceCall;
import de.cau.cs.kieler.kexpressions.Schedulable;
import de.cau.cs.kieler.kexpressions.TextExpression;

import de.cau.cs.kieler.kexpressions.ValuedObjectReference;
import de.cau.cs.kieler.kexpressions.keffects.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.cau.cs.kieler.kexpressions.keffects.KEffectsPackage
 * @generated
 */
public class KEffectsSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static KEffectsPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public KEffectsSwitch() {
        if (modelPackage == null) {
            modelPackage = KEffectsPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case KEffectsPackage.EFFECT: {
                Effect effect = (Effect)theEObject;
                T result = caseEffect(effect);
                if (result == null) result = caseAnnotatable(effect);
                if (result == null) result = caseSchedulable(effect);
                if (result == null) result = caseLinkable(effect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.ASSIGNMENT: {
                Assignment assignment = (Assignment)theEObject;
                T result = caseAssignment(assignment);
                if (result == null) result = caseEffect(assignment);
                if (result == null) result = caseAnnotatable(assignment);
                if (result == null) result = caseSchedulable(assignment);
                if (result == null) result = caseLinkable(assignment);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.EMISSION: {
                Emission emission = (Emission)theEObject;
                T result = caseEmission(emission);
                if (result == null) result = caseEffect(emission);
                if (result == null) result = caseAnnotatable(emission);
                if (result == null) result = caseSchedulable(emission);
                if (result == null) result = caseLinkable(emission);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.HOSTCODE_EFFECT: {
                HostcodeEffect hostcodeEffect = (HostcodeEffect)theEObject;
                T result = caseHostcodeEffect(hostcodeEffect);
                if (result == null) result = caseEffect(hostcodeEffect);
                if (result == null) result = caseTextExpression(hostcodeEffect);
                if (result == null) result = caseAnnotatable(hostcodeEffect);
                if (result == null) result = caseLinkable(hostcodeEffect);
                if (result == null) result = caseExpression(hostcodeEffect);
                if (result == null) result = caseSchedulable(hostcodeEffect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.REFERENCE_CALL_EFFECT: {
                ReferenceCallEffect referenceCallEffect = (ReferenceCallEffect)theEObject;
                T result = caseReferenceCallEffect(referenceCallEffect);
                if (result == null) result = caseEffect(referenceCallEffect);
                if (result == null) result = caseReferenceCall(referenceCallEffect);
                if (result == null) result = caseAnnotatable(referenceCallEffect);
                if (result == null) result = caseLinkable(referenceCallEffect);
                if (result == null) result = caseValuedObjectReference(referenceCallEffect);
                if (result == null) result = caseCall(referenceCallEffect);
                if (result == null) result = caseExpression(referenceCallEffect);
                if (result == null) result = caseSchedulable(referenceCallEffect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.FUNCTION_CALL_EFFECT: {
                FunctionCallEffect functionCallEffect = (FunctionCallEffect)theEObject;
                T result = caseFunctionCallEffect(functionCallEffect);
                if (result == null) result = caseEffect(functionCallEffect);
                if (result == null) result = caseFunctionCall(functionCallEffect);
                if (result == null) result = caseAnnotatable(functionCallEffect);
                if (result == null) result = caseLinkable(functionCallEffect);
                if (result == null) result = caseCall(functionCallEffect);
                if (result == null) result = caseExpression(functionCallEffect);
                if (result == null) result = caseSchedulable(functionCallEffect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.PRINT_CALL_EFFECT: {
                PrintCallEffect printCallEffect = (PrintCallEffect)theEObject;
                T result = casePrintCallEffect(printCallEffect);
                if (result == null) result = caseEffect(printCallEffect);
                if (result == null) result = casePrintCall(printCallEffect);
                if (result == null) result = caseAnnotatable(printCallEffect);
                if (result == null) result = caseLinkable(printCallEffect);
                if (result == null) result = caseCall(printCallEffect);
                if (result == null) result = caseExpression(printCallEffect);
                if (result == null) result = caseSchedulable(printCallEffect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.RANDOMIZE_CALL_EFFECT: {
                RandomizeCallEffect randomizeCallEffect = (RandomizeCallEffect)theEObject;
                T result = caseRandomizeCallEffect(randomizeCallEffect);
                if (result == null) result = caseEffect(randomizeCallEffect);
                if (result == null) result = caseRandomizeCall(randomizeCallEffect);
                if (result == null) result = caseAnnotatable(randomizeCallEffect);
                if (result == null) result = caseLinkable(randomizeCallEffect);
                if (result == null) result = caseCall(randomizeCallEffect);
                if (result == null) result = caseExpression(randomizeCallEffect);
                if (result == null) result = caseSchedulable(randomizeCallEffect);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.LINKABLE: {
                Linkable linkable = (Linkable)theEObject;
                T result = caseLinkable(linkable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.LINK: {
                Link link = (Link)theEObject;
                T result = caseLink(link);
                if (result == null) result = caseAnnotatable(link);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.DEPENDENCY: {
                Dependency dependency = (Dependency)theEObject;
                T result = caseDependency(dependency);
                if (result == null) result = caseLink(dependency);
                if (result == null) result = caseAnnotatable(dependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case KEffectsPackage.DATA_DEPENDENCY: {
                DataDependency dataDependency = (DataDependency)theEObject;
                T result = caseDataDependency(dataDependency);
                if (result == null) result = caseDependency(dataDependency);
                if (result == null) result = caseLink(dataDependency);
                if (result == null) result = caseAnnotatable(dataDependency);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEffect(Effect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssignment(Assignment object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Emission</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Emission</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEmission(Emission object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Hostcode Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Hostcode Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseHostcodeEffect(HostcodeEffect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reference Call Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reference Call Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReferenceCallEffect(ReferenceCallEffect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Function Call Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Function Call Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFunctionCallEffect(FunctionCallEffect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Print Call Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Print Call Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrintCallEffect(PrintCallEffect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Randomize Call Effect</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Randomize Call Effect</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRandomizeCallEffect(RandomizeCallEffect object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Linkable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Linkable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkable(Linkable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLink(Link object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dependency</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDependency(Dependency object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Dependency</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Dependency</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataDependency(DataDependency object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Annotatable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Annotatable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAnnotatable(Annotatable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedulable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedulable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchedulable(Schedulable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExpression(Expression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Text Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Text Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextExpression(TextExpression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Valued Object Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Valued Object Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValuedObjectReference(ValuedObjectReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Call</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCall(Call object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reference Call</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reference Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReferenceCall(ReferenceCall object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Function Call</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Function Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFunctionCall(FunctionCall object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Print Call</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Print Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrintCall(PrintCall object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Randomize Call</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Randomize Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRandomizeCall(RandomizeCall object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //KEffectsSwitch
