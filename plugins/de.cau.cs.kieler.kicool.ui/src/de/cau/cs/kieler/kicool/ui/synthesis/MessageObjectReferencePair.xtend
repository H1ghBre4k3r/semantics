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
package de.cau.cs.kieler.kicool.ui.synthesis

import de.cau.cs.kieler.core.model.Pair
import de.cau.cs.kieler.kicool.environments.MessageObjectReferences

/**
 * @author ssm
 * @kieler.design 2017-07-10 proposed
 * @kieler.rating 2017-07-10 proposed yellow  
 */
class MessageObjectReferencePair extends Pair<MessageObjectReferences, Object> {
    
    new(MessageObjectReferences references, Object object) {
        super(references, object)    
    }
    
}