/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scgsched;

import de.cau.cs.kieler.core.kexpressions.Expression;
import de.cau.cs.kieler.core.kexpressions.ValuedObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Empty Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scgsched.EmptyExpression#getValuedObject <em>Valued Object</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scgsched.EmptyExpression#getThreadExitObject <em>Thread Exit Object</em>}</li>
 *   <li>{@link de.cau.cs.kieler.scgsched.EmptyExpression#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.cau.cs.kieler.scgsched.ScgschedPackage#getEmptyExpression()
 * @model
 * @generated
 */
public interface EmptyExpression extends EObject {
    /**
	 * Returns the value of the '<em><b>Valued Object</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Valued Object</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Valued Object</em>' reference.
	 * @see #setValuedObject(ValuedObject)
	 * @see de.cau.cs.kieler.scgsched.ScgschedPackage#getEmptyExpression_ValuedObject()
	 * @model required="true"
	 * @generated
	 */
    ValuedObject getValuedObject();

    /**
	 * Sets the value of the '{@link de.cau.cs.kieler.scgsched.EmptyExpression#getValuedObject <em>Valued Object</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valued Object</em>' reference.
	 * @see #getValuedObject()
	 * @generated
	 */
    void setValuedObject(ValuedObject value);

    /**
	 * Returns the value of the '<em><b>Thread Exit Object</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Thread Exit Object</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Exit Object</em>' reference.
	 * @see #setThreadExitObject(ValuedObject)
	 * @see de.cau.cs.kieler.scgsched.ScgschedPackage#getEmptyExpression_ThreadExitObject()
	 * @model required="true"
	 * @generated
	 */
    ValuedObject getThreadExitObject();

    /**
	 * Sets the value of the '{@link de.cau.cs.kieler.scgsched.EmptyExpression#getThreadExitObject <em>Thread Exit Object</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Exit Object</em>' reference.
	 * @see #getThreadExitObject()
	 * @generated
	 */
    void setThreadExitObject(ValuedObject value);

    /**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see de.cau.cs.kieler.scgsched.ScgschedPackage#getEmptyExpression_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
    Expression getExpression();

    /**
	 * Sets the value of the '{@link de.cau.cs.kieler.scgsched.EmptyExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
    void setExpression(Expression value);

} // EmptyExpression
