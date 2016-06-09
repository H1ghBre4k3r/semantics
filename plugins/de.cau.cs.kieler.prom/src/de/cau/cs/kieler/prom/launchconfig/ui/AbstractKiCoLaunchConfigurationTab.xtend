package de.cau.cs.kieler.prom.launchconfig.ui

import de.cau.cs.kieler.prom.common.KiCoLaunchData
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab

abstract class AbstractKiCoLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
    /**
     * The tab group that created this tab.
     */
    private KiCoLaunchConfigurationTabGroup tabGroup
    
    /**
     * Flag to indicate that changes in the UI should not be applied to the lanuch config.
     * This is needed in initializeFrom() to prevent applying the loaded values,
     * until all UI controls have been initialized.  
     */
    protected boolean doNotApplyUIChanges
    
    
     /**
     * Constructor
     */
    new(KiCoLaunchConfigurationTabGroup tabGroup) {
        this.tabGroup = tabGroup
    }
    
    /**
     * {@inheritDoc}
     */
    override initializeFrom(ILaunchConfiguration configuration) {
//        if(launchData == null)
            launchData = KiCoLaunchData.loadFromConfiguration(configuration)
    }
    
    /** 
     * {@inheritDoc}
     */
    override void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    }
    
    /**
     * Delegate to the launch data of the tab group 
     */
    def protected KiCoLaunchData getLaunchData() {
        return tabGroup.launchData
    }
    
     /**
     * Delegate to the launch data of the tab group 
     */
    def protected void setLaunchData(KiCoLaunchData value) {
        tabGroup.launchData = value
    }
}