/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.lustre.lustre;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>If Then Else</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getIfexpr <em>Ifexpr</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getThenexpr <em>Thenexpr</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getElseexpr <em>Elseexpr</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getIfThenElse()
 * @model
 * @generated
 */
public interface IfThenElse extends Expression
{
  /**
   * Returns the value of the '<em><b>Ifexpr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ifexpr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ifexpr</em>' containment reference.
   * @see #setIfexpr(Expression)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getIfThenElse_Ifexpr()
   * @model containment="true"
   * @generated
   */
  Expression getIfexpr();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getIfexpr <em>Ifexpr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ifexpr</em>' containment reference.
   * @see #getIfexpr()
   * @generated
   */
  void setIfexpr(Expression value);

  /**
   * Returns the value of the '<em><b>Thenexpr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Thenexpr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Thenexpr</em>' containment reference.
   * @see #setThenexpr(Expression)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getIfThenElse_Thenexpr()
   * @model containment="true"
   * @generated
   */
  Expression getThenexpr();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getThenexpr <em>Thenexpr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Thenexpr</em>' containment reference.
   * @see #getThenexpr()
   * @generated
   */
  void setThenexpr(Expression value);

  /**
   * Returns the value of the '<em><b>Elseexpr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elseexpr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elseexpr</em>' containment reference.
   * @see #setElseexpr(Expression)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getIfThenElse_Elseexpr()
   * @model containment="true"
   * @generated
   */
  Expression getElseexpr();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.IfThenElse#getElseexpr <em>Elseexpr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Elseexpr</em>' containment reference.
   * @see #getElseexpr()
   * @generated
   */
  void setElseexpr(Expression value);

} // IfThenElse