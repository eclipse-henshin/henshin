package org.eclipse.emf.henshin.model.compact;

import java.text.ParseException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;

/**
 * Compact Node
 * Contains a {@link org.eclipse.emf.henshin.model.Node}-Instance
 * Provides compact Methods to create Node-Components 
 * and Edges, using the {@link org.eclipse.emf.henshin.model.Rule} the Node is contained in.
 * @author Johannes Ludwig
 */
public class CNode {

	private Node node;
	
	protected CNode(Node node) {
		this.node = node;
	}
	
	/**
	 * Tests if an Edge of the given Type can be created between this Node and the given target-Node
	 * @param target the target of the Edge
	 * @param ref type of the Edge
	 * @return true - if the Edge can be created. false - otherwise
	 */
	public boolean canCreateEdge(CNode target, EReference ref) {
		return node.getGraph().getRule().canCreateEdge(getNode(), target.getNode(), ref);
	}
	
	/**
	* Tests if an Edge of the given Type can be created between this Node and the given target-Node
	 * @param target the target of the Edge
	 * @param ref String representation of the type of the Edge
	 * @return true - if the Edge can be created. false - otherwise
	 */
	public boolean canCreateEdge(CNode target, String ref)
	{
		return canCreateEdge(target, getEReferenceByName(ref));
	}
	
	/**
	 * Creates an Edge from the Node to another Node.
	 * @param target targeted Node
	 * @param ref Type of the Edge
	 * @param action Action of the Edge e.g. preserve, create etc. default: preserve
	 * @throws RuntimeException if Action passed via String does not conform to Action-Format
	 */
	public CNode createEdge(CNode target, EReference ref, Action action) {
		Edge edge = node.getGraph().getRule().createEdge(node, target.getNode(), ref);
		if(edge == null)
		{
			throw new RuntimeException("Failed to create Edge");
		}
		edge.setAction(action);
		return this;
	}
	
	/*
	 * Operations of all createEdge-Methods are delegated to the Method on top,
	 * using <<preserve>> as default Action and converting all Strings into Literals via Helper-Methods
	 * 
	 */
	/**
	 * createEdge using a String as Reference and not specifying an Action.
	 */
	public CNode createEdge(CNode target, String ref)
	{
		return createEdge(target, getEReferenceByName(ref), new Action(Action.Type.PRESERVE));
	}
	
	/**
	 * createEdge not specifying an Action.
	 */
	public CNode createEdge(CNode target, EReference ref)
	{
		return createEdge(target, ref, new Action(Action.Type.PRESERVE));
	}
	
	/**
	 * createEdge using a String as Reference and specifying an Action using a String.
	 */
	public CNode createEdge(CNode target, String ref, String action) {
		try {
			return createEdge(target, getEReferenceByName(ref), Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	/**
	 * createEdge using a String as Reference and specifying an Action.
	 */
	public CNode createEdge(CNode target, String ref, Action action) {
		return createEdge(target, getEReferenceByName(ref), action);
	}
	
	/**
	 * createEdge using a String to specify an Action.
	 */
	public CNode createEdge(CNode target, EReference ref, String action){
		try {
			return createEdge(target, ref, Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	
	/**
	 * Adds an Attribute to the contained Node
	 * Can use Strings to convert them to EAttribute-Literals
	 * @param attribute Datatype of the Attribute
	 * @param value String representing the Value of the Attribute
	 * @param action Action of the Attribute e.g. create, delete etc.
	 * Parameter is optional. If not specified, default Action will be the parent Nodes Action.
	 * @throws RuntimeException if Action passed via String does not conform to Action-Format
	 */
	public CNode createAttribute(EAttribute attribute, String value, Action action) {
		String result = "";
		if(value.contains("->")) {
			int index = value.indexOf("->");
			result = value.substring(index+2);
			value = value.substring(0,index);
		}
		Attribute att = HenshinFactory.eINSTANCE.createAttribute(node, attribute, value);
		att.setAction(action);
		node.getAttributes().add(att);
		if(!result.equals("")) {
			setAttributeResult(attribute, result);
		}
		
		return this;
	}
	
	/*
	 * Operations of all createAttribute-Methods are delegated to the Method on top,
	 * using <<preserve>> as default Action and converting all Strings into Literals via Helper-Methods
	 * 
	 */
	/**
	 * createAttribute using Strings to specify the Attribute-Type, while not specifying an Action
	 */
	public CNode createAttribute(String attribute, String value) {
		return createAttribute(getEAttributeByName(attribute), value, node.getAction());
	}
	
	/**
	 * createAttribute without specifying an Action
	 */
	public CNode createAttribute(EAttribute attribute, String value) {
		return createAttribute(attribute, value, node.getAction());
	}
	
	/**
	 * createAttribute using Strings to specify the Value, the Attribute-Type and the Action
	 */
	public CNode createAttribute(String attribute, String value, String action) {
		try {
			return createAttribute(getEAttributeByName(attribute), value, Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	/**
	 * createAttribute using a String to specify the Action
	 */
	public CNode createAttribute(EAttribute attribute, String value, String action){
		try {
			return createAttribute(attribute, value, Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	/**
	 * createAttribute using a String to specify the Attribute-Type
	 */
	public CNode createAttribute(String attribute, String value, Action action) {
		return createAttribute(getEAttributeByName(attribute), value, action);
	}
	

	/**
	 * Defines the result of an Attribute-Evaluation
	 * @param attribute the Attribute-Type of the Attribute to evaluate
	 * @param resultValue the value after the Evaluation
	 */
	private CNode setAttributeResult(EAttribute attribute, String resultValue) {
		Node imageNode = node.getGraph().getRule().getMappings().getImage(node, node.getGraph().getRule().getRhs());
		Attribute imageAttribute = imageNode.getAttribute(attribute);
		imageAttribute.setValue(resultValue);
		return this;
	}
	
	public void setName(String name) {
		node.setName(name);
	}

	public void setNode(Node node) {
		this.node = node;
	}


	public Node getNode() {
		return node;
	}

	//Helper Methods
	
	private EReference getEReferenceByName(String name) {
		EReference result = null;
		for(EReference ref : node.getType().getEAllReferences())
		{
			if(ref.getName().equals(name))
			{
				result = ref;
				break;
			}
		}
		if(result == null) {
			throw new RuntimeException("No Reference for "+name+" found.");
		}
		return result;
	}
	
	private EAttribute getEAttributeByName(String name) {
		
		EAttribute result = null;
		for(EAttribute att : node.getType().getEAllAttributes())
		{
			if(att.getName().equals(name))
			{
				result = att;
				break;
			}
		}
		if(result == null) {
			throw new RuntimeException("No Attribute for "+name+" found.");
		}
		return result;
	}
	
	
}
