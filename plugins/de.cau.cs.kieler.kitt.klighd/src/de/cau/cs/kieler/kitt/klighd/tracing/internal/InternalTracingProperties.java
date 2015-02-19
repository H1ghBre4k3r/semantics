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
package de.cau.cs.kieler.kitt.klighd.tracing.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Predicate;

import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.krendering.KStyle;
import de.cau.cs.kieler.core.properties.IProperty;
import de.cau.cs.kieler.core.properties.Property;
import de.cau.cs.kieler.core.util.Pair;
import de.cau.cs.kieler.kiml.klayoutdata.KLayoutData;
import de.cau.cs.kieler.kitt.klighd.tracing.TracingSynthesisOption.TracingMode;
import de.cau.cs.kieler.kitt.tracing.internal.TracingMapping;

/**
 * Contains Properties for marking tracing diagrams.
 * 
 * @author als
 * @kieler.design 2014-08-19 proposedw
 * @kieler.rating 2014-08-19 proposed yellow
 * 
 */
public final class InternalTracingProperties {

    /**
     * Privates constructor to prevent instantiation.
     */
    private InternalTracingProperties() {
    }

    // -- ViewContext Properties --
    // ----------------------------
    // These Properties are added to / used in the ViewContext

    /**
     * Indicates Tracing visualization mode of a diagram in its ViewContext.
     */
    public static final IProperty<TracingMode> VISUALIZATION_MODE =
            new Property<TracingMode>(
                    "de.cau.cs.kieler.kitt.klighd.tracing.internal.visualization.mode",
                    TracingMode.NO_TRACING);

    /**
     * Contains applied mappings.
     */
    public static final IProperty<TracingMapping> MAPPING = new Property<TracingMapping>(
            "de.cau.cs.kieler.kitt.klighd.tracing.internal.map", null);

    /**
     * Contains equivalence classes between diagram elements and model elements especially those
     * which have no direct representation in the diagram.
     */
    public static final IProperty<TracingMapping> DIAGRAM_EQUIVALENCE_CLASSES =
            new Property<TracingMapping>(
                    "de.cau.cs.kieler.kitt.klighd.tracing.internal.equivalence.classes", null);

    // -- KGraph Properties --
    // ----------------------------
    // These Properties are added to / used in KGraph including Renderings

    /**
     * Marks an edge as a tracing edge when object is not null. Object indicates the origin object
     * pair in source model which is the source and target element of this edge.
     */
    public static final IProperty<Pair<Object, Object>> TRACING_EDGE =
            new Property<Pair<Object, Object>>("de.cau.cs.kieler.kitt.klighd.tracing.internal.edge", null);

    /**
     * Marks an node as selected as source in a tracing resolve selection.
     */
    public static final IProperty<Boolean> SOURCE_SELECTION = new Property<Boolean>(
            "de.cau.cs.kieler.kitt.klighd.tracing.internal.selection.source", false);

    /**
     * Marks an node as selected as target in a tracing resolve selection.
     */
    public static final IProperty<Boolean> TARGET_SELECTION = new Property<Boolean>(
            "de.cau.cs.kieler.kitt.klighd.tracing.internal.selection.target", false);

    /**
     * Holds the highlighting styles for an diagram element.
     */
    public static final IProperty<List<? extends KStyle>> SELECTION_HIGHLIGHTING =
            new Property<List<? extends KStyle>>(
                    "de.cau.cs.kieler.kitt.klighd.tracing.internal.selection.highlighting", null);

}
