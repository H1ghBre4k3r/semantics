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
package de.cau.cs.kieler.sccharts.klighd

import com.google.inject.Inject
import de.cau.cs.kieler.core.kgraph.KEdge
import de.cau.cs.kieler.core.kgraph.KLabel
import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.Colors
import de.cau.cs.kieler.core.krendering.KRectangle
import de.cau.cs.kieler.core.krendering.KRenderingFactory
import de.cau.cs.kieler.core.krendering.LineStyle
import de.cau.cs.kieler.core.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.core.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.kico.KielerCompiler
import de.cau.cs.kieler.kiml.klayoutdata.KLayoutData
import de.cau.cs.kieler.kiml.options.LayoutOptions
import de.cau.cs.kieler.kitt.klighd.tracing.internal.TracingEdgeNode
import de.cau.cs.kieler.kitt.tracing.TracingManager
import de.cau.cs.kieler.kitt.tracing.internal.TracingMapping
import de.cau.cs.kieler.klighd.internal.util.SourceModelTrackingAdapter
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.scg.AbsoluteWrite_Read
import de.cau.cs.kieler.scg.AbsoluteWrite_RelativeWrite
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.Dependency
import de.cau.cs.kieler.scg.RelativeWrite_Read
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.Write_Write
import java.util.Collection
import java.util.HashMap
import org.eclipse.emf.ecore.EObject

import static extension com.google.common.base.Predicates.*
import de.cau.cs.kieler.core.kexpressions.ValuedObject

/**
 * @author als
 *
 */
class SCGDepExtension {

    extension KRenderingFactory = KRenderingFactory.eINSTANCE

    @Inject
    extension KEdgeExtensions

    @Inject
    extension KRenderingExtensions

    @Inject
    extension KPolylineExtensions

    @Inject
    extension KContainerRenderingExtensions

    val HashMap<KNode, SourceModelTrackingAdapter> adapters = newHashMap;

    def prepareSCGDependcyEdges(KNode rootNode) {
        val adapter = new SourceModelTrackingAdapter();
        rootNode.eAdapters.add(adapter);
        adapters.put(rootNode, adapter)
    }

    def addSCGDependcyEdges(KNode rootNode, State scc) {
        val tracking = adapters.get(rootNode);
        if (tracking != null) {
            TracingManager.activateTracing(scc);
            val result = KielerCompiler.compile("SCGDEP", scc, true, false);
            val compiledModel = result.object;
            val attachNode = rootNode.children.head;
            val equivalenceClasses = new TracingMapping(null);

            scc.eAllContents.forEach [
                var elements = tracking.getTargetElements(it);
                //If no diagram element is associated with the given model element its container is used to find an appropriate representation
                if (elements.empty && it instanceof EObject) {
                    var next = (it as EObject)
                    while (elements.empty && next != null) {
                        next = next.eContainer;
                        elements = tracking.getTargetElements(next);
                    }
                    equivalenceClasses.putAll(it, elements);
                }
            ];

            if (compiledModel instanceof SCGraph && attachNode != null) {
                val scg = compiledModel as SCGraph;

                val mapping = TracingManager.getMapping(scg, scc);
                if (mapping != null) {
                    val filterDiagramPredicate = KLabel.instanceOf.or(KRectangle.instanceOf);
                    val filterModelPredicate = Action.instanceOf.or(ValuedObject.instanceOf);
                    for (Assignment asng : scg.nodes.filter(Assignment)) {
                        val sources = mapping.get(asng).filter(filterModelPredicate).fold(newHashSet()) [ list, item |
                            list.addAll(tracking.getTargetElements(item).filter(filterDiagramPredicate));
                            list.addAll(equivalenceClasses.getTargets(item).filter(EObject).filter(filterDiagramPredicate).toList);
                            list;
                        ];
                        for (Dependency dep : asng.dependencies) {
                            if (!dep.confluent && dep.concurrent) {
                                val targets = mapping.get(dep.target).filter(filterModelPredicate).fold(newHashSet()) [ list, item |
                                    list.addAll(tracking.getTargetElements(item).filter(filterDiagramPredicate));
                                    list.addAll(equivalenceClasses.getTargets(item).filter(EObject).filter(filterDiagramPredicate));
                                    list;
                                ];
                                for (EObject source : sources) {
                                    for (EObject target : targets) {
                                        attachNode.addDependencyEdge(source, target, dep)
                                    }
                                }
                            }
                        }

                    }
                }
            }
            rootNode.eAdapters.remove(tracking)
        }
        return rootNode
    }

    protected def KEdge create edge : createEdge() addDependencyEdge(KNode attachNode, EObject source, EObject target,
        Dependency dependency) {
        edge.getData(KLayoutData).setProperty(LayoutOptions.NO_LAYOUT, true);
        edge.data += createKCustomRendering => [
            it.figureObject = new TracingEdgeNode(source, target, attachNode);
            it.setProperty(KlighdProperties.NOT_SELECTABLE, true);
            it.addPolyline => [
                if(dependency instanceof AbsoluteWrite_Read) it.foreground = Colors.GREEN
                if(dependency instanceof RelativeWrite_Read) it.foreground = Colors.GREEN
                if(dependency instanceof AbsoluteWrite_RelativeWrite) it.foreground = Colors.GREEN
                if(dependency instanceof Write_Write) it.foreground = Colors.RED
                it.lineWidth = 2
                it.lineStyle = LineStyle::DASH
                it.addArrowDecorator
            ];
        ];
        edge.source = attachNode;
        edge.target = attachNode;
    }
}
