/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.legacy.annotations;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class AnnotationsStandaloneSetup extends AnnotationsStandaloneSetupGenerated{

	public static void doSetup() {
		new AnnotationsStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
