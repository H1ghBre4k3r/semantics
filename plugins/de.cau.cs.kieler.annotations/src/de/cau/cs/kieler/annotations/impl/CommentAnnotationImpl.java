/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2010 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.annotations.impl;

import org.eclipse.emf.ecore.EClass;

import de.cau.cs.kieler.annotations.AnnotationsPackage;
import de.cau.cs.kieler.annotations.CommentAnnotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CommentAnnotationImpl extends StringAnnotationImpl implements CommentAnnotation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CommentAnnotationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AnnotationsPackage.Literals.COMMENT_ANNOTATION;
    }

} //CommentAnnotationImpl
