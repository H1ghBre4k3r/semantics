/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.cview.ecids

import com.google.inject.Inject
import de.cau.cs.kieler.cview.extensions.CViewAnalysisExtensions
import de.cau.cs.kieler.cview.hooks.IAnalysisHook
import de.cau.cs.kieler.cview.model.cViewModel.CViewModel
import de.cau.cs.kieler.cview.model.cViewModel.Component
import de.cau.cs.kieler.cview.model.cViewModel.Connection
import de.cau.cs.kieler.cview.model.extensions.CViewModelExtensions
import java.util.List
import java.util.HashMap

/**
 * @author cmot
 * 
 */
class ExampleAnalysis implements IAnalysisHook {

    @Inject extension CViewModelExtensions
    @Inject extension CViewAnalysisExtensions
    
    override getName() {
        return "ExampleAnalysis";
    }

    override getId() {
        return "de.cau.cs.kieler.cview.ecids.ExampleAnalysis";
    }
    
    override List<Connection> createConnections(Component component, CViewModel model) {
        val List<Connection> returnList = newArrayList()
        
        if (component.isDir && component.name.equals("Include")) {
            val otherComponents = model.findByName("SYC").filter[e | e.isDir]
            for (otherComponent : otherComponents) {
                val connection = component.connectTo(otherComponent)
                returnList.add(connection)
            }
        }
        return returnList
    }

    override initialize(CViewModel model) {
    }

    override wrapup(CViewModel model) {
    }
}