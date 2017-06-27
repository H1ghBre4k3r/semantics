/**
 */
package de.cau.cs.kieler.sccharts.legacy.kexpressions.kext;

import de.cau.cs.kieler.sccharts.legacy.annotations.Annotatable;

import de.cau.cs.kieler.sccharts.legacy.kexpressions.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotated Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.AnnotatedExpression#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.KExtPackage#getAnnotatedExpression()
 * @model
 * @generated
 */
public interface AnnotatedExpression extends Annotatable {
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
     * @see de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.KExtPackage#getAnnotatedExpression_Expression()
     * @model containment="true"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.sccharts.legacy.kexpressions.kext.AnnotatedExpression#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

} // AnnotatedExpression
