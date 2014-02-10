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
package de.cau.cs.kieler.ktm.klighd.util;

import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KLabel;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.core.kgraph.KPort;

/**
 * Serves utility function to calculate the nearest KNode for a KGraphElement.
 * 
 * @author als *
 */
public final class NearestNodeUtil {

    /**
     * private Constructor to prevent instantiation.
     */
    private NearestNodeUtil() {
    }

    /**
     * Calculates the nearest KNode for a KGraphElement which is the most sensible node to use for
     * associations.
     * 
     * @param element
     *            element to find node for
     * @return nearest node or null
     */
    public static KNode getNearestNode(final KGraphElement element) {
        return getNearestNode(element, null);
    }

    /**
     * Calculates the nearest KNode for a KGraphElement which is the most sensible node to use for
     * associations.
     * 
     * @param element
     *            element to find node for
     * @param defaultNode
     *            a default node if no nearest node is found
     * @return nearest node or default node
     */
    public static KNode getNearestNode(final KGraphElement element, final KNode defaultNode) {
        if (element instanceof KNode) {
            return (KNode) element;
        } else if (element instanceof KEdge) {
            return ((KEdge) element).getSource();
        } else if (element instanceof KLabel) {
            return getNearestNode(((KLabel) element).getParent(), defaultNode);
        } else if (element instanceof KPort) {
            return ((KPort) element).getNode();
        }
        return defaultNode;
    }
}
