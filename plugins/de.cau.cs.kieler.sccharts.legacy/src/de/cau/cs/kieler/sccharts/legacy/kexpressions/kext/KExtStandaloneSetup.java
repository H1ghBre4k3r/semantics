/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.legacy.kexpressions.kext;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class KExtStandaloneSetup extends KExtStandaloneSetupGenerated{

	public static void doSetup() {
		new KExtStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
