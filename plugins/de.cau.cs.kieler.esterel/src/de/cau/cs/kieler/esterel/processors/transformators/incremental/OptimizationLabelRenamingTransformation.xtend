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
import de.cau.cs.kieler.scl.Label
import de.cau.cs.kieler.scl.Parallel
import de.cau.cs.kieler.scl.Statement
import de.cau.cs.kieler.scl.StatementContainer
import org.eclipse.emf.common.util.EList

/**
 * @author mrb
 *
 */
class OptimizationLabelRenamingTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::LABELRENAMING_ID
    }

    override getName() {
        return SCEstTransformation::LABELRENAMING_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::LABELRENAMING_ID
//    }
//        
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(
//              SCEstTransformation::ABORT_ID, SCEstTransformation::ESTERELPARALLEL_ID
//            , SCEstTransformation::NOTHING_ID, SCEstTransformation::HALT_ID
//            , SCEstTransformation::BLOCK_ID, SCEstTransformation::EMIT_ID
//            , SCEstTransformation::SUSTAIN_ID
//            , SCEstTransformation::PROCCALL_ID, SCEstTransformation::PRESENT_ID
//            , SCEstTransformation::IFTEST_ID, SCEstTransformation::LOOP_ID
//            , SCEstTransformation::REPEAT_ID, SCEstTransformation::AWAIT_ID
//            , SCEstTransformation::EVERYDO_ID, SCEstTransformation::SUSPEND_ID
//            , SCEstTransformation::TRAP_ID, SCEstTransformation::EXEC_ID
//            , SCEstTransformation::LOCALSIGNALDECL_ID, SCEstTransformation::LOCALVARIABLE_ID
//            , SCEstTransformation::RUN_ID, SCEstTransformation::DO_ID
//            , SCEstTransformation::UNEMIT_ID, SCEstTransformation::SET_ID
//            , SCEstTransformation::SIGNAL_ID, SCEstTransformation::CONSTANT_ID
//            , SCEstTransformation::SENSOR_ID, SCEstTransformation::FUNCTION_ID
//        )
//    }

    @Inject
    extension EsterelTransformationExtensions
    @Inject
    extension EsterelExtensions
    
    override EsterelProgram transform(EsterelProgram prog) {
        resetLabelSuffix
        prog.modules.forEach [ m | transformStatements(m.statements)]
        return prog
    }
    
    def void transformStatements(EList<Statement> statements) {
        if (statements != null) {
            for (var i=0; i<statements.length; i++) {
                statements.get(i).transformStatement
            }
        }
    }
    
    def transformStatement(Statement statement) {
        if (statement instanceof Label) {
            (statement as Label).name = createNewUniqueLabelOptimization
        }
        else if (statement instanceof Present) {
            transformStatements((statement as Present).statements)
            if ((statement as Present).cases != null) {
                (statement as Present).cases.forEach[ c | transformStatements(c.statements)]
            }
            transformStatements((statement as Present).elseStatements)
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
    }
}