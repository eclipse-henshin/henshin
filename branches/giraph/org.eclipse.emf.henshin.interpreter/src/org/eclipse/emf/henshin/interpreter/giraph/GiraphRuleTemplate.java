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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.ArrayDeque;";
  protected final String TEXT_3 = NL + "import java.util.ArrayList;" + NL + "import java.util.Collections;";
  protected final String TEXT_4 = NL + "import java.util.Deque;" + NL + "import java.util.List;" + NL + "" + NL + "import org.apache.giraph.aggregators.LongSumAggregator;" + NL + "import org.apache.giraph.edge.Edge;";
  protected final String TEXT_5 = NL + "import org.apache.giraph.edge.EdgeFactory;";
  protected final String TEXT_6 = NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.master.DefaultMasterCompute;" + NL + "import org.apache.hadoop.io.ByteWritable;" + NL + "import org.apache.hadoop.io.LongWritable;";
  protected final String TEXT_7 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_8 = NL + NL + "/**" + NL + " * Generated implementation of the Henshin unit \"";
  protected final String TEXT_9 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_10 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_11 = " extends" + NL + "  BasicComputation<HenshinUtil.VertexId, ByteWritable," + NL + "  ByteWritable, HenshinUtil.Match> {" + NL + "" + NL + "  /**" + NL + "   * Name of the rule application count aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_RULE_APPLICATIONS = \"ruleApps\";" + NL + "" + NL + "  /**" + NL + "   * Name of the node generation aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_NODE_GENERATION = \"nodeGen\";" + NL + "" + NL + "  /**" + NL + "   * Name of the application stack aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_APPLICATION_STACK = \"appStack\";";
  protected final String TEXT_12 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_13 = "\"." + NL + "   */" + NL + "  public static final ByteWritable ";
  protected final String TEXT_14 = NL + "    = new ByteWritable((byte) ";
  protected final String TEXT_15 = ");";
  protected final String TEXT_16 = NL + NL + "  /**" + NL + "   * ";
  protected final String TEXT_17 = " constant for \"";
  protected final String TEXT_18 = "\"." + NL + "   */" + NL + "  public static final int ";
  protected final String TEXT_19 = " = ";
  protected final String TEXT_20 = ";";
  protected final String TEXT_21 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  protected static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_22 = ".class);";
  protected final String TEXT_23 = NL + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches) throws IOException {" + NL + "" + NL + "    // Get the current application stack:" + NL + "    HenshinUtil.ApplicationStack stack =" + NL + "      getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "" + NL + "    // Check if we should stop:" + NL + "    if (stack.getStackSize() == 0) {" + NL + "      long ruleApps = ((LongWritable)" + NL + "        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();" + NL + "      if (ruleApps == 0) {" + NL + "        vertex.voteToHalt();" + NL + "      }" + NL + "      return;" + NL + "    }" + NL + "" + NL + "    // Get the last step:" + NL + "    int rule = stack.getLastUnit();" + NL + "    int microstep = stack.getLastMicrostep();" + NL + "" + NL + "    // Find out which rule to apply:" + NL + "    switch (rule) {";
  protected final String TEXT_24 = NL + "    case ";
  protected final String TEXT_25 = ":" + NL + "      match";
  protected final String TEXT_26 = "(vertex, matches, microstep);" + NL + "      break;";
  protected final String TEXT_27 = NL + "    default:" + NL + "      throw new RuntimeException(\"Unknown rule: \" + rule);" + NL + "    }" + NL + "  }";
  protected final String TEXT_28 = NL + NL + "  /**" + NL + "   * Match (and apply) the rule \"";
  protected final String TEXT_29 = "\"." + NL + "   * This takes ";
  protected final String TEXT_30 = " microsteps." + NL + "   * @param vertex The current vertex." + NL + "   * @param matches The current matches." + NL + "   * @param microstep Current microstep." + NL + "   */" + NL + "  protected void match";
  protected final String TEXT_31 = "(" + NL + "      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<HenshinUtil.Match> matches," + NL + "      int microstep) throws IOException {";
  protected final String TEXT_32 = NL + NL + "    LOG.info(\"Vertex \" + vertex.getId() + \" in superstep \" + getSuperstep() +" + NL + "      \" matching rule ";
  protected final String TEXT_33 = " in microstep \" + microstep);" + NL + "    for (HenshinUtil.Match match : matches) {" + NL + "      LOG.info(\"Vertex \" + vertex.getId() +" + NL + "        \" received (partial) match \" + match);" + NL + "    }";
  protected final String TEXT_34 = NL + NL;
  protected final String TEXT_35 = " if (microstep == ";
  protected final String TEXT_36 = ") {" + NL;
  protected final String TEXT_37 = NL + "      // Node ";
  protected final String TEXT_38 = ": check for edge to match of ";
  protected final String TEXT_39 = " of type \"";
  protected final String TEXT_40 = "\":" + NL + "      for (HenshinUtil.Match match : matches) {" + NL + "        HenshinUtil.VertexId targetId = match.getVertexId(";
  protected final String TEXT_41 = ");" + NL + "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :" + NL + "          vertex.getEdges()) {" + NL + "          if (edge.getValue().get() ==";
  protected final String TEXT_42 = NL + "            ";
  protected final String TEXT_43 = ".get() &&" + NL + "            edge.getTargetVertexId().equals(targetId)) {";
  protected final String TEXT_44 = NL + "            // Apply the rule:" + NL + "            apply";
  protected final String TEXT_45 = "(vertex, match);";
  protected final String TEXT_46 = NL + "          }" + NL + "        }" + NL + "      }" + NL;
  protected final String TEXT_47 = NL + "      // Matching node ";
  protected final String TEXT_48 = ". Type must be \"";
  protected final String TEXT_49 = "\":" + NL + "      boolean ok = vertex.getValue().get() ==";
  protected final String TEXT_50 = NL + "        ";
  protected final String TEXT_51 = ".get();" + NL + "      if (ok) {";
  protected final String TEXT_52 = NL + "        // Create a new partial match:" + NL + "        HenshinUtil.Match match =" + NL + "          new HenshinUtil.Match().append(vertex.getId());";
  protected final String TEXT_53 = NL + "        // Extend all partial matches:" + NL + "        for (HenshinUtil.Match match : matches) {" + NL + "          match = match.append(vertex.getId());";
  protected final String TEXT_54 = NL;
  protected final String TEXT_55 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_56 = "\":";
  protected final String TEXT_57 = NL;
  protected final String TEXT_58 = "        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :";
  protected final String TEXT_59 = NL;
  protected final String TEXT_60 = "          vertex.getEdges()) {";
  protected final String TEXT_61 = NL;
  protected final String TEXT_62 = "          if (edge.getValue().get() ==";
  protected final String TEXT_63 = NL;
  protected final String TEXT_64 = "            ";
  protected final String TEXT_65 = ".get()) {";
  protected final String TEXT_66 = NL;
  protected final String TEXT_67 = "            LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_68 = NL;
  protected final String TEXT_69 = "              \" sending (partial) match \" + match +";
  protected final String TEXT_70 = NL;
  protected final String TEXT_71 = "              \" to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_72 = NL;
  protected final String TEXT_73 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_74 = NL;
  protected final String TEXT_75 = "          }";
  protected final String TEXT_76 = NL;
  protected final String TEXT_77 = "        }";
  protected final String TEXT_78 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_79 = ":" + NL + "          for (HenshinUtil.Match m : matches) {" + NL + "            HenshinUtil.VertexId targetVertexId =" + NL + "              m.getVertexId(";
  protected final String TEXT_80 = ");";
  protected final String TEXT_81 = NL + "            LOG.info(\"Vertex \" + vertex.getId() +" + NL + "              \" sending (partial) match \" + match +" + NL + "              \" to vertex \" + targetVertexId);";
  protected final String TEXT_82 = NL + "            sendMessage(targetVertexId, match);" + NL + "          }";
  protected final String TEXT_83 = NL + "          // Apply the rule:" + NL + "          apply";
  protected final String TEXT_84 = "(vertex, match);";
  protected final String TEXT_85 = NL + "        }";
  protected final String TEXT_86 = NL + "      } // end if ok";
  protected final String TEXT_87 = NL + NL + "    }";
  protected final String TEXT_88 = " else {" + NL + "      throw new RuntimeException(\"Illegal microstep for rule \" +" + NL + "        \"";
  protected final String TEXT_89 = ": \" + microstep);" + NL + "    }" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule \"";
  protected final String TEXT_90 = "\" to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  protected void apply";
  protected final String TEXT_91 = "(" + NL + "    Vertex<HenshinUtil.VertexId, ByteWritable," + NL + "    ByteWritable> vertex," + NL + "    HenshinUtil.Match match) throws IOException {" + NL;
  protected final String TEXT_92 = NL + "    LOG.info(\"Vertex \" + vertex.getId() +" + NL + "      \" applying rule ";
  protected final String TEXT_93 = " with match \" + match);" + NL;
  protected final String TEXT_94 = NL + "    HenshinUtil.VertexId cur";
  protected final String TEXT_95 = " = match.getVertexId(";
  protected final String TEXT_96 = ");";
  protected final String TEXT_97 = NL + NL + "    // Remove edge ";
  protected final String TEXT_98 = " -> ";
  protected final String TEXT_99 = ":" + NL + "    removeEdgesRequest(cur";
  protected final String TEXT_100 = ", cur";
  protected final String TEXT_101 = ");";
  protected final String TEXT_102 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_103 = ":" + NL + "    removeVertexRequest(cur";
  protected final String TEXT_104 = ");";
  protected final String TEXT_105 = NL + NL + "    // Create vertex ";
  protected final String TEXT_106 = ":" + NL + "    HenshinUtil.VertexId new";
  protected final String TEXT_107 = " =";
  protected final String TEXT_108 = NL + "      HenshinUtil.VertexId.randomVertexId();";
  protected final String TEXT_109 = NL + "      deriveVertexId(vertex.getId(), (byte) ";
  protected final String TEXT_110 = ");";
  protected final String TEXT_111 = NL + "    addVertexRequest(new";
  protected final String TEXT_112 = ", ";
  protected final String TEXT_113 = ");";
  protected final String TEXT_114 = NL + NL + "    // Create edge ";
  protected final String TEXT_115 = " -> ";
  protected final String TEXT_116 = ":";
  protected final String TEXT_117 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_118 = " = new";
  protected final String TEXT_119 = ";";
  protected final String TEXT_120 = NL + "    HenshinUtil.VertexId src";
  protected final String TEXT_121 = " = cur";
  protected final String TEXT_122 = ";";
  protected final String TEXT_123 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_124 = " = new";
  protected final String TEXT_125 = ";";
  protected final String TEXT_126 = NL + "    HenshinUtil.VertexId trg";
  protected final String TEXT_127 = " = cur";
  protected final String TEXT_128 = ";";
  protected final String TEXT_129 = NL + "    Edge<HenshinUtil.VertexId, ByteWritable> edge";
  protected final String TEXT_130 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_131 = ", ";
  protected final String TEXT_132 = ");" + NL + "    addEdgeRequest(src";
  protected final String TEXT_133 = ", edge";
  protected final String TEXT_134 = ");";
  protected final String TEXT_135 = NL + NL + "    // Update the statistics:" + NL + "    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));" + NL + "" + NL + "  }";
  protected final String TEXT_136 = NL;
  protected final String TEXT_137 = NL + "  /**" + NL + "   * Derive a new vertex Id from an exiting one." + NL + "   * @param baseId The base vertex Id." + NL + "   * @param vertexIndex The relative index of the new vertex." + NL + "   * @return The derived vertex Id." + NL + "   */" + NL + "  private HenshinUtil.VertexId deriveVertexId(" + NL + "    HenshinUtil.VertexId baseId, int vertexIndex) {" + NL + "    long generation = ((LongWritable) getAggregatedValue(" + NL + "        AGGREGATOR_NODE_GENERATION)).get();" + NL + "    return baseId.append((byte) generation).append((byte) vertexIndex);" + NL + "  }";
  protected final String TEXT_138 = NL + NL + "  /**" + NL + "   * Master compute which registers and updates the required aggregators." + NL + "   */" + NL + "  public static class MasterCompute extends DefaultMasterCompute {" + NL + "" + NL + "    /**" + NL + "     * Stack for storing unit success flags." + NL + "     */" + NL + "    private final Deque<Boolean> unitSuccesses =" + NL + "      new ArrayDeque<Boolean>();" + NL + "" + NL + "    /**" + NL + "     * Stack for storing the execution orders of independent units." + NL + "     */" + NL + "    private final Deque<List<Integer>> independentUnitOrders =" + NL + "      new ArrayDeque<List<Integer>>();" + NL + "" + NL + "    @Override" + NL + "    public void compute() {" + NL + "" + NL + "      // Get the number of rule applications in the last superstep:" + NL + "      long ruleApps = ((LongWritable)" + NL + "        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();";
  protected final String TEXT_139 = NL + "      if (getSuperstep() > 0) {" + NL + "        LOG.info(ruleApps + \" rule applications in superstep \" +" + NL + "          (getSuperstep() - 1));" + NL + "      }";
  protected final String TEXT_140 = NL + "      if (ruleApps > 0) {" + NL + "        long nodeGen = ((LongWritable)" + NL + "          getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();" + NL + "        setAggregatedValue(AGGREGATOR_NODE_GENERATION," + NL + "          new LongWritable(nodeGen + 1));" + NL + "      }" + NL + "" + NL + "      // Update the application stack:" + NL + "      HenshinUtil.ApplicationStack stack;" + NL + "      if (getSuperstep() == 0) {" + NL + "        stack = new HenshinUtil.ApplicationStack();" + NL + "        stack = stack.append(";
  protected final String TEXT_141 = ", 0);";
  protected final String TEXT_142 = NL + "        stack = nextRuleStep(stack, ruleApps);";
  protected final String TEXT_143 = NL + "      } else {" + NL + "        stack = getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "        stack = nextRuleStep(stack, ruleApps);" + NL + "      }" + NL + "      setAggregatedValue(AGGREGATOR_APPLICATION_STACK, stack);" + NL + "" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Compute the next rule application stack." + NL + "     * @param stack Current application stack." + NL + "     * @param ruleApps Number of rule applications in last superstep." + NL + "     * @return the new application stack." + NL + "     */" + NL + "    private HenshinUtil.ApplicationStack nextRuleStep(" + NL + "      HenshinUtil.ApplicationStack stack, long ruleApps) {" + NL + "" + NL + "      // Iteratively update the application stack:" + NL + "      while (stack.getStackSize() > 0) {" + NL + "        int unit = stack.getLastUnit();" + NL + "        int microstep = stack.getLastMicrostep();" + NL + "        stack = stack.removeLast();" + NL + "        switch (unit) {";
  protected final String TEXT_144 = NL + "        case ";
  protected final String TEXT_145 = ":" + NL + "          stack = process";
  protected final String TEXT_146 = "(" + NL + "            stack, microstep, ruleApps);" + NL + "          break;";
  protected final String TEXT_147 = NL + "        default:" + NL + "          throw new RuntimeException(\"Unknown unit \" + unit);" + NL + "        }" + NL + "        // If the last unit is a rule, we can stop:" + NL + "        if (stack.getStackSize() > 0) {" + NL + "          unit = stack.getLastUnit();";
  protected final String TEXT_148 = NL + "          ";
  protected final String TEXT_149 = "unit == ";
  protected final String TEXT_150 = NL + "            break;" + NL + "          }" + NL + "        }" + NL + "      }" + NL + "      return stack;" + NL + "    }";
  protected final String TEXT_151 = NL + NL + "   /**" + NL + "     * Process ";
  protected final String TEXT_152 = " \"";
  protected final String TEXT_153 = "\"." + NL + "     * @param stack Current application stack." + NL + "     * @param microstep Current microstep." + NL + "     * @param ruleApps Number of rule applications in last superstep." + NL + "     * @return the new application stack." + NL + "     */" + NL + "    private HenshinUtil.ApplicationStack process";
  protected final String TEXT_154 = "(" + NL + "      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {" + NL + "" + NL + "      // Update the application stack:";
  protected final String TEXT_155 = NL + "      if (microstep > 0 && !unitSuccesses.pop()) {" + NL + "        unitSuccesses.push(false); // no success" + NL + "      } else if (microstep == ";
  protected final String TEXT_156 = ") {" + NL + "        unitSuccesses.push(true); // success" + NL + "      } else if (microstep < ";
  protected final String TEXT_157 = ") {" + NL + "        stack = stack.append(";
  protected final String TEXT_158 = ", microstep + 1);" + NL + "        stack = stack.append(";
  protected final String TEXT_159 = ", 0);" + NL + "      }";
  protected final String TEXT_160 = NL + "      if (microstep > 0 && !unitSuccesses.pop()) {" + NL + "        unitSuccesses.push(false); // no success" + NL + "      } else if (microstep == ";
  protected final String TEXT_161 = ") {" + NL + "        unitSuccesses.push(true); // success" + NL + "      } else {" + NL + "        switch (microstep) {";
  protected final String TEXT_162 = NL + "        case ";
  protected final String TEXT_163 = ":" + NL + "          stack = stack.append(";
  protected final String TEXT_164 = ", ";
  protected final String TEXT_165 = ");" + NL + "          stack = stack.append(";
  protected final String TEXT_166 = ", 0);" + NL + "          break;";
  protected final String TEXT_167 = NL + "        default:" + NL + "          break;" + NL + "        }" + NL + "      }";
  protected final String TEXT_168 = NL + "      if (microstep == 0) {" + NL + "        // Prepare a random execution order:" + NL + "        List<Integer> order = new ArrayList<Integer>();" + NL + "        for (int i = 0; i < ";
  protected final String TEXT_169 = "; i++) {" + NL + "          order.add(i);" + NL + "        }" + NL + "        Collections.shuffle(order);" + NL + "        independentUnitOrders.push(order);" + NL + "      }" + NL + "      if (microstep > 0 && unitSuccesses.pop()) {" + NL + "        independentUnitOrders.pop();" + NL + "        unitSuccesses.push(true); // success" + NL + "      } else if (microstep == ";
  protected final String TEXT_170 = ") {" + NL + "        independentUnitOrders.pop();" + NL + "        unitSuccesses.push(false); // no success" + NL + "      } else {" + NL + "        // Choose and execute the next subunit:" + NL + "        int next = independentUnitOrders.peek().get(microstep);" + NL + "        switch (next) {";
  protected final String TEXT_171 = NL + "        case ";
  protected final String TEXT_172 = ":" + NL + "          stack = stack.append(";
  protected final String TEXT_173 = ", microstep + 1);" + NL + "          stack = stack.append(";
  protected final String TEXT_174 = ", 0);" + NL + "          break;";
  protected final String TEXT_175 = NL + "        default:" + NL + "          break;" + NL + "        }" + NL + "      }";
  protected final String TEXT_176 = NL + "      if (microstep == 0 || unitSuccesses.pop()) {" + NL + "        stack = stack.append(";
  protected final String TEXT_177 = ", 1);" + NL + "        stack = stack.append(";
  protected final String TEXT_178 = ", 0);" + NL + "      } else {" + NL + "        unitSuccesses.push(true); // always successful" + NL + "      }";
  protected final String TEXT_179 = NL + "      if (microstep < ";
  protected final String TEXT_180 = ") {" + NL + "        stack = stack.append(";
  protected final String TEXT_181 = ", microstep + 1);" + NL + "      } else {" + NL + "        unitSuccesses.push(ruleApps > 0);" + NL + "      }";
  protected final String TEXT_182 = NL + "      return stack;" + NL + "    }";
  protected final String TEXT_183 = NL + NL + "    @Override" + NL + "    public void initialize() throws InstantiationException," + NL + "        IllegalAccessException {" + NL + "      registerAggregator(AGGREGATOR_RULE_APPLICATIONS," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_NODE_GENERATION," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_APPLICATION_STACK," + NL + "        HenshinUtil.ApplicationStackAggregator.class);" + NL + "    }" + NL + "" + NL + "  }" + NL + "}";
  protected final String TEXT_184 = NL;

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

List<Unit> allUnits = new ArrayList<Unit>();
allUnits.add(mainUnit);
allUnits.addAll(mainUnit.getSubUnits(true));

List<Rule> rules = new ArrayList<Rule>(ruleData.keySet());

boolean needsEdgeFactory = false;
int maxCreatedNodes = 0;
for (GiraphRuleData data : ruleData.values()) {
  if (!data.changeInfo.getCreatedEdges().isEmpty()) {
    needsEdgeFactory = true;
  }
  maxCreatedNodes = Math.max(maxCreatedNodes, data.changeInfo.getCreatedNodes().size());
}

boolean needsCollections = false;
for (Unit unit : allUnits) {
  if (unit instanceof IndependentUnit) {
    needsCollections = true;
    break;
  }
}


    stringBuffer.append(TEXT_1);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_2);
     if (needsCollections) { 
    stringBuffer.append(TEXT_3);
     } 
    stringBuffer.append(TEXT_4);
     if (needsEdgeFactory) { 
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_6);
    if (logging) { 
    stringBuffer.append(TEXT_7);
    } 
    stringBuffer.append(TEXT_8);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_11);
    

Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(mainUnit.getModule());
int value = 0;
for (ENamedElement type : typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_12);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( typeConstants.get(type) );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_15);
    
}

Map<Unit,String> unitConstants = GiraphUtil.getUnitConstants(mainUnit);
value = 0;
for (Unit unit : unitConstants.keySet()) {
  
    stringBuffer.append(TEXT_16);
    stringBuffer.append( (unit instanceof Rule) ? "Rule" : "Unit" );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_20);
    
}

if (logging) {

    stringBuffer.append(TEXT_21);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_22);
     } 
    stringBuffer.append(TEXT_23);
     for (Rule rule : rules) { 
    stringBuffer.append(TEXT_24);
    stringBuffer.append( unitConstants.get(rule) );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_26);
     } 
    stringBuffer.append(TEXT_27);
    

// Generate the code for all rules: 
for (Rule rule : ruleData.keySet()) {
  GiraphRuleData data = ruleData.get(rule);
  RuleChangeInfo changeInfo = new RuleChangeInfo(rule);


    stringBuffer.append(TEXT_28);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_31);
    if (logging) { 
    stringBuffer.append(TEXT_32);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_33);
    } 
    stringBuffer.append(TEXT_34);
     
    for (int i=0; i<data.matchingSteps.size(); i++) {
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_36);
        if (step.verifyEdgeTo != null) {
    stringBuffer.append(TEXT_37);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.verifyEdgeTo) );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(TEXT_42);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_43);
            if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_44);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_45);
            } 
    stringBuffer.append(TEXT_46);
        } else {
    stringBuffer.append(TEXT_47);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(TEXT_50);
    stringBuffer.append( typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_51);
        if (step.isStart) {
    stringBuffer.append(TEXT_52);
     } else {
    stringBuffer.append(TEXT_53);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_54);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_55);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_56);
    stringBuffer.append(TEXT_57);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_58);
    stringBuffer.append(TEXT_59);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_60);
    stringBuffer.append(TEXT_61);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_62);
    stringBuffer.append(TEXT_63);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_64);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_65);
    if (logging) { 
    stringBuffer.append(TEXT_66);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_67);
    stringBuffer.append(TEXT_68);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_69);
    stringBuffer.append(TEXT_70);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_71);
    } 
    stringBuffer.append(TEXT_72);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_73);
    stringBuffer.append(TEXT_74);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_75);
    stringBuffer.append(TEXT_76);
    stringBuffer.append( !step.isStart ? "  " : "");
    stringBuffer.append(TEXT_77);
    
      } else if (step.sendBackTo != null) {
    stringBuffer.append(TEXT_78);
    stringBuffer.append( GiraphUtil.getNodeName(step.sendBackTo) );
    stringBuffer.append(TEXT_79);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_80);
    if (logging) { 
    stringBuffer.append(TEXT_81);
    } 
    stringBuffer.append(TEXT_82);
    
      } else if (i == data.matchingSteps.size()-1) {
    stringBuffer.append(TEXT_83);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_84);
        }
      if (!step.isStart) {
    stringBuffer.append(TEXT_85);
    
       }
    stringBuffer.append(TEXT_86);
    
      }
    stringBuffer.append(TEXT_87);
     

    } // end for


    stringBuffer.append(TEXT_88);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_89);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_90);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_91);
    if (logging) { 
    stringBuffer.append(TEXT_92);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_93);
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
    stringBuffer.append(TEXT_94);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_95);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_96);
      }
  }

  for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_97);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_98);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_99);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_100);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_101);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_102);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_103);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_104);
      }

    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_105);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_106);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_107);
     if (useUUIDs) { 
    stringBuffer.append(TEXT_108);
     } else { 
    stringBuffer.append(TEXT_109);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_110);
     } 
    stringBuffer.append(TEXT_111);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_112);
    stringBuffer.append( typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_113);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_114);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_115);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_116);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_117);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_118);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_119);
    	} else { 
    stringBuffer.append(TEXT_120);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_121);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_122);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_123);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_124);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_125);
    	} else { 
    stringBuffer.append(TEXT_126);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_127);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_128);
    	} 
    stringBuffer.append(TEXT_129);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_130);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_131);
    stringBuffer.append( typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_132);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_133);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_134);
      e++;
    } 
    stringBuffer.append(TEXT_135);
    
} // end of for all rules

    stringBuffer.append(TEXT_136);
     if (!useUUIDs) { 
    stringBuffer.append(TEXT_137);
     } 
    stringBuffer.append(TEXT_138);
     if (logging) {
    stringBuffer.append(TEXT_139);
     } 
    stringBuffer.append(TEXT_140);
    stringBuffer.append( unitConstants.get(mainUnit) );
    stringBuffer.append(TEXT_141);
     if (!(mainUnit instanceof Rule)) { 
    stringBuffer.append(TEXT_142);
     } 
    stringBuffer.append(TEXT_143);
     for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_144);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_145);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_146);
     } // end for 
    stringBuffer.append(TEXT_147);
     for (int i=0; i<rules.size(); i++) { 
    stringBuffer.append(TEXT_148);
    stringBuffer.append( i==0 ? "if (" : "  " );
    stringBuffer.append(TEXT_149);
    stringBuffer.append( unitConstants.get(rules.get(i)) + (i<rules.size()-1 ? " ||" : ") {" ) );
     } 
    stringBuffer.append(TEXT_150);
    

for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_151);
    stringBuffer.append( unit.eClass().getName() );
    stringBuffer.append(TEXT_152);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_153);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_154);
     if (unit instanceof IteratedUnit) {
   int iters = Integer.parseInt(((IteratedUnit) unit).getIterations()); 
    stringBuffer.append(TEXT_155);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_156);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_157);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_158);
    stringBuffer.append( unitConstants.get(((IteratedUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_159);
     } else if (unit instanceof SequentialUnit) {
     SequentialUnit seq = (SequentialUnit) unit; 
    stringBuffer.append(TEXT_160);
    stringBuffer.append( seq.getSubUnits().size() );
    stringBuffer.append(TEXT_161);
     for (int i=0; i<seq.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_162);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_163);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_164);
    stringBuffer.append( i+1 );
    stringBuffer.append(TEXT_165);
    stringBuffer.append( unitConstants.get(seq.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_166);
     } 
    stringBuffer.append(TEXT_167);
     } else if (unit instanceof IndependentUnit) { 
     IndependentUnit indi = (IndependentUnit) unit; 
    stringBuffer.append(TEXT_168);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_169);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_170);
     for (int i=0; i<indi.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_171);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_172);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_173);
    stringBuffer.append( unitConstants.get(indi.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_174);
     } 
    stringBuffer.append(TEXT_175);
     } else if (unit instanceof LoopUnit) { 
    stringBuffer.append(TEXT_176);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_177);
    stringBuffer.append( unitConstants.get(((LoopUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_178);
     } else if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_179);
    stringBuffer.append( ruleData.get(unit).matchingSteps.size()-1 );
    stringBuffer.append(TEXT_180);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_181);
     } 
    stringBuffer.append(TEXT_182);
     } // end for 
    stringBuffer.append(TEXT_183);
    stringBuffer.append(TEXT_184);
    return stringBuffer.toString();
  }
}
