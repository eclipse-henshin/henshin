package org.eclipse.emf.henshin.interpreter.ui.giraph;

public class InputFormatTemplate
{
  protected static String nl;
  public static synchronized InputFormatTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    InputFormatTemplate result = new InputFormatTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "import com.google.common.collect.Lists;" + NL + "import org.apache.giraph.edge.Edge;" + NL + "import org.apache.giraph.edge.EdgeFactory;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.hadoop.io.DoubleWritable;" + NL + "import org.apache.hadoop.io.FloatWritable;" + NL + "import org.apache.hadoop.io.LongWritable;" + NL + "import org.apache.hadoop.io.Text;" + NL + "import org.apache.hadoop.mapreduce.InputSplit;" + NL + "import org.apache.hadoop.mapreduce.TaskAttemptContext;" + NL + "import org.json.JSONArray;" + NL + "import org.json.JSONException;" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.util.List;" + NL + "" + NL + "/**" + NL + "  * VertexInputFormat that features <code>long</code> vertex ID's," + NL + "  * <code>int</code> vertex values and <code>int</code>" + NL + "  * edge values, and <code>byte</code>-array message types," + NL + "  * specified in JSON format." + NL + "  */" + NL + "public class JsonLongIntIntBytesVertexInputFormat extends TextVertexInputFormat<LongWritable, IntWritable, IntWritable> {" + NL + "" + NL + "  @Override" + NL + "  public TextVertexReader createVertexReader(InputSplit split, TaskAttemptContext context) {" + NL + "    return new JsonLongDoubleFloatDoubleVertexReader();" + NL + "  }" + NL + "" + NL + " /**" + NL + "  * VertexReader that features <code>int</code> vertex" + NL + "  * values and <code>int</code> out-edge values. The" + NL + "  * files should be in the following JSON format:" + NL + "  * JSONArray(<vertex id>, <vertex value>," + NL + "  *   JSONArray(JSONArray(<dest vertex id>, <edge value>), ...))" + NL + "  * Here is an example with vertex id 1, vertex value 4, and two edges." + NL + "  * First edge has a destination vertex 2, edge value 5." + NL + "  * Second edge has a destination vertex 3, edge value 7." + NL + "  * [1,4,[[2,5],[3,7]]]" + NL + "  */" + NL + "  class JsonLongIntIntBytesVertexReader extends" + NL + "    TextVertexReaderFromEachLineProcessedHandlingExceptions<JSONArray,JSONException> {" + NL + "" + NL + "    @Override" + NL + "    protected JSONArray preprocessLine(Text line) throws JSONException {" + NL + "      return new JSONArray(line.toString());" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    protected LongWritable getId(JSONArray jsonVertex) throws JSONException, IOException {" + NL + "      return new LongWritable(jsonVertex.getLong(0));" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    protected IntWritable getValue(JSONArray jsonVertex) throws JSONException, IOException {" + NL + "      return new IntWritable(jsonVertex.getInt(1));" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    protected Iterable<Edge<LongWritable, IntWritable>> getEdges(JSONArray jsonVertex) throws JSONException, IOException {" + NL + "      JSONArray jsonEdgeArray = jsonVertex.getJSONArray(2);" + NL + "      List<Edge<LongWritable, IntWritable>> edges = Lists.newArrayListWithCapacity(jsonEdgeArray.length());" + NL + "      for (int i = 0; i < jsonEdgeArray.length(); ++i) {" + NL + "        JSONArray jsonEdge = jsonEdgeArray.getJSONArray(i);" + NL + "        edges.add(EdgeFactory.create(new LongWritable(jsonEdge.getLong(0))," + NL + "            new IntWritable(jsonEdge.getInt(1))));" + NL + "      }" + NL + "      return edges;" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    protected Vertex<LongWritable, IntWritable, IntWritable>" + NL + "    handleException(Text line, JSONArray jsonVertex, JSONException e) {" + NL + "      throw new IllegalArgumentException(" + NL + "          \"Couldn't get vertex from line \" + line, e);" + NL + "    }" + NL + "" + NL + "  }" + NL + "}";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
