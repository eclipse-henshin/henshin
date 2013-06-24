package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.*;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.interpreter.info.*;
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
  protected final String TEXT_1 = "/*" + NL + " * Licensed to the Apache Software Foundation (ASF) under one" + NL + " * or more contributor license agreements.  See the NOTICE file" + NL + " * distributed with this work for additional information" + NL + " * regarding copyright ownership.  The ASF licenses this file" + NL + " * to you under the Apache License, Version 2.0 (the" + NL + " * \"License\"); you may not use this file except in compliance" + NL + " * with the License.  You may obtain a copy of the License at" + NL + " *" + NL + " *     http://www.apache.org/licenses/LICENSE-2.0" + NL + " *" + NL + " * Unless required by applicable law or agreed to in writing, software" + NL + " * distributed under the License is distributed on an \"AS IS\" BASIS," + NL + " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." + NL + " * See the License for the specific language governing permissions and" + NL + " * limitations under the License." + NL + " */" + NL + "package org.apache.giraph.examples;" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.Arrays;" + NL + "import java.util.Iterator;" + NL + "import java.util.List;" + NL + "import java.util.ArrayList;" + NL + "" + NL + "import org.apache.giraph.edge.Edge;" + NL + "import org.apache.giraph.edge.EdgeFactory;" + NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.hadoop.io.IntWritable;" + NL + "import org.apache.log4j.Logger;" + NL + "" + NL + "/**" + NL + " * Generated implementation of the Henshin rule \"";
  protected final String TEXT_2 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_3 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_4 = " extends" + NL + "  BasicComputation<HenshinUtil.VertexId, IntWritable," + NL + "  IntWritable, HenshinUtil.Match> {" + NL + "" + NL + "  /**" + NL + "   * Default number of applications of this rule." + NL + "   */" + NL + "  public static final int DEFAULT_APPLICATION_COUNT = 1;";
  protected final String TEXT_5 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_6 = "\"." + NL + "   */" + NL + "  public static final int ";
  protected final String TEXT_7 = " = ";
  protected final String TEXT_8 = ";";
  protected final String TEXT_9 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  private static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_10 = ".class);" + NL + "" + NL + "  /**" + NL + "   * Number of applications of this rule." + NL + "   */" + NL + "  private int applicationCount = DEFAULT_APPLICATION_COUNT;" + NL + "" + NL + "  /**" + NL + "   * Get the number of application to be executed for this rule." + NL + "   * @return the number of rule applications." + NL + "   */" + NL + "  public int getApplicationCount() {" + NL + "    return applicationCount;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Set the number of application to be executed for this rule." + NL + "   * @param applicationCount The new number of rule applications." + NL + "   */" + NL + "  public void setApplicationCount(int applicationCount) {" + NL + "    this.applicationCount = applicationCount;" + NL + "  }" + NL + "" + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<HenshinUtil.VertexId, IntWritable, IntWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches) throws IOException {" + NL + "" + NL + "    // Get the current superstep:" + NL + "    long superstep = getSuperstep();" + NL + "" + NL + "    // Check if we should we stop:" + NL + "    if (superstep >= applicationCount * ";
  protected final String TEXT_11 = ") {" + NL + "      vertex.voteToHalt();" + NL + "      return;" + NL + "    }" + NL + "" + NL + "    // Log partial matches:" + NL + "    LOG.info(\"Executing superstep \" + superstep);" + NL + "    for (HenshinUtil.Match match : matches) {" + NL + "      LOG.info(\"Received partial match: \" + HenshinUtil.matchToString(match));" + NL + "    }" + NL + NL;
  protected final String TEXT_12 = " if (superstep % ";
  protected final String TEXT_13 = " == ";
  protected final String TEXT_14 = ") {" + NL;
  protected final String TEXT_15 = NL + "      // Node ";
  protected final String TEXT_16 = ": check for edge to match of ";
  protected final String TEXT_17 = " of type \"";
  protected final String TEXT_18 = "\":" + NL + "      List<HenshinUtil.Match> validMatches = new ArrayList<HenshinUtil.Match>();" + NL + "      Iterator<HenshinUtil.Match> it = matches.iterator();" + NL + "      while (it.hasNext()) {" + NL + "        HenshinUtil.Match match = it.next();" + NL + "        HenshinUtil.VertexId targetId = HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_19 = ");" + NL + "        for (Edge<HenshinUtil.VertexId, IntWritable> edge : vertex.getEdges()) {" + NL + "          if (edge.getValue().get() == ";
  protected final String TEXT_20 = " &&" + NL + "              edge.getTargetVertexId().equals(targetId)) {" + NL + "            validMatches.add(match);" + NL + "            break;" + NL + "          }" + NL + "        }" + NL + "      }" + NL + "      matches = validMatches;" + NL;
  protected final String TEXT_21 = NL + "      // Matching node ";
  protected final String TEXT_22 = ". Type must be \"";
  protected final String TEXT_23 = "\":" + NL + "      boolean ok = vertex.getValue().get() == ";
  protected final String TEXT_24 = ";" + NL + "      if (ok) {";
  protected final String TEXT_25 = NL + "        // Create a new partial match:" + NL + "        HenshinUtil.Match match =" + NL + "          HenshinUtil.addMatchVertex(null, vertex.getId());";
  protected final String TEXT_26 = NL + "        // Extend all partial matches:" + NL + "        for (HenshinUtil.Match match : matches) {" + NL + "          match = HenshinUtil.addMatchVertex(match, vertex.getId());";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_29 = "\":";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = "        for (Edge<HenshinUtil.VertexId, IntWritable> edge : vertex.getEdges()) {";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = "          if (edge.getValue().get() == ";
  protected final String TEXT_34 = ") {";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = "            LOG.info(\"Sending partial match: \" +";
  protected final String TEXT_37 = NL;
  protected final String TEXT_38 = "              HenshinUtil.matchToString(match));";
  protected final String TEXT_39 = NL;
  protected final String TEXT_40 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_41 = NL;
  protected final String TEXT_42 = "          }";
  protected final String TEXT_43 = NL;
  protected final String TEXT_44 = "        }";
  protected final String TEXT_45 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_46 = ":" + NL + "          for (HenshinUtil.Match m : matches) {" + NL + "            LOG.info(\"Sending partial match: \" +" + NL + "              HenshinUtil.matchToString(match));" + NL + "            sendMessage(HenshinUtil.getMatchVertexId(m, ";
  protected final String TEXT_47 = "), match);" + NL + "          }";
  protected final String TEXT_48 = NL + "        }";
  protected final String TEXT_49 = NL + "      } // end if ok" + NL;
  protected final String TEXT_50 = NL + "      // Apply rule for all matches:" + NL + "      for (HenshinUtil.Match match : matches) {" + NL + "        applyRule(vertex, match);" + NL + "      }";
  protected final String TEXT_51 = NL + "      // In the last iteration the vertex can be made inactive:" + NL + "      if (superstep / ";
  protected final String TEXT_52 = " == applicationCount - 1) {" + NL + "        vertex.voteToHalt();" + NL + "      }";
  protected final String TEXT_53 = NL + NL + "    }";
  protected final String TEXT_54 = NL + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  private void applyRule(Vertex<HenshinUtil.VertexId, IntWritable," + NL + "    IntWritable> vertex, HenshinUtil.Match match) throws IOException {" + NL + "" + NL + "    LOG.info(\"Applying rule with match \" + HenshinUtil.matchToString(match));";
  protected final String TEXT_55 = NL + NL + "    // Remove edge ";
  protected final String TEXT_56 = " -> ";
  protected final String TEXT_57 = ":" + NL + "    removeEdgesRequest(" + NL + "      HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_58 = ")," + NL + "      HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_59 = ")" + NL + "    );";
  protected final String TEXT_60 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_61 = ":" + NL + "    removeVertexRequest(" + NL + "      HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_62 = ")" + NL + "    );";
  protected final String TEXT_63 = NL + "    byte[] thisVertexId = vertex.getId().getBytes();" + NL + "    byte[] newVertexId;";
  protected final String TEXT_64 = NL + NL + "    // Create vertex ";
  protected final String TEXT_65 = ":" + NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_66 = ";" + NL + "    addVertexRequest(new HenshinUtil.VertexId(newVertexId)," + NL + "      new IntWritable(";
  protected final String TEXT_67 = "));";
  protected final String TEXT_68 = NL + NL + "    // Create edge ";
  protected final String TEXT_69 = " -> ";
  protected final String TEXT_70 = ":";
  protected final String TEXT_71 = NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_72 = ";" + NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_73 = " = new HenshinUtil.VertexId(newVertexId);";
  protected final String TEXT_74 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_75 = " = HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_76 = ");";
  protected final String TEXT_77 = NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_78 = ";" + NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_79 = " = new HenshinUtil.VertexId(newVertexId);";
  protected final String TEXT_80 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_81 = " = HenshinUtil.getMatchVertexId(match, ";
  protected final String TEXT_82 = ");";
  protected final String TEXT_83 = NL + "    Edge<HenshinUtil.VertexId, IntWritable> edge";
  protected final String TEXT_84 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_85 = ", new IntWritable(";
  protected final String TEXT_86 = "));" + NL + "    addEdgeRequest(src";
  protected final String TEXT_87 = ", edge";
  protected final String TEXT_88 = ");";
  protected final String TEXT_89 = NL + NL + "  }" + NL + "" + NL + "}";
  protected final String TEXT_90 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

GiraphRuleData data = (GiraphRuleData) args.get("data");

RuleChangeInfo changeInfo = new RuleChangeInfo(data.rule);

String className = (String) args.get("className");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_4);
    
int value = 0;
for (ENamedElement type : data.typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_5);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( data.typeConstants.get(type) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_8);
    
}

    stringBuffer.append(TEXT_9);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_11);
     
    for (int i=0; i<data.matchingSteps.size(); i++) {
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_14);
        if (step.verifyEdgeTo >= 0) {
    stringBuffer.append(TEXT_15);
    stringBuffer.append( data.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( data.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( step.verifyEdgeTo );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_20);
        } else {
    stringBuffer.append(TEXT_21);
    stringBuffer.append( data.getNodeName(step.node) );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( data.typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_24);
        if (i == 0) {
    stringBuffer.append(TEXT_25);
     } else {
    stringBuffer.append(TEXT_26);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_27);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_28);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_33);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_36);
    stringBuffer.append(TEXT_37);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_38);
    stringBuffer.append(TEXT_39);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_40);
    stringBuffer.append(TEXT_41);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_42);
    stringBuffer.append(TEXT_43);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_44);
        } else if (step.sendBackTo >= 0) {
    stringBuffer.append(TEXT_45);
    stringBuffer.append( data.getNodeName(data.matchingSteps.get(step.sendBackTo).node) );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( step.sendBackTo );
    stringBuffer.append(TEXT_47);
        }
      if (i>0) {
    stringBuffer.append(TEXT_48);
    
       }
    stringBuffer.append(TEXT_49);
        }
      if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_50);
    
      } else {
    stringBuffer.append(TEXT_51);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_52);
    
      }
    stringBuffer.append(TEXT_53);
     

    } // end for


    stringBuffer.append(TEXT_54);
      for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_55);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_59);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_60);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_61);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_62);
      } 

    if (!changeInfo.getCreatedNodes().isEmpty()) {

    stringBuffer.append(TEXT_63);
    	}
    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_64);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( data.typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_67);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_68);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_69);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_70);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_71);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_73);
    	} else { 
    stringBuffer.append(TEXT_74);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( data.orderedLhsNodes.indexOf(
                              data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_76);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_77);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_78);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_79);
    	} else { 
    stringBuffer.append(TEXT_80);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_81);
    stringBuffer.append( data.orderedLhsNodes.indexOf(
                              data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_82);
    	} 
    stringBuffer.append(TEXT_83);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( data.typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_86);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_87);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_88);
      e++;
    } 
    stringBuffer.append(TEXT_89);
    stringBuffer.append(TEXT_90);
    return stringBuffer.toString();
  }
}
