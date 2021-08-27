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
package de.cau.cs.kieler.kicool.ui.klighd;

import java.util.HashSet;
import java.util.List;

import org.eclipse.ui.IEditorPart;


/**
 * Static Notifier for the KiCoModelUpdateController.
 * 
 * @author als
 * @kieler.design 2015-06-29 proposed
 * @kieler.rating 2015-06-29 proposed yellow
 *
 */
public class KiCoModelViewNotifier {
    
    /** All controllers. */
    private static final HashSet<KiCoModelUpdateController> CONTROLLERS = new HashSet<KiCoModelUpdateController>();

    /**
     * Adds the given Controller to the list of notified controllers.
     * 
     * @param controller
     *            the update controller
     */
    public static void register(final KiCoModelUpdateController controller) {
        CONTROLLERS.add(controller);
    }

    /**
     * Removes the given Controller from the list of notified controllers.
     * 
     * @param controller
     *            the update controller
     */
    public static void unregister(final KiCoModelUpdateController controller) {
        CONTROLLERS.remove(controller);
    }

    // -- Notifications
    // -------------------------------------------------------------------------
    
    /**
     * Notifies all ModelViews linked to the Compiler and listening on the given Editor about a new compilation result.
     * 
     * @param editor the related editor
     * @param model the new compilation result
     * @param context the used compilation context
     */
    public static void notifyCompilationChanged(final IEditorPart editor, final Object model) {
        for (KiCoModelUpdateController controller : CONTROLLERS) {
            if (controller.isActive() &&
                controller.getEditor() != null &&
                controller.getEditor() == editor) {
                controller.updateCompilerModel(model);
            }
        }
    }
    
    /**
     * Notifies all ModelViews linked to the Compiler and listening on the given Editor about a new compilation results.
     * 
     * @param editor the related editor
     * @param model the new compilation result
     * @param context the used compilation context
     */
    public static void notifyCompilationChangedList(final IEditorPart editor, final List<Object> models) {
        for (KiCoModelUpdateController controller : CONTROLLERS) {
            if (controller.isActive() &&
                controller.getEditor() != null &&
                controller.getEditor() == editor) {
                controller.updateCompilerModels(models);
            }
        }
    }    
}
