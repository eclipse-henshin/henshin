package org.eclipse.emf.henshin.multicda.cda.framework;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.Utils;

/**
 * This class configures types of rules. The types are needed to produce abstract table.
 * @author Jevgenij Huebert
 * @see ResultCreator
 */
public class RuleConfigurator {

	public static enum RuleType {
		None(0, "N", "Contains no nodes"), Preserve(1, "P", "Preserve nodes"), Delete(2, "D", "Delete nodes"), Create(4,
				"C", "Create nodes"), Forbid(8, "F", "Forbid nodes"), Require(16, "R", "Require nodes"),

		PreserveA(32, "pA", "Nodes with preserve attributes"), DeleteA(64, "dA",
				"Nodes with delete attributes"), CreateA(128, "cA", "Nodes with create attributes"), ForbidA(256, "fA",
						"Nodes with forbid attributes"), RequireA(512, "rA", "Nodes with require attributes"), ChangeA(
								1024, "chA", "Nodes with changing attributes"),

		PreserveAVar(2048, "paV", "Nodes with attributes that preserve a value of a variable"), DeleteAVar(4096, "daV",
				"Nodes with attributes that delete a value of a variable"), CreateAVar(8192, "caV",
						"Nodes with attributes that create a value of a variable"), ForbidAVar(16384, "faV",
								"Nodes with attributes that forbid a value of a variable"), RequireAVar(32768, "raV",
										"Nodes with attributes that require a value of a variable"), ChangeAVar(65536,
												"chaV",
												"Nodes with attributes that changing a value of a variable"), All(
														131071, "All", "Contains all kinds of rule type");

		private final int id;
		public final String TAG;
		public final String description;

		RuleType(int id, String TAG, String description) {
			this.id = id;
			this.description = description;
			this.TAG = TAG;
		}
	}

	private Set<RuleType> types = new HashSet<>();
	private int hash = 0;
	private Rule rule;
	private String TAG;

	public RuleConfigurator(Rule rule) {
		this.rule = rule;
		Action p = new Action(Action.Type.PRESERVE);
		Action d = new Action(Action.Type.DELETE);
		Action c = new Action(Action.Type.CREATE);
		Action f = new Action(Action.Type.FORBID);
		Action r = new Action(Action.Type.REQUIRE);
		if (!rule.getActionNodes(p).isEmpty() || !rule.getActionEdges(p).isEmpty())
			add(RuleType.Preserve);
		if (!rule.getActionNodes(d).isEmpty() || !rule.getActionEdges(d).isEmpty())
			add(RuleType.Delete);
		if (!rule.getActionNodes(c).isEmpty() || !rule.getActionEdges(c).isEmpty())
			add(RuleType.Create);
		if (!rule.getActionNodes(f).isEmpty() || !rule.getActionEdges(f).isEmpty())
			add(RuleType.Forbid);
		if (!rule.getActionNodes(r).isEmpty() || !rule.getActionEdges(r).isEmpty())
			add(RuleType.Require);

		checkAttributes(rule);
		for (Rule nacRule : Utils.createNACRules(rule))
			checkAttributes(nacRule, false);
		for (Rule pacRule : Utils.createPACRules(rule))
			checkAttributes(pacRule, true);
	}

	private void checkAttributes(Rule rule, boolean... nacsPacs) {
		Map<Node, Set<Pair<Attribute, Attribute>>> changeNodes = Utils.getAttributeChanges(rule);
		EList<Parameter> parameters = rule.getParameters();
		for (Node n : changeNodes.keySet()) {
			Set<Pair<Attribute, Attribute>> attributes = changeNodes.get(n);
			for (Pair<Attribute, Attribute> attribute : attributes) {
				if (attribute.first == null)
					if (parameterContains(parameters, attribute.second))
						add(RuleType.CreateAVar);
					else
						add(RuleType.CreateA);
				else if (attribute.second == null)
					if (parameterContains(parameters, attribute.first))
						if (nacsPacs.length != 0 && !nacsPacs[0])
							add(RuleType.ForbidAVar);
						else if (nacsPacs.length != 0)
							add(RuleType.PreserveAVar);
						else
							add(RuleType.DeleteAVar);
					else if (nacsPacs.length != 0 && !nacsPacs[0])
						add(RuleType.ForbidA);
					else if (nacsPacs.length != 0)
						add(RuleType.PreserveA);
					else
						add(RuleType.DeleteA);
				else if (parameterContains(parameters, attribute.first, attribute.second))
					add(RuleType.ChangeAVar);
				else
					add(RuleType.ChangeA);
			}
		}
	}
	
	public Rule getRule() {
		return rule;
	}

	private void setTAG() {
		TAG = "";
		for (RuleType type : types)
			TAG += type.TAG;
	}

	private boolean parameterContains(EList<Parameter> ps, Attribute... as) {
		for (Parameter p : ps)
			for (Attribute a : as)
				if (p.getName().equals(a.getValue()))
					return true;
		return false;
	}

	private void add(RuleType rt) {
		types.add(rt);
		hash = hash | rt.id;
	}

	public boolean is(RuleType rt) {
		return (hash & rt.id) == rt.id;
	}

	public Set<RuleType> getTypes() {
		return new HashSet<>(types);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RuleConfigurator)
			return hash == obj.hashCode();
		return false;
	}

	@Override
	public String toString() {
		String result = "";
		if (is(RuleType.All))
			result = ", ALL";
		else
			for (RuleType rt : types)
				result += ", " + rt.name();
		return rule.getName() + "\t" + (result.isEmpty() ? "" : result.substring(2)) + "\n";// + "\n" + rule.getLhs() + "\n"+ rule.getRhs() + "\n";
	}

	/**
	 * @return representation of actions of the rule in a kind of a tag 
	 */
	public String getTAG() {
		setTAG();
		return TAG;
	}
}
