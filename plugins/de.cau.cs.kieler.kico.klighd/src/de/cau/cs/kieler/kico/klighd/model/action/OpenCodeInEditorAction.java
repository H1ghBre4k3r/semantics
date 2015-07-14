/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Kiel University
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
 * @kieler.design 2014-07-30 proposed
 * @kieler.rating 2014-07-30 proposed yellow
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
        KiCoCodePlaceHolder codeModel = null;
        if (inputModel instanceof KiCoCodePlaceHolder) {
            codeModel = (KiCoCodePlaceHolder) inputModel;
        } else if (context.getKNode() != null) {
            // if input model is not KiCoCodePlaceHolder check if clicked node is associated with it
            Object domainElement = context.getDomainElement(context.getKNode());
            if (domainElement instanceof KiCoCodePlaceHolder) {
                codeModel = (KiCoCodePlaceHolder) domainElement;
            }
        }
        // open editor
        if (codeModel != null) {
            // get window for opening editor
            IWorkbenchWindow window =
                    context.getViewContext().getDiagramWorkbenchPart().getSite()
                            .getWorkbenchWindow();

            // Create editor input based on string.
            // Currently it is better to set this read only because if normal save is used instead
            // of save as saving has no effect
            IStorageEditorInput input =
                    new StringBasedEditorInput(codeModel.getName(), "Generated Code",
                            codeModel.getCode(), true, codeModel.getResourceExtension());

            // open editor
            IWorkbenchPage page = window.getActivePage();
            if (page != null)
                try {
                    page.openEditor(input, codeModel.getEditorID());
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
        }
        return ActionResult.createResult(false);

    }

}
