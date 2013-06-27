package de.cau.cs.kieler.scl.validation;
 
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.yakindu.sct.model.stext.validation.STextJavaValidator;
import org.eclipse.xtext.validation.ComposedChecks;

@ComposedChecks(validators= {org.eclipse.xtext.validation.ImportUriValidator.class, org.eclipse.xtext.validation.NamesAreUniqueValidator.class})
public class AbstractSCLJavaValidator extends STextJavaValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(de.cau.cs.kieler.scl.scl.SclPackage.eINSTANCE);
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.yakindu.org/sct/sgraph/2.0.0"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.yakindu.org/base/base/2.0.0"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.yakindu.org/base/types/2.0.0"));
		return result;
	}

}
