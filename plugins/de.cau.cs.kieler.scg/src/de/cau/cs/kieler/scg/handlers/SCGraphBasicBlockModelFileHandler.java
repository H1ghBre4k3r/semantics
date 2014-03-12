/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.cau.cs.kieler.core.model.handlers.AbstractConvertModelHandler;
import de.cau.cs.kieler.sccharts.text.sct.SctStandaloneSetup;
import de.cau.cs.kieler.scg.SCGPlugin;
import de.cau.cs.kieler.scgdep.SCGraphDep;
import de.cau.cs.kieler.scg.transformations.BasicBlockTransformation;
import de.cau.cs.kieler.scg.transformations.BasicBlockTransformationSCplus;

/**
 * Handler for menu contributions
 *
 * @author ssm
 * @kieler.design 2014-01-08 proposed 
 * @kieler.rating 2014-01-08 proposed yellow
 */
public class SCGraphBasicBlockModelFileHandler extends AbstractConvertModelHandler {

    public static final String BASICBLOCKTRANSFORMATION =
            "de.cau.cs.kieler.scg.commands.SCGDEPToSCGBBTransformation";
    public static final String BASICBLOCKTRANSFORMATIONSCPLUS =
            "de.cau.cs.kieler.scg.commands.BasicBlockTransformationSCplus";

    private static Injector injector = new SctStandaloneSetup()
        .createInjectorAndDoEMFRegistration();

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTargetExtension(EObject model, ExecutionEvent event, ISelection selection) {
        return "scgbb";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Injector createResourceInjector() {
        return injector;
    }

    public String getDiagramEditorID() {
        return SCGPlugin.EDITOR_ID;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Object transform(EObject model, ExecutionEvent event, ISelection selection) {
        String commandString = event.getCommand().getId().toString();
        EObject transformed = null;

        
        // Call the model transformation (this creates a copy of the model containing the
        // refactored model).
        transformed = model;
        if (commandString.equals(BASICBLOCKTRANSFORMATION)) {
            BasicBlockTransformation transformation =
                    Guice.createInjector().getInstance(BasicBlockTransformation.class);
            transformed = transformation.transformSCGDEPToSCGBB((SCGraphDep) model);
        }
        else if (commandString.equals(BASICBLOCKTRANSFORMATIONSCPLUS)) {
            BasicBlockTransformationSCplus transformation =
                    Guice.createInjector().getInstance(BasicBlockTransformationSCplus.class);
            transformed = transformation.transformSCGDEPToSCGBB((SCGraphDep) model);
        }
        return transformed;
    }

}
