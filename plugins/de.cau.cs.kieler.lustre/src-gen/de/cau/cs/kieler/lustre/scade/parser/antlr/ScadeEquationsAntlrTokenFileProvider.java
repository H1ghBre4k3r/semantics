/*
 * generated by Xtext
 */
package de.cau.cs.kieler.lustre.scade.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class ScadeEquationsAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("de/cau/cs/kieler/lustre/scade/parser/antlr/internal/InternalScadeEquations.tokens");
	}
}