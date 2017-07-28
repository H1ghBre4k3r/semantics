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
package de.cau.cs.kieler.scg.transformations.guards

import de.cau.cs.kieler.kico.transformation.AbstractProductionTransformation
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.scg.SCGraph
import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions

/** 
 * 
 * @author ssm
 * @kieler.design 2016-02-22 proposed 
 * @kieler.rating 2016-02-22 proposed yellow
 */

abstract class AbstractGuardTransformation extends AbstractProductionTransformation {
    
    @Inject extension KExpressionsDeclarationExtensions
    @Inject extension KExpressionsValuedObjectExtensions    
    
    
    /** Name of the term signal. */
    public static val String TERM_GUARD_NAME = "_TERM"
    

    protected def ValuedObject createTERMSignal(SCGraph scg) {
        createValuedObject(TERM_GUARD_NAME) => [
            scg.declarations += createBoolDeclaration.attach(it)
        ]        
    }
	
}