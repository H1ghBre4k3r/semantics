/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.klighd.synthesis.hooks

import com.google.inject.Inject
import de.cau.cs.kieler.core.kgraph.KNode
import de.cau.cs.kieler.core.krendering.Colors
import de.cau.cs.kieler.core.krendering.ViewSynthesisShared
import de.cau.cs.kieler.core.krendering.extensions.KRenderingExtensions
import de.cau.cs.kieler.klighd.SynthesisOption
import de.cau.cs.kieler.sccharts.Scope
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.klighd.hooks.SynthesisHook
import de.cau.cs.kieler.sccharts.klighd.synthesis.styles.ColorStore
import de.cau.cs.kieler.sccharts.klighd.synthesis.styles.StateStyles

import static de.cau.cs.kieler.sccharts.klighd.synthesis.styles.ColorStore.Color.*

/**
 * Applies paper color theme.
 *  
 * @author als
 * @kieler.design 2015-08-13 proposed
 * @kieler.rating 2015-08-13 proposed yellow
 * 
 */
@ViewSynthesisShared
class BlackWhiteModeHook extends SynthesisHook {

    @Inject
    extension KRenderingExtensions

    @Inject
    extension ColorStore
    
    @Inject
    extension StateStyles

    /** The related synthesis option */
    public static final SynthesisOption PAPER_BW = SynthesisOption.createCheckOption("Paper (Black/White)", false);

    override getDisplayedSynthesisOptions() {
        return newLinkedList(PAPER_BW);
    }

    override start(Scope scope, KNode root) {
        if (PAPER_BW.booleanValue) {
            configureColor(TRANSITION_DEFERRED_DECORATOR, Colors.GRAY);
            configureColor(TRANSITION_ABORT_DECORATOR, Colors.GRAY);
            configureColor(TRANSITION_TERMINATION_DECORATOR, Colors.GRAY);

            configureColor(STATE_BACKGROUND_GRADIENT_1, Colors.GRAY_95);
            configureColor(STATE_BACKGROUND_GRADIENT_2, Colors.GRAY_95);
            configureColor(STATE_REFERENCED_BACKGROUND_GRADIENT_1, Colors.GRAY_97);
            configureColor(STATE_REFERENCED_BACKGROUND_GRADIENT_2, Colors.GRAY_97);

            configureColor(KEYWORD, Colors.DIM_GRAY);
            
            baseLineWidth = 2;
        }
    }

    override processState(State state, KNode node) {
        if (PAPER_BW.booleanValue) {
            val container = node.getKContainerRendering;
            container.styles.remove(container.shadow);
        }
    }

}