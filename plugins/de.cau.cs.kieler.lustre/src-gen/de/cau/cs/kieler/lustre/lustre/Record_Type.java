/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.lustre.lustre;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Record Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.Record_Type#getFields <em>Fields</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getRecord_Type()
 * @model
 * @generated
 */
public interface Record_Type extends EObject
{
  /**
   * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.Field}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fields</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getRecord_Type_Fields()
   * @model containment="true"
   * @generated
   */
  EList<Field> getFields();

} // Record_Type