/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.esterel.scest

import de.cau.cs.kieler.esterel.EsterelProgram

/**
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
class EsterelPredicate extends SCEstPredicate {
    
    override apply(Object input) {
        if (input instanceof EsterelProgram) {
            return !super.apply(input)
        }
        return false
    }
    
}