package org.eclipse.emf.henshin.monitoring.ui.util;

import java.util.LinkedList;

public class RuleVisualizationData {
	private String ruleName;
	private LinkedList<String> nodeNames;
	private LinkedList<Long> numberOfBacktracking;
	private long[][] dataMap;
	private LinkedList<Long> investigated;
	private LinkedList<Long> domainSize;


	public RuleVisualizationData(String ruleName){
		this.ruleName=ruleName;
	}
	
	public String getRuleName() {
		return this.ruleName;
	}

	public LinkedList<String> getNodeNames() {
		return this.nodeNames;
	}
	
	public void setNodeNames(LinkedList<String> nodeNames) {
		this.nodeNames = nodeNames;
	}

	public LinkedList<Long> getNumberOfBacktracking() {
		return this.numberOfBacktracking;
	}

	public void setNumberOfBacktracking(LinkedList<Long> numberOfBacktracking) {
		this.numberOfBacktracking = numberOfBacktracking;
	}

	public long[][] getDataMap() {
		return this.dataMap;
	}

	public void setDataMap(long[][] dataMap) {
		this.dataMap = dataMap;
	}

	public LinkedList<Long> getInvestigated() {
		return this.investigated;
	}

	public void setInvestigated(LinkedList<Long> investigated) {
		this.investigated = investigated;
	}

	public LinkedList<Long> getDomainSize() {
		return this.domainSize;
	}

	public void setDomainSize(LinkedList<Long> domainSize) {
		this.domainSize = domainSize;
	}

}
