/*
 * generated by Xtext
 */
package de.cau.cs.kieler.s.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class SAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("de/cau/cs/kieler/s/parser/antlr/internal/InternalS.tokens");
	}
}