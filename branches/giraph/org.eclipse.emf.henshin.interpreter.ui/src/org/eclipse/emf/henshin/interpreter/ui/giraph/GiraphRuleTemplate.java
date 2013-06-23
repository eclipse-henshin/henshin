package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.*;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.interpreter.info.*;
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
  protected final String TEXT_1 = "/*" + NL + " * Licensed to the Apache Software Foundation (ASF) under one" + NL + " * or more contributor license agreements.  See the NOTICE file" + NL + " * distributed with this work for additional information" + NL + " * regarding copyright ownership.  The ASF licenses this file" + NL + " * to you under the Apache License, Version 2.0 (the" + NL + " * \"License\"); you may not use this file except in compliance" + NL + " * with the License.  You may obtain a copy of the License at" + NL + " *" + NL + " *     http://www.apache.org/licenses/LICENSE-2.0" + NL + " *" + NL + " * Unless required by applicable law or agreed to in writing, software" + NL + " * distributed under the License is distributed on an \"AS IS\" BASIS," + NL + " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." + NL + " * See the License for the specific language governing permissions and" + NL + " * limitations under the License." + NL + " */" + NL + "package org.apache.giraph.examples;" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.Arrays;" + NL + "import java.util.Iterator;" + NL + "import java.util.List;" + NL + "import java.util.ArrayList;" + NL + "" + NL + "import org.apache.giraph.edge.Edge;" + NL + "import org.apache.giraph.edge.EdgeFactory;" + NL + "import org.apache.giraph.graph.BasicComputation;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.io.formats.TextVertexInputFormat;" + NL + "import org.apache.giraph.io.formats.TextVertexOutputFormat;" + NL + "import org.apache.hadoop.io.BytesWritable;" + NL + "import org.apache.hadoop.io.IntWritable;" + NL + "import org.apache.hadoop.io.Text;" + NL + "import org.apache.hadoop.mapreduce.InputSplit;" + NL + "import org.apache.hadoop.mapreduce.TaskAttemptContext;" + NL + "import org.apache.log4j.Logger;" + NL + "import org.json.JSONArray;" + NL + "import org.json.JSONException;" + NL + "" + NL + "import com.google.common.collect.Lists;" + NL + "" + NL + "/**" + NL + " * Generated implementation of the Henshin rule \"";
  protected final String TEXT_2 = "\"." + NL + " */" + NL + "@Algorithm(" + NL + "    name = \"";
  protected final String TEXT_3 = "\"" + NL + ")" + NL + "public class ";
  protected final String TEXT_4 = " extends" + NL + "  BasicComputation<BytesWritable, IntWritable, IntWritable, BytesWritable> {";
  protected final String TEXT_5 = NL + NL + "  /**" + NL + "   * Type constant for \"";
  protected final String TEXT_6 = "\"." + NL + "   */" + NL + "  public static final int ";
  protected final String TEXT_7 = " = ";
  protected final String TEXT_8 = ";";
  protected final String TEXT_9 = NL + NL + "  /**" + NL + "   * Logging support." + NL + "   */" + NL + "  private static final Logger LOG = Logger.getLogger(";
  protected final String TEXT_10 = ".class);" + NL + "" + NL + "  /**" + NL + "   * Empty match." + NL + "   */" + NL + "  private static final byte[] EMPTY_MATCH = new byte[] { 0 };" + NL + "" + NL + "  /*" + NL + "   * (non-Javadoc)" + NL + "   * @see org.apache.giraph.graph.Computation#compute(" + NL + "   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)" + NL + "   */" + NL + "  @Override" + NL + "  public void compute(" + NL + "      Vertex<BytesWritable, IntWritable, IntWritable> vertex," + NL + "      Iterable<BytesWritable> matches) throws IOException {" + NL + "" + NL + "    long superstep = getSuperstep();" + NL + "" + NL + "    // Log partial matches:" + NL + "    LOG.info(\"Superstep \" + superstep + \": current partial matches are...\");" + NL + "    for (BytesWritable match : matches) {" + NL + "      LOG.info(\"  \" + matchToString(match));" + NL + "    }" + NL + NL;
  protected final String TEXT_11 = " if (superstep == ";
  protected final String TEXT_12 = ") {" + NL;
  protected final String TEXT_13 = NL + "      // Node ";
  protected final String TEXT_14 = ": check for edge to match of ";
  protected final String TEXT_15 = " of type \"";
  protected final String TEXT_16 = "\":" + NL + "      List<BytesWritable> validMatches = new ArrayList<BytesWritable>();" + NL + "      Iterator<BytesWritable> it = matches.iterator();" + NL + "      while (it.hasNext()) {" + NL + "        BytesWritable match = it.next();" + NL + "        BytesWritable targetId = getMatchVertexId(match, ";
  protected final String TEXT_17 = ");" + NL + "        for (Edge<BytesWritable, IntWritable> edge : vertex.getEdges()) {" + NL + "          if (edge.getValue().get() == ";
  protected final String TEXT_18 = " &&" + NL + "              edge.getTargetVertexId().equals(targetId)) {" + NL + "            validMatches.add(match);" + NL + "            break;" + NL + "          }" + NL + "        }" + NL + "      }" + NL + "      matches = validMatches;" + NL;
  protected final String TEXT_19 = NL + "      // Matching node ";
  protected final String TEXT_20 = ". Type must be \"";
  protected final String TEXT_21 = "\":" + NL + "      boolean ok = vertex.getValue().get() == ";
  protected final String TEXT_22 = ";" + NL + "      if (ok) {";
  protected final String TEXT_23 = NL + "        // Create a new partial match:" + NL + "        BytesWritable match = addMatchVertex(null, vertex.getId());";
  protected final String TEXT_24 = NL + "        // Extend all partial matches:" + NL + "        for (BytesWritable match : matches) {" + NL + "          match = addMatchVertex(match, vertex.getId());";
  protected final String TEXT_25 = NL;
  protected final String TEXT_26 = "        // Send a match request to all outgoing edges of type \"";
  protected final String TEXT_27 = "\":";
  protected final String TEXT_28 = NL;
  protected final String TEXT_29 = "        for (Edge<BytesWritable, IntWritable> edge : vertex.getEdges()) {";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = "          if (edge.getValue().get() == ";
  protected final String TEXT_32 = ") {";
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = "            sendMessage(edge.getTargetVertexId(), match);";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = "          }";
  protected final String TEXT_37 = NL;
  protected final String TEXT_38 = "        }";
  protected final String TEXT_39 = NL + "          // Send the message back to matches of node ";
  protected final String TEXT_40 = ":" + NL + "          for (BytesWritable m : matches) {" + NL + "            sendMessage(getMatchVertexId(m, ";
  protected final String TEXT_41 = "), match);" + NL + "          }";
  protected final String TEXT_42 = NL + "        }";
  protected final String TEXT_43 = NL + "      } // end if ok" + NL;
  protected final String TEXT_44 = NL + NL + "      // Apply rule for all matches:" + NL + "      for (BytesWritable match : matches) {" + NL + "        applyRule(vertex, match);" + NL + "      }";
  protected final String TEXT_45 = NL + "    }";
  protected final String TEXT_46 = NL + "    // Next nodes are activated by a message, so we can put this one to sleep:" + NL + "    vertex.voteToHalt();" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Pretty-print a match." + NL + "   * @param match The Match to be printed." + NL + "   * @return The printed string." + NL + "   */" + NL + "  private static String matchToString(BytesWritable match) {" + NL + "    byte[] bytes = (match != null) ? match.getBytes() : EMPTY_MATCH;" + NL + "    StringBuffer r = new StringBuffer();" + NL + "    int d = 1;    " + NL + "    for (int i = 0; i < bytes[0]; i++) {" + NL + "      r.append(\"[\");" + NL + "      for (int j = 1; j <= bytes[d]; j++) {" + NL + "        r.append(bytes[d + j]);" + NL + "        if (j < bytes[d]) {" + NL + "          r.append(\",\");" + NL + "        }" + NL + "      }" + NL + "      r.append(\"]\");" + NL + "      if (i < bytes[0] - 1) {" + NL + "        r.append(\",\");" + NL + "      }" + NL + "    }" + NL + "    return \"[\" + r.toString() + \"]\";" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Get the vertex ID of a matched node." + NL + "   * @param match The match oject." + NL + "   * @param vertexIndex Index of the next vertex." + NL + "   * @return The vertex ID." + NL + "   */" + NL + "  private static BytesWritable getMatchVertexId(BytesWritable match," + NL + "    int vertexIndex) {" + NL + "    byte[] bytes = match.getBytes();" + NL + "    int d = 1;" + NL + "    for (int i = 0; i < vertexIndex; i++) {" + NL + "      d += bytes[d] + 1;" + NL + "    }" + NL + "    return new BytesWritable(" + NL + "      Arrays.copyOfRange(bytes, d + 1, d + 1 + bytes[d]));" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Extend a partial match." + NL + "   * @param match The match object." + NL + "   * @param vertexId The ID of the next matched vertex." + NL + "   * @return The extended match object." + NL + "   */" + NL + "  private BytesWritable addMatchVertex(BytesWritable match," + NL + "    BytesWritable vertexId) {" + NL + "    byte[] bytes = (match != null) ? match.getBytes() : EMPTY_MATCH;" + NL + "    byte[] id = vertexId.getBytes();" + NL + "    int d = 1;" + NL + "    for (byte i = 0; i < bytes[0]; i++) {" + NL + "      d += bytes[d] + 1;" + NL + "    }" + NL + "    byte[] result = Arrays.copyOf(bytes, d + 1 + id.length);" + NL + "    result[0]++;" + NL + "    result[d] = (byte) id.length;" + NL + "    System.arraycopy(id, 0, result, d + 1, id.length);" + NL + "    BytesWritable theResult = new BytesWritable(result);" + NL + "    LOG.info(\"Extending match: \" + matchToString(match) +" + NL + "      \" -> \" + matchToString(theResult));" + NL + "    return theResult;" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Apply the rule to a given match." + NL + "   * @param vertex The base vertex." + NL + "   * @param match The match object." + NL + "   * @throws IOException On I/O errors." + NL + "   */" + NL + "  private void applyRule(Vertex<BytesWritable, IntWritable," + NL + "    IntWritable> vertex, BytesWritable match) throws IOException {" + NL + "" + NL + "    LOG.info(\"Applying rule ";
  protected final String TEXT_47 = " with match \" + matchToString(match));";
  protected final String TEXT_48 = NL + NL + "    // Remove edge ";
  protected final String TEXT_49 = " -> ";
  protected final String TEXT_50 = ":" + NL + "    removeEdgesRequest(" + NL + "      getMatchVertexId(match, ";
  protected final String TEXT_51 = ")," + NL + "      getMatchVertexId(match, ";
  protected final String TEXT_52 = ")" + NL + "    );";
  protected final String TEXT_53 = NL + NL + "    // Remove vertex ";
  protected final String TEXT_54 = ":" + NL + "    removeVertexRequest(" + NL + "      getMatchVertexId(match, ";
  protected final String TEXT_55 = ")" + NL + "    );";
  protected final String TEXT_56 = NL + "    byte[] thisVertexId = vertex.getId().getBytes();" + NL + "    byte[] newVertexId;";
  protected final String TEXT_57 = NL + NL + "    // Create vertex ";
  protected final String TEXT_58 = ":" + NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_59 = ";" + NL + "    addVertexRequest(new BytesWritable(newVertexId)," + NL + "      new IntWritable(";
  protected final String TEXT_60 = "));";
  protected final String TEXT_61 = NL + NL + "    // Create edge ";
  protected final String TEXT_62 = " -> ";
  protected final String TEXT_63 = ":";
  protected final String TEXT_64 = NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_65 = ";" + NL + "    BytesWritable src";
  protected final String TEXT_66 = " = new BytesWritable(newVertexId);";
  protected final String TEXT_67 = NL + "    BytesWritable src";
  protected final String TEXT_68 = " = getMatchVertexId(match, ";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = NL + "    newVertexId = Arrays.copyOf(thisVertexId, thisVertexId.length + 1);" + NL + "    newVertexId[newVertexId.length - 1] = ";
  protected final String TEXT_71 = ";" + NL + "    BytesWritable trg";
  protected final String TEXT_72 = " = new BytesWritable(newVertexId);";
  protected final String TEXT_73 = NL + "    BytesWritable trg";
  protected final String TEXT_74 = " = getMatchVertexId(match, ";
  protected final String TEXT_75 = ");";
  protected final String TEXT_76 = NL + "    Edge<BytesWritable, IntWritable> edge";
  protected final String TEXT_77 = " =" + NL + "      EdgeFactory.create(trg";
  protected final String TEXT_78 = ", new IntWritable(";
  protected final String TEXT_79 = "));" + NL + "    addEdgeRequest(src";
  protected final String TEXT_80 = ", edge";
  protected final String TEXT_81 = ");";
  protected final String TEXT_82 = NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin input format." + NL + "   */" + NL + "  public static class HenshinInputFormat extends" + NL + "    TextVertexInputFormat<BytesWritable, IntWritable, IntWritable> {" + NL + "" + NL + "    @Override" + NL + "    public TextVertexReader createVertexReader(InputSplit split," + NL + "      TaskAttemptContext context) {" + NL + "      return new HenshinInputReader();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Henshin input reader." + NL + "     */" + NL + "    class HenshinInputReader extends" + NL + "      TextVertexReaderFromEachLineProcessedHandlingExceptions<JSONArray," + NL + "        JSONException> {" + NL + "" + NL + "      @Override" + NL + "      protected JSONArray preprocessLine(Text line) throws JSONException {" + NL + "        return new JSONArray(line.toString());" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected BytesWritable getId(JSONArray jsonVertex)" + NL + "        throws JSONException, IOException {" + NL + "        return jsonArrayToBytesWritable(jsonVertex.getJSONArray(0));" + NL + "      }" + NL + "" + NL + "      /**" + NL + "       * Convert a JSON array to a BytesWritable object." + NL + "       * @param jsonArray The JSON array to be converted." + NL + "       * @return The corresponding BytesWritable." + NL + "       */" + NL + "      private BytesWritable jsonArrayToBytesWritable(JSONArray jsonArray)" + NL + "        throws JSONException {" + NL + "        byte[] bytes = new byte[jsonArray.length()];" + NL + "        for (int i = 0; i < bytes.length; i++) {" + NL + "          bytes[i] = (byte) jsonArray.getInt(i);" + NL + "        }" + NL + "        return new BytesWritable(bytes);" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected IntWritable getValue(JSONArray jsonVertex)" + NL + "        throws JSONException, IOException {" + NL + "        return new IntWritable(jsonVertex.getInt(1));" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected Iterable<Edge<BytesWritable, IntWritable>> getEdges(" + NL + "        JSONArray jsonVertex) throws JSONException, IOException {" + NL + "        JSONArray jsonEdgeArray = jsonVertex.getJSONArray(2);" + NL + "        List<Edge<BytesWritable, IntWritable>> edges =" + NL + "          Lists.newArrayListWithCapacity(jsonEdgeArray.length());" + NL + "        for (int i = 0; i < jsonEdgeArray.length(); ++i) {" + NL + "          JSONArray jsonEdge = jsonEdgeArray.getJSONArray(i);" + NL + "          edges.add(EdgeFactory.create(jsonArrayToBytesWritable(" + NL + "            jsonEdge.getJSONArray(0)), new IntWritable(jsonEdge.getInt(1))));" + NL + "        }" + NL + "        return edges;" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected Vertex<BytesWritable, IntWritable, IntWritable>" + NL + "      handleException(Text line, JSONArray jsonVertex, JSONException e) {" + NL + "        throw new IllegalArgumentException(" + NL + "          \"Couldn't get vertex from line \" + line, e);" + NL + "      }" + NL + "    }" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin output format." + NL + "   */" + NL + "  public static class HenshinOutputFormat extends" + NL + "    TextVertexOutputFormat<BytesWritable, IntWritable, IntWritable> {" + NL + "" + NL + "    @Override" + NL + "    public TextVertexWriter createVertexWriter(TaskAttemptContext context)" + NL + "      throws IOException, InterruptedException {" + NL + "      return new HenshinOutputWriter();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Henshin output writer." + NL + "     */" + NL + "    class HenshinOutputWriter extends TextVertexWriterToEachLine {" + NL + "" + NL + "      @Override" + NL + "      protected Text convertVertexToLine(" + NL + "        Vertex<BytesWritable, IntWritable, IntWritable> vertex)" + NL + "        throws IOException {" + NL + "" + NL + "        JSONArray vertexArray = new JSONArray();" + NL + "        JSONArray idArray = new JSONArray();" + NL + "        byte[] id = vertex.getId().getBytes();" + NL + "        for (int i = 0; i < id.length; i++) {" + NL + "          idArray.put(id[i]);" + NL + "        }" + NL + "        vertexArray.put(idArray);" + NL + "        vertexArray.put(vertex.getValue().get());" + NL + "        JSONArray allEdgesArray = new JSONArray();" + NL + "        for (Edge<BytesWritable, IntWritable> edge : vertex.getEdges()) {" + NL + "          JSONArray edgeArray = new JSONArray();" + NL + "          JSONArray targetIdArray = new JSONArray();" + NL + "          byte[] targetId = edge.getTargetVertexId().getBytes();" + NL + "          for (int i = 0; i < targetId.length; i++) {" + NL + "            targetIdArray.put(targetId[i]);" + NL + "          }" + NL + "          edgeArray.put(targetIdArray);" + NL + "          edgeArray.put(edge.getValue().get());" + NL + "          allEdgesArray.put(edgeArray);" + NL + "        }" + NL + "        vertexArray.put(allEdgesArray);" + NL + "        return new Text(vertexArray.toString());" + NL + "      }" + NL + "    }" + NL + "  }" + NL + "}";
  protected final String TEXT_83 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

Rule rule = (Rule) args.get("rule");
RuleChangeInfo changeInfo = new RuleChangeInfo(rule);

String className = (String) args.get("className");

Map<ENamedElement,String> typeConstants = GiraphUtil.getTypeConstants(rule.getModule());
List<MatchingStep> matchingSteps = GiraphUtil.getMatchingSteps(rule);

List<Node> orderedLhsNodes = new ArrayList<Node>();
for (MatchingStep step : matchingSteps) {
  if (step.node!=null && !orderedLhsNodes.contains(step.node)) {
    orderedLhsNodes.add(step.node);
  }
}


    stringBuffer.append(TEXT_1);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( rule.getName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_4);
    
int value = 0;
for (ENamedElement type : typeConstants.keySet()) {
  
    stringBuffer.append(TEXT_5);
    stringBuffer.append( type.getName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( typeConstants.get(type) );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( value++ );
    stringBuffer.append(TEXT_8);
    
}

    stringBuffer.append(TEXT_9);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_10);
     
    for (int i=0; i<matchingSteps.size(); i++) {
      MatchingStep step = matchingSteps.get(i);

    stringBuffer.append( i>0 ? " else" : "   " );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( i );
    stringBuffer.append(TEXT_12);
        if (step.verifyEdgeTo >= 0) {
    stringBuffer.append(TEXT_13);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getSource()) );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( GiraphUtil.getNodeName(step.edge.getTarget()) );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( step.verifyEdgeTo );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_18);
        } else {
    stringBuffer.append(TEXT_19);
    stringBuffer.append( GiraphUtil.getNodeName(step.node) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( step.node.getType().getName() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( typeConstants.get(step.node.getType()) );
    stringBuffer.append(TEXT_22);
        if (i == 0) {
    stringBuffer.append(TEXT_23);
     } else {
    stringBuffer.append(TEXT_24);
        }
      if (step.edge != null) {
    stringBuffer.append(TEXT_25);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_26);
    stringBuffer.append( step.edge.getType().getName() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(TEXT_28);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_31);
    stringBuffer.append( typeConstants.get(step.edge.getType()) );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_36);
    stringBuffer.append(TEXT_37);
    stringBuffer.append( i > 0 ? "  " : "");
    stringBuffer.append(TEXT_38);
        } else if (step.sendBackTo >= 0) {
    stringBuffer.append(TEXT_39);
    stringBuffer.append( GiraphUtil.getNodeName(matchingSteps.get(step.sendBackTo).node) );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( step.sendBackTo );
    stringBuffer.append(TEXT_41);
        }
      if (i>0) {
    stringBuffer.append(TEXT_42);
    
       }
    stringBuffer.append(TEXT_43);
        }
      if (i == matchingSteps.size()-1) {
    stringBuffer.append(TEXT_44);
    
      }
    stringBuffer.append(TEXT_45);
     
    } // end for

    stringBuffer.append(TEXT_46);
    stringBuffer.append( className );
    stringBuffer.append(TEXT_47);
      for (Edge edge : changeInfo.getDeletedEdges()) {
    stringBuffer.append(TEXT_48);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( orderedLhsNodes.indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( orderedLhsNodes.indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_52);
      }
    for (Node node : changeInfo.getDeletedNodes()) {
    stringBuffer.append(TEXT_53);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( orderedLhsNodes.indexOf(node) );
    stringBuffer.append(TEXT_55);
      } 

    if (!changeInfo.getCreatedNodes().isEmpty()) {

    stringBuffer.append(TEXT_56);
    	}
    int n = 0;
    for (Node node : changeInfo.getCreatedNodes()) {
    stringBuffer.append(TEXT_57);
    stringBuffer.append( GiraphUtil.getNodeName(node) );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( n++ );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( typeConstants.get(node.getType()) );
    stringBuffer.append(TEXT_60);
      }

    int e = 0;
    for (Edge edge : changeInfo.getCreatedEdges()) { 
    stringBuffer.append(TEXT_61);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getSource()) );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( GiraphUtil.getNodeName(edge.getTarget()) );
    stringBuffer.append(TEXT_63);
    	// THE SOURCE OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getSource())) { 
    stringBuffer.append(TEXT_64);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getSource()) );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_66);
    	} else { 
    stringBuffer.append(TEXT_67);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_68);
    stringBuffer.append( orderedLhsNodes.indexOf(
                                  rule.getMappings().getOrigin(edge.getSource())) );
    stringBuffer.append(TEXT_69);
    	}
	// THE TARGET OF THE NEW EDGE:
   	if (changeInfo.getCreatedNodes().contains(edge.getTarget())) { 
    stringBuffer.append(TEXT_70);
    stringBuffer.append( changeInfo.getCreatedNodes().indexOf(edge.getTarget()) );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_72);
    	} else { 
    stringBuffer.append(TEXT_73);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_74);
    stringBuffer.append( orderedLhsNodes.indexOf(
                                  rule.getMappings().getOrigin(edge.getTarget())) );
    stringBuffer.append(TEXT_75);
    	} 
    stringBuffer.append(TEXT_76);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_77);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_78);
    stringBuffer.append( typeConstants.get(edge.getType()) );
    stringBuffer.append(TEXT_79);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( e );
    stringBuffer.append(TEXT_81);
      e++;
    } 
    stringBuffer.append(TEXT_82);
    stringBuffer.append(TEXT_83);
    return stringBuffer.toString();
  }
}
