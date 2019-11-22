package org.eclipse.emf.henshin.variability.mergein.normalize;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action.Type;

public class HenshinAttributeNode extends HenshinNode {
	String value;
	
	public HenshinAttributeNode(HenshinGraph graph, EObject type, Type actionType, String ruleName, String value) {
		super(graph,type, actionType, ruleName);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = super.hashCode();
//		result = prime * result + ((value == null) ? 0 : value.hashCode());
//		result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
//		result = prime * result + ((type == null) ? 0 : type.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (!super.equals(obj))
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		HenshinAttributeNode other = (HenshinAttributeNode) obj;
//		if (actionType != other.actionType)
//			return false;
//		if (type == null) {
//			if (other.type != null)
//				return false;
//		} else if (!type.equals(other.type))
//			return false;
//		if (value == null) {
//			if (other.value != null)
//				return false;
//		} else if (!value.equals(other.value))
//			return false;
//		return true;
//	}

}
