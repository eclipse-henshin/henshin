package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.function.Function;

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
 * @author Strüber
 *
 */
public class ParsemisParserSerializer implements GraphParser<INodeLabel, IEdgeLabel> {
	private static final long serialVersionUID = 8826507660954709617L;

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

	public static LabelParser<INodeLabel> NODE_LABEL_PARSER = new SerializingLabelParser<>(INodeLabel::getLabelName);
	public static LabelParser<IEdgeLabel> EDGE_LABEL_PARSER = new SerializingLabelParser<>(IEdgeLabel::getLabelName);

	private static class SerializingLabelParser<T> implements LabelParser<T> {
		private static final long serialVersionUID = 1L;
		private final Function<T, String> serializer;

		public SerializingLabelParser(Function<T, String> serializer) {
			this.serializer = serializer;
		}

		@Override
		public T parse(String text) throws ParseException {
			throw new RuntimeException("This function should never be called");
		}

		@Override
		public String serialize(T label) {
			return serializer.apply(label);
		}
	}

}