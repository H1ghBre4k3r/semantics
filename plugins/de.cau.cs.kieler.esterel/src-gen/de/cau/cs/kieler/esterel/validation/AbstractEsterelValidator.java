/*
 * generated by Xtext
 */
package de.cau.cs.kieler.esterel.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.ComposedChecks;

@ComposedChecks(validators= {org.eclipse.xtext.validation.ImportUriValidator.class})
public class AbstractEsterelValidator extends de.cau.cs.kieler.esterel.kexpressions.validation.KExpressionsValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
	    result.add(de.cau.cs.kieler.esterel.esterel.EsterelPackage.eINSTANCE);
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://kieler.cs.cau.de/esterel/kexpressions/0.1.2"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/emf/2002/Ecore"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://kieler.cs.cau.de/annotations"));
		return result;
	}
}