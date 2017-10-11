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
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.scl.Conditional
import de.cau.cs.kieler.scl.Label
import de.cau.cs.kieler.scl.Parallel
import de.cau.cs.kieler.scl.Pause
import de.cau.cs.kieler.scl.Statement
import de.cau.cs.kieler.scl.StatementContainer
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * @author mrb
 *
 */
class AbortTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::ABORT_ID
    }

    override getName() {
        return SCEstTransformation::ABORT_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::ABORT_ID
//    }
//
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCEstTransformation::INITIALIZATION_ID, SCEstTransformation::HALT_ID,
//            SCEstTransformation::LOCALSIGNALDECL_ID, SCEstTransformation::LOCALVARIABLE_ID,
//            SCEstTransformation::AWAIT_ID, SCEstTransformation::SUSTAIN_ID,
//            SCEstTransformation::DO_ID, SCEstTransformation::RUN_ID)
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
        if (statement instanceof Abort) {
            var abort = statement as Abort
            var statements =  statement.getContainingList
            var pos = statements.indexOf(statement)
            var scope = createScopeStatement
            var depth = abort.getDepth
            var parallel = createParallel
            var thread1 = createThread
            var thread2 = createThread
            parallel.threads.add(thread1)
            parallel.threads.add(thread2)
            
            // check if its just a single abort delay or if this abort includes cases
            if (abort.delay != null) {
                abort.delay.transformReferences
                // abort with expression before signal expression. e.g. "abort when 3 A" or "weak abort when 3 A"
                if (abort.delay.expression != null) {
                    var abortFlag = createNewUniqueAbortFlag(createBoolValue(false))
                    var decl = createDeclaration(ValueType.BOOL, abortFlag)
                    var label = createLabel
                    scope.declarations.add(decl)
                    var countingVariables = new LinkedList<ValuedObject>()
                    var variable = createNewUniqueVariable(createIntValue(0))
                    countingVariables.add(variable)
                    var decl2 = createDeclaration(ValueType.INT, variable)
                    scope.declarations.add(decl2)
                    scope.statements.add(parallel)
                    var label2 = createLabel
                    var label3 = createLabel
                    var conditional = newIfThenGoto(createValuedObjectReference(abortFlag), label3, false)
                    conditional.annotations.add(createAnnotation(depth))
                    var conditional2  = createConditional(EcoreUtil.copy(abort.delay.expression))
                    conditional2.statements.add(incrementInt(variable))
                    thread1.statements.add(label2)
                    thread1.statements.add(conditional) 
                    thread1.statements.add(createPause)
                    thread1.statements.add(conditional2)
                    thread1.statements.add(createGotoStatement(label2))
                    thread1.statements.add(label3)
                    // the order of the next lines is crucial! (first trasformStatements, add(Label), then transformPauses)
                    abort.statements.transformStatements
                    scope.statements.add(label)
                    thread2.statements.add(abort.statements)
                    thread2.statements.add(createLabel)
                    thread2.statements.transformPauses(abort, label, abortFlag, null, countingVariables)
                    scope.statements.add(label)
                    if (!abort.doStatements.empty) {
                                var conditional3 = createConditional(createValuedObjectReference(abortFlag))
                                conditional3.statements.add(abort.doStatements)
                                scope.statements.add(conditional3)
                            }
                    statements.set(pos,scope)
                }
                else {
                    // WEAK ABORT
                    if (abort.weak) {
                        // e.g. "weak abort when immediate A"
                        if (abort.delay.isImmediate) {
                            var abortFlag = createNewUniqueAbortFlag(createBoolValue(false))
                            var decl = createDeclaration(ValueType.BOOL, abortFlag)
                            var label = createLabel
                            // the order of the next three lines is crucial! (first trasformStatements, add(Label), then transformPauses)
                            abort.statements.transformStatements
                            abort.statements.add(label)
                            abort.statements.transformPauses(abort, label, abortFlag, null, null)
                            scope.declarations.add(decl)
                            scope.statements.add(abort.statements)
                            if (!abort.doStatements.empty) {
                                var conditional = createConditional(createValuedObjectReference(abortFlag))
                                conditional.statements.add(abort.doStatements)
                                scope.statements.add(conditional)
                            }
                            statements.set(pos,scope)
                        }
                        // e.g. "weak abort when A"
                        else {
                            var abortFlag = createNewUniqueAbortFlag(createBoolValue(false))
                            var depthFlag = createNewUniqueDepthFlag(createBoolValue(false))
                            var decl = createDeclaration(ValueType.BOOL, abortFlag)
                            var label = createLabel
                            scope.declarations.add(decl)
                            // e.g. "weak abort when A"
                            decl.valuedObjects.add(depthFlag)
                            // the order of the next three lines is crucial! (first trasformStatements, add(Label), then transformPauses)
                            abort.statements.transformStatements
                            abort.statements.add(label)
                            abort.statements.transformPauses(abort, label, abortFlag, depthFlag, null)
                            scope.statements.add(abort.statements)
                            if (!abort.doStatements.empty) {
                                var conditional = createConditional(createValuedObjectReference(abortFlag))
                                conditional.statements.add(abort.doStatements)
                                scope.statements.add(conditional)
                            }
                            statements.set(pos,scope)
                        }
                    }
                    // STRONG ABORT
                    else {
                        // e.g. "abort when immediate A"
                        if (abort.delay.isImmediate) {
                            var label = createLabel
                            var conditional = newIfThenGoto(EcoreUtil.copy(abort.delay.expression), label, false)
                            // the order of the next three lines is crucial! (first trasformStatements, add(Label), then transformPauses)
                            abort.statements.transformStatements
                            abort.statements.add(label)
                            abort.statements.transformPauses(abort, label, null, null, null)
                            scope.statements.add(conditional)
                            scope.statements.add(abort.statements)
                            if (!abort.doStatements.empty) {
                                var conditional2 = createConditional(EcoreUtil.copy(abort.delay.expression))
                                conditional2.statements.add(abort.doStatements)
                                scope.statements.add(conditional2)
                            }
                            statements.set(pos, scope)
                        }
                        // e.g. "abort when A"
                        else {
                            var abortFlag = createNewUniqueAbortFlag(createBoolValue(false))
                            var decl = createDeclaration(ValueType.BOOL, abortFlag)
                            var label = createLabel
                            scope.declarations.add(decl)
                            // the order of the next three lines is crucial! (first trasformStatements, add(Label), then transformPauses)
                            abort.statements.transformStatements
                            abort.statements.add(label)
                            abort.statements.transformPauses(abort, label, abortFlag, null, null)
                            scope.statements.add(abort.statements)
                            if (!abort.doStatements.empty) {
                                var conditional = createConditional(createValuedObjectReference(abortFlag))
                                conditional.statements.add(abort.doStatements)
                                scope.statements.add(conditional)
                            }
                            statements.set(pos,scope)
                        }
                    }
                    
                }
            }
            // ABORT CASES
            else {
                var label2 = createLabel
                var countingVariables = new LinkedList<ValuedObject>()
                var depthFlag = createNewUniqueDepthFlag(createBoolValue(false))
                
                var abortFlag = createNewUniqueAbortFlag(createBoolValue(false))
                var decl = createDeclaration(ValueType.BOOL, abortFlag)
                decl.valuedObjects.add(depthFlag)
                var label = createLabel
                var label3 = createLabel
                var label4 = createLabel
                var conditional = newIfThenGoto(createValuedObjectReference(abortFlag), label4, false)
                conditional.annotations.add(createAnnotation(depth))
                thread1.statements.add(label3)
                thread1.statements.add(conditional) 
                thread1.statements.add(createPause)
                thread1.statements.add(createAssignment(depthFlag, createBoolValue(true)))
                abort.statements.transformStatements
                scope.statements.add(label)
                for ( c : abort.cases ) {
                    c.delay.transformReferences
                    // creating counting variables for cases with an expression before the signal expression
                    if (c.delay.expression != null) {
                        var variable = createNewUniqueVariable(createIntValue(0))
                        var decl2 = createDeclaration(ValueType.INT, variable)
                        countingVariables.add(variable)
                        scope.declarations.add(decl2)  
                        var conditional2  = createConditional(EcoreUtil.copy(c.delay.expression))
                        conditional2.statements.add(incrementInt(variable))
                        thread1.statements.add(conditional2)
                    }
                    // adding if for an immediate case before the statements which should be aborted
                    else if (c.delay.immediate && !abort.weak) {
                        var conditional2 = newIfThenGoto(EcoreUtil.copy(c.delay.expression), label, false)
                        conditional2.annotations.add(createAnnotation(depth))
                        scope.statements.add(scope.statements.length-1, conditional2)
                    }
                }
                thread1.statements.add(createGotoStatement(label3))
                thread1.statements.add(label4)
                thread2.statements.add(abort.statements)
                thread2.statements.add(createLabel)
                scope.declarations.add(decl)
                scope.statements.add(scope.statements.length-1, parallel)
                thread2.statements.transformPauses(abort, label, abortFlag, depthFlag, countingVariables)

                var i = 0
                // adding the do blocks of the cases after the abort label
                for ( c : abort.cases ) {
                    var Conditional conditional2
                    if (c.delay.immediate) {
                        conditional2 = newIfThenGoto(EcoreUtil.copy(c.delay.expression), label2, false)
                    }
                    else if (c.delay.expression == null) {
                        conditional2 = newIfThenGoto(createAnd(EcoreUtil.copy(c.delay.expression), createValuedObjectReference(depthFlag)), label2, false)
                    }
                    else {
                        countingVariables.get(i)
                        conditional2 = newIfThenGoto(createGEQ(createValuedObjectReference(countingVariables.get(i)), EcoreUtil.copy(c.delay.expression)), label2, false)
                        i++
                    }
                    conditional2.statements.add(0, c.statements)
                    scope.statements.add(conditional2)
                }
                scope.statements.add(label2)
                statements.set(pos,scope)
                
            }
            statements.checkGotos
            return scope
        }
        else if (statement instanceof Present) {
            transformStatements((statement as Present).statements)
            (statement as Present).cases?.forEach[ c | transformStatements(c.statements)]
            transformStatements((statement as Present).elseStatements)
        }
        else if (statement instanceof IfTest) {
            transformStatements((statement as IfTest).statements)
            (statement as IfTest).elseif?.forEach [ elsif | transformStatements(elsif.statements)]
            transformStatements((statement as IfTest).elseStatements)
        }
        else if (statement instanceof EsterelParallel) {
            (statement as EsterelParallel).threads?.forEach [ t |
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
            (statement as Parallel).threads?.forEach [ t |
                transformStatements(t.statements)
            ]
        }
        else if (statement instanceof Run) {
            statement.module?.module?.statements.transformStatements    
        }
        return statement
    }
    
    def EList<Statement> transformPauses(EList<Statement> statements, Abort abort, Label label, ValuedObject abortFlag, ValuedObject depthFlag, List<ValuedObject> countingVariables) {
        for (var i=0; i<statements?.length; i++) {
            var offsetI = statements.get(i).transformPause(abort, label, abortFlag, depthFlag, countingVariables)
            i += offsetI
            // the offset is used to prevent the indefinite transformation of one specific pause
            // for example for a "weak delayed abort" an if statement is added before the pause and 
            // therefore in the next cycle without the offset the pause would be transformed again and again ... 
        }
        return statements
    }
    
    def int transformPause(Statement statement, Abort abort, Label label, ValuedObject abortFlag, ValuedObject depthFlag, List<ValuedObject> countingVariables) {
        if (statement instanceof Pause) {
            var statements =  statement.getContainingList
            var pos = statements.indexOf(statement)
            var label2 = findClosestLabel(label, statement)
            var depth = abort.getDepth
            
            // ABORT CASES
            if (abort.delay == null) {
                return transformCases(statements, statement, abort, label2, abortFlag, depthFlag, countingVariables)
            }
            // WEAK ABORT
            if (abort.weak) {
                // e.g. "weak abort immediate A"
                if (abort.delay.isImmediate) {
                    if (label2 != null) {
                        var conditional = newIfThenGoto(abort.delay.expression, label2, false)
                        conditional.annotations.add(createAnnotation(depth))
                        conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                        insertConditionalAbove(statements, conditional, pos, depth)
                        return 1
                    }
                }
                else {
                    // e.g. "weak abort when A"
                    if (abort.delay.expression == null && label2 != null) {
                        var expr = createAnd(EcoreUtil.copy(abort.delay.expression), createValuedObjectReference(depthFlag))
                        var conditional = newIfThenGoto(expr, label2, false)
                        conditional.annotations.add(createAnnotation(depth))
                        conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                        statements.add(pos+1, createAssignment(depthFlag, createBoolValue(true)))
                        insertConditionalAbove(statements, conditional, pos, depth)
                        return 2
                    }
                    // e.g. "weak abort when 3 A"
                    else {
                        if (label2 != null) {
                            var GEQExpr = createGEQ(createValuedObjectReference(countingVariables.get(0)), EcoreUtil.copy(abort.delay.expression))
                            var conditional = newIfThenGoto(GEQExpr, label2, false)
                            conditional.annotations.add(createAnnotation(depth))
                            conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                            insertConditionalAbove(statements, conditional, pos, depth)
                            return 1
                        }
                    }
                }
            }
            // STRONG ABORT
            else {
                // e.g. "abort immediate A"
                if (abort.delay.isImmediate) {
                    if (label2 != null) {
                        var conditional = newIfThenGoto(abort.delay.expression, label2, false)
                        conditional.annotations.add(createAnnotation(depth))
                        insertConditional(statements, conditional, pos, depth)
                    }
                }
                else {
                    // e.g. "abort when A"
                    if (abort.delay.expression == null && label2 != null) {
                        var conditional = newIfThenGoto(abort.delay.expression, label2, false)
                        conditional.annotations.add(createAnnotation(depth))
                        conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                        insertConditional(statements, conditional, pos, depth)
                    }
                    // e.g. "abort when 3 A"
                    else {
                        if (label2 != null) {
                            var GEQExpr = createGEQ(createValuedObjectReference(countingVariables.get(0)), EcoreUtil.copy(abort.delay.expression))
                            var conditional = newIfThenGoto(GEQExpr, label2, false)
                            conditional.annotations.add(createAnnotation(depth))
                            conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                            insertConditional(statements, conditional, pos, depth)
                        }
                    }
                }
                return 0
            }
            
            return 0
        }
        
        else if (statement instanceof EsterelParallel) {
            (statement as EsterelParallel).threads?.forEach [ t |
                transformPauses(t.statements, abort, label, abortFlag, depthFlag, countingVariables)
            ]
            transformJoin(statement, abort, label, abortFlag)
        }
        else if (statement instanceof Parallel) {
            (statement as Parallel).threads?.forEach [ t |
                transformPauses(t.statements, abort, label, abortFlag, depthFlag, countingVariables)
            ]
            transformJoin(statement, abort, label, abortFlag)
            
        }
        else if (statement instanceof Present) {
            transformPauses((statement as Present).statements, abort, label, abortFlag, depthFlag, countingVariables)
            (statement as Present).cases?.forEach[ c | transformPauses(c.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            transformPauses((statement as Present).elseStatements, abort, label, abortFlag, depthFlag, countingVariables)
        }
        else if (statement instanceof IfTest) {
            transformPauses((statement as IfTest).statements, abort, label, abortFlag, depthFlag, countingVariables)
            (statement as IfTest).elseif?.forEach [ elsif | transformPauses(elsif.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            transformPauses((statement as IfTest).elseStatements, abort, label, abortFlag, depthFlag, countingVariables)
        }
        else if (statement instanceof StatementContainer) {
            if (!(statement instanceof Conditional)) {
                transformPauses((statement as StatementContainer).statements, abort, label, abortFlag, depthFlag, countingVariables)
            }
            
            if (statement instanceof Trap) {
                (statement as Trap).trapHandler?.forEach[h | transformPauses(h.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            }
            else if (statement instanceof Abort) {
                transformPauses((statement as Abort).doStatements, abort, label, abortFlag, depthFlag, countingVariables)
                (statement as Abort).cases?.forEach[ c | transformPauses(c.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            }
            else if (statement instanceof Await) {
                (statement as Await).cases?.forEach[ c | transformPauses(c.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            }
            else if (statement instanceof Exec) {
                (statement as Exec).execCaseList?.forEach[ c | transformPauses(c.statements, abort, label, abortFlag, depthFlag, countingVariables)]
            }
            else if (statement instanceof Do) {
                transformPauses((statement as Do).watchingStatements, abort, label, abortFlag, depthFlag, countingVariables)
            }
            else if (statement instanceof Conditional) {
                // Don't transform the pauses in generated Conditionals.
                var annotations = (statement as Conditional).annotations
                if (!isGenerated(annotations)) {
                    transformPauses((statement as StatementContainer).statements, abort, label, abortFlag, depthFlag, countingVariables)
                    transformPauses((statement as Conditional).getElse()?.statements, abort, label, abortFlag, depthFlag, countingVariables)
                }
            }
        }
        else if (statement instanceof Run) {
            transformPauses(statement.module?.module?.statements, abort, label, abortFlag, depthFlag, countingVariables)    
        }
        return 0
    }
    
    def void transformJoin(Statement statement, Abort abort, Label label, ValuedObject flag) {
        var statements =  statement.getContainingList
        var pos = statements.indexOf(statement)
        var conditional = newIfThenGoto(abort.delay?.expression, findClosestLabel(label, statement), false)
        conditional.annotations.add(createAnnotation(getDepth(abort)))
        if (abort.delay?.isImmediate && !abort.weak) {
            // just for an immediate strong abort
            conditional = newIfThenGoto(abort.delay?.expression, findClosestLabel(label, statement), false)
        } 
        else {
            conditional = newIfThenGoto(createValuedObjectReference(flag), findClosestLabel(label, statement), false)
        }
        insertConditional(statements, conditional, pos, getDepth(abort))
    }
    
    def int transformCases(EList<Statement> statements, Statement statement, Abort abort, Label label, ValuedObject abortFlag, ValuedObject depthFlag, List<ValuedObject> countingVariables) {
        var pos = statements.indexOf(statement)
        var countConditionals = 0
        var i = 0
        for (c : abort.cases) {
            // e.g. "case 3 A"
            if (c.delay.expression != null) {
                var conditional = newIfThenGoto(createGEQ(createValuedObjectReference(countingVariables.get(i)), EcoreUtil.copy(c.delay.expression)), label, false)
                conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                conditional.annotations.add(createAnnotation(getDepth(abort)))
                if (abort.weak) {
                    insertConditionalAbove(statements, conditional, pos, abort.getDepth)
                    pos++
                }
                else {
                    insertConditional(statements, conditional, pos, getDepth(abort))
                }
                i++
                countConditionals++
            }
            // e.g. "case immediate A" or "case A"
            else {
                var conditional = newIfThenGoto(EcoreUtil.copy(c.delay.expression), label, false)
                if (!c.delay.immediate) {
                    conditional = newIfThenGoto(createAnd(EcoreUtil.copy(c.delay.expression), createValuedObjectReference(depthFlag)), label, false)
                }
                else {
                    conditional = newIfThenGoto(EcoreUtil.copy(c.delay.expression), label, false)
                }
                conditional.statements.add(0, createAssignment(abortFlag, createBoolValue(true)))
                conditional.annotations.add(createAnnotation(getDepth(abort)))
                if (abort.weak) {
                    insertConditionalAbove(statements, conditional, pos, abort.getDepth)
                    pos++
                }
                else {
                    insertConditional(statements, conditional, pos, abort.getDepth)
                }
                countConditionals++
            }
        }
        return countConditionals+1
    }
    
}