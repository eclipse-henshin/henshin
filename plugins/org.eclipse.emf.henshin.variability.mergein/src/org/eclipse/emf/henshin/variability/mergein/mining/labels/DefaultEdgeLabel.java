package org.eclipse.emf.henshin.variability.mergein.mining.labels;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;

/**
 * This class represents the label of the graph edges under mining. It stores
 * the type of a given edge, and supports hashCode() and equals() methods for
 * the comparison of different instances.
 * 
 * @author strüber
 *
 */
public class DefaultEdgeLabel implements IEdgeLabel {

	private EReference type;
	private Type actionType;

	public DefaultEdgeLabel(HenshinEdge edge) {

		this.type = edge.getType();
		this.actionType = edge.getActionType();

		if (type==null || actionType == null) {
			System.err.println("type or actionType was null");
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(actionType.name());
		result.append("");
		result.append(type.getName());
		return result.toString();
	}

	public EReference getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DefaultEdgeLabel)) {
			return false;
		}

		DefaultEdgeLabel other = (DefaultEdgeLabel) o;

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
		int typeHashcode = (type==null)? 0 : type.hashCode();
		int actionHashcode = (actionType==null)? 0 : actionType.hashCode();
		return typeHashcode + actionHashcode;
	}

	@Override
	public String getLabelName() {
		return toString();
	}
}
