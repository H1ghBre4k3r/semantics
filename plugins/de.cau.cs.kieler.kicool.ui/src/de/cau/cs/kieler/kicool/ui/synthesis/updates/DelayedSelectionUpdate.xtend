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
package de.cau.cs.kieler.kicool.ui.synthesis.updates

import de.cau.cs.kieler.klighd.kgraph.KNode
import de.cau.cs.kieler.kicool.ui.view.CompilerView
import de.cau.cs.kieler.kicool.ui.synthesis.actions.IntermediateSelection
import org.eclipse.ui.progress.UIJob
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import static extension de.cau.cs.kieler.kicool.ui.synthesis.updates.ProcessorDataManager.retrieveIntermediateModel

/**
 * Class that runs a selection setting in the UI thread after a short delay. 
 * This is necessary, because Klighd will not recognize the user-set selection on newly created intermediate nodes in 
 * the compiler view. 
 * 
 * @author ssm
 * @kieler.design 2018-04-12 proposed 
 * @kieler.rating 2018-04-12 proposed yellow
 *
 */
class DelayedSelectionUpdate implements Runnable {
    
    static val UPDATE_DELAY = 100
    
    var KNode node
    var CompilerView view
    var Object model
    var IntermediateSelection intermediateSelection
    
    new(KNode node, CompilerView view, Object model, IntermediateSelection selection) {
        this.node = node
        this.view = view
        this.model = model
        this.intermediateSelection = selection
    }
    
    override run() {
        Thread.sleep(UPDATE_DELAY)
        new UIJob("Updating selection...") {
            override IStatus runInUIThread(IProgressMonitor monitor) {
                retrieveIntermediateModel(node, view, model, intermediateSelection, false)
                return Status.OK_STATUS;
            }
        }.schedule
    }
    
}