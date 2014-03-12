/**
 */
package de.cau.cs.kieler.scl.scl;

import de.cau.cs.kieler.core.annotations.Annotation;

import de.cau.cs.kieler.core.kexpressions.TypeGroup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scl.scl.Program#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scl.scl.Program#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scl.scl.Program#getTypeGroups <em>Type Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.cau.cs.kieler.scl.scl.SclPackage#getProgram()
 * @model
 * @generated
 */
public interface Program extends StatementSequence
{
  /**
   * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.core.annotations.Annotation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Annotations</em>' containment reference list.
   * @see de.cau.cs.kieler.scl.scl.SclPackage#getProgram_Annotations()
   * @model containment="true"
   * @generated
   */
  EList<Annotation> getAnnotations();

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
   * @see de.cau.cs.kieler.scl.scl.SclPackage#getProgram_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.scl.scl.Program#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Type Groups</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.core.kexpressions.TypeGroup}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type Groups</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type Groups</em>' containment reference list.
   * @see de.cau.cs.kieler.scl.scl.SclPackage#getProgram_TypeGroups()
   * @model containment="true"
   * @generated
   */
  EList<TypeGroup> getTypeGroups();

} // Program
