/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.text.formatting2;

import com.google.inject.Inject;
import de.cau.cs.kieler.annotations.Annotation;
import de.cau.cs.kieler.annotations.Pragma;
import de.cau.cs.kieler.kexpressions.Declaration;
import de.cau.cs.kieler.kexpressions.Expression;
import de.cau.cs.kieler.kexpressions.Parameter;
import de.cau.cs.kieler.kexpressions.keffects.Assignment;
import de.cau.cs.kieler.kexpressions.keffects.Effect;
import de.cau.cs.kieler.kexpressions.kext.formatting2.KExtFormatter;
import de.cau.cs.kieler.sccharts.ControlflowRegion;
import de.cau.cs.kieler.sccharts.DataflowRegion;
import de.cau.cs.kieler.sccharts.DuringAction;
import de.cau.cs.kieler.sccharts.EntryAction;
import de.cau.cs.kieler.sccharts.ExitAction;
import de.cau.cs.kieler.sccharts.LocalAction;
import de.cau.cs.kieler.sccharts.PrecedingAction;
import de.cau.cs.kieler.sccharts.Region;
import de.cau.cs.kieler.sccharts.SCCharts;
import de.cau.cs.kieler.sccharts.ScopeCall;
import de.cau.cs.kieler.sccharts.State;
import de.cau.cs.kieler.sccharts.SucceedingAction;
import de.cau.cs.kieler.sccharts.SuspendAction;
import de.cau.cs.kieler.sccharts.Transition;
import de.cau.cs.kieler.sccharts.text.services.SCTXGrammarAccess;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.emf.ecore.EObject
import de.cau.cs.kieler.sccharts.SCChartsPackage

class SCTXFormatter extends KExtFormatter {
	
	@Inject extension SCTXGrammarAccess

	def dispatch void format(SCCharts sccharts, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Pragma pragmas : sccharts.getPragmas()) {
		    pragmas.append[ setNewLines(1) ]
			format(pragmas, document);
		}

		for (State rootStates : sccharts.getRootStates()) {
			format(rootStates, document);
		}
	}

	def dispatch void format(State state, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Annotation annotations : state.getAnnotations()) {
			format(annotations, document);
		}
		
        state.regionFor.keyword("{")?.prepend[ oneSpace ].append[ newLine ]
        state.regionFor.keywordPairs("{", "}").head?.interior[ indent ]
        state.regionFor.keyword("}")?.prepend[ newLine ].append[ newLine ]
		
		var EObject lastObject = null
		for (Declaration declarations : state.getDeclarations()) {
			format(declarations, document);
			lastObject = declarations
		}
		for (LocalAction actions : state.getActions()) {
			format(actions, document);
			lastObject = actions
		}
		
		var emptyLines = 1
		for (Region regions : state.getRegions()) {
		    val eL = emptyLines
  		    switch (regions) {
   	           ControlflowRegion: regions.regionFor.keyword(controlflowRegionAccess.regionKeyword_2).prepend[ setNewLines(eL) ]
   	           DataflowRegion: regions.regionFor.keyword(dataflowRegionAccess.dataflowKeyword_2).prepend[ setNewLines(eL) ]
   	        } 
			format(regions, document);
			emptyLines = 2
		}
		format(state.getReference(), document);
		
        if (lastObject != null) {
            lastObject.append[ setNewLines(2) highPriority ]
        }
		
		for (Transition outgoingTransitions : state.getOutgoingTransitions()) {
		    outgoingTransitions.prepend[ newLine ]
			format(outgoingTransitions, document);
		}
	}

	def dispatch void format(ScopeCall scopecall, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Parameter parameters : scopecall.getParameters()) {
			format(parameters, document);
		}
	}

	def dispatch void format(Transition transition, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Annotation annotations : transition.getAnnotations()) {
			format(annotations, document);
		}
		format(transition.getTrigger(), document);
		for (Effect effects : transition.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(EntryAction entryaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(entryaction.getTrigger(), document);
		for (Effect effects : entryaction.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(DuringAction duringaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(duringaction.getTrigger(), document);
		for (Effect effects : duringaction.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(ExitAction exitaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(exitaction.getTrigger(), document);
		for (Effect effects : exitaction.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(SuspendAction suspendaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(suspendaction.getTrigger(), document);
	}

	def dispatch void format(PrecedingAction precedingaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(precedingaction.getTrigger(), document);
		for (Effect effects : precedingaction.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(SucceedingAction succeedingaction, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		format(succeedingaction.getTrigger(), document);
		for (Effect effects : succeedingaction.getEffects()) {
			format(effects, document);
			effects.append[ noSpace ]
		}
	}

	def dispatch void format(ControlflowRegion controlflowregion, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc.
	
		controlflowregion.regionFor.keyword(":").prepend[ noSpace ].append[ setNewLines(2) ]
		 
		for (Annotation annotations : controlflowregion.getAnnotations()) {
			format(annotations, document);
		}
		for (Declaration declarations : controlflowregion.getDeclarations()) {
			format(declarations, document);
		}
		for (State states : controlflowregion.getStates()) {
		    states.prepend[ setNewLines(2) ]
			format(states, document);
		}
	}

	def dispatch void format(DataflowRegion dataflowregion, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Assignment equations : dataflowregion.getEquations()) {
			format(equations, document);
		}
		for (Annotation annotations : dataflowregion.getAnnotations()) {
			format(annotations, document);
		}
		for (Declaration declarations : dataflowregion.getDeclarations()) {
			format(declarations, document);
		}
	}

	override dispatch void format(Assignment assignment, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (Annotation annotations : assignment.getAnnotations()) {
			format(annotations, document);
		}
		for (Expression indices : assignment.getIndices()) {
			format(indices, document);
		}
		format(assignment.getExpression(), document);
	}
}