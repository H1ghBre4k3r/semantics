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
package de.cau.cs.kieler.esterel.kexpressions;

/**
 * @author chsch
 *
 */
public class KExpressionsRuntimeModule extends
        de.cau.cs.kieler.esterel.kexpressions.AbstractKExpressionsRuntimeModule {

    public Class<? extends org.eclipse.xtext.conversion.IValueConverterService> bindIValueConverterService() {
        return de.cau.cs.kieler.esterel.kexpressions.formatting.KExpressionsValueConverter.class;
    }

}