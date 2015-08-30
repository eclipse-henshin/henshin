package org.eclipse.emf.henshin.sam.invcheck.launch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.henshin.sam.invcheck.InvariantChecker;
import org.eclipse.emf.henshin.sam.invcheck.launch.ui.ShowResultDialog;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

public class InvariantCheckingMetaModelLaunchDelegate implements ILaunchConfigurationDelegate {

	// @Override
	@SuppressWarnings("unchecked")
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, final IProgressMonitor monitor)
			throws CoreException {

		monitor.beginTask("Running Pipe & Filter Configuration" + configuration.getName(), IProgressMonitor.UNKNOWN);

		Bundle theBundle = null;
		ILog theLog = null;
		final ResourceSet rs = new ResourceSetImpl();
		final String s = configuration.getAttribute(InvariantCheckingLauncherConstants.verificationTaskURI, "");
		URI uri = URI.createURI(s);

		Resource model = rs.getResource(uri, true);

		final EObject vt = model.getContents().get(0);

		/*
		 * validate the specified EObject if wished by the user and in case of
		 * validation errors log an error status into the error-log and return
		 * without doing anything
		 */
		if (configuration.getAttribute(InvariantCheckingLauncherConstants.validateModel, false)) {
			Diagnostic diag = Diagnostician.INSTANCE.validate(vt);
			if (diag.getSeverity() == Diagnostic.ERROR) {
				Bundle bundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					ILog log = Platform.getLog(bundle);
					if (log != null) {
						log.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID,
								"Model validation failed! FilterChain will not be executed"));
					}
				}
				return;
			}
		}

		Map<String, Map<String, Boolean>> options = new HashMap<String, Map<String, Boolean>>();
		Map<String, List<IConfigurationElement>> extensions = PAFActivator.getDefault().getFilterFactory()
				.getAllConfigFields();
		extensions.putAll(PAFActivator.getDefault().getConsumerFactory().getAllConfigFields());
		extensions.putAll(PAFActivator.getDefault().getProducerFactory().getAllConfigFields());
		for (String clazz : extensions.keySet()) {
			Map<String, Boolean> values = new HashMap<String, Boolean>();
			for (IConfigurationElement field : extensions.get(clazz)) {
				boolean value = configuration.getAttribute(clazz + ":" + field.getAttribute("name"),
						field.getAttribute("defaultValue").equals("true") ? true : false);
				values.put(field.getAttribute("name"), value);
			}
			options.put(clazz, values);
		}
		boolean fileOutput = configuration.getAttribute(InvariantCheckingLauncherConstants.fileOutput, false);
		boolean usePredefinedChain = configuration.getAttribute(InvariantCheckingLauncherConstants.usePredefinedChain,
				false);

		String selectedProducer = configuration.getAttribute(InvariantCheckingLauncherConstants.selectedProducer, "");
		String selectedConsumer = configuration.getAttribute(InvariantCheckingLauncherConstants.selectedConsumer, "");
		List<?> filterList = configuration.getAttribute(InvariantCheckingLauncherConstants.selectedFilters,
				new ArrayList(1));

		String predefinedChain = configuration.getAttribute(InvariantCheckingLauncherConstants.predefinedChain, "");
		final long timeStart = System.currentTimeMillis();
		long timeEnd = 0L;

		try {

			InvariantChecker checker = new InvariantChecker();
			final Map<String, Map<String, String>> compuResults = checker.run(vt, options, fileOutput,
					usePredefinedChain, predefinedChain, selectedProducer, selectedConsumer, filterList, monitor);

			timeEnd = System.currentTimeMillis();

			final Display disp = PlatformUI.getWorkbench().getDisplay();
			disp.syncExec(new Runnable() {

				public void run() {
					Dialog diag = new ShowResultDialog(disp.getActiveShell(), compuResults);
					diag.open();
				}
			});
		} catch (Exception e) {
			if (theLog == null) {
				if (theBundle == null) {
					theBundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				}
				theLog = Platform.getLog(theBundle);
			}
			theLog.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID,
					"InterruptedException occurred", e));
		}
		if (theLog == null) {
			if (theBundle == null) {
				theBundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
			}
			theLog = Platform.getLog(theBundle);
		}
		final String terminationMessage = timeEnd == 0L ? "Execution got interrupted and finished abnormally!"
				: "Execution took: " + (timeEnd - timeStart) + " ms";
		theLog.log(new Status(IStatus.INFO, InvariantCheckingLauncherConstants.PLUGIN_ID, terminationMessage));

		model.setModified(true);

		try {
			model.save(null);
		} catch (IOException w) {
			theLog.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID,
					"saving the model failed", w));
		}
		monitor.done();
	}

}
