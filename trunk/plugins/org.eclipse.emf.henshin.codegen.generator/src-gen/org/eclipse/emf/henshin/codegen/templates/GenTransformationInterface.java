package org.eclipse.emf.henshin.codegen.templates;

import java.util.*;
import org.eclipse.emf.henshin.codegen.model.*;

public class GenTransformationInterface
{
  protected static String nl;
  public static synchronized GenTransformationInterface create(String lineSeparator)
  {
    nl = lineSeparator;
    GenTransformationInterface result = new GenTransformationInterface();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "/*" + NL + " * @generated" + NL + " */" + NL + "public interface ";
  protected final String TEXT_3 = " {" + NL;
  protected final String TEXT_4 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tboolean ";
  protected final String TEXT_5 = ";" + NL + "\t";
  protected final String TEXT_6 = NL + "}";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenTransformation genTrafo = (GenTransformation) argument;
GenHenshin genHenshin = genTrafo.getGenHenshin();
String className = genHenshin.applyInterfacePattern(genTrafo.getTransformationClassFormatted());


    stringBuffer.append(
genHenshin.getCopyrightComment()
);
    stringBuffer.append(TEXT_1);
    stringBuffer.append( genHenshin.getInterfacePackage() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_3);
     
for (GenUnit genUnit : genTrafo.getGenUnits()) {
	if (!genUnit.isPublic()) continue;
	String paramsString = "";
	List<String> params = genUnit.getParametersFormatted();
	for (int i=0; i<params.size(); i++) {
		paramsString = paramsString + "String " + params.get(i);
		if (i<params.size()-1) paramsString = paramsString + ", ";
	}

    stringBuffer.append(TEXT_4);
    stringBuffer.append( genUnit.getMethodFormatted() + "(" + paramsString + ")" );
    stringBuffer.append(TEXT_5);
    
}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
