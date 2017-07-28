/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2016 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.sccharts.text.sctgenerator

import de.cau.cs.kieler.sccharts.text.sctgenerator.SCTGeneratorProperty
import org.eclipse.xtend.lib.annotations.Data

/**
 * Data holder for SCT Generator properties with primitive values  
 * 
 * @author ssm
 * @kieler.rating 2016-06-10 yellow KI-125 als
 */
@Data
class Value<T> extends SCTGeneratorProperty {
    T value     // The value
}