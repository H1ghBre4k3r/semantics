/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.klighd;

import de.cau.cs.kieler.core.kgraph.KLabel;
import de.cau.cs.kieler.klighd.labels.AbstractKlighdLabelManager;

/**
 * Truncate all HostCode Arguments and add some dots to point this out.
 * 
 * @author ybl
 *
 */
public class TruncateHostCodeArguments extends AbstractKlighdLabelManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public String resizeLabel(KLabel label, double targetWidth) {
        //TODO was passiert mit verschachtelten HostCode?
        String text = label.getText();
        int bracketOpen = 0;
        int bracketClose = 0;
        for (int i = 0; i < text.length(); i++) {
            char character = text.toCharArray()[i];

            if (character == '(') {
                bracketOpen = i;
            }

            if (character == ')') {
                bracketClose = i;
                if (bracketOpen + 1 != bracketClose) {
                    text = text.replace(text.substring(bracketOpen + 1, bracketClose), "...");
                }
            }
        }

        return text;
    }

}
