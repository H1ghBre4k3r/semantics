/**
 * generated by Xtext
 */
package de.cau.cs.kieler.lustre.lustre;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getUsesIds <em>Uses Ids</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getProvisions <em>Provisions</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getPackageDeclaration()
 * @model
 * @generated
 */
public interface PackageDeclaration extends EObject
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
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getPackageDeclaration_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Uses Ids</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uses Ids</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uses Ids</em>' attribute list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getPackageDeclaration_UsesIds()
   * @model unique="false"
   * @generated
   */
  EList<String> getUsesIds();

  /**
   * Returns the value of the '<em><b>Provisions</b></em>' containment reference list.
   * The list contents are of type {@link de.cau.cs.kieler.lustre.lustre.Provide}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Provisions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Provisions</em>' containment reference list.
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getPackageDeclaration_Provisions()
   * @model containment="true"
   * @generated
   */
  EList<Provide> getProvisions();

  /**
   * Returns the value of the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body</em>' containment reference.
   * @see #setBody(PackBody)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getPackageDeclaration_Body()
   * @model containment="true"
   * @generated
   */
  PackBody getBody();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.PackageDeclaration#getBody <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body</em>' containment reference.
   * @see #getBody()
   * @generated
   */
  void setBody(PackBody value);

} // PackageDeclaration