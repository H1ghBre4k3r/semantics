/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.esterel.processors.transformators.incremental

import com.google.inject.Inject
import de.cau.cs.kieler.esterel.Abort
import de.cau.cs.kieler.esterel.Await
import de.cau.cs.kieler.esterel.Do
import de.cau.cs.kieler.esterel.EsterelParallel
import de.cau.cs.kieler.esterel.EsterelProgram
import de.cau.cs.kieler.esterel.Exec
import de.cau.cs.kieler.esterel.IfTest
import de.cau.cs.kieler.esterel.Present
import de.cau.cs.kieler.esterel.Run
import de.cau.cs.kieler.esterel.Trap
import de.cau.cs.kieler.esterel.extensions.EsterelExtensions
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.esterel.processors.EsterelProcessor
import de.cau.cs.kieler.scl.Conditional
import de.cau.cs.kieler.scl.Parallel
import de.cau.cs.kieler.scl.Statement
import de.cau.cs.kieler.scl.StatementContainer
import org.eclipse.emf.common.util.EList

/**
 * @author mrb
 *
 */
class PresentTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::PRESENT_ID
    }

    override getName() {
        return SCEstTransformation::PRESENT_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::PRESENT_ID
//    }
//     
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCEstTransformation::INITIALIZATION_ID, SCEstTransformation::RUN_ID)
//    }

    @Inject
    extension EsterelTransformationExtensions
    @Inject
    extension EsterelExtensions
    
    override EsterelProgram transform(EsterelProgram prog) {
        prog.modules.forEach [ m | transformStatements(m.statements)]
        return prog
    }
    
    def EList<Statement> transformStatements(EList<Statement> statements) {
        if (statements != null) {
            for (var i=0; i<statements.length; i++) {
                var statement = statements.get(i).transformStatement
                if (statement instanceof Statement) {
                    statements.set(i, statement)
                }
            }
        }
        return statements
    }
    
    def Statement transformStatement(Statement statement) {
        if (statement instanceof Present) {
            var present = statement as Present
            transformStatements(present.statements)
            if (present.cases != null) {
                present.cases.forEach[ c | transformStatements(c.statements)]
            }
            transformStatements(present.elseStatements)
            
            var Conditional conditional
            if (!present.cases.empty) {
                present.cases.get(0).expression.transformReferences
                conditional = createConditional(present.cases.get(0).expression)
                conditional.statements.add(present.cases.get(0).statements)
                var tempConditional = conditional
                for (var i=1; i<present.cases.length; i++) {
                    var e = present.cases.get(i)
                    e.expression.transformReferences
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
                present.expression.transformReferences
                conditional = createConditional(present.expression)
                conditional.statements.add(present.statements)
                if (!present.elseStatements.isEmpty) {
                    conditional.setElse(createElseScope(present.elseStatements))
                }
            }
            
            return conditional
        }
        else if (statement instanceof IfTest) {
            transformStatements((statement as IfTest).statements)
            if ((statement as IfTest).elseif != null) {
                (statement as IfTest).elseif.forEach [ elsif | transformStatements(elsif.statements)]
            }
            transformStatements((statement as IfTest).elseStatements)
        }
        else if (statement instanceof EsterelParallel) {
            (statement as EsterelParallel).threads.forEach [ t |
                transformStatements(t.statements)
            ]
        }
        else if (statement instanceof StatementContainer) {
            
            transformStatements((statement as StatementContainer).statements)
            
            if (statement instanceof Trap) {
                if ((statement as Trap).trapHandler != null) {
                    (statement as Trap).trapHandler.forEach[h | transformStatements(h.statements)]
                }
            }
            else if (statement instanceof Abort) {
                transformStatements((statement as Abort).doStatements)
                if ((statement as Abort).cases != null) {
                    (statement as Abort).cases.forEach[ c | transformStatements(c.statements)]
                }
            }
            else if (statement instanceof Await) {
                (statement as Await).cases?.forEach[ c | transformStatements(c.statements)]
            }
            else if (statement instanceof Exec) {
                if ((statement as Exec).execCaseList != null) {
                    (statement as Exec).execCaseList.forEach[ c | transformStatements(c.statements)]
                }
            }
            else if (statement instanceof Do) {
                transformStatements((statement as Do).watchingStatements)
            }
            else if (statement instanceof Conditional) {
                if ((statement as Conditional).getElse() != null) {
                    transformStatements((statement as Conditional).getElse().statements)
                }
            }
        }
        else if (statement instanceof Parallel) {
            (statement as Parallel).threads.forEach [ t |
                transformStatements(t.statements)
            ]
        }
        else if (statement instanceof Run) {
            statement.module?.module?.statements.transformStatements    
        }
        return statement
    }
    
}