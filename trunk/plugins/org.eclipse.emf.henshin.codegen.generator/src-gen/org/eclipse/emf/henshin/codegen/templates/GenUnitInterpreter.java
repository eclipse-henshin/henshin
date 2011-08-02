package org.eclipse.emf.henshin.codegen.templates;

import java.util.*;
import org.eclipse.emf.henshin.codegen.model.*;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;

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
  protected final String TEXT_6 = "==null) {" + NL + "\t\t\t";
  protected final String TEXT_7 = " = HenshinFactory.eINSTANCE.create";
  protected final String TEXT_8 = "(); ";
  protected final String TEXT_9 = NL + "\t\t\t";
  protected final String TEXT_10 = ".setLhs(HenshinFactory.eINSTANCE.createGraph());" + NL + "\t\t\t";
  protected final String TEXT_11 = ".setRhs(HenshinFactory.eINSTANCE.createGraph()); ";
  protected final String TEXT_12 = NL + "\t\t\tHenshinFactory.eINSTANCE.createNode(";
  protected final String TEXT_13 = "); ";
  protected final String TEXT_14 = NL + "\t\t\tHenshinFactory.eINSTANCE.createNode(";
  protected final String TEXT_15 = "); ";
  protected final String TEXT_16 = NL + "\t\t\t";
  protected final String TEXT_17 = ".getMappings().add(HenshinFactory.eINSTANCE.createMapping(";
  protected final String TEXT_18 = ", ";
  protected final String TEXT_19 = "));";
  protected final String TEXT_20 = NL + "\t\t}";
  protected final String TEXT_21 = NL + "\t\t";
  protected final String TEXT_22 = " application = new ";
  protected final String TEXT_23 = "(engine, ";
  protected final String TEXT_24 = ");" + NL + "\t\treturn application.apply();" + NL + "\t}";
  protected final String TEXT_25 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenUnit genUnit = (GenUnit) argument;
GenTransformation genTrafo = genUnit.getGenTransformation();
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
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( genUnit.getUnit().eClass().getName() );
    stringBuffer.append(TEXT_8);
    
			if (isRule) {
				Rule rule = ((GenRule) genUnit).getRule();
				
    stringBuffer.append(TEXT_9);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_11);
    
				for (Node lhsNode : rule.getLhs().getNodes()) {
					GenPackage genPackage = genTrafo.getGenPackage(lhsNode.getType().getEPackage());
					GenClass genClass = genTrafo.getGenClass(lhsNode.getType());
					String nodeType = "null"; //genPackage.getQualifiedPackageInterfaceName() + 
							//".eINSTANCE.get" + genClass.getFormattedName() + "()";
					
    stringBuffer.append(TEXT_12);
    stringBuffer.append( unitModelName + ".getLhs(), " + nodeType );
    stringBuffer.append(TEXT_13);
    
				}
				for (Node lhsNode : rule.getLhs().getNodes()) { 
					String nodeType = "null"; 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( unitModelName + ".getRhs(), " + nodeType );
    stringBuffer.append(TEXT_15);
    
				}
				for (Mapping mapping : rule.getMappings()) { 
					String origin = unitModelName + ".getLhs().getNodes().get(" + 
									rule.getLhs().getNodes().indexOf(mapping.getOrigin()) + ")";
					String image  = unitModelName + ".getRhs().getNodes().get(" + 
									rule.getRhs().getNodes().indexOf(mapping.getImage()) + ")"; 
    stringBuffer.append(TEXT_16);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( origin );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( image );
    stringBuffer.append(TEXT_19);
    
				}
			}
			else {
			
			}	
			
    stringBuffer.append(TEXT_20);
     
		String applicationType = isRule ? "RuleApplication" : "UnitApplication";
		
    stringBuffer.append(TEXT_21);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( applicationType );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( unitModelName );
    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
