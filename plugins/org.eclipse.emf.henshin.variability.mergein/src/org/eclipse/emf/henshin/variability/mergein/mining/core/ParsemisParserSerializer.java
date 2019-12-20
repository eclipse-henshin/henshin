package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Collection;

import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.graph.GraphFactory;
import de.parsemis.miner.general.Fragment;
import de.parsemis.parsers.GraphParser;
import de.parsemis.parsers.LabelParser;

/**
 * Implements interpretation of the Parsemis representation of the HenshinGraph
 * as String. 
 * 
 * @author strüber
 *
 */
public class ParsemisParserSerializer implements
		GraphParser<INodeLabel, IEdgeLabel>, LabelParser {

	public LabelParser<IEdgeLabel> getEdgeParser() {
		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	public LabelParser<INodeLabel> getNodeParser() {
		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	public Collection<Graph<INodeLabel, IEdgeLabel>> parse(InputStream arg0,
			GraphFactory<INodeLabel, IEdgeLabel> arg1) throws ParseException,
			IOException {
		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	public Graph<INodeLabel, IEdgeLabel> parse(String arg0,
			GraphFactory<INodeLabel, IEdgeLabel> arg1) throws ParseException {

		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	public String serialize(Graph<INodeLabel, IEdgeLabel> graph) {
		String graphString = "SG: edge #:" + graph.getEdgeCount() + " | ";
		for (int i = 0; i < graph.getEdgeCount(); i++) {
			Edge<INodeLabel, IEdgeLabel> edge = graph.getEdge(i);
			String edgeString = "{[";
			edgeString += edge.getNodeA().getLabel().getLabelName();
			edgeString += "]}-";
			edgeString += edge.getLabel().getLabelName();
			edgeString += "-{[";
			edgeString += edge.getNodeB().getLabel().getLabelName();
			edgeString += "]} ";

			graphString += edgeString;
		}
		return graphString;
	}

	public void serialize(OutputStream arg0,
			Collection<Graph<INodeLabel, IEdgeLabel>> arg1) throws IOException {

		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	public void serializeFragments(OutputStream arg0,
			Collection<Fragment<INodeLabel, IEdgeLabel>> arg1)
			throws IOException {

		throw new UnsupportedOperationException("Unexpected function call!", null);
	}

	private static ParsemisParserSerializer instance = new ParsemisParserSerializer();

	public static String ser(Graph<INodeLabel, IEdgeLabel> graph) {
		return instance.serialize(graph);
	}

	public Object parse(String text) throws ParseException {
		throw new RuntimeException("This function should never be called");
	}

	public String serialize(Object label) {
		if (label instanceof INodeLabel) {
			INodeLabel myNodeLabel = (INodeLabel) label;
			return myNodeLabel.getLabelName();
		}
		if (label instanceof IEdgeLabel) {
			IEdgeLabel myEdgeLabel = (IEdgeLabel) label;
			return myEdgeLabel.getLabelName();
		}
		throw new RuntimeException("tried to serialize unknown label..",null);
	}

}