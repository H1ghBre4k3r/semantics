/**
 */
package de.cau.cs.kieler.kivis.kivis;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.Interaction#getEvent <em>Event</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.Interaction#isAfterTick <em>After Tick</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.Interaction#isBeforeTick <em>Before Tick</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.Interaction#getActions <em>Actions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.Interaction#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction()
 * @model
 * @generated
 */
public interface Interaction extends EObject
{
  /**
   * Returns the value of the '<em><b>Event</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Event</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Event</em>' containment reference.
   * @see #setEvent(Event)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction_Event()
   * @model containment="true"
   * @generated
   */
  Event getEvent();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.Interaction#getEvent <em>Event</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Event</em>' containment reference.
   * @see #getEvent()
   * @generated
   */
  void setEvent(Event value);

  /**
   * Returns the value of the '<em><b>After Tick</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>After Tick</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>After Tick</em>' attribute.
   * @see #setAfterTick(boolean)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction_AfterTick()
   * @model
   * @generated
   */
  boolean isAfterTick();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.Interaction#isAfterTick <em>After Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>After Tick</em>' attribute.
   * @see #isAfterTick()
   * @generated
   */
  void setAfterTick(boolean value);

  /**
   * Returns the value of the '<em><b>Before Tick</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Before Tick</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Before Tick</em>' attribute.
   * @see #setBeforeTick(boolean)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction_BeforeTick()
   * @model
   * @generated
   */
  boolean isBeforeTick();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.Interaction#isBeforeTick <em>Before Tick</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Before Tick</em>' attribute.
   * @see #isBeforeTick()
   * @generated
   */
  void setBeforeTick(boolean value);

  /**
   * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.kivis.kivis.Action}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actions</em>' containment reference list.
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction_Actions()
   * @model containment="true"
   * @generated
   */
  EList<Action> getActions();

  /**
   * Returns the value of the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Condition</em>' containment reference.
   * @see #setCondition(Condition)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getInteraction_Condition()
   * @model containment="true"
   * @generated
   */
  Condition getCondition();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.Interaction#getCondition <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Condition</em>' containment reference.
   * @see #getCondition()
   * @generated
   */
  void setCondition(Condition value);

} // Interaction