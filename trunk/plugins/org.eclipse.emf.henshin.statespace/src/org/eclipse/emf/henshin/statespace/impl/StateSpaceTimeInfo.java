package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.ObjectKeyHelper;
import org.eclipse.emf.henshin.statespace.util.StateSpaceTypesHelper;

public class StateSpaceTimeInfo {
	
	private final boolean timed;
	
	private final List<EClass> types;
	
	private final Map<EClass,List<String>> clockDeclarations;
	
	private final int clockCount;
	
	private final Map<EClass,Integer> maxObjectIds;
	
	private final Map<Rule,Vector<String>> ruleGuards;
	
	private final Map<Rule,Vector<String>> ruleResets;
	
	private final Map<Rule,Vector<String>> ruleInvariants;
	
	/**
	 * Default constructor.
	 * @param index State space index.
	 */
	public StateSpaceTimeInfo(StateSpaceIndex index) throws StateSpaceException {
		StateSpace stateSpace = index.getStateSpace();
		
		// Find out whether the state space is timed:
		String useClocks = stateSpace.getProperties().get(StateSpace.PROPERTY_USE_CLOCKS);
		timed = ("yes".equalsIgnoreCase(useClocks) || "true".equalsIgnoreCase(useClocks));
		
		// Extract the clock declarations:
		clockDeclarations = new LinkedHashMap<EClass,List<String>>();
		types = StateSpaceTypesHelper.getTypes(index.getStateSpace());
		for (EClass type : types) {
			clockDeclarations.put(type, new Vector<String>());
		}
		String prop = stateSpace.getProperties().get(StateSpace.PROPERTY_CLOCK_DECLARATIONS);
		if (prop!=null) {
			Map<String,EClass> typeNameMap = StateSpaceTypesHelper.getTypesNameMap(index.getStateSpace());
			for (String decl : prop.split(",")) {
				String[] split = decl.split("\\.");
				if (split.length==2) {
					EClass type = typeNameMap.get(split[0]);
					if (type!=null) {
						clockDeclarations.get(type).add(split[1]);
					}
				}
			}
		}
		
		// Compute the maximum used object IDs for every type:
		maxObjectIds = new LinkedHashMap<EClass,Integer>();
		for (EClass type : types) {
			maxObjectIds.put(type, 0);
		}
		for (State state : stateSpace.getStates()) {
			Model model = index.getModel(state);
			for (Entry<EObject,Integer> entry : model.getObjectKeysMap()) {
				int id = ObjectKeyHelper.getObjectID(entry.getValue());
				if (maxObjectIds.get(entry.getKey().eClass()) < id) {
					maxObjectIds.put(entry.getKey().eClass(), id);
				}
			}
		}
		
		// Now we can compute the required number of clocks:
		int clocks = 0;
		for (EClass type : types) {
			clocks += maxObjectIds.get(type) * clockDeclarations.get(type).size();
		}
		clockCount = clocks;
		
		// Extract the rule guards and resets:
		ruleGuards = getRuleProperties(stateSpace, "guard");
		ruleResets = getRuleProperties(stateSpace, "resets");
		ruleInvariants = getRuleProperties(stateSpace, "invariant");
		
	}
	
	/*
	 * Helper method for extracting rule properties from a state space.
	 */
	private static Map<Rule,Vector<String>> getRuleProperties(StateSpace stateSpace, String prefix) {
		Map<Rule,Vector<String>> result = new LinkedHashMap<Rule,Vector<String>>();
		for (Rule rule : stateSpace.getRules()) {
			int index = 1;
			for (Rule rule2 : stateSpace.getRules()) {
				if (rule2==rule) break;
				if (rule2.getName().equals(rule.getName())) index++;
			}
			String property = prefix + capitalize(rule.getName()) + index;
			if (!stateSpace.getProperties().containsKey(property)) {
				property = prefix + capitalize(rule.getName());				
			}
			String value = stateSpace.getProperties().get(property);
			if (value!=null) {
				value = value.trim();
				if (value.length()>0) {
					Vector<String> vals = new Vector<String>();
					for (String s : value.split("&")) {
						vals.add(s);
					}
					result.put(rule, vals);
				}
			}
		}
		return result;
	}
	
	/**
	 * Get the clock declarations to be used in a state space.
	 * @return The clock declarations.
	 */
	public Map<EClass,List<String>> getClockDeclarations() {
		return clockDeclarations;
	}
	
	/**
	 * Check whether this state space is timed.
	 * @return <code>true</code> if it is timed.
	 */
	public boolean isTimed() {
		return timed;
	}
	
	/**
	 * Get the number of clocks required for the state space.
	 * @return The total number of required clocks.
	 */
	public int getClockCount() {
		return clockCount;
	}
	
	public String getClock(Model model, EObject object, String clockName) {
		int objectId = ObjectKeyHelper.getObjectID(model.getObjectKeysMap().get(object));
		if (objectId<1) {
			throw new IllegalArgumentException("Class '" + object.eClass().getName() + "' must be an identity type to have clocks.");
		}
		int clock = 0;
		for (EClass type : types) {
			if (type==object.eClass()) {
				clock += (objectId-1);
				break;
			} else {
				clock += maxObjectIds.get(type) * clockDeclarations.get(type).size();
			}
		}
		return "c" + clock;
	}
	
	public Iterable<String> getClocks() {
		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					int clock = 0;
					@Override
					public boolean hasNext() {
						return clock<clockCount;
					}
					@Override
					public String next() {
						return "c" + clock++;
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public String getGuard(Transition transition, Match match, Match resultMatch) {
		return getNestedMatchProperty(ruleGuards.get(transition.getRule()), transition, match, resultMatch);
	}

	public String getResets(Transition transition, Match match, Match resultMatch) {
		return getNestedMatchProperty(ruleResets.get(transition.getRule()), transition, match, resultMatch);
	}
	
	public String getInvariant(State state, List<Match> matches) {
		int index = 0;
		String r = "";
		Set<String> atoms = new HashSet<String>();
		for (Transition t : state.getOutgoing()) {
			String s = getNestedMatchProperty(ruleInvariants.get(t.getRule()), t, matches.get(index++), null);
			if (s!=null && !atoms.contains(s)) {
				if (r.length()>0) r = r + " & ";
				r = r + s;
				atoms.add(s);
			}
		}
		if (r.length()==0) return null;
		return r;
	}
	
	private String getNestedMatchProperty(Vector<String> properties, Transition transition, Match match, Match resultMatch) {
		if (properties!=null) {
			List<String> original = new Vector<String>(properties);
			properties = new Vector<String>(properties);
			Map<String,List<String>> replacements = new HashMap<String,List<String>>();
			computePropertyReplacements(getAllNodes(transition.getRule()), transition, match, resultMatch, replacements);
			for (String key : replacements.keySet()) {
				List<String> details = replacements.get(key);
				for (int i=0; i<details.size(); i++) {
					for (int j=0; j<properties.size(); j++) {
						properties.set(j, properties.get(j).replaceAll(key, details.get(i)));
					}
					if (i<details.size()-1) {
						properties.addAll(original);
					}
				}
			}
			properties.removeAll(original);
			Set<String> missingClockVariables = getMissingClockVariables(replacements.keySet(), match.getRule());
			
			String result = "";
			int count = properties.size();
			for (int j=0; j<count; j++) {
				String prop = properties.get(j);
				boolean use = true;
				for (int k=0; k<j; k++) {
					if (properties.get(k).equals(prop)) {
						use = false;
						break;
					}
				}
				if (use) {
					for (String missing : missingClockVariables) {
						if (prop.indexOf(missing)>=0) {
							use = false;
							break;
						}
					}
				}
				if (use) {
					prop = prop.trim();
					if (!prop.startsWith("(")) {
						prop = "(" + prop + ")";
					}
					if (result.length()>0) {
						result = result + "&";
					}
					result = result + prop;
				}
			}
			return result.length()>0 ? result : null;
		}
		return null;
	}
	
	private List<Node> getAllNodes(Rule rule) {
		List<Node> nodes = new ArrayList<Node>();
		nodes.addAll(rule.getLhs().getNodes());
		nodes.addAll(rule.getRhs().getNodes());
		for (Rule multiRule : rule.getMultiRules()) {
			nodes.addAll(getAllNodes(multiRule));
		}
		return nodes;
	}
	
	private void computePropertyReplacements(List<Node> nodes, Transition transition, Match match, Match resultMatch, Map<String,List<String>> replacements) {
		String nodeName;
		for (Node node : nodes) {
			nodeName = node.getName();
			if (nodeName!=null) {
				EObject object = match.getNodeTarget(node);
				Model model = transition.getSource().getModel();
				if (object==null && resultMatch!=null) {
					object = resultMatch.getNodeTarget(node);
					model = transition.getTarget().getModel();
				}
				if (object==null) continue;
				for (String clockName : clockDeclarations.get(node.getType())) {
					String key = nodeName + "\\." + clockName;
					if (!replacements.containsKey(key)) {
						replacements.put(key, new Vector<String>());
					}
					replacements.get(key).add(getClock(model, object, clockName));
				}
			}
		}
		for (Rule multiRule : match.getRule().getMultiRules()) {
			int size = match.getMultiMatches(multiRule).size();
			for (int i=0; i<size; i++) {
				Match r = resultMatch!=null ? resultMatch.getMultiMatches(multiRule).get(i) : null;
				computePropertyReplacements(nodes, transition, match.getMultiMatches(multiRule).get(i), r, replacements);
			}
		}
	}
	
	private Set<String> getMissingClockVariables(Set<String> usedClockVariables, Rule rule) {
		Set<String> missing = new HashSet<String>();
		String nodeName;
		for (Node node : rule.getLhs().getNodes()) {
			nodeName = node.getName();
			if (nodeName!=null) {
				for (String clockName : clockDeclarations.get(node.getType())) {
					if (!usedClockVariables.contains(nodeName + "\\." + clockName)) {
						missing.add(nodeName + "." + clockName);
					}
				}
			}
		}
		for (Rule multiRule : rule.getMultiRules()) {
			missing.addAll(getMissingClockVariables(usedClockVariables, multiRule));
		}
		return missing;
	}
	
	/*
	 * Capitalize a string.
	 */
	private static String capitalize(String string) {
		if (string==null || string.length()==0) return string;
		String first = string.substring(0,1).toUpperCase();
		if (string.length()==1) return first;
		return first + string.substring(1);
	}

}
