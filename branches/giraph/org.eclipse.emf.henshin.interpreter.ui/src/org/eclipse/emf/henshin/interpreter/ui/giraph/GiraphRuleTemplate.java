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
  protected final String TEXT_1 = "/*" + NL + " * Licensed to the Apache Software Foundation (ASF) under one" + NL + " * or more contributor license agreements.  See the NOTICE file" + NL + " * distributed with this work for additional information" + NL + " * regarding copyright ownership.  The ASF licenses this file" + NL + " * to you under the Apache License, Version 2.0 (the" + NL + " * \"License\"); you may not use this file except in compliance" + NL + " * with the License.  You may obtain a copy of the License at" + NL + " *" + NL + " *     http://www.apache.org/licenses/LICENSE-2.0" + NL + " *" + NL + " * Unless required by applicable law or agreed to in writing, software" + NL + " * distributed under the License is distributed on an \"AS IS\" BASIS," + NL + " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." + NL + " * See the License for the specific language governing permissions and" + NL + " * limitations under the License." + NL + " */" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.Iterator;" + NL + "import java.util.List;" + NL + "import java.util.ArrayList;" + NL + "" + NL + "import org.apache.giraph.edge.Edge;" + NL + "import org.apache.giraph.edge.EdgeFactory;" + NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.hadoop.io.IntWritable;";
  protected final String TEXT_3 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_4 = NL + NL + "/**" + NL + " * Generated implementation of the Henshin rule \"";
  protected final String TEXT_5 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_6 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_7 = " extends" + NL + "  BasicComputation<HenshinUtil.VertexId, IntWritable," + NL + "  IntWritable, HenshinUtil.Match> {" + NL + "" + NL + "  /**" + NL + "   * Default number of applications of this rule." + NL + "   */" + NL + "  public static final int DEFAULT_APPLICATION_COUNT = ";
  protected final String TEXT_8 = ";";
  protected final String TEXT_9 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_10 = "\"." + NL + "   */" + NL + "  public static final int ";
  protected final String TEXT_11 = " = ";
  protected final String TEXT_12 = ";";
  protected final String TEXT_13 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  private static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_14 = ".class);";
  protected final String TEXT_15 = NL + NL + "  /**" + NL + "   * Number of applications of this rule." + NL + "   */" + NL + "  private int applicationCount = DEFAULT_APPLICATION_COUNT;" + NL + "" + NL + "  /**" + NL + "   * Get the number of application to be executed for this rule." + NL + "   * @return the number of rule applications." + NL + "   */" + NL + "  public int getApplicationCount() {" + NL + "    return applicationCount;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Set the number of application to be executed for this rule." + NL + "   * @param applicationCount The new number of rule applications." + NL + "   */" + NL + "  public void setApplicationCount(int applicationCount) {" + NL + "    this.applicationCount = applicationCount;" + NL + "  }" + NL + "" + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<HenshinUtil.VertexId, IntWritable, IntWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches) throws IOException {" + NL + "" + NL + "    // Get the current superstep:" + NL + "    long superstep = getSuperstep();";
  protected final String TEXT_16 = NL + "    LOG.info(\"Vertex \" + vertex.getId() + \" executing superstep \" + superstep);";
  protected final String TEXT_17 = NL + NL + "    // Check if we can stop:" + NL + "    if (superstep == applicationCount * ";
  protected final String TEXT_18 = ") {" + NL + "      // We need to wait one more superstep to see the changes:" + NL + "      return;" + NL + "    } else if (superstep > applicationCount * ";
  protected final String TEXT_19 = ") {";
  protected final String TEXT_20 = NL + "      LOG.info(\"Vertex \" + vertex.getId() + \" finished with \" +" + NL + "        getTotalNumVertices() + \" vertices and \" +" + NL + "        getTotalNumEdges() + \" edges\");";
  protected final String TEXT_21 = NL + "      vertex.voteToHalt();" + NL + "      return;" + NL + "    }" + NL;
  protected final String TEXT_22 = NL + "    // Log received (partial) matches:" + NL + "    for (HenshinUtil.Match match : matches) {" + NL + "      LOG.info(\"Vertex \" + vertex.getId() +" + NL + "        \" received (partial) match \" + match);" + NL + "    }";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = " if (superstep % ";
  protected final String TEXT_25 = " == ";
  protected final String TEXT_26 = ") {" + NL;
  protected final String TEXT_27 = NL + "      // Node ";
  protected final String TEXT_28 = ": check for edge to match of ";
  protected final String TEXT_29 = " of type \"";
  protected final String TEXT_30 = "\":" + NL + "      List<HenshinUtil.Match> validMatches = new ArrayList<HenshinUtil.Match>();" + NL + "      Iterator<HenshinUtil.Match> it = matches.iterator();" + NL + "      while (it.hasNext()) {" + NL + "        HenshinUtil.Match match = it.next();" + NL + "        HenshinUtil.VertexId targetId = match.getVertexId(";
  protected final String TEXT_31 = ");" + NL + "        for (Edge<HenshinUtil.VertexId, IntWritable> edge :" + NL + "          vertex.getEdges()) {" + NL + "          if (edge.getValue().get() == ";
  protected final String TEXT_32 = " &&" + NL + "              edge.getTargetVertexId().equals(targetId)) {" + NL + "            validMatches.add(match);" + NL + "            break;" + NL + "          }" + NL + "        }" + NL + "      }" + NL + "      matches = validMatches;" + NL;
  protected final String TEXT_33 = NL + "      // Matching node ";
  protected final String TEXT_34 = ". Type must be \"";
  protected final String TEXT_35 = "\":" + NL + "      boolean ok = vertex.getValue().get() == ";
  protected final String TEXT_36 = ";" + NL + "      if (ok) {";
  protected final String TEXT_37 = NL + "        // Create a new partial match:" + NL + "        HenshinUtil.Match match =" + NL + "          new HenshinUtil.Match().extend(vertex.getId());";
  protected final String TEXT_38 = NL + "        // Extend all partial matches:" + NL + "        for (HenshinUtil.Match match : matches) {" + NL + "          match = match.extend(vertex.getId());";
  protected final String TEXT_39 = NL;
  protected final String TEXT_40 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_41 = "\":";
  protected final String TEXT_42 = NL;
  protected final String TEXT_43 = "        for (Edge<HenshinUtil.VertexId, IntWritable> edge :";
  protected final String TEXT_44 = NL;
  protected final String TEXT_45 = "          vertex.getEdges()) {";
  protected final String TEXT_46 = NL;
  protected final String TEXT_47 = "          if (edge.getValue().get() == ";
  protected final String TEXT_48 = ") {";
  protected final String TEXT_49 = NL;
  protected final String TEXT_50 = "            LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_51 = NL;
  protected final String TEXT_52 = "              \" sending (partial) match \" + match +";
  protected final String TEXT_53 = NL;
  protected final String TEXT_54 = "              \" to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_55 = NL;
  protected final String TEXT_56 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_57 = NL;
  protected final String TEXT_58 = "          }";
  protected final String TEXT_59 = NL;
  protected final String TEXT_60 = "        }";
  protected final String TEXT_61 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_62 = ":" + NL + "          for (HenshinUtil.Match m : matches) {" + NL + "            HenshinUtil.VertexId targetVertexId =" + NL + "              m.getVertexId(";
  protected final String TEXT_63 = ");";
  protected final String TEXT_64 = NL + "            LOG.info(\"Vertex \" + vertex.getId() +" + NL + "              \" sending (partial) match \" + match +" + NL + "              \" to vertex \" + targetVertexId);";
  protected final String TEXT_65 = NL + "            sendMessage(targetVertexId, match);" + NL + "          }";
  protected final String TEXT_66 = NL + "        }";
  protected final String TEXT_67 = NL + "      } // end if ok" + NL;
  protected final String TEXT_68 = NL + "      // Apply rule for all matches:" + NL + "      for (HenshinUtil.Match match : matches) {" + NL + "        applyRule(vertex, match);" + NL + "      }";
  protected final String TEXT_69 = NL + "      // In the last iteration the vertex can be made inactive:" + NL + "      if (superstep / ";
  protected final String TEXT_70 = " == applicationCount - 1) {" + NL + "        vertex.voteToHalt();" + NL + "      }";
  protected final String TEXT_71 = NL + NL + "    }";
  protected final String TEXT_72 = NL + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  private void applyRule(Vertex<HenshinUtil.VertexId, IntWritable," + NL + "    IntWritable> vertex, HenshinUtil.Match match) throws IOException {" + NL;
  protected final String TEXT_73 = NL + "    LOG.info(\"Vertex \" + vertex.getId() +" + NL + "      \" applying rule with match \" + match);";
  protected final String TEXT_74 = NL + NL + "    // Remove edge ";
  protected final String TEXT_75 = " -> ";
  protected final String TEXT_76 = ":" + NL + "    removeEdgesRequest(" + NL + "      match.getVertexId(";
  protected final String TEXT_77 = ")," + NL + "      match.getVertexId(";
  protected final String TEXT_78 = ")" + NL + "    );";
  protected final String TEXT_79 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_80 = ":" + NL + "    removeVertexRequest(" + NL + "      match.getVertexId(";
  protected final String TEXT_81 = ")" + NL + "    );";
  protected final String TEXT_82 = NL + NL + "    // Base Id for new vertices:" + NL + "    HenshinUtil.VertexId newBaseId =" + NL + "      vertex.getId().extend((byte) getSuperstep());";
  protected final String TEXT_83 = NL + NL + "    // Create vertex ";
  protected final String TEXT_84 = ":" + NL + "    addVertexRequest(newBaseId.extend((byte) ";
  protected final String TEXT_85 = ")," + NL + "      new IntWritable(";
  protected final String TEXT_86 = "));";
  protected final String TEXT_87 = NL + NL + "    // Create edge ";
  protected final String TEXT_88 = " -> ";
  protected final String TEXT_89 = ":";
  protected final String TEXT_90 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_91 = " = newBaseId.extend((byte) ";
  protected final String TEXT_92 = ");";
  protected final String TEXT_93 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_94 = " = match.getVertexId(";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_97 = " = newBaseId.extend((byte) ";
  protected final String TEXT_98 = ");";
  protected final String TEXT_99 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_100 = " = match.getVertexId(";
  protected final String TEXT_101 = ");";
  protected final String TEXT_102 = NL + "    Edge<HenshinUtil.VertexId, IntWritable> edge";
  protected final String TEXT_103 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_104 = ", new IntWritable(";
  protected final String TEXT_105 = "));" + NL + "    addEdgeRequest(src";
  protected final String TEXT_106 = ", edge";
  protected final String TEXT_107 = ");";
  protected final String TEXT_108 = NL + NL + "  }" + NL + "" + NL + "}";
  protected final String TEXT_109 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

GiraphRuleData data = (GiraphRuleData) args.get("data");

RuleChangeInfo changeInfo = new RuleChangeInfo(data.rule);

String className = (String) args.get("className");
String packageName = (String) args.get("packageName");
boolean logging = (Boolean) args.get("logging");
int applicationCount = (Integer) args.get("applicationCount");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_2);
    if (logging) { 
    stringBuffer.append(TEXT_3);
    } 
    stringBuffer.append(TEXT_4);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( applicationCount );
    stringBuffer.append(TEXT_8);
    
int value = 0;
for (ENamedElement type : data.typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_9);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( data.typeConstants.get(type) );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_12);
    
}

if (logging) {

    stringBuffer.append(TEXT_13);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_14);
     } 
    stringBuffer.append(TEXT_15);
    if (logging) { 
    stringBuffer.append(TEXT_16);
    } 
    stringBuffer.append(TEXT_17);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_19);
    if (logging) { 
    stringBuffer.append(TEXT_20);
    } 
    stringBuffer.append(TEXT_21);
    if (logging) { 
    stringBuffer.append(TEXT_22);
    } 
    stringBuffer.append(TEXT_23);
     
    for (int i=0; i<data.matchingSteps.size(); i++) {
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_26);
        if (step.verifyEdgeTo >= 0) {
    stringBuffer.append(TEXT_27);
    stringBuffer.append( data.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( data.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( step.verifyEdgeTo );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_32);
        } else {
    stringBuffer.append(TEXT_33);
    stringBuffer.append( data.getNodeName(step.node) );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( data.typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_36);
        if (i == 0) {
    stringBuffer.append(TEXT_37);
     } else {
    stringBuffer.append(TEXT_38);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_39);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_40);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(TEXT_42);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_43);
    stringBuffer.append(TEXT_44);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_45);
    stringBuffer.append(TEXT_46);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_47);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_48);
    if (logging) { 
    stringBuffer.append(TEXT_49);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_52);
    stringBuffer.append(TEXT_53);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_54);
    } 
    stringBuffer.append(TEXT_55);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_56);
    stringBuffer.append(TEXT_57);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_58);
    stringBuffer.append(TEXT_59);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_60);
        } else if (step.sendBackTo >= 0) {
    stringBuffer.append(TEXT_61);
    stringBuffer.append( data.getNodeName(data.matchingSteps.get(step.sendBackTo).node) );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( step.sendBackTo );
    stringBuffer.append(TEXT_63);
    if (logging) { 
    stringBuffer.append(TEXT_64);
    } 
    stringBuffer.append(TEXT_65);
        }
      if (i>0) {
    stringBuffer.append(TEXT_66);
    
       }
    stringBuffer.append(TEXT_67);
        }
      if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_68);
    
      } else {
    stringBuffer.append(TEXT_69);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_70);
    
      }
    stringBuffer.append(TEXT_71);
     

    } // end for


    stringBuffer.append(TEXT_72);
    if (logging) { 
    stringBuffer.append(TEXT_73);
    }
  for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_74);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_76);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_77);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_78);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_79);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_81);
      }

    if (!changeInfo.getCreatedNodes().isEmpty()) {
    stringBuffer.append(TEXT_82);
      }

    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_83);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( data.typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_86);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_87);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_88);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_89);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_90);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_91);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_92);
    	} else { 
    stringBuffer.append(TEXT_93);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_94);
    stringBuffer.append( data.orderedLhsNodes.indexOf(
                                         data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_95);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_96);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_97);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_98);
    	} else { 
    stringBuffer.append(TEXT_99);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_100);
    stringBuffer.append( data.orderedLhsNodes.indexOf(
                                         data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_101);
    	} 
    stringBuffer.append(TEXT_102);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_103);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_104);
    stringBuffer.append( data.typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_105);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_106);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_107);
      e++;
    } 
    stringBuffer.append(TEXT_108);
    stringBuffer.append(TEXT_109);
    return stringBuffer.toString();
  }
}
