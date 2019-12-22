package org.eclipse.emf.henshin.variability;

import org.eclipse.emf.henshin.model.Unit;

/**
 * One entry of the {@link MatchingLog}.
 * 
 * @author Daniel Strüber
 *
 */
public class MatchingLogEntry {
	public MatchingLogEntry(Unit unit, boolean successful, long runtime, int graphNodes, int graphEdges) {
		super();
		this.unit = unit;
		this.successful = successful;
		this.runtime = runtime;
		this.graphNodes = graphNodes;
		this.graphEdges = graphEdges;
	}
	public MatchingLogEntry(Unit unit, boolean successful, long runtime, int graphNodes, int graphEdges, int numberOfMatches) {
		super();
		this.unit = unit;
		this.successful = successful;
		this.runtime = runtime;
		this.graphNodes = graphNodes;
		this.graphEdges = graphEdges;
		this.numberOfMatches = numberOfMatches;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	Unit unit;
	boolean successful;
	long runtime;
	public int getGraphNodes() {
		return graphNodes;
	}
	public void setGraphNodes(int graphNodes) {
		this.graphNodes = graphNodes;
	}
	public int getGraphEdges() {
		return graphEdges;
	}
	public void setGraphEdges(int graphEdges) {
		this.graphEdges = graphEdges;
	}
	private int graphNodes;
	private int graphEdges;
	private int numberOfMatches;
	
	public long getRuntime() {
		return runtime;
	}
	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}
	public int getNumberOfMatches() {
		return numberOfMatches;
	}
	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}
}
