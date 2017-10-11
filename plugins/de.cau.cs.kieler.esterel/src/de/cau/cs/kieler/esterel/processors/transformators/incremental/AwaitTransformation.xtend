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
import de.cau.cs.kieler.scl.Conditional
import de.cau.cs.kieler.scl.Label
import de.cau.cs.kieler.scl.Parallel
import de.cau.cs.kieler.scl.Statement
import de.cau.cs.kieler.scl.StatementContainer
import java.util.LinkedList
import org.eclipse.emf.common.util.EList

/**
 * @author mrb
 *
 */
class AwaitTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::AWAIT_ID
    }

    override getName() {
        return SCEstTransformation::AWAIT_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::AWAIT_ID
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
        if (statement instanceof Await) {
            var await = statement as Await
            var statements =  statement.getContainingList
            var pos = statements.indexOf(statement)
            
            if (await.delay != null) {
                var label = createLabel(createNewUniqueLabel)
                var variable = createNewUniqueVariable(createIntValue(0))
                var decl = createDeclaration(ValueType.INT, variable)
                await.delay.transformReferences
                if (await.delay.expression != null) {
                    var scope = createScopeStatement(decl)
                    statements.set(pos, scope)
                    var lt = createLT(createValuedObjectReference(variable), await.delay.expression)
                    var conditional = createConditional(await.delay.expression)
                    conditional.statements.add(incrementInt(variable))
                    conditional.annotations.add(createAnnotation(0))
                    var conditional2 = newIfThenGoto(lt, label, false)
                    await.annotations.copyAnnotations(conditional2)
                    scope.statements.add(label)
                    scope.statements.add(createPause)
                    scope.statements.add(conditional)
                    scope.statements.add(conditional2)
                    if (await.statements != null) {
                        statements.add(pos+1, await.statements)
                    }
                   /* 
                    *  if "await immediate 5 A" would be allowed
                    *  at the moment there can only be one of 'immediate' OR 'Expression' before the signal expression
                    * 
                    scope.statements.add(label)
                    if (await.delay.isIsImmediate) {
                        scope.statements.add(conditional)
                        scope.statements.add(createPause)
                    }
                    else {
                        scope.statements.add(createPause)
                        scope.statements.add(conditional)
                    }
                    scope.statements.add(conditional2)
                    */
                }
                else {
                    if (await.delay.immediate) {
                        statements.set(pos, label)
                        statements.add(pos+1, newIfThenGoto(createNot(await.delay.expression), label, true))
                        if (await.statements != null) {
                            statements.add(pos+2, await.statements)
                        }
                    }
                    else {
                        statements.set(pos, label)
                        statements.add(pos+1, createPause)
                        statements.add(pos+2, newIfThenGoto(createNot(await.delay.expression), label, false))
                        if (await.statements != null) {
                            statements.add(pos+3, await.statements)
                        }
                    }
                }
            }
            else {
                // cases
                
                var cases = await.cases
                var scope = createScopeStatement(null)
                var startLabel = createLabel
                var endLabel = createLabel
                scope.statements.add(startLabel)
                var LinkedList<Pair<Label, Conditional>> immediateLabels = new LinkedList()
                var label = startLabel
                var nextLabel = createLabel
                for (var i=0; i<cases.length; i++) {
                    var c = cases.get(i)
                    if (c.delay != null) {
                        c.delay.transformReferences
                        if (c.delay.expression != null) {
                            var variable = createNewUniqueVariable(createIntValue(0))
                            var decl = createDeclaration(ValueType.INT, variable)
                            scope.declarations.add(decl)
                            var conditional = createConditional(c.delay.expression)
                            conditional.statements.add(incrementInt(variable))
                            var lt = createLT(createValuedObjectReference(variable), c.delay.expression)
                            var conditional2 = newIfThenGoto(lt, nextLabel, false)
                            scope.statements.add(1, conditional)
                            scope.statements.add(conditional2)
                            if (c.statements != null) {
                                c.statements.transformStatements 
                                scope.statements.add(c.statements)
                            }
                            scope.statements.add(createGotoStatement(endLabel))
                            scope.statements.add(nextLabel)
                            if (i+1<cases.length) {
                                label = nextLabel
                                nextLabel = createLabel
                            }
                        }
                        else {
                            var conditional = newIfThenGoto(createNot(c.delay.expression), nextLabel, false)
                            scope.statements.add(conditional)
                            if (c.delay.immediate) {
                                immediateLabels.add(new Pair(label, conditional))
                            }
                            if (c.statements != null) {
                                c.statements.transformStatements 
                                scope.statements.add(c.statements)
                            }
                            scope.statements.add(createGotoStatement(endLabel))
                            scope.statements.add(nextLabel)
                            if (i+1<cases.length) {
                                label = nextLabel
                                nextLabel = createLabel(createNewUniqueLabel)
                            }
                        }
                    }
                }
                if (!immediateLabels.isEmpty) {
                    var flag = createNewUniqueFlag(createBoolValue(true))
                    var decl = createDeclaration(ValueType.BOOL, flag)
                    scope.declarations.add(decl)
                    scope.statements.add(0, createGotoStatement(immediateLabels.get(0).key))
                    for (var i=0; i<immediateLabels.length; i++) {
                        if (i+1<immediateLabels.length) {
                            immediateLabels.get(i).value.statements.add(
                                0, newIfThenGoto(createValuedObjectReference(flag), immediateLabels.get(i+1).key, false)
                            )
                        }
                        else {
                            // set 'variable' to false, since the immediate run is over
                            // now just the normal order of the cases counts
                            var conditional = newIfThenGoto(createValuedObjectReference(flag), nextLabel, false)
                            conditional.statements.add(0,createAssignment(flag, createBoolValue(false)))
                            immediateLabels.get(i).value.statements.add(0, conditional)
                        }
                    }
                }
                scope.statements.add(createPause)
                scope.statements.add(createGotoStatement(startLabel))
                scope.statements.add(endLabel)
                statements.set(pos, scope)
            }
            
            
            
            
            return null
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