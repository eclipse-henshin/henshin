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
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
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

import compactconditionmodel.CompactConditionModel;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.core.Completer;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.Constants;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import laxcondition.Condition;
import laxcondition.LaxconditionPackage;
import laxcondition.util.extensions.LaxConditionSimplifier;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.Variable;
import nestedcondition.util.extensions.NestedConditionSimplifier;
import nestedconstraintmodel.NestedConstraintModel;
import nestedconstraintmodel.NestedconstraintmodelFactory;
import nestedconstraintmodel.NestedconstraintmodelPackage;

public class LaxCond2GCAction implements IObjectActionDelegate {

	private static final String COMPACTCONDITION = ".compactconditionmodel";
	private Shell shell;
	private IFile selectedFile = null;
	HashMap<Condition, List<String>> mapCon2Var = null;

	/**
	 * Constructor for TransformAction.
	 */
	public LaxCond2GCAction() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		mapCon2Var = new HashMap<Condition, List<String>>();
		if (checkFile()) {
			Cursor oldCursor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getCursor();
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.setCursor(new Cursor(null, SWT.CURSOR_WAIT));

			URI uri = URI.createPlatformResourceURI(this.selectedFile.getFullPath().toString(), true);
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			Resource resource = resourceSet.getResource(uri, true);
			EObject root = resource.getContents().get(0);

			long start = System.currentTimeMillis();

			// 1. Get List of lax Conditions
			List<Condition> arrayListLaxConditions = new ArrayList<Condition>();
			if (root instanceof CompactConditionModel) {
				arrayListLaxConditions.addAll(((CompactConditionModel) root).getCompactconditions());
			}

			System.out.println("arrayListLaxConditions size is: " + arrayListLaxConditions.size());

			// Construct mapCon2Var
			for (Condition cond : arrayListLaxConditions) {
				mapCon2Var.put(cond, getDistinctVariableNames(cond));
			}

			// 2. Simplify each lax condition
			simplifyLaxConditions(arrayListLaxConditions);

			// 3. Complete each lax condition to NestedConstraints
			List<NestedConstraint> arrayListNestedConstraints = completeLaxConditions(mapCon2Var,
					arrayListLaxConditions);

			// 4. Simplify each NestedConstraint
			simplifyNestedConstraints(arrayListNestedConstraints);
			long stop = System.currentTimeMillis();
			long translationTime = stop - start;
			System.out.println("[#" + translationTime + ")]");

			// 5. Persist the simplified nested constraints
			NestedconstraintmodelPackage.eINSTANCE.eClass();
			NestedConstraintModel constraintmodel = NestedconstraintmodelFactory.eINSTANCE
					.createNestedConstraintModel();
			constraintmodel.setName(arrayListNestedConstraints.get(0).getTypeGraph().getName() + "Constraints");

			constraintmodel.getNestedconstrainmodels().addAll(arrayListNestedConstraints);
			System.out.println("[# Resulted NGC is:(" + arrayListNestedConstraints.size() + ")]");
			Date date = new GregorianCalendar().getTime();
			persistNestedConstraintsModel(date, constraintmodel);
			shell.setCursor(oldCursor);

			MessageDialog.openInformation(shell, "OCL2AC",
					"The translation time is: (" + (translationTime / (double) 1000) + ") second(s).");
		}

	}

	/**
	 * 
	 * @param cond
	 *            of LaxconditionPackage
	 * @return
	 */
	private List<String> getDistinctVariableNames(Condition cond) {
		List<String> varNames = new ArrayList<String>();
		HenshinFactory factory = HenshinFactory.eINSTANCE;
		Rule rule = factory.createRule();
		Parameter param = factory.createParameter("p");
		rule.getParameters().add(param);
		Node sourceLHS = factory.createNode(rule.getLhs(), LaxconditionPackage.Literals.VARIABLE, "");
		sourceLHS.setName(param.getName());
		Node sourceRHS = factory.createNode(rule.getRhs(), LaxconditionPackage.Literals.VARIABLE, "");
		sourceRHS.setName(param.getName());
		Mapping sourceMapping = factory.createMapping(sourceLHS, sourceRHS);
		rule.getMappings().add(sourceMapping);

		HenshinPackage.eINSTANCE.eClass();
		EGraph graph = new EGraphImpl(cond);
		Engine engine = new EngineImpl();
		List<Match> allMatches = InterpreterUtil.findAllMatches(engine, rule, graph, null);
		for (Match m : allMatches) {
			Object parameterValue = m.getParameterValue(param);
			if (parameterValue instanceof laxcondition.Variable) {
				laxcondition.Variable v = (laxcondition.Variable) parameterValue;
				if (v.getName() != null) {
					if (!varNames.contains(v.getName())) {
						varNames.add(v.getName());
					}
				}
			}
		}

		return varNames;
	}

	/**
	 * 
	 * @param arrayListNestedConstraints
	 */
	private void simplifyNestedConstraints(List<NestedConstraint> arrayListNestedConstraints) {
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
	private List<NestedConstraint> completeLaxConditions(HashMap<Condition, List<String>> mapCon2Var,
			List<Condition> arrayListLaxConditions) {
		List<NestedConstraint> arrayListNestedConstraints = new ArrayList<NestedConstraint>();
		List<String> conditionVarNames = null;
		for (Condition condition : arrayListLaxConditions) {
			try {
				conditionVarNames = mapCon2Var.get(condition);
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

	// TODO add variables to LaxCondition. I added. To be tested
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
		String path = selectedFile.getParent().getLocation().toOSString();// append(Constants.NESTED_CONSTRAINTS
																			// +
																			// sdf.format(date)).toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(nestedConstraintModel,
				nestedConstraintModel.getName().concat("_" + sdf.format(date) + Constants.NESTED_CONSTRAINTS_MODEL));
		try {
			selectedFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkFile() {
		if (!(selectedFile.getName().endsWith(COMPACTCONDITION)))
			return false;
		return true;
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@SuppressWarnings("unchecked")
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object object = ss.getFirstElement();
			if (object instanceof IFile)
				selectedFile = (IFile) object;
		}
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
