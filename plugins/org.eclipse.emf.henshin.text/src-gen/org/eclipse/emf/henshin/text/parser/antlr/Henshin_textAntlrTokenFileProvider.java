/*
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class Henshin_textAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/eclipse/emf/henshin/text/parser/antlr/internal/InternalHenshin_text.tokens");
	}
}
