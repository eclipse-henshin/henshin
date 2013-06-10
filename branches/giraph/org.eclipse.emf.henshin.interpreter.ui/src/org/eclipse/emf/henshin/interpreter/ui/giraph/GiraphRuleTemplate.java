package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.*;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.interpreter.ui.giraph.GiraphUtil;
import org.eclipse.emf.henshin.interpreter.ui.giraph.GiraphUtil.MatchingStep;
import org.eclipse.emf.ecore.*;

public class GiraphRuleTemplate
{
  protected static String nl;
  public static synchronized GiraphRuleTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GiraphRuleTemplate result = new GiraphRuleTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "import org.apache.log4j.Logger;" + NL + "" + NL + "public class ";
  protected final String TEXT_3 = " extends BasicComputation {" + NL;
  protected final String TEXT_4 = NL + "\tpublic static final int ";
  protected final String TEXT_5 = " = ";
  protected final String TEXT_6 = ";" + NL + "\t";
  protected final String TEXT_7 = NL + "\tprivate static Logger LOG = Logger.getLogger(";
  protected final String TEXT_8 = ".class);" + NL + "" + NL + "" + NL + "\t@Override" + NL + "\tpublic void compute(" + NL + "      Vertex<LongWritable, DoubleWritable, FloatWritable> vertex," + NL + "      Iterable<DoubleWritable> messages) throws IOException {\t\t";
  protected final String TEXT_9 = "\t\t" + NL + "\t\t";
  protected final String TEXT_10 = "if (getSuperStep()==";
  protected final String TEXT_11 = ") {" + NL + "" + NL + "\t\t\t// Check whether this vertex is a possible match for node ";
  protected final String TEXT_12 = ":" + NL + "\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t// Create a new partial match:" + NL + "\t\t\t";
  protected final String TEXT_14 = NL + "\t\t\t// For every partial match (incoming message) do:" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t" + NL + "\t\t\t\t";
  protected final String TEXT_16 = NL + "\t\t\t    // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_17 = "\":" + NL + "\t\t\t    " + NL + "\t\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_19 = NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t" + NL + "\t\t}" + NL;
  protected final String TEXT_22 = "\t\t" + NL + "\t\t// Next nodes are activated by a message, so we can stop:" + NL + "\t\tvoteForHalt();\t\t" + NL + "\t" + NL + "\t}" + NL + "" + NL + "" + NL + "" + NL + "}";
  protected final String TEXT_23 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

Rule rule = (Rule) args.get("rule");
String className = (String) args.get("className");

Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(rule.getModule());
List<MatchingStep> matchingSteps = GiraphUtil.getMatchingSteps(rule);


    stringBuffer.append(TEXT_2);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_3);
    
int value = 0;
for (ENamedElement type : typeConstants.keySet()) {
	
    stringBuffer.append(TEXT_4);
    stringBuffer.append( typeConstants.get(type) );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_6);
    
}

    stringBuffer.append(TEXT_7);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_8);
     
		for (int i=0; i<matchingSteps.size(); i++) {
			MatchingStep step = matchingSteps.get(i);

    stringBuffer.append(TEXT_9);
    stringBuffer.append( i>0 ? "else " : "" );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_12);
     if (i==0) { 
    stringBuffer.append(TEXT_13);
     } else { 
    stringBuffer.append(TEXT_14);
     } 
    stringBuffer.append(TEXT_15);
     if (step.edge!=null) { 
    stringBuffer.append(TEXT_16);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_17);
     } 
    stringBuffer.append(TEXT_18);
     if (i>0) { 
    stringBuffer.append(TEXT_19);
     } else { 
    stringBuffer.append(TEXT_20);
     } 
    stringBuffer.append(TEXT_21);
     
		} // end for

    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    return stringBuffer.toString();
  }
}
