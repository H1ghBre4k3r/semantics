/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.klighd

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.google.inject.Injector
import de.cau.cs.kieler.core.kgraph.KEdge
import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.KContainerRendering
import de.cau.cs.kieler.core.krendering.KDecoratorPlacementData
import de.cau.cs.kieler.core.krendering.KPolygon
import de.cau.cs.kieler.core.krendering.KPolyline
import de.cau.cs.kieler.core.krendering.KRendering
import de.cau.cs.kieler.core.krendering.LineStyle
import de.cau.cs.kieler.core.krendering.extensions.KColorExtensions
import de.cau.cs.kieler.core.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.core.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KLabelExtensions
import de.cau.cs.kieler.core.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.core.properties.IProperty
import de.cau.cs.kieler.kiml.options.Direction
import de.cau.cs.kieler.kiml.options.EdgeRouting
import de.cau.cs.kieler.kiml.options.LayoutOptions
import de.cau.cs.kieler.klighd.KlighdConstants
import de.cau.cs.kieler.klighd.TransformationOption
import de.cau.cs.kieler.klighd.transformations.AbstractDiagramSynthesis
import java.util.Collection
import java.util.List
import javax.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.serializer.ISerializer
import de.cau.cs.kieler.klay.layered.p4nodes.NodePlacementStrategy
import de.cau.cs.kieler.klay.layered.properties.LayerConstraint
import de.cau.cs.kieler.klay.layered.properties.Properties


import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.krendering.KColor
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.klighd.SCGraphShapes
import de.cau.cs.kieler.scg.Surface
import de.cau.cs.kieler.scg.Depth
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Fork
import de.cau.cs.kieler.scg.Join

class SCGraphDiagramSynthesis extends AbstractDiagramSynthesis<SCGraph> {
        
    @Inject
    extension KNodeExtensions
    
    @Inject
    extension KEdgeExtensions
    
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
    
    @Inject
    extension SCGraphShapes
    
    private static val TransformationOption SHOW_SIGNAL_DECLARATIONS
        = TransformationOption::createCheckOption("Declarations", true);

    private static val TransformationOption SHOW_LABELS
        = TransformationOption::createCheckOption("Transition labels", true);
        
    private static val TransformationOption SHOW_SHADOW
        = TransformationOption::createCheckOption("Shadow", true);
        
    private static val TransformationOption ALIGN_TICK_EDGES
        = TransformationOption::createCheckOption("Tick edge alignment", true);

    override public getTransformationOptions() {
        return ImmutableSet::of(SHOW_SIGNAL_DECLARATIONS, SHOW_LABELS, SHOW_SHADOW, ALIGN_TICK_EDGES);
    }
    
    override public getRecommendedLayoutOptions() {
        return ImmutableMap::<IProperty<?>, Collection<?>>of(
            LayoutOptions::ALGORITHM, emptyList,
            LayoutOptions::DIRECTION, Direction::values.drop(1).sortBy[ it.name ],
            LayoutOptions::SPACING, newArrayList(0, 255)
        );
    }
    
    override transform(SCGraph model) {
        return model.translate();
    }
    
    
    private static val float REGION_DASH_BLACK = 10;
    private static val float REGION_DASH_WHITE = 5;
    private static val List<Float> REGION_DASH_PATTERN = newArrayList(REGION_DASH_BLACK, REGION_DASH_WHITE); 
    private static val float TRANSITION_DASH_BLACK = 7;
    private static val float TRANSITION_DASH_WHITE = 3;
    private static val List<Float> TRANSITION_DASH_PATTERN = newArrayList(TRANSITION_DASH_BLACK, TRANSITION_DASH_WHITE); 

    private static val KColor SCCHARTSGRAY = RENDERING_FACTORY.createKColor()=>[it.red=240;it.green=240;it.blue=240];
    private static val KColor SCCHARTSBLUE1 = RENDERING_FACTORY.createKColor()=>[it.red=248;it.green=249;it.blue=253];
    private static val KColor SCCHARTSBLUE2 = RENDERING_FACTORY.createKColor()=>[it.red=205;it.green=220;it.blue=243];
    
    


    def dispatch KNode translate(SCGraph r) {
        return r.createNode().putToLookUpWith(r) => [ node |
            // node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.graphviz.dot");
            // node.setLayoutOption(LayoutOptions::DIRECTION, Direction::RIGHT);
            // node.setLayoutOption(LayoutOptions::SPACING, 40f);
            for (s : r.nodes) {
                node.children += s.translate;
            }
            
        ]
    }
    
    def dispatch KNode translate(Assignment s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val cornerRadius = 2;
            val lineWidth = 1.0f;

            val figure = node.addRoundedRectangle(cornerRadius, cornerRadius, lineWidth)
                .background = "white".color;

            (figure) => [
                node.setMinimalNodeSize(75, 25);
//                it.setBackgroundGradient(SCCHARTSBLUE1.copy, SCCHARTSBLUE2.copy, 90);
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
                it.setGridPlacement(1);
                it.addText("assignment").putToLookUpWith(s);

            ];
        ]
    }
    
    def dispatch KNode translate(Conditional s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addPolygon().createDiamondShape();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("cond").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ];
    }
    
    def dispatch KNode translate(Surface s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addPolygon().createSurfaceShape();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("surface").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
            
            s.depth?.translateTickEdge
        ];
    }

    def dispatch KNode translate(Depth s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);
            if (ALIGN_TICK_EDGES.optionBooleanValue) {
                node.addLayoutParam(Properties::LAYER_CONSTRAINT, LayerConstraint::FIRST)
            }

            val figure = node.addPolygon().createDepthShape();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("depth").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ]
    }
    
    def dispatch KNode translate(Entry s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addEllipse();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("entry").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ];
    }    
 
    def dispatch KNode translate(Exit s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addEllipse();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("exit").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ];
    }

    def dispatch KNode translate(Fork s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addPolygon().createTriangleShape();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("exit").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ];
    }

    def dispatch KNode translate(Join s) {
        return s.createNode().putToLookUpWith(s) => [ node |
            //node.setLayoutOption(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.box");
            // node.setLayoutOption(LayoutOptions::BORDER_SPACING, 2f);
            // node.setLayoutOption(LayoutOptions::SPACING, 0f);
            node.setLayoutOption(LayoutOptions::EXPAND_NODES, true);

            val figure = node.addPolygon().createTriangleShapeReversed();
//                .background = "white".color;

            figure => [ node.setMinimalNodeSize(75, 25); 
                node.KRendering.add(factory.createKText.of("exit").putToLookUpWith(s));
                if (SHOW_SHADOW.optionBooleanValue) {
                    it.shadow = "black".color;
                }
            ]
        ];
    }


    def KEdge translateTickEdge(Depth t) {
        return t.createEdge().putToLookUpWith(t) => [ edge |
            edge.source = t.surface?.node;
            edge.target = t.node;
            edge.setLayoutOption(LayoutOptions::EDGE_ROUTING, EdgeRouting::SPLINES);
      
            edge.addSpline(2) => [
                    it.lineStyle = LineStyle::DOT;
//                    it.lineStyle.dashPattern.clear;
//                    it.lineStyle.dashPattern += TRANSITION_DASH_PATTERN;
            ]
                        
//            if (SHOW_LABELS.optionBooleanValue) {
//                scopeProvider.parent = t.sourceState;
//                var String label =
//                    try {
//                        serializer.serialize(t.copy => [
//                            TMP_RES.contents += it;
//                        ]);
//                    } finally {
//                        TMP_RES.contents.clear;
//                    } 
//                if (t.sourceState.outgoingTransitions.size > 1) {
//                    label =  t.sourceState.outgoingTransitions.indexOf(t) + 1 + ": " + label;
//                }
//                if (!label.nullOrEmpty) {
//                    t.createLabel(edge).putToLookUpWith(t).configureCenteralLabel(
//                        label, 5, KlighdConstants::DEFAULT_FONT_NAME
//                    );
//                }
//            }
        ]
    }
   
}
