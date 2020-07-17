package org.eclipse.emf.henshin.interpreter.monitoring;

public class VariableCheck {
	private String variableId;
	private long domainSize;
	private long checkedModelElements;
	private boolean initNewDomain;
	
	public VariableCheck(String variableId,boolean initNewDomain){
		this.variableId=variableId;
		this.domainSize=-1;
		this.checkedModelElements=0;
		this.initNewDomain=initNewDomain;
	}
	
	public void incCheckedModelElements(){
		this.checkedModelElements++;
	}
	
	public String getVariableId() {
		return this.variableId;
	}

	public long getDomainSize() {
		return this.domainSize;
	}

	public void setDomainSize(long domainSize) {
		this.domainSize = domainSize;
	}

	public long getCheckedModelElements() {
		return this.checkedModelElements;
	}

	public void setCheckedModelElements(long checkedModelElements) {
		this.checkedModelElements = checkedModelElements;
	}

	public boolean isInitNewDomain() {
		return this.initNewDomain;
	}

}
