/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package graph.util.extensions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import graph.Attribute;
import graph.Edge;
import graph.Graph;
import graph.Node;

public class GraphPrinter {

	private Graph graph;
	private int lastPrinted;
	private StringBuffer result;

	public GraphPrinter(Graph graph) {
		this.graph = graph;
		this.lastPrinted = -1;
	}

	private List<ExtendedNode> getExtendedNodes(Graph graph) {
		List<ExtendedNode> extNodes = new ArrayList<ExtendedNode>();
		// create extended nodes
		for (Node n : graph.getNodes()) {
			extNodes.add(new ExtendedNode(n));
		}
		// add predecessor and successors
		for (ExtendedNode extNode : extNodes) {
			for (Edge edge : graph.getEdges()) {
				if (extNode.getNode() == edge.getTarget()) {
					extNode.setPredecessor(getExtendedNode(extNodes, edge.getSource()));
				}
				if (extNode.getNode() == edge.getSource()) {
					extNode.addSuccessor(getExtendedNode(extNodes, edge.getTarget()));
				}
			}
		}
		// reduce roots
		List<ExtendedNode> rootExtNodes = new ArrayList<ExtendedNode>();
		for (ExtendedNode extNode : extNodes) {
			if (extNode.getPredecessor() == null) {
				rootExtNodes.add(extNode);
			}
		}
		return rootExtNodes;
	}

	private ExtendedNode getExtendedNode(List<ExtendedNode> extNodes, Node node) {
		for (ExtendedNode extNode : extNodes) {
			if (extNode.getNode() == node) {
				return extNode;
			}
		}
		return null;
	}

	public void printDocument() {
		result = new StringBuffer(printPreambel());
		result.append(Constants.BEGIN_DOCUMENT);
		result.append(printGraph());
		result.append(Constants.END_DOCUMENT);
		saveFile();
	}

	public String printPreambel() {
		StringBuffer preambel = new StringBuffer(Constants.DOCUMENTCLASS);
		preambel.append(Constants.TIKZ_CELLULAR);
		preambel.append(Constants.TIKZ_VCOND);
		preambel.append(Constants.TIKZ_EXTENSION);
		preambel.append(Constants.AMSSYMB);
		return preambel.toString();
	}

	public String printPreambel2() {
		StringBuffer preambel = new StringBuffer(Constants.DOCUMENTCLASS2);
		preambel.append(Constants.TIKZ_CELLULAR);
		preambel.append(Constants.TIKZ_VCOND);
		preambel.append(Constants.TIKZ_EXTENSION);
		preambel.append(Constants.TIKZ_ARROWS);
		preambel.append(Constants.AMSSYMB);
		return preambel.toString();
	}

	public String printGraph() {
		StringBuffer ret;
		if (graph.getNodes().isEmpty()) {
			ret = new StringBuffer(Constants.EMPTYSET);
		} else {
			ret = new StringBuffer(Constants.TIKZ + Constants.SETBRACKETOPEN);
			ret.append(printExtendedNodes(getExtendedNodes(graph), false));
			for (Edge edge : graph.getEdges()) {
				ret.append(printEdge(edge));
			}
			ret.append(Constants.SETBRACKETCLOSE);
		}
		lastPrinted = -1;
		return ret.toString();
	}

	private String printEdge(Edge edge) {
		EList<Node> nodes = graph.getNodes();
		StringBuffer ret;
		if (edge.getType().isContainment())
			ret = new StringBuffer(
					Constants.DRAWCONTAINMENT + getNodeName(nodes, edge.getSource()) + Constants.BRACKETCLOSE);
		else
			ret = new StringBuffer(Constants.DRAW + getNodeName(nodes, edge.getSource()) + Constants.BRACKETCLOSE);
		ret.append(Constants.TONODE + edge.getType().getName() + Constants.SETBRACKETCLOSE);
		ret.append(Constants.BRACKETOPEN + getNodeName(nodes, edge.getTarget()) + Constants.BRACKETCLOSE
				+ Constants.SEMICOLON + Constants.NEWLINE);
		return ret.toString();
	}

	private String printExtendedNodes(List<ExtendedNode> extendedNodes, boolean succ) {
		int source = -1;
		if (succ && !extendedNodes.isEmpty()) {
			source = graph.getNodes().indexOf(extendedNodes.get(0).getPredecessor().getNode());
		}
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < extendedNodes.size(); i++) {
			ret.append(printNode(extendedNodes.get(i).getNode(), i, succ, source));
			this.lastPrinted = graph.getNodes().indexOf(extendedNodes.get(i).getNode());
		}
		for (int i = 0; i < extendedNodes.size(); i++) {
			ret.append(printExtendedNodes(extendedNodes.get(i).getSuccessors(), true));
		}
		return ret.toString();
	}

	private String printNode(Node node, int index, boolean succ, int source) {
		EList<Node> nodes = graph.getNodes();
		String sro = "";
		if (lastPrinted > -1) {
			sro = Constants.COMMA;
			if (source < 0) {
				sro += " strictly below of= " + getNodeName(nodes, nodes.get(lastPrinted)) + ",node distance=4em";
			} else {
				sro += " strictly right of= " + getNodeName(nodes, nodes.get(source)) + ",node distance=4em";
			}
			if (succ && index > 0) {
				if (index == 1) {
					sro += Constants.COMMA + " above=4ex";
				} else {
					sro += Constants.COMMA + " below=4ex";
				}
			}
		}
		StringBuffer ret = new StringBuffer(
				Constants.NODE + Constants.BRACKETOPEN + getNodeName(nodes, node) + Constants.BRACKETCLOSE);
		if (node.getAttributes().isEmpty()) {
			ret.append(Constants.SCLASS + sro + Constants.CLASSCLOSE + Constants.SETBRACKETOPEN);
			if (node.getName() != null && !node.getName().isEmpty()) {
				ret.append(node.getName());
			}
			ret.append(Constants.COLON + node.getType().getName());
			ret.append(Constants.SETBRACKETCLOSE);
		} else {
			ret.append(Constants.NORMALCLASS + sro + Constants.CLASSCLOSE + Constants.SETBRACKETOPEN);
			ret.append(Constants.GNODELABELCLASS + Constants.SETBRACKETOPEN);
			if (node.getName() != null && !node.getName().isEmpty()) {
				ret.append(node.getName());
			}
			ret.append(Constants.COLON + node.getType().getName() + Constants.SETBRACKETCLOSE);
			if (!node.getAttributes().isEmpty())
				ret.append(Constants.SETBRACKETOPEN);
			for (Attribute attr : node.getAttributes()) {
				if (node.getAttributes().indexOf(attr) != 0)
					ret.append(Constants.DOUBLEBS);
				ret.append(printAttribute(attr));
			}
			if (!node.getAttributes().isEmpty())
				ret.append(Constants.SETBRACKETCLOSE);
			ret.append(Constants.SETBRACKETCLOSE);
		}
		ret.append(Constants.SEMICOLON + Constants.NEWLINE);
		return ret.toString();
	}

	private String getNodeName(EList<Node> nodes, Node n) {
		if (n.getName() != null && !n.getName().isEmpty()) {
			return n.getName();
		} else {
			return "n" + String.valueOf(nodes.indexOf(n));
		}
	}

	private String printAttribute(Attribute attr) {
		StringBuffer ret = new StringBuffer(attr.getType().getName());
		ret.append(Constants.SPACE);
		ret.append(printOp(attr.getOp()));
		ret.append(Constants.SPACE);
		if (attr.getValue() == null || attr.getValue().isEmpty())
			ret.append("' '");
		else
			ret.append(attr.getValue());
		return ret.toString();
	}

	private String printOp(String op) {
		// TODO to be extended
		if (op.equals(Constants.GEQ)) {
			return Constants.$GEQ$;
		}
		if (op.equals(Constants.NOTEQUALS)) {
			return Constants.$NOTEQUALS$;
		}

		if (op.equals(Constants.NOTEQUALS2)) {
			return Constants.$NOTEQUALS2$;
		}

		if (op.equals(Constants.GRTH)) {
			return Constants.$GRTH$;
		}
		if (op.equals(Constants.EQUALS)) {
			return Constants.$EQUALS$;
		}
		if (op.equals(Constants.LSTH)) {
			return Constants.$LSTH$;
		}
		return "TODO";
	}

	private void saveFile() {
		URI uri = getActualProject().getLocationURI();
		String path = uri.getPath().concat(Constants.TEXFOLDER + Constants.GRAPH + Constants.TEX);
		File file = new File(path);
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				getActualProject().refreshLocal(IProject.DEPTH_INFINITE, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("finally")
	private IProject getActualProject() {
		IProject actualProject = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			IEditorPart editorPart = window.getActivePage().getActiveEditor();
			if (editorPart != null) {
				IEditorInput input = editorPart.getEditorInput();
				if (input instanceof IFileEditorInput) {
					IFileEditorInput fileInput = (IFileEditorInput) input;
					actualProject = fileInput.getFile().getProject();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return actualProject;
		}
	}
}
