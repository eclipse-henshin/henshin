package org.eclipse.emf.henshin.statespace.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.eclipse.emf.henshin.statespace.StateSpaceProperties;
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
	
	/**
	 * Default constructor.
	 * @param index State space index.
	 */
	public StateSpaceTimeInfo(StateSpaceIndex index) throws StateSpaceException {
		StateSpace stateSpace = index.getStateSpace();
		
		// Find out whether the state space is timed:
		String useClocks = stateSpace.getProperties().get(StateSpaceProperties.USE_CLOCKS);
		timed = ("yes".equalsIgnoreCase(useClocks) || "true".equalsIgnoreCase(useClocks));
		
		// Extract the clock declarations:
		clockDeclarations = new LinkedHashMap<EClass,List<String>>();
		types = StateSpaceTypesHelper.getTypes(index.getStateSpace());
		for (EClass type : types) {
			clockDeclarations.put(type, new Vector<String>());
		}
		String prop = stateSpace.getProperties().get(StateSpaceProperties.CLOCK_DECLARATIONS);
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
	
	public String getGuard(Match match, Model model) {
		return getNestedMatchProperty(ruleGuards.get(match.getRule()), match, model);
	}

	public String getResets(Match match, Model model) {
		return getNestedMatchProperty(ruleResets.get(match.getRule()), match, model);
	}
	
	private String getNestedMatchProperty(Vector<String> properties, Match match, Model model) {
		if (properties!=null) {
			List<String> original = new Vector<String>(properties);
			properties = new Vector<String>(properties);
			Map<String,List<String>> replacements = new HashMap<String,List<String>>();
			computePropertyReplacements(match, model, replacements);
			for (String key : replacements.keySet()) {
				List<String> details = replacements.get(key);
				for (int i=0; i<details.size(); i++) {
					for (int j=0; j<properties.size(); j++) {
						properties.set(j, properties.get(j).replaceAll(key, details.get(i)));
					}
					if (i<details.size()-1) {
						properties = new Vector<String>(properties);
						properties.addAll(properties);
					}
				}
			}
			properties.removeAll(original);
			if (properties.isEmpty()) {
				return null;
			}
			String result = "";
			int count = properties.size();
			for (int j=0; j<count; j++) {
				boolean duplicate = false;
				for (int k=0; k<j; k++) {
					if (properties.get(k).equals(properties.get(j))) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) {
					String prop = properties.get(j).trim();
					if (!prop.startsWith("(")) {
						prop = "(" + prop + ")";
					}
					if (result.length()>0) {
						result = result + "&";
					}
					result = result + prop;
				}
			}
			return result;
		}
		return null;
	}
	
	private void computePropertyReplacements(Match match, Model model, Map<String,List<String>> replacements) {
		String nodeName;
		for (Node node : match.getRule().getLhs().getNodes()) {
			nodeName = node.getName();
			if (nodeName!=null) {
				EObject object = match.getNodeTarget(node);
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
			for (Match multiMatch : match.getNestedMatches(multiRule)) {
				computePropertyReplacements(multiMatch, model, replacements);
			}
		}
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
