package org.eclipse.emf.henshin.text.ui.util

import com.google.inject.Inject
import org.antlr.runtime.ANTLRStringStream
import org.eclipse.emf.henshin.text.Henshin_textStandaloneSetup
import org.eclipse.emf.henshin.text.henshin_text.Expression
import org.eclipse.emf.henshin.text.parser.antlr.internal.InternalHenshin_textLexer
import org.eclipse.emf.henshin.text.parser.antlr.internal.InternalHenshin_textParser
import org.eclipse.emf.henshin.text.services.Henshin_textGrammarAccess
import org.eclipse.xtext.parser.antlr.ITokenDefProvider
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider
import org.eclipse.xtext.parser.antlr.XtextTokenStream
import org.eclipse.emf.henshin.text.henshin_text.impl.Henshin_textFactoryImpl

public class ExpressionParser {

	@Inject
	private Henshin_textGrammarAccess grammarAccess;
	
	@Inject 
    protected ITokenDefProvider antlrTokenDefProvider;
    
	def Expression parseExpression(String theString) {
		var result = Henshin_textFactoryImpl.eINSTANCE.createStringValue
		result.value = theString		
		return result
	}
    
//	def Expression parseExpression(String theString) {
//		val injector = new Henshin_textStandaloneSetup().createInjectorAndDoEMFRegistration
//		injector.injectMembers(this) 		
//		
//		val string = "("+theString+")";
//		
//		val lexer = new InternalHenshin_textLexer(new ANTLRStringStream(string))
// 		val input = new XtextTokenStream(lexer, antlrTokenDefProvider);
//		input.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
//		
//		val parser = new InternalHenshin_textParser(input, grammarAccess);
//		parser.semanticModelBuilder = new HenshinTextSemanticModelBuilder
//		parser.syntaxErrorProvider = new SyntaxErrorMessageProvider
//		val parseResult = parser.parse(grammarAccess.expressionRule.name)
//		val exp = parseResult.rootASTElement as Expression
//		println("Finished parsing without syntax errors: "+!parseResult.hasSyntaxErrors)
//		return exp
//	}
	
	def static void main(String[] args) {
		val exp1 = new ExpressionParser().parseExpression("client")
//		val exp1 = new ExpressionParser().parseExpression("1")
//		val exp1 = new ExpressionParser().parseExpression("1+2")
//		val exp2 = new ExpressionParser().parseExpression("a+2")
//		val exp3 = new ExpressionParser().parseExpression("\"a\"+2")
		println(exp1)
	}
}
