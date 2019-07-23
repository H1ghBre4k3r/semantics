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
package de.cau.cs.kieler.sccharts.text

import com.google.inject.Injector

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 * 
 * @author sdo
 */
class SCTXStandaloneSetup extends SCTXStandaloneSetupGenerated {
    
    protected static var Injector injector

    /**
     * Used by LS to override previously created injector, if the current injector does not contain an SCTXIdeSetup
     */
    public static var boolean force
	def static doSetup() {
	    if (injector === null || force) {
	        injector = new SCTXStandaloneSetup().createInjectorAndDoEMFRegistration()
	    }
	    return injector
	}
}
