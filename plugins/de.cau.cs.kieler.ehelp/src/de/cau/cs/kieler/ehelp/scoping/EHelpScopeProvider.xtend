/*
 * generated by Xtext 2.13.0
 */
package de.cau.cs.kieler.ehelp.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import de.cau.cs.kieler.ehelp.eHelp.Chapter
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.scoping.Scopes
import de.cau.cs.kieler.ehelp.eHelp.Link

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class EHelpScopeProvider extends de.cau.cs.kieler.ehelp.scoping.AbstractEHelpScopeProvider {

    override getScope(EObject context, EReference reference) {
        if (context instanceof Link) {
        	val rootElement = EcoreUtil2.getRootContainer(context)
        	val candidates = EcoreUtil2.getAllContentsOfType(rootElement, Chapter)
	        // Create IEObjectDescriptions and puts them into an IScope instance
        	return Scopes.scopeFor(candidates)        	
        }
        return super.getScope(context, reference);
    }

}