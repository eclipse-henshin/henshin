package org.eclipse.emf.henshin.statespace.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		List<Node> nodes = new ArrayList<Node>();
		String value = stateSpace.getProperties().get(getParametersKey(rule));
		if (value==null) return nodes;
		String[] names = value.split(",");
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
	public static double getRate(StateSpace stateSpace, Rule rule) {
		return getDouble(stateSpace, getRateKey(rule), -1.0);
	}

	/**
	 * Set the rate of a rule, stored in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @param rate Its rate.
	 */
	public static void setRate(StateSpace stateSpace, Rule rule, double rate) {
		setDouble(stateSpace, getRateKey(rule), rate);
	}

	/**
	 * Get the reward of a rule, as specified in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @return Its reward.
	 */
	public static double getReward(StateSpace stateSpace, Rule rule) {
		return getDouble(stateSpace, getRewardKey(rule), 0.0);
	}

	/**
	 * Set the reward of a rule, stored in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @param rate Its reward.
	 */
	public static void setReward(StateSpace stateSpace, Rule rule, double reward) {
		setDouble(stateSpace, getRewardKey(rule), reward);
	}


	/*
	 * Get a double properties
	 */
	private static double getDouble(StateSpace stateSpace, String name, Double defaultValue) {
		String value = stateSpace.getProperties().get(name);
		if (value==null) return defaultValue;
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
			Number number = format.parse(value);
			return number.doubleValue();
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	/*
	 * Set a double value.
	 */
	private static void setDouble(StateSpace stateSpace, String name, double value) {
		NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
		stateSpace.getProperties().put(name,format.format(value));
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
		if (!stateSpace.getProperties().containsKey(getRewardKey(rule))) {
			setReward(stateSpace,rule,0);
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
	 * Get the properties key for rule rewards.
	 */
	private static String getRewardKey(Rule rule) {
		return "reward" + capitalize(removeWhiteSpace(rule.getName()));
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
