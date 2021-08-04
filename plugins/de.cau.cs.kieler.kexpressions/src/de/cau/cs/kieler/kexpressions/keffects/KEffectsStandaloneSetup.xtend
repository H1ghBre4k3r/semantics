/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kexpressions.keffects

import com.google.inject.Injector

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 * 
 * @author sdo
 */
class KEffectsStandaloneSetup extends KEffectsStandaloneSetupGenerated {
	
    static Injector injector
    
    def static doSetup() {
        if (injector === null) {
            injector = new KEffectsStandaloneSetup().createInjectorAndDoEMFRegistration()
        }
        return injector
    }
    
    override register(Injector injector) {
        super.register(injector)
        // Ensure package is registered 
        KEffectsPackage.eINSTANCE.eClass()
    }
}
