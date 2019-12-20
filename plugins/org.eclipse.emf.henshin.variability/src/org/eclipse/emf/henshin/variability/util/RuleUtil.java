package org.eclipse.emf.henshin.variability.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;


/**
 * 
 * @author Daniel Strüber
 *
 */
public class RuleUtil {

	public static void removeElementsFromRule(Rule rule,
			List<GraphElement> elementsToRemove) {
		Set<Node> nodes2delete = new HashSet<Node>();
		Set<Edge> edges2delete = new HashSet<Edge>();
		Set<Attribute> attribs2delete = new HashSet<Attribute>();

		for (GraphElement o : elementsToRemove) {
			if (o instanceof Node) {
				Node n = (Node) o;
				nodes2delete.add(n);
				edges2delete.addAll(n.getIncoming());
				edges2delete.addAll(n.getOutgoing());
			}
			else if (o instanceof Edge)
				edges2delete.add((Edge) o);
			else if (o instanceof Attribute)
				attribs2delete.add((Attribute) o);
		}

		for (Attribute a : attribs2delete)
			rule.removeAttribute(a, true);
		for (Edge e : edges2delete)
			rule.removeEdge(e, true);
		for (Node n : nodes2delete)
			rule.removeNode(n, true);
	}

	public static boolean isVarRule(Unit unit) {
		if (unit instanceof Rule) {
			if (VariabilityFactory.createVariabilityRule((Rule)unit).getFeatureModel() != null) {
				return true;
			}
		}
		return false;
	}

}
