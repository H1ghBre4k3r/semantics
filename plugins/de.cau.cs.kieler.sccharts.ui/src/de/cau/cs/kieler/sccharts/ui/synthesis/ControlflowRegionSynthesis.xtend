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
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kicool.ui.kitt.tracing.TracingVisualizationProperties
import de.cau.cs.kieler.kicool.ui.synthesis.updates.MessageObjectReferencesManager
import de.cau.cs.kieler.klighd.kgraph.KGraphFactory
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.KRectangle
import de.cau.cs.kieler.klighd.krendering.KRendering
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.Region
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsSerializeHRExtensions
import de.cau.cs.kieler.sccharts.processors.transformators.For
import de.cau.cs.kieler.sccharts.ui.synthesis.actions.ReferenceExpandAction
import de.cau.cs.kieler.sccharts.ui.synthesis.hooks.actions.MemorizingExpandCollapseAction
import de.cau.cs.kieler.sccharts.ui.synthesis.styles.ColorStore
import de.cau.cs.kieler.sccharts.ui.synthesis.styles.ControlflowRegionStyles
import org.eclipse.elk.alg.layered.options.CenterEdgeLabelPlacementStrategy
import org.eclipse.elk.alg.layered.options.FixedAlignment
import org.eclipse.elk.alg.layered.options.LayeredOptions
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.EdgeRouting

import static de.cau.cs.kieler.sccharts.ui.synthesis.GeneralSynthesisOptions.*

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*

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
    @Inject extension ColorStore

    override performTranformation(ControlflowRegion region) {
        val node = region.createNode().associateWith(region);

        // Set KIdentifier for use with incremental update
        if (!region.name.nullOrEmpty) {
            node.data += KGraphFactory::eINSTANCE.createKIdentifier => [it.id = region.name]
        }
        
        if (USE_KLAY.booleanValue) {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.layered");
            node.setLayoutOption(LayeredOptions::NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment::BALANCED);
            node.setLayoutOption(LayeredOptions::EDGE_LABELS_CENTER_LABEL_PLACEMENT_STRATEGY,
                CenterEdgeLabelPlacementStrategy::TAIL_LAYER);
        } else {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.graphviz.dot");
            node.setLayoutOption(CoreOptions::SPACING_NODE_NODE, 40.0);
        }
        node.addLayoutParam(CoreOptions::EDGE_ROUTING, EdgeRouting::SPLINES);
        node.addLayoutParam(CoreOptions::SEPARATE_CONNECTED_COMPONENTS, false)
        // Direction is set by the {@link LayoutHook}
        
        // Do not set! This is handled by the ExpandCollapseHook
        // node.initiallyExpand
        
        // This node does not support comment boxes on the same layer, because regions are layouted by the box layouter.
        node.setProperty(MessageObjectReferencesManager.SUPPORTS_COMMENT_BOXES, false)
        
        // User schedules
        val sLabel = new StringBuilder
        val userSchedule = region.schedule
        if (userSchedule.size > 0) {
            val exists = <Pair<ValuedObject, Integer>> newHashSet
            for (s : userSchedule) {
                val existPair = new Pair<ValuedObject, Integer>(s.valuedObject, s.priority)
                if (!exists.contains(existPair)) {
                    sLabel.append(", ")
                    sLabel.append(s.valuedObject.name + " " + s.priority)
                    exists.add(existPair)
                }
            }
        }        

        if (!region.states.empty) {
            var forLabel = ""
            if (region.counterVariable !== null) {
                val range = For.getForRegionRange(region)
                forLabel = " | " + region.counterVariable.name + "[" + range.first + ", " + range.second + "]"
            }
            
            val label = (if(region.label.nullOrEmpty) "" else " " + region.serializeHR.toString) + 
                forLabel + sLabel.toString

            // Expanded
            node.addRegionFigure(region.final) => [
                setAsExpandedView
                associateWith(region)
                addDoubleClickAction(ReferenceExpandAction::ID)
                if (region.declarations.empty && region.actions.empty) {
                    addStatesArea(!label.nullOrEmpty);
                } else {
                    addStatesAndDeclarationsAndActionsArea(!label.nullOrEmpty, region.declarations.size > 3);
                    // Add declarations
                    for (declaration : region.variableDeclarations) {
                        addDeclarationLabel(declaration.serializeHighlighted(true)) => [
                            setProperty(TracingVisualizationProperties.TRACING_NODE, true);
                            associateWith(declaration);
                            eAllContents.filter(typeof(KRendering)).forEach[associateWith(declaration)];
                        ]
                    }
                    // Add actions
                    for (action : region.actions) {
                        addActionLabel(action.serializeHighlighted(true)) => [
                            setProperty(TracingVisualizationProperties.TRACING_NODE, true);
                            associateWith(action);
                            eAllContents.filter(KRendering).forEach[associateWith(action)];
                        ]
                    }
                }
                if (sLabel.length > 0) it.setUserScheduleStyle
                // Add Button after area to assure correct overlapping
                addCollapseButton(label).addDoubleClickAction(MemorizingExpandCollapseAction.ID);
            ]

            // Collapsed
            node.addRegionFigure(region.final) => [
                setAsCollapsedView
                associateWith(region)
                if (sLabel.length > 0) it.setUserScheduleStyle
                addDoubleClickAction(ReferenceExpandAction::ID)
                addExpandButton(label).addDoubleClickAction(MemorizingExpandCollapseAction.ID);
            ]

            // Add inner states
            for (state : region.states) {
                node.children += state.transform;
            }

        } else if (region.reference !== null) {
            var label = if(region.label.nullOrEmpty) "" else " " + region.serializeHR.toString
            label += "@"
            if (region.reference.scope !== null) {
                label += (region.reference.scope as Region).serializeHR
            } else {
                label += "UnresolvedReference"
            }
            if (SHOW_BINDINGS.booleanValue) {
                label += region.reference.parameters.serializeHRParameters
            }
            
            node.createReferenceRegionFigures(label) => [
                node.data.filter(KRectangle).forEach[
                    it.foreground = ColorStore.Color.STATE_REFERENCED_BACKGROUND_GRADIENT_2.color
                    it.lineWidth = it.lineWidth.lineWidth + 1
                ]
            ]
        } else {
            node.addRegionFigure(region.final)
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
        } else {
            node.addLayoutParam(CoreOptions::ALGORITHM, "org.eclipse.elk.graphviz.dot");
            node.setLayoutOption(CoreOptions::SPACING_NODE_NODE, 40.0);
        }
        node.addLayoutParam(CoreOptions::EDGE_ROUTING, EdgeRouting::SPLINES);

        node.createReferenceRegionFigures(null)

        return node;
    }
    
    protected def KNode createReferenceRegionFigures(KNode node, String label) {
        // Set initially collapsed
        node.setLayoutOption(KlighdProperties::EXPAND, false);

        // Expanded
        node.addRegionFigure(false) => [
            setAsExpandedView
            addStatesArea(label !== null)
            addDoubleClickAction(ReferenceExpandAction::ID)
            // Add Button after area to assure correct overlapping
            // Use special expand action to resolve references
            addCollapseButton(label).addDoubleClickAction(ReferenceExpandAction::ID)
        ]

        // Collapsed
        node.addRegionFigure(false) => [
            setAsCollapsedView
            addDoubleClickAction(ReferenceExpandAction::ID)
            addExpandButton(label).addDoubleClickAction(ReferenceExpandAction::ID)
        ]

        return node;
    }

}
