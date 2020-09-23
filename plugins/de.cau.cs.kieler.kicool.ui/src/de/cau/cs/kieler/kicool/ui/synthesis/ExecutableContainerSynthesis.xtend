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
import de.cau.cs.kieler.kicool.compilation.ExecutableContainer
import de.cau.cs.kieler.kicool.ui.synthesis.actions.StartExecutableAction
import de.cau.cs.kieler.klighd.ide.model.MessageModel
import de.cau.cs.kieler.klighd.ide.syntheses.MessageModelSynthesis
import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.klighd.krendering.KImage
import de.cau.cs.kieler.klighd.krendering.ViewSynthesisShared
import de.cau.cs.kieler.klighd.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*
import de.cau.cs.kieler.kicool.ide.KiCoolIdeModule

/**
 * Generic synthesis for ExecutableContainer with strart button.
 * 
 * @author als
 * @kieler.design 2016-10-20 proposed 
 * @kieler.rating 2016-10-20 proposed yellow
 */
@ViewSynthesisShared
class ExecutableContainerSynthesis extends AbstractDiagramSynthesis<ExecutableContainer> {

    @Inject
    extension KRenderingExtensions

    @Inject
    extension MessageModelSynthesis

    // -------------------------------------------------------------------------
    // Constants
    public static val String ID = "de.cau.cs.kieler.kicool.ui.synthesis.ExecutableContainer";

    // -------------------------------------------------------------------------
    // Synthesis
    override KNode transform(ExecutableContainer model) {
        // create basic representation with message model synthesis
        val rootNode = (new MessageModel("Executable", model.file.toPath.fileName.toString, KiCoolIdeModule.PLUGIN_ID, "icons/play-button.png", 150)).transform
        // Add action
        if (rootNode !== null && !rootNode.children.empty) {
            rootNode.eAllContents.filter(KNode).forEach[it.suppressSelectability]
            val icon = rootNode.eAllContents.filter(KImage).head
            if (icon !== null) {
                icon.addSingleClickAction(StartExecutableAction.ID);
                icon.addDoubleClickAction(StartExecutableAction.ID);
            }
        }
        return rootNode;
    }
    
}