/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 *
 * Copyright 2010 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 *
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.text.sct.ui;

import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;

import de.cau.cs.kieler.core.annotations.text.ui.AnnotationsHighlightingConfiguration;
import de.cau.cs.kieler.sccharts.text.actions.ui.ActionsHighlightingConfiguration;

/**
 * Custom {@link IHighlightingConfiguration} contributing to the Sct editor. The required method
 * {@link IHighlightingConfiguration#configure(org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor)}
 * will be provided by the {@link AnnotationsHighlightingConfiguration} and can be specialized in
 * order to add KITS specific highlighting profiles.
 *
 * @author chsch
 */
public class SctHighlightingConfiguration extends ActionsHighlightingConfiguration {

//	public void configure(IHighlightingConfigurationAcceptor acceptor) {
//		super.configure(acceptor);
//	}
}