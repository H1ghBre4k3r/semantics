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
package de.cau.cs.kieler.kicool.ui.synthesis.updates

import java.util.Map
import de.cau.cs.kieler.kicool.compilation.RuntimeSystems
import de.cau.cs.kieler.klighd.krendering.KForeground

import static extension org.eclipse.xtext.EcoreUtil2.* 
import de.cau.cs.kieler.klighd.krendering.KColor
import de.cau.cs.kieler.klighd.krendering.KBackground
import de.cau.cs.kieler.kicool.compilation.observer.AbstractProcessorNotification
import de.cau.cs.kieler.kicool.compilation.ProcessorStatus
import de.cau.cs.kieler.klighd.krendering.KContainerRendering
import de.cau.cs.kieler.klighd.krendering.KColoring
import static extension de.cau.cs.kieler.kicool.ui.synthesis.styles.ColorStore.*
import static de.cau.cs.kieler.kicool.ui.synthesis.styles.ColorStore.Color.*
import static de.cau.cs.kieler.kicool.ui.synthesis.styles.ColorSystem.*
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorProgress
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorFinished
import static extension de.cau.cs.kieler.kicool.util.KiCoolUtils.uniqueProcessorId
import de.cau.cs.kieler.kicool.compilation.observer.AbstractCompilationNotification
import static extension de.cau.cs.kieler.kicool.environments.Environment.*
import static extension de.cau.cs.kieler.kicool.compilation.Metric.*
import de.cau.cs.kieler.kicool.ui.KiCoolUiModule
import de.cau.cs.kieler.kicool.ui.synthesis.actions.SelectIntermediateAction
import de.cau.cs.kieler.klighd.krendering.Trigger
import de.cau.cs.kieler.klighd.krendering.KRendering
import de.cau.cs.kieler.klighd.krendering.KRenderingFactory
import de.cau.cs.kieler.kicool.ui.view.CompilerView
import de.cau.cs.kieler.kicool.compilation.observer.CompilationStart
import de.cau.cs.kieler.kicool.environments.Snapshots
import static extension de.cau.cs.kieler.kicool.ui.synthesis.KNodeProperties.INTERMEDIATE_DATA
import static extension de.cau.cs.kieler.kicool.ui.synthesis.KNodeProperties.TOGGLE_ON_OFF_DATA
import static extension de.cau.cs.kieler.kicool.ui.synthesis.KNodeProperties.PROCESSOR_IDENTIFIER
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.kgraph.KIdentifier
import de.cau.cs.kieler.klighd.kgraph.KLabel
import de.cau.cs.kieler.klighd.kgraph.KShapeLayout
import de.cau.cs.kieler.kicool.compilation.observer.AbstractContextNotification
import de.cau.cs.kieler.kicool.ui.synthesis.feedback.PostUpdateDoubleCollector
import org.eclipse.elk.core.options.CoreOptions
import de.cau.cs.kieler.klighd.LightDiagramServices
import de.cau.cs.kieler.kicool.ui.synthesis.actions.ToggleProcessorOnOffAction
import de.cau.cs.kieler.kicool.ui.synthesis.actions.IntermediateData
import de.cau.cs.kieler.kicool.ui.synthesis.actions.ToggleOnOffData
import static extension de.cau.cs.kieler.kicool.compilation.Metric.METRIC
import java.util.Locale
import de.cau.cs.kieler.klighd.kgraph.KEdge
import de.cau.cs.kieler.klighd.krendering.KPolygon
import de.cau.cs.kieler.kicool.ProcessorReference
import org.eclipse.emf.ecore.EObject
import de.cau.cs.kieler.kicool.ui.synthesis.KiCoolSynthesis
import de.cau.cs.kieler.kicool.ui.synthesis.styles.ColorSystem
import static extension de.cau.cs.kieler.kicool.ui.synthesis.updates.MessageObjectReferencesManager.fillUndefinedColors
import de.cau.cs.kieler.kicool.ui.synthesis.actions.OnOffToggle
import de.cau.cs.kieler.kicool.ui.synthesis.MessageObjectListPair
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.LightDiagramLayoutConfig
import de.cau.cs.kieler.kicool.environments.EnvironmentPair
import de.cau.cs.kieler.kicool.compilation.observer.CompilationChanged
import de.cau.cs.kieler.kicool.ui.synthesis.ProcessorSynthesis
import de.cau.cs.kieler.klighd.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.kicool.ProcessorGroup
import de.cau.cs.kieler.kicool.ui.synthesis.ProcessorStyles
import com.google.inject.Guice
import de.cau.cs.kieler.klighd.krendering.KPolyline
import de.cau.cs.kieler.klighd.krendering.LineCap
import de.cau.cs.kieler.klighd.krendering.extensions.PositionReferenceX
import de.cau.cs.kieler.klighd.krendering.extensions.PositionReferenceY
import de.cau.cs.kieler.klighd.krendering.KStyle
import de.cau.cs.kieler.klighd.krendering.LineJoin
import de.cau.cs.kieler.kicool.ui.synthesis.actions.IntermediateSelection
import de.cau.cs.kieler.klighd.internal.util.KlighdInternalProperties
import org.eclipse.ui.progress.UIJob
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import de.cau.cs.kieler.klighd.util.KlighdProperties
import de.cau.cs.kieler.klighd.krendering.KText
import de.cau.cs.kieler.kicool.ui.synthesis.actions.SelectParent
import de.cau.cs.kieler.kicool.ui.synthesis.styles.SkinSelector
import java.util.List
import org.eclipse.ui.IEditorPart

/**
 * The data manager handles all synthesis updates.
 * 
 * @author ssm
 * @kieler.design 2017-02-27 proposed 
 * @kieler.rating 2017-02-27 proposed yellow
 */
class ProcessorDataManager {
    
    private static val KRenderingFactory renderingFactory = KRenderingFactory::eINSTANCE
    private static KRenderingExtensions kRenderingExtensions = new KRenderingExtensions
    private static KEdgeExtensions kEdgeExtensions = new KEdgeExtensions
    private static ProcessorSynthesis processorSynthesis = new ProcessorSynthesis
    private static ProcessorStyles processorStyles = new ProcessorStyles
    
    public static val NODE_PROCESSOR_BODY = "processorbody"
    static val NODE_ACTIVITY_STATUS = "status"
    public static val NODE_NAME = "name"
    static val NODE_PROGRESS = #["p1", "p2", "p3", "p4", "p5"]
    static val NODE_ENVIRONMENT = "environment"
    static val NODE_INTERMEDIATE = "intermediate"
    static val NODE_SOURCE = "sourcebody"
    static val NODE_ACTIVE = "active"
    
    static val INTERMEDIATE_KGT = "intermediate.kgt"
    static var INTERMEDIATE_NODE = KiCoolSynthesis.getKGTFromBundle(KiCoolUiModule.BUNDLE_ID, INTERMEDIATE_KGT)
    
    private static def intermediateNode() {
        INTERMEDIATE_NODE.copy
    }
    
    static def void populateProcessorData(ProcessorReference processorReference, KNode node) {
        node.setProperty(PROCESSOR_IDENTIFIER, processorReference)
        
        val rtProcessor = RuntimeSystems.getProcessorInstance(processorReference)
        
        val nodeIdMap = node.createNodeIdMap
        
        if (rtProcessor === null) {
            nodeIdMap.findNode(NODE_PROCESSOR_BODY).setFrameErrorColor
            nodeIdMap.findNode(NODE_NAME).label.text = processorReference.id.split("\\.").last
            return;
        }
        
        val nameNode = nodeIdMap.findNode(NODE_NAME)
        val nameStr = if (rtProcessor.name.length < SkinSelector.skinMaxNameSize) rtProcessor.name else
            rtProcessor.name.substring(0, SkinSelector.skinMaxNameSize - 2) + "..."
        val label = nameNode.label
        label.text = nameStr
//        label.data.filter(KText).head.setProperty(KlighdProperties.NOT_SELECTABLE, true)
        val text = label.data.filter(KText).head
        text.addAction(Trigger::SINGLECLICK, SelectParent.ID)
        nameNode.containers.forEach[ addAction(Trigger::SINGLECLICK, SelectParent.ID) ]
        
        val toggleOnOffNode = nodeIdMap.findNode(NODE_ACTIVE)
        if (toggleOnOffNode !== null) {
            toggleOnOffNode.container.addAction(Trigger::SINGLECLICK, ToggleProcessorOnOffAction.ID)
            toggleOnOffNode.setProperty(TOGGLE_ON_OFF_DATA, new ToggleOnOffData(processorReference))
            val toggle = ToggleProcessorOnOffAction.deactivatedProcessors.get(processorReference)
            if (toggle === null || toggle == OnOffToggle.ON) {
                setFBColor(getContainer(toggleOnOffNode), ON)
            } else if (toggle == OnOffToggle.OFF) {
                setFBColor(getContainer(toggleOnOffNode), OFF)
            } else {
                setFBColor(getContainer(toggleOnOffNode), HALT)
            }
        }
    }
    
    static def void setCompatibilityError(KNode node) {
        val nodeIdMap = node.createNodeIdMap
        
        nodeIdMap.findNode(NODE_PROCESSOR_BODY).setFrameErrorColor
    }
    
    
    static def void resetSystem(AbstractCompilationNotification compilationNotification, KNode node, CompilerView view) {
        val allProcessors = compilationNotification.compilationContext.processorMap.keySet.toList
        for(processor : allProcessors) {
            val processorNode = node.findNode(processor.uniqueProcessorId)    
            if (processorNode === null) {
                // This can happen because metrics are also listed in the processor map.
            } else {
                processorNode.resetProcessorNode(node)
            }
        }
        
        if (compilationNotification instanceof CompilationStart) {
            // Clear all intermediate results and data
            node.eAllOfType(KNode).forEach[
                if (getData(KIdentifier) !== null && NODE_INTERMEDIATE.equals(getData(KIdentifier).id)) {
                    children.clear
                }
                setProperty(INTERMEDIATE_DATA, null)
            ]
            
            // Set Select Nothing Data
            node.setProperty(INTERMEDIATE_DATA, 
                new IntermediateData(null, 
                    compilationNotification.compilationContext, 
                    null, view, -1, node
                ))
            
            
            // Set source model
            val sourceNode = node.findNode(NODE_SOURCE)
            val processorUnit = compilationNotification.compilationContext.getFirstProcessorInstance
            sourceNode.container.removeAllActions
            sourceNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
            sourceNode.setProperty(INTERMEDIATE_DATA, 
                new IntermediateData(null, 
                    compilationNotification.compilationContext, 
                    null, view, -1, sourceNode
                ))
        }
        
        // Reload intermediate nodes.
        INTERMEDIATE_NODE = KiCoolSynthesis.getKGTFromBundle(KiCoolUiModule.BUNDLE_ID, INTERMEDIATE_KGT)
    }
    
    static def void resetProcessor(AbstractProcessorNotification processorNotification, KNode node) {
        val compilationContext = processorNotification.compilationContext
        val processorReference = processorNotification.processorReference
        val processorInstance = processorNotification.processorInstance
        val originalProcessorReference = compilationContext.getOriginalProcessorEntry(processorReference)
        var KNode processorNodeCandidate = null
        if (originalProcessorReference === null) {
            processorNodeCandidate = node.findNode(processorReference.uniqueProcessorId)
        } else {
            processorNodeCandidate = node.findNode(originalProcessorReference.uniqueProcessorId) 
        }
        val processorNode = processorNodeCandidate
        if (processorNode === null) {
            System.err.println("There was an update notification for an non-existing processor (" + originalProcessorReference.uniqueProcessorId + 
                "). This should not happen. I'm very sorry.")
            return
        }
        
        processorNode.resetProcessorNode(node)
        
        val edges = processorNode.incomingEdges
        if (edges.size > 1) for (edge : edges) {
            val procId = edge.source.getProperty(PROCESSOR_IDENTIFIER)
            if (procId !== null) {
                val proc = processorNotification.compilationContext.processorMap.get(procId)
                if (processorInstance.sourceEnvironment != proc.environment) {
                    edge.container.setFBColors(INACTIVE_ENVIRONMENT.color, INACTIVE_ENVIRONMENT.color, INACTIVE_ENVIRONMENT.color)
                    edge.container.children.filter(KPolygon).head.setFBColors(INACTIVE_ENVIRONMENT.color, INACTIVE_ENVIRONMENT.color, INACTIVE_ENVIRONMENT.color)
                } else {
                    edge.container.setFBColors(ACTIVE_ENVIRONMENT.color, ACTIVE_ENVIRONMENT.color, ACTIVE_ENVIRONMENT.color)
                    edge.container.children.filter(KPolygon).head.setFBColors(ACTIVE_ENVIRONMENT.color, ACTIVE_ENVIRONMENT.color, ACTIVE_ENVIRONMENT.color)
                }
            }
        } 
    }   
    
    static private def void resetProcessorNode(KNode processorNode, KNode node) {
        val nodeIdMap = processorNode.createNodeIdMap
        
        NODE_ACTIVITY_STATUS.getContainer(nodeIdMap)?.setFBColor(BUSY)
        for(i : 0..NODE_PROGRESS.length-1) {
            NODE_PROGRESS.get(i).getContainer(nodeIdMap)?.setFBAColor(PROGRESSBAR, 0)
        }
    } 
    
    static def void addNewProcessor(CompilationChanged notification, KNode node, CompilerView view) {
        val compilationContext = notification.compilationContext
        val newNode = processorSynthesis.transform(notification.processorEntry)
        
        node.children += newNode
        
        val parent = notification.processorEntry.eContainer
        if (parent instanceof ProcessorGroup) {
            val pos = parent.processors.indexOf(notification.processorEntry)
            val predecessorIndex = if (pos > 0) pos - 1 else pos
            if (predecessorIndex < pos) {
                val predecessorEntry = parent.processors.get(predecessorIndex)
                var predecessorNode = node.findNode(predecessorEntry.uniqueProcessorId)
                if (predecessorNode === null) {
                    val originalProcessorReference = compilationContext.getOriginalProcessorEntry(predecessorEntry)
                    if (originalProcessorReference !== null) {
                        predecessorNode = node.findNode(originalProcessorReference.uniqueProcessorId)
                    }
                }
                val edge = kEdgeExtensions.createEdge 
                edge.source = predecessorNode
                edge.target = node.findNode(notification.processorEntry.uniqueProcessorId)
                
                renderingFactory.createKPolyline() => [
                    edge.data += it
                    kRenderingExtensions.setLineWidth(it, 0.5f)
                    kRenderingExtensions.setForeground(it, ACTIVE_ENVIRONMENT.color)
                    internalAddArrowDecorator(it, true)
                ]
            }
        }
        
    }
    
    static def void updateProcessor(AbstractProcessorNotification processorNotification, KNode node, CompilerView view) {
        val compilationContext = processorNotification.compilationContext
        val processorReference = processorNotification.processorReference
        val processorInstance = processorNotification.processorInstance
        val originalProcessorReference = compilationContext.getOriginalProcessorEntry(processorReference)
        var KNode processorNodeCandidate = null
        if (originalProcessorReference === null) {
            processorNodeCandidate = node.findNode(processorReference.uniqueProcessorId)
        } else {
            processorNodeCandidate = node.findNode(originalProcessorReference.uniqueProcessorId) 
        }
        val processorNode = processorNodeCandidate
        if (processorNode === null) {
            System.err.println("There was an update notification for an non-existing processor system (" + originalProcessorReference.uniqueProcessorId + 
                "). This should not happen. I'm sorry.")
            return
        }
        val nodeIdMap = processorNode.createNodeIdMap
        
        switch(processorInstance.environment.status) {
            case ERRORS: NODE_ACTIVITY_STATUS.getContainer(nodeIdMap)?.setFBColor(ERROR)
            case WARNINGS: NODE_ACTIVITY_STATUS.getContainer(nodeIdMap)?.setFBColor(WARNING)
            default: {
            }
        }
        
        val pTime = processorInstance.environment.getProperty(PTIME)
        var envText = "pTime: " + pTime + "ms"
        val mMetric = processorInstance.environment.getProperty(METRIC)
        if (mMetric !== null) envText += "\nmMetric: " + String.format(Locale.US, "%.3f", mMetric as Double) 
        NODE_ENVIRONMENT.findNode(nodeIdMap)?.setLabel(envText)
        
        var intermediateModelCounter = 0
        
        
// This sometimes causes a Klighd exception: Exception in thread "pool-2-thread-30" java.lang.NullPointerException
//    at de.cau.cs.kieler.klighd.internal.macrolayout.KlighdLayoutConfigurationStore.getContainer(KlighdLayoutConfigurationStore.java:396)
        val processorBodyNode = NODE_PROCESSOR_BODY.findNode(nodeIdMap)
        if (processorBodyNode !== null) {
            processorBodyNode.container.removeAllActions
            processorBodyNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
            processorBodyNode.setProperty(INTERMEDIATE_DATA, 
                new IntermediateData(processorInstance, processorNotification.compilationContext, 
                    processorInstance.targetModel, view, intermediateModelCounter++, processorBodyNode
                ))
        }
        // Does not work on text.
//        val processorNameNode = NODE_NAME.findNode(nodeIdMap)
//        if (processorNameNode !== null) {
//            processorNameNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
//            processorNameNode.setProperty(INTERMEDIATE_DATA, 
//                new IntermediateData(processorInstance, processorNotification.compilationContext, processorInstance.targetModel, view))
//        }
        

        val intermediateRootNode = NODE_INTERMEDIATE.findNode(nodeIdMap)
        if (intermediateRootNode !== null) {
            val intermediateKGT = intermediateNode
            intermediateRootNode.children.clear
            val intermediatePosXInc = intermediateKGT.width - 0.5f
            var intermediatePosX = 0.0f
            
            if (processorInstance.environment.getProperty(DEBUG_ENVIRONMENT_MODELS)) {
                val environmentNode = intermediateKGT.copy
                environmentNode.xpos = intermediatePosX
                environmentNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                intermediateRootNode.children += environmentNode 
                environmentNode.setProperty(INTERMEDIATE_DATA, 
                    new IntermediateData(processorInstance, 
                        processorNotification.compilationContext, 
                        new EnvironmentPair(processorInstance.sourceEnvironment, processorInstance.sourceEnvironment),
                        view, intermediateModelCounter++, environmentNode
                    ))
                intermediatePosX += intermediatePosXInc          
                environmentNode.container.setFBColor(ENVIRONMENT_MODEL)                 
            }            
            
            // Test for snapshots
            var Object lastModel = null
            val snapshots = processorInstance.environment.getProperty(SNAPSHOTS) as Snapshots
            if (snapshots !== null) {
                for(snapshot : snapshots) {
                    val intermediateNode = intermediateKGT.copy
                    intermediateNode.xpos = intermediatePosX
                    intermediateNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                    intermediateRootNode.children += intermediateNode
                    intermediateNode.setProperty(INTERMEDIATE_DATA, 
                        new IntermediateData(processorInstance, processorNotification.compilationContext, snapshot.object, 
                            view, intermediateModelCounter++, intermediateNode
                        ))
                    intermediatePosX += intermediatePosXInc
                    lastModel = snapshot.object
                }
            }
            
            // Final result
            if (lastModel !== processorInstance.targetModel) {
                val finalResultNode = intermediateKGT.copy
                finalResultNode.xpos = intermediatePosX
                finalResultNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                intermediateRootNode.children += finalResultNode 
                finalResultNode.setProperty(INTERMEDIATE_DATA, 
                    new IntermediateData(processorInstance, processorNotification.compilationContext, 
                        processorInstance.targetModel, view, intermediateModelCounter++, finalResultNode
                    ))
                intermediatePosX += intermediatePosXInc          
                finalResultNode.container.setFBColor(INTERMEDIATE_FINAL_RESULT)
            }        
    
    
            if (processorInstance.environment.getProperty(DEBUG_ENVIRONMENT_MODELS)) {
                val environmentNode = intermediateKGT.copy
                environmentNode.xpos = intermediatePosX
                environmentNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                intermediateRootNode.children += environmentNode 
                environmentNode.setProperty(INTERMEDIATE_DATA, 
                    new IntermediateData(processorInstance, 
                        processorNotification.compilationContext, 
                        new EnvironmentPair(processorInstance.sourceEnvironment, processorInstance.environment), 
                        view, intermediateModelCounter++, environmentNode
                    ))
                intermediatePosX += intermediatePosXInc          
                environmentNode.container.setFBColor(ENVIRONMENT_MODEL)                 
            }            
    
    
            val infos = processorInstance.environment.getProperty(INFOS)
            if (infos.size > 0) {
                for (infoKey : infos.keySet) {
                    val infoNode = intermediateKGT.copy
                    infoNode.xpos = intermediatePosX
                    infoNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                    intermediateRootNode.children += infoNode 
                    
                    val model = if (infoKey === null) processorInstance.targetModel else infoKey
                    if (model instanceof EObject) {
                        val morModel = new MessageObjectListPair(infos.get(infoKey).fillUndefinedColors(INFO), 
                            if (infoKey === null) model else infoKey)
                        infoNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, infoNode
                            ))
                    } else {
                        val morModel = new MessageObjectListPair(infos.get(infoKey).fillUndefinedColors(INFO), 
                            if (infoKey === null) model else infoKey)
                        infoNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, infoNode
                            ))
                    }
                        
                    infoNode.container.setFBColor(INFO)
                    intermediatePosX += intermediatePosXInc
                }
            }            
                
            val warnings = processorInstance.environment.getProperty(WARNINGS)
            if (warnings.size > 0) {
                for (warningKey : warnings.keySet) {
                    val warningNode = intermediateKGT.copy
                    warningNode.xpos = intermediatePosX
                    warningNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                    intermediateRootNode.children += warningNode 
                    
                    val model = if (warningKey === null) processorInstance.targetModel else warningKey
                    if (model instanceof EObject) {
                        val morModel = new MessageObjectListPair(warnings.get(warningKey).fillUndefinedColors(WARNING), 
                            if (warningKey === null) model else warningKey)
                        warningNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, warningNode
                            ))
                    } else {
                        val morModel = new MessageObjectListPair(warnings.get(warningKey).fillUndefinedColors(WARNING), 
                            if (warningKey === null) model else warningKey)
                        warningNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, warningNode
                            ))
                    }
                        
                    warningNode.container.setFBColor(WARNING)
                    intermediatePosX += intermediatePosXInc
                }
            }       
            
            val errors = processorInstance.environment.getProperty(ERRORS)
            if (errors.size > 0) {
                for (errorKey : errors.keySet) {
                    val errorNode = intermediateKGT.copy
                    errorNode.xpos = intermediatePosX
                    errorNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                    intermediateRootNode.children += errorNode 
                    
                    val model = processorInstance.targetModel
                    if (model instanceof EObject) {
                        val morModel = new MessageObjectListPair(errors.get(errorKey).fillUndefinedColors(ERROR), 
                            if (errorKey === null) model else errorKey)
                        errorNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, errorNode
                            ))
                    } else {
                        val morModel = new MessageObjectListPair(errors.get(errorKey).fillUndefinedColors(ERROR), 
                            if (errorKey === null) model else errorKey)
                        errorNode.setProperty(INTERMEDIATE_DATA, 
                            new IntermediateData(processorInstance, processorNotification.compilationContext, morModel, 
                                view, intermediateModelCounter++, errorNode
                            ))
                    }
                        
                    errorNode.container.setFBColor(ERROR)
                    intermediatePosX += intermediatePosXInc
                }
            }
            
            val logs = processorInstance.environment.getProperty(LOGS)
            if (logs !== null && logs.files.size > 0) {
                val logNode = intermediateKGT.copy
                logNode.xpos = intermediatePosX
                logNode.container.addAction(Trigger::SINGLECLICK, SelectIntermediateAction.ID)
                logNode.container.setFBColor(LOG)
                intermediateRootNode.children += logNode
                 
                logNode.setProperty(INTERMEDIATE_DATA, 
                    new IntermediateData(processorInstance, processorNotification.compilationContext, logs, 
                        view, intermediateModelCounter++, logNode
                    ))
                intermediatePosX += intermediatePosXInc
            }
        }               
        
        if (processorNotification instanceof ProcessorProgress) {
            updateProgressbar((processorNotification.progress * 100) as int, nodeIdMap)
        } else if (processorNotification instanceof ProcessorFinished) {
            updateProgressbar(100, nodeIdMap)
            if (processorInstance.environment.status == ProcessorStatus.OK) {
                NODE_ACTIVITY_STATUS.getContainer(nodeIdMap)?.setFBColor(OK)
            }        
        }
    }
    
    static def void updateProgressbar(int progress, Map<String, KNode> nodeIdMap) {
        var s = "" + progress + " =  "
        for(i : 0..NODE_PROGRESS.length - 1) {
            val p = (range(progress, 20 * i, 20 * i + 20) - i * 20) * 5    
            NODE_PROGRESS.get(i).getContainer(nodeIdMap)?.setBAlpha(255 * p / 100)
            s += " p" + i + ":" + p
        }
    }
    
    
    static def void postUpdateProcessors(AbstractContextNotification contextNotification, KNode node, CompilerView view) {
        val postUpdateCollector = new PostUpdateDoubleCollector(METRIC)
        val allProcessors = contextNotification.compilationContext.processorMap.keySet
        
        // Gather data.       
        for(processor : allProcessors) {
            val processorNode = node.findNode(processor.uniqueProcessorId)    
            if (processorNode === null) {
                // This can happen because metrics are also listed in the processor map.
            } else {
                val compilationUnit = contextNotification.compilationContext.getProcessorInstance(processor)
                postUpdateCollector.addProcessor(compilationUnit)
            }
        }
        
        // Update view.
        for(processor : allProcessors) {
            val processorNode = node.findNode(processor.uniqueProcessorId)    
            if (processorNode === null) {
                // This can happen because metrics are also listed in the processor map.
            } else {
                val compilationUnit = contextNotification.compilationContext.getProcessorInstance(processor)
                val perc = postUpdateCollector.getPercentile(compilationUnit)
                processorNode.setProperty(CoreOptions.SCALE_FACTOR, perc)
            }
        }
        
        LightDiagramServices.layoutDiagram(new LightDiagramLayoutConfig(view))
    }
    
    
    static def List<Object> retrieveIntermediateModel(KNode node, CompilerView view, Object model, IntermediateSelection selection, IEditorPart editor) {
        retrieveIntermediateModel(node, view, model, selection, editor, true)
    }
    
    package static def List<Object> retrieveIntermediateModel(KNode node, CompilerView view, Object model, 
        IntermediateSelection selection, IEditorPart editor, boolean scheduleUIJob
    ) {
        val processorNodes = <Pair<KNode, Integer>> newArrayList
        for (s : selection.entries) {
            if (s.processor === null) {
                processorNodes += new Pair<KNode, Integer>(node.findNode(NODE_SOURCE), s.intermediateIndex)
            } else {
                val processorReference = s.processor.processorReference
                processorNodes += new Pair<KNode, Integer>(
                    node.eAllContents.filter(KNode).filter[ getData(KIdentifier)?.id.startsWith(processorReference.id) ]?.head,
                    s.intermediateIndex)
            }
        }
        
        val intermediateData = <IntermediateData> newArrayList
        for (p : processorNodes.filter[ it.key !== null] ) {
            val iD = p.key.eAllContents.filter(KNode).filter[ 
                val iData = getProperty(INTERMEDIATE_DATA)
                return (iData !== null) && (iData.intermediateIndex <= p.value)
            ].map[ getProperty(INTERMEDIATE_DATA) ].toIterable.sortBy[ -intermediateIndex ].head
            
            if (iD !== null) 
                intermediateData += iD
        }
        
        if (!intermediateData.empty) {
            if (scheduleUIJob) {
                new Thread(new DelayedSelectionUpdate(node, view, model, selection, editor)).start
            } else {
                val modelList = <Object> newArrayList
                if (intermediateData.size == 1) {
                    view.viewer.resetSelectionTo(intermediateData.head.parentNode)
                    intermediateData.head.parentNode.containers.forEach[ 
                        setProperty(KlighdInternalProperties.SELECTED, true)
                    ]
                    modelList += intermediateData.head.model
                } else {
                    val selectionIter = intermediateData.map[parentNode]
                    view.viewer.resetSelectionToDiagramElements(selectionIter)
                    for (iM : intermediateData) {
                        iM.parentNode.containers.forEach[ 
                            setProperty(KlighdInternalProperties.SELECTED, true)
                        ]
                        modelList += iM.model
                    }
                }
                return modelList
            }
        }
         
        return <Object> newArrayList => [ it += model ]  
    }
    
    
    
    static def void setFrameErrorColor(KNode node) {
        val rect = node.getData(KContainerRendering) as KContainerRendering
        rect.setFBColor(ERROR)
    }
    
    static def void setLabel(KNode node, String string) {
        node.labels.head.text = string
    }    
    
    static def void setFBColor(KContainerRendering container, ColorSystem colorSystem) {
        container.setFBColors(colorSystem.foreground.color, colorSystem.background.color, colorSystem.backgroundTarget.color)
    }
    
    static def void setFBColorViaExtension(KContainerRendering container, ColorSystem colorSystem) {
        container.setFBColorsViaExtension(colorSystem.foreground.color, colorSystem.background.color, colorSystem.backgroundTarget.color)
    }

    static def void setFBAColor(KContainerRendering container, ColorSystem colorSystem, int alpha) {
        container.setFBAColors(colorSystem.foreground.color, colorSystem.background.color, colorSystem.backgroundTarget.color, alpha)
    }
    
    static def void setBAlpha(KContainerRendering container, int alpha) {
        container.styles.filter(KColoring).forEach[ c |
            if (c instanceof KBackground) { 
                c.alpha = alpha
                c.targetAlpha = alpha
            }
        ]
    }
    

    
    /**
     * Private because KColors are not copied.
     */
    private static def void setFBColors(KContainerRendering container, KColor foreground, KColor background, KColor backgroundTarget) {
        container.styles.filter(KColoring).forEach[ c |
            if (c instanceof KForeground) c.color = foreground
            if (c instanceof KBackground) { 
                c.color = background
                c.targetColor = backgroundTarget
            }
        ]
    }
    
    private static def void setFBColorsViaExtension(KContainerRendering container, KColor foreground, KColor background, KColor backgroundTarget) {
        kRenderingExtensions.setForeground(container, foreground)
        kRenderingExtensions.setBackgroundGradient(container, background, backgroundTarget, 0)
    }
    
    private static def void setFBAColors(KContainerRendering container, KColor foreground, KColor background, KColor backgroundTarget, int alpha) {
        container.styles.filter(KColoring).forEach[ c |
            if (c instanceof KForeground) c.color = foreground
            if (c instanceof KBackground) { 
                c.color = background
                c.targetColor = backgroundTarget
                c.alpha = alpha
                c.targetAlpha = alpha
            }
        ]
    }
    
    
    static def getContainer(String id, Map<String, KNode> nodeIdMap) {
        nodeIdMap.findNode(id)?.getContainer
    }
    
    static def getContainer(KNode node) {
        node.getData(KContainerRendering) as KContainerRendering
    }
    
    static def getContainers(KNode node) {
        node.data.filter(KContainerRendering) 
    }

    static def getContainer(KEdge edge) {
        edge.getData(KContainerRendering) as KContainerRendering
    }
    
    static def Map<String, KNode> createNodeIdMap(KNode node) {
        <String, KNode> newHashMap => [ map |
            node.eAllContents.filter(KNode).forEach[
                val identifier = getData(KIdentifier)
                map.put(identifier.id, it)
            ]
        ]
    }
    
    static def KNode findNode(Map<String, KNode> idMap, String id) {
        idMap.get(id)
    }
    
    static def KNode findNode(String id, Map<String, KNode> idMap) {
        idMap.get(id)
    }
    
    static def KNode findNode(KNode node, String id) {
        node.eAllContents.filter(KNode).filter[ id.equals(getData(KIdentifier)?.id) ]?.head
    }
        
    static def findAllNodes(KNode node, String id) {
        node.eAllContents.filter(KNode).filter[ id.equals(getData(KIdentifier)?.id) ].toList
    }
    
    static def getId(KNode node) {
        node.eContents.filter(KIdentifier).head.id
    }    
    
    static def KLabel getLabel(KNode node) {
        node.eContents.filter(KLabel).head
    }
    
    static def KShapeLayout getShapeLayout(KNode node) {
        node.eContents.filter(KShapeLayout).head
    }
    
    private static def int range(int value, int min, int max) {
        if (value < min) return min
        else if (value > max) return max
        else return value
    }
    
    static private def <T extends KRendering> T addAction(T rendering, Trigger trigger, String actionId) {
        rendering.actions += KRenderingFactory.eINSTANCE.createKAction() => [
            it.trigger = trigger;
            it.actionId = actionId;
        ];
        return rendering;
    }
    
    static private def <T extends KRendering> void removeAllActions(T rendering) {
            rendering.actions.clear
    }    
    
    
    static private def KRendering internalAddArrowDecorator(KPolyline pl, boolean head) {
        kRenderingExtensions.setLineCap(pl, LineCap::CAP_FLAT)
        return pl.drawArrow => [
            it.placementData = renderingFactory.createKDecoratorPlacementData => [
                it.rotateWithLine = true;
                it.relative = if (head) 1f else 0f;
                it.absolute = if (head) -2f else 2f;
                it.width = 6;
                it.height = 4;
                it.setXOffset(if (head) -4f else 6f); // chsch: used the regular way here and below, as the alias 
                it.setYOffset(if (head) -2f else 3f); //  name translation convention changed from Xtext 2.3 to 2.4.
            ];
            if (!head) kRenderingExtensions.setRotation(it, 180f)
        ];
    }   
    
    static private def <T extends KRendering> T addChild(KContainerRendering parent, T child) {
        return child => [
            parent.children.add(it);
        ];
    }      
    
    static private def KPolygon drawArrow(KContainerRendering cr) {
        return renderingFactory.createKPolygon => [
            kRenderingExtensions.setLineJoin(
                kRenderingExtensions.setBackground(cr.addChild(it).withCopyOf(kRenderingExtensions.getLineWidth(cr)).withCopyOf(kRenderingExtensions.getForeground(cr)), 
                    kRenderingExtensions.getForeground(cr)
                ),
                LineJoin.JOIN_ROUND
            )
            it.points += kRenderingExtensions.createKPosition(PositionReferenceX::LEFT, 0, 0, PositionReferenceY::TOP, 0, 0);
            it.points += kRenderingExtensions.createKPosition(PositionReferenceX::LEFT, 0, 0.66f, PositionReferenceY::TOP, 0, 0.5f);
            it.points += kRenderingExtensions.createKPosition(PositionReferenceX::LEFT, 0, 0, PositionReferenceY::BOTTOM, 0, 0);
            it.points += kRenderingExtensions.createKPosition(PositionReferenceX::RIGHT, 0, 0, PositionReferenceY::BOTTOM, 0, 0.5f);    
       ]
    }
    
    static private def <T extends KRendering> T withCopyOf(T rendering, KStyle style) {
        rendering.styles += style.copy;
        return rendering;
    }

}