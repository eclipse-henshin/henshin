package org.eclipse.emf.henshin.codegen.generator.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.GenUnit;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class UnitModelCodeGenerator {
	
	// GenTransformation model:
	private GenTransformation genTrafo;
	
	// Factory variable name:
	private String factory;
	
	// Internal print writer:
	private PrintWriter out;
	
	// Nodes which are mapped:
	private Set<Node> mappedNodes;
	
	// Node names:
	private Map<Node,String> nodeNames;
	
	/**
	 * Default constructor.
	 * @param genTrafo GenTransformation.
	 * @param factoryName Name of the Henshin factory instance to be used.
	 */
	public UnitModelCodeGenerator(GenTransformation genTrafo, String factoryName) {
		this.genTrafo = genTrafo;
		this.factory = factoryName;
	}
	
	public String generate(GenUnit genUnit, String varName, String indent) {
		
		// Reset the print writer:
		StringWriter writer = new StringWriter();
		out = new PrintWriter(writer);
		
		// Generate the set of mapped nodes:
		TransformationUnit unit = genUnit.getUnit();
		TreeIterator<EObject> contents = unit.eAllContents();
		mappedNodes = new HashSet<Node>();
		while (contents.hasNext()) {
			EObject next = contents.next();
			if (next instanceof Mapping) {
				Mapping mapping = (Mapping) next;
				if (mapping.getOrigin()!=null) mappedNodes.add(mapping.getOrigin());
				if (mapping.getImage()!=null) mappedNodes.add(mapping.getImage());
			}
		}
		
		// Reset the node names:
		nodeNames = new HashMap<Node,String>();
		
		// Instantiate the unit variable:
		out.println(indent + varName + " = " + factory + ".create" + unit.eClass().getName() + "();");
		
		// Now create the contents:
		if (unit instanceof Rule) {
			Rule rule = (Rule) unit;
			
			// LHS and RHS:
			generateGraph(rule.getLhs(), "lhs", indent);
			out.println(indent + varName + ".setLhs(lhs);");
			generateGraph(rule.getRhs(), "rhs", indent);
			out.println(indent + varName + ".setRhs(rhs);");
			
			// Mappings LHS->RHS:
			for (Mapping mapping : rule.getMappings()) {
				out.println(indent + factory + ".createMapping(" +
						nodeNames.get(mapping.getOrigin()) + ", " + 
						nodeNames.get(mapping.getImage()) + ");");
			}
			
		}
		
		// Clean-up.
		mappedNodes = null;
		nodeNames = null;
		
		// Done.
		return writer.toString();
		
	}
	
	private void generateGraph(Graph graph, String name, String indent) {
		out.println(indent + "Graph " + name + " = " + factory + ".createGraph();");
		for (int i=0; i<graph.getNodes().size(); i++) {
			generateNode(graph.getNodes().get(i), name + "Node" + i, name, indent);
		}
	}
	
	private void generateNode(Node node, String name, String graphName, String indent) {
		boolean requiresName = false;
		if (!node.getAttributes().isEmpty() || mappedNodes.contains(node)) {
			requiresName = true;
		}
		String assignment = requiresName ? ("Node " + name + " = ") : "";
		out.println(indent + assignment + factory + ".createNode(" + graphName + ", " + getNodeType(node) + ");");
		for (Attribute attribute : node.getAttributes()) {
			out.println(indent + factory + ".createAttribute(" + name + ", " + 
					getAttributeType(attribute) + ", " + asString(attribute.getValue()) + ");");
		}
		
	}
	
	private String getNodeType(Node node) {
		if (node.getType()!=null) {
			String pack;
			String clazz;
			GenClass genClass = genTrafo.getGenClass(node.getType());
			if (genClass!=null) {
				GenPackage genPackage = genClass.getGenPackage();
				pack = genPackage.getInterfacePackageName();
				clazz = genClass.getImportedMetaType();
			} else {
				pack = node.getType().getEPackage().getName();
				pack = capitalize(pack) + "Package";
				clazz = node.getType().getName();
			}
			return pack + ".eINSTANCE.get" + clazz + "()"; 
		} else {
			return "null";
		}
	}

	private String getAttributeType(Attribute attribute) {
		Node dummyNode = HenshinFactory.eINSTANCE.createNode();
		dummyNode.setType(attribute.getType().getEContainingClass());
		String nodeType = getNodeType(dummyNode);
		if (nodeType.equals("null")) {
			return "null";
		} else {
			return nodeType.replaceFirst("\\(\\)", "_" + capitalize(attribute.getType().getName()) + "()");
		}
	}
	
	private static String capitalize(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	private static String asString(String value) {
		return "\"" + value.replaceAll("\"", "\\\\\"")+ "\"";
	}
}
