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
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import de.cau.cs.kieler.klighd.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.kicool.System
import org.eclipse.elk.core.options.CoreOptions
import org.eclipse.elk.core.options.EdgeRouting
import org.eclipse.elk.core.options.Direction
import org.eclipse.elk.alg.layered.p4nodes.NodePlacementStrategy
import org.eclipse.elk.alg.layered.properties.LayeredOptions
import org.osgi.framework.Bundle
import org.eclipse.core.runtime.Platform
import java.net.URL
import org.eclipse.core.runtime.FileLocator
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.elk.core.math.ElkPadding
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.kgraph.util.KGraphUtil
import org.eclipse.elk.alg.layered.p2layers.LayeringStrategy
import org.eclipse.elk.alg.layered.properties.FixedAlignment
import de.cau.cs.kieler.klighd.KlighdOptions
import de.cau.cs.kieler.kicool.ui.synthesis.styles.SkinSelector

/**
 * Main diagram synthesis for KiCool.
 * 
 * @author ssm
 * @kieler.design 2016-10-20 proposed 
 * @kieler.rating 2016-10-20 proposed yellow
 */
@ViewSynthesisShared
class KiCoolSynthesis extends AbstractDiagramSynthesis<System> {

    @Inject extension KNodeExtensions
    @Inject extension ProcessorSynthesis
    @Inject extension SourceSynthesis
    
    override transform(System model) {
        val rootNode = model.createNode
        
        rootNode.setLayoutOption(CoreOptions::ALGORITHM, "org.eclipse.elk.layered");
        rootNode.setLayoutOption(CoreOptions::EDGE_ROUTING, EdgeRouting::ORTHOGONAL);
        rootNode.setLayoutOption(CoreOptions::DIRECTION, Direction::RIGHT);
        rootNode.setLayoutOption(LayeredOptions::NODE_PLACEMENT_STRATEGY, NodePlacementStrategy::BRANDES_KOEPF)
        rootNode.setLayoutOption(LayeredOptions::NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment.BALANCED)
        rootNode.setLayoutOption(CoreOptions::SPACING_NODE_NODE, 10d);
        rootNode.setLayoutOption(LayeredOptions::SPACING_NODE_NODE_BETWEEN_LAYERS, 12d);
        rootNode.setLayoutOption(CoreOptions::PADDING, new ElkPadding(8d));
        rootNode.setLayoutOption(LayeredOptions::LAYERING_STRATEGY, LayeringStrategy::LONGEST_PATH)
        rootNode.setLayoutOption(LayeredOptions::SAUSAGE_FOLDING, true)

        // Workaround until we use the next version of ELK        
        // val size = usedContext.getProperty(KlighdOptions.VIEWER).getControl.getSize
//      val aspectRatio = size.x as double / size.y * 2
        rootNode.setLayoutOption(LayeredOptions::ASPECT_RATIO, 10.0) 
        
        val source = sourceNode
        val processorNodes = model.processors.transform
        val nodes = processorNodes.head.children
        source.sourceConnect(nodes.head)
        nodes += source

        rootNode.children += nodes 
        
        rootNode
    }
    
    /**
     * Load a KGT from a bundle.
     */
    def static getKGTFromBundle(String bundleId, String resourceLocation) {
//        val Bundle bundle = Platform.getBundle(bundleId)
//        val URL fileURL = bundle.getEntry(SkinSelector.skinPrefix + resourceLocation)
//        val absFile = FileLocator.resolve(fileURL)
//        val newURI = URI.createFileURI(absFile.getFile)
        val newURI = URI.createPlatformPluginURI("/" + bundleId + "/" + SkinSelector.skinPrefix + resourceLocation, true)
        val provider = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(newURI)
        val newResourceSet = provider.get(XtextResourceSet)
        newResourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.FALSE)
        val res = newResourceSet.createResource(newURI)
        try {
            res.load(newResourceSet.loadOptions)
            val node = (res.getContents().get(0) as KNode).children.head
            return node
        } catch (Exception e) {
            e.printStackTrace
        }         
        return KGraphUtil::createInitializedNode
    }    
    
}