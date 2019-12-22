package org.eclipse.emf.henshin.variability.mergein.normalize;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action.Type;

public class HenshinNode extends HenshinGraphElement {
	EObject type;
	Type actionType;
	String ruleName;


	public HenshinNode(HenshinGraph graph, EObject type, Type actionType, String ruleName) {
		super(graph);
		this.type = type;
		this.actionType = actionType;
		this.ruleName = ruleName;
	}

	public EObject getType() {
		return type;
	}

	public void setType(EObject type) {
		this.type = type;
	}

	public Type getActionType() {
		return actionType;
	}

	public void setActionType(Type actionType) {
		this.actionType = actionType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String rule) {
		this.ruleName = rule;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
//		result = prime * result + ((type == null) ? 0 : type.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		HenshinNode other = (HenshinNode) obj;
//		if (actionType != other.actionType)
//			return false;
//		if (type == null) {
//			if (other.type != null)
//				return false;
//		} else if (!type.equals(other.type))
//			return false;
//		return true;
//	}
}
