/**
 * generated by Xtext
 */
package de.cau.cs.kieler.lustre.lustre;

import de.cau.cs.kieler.kexpressions.ValueType;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Static Param</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getName <em>Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getType <em>Type</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getNodeInput <em>Node Input</em>}</li>
 *   <li>{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getNodeOutput <em>Node Output</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getStaticParam()
 * @model
 * @generated
 */
public interface StaticParam extends EObject
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
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getStaticParam_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * The literals are from the enumeration {@link de.cau.cs.kieler.kexpressions.ValueType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see de.cau.cs.kieler.kexpressions.ValueType
   * @see #setType(ValueType)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getStaticParam_Type()
   * @model
   * @generated
   */
  ValueType getType();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see de.cau.cs.kieler.kexpressions.ValueType
   * @see #getType()
   * @generated
   */
  void setType(ValueType value);

  /**
   * Returns the value of the '<em><b>Node Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Node Input</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Node Input</em>' containment reference.
   * @see #setNodeInput(Params)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getStaticParam_NodeInput()
   * @model containment="true"
   * @generated
   */
  Params getNodeInput();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getNodeInput <em>Node Input</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Node Input</em>' containment reference.
   * @see #getNodeInput()
   * @generated
   */
  void setNodeInput(Params value);

  /**
   * Returns the value of the '<em><b>Node Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Node Output</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Node Output</em>' containment reference.
   * @see #setNodeOutput(Params)
   * @see de.cau.cs.kieler.lustre.lustre.LustrePackage#getStaticParam_NodeOutput()
   * @model containment="true"
   * @generated
   */
  Params getNodeOutput();

  /**
   * Sets the value of the '{@link de.cau.cs.kieler.lustre.lustre.StaticParam#getNodeOutput <em>Node Output</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Node Output</em>' containment reference.
   * @see #getNodeOutput()
   * @generated
   */
  void setNodeOutput(Params value);

} // StaticParam