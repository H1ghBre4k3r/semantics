/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.esterel.processors.transformators.incremental

import com.google.inject.Inject
import de.cau.cs.kieler.esterel.Present
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.scl.Conditional

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * @author mrb
 *
 */
class PresentTransformation extends AbstractSCEstDynamicProcessor<Present>  {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    
    public static val ID = "de.cau.cs.kieler.esterel.processors.present"
    
    override getId() {
        return ID
    }

    override getName() {
        return "Present"
    }
    
    @Inject
    extension EsterelTransformationExtensions
    
    override transform(Present present) {
        var Conditional conditional
        if (!present.cases.empty) {
            conditional = createConditional(present.cases.get(0).expression)
            conditional.statements.add(present.cases.get(0).statements)
            var tempConditional = conditional
            // present cases lead to nested conditional statements
            for (var i=1; i<present.cases.length; i++) {
                var e = present.cases.get(i)
                var conditional2 = createConditional(e.expression)
                conditional2.statements.add(e.statements)
                var elseStatement = createElseScope(conditional2)
                tempConditional.setElse(elseStatement)
                tempConditional = conditional2
            }
            if (!present.elseStatements.isEmpty) {
                tempConditional.setElse(createElseScope(present.elseStatements))
            }
        }
        else {
            conditional = createConditional(present.expression)
            conditional.statements.add(present.statements)
            if (!present.elseStatements.isEmpty) {
                conditional.setElse(createElseScope(present.elseStatements))
            }
        }  
        present.replace(conditional)  
        lastStatement = conditional
    }
    
}