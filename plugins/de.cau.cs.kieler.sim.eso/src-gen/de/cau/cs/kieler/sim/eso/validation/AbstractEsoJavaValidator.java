/*
* generated by Xtext
*/
package de.cau.cs.kieler.sim.eso.validation;
 
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

public class AbstractEsoJavaValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(de.cau.cs.kieler.sim.eso.eso.EsoPackage.eINSTANCE);
		return result;
	}

}