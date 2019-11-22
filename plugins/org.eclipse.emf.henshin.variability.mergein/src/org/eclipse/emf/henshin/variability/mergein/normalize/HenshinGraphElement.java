package org.eclipse.emf.henshin.variability.mergein.normalize;

public abstract class HenshinGraphElement {
	private HenshinGraph henshinGraph;
	public HenshinGraphElement(HenshinGraph henshinGraph) {
		this.henshinGraph = henshinGraph;
	}
	public HenshinGraph getHenshinGraph() {
		return henshinGraph;
	}
	public void setHenshinGraph(HenshinGraph henshinGraph) {
		this.henshinGraph = henshinGraph;
	}
}
