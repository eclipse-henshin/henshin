package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;

/**
 * For each rule from the input rule, returns a one-element clone group.
 * Every edge in the rule is considered part of the identified "clone".
 * 
 * @author strueber
 *
 */
public class DummyCloneGroupDetector extends AbstractCloneGroupDetector {

	public DummyCloneGroupDetector(List<Rule> rules) {
		super(rules);
	}

	@Override
	public void detectCloneGroups() {
		result = new HashSet<CloneGroup>();
		for (Rule rule : rules) {
			// Construct edge mappings
			Map<Edge, Map<Rule, Edge>> edgeMappings = new HashMap<Edge, Map<Rule, Edge>>();
			Map<Attribute, Map<Rule, Attribute>> attributeMappings = new HashMap<Attribute, Map<Rule,Attribute>>();
			TreeIterator<EObject> it = rule.eAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Edge && ((Edge) o).getAction() != null) {
					Edge edge = (Edge) o;
					Map<Rule, Edge> map = new HashMap<Rule, Edge>();
					map.put(rule, edge);
					edgeMappings.put(edge, map);
				}

				if (o instanceof Attribute && ((Attribute) o).getAction() != null) {
					Attribute attribute = (Attribute) o;
					Map<Rule, Attribute> map = new HashMap<Rule, Attribute>();
					map.put(rule, attribute);
					attributeMappings.put(attribute, map);
				}
			}

			// Construct one-element rule set and result
			List<Rule> affected = new ArrayList<Rule>();
			affected.add(rule); 
			result.add(new CloneGroup(affected, edgeMappings, attributeMappings));
		}
	}

}
