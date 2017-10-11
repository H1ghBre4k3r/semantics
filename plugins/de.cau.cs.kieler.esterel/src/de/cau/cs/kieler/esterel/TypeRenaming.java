/**
 */
package de.cau.cs.kieler.esterel;

import de.cau.cs.kieler.kexpressions.ValueType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Renaming</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.esterel.TypeRenaming#getNewName <em>New Name</em>}</li>
 *   <li>{@link de.cau.cs.kieler.esterel.TypeRenaming#getNewType <em>New Type</em>}</li>
 *   <li>{@link de.cau.cs.kieler.esterel.TypeRenaming#getOldName <em>Old Name</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.esterel.EsterelPackage#getTypeRenaming()
 * @model
 * @generated
 */
public interface TypeRenaming extends Renaming {
    /**
     * Returns the value of the '<em><b>New Name</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Name</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>New Name</em>' reference.
     * @see #setNewName(TypeDefinition)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getTypeRenaming_NewName()
     * @model
     * @generated
     */
    TypeDefinition getNewName();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.TypeRenaming#getNewName <em>New Name</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Name</em>' reference.
     * @see #getNewName()
     * @generated
     */
    void setNewName(TypeDefinition value);

    /**
     * Returns the value of the '<em><b>New Type</b></em>' attribute.
     * The literals are from the enumeration {@link de.cau.cs.kieler.kexpressions.ValueType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>New Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>New Type</em>' attribute.
     * @see de.cau.cs.kieler.kexpressions.ValueType
     * @see #setNewType(ValueType)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getTypeRenaming_NewType()
     * @model
     * @generated
     */
    ValueType getNewType();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.TypeRenaming#getNewType <em>New Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Type</em>' attribute.
     * @see de.cau.cs.kieler.kexpressions.ValueType
     * @see #getNewType()
     * @generated
     */
    void setNewType(ValueType value);

    /**
     * Returns the value of the '<em><b>Old Name</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old Name</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old Name</em>' reference.
     * @see #setOldName(TypeDefinition)
     * @see de.cau.cs.kieler.esterel.EsterelPackage#getTypeRenaming_OldName()
     * @model
     * @generated
     */
    TypeDefinition getOldName();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.esterel.TypeRenaming#getOldName <em>Old Name</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Name</em>' reference.
     * @see #getOldName()
     * @generated
     */
    void setOldName(TypeDefinition value);

} // TypeRenaming
