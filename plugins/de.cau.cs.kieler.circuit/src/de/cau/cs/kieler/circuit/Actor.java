/**
 */
package de.cau.cs.kieler.circuit;

import de.cau.cs.kieler.core.annotations.NamedObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Actor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.circuit.Actor#getInnerActors <em>Inner Actors</em>}</li>
 *   <li>{@link de.cau.cs.kieler.circuit.Actor#getInnerLinks <em>Inner Links</em>}</li>
 *   <li>{@link de.cau.cs.kieler.circuit.Actor#getType <em>Type</em>}</li>
 *   <li>{@link de.cau.cs.kieler.circuit.Actor#getPorts <em>Ports</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.cau.cs.kieler.circuit.CircuitPackage#getActor()
 * @model
 * @generated
 */
public interface Actor extends Linkable, NamedObject {
	/**
	 * Returns the value of the '<em><b>Inner Actors</b></em>' containment reference list.
	 * The list contents are of type {@link de.cau.cs.kieler.circuit.Actor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inner Actors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inner Actors</em>' containment reference list.
	 * @see de.cau.cs.kieler.circuit.CircuitPackage#getActor_InnerActors()
	 * @model containment="true"
	 * @generated
	 */
	EList<Actor> getInnerActors();

	/**
	 * Returns the value of the '<em><b>Inner Links</b></em>' containment reference list.
	 * The list contents are of type {@link de.cau.cs.kieler.circuit.Link}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inner Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inner Links</em>' containment reference list.
	 * @see de.cau.cs.kieler.circuit.CircuitPackage#getActor_InnerLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getInnerLinks();

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link de.cau.cs.kieler.circuit.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see de.cau.cs.kieler.circuit.CircuitPackage#getActor_Ports()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see de.cau.cs.kieler.circuit.CircuitPackage#getActor_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link de.cau.cs.kieler.circuit.Actor#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // Actor