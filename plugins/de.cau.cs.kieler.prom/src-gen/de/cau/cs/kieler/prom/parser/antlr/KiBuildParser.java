/*
 * generated by Xtext
 */
package de.cau.cs.kieler.prom.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import de.cau.cs.kieler.prom.services.KiBuildGrammarAccess;

public class KiBuildParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private KiBuildGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected de.cau.cs.kieler.prom.parser.antlr.internal.InternalKiBuildParser createParser(XtextTokenStream stream) {
		return new de.cau.cs.kieler.prom.parser.antlr.internal.InternalKiBuildParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "BuildConfiguration";
	}
	
	public KiBuildGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(KiBuildGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}