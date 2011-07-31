package org.eclipse.emf.henshin.codegen.templates.interpreter;

import org.eclipse.emf.henshin.codegen.model.*;

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
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = NL + NL + "public class ";
  protected final String TEXT_3 = " {" + NL + "" + NL + "" + NL + "}";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenTransformation genTrafo = (GenTransformation) argument;
GenHenshin genHenshin = genTrafo.getGenHenshin();
String className = genHenshin.applyImplementationPattern(genTrafo.getTransformationClassFormatted());


    stringBuffer.append(
genHenshin.getCopyrightComment()
);
    stringBuffer.append(TEXT_1);
    stringBuffer.append( genHenshin.getImplementationPackage() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
