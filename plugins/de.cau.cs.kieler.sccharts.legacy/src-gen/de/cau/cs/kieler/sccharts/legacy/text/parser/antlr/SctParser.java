/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.legacy.text.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import de.cau.cs.kieler.sccharts.legacy.text.services.SctGrammarAccess;

public class SctParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private SctGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected de.cau.cs.kieler.sccharts.legacy.text.parser.antlr.internal.InternalSctParser createParser(XtextTokenStream stream) {
		return new de.cau.cs.kieler.sccharts.legacy.text.parser.antlr.internal.InternalSctParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Root";
	}
	
	public SctGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(SctGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}