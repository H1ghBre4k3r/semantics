/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.kico.klighd.model

import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.Colors
import de.cau.cs.kieler.core.krendering.KColor
import de.cau.cs.kieler.core.krendering.extensions.KColorExtensions
import de.cau.cs.kieler.core.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.core.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KLabelExtensions
import de.cau.cs.kieler.core.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPortExtensions
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.core.properties.IProperty
import de.cau.cs.kieler.core.util.Pair
import de.cau.cs.kieler.kiml.options.Direction
import de.cau.cs.kieler.kiml.options.LayoutOptions
import de.cau.cs.kieler.kitt.klighd.tracing.TracingSynthesisOption
import de.cau.cs.kieler.klighd.KlighdConstants
import de.cau.cs.kieler.klighd.LightDiagramServices
import de.cau.cs.kieler.klighd.krendering.SimpleUpdateStrategy
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.klighd.util.KlighdSynthesisProperties
import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.kitt.klighd.tracing.TracingVisualizationProperties

/**
 * Diagram synthesis of a KiCoModelChain.
 * 
 * @author als
 * @kieler.design 2014-07-30 proposed
 * @kieler.rating 2014-07-30 proposed yellow
 *
 */
class KiCoModelChainSynthesis extends AbstractDiagramSynthesis<KiCoModelChain> {

    @Inject
    extension KNodeExtensions

    @Inject
    extension KEdgeExtensions

    @Inject
    extension KPortExtensions

    @Inject
    extension KLabelExtensions

    @Inject
    extension KRenderingExtensions

    @Inject
    extension KContainerRenderingExtensions

    @Inject
    extension KPolylineExtensions

    @Inject
    extension KColorExtensions

    // -------------------------------------------------------------------------
    private static val KColor BG_COLOR = RENDERING_FACTORY.createKColor() => [red = 255; green = 202; blue = 119];
    private static val KColor SHADOW_COLOR = RENDERING_FACTORY.createKColor() => [it.color = Colors.BLACK];

    override public getDisplayedSynthesisOptions() {
        return newLinkedList(TracingSynthesisOption.synthesisOption);
    }

    override public getDisplayedLayoutOptions() {
        return newLinkedList(
            new Pair<IProperty<?>, List<?>>(LayoutOptions::DIRECTION,
                newImmutableList(Direction::DOWN, Direction::RIGHT)),
            new Pair<IProperty<?>, List<?>>(LayoutOptions::SPACING, newArrayList(0, 150))
        );
    }

    // -------------------------------------------------------------------------
    // The Main entry transform function
    override KNode transform(KiCoModelChain chainWrapper) {
        val chain = chainWrapper.models;
        val rootNode = createNode();
        rootNode.addLayoutParam(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.klay.layered");
        rootNode.addLayoutParam(LayoutOptions::DIRECTION, Direction.RIGHT);

        if (!chain.empty) {

            //transform first
            var first = chainWrapper.transformModel(chain.get(0));
            rootNode.children += first;

            //transform rest and add edges in between
            for (i : 1 ..< chain.size) {
                val second = chainWrapper.transformModel(chain.get(i));
                rootNode.children += second;
                val edge = createEdge => [
                    it.addPolyline => [
                        //if label name is null hide edge
                        it.addArrowDecorator.invisible = chainWrapper.blankMode;
                        it.invisible = chainWrapper.blankMode;
                    ]
                    //if available add label
                    if (!chainWrapper.blankMode && i - 1 < chainWrapper.tranformations.size) {
                        it.createLabel.configureCenterEdgeLabel(chainWrapper.tranformations.get(i - 1),
                            KlighdConstants::DEFAULT_FONT_SIZE,
                            KlighdConstants::DEFAULT_FONT_NAME);
                    }
                ]
                edge.source = first;
                edge.target = second;
                first = second;
            }
        }
        return rootNode;
    }

    private def KNode transformModel(KiCoModelChain chain, Object model) {
        val node = createNode.associateWith(model);

        //if label is not null a parent node is created and model diagram is added in collapsed child area
        if (!chain.blankMode) {

            node.setLayoutOption(KlighdProperties::EXPAND, !chain.collapse.get(model));

            //Expanded Rectangle
            node.createFigure() => [
                it.setProperty(KlighdProperties::EXPANDED_RENDERING, true);
                it.setGridPlacement(1);
                it.addText("[Hide]") => [
                    it.foreground = "blue".color
                    it.fontSize = 9
                    //center
                    it.setSurroundingSpaceGrid(5, 0);
                    it.addSingleClickAction(KlighdConstants::ACTION_COLLAPSE_EXPAND);
                    it.addDoubleClickAction(KlighdConstants::ACTION_COLLAPSE_EXPAND);
                ];
                it.addRectangle => [
                    it.setGridPlacementData.from(LEFT, 8, 0, TOP, 0, 0).to(RIGHT, 8, 0, BOTTOM, 8, 0);
                    it.setBackground = "white".color;
                    it.foreground = "gray".color
                    it.lineWidth = 1;
                    it.addChildArea()
                ];
            ];

            //Collapse Rectangle
            node.createFigure() => [
                it.setProperty(KlighdProperties::COLLAPSED_RENDERING, true);
                it.addText("[Show Model]") => [
                    it.foreground = "blue".color
                    it.fontSize = 9
                    it.addSingleClickAction(KlighdConstants::ACTION_COLLAPSE_EXPAND);
                    it.addDoubleClickAction(KlighdConstants::ACTION_COLLAPSE_EXPAND);
                    it.setSurroundingSpace(5, 0);
                ];
            ];
        } else {
            node.addInvisibleContainerRendering;
        }

        //Create subdiagram from referenced model synthesis or fallback to component synthesis
        var KNode subDiagramNode = null;
        try {
            val properties = new KlighdSynthesisProperties();
            properties.setProperty(KlighdSynthesisProperties.REQUESTED_UPDATE_STRATEGY, SimpleUpdateStrategy.ID);
            subDiagramNode = LightDiagramServices::translateModel(model, usedContext, properties);
        } catch (Exception e) {
            //fallthrou
        }
        if ((subDiagramNode == null || subDiagramNode.children.isEmpty) && model instanceof EObject) { //component synthesis
            subDiagramNode = createNode();
            val modelObject = model as EObject;
            subDiagramNode.children += modelObject.translateEObject
            subDiagramNode.children += modelObject.eAllContents.map [
                val child = it.translateEObject;
                val container = it.eContainer;
                if (container != null) {
                    createEdge => [
                        it.source = container.translateEObject;
                        it.target = child;
                        it.addPolyline.addArrowDecorator;
                    ]
                }
                return child;
            ].toIterable;
        }
        if (subDiagramNode != null && !subDiagramNode.children.isEmpty) {

            // prevent adding of rectangle by adding an invisible own one.
            subDiagramNode.addRectangle.invisible = true;

            //Add subdiagram to collapseable child area
            node.children += subDiagramNode;
            node.setLayoutOption(TracingVisualizationProperties.TRACED_MODEL_ROOT_NODE, true);
        }
        return node;
    }

    private def create node : object.createNode translateEObject(EObject object) {
        node.associateWith(object);

        //create and add colored rectangle for this node
        val figure = node.createFigure;
        figure.background = Colors.GRAY_95;

        //align all text fields in a column.
        figure.setGridPlacement(1);

        //add name of object as text
        figure.addText(object.eClass.name).putToLookUpWith(object) => [
            it.fontSize = 11;
            it.setFontBold(true);
            it.setGridPlacementData().from(LEFT, 9, 0, TOP, 8f, 0).to(RIGHT, 8, 0, BOTTOM, 8, 0);
            it.suppressSelectability;
        ];

        //add all attributes as string representation
        object.eClass.EAllAttributes.filterNull.forEach [
            //add a text with name and value of the attribute
            figure.addText(it.name + ": " + String::valueOf(object.eGet(it))) => [
                it.fontSize = 9;
                it.setGridPlacementData().from(LEFT, 5, 0, TOP, 2, 0).to(RIGHT, 5, 0, BOTTOM, 2, 0);
                it.suppressSelectability;
            ];
        ]
    }

    /**
     * Create and adds colored rectangle for given node.
     */
    private def createFigure(KNode node) {
        val figure = node.addRoundedRectangle(8, 8, 1);
        figure.lineWidth = 1;
        figure.foreground = Colors.GRAY;
        figure.background = BG_COLOR;

        //add shadow
        figure.shadow = SHADOW_COLOR.copy;
        figure.shadow.XOffset = 4;
        figure.shadow.YOffset = 4;

        //minimal node size is necessary if no text will be added
        node.setMinimalNodeSize(2 * figure.cornerWidth, 2 * figure.cornerHeight);
        return figure;
    }
}
