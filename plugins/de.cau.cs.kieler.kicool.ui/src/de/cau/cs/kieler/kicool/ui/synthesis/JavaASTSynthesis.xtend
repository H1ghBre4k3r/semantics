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
package de.cau.cs.kieler.kicool.ui.synthesis

import com.google.inject.Inject
import de.cau.cs.kieler.klighd.SynthesisOption
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.krendering.extensions.KColorExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import java.util.List
import java.util.regex.Pattern
import org.eclipse.elk.alg.layered.options.LayeredOptions
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.Direction
import org.eclipse.elk.core.options.EdgeRouting
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*

/**
 * @author ssm
 * @kieler.design 2018-06-01 proposed
 * @kieler.rating 2018-06-01 proposed yellow 
 */
@ViewSynthesisShared
class JavaASTSynthesis extends AbstractDiagramSynthesis<CompilationUnit> {

    @Inject extension KNodeExtensions
    @Inject extension KEdgeExtensions
    @Inject extension KRenderingExtensions
    @Inject extension KContainerRenderingExtensions
    @Inject extension KPolylineExtensions
    @Inject extension KColorExtensions

    public static val SynthesisOption SHOW_FULLY_QUALIFIED_NAMES = SynthesisOption::createCheckOption(JavaASTSynthesis,
        "Show Fully Qualified Names", false)
    public static val SynthesisOption SHOW_HUMAN_READABLE_NAMES = SynthesisOption::createCheckOption(JavaASTSynthesis,
        "Show Human Readable Names", true)

    val processorConnections = <String, Integer>newHashMap

    override getDisplayedSynthesisOptions() {
        <SynthesisOption>newLinkedList => [
            add(SHOW_FULLY_QUALIFIED_NAMES)
            add(SHOW_HUMAN_READABLE_NAMES)
        ]
    }

    override transform(CompilationUnit model) {
        val rootRootNode = model.createNode(0)
        val rootNode = model.createNode(1)
        processorConnections.clear

        rootRootNode.addLayoutParam(CoreOptions::ALGORITHM, LayeredOptions.ALGORITHM_ID)
        rootRootNode.setLayoutOption(CoreOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL)
        rootRootNode.setLayoutOption(CoreOptions::DIRECTION, Direction.DOWN)
        rootNode.addLayoutParam(CoreOptions::ALGORITHM, LayeredOptions.ALGORITHM_ID)
        rootNode.setLayoutOption(CoreOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL)
        rootNode.setLayoutOption(CoreOptions::DIRECTION, Direction.DOWN)
        rootNode.setLayoutOption(LayeredOptions::SPACING_NODE_NODE, 80d)
        rootNode.setLayoutOption(LayeredOptions::SPACING_NODE_NODE_BETWEEN_LAYERS, 80d)
        rootNode.setLayoutOption(LayeredOptions::SPACING_EDGE_NODE, 80d)
        rootNode.setLayoutOption(LayeredOptions::SPACING_EDGE_EDGE, 10d)
        rootNode.setLayoutOption(LayeredOptions::SPACING_EDGE_EDGE_BETWEEN_LAYERS, 40d)
        rootNode.setLayoutOption(LayeredOptions::SPACING_EDGE_NODE_BETWEEN_LAYERS, 40d)

        rootNode.children += model.children.createASTNodes(null)
        rootNode.addRoundedRectangle(0, 0, 1) => [
            foreground = "fff".color
        ]

        rootRootNode.children += rootNode
        rootRootNode
    }

    def List<KNode> createASTNodes(Iterable<ASTNode> nodes, KNode parentKNode) {
        val kNodes = <KNode>newLinkedList
        if(nodes === null) return kNodes

        for (node : nodes) {

            val kNode = node.createNode
            kNode.setMinimalNodeSize(64, 64); // 2 x corner radius
            val content = kNode.addRoundedRectangle(4, 4, 1) => [
                setBackgroundGradient("#fff".color, "#ddd".color, 90);
                foreground = "#000".color;
            ]

            content.addText(naming(node.toString)) => [
                fontSize = 32;
                // Add surrounding space
                setGridPlacementData().from(LEFT, 32, 0, TOP, 28, 0).to(RIGHT, 32, 0, BOTTOM, 28, 0);
                suppressSelectability
            ]

            kNodes += kNode

            if (parentKNode !== null) {
                kNode.createEdge(parentKNode) => [
                    source = parentKNode
                    target = kNode
                    addPolyline => [
                        addHeadArrowDecorator
                        addJunctionPointDecorator
                        selectionLineWidth = 6f
                        selectionForeground = "#99f".color
                    ]
                ]
            }

            kNodes += node.children.createASTNodes(kNode)
        }

        return kNodes
    }

    protected def naming(String s) {
        if (!SHOW_FULLY_QUALIFIED_NAMES.booleanValue || SHOW_HUMAN_READABLE_NAMES.booleanValue) {
            val pattern = Pattern.compile("^.*\\.(.*)@.*");
            val matcher = pattern.matcher(s)
            if (matcher.find) {
                if (SHOW_HUMAN_READABLE_NAMES.booleanValue) {
                    var s2 = matcher.group(1).substring(4)
                    val p2 = Pattern.compile("[A-Z][a-z]*")
                    val m2 = p2.matcher(s2)
                    var s3 = ""
                    while (m2.find) {
                        s3 = s3 + m2.group + " "
                    }
                    return s3.trim
                } else {
                    return matcher.group(1)
                }
            }
            return s
        }
        return s
    }

    def getChildren(ASTNode node) {
        val children = <ASTNode>newArrayList
        val list = node.structuralPropertiesForType()
        for (var i = 0; i < list.size(); i++) {
            val curr = list.get(i) as StructuralPropertyDescriptor
            val child = node.getStructuralProperty(curr)
            if (child instanceof List) {
                (child as List).forEach [
                    if (it instanceof ASTNode) {
                        children.add(it)
                    }
                ]
            } else if (child instanceof ASTNode) {
                children.add(child)
            }
        }
        children
    }

}
