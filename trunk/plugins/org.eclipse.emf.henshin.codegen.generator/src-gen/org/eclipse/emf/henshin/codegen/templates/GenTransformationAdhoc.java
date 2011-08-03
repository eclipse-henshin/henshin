package org.eclipse.emf.henshin.codegen.templates;

public class GenTransformationAdhoc
{
  protected static String nl;
  public static synchronized GenTransformationAdhoc create(String lineSeparator)
  {
    nl = lineSeparator;
    GenTransformationAdhoc result = new GenTransformationAdhoc();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

// For now use the interpreter template...
GenTransformationInterpreter template = new GenTransformationInterpreter();


    stringBuffer.append(
template.generate(argument)
);
    return stringBuffer.toString();
  }
}
