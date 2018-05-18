package org.eclipse.emf.henshin.text.ui.util;

import com.google.inject.Inject;
import org.eclipse.emf.henshin.text.henshin_text.Expression;
import org.eclipse.emf.henshin.text.henshin_text.StringValue;
import org.eclipse.emf.henshin.text.henshin_text.impl.Henshin_textFactoryImpl;
import org.eclipse.emf.henshin.text.services.Henshin_textGrammarAccess;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ExpressionParser {
  @Inject
  private Henshin_textGrammarAccess grammarAccess;
  
  @Inject
  protected ITokenDefProvider antlrTokenDefProvider;
  
  public Expression parseExpression(final String theString) {
    StringValue result = Henshin_textFactoryImpl.eINSTANCE.createStringValue();
    result.setValue(theString);
    return result;
  }
  
  public static void main(final String[] args) {
    ExpressionParser _expressionParser = new ExpressionParser();
    final Expression exp1 = _expressionParser.parseExpression("client");
    InputOutput.<Expression>println(exp1);
  }
}
