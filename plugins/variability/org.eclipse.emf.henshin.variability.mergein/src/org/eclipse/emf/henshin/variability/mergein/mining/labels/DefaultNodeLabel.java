package org.eclipse.emf.henshin.variability.mergein.mining.labels;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

/**
 * This class represents the label of the graph nodes under mining. It
 * stores the type of a given node, and supports hashCode() and equals()
 * methods for the comparison of different instances. 
 * 
 * @author strüber
 *
 */ 
public class DefaultNodeLabel implements INodeLabel {

	protected EObject type;
	protected Type actionType;
	
	public DefaultNodeLabel(HenshinNode node) {
		this.type = node.getType();
		this.actionType = node.getActionType();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(actionType.name());
		if (type instanceof ENamedElement) {
			result.append("");
			result.append(((ENamedElement)type).getName());
		}
		return result.toString();
	}


	public EObject getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DefaultNodeLabel)) {
			return false;
		}

		DefaultNodeLabel other = (DefaultNodeLabel) o;

		if (!other.getType().equals(this.getType())) {
			return false;
		}
		if (!other.getActionType().equals(this.getActionType())) {
			return false;
		}
		return true;
	}

	private Type getActionType() {
		return actionType;
	}

	@Override
	public int hashCode() {
		return type.hashCode() + actionType.hashCode();
	}

	@Override
	public String getLabelName() {
		return toString();
	}
}

