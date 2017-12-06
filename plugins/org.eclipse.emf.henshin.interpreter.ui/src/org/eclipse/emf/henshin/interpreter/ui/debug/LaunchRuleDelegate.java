package org.eclipse.emf.henshin.interpreter.ui.debug;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.compare.internal.CompareAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.impl.DebugEngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.interpreter.ui.util.TransformOperation;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;


/**
 * The launch delegate responsible for launching a henshin rule application.
 */
@SuppressWarnings("restriction")
public class LaunchRuleDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		String modulePath = configuration.getAttribute(IHenshinConfigConstants.MODULE_PATH, "");
		String unitName = configuration.getAttribute(IHenshinConfigConstants.UNIT_NAME, "");
		String inputURI = configuration.getAttribute(IHenshinConfigConstants.INPUT_URI, "");
		String outputURI = configuration.getAttribute(IHenshinConfigConstants.OUTPUT_URI, "");
		Map<String, String> paramTypes = configuration.getAttribute(IHenshinConfigConstants.PARAMETER_TYPES,
				new HashMap<String, String>());
		Map<String, String> paramValues = configuration.getAttribute(IHenshinConfigConstants.PARAMETER_VALUES,
				new HashMap<String, String>());
		List<String> unsetParamNames = configuration.getAttribute(IHenshinConfigConstants.UNSET_PARAMETERS,
				new ArrayList<String>());
		boolean openCompare = configuration.getAttribute(IHenshinConfigConstants.OPEN_COMPARE, true);

		// Load the module:
		URI moduleUri = URI.createURI(modulePath);

		// Create a fresh resource set for storing a copy of the module:
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		Module module = resourceSet.getModule(moduleUri, false);
		Resource oldModuleResource = module.eResource();
		Resource newModuleResource = resourceSet.createResource(oldModuleResource.getURI());

		// Copy the original module into the fresh resource:
		Copier copier = new Copier();
		Collection<EObject> copies = copier.copyAll(oldModuleResource.getContents());
		copier.copyReferences();
		newModuleResource.getContents().addAll(copies);

		// Now switch to the copied version:
		module = (Module) copier.get(module);

		// Manually initialize the package registry:
		for (EPackage ePackage : module.getImports()) {
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}

		// get the unit with the specified name
		Unit unit = module.getUnit(unitName);

		// get parameters for unit
		List<ParameterConfig> paramConfigs = ParamUtil.getParameterPreferences(unit);
		ParamUtil.fillParamConfigs(paramConfigs, paramTypes, paramValues, unsetParamNames);

		TransformOperation transformOperation = new TransformOperation();

		transformOperation.setUnit(unit, paramConfigs);
		transformOperation.setInputURI(URI.createURI(inputURI));
		transformOperation.setOutputURI(URI.createURI(outputURI));

		// remove "var" and "out" params
		Iterator<ParameterConfig> iterator = transformOperation.getParameterConfigurations().iterator();
		while (iterator.hasNext()) {
			ParameterConfig paramConfig = iterator.next();
			if (paramConfig.getKind() == ParameterKind.VAR || paramConfig.getKind() == ParameterKind.OUT) {
				iterator.remove();
			}
		}
		
		// debug launch: add IDebugTarget
	    if (mode.equals(ILaunchManager.DEBUG_MODE)) {

	    	// Create an engine:
	    	DebugEngineImpl engine = new DebugEngineImpl();

	    	HenshinDebugTarget target = new HenshinDebugTarget(launch, unitName);
	    	engine.setDebugTarget(target);
	    	
			EGraph graph = new EGraphImpl(resourceSet.getResource(URI.createURI(inputURI), true));
			Rule rule = null;
			try {
				// we can only debug plain rule applications at the moment
				rule = (Rule) unit;
			} catch (ClassCastException e) {
				final String message = "The selected unit is not a rule";
				final String configName = configuration.getName();
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						MessageDialog.openError(shell, configName, message);
					}
				});
			}
			Match partialMatch = new MatchImpl(rule);
			
			for (Parameter param : rule.getParameters()) {
				if (param.getKind() != ParameterKind.VAR && param.getKind() != ParameterKind.OUT) {
					partialMatch.setParameterValue(param, transformOperation.getParameterValue(param.getName()));
				}
			}
			
			DebugApplicationCondition applicationCondition = engine.getDebugApplicationCondition(rule, graph, partialMatch);
			engine.getDebugTarget().initTarget(applicationCondition);
			launch.addDebugTarget(engine.getDebugTarget());

			applicationCondition.initNextVariable();

			
	    // normal launch
	    } else if (mode.equals(ILaunchManager.RUN_MODE)) {
			try {
				// execute the transformOperation
				transformOperation.run(monitor);
	
				// open the compare view if necessary
				if (openCompare) {
					IFile left = ParamUtil.getIFile(outputURI);
					IFile right = ParamUtil.getIFile(inputURI);
					if (left != null && right != null) {
						try {
							final ISelection selection = new StructuredSelection(new Object[] { left, right });
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									MyCompareAction c = new MyCompareAction();
									IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
											.getActivePage().getActivePart();
									c.setActivePart(null, part);
									c.run(selection);
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (InvocationTargetException e) {
				final String message;
				if (e.getCause() != null && e.getCause().getMessage() != null) {
					message = e.getCause().getMessage();
				} else {
					message = "Error applying transformation";
				}
				final String configName = configuration.getName();
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						MessageDialog.openError(shell, configName, message);
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}

	private class MyCompareAction extends CompareAction {
		@Override
		public void run(ISelection selection) {
			if (super.isEnabled(selection)) {
				super.run(selection);
			}
		}
	}

}
