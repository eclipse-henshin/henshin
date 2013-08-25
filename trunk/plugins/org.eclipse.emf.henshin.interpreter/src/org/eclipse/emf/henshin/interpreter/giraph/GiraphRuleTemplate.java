package org.eclipse.emf.henshin.interpreter.giraph;

import java.util.*;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.model.staticanalysis.*;
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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.ArrayDeque;" + NL + "import java.util.ArrayList;";
  protected final String TEXT_3 = NL + "import java.util.Collections;";
  protected final String TEXT_4 = NL + "import java.util.Deque;" + NL + "import java.util.HashSet;" + NL + "import java.util.List;" + NL + "import java.util.Set;" + NL + "" + NL + "import org.apache.giraph.aggregators.LongSumAggregator;";
  protected final String TEXT_5 = NL + "import org.apache.giraph.edge.Edge;";
  protected final String TEXT_6 = NL + "import org.apache.giraph.edge.EdgeFactory;";
  protected final String TEXT_7 = NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.master.DefaultMasterCompute;" + NL + "import org.apache.hadoop.io.ByteWritable;" + NL + "import org.apache.hadoop.io.LongWritable;";
  protected final String TEXT_8 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_9 = NL + "import static ";
  protected final String TEXT_10 = ".HenshinUtil" + NL + "  .ApplicationStack;" + NL + "import static ";
  protected final String TEXT_11 = ".HenshinUtil" + NL + "  .ApplicationStackAggregator;" + NL + "import static ";
  protected final String TEXT_12 = ".HenshinUtil" + NL + "  .Match;" + NL + "import static ";
  protected final String TEXT_13 = ".HenshinUtil" + NL + "  .VertexId;" + NL + "" + NL + "/**" + NL + " * Generated implementation of the Henshin unit \"";
  protected final String TEXT_14 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_15 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_16 = " extends" + NL + "  BasicComputation<VertexId, ByteWritable, ByteWritable, Match> {" + NL + "" + NL + "  /**" + NL + "   * Name of the match count aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_MATCHES = \"matches\";" + NL + "" + NL + "  /**" + NL + "   * Name of the rule application count aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_RULE_APPLICATIONS = \"ruleApps\";" + NL + "" + NL + "  /**" + NL + "   * Name of the node generation aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_NODE_GENERATION = \"nodeGen\";" + NL + "" + NL + "  /**" + NL + "   * Name of the application stack aggregator." + NL + "   */" + NL + "  public static final String AGGREGATOR_APPLICATION_STACK = \"appStack\";";
  protected final String TEXT_17 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_18 = "\"." + NL + "   */" + NL + "  public static final byte ";
  protected final String TEXT_19 = " = ";
  protected final String TEXT_20 = ";";
  protected final String TEXT_21 = NL + NL + "  /**" + NL + "   * ";
  protected final String TEXT_22 = " constant for \"";
  protected final String TEXT_23 = "\"." + NL + "   */" + NL + "  public static final int ";
  protected final String TEXT_24 = " = ";
  protected final String TEXT_25 = ";";
  protected final String TEXT_26 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  protected static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_27 = ".class);";
  protected final String TEXT_28 = NL + NL + "  /**" + NL + "   * Default segment count." + NL + "   */" + NL + "  private static int SEGMENT_COUNT = ";
  protected final String TEXT_29 = ";" + NL + "" + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<VertexId, ByteWritable, ByteWritable> vertex," + NL + "      Iterable<Match> matches) throws IOException {" + NL + "    ApplicationStack stack =" + NL + "      getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "    if (stack.getStackSize() == 0) {" + NL + "      long ruleApps = ((LongWritable)" + NL + "        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();" + NL + "      if (ruleApps == 0) {" + NL + "        vertex.voteToHalt();" + NL + "      }" + NL + "      return;" + NL + "    }" + NL + "    int rule = stack.getLastUnit();" + NL + "    int segment = stack.getLastSegment();" + NL + "    int microstep = stack.getLastMicrostep();" + NL + "    switch (rule) {";
  protected final String TEXT_30 = NL + "    case ";
  protected final String TEXT_31 = ":" + NL + "      match";
  protected final String TEXT_32 = "(" + NL + "        vertex, matches, segment, microstep);" + NL + "      break;";
  protected final String TEXT_33 = NL + "    default:" + NL + "      throw new RuntimeException(\"Unknown rule: \" + rule);" + NL + "    }" + NL + "  }";
  protected final String TEXT_34 = NL + NL + "  /**" + NL + "   * Match (and apply) the rule \"";
  protected final String TEXT_35 = "\"." + NL + "   * This takes ";
  protected final String TEXT_36 = " microsteps." + NL + "   * @param vertex The current vertex." + NL + "   * @param matches The current matches." + NL + "   * @param segment The current segment." + NL + "   * @param microstep The current microstep." + NL + "   */" + NL + "  protected void match";
  protected final String TEXT_37 = "(" + NL + "    Vertex<VertexId, ByteWritable, ByteWritable> vertex," + NL + "    Iterable<Match> matches, int segment, int microstep)" + NL + "    throws IOException {" + NL;
  protected final String TEXT_38 = NL + "    LOG.info(\"Vertex \" + vertex.getId() + \" in superstep \" + getSuperstep() +" + NL + "      \" matching rule ";
  protected final String TEXT_39 = " on segment \" + segment +" + NL + "      \" in microstep \" + microstep);" + NL + "    for (Match match : matches) {" + NL + "      LOG.info(\"Vertex \" + vertex.getId() +" + NL + "        \" in superstep \" + getSuperstep() +" + NL + "        \" received (partial) match \" + match);" + NL + "    }";
  protected final String TEXT_40 = NL + "    Set<Match> appliedMatches = new HashSet<Match>();";
  protected final String TEXT_41 = NL + "    ";
  protected final String TEXT_42 = "filter";
  protected final String TEXT_43 = "(" + NL + "      vertex, matches, segment, microstep, appliedMatches);" + NL + "    long matchCount = 0;" + NL;
  protected final String TEXT_44 = " if (microstep == ";
  protected final String TEXT_45 = ") {";
  protected final String TEXT_46 = NL + "      // Joining matches at node ";
  protected final String TEXT_47 = ":" + NL + "      List<Match> matches1 = new ArrayList<Match>();" + NL + "      List<Match> matches2 = new ArrayList<Match>();" + NL + "      VertexId id = vertex.getId();" + NL + "      for (Match match : matches) {" + NL + "        if (id.equals(match.getVertexId(";
  protected final String TEXT_48 = "))) {" + NL + "          matches1.add(match.copy());" + NL + "        } else {" + NL + "          matches2.add(match.copy());" + NL + "        }" + NL + "      }";
  protected final String TEXT_49 = NL + "      LOG.info(\"Vertex \" + id + \" in superstep \" + getSuperstep() +" + NL + "        \" joining \" + matches1.size() + \" x \" + matches2.size() +" + NL + "        \" partial matches of rule ";
  protected final String TEXT_50 = "\");";
  protected final String TEXT_51 = NL + "      for (Match m1 : matches1) {" + NL + "        for (Match m2 : matches2) {" + NL + "          Match match = m1.append(m2);";
  protected final String TEXT_52 = NL + "          if (!match.isInjective()) {" + NL + "            continue;" + NL + "          }" + NL + "          matchCount++;";
  protected final String TEXT_53 = NL + "          // Send the message back to match of node ";
  protected final String TEXT_54 = ":";
  protected final String TEXT_55 = NL + "          LOG.info(\"Vertex \" + vertex.getId() +" + NL + "            \" sending (partial) match \" + match +" + NL + "            \" back to vertex \" + match.getVertexId(";
  protected final String TEXT_56 = "));";
  protected final String TEXT_57 = NL + "          sendMessage(match.getVertexId(";
  protected final String TEXT_58 = "), match);";
  protected final String TEXT_59 = NL + "          if (segment == SEGMENT_COUNT - 1) {" + NL + "            apply";
  protected final String TEXT_60 = "(" + NL + "              vertex, match, appliedMatches);" + NL + "          } else {" + NL + "            sendMessage(vertex.getId(), match);" + NL + "          }";
  protected final String TEXT_61 = NL + "        }" + NL + "      }";
  protected final String TEXT_62 = NL + "      // Matching node ";
  protected final String TEXT_63 = ":";
  protected final String TEXT_64 = NL + "      ";
  protected final String TEXT_65 = "vertex.getValue().get() == ";
  protected final String TEXT_66 = NL + "      ok = ok && vertex.getNumEdges() >= ";
  protected final String TEXT_67 = ";";
  protected final String TEXT_68 = NL + "      ok = ok && (SEGMENT_COUNT == 1 || getSegment(vertex.getId()) == segment);";
  protected final String TEXT_69 = NL + "      if (ok) {";
  protected final String TEXT_70 = NL + "        Match match = new Match(segment).append(vertex.getId());" + NL + "        matchCount++;";
  protected final String TEXT_71 = NL;
  protected final String TEXT_72 = "      for (Match match : matches) {";
  protected final String TEXT_73 = NL;
  protected final String TEXT_74 = "        match = match.append(vertex.getId());";
  protected final String TEXT_75 = NL;
  protected final String TEXT_76 = "        if (!match.isInjective()) {";
  protected final String TEXT_77 = NL;
  protected final String TEXT_78 = "          continue;";
  protected final String TEXT_79 = NL;
  protected final String TEXT_80 = "        }";
  protected final String TEXT_81 = NL;
  protected final String TEXT_82 = "        if (vertex.getId().compareTo(match.getVertexId(";
  protected final String TEXT_83 = ")) < 0) {";
  protected final String TEXT_84 = NL;
  protected final String TEXT_85 = "          continue;";
  protected final String TEXT_86 = NL;
  protected final String TEXT_87 = "        }";
  protected final String TEXT_88 = NL;
  protected final String TEXT_89 = "        matchCount++;";
  protected final String TEXT_90 = NL;
  protected final String TEXT_91 = "    // Node ";
  protected final String TEXT_92 = ": check for edge to match of ";
  protected final String TEXT_93 = " of type \"";
  protected final String TEXT_94 = "\":";
  protected final String TEXT_95 = NL;
  protected final String TEXT_96 = "    VertexId targetId = match.getVertexId(";
  protected final String TEXT_97 = ");";
  protected final String TEXT_98 = NL;
  protected final String TEXT_99 = "    for (Edge<VertexId, ByteWritable> edge :";
  protected final String TEXT_100 = NL;
  protected final String TEXT_101 = "      vertex.getEdges()) {";
  protected final String TEXT_102 = NL;
  protected final String TEXT_103 = "      if (edge.getValue().get() ==";
  protected final String TEXT_104 = NL;
  protected final String TEXT_105 = "        ";
  protected final String TEXT_106 = " &&";
  protected final String TEXT_107 = NL;
  protected final String TEXT_108 = "        edge.getTargetVertexId().equals(targetId)) {";
  protected final String TEXT_109 = NL;
  protected final String TEXT_110 = "        matchCount++;";
  protected final String TEXT_111 = NL;
  protected final String TEXT_112 = "        // Send the message back to matches of node ";
  protected final String TEXT_113 = ":";
  protected final String TEXT_114 = NL;
  protected final String TEXT_115 = "        LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_116 = NL;
  protected final String TEXT_117 = "          \" sending (partial) match \" + match +";
  protected final String TEXT_118 = NL;
  protected final String TEXT_119 = "          \" back to vertex \" + match.getVertexId(";
  protected final String TEXT_120 = "));";
  protected final String TEXT_121 = NL;
  protected final String TEXT_122 = "        sendMessage(match.getVertexId(";
  protected final String TEXT_123 = "), match);";
  protected final String TEXT_124 = NL;
  protected final String TEXT_125 = "        if (segment == SEGMENT_COUNT - 1) {";
  protected final String TEXT_126 = NL;
  protected final String TEXT_127 = "          apply";
  protected final String TEXT_128 = "(";
  protected final String TEXT_129 = NL;
  protected final String TEXT_130 = "            vertex, match, appliedMatches);";
  protected final String TEXT_131 = NL;
  protected final String TEXT_132 = "        } else {";
  protected final String TEXT_133 = NL;
  protected final String TEXT_134 = "          sendMessage(vertex.getId(), match);";
  protected final String TEXT_135 = NL;
  protected final String TEXT_136 = "        }";
  protected final String TEXT_137 = NL;
  protected final String TEXT_138 = "      }";
  protected final String TEXT_139 = NL;
  protected final String TEXT_140 = "    }";
  protected final String TEXT_141 = NL;
  protected final String TEXT_142 = "        // Send the match along all \"";
  protected final String TEXT_143 = "\"-edges:";
  protected final String TEXT_144 = NL;
  protected final String TEXT_145 = "        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {";
  protected final String TEXT_146 = NL;
  protected final String TEXT_147 = "          if (edge.getValue().get() ==";
  protected final String TEXT_148 = NL;
  protected final String TEXT_149 = "            ";
  protected final String TEXT_150 = ") {";
  protected final String TEXT_151 = NL;
  protected final String TEXT_152 = "            LOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_153 = NL;
  protected final String TEXT_154 = "              \" sending (partial) match \" + match +";
  protected final String TEXT_155 = NL;
  protected final String TEXT_156 = "              \" forward to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_157 = NL;
  protected final String TEXT_158 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_159 = NL;
  protected final String TEXT_160 = "          }";
  protected final String TEXT_161 = NL;
  protected final String TEXT_162 = "        }";
  protected final String TEXT_163 = NL;
  protected final String TEXT_164 = "      }";
  protected final String TEXT_165 = NL + "      }";
  protected final String TEXT_166 = NL + "      // Keep matches received at node ";
  protected final String TEXT_167 = ":" + NL + "      for (Match match : matches) {" + NL + "        VertexId id = match.getVertexId(";
  protected final String TEXT_168 = ");" + NL + "        if (vertex.getId().equals(id)) {" + NL + "          matchCount++;";
  protected final String TEXT_169 = NL + "          LOG.info(\"Vertex \" + id + \" in superstep \" + getSuperstep() +" + NL + "            \" sending (partial) match \" + match + \" to myself\");";
  protected final String TEXT_170 = NL + "          sendMessage(id, match);" + NL + "        }" + NL + "      }";
  protected final String TEXT_171 = NL + "    }";
  protected final String TEXT_172 = " else {" + NL + "      throw new RuntimeException(\"Illegal microstep for rule \" +" + NL + "        \"";
  protected final String TEXT_173 = ": \" + microstep);" + NL + "    }" + NL + "    if (matchCount > 0) {" + NL + "      aggregate(AGGREGATOR_MATCHES," + NL + "        new LongWritable(matchCount));" + NL + "    }" + NL + "    if (!appliedMatches.isEmpty()) {" + NL + "      aggregate(AGGREGATOR_RULE_APPLICATIONS," + NL + "        new LongWritable(appliedMatches.size()));" + NL + "    }" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Filter matches per segment for the rule \"";
  protected final String TEXT_174 = "\"." + NL + "   * @param vertex The current vertex." + NL + "   * @param matches The current matches." + NL + "   * @param segment The current segment." + NL + "   * @param microstep The current microstep." + NL + "   * @param appliedMatches Set of applied matches." + NL + "   * @return The filtered matches." + NL + "   */" + NL + "  protected Iterable<Match> filter";
  protected final String TEXT_175 = "(" + NL + "    Vertex<VertexId, ByteWritable, ByteWritable> vertex," + NL + "    Iterable<Match> matches, int segment, int microstep," + NL + "    Set<Match> appliedMatches)" + NL + "    throws IOException {" + NL + "    if (segment > 0) {" + NL + "      List<Match> filtered = new ArrayList<Match>();" + NL + "      long matchCount = 0;" + NL + "      for (Match match : matches) {" + NL + "        int matchSegment = match.getSegment();" + NL + "        if (matchSegment < segment) {" + NL + "          if (match.getMatchSize() != ";
  protected final String TEXT_176 = ") {" + NL + "            throw new RuntimeException(\"Incomplete match \" + match +" + NL + "              \" of rule ";
  protected final String TEXT_177 = " received in segment \" +" + NL + "              segment);" + NL + "          }" + NL + "          matchCount++;" + NL + "          if (segment == SEGMENT_COUNT - 1 && microstep == ";
  protected final String TEXT_178 = ") {" + NL + "            apply";
  protected final String TEXT_179 = "(" + NL + "              vertex, match, appliedMatches);" + NL + "          } else {" + NL + "            sendMessage(vertex.getId(), match);" + NL + "          }" + NL + "        } else if (matchSegment > segment) {" + NL + "          throw new RuntimeException(\"Received match \" + match +" + NL + "            \" of rule ";
  protected final String TEXT_180 = " of segment \" +" + NL + "            matchSegment + \", but current segment is only \" + segment);" + NL + "        } else {" + NL + "          filtered.add(match.copy());" + NL + "        }" + NL + "      }" + NL + "      if (matchCount > 0) {" + NL + "        aggregate(AGGREGATOR_MATCHES," + NL + "          new LongWritable(matchCount));" + NL + "      }" + NL + "      return filtered;" + NL + "    }" + NL + "    return matches;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule \"";
  protected final String TEXT_181 = "\" to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @param appliedMatches Already applied matches." + NL + "   * @return true if the rule was applied." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  protected boolean apply";
  protected final String TEXT_182 = "(" + NL + "    Vertex<VertexId, ByteWritable, ByteWritable> vertex," + NL + "    Match match, Set<Match> appliedMatches) throws IOException {";
  protected final String TEXT_183 = NL + "    VertexId cur";
  protected final String TEXT_184 = " = match.getVertexId(";
  protected final String TEXT_185 = ");";
  protected final String TEXT_186 = NL + "    match = match.remove(";
  protected final String TEXT_187 = ");";
  protected final String TEXT_188 = NL + "    if (!appliedMatches.add(match)) {" + NL + "      return false;" + NL + "    }";
  protected final String TEXT_189 = NL + "    LOG.info(\"Vertex \" + vertex.getId() +" + NL + "      \" applying rule ";
  protected final String TEXT_190 = " with match \" + match);";
  protected final String TEXT_191 = NL + "    removeEdgesRequest(cur";
  protected final String TEXT_192 = ", cur";
  protected final String TEXT_193 = ");";
  protected final String TEXT_194 = NL + "    removeVertexRequest(cur";
  protected final String TEXT_195 = ");";
  protected final String TEXT_196 = NL + "    VertexId new";
  protected final String TEXT_197 = " =";
  protected final String TEXT_198 = NL + "      VertexId.randomVertexId();";
  protected final String TEXT_199 = NL + "      deriveVertexId(vertex.getId(), appliedMatches.size(), ";
  protected final String TEXT_200 = ");";
  protected final String TEXT_201 = NL + "    addVertexRequest(new";
  protected final String TEXT_202 = "," + NL + "      new ByteWritable(";
  protected final String TEXT_203 = "));";
  protected final String TEXT_204 = NL + "    VertexId src";
  protected final String TEXT_205 = " = new";
  protected final String TEXT_206 = ";";
  protected final String TEXT_207 = NL + "    VertexId src";
  protected final String TEXT_208 = " = cur";
  protected final String TEXT_209 = ";";
  protected final String TEXT_210 = NL + "    VertexId trg";
  protected final String TEXT_211 = " = new";
  protected final String TEXT_212 = ";";
  protected final String TEXT_213 = NL + "    VertexId trg";
  protected final String TEXT_214 = " = cur";
  protected final String TEXT_215 = ";";
  protected final String TEXT_216 = NL + "    Edge<VertexId, ByteWritable> edge";
  protected final String TEXT_217 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_218 = "," + NL + "        new ByteWritable(";
  protected final String TEXT_219 = "));" + NL + "    addEdgeRequest(src";
  protected final String TEXT_220 = ", edge";
  protected final String TEXT_221 = ");";
  protected final String TEXT_222 = NL + "    return true;" + NL + "  }";
  protected final String TEXT_223 = NL;
  protected final String TEXT_224 = NL + "  /**" + NL + "   * Derive a new vertex Id from an exiting one." + NL + "   * @param baseId The base vertex Id." + NL + "   * @param matchIndex The index of the match." + NL + "   * @param vertexIndex The index of the new vertex." + NL + "   * @return The derived vertex Id." + NL + "   */" + NL + "  private VertexId deriveVertexId(VertexId baseId, int matchIndex," + NL + "    int vertexIndex) {" + NL + "    long generation = ((LongWritable) getAggregatedValue(" + NL + "        AGGREGATOR_NODE_GENERATION)).get();" + NL + "    return baseId" + NL + "      .append((byte) generation)" + NL + "      .append((byte) matchIndex)" + NL + "      .append((byte) vertexIndex);" + NL + "  }" + NL;
  protected final String TEXT_225 = NL + "  /**" + NL + "   * Get the segment that a vertex belongs to." + NL + "   * @param vertexId The ID of the vertex." + NL + "   * @return The segment of the vertex." + NL + "   */" + NL + "  private int getSegment(VertexId vertexId) {" + NL + "    return vertexId.hashCode() % SEGMENT_COUNT;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Master compute which registers and updates the required aggregators." + NL + "   */" + NL + "  public static class MasterCompute extends DefaultMasterCompute {" + NL + "" + NL + "    /**" + NL + "     * Stack for storing unit success flags." + NL + "     */" + NL + "    private final Deque<Boolean> unitSuccesses =" + NL + "      new ArrayDeque<Boolean>();" + NL + "" + NL + "    /**" + NL + "     * Stack for storing the execution orders of independent units." + NL + "     */" + NL + "    private final Deque<List<Integer>> unitOrders =" + NL + "      new ArrayDeque<List<Integer>>();" + NL + "" + NL + "    /*" + NL + "     * (non-Javadoc)" + NL + "     * @see org.apache.giraph.master.DefaultMasterCompute#compute()" + NL + "     */" + NL + "    @Override" + NL + "    public void compute() {" + NL + "      long ruleApps = ((LongWritable)" + NL + "        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();";
  protected final String TEXT_226 = NL + "      long matches = ((LongWritable)" + NL + "        getAggregatedValue(AGGREGATOR_MATCHES)).get();" + NL + "      if (getSuperstep() > 0) {" + NL + "        LOG.info(matches + \" (partial) matches computed and \" +" + NL + "          ruleApps + \" rule applications conducted in superstep \" +" + NL + "          (getSuperstep() - 1));" + NL + "      }";
  protected final String TEXT_227 = NL + "      if (ruleApps > 0) {" + NL + "        long nodeGen = ((LongWritable)" + NL + "          getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();" + NL + "        setAggregatedValue(AGGREGATOR_NODE_GENERATION," + NL + "          new LongWritable(nodeGen + 1));" + NL + "      }" + NL + "      ApplicationStack stack;" + NL + "      if (getSuperstep() == 0) {" + NL + "        stack = new ApplicationStack();" + NL + "        stack = stack.append(";
  protected final String TEXT_228 = ", 0, 0);";
  protected final String TEXT_229 = NL + "        stack = nextRuleStep(stack, ruleApps);";
  protected final String TEXT_230 = NL + "      } else {" + NL + "        stack = getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "        stack = nextRuleStep(stack, ruleApps);" + NL + "      }" + NL + "      setAggregatedValue(AGGREGATOR_APPLICATION_STACK, stack);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Compute the next rule application stack." + NL + "     * @param stack The current application stack." + NL + "     * @param ruleApps Number of rule applications in last superstep." + NL + "     * @return The new application stack." + NL + "     */" + NL + "    private ApplicationStack nextRuleStep(" + NL + "      ApplicationStack stack, long ruleApps) {" + NL + "      while (stack.getStackSize() > 0) {" + NL + "        int unit = stack.getLastUnit();" + NL + "        int segment = stack.getLastSegment();" + NL + "        int microstep = stack.getLastMicrostep();" + NL + "        stack = stack.removeLast();" + NL + "        switch (unit) {";
  protected final String TEXT_231 = NL + "        case ";
  protected final String TEXT_232 = ":" + NL + "          stack = process";
  protected final String TEXT_233 = "(" + NL + "            stack";
  protected final String TEXT_234 = ", microstep";
  protected final String TEXT_235 = ");" + NL + "          break;";
  protected final String TEXT_236 = NL + "        default:" + NL + "          throw new RuntimeException(\"Unknown unit \" + unit);" + NL + "        }" + NL + "        if (stack.getStackSize() > 0) {" + NL + "          unit = stack.getLastUnit();";
  protected final String TEXT_237 = NL + "          ";
  protected final String TEXT_238 = "unit == ";
  protected final String TEXT_239 = NL + "            break;" + NL + "          }" + NL + "        }" + NL + "      }" + NL + "      return stack;" + NL + "    }";
  protected final String TEXT_240 = NL + NL + "   /**" + NL + "     * Process ";
  protected final String TEXT_241 = " \"";
  protected final String TEXT_242 = "\"." + NL + "     * @param stack The current application stack.";
  protected final String TEXT_243 = NL + "     * @param segment The current segment.";
  protected final String TEXT_244 = NL + "     * @param microstep The current microstep.";
  protected final String TEXT_245 = NL + "     * @param ruleApps Number of rule applications in last superstep.";
  protected final String TEXT_246 = NL + "     * @return The new application stack." + NL + "     */" + NL + "    private ApplicationStack process";
  protected final String TEXT_247 = "(" + NL + "      ApplicationStack stack";
  protected final String TEXT_248 = ", int microstep";
  protected final String TEXT_249 = ") {";
  protected final String TEXT_250 = NL + "      if (microstep > 0 && !unitSuccesses.pop()) {" + NL + "        unitSuccesses.push(false);" + NL + "      } else if (microstep == ";
  protected final String TEXT_251 = ") {" + NL + "        unitSuccesses.push(true);" + NL + "      } else if (microstep < ";
  protected final String TEXT_252 = ") {" + NL + "        stack = stack.append(";
  protected final String TEXT_253 = ", 0, microstep + 1);" + NL + "        stack = stack.append(";
  protected final String TEXT_254 = ", 0, 0);" + NL + "      }";
  protected final String TEXT_255 = NL + "      if (microstep > 0 && !unitSuccesses.pop()) {" + NL + "        unitSuccesses.push(false);" + NL + "      } else if (microstep == ";
  protected final String TEXT_256 = ") {" + NL + "        unitSuccesses.push(true);" + NL + "      } else {" + NL + "        switch (microstep) {";
  protected final String TEXT_257 = NL + "        case ";
  protected final String TEXT_258 = ":" + NL + "          stack = stack.append(";
  protected final String TEXT_259 = ", 0, ";
  protected final String TEXT_260 = ");" + NL + "          stack = stack.append(";
  protected final String TEXT_261 = ", 0, 0);" + NL + "          break;";
  protected final String TEXT_262 = NL + "        default:" + NL + "          break;" + NL + "        }" + NL + "      }";
  protected final String TEXT_263 = NL + "      if (microstep == 0) {" + NL + "        List<Integer> order = new ArrayList<Integer>();" + NL + "        for (int i = 0; i < ";
  protected final String TEXT_264 = "; i++) {" + NL + "          order.add(i);" + NL + "        }" + NL + "        Collections.shuffle(order);" + NL + "        unitOrders.push(order);" + NL + "      }" + NL + "      if (microstep > 0 && unitSuccesses.pop()) {" + NL + "        unitOrders.pop();" + NL + "        unitSuccesses.push(true);" + NL + "      } else if (microstep == ";
  protected final String TEXT_265 = ") {" + NL + "        unitOrders.pop();" + NL + "        unitSuccesses.push(false);" + NL + "      } else {" + NL + "        int next = unitOrders.peek().get(microstep);" + NL + "        switch (next) {";
  protected final String TEXT_266 = NL + "        case ";
  protected final String TEXT_267 = ":" + NL + "          stack = stack.append(";
  protected final String TEXT_268 = ", 0, microstep + 1);" + NL + "          stack = stack.append(";
  protected final String TEXT_269 = ", 0, 0);" + NL + "          break;";
  protected final String TEXT_270 = NL + "        default:" + NL + "          break;" + NL + "        }" + NL + "      }";
  protected final String TEXT_271 = NL + "      if (microstep == 0 || unitSuccesses.pop()) {" + NL + "        stack = stack.append(";
  protected final String TEXT_272 = ", 0, 1);" + NL + "        stack = stack.append(";
  protected final String TEXT_273 = ", 0, 0);" + NL + "      } else {" + NL + "        unitSuccesses.push(true);" + NL + "      }";
  protected final String TEXT_274 = NL + "      if (microstep < ";
  protected final String TEXT_275 = ") {" + NL + "        stack = stack.append(";
  protected final String TEXT_276 = ", segment, microstep + 1);" + NL + "      } else if (segment < SEGMENT_COUNT - 1) {" + NL + "        stack = stack.append(";
  protected final String TEXT_277 = ", segment + 1, 0);" + NL + "      } else {" + NL + "        unitSuccesses.push(ruleApps > 0);" + NL + "      }";
  protected final String TEXT_278 = NL + "      return stack;" + NL + "    }";
  protected final String TEXT_279 = NL + NL + "    /*" + NL + "     * (non-Javadoc)" + NL + "     * @see org.apache.giraph.master.DefaultMasterCompute#initialize()" + NL + "     */" + NL + "    @Override" + NL + "    public void initialize() throws InstantiationException," + NL + "        IllegalAccessException {" + NL + "      registerAggregator(AGGREGATOR_MATCHES," + NL + "        LongSumAggregator.class);" + NL + "      registerAggregator(AGGREGATOR_RULE_APPLICATIONS," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_NODE_GENERATION," + NL + "        LongSumAggregator.class);" + NL + "      registerPersistentAggregator(AGGREGATOR_APPLICATION_STACK," + NL + "        ApplicationStackAggregator.class);" + NL + "    }" + NL + "" + NL + "  }" + NL + "}";
  protected final String TEXT_280 = NL;

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
boolean masterLogging = (Boolean) args.get("masterLogging");
boolean vertexLogging = (Boolean) args.get("vertexLogging");
boolean useUUIDs = (Boolean) args.get("useUUIDs");
int segmentCount = (Integer) args.get("segmentCount");

List<Unit> allUnits = new ArrayList<Unit>();
allUnits.add(mainUnit);
allUnits.addAll(mainUnit.getSubUnits(true));

List<Rule> rules = new ArrayList<Rule>(ruleData.keySet());

boolean needsEdgeFactory = false;
boolean needsVertexIdFactory = false;
int maxCreatedNodes = 0;
for (GiraphRuleData data : ruleData.values()) {
  if (!data.changeInfo.getCreatedEdges().isEmpty()) {
    needsEdgeFactory = true;
  }
  if (!data.changeInfo.getCreatedNodes().isEmpty()) {
    needsVertexIdFactory = true;
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

boolean needsEdgeClass = false;
for (Rule rule : rules) {
  if (!rule.getLhs().getEdges().isEmpty() || !rule.getRhs().getEdges().isEmpty()) {
    needsEdgeClass = true;
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
     if (needsEdgeClass) { 
    stringBuffer.append(TEXT_5);
     }
if (needsEdgeFactory) { 
    stringBuffer.append(TEXT_6);
     } 
    stringBuffer.append(TEXT_7);
    if (masterLogging || vertexLogging) { 
    stringBuffer.append(TEXT_8);
    } 
    stringBuffer.append(TEXT_9);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_16);
    

Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(mainUnit.getModule());
int value = 0;
for (ENamedElement type : typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_17);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( typeConstants.get(type) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_20);
    
}

Map<Unit,String> unitConstants = GiraphUtil.getUnitConstants(mainUnit);
value = 0;
for (Unit unit : unitConstants.keySet()) {
  
    stringBuffer.append(TEXT_21);
    stringBuffer.append( (unit instanceof Rule) ? "Rule" : "Unit" );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_25);
    
}

if (masterLogging || vertexLogging) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_27);
     } 
    stringBuffer.append(TEXT_28);
    stringBuffer.append( segmentCount );
    stringBuffer.append(TEXT_29);
     for (Rule rule : rules) { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append( unitConstants.get(rule) );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( ruleData.get(rule).rule.getName() );
    stringBuffer.append(TEXT_32);
     } 
    stringBuffer.append(TEXT_33);
    

// Generate the code for all rules: 
for (GiraphRuleData data : ruleData.values()) {
  Rule rule = data.rule;
  RuleChangeInfo changeInfo = data.changeInfo;


    stringBuffer.append(TEXT_34);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_37);
    
  /* START LOGGING */
  if (vertexLogging) { 
    stringBuffer.append(TEXT_38);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_39);
     } 
  /* END LOGGING */

    stringBuffer.append(TEXT_40);
    stringBuffer.append(TEXT_41);
    stringBuffer.append( data.matchingSteps.size() > 1 ? "matches = " : "" );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_43);
      for (int i=0; i<data.matchingSteps.size(); i++) {

      /* START STEP LOOP */
      GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_45);
        if (step.isJoin) {
        /* START JOIN */        
        
    stringBuffer.append(TEXT_46);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.node) );
    stringBuffer.append(TEXT_48);
          if (vertexLogging) { 
    stringBuffer.append(TEXT_49);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_50);
          } 
    stringBuffer.append(TEXT_51);
        if (rule.isInjectiveMatching()) {
    stringBuffer.append(TEXT_52);
        }
      if (step.sendBackTo != null) {
        /* START SEND BACK TO */        

    stringBuffer.append(TEXT_53);
    stringBuffer.append( GiraphUtil.getNodeName(step.sendBackTo) );
    stringBuffer.append(TEXT_54);
          if (vertexLogging) { 
    stringBuffer.append(TEXT_55);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_56);
          } 
    stringBuffer.append(TEXT_57);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_58);
            /* END SEND BACK TO */
      } else if (i == data.matchingSteps.size()-1) { 
    stringBuffer.append(TEXT_59);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_60);
        } 
    stringBuffer.append(TEXT_61);
    
        /* END JOIN */        
      } else {
        /* START CHECKING */        
        String xx = "";
        if (step.isMatching) {
          /* START MATCHING */
          xx = "  ";
          List<EClass> validTypes = GiraphUtil.getValidTypes(step.node, mainUnit.getModule());

    stringBuffer.append(TEXT_62);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_63);
        for (int j=0; j<validTypes.size(); j++) { 
    stringBuffer.append(TEXT_64);
    stringBuffer.append( (j==0) ? "boolean ok = " : "  " );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( typeConstants.get(validTypes.get(j)) );
    stringBuffer.append( (j==validTypes.size()-1) ? ";" : " ||" );
        }
      if (rule.isInjectiveMatching() && !step.node.getOutgoing().isEmpty()) { 
    stringBuffer.append(TEXT_66);
    stringBuffer.append( step.node.getOutgoing().size() );
    stringBuffer.append(TEXT_67);
        } 
      if (i==0) { 
    stringBuffer.append(TEXT_68);
        } 
    stringBuffer.append(TEXT_69);
    
          /* END MATCHING */
        }
        if (step.isStart) {
          /* START IS START */        

    stringBuffer.append(TEXT_70);
    
          /* END IS START */        
        } else {
          /* START IS NOT START*/        

    stringBuffer.append(TEXT_71);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_72);
            if (step.isMatching) { 
    stringBuffer.append(TEXT_73);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_74);
              if (rule.isInjectiveMatching()) {
    stringBuffer.append(TEXT_75);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_80);
              }
            NodeEquivalence equi = data.requiredNodesEquivalences.get(step.node);
            if (equi!=null && equi.indexOf(step.node)>0) { 
              Node compareTo = equi.get(equi.indexOf(step.node)-1); 
    stringBuffer.append(TEXT_81);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_82);
    stringBuffer.append( data.orderedLhsNodes.indexOf(compareTo) );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_87);
              } 
          } 
          if (step.verifyEdgeTo == null) { 
    stringBuffer.append(TEXT_88);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_89);
            }
          /* END IS NOT START*/
        }
        if (step.edge != null) {
          /* START EDGE */
          if (step.verifyEdgeTo != null) {
            /* START VERIFY EDGE */
            xx = xx + "    ";

    stringBuffer.append(TEXT_90);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_91);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_93);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_96);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.verifyEdgeTo) );
    stringBuffer.append(TEXT_97);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_105);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_106);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_110);
    
            /* END VERIFY EDGE */        
          }
          /* END EDGE */
        } 
          if (step.sendBackTo != null) {
          /* START SEND BACK TO */        

    stringBuffer.append(TEXT_111);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_112);
    stringBuffer.append( GiraphUtil.getNodeName(step.sendBackTo) );
    stringBuffer.append(TEXT_113);
            if (vertexLogging) { 
    stringBuffer.append(TEXT_114);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_119);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_120);
            } 
    stringBuffer.append(TEXT_121);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_122);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_123);
            /* END SEND BACK TO */        
        } else if (i == data.matchingSteps.size()-1) {
          /* START LAST STEP */ 
          if (step.isStart) {
            xx = "";
          }
          
    stringBuffer.append(TEXT_124);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_127);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_128);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_136);
            /* END LAST STEP */        
        }
        if (step.verifyEdgeTo != null) {
          /* START VERIFY EDGE */

    stringBuffer.append(TEXT_137);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_140);
    
          xx = xx.substring(0, xx.length() - 4);
          /* END VERIFY EDGE */
        }
        if (step.edge!=null && step.verifyEdgeTo==null) {
          /* START NOT VERIFY EDGE */
          String yy = !step.isStart && step.isMatching ? "  " : "";

    stringBuffer.append(TEXT_141);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_142);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_143);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_149);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_150);
              if (vertexLogging) { 
    stringBuffer.append(TEXT_151);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_156);
              } 
    stringBuffer.append(TEXT_157);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_162);
    
          /* END NOT VERIFY EDGE */        
        }
        if (!step.isStart) {
    stringBuffer.append(TEXT_163);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_164);
          }
        if (step.isMatching) { 
    stringBuffer.append(TEXT_165);
          }
        if (step.keepMatchesOf != null) {

    stringBuffer.append(TEXT_166);
    stringBuffer.append( GiraphUtil.getNodeName(step.keepMatchesOf) );
    stringBuffer.append(TEXT_167);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.keepMatchesOf) );
    stringBuffer.append(TEXT_168);
            if (vertexLogging) { 
    stringBuffer.append(TEXT_169);
            } 
    stringBuffer.append(TEXT_170);
            }
     } 
    stringBuffer.append(TEXT_171);
    
    } // end for

    stringBuffer.append(TEXT_172);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_173);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_174);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_175);
    stringBuffer.append( data.orderedLhsNodes.size() );
    stringBuffer.append(TEXT_176);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_177);
    stringBuffer.append( data.matchingSteps.size()-1 );
    stringBuffer.append(TEXT_178);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_179);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_180);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_181);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_182);
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
    stringBuffer.append(TEXT_183);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_184);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_185);
      }
  }
  // Sort indexes of nodes to be removed from the match:
  List<Integer> required = new ArrayList<Integer>();
  for (Node node : data.requiredNodes) {
    required.add(data.orderedLhsNodes.indexOf(node));
  }
  Collections.sort(required);
  Collections.reverse(required);
  for (Integer req : required) { 
    stringBuffer.append(TEXT_186);
    stringBuffer.append( req );
    stringBuffer.append(TEXT_187);
     } 
    stringBuffer.append(TEXT_188);
     if (vertexLogging) { 
    stringBuffer.append(TEXT_189);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_190);
     } 
    
  for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_191);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_192);
    stringBuffer.append( data.orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_193);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_194);
    stringBuffer.append( data.orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_195);
      }

    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_196);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_197);
     if (useUUIDs) { 
    stringBuffer.append(TEXT_198);
     } else { 
    stringBuffer.append(TEXT_199);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_200);
     } 
    stringBuffer.append(TEXT_201);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_202);
    stringBuffer.append( typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_203);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_204);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_205);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_206);
    	} else { 
    stringBuffer.append(TEXT_207);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_208);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_209);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_210);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_211);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_212);
    	} else { 
    stringBuffer.append(TEXT_213);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_214);
    stringBuffer.append( data.orderedLhsNodes.indexOf(data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_215);
    	} 
    stringBuffer.append(TEXT_216);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_217);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_219);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_220);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_221);
      e++;
    } 
    stringBuffer.append(TEXT_222);
    
} // end of for all rules

    stringBuffer.append(TEXT_223);
     if (needsVertexIdFactory && !useUUIDs) { 
    stringBuffer.append(TEXT_224);
     } 
    stringBuffer.append(TEXT_225);
     if (masterLogging) { 
    stringBuffer.append(TEXT_226);
     } 
    stringBuffer.append(TEXT_227);
    stringBuffer.append( unitConstants.get(mainUnit) );
    stringBuffer.append(TEXT_228);
     if (!(mainUnit instanceof Rule)) { 
    stringBuffer.append(TEXT_229);
     } 
    stringBuffer.append(TEXT_230);
     for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_231);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_232);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_233);
    stringBuffer.append( (unit instanceof Rule) ? ", segment" : "" );
    stringBuffer.append(TEXT_234);
    stringBuffer.append( (unit instanceof Rule) ? ", ruleApps" : "" );
    stringBuffer.append(TEXT_235);
     } // end for 
    stringBuffer.append(TEXT_236);
     for (int i=0; i<rules.size(); i++) { 
    stringBuffer.append(TEXT_237);
    stringBuffer.append( i==0 ? "if (" : "  " );
    stringBuffer.append(TEXT_238);
    stringBuffer.append( unitConstants.get(rules.get(i)) + (i<rules.size()-1 ? " ||" : ") {" ) );
     } 
    stringBuffer.append(TEXT_239);
    

for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_240);
    stringBuffer.append( unit.eClass().getName() );
    stringBuffer.append(TEXT_241);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_242);
     if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_243);
     } 
    stringBuffer.append(TEXT_244);
     if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_245);
     } 
    stringBuffer.append(TEXT_246);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_247);
    stringBuffer.append( (unit instanceof Rule) ? ", int segment" : "" );
    stringBuffer.append(TEXT_248);
    stringBuffer.append( (unit instanceof Rule) ? ", long ruleApps" : "" );
    stringBuffer.append(TEXT_249);
     if (unit instanceof IteratedUnit) {
   int iters = Integer.parseInt(((IteratedUnit) unit).getIterations()); 
    stringBuffer.append(TEXT_250);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_251);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_252);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_253);
    stringBuffer.append( unitConstants.get(((IteratedUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_254);
     } else if (unit instanceof SequentialUnit) {
     SequentialUnit seq = (SequentialUnit) unit; 
    stringBuffer.append(TEXT_255);
    stringBuffer.append( seq.getSubUnits().size() );
    stringBuffer.append(TEXT_256);
     for (int i=0; i<seq.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_257);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_258);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_259);
    stringBuffer.append( i+1 );
    stringBuffer.append(TEXT_260);
    stringBuffer.append( unitConstants.get(seq.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_261);
     } 
    stringBuffer.append(TEXT_262);
     } else if (unit instanceof IndependentUnit) { 
     IndependentUnit indi = (IndependentUnit) unit; 
    stringBuffer.append(TEXT_263);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_264);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_265);
     for (int i=0; i<indi.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_266);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_267);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_268);
    stringBuffer.append( unitConstants.get(indi.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_269);
     } 
    stringBuffer.append(TEXT_270);
     } else if (unit instanceof LoopUnit) { 
    stringBuffer.append(TEXT_271);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_272);
    stringBuffer.append( unitConstants.get(((LoopUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_273);
     } else if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_274);
    stringBuffer.append( ruleData.get(unit).matchingSteps.size()-1 );
    stringBuffer.append(TEXT_275);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_276);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_277);
     } 
    stringBuffer.append(TEXT_278);
     } // end for 
    stringBuffer.append(TEXT_279);
    stringBuffer.append(TEXT_280);
    return stringBuffer.toString();
  }
}
