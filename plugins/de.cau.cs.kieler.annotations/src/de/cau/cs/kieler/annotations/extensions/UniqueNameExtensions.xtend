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
package de.cau.cs.kieler.annotations.extensions

import de.cau.cs.kieler.annotations.NamedObject

/**
 * @author ssm
 * @kieler.design 2017-06-28 proposed 
 * @kieler.rating 2017-06-28 proposed yellow 
 *
 */
class UniqueNameExtensions {
    
    def createUniqueNameCache() {
        new UniqueNameCache
    }   
    
    def <T extends NamedObject> T uniqueName(T namedObject, UniqueNameCache nameCache) {
        namedObject.name = nameCache.getNewUniqueName(namedObject.name)
        return namedObject
    }
    
}