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

import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import com.google.inject.Inject
import de.cau.cs.kieler.klighd.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.klighd.util.KlighdSynthesisProperties
import de.cau.cs.kieler.klighd.krendering.SimpleUpdateStrategy
import de.cau.cs.kieler.klighd.LightDiagramServices
import de.cau.cs.kieler.kicool.ui.synthesis.updates.MessageObjectReferencesManager
import de.cau.cs.kieler.kicool.ui.klighd.KiCoDiagramViewProperties
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions

/**
 * Generic synthesis for strings.
 * I use this for displaying messages in the model view.
 * 
 * @author ssm
 * @kieler.design 2016-10-20 proposed 
 * @kieler.rating 2016-10-20 proposed yellow
 */
@ViewSynthesisShared
class MessageObjectListSynthesis extends AbstractDiagramSynthesis<MessageObjectListPair> {
    
    @Inject extension KNodeExtensions
    @Inject extension KRenderingExtensions
    @Inject extension MessageObjectReferencesManager
   
    override transform(MessageObjectListPair model) {
        val rootNode = model.createNode
        
        val compilationContext = this.usedContext.getProperty(KiCoDiagramViewProperties.COMPILATION_CONTEXT)
        val properties = new KlighdSynthesisProperties
        
        properties.setProperty(KlighdSynthesisProperties.REQUESTED_UPDATE_STRATEGY, SimpleUpdateStrategy.ID)
        properties.setProperty(KiCoDiagramViewProperties.COMPILATION_CONTEXT, compilationContext)
        
        val subDiagramViewContext = LightDiagramServices::translateModel2(model.value, usedContext, properties)
        usedContext.addChildViewContext(subDiagramViewContext)
        
        val subDiagramNode = subDiagramViewContext.viewModel
        subDiagramNode.addRectangle => [invisible = true]
        
        rootNode.children.add(subDiagramNode)
        annotateModelNodes(model.key, rootNode)
        
        rootNode
    }
    
}
