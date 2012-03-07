package org.eclipse.emf.henshin.statespace.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;

/**
 * Properties manager for rule parameters, as used in the state space tools.
 * @author Christian Krause
 */
public class ParametersPropertiesManager implements StateSpacePropertiesManager {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.StateSpacePropertiesManager#initialize(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	@Override
	public void initialize(StateSpace stateSpace) {
//		for (Rule rule : stateSpace.getRules()) {
//			String key = getParametersKey(rule);
//			if (!stateSpace.getProperties().containsKey(key)) {
//				stateSpace.getProperties().put(key,"");	// no parameters per default
//			}
//		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.StateSpacePropertiesManager#validate(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	@Override
	public IStatus validate(StateSpace stateSpace) {
		for (Rule rule : stateSpace.getRules()) {
			try {
				getParameters(stateSpace, rule);
			} catch (StateSpaceException e) {
				return new Status(IStatus.ERROR, StateSpacePlugin.PLUGIN_ID, e.getMessage());
			}
		}
		return Status.OK_STATUS;
	}
	
	/*
	 * Get the properties key for rule parameters.
	 */
	static String getParametersKey(Rule rule) {
		
		// Get the rule name:
		String name = String.valueOf(rule.getName());
		
		// Remove white spaces:
		name = name.replaceAll(" ", "_");
		name = name.replaceAll("\t", "_");
		name = name.replaceAll("\n", "_");
		
		// Capitalize the rule name:
		if (name.length()>0) {
			String first = name.substring(0,1).toUpperCase();
			name = first + name.substring(1);
		}
		
		// Prepend "params":
		return "params" + name;
		
	}

	/**
	 * Get the parameters of a rule, as used in the state space for labeling transitions.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getParameters(StateSpace stateSpace, Rule rule) throws StateSpaceException {
		
		List<Node> nodes = new ArrayList<Node>();
		
		// First check the "params..." properties:
		String value = stateSpace.getProperties().get(ParametersPropertiesManager.getParametersKey(rule));
		if (value!=null && value.trim().length()>0) {
			String[] names = value.split(",");
			for (int i=0; i<names.length; i++) {
				String name = names[i].trim();
				if (name.length()==0) {
					throw new StateSpaceException("Illegal rule parameters for rule \"" + rule.getName() + "\"");
				}
				Node node = findNodeByName(name, rule);
				if (node!=null) nodes.add(node);
			}
		} else {
			// Otherwise use the rule parameters...
			for (Parameter param : rule.getParameters()) {
				Node node = findNodeByName(param.getName(), rule);
				if (node!=null) nodes.add(node);
			}
		}
		
		return nodes;
	}

	/**
	 * Get the parameter types of a rule.
	 * @param stateSpace State space.
	 * @param rule The rule.
	 * @return List of parameter types.
	 * @throws StateSpaceException On errors.
	 */
	public static List<EClass> getParameterTypes(StateSpace stateSpace, Rule rule) throws StateSpaceException {
		List<Node> params = getParameters(stateSpace, rule);
		List<EClass> types = new ArrayList<EClass>(params.size());
		for (Node param : params) {
			types.add(param.getType());
		}
		return types;
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
				throw new IllegalArgumentException("All parameter nodes must have a unique, non-empty name");
			}
			value = value + name;
			if (i<nodes.size()-1) value = value + ",";
		}
		stateSpace.getProperties().put(getParametersKey(rule), value);
	}

	/*
	 * Find a node in a graph based on its name.
	 */
	static Node findNodeByName(String name, Graph graph) {
		for (Node node : graph.getNodes()) {
			if (name.equals(node.getName())) return node;
		}
		return null;
	}
	
	/*
	 * Find a node in a rule based on its name.
	 */
	static Node findNodeByName(String name, Rule rule) throws StateSpaceException {
		Node node = findNodeByName(name, rule.getLhs());
		if (node==null) {
			node = findNodeByName(name, rule.getRhs());
		}
		return node;
	}

}
