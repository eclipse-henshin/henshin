package org.eclipse.emf.henshin.codegen.templates;

import java.util.*;
import org.eclipse.emf.henshin.codegen.model.*;
import org.eclipse.emf.henshin.codegen.generator.internal.*;

public class GenUnitInterpreter
{
  protected static String nl;
  public static synchronized GenUnitInterpreter create(String lineSeparator)
  {
    nl = lineSeparator;
    GenUnitInterpreter result = new GenUnitInterpreter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate static ";
  protected final String TEXT_2 = " = null;" + NL;
  protected final String TEXT_3 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\t";
  protected final String TEXT_4 = " boolean ";
  protected final String TEXT_5 = " {" + NL + "\t\tif (";
  protected final String TEXT_6 = "==null) {";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "\t\t}";
  protected final String TEXT_9 = NL + "\t\t";
  protected final String TEXT_10 = " application = new ";
  protected final String TEXT_11 = "(engine, ";
  protected final String TEXT_12 = ");" + NL + "\t\treturn application.apply();" + NL + "\t}";
  protected final String TEXT_13 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenUnit genUnit = (GenUnit) argument;
boolean isRule = (genUnit instanceof GenRule);

String unitModelName = genUnit.getMethodFormatted().toUpperCase();
unitModelName = (isRule ? "RULE" : "UNIT") + "_" + unitModelName;

// Unit model:


    stringBuffer.append(TEXT_1);
    stringBuffer.append( (isRule ? "Rule" : "TransformationUnit") + " " + unitModelName );
    stringBuffer.append(TEXT_2);
    

// Unit parameters:
String paramsString = "";
List<String> params = genUnit.getParametersFormatted();
for (int i=0; i<params.size(); i++) {
	paramsString = paramsString + "String " + params.get(i);
	if (i<params.size()-1) paramsString = paramsString + ", ";
}

// Visibility of the unit:
String visibility = genUnit.isPublic() ? "public" : "protected";


    stringBuffer.append(TEXT_3);
    stringBuffer.append( visibility );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( genUnit.getMethodFormatted() + "(" + paramsString + ")" );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append( 
		CodeGenUnitModelGenerator.generate(genUnit, unitModelName, "\t\t\t") 
);
    stringBuffer.append(TEXT_8);
     
		String applicationType = isRule ? "RuleApplication" : "UnitApplication";
		
    stringBuffer.append(TEXT_9);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
