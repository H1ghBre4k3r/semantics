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
package de.cau.cs.kieler.scg;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.cau.cs.kieler.scg.Schedule#getGuards <em>Guards</em>}</li>
 * </ul>
 *
 * @see de.cau.cs.kieler.scg.ScgPackage#getSchedule()
 * @model
 * @generated
 */
public interface Schedule extends EObject {
    /**
	 * Returns the value of the '<em><b>Guards</b></em>' reference list.
	 * The list contents are of type {@link de.cau.cs.kieler.scg.Guard}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guards</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guards</em>' reference list.
	 * @see de.cau.cs.kieler.scg.ScgPackage#getSchedule_Guards()
	 * @model
	 * @generated
	 */
	EList<Guard> getGuards();

} // Schedule
