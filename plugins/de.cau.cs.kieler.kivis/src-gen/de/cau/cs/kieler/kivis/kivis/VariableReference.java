/**
 */
package de.cau.cs.kieler.kivis.kivis;

import de.cau.cs.kieler.prom.kibuild.ArrayIndex;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getModel <em>Model</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getArrayIndex <em>Array Index</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getVariableReference()
 * @model
 * @generated
 */
public interface VariableReference extends EObject
{
  /**
   * Returns the value of the '<em><b>Model</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Model</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Model</em>' containment reference.
   * @see #setModel(ModelReference)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getVariableReference_Model()
   * @model containment="true"
   * @generated
   */
  ModelReference getModel();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getModel <em>Model</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Model</em>' containment reference.
   * @see #getModel()
   * @generated
   */
  void setModel(ModelReference value);

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
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getVariableReference_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Array Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Array Index</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Array Index</em>' containment reference.
   * @see #setArrayIndex(ArrayIndex)
   * @see de.cau.cs.kieler.kivis.kivis.KivisPackage#getVariableReference_ArrayIndex()
   * @model containment="true"
   * @generated
   */
  ArrayIndex getArrayIndex();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.kivis.kivis.VariableReference#getArrayIndex <em>Array Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Array Index</em>' containment reference.
   * @see #getArrayIndex()
   * @generated
   */
  void setArrayIndex(ArrayIndex value);

} // VariableReference