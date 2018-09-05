/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg;

import de.cau.cs.kieler.annotations.Annotatable;
import de.cau.cs.kieler.annotations.NamedObject;
import de.cau.cs.kieler.kexpressions.keffects.Linkable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scg.Node#isIsInitial <em>Is Initial</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scg.Node#isSchizophrenic <em>Schizophrenic</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.scg.ScgPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends Annotatable, NamedObject, Linkable {
    /**
     * Returns the value of the '<em><b>Is Initial</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Initial</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Initial</em>' attribute.
     * @see #setIsInitial(boolean)
     * @see de.cau.cs.kieler.scg.ScgPackage#getNode_IsInitial()
     * @model
     * @generated
     */
    boolean isIsInitial();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.scg.Node#isIsInitial <em>Is Initial</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Initial</em>' attribute.
     * @see #isIsInitial()
     * @generated
     */
    void setIsInitial(boolean value);

    /**
     * Returns the value of the '<em><b>Schizophrenic</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schizophrenic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schizophrenic</em>' attribute.
     * @see #setSchizophrenic(boolean)
     * @see de.cau.cs.kieler.scg.ScgPackage#getNode_Schizophrenic()
     * @model
     * @generated
     */
    boolean isSchizophrenic();

    /**
     * Sets the value of the '{@link de.cau.cs.kieler.scg.Node#isSchizophrenic <em>Schizophrenic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schizophrenic</em>' attribute.
     * @see #isSchizophrenic()
     * @generated
     */
    void setSchizophrenic(boolean value);

} // Node
