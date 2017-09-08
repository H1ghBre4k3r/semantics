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
package de.cau.cs.kieler.esterel.scest.transformations

import com.google.inject.Inject
import de.cau.cs.kieler.esterel.scest.extensions.SCEstExtension
import de.cau.cs.kieler.esterel.scest.features.SCEstFeature
import de.cau.cs.kieler.esterel.scest.scest.SCEstProgram
import de.cau.cs.kieler.kico.transformation.AbstractExpansionTransformation
import de.cau.cs.kieler.kitt.tracing.Traceable
import org.eclipse.emf.common.util.EList
import de.cau.cs.kieler.scl.scl.Statement
import de.cau.cs.kieler.scl.scl.StatementContainer
import de.cau.cs.kieler.esterel.esterel.Trap
import de.cau.cs.kieler.esterel.esterel.Exec
import de.cau.cs.kieler.esterel.esterel.Do
import de.cau.cs.kieler.esterel.esterel.Present
import de.cau.cs.kieler.esterel.esterel.IfTest
import de.cau.cs.kieler.esterel.esterel.Abort
import de.cau.cs.kieler.scl.scl.Conditional
import de.cau.cs.kieler.esterel.esterel.EsterelParallel
import de.cau.cs.kieler.scl.scl.Parallel
import com.google.common.collect.Sets
import de.cau.cs.kieler.esterel.esterel.LocalSignalDecl
import de.cau.cs.kieler.scl.scl.ScopeStatement
import de.cau.cs.kieler.esterel.esterel.ISignal
import de.cau.cs.kieler.kexpressions.ValueType
import java.util.HashMap
import de.cau.cs.kieler.esterel.scest.extensions.NewSignals
import de.cau.cs.kieler.kexpressions.Declaration
import de.cau.cs.kieler.esterel.esterel.Await
import de.cau.cs.kieler.esterel.esterel.Run

/**
 * @author mrb
 *
 */
class LocalSignalDeclTransformation extends AbstractExpansionTransformation implements Traceable{
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::LOCALSIGNALDECL_ID
    }

    override getName() {
        return SCEstTransformation::LOCALSIGNALDECL_NAME
    }

    override getExpandsFeatureId() {
        return SCEstFeature::LOCALSIGNALDECL_ID
    }
    
    override getProducesFeatureIds() {
        return Sets.newHashSet(SCEstTransformation::LOOP_ID, SCEstTransformation::ABORT_ID
                            , SCEstTransformation::AWAIT_ID, SCEstTransformation::PRESENT_ID
                            , SCEstTransformation::IFTEST_ID)
    }    
    
    override getNotHandlesFeatureIds() {
        return Sets.newHashSet(SCEstTransformation::INITIALIZATION_ID, SCEstTransformation::SIGNAL_ID,
                               SCEstTransformation::RUN_ID)
    }

    @Inject
    extension SCEstExtension
    
    /*
     * This transformation has to be scheduled after the signal transformation because the signal transformation
     * iterates over "newSignals" in SCEstExtension.
     * Therefore the signal transformation would have problems when "createParallelForSignals" is called.
     */
    
    def SCEstProgram transform(SCEstProgram prog) {
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
        if (statement instanceof LocalSignalDecl) {
            statement.transformSignals
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
        else if (statement instanceof Present) {
            transformStatements((statement as Present).thenStatements)
            (statement as Present).cases?.forEach[ c | transformStatements(c.statements)]
            transformStatements((statement as Present).elseStatements)
        }
        else if (statement instanceof IfTest) {
            transformStatements((statement as IfTest).thenStatements)
            (statement as IfTest).elseif?.forEach [ elsif | transformStatements(elsif.thenStatements)]
            transformStatements((statement as IfTest).elseStatements)
        }
        else if (statement instanceof EsterelParallel) {
            (statement as EsterelParallel).threads.forEach [ t |
                transformStatements(t.statements)
            ]
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
        var HashMap<ISignal, NewSignals> signalsMap = new HashMap<ISignal, NewSignals>()
        var localSignals = statement as LocalSignalDecl
        var statements =  statement.getContainingList
        var pos = statements.indexOf(statement)
        var scope = createScopeStatement
        scope.statements.add(localSignals.statements)
        for (signal : localSignals.signals) {
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
                        var s_val = createSignalVariable(signal.expression, signal.combineOperator, s.name + "_val")
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
    
    def createParallelForSignals(ScopeStatement scope, HashMap<ISignal, NewSignals> signalsMap) {
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