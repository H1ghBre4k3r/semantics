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
package de.cau.cs.kieler.kicool.ide.synthesis.updates

import com.google.inject.Inject
import de.cau.cs.kieler.kicool.classes.IColorSystem
import de.cau.cs.kieler.kicool.environments.MessageObjectLink
import de.cau.cs.kieler.kicool.environments.MessageObjectList
import de.cau.cs.kieler.kicool.ide.synthesis.SourceModelTrackingAdapterReplacement
import de.cau.cs.kieler.kicool.ide.synthesis.styles.ColorSystem
import de.cau.cs.kieler.klighd.kgraph.KEdge
import de.cau.cs.kieler.klighd.kgraph.KLabel
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.KPolyline
import de.cau.cs.kieler.klighd.krendering.KRoundedRectangle
import de.cau.cs.kieler.klighd.krendering.KText
import de.cau.cs.kieler.klighd.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KLabelExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import org.eclipse.elk.alg.layered.options.LayerConstraint
import org.eclipse.elk.alg.layered.options.LayeredOptions
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.graph.properties.IProperty
import org.eclipse.elk.graph.properties.IPropertyHolder
import org.eclipse.elk.graph.properties.Property

import static extension de.cau.cs.kieler.kicool.ide.synthesis.styles.ColorStore.*
import static extension de.cau.cs.kieler.kicool.ide.synthesis.updates.ProcessorIdeDataManager.*

/**
 * @author ssm
 * @kieler.design 2017-07-11 proposed 
 * @kieler.rating 2017-07-11 proposed yellow
 *
 */
class MessageObjectReferencesManager {
    
    public static val IProperty<Object> MESSAGE_OBJECT_REFERENCE = 
        new Property<Object>("de.cau.cs.kieler.kicool.ui.updates.messageObjectReference", null)    
        
    public static val IProperty<Boolean> SUPPORTS_COMMENT_BOXES = 
        new Property<Boolean>("de.cau.cs.kieler.kicool.ui.updates.messageObjectReference.supportsCommentBoxes", true)    
    
    @Inject extension KNodeExtensions
    @Inject extension KEdgeExtensions 
    @Inject extension KLabelExtensions
    @Inject extension KRenderingExtensions  
    @Inject extension KContainerRenderingExtensions  
        
    def annotateModelNodes(MessageObjectList references, KNode node) {
        val trackingAdapter = new SourceModelTrackingAdapterReplacement
        node.eAdapters.add(trackingAdapter)
        
        val reverseLabelList = <KEdge> newLinkedList

        val morElements = node.eAllContents.filter(IPropertyHolder).filter[ getProperty(MESSAGE_OBJECT_REFERENCE) !== null ].toList
        
        for(reference : references) {
            if (reference.object !== null) {
                val nodes = trackingAdapter.getTargetElements(reference.object)
                if (nodes.empty) {
                    val commentNode = reference.createCommentBox(reference.message, null, reference.colorSystem as ColorSystem, true)
                    node.children += commentNode
                } else {
                    for (n : nodes) {
                        if (n instanceof KNode) {
                            val parentNode = n.eContainer as KNode
                            val commentBoxSupport = n.getProperty(SUPPORTS_COMMENT_BOXES)
                            val commentNode = reference.createCommentBox(reference.message, n, reference.colorSystem as ColorSystem, commentBoxSupport)
                            
                            if (commentBoxSupport) {
                                parentNode.children.add(commentNode)
                            } else {
                                commentNode.addLayoutParam(LayeredOptions::LAYERING_LAYER_CONSTRAINT, LayerConstraint::FIRST_SEPARATE)
                                n.children.add(commentNode)
                            }
                        } else if (n instanceof KEdge) {
                            reference.createCommentLabel(reference.message, n, reference.colorSystem as ColorSystem)
                            reverseLabelList += n

                            for (obj : morElements) {
                                val mor = obj.getProperty(MESSAGE_OBJECT_REFERENCE)
                                if (mor.equals(reference.payload)) {
                                    if (obj instanceof KEdge) {
                                        obj.container.setFBColorViaExtension(reference.colorSystem as ColorSystem)
                                        obj.container.lineWidth = 2.0f
                                        obj.container.styles.forEach[ propagateToChildren = true ]
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val commentNode = reference.createCommentBox(reference.message, null, reference.colorSystem as ColorSystem, true)
                node.children += commentNode
            }
        }
        
        for (edge : reverseLabelList) {
            val labels = <KLabel> newLinkedList => [ it += edge.labels ]
            edge.labels.clear
            edge.labels += labels.reverse
        }
                
    } 
    
    private def KLabel createCommentLabel(Object association, String text, KEdge edge, ColorSystem colorSystem) {
        val label = edge.createLabel()
        label.configureCenterEdgeLabel(text) // Add text
        label.getKRendering => [ // Configure text
            fontSize = 7;
            
            setBackgroundGradient(colorSystem.background.color, colorSystem.backgroundTarget.color, 90);
            foreground = colorSystem.foreground.color
        ]

        return label;        
    }
    
    private def KNode createCommentBox(Object association, String text, KNode relatedNode, ColorSystem colorSystem, boolean setCommentBoxProperty) {
        val node = association.createNode
        
        if (setCommentBoxProperty) {
            node.addLayoutParam(CoreOptions.COMMENT_BOX, true)
        }
        
        node.addCommentFigure(colorSystem)
        node.addCommentText(text)
        
        if (relatedNode !== null && setCommentBoxProperty) {
            val edge = association.createEdge
            edge.source = relatedNode
            edge.target = node
            edge.addCommentPolyline(colorSystem)        
        }        

        return node        
    }
    
    def KRoundedRectangle addCommentFigure(KNode node, ColorSystem colorSystem) {
        node.setMinimalNodeSize(16, 16)
        node.addRoundedRectangle(1, 1, 1) => [
            setBackgroundGradient(colorSystem.background.color, colorSystem.backgroundTarget.color, 90);
            foreground = colorSystem.foreground.color
        ]
    }

    def KText addCommentText(KNode node, String text) {
        node.getKContainerRendering.addText(text) => [
            fontSize = 8;
            setGridPlacementData().from(LEFT, 4, 0, TOP, 4, 0).to(RIGHT, 4, 0, BOTTOM, 4, 0);
        ]
    }
    
    def KPolyline addCommentPolyline(KEdge edge, ColorSystem colorSystem) {
        edge.addPolyline => [
            lineWidth = 1;
            foreground = colorSystem.foreground.color
        ]
    }
    
    
    static def fillUndefinedColors(MessageObjectList mol, ColorSystem colorSystem) {
        val newReferences = new MessageObjectList
        for(ml : mol) {
            val IColorSystem cs = if (ml.colorSystem !== null) ml.colorSystem else colorSystem
            newReferences.add(new MessageObjectLink(ml.message, ml.object, ml.annotate, cs, null, ml.payload))
        }
        newReferences
    }     
    
}