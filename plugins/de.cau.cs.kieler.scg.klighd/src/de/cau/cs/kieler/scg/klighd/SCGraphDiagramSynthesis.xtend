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

import com.google.inject.Guice
import com.google.inject.Injector
import de.cau.cs.kieler.core.annotations.StringAnnotation
import de.cau.cs.kieler.core.kexpressions.KExpressionsStandaloneSetup
import de.cau.cs.kieler.core.kgraph.KEdge
import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.kgraph.KPort
import de.cau.cs.kieler.core.krendering.KColor
import de.cau.cs.kieler.core.krendering.KPolygon
import de.cau.cs.kieler.core.krendering.KRenderingFactory
import de.cau.cs.kieler.core.krendering.KRoundedBendsPolyline
import de.cau.cs.kieler.core.krendering.KRoundedRectangle
import de.cau.cs.kieler.core.krendering.LineStyle
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
import de.cau.cs.kieler.kiml.options.EdgeRouting
import de.cau.cs.kieler.kiml.options.LayoutOptions
import de.cau.cs.kieler.kiml.options.PortConstraints
import de.cau.cs.kieler.kiml.options.PortSide
import de.cau.cs.kieler.klay.layered.p4nodes.NodePlacementStrategy
import de.cau.cs.kieler.klay.layered.properties.LayerConstraint
import de.cau.cs.kieler.klay.layered.properties.Properties
import de.cau.cs.kieler.klighd.KlighdConstants
import de.cau.cs.kieler.klighd.SynthesisOption
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Conditional
import de.cau.cs.kieler.scg.ControlFlow
import de.cau.cs.kieler.scg.Depth
import de.cau.cs.kieler.scg.Entry
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Fork
import de.cau.cs.kieler.scg.Join
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.Surface
import de.cau.cs.kieler.scg.extensions.SCGCopyExtensions
import de.cau.cs.kieler.scg.extensions.SCGExtensions
import de.cau.cs.kieler.scg.klighd.analyzer.AnalysesVisualization
import de.cau.cs.kieler.scgbb.BasicBlock
import de.cau.cs.kieler.scgbb.SCGraphBB
import de.cau.cs.kieler.scgbb.SchedulingBlock
import de.cau.cs.kieler.scgdep.AbsoluteWrite_Read
import de.cau.cs.kieler.scgdep.AbsoluteWrite_RelativeWrite
import de.cau.cs.kieler.scgdep.Dependency
import de.cau.cs.kieler.scgdep.RelativeWrite_Read
import de.cau.cs.kieler.scgdep.SCGraphDep
import de.cau.cs.kieler.scgdep.Write_Write
import de.cau.cs.kieler.scgsched.SCGraphSched
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import javax.inject.Inject
import org.eclipse.xtext.serializer.ISerializer

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.core.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout
import de.cau.cs.kieler.core.kexpressions.TextExpression
import de.cau.cs.kieler.core.kexpressions.Expression

/** 
 * SCCGraph KlighD synthesis class. It contains all method mandatory to handle the visualization of
 * all predefined types of SC graphs.
 * 
 * @author ssm
 * @kieler.design 2013-10-23 proposed 
 * @kieler.rating 2013-10-23 proposed yellow
 */
class SCGraphDiagramSynthesis extends AbstractDiagramSynthesis<SCGraph> {

    // -------------------------------------------------------------------------
    // -- Guice
    // -------------------------------------------------------------------------
    // Retrieve an injector and instances for the serialization.
    private static var Injector guiceInjector;

    //    @SuppressWarnings("unused")
    private static val KExpressionsStandaloneSetup standAloneSetup = new KExpressionsStandaloneSetup() => [
        guiceInjector = Guice.createInjector(new SCGRuntimeModule);
        it.register(guiceInjector);
    ]
    private static val SCGKExpressionsScopeProvider scopeProvider = guiceInjector.getInstance(
        typeof(SCGKExpressionsScopeProvider));
    private static val ISerializer serializer = guiceInjector.getInstance(typeof(ISerializer));

    // -------------------------------------------------------------------------
    // -- Extensions 
    // -------------------------------------------------------------------------
    //    extension KRenderingFactory = KRenderingFactory.eINSTANCE
    /** Inject node extensions. */
    @Inject
    extension KNodeExtensions

    /** Inject edge extensions. */
    @Inject
    extension KEdgeExtensions

    /** Inject label extensions. */
    @Inject
    extension KLabelExtensions

    /** Inject rendering extensions. */
    @Inject
    extension KRenderingExtensions

    /** Inject port extensions. */
    @Inject
    extension KPortExtensions

    /** Inject container rendering extensions. */
    @Inject
    extension KContainerRenderingExtensions

    /** Inject polyline extensions. */
    @Inject
    extension KPolylineExtensions

    /** Inject color extensions. */
    @Inject
    extension KColorExtensions

    /** Inject KExpression extension. */
    @Inject
    extension KExpressionsExtension

    /** Inject SCGraph shapes extensions. */
    @Inject
    extension AnnotationsExtensions

    /** Inject SCG shapes extensions. */
    @Inject
    extension SCGraphShapes

    /** Inject SCG extensions. */
    @Inject
    extension SCGExtensions

    /** Inject SCG copy extensions. */
    @Inject
    extension SCGCopyExtensions

    /** Inject analysis extensions. */
    @Inject
    extension AnalysesVisualization

    // -------------------------------------------------------------------------
    // -- KlighD Options
    // -------------------------------------------------------------------------
    /** Show caption */
    private static val SynthesisOption SHOW_CAPTION = SynthesisOption::createCheckOption("Captions", true);

    /** Show dependencies */
    private static val SynthesisOption SHOW_DEPENDENCIES = SynthesisOption::createCheckOption("Dependencies", true);

    /** Layout dependencies */
    private static val SynthesisOption LAYOUT_DEPENDENCIES = SynthesisOption::createCheckOption("Dependencies", false);

    /** Show non concurrent dependencies */
    private static val SynthesisOption SHOW_NONCONCURRENT = SynthesisOption::createCheckOption(
        "Non-concurrent dependencies", false);

    /** Show confluent dependencies */
    private static val SynthesisOption SHOW_CONFLUENT = SynthesisOption::createCheckOption("Confluent dependencies",
        false);

    /** Show basic blocks */
    private static val SynthesisOption SHOW_BASICBLOCKS = SynthesisOption::createCheckOption("Basic Blocks", false);

    /** Show scheduling blocks */
    private static val SynthesisOption SHOW_SCHEDULINGBLOCKS = SynthesisOption::createCheckOption("Scheduling Blocks",
        true);

    /** Show scheduling path */
    private static val SynthesisOption SHOW_SCHEDULINGPATH = SynthesisOption::createCheckOption("Scheduling path", true);

    /** Show potential problems */
    private static val SynthesisOption SHOW_POTENTIALPROBLEMS = SynthesisOption::createCheckOption("Potential problems",
        true);

    /** Show shadow */
    private static val SynthesisOption SHOW_SHADOW = SynthesisOption::createCheckOption("Shadow", true);

    /** Align tick start */
    private static val SynthesisOption ALIGN_TICK_START = SynthesisOption::createCheckOption("Tick start", true);

    /** Align entry/exit */
    private static val SynthesisOption ALIGN_ENTRYEXIT_NODES = SynthesisOption::createCheckOption("Entry & Exit nodes",
        true);

    /** Show hierarchy */
    private static val SynthesisOption SHOW_HIERARCHY = SynthesisOption::createCheckOption("Hierarchy", true);

    /** Hierarchy transparency */
    private static val SynthesisOption HIERARCHY_TRANSPARENCY = SynthesisOption::createRangeOption("Hierarchy", 0f, 255f,
        128f);

    /** Control flow thickness */
    private static val SynthesisOption CONTROLFLOW_THICKNESS = SynthesisOption::createRangeOption(
        "Controlflow thickness", 0.5f, 5f, 0.5f, 2f);

    /** Graph orientation */
    private static val SynthesisOption ORIENTATION = SynthesisOption::createChoiceOption("Orientation",
        <String>newLinkedList("Top-Down", "Left-Right"), "Top-Down");

    // Text constants for the dependency types filter
    private static val DEPENDENCYFILTERSTRING_WRITE_WRITE = "write - write"
    private static val DEPENDENCYFILTERSTRING_ABSWRITE_RELWRITE = "abs. write - rel. write"
    private static val DEPENDENCYFILTERSTRING_WRITE_READ = "write - read"
    private static val DEPENDENCYFILTERSTRING_RELWRITE_READ = "rel. write - read"

    /** Show write-write dependencies */
    private static val SynthesisOption SHOW_DEPENDENCY_WRITE_WRITE = SynthesisOption::createCheckOption(
        DEPENDENCYFILTERSTRING_WRITE_WRITE, true);

    /** Show absolute write-relative write dependencies */
    private static val SynthesisOption SHOW_DEPENDENCY_ABSWRITE_RELWRITE = SynthesisOption::
        createCheckOption(DEPENDENCYFILTERSTRING_ABSWRITE_RELWRITE, true);

    /** Show write-read dependencies */
    private static val SynthesisOption SHOW_DEPENDENCY_WRITE_READ = SynthesisOption::createCheckOption(
        DEPENDENCYFILTERSTRING_WRITE_READ, true);

    /** Show relative write-read dependencies */
    private static val SynthesisOption SHOW_DEPENDENCY_RELWRITE_READ = SynthesisOption::
        createCheckOption(DEPENDENCYFILTERSTRING_RELWRITE_READ, true);

    /**  
     * Returns a list of KlighD visualization options. Called by KlighD.
     * 
     * @return Returns a list of KlighD visualization options.
     */
    override public getDisplayedSynthesisOptions() {
        return newLinkedList(
            SynthesisOption::createSeparator("Visibility"),
            SHOW_CAPTION,
            SHOW_HIERARCHY,
            SHOW_DEPENDENCIES,
            SHOW_NONCONCURRENT,
            SHOW_CONFLUENT,
            SHOW_BASICBLOCKS,
            SHOW_SCHEDULINGBLOCKS,
            SHOW_SCHEDULINGPATH,
            SHOW_POTENTIALPROBLEMS,
            SHOW_SHADOW,
            HIERARCHY_TRANSPARENCY,
            CONTROLFLOW_THICKNESS,
            SynthesisOption::createSeparator("Dependency Filter"),
            SHOW_DEPENDENCY_WRITE_WRITE,
            SHOW_DEPENDENCY_ABSWRITE_RELWRITE,
            SHOW_DEPENDENCY_WRITE_READ,
            SHOW_DEPENDENCY_RELWRITE_READ,
            SynthesisOption::createSeparator("Alignment"),
            ALIGN_TICK_START,
            ALIGN_ENTRYEXIT_NODES,
            SynthesisOption::createSeparator("Layout"),
            LAYOUT_DEPENDENCIES,
            ORIENTATION
        );
    }

    /**
     * Returns a list of layout options. Called by KlighD.<br>
     * SCG Synthesis uses the predefined SPACING and NODE_PLACER options. 
     * 
     * @return Returns a list of layout options.
     */
    override public getDisplayedLayoutOptions() {
        return newLinkedList(
            new Pair<IProperty<?>, List<?>>(LayoutOptions::SPACING, newArrayList(0, 255)),
            new Pair<IProperty<?>, List<?>>(Properties::NODE_PLACER, NodePlacementStrategy::values)
        );
    }

    // -------------------------------------------------------------------------
    // -- Constants
    // -------------------------------------------------------------------------
    /** Spacing for guard indentation. */
    private static val String KLIGHDSPACERCHAR = " " 
    private static val String KLIGHDSPACER = KLIGHDSPACERCHAR + KLIGHDSPACERCHAR + KLIGHDSPACERCHAR + KLIGHDSPACERCHAR

    /** Color codes */
    private static val KColor SCCHARTSBLUE = RENDERING_FACTORY.createKColor() =>
        [it.red = 205; it.green = 220; it.blue = 243];
    private static val KColor REGIONLABEL = RENDERING_FACTORY.createKColor() =>
        [it.red = 64; it.green = 80; it.blue = 128];
    private static val KColor BASICBLOCKBORDER = RENDERING_FACTORY.createKColor() =>
        [it.red = 248; it.green = 0; it.blue = 253];
    private static val KColor SCHEDULINGBLOCKBORDER = RENDERING_FACTORY.createKColor() =>
        [it.red = 128; it.green = 0; it.blue = 243];
    private static val KColor DEPENDENCY_ABSWRITEREAD = RENDERING_FACTORY.createKColor() =>
        [it.red = 0; it.green = 192; it.blue = 0;]
    private static val KColor DEPENDENCY_RELWRITEREAD = RENDERING_FACTORY.createKColor() =>
        [it.red = 0; it.green = 192; it.blue = 192;]
    private static val KColor DEPENDENCY_ABSWRITERELWRITE = RENDERING_FACTORY.createKColor() =>
        [it.red = 0; it.green = 0; it.blue = 255;]
    private static val KColor DEPENDENCY_ABSWRITEABSWRITE = RENDERING_FACTORY.createKColor() =>
        [it.red = 255; it.green = 0; it.blue = 0;]
    private static val KColor SCHEDULING_NOTSCHEDULABLE = RENDERING_FACTORY.createKColor() =>
        [it.red = 255; it.green = 0; it.blue = 0;]
    private static val KColor STANDARD_CONTROLFLOWEDGE = RENDERING_FACTORY.createKColor() =>
        [it.red = 0; it.green = 0; it.blue = 0;]
    private static val KColor SCHEDULING_CONTROLFLOWEDGE = RENDERING_FACTORY.createKColor() =>
        [it.red = 144; it.green = 144; it.blue = 144;]
    private static val KColor SCHEDULING_SCHEDULINGEDGE = RENDERING_FACTORY.createKColor() =>
        [it.red = 128; it.green = 0; it.blue = 253;]
    private static val int SCHEDULING_SCHEDULINGEDGE_ALPHA = 96

    /** Constants for semantic object mapping */
    private static val String SCGPORTID_INCOMING = "incoming"
    private static val String SCGPORTID_OUTGOING = "outgoing"
    private static val String SCGPORTID_OUTGOING_THEN = "outgoingThen"
    private static val String SCGPORTID_OUTGOING_ELSE = "outgoingElse"
    private static val String SCGPORTID_HIERARCHYPORTS = "hierarchyPorts"
    private static val String SCGPORTID_INCOMINGDEPENDENCY = "incomingDependency"
    private static val String SCGPORTID_OUTGOINGDEPENDENCY = "outgoingDependency"

    /** Constants for annotations */
    private static val String ANNOTATION_BRANCH = "branch"
    private static val String ANNOTATION_REGIONNAME = "regionName"

    /** 
	 * Constants for hierarchical node groups
	 * Since hierarchy, basic blocks and scheduling blocks use the same mechanism to group nodes and draw hierarchical edges, 
	 * use this constants to define the desired kind of group. 
	 */
    private static val int NODEGROUPING_HIERARCHY = 0
    private static val int NODEGROUPING_BASICBLOCK = 1
    private static val int NODEGROUPING_SCHEDULINGBLOCK = 2

    /** Constants for the graph orientation */
    private static val int ORIENTATION_PORTRAIT = 0
    private static val int ORIENTATION_LANDSCAPE = 1

    /** Thickness of an highlighted scheduling edge */
    private static val int CONTROLFLOW_SCHEDULINGEDGE_WIDTH = 4

    /** Figure constants */
    private static val int MINIMALWIDTH = 75
    private static val int MINIMALHEIGHT = 25
    private static val float CORNERRADIUS = 2.0f
    private static val float LINEWIDTH = 1.0f

    // -------------------------------------------------------------------------
    // -- Globals 
    // -------------------------------------------------------------------------
    /** The root node */
    private KNode rootNode;

    /** The selected orientation */
    private int orientation;

    // -------------------------------------------------------------------------
    // -- Main Entry Point 
    // -------------------------------------------------------------------------
    /**
	 * This method is called by KlighD. Trigger your visualization here.
	 * 
	 * @param model
	 * 			the model in question
	 * @return Returns the root KNode.
	 */
    override transform(SCGraph model) {

        // Connect the model to the scope provider for the serialization.
        scopeProvider.parent = model;

        // Invoke the synthesis.
        return model.synthesize();
    }

    // -------------------------------------------------------------------------
    // -- Synthesis 
    // -------------------------------------------------------------------------
    /**
     * Is triggered when a SCGrpah must be synthesized.
     * 
     * @param scg
     * 			the SCG in question
     * @return Returns the top level KNode.
     */
    private def dispatch KNode synthesize(SCGraph scg) {
        return scg.createNode().putToLookUpWith(scg) => [ node |
            // Set root node and layout options.
            rootNode = node
            if(ORIENTATION.objectValue == "Left-Right") orientation = ORIENTATION_LANDSCAPE else orientation = ORIENTATION_PORTRAIT
            if (topdown)
                node.setLayoutOption(LayoutOptions::DIRECTION, Direction::DOWN)
            else
                node.setLayoutOption(LayoutOptions::DIRECTION, Direction::RIGHT)
            node.setLayoutOption(LayoutOptions::SPACING, 25f);
            node.addLayoutParam(LayoutOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL);
            node.addLayoutParam(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.klay.layered");
            node.addLayoutParam(Properties::THOROUGHNESS, 100)
            node.addLayoutParam(LayoutOptions::SEPARATE_CC, false);
            // Synthesize all children             
            scg.nodes.forEach[node.children += synthesize]
            // For each node transform the control flow edges.
            // This must be done after all nodes have been created.
            scg.nodes.forEach [
                if(it instanceof Surface) (it as Surface).depth?.synthesizeTickEdge
                if(it instanceof Assignment) (it as Assignment).next?.synthesizeControlFlow(SCGPORTID_OUTGOING)
                if(it instanceof Entry) (it as Entry).next?.synthesizeControlFlow(SCGPORTID_OUTGOING)
                if(it instanceof Exit) (it as Exit).next?.synthesizeControlFlow(SCGPORTID_OUTGOING)
                if(it instanceof Join) (it as Join).next?.synthesizeControlFlow(SCGPORTID_OUTGOING)
                if(it instanceof Depth) (it as Depth).next?.synthesizeControlFlow(SCGPORTID_OUTGOING)
                if(it instanceof Fork) (it as Fork).getNext().forEach[synthesizeControlFlow("")]
                if (it instanceof Conditional) {
                    (it as Conditional).then?.synthesizeControlFlow(SCGPORTID_OUTGOING_THEN)
                    (it as Conditional).^else.synthesizeControlFlow(SCGPORTID_OUTGOING_ELSE)
                }
            ]
            // If the dependency edges shall be layouted as well, they must be drawn before any 
            // hierarchy management. The hierarchy methods break edges in half and connect them via a port.
            if (scg instanceof SCGraphDep && SHOW_DEPENDENCIES.booleanValue && LAYOUT_DEPENDENCIES.booleanValue) {
                scg.eAllContents.filter(Dependency).forEach[synthesizeDependency]
            }
            // Apply any hierarchy if the corresponding option is set. Since layout of edges between nodes
            // in different hierarchies is not supported, the synthesis splits these edges at the hierarchy
            // border and connects them via a port. Thus, a kind of pseudo hierarchical edge layout is archived. 
            if (SHOW_HIERARCHY.booleanValue) {
                scg.nodes.filter(typeof(Fork)).forEach[
                    allNext.map[target].filter(typeof(Entry)).forEach [
                        if (it != null) {
                            val regionLabel = it.getStringAnnotationValue(ANNOTATION_REGIONNAME)
                            getThreadNodes.createHierarchy(NODEGROUPING_HIERARCHY) => [
                                if (!regionLabel.nullOrEmpty)
                                    addInsideTopLeftNodeLabel(regionLabel, 10, KlighdConstants::DEFAULT_FONT_NAME).foreground = REGIONLABEL
                            ]
                        }
                    ]]
            }
            // Draw basic blocks if present.
            if(scg instanceof SCGraphBB) (scg as SCGraphBB).synthesizeBasicBlocks
            // If dependency edge are drawn plain (without layout), draw them after the hierarchy management.
            if (scg instanceof SCGraphDep && SHOW_DEPENDENCIES.booleanValue && !LAYOUT_DEPENDENCIES.booleanValue)
                scg.eAllContents.filter(Dependency).forEach[synthesizeDependency]
            // Draw analysis visualization if present.
            if(scg instanceof SCGraphSched) (scg as SCGraphSched).synthesizeAnalyses
        ]
    }



    // added by cmot (9.3.14)
    private def String getTextExpressionString(Expression expression) {
        if (expression instanceof TextExpression) {
            val text = (expression as TextExpression).getText
            return removeEnclosingQuotes(text)
        }
        return null
    }

    /**
	 * This dispatch method is triggered when an assignment must be synthesized.
	 * 
	 * @param assignment
	 * 			the assignment in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Assignment assignment) {
        return assignment.createNode().putToLookUpWith(assignment) => [ node |
            // Straightforward rectangle drawing
            val figure = node.addRoundedRectangle(CORNERRADIUS, CORNERRADIUS, LINEWIDTH).background = "white".color;
            (figure) => [
                node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                // Serialize the assignment
                // Additionally, remove unnecessary parenthesis and add spacing in line breaks.
                if (assignment.valuedObject != null && assignment.assignment != null) {
                    var assignmentText = serializer.serialize(assignment.assignment.copy.fix).removeParenthesis
                    var valuedObjectName = assignment.valuedObject.name
                    if (!assignment.indices.nullOrEmpty) {
                        valuedObjectName = valuedObjectName + serializer.serializeIndices(assignment.indices)
                    }
                    // added by cmot (9.3.14)
                    if (assignment.assignment instanceof TextExpression) {
                        assignmentText = assignment.assignment.getTextExpressionString
                    }
                    var assignmentStr = valuedObjectName + " = " + assignmentText
                        
                    if (assignmentStr.contains("&") && assignmentStr.indexOf("&") != assignmentStr.lastIndexOf("&")) {
                        assignmentStr = assignmentStr.replaceAll("=", "=\n" + KLIGHDSPACER)
                        assignmentStr = assignmentStr.replaceAll("&", "&\n" + KLIGHDSPACER)
                    }
                    it.addText(assignmentStr).putToLookUpWith(assignment).setSurroundingSpace(4, 0, 2, 0)
                } else if (assignment.assignment instanceof TextExpression) {
                    // added by cmot (9.3.14)
                    it.addText(assignment.assignment.getTextExpressionString).putToLookUpWith(assignment).setSurroundingSpace(4, 0, 2, 0)
                }
            ]
            // Add ports for control-flow and dependency routing.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS);
            if (topdown()) {
                node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOING, 37, 24, 0, PortSide::SOUTH)
                node.addPort(SCGPORTID_INCOMINGDEPENDENCY, 47, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOINGDEPENDENCY, 47, 24, 0, PortSide::SOUTH)
            } else {
                node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOING, 75, 12.5f, 1, PortSide::EAST)
                node.addPort(SCGPORTID_INCOMINGDEPENDENCY, 0, 19, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOINGDEPENDENCY, 75, 19, 1, PortSide::EAST)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when a conditional must be synthesized.
	 * 
	 * @param conditional
	 * 			the conditional in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Conditional conditional) {
        return conditional.createNode().putToLookUpWith(conditional) => [ node |
            // Draw a diamond figure for conditionals.
            val figure = node.addPolygon().createDiamondShape()
            figure => [
                node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                // Serialize the condition in the conditional
                if (conditional.condition != null)
                    node.KContainerRendering.addText(
                        serializer.serialize(conditional.condition.copy.fix).removeParenthesis).setAreaPlacementData.
                        from(LEFT, 0, 0, TOP, 0, 0).to(RIGHT, 1, 0, BOTTOM, 1, 0).putToLookUpWith(conditional)
                if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
            ]
            // Add ports for control-flow and dependency routing.
            var switchBranch = false
            val branchAnnotation = conditional.getAnnotation(ANNOTATION_BRANCH)
            if (branchAnnotation instanceof StringAnnotation) {
                val annotationValue = (branchAnnotation as StringAnnotation).getValue
                if (annotationValue == "switch") {
                    switchBranch = true
                }
            }
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS)
            var KPort port
            if (topdown) {
                node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOING_ELSE, 37.5f, 24, 0, PortSide::SOUTH)
                if (switchBranch)
                    port = node.addPort(SCGPORTID_OUTGOING_THEN, 7, 12.5f, 0, PortSide::WEST)
                else
                    port = node.addPort(SCGPORTID_OUTGOING_THEN, 68, 12.5f, 0, PortSide::EAST)
                node.addPort(SCGPORTID_INCOMINGDEPENDENCY, 47, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOINGDEPENDENCY, 47, 21, 1, PortSide::SOUTH)
                port.addLayoutParam(LayoutOptions::OFFSET, -1.5f)
            } else {
                node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOING_ELSE, 75, 12.5f, 0, PortSide::EAST)
                if (switchBranch)
                    port = node.addPort(SCGPORTID_OUTGOING_THEN, 37.5f, 0, 0, PortSide::NORTH)
                else
                    port = node.addPort(SCGPORTID_OUTGOING_THEN, 37.5f, 20, 0, PortSide::SOUTH)
                node.addPort(SCGPORTID_INCOMINGDEPENDENCY, 0, 19, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOINGDEPENDENCY, 75, 19, 1, PortSide::EAST)
                port.addLayoutParam(LayoutOptions::OFFSET, 0f)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when a surface must be synthesized.
	 * 
	 * @param surface
	 * 			the surface in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Surface surface) {
        return surface.createNode().putToLookUpWith(surface) => [ node |
            // Draw a surface node...
            var KPolygon figure
            if (topdown) {
                figure = node.addPolygon().createSurfaceShape()
                figure => [
                    node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("surface").putToLookUpWith(surface)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            } else {
                figure = node.addPolygon().createSurfaceLandscapeShape()
                figure => [
                    node.setMinimalNodeSize(75, 25)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("surface").setAreaPlacementData.from(LEFT, 10, 0, TOP, 0, 0).
                            to(RIGHT, 0, 0, BOTTOM, 3, 0).putToLookUpWith(surface)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            }
            // Add ports for control-flow/tick edge routing.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS);
            if (topdown) {
                val port = node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOING, 37, 25, 0, PortSide::SOUTH)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            } else {
                val port = node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOING, 75, 12.5f, 0, PortSide::EAST)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when a depth must be synthesized.
	 * 
	 * @param depth
	 * 			the depth in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Depth depth) {
        return depth.createNode().putToLookUpWith(depth) => [ node |
            // If the corresponding option is set to true, depth nodes are placed in the first layer.
            if(ALIGN_TICK_START.booleanValue) node.addLayoutParam(Properties::LAYER_CONSTRAINT, LayerConstraint::FIRST)
            // Draw a depth figure;
            var KPolygon figure
            if (topdown) {
                figure = node.addPolygon().createDepthShape()
                figure => [
                    node.setMinimalNodeSize(75, 25)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("depth").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(
                            RIGHT, 0, 0, BOTTOM, 4, 0).putToLookUpWith(depth)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            } else {
                figure = node.addPolygon().createDepthLandscapeShape()
                figure => [
                    node.setMinimalNodeSize(75, 25)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("depth").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(
                            RIGHT, 10, 0, BOTTOM, 2, 0).putToLookUpWith(depth)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            }
            // Add ports for control-flow/tick edge routing.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS);
            if (topdown) {
                node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                val port = node.addPort(SCGPORTID_OUTGOING, 37.5f, 25, 0, PortSide::SOUTH)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            } else {
                node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                val port = node.addPort(SCGPORTID_OUTGOING, 75, 12.5f, 0, PortSide::EAST)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when an entry must be synthesized.
	 * 
	 * @param entry
	 * 			the entry node in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Entry entry) {
        return entry.createNode().putToLookUpWith(entry) => [ node |
            // If the corresponding option is set to true, exit nodes are placed in the first layer;
            if (ALIGN_ENTRYEXIT_NODES.booleanValue)
                node.addLayoutParam(Properties::LAYER_CONSTRAINT, LayerConstraint::FIRST)
            // Draw an ellipse figure for exit nodes...
            val figure = node.addEllipse()
            figure => [
                node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                if (SHOW_CAPTION.booleanValue)
                    node.KContainerRendering.addText("entry").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(RIGHT,
                        0, 0, BOTTOM, 1, 0).putToLookUpWith(entry)
                if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
            ]
            // Add ports for control-flow routing.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS);
            if (topdown) {
                node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOING, 37, 25, 0, PortSide::SOUTH)
            } else {
                node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOING, 75, 12.5f, 0, PortSide::EAST)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when an exit must be synthesized.
	 * 
	 * @param exit
	 * 			the exit node in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Exit exit) {
        return exit.createNode().putToLookUpWith(exit) => [ node |
            // If the corresponding option is set to true, exit nodes are placed in the last layer.
            if (ALIGN_ENTRYEXIT_NODES.booleanValue)
                node.addLayoutParam(Properties::LAYER_CONSTRAINT, LayerConstraint::LAST)
            // Draw an ellipse for an exit node...
            val figure = node.addEllipse()
            figure => [
                node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                if (SHOW_CAPTION.booleanValue)
                    node.KContainerRendering.addText("exit").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(RIGHT,
                        0, 0, BOTTOM, 1, 0).putToLookUpWith(exit)
                if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
            ]
            // Add ports for control-flow routing.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_POS)
            if (topdown) {
                node.addPort(SCGPORTID_INCOMING, 37, 0, 1, PortSide::NORTH)
                node.addPort(SCGPORTID_OUTGOING, 37, 25, 0, PortSide::SOUTH)
            } else {
                node.addPort(SCGPORTID_INCOMING, 0, 12.5f, 1, PortSide::WEST)
                node.addPort(SCGPORTID_OUTGOING, 75, 12.5f, 0, PortSide::EAST)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when a fork must be synthesized.
	 * 
	 * @param fork
	 * 			the fork node in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Fork fork) {
        return fork.createNode().putToLookUpWith(fork) => [ node |
            // Draw a fork triangle...
            var KPolygon figure
            if (topdown) {
                figure = node.addPolygon().createTriangleShape()
                figure => [
                    node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("fork").setAreaPlacementData.from(LEFT, 0, 0, TOP, 4, 0).to(
                            RIGHT, 0, 0, BOTTOM, 0, 0).putToLookUpWith(fork)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            } else {
                figure = node.addPolygon().createTriangleLandscapeShape();
                figure => [
                    node.setMinimalNodeSize(MINIMALHEIGHT, MINIMALWIDTH);
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("fork").setAreaPlacementData.from(LEFT, 2, 0, TOP, 0, 0).to(
                            RIGHT, 0, 0, BOTTOM, 2, 0).putToLookUpWith(fork)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            }
            // Only add one port for incoming control flow edges.
            // Outgoing ports are added by the control flows.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_SIDE);
            if (topdown) {
                val port = node.addPort(SCGPORTID_INCOMING, 36, 0, 1, PortSide::NORTH)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            } else {
                val port = node.addPort(SCGPORTID_INCOMING, 0, 37.5f, 1, PortSide::WEST)
                port.addLayoutParam(LayoutOptions::OFFSET, 0.5f)
            }
        ]
    }

    /**
	 * This dispatch method is triggered when a join must be synthesized.
	 * 
	 * @param join
	 * 			the join node in question.
	 * @return Returns the top level KNode. 
	 */
    private def dispatch KNode synthesize(Join join) {
        return join.createNode().putToLookUpWith(join) => [ node |
            // Draw a join triangle...
            var KPolygon figure
            if (topdown) {
                figure = node.addPolygon().createTriangleShapeReversed()
                figure => [
                    node.setMinimalNodeSize(MINIMALWIDTH, MINIMALHEIGHT)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("join").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(
                            RIGHT, 0, 0, BOTTOM, 10, 0).putToLookUpWith(join)
                    if(SHOW_SHADOW.booleanValue) it.shadow = "black".color
                ]
            } else {
                figure = node.addPolygon().createTriangleLandscapeShapeReversed()
                figure => [
                    node.setMinimalNodeSize(MINIMALHEIGHT, MINIMALWIDTH)
                    if (SHOW_CAPTION.booleanValue)
                        node.KContainerRendering.addText("join").setAreaPlacementData.from(LEFT, 0, 0, TOP, 0, 0).to(
                            RIGHT, 0, 0, BOTTOM, 4, 0).putToLookUpWith(join)
                    if (SHOW_SHADOW.booleanValue) {
                        it.shadow = "black".color;
                    }
                ]
            }
            // Only add one port for an outgoing control flow edge.
            // Incoming ports are added by the control flows.
            node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_SIDE);
            if (topdown) {
                val port = node.addPort(SCGPORTID_OUTGOING, 36, 25, 0, PortSide::SOUTH)
                port.addLayoutParam(LayoutOptions::OFFSET, -0.5f)
            } else {
                val port = node.addPort(SCGPORTID_OUTGOING, 0, 37.5f, 0, PortSide::EAST)
                port.addLayoutParam(LayoutOptions::OFFSET, -0.5f)
            }
        ]
    }

    /**
	 * Draw a dotted line from the corresponding surface node to the given depth node.
	 * 
	 * @param depth
	 * 			the depth (target= node of the tick edge
	 * @return Returns the KEdge.
	 */
    private def KEdge synthesizeTickEdge(Depth depth) {
        return depth.createNewEdge().putToLookUpWith(depth) => [ edge |
            edge.source = depth.surface?.node;
            edge.target = depth.node;
            edge.sourcePort = depth.surface?.node.getPort(SCGPORTID_OUTGOING)
            edge.targetPort = depth.node.getPort(SCGPORTID_INCOMING)
            edge.setLayoutOption(LayoutOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL);
            edge.addRoundedBendsPolyline(8, CONTROLFLOW_THICKNESS.intValue) => [
                it.lineStyle = LineStyle::DOT;
            ]
        ]
    }

    /**
	 * Draw a control flow edge from one node to another.
	 * Use the outgoing port description as port identifier.
	 * 
	 * @param controlFlow
	 * 			the control flow in question
	 * @param outgoingPortId
	 * 			the identifier for the outgoing port
	 * @return Returns the KEdge. 
	 */
    private def KEdge synthesizeControlFlow(ControlFlow controlFlow, String outgoingPortId) {
        if(controlFlow.target == null || controlFlow.eContainer == null) return null;

        return controlFlow.createNewEdge().putToLookUpWith(controlFlow) => [ edge |
            // Get and set source and target information.
            val sourceObj = controlFlow.eContainer
            val targetObj = controlFlow.target
            var sourceNode = sourceObj.node
            var targetNode = targetObj.node
            edge.source = sourceNode
            edge.target = targetNode
            edge.setLayoutOption(LayoutOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL)
            // If the source is a fork node, create a new port for this control flow and attach it.
            // Otherwise, use the outgoing port identified by the given parameter.
            if (sourceObj instanceof Fork) {
                edge.sourcePort = sourceObj.node.createPort("fork" + targetObj.hashCode()) => [
                    if (topdown())
                        it.addLayoutParam(LayoutOptions::PORT_SIDE, PortSide::SOUTH)
                    else
                        it.addLayoutParam(LayoutOptions::PORT_SIDE, PortSide::EAST)
                    it.setPortSize(3, 3)
                    it.addRectangle.invisible = true;
                    it.addLayoutParam(LayoutOptions::OFFSET, -3f)
                    sourceObj.node.ports += it
                ]
            } else {
                edge.sourcePort = sourceNode.getPort(outgoingPortId)
            }
            // If the target is a join node, create a new port for this control flow and attach it.
            // Otherwise, use the default incoming port.
            if (targetObj instanceof Join) {
                edge.targetPort = targetObj.node.createPort("join" + sourceObj.hashCode()) => [
                    if (topdown())
                        it.addLayoutParam(LayoutOptions::PORT_SIDE, PortSide::NORTH)
                    else
                        it.addLayoutParam(LayoutOptions::PORT_SIDE, PortSide::WEST)
                    it.setPortSize(3, 3)
                    it.addRectangle.invisible = true;
                    it.addLayoutParam(LayoutOptions::OFFSET, -1.5f)
                    targetObj.node.ports += it
                ]
            } else {
                edge.targetPort = targetNode.getPort(SCGPORTID_INCOMING)
            }
            // Finally, draw the solid edge and add a decorator.
            edge.addRoundedBendsPolyline(8, CONTROLFLOW_THICKNESS.intValue) => [
                it.lineStyle = LineStyle::SOLID
                it.addArrowDecorator
                if ((controlFlow.eContainer as Node).graph instanceof SCGraphSched && SHOW_SCHEDULINGPATH.booleanValue)
                    it.foreground = SCHEDULING_CONTROLFLOWEDGE.copy
                else
                    it.foreground = STANDARD_CONTROLFLOWEDGE.copy
                it.foreground.propagateToChildren = true
            ]
            // If the outgoing identifier indicates a 'then branch', add a 'then label'.
            if (outgoingPortId == SCGPORTID_OUTGOING_THEN) {
                edge.createLabel.configureTailEdgeLabel('true', 9, KlighdConstants::DEFAULT_FONT_NAME)
            }
        ]
    }

    /**
	 * Synthesize a (single) dependency.
	 * 
	 * @param dependency
	 * 			the dependency in question
	 * @return Returns the dependency. 
	 */
    private def Dependency synthesizeDependency(Dependency dependency) {

        // If non concurrent dependency are hidden and the given dependency is not concurrent, exit at once.
        if(!SHOW_NONCONCURRENT.booleanValue && !dependency.isConcurrent) return dependency;
        if(!SHOW_CONFLUENT.booleanValue && dependency.confluent) return dependency;

        if(!SHOW_DEPENDENCY_WRITE_WRITE.booleanValue && dependency instanceof Write_Write) return dependency;
        if(!SHOW_DEPENDENCY_ABSWRITE_RELWRITE.booleanValue && dependency instanceof AbsoluteWrite_RelativeWrite) return dependency;
        if(!SHOW_DEPENDENCY_WRITE_READ.booleanValue && dependency instanceof AbsoluteWrite_Read) return dependency;
        if(!SHOW_DEPENDENCY_RELWRITE_READ.booleanValue && dependency instanceof RelativeWrite_Read) return dependency;

        // Retrieve node information.
        val sourceNode = (dependency.eContainer as Node).node
        val targetNode = dependency.target.node

        // Draw the dashed dependency edge....
        dependency.createNewEdge().putToLookUpWith(dependency) => [ edge |
            edge.source = sourceNode
            edge.target = targetNode
            edge.addRoundedBendsPolyline(8, 2) => [
                // ... and use the predefined color for the different dependency types.    
                if(dependency instanceof AbsoluteWrite_Read) it.foreground = DEPENDENCY_ABSWRITEREAD.copy
                if(dependency instanceof RelativeWrite_Read) it.foreground = DEPENDENCY_RELWRITEREAD.copy
                if(dependency instanceof AbsoluteWrite_RelativeWrite) it.foreground = DEPENDENCY_ABSWRITERELWRITE.copy
                if(dependency instanceof Write_Write) it.foreground = DEPENDENCY_ABSWRITEABSWRITE.copy
                it.lineStyle = LineStyle::DASH
                it.addArrowDecorator
            ]
            // If dependency edges are layouted, use the dependency ports to attach the edges.
            if (LAYOUT_DEPENDENCIES.booleanValue) {
                edge.sourcePort = sourceNode.getPort(SCGPORTID_OUTGOINGDEPENDENCY)
                edge.targetPort = targetNode.getPort(SCGPORTID_INCOMINGDEPENDENCY)
            } else {

                // Otherwise, add NO_LAYOUT as layout option to trigger node-to-node hierarchy-crossover
                // drawing.
                edge.setLayoutOption(LayoutOptions::NO_LAYOUT, true)
            }
        ]

        dependency
    }

    /**
     * This method takes a list of nodes and creates a new container around them. 
     * All edges between nodes inside this container and nodes on the outside of the container a split up
     * and connected via ports on the container border. 
     * At the moment the new container is always a SCG thread hierarchy container, but this will probably
     * change when basic blocks are re-introduced.
     * 
     * @param nodes
     * 			the list of nodes that should be includes in the hierarchy
     * @param
     * 			the kindof grouping that should be applied.
     * @return Returns the container KNode.
     */
    private def KNode createHierarchy(List<Node> nodes, int nodeGrouping) {

        // Gather mandatory information.
        val firstNode = nodes.head
        val kParent = firstNode.node.eContainer as KNode
        val kContainer = firstNode.createNode("hierarchy" + nodeGrouping.toString)
        val kNodeList = new ArrayList<KNode>
        nodes.forEach[e|kNodeList.add(e.node)]

        // Determine all interleaving edges...        
        val iSecEdges = new ArrayList<KEdge>
        for (rc : kNodeList) {
            iSecEdges.addAll(rc.outgoingEdges.filter[!kNodeList.contains(it.target)])
            iSecEdges.addAll(rc.incomingEdges.filter[!kNodeList.contains(it.source)])
        }

        // Set options for the container.
//        kContainer.addLayoutParam(LayoutOptions::SPACING, 10.0f)
        if (topdown())
            kContainer.addLayoutParam(LayoutOptions::DIRECTION, Direction::DOWN)
        else
            kContainer.addLayoutParam(LayoutOptions::DIRECTION, Direction::RIGHT)
        kContainer.addLayoutParam(LayoutOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL)
        kContainer.addLayoutParam(LayoutOptions::ALGORITHM, "de.cau.cs.kieler.klay.layered")
        kContainer.addLayoutParam(LayoutOptions::SEPARATE_CC, false);
        kContainer.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FREE);

        if (nodeGrouping == NODEGROUPING_HIERARCHY) {
//            kContainer.addLayoutParam(LayoutOptions::SPACING, 25.0f)
            kContainer.addRoundedRectangle(5, 5, 0)
            kContainer.KRendering.foreground = SCCHARTSBLUE.copy;
            kContainer.KRendering.foreground.alpha = Math.round(HIERARCHY_TRANSPARENCY.objectValue as Float)
            kContainer.KRendering.background = SCCHARTSBLUE.copy;
            kContainer.KRendering.background.alpha = Math.round(HIERARCHY_TRANSPARENCY.objectValue as Float)
        }
        if (nodeGrouping == NODEGROUPING_BASICBLOCK) {
//            kContainer.addLayoutParam(LayoutOptions::SPACING, 5.0f)
            kContainer.addRoundedRectangle(1, 1, 1) => [
                it.lineStyle = LineStyle::SOLID
            ]
            kContainer.KRendering.foreground = BASICBLOCKBORDER.copy;
            kContainer.KRendering.foreground.alpha = Math.round(255f)
            kContainer.KRendering.background = SCCHARTSBLUE.copy;
            kContainer.KRendering.background.alpha = Math.round(0f)
        }
        if (nodeGrouping == NODEGROUPING_SCHEDULINGBLOCK) {
//            kContainer.addLayoutParam(LayoutOptions::SPACING, 5.0f)
            kContainer.addRoundedRectangle(1, 1, 1) => [
                it.lineStyle = LineStyle::SOLID
            ]
            kContainer.KRendering.foreground = SCHEDULINGBLOCKBORDER.copy;
            kContainer.KRendering.foreground.alpha = Math.round(255f)
            kContainer.KRendering.background = SCCHARTSBLUE.copy;
            kContainer.KRendering.background.alpha = Math.round(0f)
        }

        // Add the nodes to the container.
        // They will be removed from the original parent!
        for (tn : nodes) {
            kContainer.children += tn.node

// FIXME
//            if (nodeGrouping != NODEGROUPING_HIERARCHY)
//                if (tn.node.getData(typeof(KShapeLayout)).getProperty(Properties::LAYER_CONSTRAINT) ==
//                    LayerConstraint::FIRST) {
//                        kContainer.addLayoutParam(Properties::LAYER_CONSTRAINT, LayerConstraint::FIRST)
//                    }
        }

        // Add the container to the original parent.
        kParent.children += kContainer

        // Determine all interleaving edges...        
        //        val iSecEdges = new ArrayList<KEdge>
        //        for(rc : kParent.children) {
        //            iSecEdges.addAll(rc.outgoingEdges.filter[edge | kNodeList.contains(edge.target)])
        //            iSecEdges.addAll(rc.incomingEdges.filter[edge | kNodeList.contains(edge.source)])
        //        }
        // ... and split them up. This is done by re-routing the edge. The source of the edge is now the
        // container. A new edge is then created to attach the original source with the corresponding 
        // port on the border of the container.
        for (ne : iSecEdges) {
            val portName = SCGPORTID_HIERARCHYPORTS + ne.hashCode.toString + nodeGrouping.toString +
                ne.source.hashCode.toString + kContainer.hashCode.toString

            //            System.out.println("Creating helper port: " + portName)
            val hPort = kContainer.addHelperPort(portName)
            val origSource = ne.source
            val origSourcePort = ne.sourcePort
            ne.source = kContainer
            ne.sourcePort = hPort
            ne.semanticObject.createNewEdge() => [
                it.source = origSource
                it.sourcePort = origSourcePort
                it.target = kContainer
                it.targetPort = kContainer.getPort(portName)
                it.setLayoutOption(LayoutOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL)
                it.addRoundedBendsPolyline(8, CONTROLFLOW_THICKNESS.intValue) => [
                    it.lineStyle = ne.KRendering.lineStyleValue
                    it.foreground = ne.KRendering.foreground
                ]
                it.labels.addAll(ne.labels)
            ]

        }
        kContainer
    }

    /**
     * Synthesizes the basic blocks.
     * 
     * @param scg
     * 			the SCG containing the basic block information
     * @return Returns nothing.
     */
    private def void synthesizeBasicBlocks(SCGraphBB scg) {

        val schedulingBlockMapping = new HashMap<SchedulingBlock, KNode>

        val bbContainerList = new HashMap<BasicBlock, KNode>()
        for (bb : scg.basicBlocks) {
            if (SHOW_BASICBLOCKS.booleanValue) {
                val bbNodes = <Node>newLinkedList
                bb.schedulingBlocks.forEach[bbNodes.addAll(it.nodes)]
                val bbContainer = bbNodes.createHierarchy(NODEGROUPING_BASICBLOCK)
                bbContainerList.put(bb, bbContainer)
                val bbName = serializer.serialize(bb.guards.head.reference)
                bbContainer.addOutsideTopLeftNodeLabel(bbName, 9, KlighdConstants::DEFAULT_FONT_NAME).foreground = BASICBLOCKBORDER
            }
            if (SHOW_SCHEDULINGBLOCKS.booleanValue)
                for (schedulingBlock : bb.schedulingBlocks) {
                    val sbContainer = schedulingBlock.nodes.createHierarchy(NODEGROUPING_SCHEDULINGBLOCK)
                    schedulingBlockMapping.put(schedulingBlock, sbContainer)
                    val sbName = serializer.serialize(schedulingBlock.guard.reference)
                    sbContainer.addOutsideTopLeftNodeLabel(sbName, 9, KlighdConstants::DEFAULT_FONT_NAME).foreground = SCHEDULINGBLOCKBORDER
                }
        }

        if (scg instanceof SCGraphSched && SHOW_SCHEDULINGPATH.booleanValue) {
            var Node source = null
            var Node target = null
            for (node : (scg as SCGraphSched).getSchedules.head.scheduleNodes) {
                target = node
                if (target != null && source != null) {

                    val controlFlows = source.getControlFlows(target)
                    if (controlFlows.size > 0) {
                        controlFlows.forEach[colorControlFlow(SCHEDULING_SCHEDULINGEDGE)]
                        controlFlows.forEach[thickenControlFlow(CONTROLFLOW_SCHEDULINGEDGE_WIDTH)]
                        controlFlows.forEach[controlFlowAlpha(SCHEDULING_SCHEDULINGEDGE_ALPHA)]
                    } else {
                        val sourceF = source.node
                        val targetF = target.node
                        source.createEdge("schedule " + source.toString + target.toString) => [ edge |
                            edge.source = sourceF
                            edge.target = targetF
                            edge.addRoundedBendsPolyline(8, CONTROLFLOW_SCHEDULINGEDGE_WIDTH) => [
                                it.foreground = SCHEDULING_SCHEDULINGEDGE.copy
                                it.foreground.alpha = SCHEDULING_SCHEDULINGEDGE_ALPHA
                                it.addArrowDecorator
                            ]
                            edge.setLayoutOption(LayoutOptions::NO_LAYOUT, true)
                        ]
                    }
                }
                source = target
            }

            // not schedulable blocks!
            if (SHOW_SCHEDULINGBLOCKS.booleanValue && (scg as SCGraphSched).unschedulable) {
                val usBlocks = scg.allSchedulingBlocks()
                (scg as SCGraphSched).getSchedules.head.getSchedulingBlocks.forEach[usBlocks.remove(it)]
                usBlocks.forEach [
                    val node = schedulingBlockMapping.get(it)
                    node.getData(typeof(KRoundedRectangle)) => [
                        it.lineStyle = LineStyle::SOLID
                        it.foreground = SCHEDULING_NOTSCHEDULABLE.copy
                    ]
                    node.KRendering.background = SCHEDULING_NOTSCHEDULABLE.copy
                    node.KRendering.background.alpha = 128
                ]
            }
        }
    }

    /**
     * Calls the analyses visualization class. 
     * Each additional analysis stored by the scheduler can hook they visualization in the visualization class.
     * 
     * @param scg
     * 			the scg containing additional visualization data
     * @return Returns nothing.
     */
    private def void synthesizeAnalyses(SCGraphSched scg) {

        // val AnalysesVisualization analysesVisualization = Guice.createInjector().getInstance(typeof(AnalysesVisualization))
        if(!SHOW_POTENTIALPROBLEMS.booleanValue) return;
        scg.analyses.forEach[visualize(it, this)]
    }

    // -------------------------------------------------------------------------
    // -- Helper: Edges 
    // -------------------------------------------------------------------------
    /**
	 * Re-color an existing control flow. All edges, even split up ones, are colored.
	 * 
	 * @param controlFlow
	 * 			the control flow in question
	 * @param color
	 * 			the desired color
	 * @return Returns the control flow 
	 */
    def ControlFlow colorControlFlow(ControlFlow controlFlow, KColor color) {
        controlFlow => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).foreground = color.copy
                polyline.foreground.propagateToChildren = true
            ]]
    }

    /**
	 * Re-alpha an existing control flow. All alpha values, even on split up edges, are changed.
	 * 
	 * @param controlFlow
	 * 			the control flow in question
	 * @param alpha 
	 * 			the desired alpha value
	 * @return Returns the control flow 
	 */
    def ControlFlow controlFlowAlpha(ControlFlow controlFlow, int alpha) {
        controlFlow => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)) => [getForeground.alpha = alpha];
                polyline.foreground.propagateToChildren = true
            ]]
    }

    /**
	 * Re-thicken an existing control flow. All edges, even split up ones, are thicken.
	 * 
	 * @param controlFlow
	 * 			the control flow in question
	 * @param width
	 * 			the desired edge width
	 * @return Returns the control flow 
	 */
    def ControlFlow thickenControlFlow(ControlFlow controlFlow, int width) {
        controlFlow => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).lineWidth = width
                polyline.lineWidth.propagateToChildren = true
            ]]
    }

    /**
	 * Re-color an existing tick edge.
	 * 
	 * @param depth
	 * 			the depth (target) node of the tick edge in question
	 * @param color
	 * 			the desired color
	 * @return Returns the depth. 
	 */
    def Depth colorTickEdge(Depth depth, KColor color) {
        depth => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).foreground = color.copy
                polyline.foreground.propagateToChildren = true
            ]]
    }

    /**
	 * Re-thicken an existing tick edge.
	 * 
	 * @param depth
	 * 			the depth (target) node of the tick edge in question
	 * @param width
	 * 			the desired edge width
	 * @return Returns the depth. 
	 */
    def Depth thickenTickEdge(Depth depth, int width) {
        depth => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).lineWidth = width
                polyline.lineWidth.propagateToChildren = true
            ]]
    }

    /**
	 * Re-color an existing dependency. All edges, even split up ones, are colored.
	 * 
	 * @param dependency
	 * 			the dependency in question
	 * @param color
	 * 			the desired color
	 * @return Returns the dependency 
	 */
    def Dependency colorDependency(Dependency dependency, KColor color) {
        dependency => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).foreground = color.copy
                polyline.foreground.propagateToChildren = true
            ]]
    }

    /**
	 * Re-thicken an existing dependency. 
	 * 
	 * @param dependency
	 * 			the dependency in question
	 * @param width
	 * 			the desired edge width
	 * @return Returns the dependency 
	 */
    def Dependency thickenDependency(Dependency dependency, int width) {
        dependency => [
            allEdges.forEach [
                val polyline = it.getData(typeof(KRoundedBendsPolyline)).lineWidth = width
                polyline.lineWidth.propagateToChildren = true
            ]]
    }

    // -------------------------------------------------------------------------
    // -- Helper: Ports 
    // -------------------------------------------------------------------------
    def KPort addPort(KNode node, String mapping, float x, float y, int size, PortSide side) {
        node.createPort(mapping) => [
            it.addLayoutParam(LayoutOptions::PORT_SIDE, side);
            it.setPortPos(x, y)
            it.setPortSize(size, size)
            it.addRectangle.invisible = true;
            node.ports += it
        ]
    }

    def KPort addPortFixedSide(KNode node, String mapping, PortSide side) {
        node.addLayoutParam(LayoutOptions::PORT_CONSTRAINTS, PortConstraints::FIXED_SIDE);
        node.addPort(mapping, 37, 0, 3, side)
    }

    def KPort addHelperPort(KNode node, String mapping) {
        node.createPort(mapping) => [
            node.ports += it
        ]
    }

    // -------------------------------------------------------------------------
    // -- Helper: Serialization Strings 
    // -------------------------------------------------------------------------
    def String removeParenthesis(String str) {
        if (str.contains("&")) {
            return str.replaceAll("\\(\\(", "(").replaceAll("\\)\\)", ")");
        }
        return str
    }
    
    def String serializeIndices(ISerializer serializer, List<Expression> indices) {
        var String indicesStr = ""
        for(index : indices) {
            indicesStr = indicesStr + "[" + 
                serializer.serialize(index.copy)
                + "]"          
        }
        indicesStr
    }

    def boolean topdown() {
        orientation == ORIENTATION_PORTRAIT
    }

    def boolean leftright() {
        orientation == ORIENTATION_LANDSCAPE
    }

    def RenderingFactory() {
        KRenderingFactory.eINSTANCE
    }
}
