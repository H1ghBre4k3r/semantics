/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.lustre.lustre;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AState</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.AState#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.AState#getEquations <em>Equations</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.AState#getAssertions <em>Assertions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.AState#getAutomatons <em>Automatons</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.AState#getTransitions <em>Transitions</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState()
 * @model
 * @generated
 */
public interface AState extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.AState#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Equations</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.Equation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Equations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Equations</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState_Equations()
   * @model containment="true"
   * @generated
   */
  EList<Equation> getEquations();

  /**
   * Returns the value of the '<em><b>Assertions</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.Assertion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assertions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assertions</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState_Assertions()
   * @model containment="true"
   * @generated
   */
  EList<Assertion> getAssertions();

  /**
   * Returns the value of the '<em><b>Automatons</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.Automaton}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Automatons</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Automatons</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState_Automatons()
   * @model containment="true"
   * @generated
   */
  EList<Automaton> getAutomatons();

  /**
   * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.ATransition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transitions</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getAState_Transitions()
   * @model containment="true"
   * @generated
   */
  EList<ATransition> getTransitions();

} // AState