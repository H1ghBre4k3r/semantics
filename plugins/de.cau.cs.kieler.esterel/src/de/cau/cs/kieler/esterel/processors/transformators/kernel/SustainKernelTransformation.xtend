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
package de.cau.cs.kieler.esterel.processors.transformators.kernel

import de.cau.cs.kieler.esterel.EsterelFactory
import de.cau.cs.kieler.esterel.EsterelProgram
import de.cau.cs.kieler.esterel.Sustain
import de.cau.cs.kieler.kicool.compilation.InplaceProcessor
import de.cau.cs.kieler.kicool.kitt.tracing.Traceable
import de.cau.cs.kieler.scl.SCLFactory

import static extension de.cau.cs.kieler.kicool.kitt.tracing.TransformationTracing.*
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

/**
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
class SustainKernelTransformation extends InplaceProcessor<EsterelProgram> implements Traceable {

    public static val ID = "de.cau.cs.kieler.esterel.processors.transformators.kernel.sustain"

    // -------------------------------------------------------------------------
    // --                 K I C O      C O N F I G U R A T I O N              --
    // -------------------------------------------------------------------------
    override getId() {
        return ID
    }

    override getName() {
        return "Sustain"
    }
    
    override process() {
        // TODO custom iterator
        model.eAllContents.filter(Sustain).toList.forEach[transform]
    }
    
    // -------------------------------------------------------------------------
    // -- Injections 
    // -------------------------------------------------------------------------
    
    extension EsterelFactory eFac = EsterelFactory.eINSTANCE
    extension SCLFactory = SCLFactory.eINSTANCE
    
    def transform(Sustain sus) {
        sus.replace(eFac.createLoop.trace(sus) => [
            statements += createEmit.trace(sus) => [signal = sus.signal]
            statements += createPause.trace(sus)
        ])
    }
    
}