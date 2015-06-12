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
package de.cau.cs.kieler.scl.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * @author krat
 * 
 */
public class SclHighlightingConfiguration extends DefaultHighlightingConfiguration {
    // provide an id string for the highlighting calculator
    public static final String LABEL = "Labels";
    

    // declare style for different highlighting
    public void configure(IHighlightingConfigurationAcceptor acceptor) {
        addType(acceptor, LABEL, 0, 0, 0, SWT.ITALIC);
        super.configure(acceptor);
    }

    public void addType(IHighlightingConfigurationAcceptor acceptor, String s, int r, int g, int b,
            int style) {
        TextStyle textStyle = new TextStyle();
        textStyle.setBackgroundColor(new RGB(255, 255, 255));
        textStyle.setColor(new RGB(r, g, b));
        textStyle.setStyle(style);
        acceptor.acceptDefaultHighlighting(s, s, textStyle);
    }

}
