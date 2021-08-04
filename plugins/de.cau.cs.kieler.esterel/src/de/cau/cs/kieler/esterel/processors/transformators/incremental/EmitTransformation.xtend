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
import de.cau.cs.kieler.esterel.Emit
import de.cau.cs.kieler.esterel.Signal
import de.cau.cs.kieler.esterel.extensions.EsterelTransformationExtensions
import de.cau.cs.kieler.kexpressions.CombineOperator
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.scl.Assignment

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * @author mrb
 *
 */
class EmitTransformation extends AbstractSCEstDynamicProcessor<Emit> {
    
    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    
    public static val ID = "de.cau.cs.kieler.esterel.processors.emit"
    
    override getId() {
        return ID
    }

    override getName() {
        return "Emit"
    }
    
    @Inject
    extension EsterelTransformationExtensions
        
    override transform(Emit emit) {
        var signal = emit.signal as Signal
        if (emit.expression === null && signal.type != ValueType.PURE) {
            throw new UnsupportedOperationException("The following signal is a valued signal. 
                                    Thus a non valued emit is invalid! " + signal.toString)
        }
        val statements = emit.getContainingList
        val pos = statements.indexOf(emit)
        val expr = createOr(signal.createSignalReference, createTrue)
        val assign = createAssignment(signal.createSignalReference, expr)
        emit.replace(assign)
        if (emit.expression !== null) {
            if (signal.type != ValueType.PURE) {
                var Assignment assign2
                // if no combineOperator exists, handle valued signal like Karsten Rathlev did in his master thesis
                if (signal.combineOperator === null || signal.combineOperator == CombineOperator.NONE) {
                    assign2 = createValAssignment(signal.createSignalReference, emit.expression)
                }
                // otherwise, if combineOperator exists, handle valued signal like he did in the paper:
                // memocode 15 -> SCEst: Sequentially Constructive Esterel
                else {
                    assign2 = createCurAssignment(signal.createSignalReference, 
                        createOperatorExpression(signal.createSignalReference, 
                            emit.expression, signal.combineOperator.getOperator
                        ))
                }
                statements.add(pos+1, assign2)
                lastStatement = assign2
            } else {
                throw new UnsupportedOperationException("The following signal is not a valued signal! Thus a valued emit is invalid! " + signal.toString)
            }
        }
        else {
            lastStatement = assign
        }
    }
    
}