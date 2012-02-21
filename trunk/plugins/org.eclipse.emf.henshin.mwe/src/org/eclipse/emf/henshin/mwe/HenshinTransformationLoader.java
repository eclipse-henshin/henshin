package org.eclipse.emf.henshin.mwe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class HenshinTransformationLoader extends AbstractWorkflowComponent2 {

	String uri;

	Collection<SlotAssignment> unitAssignments = new ArrayList<SlotAssignment>();

	public void addUnitAssignment(SlotAssignment assignment) {
		unitAssignments.add(assignment);
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		HenshinPackageImpl.init();

		final Resource res;
		try {
			ResourceSet resSet = new ResourceSetImpl();
			res = resSet.getResource(URI.createURI(uri), true);
			if (res == null)
				throw new WorkflowInterruptedException(
						"Unable to load henshin resource: " + uri);
			if (!res.isLoaded())
				res.load(Collections.EMPTY_MAP);
		} catch (Exception e) {
			throw new WorkflowInterruptedException(
					"An error occured while loading " + uri + " : "
							+ e.getMessage());
		}
		if (res.getContents().size() < 1)
			throw new WorkflowInterruptedException(
					"Henshin transformation is empty!");
		EObject root = res.getContents().get(0);
		if (!(root instanceof TransformationSystem))
			throw new WorkflowInterruptedException(
					"Resource does not contain a Henshin transformation!");
		TransformationSystem trafoSys = (TransformationSystem) root;
		for (SlotAssignment assignemnt : unitAssignments) {
			TransformationUnit unit = null;
			if (assignemnt.isRule)
				unit = trafoSys.findRuleByName(assignemnt.unit);
			else
				unit = trafoSys.findUnitByName(assignemnt.unit);

			if (unit == null)
				throw new WorkflowInterruptedException(
						"TransformationSystem does not contain "
								+ (assignemnt.isRule ? "Rule"
										: "TransformationUnit") + ": "
								+ assignemnt.unit);
			ctx.set(assignemnt.slot, unit);
		}

	}

	public static class SlotAssignment {
		String unit;
		String slot;
		boolean isRule = true;

		public void setIsRule(boolean isRule) {
			this.isRule = isRule;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public void setSlot(String slot) {
			this.slot = slot;
		}

	}

}
