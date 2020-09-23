/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.ui.synthesis.actions

import de.cau.cs.kieler.kicool.ide.synthesis.actions.AbstractSelectNothing
import de.cau.cs.kieler.klighd.IAction

import static de.cau.cs.kieler.kicool.ui.synthesis.KNodeUIProperties.*

/**
 * Class that handles select nothing requests.
 * 
 * @author ssm
 * @kieler.design 2018-04-12 proposed 
 * @kieler.rating 2018-04-12 proposed yellow
 *
 */
class SelectNothing extends AbstractSelectNothing {
    
    override execute(ActionContext context) {
        val intermediateData = context.KNode.getProperty(INTERMEDIATE_DATA)
        if (intermediateData !== null && intermediateData.view !== null) {
            intermediateData.view.editPartSystemManager.intermediateSelection = null
        }
        
        IAction.ActionResult.createResult(false).dontAnimateLayout()
    }
    
}
