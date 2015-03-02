/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.kico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class represents a transformation dummy for fast processing transformation dependencies.
 * 
 * @author cmot
 * @kieler.design 2014-03-11 proposed
 * @kieler.rating 2014-03-11 proposed yellow
 * 
 */
public class TransformationDummy {

    /** The dependencies. */
    public HashSet<TransformationDummy> dependencies = new HashSet<TransformationDummy>();

    /** The reverse dependencies. */
    public HashSet<TransformationDummy> reverseDependencies = new HashSet<TransformationDummy>();

    /** The transformation. */
    public Transformation transformation;

    /** The id. */
    public String id;
    
    /** The parent graph/list if any */
    public List<TransformationDummy> parent = null;

    /** The marked. */
    public boolean marked = false;
    
    public int order = -1;
    
    public TransformationDummy() {

    }

    public boolean isGroup() {
        boolean returnValue = (transformation instanceof TransformationGroup);
        return returnValue;
    }

    public boolean isAlternative() {
        if (isGroup() && ((TransformationGroup) transformation).isAlternatives()) {
            return true;
        }
        return false;
    }
    
    public TransformationDummy(Transformation transformation) {
        this.id = transformation.getId();
        this.transformation = transformation;
    }
}
