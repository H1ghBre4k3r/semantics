/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2016 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.core.kexpressions.keffects.extensions

import de.cau.cs.kieler.core.kexpressions.keffects.AssignOperator
import de.cau.cs.kieler.core.kexpressions.keffects.Assignment

/**
 * @author ssm
 *
 * @kieler.design 2016-01-07 proposed ssm
 * @kieler.rating 2016-01-07 proposed yellow
 */
class KEffectsExtensions {
    
    def isPostfixOperator(AssignOperator operator) {
        operator == AssignOperator.POSTFIXADD || operator == AssignOperator.POSTFIXSUB
    }
    
    def isPostfixOperation(Assignment assignment) {
        assignment.operator.isPostfixOperator
    }
}