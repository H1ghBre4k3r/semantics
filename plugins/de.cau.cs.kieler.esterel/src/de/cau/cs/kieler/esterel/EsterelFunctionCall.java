/**
 */
package de.cau.cs.kieler.esterel;

import de.cau.cs.kieler.kexpressions.Expression;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.esterel.EsterelFunctionCall#getFunction <em>Function</em>}</li>
 *   <li>{@link de.cau.cs.kieler.esterel.EsterelFunctionCall#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.esterel.EsterelPackage#getEsterelFunctionCall()
 * @model
 * @generated
 */
public interface EsterelFunctionCall extends Expression {
    /**
     * Returns the value of the '<em><b>Function</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Function</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Function</em>' reference.
     * @see #setFunction(Function)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getEsterelFunctionCall_Function()
     * @model
     * @generated
     */
    Function getFunction();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.EsterelFunctionCall#getFunction <em>Function</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Function</em>' reference.
     * @see #getFunction()
     * @generated
     */
    void setFunction(Function value);

    /**
     * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
     * The list contents are of type {@link de.cau.cs.kieler.kexpressions.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter</em>' containment reference list.
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getEsterelFunctionCall_Parameter()
     * @model containment="true"
     * @generated
     */
    EList<Expression> getParameter();

} // EsterelFunctionCall
