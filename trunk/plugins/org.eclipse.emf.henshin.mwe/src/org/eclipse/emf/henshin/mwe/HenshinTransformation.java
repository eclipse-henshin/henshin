package org.eclipse.emf.henshin.mwe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class HenshinTransformation extends AbstractWorkflowComponent2 {

	boolean applicationRequired = false;

	Collection<Parameter> parameters = new ArrayList<Parameter>();

	Collection<String> modelSlots = new ArrayList<String>();
	String unit;
	String transformationSlot;

	public void setApplicationRequired(boolean applicationRequired) {
		this.applicationRequired = applicationRequired;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setTransformationSlot(String transformationSlot) {
		this.transformationSlot = transformationSlot;
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		boolean result = false;
		InterpreterEngine engine = getEngine(ctx);
		TransformationUnit unit = (TransformationUnit) ctx
				.get(transformationSlot);
		UnitApplication unitApplication = new UnitApplication(engine, unit);
		unitApplication.setParameterValues(getParameterValues(ctx));
		try {
			result = unitApplication.execute();
		} catch (Exception e) {
			System.out.println("The exception: " + e.getCause());
			throw new WorkflowInterruptedException(
					"An Exception occured in Henshin transformation: ", e);
		}
		if (!result && applicationRequired)
			throw new WorkflowInterruptedException(
					"Henshin transformation not applicable!");
	}

	private Map<String, Object> getParameterValues(WorkflowContext ctx) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Parameter p : this.parameters)
			parameters.put(p.name, p.type.getValue(ctx));
		return parameters;
	}

	private InterpreterEngine getEngine(WorkflowContext ctx) {
		EmfGraph graph = new EmfGraph();
		for (String modelSlot : modelSlots)
			graph.addRoot((EObject) ctx.get(modelSlot));
		return new EmfEngine(graph);
	}

	public void addModelSlot(String modelSlot) {
		modelSlots.add(modelSlot);
	}

	@Override
	protected void checkConfigurationInternal(Issues issues) {
	}

	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

}
