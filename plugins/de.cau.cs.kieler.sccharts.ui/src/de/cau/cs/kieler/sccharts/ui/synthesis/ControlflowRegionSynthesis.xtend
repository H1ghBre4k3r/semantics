/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.ui.synthesis

import com.google.inject.Inject
import de.cau.cs.kieler.kicool.ui.kitt.tracing.TracingVisualizationProperties
import de.cau.cs.kieler.klighd.kgraph.KGraphFactory
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.KRendering
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsSerializeHRExtensions
import de.cau.cs.kieler.sccharts.ui.synthesis.actions.ReferenceExpandAction
import de.cau.cs.kieler.sccharts.ui.synthesis.hooks.actions.MemorizingExpandCollapseAction
import de.cau.cs.kieler.sccharts.ui.synthesis.styles.ControlflowRegionStyles
import org.eclipse.elk.alg.layered.options.EdgeLabelSideSelection
import org.eclipse.elk.alg.layered.options.FixedAlignment
import org.eclipse.elk.alg.layered.options.LayeredOptions
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.EdgeRouting

import static de.cau.cs.kieler.sccharts.ui.synthesis.GeneralSynthesisOptions.*

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.sccharts.processors.transformators.For

/**
 * Transforms {@link ControlflowRegion} into {@link KNode} diagram elements.
 * 
 * @author als ssm
 * @kieler.design 2015-08-13 proposed
 * @kieler.rating 2015-08-13 proposed yellow
 * 
 */
@ViewSynthesisShared
class ControlflowRegionSynthesis extends SubSynthesis<ControlflowRegion, KNode> {

    @Inject extension KNodeExtensionsReplacement
    @Inject extension KRenderingExtensions
    @Inject extension AnnotationsExtensions
    @Inject extension SCChartsSerializeHRExtensions
    @Inject extension KExpressionsDeclarationExtensions    
    @Inject extension StateSynthesis
    @Inject extension ControlflowRegionStyles
    @Inject extension CommentSynthesis

    override performTranformation(ControlflowRegion region) {
        val node = region.createNode().associateWith(region);

        // Set KIdentifier for use with incremental update
        if (!region.name.nullOrEmpty) {
            node.data += KGraphFactory::eINSTANCE.createKIdentifier => [it.id = region.name]
        }
        
        if (USE_KLAY.booleanValue) {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.layered");
            node.setLayoutOption(LayeredOptions::NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment::BALANCED);
            node.setLayoutOption(LayeredOptions::EDGE_LABELS_SIDE_SELECTION, EdgeLabelSideSelection.ALWAYS_UP)
        } else {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.graphviz.dot");
            node.setLayoutOption(CoreOptions::SPACING_NODE_NODE, 40.0);
        }
        node.addLayoutParam(CoreOptions::EDGE_ROUTING, EdgeRouting::SPLINES);
        // Direction is set by the {@link LayoutHook}
        
        // Do not set! This is handled by the ExpandCollapseHook
        // node.initiallyExpand

        if (!region.states.empty) {

            var forLabel = ""
            if (region.counterVariable !== null) {
                val range = For.getForRegionRange(region)
                forLabel = " | " + region.counterVariable.name + "[" + range.first + ", " + range.second + "]"
            }
            
            val label = (if(region.label.nullOrEmpty) "" else " " + region.serializeHR.toString) + forLabel

            // Expanded
            node.addRegionFigure => [
                setAsExpandedView
                associateWith(region)
                if (region.declarations.empty) {
                    addStatesArea(!label.nullOrEmpty);
                } else {
                    addStatesAndDeclarationsArea(!label.nullOrEmpty, region.declarations.size > 3);
                    // Add declarations
                    for (declaration : region.variableDeclarations) {
                        addDeclarationLabel(declaration.serializeHighlighted(true)) => [
                            setProperty(TracingVisualizationProperties.TRACING_NODE, true);
                            associateWith(declaration);
                            eAllContents.filter(typeof(KRendering)).forEach[associateWith(declaration)];
                        ]
                    }
                }
                // Add Button after area to assure correct overlapping
                addButton("[-]" + label).addDoubleClickAction(MemorizingExpandCollapseAction.ID);
            ]

            // Collapsed
            node.addRegionFigure => [
                setAsCollapsedView
                associateWith(region)
                addButton("[+]" + label).addDoubleClickAction(MemorizingExpandCollapseAction.ID);
            ]

            // Add inner states
            for (state : region.states) {
                node.children += state.transform;
            }

        } else {
            node.addRegionFigure;
        }

        val returnNodes = <KNode> newArrayList(node)
        
        if (SHOW_COMMENTS.booleanValue) {
            region.getCommentAnnotations.forEach[
                node.children += it.transform                
            ]
        }                     

        return returnNodes
    }

    /**
     * Create region area for reference states
     * 
     * @param state 
     *          the reference state
     */
    def KNode createReferenceRegion(State state) {
        val node = createNode().associateWith(state); // This association is important for the ReferenceExpandAction
        if (USE_KLAY.booleanValue) {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.layered");
//            node.setLayoutOption(CoreOptions::SPACING_NODE, 3);
//            node.setLayoutOption(CoreOptions::SPACING_BORDER, 8);
        } else {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.graphviz.dot");
            node.setLayoutOption(CoreOptions::SPACING_NODE_NODE, 40.0);
        }
        node.addLayoutParam(CoreOptions::EDGE_ROUTING, EdgeRouting::SPLINES);
//        node.setLayoutOption(CoreOptions::SPACING_NODE, 40);

        // Set initially collapsed
        node.setLayoutOption(KlighdProperties::EXPAND, false);

        // Expanded
        node.addRegionFigure => [
            setAsExpandedView;
            addStatesArea(false);
            // Add Button after area to assure correct overlapping
            // Use special expand action to resolve references
            addButton("[-]").addDoubleClickAction(ReferenceExpandAction::ID);
        ]

        // Collapsed
        node.addRegionFigure => [
            setAsCollapsedView;
            addButton("[+]").addDoubleClickAction(ReferenceExpandAction::ID);
        ]

        return node;
    }

}