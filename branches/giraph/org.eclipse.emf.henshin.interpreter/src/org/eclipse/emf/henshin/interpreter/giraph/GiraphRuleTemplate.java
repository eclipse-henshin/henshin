package org.eclipse.emf.henshin.interpreter.giraph;

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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "" + NL + "import org.apache.giraph.edge.Edge;";
  protected final String TEXT_3 = NL + "import org.apache.giraph.edge.EdgeFactory;";
  protected final String TEXT_4 = NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.hadoop.io.ByteWritable;";
  protected final String TEXT_5 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * Generated implementation of the Henshin rule \"";
  protected final String TEXT_7 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_8 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_9 = " extends" + NL + "  BasicComputation<HenshinUtil.VertexId, ByteWritable," + NL + "  ByteWritable, HenshinUtil.Match> {" + NL + "" + NL + "  /**" + NL + "   * Default number of applications of this rule." + NL + "   */" + NL + "  public static final int DEFAULT_APPLICATION_COUNT = ";
  protected final String TEXT_10 = ";";
  protected final String TEXT_11 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_12 = "\"." + NL + "   */" + NL + "  public static final ByteWritable ";
  protected final String TEXT_13 = NL + "    = new ByteWritable((byte) ";
  protected final String TEXT_14 = ");";
  protected final String TEXT_15 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  private static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_16 = ".class);";
  protected final String TEXT_17 = NL + NL + "  /**" + NL + "   * Number of applications of this rule." + NL + "   */" + NL + "  private int applicationCount = DEFAULT_APPLICATION_COUNT;" + NL + "" + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches) throws IOException {" + NL + "" + NL + "    // Get the current superstep:" + NL + "    long superstep = getSuperstep();";
  protected final String TEXT_18 = NL + "    LOG.info(\"Vertex \" + vertex.getId() + \" executing superstep \" + superstep);";
  protected final String TEXT_19 = NL + NL + "    // Check if we can stop:" + NL + "    if (superstep >= applicationCount * ";
  protected final String TEXT_20 = ") {" + NL + "      vertex.voteToHalt();" + NL + "      return;" + NL + "    }" + NL;
  protected final String TEXT_21 = NL + "    // Log received (partial) matches:" + NL + "    for (HenshinUtil.Match match : matches) {" + NL + "      LOG.info(\"Vertex \" + vertex.getId() +" + NL + "        \" received (partial) match \" + match);" + NL + "    }";
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = " if (superstep % ";
  protected final String TEXT_24 = " == ";
  protected final String TEXT_25 = ") {" + NL;
  protected final String TEXT_26 = NL + "      // Node ";
  protected final String TEXT_27 = ": check for edge to match of ";
  protected final String TEXT_28 = " of type \"";
  protected final String TEXT_29 = "\":" + NL + "      for (HenshinUtil.Match match : matches) {" + NL + "        HenshinUtil.VertexId targetId = match.getVertexId(";
  protected final String TEXT_30 = ");" + NL + "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :" + NL + "          vertex.getEdges()) {" + NL + "          if (edge.getValue().get() ==";
  protected final String TEXT_31 = NL + "            ";
  protected final String TEXT_32 = ".get() &&" + NL + "            edge.getTargetVertexId().equals(targetId)) {";
  protected final String TEXT_33 = NL + "            // Apply the rule:" + NL + "            applyRule(vertex, match);";
  protected final String TEXT_34 = NL + "          }" + NL + "        }" + NL + "      }" + NL;
  protected final String TEXT_35 = NL + "      // Matching node ";
  protected final String TEXT_36 = ". Type must be \"";
  protected final String TEXT_37 = "\":" + NL + "      boolean ok = vertex.getValue().get() ==";
  protected final String TEXT_38 = NL + "        ";
  protected final String TEXT_39 = ".get();" + NL + "      if (ok) {";
  protected final String TEXT_40 = NL + "        // Create a new partial match:" + NL + "        HenshinUtil.Match match =" + NL + "          new HenshinUtil.Match().extend(vertex.getId());";
  protected final String TEXT_41 = NL + "        // Extend all partial matches:" + NL + "        for (HenshinUtil.Match match : matches) {" + NL + "          match = match.extend(vertex.getId());";
  protected final String TEXT_42 = NL;
  protected final String TEXT_43 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_44 = "\":";
  protected final String TEXT_45 = NL;
  protected final String TEXT_46 = "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :";
  protected final String TEXT_47 = NL;
  protected final String TEXT_48 = "          vertex.getEdges()) {";
  protected final String TEXT_49 = NL;
  protected final String TEXT_50 = "          if (edge.getValue().get() ==";
  protected final String TEXT_51 = NL;
  protected final String TEXT_52 = "            ";
  protected final String TEXT_53 = ".get()) {";
  protected final String TEXT_54 = NL;
  protected final String TEXT_55 = "            LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_56 = NL;
  protected final String TEXT_57 = "              \" sending (partial) match \" + match +";
  protected final String TEXT_58 = NL;
  protected final String TEXT_59 = "              \" to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_60 = NL;
  protected final String TEXT_61 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_62 = NL;
  protected final String TEXT_63 = "          }";
  protected final String TEXT_64 = NL;
  protected final String TEXT_65 = "        }";
  protected final String TEXT_66 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_67 = ":" + NL + "          for (HenshinUtil.Match m : matches) {" + NL + "            HenshinUtil.VertexId targetVertexId =" + NL + "              m.getVertexId(";
  protected final String TEXT_68 = ");";
  protected final String TEXT_69 = NL + "            LOG.info(\"Vertex \" + vertex.getId() +" + NL + "              \" sending (partial) match \" + match +" + NL + "              \" to vertex \" + targetVertexId);";
  protected final String TEXT_70 = NL + "            sendMessage(targetVertexId, match);" + NL + "          }";
  protected final String TEXT_71 = NL + "          // Apply the rule:" + NL + "          applyRule(vertex, match);";
  protected final String TEXT_72 = NL + "        }";
  protected final String TEXT_73 = NL + "      } // end if ok";
  protected final String TEXT_74 = NL + NL + "      // In the last iteration the vertex can be made inactive:" + NL + "      if (superstep / ";
  protected final String TEXT_75 = " == applicationCount - 1) {" + NL + "        vertex.voteToHalt();" + NL + "      }";
  protected final String TEXT_76 = NL + NL + "    }";
  protected final String TEXT_77 = NL + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  private void applyRule(Vertex<HenshinUtil.VertexId, ByteWritable," + NL + "    ByteWritable> vertex, HenshinUtil.Match match) throws IOException {" + NL;
  protected final String TEXT_78 = NL + "    LOG.info(\"Vertex \" + vertex.getId() +" + NL + "      \" applying rule with match \" + match);" + NL;
  protected final String TEXT_79 = NL + "    HenshinUtil.VertexId cur";
  protected final String TEXT_80 = " = match.getVertexId(";
  protected final String TEXT_81 = ");";
  protected final String TEXT_82 = NL + NL + "    // Remove edge ";
  protected final String TEXT_83 = " -> ";
  protected final String TEXT_84 = ":" + NL + "    removeEdgesRequest(cur";
  protected final String TEXT_85 = ", cur";
  protected final String TEXT_86 = ");";
  protected final String TEXT_87 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_88 = ":" + NL + "    removeVertexRequest(cur";
  protected final String TEXT_89 = ");";
  protected final String TEXT_90 = NL + NL + "    // Create vertex ";
  protected final String TEXT_91 = ":" + NL + "    HenshinUtil.VertexId new";
  protected final String TEXT_92 = " =" + NL + "      deriveVertexId(vertex.getId(), (byte) ";
  protected final String TEXT_93 = ");" + NL + "    addVertexRequest(new";
  protected final String TEXT_94 = ", ";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = NL + NL + "    // Create edge ";
  protected final String TEXT_97 = " -> ";
  protected final String TEXT_98 = ":";
  protected final String TEXT_99 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_100 = " = new";
  protected final String TEXT_101 = ";";
  protected final String TEXT_102 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_103 = " = cur";
  protected final String TEXT_104 = ";";
  protected final String TEXT_105 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_106 = " = new";
  protected final String TEXT_107 = ";";
  protected final String TEXT_108 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_109 = " = cur";
  protected final String TEXT_110 = ";";
  protected final String TEXT_111 = NL + "    Edge<HenshinUtil.VertexId, ByteWritable> edge";
  protected final String TEXT_112 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_113 = ", ";
  protected final String TEXT_114 = ");" + NL + "    addEdgeRequest(src";
  protected final String TEXT_115 = ", edge";
  protected final String TEXT_116 = ");";
  protected final String TEXT_117 = NL + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Derive a new vertex Id from an exiting one." + NL + "   * @param baseId The base vertex Id." + NL + "   * @param vertexIndex The relative index of the new vertex." + NL + "   * @return The derived vertex Id." + NL + "   */" + NL + "  private HenshinUtil.VertexId deriveVertexId(" + NL + "    HenshinUtil.VertexId baseId, int vertexIndex) {";
  protected final String TEXT_118 = NL + "    int appCount = applicationCount;" + NL + "    int bitsNeededForApp = 0;" + NL + "    while (appCount > 0) {" + NL + "      appCount = appCount / 2;" + NL + "      bitsNeededForApp++;" + NL + "    }" + NL + "    long code = (getSuperstep() + 1) / ";
  protected final String TEXT_119 = ";" + NL + "    if (bitsNeededForApp <= ";
  protected final String TEXT_120 = ") {" + NL + "      code = ((code << ";
  protected final String TEXT_121 = ")) | vertexIndex;" + NL + "      return baseId.extend((byte) code);" + NL + "    } else {" + NL + "      return baseId.extend((byte) code).extend((byte) vertexIndex);" + NL + "    }" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Get the number of application to be executed for this rule." + NL + "   * @return the number of rule applications." + NL + "   */" + NL + "  public int getApplicationCount() {" + NL + "    return applicationCount;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Set the number of application to be executed for this rule." + NL + "   * @param applicationCount The new number of rule applications." + NL + "   */" + NL + "  public void setApplicationCount(int applicationCount) {" + NL + "    this.applicationCount = applicationCount;" + NL + "  }" + NL + "" + NL + "}";
  protected final String TEXT_122 = NL;

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
     if (!changeInfo.getCreatedEdges().isEmpty()) { 
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
    if (logging) { 
    stringBuffer.append(TEXT_5);
    } 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( applicationCount );
    stringBuffer.append(TEXT_10);
    
int value = 0;
for (ENamedElement type : data.typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_11);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( data.typeConstants.get(type) );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_14);
    
}

if (logging) {

    stringBuffer.append(TEXT_15);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_16);
     } 
    stringBuffer.append(TEXT_17);
    if (logging) { 
    stringBuffer.append(TEXT_18);
    } 
    stringBuffer.append(TEXT_19);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_20);
    if (logging) { 
    stringBuffer.append(TEXT_21);
    } 
    stringBuffer.append(TEXT_22);
     
    for (int i=0; i<data.matchingSteps.size(); i++) {
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_25);
        if (step.verifyEdgeTo != null) {
    stringBuffer.append(TEXT_26);
    stringBuffer.append( data.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( data.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.verifyEdgeTo) );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(TEXT_31);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_32);
            if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_33);
            } 
    stringBuffer.append(TEXT_34);
        } else {
    stringBuffer.append(TEXT_35);
    stringBuffer.append( data.getNodeName(step.node) );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append( data.typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_39);
        if (i == 0) {
    stringBuffer.append(TEXT_40);
     } else {
    stringBuffer.append(TEXT_41);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_42);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_43);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_44);
    stringBuffer.append(TEXT_45);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_46);
    stringBuffer.append(TEXT_47);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_48);
    stringBuffer.append(TEXT_49);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_52);
    stringBuffer.append( data.typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_53);
    if (logging) { 
    stringBuffer.append(TEXT_54);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_55);
    stringBuffer.append(TEXT_56);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_57);
    stringBuffer.append(TEXT_58);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_59);
    } 
    stringBuffer.append(TEXT_60);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_61);
    stringBuffer.append(TEXT_62);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_63);
    stringBuffer.append(TEXT_64);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_65);
    
      } else if (step.sendBackTo != null) {
    stringBuffer.append(TEXT_66);
    stringBuffer.append( data.getNodeName(step.sendBackTo) );
    stringBuffer.append(TEXT_67);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_68);
    if (logging) { 
    stringBuffer.append(TEXT_69);
    } 
    stringBuffer.append(TEXT_70);
    
      } else if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_71);
        }
      if (i>0) {
    stringBuffer.append(TEXT_72);
    
       }
    stringBuffer.append(TEXT_73);
    
      if (i < data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_74);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_75);
        }
      }
    stringBuffer.append(TEXT_76);
     

    } // end for


    stringBuffer.append(TEXT_77);
    if (logging) { 
    stringBuffer.append(TEXT_78);
    }
  for (int j = 0; j < data.orderedLhsNodes.size(); j++) {

    stringBuffer.append(TEXT_79);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_81);
    }

  for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_82);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_83);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_86);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_87);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_88);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_89);
      }

    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_90);
    stringBuffer.append( data.getNodeName(node) );
    stringBuffer.append(TEXT_91);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_93);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_94);
    stringBuffer.append( data.typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_95);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_96);
    stringBuffer.append( data.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_97);
    stringBuffer.append( data.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_98);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_99);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_100);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_101);
    	} else { 
    stringBuffer.append(TEXT_102);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_103);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_104);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_105);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_106);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_107);
    	} else { 
    stringBuffer.append(TEXT_108);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_109);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_110);
    	} 
    stringBuffer.append(TEXT_111);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_112);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_113);
    stringBuffer.append( data.typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_114);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_115);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_116);
      e++;
    } 
    stringBuffer.append(TEXT_117);
    
    int createdNodes = changeInfo.getCreatedNodes().size();
    int bitsNeededForIndex = 0;
    while (createdNodes > 0) {
      createdNodes = createdNodes / 2;
      bitsNeededForIndex++;
    }

    stringBuffer.append(TEXT_118);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_119);
    stringBuffer.append( 8 - bitsNeededForIndex );
    stringBuffer.append(TEXT_120);
    stringBuffer.append( bitsNeededForIndex );
    stringBuffer.append(TEXT_121);
    stringBuffer.append(TEXT_122);
    return stringBuffer.toString();
  }
}
