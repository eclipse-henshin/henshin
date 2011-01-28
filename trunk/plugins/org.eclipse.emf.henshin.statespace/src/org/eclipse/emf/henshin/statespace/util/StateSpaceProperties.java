package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Utility functions for state space properties.
 * @author Christian Krause
 */
public class StateSpaceProperties {
	
	/**
	 * Get the parameters of a rule, as used in the state space for labeling transitions.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getParameters(StateSpace stateSpace, Rule rule) {
		String value = stateSpace.getProperties().get(getParametersKey(rule));
		String[] names = value.split(",");
		List<Node> nodes = new ArrayList<Node>();
		for (int i=0; i<names.length; i++) {
			String name = names[i].trim();
			if (name.length()==0) continue;
			Node node = findNodeByName(name,rule.getLhs());
			if (node==null) node = findNodeByName(name,rule.getRhs());
			if (node!=null) nodes.add(node);
		}
		return nodes;
	}

	/**
	 * Set the parameters of a rule, as used in the state space for labeling transitions.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @param List of nodes.
	 */
	public static void setParameters(StateSpace stateSpace, Rule rule, List<Node> nodes) {
		String value = "";
		for (int i=0; i<nodes.size(); i++) {
			String name = nodes.get(i).getName();
			if (name==null || name.trim().length()==0) {
				throw new IllegalArgumentException("All parameter nodes must have a unique name");
			}
			value = value + name;
			if (i<nodes.size()-1) value = value + ",";
		}
		stateSpace.getProperties().put(getParametersKey(rule), value);
	}

	/*
	 * Find a node in a graph based on its name.
	 */
	private static Node findNodeByName(String name, Graph graph) {
		for (Node node : graph.getNodes()) {
			if (name.equals(node.getName())) return node;
		}
		return null;
	}
	
	/**
	 * Get the rate of a rule, as specified in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @return Its rate.
	 */
	public static int getRate(StateSpace stateSpace, Rule rule) {
		String value = stateSpace.getProperties().get(getRateKey(rule));
		if (value==null) return -1;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Set the rate of a rule, stored in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @param rate Its rate.
	 */
	public static void setRate(StateSpace stateSpace, Rule rule, int rate) {
		stateSpace.getProperties().put(getRateKey(rule), String.valueOf(rate));
	}
	
	/**
	 * Initialize the default properties of a rule.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 */
	public static void initializeDefaultProperties(StateSpace stateSpace, Rule rule) {
		if (!stateSpace.getProperties().containsKey(getRateKey(rule))) {
			setRate(stateSpace,rule,1);
		}
		if (!stateSpace.getProperties().containsKey(getParametersKey(rule))) {
			setParameters(stateSpace, rule, new ArrayList<Node>());
		}
	}
	
	/*
	 * Get the properties key for rule rates.
	 */
	private static String getRateKey(Rule rule) {
		return "rate" + capitalize(removeWhiteSpace(rule.getName()));
	}
	
	/*
	 * Get the properties key for rule parameters.
	 */
	private static String getParametersKey(Rule rule) {
		return "params" + capitalize(removeWhiteSpace(rule.getName()));
	}

	/*
	 * Capitalize a string.
	 */
	private static String capitalize(String string) {
		if (string==null || string.length()==0) return string;
		String first = string.substring(0,1).toUpperCase();
		if (string.length()==0) return first;
		else return first + string.substring(1);
	}
	
	/*
	 * Remove white space from a string.
	 */
	private static String removeWhiteSpace(String string) {
		string = string.replaceAll(" ", "_");
		string = string.replaceAll("\t", "_");
		string = string.replaceAll("\n", "_");
		return string;
	}
	
}
