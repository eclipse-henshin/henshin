/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.Integrator;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionPackage;

public class IntegrateAction implements IObjectActionDelegate {

	public static final String NESTED_CONDITIONS = "/nestedconditions_";
	public static final String NESTED_CONDITION = ".nestedcondition";
	private static final String HENSHIN = ".henshin";
	private Shell shell;
	private List<IFile> files = null;
	private IFile henshinFile = null;
	private IFile nestedconditionFile = null;

	// Workbench part:
	protected IWorkbenchPart workbenchPart;

	// Henshin module and rule:
	protected Module module;
	protected Rule rule;

	// Nested constraint:
	private NestedConstraint constraint;

	/**
	 * Constructor for IntegrateAction.
	 */
	public IntegrateAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (checkFiles()) {
			initModels();
			integrate();
			MessageDialog.openInformation(shell, "Integration", "Integration finished.");
		} else {
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Integration", "Integration can not be executed on the input.");
		}
	}

	private void initModels() {
		URI uriHenshin = URI.createPlatformResourceURI(this.henshinFile.getFullPath().toString(), true);
		URI uriNestedcondition = URI.createPlatformResourceURI(this.nestedconditionFile.getFullPath().toString(), true);
		if (uriHenshin != null && uriNestedcondition != null) {
			// Load the henshin module and the henshin rule
			HenshinPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("*.henshin", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			Resource resourceHenshin = resSet.getResource(uriHenshin, true);
			this.module = (Module) resourceHenshin.getContents().get(0);
			// Load the nested condition
			NestedconditionPackage.eINSTANCE.eClass();
			m.put("*.nestedcondition", new XMIResourceFactoryImpl());
			Resource resourceNestedcondition = resSet.getResource(uriNestedcondition, true);
			this.constraint = (NestedConstraint) resourceNestedcondition.getContents().get(0);
		}
	}

	private boolean checkFiles() {
		IFile file1 = files.get(0);
		IFile file2 = files.get(1);
		System.out.println(file1.getName());
		System.out.println(file2.getName());
		if (!(file1.getName().endsWith(HENSHIN) || file2.getName().endsWith(HENSHIN)))
			return false;
		if (!(file1.getName().endsWith(NESTED_CONDITION) || file2.getName().endsWith(NESTED_CONDITION)))
			return false;
		if (file1.getName().endsWith(HENSHIN)) {
			henshinFile = file1;
			nestedconditionFile = file2;
		} else {
			henshinFile = file2;
			nestedconditionFile = file1;
		}
		return true;
	}

	/*
	 * Start the integration
	 */
	protected void integrate() {
		if (rule != null) {
			Integrator integrator = new Integrator(constraint, rule);
			integrator.integrate();
			NestedCondition condition = integrator.getShiftedCondition();
			System.out.println(condition);
			System.out.println(condition.getDomain());
			this.constraint.setDomain(condition.getDomain());
			this.constraint.setCondition(condition);
			persistResults();
		}
	}

	private void persistResults() {
		Date date = new GregorianCalendar().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String path = nestedconditionFile.getParent().getLocation().append(NESTED_CONDITIONS + sdf.format(date))
				.toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(constraint, constraint.getName().concat(NESTED_CONDITION));
		resourceSet.saveEObject(rule.getModule(), henshinFile.getName());
		try {
			nestedconditionFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
}
