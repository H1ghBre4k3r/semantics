/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.text3.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import de.cau.cs.kieler.sccharts.text3.services.Sct3GrammarAccess;

public class Sct3Parser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private Sct3GrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected de.cau.cs.kieler.sccharts.text3.parser.antlr.internal.InternalSct3Parser createParser(XtextTokenStream stream) {
		return new de.cau.cs.kieler.sccharts.text3.parser.antlr.internal.InternalSct3Parser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Root";
	}
	
	public Sct3GrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(Sct3GrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}