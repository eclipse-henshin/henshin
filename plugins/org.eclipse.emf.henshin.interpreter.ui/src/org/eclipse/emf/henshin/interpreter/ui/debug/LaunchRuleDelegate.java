package org.eclipse.emf.henshin.interpreter.ui.debug;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.compare.internal.CompareAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.interpreter.Change;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.impl.DebugEngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Solution;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
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
		String inputURIString = configuration.getAttribute(IHenshinConfigConstants.INPUT_URI, "");
		String outputURIString = configuration.getAttribute(IHenshinConfigConstants.OUTPUT_URI, "");
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

		// get the URIs from the URI Strings
		URI inputUri = URI.createURI(inputURIString);
		URI outputUri = URI.createURI(outputURIString);

		TransformOperation transformOperation = new TransformOperation();
		transformOperation.setUnit(unit, paramConfigs);
		transformOperation.setInputURI(inputUri);
		transformOperation.setOutputURI(outputUri);

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

			Resource input = resourceSet.getResource(inputUri, true);
			EGraph graph = new EGraphImpl(input);
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

			DebugApplicationCondition applicationCondition = engine.getDebugApplicationCondition(rule, graph, partialMatch, new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					if (!(arg instanceof Solution)) {
						throw new IllegalStateException("update arg has to be of type Solution, but is of type "
								+ arg.getClass().getSimpleName());
					}
					Solution solution = (Solution) arg;
					Match completeMatch = engine.getMatchFinder().basicMatchFromSolution(solution);
					Match resultMatch = new MatchImpl((Rule) unit, true);
					Change change = engine.createChange((Rule) unit, graph, completeMatch, resultMatch);
					change.applyAndReverse();

					Resource output;
					if (inputUri.equals(outputUri)) {
						output = input;
					} else {
						output = resourceSet.createResource(outputUri);
						output.getContents().addAll(input.getContents());
					}

					Map<Object, Object> options = new HashMap<Object, Object>();
					options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
					try {
						output.save(options);

						if (outputUri.isPlatformResource()) {
							IPath path = new Path(outputUri.toPlatformString(false));
							IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
							if (file != null) {
								file.getParent().refreshLocal(2, SubMonitor.convert(monitor, 2));

							}
						}

					} catch (CoreException | IOException e) {
						e.printStackTrace();
						displayMessage(configuration, "Error saving transformation result.");
					}

					if (openCompare) {
						openCompareView(inputUri, outputUri);
					}
				}
			});

			engine.getDebugTarget().initTarget(applicationCondition, findResourceForModulePath(modulePath));
			launch.addDebugTarget(engine.getDebugTarget());

			applicationCondition.initNextVariable();

	    // normal launch
	    } else if (mode.equals(ILaunchManager.RUN_MODE)) {
			try {
				// execute the transformOperation
				transformOperation.run(monitor);

				if (openCompare) {
					openCompareView(inputUri, outputUri);
				}
			} catch (InvocationTargetException e) {
				if (e.getCause() != null && e.getCause().getMessage() != null) {
					displayMessage(configuration, e.getCause().getMessage());
				} else {
					displayMessage(configuration, "Error applying transformation");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}

	private void displayMessage(ILaunchConfiguration configuration, final String message) {
		final String configName = configuration.getName();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell, configName, message);
			}
		});
	}

	private IFile findResourceForModulePath(String modulePath) throws CoreException {
		try {
			// returns IFile to use later in Marker to register breakpoints
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(FileLocator.resolve(java.net.URI.create(modulePath).toURL()).toURI());
			if (files.length > 0)
				return files[0];
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new CoreException(new Status(IStatus.ERROR, HenshinInterpreterUIPlugin.PLUGIN_ID, "Didn't find resource for modulePath '" + modulePath + "'."));
	}

	private void openCompareView(URI inputUri, URI outputUri) {
		IFile left = ParamUtil.getIFile(outputUri);
		IFile right = ParamUtil.getIFile(inputUri);
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

	private class MyCompareAction extends CompareAction {
		@Override
		public void run(ISelection selection) {
			if (super.isEnabled(selection)) {
				super.run(selection);
			}
		}
	}

}
