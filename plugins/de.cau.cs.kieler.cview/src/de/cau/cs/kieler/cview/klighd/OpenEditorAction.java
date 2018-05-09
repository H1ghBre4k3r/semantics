/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.cview.klighd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.editors.text.FileEditorInputAdapterFactory;

import de.cau.cs.kieler.cview.model.cViewModel.Component;
import de.cau.cs.kieler.cview.model.cViewModel.ComponentType;
import de.cau.cs.kieler.klighd.IAction;

/**
 * Open in C or Text Editor
 * 
 * @author cmot
 * @kieler.design 2014-07-30 proposed
 * @kieler.rating 2014-07-30 proposed yellow
 * 
 */
public class OpenEditorAction implements IAction {
 
    /** The action ID. */
    public static final String ID = "de.cau.cs.kieler.cview.OpenEditorAction";

    // -------------------------------------------------------------------------

    private String shortenFile(String pathString) {
        if (pathString == null) {
            return null;
        }
        int i = pathString.indexOf("/");
        int j = pathString.indexOf("\\");
        if (j > 0) {
            i = j;
        }
        if (i == -1) {
            // not found
            return null;
        } else {
            return (pathString.substring(i + 1));
        }
    }
    
    
    private IFile resolveFile(String pathString) {
        Path path = new Path(pathString);
        IFile file = null;
        boolean done = false;
        boolean doneAll = false;
        List<IContainer> iContainerList = new ArrayList<IContainer>();
        iContainerList.add(ResourcesPlugin.getWorkspace().getRoot());
        // Brute force search also in all projects (if renamed project)
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            iContainerList.add(project);
        }
        for (IContainer iContainer : iContainerList) {
            if (!doneAll) {
                done = false;
                String pathStringTemp = pathString;
                while (!done && !doneAll) {
                    if (!done) {
                        try {
                            file = iContainer.getFile(path);
                        } catch (Exception e) {
                        }
                        if (file.exists()) {
                            doneAll = true;
                        } else {
                            pathStringTemp = shortenFile(pathStringTemp);
                            if (pathStringTemp != null) {
                                path = new Path(pathStringTemp);
                            } else {
                                done = true;
                            }
                        }
                    }
                }
            }
        }
        return file;
    }
    
    

    /**
     * {@inheritDoc}
     */
    public ActionResult execute(final ActionContext context) {
        Object inputModel = context.getViewContext().getInputModel();
        Object domainElement = context.getDomainElement(context.getKNode());

        Component component = (Component) domainElement;
        String pathString = component.getLocation();
        if (pathString == null && component.getParent() != null) {
            // The struct or typedef case
            pathString = component.getParent().getLocation();
            if (pathString == null && component.getParent().getParent() != null) {
                // The declaration in struct case
                pathString = component.getParent().getParent().getLocation();
            }
        }
        IFile file = resolveFile(pathString);

        // -------------------------------------------------------------------------

        IWorkbenchPage page = Workbench.getInstance().getWorkbenchWindows()[0].getActivePage();
        IEditorDescriptor desc =
                PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
        try {
            IEditorPart editorPart = page.openEditor(new FileEditorInput(file), desc.getId());
            if (domainElement instanceof Component) {
                int line = component.getReferenceLine();
                if (component.getType() != ComponentType.DIR) {
                    goToLine(editorPart, line);
                } else {
                    // goToLine(editorPart, 1);
                }
            }
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        return ActionResult.createResult(false);
    }

    // -------------------------------------------------------------------------

    /**
     * 
     * @param editorPart
     * @param lineNumber
     */
    private static void goToLine(IEditorPart editorPart, int lineNumber) {
        if (!(editorPart instanceof ITextEditor) || lineNumber <= 0) {
            return;
        }
        ITextEditor editor = (ITextEditor) editorPart;
        IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
        if (document != null) {
            IRegion lineInfo = null;
            try {
                // line count internally starts with 0, and not with 1 like in
                // GUI
                lineInfo = document.getLineInformation(lineNumber - 1);
            } catch (BadLocationException e) {
                // ignored because line number may not really exist in document,
                // we guess this...
            }
            if (lineInfo != null) {
                editor.selectAndReveal(lineInfo.getOffset(), lineInfo.getLength());
            }
        }
    }

    // -------------------------------------------------------------------------
}
