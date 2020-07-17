package org.eclipse.emf.henshin.monitoring.ui.util;

import java.util.LinkedList;

public class UnitVisualizationData {

	private String label;
	private long duration;
	private UnitTypes type;

	private LinkedList<UnitVisualizationData> calls;
	
	private int tabIdx=-1;
	

	public UnitVisualizationData(String label, long start,long stop,String typeName) {
		this.label=label;
		long startMS=start/1000000; //ns in ms
		long stopMS=stop/1000000; //ns in ms
		this.duration=stopMS-startMS;
		this.calls=new LinkedList<UnitVisualizationData>();
		this.type=selectType(typeName);
		
	}
	
	public UnitVisualizationData(String label, long duration) {
		this.label=label;
		this.type=UnitTypes.NONE;
		this.duration=duration;
	}
	
	private UnitTypes selectType(String typeName) {
		switch(typeName){
			case "Rule": return UnitTypes.RULE;
			case "LoopUnit": return UnitTypes.LOOP_UNIT;
			case "IteratedUnit": return UnitTypes.ITERATED_UNIT;
			case "ConditionalUnit": return UnitTypes.CONDITIONAL_UNIT;
			case "SequentialUnit": return UnitTypes.SEQUENTIAL_UNIT;
			case "PriorityUnit": return UnitTypes.PRIORITY_UNIT;
			case "IndependentUnit": return UnitTypes.INDEPENDENT_UNIT;
		}
		return UnitTypes.NONE;
	}

	public String getLabel() {
		return this.label;
	}

	public LinkedList<UnitVisualizationData> getCalls() {
		return this.calls;
	}

	public void setCalls(LinkedList<UnitVisualizationData> calls) {
		this.calls = calls;
	}

	public long getDuration() {
		return this.duration;
	}

	public UnitTypes getType() {
		return this.type;
	}

	public int getTabIdx() {
		return tabIdx;
	}

	public void setTabIdx(int tabIdx) {
		this.tabIdx = tabIdx;
	}

	
}
