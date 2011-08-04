package org.eclipse.emf.henshin.codegen.templates;

import org.eclipse.emf.henshin.codegen.model.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;

public class GenTransformationInterpreter
{
  protected static String nl;
  public static synchronized GenTransformationInterpreter create(String lineSeparator)
  {
    nl = lineSeparator;
    GenTransformationInterpreter result = new GenTransformationInterpreter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL;
  protected final String TEXT_3 = NL + "import ";
  protected final String TEXT_4 = ";";
  protected final String TEXT_5 = NL + "import ";
  protected final String TEXT_6 = ".*;";
  protected final String TEXT_7 = NL + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.emf.ecore.resource.ResourceSet;" + NL + "import org.eclipse.emf.ecore.*; // TODO: remove general Ecore import" + NL + "" + NL + "import org.eclipse.emf.henshin.common.util.EmfGraph;" + NL + "import org.eclipse.emf.henshin.interpreter.*;" + NL + "import org.eclipse.emf.henshin.model.*;" + NL + "" + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = " {" + NL + "" + NL + "\t/** " + NL + "\t * Interpreter engine to be used." + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate EmfEngine engine;" + NL + "\t" + NL + "\t/**" + NL + "\t * Default constructor." + NL + "\t * @param resources The model resources to be transformed. " + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_9 = "(Resource... resources) {" + NL + "\t\tfinal EmfGraph graph = new EmfGraph();" + NL + "\t\tfor (Resource resource : resources) {" + NL + "\t\t\tfor (EObject root : resource.getContents()) {" + NL + "\t\t\t\tgraph.addRoot(root);" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tengine = new EmfEngine(graph);" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * Constructor for a resource set." + NL + "\t * @param resourceSet The model resources to be transformed. " + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_10 = "(ResourceSet resourceSet) {" + NL + "\t\tthis(resourceSet.getResources().toArray(new Resource[0]));" + NL + "\t}" + NL + "\t";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + "}";
  protected final String TEXT_13 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

GenTransformation genTrafo = (GenTransformation) argument;
GenHenshin genHenshin = genTrafo.getGenHenshin();

String className = genHenshin.applyImplementationPattern(genTrafo.getTransformationClassFormatted());
String interfaceName = genHenshin.applyInterfacePattern(genTrafo.getTransformationClassFormatted());
String interfacePackage = genHenshin.getInterfacePackage();


    stringBuffer.append(
genHenshin.getCopyrightComment()
);
    stringBuffer.append(TEXT_1);
    stringBuffer.append( genHenshin.getImplementationPackage() );
    stringBuffer.append(TEXT_2);
    
if (!genHenshin.isSupressInterfaces()) {

    stringBuffer.append(TEXT_3);
    stringBuffer.append( interfacePackage + "." + interfaceName );
    stringBuffer.append(TEXT_4);
    
}

for (GenPackage genPackage : genTrafo.getGenPackages()) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append( genPackage.getQualifiedPackageName());
    stringBuffer.append(TEXT_6);
     } 
    stringBuffer.append(TEXT_7);
    stringBuffer.append( className + (genHenshin.isSupressInterfaces() ? "" : " implements " + interfaceName) );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_10);
     
GenUnitInterpreter unitTemplate = new GenUnitInterpreter();
for (GenUnit genUnit : genTrafo.getGenUnits()) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append( unitTemplate.generate(genUnit) );
    
}

    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
