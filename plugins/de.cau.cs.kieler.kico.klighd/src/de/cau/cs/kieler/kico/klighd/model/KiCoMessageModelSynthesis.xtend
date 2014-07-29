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
package de.cau.cs.kieler.kico.klighd.model

import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.extensions.KColorExtensions
import de.cau.cs.kieler.core.krendering.extensions.KContainerRenderingExtensions
import de.cau.cs.kieler.core.krendering.extensions.KEdgeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KLabelExtensions
import de.cau.cs.kieler.core.krendering.extensions.KNodeExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPolylineExtensions
import de.cau.cs.kieler.core.krendering.extensions.KPortExtensions
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.syntheses.AbstractDiagramSynthesis
import javax.inject.Inject

import static extension de.cau.cs.kieler.klighd.syntheses.DiagramSyntheses.*

/**
 * Diagram synthesis of a KiCoMessageModel.
 * 
 * @author als
 *
 */
class KiCoMessageModelSynthesis extends AbstractDiagramSynthesis<KiCoMessageModel> {

    @Inject
    extension KNodeExtensions

    @Inject
    extension KEdgeExtensions

    @Inject
    extension KPortExtensions

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

    // -------------------------------------------------------------------------
    // The Main entry transform function
    override KNode transform(KiCoMessageModel model) {
        val rootNode = createNode();
        rootNode.children += createNode(model) => [
            it.addRectangle() => [
                it.invisible = true;
                it.setGridPlacement(1);
                //upper part is icon
                if (model.hasIcon()) {
                    it.addRectangle => [
                        it.invisible = true;
                        //set minimal size
                        it.setGridPlacementData(model.iconSize, model.iconSize);
                        it.addImage(model.iconPlugin, model.iconPath).setPointPlacementData(LEFT, 0, 0.5f, TOP, 0, 0.5f,
                            H_CENTRAL, V_CENTRAL, 0, 0, model.iconSize, model.iconSize).addRectangularClip;
                    ]
                }
                //lower part is message
                it.addRoundedRectangle(7, 7) => [
                    it.setGridPlacement(1);
                    it.lineWidth = 2;
                    //title
                    if (model.title != null) {
                        it.addText(model.title) => [
                            it.fontSize = 12;
                            it.setFontBold = true;
                            it.setGridPlacementData().from(LEFT, 8, 0, TOP, 8, 0).to(RIGHT, 8, 0, BOTTOM, 4, 0);
                            it.suppressSelectability;
                        ]
                    }
                    //message
                    if (model.message != null) {
                        it.addText(model.message) => [
                            it.fontSize = 12;
                            if (model.title != null) {
                                it.setGridPlacementData().from(LEFT, 8, 0, TOP, 0, 0).to(RIGHT, 8, 0, BOTTOM, 4, 0);
                            } else {
                                it.setGridPlacementData().from(LEFT, 8, 0, TOP, 8, 0).to(RIGHT, 8, 0, BOTTOM, 8, 0);
                            }
                            it.suppressSelectability;
                        ]
                    }
                ]
            ]
        ]
        return rootNode;
    }
}
