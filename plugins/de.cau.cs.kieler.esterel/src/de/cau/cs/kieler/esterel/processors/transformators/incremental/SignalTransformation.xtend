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
import de.cau.cs.kieler.esterel.EsterelProgram
import de.cau.cs.kieler.esterel.LocalSignalDeclaration
import de.cau.cs.kieler.esterel.OutputDeclaration
import de.cau.cs.kieler.esterel.Signal
import de.cau.cs.kieler.esterel.SignalDeclaration
import de.cau.cs.kieler.esterel.extensions.EsterelExtensions
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.esterel.extensions.NewSignals
import de.cau.cs.kieler.esterel.processors.EsterelProcessor
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.scl.Module
import de.cau.cs.kieler.scl.ScopeStatement
import java.util.HashMap
import java.util.List

/**
 * @author mrb
 *
 */
class  SignalTransformation extends EsterelProcessor {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return SCEstTransformation::SIGNAL_ID
    }

    override getName() {
        return SCEstTransformation::SIGNAL_NAME
    }

//    override getExpandsFeatureId() {
//        return SCEstFeature::SIGNAL_ID
//    }
//    
//    override getNotHandlesFeatureIds() {
//        return Sets.newHashSet(SCEstTransformation::INITIALIZATION_ID, SCEstTransformation::RUN_ID)
//    }

    @Inject
    extension EsterelTransformationExtensions
    @Inject
    extension EsterelExtensions
    
    /*
     *  TODO only the signals with type != null will be transformed
     *  signals with typeID != null are not handled
     *  SCL doesn't allow anything else then the predefined types
     *  see KExt.xtext => Declaration
    */
    
    /*
     * This transformation has to be done before the local signal transformation because the local signal transformation
     * writes also in the end its signals to "newSignals" in EsterelTransformationExtensions.
     * Therefore this transformation would also iterate over the local signals when "createParallelForSignals" is called.
     */
    
    override EsterelProgram transform(EsterelProgram prog) {
        for (m : prog.modules) { 
            m.signalDeclarations.toList.transformSignals(m)
//            transformStatements(m.statements)
//            m.intSignalDecls.clear
        }
        return prog
    }
    
    def transformSignals(List<SignalDeclaration> signalDecl, Module module) {
        var ScopeStatement scope = module.getIScope
        for (interfaceSD : signalDecl) {
            for (signal : interfaceSD.signals) {
                var s = createSignalVariable(createFalse, null, signal.name)
                signal.name = signal.name.createNewUniqueSignalName
                var decl = createDeclaration(ValueType.BOOL, s)
                var decl2 = createDeclaration(null, null)
                if (signal.type != null) {
                    scope.declarations.add(decl)
                    if (signal.type == ValueType.PURE) {
                        newSignals.put(signal, new NewSignals(s))
                    }
                    else {
                        var s_set = createSignalVariable(createFalse, null, s.name + "_set")
                        decl.valuedObjects.add(s_set)
                        var s_cur = createSignalVariable(null, signal.combineOperator, s.name + "_cur")
                        var s_val = createSignalVariable(signal.initialValue, signal.combineOperator, s.name + "_val")
                        var tempType = if (signal.type == ValueType.DOUBLE) ValueType.FLOAT else signal.type
                        decl2 = createDeclaration(tempType, null)
                        decl2.valuedObjects.add(s_cur)
                        decl2.valuedObjects.add(s_val)
                        newSignals.put(signal, new NewSignals(s, s_set, s_cur, s_val))
                        scope.declarations.add(decl2)
                    }
                }
                else { // shouldn't be possible
                    throw new UnsupportedOperationException(
                        "The following signal doesn't have a type! " + s.name)
                }

                decl.input = interfaceSD.input
                decl.output = interfaceSD.input
                decl2.input = interfaceSD.output
                decl2.output = interfaceSD.output
            }
        }
        createParallelForSignals(scope, newSignals)
//        scope.transformReferences
    }
    
    def createParallelForSignals(ScopeStatement scope, HashMap<Signal, NewSignals> signalsMap) {
        var necessary = false
        var signals = signalsMap.keySet.iterator.toList
        var i = 0
        while (!necessary && i<signals.length) {
            var Signal signal = signals.get(i)
            necessary = necessary || (signal.type != ValueType.PURE) || 
                            (signal.eContainer instanceof OutputDeclaration) || (signal.eContainer instanceof LocalSignalDeclaration)
            i++
        }
        if (necessary) {
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
            
            // thread1 statements: the initializations of the output signals
            var label = createLabel
            thread1.statements.add(label)
            for (signal : signals) {
                var keyValue = signalsMap.get(signal)
                var s = keyValue.s
                if (signal.type != ValueType.PURE) {
                    var s_set = keyValue.s_set
                    var s_cur = keyValue.s_cur
                    var s_val = keyValue.s_val
                    if (signal.eContainer instanceof OutputDeclaration) {
                        var assign1 = createAssignment(s, createFalse)
                        thread1.statements.add(assign1)
                    }
                    else if (signal.eContainer instanceof LocalSignalDeclaration) {
                        var assign1 = createAssignment(s, createFalse)
                        thread1.statements.add(assign1)
                    }
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
                else if (signal.eContainer instanceof OutputDeclaration) {
                    var assign1 = createAssignment(s, createFalse)
                    thread1.statements.add(assign1)
                }
                else if (signal.eContainer instanceof LocalSignalDeclaration) {
                    var assign1 = createAssignment(s, createFalse)
                    thread1.statements.add(assign1)
                }
            }
            var conditional = newIfThenGoto(createNot(createValuedObjectReference(term)), label, true)
            thread1.statements.add(conditional)
        }
    }
    
}