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
package de.cau.cs.kieler.kicool.ui.view.actions

import de.cau.cs.kieler.kicool.System
import de.cau.cs.kieler.kicool.ui.KiCoolEditorInput
import de.cau.cs.kieler.kicool.ui.internal.KiCoolActivator
import de.cau.cs.kieler.kicool.ui.view.CompilerView
import java.io.File
import org.eclipse.jface.action.Action
import org.eclipse.jface.action.IAction
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.ui.IWorkbenchWindow
import org.eclipse.ui.PartInitException
import org.eclipse.ui.ide.IDE
import org.eclipse.ui.plugin.AbstractUIPlugin
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * @author ssm
 * @kieler.design 2017-05-12 proposed
 * @kieler.rating 2017-05-12 proposed yellow  
 */
class OpenSystemAction {

    public static final ImageDescriptor ICON_OPEN_SYSTEM = AbstractUIPlugin.imageDescriptorFromPlugin(
            "de.cau.cs.kieler.kicool.ui", "icons/IMBC_edit.png");    
    
    /** The action for compiling systems. */
    @Accessors private Action action
    @Accessors private CompilerView view
    
    new(CompilerView view) {  
        this.view = view
        action = new Action("OpenSystem", IAction.AS_PUSH_BUTTON) {
            override void run() {
                invokeOpenSystem
            }
        }
        action.setId("openSystemAction")
        action.setText("Open active system")
        action.setToolTipText("Opens the actual active system inside a seperate editor to enable modifications.")
        action.imageDescriptor = ICON_OPEN_SYSTEM    
        action.disabledImageDescriptor = null
    }
    
    def void invokeOpenSystem() {
        val system = view.systemSelectionManager.selectedSystem
        system.openSystemInEditor
    }
    
    def private void openSystemInEditor(System system) {
        val IWorkbenchWindow window = view.getViewContext().getDiagramWorkbenchPart().getSite()
            .getWorkbenchWindow();

        val input = new KiCoolEditorInput(system)

        val page = window.getActivePage();
        if (page !== null) {
            try {
                if (view.systemSelectionManager.getProjectSystemFilePath(system.id) !== null) {
                    IDE.openEditor(page, new File(view.systemSelectionManager.getProjectSystemFilePath(system.id)).toURI, KiCoolActivator.DE_CAU_CS_KIELER_KICOOL_KICOOL, true)
                } else {
                    page.openEditor(input, KiCoolActivator.DE_CAU_CS_KIELER_KICOOL_KICOOL);
                }
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }    
}