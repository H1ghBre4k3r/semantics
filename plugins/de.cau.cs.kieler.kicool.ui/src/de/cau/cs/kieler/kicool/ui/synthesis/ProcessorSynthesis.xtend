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

import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.Scopes
import de.cau.cs.kieler.kicool.ProcessorAlternativeGroup
import de.cau.cs.kieler.kicool.ProcessorEntry
import de.cau.cs.kieler.kicool.ProcessorGroup
import de.cau.cs.kieler.kicool.ProcessorReference
import de.cau.cs.kieler.kicool.ProcessorSystem
import de.cau.cs.kieler.kicool.System
import de.cau.cs.kieler.kicool.registration.KiCoolRegistration
import de.cau.cs.kieler.kicool.ui.synthesis.styles.ProcessorStyles
import de.cau.cs.kieler.klighd.kgraph.KEdge
import de.cau.cs.kieler.klighd.kgraph.KGraphFactory
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.KRoundedRectangle
import de.cau.cs.kieler.klighd.krendering.KText
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.klighd.krendering.extensions.KPortExtensions
import de.cau.cs.kieler.klighd.util.KlighdProperties
import java.util.List
import org.eclipse.elk.alg.layered.options.LayeredOptions
import org.eclipse.elk.core.math.ElkPadding
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.PortConstraints
import org.eclipse.elk.core.options.PortSide
import org.eclipse.elk.graph.properties.Property
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet

import static extension de.cau.cs.kieler.annotations.ide.klighd.CommonSynthesisUtil.*
import static extension de.cau.cs.kieler.kicool.ui.synthesis.updates.ProcessorDataManager.*
import static extension de.cau.cs.kieler.kicool.util.KiCoolUtils.uniqueProcessorId

/**
 * Main diagram synthesis for processors in KiCool.
 * 
 * @author ssm
 * @kieler.design 2016-10-20 proposed 
 * @kieler.rating 2016-10-20 proposed yellow
 */
@ViewSynthesisShared
class ProcessorSynthesis {
   
    @Inject Injector injector
    @Inject extension ProcessorStyles
    @Inject extension KNodeExtensions
    @Inject extension KEdgeExtensions
    @Inject extension KPortExtensions
    @Inject IResourceServiceProvider.Registry regXtext;
    
    public static val GROUP_NODE = new Property("de.cau.cs.kieler.kicool.ui.synthesis.groupNode", false)
    public static val COLLAPSED_ID = "collapsed"
    public static val EXPANDED_ID = "expanded" 
    
    @Accessors var boolean onOffButtons = false
    @Accessors var double defaultProcessGroupAspectRatio = 2.0
    
    new(){
        if (injector === null) {
            Guice.createInjector(new Module() {
                // This Module is created to satisfy ViewSynthesisShared scope of used synthesis-extensions
                override configure(Binder binder) {
                    binder.bindScope(ViewSynthesisShared, Scopes.SINGLETON);
                }
            }).injectMembers(this)
        }
    }
    
    def KNode processorNode() {
        createNode => [
            setDefaultProcessorSize()
            setProperty(CoreOptions::PADDING, new ElkPadding(4d))
            data += KGraphFactory.eINSTANCE.createKIdentifier
            addProcessorFigure(onOffButtons)
            
            // Attention: in other place the code always assumes fist port is incoming and last is outgoing
            setProperty(CoreOptions::PORT_CONSTRAINTS, PortConstraints.FIXED_SIDE)
            val in = createPort()
            in.setProperty(CoreOptions::PORT_SIDE, PortSide.WEST)
            ports += in
            val out = createPort()
            out.setProperty(CoreOptions::PORT_SIDE, PortSide.EAST)
            ports += out
        ]
    }
    
    def KNode groupNode(){
        createNode => [
            setDefaultProcessorSize()
            KiCoolSynthesis.configureBasicLayout(it)
            setProperty(LayeredOptions::ASPECT_RATIO, defaultProcessGroupAspectRatio)
            setProperty(CoreOptions::PADDING, new ElkPadding(0.0))
            setProperty(KlighdProperties::EXPAND, false)
            data += KGraphFactory.eINSTANCE.createKIdentifier
            addGroupFigure
            
            // Attention: in other place the code always assumes fist port is incoming and last is outgoing
            setProperty(CoreOptions::PORT_CONSTRAINTS, PortConstraints.FIXED_SIDE)
            val in = createPort()
            in.setProperty(CoreOptions::PORT_SIDE, PortSide.WEST)
            ports += in
            val out = createPort()
            out.setProperty(CoreOptions::PORT_SIDE, PortSide.EAST)
            ports += out
        ]
    }

    dispatch def List<KNode> transform(ProcessorReference processorReference) {
        val processorNode = processorNode
        val nodeId = processorReference.uniqueProcessorId
        processorNode.KID = nodeId
        processorReference.populateProcessorData(processorNode)        
        processorNode.adjustSize
        newArrayList(processorNode)
    }
    
    dispatch def List<KNode> transform(ProcessorGroup processorGroup) {
        val groupNode = groupNode()
        groupNode.setProperty(GROUP_NODE, true)
        
        val collapsedRendering = groupNode.eContents.filter(KRoundedRectangle).filter[ COLLAPSED_ID.equals(id) ].head
        val expandedRendering = groupNode.eContents.filter(KRoundedRectangle).filter[ EXPANDED_ID.equals(id) ].head
        
        var label = processorGroup.label
        if (processorGroup.eContainer instanceof System) label = (processorGroup.eContainer as System).label
        groupNode.KID = label
        collapsedRendering.eContents.filter(KText).head.text = label
        expandedRendering.eContents.filter(KText).head.text = label        
        
        groupNode.addLayoutParam(KlighdProperties::EXPAND, false)
        collapsedRendering.properties.put(KlighdProperties::COLLAPSED_RENDERING, true)
        expandedRendering.properties.put(KlighdProperties::EXPANDED_RENDERING, true)
        
        val List<KNode> lastNodes = newArrayList()
        val List<ProcessorEntry> lastEntries = newArrayList()
        val List<KEdge> edges = newArrayList() 
        for(it : processorGroup.processors) {
            val processorNodes = it.transform
            groupNode.children += processorNodes
            for(node : processorNodes) {
                for(lastNode : lastNodes) {
                    val edge = createEdge 
                    edge.source = lastNode
                    edge.sourcePort = lastNode.ports.last // Assuming the last port is right
                    edge.target = node
                    edge.targetPort = node.ports.head // Assuming the first port is left
                    edge.addConnectionFigure()
                    edges += edge
                }
            }
            
            if (it instanceof ProcessorReference) {
                for (le : lastEntries.filter(ProcessorReference)) {
                    val sourceId = le.id
                    val targetId = it.id
                    if (!KiCoolRegistration.checkProcessorCompatibility(sourceId, targetId)) {
                        for (lastNode : lastNodes) {
                            lastNode.setCompatibilityError
                        }
                        for (node : processorNodes) {            
                            node.setCompatibilityError
                        }
                        for (edge : edges) {
                            edge.getContainer.addIncompatibilityEdgeDecorator                         
                        }
                    }
                }
            }
            
            edges.clear
            lastNodes.clear
            lastNodes += processorNodes
            lastEntries.clear
            switch (it) {
                ProcessorReference: lastEntries += it
                ProcessorAlternativeGroup: lastEntries += it.processors
                ProcessorGroup: lastEntries += it.processors.last
                ProcessorSystem: lastEntries += it
            }
        }
        
        newArrayList(groupNode)
    }

    dispatch def List<KNode> transform(ProcessorAlternativeGroup processorAlternativeGroup) {
        val alternativeGroupNodes = <KNode> newArrayList()
     
        // Check if all groups only have one processor
        if (processorAlternativeGroup.processors.filter(ProcessorGroup).filter[ processors.size == 1].size == 
            processorAlternativeGroup.processors.size
        ) {
            processorAlternativeGroup.processors.filter(ProcessorGroup).forEach[
                alternativeGroupNodes += it.processors.head.transform
            ]
            
        } else {
     
            processorAlternativeGroup.processors.forEach[
                alternativeGroupNodes += it.transform
            ]
        
        }
     
        alternativeGroupNodes   
    }
    
    dispatch def List<KNode> transform(ProcessorSystem processorSystem) {
        val system = processorSystem.getProcessorSystem
        if (system !== null) {
//            val systemModel = system.transform
            val systemModel = system.processors.transform
            return newArrayList(systemModel) 
        }
        return newArrayList(createNode)
    }
    

    protected def System getProcessorSystem(ProcessorSystem processorSystem) {
        return KiCoolRegistration.getSystemById(processorSystem.id)
    }

    protected def System getProcessorSystemFromModelFile(ProcessorSystem processorSystem) {
        val sl = processorSystem.eResource.URI.segmentsList
        val nsl = sl.take(sl.length - 1).drop(1)
        processorSystem.id.getProcessorSystemFromModelFile(nsl.join("/"))
    }

    protected def System getProcessorSystemFromModelFile(String processorSystemId, String path) {
        val newURI = URI.createPlatformResourceURI(path + "/" + processorSystemId + '.kico', false)   
        val provider = regXtext.getResourceServiceProvider(newURI)
        val newResourceSet = provider.get(XtextResourceSet)
        newResourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.FALSE)
        val res = newResourceSet.createResource(newURI)
        try {
            res.load(newResourceSet.loadOptions)
            val node = (res.getContents().get(0) as System)
            return node
        } catch (Exception e) {
            val rSystem = KiCoolRegistration.getSystemById(processorSystemId)
            return rSystem
        }         
    }

    
}
