package org.eclipse.emf.henshin.giraph;

import java.util.Collection;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class GiraphConfig {

	protected String projectName = "giraph-henshin-examples";

	protected String packageName = "org.apache.giraph.henshin.examples";

	protected String className = "Main";

	protected String inputName = "Input";

	protected Unit mainUnit;

	protected Graph inputGraph;

	protected boolean masterLogging = true;

	protected boolean testEnvironment = false;

	protected boolean useUUIDs = false;

	protected boolean vertexLogging = false;

	public GiraphConfig() {
		super();
	}

	public String getClassName() {
		return className;
	}

	public Graph getInputGraph() {
		return inputGraph;
	}

	public String getInputName() {
		return inputName;
	}

	public Unit getMainUnit() {
		return mainUnit;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public boolean isMasterLogging() {
		return masterLogging;
	}

	public boolean isTestEnvironment() {
		return testEnvironment;
	}

	public boolean isUseUUIDs() {
		return useUUIDs;
	}

	public boolean isVertexLogging() {
		return vertexLogging;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setInputGraph(Graph inputGraph) {
		this.inputGraph = inputGraph;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public void setMainUnit(Unit mainUnit) {
		this.mainUnit = mainUnit;
		if (inputGraph == null) {
			Collection<Rule> rules = GiraphUtil.collectRules(mainUnit);
			if (!rules.isEmpty()) {
				Rule rule = rules.iterator().next();
				inputGraph = rule.getLhs();
				if (inputGraph.getNodes().isEmpty() && !rule.getMultiRules().isEmpty()) {
					inputGraph = rule.getMultiRules().get(0).getLhs();
				}
			}
		}
		if (mainUnit.getName() != null && !mainUnit.getName().trim().isEmpty()) {
			className = mainUnit.getName().trim();
			className = className.substring(0, 1).toUpperCase() + className.substring(1);
			inputName = className;
		}
	}

	public void setMasterLogging(boolean masterLogging) {
		this.masterLogging = masterLogging;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setTestEnvironment(boolean setupTestEnvironment) {
		this.testEnvironment = setupTestEnvironment;
	}

	public void setUseUUIDs(boolean useUUIDs) {
		this.useUUIDs = useUUIDs;
	}

	public void setVertexLogging(boolean vertexLogging) {
		this.vertexLogging = vertexLogging;
	}

}