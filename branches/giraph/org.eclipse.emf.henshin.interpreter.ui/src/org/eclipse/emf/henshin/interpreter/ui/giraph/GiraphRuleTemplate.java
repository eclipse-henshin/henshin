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
  protected final String TEXT_2 = NL + "import org.apache.giraph.graph.*;" + NL + "import org.apache.hadoop.io.*;" + NL + "import org.apache.log4j.Logger;" + NL + "" + NL + "public class ";
  protected final String TEXT_3 = " extends BasicComputation" + NL + "\t<LongWritable, IntegerWritable, IntegerWritable, BytesWritable> {" + NL;
  protected final String TEXT_4 = NL + "\tpublic static final int ";
  protected final String TEXT_5 = " = ";
  protected final String TEXT_6 = ";" + NL + "\t";
  protected final String TEXT_7 = NL + "\tprivate static Logger LOG = Logger.getLogger(";
  protected final String TEXT_8 = ".class);" + NL + "" + NL + "" + NL + "\t@Override" + NL + "\tpublic void compute(" + NL + "\t\t\tVertex<LongWritable, IntegerWritable, IntegerWritable> vertex," + NL + "\t\t\tIterable<BytesWritable> matches) throws IOException {" + NL + "" + NL + "\t\tint superStep = getSuperStep();" + NL + "\t\t";
  protected final String TEXT_9 = "\t\t";
  protected final String TEXT_10 = "if (superStep==";
  protected final String TEXT_11 = ") {" + NL;
  protected final String TEXT_12 = NL + "\t\t\t// Node ";
  protected final String TEXT_13 = ": check for edge to match of ";
  protected final String TEXT_14 = " of type \"";
  protected final String TEXT_15 = "\":" + NL + "\t\t\tIterator<BytesWritable> it = matches.iterator();" + NL + "\t\t\twhile (filteredMatches.hasNext()) {" + NL + "\t\t\t\tBytesWritable match = it.next();" + NL + "\t\t\t\tint target = getInt(match, ";
  protected final String TEXT_16 = ");" + NL + "\t\t\t\tfor (Edge edge : getEdges()) {" + NL + "\t\t\t\t\tif (edge.getValue().get()==";
  protected final String TEXT_17 = " && edge.getTargetVertexId()==target) {" + NL + "\t\t\t\t\t\ttarget = -1;" + NL + "\t\t\t\t\t\tbreak; // Ok, we found it." + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t\t// If no edge found, remove this partial match:" + NL + "\t\t\t\tif (target>=0) {" + NL + "\t\t\t\t\tit.remove();" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t// Matching node ";
  protected final String TEXT_19 = ". Type must be \"";
  protected final String TEXT_20 = "\":" + NL + "\t\t\tboolean ok = (vertex.getValue().get()==";
  protected final String TEXT_21 = ");" + NL + "\t\t\tif (ok) {" + NL + "\t\t\t\t";
  protected final String TEXT_22 = NL + "\t\t\t\t// Create a new partial match:" + NL + "\t\t\t\tBytesWritable match = addInt(null, vertex.getId());" + NL + "\t\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\t// Extend all partial matches:" + NL + "\t\t\t\tfor (BytesWritable match : matches) {" + NL + "\t\t\t\t\tmatch = addInt(match, vertex.getId());" + NL + "\t\t\t\t";
  protected final String TEXT_24 = NL + "\t\t\t\t\t// Send a match request to all outgoing edges of type \"";
  protected final String TEXT_25 = "\":" + NL + "\t\t\t\t\tfor (Edge edge : getEdges()) {" + NL + "\t\t\t\t\t\tif (edge.getValue().get()==";
  protected final String TEXT_26 = ") {" + NL + "\t\t\t\t\t\t\tsendMessage(edge.getTargetVertexId(), match);" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\t\t// Send the message back to matches of node ";
  protected final String TEXT_28 = ":" + NL + "\t\t\t\t\tfor (ByteWritable match : matches) {" + NL + "\t\t\t\t\t\tsendMessage(getInt(match, ";
  protected final String TEXT_29 = "), match);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_30 = NL + "\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_31 = NL + "\t\t\t} // end if ok" + NL + "\t\t\t";
  protected final String TEXT_32 = NL + "\t\t\tLOG.debug(\"Found \" + matches.size() + \" for rule ";
  protected final String TEXT_33 = "\");" + NL + "\t\t\t";
  protected final String TEXT_34 = "\t\t\t\t\t\t" + NL + "\t\t}" + NL;
  protected final String TEXT_35 = "\t\t" + NL + "\t\t// Next nodes are activated by a message, so we can sleep:" + NL + "\t\tvoteForHalt();" + NL + "\t" + NL + "\t}" + NL + "" + NL + "\tprivate static int getInt(BytesWritable message, int index) {" + NL + "\t\tbyte[] b = message.getBytes();" + NL + "\t\tint x = index*4;" + NL + "\t\treturn (((int)b[x]) << 24) + " + NL + "\t\t\t  ((((int)b[x+1]) & 0xFF) << 16) + " + NL + "\t\t\t  ((((int)b[x+2]) & 0xFF) << 8) + " + NL + "\t\t\t  (((int)b[x+3]) & 0xFF);" + NL + "\t}" + NL + "" + NL + "\tprivate static BytesWritable addInt(BytesWritable message, int value) {" + NL + "\t\tbyte[] b = (message!=null) ? message.getBytes() : EMPTY_BYTE_ARRAY;" + NL + "\t\tb = Arrays.copyOf(b, b.length+4);" + NL + "\t\tb[b.length-4] = value >>> 24;" + NL + "\t\tb[b.length-3] = value >>> 16;" + NL + "\t\tb[b.length-2] = value >>> 8;" + NL + "\t\tb[b.length-1] = value;" + NL + "\t\treturn new BytesWritable(b);" + NL + "\t}" + NL + "" + NL + "\tprivate static byte[] EMPTY_BYTE_ARRAY = new byte[0];" + NL + "\t" + NL + "}";
  protected final String TEXT_36 = NL;

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
     			if (step.verifyEdgeTo>=0) { 
    stringBuffer.append(TEXT_12);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( step.verifyEdgeTo );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_17);
    			} else { 
    stringBuffer.append(TEXT_18);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_21);
     if (i==0) { 
    stringBuffer.append(TEXT_22);
     } else { 
    stringBuffer.append(TEXT_23);
     } 
				if (step.edge!=null) { 
    stringBuffer.append(TEXT_24);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_26);
     } else if (step.sendBackTo>=0) { 
    stringBuffer.append(TEXT_27);
    stringBuffer.append( GiraphUtil.getNodeName(matchingSteps.get(step.sendBackTo).node) );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( step.sendBackTo );
    stringBuffer.append(TEXT_29);
     }
				if (i>0) { 
    stringBuffer.append(TEXT_30);
     } 
    stringBuffer.append(TEXT_31);
    			}
			if (i==matchingSteps.size()-1) { 
    stringBuffer.append(TEXT_32);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_33);
              } 
    stringBuffer.append(TEXT_34);
     
		} // end for

    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    return stringBuffer.toString();
  }
}
