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
package de.cau.cs.kieler.kexpressions.keffects.util

import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.kexpressions.Expression
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCompareExtensions
import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.keffects.Assignment
import de.cau.cs.kieler.kexpressions.Value
import org.eclipse.xtend.lib.annotations.Accessors
import de.cau.cs.kieler.kexpressions.ValuedObject

/**
 * Container class für ValuedObjects
 * 
 * @author ssm
 * @kieler.design 2017-05-31 proposed 
 * @kieler.rating 2017-05-31 proposed yellow
 */
class ValuedObjectContainer {
    
    @Inject extension KExpressionsCompareExtensions
    
    @Accessors(PUBLIC_GETTER) var ValuedObject valuedObject = null
    private var indices = <Expression> newArrayList
    @Accessors(PUBLIC_GETTER) var boolean potentiallyEqual = false
    @Accessors var boolean strictEqual = true

    def set(Assignment assignment) {
        valuedObject = assignment.reference.valuedObject
        indices.clear
        for(e : assignment.reference.indices) {
            indices += e
        }
    }
    
    def set(ValuedObjectReference valuedObjectReference) {
        if (valuedObjectReference.valuedObject == null) {
            throw new IllegalArgumentException("The valued object of reference " + valuedObjectReference + " is null!")
        }
        valuedObject = valuedObjectReference.valuedObject
        indices.clear
        for(e : valuedObjectReference.indices) {
            indices += e
        }
    }
    
    def set(ValuedObject valuedObject) {
        this.valuedObject = valuedObject
        indices.clear
    }    
    
    def setPotentiallyEqual(boolean pe) {
        potentiallyEqual = pe
        if (strictEqual && pe) strictEqual = false
    }
    
    override equals(Object object) {
        if (object instanceof ValuedObjectContainer) {
            val localStrict = strictEqual && object.strictEqual
            if (!localStrict) {
                if (potentiallyEquals || object.potentiallyEquals) return potentiallyEquals(object)
                return semanticEquals(object)
            }
            return super.equals(object)
        }
        return false
    }
    
    def semanticEquals(Object object) {
        if (object instanceof ValuedObjectContainer) {
            if (!valuedObject.equals(object.valuedObject)) return false
            if (indices.size != object.indices.size) return false
            if (indices.size>0) {
                for(var i = 0; i < indices.size; i++) {
                    if (!indices.get(i).equals2(object.indices.get(i))) {
                        return false
                    }
                }
            }
            return true
        }
        return super.equals(object)
    }
    
    private def boolean cannotDetermineEquality(Object object) {
        if (object instanceof ValuedObjectContainer) {
            if (indices.size != object.indices.size) return false
            if (indices.size > 0 && object.indices.size > 0) {
                for(var i = 0; i < indices.size; i++) {
                    if (((indices.get(i) instanceof Value) && (!(object.indices.get(i) instanceof Value))) || 
                        ((!(indices.get(i) instanceof Value)) && (object.indices.get(i) instanceof Value))) {
                            return true
                    }
                }
            }
        }
        return false
    }
    
    def potentiallyEquals(Object object) {
        return semanticEquals(object) || cannotDetermineEquality(object)
    }
    
    override hashCode() {
        valuedObject.hashCode
    }
    
}