/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.tool.ui.WizardGC2AppCond;
import nestedcondition.NestedConstraint;
import nestedconstraintmodel.NestedConstraintModel;
import nestedconstraintmodel.NestedconstraintmodelPackage;

public class GC2AppCondAction implements IObjectActionDelegate {

	private static final String NESTED_CONSTRAINT_MODEL = ".nestedconstraintmodel";
	private static final String HENSHIN = ".henshin";
	private Shell shell;
	private List<IFile> files = null;
	private IFile nestedConstraintModelFile = null;
	private IFile henshinFile = null;
	private Module module = null;
	private NestedConstraintModel nestedconstraintmodel = null;

	/**
	 * Constructor for TransformAction.
	 */
	public GC2AppCondAction() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (checkFiles()) {
			initModels();
			HashMap<Integer, NestedConstraint> hashmapAllNestedconstraints = fillHashMap_NC();
			HashMap<Integer, Rule> hashmapAllRules = fillHashMap_Rule();

			if (hashmapAllNestedconstraints.size() > 0 && hashmapAllRules.size() > 0) {
				Display display = Display.getDefault();
				WizardGC2AppCond uiAssistent = new WizardGC2AppCond(display, henshinFile, hashmapAllNestedconstraints,
						hashmapAllRules, nestedConstraintModelFile);

				Monitor primary = display.getPrimaryMonitor();
				Rectangle bounds = primary.getBounds();
				Rectangle rect = uiAssistent.getBounds();
				int x = bounds.x + (bounds.width - rect.width) / 2;
				int y = bounds.y + (bounds.height - rect.height) / 2;
				uiAssistent.setLocation(x, y);
				uiAssistent.open();
				uiAssistent.layout();
				while (!uiAssistent.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		} else {
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Integration", "Integration can not be executed on the input.");
		}

	}

	private void initModels() {
		URI uriHenshin = URI.createPlatformResourceURI(this.henshinFile.getFullPath().toString(), true);
		URI uriNestedConstraintModel = URI
				.createPlatformResourceURI(this.nestedConstraintModelFile.getFullPath().toString(), true);
		if (uriHenshin != null && uriNestedConstraintModel != null) {
			// Load the henshin module and the henshin rule
			HenshinPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("*.henshin", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			Resource resourceHenshin = resSet.getResource(uriHenshin, true);
			this.module = (Module) resourceHenshin.getContents().get(0);
			// Load the nested condition
			NestedconstraintmodelPackage.eINSTANCE.eClass();
			m.put("*.nestedconstraintmodel", new XMIResourceFactoryImpl());
			Resource resourceNestedcondition = resSet.getResource(uriNestedConstraintModel, true);
			this.nestedconstraintmodel = (NestedConstraintModel) resourceNestedcondition.getContents().get(0);
		}
	}

	private boolean checkFiles() {
		IFile file1 = files.get(0);
		IFile file2 = files.get(1);
		if (!(file1.getName().endsWith(HENSHIN) || file2.getName().endsWith(HENSHIN)))
			return false;
		if (!(file1.getName().endsWith(NESTED_CONSTRAINT_MODEL) || file2.getName().endsWith(NESTED_CONSTRAINT_MODEL)))
			return false;
		if (file1.getName().endsWith(NESTED_CONSTRAINT_MODEL)) {
			nestedConstraintModelFile = file1;
			henshinFile = file2;
		} else {
			nestedConstraintModelFile = file2;
			henshinFile = file1;
		}
		return true;
	}

	private HashMap<Integer, Rule> fillHashMap_Rule() {
		HashMap<Integer, Rule> hashmapAllRules = new HashMap<Integer, Rule>();
		int j = 0;
		for (Unit unit : module.getUnits()) {
			if (unit instanceof Rule) {
				hashmapAllRules.put(j, (Rule) unit);
				j++;
			}
		}
		return hashmapAllRules;
	}

	private HashMap<Integer, NestedConstraint> fillHashMap_NC() {
		HashMap<Integer, NestedConstraint> hashmapAllNestedconstraints = new HashMap<Integer, NestedConstraint>();
		int i = 0;
		for (NestedConstraint nc : nestedconstraintmodel.getNestedconstrainmodels()) {
			hashmapAllNestedconstraints.put(i, nc);
			i++;
		}
		return hashmapAllNestedconstraints;
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
