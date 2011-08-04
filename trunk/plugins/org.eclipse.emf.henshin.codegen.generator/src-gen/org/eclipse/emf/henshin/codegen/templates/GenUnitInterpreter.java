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
  protected final String TEXT_1 = "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected static ";
  protected final String TEXT_2 = " = null;" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected ";
  protected final String TEXT_3 = "() {" + NL + "\t\tif (";
  protected final String TEXT_4 = "==null) {" + NL + "\t\t\tHenshinFactory factory = HenshinFactory.eINSTANCE;";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "\t\t}" + NL + "\t\treturn ";
  protected final String TEXT_7 = ";" + NL + "\t}" + NL;
  protected final String TEXT_8 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\t";
  protected final String TEXT_9 = " boolean ";
  protected final String TEXT_10 = " {";
  protected final String TEXT_11 = NL + "\t\t";
  protected final String TEXT_12 = " application = new ";
  protected final String TEXT_13 = "(engine, ";
  protected final String TEXT_14 = "());";
  protected final String TEXT_15 = NL + "\t\tapplication.setParameterValue(\"";
  protected final String TEXT_16 = "\", ";
  protected final String TEXT_17 = ");";
  protected final String TEXT_18 = NL + "\t\treturn application.apply();" + NL + "\t}";
  protected final String TEXT_19 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenUnit genUnit = (GenUnit) argument;
boolean isRule = (genUnit instanceof GenRule);
GenTransformation genTrafo = genUnit.getGenTransformation();

String unitMethod = genUnit.getMethodFormatted();
String unitMethodCap = unitMethod.substring(0,1).toUpperCase() + unitMethod.substring(1);
String unitModelName = (isRule ? "RULE" : "UNIT") + "_" + unitMethod.toUpperCase();
String unitGetter = "get" + (isRule ? "Rule" : "Unit") + unitMethodCap;

// Unit model:


    stringBuffer.append(TEXT_1);
    stringBuffer.append( (isRule ? "Rule" : "TransformationUnit") + " " + unitModelName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( (isRule ? "Rule" : "TransformationUnit") + " " + unitGetter );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(
		new UnitModelCodeGenerator(genTrafo,"factory").generate(genUnit, unitModelName, "\t\t\t")
);
    stringBuffer.append(TEXT_6);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_7);
    

// Unit parameters:
String paramsString = "";
List<String> params = genUnit.getParametersFormatted();
for (int i=0; i<params.size(); i++) {
	paramsString = paramsString + "String " + params.get(i);
	if (i<params.size()-1) paramsString = paramsString + ", ";
}

// Visibility of the unit:
String visibility = genUnit.isPublic() ? "public" : "protected";


    stringBuffer.append(TEXT_8);
    stringBuffer.append( visibility );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( genUnit.getMethodFormatted() + "(" + paramsString + ")" );
    stringBuffer.append(TEXT_10);
     
		String applicationType = isRule ? "RuleApplication" : "UnitApplication";
		
    stringBuffer.append(TEXT_11);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( unitGetter );
    stringBuffer.append(TEXT_14);
    
		for (int i=0; i<params.size(); i++) {

    stringBuffer.append(TEXT_15);
    stringBuffer.append( genUnit.getUnit().getParameters().get(i).getName() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( params.get(i) );
    stringBuffer.append(TEXT_17);
    
		}

    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
