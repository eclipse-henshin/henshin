/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.ocl2gc.core.Completer;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.Constants;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import org.eclipse.emf.henshin.ocl2ac.tool.commands.OCL2LaxCondCommand;
import laxcondition.Condition;
import laxcondition.util.extensions.LaxConditionSimplifier;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.Variable;
import nestedcondition.util.extensions.NestedConditionSimplifier;
import nestedconstraintmodel.NestedConstraintModel;
import nestedconstraintmodel.NestedconstraintmodelFactory;
import nestedconstraintmodel.NestedconstraintmodelPackage;

public class OCL2GCAction implements IObjectActionDelegate {

	private static final String OCLAS = ".oclas";
	private static final String ECORE = ".ecore";
	private Shell shell;
	private List<IFile> files = null;
	private IFile ecoreFile = null;
	private IFile oclasFile = null;

	/**
	 * Constructor for TransformAction.
	 */
	public OCL2GCAction() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (checkFiles()) {
			Cursor oldCursor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getCursor();
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.setCursor(new Cursor(null, SWT.CURSOR_WAIT));

			long start = System.currentTimeMillis();
			// 1. Get List of lax Conditions
			OCL2LaxCondCommand translator = new OCL2LaxCondCommand(oclasFile, ecoreFile);
			List<Condition> arrayListLaxConditions = translator.getSetofLaxConditions();
			System.out.println("NN: arrayListLaxConditions size is: " + arrayListLaxConditions.size());
			EPackage typeModel = translator.getTypeModel();

			// 2. Simplify each lax condition
			simplifyLaxConditions(arrayListLaxConditions);

			// 3. Complete each lax condition to NestedConstraints
			List<NestedConstraint> arrayListNestedConstraints = completeLaxConditions(translator,
					arrayListLaxConditions);

			// 4. Simplify each NestedConstraint
			simplifyNextedConstraints(arrayListNestedConstraints);

			long stop = System.currentTimeMillis();

			long translationTime = stop - start;
			System.out.println("[#" + translationTime + ")]");

			// 5. Persist the simplified nested constraints
			NestedconstraintmodelPackage.eINSTANCE.eClass();
			NestedConstraintModel constraintmodel = NestedconstraintmodelFactory.eINSTANCE
					.createNestedConstraintModel();
			constraintmodel.setName(typeModel.getName() + "Constraints");

			constraintmodel.getNestedconstrainmodels().addAll(arrayListNestedConstraints);

			System.out.println("[# OCL Invariants is:(" + translator.invariants.size() + ")]");
			System.out.println("[# Resulted NGC is:(" + arrayListNestedConstraints.size() + ")]");
			Date date = new GregorianCalendar().getTime();
			persistNestedConstraintsModel(date, constraintmodel);
			shell.setCursor(oldCursor);

			MessageDialog.openInformation(shell, "OCL2AC",
					"The translation time is: (" + (translationTime / (double) 1000) + ") second(s).");

		} else {
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Translation", "Translation can not be executed on the input.");
		}

	}

	/**
	 * 
	 * @param arrayListNestedConstraints
	 */
	private void simplifyNextedConstraints(List<NestedConstraint> arrayListNestedConstraints) {
		for (NestedConstraint nestedconstrain : arrayListNestedConstraints) {
			try {
				NestedConditionSimplifier simplifierNGC = new NestedConditionSimplifier(nestedconstrain);
				boolean bc = simplifierNGC.simplify();
			} catch (Exception e) {
				System.err.println("The nestedconstrain " + nestedconstrain.getName() + " is not simplified well");
			}
		}
	}

	/**
	 * 
	 * @param translator
	 * @param arrayListLaxConditions
	 * @return
	 */
	private List<NestedConstraint> completeLaxConditions(OCL2LaxCondCommand translator,
			List<Condition> arrayListLaxConditions) {
		List<NestedConstraint> arrayListNestedConstraints = new ArrayList<NestedConstraint>();
		List<String> conditionVarNames = null;
		for (Condition condition : arrayListLaxConditions) {
			try {
				conditionVarNames = translator.mapCon2Var.get(condition);
				Completer completer = new Completer(condition);
				long timeNeeded = completer.complete();
				NestedConstraint nestedConstraint = completer.getConstraint();
				NestedCondition nestedCondition = nestedConstraint.getCondition();

				if (conditionVarNames != null)
					addVariables2Condition(nestedCondition, conditionVarNames);

				arrayListNestedConstraints.add(nestedConstraint);
			} catch (Exception e) {
				System.err.println("The condition " + condition.getName() + " is not completed well");
			}
		}
		return arrayListNestedConstraints;
	}

	/**
	 * 
	 * @param arrayListLaxConditions
	 */
	private void simplifyLaxConditions(List<Condition> arrayListLaxConditions) {
		for (Condition condition : arrayListLaxConditions) {
			try {
				LaxConditionSimplifier simplifier = new LaxConditionSimplifier(condition);
				simplifier.simplify();
			} catch (Exception e) {
				System.err.println("The condition " + condition.getName() + " is not simplified well");
			}
		}
	}

	/**
	 * Add variables to the completed nested condition
	 * 
	 * @param nestedCondition
	 * @param conditionVarNames
	 */
	private void addVariables2Condition(NestedCondition nestedCondition, List<String> conditionVarNames) {
		for (String v : conditionVarNames) {
			Variable conVar = NestedconditionFactory.eINSTANCE.createVariable();
			conVar.setName(v);
			if (!nestedCondition.getVariables().contains(conVar))
				nestedCondition.getVariables().add(conVar);
		}
	}

	/**
	 * 
	 * @param date
	 * @param nestedConstraintModel
	 */
	private void persistNestedConstraintsModel(Date date, NestedConstraintModel nestedConstraintModel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String path = oclasFile.getParent().getLocation().toOSString();// append(Constants.NESTED_CONSTRAINTS
																		// +
																		// sdf.format(date)).toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(nestedConstraintModel,
				nestedConstraintModel.getName().concat("_" + sdf.format(date) + Constants.NESTED_CONSTRAINTS_MODEL));
		try {
			oclasFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkFiles() {
		IFile file1 = files.get(0);
		IFile file2 = files.get(1);
		if (!(file1.getName().endsWith(ECORE) || file2.getName().endsWith(ECORE)))
			return false;
		if (!(file1.getName().endsWith(OCLAS) || file2.getName().endsWith(OCLAS)))
			return false;
		if (file1.getName().endsWith(ECORE)) {
			ecoreFile = file1;
			oclasFile = file2;
		} else {
			ecoreFile = file2;
			oclasFile = file1;
		}
		return true;
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@SuppressWarnings("unchecked")
	public void selectionChanged(IAction action, ISelection selection) {
		files = new ArrayList<IFile>();
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			List<Object> objects = ss.toList();
			for (Object o : objects) {
				files.add((IFile) o);
			}
		}
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
