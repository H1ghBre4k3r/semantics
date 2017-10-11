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
package de.cau.cs.kieler.sccharts.ui.synthesis.hooks

import com.google.common.collect.HashMultimap
import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kicool.compilation.CompilationContext
import de.cau.cs.kieler.kicool.compilation.Compile
import de.cau.cs.kieler.kicool.environments.Environment
import de.cau.cs.kieler.kicool.kitt.tracing.Tracing
import de.cau.cs.kieler.kicool.kitt.tracing.internal.TracingMapping
import de.cau.cs.kieler.kicool.ui.kitt.tracing.internal.TracingEdgeNode
import de.cau.cs.kieler.klighd.IKlighdSelection
import de.cau.cs.kieler.klighd.LightDiagramLayoutConfig
import de.cau.cs.kieler.klighd.SynthesisOption
import de.cau.cs.kieler.klighd.internal.util.SourceModelTrackingAdapter
import de.cau.cs.kieler.klighd.kgraph.KEdge
import de.cau.cs.kieler.klighd.kgraph.KLabel
import de.cau.cs.kieler.klighd.kgraph.KLayoutData
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.Colors
import de.cau.cs.kieler.klighd.krendering.KCustomRendering
import de.cau.cs.kieler.klighd.krendering.KDecoratorPlacementData
import de.cau.cs.kieler.klighd.krendering.KPolyline
import de.cau.cs.kieler.klighd.krendering.KRectangle
import de.cau.cs.kieler.klighd.krendering.KRenderingFactory
import de.cau.cs.kieler.klighd.krendering.KText
import de.cau.cs.kieler.klighd.krendering.LineStyle
import de.cau.cs.kieler.klighd.krendering.SimpleUpdateStrategy
import de.cau.cs.kieler.klighd.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.sccharts.Region
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.Scope
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.ui.synthesis.GeneralSynthesisOptions
import de.cau.cs.kieler.sccharts.ui.synthesis.SCChartsDiagramProperties
import de.cau.cs.kieler.sccharts.ui.synthesis.styles.StateStyles
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.DataDependency
import de.cau.cs.kieler.scg.DataDependencyType
import de.cau.cs.kieler.scg.Dependency
import de.cau.cs.kieler.scg.SCGraphs
import java.util.HashMap
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.graph.properties.IProperty
import org.eclipse.elk.graph.properties.Property
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.ui.progress.UIJob

import static extension com.google.common.base.Predicates.*
import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*

/**
 * Adds the SCG dependencies into the SCChart.
 * 
 * TODO Move this class to de.cau.cs.kieler.sccharts.scg
 * 
 * @author als
 * @kieler.design 2015-08-13 proposed
 * @kieler.rating 2015-08-13 proposed yellow
 * 
 */
class SCGDependencyHook extends SynthesisActionHook {

	extension KRenderingFactory = KRenderingFactory.eINSTANCE

    @Inject
    extension KEdgeExtensions

	@Inject
	extension KRenderingExtensions

	@Inject
	extension KPolylineExtensions

	@Inject
	extension KContainerRenderingExtensions

	/** Action ID */
	public static final String ID = "de.cau.cs.kieler.sccharts.ui.synthesis.hooks.SCGDependencyHook";
	/** Job name */
	public static final String JOB_NAME = "Calculating SCG Dependencies";
	/** The related synthesis option */
	public static final SynthesisOption SHOW_SCG_DEPENDENCIES = SynthesisOption.createCheckOption(
		"Show SCG Dependencies", false)
		.setCategory(GeneralSynthesisOptions::DEBUGGING)
		.setUpdateStrategy(SimpleUpdateStrategy.ID)
		// Deactivated to force Simple Update Strategy
		//.setUpdateAction(SCGDependencyHook.ID); // Add this action as updater
	/** The related synthesis option for regions */
    public static final SynthesisOption SCG_DEPENDENCY_TYPES = SynthesisOption.createChoiceOption("Dependency Types",
        newArrayList(DepType.Elements, DepType.Regions),
        DepType.Elements).setCategory(GeneralSynthesisOptions::DEBUGGING)
        .setUpdateStrategy(SimpleUpdateStrategy.ID)
        // Deactivated to force Simple Update Strategy
        //.setUpdateAction(SCGDependencyHook.ID); // Add this action as updater
    /** Option to show only dependencies of selected elements */
	public static final SynthesisOption SHOW_SELECTED_DEPENDENCIES = SynthesisOption.createCheckOption(
		"Show only Dependencies of selected Elements", false)
		.setCategory(GeneralSynthesisOptions::DEBUGGING)
		.setUpdateStrategy(SimpleUpdateStrategy.ID)
        // Deactivated to force Simple Update Strategy
        //.setUpdateAction(SCGDependencyHook.ID); // Add this action as updater
	/** Property to store analysis results */
	private static final IProperty<HashMultimap<DepType, KEdge>> DEPENDENCY_EDGES = new Property<HashMultimap<DepType, KEdge>>(
		"de.cau.cs.kieler.sccharts.ui.synthesis.hooks.dependency.edges", null);
	
	/**
     * Global selection listener which dependencies for element selection.
     */
    private static final ISelectionChangedListener selectionListener = new ISelectionChangedListener() {

        override void selectionChanged(SelectionChangedEvent event) {
            val selection = event.getSelection() as IKlighdSelection
            // Klighd does not select KLabels when its KText is selected, so this the KLabel are
            // added here
            val selectionList = newArrayList()
            selection.diagramElementsIterator().forall[selectionList.add(it)]
            for (eObject : selection.diagramElementsIterator().filter(EObject).toIterable ){
                if (eObject instanceof KText && eObject.eContainer() instanceof KLabel) {
                    selectionList.add(eObject.eContainer());
                }
            }

            val viewContext = selection.getViewContext();
            if(viewContext.getOptionValue(SHOW_SCG_DEPENDENCIES) as Boolean && viewContext.getOptionValue(SHOW_SELECTED_DEPENDENCIES) as Boolean) {
				val rootNode = viewContext.viewModel
				val propertyHolder = rootNode.data.filter(KLayoutData).head;
				val edges = propertyHolder?.getProperty(DEPENDENCY_EDGES);
				if (edges !== null) {
					val viewer = viewContext.viewer
					edges.get(viewContext.getOptionValue(SCG_DEPENDENCY_TYPES) as DepType).forEach[
						val edgeNode = it.getData(KCustomRendering).figureObject as TracingEdgeNode
						if (selectionList.contains(edgeNode.source) || selectionList.contains(edgeNode.target)) {
							viewer.show(it)
						}else{
							viewer.hide(it)
						}
						// TODO Implement selection based on variable usage
					];
				}
            }
        }
    };
    
	/** The different type of dependency filter */
	private enum DepType {
		Elements, Regions;
	}
	
	override getDisplayedSynthesisOptions() {
		return newLinkedList(SHOW_SCG_DEPENDENCIES, SCG_DEPENDENCY_TYPES); // SHOW_SELECTED_DEPENDENCIES
	}

	override finish(Scope model, KNode rootNode) {
		if (SHOW_SCG_DEPENDENCIES.booleanValue) {
			rootNode.showDependencies(model as State);
			val contextViewer = usedContext.getViewer()?.getContextViewer();
			if (SHOW_SELECTED_DEPENDENCIES.booleanValue) {
				rootNode.hideDependencies
				contextViewer?.addSelectionChangedListener(selectionListener);
            } else {
                contextViewer?.removeSelectionChangedListener(selectionListener);
			}
		} else {
			val contextViewer = usedContext.getViewer()?.getContextViewer();
            contextViewer?.removeSelectionChangedListener(selectionListener);
		}
	}

	override executeAction(KNode rootNode) {
//	    var boolean relayout = false
//		if (SHOW_SCG_DEPENDENCIES.booleanValue) {
//			relayout = rootNode.showDependencies(usedContext.inputModel);
//		} else {
//			rootNode.hideDependencies;
//		}
//		val contextViewer = usedContext.getViewer().getContextViewer();
//		if (SHOW_SELECTED_DEPENDENCIES.booleanValue) {
//			rootNode.hideDependencies
//			contextViewer.addSelectionChangedListener(selectionListener);
//        } else {
//            contextViewer.removeSelectionChangedListener(selectionListener);
//		}
//		return ActionResult.createResult(relayout);
      return ActionResult.createResult(false);
	}

	/** 
	 * If necessary create the dependency edges and show them.
	 */
	private def boolean showDependencies(KNode rootNode, State rootState) {
		if (!(rootState instanceof State)) {
			throw new IllegalArgumentException("Cannot perform SCG analysis on models other than states");
		}
		val scc = rootState as State;
		val context = usedContext;
		val edges = rootNode.getProperty(DEPENDENCY_EDGES);
		// If not already created
		if (edges === null) {
			val tracker = rootNode.getProperty(SCChartsDiagramProperties::MODEL_TRACKER);
			if (tracker === null) {
				throw new IllegalArgumentException("Missing source model tracker");
			}
			val attachNode = rootNode.children.head;
			if (attachNode === null) {
				return false;
			}

			val type = context.getOptionValue(SCG_DEPENDENCY_TYPES) as DepType;
            if (type == DepType.Elements || type == DepType.Regions) {
    			// Create and start background job for compiling
    			new Job(JOB_NAME) {
    
    				override protected run(IProgressMonitor monitor) {
    					val newLoopElements = calculateSCGDependencyEdges(rootNode, scc, tracker, attachNode);
    
    					// This part should be synchronized with the ui
    					new UIJob(JOB_NAME) {
    
    						override runInUIThread(IProgressMonitor monitor) {
    							if (rootNode.getProperty(DEPENDENCY_EDGES) === null) {
    								rootNode.setProperty(DEPENDENCY_EDGES, newLoopElements);
    								val viewer = context.viewer;
    								newLoopElements.entries.forEach [
        									it.value.source = attachNode
        									it.value.target = attachNode
    									if (it.key != type) {
    										it.value.initiallyHide;
    										viewer?.hide(it.value);
    									}
    								];
    								// Re layout to place edges correctly
    								new LightDiagramLayoutConfig(context).performLayout
    							}
    							return Status.OK_STATUS;
    						}
    
    					}.schedule
    					return Status.OK_STATUS;
    				}
    
    			}.schedule;
    			return false;
            } else {
                return false;
            }
        } else {
            val viewer = context.viewer;
			rootNode.hideDependencies;
			edges.get(context.getOptionValue(SCG_DEPENDENCY_TYPES) as DepType).forEach[viewer.show(it)];
			return false;
		}
	}
    
	/** 
	 * Hide the dependency edges
	 */
	private def hideDependencies(KNode rootNode) {
		val propertyHolder = rootNode.data.filter(KLayoutData).head;
		val edges = propertyHolder?.getProperty(DEPENDENCY_EDGES);
		if (edges !== null) {
			val viewer = usedContext.viewer;
			edges.values.forEach[viewer.hide(it)];
		}
	}

    /**
     * Compiles the given SCChart with tracing to get dependencies
     */
    private def CompilationContext compileDependencies(State state) {
        val model = state.eContainer as SCCharts
        val cc = Compile.createCompilationContext("de.cau.cs.kieler.sccharts.netlist", model)
        
        cc.startEnvironment.setProperty(Environment.INPLACE, true)
        cc.startEnvironment.setProperty(Tracing.ACTIVE_TRACING, true)
        
        val dependecyAnalysis = cc.processorMap.entrySet.findFirst[
            key.id.equals("de.cau.cs.kieler.scg.processors.transformators.dependency")
        ]?.value
        if (dependecyAnalysis === null) throw new NullPointerException("Can not find dependency transformation in compilation system")
        // Stop after the dependency analysis
        dependecyAnalysis.environment.setProperty(Environment.CANCEL_COMPILATION, true) 
        
        cc.compile

        return cc
    }

	/** 
	 * Calculate and create the dependency edges
	 */
	private def calculateSCGDependencyEdges(KNode rootNode, State scc, SourceModelTrackingAdapter tracking,
		KNode attachNode) {

		val context = compileDependencies(scc);
		val compiledModel = context.result.getModel

		// Calculate equivalence classes for diagram elements
		val equivalenceClasses = new TracingMapping(null);
		for (EObject obj : scc.eAllContents.toIterable) {
			var elements = tracking.getTargetElements(obj);
			// If no diagram element is associated with the given model element its container
			// is used to find an appropriate representation
			if (elements.empty) {
				var next = obj;
				while (elements.empty && next !== null) {
					next = next.eContainer;
					elements = tracking.getTargetElements(next);
				}
				equivalenceClasses.putAll(obj, elements);
			}
		};

		// Find the label of the root SCChart
		val rootLabel = tracking.getTargetElements(scc).filter(KText).head;

		// Analyze dependencies and tracing
		val HashMap<Pair<EObject, EObject>, KEdge> elementEdges = newHashMap;
		val HashMap<Pair<EObject, EObject>, KEdge> regionEdges = newHashMap;
        
		if (compiledModel instanceof SCGraphs) {
			val scgRoot = (compiledModel as SCGraphs)
			val scg = scgRoot.scgs.head;

            val mapping = context.startEnvironment.getProperty(Tracing.TRACING_DATA).getMapping(scgRoot, scc.eContainer as SCCharts)      
			if (mapping !== null) {
				val filterDiagramPredicate = KLabel.instanceOf.or([
					return it instanceof KRectangle && !(it as KRectangle).getProperty(StateStyles.IS_LAYOUT_ELEMENT)
				]);
				val filterModelPredicate = or(Action.instanceOf, ValuedObject.instanceOf);
				for (Assignment asgn : scg.nodes.filter(Assignment)) {
					val sources = mapping.get(asgn).filter(filterModelPredicate).fold(newHashSet()) [ list, item |
						list.addAll(tracking.getTargetElements(item).filter(filterDiagramPredicate));
						list.addAll(
							equivalenceClasses.getTargets(item).filter(EObject).filter(filterDiagramPredicate).toList);
						list;
					];
					for (dep : asgn.dependencies.filter(DataDependency)) {
						if (!dep.confluent && dep.concurrent) {
							val targets = mapping.get(dep.target).filter(filterModelPredicate).fold(newHashSet()) [ list, item |
								list.addAll(tracking.getTargetElements(item).filter(filterDiagramPredicate));
								list.addAll(
									equivalenceClasses.getTargets(item).filter(EObject).filter(filterDiagramPredicate));
								list;
							];
							for (EObject source : sources) {
								for (EObject target : targets) {
									// Element Edges
									elementEdges.createDependencyEdge(attachNode, source, target, dep, false)
									// Region Edges
									var sourceRegion = source
									while (sourceRegion !== null &&
										!(tracking.getSourceElement(sourceRegion) instanceof Region)) {
										sourceRegion = sourceRegion.eContainer
									}
									val newsources = newLinkedList(rootLabel)
									if (sourceRegion !== null) {
										newsources.clear
										newsources.addAll((sourceRegion as KNode).data.filter(KRectangle).map[it.children.filter(KText).head])
									}
									var targetRegion = target
									while (targetRegion !== null &&
										!(tracking.getSourceElement(targetRegion) instanceof Region)) {
										targetRegion = targetRegion.eContainer
									}
									val newtargets = newLinkedList(rootLabel)
									 if (targetRegion !== null) {
									 	newtargets.clear
										newtargets.addAll((targetRegion as KNode).data.filter(KRectangle).map[it.children.filter(KText).head])
									}
									for (EObject newsource : newsources) {
										for (EObject newtarget : newtargets) {
											if (newsource != newtarget) {
												regionEdges.createDependencyEdge(attachNode, newsource, newtarget, dep, true)
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		val edges = HashMultimap.create(2,newArrayList(elementEdges.size, regionEdges.size).max);
		edges.putAll(DepType.Elements, elementEdges.values)
        edges.putAll(DepType.Regions, regionEdges.values)
		return edges;
	}

    /** 
     * Create and configure the edge for the given dependency
     */
    private def void createDependencyEdge(HashMap<Pair<EObject, EObject>, KEdge> edges, KNode attachNode,
        EObject source, EObject target, Dependency dependency, boolean ignoreFirstCollapsibleParent) {
        val sourceTargetPair = new Pair(source, target);
        val targetSourcePair = new Pair(target, source);
        var opposite = false;
        var KEdge edge;
        // If the is a mutual dependency use the already crested edge
        if (edges.containsKey(sourceTargetPair)) {
            edge = edges.get(sourceTargetPair);
        } else if (edges.containsKey(targetSourcePair)) {
            edge = edges.get(targetSourcePair);
            opposite = true;
        } else {
            // Create edge
            edge = createEdge(source, target);
            edges.put(sourceTargetPair, edge);
            edge.setProperty(CoreOptions.NO_LAYOUT, true);
            edge.data += createKCustomRendering => [
                val edgeNode = new TracingEdgeNode(source, target, attachNode);
                edgeNode.setIgnoreFirstCollapsibleParent(ignoreFirstCollapsibleParent, ignoreFirstCollapsibleParent)
                it.figureObject = edgeNode
                it.setProperty(KlighdProperties.NOT_SELECTABLE, true);
                it.addPolyline => [
                    it.lineWidth = 2
                    it.lineStyle = LineStyle::DASH
                    // Default arrow head
                    it.addHeadArrowDecorator
                    // Default green color
                    it.foreground = Colors.GREEN;
                    it.foreground.propagateToChildren = true;
                ];
            ];
        }
        val line = edge.getData(KCustomRendering).children.filter(KPolyline).head;
        // Configure mutual dependency with additional arrow
        if (dependency instanceof DataDependency) {
            if (dependency.type == DataDependencyType.WRITE_WRITE) {
                line.foreground = Colors.RED
                line.foreground.propagateToChildren = true;
            }
        }
        if (opposite) {
            line.addTailArrowDecorator.placementData as KDecoratorPlacementData => [
                // This fixes a weird bug in the KPolylineExtension
                it.setXOffset(-6f);
                it.setYOffset(-5f);
            ];
        }
    }
}