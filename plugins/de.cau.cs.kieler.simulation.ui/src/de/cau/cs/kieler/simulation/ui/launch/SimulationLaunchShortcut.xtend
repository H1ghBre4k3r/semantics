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
package de.cau.cs.kieler.simulation.ui.launch

import de.cau.cs.kieler.prom.ui.launch.PromLaunchShortcut
import de.cau.cs.kieler.simulation.launch.SimulationLaunchConfig
import de.cau.cs.kieler.simulation.ui.views.DataPoolView
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.ui.PlatformUI

/**
 * The launch shortcut to start a simulation from selected files.
 * 
 * @author aas
 *
 */
class SimulationLaunchShortcut extends PromLaunchShortcut {
    
    /**
     * {@inheritDoc}
     */
    override launch() {
        // Show data pool view
        if(DataPoolView.instance != null) {
            for(page : PlatformUI.getWorkbench().getActiveWorkbenchWindow().pages) {
                page.bringToTop(DataPoolView.instance);
            }
        }

        super.launch()
    }
    
    /**
     * {@inheritDoc}
     */
    override getNewLaunchConfigurationBaseName() {
        return files.map[it.name].join(", ")
    }
    
    /**
     * {@inheritDoc}
     */
    override protected initializeConfiguration(ILaunchConfigurationWorkingCopy config) {
        config.setAttribute(SimulationLaunchConfig.FILES_ATTR, files.map[it.fullPath.toOSString])
    }
    
    /**
     * {@inheritDoc}
     */
    override boolean isGoodMatch(ILaunchConfiguration configuration) {
        val loadedFiles = configuration.getAttribute(SimulationLaunchConfig.FILES_ATTR, #[])
        return loadedFiles == files.map[it.fullPath.toOSString]
    }
    
    /**
     * {@inheritDoc}
     */
    override protected getLaunchConfigurations() {
        return getLaunchConfigurations(SimulationLaunchConfig.LAUNCH_CONFIGURATION_TYPE_ID)
    }
    
    /**
     * {@inheritDoc}
     */
    override ILaunchConfiguration createNewConfiguration() {
        return createNewConfiguration(SimulationLaunchConfig.LAUNCH_CONFIGURATION_TYPE_ID)
    }
}