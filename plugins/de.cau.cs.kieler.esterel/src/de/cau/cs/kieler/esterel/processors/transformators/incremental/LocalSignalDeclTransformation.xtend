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
import de.cau.cs.kieler.esterel.LocalSignalDeclaration
import de.cau.cs.kieler.esterel.Present
import de.cau.cs.kieler.esterel.Run
import de.cau.cs.kieler.esterel.Signal
import de.cau.cs.kieler.esterel.Trap
import de.cau.cs.kieler.esterel.extensions.EsterelExtensions
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.esterel.extensions.NewSignals
import de.cau.cs.kieler.esterel.processors.EsterelProcessor
import de.cau.cs.kieler.kexpressions.Declaration
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.scl.Conditional
import de.cau.cs.kieler.scl.Parallel
import de.cau.cs.kieler.scl.ScopeStatement
import de.cau.cs.kieler.scl.Statement
import de.cau.cs.kieler.scl.StatementContainer
import java.util.HashMap
import org.eclipse.emf.common.util.EList

/**
 * @author mrb
 *
 */
class LocalSignalDeclTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::LOCALSIGNALDECL_ID
    }

    override getName() {
        return SCEstTransformation::LOCALSIGNALDECL_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::LOCALSIGNALDECL_ID
//    }
//        
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCEstTransformation::INITIALIZATION_ID, SCEstTransformation::SIGNAL_ID,
//                               SCEstTransformation::RUN_ID)
//    }

    @Inject
    extension EsterelTransformationExtensions
    @Inject
    extension EsterelExtensions
    
    /*
     * This transformation has to be scheduled after the signal transformation because the signal transformation
     * iterates over "newSignals" in EsterelTransformationExtensions.
     * Therefore the signal transformation would have problems when "createParallelForSignals" is called.
     */
    
    override EsterelProgram transform(EsterelProgram prog) {
        for (m : prog.modules) { 
            transformStatements(m.statements)
        }
        return prog
    }
    
    def EList<Statement> transformStatements(EList<Statement> statements) {
        for (var i=0; i<statements?.length; i++) {
            statements.get(i).transformStatement
        }
        return statements
    }
    
    def transformStatement(Statement statement) {
        if (statement instanceof LocalSignalDeclaration) {
            statement.transformSignals
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
                (statement as Trap).trapHandler?.forEach[h | transformStatements(h.statements)]
            }
            else if (statement instanceof Abort) {
                transformStatements((statement as Abort).doStatements)
                (statement as Abort).cases?.forEach[ c | transformStatements(c.statements)]
            }
            else if (statement instanceof Await) {
                (statement as Await).cases?.forEach[ c | transformStatements(c.statements)]
            }
            else if (statement instanceof Exec) {
                (statement as Exec).execCaseList?.forEach[ c | transformStatements(c.statements)]
            }
            else if (statement instanceof Do) {
                transformStatements((statement as Do).watchingStatements)
            }
            else if (statement instanceof Conditional) {
                transformStatements((statement as Conditional).getElse()?.statements)
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
    
    def transformSignals(Statement statement) {
        // for valued singals: signal S will be transformed to s, s_set, s_cur, s_val => new NewSignals(s, s_set, s_cur, s_val)
        var HashMap<Signal, NewSignals> signalsMap = new HashMap<Signal, NewSignals>()
        var localSignals = statement as LocalSignalDeclaration
        var statements =  statement.getContainingList
        var pos = statements.indexOf(statement)
        var scope = createScopeStatement
        scope.statements.add(localSignals.statements)
        for (signal : localSignals.valuedObjects.filter(Signal)) {
                var s = createSignalVariable(createFalse, null, null.createNewUniqueSignalName)
                signal.name = null.createNewUniqueSignalName
                var decl = createDeclaration(ValueType.BOOL, s)
                var Declaration decl2 = createDeclaration(null, null)
                if (signal.type != null) {
                    scope.declarations.add(decl)
                    if (signal.type == ValueType.PURE) {
                        signalsMap.put(signal, new NewSignals(s))
                    }
                    else {
                        var s_set = createSignalVariable(createFalse, null, s.name + "_set")
                        decl.valuedObjects.add(s_set)
                        var s_val = createSignalVariable(signal.initialValue, signal.combineOperator, s.name + "_val")
                        var s_cur = createSignalVariable(null, signal.combineOperator, s.name + "_cur")
                        var tempType = if (signal.type == ValueType.DOUBLE) ValueType.FLOAT else signal.type
                        decl2 = createDeclaration(tempType, null)
                        decl2.valuedObjects.add(s_val)
                        decl2.valuedObjects.add(s_cur)
                        signalsMap.put(signal, new NewSignals(s, s_set, s_cur, s_val))
                        scope.declarations.add(decl2)
                    }
                }
                else { // shouldn't be possible
                    throw new UnsupportedOperationException(
                        "The following signal doesn't have a type! " + s.name)
                }
        }
        createParallelForSignals(scope, signalsMap)
        
        // One of the next two lines can be used. First: ISignals will be deleted; Second: ISignals stay
//        statements.set(pos, scope)
        localSignals.statements.add(scope)
        
        newSignals.putAll(signalsMap)
        scope.statements.transformStatements
//        scope.transformReferences
    }
    
    def createParallelForSignals(ScopeStatement scope, HashMap<Signal, NewSignals> signalsMap) {
        var signals = signalsMap.keySet.iterator.toList
        var term = createNewUniqueTermFlag(createFalse)
        var decl = createDeclaration(ValueType.BOOL, term)
        var parallel = createParallel
        var thread1 = createThread
        var thread2 = createThread
        parallel.threads.add(thread1)
        parallel.threads.add(thread2)
        thread2.statements.add(scope.statements)
        thread2.statements.add(createAssignment(term, createTrue))
        scope.statements.add(parallel)
        scope.declarations.add(decl)
        
        // thread1 statements: the initializations of the signals
        var label = createLabel
        thread1.statements.add(label)
        for (signal : signals) {
            var keyValue = signalsMap.get(signal)
            var s = keyValue.s
            if (signal.type != ValueType.PURE) {
                var s_set = keyValue.s_set
                var s_cur = keyValue.s_cur
                var s_val = keyValue.s_val
                var assign1 = createAssignment(s, createFalse)
                thread1.statements.add(assign1)
                var assign2 = createAssignment(s_set, createFalse)
                thread1.statements.add(assign2)
                var conditional1 = createConditional(createNot(createValuedObjectReference(s_set)))
                var assign3 = createAssignment(s_cur, getNeutral(s_cur.combineOperator))
                conditional1.statements.add(assign3)
                thread1.statements.add(conditional1)
                var conditional2 = createConditional(createValuedObjectReference(s))
                var assign4 = createAssignment(s_val, createValuedObjectReference(s_cur))
                conditional2.statements.add(assign4)
                thread1.statements.add(conditional2)
            }
            else {
                var assign1 = createAssignment(s, createFalse)
                thread1.statements.add(assign1)
            }
        }
        var conditional = newIfThenGoto(createNot(createValuedObjectReference(term)), label, true)
        thread1.statements.add(conditional)
    }
    
}