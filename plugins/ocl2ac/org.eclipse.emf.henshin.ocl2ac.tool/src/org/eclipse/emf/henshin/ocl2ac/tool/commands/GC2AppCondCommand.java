/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.Integrator;
import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.Lefter;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;

public class GC2AppCondCommand {

	private static final String HENSHIN = ".henshin";
	private IFile henshinFile = null;
	private Rule rule = null;
	private NestedConstraint constraint;
	public long translationTime = 0;

	public GC2AppCondCommand() {
	}

	public GC2AppCondCommand(IFile henshinFile) {
		this.henshinFile = henshinFile;
	}

	public boolean enableOptimizer = false;

	/*
	 * Start the integration
	 */
	public void integrateAndleft(NestedConstraint constraint, Rule rule) {
		if (rule != null) {
			this.rule = rule;
			this.constraint = constraint;

			Module module = rule.getModule();
			Copier copy = new Copier();

			Rule ruleCopyOriginal = (Rule) copy.copy(rule);
			copy.copyReferences();

			String UpdatedRuleName = rule.getName() + "_updated_" + constraint.getName();
			if (module.getUnit(UpdatedRuleName) != null) {
				Date date = new GregorianCalendar().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
				UpdatedRuleName += sdf.format(date);
			}

			rule.setName(UpdatedRuleName);

			long start = System.currentTimeMillis();

			// Integrate, i.e., shifting->prepare->translate2formula
			Integrator integrator = new Integrator(constraint, rule);
			System.out.println(this.getClass().getName() + ":" + enableOptimizer);
			integrator.setEnableOptimizer(enableOptimizer);
			integrator.integrate();

			NestedCondition condition = integrator.getShiftedCondition();
			// manage the result of empty overlap pairs
			if (condition == null) {
				System.out.println("Return in The class name: " + this.getClass().getSimpleName() + "<-");
				return;
			}
			System.out.println(condition);
			System.out.println(condition.getDomain());

			constraint.setDomain(condition.getDomain());
			constraint.setCondition(condition);

			org.eclipse.emf.henshin.model.Formula existingFormula = rule.getLhs().getFormula();
			// left the RAC to LAC
			Lefter lefter = new Lefter(rule);
			lefter.enableOptimizer = enableOptimizer;
			lefter.left();
			enableOptimizer = false;
			// add the original nac of the rule to the result
			if (existingFormula != null) {
				And henshinAnd = HenshinFactory.eINSTANCE.createAnd();
				henshinAnd.setLeft(existingFormula);
				henshinAnd.setRight(rule.getLhs().getFormula());
				rule.getLhs().setFormula(henshinAnd);
			}

			long stop = System.currentTimeMillis();
			translationTime = stop - start;
			// Persist in Place
			module.getUnits().add(ruleCopyOriginal);
			persistTheUpdatedRuleInPlace(module);
		}
	}

	/**
	 * 
	 * @param graph
	 * @return
	 */
	private EList<org.eclipse.emf.henshin.model.Edge> getEdgesTypedContainment(
			org.eclipse.emf.henshin.model.Graph graph) {
		EList<org.eclipse.emf.henshin.model.Edge> containmentEdges = new BasicEList<org.eclipse.emf.henshin.model.Edge>();

		for (org.eclipse.emf.henshin.model.Edge edge : graph.getEdges()) {
			if (edge.getType().isContainment())
				containmentEdges.add(edge);
		}
		return containmentEdges;
	}

	/*
	 * Start the integration
	 */
	public void integrateAndleftInPlace(NestedConstraint constraint, Rule rule) {
		if (rule != null) {
			this.rule = rule;
			this.constraint = constraint;
			long start = System.currentTimeMillis();

			// Integrate, i.e., shifting->prepare->translate2formula
			Integrator integrator = new Integrator(constraint, rule);
			System.out.println(this.getClass().getName() + ":" + enableOptimizer);
			integrator.setEnableOptimizer(enableOptimizer);
			integrator.integrate();

			NestedCondition condition = integrator.getShiftedCondition();
			// manage the result of empty overlap pairs
			if (condition == null) {
				System.out.println("Return in The class name: " + this.getClass().getSimpleName() + "<-");
				return;
			}
			System.out.println(condition);
			System.out.println(condition.getDomain());

			constraint.setDomain(condition.getDomain());
			constraint.setCondition(condition);

			org.eclipse.emf.henshin.model.Formula existingFormula = rule.getLhs().getFormula();

			// left the RAC to LAC
			Lefter lefter = new Lefter(rule);
			lefter.enableOptimizer = enableOptimizer;
			lefter.left();

			// add the original nac of the rule to the result
			if (existingFormula != null) {
				And henshinAnd = HenshinFactory.eINSTANCE.createAnd();
				henshinAnd.setLeft(existingFormula);
				henshinAnd.setRight(rule.getLhs().getFormula());
				rule.getLhs().setFormula(henshinAnd);
			}

			long stop = System.currentTimeMillis();
			translationTime = stop - start;
		}
	}

	private void persistTheUpdatedRuleInPlace(Module module) {
		String path = henshinFile.getLocation().toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(module, path);
		try {
			henshinFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
