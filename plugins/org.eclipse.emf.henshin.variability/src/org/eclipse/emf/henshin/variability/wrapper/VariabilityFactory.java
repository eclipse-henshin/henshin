package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The factory for the variability-aware graph elements.
 * It provides methods for creating different variability graph elements.
 * 
 * @author Stefan Schulz
 * 
 */
public class VariabilityFactory {
	
	private VariabilityFactory() {
		// This class should not be instatiated
	}
	
	/**
	 * Creates a new empty variability-aware {@link org.eclipse.emf.henshin.model.Attribute}.
	 * @return the Attribute.
	 */
	public static VariabilityAttribute createVariabilityAttribute() {
		return new VariabilityAttribute();
	}
	
	/**
	 * Creates a new variability-aware {@link org.eclipse.emf.henshin.model.Attribute} with the given type and value
	 * and adds it to the given {@link org.eclipse.emf.henshin.model.Node}.
	 * @param node the Node to add the Attribute to.
	 * @param type the type of the Attribute.
	 * @param value the value of the Attribute.
	 * @return the Attribute.
	 */
	public static VariabilityAttribute createVariabilityAttribute(Node node, EAttribute type, String value) {
		return new VariabilityAttribute(node, type, value);
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.Attribute} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityAttribute}.
	 * If the Attribute is already variability-aware it is not wrapped, but returned instead.
	 * @param attribute the Attribute to add variability-awareness to.
	 * @return the variability-aware Attribute.
	 */
	public static VariabilityAttribute createVariabilityAttribute(Attribute attribute) {
		if (attribute instanceof VariabilityAttribute) {
			return (VariabilityAttribute) attribute;
		} else {
			return new VariabilityAttribute(attribute);
		}
	}
	
	/**
	 * Creates a new empty variability-aware {@link org.eclipse.emf.henshin.model.Edge}.
	 * @return the Edge.
	 */
	public static VariabilityEdge createVariabilityEdge() {
		return new VariabilityEdge();
	}
	
	/**
	 * Creates a new variability-aware {@link org.eclipse.emf.henshin.model.Edge} between the given {@link org.eclipse.emf.henshin.model.Node}s.
	 * @param source Source node.
	 * @param target Target node.
	 * @param type Edge type.
	 * @return the variability-aware Edge.
	 */
	public static VariabilityEdge createVariabilityEdge(Node source, Node target, EReference type) {
		return new VariabilityEdge(source, target, type);
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.Edge} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityEdge}.
	 * If the Edge is already variability-aware it is not wrapped, but returned instead.
	 * @param edge the Edge to add variability-awareness to.
	 * @return the variability-aware Edge.
	 */
	public static VariabilityEdge createVariabilityEdge(Edge edge) {
		if (edge instanceof VariabilityEdge) {
			return (VariabilityEdge) edge;
		} else {			
			return new VariabilityEdge(edge);
		}
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.GraphElement} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityGraphElement}.
	 * If the GraphElement is already variability-aware it is not wrapped, but returned instead.
	 * @param graphElement the GraphElement to add variability-awareness to.
	 * @return the variability-aware GraphElement.
	 */
	public static VariabilityGraphElement createVariabilityGraphElement(GraphElement graphElement) {
		if (graphElement instanceof VariabilityGraphElement) {
			return (VariabilityGraphElement) graphElement;
		} else {
			if (graphElement instanceof Attribute) {
				return createVariabilityAttribute((Attribute) graphElement);
			} else if (graphElement instanceof Node) {
				return createVariabilityNode((Node) graphElement);
			} else if (graphElement instanceof Edge) {
				return createVariabilityEdge((Edge) graphElement);
			} else {
				throw new RuntimeException();
			}
		}
	}
	
	/**
	 * Creates a new empty variability-aware {@link org.eclipse.emf.henshin.model.NestedCondition}.
	 * @return the NestedCondition.
	 */
	public static VariabilityNestedCondition createVariabilityNestedCondition() {
		return new VariabilityNestedCondition();
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.NestedCondition} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityNestedCondition}.
	 * If the NestedCondition is already variability-aware it is not wrapped, but returned instead.
	 * @param condition the NestedCondition to add variability-awareness to.
	 * @return the variability-aware NestedCondition.
	 */
	public static VariabilityNestedCondition createVariabilityNestedCondition(NestedCondition condition) {
		if (condition instanceof VariabilityGraphElement) {
			return (VariabilityNestedCondition) condition;
		} else {
			return new VariabilityNestedCondition(condition);
		}
	}
	
	/**
	 * Creates a new empty variability-aware {@link org.eclipse.emf.henshin.model.Node}.
	 * @return the Node.
	 */
	public static VariabilityNode createVariabilityNode() {
		return new VariabilityNode();
	}
	
	public static VariabilityNode createVariabilityNode(Graph graph, EClass type, String name) {
		return new VariabilityNode(graph, type, name);
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.Node} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityNode}.
	 * If the Node is already variability-aware it is not wrapped, but returned instead.
	 * @param node the Node to add variability-awareness to.
	 * @return the variability-aware Node.
	 */
	public static VariabilityNode createVariabilityNode(Node node) {
		if (node instanceof VariabilityNode) {
			return (VariabilityNode) node;
		} else {
			return new VariabilityNode(node);
		}
	}
	
	/**
	 * Creates a new empty variability-aware {@link org.eclipse.emf.henshin.model.Rule}.
	 * @return the Rule.
	 */
	public static VariabilityRule createVariabilityRule() {
		return new VariabilityRule();
	}
	
	/**
	 * Creates a new variability-aware {@link org.eclipse.emf.henshin.model.Rule} with the given name.
	 * @param name the name of the Rule.
	 * @return the variability-aware Rule.
	 */
	public static VariabilityRule createVariabilityRule(String name) {
		return new VariabilityRule(name);
	}
	
	/**
	 * Adds variability-awareness to the given {@link org.eclipse.emf.henshin.model.Rule} by wrapping it in a {@link org.eclipse.emf.henshin.variability.wrapper.VariabilityRule}.
	 * If the Rule is already variability-aware it is not wrapped, but returned instead.
	 * @param rule the Rule to add variability-awareness to.
	 * @return the variability-aware Rule.
	 */
	public static VariabilityRule createVariabilityRule(Rule rule) {
		if (rule instanceof VariabilityRule) {
			return (VariabilityRule) rule;
		} else {
			return new VariabilityRule(rule);
		}
	}
}