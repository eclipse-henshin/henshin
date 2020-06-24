package org.eclipse.emf.henshin.model.compact;

import java.text.ParseException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Compact Rule
 * Contains a {@link import org.eclipse.emf.henshin.model.Rule;}-Instance
 * Provides compact Methods to create Rule-Components
 * @author Johannes Ludwig
 */
public class CRule extends CUnit{

	
	
	protected CRule(String name) {
		super(HenshinFactory.eINSTANCE.createRule(name));
	}
	
	protected CRule(Rule rule) {
		super(rule);
	}

	/**
	 * Creates a Node
	 * Can use Strings to convert them to EClass-Literals
	 * Please note, that using Strings will not function correctly,
	 * if two or more EPackages containing EClasses with the same Name exist!
	 * @param classifier Type of the Node
	 * @param action Action of the Node. default: preserve
	 */
	public CNode createNode(EClass classifier, Action action) {
		Node node = getUnit().createNode(classifier);
		node.setAction(action);
		
		//Make Multi-Unit Parameters correct
		//Necessary because Henshin doesn't set the ParameterKind Values when creating new Multi-Rules
		if(action.isMulti()) {
			restoreParameterKindsInMultiRules();
		}
		return new CNode(node);
	}

	/*
	 * Operations of all createNode-Methods are delegated to the Method on top,
	 * using <<preserve>> as default Action and converting all Strings into Literals via Helper-Methods
	 * 
	 */
	/**
	 * createNode using a String as Classifier without specifying an Action
	 */
	public CNode createNode(String classifier) {
		return createNode(getEClassByName(classifier), new Action(Action.Type.PRESERVE));
	}
	
	/**
	 * createNode using Strings as Classifier and Action
	 */
	public CNode createNode(String classifier, String action){
		try {
			return createNode(getEClassByName(classifier), Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	/**
	 * createNode using a String as Classifier
	 */
	public CNode createNode(String classifier, Action action) {
		return createNode(getEClassByName(classifier),action);
	}
	
	/**
	 * createNode without specifying an Action
	 */
	public CNode createNode(EClass classifier) {
		return createNode(classifier, new Action(Action.Type.PRESERVE));
	}
	
	/**
	 * createNode using a String as Action
	 */
	public CNode createNode(EClass classifier, String action){
		try {
			return createNode(classifier,Action.parse(action));
		} catch (ParseException e) {
			throw new RuntimeException(action +" is not a valid Action");
		}
	}
	
	/**
	 * Creates an AttributeCondition on the Rule.
	 * @param name The Name of the Condition
	 * @param value The logical Operation representing the value of the AttributeCondition
	 */
	public CRule createAttributeCondition(String name, String value) {
		AttributeCondition cond = HenshinFactory.eINSTANCE.createAttributeCondition();
		cond.setName(name);
		cond.setConditionText(value);
		
		getUnit().getAttributeConditions().add(cond);
		
		return this;
	}
	/**
	 * Sets a Formula for the Lhs Graph, making it a Pre-Condition
	 * @param formula the Formula to set as Pre-Condition
	 */
	public CRule setPreConditionFormula(Formula formula) {
		getUnit().getLhs().setFormula(formula);
		return this;
	}

	/**
	 * Returns the PAC with the given Name
	 * @param name the Identifier of the PAC
	 * @return the PAC with the given Name
	 */
	public NestedCondition getPAC(String name) {
		return getCondition(name,true);
	}
	
	/**
	 * Returns the NAC with the given Name
	 * @param name the Identifier of the PAC
	 * @return the NAC with the given Name. Packed inside a Not-Formula
	 */
	public Not getNAC(String name) {
		NestedCondition nc = getCondition(name, false);
		Not not = (Not) nc.eContainer();
		return not;
	}
	
	/**
	 * Returns a NestedCondition with the specified Name and Type
	 * @param name the Name of the Condition
	 * @param positive - true means PAC, false means NAC
	 * @return the Condition
	 */
	private NestedCondition getCondition(String name, boolean positive) {
		NestedCondition res = null;
		if(positive) {
			res = getUnit().getLhs().getPAC(name);
		} 
		else {
			res = getUnit().getLhs().getNAC(name);
		}
		
		if(res == null) {
			throw new RuntimeException("Could not find any Condition named: "+name);
		}
		return res;
	}
	
	//if a new Multi-Rule is created programmatically, ParameterKinds are not set automatically.
	protected void restoreParameterKindsInMultiRules() {
		EList<Rule> multis = getUnit().getAllMultiRules();
		for(Parameter p : getUnit().getParameters())
		{
			for(Rule r : multis)
			{
				r.getParameter(p.getName()).setKind(p.getKind());
			}
		}
	}
	
	public Rule getUnit() {
		return (Rule)super.getUnit();
	}
	
	@SuppressWarnings("unused")
	public void setUnit(Unit rule) {
		//Assure that the given Unit is a Rule
		try {
			Rule r = (Rule) rule;
		} catch(ClassCastException e)
		{
			throw new RuntimeException("Given Unit is not a Rule!");
		}
		super.setUnit(rule);
	}
}
