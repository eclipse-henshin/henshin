package org.eclipse.emf.henshin.variability.mergein.mining.labels;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinAttributeNode;

/**
 * This class represents the label of the graph nodes under mining. It
 * stores the type of a given node, and supports hashCode() and equals()
 * methods for the comparison of different instances. 
 * 
 * @author strüber
 *
 */ 
public class DefaultAttributeNodeLabel extends DefaultNodeLabel {

	protected String value;
	
	public DefaultAttributeNodeLabel(HenshinAttributeNode node) {
		super(node);
		this.value = node.getValue();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(actionType.name());
		if (type instanceof ENamedElement) {
			result.append(" ");
			result.append(((ENamedElement)type).getName());
		}
		result.append(" ");
		result.append(value);
		return result.toString();
	}


	public EObject getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DefaultAttributeNodeLabel)) {
			return false;
		}

		DefaultAttributeNodeLabel other = (DefaultAttributeNodeLabel) o;

		if (!other.getType().equals(this.getType())) {
			return false;
		}
		if (!other.getActionType().equals(this.getActionType())) {
			return false;
		}
		if (!other.getValue().equals(this.getValue())) {
			return false;
		}
		return true;
	}

	private String getValue() {
		return value;
	}

	private Type getActionType() {
		return actionType;
	}

	@Override
	public int hashCode() {
		return type.hashCode() + actionType.hashCode() + value.hashCode();
	}

	@Override
	public String getLabelName() {
		return toString();
	}
}

