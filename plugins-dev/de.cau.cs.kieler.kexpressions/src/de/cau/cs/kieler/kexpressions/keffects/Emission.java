/**
 */
package de.cau.cs.kieler.kexpressions.keffects;

import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.ValuedObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Emission</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.Emission#getValuedObject <em>Valued Object</em>}</li>
 *   <li>{@link de.cau.cs.kieler.kexpressions.keffects.Emission#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.kexpressions.keffects.KEffectsPackage#getEmission()
 * @model
 * @generated
 */
public interface Emission extends Effect {
	/**
     * Returns the value of the '<em><b>Valued Object</b></em>' reference.
     * <!-- begin-user-doc -->
     * @return the value of the '<em>Valued Object</em>' reference.
     * @see #setValuedObject(ValuedObject)
     * @see de.cau.cs.kieler.kexpressions.keffects.KEffectsPackage#getEmission_ValuedObject()
     * @model required="true"
     * @generated
     */
	ValuedObject getValuedObject();

	/**
     * Sets the value of the '{@link de.cau.cs.kieler.kexpressions.keffects.Emission#getValuedObject <em>Valued Object</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Valued Object</em>' reference.
     * @see #getValuedObject()
     * @generated
     */
	void setValuedObject(ValuedObject value);

	/**
     * Returns the value of the '<em><b>New Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * @return the value of the '<em>New Value</em>' containment reference.
     * @see #setNewValue(Expression)
     * @see de.cau.cs.kieler.kexpressions.keffects.KEffectsPackage#getEmission_NewValue()
     * @model containment="true"
     * @generated
     */
	Expression getNewValue();

	/**
     * Sets the value of the '{@link de.cau.cs.kieler.kexpressions.keffects.Emission#getNewValue <em>New Value</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Value</em>' containment reference.
     * @see #getNewValue()
     * @generated
     */
	void setNewValue(Expression value);

} // Emission
