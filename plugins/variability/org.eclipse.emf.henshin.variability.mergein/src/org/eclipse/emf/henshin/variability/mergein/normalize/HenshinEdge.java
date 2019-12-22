package org.eclipse.emf.henshin.variability.mergein.normalize;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action.Type;

public class HenshinEdge extends HenshinGraphElement {
	private EReference type;
	private Type actionType;
	private String ruleName;
	
	public HenshinEdge(HenshinGraph graph, EReference type, Type actionType, String ruleName, boolean attributeEdge) {
		super(graph);
		this.type = type;
		this.actionType = actionType;
		this.ruleName = ruleName;
		
		if (type==null || actionType == null) {
			System.err.println("type or actionType was null");
		}
	}
	public EReference getType() {
		return type;
	}
	public void setType(EReference type) {
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
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
//		result = prime * result + ((type == null) ? 0 : type.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		HenshinEdge other = (HenshinEdge) obj;
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
