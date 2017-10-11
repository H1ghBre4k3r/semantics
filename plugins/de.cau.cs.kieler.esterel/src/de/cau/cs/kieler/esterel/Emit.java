/**
 */
package de.cau.cs.kieler.esterel;

import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.ValuedObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Emit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.esterel.Emit#getSignal <em>Signal</em>}</li>
 *   <li>{@link de.cau.cs.kieler.esterel.Emit#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.esterel.EsterelPackage#getEmit()
 * @model
 * @generated
 */
public interface Emit extends EsterelStatement {
    /**
     * Returns the value of the '<em><b>Signal</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Signal</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Signal</em>' reference.
     * @see #setSignal(ValuedObject)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getEmit_Signal()
     * @model
     * @generated
     */
    ValuedObject getSignal();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.Emit#getSignal <em>Signal</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Signal</em>' reference.
     * @see #getSignal()
     * @generated
     */
    void setSignal(ValuedObject value);

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression</em>' containment reference.
     * @see #setExpression(Expression)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getEmit_Expression()
     * @model containment="true"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.Emit#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

} // Emit
