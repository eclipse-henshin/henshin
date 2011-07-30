package org.eclipse.emf.henshin.codegen.templates.interpreter;

import org.eclipse.emf.henshin.model.*;

public class GenTransformationTemplate
{
  protected static String nl;
  public static synchronized GenTransformationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GenTransformationTemplate result = new GenTransformationTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "public class Hello {" + NL + "" + NL + "" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
		

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
