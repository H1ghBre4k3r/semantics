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
package de.cau.cs.kieler.kitt.klighd.tracing.internal

import com.google.common.base.Predicates
import com.google.inject.Inject
import de.cau.cs.kieler.core.kgraph.KEdge
import de.cau.cs.kieler.core.kgraph.KGraphElement
import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.Colors
import de.cau.cs.kieler.core.krendering.KRendering
import de.cau.cs.kieler.core.krendering.KRenderingFactory
import de.cau.cs.kieler.core.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.core.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.core.util.Pair
import de.cau.cs.kieler.kiml.klayoutdata.KLayoutData
import de.cau.cs.kieler.kiml.options.LayoutOptions
import de.cau.cs.kieler.kitt.tracing.TracingTreeExtensions
import de.cau.cs.kieler.kitt.tracing.internal.TracingMapping
import de.cau.cs.kieler.kitt.tracingtree.EObjectWrapper
import de.cau.cs.kieler.kitt.tracingtree.ModelWrapper
import de.cau.cs.kieler.klighd.ViewContext
import de.cau.cs.kieler.klighd.util.KlighdProperties
import java.util.Collection
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject

import static extension de.cau.cs.kieler.kitt.klighd.actions.AbstractTracingSelectionAction.*
import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*
import static extension de.cau.cs.kieler.kitt.tracing.TracingManager.*
import static extension de.cau.cs.kieler.klighd.util.ModelingUtil.*
import de.cau.cs.kieler.kitt.klighd.tracing.TracingProperties
import de.cau.cs.kieler.klighd.IViewer

/**
 * Adds tracing edges from mappings to a diagram.
 * 
 * @author als
 * @kieler.design 2015-02-25 proposed
 * @kieler.rating 2015-02-25 proposed yellow
 */
class TracingVisualizer {

    extension KRenderingFactory = KRenderingFactory.eINSTANCE

    @Inject
    extension KEdgeExtensions

    @Inject
    extension KRenderingExtensions

    @Inject
    extension KPolylineExtensions

    @Inject
    extension KContainerRenderingExtensions

    @Inject
    extension TracingTreeExtensions

    /** Checks if tracing information exists for the given model */
    def boolean hasTracingInformation(Object model, KNode diagram, ViewContext viewContext) {
        if (model instanceof EObject && (model as EObject).tracingActivated) {

            //If model is traced model directly
            return true;
        } else {

            //If model is a wrapper around a traced model marked as TRACED_MODEL_ROOT_NODE
            return diagram.eAllContentsOfType2(typeof(KNode)).filter [
                (it as KNode).getData(KLayoutData)?.getProperty(TracingProperties.TRACED_MODEL_ROOT_NODE)
            ].findFirst[viewContext.getSourceElement(it).tracingActivated] != null;
        }
    }

    /** Removed all tracing edges from the diagram  */
    def clearTracing(KNode diagram, IViewer view) {
        diagram.hideTracingSelection;

        //Hide all edges marked as TRACING_EDGE
        diagram.eAllContentsOfType2(typeof(KNode)).forEach [
            (it as KNode).outgoingEdges.filter [
                it.tracingEdge;
            ].toList.forEach [
                view.hide(it);
            ]
        ]
    }

    /** Shows all tracing edges in the given diagram  */
    def traceAll(KNode diagram, ViewContext viewContext, boolean forceReTrace) {
        diagram.showTracingSelection;

        //check for existing tracing edge
        val hasTracingEdges = diagram.eAllContentsOfType2(typeof(KNode)).findFirst [
            (it as KNode).outgoingEdges.findFirst [
                it.tracingEdge;
            ] != null;
        ] != null;

        //add tracing edges if necessary
        if (!hasTracingEdges || forceReTrace) {
            diagram.addAllTracingEdges(viewContext);
        }

        //Show all edges marked as TRACING_EDGE
        val view = viewContext.viewer;
        diagram.eAllContentsOfType2(typeof(KNode)).forEach [
            (it as KNode).outgoingEdges.filter [
                it.tracingEdge;
            ].toList.forEach [
                view.show(it);
            ]
        ];
    }

    /** Shows tracing edges in the given diagram for the given selection */
    def traceElements(KNode diagram, ViewContext viewContext, List<EObject> selection, boolean forceReTrace) {
        diagram.showTracingSelection;

        //check for existing tracing edge
        val hasTracingEdges = diagram.eAllContentsOfType2(typeof(KNode)).findFirst [
            (it as KNode).outgoingEdges.findFirst [
                it.tracingEdge;
            ] != null;
        ] != null;

        //add tracing edges if necessary
        if (!hasTracingEdges || forceReTrace) {
            diagram.addAllTracingEdges(viewContext);
        }

        //Show selected edges marked as TRACING_EDGE
        val HashSet<Object> visibleEdgesModelOrigin = newHashSet(); //Set of all origins of tracing edges which are selected or related to these
        if (selection != null && !selection.empty) {
            val selectedSourceElements = newHashSet;

            //Get the stored equivalence class element
            val equivalenceClasses = viewContext.getProperty(InternalTracingProperties.DIAGRAM_EQUIVALENCE_CLASSES);
            for (EObject obj : selection) {
                val source = viewContext.getSourceElement(obj);
                if (source != null) {
                    selectedSourceElements.add(source);
                }
                if (equivalenceClasses != null) {
                    val boo = equivalenceClasses.getOrigins(obj);
                    selectedSourceElements.addAll(boo);
                }
            }

            if (viewContext.inputModel instanceof ModelWrapper) { //In case of displaying a TracingTree
                selectedSourceElements.forEach [
                    if (it instanceof EObjectWrapper) {
                        val eow = it as EObjectWrapper;

                        //add tracing children
                        val children = new FunctionalTreeIterator(eow, true,
                            [
                                val elem = it as EObjectWrapper;
                                if (!elem.model.targetTransformations.empty) {
                                    elem.children(elem.model.targetTransformations.get(0)).iterator;
                                } else {
                                    emptyList.iterator;
                                }
                            ]).toIterable.toList;
                        visibleEdgesModelOrigin.addAll(children);

                        //add tracing parents
                        val parents = new FunctionalTreeIterator(eow, false,
                            [
                                val elem = it as EObjectWrapper;
                                if (elem.model.sourceTransformation != null) {
                                    elem.parents(elem.model.sourceTransformation).iterator;
                                } else {
                                    emptyList.iterator;
                                }
                            ]).toIterable.toList;
                        visibleEdgesModelOrigin.addAll(parents);

                        //add real object if tracing tree is not transient
                        if (!eow.model.transient) {
                            visibleEdgesModelOrigin.addAll(parents.map[it.EObject].filterNull);
                        }
                    } else {
                        visibleEdgesModelOrigin.add(it);
                    }
                ]
            } else { //In case of displaying a chain
                val mapping = viewContext.getProperty(InternalTracingProperties.MAPPING);
                if (mapping != null) {
                    val selectedSourceElementsList = selectedSourceElements.toList;
                    var children = selectedSourceElementsList; //first add already selected elements
                    while (!children.empty && visibleEdgesModelOrigin.addAll(children)) { //continue if derived elements exist and are new (changing the set)
                        children = children.fold(newLinkedList) [ list, item |
                            //derive implicit selected elements from mapping
                            list.addAll(mapping.getTargets(item));
                            list;
                        ];
                    }
                    var parents = selectedSourceElementsList;
                    do { //this time a do-while because selectedSourceElements already contained
                        parents = parents.fold(newLinkedList) [ list, item |
                            //derive implicit selected elements from reverse mapping
                            list.addAll(mapping.getOrigins(item));
                            list;
                        ];
                    } while (!parents.empty && visibleEdgesModelOrigin.addAll(parents)); //continue if derived elements exist and are new (changing the set)
                }
            }
        }

        //iterate over all tracing edges and show all edges originated from selected model elements, all others invisible
        val view = viewContext.viewer;
        diagram.eAllContentsOfType2(typeof(KNode)).forEach [
            (it as KNode).outgoingEdges.filter [
                it.tracingEdge;
            ].toList.forEach [
                if (visibleEdgesModelOrigin.empty) {
                    view.hide(it);
                } else {
                    val origin = it.getData(KLayoutData).getProperty(InternalTracingProperties.TRACING_EDGE);
                    if (visibleEdgesModelOrigin.contains(origin.first) &&
                        visibleEdgesModelOrigin.contains(origin.second)) {
                        view.show(it);
                    } else {
                        view.hide(it);
                    }
                }
            ]
        ];
    }

    /** Adds all tracing edges to the diagram */
    private def addAllTracingEdges(KNode diagram, ViewContext viewContext) {

        //Remove all edges marked as TRACING_EDGE
        diagram.eAllContentsOfType2(typeof(KNode)).forEach [
            (it as KNode).outgoingEdges.filter [
                it.tracingEdge;
            ].toList.forEach [
                it.source = null;
                it.target = null;
            ]
        ];

        //Clear equivalence classes
        viewContext.setProperty(InternalTracingProperties.DIAGRAM_EQUIVALENCE_CLASSES, null);

        //Add tracing edges
        if (viewContext.inputModel instanceof ModelWrapper) { //Tracing Tree
            val tree = viewContext.inputModel as ModelWrapper;
            val selection = diagram.getTracingSelection(viewContext);
            if (selection != null) { //resolve mapping if source target are selected
                addTracingTreeEdges(selection.first as ModelWrapper, selection.second as ModelWrapper, viewContext);
            } else { //dont resolve -> show all
                tree.succeedingTransformations.forEach [
                    addTracingTreeEdges(it.source, it.target, viewContext);
                ];
            }
        } else { //Other models

            //get all traced models contained in this model
            val tracedModelMap = diagram.eAllContentsOfType2(typeof(KNode)).filter [
                (it as KNode).getData(KLayoutData).getProperty(TracingProperties.TRACED_MODEL_ROOT_NODE);
            ].map[it as KNode].fold(newHashMap()) [ map, node |
                val model = viewContext.getSourceElement(node);
                if (model != null && model.tracingActivated) {
                    map.put(model, node);
                }
                return map;
            ];

            if (!tracedModelMap.empty) {
                val traceMap = new TracingMapping(null);
                val equivalenceClasses = new TracingMapping(null);
                val selection = diagram.getTracingSelection(viewContext);
                if (selection != null) { //resolve mapping if source target are selected
                    val mapping = getMapping(selection.first, selection.second);
                    mapping.entries.iterator.addTracingEdges(viewContext, tracedModelMap.get(selection.first),
                        equivalenceClasses);
                    traceMap.putAll(mapping);
                } else { //dont resolve -> show all
                    val chain = tracedModelMap.keySet.head.tracingChain;
                    if (!chain.models.empty) {

                        //Iterate over all step in the chain apply mappings
                        val chainIter = chain.models.listIterator;
                        var item = chainIter.next;
                        while (chainIter.hasNext) {
                            var next = chainIter.next;
                            if (tracedModelMap.containsKey(item)) {
                                if (tracedModelMap.containsKey(next)) {

                                    //create edges
                                    val mapping = chain.getRawMapping(item, next);
                                    mapping.entryIterator.addTracingEdges(viewContext, tracedModelMap.get(item),
                                        equivalenceClasses);
                                    traceMap.putAll(mapping);
                                } else { //if a model in the chain is not represented in the diagram skip it and resolve to next represented one
                                    var loop = true;
                                    while (loop && chainIter.hasNext) {
                                        next = chainIter.next;
                                        if (tracedModelMap.containsKey(next)) {

                                            //create edges
                                            val mapping = getMapping(item, next)
                                            mapping.entries.iterator.addTracingEdges(viewContext,
                                                tracedModelMap.get(item), equivalenceClasses);
                                            traceMap.putAll(mapping);

                                            //end this loop
                                            loop = false;
                                        }
                                    }
                                }
                            }
                            item = next;
                        }
                    }
                }
                viewContext.setProperty(InternalTracingProperties.MAPPING, traceMap);
                viewContext.setProperty(InternalTracingProperties.DIAGRAM_EQUIVALENCE_CLASSES, equivalenceClasses);
            }
        }
    }

    /** Adds tracing edges for given source target mapping */
    private def addTracingEdges(Iterator<Map.Entry<Object, Object>> entryIter, ViewContext viewContext, KNode attachNode,
        TracingMapping equivalenceClasses) {
        entryIter.forEach [ entry |
            createTracingEdges(entry.key, entry.value, attachNode, viewContext, equivalenceClasses);
        ]
    }

    /** Adds tracing edges for given source target mapping in a TracingTree */
    private def addTracingTreeEdges(ModelWrapper source, ModelWrapper target, ViewContext viewContext) {
        if (source != null && target != null) {
            val mapping = source.joinWrapperMappings(target);
            var Map<EObjectWrapper, EObject> _sourceInstanceMap = null;
            var Map<EObjectWrapper, EObject> _targetInstanceMap = null;
            val sourceModelRootNode = viewContext.getTargetElements(source).findFirst[
                it instanceof KNode &&
                    (it as KNode).getData(KLayoutData).getProperty(TracingProperties.TRACED_MODEL_ROOT_NODE)] as KNode;
            val targetModelRootNode = viewContext.getTargetElements(target).findFirst[
                it instanceof KNode &&
                    (it as KNode).getData(KLayoutData).getProperty(TracingProperties.TRACED_MODEL_ROOT_NODE)] as KNode;
            if (!source.transient && sourceModelRootNode != null && source.rootObject.EObject != null) {
                _sourceInstanceMap = source.modelInstanceMapping(source.rootObject.EObject);
            }
            if (!target.transient && targetModelRootNode != null && target.rootObject.EObject != null) {
                _targetInstanceMap = target.modelInstanceMapping(target.rootObject.EObject);
            }
            val sourceInstanceMap = _sourceInstanceMap;
            val targetInstanceMap = _targetInstanceMap;
            if (mapping != null && !mapping.empty) {
                mapping.entries.forEach [ entry |
                    val key = if (sourceInstanceMap != null) {
                            sourceInstanceMap.get(entry.key);
                        } else {
                            entry.key;
                        }
                    val value = if (targetInstanceMap != null) {
                            targetInstanceMap.get(entry.value);
                        } else {
                            entry.value;
                        }
                    createTracingEdges(key, value, sourceModelRootNode, viewContext, null);
                ]
            }
        }
    }

    /** Adds a tracing edges for given source target pair */
    private def createTracingEdges(Object source, Object target, KNode attachNode, ViewContext viewContext,
        TracingMapping equivalenceClasses) {
        val origin = new Pair(source, target);
        val predicate = Predicates.or(viewContext.getProperty(TracingProperties.VISUALIZATION_PREDICATE),
            [
                if (it instanceof KGraphElement) {
                    return (it as KGraphElement).getData(KLayoutData).getProperty(TracingProperties.TRACING_NODE);
                } else if (it instanceof KRendering) {
                    return (it as KRendering).getProperty(TracingProperties.TRACING_NODE);
                } else {
                    return false;
                }
            ]);
        viewContext.getDiagramElements(source, equivalenceClasses).filter(predicate).forEach [ sourceElem |
            viewContext.getDiagramElements(target, equivalenceClasses).filter(predicate).forEach [ targetElem |
                createTracingEdge(sourceElem, targetElem, origin, attachNode);
            ]
        ]
    }

    /** Returns the diagram elements associated with the given model element including appropriate equivalent elements */
    private def Collection<EObject> getDiagramElements(ViewContext viewContext, Object modelElement,
        TracingMapping equivalenceClasses) {
        var elements = viewContext.getTargetElements(modelElement);

        //If no diagram element is associated with the given model element its container is used to find an appropriate representation
        if (elements.empty && modelElement instanceof EObject) {
            val maxDepth = viewContext.getProperty(TracingProperties.VISUALIZATION_EQUIVALENCE_CLASS_DEPTH);
            var depth = 0;
            var next = (modelElement as EObject)
            while (elements.empty && next != null && depth < maxDepth) {
                next = next.eContainer;
                elements = viewContext.getTargetElements(next);
            }
            equivalenceClasses.putAll(modelElement, elements);
        }
        return elements;
    }

    /** Create a single tracing edge and adds it to the diagram hidden */
    private def createTracingEdge(EObject source, EObject target, Pair<Object, Object> origin, KNode attachNode) {
        if (source != null && target != null && origin != null && attachNode != null) {
            val edge = createEdge;
            edge.getData(KLayoutData).setProperty(LayoutOptions.NO_LAYOUT, true);
            edge.getData(KLayoutData).setProperty(InternalTracingProperties.TRACING_EDGE, origin);
            edge.initiallyHide;
            edge.data += createKCustomRendering => [
                it.figureObject = new TracingEdgeNode(source, target, attachNode);
                it.setProperty(KlighdProperties.NOT_SELECTABLE, true);
                it.addPolyline => [
                    it.foreground = Colors.CHOCOLATE_1;
                    it.addArrowDecorator;
                ];
            ];
            edge.source = attachNode;
            edge.target = attachNode;
        }

    }

    /** Checks if given edge is a tracing edge */
    private def isTracingEdge(KEdge edge) {
        return edge.getData(KLayoutData)?.getProperty(InternalTracingProperties.TRACING_EDGE) != null;
    }
}
