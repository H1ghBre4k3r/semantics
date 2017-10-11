/**
 */
package de.cau.cs.kieler.esterel;

import de.cau.cs.kieler.kexpressions.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trap Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.esterel.TrapExpression#getTrap <em>Trap</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.esterel.EsterelPackage#getTrapExpression()
 * @model
 * @generated
 */
public interface TrapExpression extends Expression {
    /**
     * Returns the value of the '<em><b>Trap</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trap</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trap</em>' reference.
     * @see #setTrap(Signal)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getTrapExpression_Trap()
     * @model
     * @generated
     */
    Signal getTrap();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.TrapExpression#getTrap <em>Trap</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trap</em>' reference.
     * @see #getTrap()
     * @generated
     */
    void setTrap(Signal value);

} // TrapExpression
