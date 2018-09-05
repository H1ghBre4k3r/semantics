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
package de.cau.cs.kieler.scg.impl;

import de.cau.cs.kieler.kexpressions.keffects.impl.DependencyImpl;
import de.cau.cs.kieler.scg.ScgPackage;
import de.cau.cs.kieler.scg.ScheduleDependency;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schedule Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ScheduleDependencyImpl extends DependencyImpl implements ScheduleDependency {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ScheduleDependencyImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ScgPackage.Literals.SCHEDULE_DEPENDENCY;
    }

} //ScheduleDependencyImpl
