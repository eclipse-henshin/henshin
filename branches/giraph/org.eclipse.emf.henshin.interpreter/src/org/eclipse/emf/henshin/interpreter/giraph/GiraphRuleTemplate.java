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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "" + NL + "import org.apache.giraph.aggregators.LongSumAggregator;" + NL + "import org.apache.giraph.edge.Edge;";
  protected final String TEXT_3 = NL + "import org.apache.giraph.edge.EdgeFactory;";
  protected final String TEXT_4 = NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.master.DefaultMasterCompute;" + NL + "import org.apache.hadoop.io.ByteWritable;" + NL + "import org.apache.hadoop.io.LongWritable;";
  protected final String TEXT_5 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * Generated implementation of the Henshin unit \"";
  protected final String TEXT_7 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_8 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_9 = " extends" + NL + "  BasicComputation<HenshinUtil.VertexId, ByteWritable," + NL + "  ByteWritable, HenshinUtil.Match> {" + NL + "" + NL + "  /**" + NL + "   * Name of the rule application count aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_RULE_APPLICATIONS = \"ruleApps\";" + NL + "" + NL + "  /**" + NL + "   * Name of the node generation aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_NODE_GENERATION = \"nodeGen\";" + NL + "" + NL + "  /**" + NL + "   * Name of the application stack aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_APPLICATION_STACK = \"appStack\";" + NL + "" + NL + "  /**" + NL + "   * Default number of applications of this rule." + NL + "   */" + NL + "  public static final int DEFAULT_APPLICATION_COUNT = ";
  protected final String TEXT_10 = ";";
  protected final String TEXT_11 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_12 = "\"." + NL + "   */" + NL + "  public static final ByteWritable ";
  protected final String TEXT_13 = NL + "    = new ByteWritable((byte) ";
  protected final String TEXT_14 = ");";
  protected final String TEXT_15 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  protected static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_16 = ".class);";
  protected final String TEXT_17 = NL + NL + "  /**" + NL + "   * Number of applications of this rule." + NL + "   */" + NL + "  private int applicationCount = DEFAULT_APPLICATION_COUNT;" + NL;
  protected final String TEXT_18 = NL + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches) throws IOException {" + NL + "" + NL + "    // Get the current superstep:" + NL + "    long superstep = getSuperstep();";
  protected final String TEXT_19 = NL + "    LOG.info(\"Vertex \" + vertex.getId() + \" executing superstep \" + superstep);";
  protected final String TEXT_20 = NL + NL + "    // Check if we can stop:" + NL + "    if (superstep >= applicationCount * ";
  protected final String TEXT_21 = ") {" + NL + "      vertex.voteToHalt();" + NL + "      return;" + NL + "    }" + NL;
  protected final String TEXT_22 = NL + "    // Log received (partial) matches:" + NL + "    for (HenshinUtil.Match match : matches) {" + NL + "      LOG.info(\"Vertex \" + vertex.getId() +" + NL + "        \" received (partial) match \" + match);" + NL + "    }";
  protected final String TEXT_23 = NL + NL + "    match";
  protected final String TEXT_24 = "(vertex, matches, (int) (superstep % ";
  protected final String TEXT_25 = "));" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Match (and apply) the rule \"";
  protected final String TEXT_26 = "\"." + NL + "   * This takes ";
  protected final String TEXT_27 = " supersteps." + NL + "   * @param vertex The current vertex." + NL + "   * @param matches The current matches." + NL + "   * @param microstep Current microstep." + NL + "   */" + NL + "  protected void match";
  protected final String TEXT_28 = "(" + NL + "      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches," + NL + "      int microstep) throws IOException {" + NL + NL;
  protected final String TEXT_29 = " if (microstep == ";
  protected final String TEXT_30 = ") {" + NL;
  protected final String TEXT_31 = NL + "      // Node ";
  protected final String TEXT_32 = ": check for edge to match of ";
  protected final String TEXT_33 = " of type \"";
  protected final String TEXT_34 = "\":" + NL + "      for (HenshinUtil.Match match : matches) {" + NL + "        HenshinUtil.VertexId targetId = match.getVertexId(";
  protected final String TEXT_35 = ");" + NL + "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :" + NL + "          vertex.getEdges()) {" + NL + "          if (edge.getValue().get() ==";
  protected final String TEXT_36 = NL + "            ";
  protected final String TEXT_37 = ".get() &&" + NL + "            edge.getTargetVertexId().equals(targetId)) {";
  protected final String TEXT_38 = NL + "            // Apply the rule:" + NL + "            apply";
  protected final String TEXT_39 = "(vertex, match);";
  protected final String TEXT_40 = NL + "          }" + NL + "        }" + NL + "      }" + NL;
  protected final String TEXT_41 = NL + "      // Matching node ";
  protected final String TEXT_42 = ". Type must be \"";
  protected final String TEXT_43 = "\":" + NL + "      boolean ok = vertex.getValue().get() ==";
  protected final String TEXT_44 = NL + "        ";
  protected final String TEXT_45 = ".get();" + NL + "      if (ok) {";
  protected final String TEXT_46 = NL + "        // Create a new partial match:" + NL + "        HenshinUtil.Match match =" + NL + "          new HenshinUtil.Match().append(vertex.getId());";
  protected final String TEXT_47 = NL + "        // Extend all partial matches:" + NL + "        for (HenshinUtil.Match match : matches) {" + NL + "          match = match.append(vertex.getId());";
  protected final String TEXT_48 = NL;
  protected final String TEXT_49 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_50 = "\":";
  protected final String TEXT_51 = NL;
  protected final String TEXT_52 = "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :";
  protected final String TEXT_53 = NL;
  protected final String TEXT_54 = "          vertex.getEdges()) {";
  protected final String TEXT_55 = NL;
  protected final String TEXT_56 = "          if (edge.getValue().get() ==";
  protected final String TEXT_57 = NL;
  protected final String TEXT_58 = "            ";
  protected final String TEXT_59 = ".get()) {";
  protected final String TEXT_60 = NL;
  protected final String TEXT_61 = "            LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_62 = NL;
  protected final String TEXT_63 = "              \" sending (partial) match \" + match +";
  protected final String TEXT_64 = NL;
  protected final String TEXT_65 = "              \" to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_66 = NL;
  protected final String TEXT_67 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_68 = NL;
  protected final String TEXT_69 = "          }";
  protected final String TEXT_70 = NL;
  protected final String TEXT_71 = "        }";
  protected final String TEXT_72 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_73 = ":" + NL + "          for (HenshinUtil.Match m : matches) {" + NL + "            HenshinUtil.VertexId targetVertexId =" + NL + "              m.getVertexId(";
  protected final String TEXT_74 = ");";
  protected final String TEXT_75 = NL + "            LOG.info(\"Vertex \" + vertex.getId() +" + NL + "              \" sending (partial) match \" + match +" + NL + "              \" to vertex \" + targetVertexId);";
  protected final String TEXT_76 = NL + "            sendMessage(targetVertexId, match);" + NL + "          }";
  protected final String TEXT_77 = NL + "          // Apply the rule:" + NL + "          apply";
  protected final String TEXT_78 = "(vertex, match);";
  protected final String TEXT_79 = NL + "        }";
  protected final String TEXT_80 = NL + "      } // end if ok";
  protected final String TEXT_81 = NL + NL + "    }";
  protected final String TEXT_82 = NL + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule ";
  protected final String TEXT_83 = "to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  protected void apply";
  protected final String TEXT_84 = "(" + NL + "    Vertex<HenshinUtil.VertexId, ByteWritable," + NL + "    ByteWritable> vertex," + NL + "    HenshinUtil.Match match) throws IOException {" + NL;
  protected final String TEXT_85 = NL + "    LOG.info(\"Vertex \" + vertex.getId() +" + NL + "      \" applying rule with match \" + match);" + NL;
  protected final String TEXT_86 = NL + "    HenshinUtil.VertexId cur";
  protected final String TEXT_87 = " = match.getVertexId(";
  protected final String TEXT_88 = ");";
  protected final String TEXT_89 = NL + NL + "    // Remove edge ";
  protected final String TEXT_90 = " -> ";
  protected final String TEXT_91 = ":" + NL + "    removeEdgesRequest(cur";
  protected final String TEXT_92 = ", cur";
  protected final String TEXT_93 = ");";
  protected final String TEXT_94 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_95 = ":" + NL + "    removeVertexRequest(cur";
  protected final String TEXT_96 = ");";
  protected final String TEXT_97 = NL + NL + "    // Create vertex ";
  protected final String TEXT_98 = ":" + NL + "    HenshinUtil.VertexId new";
  protected final String TEXT_99 = " =";
  protected final String TEXT_100 = NL + "      HenshinUtil.VertexId.randomVertexId();";
  protected final String TEXT_101 = NL + "      deriveVertexId(vertex.getId(), (byte) ";
  protected final String TEXT_102 = ");";
  protected final String TEXT_103 = NL + "    addVertexRequest(new";
  protected final String TEXT_104 = ", ";
  protected final String TEXT_105 = ");";
  protected final String TEXT_106 = NL + NL + "    // Create edge ";
  protected final String TEXT_107 = " -> ";
  protected final String TEXT_108 = ":";
  protected final String TEXT_109 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_110 = " = new";
  protected final String TEXT_111 = ";";
  protected final String TEXT_112 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_113 = " = cur";
  protected final String TEXT_114 = ";";
  protected final String TEXT_115 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_116 = " = new";
  protected final String TEXT_117 = ";";
  protected final String TEXT_118 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_119 = " = cur";
  protected final String TEXT_120 = ";";
  protected final String TEXT_121 = NL + "    Edge<HenshinUtil.VertexId, ByteWritable> edge";
  protected final String TEXT_122 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_123 = ", ";
  protected final String TEXT_124 = ");" + NL + "    addEdgeRequest(src";
  protected final String TEXT_125 = ", edge";
  protected final String TEXT_126 = ");";
  protected final String TEXT_127 = NL + NL + "    // Update the statistics:" + NL + "    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));" + NL + "" + NL + "  }" + NL;
  protected final String TEXT_128 = NL;
  protected final String TEXT_129 = NL + "  /**" + NL + "   * Derive a new vertex Id from an exiting one." + NL + "   * @param baseId The base vertex Id." + NL + "   * @param vertexIndex The relative index of the new vertex." + NL + "   * @return The derived vertex Id." + NL + "   */" + NL + "  private HenshinUtil.VertexId deriveVertexId(" + NL + "    HenshinUtil.VertexId baseId, int vertexIndex) {";
  protected final String TEXT_130 = NL + "    int appCount = applicationCount;" + NL + "    int bitsNeededForApp = 0;" + NL + "    while (appCount > 0) {" + NL + "      appCount = appCount / 2;" + NL + "      bitsNeededForApp++;" + NL + "    }" + NL + "    long code = ((LongWritable) getAggregatedValue(" + NL + "        AGGREGATOR_NODE_GENERATION)).get();" + NL + "    if (bitsNeededForApp <= ";
  protected final String TEXT_131 = ") {" + NL + "      code = ((code << ";
  protected final String TEXT_132 = ")) | vertexIndex;" + NL + "      return baseId.append((byte) code);" + NL + "    } else {" + NL + "      return baseId.append((byte) code).append((byte) vertexIndex);" + NL + "    }" + NL + "  }";
  protected final String TEXT_133 = NL + NL + "  /**" + NL + "   * Master compute which registers and updates the required aggregators." + NL + "   */" + NL + "  public static class MasterCompute extends DefaultMasterCompute {" + NL + "" + NL + "    @Override" + NL + "    public void compute() {" + NL + "//      LongWritable myValue =" + NL + "//          new LongWritable(MASTER_VALUE * (1L << superstep));" + NL + "//      setAggregatedValue(MASTER_WRITE_AGG, myValue);" + NL + "" + NL + "      // Update node generation aggregator value:" + NL + "      if (getSuperstep() > 0) {" + NL + "        LongWritable ruleApps = (LongWritable)" + NL + "          getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS);";
  protected final String TEXT_134 = NL + "        LOG.info(ruleApps.get() + \" rule applications in superstep \" +" + NL + "          (getSuperstep() - 1));";
  protected final String TEXT_135 = NL + "        if (ruleApps.get() > 0) {" + NL + "          LongWritable nodeGen = (LongWritable)" + NL + "            getAggregatedValue(AGGREGATOR_NODE_GENERATION);" + NL + "          nodeGen.set(nodeGen.get() + 1);" + NL + "          setAggregatedValue(AGGREGATOR_NODE_GENERATION, nodeGen);" + NL + "        }" + NL + "      }" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    public void initialize() throws InstantiationException," + NL + "        IllegalAccessException {" + NL + "      registerAggregator(AGGREGATOR_RULE_APPLICATIONS," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_NODE_GENERATION," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_APPLICATION_STACK," + NL + "        HenshinUtil.ApplicationStackAggregator.class);" + NL + "    }" + NL + "" + NL + "  }" + NL + "}";
  protected final String TEXT_136 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

@SuppressWarnings("unchecked")
Map<Rule,GiraphRuleData> ruleData = (Map<Rule,GiraphRuleData>) args.get("ruleData");

Unit mainUnit = (Unit) args.get("mainUnit");
String className = (String) args.get("className");
String packageName = (String) args.get("packageName");
boolean logging = (Boolean) args.get("logging");
boolean useUUIDs = (Boolean) args.get("useUUIDs");
int applicationCount = (Integer) args.get("applicationCount");

boolean needsEdgeFactory = false;
int maxCreatedNodes = 0;
for (GiraphRuleData data : ruleData.values()) {
  if (!data.changeInfo.getCreatedEdges().isEmpty()) {
    needsEdgeFactory = true;
  }
  maxCreatedNodes = Math.max(maxCreatedNodes, data.changeInfo.getCreatedNodes().size());
}


    stringBuffer.append(TEXT_1);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_2);
     if (needsEdgeFactory) { 
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
    if (logging) { 
    stringBuffer.append(TEXT_5);
    } 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( applicationCount );
    stringBuffer.append(TEXT_10);
    
int value = 0;
Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(mainUnit.getModule());
for (ENamedElement type : typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_11);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( typeConstants.get(type) );
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
    

// Generate the code for all rules: 
for (Rule rule : ruleData.keySet()) {
  GiraphRuleData data = ruleData.get(rule);
  RuleChangeInfo changeInfo = new RuleChangeInfo(rule);


    stringBuffer.append(TEXT_18);
    if (logging) { 
    stringBuffer.append(TEXT_19);
    } 
    stringBuffer.append(TEXT_20);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_21);
    if (logging) { 
    stringBuffer.append(TEXT_22);
    } 
    stringBuffer.append(TEXT_23);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_28);
     
    for (int i=0; i<data.matchingSteps.size(); i++) {
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_30);
        if (step.verifyEdgeTo != null) {
    stringBuffer.append(TEXT_31);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.verifyEdgeTo) );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_37);
            if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_38);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_39);
            } 
    stringBuffer.append(TEXT_40);
        } else {
    stringBuffer.append(TEXT_41);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_43);
    stringBuffer.append(TEXT_44);
    stringBuffer.append( typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_45);
        if (step.isStart) {
    stringBuffer.append(TEXT_46);
     } else {
    stringBuffer.append(TEXT_47);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_48);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_49);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_52);
    stringBuffer.append(TEXT_53);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_54);
    stringBuffer.append(TEXT_55);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_56);
    stringBuffer.append(TEXT_57);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_58);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_59);
    if (logging) { 
    stringBuffer.append(TEXT_60);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_61);
    stringBuffer.append(TEXT_62);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_63);
    stringBuffer.append(TEXT_64);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_65);
    } 
    stringBuffer.append(TEXT_66);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_67);
    stringBuffer.append(TEXT_68);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_69);
    stringBuffer.append(TEXT_70);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_71);
    
      } else if (step.sendBackTo != null) {
    stringBuffer.append(TEXT_72);
    stringBuffer.append( GiraphUtil.getNodeName(step.sendBackTo) );
    stringBuffer.append(TEXT_73);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_74);
    if (logging) { 
    stringBuffer.append(TEXT_75);
    } 
    stringBuffer.append(TEXT_76);
    
      } else if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_77);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_78);
        }
      if (!step.isStart) {
    stringBuffer.append(TEXT_79);
    
       }
    stringBuffer.append(TEXT_80);
    
      }
    stringBuffer.append(TEXT_81);
     

    } // end for


    stringBuffer.append(TEXT_82);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_83);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_84);
    if (logging) { 
    stringBuffer.append(TEXT_85);
    }
  for (int j = 0; j < data.orderedLhsNodes.size(); j++) {
    Node lhsNode = data.orderedLhsNodes.get(j);
    Node rhsNode = data.rule.getMappings().getImage(lhsNode, data.rule.getRhs());
    boolean needed = changeInfo.getDeletedNodes().contains(lhsNode);
    for (Edge edge : lhsNode.getAllEdges()) {
      needed = needed || changeInfo.getDeletedEdges().contains(edge);
    }
    if (rhsNode!=null) {
      for (Edge edge : rhsNode.getAllEdges()) {
        needed = needed || changeInfo.getCreatedEdges().contains(edge);
      }
    }
    if (needed) { 
    stringBuffer.append(TEXT_86);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_87);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_88);
      }
  }

  for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_89);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_90);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_91);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_93);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_94);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_95);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_96);
      }

    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_97);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_98);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_99);
     if (useUUIDs) { 
    stringBuffer.append(TEXT_100);
     } else { 
    stringBuffer.append(TEXT_101);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_102);
     } 
    stringBuffer.append(TEXT_103);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_104);
    stringBuffer.append( typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_105);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_106);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_107);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_108);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_109);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_110);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_111);
    	} else { 
    stringBuffer.append(TEXT_112);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_113);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_114);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_115);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_116);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_117);
    	} else { 
    stringBuffer.append(TEXT_118);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_119);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_120);
    	} 
    stringBuffer.append(TEXT_121);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_122);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_123);
    stringBuffer.append( typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_124);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_125);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_126);
      e++;
    } 
    stringBuffer.append(TEXT_127);
    
} // end of for all rules

    stringBuffer.append(TEXT_128);
     if (!useUUIDs) { 
    stringBuffer.append(TEXT_129);
    
    int createdNodes = maxCreatedNodes;
    int bitsNeededForIndex = 0;
    while (createdNodes > 0) {
      createdNodes = createdNodes / 2;
      bitsNeededForIndex++;
    }

    stringBuffer.append(TEXT_130);
    stringBuffer.append( 8 - bitsNeededForIndex );
    stringBuffer.append(TEXT_131);
    stringBuffer.append( bitsNeededForIndex );
    stringBuffer.append(TEXT_132);
     } 
    stringBuffer.append(TEXT_133);
     if (logging) {
    stringBuffer.append(TEXT_134);
     } 
    stringBuffer.append(TEXT_135);
    stringBuffer.append(TEXT_136);
    return stringBuffer.toString();
  }
}
