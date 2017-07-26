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
package de.cau.cs.kieler.simulation.handlers

import de.cau.cs.kieler.prom.ModelImporter
import de.cau.cs.kieler.prom.PromPlugin
import de.cau.cs.kieler.simulation.core.DataHandler
import de.cau.cs.kieler.simulation.core.DataPool
import de.cau.cs.kieler.simulation.core.SimulationManager
import org.eclipse.core.resources.IFile
import org.eclipse.core.runtime.IPath

/**
 * Default implementation for a data handler.
 * 
 * @author aas
 *
 */
abstract class DefaultDataHandler implements DataHandler {
    
    override read(DataPool pool) {
    }
    
    override write(DataPool pool) {
    }
    
    override stop() {
    }
    
    protected def IFile getFile(IPath path) {
        var IFile file = PromPlugin.findFile(path.toOSString)
        if (file == null || !file.exists) {
            val simMan = SimulationManager.instance
            if(simMan != null && simMan.usedConfiguration != null) {
                val configurationFile = ModelImporter.toPlatformResource(simMan.usedConfiguration.eResource) as IFile
                file = configurationFile?.project.getFile(path)
            }
        }
        return file
    }
}