/*
 * generated by Xtext
 */
package de.cau.cs.kieler.kicool.formatting2;

import com.google.inject.Inject;
import de.cau.cs.kieler.annotations.formatting2.AnnotationsFormatter;
import de.cau.cs.kieler.kicool.ProcessorAlternativeGroup;
import de.cau.cs.kieler.kicool.ProcessorEntry;
import de.cau.cs.kieler.kicool.ProcessorGroup;
import de.cau.cs.kieler.kicool.services.KiCoolGrammarAccess;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import de.cau.cs.kieler.kicool.IntermediateReference

class KiCoolFormatter extends AnnotationsFormatter {
	
	@Inject extension KiCoolGrammarAccess

	def dispatch void format(de.cau.cs.kieler.kicool.System system, extension IFormattableDocument document) {
	    system.interior[ indent ]

		for (intermediates : system.getIntermediates()) {
			format(intermediates, document);
		}
		format(system.getProcessors(), document);
	}

	def dispatch void format(ProcessorGroup processorgroup, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (ProcessorEntry processors : processorgroup.getProcessors()) {
			format(processors, document);
		}
	}

	def dispatch void format(ProcessorAlternativeGroup processoralternativegroup, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (ProcessorEntry processors : processoralternativegroup.getProcessors()) {
			format(processors, document);
		}
	}
	
	def dispatch void format(IntermediateReference intermediate, extension IFormattableDocument document) {
	    intermediate.regionFor.keyword("metric").prepend[ newLine ]
	    
	}
}