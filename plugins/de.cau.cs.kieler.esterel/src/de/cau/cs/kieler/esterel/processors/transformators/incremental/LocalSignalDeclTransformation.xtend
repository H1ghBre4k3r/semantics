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
import de.cau.cs.kieler.esterel.LocalSignalDeclaration
import de.cau.cs.kieler.esterel.Signal
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.esterel.extensions.NewSignals
import de.cau.cs.kieler.kexpressions.CombineOperator
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.scl.Label
import de.cau.cs.kieler.scl.ScopeStatement
import de.cau.cs.kieler.scl.Thread
import java.util.HashMap

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * @author mrb
 *
 */
class LocalSignalDeclTransformation extends AbstractSCEstDynamicProcessor<LocalSignalDeclaration> {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    
    public static val ID = "de.cau.cs.kieler.esterel.processors.localsignal"
    
    override getId() {
        return ID
    }

    override getName() {
        return "LocalSignal"
    }
    
    @Inject
    extension EsterelTransformationExtensions
    
    override transform(LocalSignalDeclaration localSignals) {
        // for valued singals: signal S will be transformed to s, s_set, s_cur, s_val => new NewSignals(s, s_set, s_cur, s_val)
        val HashMap<Signal, NewSignals> signalsMap = new HashMap<Signal, NewSignals>()
        var scope = localSignals.statements.createScopeStatement
        for (signal : localSignals.valuedObjects.filter(Signal)) {
            val s = createSignalVariable(createFalse, null, null)
            var decl = createDeclaration(ValueType.BOOL, s)
            voStore.update(s, "signal")
            var decl2 = createDeclaration(null, null)
            if (signal.type !== null) {
                scope.declarations.add(decl)
                if (signal.type == ValueType.PURE) {
                    signalsMap.put(signal, new NewSignals(s))
                }
                else {
                    val s_set = createSignalVariable(createFalse, null, s.name + "_set")
                    decl.valuedObjects.add(s_set)
                    val s_val = createSignalVariable(signal.initialValue, signal.combineOperator, s.name + "_val")
                    val s_cur = createSignalVariable(null, signal.combineOperator, s.name + "_cur")
                    val tempType = if (signal.type == ValueType.DOUBLE) ValueType.FLOAT else signal.type
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
        localSignals.replace(scope)
        lastStatement = scope
        transformReferences(scope, signalsMap)
    }
    
    def createParallelForSignals(ScopeStatement scope, HashMap<Signal, NewSignals> signalsMap) {
        val signals = signalsMap.keySet.iterator.toList
        val term = createNewUniqueTermFlag(createFalse)
        val decl = createDeclaration(ValueType.BOOL, term)
        val parallel = createParallel
        val thread2 = createThread
        parallel.threads.add(thread2)
        thread2.statements.addAll(scope.statements)
        thread2.statements.add(createAssignment(term, createTrue))
        scope.statements.add(parallel)
        scope.declarations.add(decl)
        
        // tempThread statements: the initializations of the signals
        var Label tempLabel
        var Thread tempThread
        for (signal : signals) {
            tempLabel = createLabel
            tempThread = createThread
            tempThread.statements.add(tempLabel)
            parallel.threads.add(tempThread)
            val keyValue = signalsMap.get(signal)
            val s = keyValue.s
            // if no combineOperator exists, handle valued signal like Karsten Rathlev did in his master thesis
            if (signal.type != ValueType.PURE && signal.combineOperator !== null && signal.combineOperator != CombineOperator.NONE) {
                val s_set = keyValue.s_set
                val s_cur = keyValue.s_cur
                val s_val = keyValue.s_val
                val assign1 = createAssignment(s, createFalse)
                tempThread.statements.add(assign1)
                val assign2 = createAssignment(s_set, createFalse)
                tempThread.statements.add(assign2)
                val conditional1 = createConditional(createNot(createValuedObjectReference(s_set)))
                val assign3 = createAssignment(s_cur, getNeutral(s_cur.combineOperator))
                conditional1.statements.add(assign3)
                tempThread.statements.add(conditional1)
                val conditional2 = createConditional(createValuedObjectReference(s))
                val assign4 = createAssignment(s_val, createValuedObjectReference(s_cur))
                conditional2.statements.add(assign4)
                tempThread.statements.add(conditional2)
            }
            else {
                val assign1 = createAssignment(s, createFalse)
                tempThread.statements.add(assign1)
            }
            val conditional = newIfThenGoto(createNot(createValuedObjectReference(term)), tempLabel, true)
            tempThread.statements.add(conditional)
        }
    }
    
}