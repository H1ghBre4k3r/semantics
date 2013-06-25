package de.cau.cs.kieler.scl.seqscl.s.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.jface.viewers.ISelection;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.STextStandaloneSetup;
import org.yakindu.sct.ui.editor.DiagramActivator;
import org.yakindu.sct.ui.editor.editor.StatechartDiagramEditor;

import com.google.inject.Injector;

import de.cau.cs.kieler.core.model.handlers.AbstractConvertModelHandler;
import de.cau.cs.kieler.scl.seqscl.s.xtend.SeqSCLToSTransformation;
import de.cau.cs.kieler.scl.scl.Program;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SeqSCLToSFileHandler extends AbstractConvertModelHandler {

    // Create an injector to load the transformation via guice.
    private static Injector injector = new STextStandaloneSetup()
            .createInjectorAndDoEMFRegistration();
    
    @Override
    protected String getTargetExtension() {
        return "s";
    }
    
    @Override
    protected String getDiagramEditorID() {
        return "de.cau.cs.kieler.s.S";
    }
    
    @Override 
    protected boolean doCreateDiagram(EObject model, ExecutionEvent event, ISelection selection) {
        return false;
    }

    @Override 
    protected boolean doOpenEditor(Object model, ExecutionEvent event, ISelection selection) {
        return true;
    }

    @Override
    protected Injector createResourceInjector() {
        return injector;
    }

    @Override
    protected Object transform(EObject model, ExecutionEvent event, ISelection selection) {
//        String command = event.getCommand().getId().toString();        
        
        EObject transformed = (new SeqSCLToSTransformation())
                .transformSeqSCLToSProgram((Program) model);
        return transformed;
    }

}
