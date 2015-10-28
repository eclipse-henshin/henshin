package org.eclipse.emf.henshin.giraph.templates;

import java.util.*;
import org.eclipse.emf.henshin.giraph.*;
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
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.ArrayDeque;" + NL + "import java.util.ArrayList;";
  protected final String TEXT_3 = NL + "import java.util.Collections;";
  protected final String TEXT_4 = NL + "import java.util.Deque;" + NL + "import java.util.HashSet;" + NL + "import java.util.List;" + NL + "import java.util.Set;" + NL + "" + NL + "import org.apache.giraph.aggregators.LongSumAggregator;" + NL + "import org.apache.giraph.edge.Edge;";
  protected final String TEXT_5 = NL + "import org.apache.giraph.edge.EdgeFactory;";
  protected final String TEXT_6 = NL + "import org.apache.giraph.examples.Algorithm;" + NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.master.DefaultMasterCompute;" + NL + "import org.apache.hadoop.io.ByteWritable;" + NL + "import org.apache.hadoop.io.LongWritable;";
  protected final String TEXT_7 = NL + "import org.apache.log4j.Logger;";
  protected final String TEXT_8 = NL + "import static ";
  protected final String TEXT_9 = ".HenshinUtil.ApplicationStack;" + NL + "import static ";
  protected final String TEXT_10 = ".HenshinUtil.ApplicationStackAggregator;" + NL + "import static ";
  protected final String TEXT_11 = ".HenshinUtil.Match;" + NL + "import static ";
  protected final String TEXT_12 = ".HenshinUtil.VertexId;" + NL + "" + NL + "/**" + NL + " * Generated implementation of the Henshin unit \"";
  protected final String TEXT_13 = "\"." + NL + " */" + NL + "@Algorithm(name = \"";
  protected final String TEXT_14 = "\")" + NL + "public class ";
  protected final String TEXT_15 = " extends BasicComputation<VertexId, ByteWritable, ByteWritable, Match> {" + NL + "" + NL + "\t/**" + NL + "\t * Name of the match count aggregator." + NL + "\t */" + NL + "\tpublic static final String AGGREGATOR_MATCHES = \"matches\";" + NL + "" + NL + "\t/**" + NL + "\t * Name of the rule application count aggregator." + NL + "\t */" + NL + "\tpublic static final String AGGREGATOR_RULE_APPLICATIONS = \"ruleApps\";" + NL + "" + NL + "\t/**" + NL + "\t * Name of the node generation aggregator." + NL + "\t */" + NL + "\tpublic static final String AGGREGATOR_NODE_GENERATION = \"nodeGen\";" + NL + "" + NL + "\t/**" + NL + "\t * Name of the application stack aggregator." + NL + "\t */" + NL + "\tpublic static final String AGGREGATOR_APPLICATION_STACK = \"appStack\";";
  protected final String TEXT_16 = NL + NL + "\t/**" + NL + "\t * Type constant for \"";
  protected final String TEXT_17 = "\"." + NL + "\t */" + NL + "\tpublic static final byte ";
  protected final String TEXT_18 = " = ";
  protected final String TEXT_19 = ";";
  protected final String TEXT_20 = NL + NL + "\t/**" + NL + "\t * ";
  protected final String TEXT_21 = " constant for \"";
  protected final String TEXT_22 = "\"." + NL + "\t */" + NL + "\tpublic static final int ";
  protected final String TEXT_23 = " = ";
  protected final String TEXT_24 = ";";
  protected final String TEXT_25 = NL + NL + "\t/**" + NL + "\t * Logging support." + NL + "\t */" + NL + "\tprotected static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_26 = ".class);";
  protected final String TEXT_27 = NL + NL + "\t/**" + NL + "\t * Default segment count." + NL + "\t */" + NL + "\tprivate static int SEGMENT_COUNT = ";
  protected final String TEXT_28 = ";" + NL + "\t" + NL + "\t/**" + NL + "\t * Default stair count" + NL + "\t */" + NL + "\tprivate static final int STAIR_COUNT = ";
  protected final String TEXT_29 = ";" + NL + "" + NL + "\t/**" + NL + "\t * Currently active rule." + NL + "\t */" + NL + "\tprivate int rule;" + NL + "" + NL + "\t/**" + NL + "\t * Current segment." + NL + "\t */" + NL + "\tprivate int segment;" + NL + "" + NL + "\t/**" + NL + "\t * Current microstep." + NL + "\t */" + NL + "\tprivate int microstep;" + NL + "" + NL + "\t/**" + NL + "\t * Finished flag." + NL + "\t */" + NL + "\tprivate boolean finished;" + NL + "" + NL + "\t/*" + NL + "\t * (non-Javadoc)" + NL + "\t * @see org.apache.giraph.graph.Computation#preSuperstep()" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic void preSuperstep() {" + NL + "\t\tApplicationStack stack = getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "\t\tif (stack.getStackSize() == 0) {" + NL + "\t\t\tlong ruleApps = ((LongWritable) getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();" + NL + "\t\t\tfinished = ruleApps == 0;" + NL + "\t\t\trule = -1;" + NL + "\t\t} else {" + NL + "\t\t\tfinished = false;" + NL + "\t\t\trule = stack.getLastUnit();" + NL + "\t\t\tsegment = stack.getLastSegment();" + NL + "\t\t\tmicrostep = stack.getLastMicrostep();" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/*" + NL + "\t * (non-Javadoc)" + NL + "\t * @see org.apache.giraph.graph.Computation#compute(org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic void compute(Vertex<VertexId, ByteWritable, ByteWritable> vertex, Iterable<Match> matches) throws IOException {" + NL + "\t\tif (finished) {" + NL + "\t\t\tvertex.voteToHalt();" + NL + "\t\t\treturn;" + NL + "\t\t}" + NL + "\t\tswitch (rule) {";
  protected final String TEXT_30 = NL + "\t\tcase ";
  protected final String TEXT_31 = ":" + NL + "\t\t\tmatch";
  protected final String TEXT_32 = "(vertex, matches, segment, microstep);" + NL + "\t\t\tbreak;";
  protected final String TEXT_33 = NL + "\t\tdefault:" + NL + "\t\t\tbreak;" + NL + "\t\t}" + NL + "\t}";
  protected final String TEXT_34 = NL + NL + "\t/**" + NL + "\t * Match (and apply) the rule \"";
  protected final String TEXT_35 = "\"." + NL + "\t * This takes ";
  protected final String TEXT_36 = " microsteps." + NL + "\t * @param vertex The current vertex." + NL + "\t * @param matches The current matches." + NL + "\t * @param segment The current segment." + NL + "\t * @param microstep The current microstep." + NL + "\t */" + NL + "\tprotected void match";
  protected final String TEXT_37 = "(Vertex<VertexId, ByteWritable, ByteWritable> vertex, Iterable<Match> matches, int segment, int microstep) throws IOException {";
  protected final String TEXT_38 = NL + "\t\tLOG.info(\"Vertex \" + vertex.getId() + \" in superstep \" + getSuperstep() +" + NL + "\t\t\t\" matching rule ";
  protected final String TEXT_39 = " on segment \" + segment +" + NL + "\t\t\t\" in microstep \" + microstep);" + NL + "\t\tfor (Match match : matches) {" + NL + "\t\t\tLOG.info(\"Vertex \" + vertex.getId() +" + NL + "\t\t\t\t\" in superstep \" + getSuperstep() +" + NL + "\t\t\t\t\" received (partial) match \" + match);" + NL + "\t\t}";
  protected final String TEXT_40 = NL + "\t\tSet<Match> finalMatches = new HashSet<>();" + NL + "\t\t";
  protected final String TEXT_41 = "filter";
  protected final String TEXT_42 = "(vertex, matches, segment, microstep, finalMatches);" + NL + "\t\tlong matchCount = 0;" + NL + "\t\tlong appCount = 0;" + NL + "\t\tboolean ok;";
  protected final String TEXT_43 = NL + "\t\tVertexId id;" + NL + "\t\tList<Match> matches1 = new ArrayList<>();" + NL + "\t\tList<Match> matches2 = new ArrayList<>();" + NL + "\t\tSet<Match> joinSet = new HashSet<>();" + NL + "\t\tint index = 0;";
  protected final String TEXT_44 = NL + "\t\tSet<VertexId> targets;" + NL + "\t\tVertexId targetId;";
  protected final String TEXT_45 = NL + "\t\tif (microstep == ";
  protected final String TEXT_46 = "){" + NL + "\t\t\tsendMessage(vertex.getId(), new Match(segment, ";
  protected final String TEXT_47 = "));" + NL + "\t\t}";
  protected final String TEXT_48 = NL + "\t\tif (microstep < STAIR_COUNT ";
  protected final String TEXT_49 = "){";
  protected final String TEXT_50 = NL + "\t  \tfor (Match match : matches){" + NL + "\t  \t\tswitch ((int)match.getMicrostep()){";
  protected final String TEXT_51 = NL + "\t\t\tcase ";
  protected final String TEXT_52 = ": ";
  protected final String TEXT_53 = NL + "\t\t\t\tif (microstep < ";
  protected final String TEXT_54 = " + STAIR_COUNT - 1){" + NL + "\t\t\t\t\t//wait for all stairs to finish" + NL + "\t\t\t\t\tsendMessage(vertex.getId(), match);" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t} else if (!joinSet.add(match)){" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t\t// Joining matches at node ";
  protected final String TEXT_55 = ":" + NL + "\t\t\t\tid = vertex.getId();" + NL + "\t\t\t\tif (id.equals(match.getVertexId(";
  protected final String TEXT_56 = "))) {" + NL + "\t\t\t\t\tmatches1.add(match.copy());" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tmatches2.add(match.copy());" + NL + "\t\t\t\t}";
  protected final String TEXT_57 = NL + "\t\t\t\tLOG.info(\"Vertex \" + id + \" in superstep \" + getSuperstep() +" + NL + "\t\t\t\t\t\" joining \" + matches1.size() + \" x \" + matches2.size() +" + NL + "\t\t\t\t\t\" partial matches of rule ";
  protected final String TEXT_58 = "\");";
  protected final String TEXT_59 = NL + "\t\t\t\tboolean breaking = false;" + NL + "\t\t\t\tint j = 0;" + NL + "\t\t\t\tfor (Match tmpMatch : matches){" + NL + "\t\t\t\t\tif (j <= index){" + NL + "\t\t\t\t\t\tj++;" + NL + "\t\t\t\t\t}else {" + NL + "\t\t\t\t\t\tif (tmpMatch.getMicrostep() == ";
  protected final String TEXT_60 = "){" + NL + "\t\t\t\t\t\t\tbreaking = true;" + NL + "\t\t\t\t\t\t\tbreak;" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t\tif (breaking)" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\tfor (Match m1 : matches1) {" + NL + "\t\t\t\t\tfor (Match m2 : matches2) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_61 = "match = m1.append(m2);";
  protected final String TEXT_62 = NL + "\t\t\t\t\t\tif (!match.isInjective()) {" + NL + "\t\t\t\t\t\t\tcontinue;" + NL + "\t\t\t\t\t\t}";
  protected final String TEXT_63 = NL + "\t\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));" + NL + "\t\t\t\t\t\tmatchCount++;";
  protected final String TEXT_64 = NL + "\t\t\t\t\t\tLOG.info(\"Vertex \" + vertex.getId() +" + NL + "\t\t\t\t\t\t\t\" sending (partial) match \" + match +" + NL + "\t\t\t\t\t\t\t\" back to vertex \" + match.getVertexId(";
  protected final String TEXT_65 = "));";
  protected final String TEXT_66 = NL + "\t\t\t\t\t\tsendMessage(match.getVertexId(";
  protected final String TEXT_67 = "), match);";
  protected final String TEXT_68 = NL + "\t\t\t\t\t\tmatch = match.remove(";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = NL + "\t\t\t\t\t\tif (!finalMatches.add(match)) {" + NL + "\t\t\t\t\t\t\tbreak;" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));" + NL + "\t\t\t\t\t\tmatchCount++;" + NL + "\t\t\t\t\t\tif (segment == SEGMENT_COUNT - 1 && microstep == ";
  protected final String TEXT_71 = " + STAIR_COUNT - 1) {" + NL + "\t\t\t\t\t\t\tapply";
  protected final String TEXT_72 = "(vertex, match, appCount++);" + NL + "\t\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t\tsendMessage(vertex.getId(), match);" + NL + "\t\t\t\t\t\t}";
  protected final String TEXT_73 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}";
  protected final String TEXT_74 = NL + "\t\t\t\tid = match.getVertexId(";
  protected final String TEXT_75 = ");" + NL + "\t\t\t\tif (vertex.getId().equals(id)) {" + NL + "\t\t\t\t\tmatchCount++;";
  protected final String TEXT_76 = NL + "\t\t\t\t\tLOG.info(\"Vertex \" + id + \" in superstep \" + getSuperstep() +" + NL + "\t\t\t\t\t\t\" sending (partial) match \" + match + \" to myself\");";
  protected final String TEXT_77 = NL + "\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));" + NL + "\t\t\t\t\tsendMessage(id, match);" + NL + "\t\t\t\t}";
  protected final String TEXT_78 = NL + "\t\t\t\t// Matching node ";
  protected final String TEXT_79 = ":";
  protected final String TEXT_80 = NL + "\t\t\t\t";
  protected final String TEXT_81 = "vertex.getValue().get() == ";
  protected final String TEXT_82 = NL + "\t\t\t\tok = ok && vertex.getNumEdges() >= ";
  protected final String TEXT_83 = ";";
  protected final String TEXT_84 = NL + "\t\t\t\tok = ok && (SEGMENT_COUNT == 1 || getSegment(vertex.getId()) == segment);";
  protected final String TEXT_85 = NL + "\t\t\t\tif (ok) {";
  protected final String TEXT_86 = NL + "\t\t\t\t\tif (!(match.getMatchSize() == 0)){" + NL + "\t\t\t\t\t\tbreak;" + NL + "\t\t\t\t\t}";
  protected final String TEXT_87 = NL + "\t\t\t\t\t";
  protected final String TEXT_88 = "match = new Match(segment, ";
  protected final String TEXT_89 = ").append(vertex.getId());";
  protected final String TEXT_90 = NL;
  protected final String TEXT_91 = "\t\t\t\tmatch = match.append(vertex.getId());";
  protected final String TEXT_92 = NL;
  protected final String TEXT_93 = "\t\t\t\tif (!match.isInjective()) {";
  protected final String TEXT_94 = NL;
  protected final String TEXT_95 = "\t\t\t\t\tbreak;";
  protected final String TEXT_96 = NL;
  protected final String TEXT_97 = "\t\t\t\t}";
  protected final String TEXT_98 = NL;
  protected final String TEXT_99 = "\t\t\t\tif (vertex.getId().compareTo(match.getVertexId(";
  protected final String TEXT_100 = ")) < 0) {";
  protected final String TEXT_101 = NL;
  protected final String TEXT_102 = "\t\t\t\t\tbreak;";
  protected final String TEXT_103 = NL;
  protected final String TEXT_104 = "\t\t\t\t}";
  protected final String TEXT_105 = NL;
  protected final String TEXT_106 = "\t\t// Node ";
  protected final String TEXT_107 = ": check for edge to match of ";
  protected final String TEXT_108 = " of type \"";
  protected final String TEXT_109 = "\":";
  protected final String TEXT_110 = NL;
  protected final String TEXT_111 = "\t\ttargetId = match.getVertexId(";
  protected final String TEXT_112 = ");";
  protected final String TEXT_113 = NL;
  protected final String TEXT_114 = "\t\tfor (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {";
  protected final String TEXT_115 = NL;
  protected final String TEXT_116 = "\t\t\tif (edge.getValue().get() == ";
  protected final String TEXT_117 = " && edge.getTargetVertexId().equals(targetId)) {";
  protected final String TEXT_118 = NL;
  protected final String TEXT_119 = "\t\t\t\tmatchCount++;";
  protected final String TEXT_120 = NL;
  protected final String TEXT_121 = "\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));";
  protected final String TEXT_122 = NL;
  protected final String TEXT_123 = "\t\t\t\tLOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_124 = NL;
  protected final String TEXT_125 = "\t\t\t\t\t\" sending (partial) match \" + match +";
  protected final String TEXT_126 = NL;
  protected final String TEXT_127 = "\t\t\t\t\t\" back to vertex \" + match.getVertexId(";
  protected final String TEXT_128 = "));";
  protected final String TEXT_129 = NL;
  protected final String TEXT_130 = "\t\t\t\tsendMessage(match.getVertexId(";
  protected final String TEXT_131 = "), match);";
  protected final String TEXT_132 = NL;
  protected final String TEXT_133 = "\t\t\t\tmatch = match.remove(";
  protected final String TEXT_134 = ");";
  protected final String TEXT_135 = NL;
  protected final String TEXT_136 = "\t\t\t\tif (finalMatches.add(match)) {";
  protected final String TEXT_137 = NL;
  protected final String TEXT_138 = "\t\t\t\t\tmatchCount++;";
  protected final String TEXT_139 = NL;
  protected final String TEXT_140 = "\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));";
  protected final String TEXT_141 = NL;
  protected final String TEXT_142 = "\t\t\t\t\tif (segment == SEGMENT_COUNT - 1 && microstep == ";
  protected final String TEXT_143 = " + STAIR_COUNT - 1 ) {";
  protected final String TEXT_144 = NL;
  protected final String TEXT_145 = "\t\t\t\t\t\tapply";
  protected final String TEXT_146 = "(vertex, match, appCount++);";
  protected final String TEXT_147 = NL;
  protected final String TEXT_148 = "\t\t\t\t\t} else {";
  protected final String TEXT_149 = NL;
  protected final String TEXT_150 = "\t\t\t\t\t\tsendMessage(vertex.getId(), match);";
  protected final String TEXT_151 = NL;
  protected final String TEXT_152 = "\t\t\t\t\t}";
  protected final String TEXT_153 = NL;
  protected final String TEXT_154 = "\t\t\t\t}";
  protected final String TEXT_155 = NL;
  protected final String TEXT_156 = "\t\t\t\tbreak;";
  protected final String TEXT_157 = NL;
  protected final String TEXT_158 = "\t\t\t}";
  protected final String TEXT_159 = NL;
  protected final String TEXT_160 = "\t\t}";
  protected final String TEXT_161 = NL;
  protected final String TEXT_162 = "\t\t\t\t\tmatchCount++;";
  protected final String TEXT_163 = NL;
  protected final String TEXT_164 = "\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));";
  protected final String TEXT_165 = NL;
  protected final String TEXT_166 = "\t\t\t\t\ttargets = new HashSet<>();";
  protected final String TEXT_167 = NL;
  protected final String TEXT_168 = "\t\t\t\t\tfor (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {";
  protected final String TEXT_169 = NL;
  protected final String TEXT_170 = "\t\t\t\t\t\tif (edge.getValue().get() == ";
  protected final String TEXT_171 = " && targets.add(edge.getTargetVertexId())) {";
  protected final String TEXT_172 = NL;
  protected final String TEXT_173 = "\t\t\t\t\t\t\tLOG.info(\"Vertex \" + vertex.getId() +";
  protected final String TEXT_174 = NL;
  protected final String TEXT_175 = "\t\t\t\t\t\t\t\t\" sending (partial) match \" + match +";
  protected final String TEXT_176 = NL;
  protected final String TEXT_177 = "\t\t\t\t\t\t\t\t\" forward to vertex \" + edge.getTargetVertexId());";
  protected final String TEXT_178 = NL;
  protected final String TEXT_179 = "\t\t\t\t\t\t\tsendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_180 = NL;
  protected final String TEXT_181 = "\t\t\t\t\t\t}";
  protected final String TEXT_182 = NL;
  protected final String TEXT_183 = "\t\t\t\t\t}";
  protected final String TEXT_184 = NL + "\t\t\t\t}";
  protected final String TEXT_185 = NL;
  protected final String TEXT_186 = NL + "\t\t\tdefault:" + NL + "\t\t\t\tif (finalMatches.add(match)) {" + NL + "\t\t\t\t\tmatchCount++;" + NL + "\t\t\t\t\tmatch = match.setMicrostep((byte) (match.getMicrostep() + 1));" + NL + "\t\t\t\t\tif (segment == SEGMENT_COUNT - 1 && microstep == ";
  protected final String TEXT_187 = " + STAIR_COUNT - 1 ) {" + NL + "\t\t\t\t\t\tapply";
  protected final String TEXT_188 = "(vertex, match, appCount++);" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tsendMessage(vertex.getId(), match);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}";
  protected final String TEXT_189 = NL + "\t\t\t}";
  protected final String TEXT_190 = NL + "\t\t\tindex ++;";
  protected final String TEXT_191 = NL + "\t\t}";
  protected final String TEXT_192 = NL + "\t\tif (matchCount > 0) {" + NL + "\t\t\taggregate(AGGREGATOR_MATCHES, new LongWritable(matchCount));" + NL + "\t\t}" + NL + "\t\tif (appCount > 0) {" + NL + "\t\t\taggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(appCount));" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Filter matches per segment for the rule \"";
  protected final String TEXT_193 = "\"." + NL + "\t * @param vertex The current vertex." + NL + "\t * @param matches The current matches." + NL + "\t * @param segment The current segment." + NL + "\t * @param microstep The current microstep." + NL + "\t * @param finalMatches Set of final matches." + NL + "\t * @return The filtered matches." + NL + "\t */" + NL + "\tprotected Iterable<Match> filter";
  protected final String TEXT_194 = "(Vertex<VertexId, ByteWritable, ByteWritable> vertex, Iterable<Match> matches, int segment, int microstep, Set<Match> finalMatches) throws IOException {" + NL + "\t\tif (segment > 0) {" + NL + "\t\t\tList<Match> filtered = new ArrayList<>();" + NL + "\t\t\tlong matchCount = 0;" + NL + "\t\t\tlong appCount = 0;" + NL + "\t\t\tfor (Match match : matches) {" + NL + "\t\t\t\tint matchSegment = match.getSegment();" + NL + "\t\t\t\tif (matchSegment < segment) {" + NL + "\t\t\t\t\tif (!finalMatches.add(match)) {" + NL + "\t\t\t\t\t\tcontinue;" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tmatchCount++;" + NL + "\t\t\t\t\tif (segment == SEGMENT_COUNT - 1 && microstep == ";
  protected final String TEXT_195 = " + STAIR_COUNT - 1 ) {" + NL + "\t\t\t\t\t\tapply";
  protected final String TEXT_196 = "(vertex, match, appCount++);" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tsendMessage(vertex.getId(), match);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t} else if (matchSegment > segment) {" + NL + "\t\t\t\t\tthrow new RuntimeException(\"Received match \" + match +" + NL + "\t\t\t\t\t\t\" of rule ";
  protected final String TEXT_197 = " of segment \" +" + NL + "\t\t\t\t\t\tmatchSegment + \", but current segment is only \" + segment);" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tfiltered.add(match.copy());" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\tif (matchCount > 0) {" + NL + "\t\t\t\taggregate(AGGREGATOR_MATCHES, new LongWritable(matchCount));" + NL + "\t\t\t}" + NL + "\t\t\tif (appCount > 0) {" + NL + "\t\t\t\taggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(appCount));" + NL + "\t\t\t}" + NL + "\t\t\treturn filtered;" + NL + "\t\t}" + NL + "\t\treturn matches;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Apply the rule \"";
  protected final String TEXT_198 = "\" to a given match." + NL + "\t * @param vertex The base vertex." + NL + "\t * @param match The match object." + NL + "\t * @param matchIndex Match index." + NL + "\t * @return true if the rule was applied." + NL + "\t * @throws IOException On I/O errors." + NL + "\t */" + NL + "\tprotected boolean apply";
  protected final String TEXT_199 = "(" + NL + "\t\tVertex<VertexId, ByteWritable, ByteWritable> vertex," + NL + "\t\tMatch match, long matchIndex) throws IOException {";
  protected final String TEXT_200 = NL + "\t\tVertexId cur";
  protected final String TEXT_201 = " = match.getVertexId(";
  protected final String TEXT_202 = ");";
  protected final String TEXT_203 = NL + "\t\tLOG.info(\"Vertex \" + vertex.getId() +" + NL + "\t\t\t\" applying rule ";
  protected final String TEXT_204 = " with match \" + match);";
  protected final String TEXT_205 = NL + "\t\tremoveEdgesRequest(cur";
  protected final String TEXT_206 = ", cur";
  protected final String TEXT_207 = ");";
  protected final String TEXT_208 = NL + "\t\tremoveVertexRequest(cur";
  protected final String TEXT_209 = ");";
  protected final String TEXT_210 = NL + "\t\tVertexId new";
  protected final String TEXT_211 = " =";
  protected final String TEXT_212 = NL + "\t\t\tVertexId.randomVertexId();";
  protected final String TEXT_213 = NL + "\t\t\tderiveVertexId(vertex.getId(), (int) matchIndex, ";
  protected final String TEXT_214 = ");";
  protected final String TEXT_215 = NL + "\t\taddVertexRequest(new";
  protected final String TEXT_216 = ", new ByteWritable(";
  protected final String TEXT_217 = "));";
  protected final String TEXT_218 = NL + "\t\tVertexId src";
  protected final String TEXT_219 = " = new";
  protected final String TEXT_220 = ";";
  protected final String TEXT_221 = NL + "\t\tVertexId src";
  protected final String TEXT_222 = " = cur";
  protected final String TEXT_223 = ";";
  protected final String TEXT_224 = NL + "\t\tVertexId trg";
  protected final String TEXT_225 = " = new";
  protected final String TEXT_226 = ";";
  protected final String TEXT_227 = NL + "\t\tVertexId trg";
  protected final String TEXT_228 = " = cur";
  protected final String TEXT_229 = ";";
  protected final String TEXT_230 = NL + "\t\tEdge<VertexId, ByteWritable> edge";
  protected final String TEXT_231 = " = EdgeFactory.create(trg";
  protected final String TEXT_232 = ", new ByteWritable(";
  protected final String TEXT_233 = "));" + NL + "\t\taddEdgeRequest(src";
  protected final String TEXT_234 = ", edge";
  protected final String TEXT_235 = ");";
  protected final String TEXT_236 = NL + "\t\treturn true;" + NL + "\t}";
  protected final String TEXT_237 = NL;
  protected final String TEXT_238 = NL + "\t/**" + NL + "\t * Derive a new vertex Id from an exiting one." + NL + "\t * @param baseId The base vertex Id." + NL + "\t * @param matchIndex The index of the match." + NL + "\t * @param vertexIndex The index of the new vertex." + NL + "\t * @return The derived vertex Id." + NL + "\t */" + NL + "\tprivate VertexId deriveVertexId(VertexId baseId, int matchIndex, int vertexIndex) {" + NL + "\t\tlong generation = ((LongWritable) getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();" + NL + "\t\treturn baseId.append((byte) generation).append((byte) matchIndex).append((byte) vertexIndex);" + NL + "\t}" + NL;
  protected final String TEXT_239 = NL + "\t/**" + NL + "\t * Get the segment that a vertex belongs to." + NL + "\t * @param vertexId The ID of the vertex." + NL + "\t * @return The segment of the vertex." + NL + "\t */" + NL + "\tprivate int getSegment(VertexId vertexId) {" + NL + "\t\treturn Math.abs(vertexId.hashCode()) % SEGMENT_COUNT;" + NL + "\t}" + NL + "\t/**" + NL + "\t * Get the part that a vertex belongs to" + NL + "\t*/" + NL + "\tprivate int getPart(VertexId vertexId) {" + NL + "\t\treturn Math.abs(vertexId.hashCode()) % STAIR_COUNT;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Master compute which registers and updates the required aggregators." + NL + "\t */" + NL + "\tpublic static class MasterCompute extends DefaultMasterCompute {" + NL + "" + NL + "\t\t/**" + NL + "\t\t * Stack for storing unit success flags." + NL + "\t\t */" + NL + "\t\tprotected final Deque<Boolean> unitSuccesses = new ArrayDeque<>();" + NL + "" + NL + "\t\t/**" + NL + "\t\t * Stack for storing the execution orders of independent units." + NL + "\t\t */" + NL + "\t\tprotected final Deque<List<Integer>> unitOrders = new ArrayDeque<>();" + NL + "" + NL + "\t\t/*" + NL + "\t\t * (non-Javadoc)" + NL + "\t\t * @see org.apache.giraph.master.DefaultMasterCompute#compute()" + NL + "\t\t */" + NL + "\t\t@Override" + NL + "\t\tpublic void compute() {" + NL + "\t\t\tlong ruleApps = ((LongWritable) getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();";
  protected final String TEXT_240 = NL + "\t\t\tlong matches = ((LongWritable) getAggregatedValue(AGGREGATOR_MATCHES)).get();" + NL + "\t\t\tif (getSuperstep() > 0) {" + NL + "\t\t\t\tLOG.info(matches + \" (partial) matches computed and \" +" + NL + "\t\t\t\t\truleApps + \" rule applications conducted in superstep \" +" + NL + "\t\t\t\t\t(getSuperstep() - 1));" + NL + "\t\t\t}";
  protected final String TEXT_241 = NL + "\t\t\tif (ruleApps > 0) {" + NL + "\t\t\t\tlong nodeGen = ((LongWritable) getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();" + NL + "\t\t\t\tsetAggregatedValue(AGGREGATOR_NODE_GENERATION, new LongWritable(nodeGen + 1));" + NL + "\t\t\t}" + NL + "\t\t\tApplicationStack stack;" + NL + "\t\t\tif (getSuperstep() == 0) {" + NL + "\t\t\t\tstack = new ApplicationStack();" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_242 = ", 0, 0);";
  protected final String TEXT_243 = NL + "\t\t\t\tstack = nextRuleStep(stack, ruleApps);";
  protected final String TEXT_244 = NL + "\t\t\t} else {" + NL + "\t\t\t\tstack = getAggregatedValue(AGGREGATOR_APPLICATION_STACK);" + NL + "\t\t\t\tstack = nextRuleStep(stack, ruleApps);" + NL + "\t\t\t}" + NL + "\t\t\tsetAggregatedValue(AGGREGATOR_APPLICATION_STACK, stack);" + NL + "\t\t}" + NL + "" + NL + "\t\t/**" + NL + "\t\t * Compute the next rule application stack." + NL + "\t\t * @param stack The current application stack." + NL + "\t\t * @param ruleApps Number of rule applications in last superstep." + NL + "\t\t * @return The new application stack." + NL + "\t\t */" + NL + "\t\tprivate ApplicationStack nextRuleStep(ApplicationStack stack, long ruleApps) {" + NL + "\t\t\twhile (stack.getStackSize() > 0) {" + NL + "\t\t\t\tint unit = stack.getLastUnit();" + NL + "\t\t\t\tint segment = stack.getLastSegment();" + NL + "\t\t\t\tint microstep = stack.getLastMicrostep();" + NL + "\t\t\t\tstack = stack.removeLast();" + NL + "\t\t\t\tswitch (unit) {";
  protected final String TEXT_245 = NL + "\t\t\t\tcase ";
  protected final String TEXT_246 = ":" + NL + "\t\t\t\t\tstack = process";
  protected final String TEXT_247 = "(stack";
  protected final String TEXT_248 = ", microstep";
  protected final String TEXT_249 = ");" + NL + "\t\t\t\t\tbreak;";
  protected final String TEXT_250 = NL + "\t\t\t\tdefault:" + NL + "\t\t\t\t\tthrow new RuntimeException(\"Unknown unit \" + unit);" + NL + "\t\t\t\t}" + NL + "\t\t\t\tif (stack.getStackSize() > 0) {" + NL + "\t\t\t\t\tunit = stack.getLastUnit();";
  protected final String TEXT_251 = NL + "\t\t\t\t\t";
  protected final String TEXT_252 = "unit == ";
  protected final String TEXT_253 = NL + "\t\t\t\t\t\tbreak;" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\treturn stack;" + NL + "\t\t}";
  protected final String TEXT_254 = NL + NL + "\t\t/**" + NL + "\t\t * Process ";
  protected final String TEXT_255 = " \"";
  protected final String TEXT_256 = "\"." + NL + "\t\t * @param stack The current application stack.";
  protected final String TEXT_257 = NL + "\t\t * @param segment The current segment.";
  protected final String TEXT_258 = NL + "\t\t * @param microstep The current microstep.";
  protected final String TEXT_259 = NL + "\t\t * @param ruleApps Number of rule applications in last superstep.";
  protected final String TEXT_260 = NL + "\t\t * @return The new application stack." + NL + "\t\t */" + NL + "\t\tprivate ApplicationStack process";
  protected final String TEXT_261 = "(ApplicationStack stack";
  protected final String TEXT_262 = ", int microstep";
  protected final String TEXT_263 = ") {";
  protected final String TEXT_264 = NL + "\t\t\tif (microstep > 0 && !unitSuccesses.pop()) {" + NL + "\t\t\t\tunitSuccesses.push(false);" + NL + "\t\t\t} else if (microstep == ";
  protected final String TEXT_265 = ") {" + NL + "\t\t\t\tunitSuccesses.push(true);" + NL + "\t\t\t} else if (microstep < ";
  protected final String TEXT_266 = ") {" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_267 = ", 0, microstep + 1);" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_268 = ", 0, 0);" + NL + "\t\t\t}";
  protected final String TEXT_269 = NL + "\t\t\tif (microstep > 0 && !unitSuccesses.pop()) {" + NL + "\t\t\t\tunitSuccesses.push(false);" + NL + "\t\t\t} else if (microstep == ";
  protected final String TEXT_270 = ") {" + NL + "\t\t\t\tunitSuccesses.push(true);" + NL + "\t\t\t} else {" + NL + "\t\t\t\tswitch (microstep) {";
  protected final String TEXT_271 = NL + "\t\t\t\tcase ";
  protected final String TEXT_272 = ":" + NL + "\t\t\t\t\tstack = stack.append(";
  protected final String TEXT_273 = ", 0, ";
  protected final String TEXT_274 = ");" + NL + "\t\t\t\t\tstack = stack.append(";
  protected final String TEXT_275 = ", 0, 0);" + NL + "\t\t\t\t\tbreak;";
  protected final String TEXT_276 = NL + "\t\t\t\tdefault:" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_277 = NL + "\t\t\tif (microstep == 0) {" + NL + "\t\t\t\tList<Integer> order = new ArrayList<>();" + NL + "\t\t\t\tfor (int i = 0; i < ";
  protected final String TEXT_278 = "; i++) {" + NL + "\t\t\t\t\torder.add(i);" + NL + "\t\t\t\t}" + NL + "\t\t\t\tCollections.shuffle(order);" + NL + "\t\t\t\tunitOrders.push(order);" + NL + "\t\t\t}" + NL + "\t\t\tif (microstep > 0 && unitSuccesses.pop()) {" + NL + "\t\t\t\tunitOrders.pop();" + NL + "\t\t\t\tunitSuccesses.push(true);" + NL + "\t\t\t} else if (microstep == ";
  protected final String TEXT_279 = ") {" + NL + "\t\t\t\tunitOrders.pop();" + NL + "\t\t\t\tunitSuccesses.push(false);" + NL + "\t\t\t} else {" + NL + "\t\t\t\tint next = unitOrders.peek().get(microstep);" + NL + "\t\t\t\tswitch (next) {";
  protected final String TEXT_280 = NL + "\t\t\t\tcase ";
  protected final String TEXT_281 = ":" + NL + "\t\t\t\t\tstack = stack.append(";
  protected final String TEXT_282 = ", 0, microstep + 1);" + NL + "\t\t\t\t\tstack = stack.append(";
  protected final String TEXT_283 = ", 0, 0);" + NL + "\t\t\t\t\tbreak;";
  protected final String TEXT_284 = NL + "\t\t\t\tdefault:" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_285 = NL + "\t\t\tif (microstep == 0 || unitSuccesses.pop()) {" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_286 = ", 0, 1);" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_287 = ", 0, 0);" + NL + "\t\t\t} else {" + NL + "\t\t\t\tunitSuccesses.push(true);" + NL + "\t\t\t}";
  protected final String TEXT_288 = NL + "\t\t\tif (microstep < ";
  protected final String TEXT_289 = " + (STAIR_COUNT - 1)) {" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_290 = ", segment, microstep + 1);" + NL + "\t\t\t} else if (segment < SEGMENT_COUNT - 1) {" + NL + "\t\t\t\tstack = stack.append(";
  protected final String TEXT_291 = ", segment + 1, 0);" + NL + "\t\t\t} else {" + NL + "\t\t\t\tunitSuccesses.push(ruleApps > 0);" + NL + "\t\t\t}";
  protected final String TEXT_292 = NL + "\t\t\treturn stack;" + NL + "\t\t}";
  protected final String TEXT_293 = NL + NL + "\t\t/*" + NL + "\t\t * (non-Javadoc)" + NL + "\t\t * @see org.apache.giraph.master.DefaultMasterCompute#initialize()" + NL + "\t\t */" + NL + "\t\t@Override" + NL + "\t\tpublic void initialize() throws InstantiationException, IllegalAccessException {" + NL + "\t\t\tregisterAggregator(AGGREGATOR_MATCHES, LongSumAggregator.class);" + NL + "\t\t\tregisterAggregator(AGGREGATOR_RULE_APPLICATIONS, LongSumAggregator.class);" + NL + "\t\t\tregisterPersistentAggregator(AGGREGATOR_NODE_GENERATION, LongSumAggregator.class);" + NL + "\t\t\tregisterPersistentAggregator(AGGREGATOR_APPLICATION_STACK, ApplicationStackAggregator.class);" + NL + "\t\t}" + NL + "" + NL + "\t}" + NL + "}";

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
int stairCount = (Integer) args.get("stairCount");

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
    if (masterLogging || vertexLogging) { 
    stringBuffer.append(TEXT_7);
    } 
    stringBuffer.append(TEXT_8);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( mainUnit.getName() );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_15);
    

Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(mainUnit.getModule());
int value = 0;
for (ENamedElement type : typeConstants.keySet()) {
	
    stringBuffer.append(TEXT_16);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( typeConstants.get(type) );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_19);
    
}

Map<Unit,String> unitConstants = GiraphUtil.getUnitConstants(mainUnit);
value = 0;
for (Unit unit : unitConstants.keySet()) {
	
    stringBuffer.append(TEXT_20);
    stringBuffer.append( (unit instanceof Rule) ? "Rule" : "Unit" );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_24);
    
}

if (masterLogging || vertexLogging) {

    stringBuffer.append(TEXT_25);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_26);
     } 
    stringBuffer.append(TEXT_27);
    stringBuffer.append( segmentCount );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( stairCount );
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

	// Sort indexes of nodes to be removed from the match:
	List<Integer> required = new ArrayList<Integer>();
	for (Node node : data.requiredNodes) {
		required.add(data.orderedLhsNodes.indexOf(node));
	}
	Collections.sort(required);
	Collections.reverse(required);


    stringBuffer.append(TEXT_34);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( data.matchingSteps.size() );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_37);
    	if (vertexLogging) { /* START LOGGING */
    stringBuffer.append(TEXT_38);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_39);
     	} /* END LOGGING */
    stringBuffer.append(TEXT_40);
    stringBuffer.append( data.matchingSteps.size() > 1 ? "matches = " : "" );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_42);
    /* TODO - Possibly add a set of these for each join step */
    	boolean hasJoins = false;
	for (int i = 0 ; i < data.matchingSteps.size() ; i++) {
		GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);
		if (step.keepMatchesOf != null || step.isJoin) {
		hasJoins = true;
    stringBuffer.append(TEXT_43);
    			break;
		}
	}
    stringBuffer.append(TEXT_44);
     	ArrayList<Integer> moreStarts = new ArrayList<Integer>();
	for (int i = 1 ; i < data.matchingSteps.size() ; i++) {
		if (data.matchingSteps.get(i).isStart){
			moreStarts.add(i);
		}
	}
	for (int i = 0; i < data.matchingSteps.size() ; i++ ) {/* START STEP LOOP */
		GiraphRuleData.MatchingStep step = data.matchingSteps.get(i);
		if ( i == 0 ) { 
			for (Integer start : moreStarts) {
    stringBuffer.append(TEXT_45);
    stringBuffer.append(start - 1);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(start);
    stringBuffer.append(TEXT_47);
    			}
    stringBuffer.append(TEXT_48);
    stringBuffer.append( data.matchingSteps.size() == 1 ? "" : "&& getPart(vertex.getId()) == microstep");
    stringBuffer.append(TEXT_49);
    		} else if ( i == 1 ) { 
    stringBuffer.append(TEXT_50);
    	  	}
		if ( i > 0) { 
    stringBuffer.append(TEXT_51);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_52);
    		}
		if (step.isJoin) {/* START JOIN */
    stringBuffer.append(TEXT_53);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_54);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.node) );
    stringBuffer.append(TEXT_56);
    			if (vertexLogging) { 
    stringBuffer.append(TEXT_57);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_58);
    			} 
    stringBuffer.append(TEXT_59);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(i == 0 ? "Match " : "");
    stringBuffer.append(TEXT_61);
    			if (rule.isInjectiveMatching()) {
    stringBuffer.append(TEXT_62);
    			}
			if (step.sendBackTo != null) {/* START SEND BACK TO */
    stringBuffer.append(TEXT_63);
    				if (vertexLogging) { 
    stringBuffer.append(TEXT_64);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_65);
    				} 
    stringBuffer.append(TEXT_66);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_67);
    				/* END SEND BACK TO */
			} else if (i == data.matchingSteps.size() - 1) {
				for (Integer req : required) { 
    stringBuffer.append(TEXT_68);
    stringBuffer.append( req );
    stringBuffer.append(TEXT_69);
     				} 
    stringBuffer.append(TEXT_70);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_71);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_72);
    			} 
    stringBuffer.append(TEXT_73);
    		} else { /* END JOIN */
			if (step.keepMatchesOf != null) {
    stringBuffer.append(TEXT_74);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.keepMatchesOf) );
    stringBuffer.append(TEXT_75);
    				if (vertexLogging) { 
    stringBuffer.append(TEXT_76);
    				} 
    stringBuffer.append(TEXT_77);
    			}
			/* START CHECKING */				
			String xx = "";
			if (step.isMatching) {
				/* START MATCHING */
				xx = "\t";
				List<EClass> validTypes = GiraphUtil.getValidTypes(step.node, mainUnit.getModule()); 
    stringBuffer.append(TEXT_78);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_79);
    				for ( int j = 0 ; j < validTypes.size() ; j++) { 
    stringBuffer.append(TEXT_80);
    stringBuffer.append( (j==0) ? "ok = " : "" );
    stringBuffer.append(TEXT_81);
    stringBuffer.append( typeConstants.get(validTypes.get(j)) );
    stringBuffer.append( (j==validTypes.size()-1) ? ";" : " ||" );
    				}
				if (rule.isInjectiveMatching() && !step.node.getOutgoing().isEmpty()) { 
    stringBuffer.append(TEXT_82);
    stringBuffer.append( step.node.getOutgoing().size() );
    stringBuffer.append(TEXT_83);
    				} 
				if (i==0) { 
    stringBuffer.append(TEXT_84);
    				} 
    stringBuffer.append(TEXT_85);
    			/* END MATCHING */
			}
			if (step.isStart) {/* START IS START */				
				if (i > 0) { 
    stringBuffer.append(TEXT_86);
    				} 
    stringBuffer.append(TEXT_87);
    stringBuffer.append( i == 0 ? "Match " : "");
    stringBuffer.append(TEXT_88);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_89);
    					/* END IS START */				
			} else {
					/* START IS NOT START*/				
				if (step.isMatching) { 
    stringBuffer.append(TEXT_90);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_91);
    					if (rule.isInjectiveMatching()) {
    stringBuffer.append(TEXT_92);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_97);
    					}
					NodeEquivalence equi = data.requiredNodesEquivalences.get(step.node);
					if (equi!=null && equi.indexOf(step.node)>0) { 
							Node compareTo = equi.get(equi.indexOf(step.node)-1); 
    stringBuffer.append(TEXT_98);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_99);
    stringBuffer.append( data.orderedLhsNodes.indexOf(compareTo) );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_104);
    					}
				} 
					/* END IS NOT START*/
			}
			if (step.edge != null) {
				/* START EDGE */
				if (step.verifyEdgeTo != null) {
					/* START VERIFY EDGE */
					xx = xx + "\t\t"; 
    stringBuffer.append(TEXT_105);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_106);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_107);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_108);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_109);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_111);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.verifyEdgeTo) );
    stringBuffer.append(TEXT_112);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_116);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_117);
    				}/* END VERIFY EDGE */
			} /* END EDGE */
			if (step.sendBackTo != null) {
					/* START SEND BACK TO */
    stringBuffer.append(TEXT_118);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_121);
    				if (vertexLogging) { 
    stringBuffer.append(TEXT_122);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_127);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_128);
    				} 
    stringBuffer.append(TEXT_129);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_130);
    stringBuffer.append( data.orderedLhsNodes.indexOf(step.sendBackTo) );
    stringBuffer.append(TEXT_131);
    				/* END SEND BACK TO */
			} else if (i == data.matchingSteps.size()-1) {
				/* START LAST STEP */ 
				if (step.isStart) {
					xx = "";
				} 
    				for (Integer req : required) { 
    stringBuffer.append(TEXT_132);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_133);
    stringBuffer.append( req );
    stringBuffer.append(TEXT_134);
    				} 
    stringBuffer.append(TEXT_135);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_145);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_146);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_154);
    				/* END LAST STEP */				
			}
			if (step.verifyEdgeTo != null) {
				/* START VERIFY EDGE */
    stringBuffer.append(TEXT_155);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(xx);
    stringBuffer.append(TEXT_160);
    
				xx = xx.substring(0, xx.length() - 2);
				/* END VERIFY EDGE */
			}
			if (step.edge!=null && step.verifyEdgeTo==null) {
				/* START NOT VERIFY EDGE */
				String yy = !step.isStart && step.isMatching ? "	" : ""; 
    stringBuffer.append(TEXT_161);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_170);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_171);
    					if (vertexLogging) { 
    stringBuffer.append(TEXT_172);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_177);
    					} 
    stringBuffer.append(TEXT_178);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(yy);
    stringBuffer.append(TEXT_183);
    			}/* END NOT VERIFY EDGE */	
			if (step.isMatching) { 
    stringBuffer.append(TEXT_184);
    			}
		} 
    stringBuffer.append(TEXT_185);
    stringBuffer.append( i == 0 ? "		}" : "				break;" );
    	} // /* END STEP LOOP */
if (data.matchingSteps.size() > 1) {  /*Closing switch and for*/ 
	if (stairCount > 1) {
    stringBuffer.append(TEXT_186);
    stringBuffer.append(data.matchingSteps.size() - 1);
    stringBuffer.append(TEXT_187);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_188);
    	}
    stringBuffer.append(TEXT_189);
    	if (hasJoins) { 
    stringBuffer.append(TEXT_190);
    	}
    stringBuffer.append(TEXT_191);
    }
    stringBuffer.append(TEXT_192);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_193);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_194);
    stringBuffer.append( data.matchingSteps.size()-1 );
    stringBuffer.append(TEXT_195);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_196);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_197);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_198);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_199);
    
	List<Node> matchNodes = new ArrayList<Node>();
	matchNodes.addAll(data.orderedLhsNodes);
	matchNodes.removeAll(data.requiredNodes);
	for (int j = 0; j < matchNodes.size(); j++) {
		Node lhsNode = matchNodes.get(j);
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
    stringBuffer.append(TEXT_200);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_201);
    stringBuffer.append( j );
    stringBuffer.append(TEXT_202);
    	}
	}
	if (vertexLogging) { 
    stringBuffer.append(TEXT_203);
    stringBuffer.append( data.rule.getName() );
    stringBuffer.append(TEXT_204);
     } 
    
	for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_205);
    stringBuffer.append( matchNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_206);
    stringBuffer.append( matchNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_207);
    	}
		for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_208);
    stringBuffer.append( matchNodes.indexOf(node) );
    stringBuffer.append(TEXT_209);
    	}

		int n = 0;
		for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_210);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_211);
     if (useUUIDs) { 
    stringBuffer.append(TEXT_212);
     } else { 
    stringBuffer.append(TEXT_213);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_214);
     } 
    stringBuffer.append(TEXT_215);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_216);
    stringBuffer.append( typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_217);
    	}

		int e = 0;
		for (Edge edge : changeInfo.getCreatedEdges()) { 
    	// THE SOURCE OF THE NEW EDGE:
	 	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_218);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_219);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_220);
    	} else { 
    stringBuffer.append(TEXT_221);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_222);
    stringBuffer.append( matchNodes.indexOf(data.rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_223);
    	}
	// THE TARGET OF THE NEW EDGE:
	 	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_224);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_225);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_226);
    	} else { 
    stringBuffer.append(TEXT_227);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_228);
    stringBuffer.append( matchNodes.indexOf(data.rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_229);
    	} 
    stringBuffer.append(TEXT_230);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_231);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_232);
    stringBuffer.append( typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_233);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_234);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_235);
    	e++;
		} 
    stringBuffer.append(TEXT_236);
    
} // end of for all rules

    stringBuffer.append(TEXT_237);
     if (needsVertexIdFactory && !useUUIDs) { 
    stringBuffer.append(TEXT_238);
     } 
    stringBuffer.append(TEXT_239);
     if (masterLogging) { 
    stringBuffer.append(TEXT_240);
     } 
    stringBuffer.append(TEXT_241);
    stringBuffer.append( unitConstants.get(mainUnit) );
    stringBuffer.append(TEXT_242);
     if (!(mainUnit instanceof Rule)) { 
    stringBuffer.append(TEXT_243);
     } 
    stringBuffer.append(TEXT_244);
     for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_245);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_246);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_247);
    stringBuffer.append( (unit instanceof Rule) ? ", segment" : "" );
    stringBuffer.append(TEXT_248);
    stringBuffer.append( (unit instanceof Rule) ? ", ruleApps" : "" );
    stringBuffer.append(TEXT_249);
     } // end for 
    stringBuffer.append(TEXT_250);
     for (int i=0; i<rules.size(); i++) { 
    stringBuffer.append(TEXT_251);
    stringBuffer.append( i==0 ? "if (" : "	" );
    stringBuffer.append(TEXT_252);
    stringBuffer.append( unitConstants.get(rules.get(i)) + (i<rules.size()-1 ? " ||" : ") {" ) );
     } 
    stringBuffer.append(TEXT_253);
    

for (Unit unit : allUnits) { 
    stringBuffer.append(TEXT_254);
    stringBuffer.append( unit.eClass().getName() );
    stringBuffer.append(TEXT_255);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_256);
     if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_257);
     } 
    stringBuffer.append(TEXT_258);
     if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_259);
     } 
    stringBuffer.append(TEXT_260);
    stringBuffer.append( unit.getName() );
    stringBuffer.append(TEXT_261);
    stringBuffer.append( (unit instanceof Rule) ? ", int segment" : "" );
    stringBuffer.append(TEXT_262);
    stringBuffer.append( (unit instanceof Rule) ? ", long ruleApps" : "" );
    stringBuffer.append(TEXT_263);
     if (unit instanceof IteratedUnit) {
	 int iters = Integer.parseInt(((IteratedUnit) unit).getIterations()); 
    stringBuffer.append(TEXT_264);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_265);
    stringBuffer.append( iters );
    stringBuffer.append(TEXT_266);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_267);
    stringBuffer.append( unitConstants.get(((IteratedUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_268);
     } else if (unit instanceof SequentialUnit) {
		 SequentialUnit seq = (SequentialUnit) unit; 
    stringBuffer.append(TEXT_269);
    stringBuffer.append( seq.getSubUnits().size() );
    stringBuffer.append(TEXT_270);
     for (int i=0; i<seq.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_271);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_272);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_273);
    stringBuffer.append( i+1 );
    stringBuffer.append(TEXT_274);
    stringBuffer.append( unitConstants.get(seq.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_275);
     } 
    stringBuffer.append(TEXT_276);
     } else if (unit instanceof IndependentUnit) { 
		 IndependentUnit indi = (IndependentUnit) unit; 
    stringBuffer.append(TEXT_277);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_278);
    stringBuffer.append( indi.getSubUnits().size() );
    stringBuffer.append(TEXT_279);
     for (int i=0; i<indi.getSubUnits().size(); i++) { 
    stringBuffer.append(TEXT_280);
    stringBuffer.append( i);
    stringBuffer.append(TEXT_281);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_282);
    stringBuffer.append( unitConstants.get(indi.getSubUnits().get(i)) );
    stringBuffer.append(TEXT_283);
     } 
    stringBuffer.append(TEXT_284);
     } else if (unit instanceof LoopUnit) { 
    stringBuffer.append(TEXT_285);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_286);
    stringBuffer.append( unitConstants.get(((LoopUnit) unit).getSubUnit()) );
    stringBuffer.append(TEXT_287);
     } else if (unit instanceof Rule) { 
    stringBuffer.append(TEXT_288);
    stringBuffer.append( ruleData.get(unit).matchingSteps.size()-1 );
    stringBuffer.append(TEXT_289);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_290);
    stringBuffer.append( unitConstants.get(unit) );
    stringBuffer.append(TEXT_291);
     } 
    stringBuffer.append(TEXT_292);
     } // end for 
    stringBuffer.append(TEXT_293);
    return stringBuffer.toString();
  }
}
