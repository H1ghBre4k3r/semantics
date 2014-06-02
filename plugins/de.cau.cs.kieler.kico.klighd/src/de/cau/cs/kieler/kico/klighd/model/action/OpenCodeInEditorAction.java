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
package de.cau.cs.kieler.kico.klighd.model.action;

import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import de.cau.cs.kieler.kico.klighd.editor.StringBasedEditorInput;
import de.cau.cs.kieler.kico.klighd.model.KiCoCodePlaceHolder;
import de.cau.cs.kieler.klighd.IAction;

/**
 * Action to open a Editor containing content of KiCoCodePlaceHolder.
 * 
 * @author als
 * 
 */
public class OpenCodeInEditorAction implements IAction {

    public final static String ID =
            "de.cau.cs.kieler.kico.klighd.model.action.OpenCodeInEditorAction";

    /**
     * {@inheritDoc}
     */
    public ActionResult execute(ActionContext context) {
        Object inputModel = context.getViewContext().getInputModel();
        // get error model
        if (inputModel instanceof KiCoCodePlaceHolder) {
            KiCoCodePlaceHolder codeModel = (KiCoCodePlaceHolder) inputModel;
            //get window for opening editor
            IWorkbenchWindow window =
                    context.getViewContext().getDiagramWorkbenchPart().getSite()
                            .getWorkbenchWindow();
            
            //Create editor input based on string.
            //Currently it is better to set this read only because if normal save is used instead of save as saving has no effect
            IStorageEditorInput input =
                    new StringBasedEditorInput(codeModel.getName(), "Generated Code", codeModel.getCode(), true);
            
            //open editor
            IWorkbenchPage page = window.getActivePage();
            if (page != null)
                try {
                    page.openEditor(input, "org.eclipse.ui.DefaultTextEditor");
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
        }
        return ActionResult.createResult(false);

    }

}
