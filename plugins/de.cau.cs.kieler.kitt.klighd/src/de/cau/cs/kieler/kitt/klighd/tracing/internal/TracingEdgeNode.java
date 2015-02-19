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

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.core.krendering.KRendering;
import de.cau.cs.kieler.core.math.KVector;
import de.cau.cs.kieler.core.properties.IProperty;
import de.cau.cs.kieler.core.properties.Property;
import de.cau.cs.kieler.klighd.internal.macrolayout.AnchorUtil;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.IKlighdNode;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.INode;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.KChildAreaNode;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.KCustomConnectionFigureNode;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.KEdgeNode;
import de.cau.cs.kieler.klighd.piccolo.internal.nodes.KlighdPath;
import de.cau.cs.kieler.klighd.piccolo.internal.util.Styles;
import de.cau.cs.kieler.klighd.util.ModelingUtil;
import de.cau.cs.kieler.klighd.util.RenderingContextData;
import edu.umd.cs.piccolo.PNode;

/**
 * @author als
 * 
 */
@SuppressWarnings("restriction")
public class TracingEdgeNode extends KCustomConnectionFigureNode implements PropertyChangeListener {

    /** the property for the Piccolo2D representation of a node. */
    private static final IProperty<PNode> REP =
            new Property<PNode>("klighd.piccolo.representation");
    private static final long serialVersionUID = -8894573172549728418L;
    private final HashSet<KChildAreaNode> collapsedParentalChildAreaNodes =
            new HashSet<KChildAreaNode>();
    private final EObject source;
    private final boolean sourceIsEdge;
    private PNode sourceNode = null;
    private final EObject target;
    private final boolean targetIsEdge;
    private PNode targetNode = null;
    private final KNode attachNode;
    private boolean expandPropertyChangeListenersAdded = false;

    /**
     * 
     * @param parent
     * @param source
     * @param target
     * @param attachNode
     *            most upper node in the diagram, but not root node where the KEdge of the Rending
     *            is attached.
     */
    public TracingEdgeNode(final EObject source, final EObject target, final KNode attachNode) {
        Preconditions.checkNotNull(source, "Source object is null");
        Preconditions.checkNotNull(target, "Target object is null");
        Preconditions.checkNotNull(attachNode, "Attach node is null");

        this.source = source;
        this.sourceIsEdge = source instanceof KEdge;
        this.target = target;
        this.targetIsEdge = target instanceof KEdge;
        this.attachNode = attachNode;
        addExpandPropertyChangeListeners();
    }

    /**
     * add listeners to hide edge when source or target container is collapsed
     */
    private void addExpandPropertyChangeListeners() {
        if (!expandPropertyChangeListenersAdded) {
            expandPropertyChangeListenersAdded =
                    addExpandPropertyChangeListeners(source.eContainer())
                            && addExpandPropertyChangeListeners(target.eContainer());
        }
    }

    /**
     * @param source2
     */
    private boolean addExpandPropertyChangeListeners(final EObject startObject) {
        KNode node = getKNode(startObject);
        while (node != null) {
            PNode nodeNode = RenderingContextData.get(node).getProperty(REP);
            if (nodeNode == null) {// In case this edge is created before any PNode is created
                                   // (synthesis time)
                return false;
            }
            if (nodeNode instanceof INode) {
                final KChildAreaNode childAreaNode = ((INode) nodeNode).getChildAreaNode();
                if (childAreaNode != null) {
                    childAreaNode
                            .addPropertyChangeListener(KChildAreaNode.PROPERTY_EXPANSION, this);
                    if (!childAreaNode.isExpanded()) {
                        collapsedParentalChildAreaNodes.add(childAreaNode);
                    }
                }
            }
            node = node.getParent();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void propertyChange(final PropertyChangeEvent event) {
        KChildAreaNode source = (KChildAreaNode) event.getSource();
        if ((Boolean) event.getNewValue()) {
            collapsedParentalChildAreaNodes.remove(source);
        } else {
            collapsedParentalChildAreaNodes.add(source);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(final PNode newParent) {
        // When the diagram is displayed it is faded in and the position of PNode are updated on
        // order and not by change events leading to the situation than source and target PNodes
        // don't have any points. As a consequence this case is handled by an listener reacting on
        // the fade in effect and updating the points again.
        if (newParent instanceof KEdgeNode) {
            newParent.addPropertyChangeListener(PNode.PROPERTY_TRANSPARENCY,
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            // update positions
                            TracingEdgeNode.this.setPoints(null);
                            // work done so remove the listener
                            newParent.removePropertyChangeListener(this);
                        }
                    });
        }
        super.setParent(newParent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPoints(Point2D[] points) {
        try {
            addExpandPropertyChangeListeners();
            if (collapsedParentalChildAreaNodes.isEmpty()) {
                this.setVisible(true);
                final Point2D[] thePoints =
                        new Point2D[] { new Point2D.Double(), new Point2D.Double() };

                // add offset translation of attachNode
                if (attachNode.getParent() != null) {
                    final PNode attachNodeNode =
                            RenderingContextData.get(attachNode.getParent()).getProperty(REP);
                    thePoints[0].setLocation(attachNodeNode.getGlobalTranslation());
                    thePoints[1].setLocation(attachNodeNode.getGlobalTranslation());
                }

                // source position
                if (sourceNode == null) {
                    sourceNode = findPNode(source);
                }
                if (sourceNode != null) {
                    addToPoint(thePoints[0], sourceNode.getGlobalTranslation());
                } else {
                    return;
                }

                // target position
                if (targetNode == null) {
                    targetNode = findPNode(target);
                }
                if (targetNode != null) {
                    addToPoint(thePoints[1], targetNode.getGlobalTranslation());
                } else {
                    return;
                }

                // Add offset to collide edge towards center OR point to middle of an egde
                KVector sourceVector = new KVector(thePoints[0].getX(), thePoints[0].getY());
                KVector targetVector = new KVector(thePoints[1].getX(), thePoints[1].getY());
                Dimension2D sourceDimension = sourceNode.getFullBoundsReference().getSize();
                Dimension2D targetDimension = targetNode.getFullBoundsReference().getSize();
                // target -> source
                if (sourceIsEdge) {
                    addToPoint(thePoints[0], getAppropiateBendPoint((KEdgeNode) sourceNode));
                } else {
                    addToPoint(thePoints[0], AnchorUtil.collideTowardsCenter(
                            targetVector
                                    .clone()
                                    .sub(sourceVector)
                                    .add(targetDimension.getWidth() / 2,
                                            targetDimension.getHeight() / 2),
                            sourceDimension.getWidth(), sourceDimension.getHeight(), null));
                }
                // source -> target
                if (targetIsEdge) {
                    addToPoint(thePoints[1], getAppropiateBendPoint((KEdgeNode) targetNode));
                } else {
                    addToPoint(thePoints[1], AnchorUtil.collideTowardsCenter(
                            sourceVector
                                    .clone()
                                    .sub(targetVector)
                                    .add(sourceDimension.getWidth() / 2,
                                            sourceDimension.getHeight() / 2),
                            targetDimension.getWidth(), targetDimension.getHeight(), null));
                }

                // Apply
                if (thePoints[0] != null && thePoints[1] != null) {
                    for (KlighdPath p : Iterables.filter(this.getChildrenReference(),
                            KlighdPath.class)) {
                        p.setPathToPolyline(thePoints);
                        return;
                    }
                }
            } else {
                this.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyStyles(Styles styles) {
    }

    private PNode findPNode(final EObject elem) {
        if (elem instanceof KGraphElement) {
            return RenderingContextData.get((KGraphElement) elem).getProperty(REP);
        } else if (elem instanceof KRendering) {
            // find least upper KGraphElement
            EObject findObj = ModelingUtil.eContainerOfType(elem, KGraphElement.class);
            // move down to find PNode
            if (findObj != null) {
                PNode findNode = findPNode(findObj);
                if (findNode != null) {
                    return (PNode) Iterators.find(new FunctionalTreeIterator<IKlighdNode>(
                            (IKlighdNode) findNode, true,
                            new Function<Object, Iterator<IKlighdNode>>() {

                                public Iterator<IKlighdNode> apply(Object node) {
                                    return Iterators.filter(((PNode) node).getChildrenIterator(),
                                            IKlighdNode.class);
                                }

                            }), new Predicate<IKlighdNode>() {

                        public boolean apply(IKlighdNode node) {
                            return node.getGraphElement() == elem;
                        }
                    }, null);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * @param sourceNode
     * @return
     */
    private Point2D getAppropiateBendPoint(KEdgeNode node) {
        Point2D[] bendPoints = node.getBendPoints();
        if (bendPoints.length % 2 == 0) {
            return bendPoints[bendPoints.length / 2];
        } else {
            return bendPoints[bendPoints.length / 2 + 1];
        }
    }

    private static Point2D addToPoint(Point2D point, Point2D offset) {
        point.setLocation(point.getX() + offset.getX(), point.getY() + offset.getY());
        return point;
    }

    private static Point2D addToPoint(Point2D point, KVector offset) {
        point.setLocation(point.getX() + offset.x, point.getY() + offset.y);
        return point;
    }

    private KNode getKNode(EObject object) {
        if (object instanceof KNode) {
            return (KNode) object;
        } else if (object instanceof KGraphElement || object instanceof KRendering) {
            return getKNode(object.eContainer());
        } else {
            return null;
        }
    }
}
