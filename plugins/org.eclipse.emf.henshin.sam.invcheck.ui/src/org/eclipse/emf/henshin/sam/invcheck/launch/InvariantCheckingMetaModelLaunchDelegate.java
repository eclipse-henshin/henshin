package org.eclipse.emf.henshin.sam.invcheck.launch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.eclipse.emf.henshin.sam.invcheck.launch.ui.ShowResultDialog;
import org.eclipse.emf.henshin.sam.paf.AbstractFilter;
import org.eclipse.emf.henshin.sam.paf.ConsumerFactory;
import org.eclipse.emf.henshin.sam.paf.DefaultPipe;
import org.eclipse.emf.henshin.sam.paf.FilterDispatcher;
import org.eclipse.emf.henshin.sam.paf.FilterFactory;
import org.eclipse.emf.henshin.sam.paf.IConsumer;
import org.eclipse.emf.henshin.sam.paf.IFilter;
import org.eclipse.emf.henshin.sam.paf.IPipe;
import org.eclipse.emf.henshin.sam.paf.IProducer;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.emf.henshin.sam.paf.ProducerFactory;
import org.eclipse.emf.henshin.sam.paf.preferences.PAFPreferenceConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

public class InvariantCheckingMetaModelLaunchDelegate implements
		ILaunchConfigurationDelegate {

	private int threads_med = 1, threads_low = 1, threads_high = 1;
	
	//@Override
	@SuppressWarnings("unchecked")
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, final IProgressMonitor monitor) throws CoreException {
				
		monitor.beginTask("Running Pipe & Filter Configuration" + configuration.getName(), IProgressMonitor.UNKNOWN);
		
		this.initNumberOfThreads();
		
		Bundle theBundle = null;
		ILog theLog = null;
		final ResourceSet rs = new ResourceSetImpl();
		final String s = configuration.getAttribute(InvariantCheckingLauncherConstants.verificationTaskURI, "");
		URI uri = URI.createURI(s);

		Resource model = rs.getResource(uri, true);

		final EObject vt =  model.getContents().get(0);
		
		/*
		 * validate the specified EObject if wished by the user and in case of validation errors
		 * log an error status into the error-log and return without doing anything
		 */
		if (configuration.getAttribute(InvariantCheckingLauncherConstants.validateModel, false)) {
			Diagnostic diag = Diagnostician.INSTANCE.validate(vt);
			if (diag.getSeverity() == Diagnostic.ERROR) {
				Bundle bundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					ILog log = Platform.getLog(bundle);
					if (log != null) {
						log.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID, "Model validation failed! FilterChain will not be executed"));
					}
				}
				return;
			}
		}
		
		Map<String, Map<String, Boolean>> options = new HashMap<String, Map<String, Boolean>>();
		Map<String, List<IConfigurationElement>> extensions = PAFActivator.getDefault().getFilterFactory().getAllConfigFields();
		extensions.putAll(PAFActivator.getDefault().getConsumerFactory().getAllConfigFields());
		extensions.putAll(PAFActivator.getDefault().getProducerFactory().getAllConfigFields());
		for (String clazz : extensions.keySet()) {
			Map<String, Boolean> values = new HashMap<String, Boolean>();
			for (IConfigurationElement field : extensions.get(clazz)) {
				boolean value = configuration.getAttribute(clazz + ":" + field.getAttribute("name"), field.getAttribute("defaultValue").equals("true") ? true : false);
				values.put(field.getAttribute("name"), value);
			}
			options.put(clazz, values);
		}
		boolean fileOutput = configuration.getAttribute(InvariantCheckingLauncherConstants.fileOutput, false);
		final FilterDispatcher fd = new FilterDispatcher(options, fileOutput);
		fd.setFilterInput(vt);
	
		if (configuration.getAttribute(InvariantCheckingLauncherConstants.usePredefinedChain, false) == false) {
			final String producer = configuration.getAttribute(
					InvariantCheckingLauncherConstants.selectedProducer, "");
			final String consumer = configuration.getAttribute(
					InvariantCheckingLauncherConstants.selectedConsumer, "");
			final List filter = configuration.getAttribute(
					InvariantCheckingLauncherConstants.selectedFilters,
					new ArrayList(1));
			final Map<String,Integer> filterMap = new LinkedHashMap<String, Integer>();
			for (Object o : filter) {				
				filterMap.put(o.toString(), 1);
			}
			
			buildFilterChain(fd, producer, consumer, filterMap);
		} else {
			final String configurationChainAttr = configuration.getAttribute(InvariantCheckingLauncherConstants.predefinedChain, "");
			final IConfigurationElement[] iCEs = PAFActivator.getDefault().getAllFilterChains();
			String tmpName = null;
			int index = -1;
			for (int i = 0; index < 0 && i < iCEs.length; i++) {
				tmpName = iCEs[i].getNamespaceIdentifier() + "/" + iCEs[i].getAttribute("name");
				if (tmpName.equals(configurationChainAttr)) {
					index = i;
				}
			}
			final IConfigurationElement theConfigurationElement = iCEs[index];
			IConfigurationElement[] tmpICEArray = theConfigurationElement.getChildren("consumerFilterChainEntry");
			IConfigurationElement consumer = tmpICEArray[0];
			tmpICEArray = theConfigurationElement.getChildren("producerFilterChainEntry");
			IConfigurationElement producer = tmpICEArray[0];
			tmpICEArray = theConfigurationElement.getChildren("filterChainEntry");
			Map<String, Integer> filters = new LinkedHashMap<String,Integer>(tmpICEArray.length);
			int numOthreads = 1;
			String complexity;
			for (IConfigurationElement filter : tmpICEArray) {				
				complexity = filter.getAttribute("complexity");				
				if (complexity != null) {
					if (complexity.equals("LOW")) {
						numOthreads = this.threads_low;
					} else if (complexity.equals("MEDIUM")) {
						numOthreads = this.threads_med;
					} else if (complexity.equals("HIGH")) {
						numOthreads = this.threads_high;
					} else {
						numOthreads = 1;
					}
				} else {
					numOthreads = 1;
				}
				filters.put(filter.getAttribute("referencedFilter"),numOthreads);
			}
			
			buildFilterChain(fd, producer.getAttribute("referencedProducer"), consumer.getAttribute("referencedConsumer"), filters);
		}
		final long timeStart = System.currentTimeMillis();
		long timeEnd = 0L;
		fd.startFilter();
		
		/*
		 * start a small and light thread that periodically checks, whether the user has requested
		 * to cancel the execution of the currently running LaunchConfiguration.
		 * The thread wakes up every second.
		 */
		final WatchDog watchdog = this.new WatchDog(fd, monitor);
		new Thread(watchdog, "The monitor monitor").start();
		
		try {
			fd.waitOnTermination();
			watchdog.stop();
			timeEnd = System.currentTimeMillis();
			final Map<String,Map<String, String>> compuResults = fd.getComputationDetails();
			Map<String,String> generalInformation = new LinkedHashMap<String, String>();
			generalInformation.put("Computation time", Long.toString(timeEnd-timeStart));
			for (Map.Entry<IFilter<?, ?>, Long> entry : fd.getCPUTimePerFilter().entrySet()) {
				//generalInformation.put("CPU time for: " + entry.getKey().getName(), new Long((entry.getValue() / 1000)).toString());
				generalInformation.put("CPU time for: " + entry.getKey(), entry.getValue().toString());
			}
			generalInformation.put("fileOutput", new Boolean(fileOutput).toString());
			compuResults.put("General Information", generalInformation);
			final Display disp = PlatformUI.getWorkbench().getDisplay();
			
			disp.syncExec(new Runnable() {
				
				public void run() {
					Dialog diag = new ShowResultDialog(disp.getActiveShell(), compuResults);
					diag.open();
				}
			});
		} catch (InterruptedException e) {
			if (theLog == null) {
				if (theBundle == null) {
					theBundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				}
				theLog = Platform.getLog(theBundle);
			}
			theLog.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID, "InterruptedException occurred", e));
		}
		if (theLog == null) {
			if (theBundle == null) {
				theBundle = Platform.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
			}
			theLog = Platform.getLog(theBundle);
		}
		final String terminationMessage = timeEnd == 0L ? "Execution got interrupted and finished abnormally!" : "Execution took: "+(timeEnd - timeStart) + " ms";
		theLog.log(new Status(IStatus.INFO, InvariantCheckingLauncherConstants.PLUGIN_ID, terminationMessage));

		model.setModified(true);

		try{
			model.save(null);			
		} catch (IOException w){
			theLog.log(new Status(IStatus.ERROR, InvariantCheckingLauncherConstants.PLUGIN_ID, "saving the model failed", w));
		}	
		monitor.done();
	}

	private void initNumberOfThreads() {
		final IPreferenceStore pStore = PAFActivator.getDefault().getPreferenceStore();
		if (pStore != null) {
			this.threads_low = pStore.getInt(PAFPreferenceConstants.THREADS_COMPLEXITY_LOW);
			this.threads_med = pStore.getInt(PAFPreferenceConstants.THREADS_COMPLEXITY_MEDIUM);
			this.threads_high = pStore.getInt(PAFPreferenceConstants.THREADS_COMPLEXITY_HIGH);
		}
	}

	@SuppressWarnings("unchecked")
	private void buildFilterChain(final FilterDispatcher fd, final String producer, final String consumer, final Map<String, Integer> filter) {
final FilterFactory ff = PAFActivator.getDefault().getFilterFactory();
		
		final ProducerFactory pf = PAFActivator.getDefault().getProducerFactory();
		
		final ConsumerFactory cf = PAFActivator.getDefault().getConsumerFactory();
		
		final IProducer<?> theProducer = pf.createProducer(producer);
		theProducer.setFilterDispatcher(fd);
		IPipe inputPipe = null;
		IPipe outputPipe = new DefaultPipe();
		outputPipe.setFilterDispatcher(fd);
		theProducer.getOutputPipes().put(AbstractFilter.DEFAULT_OUTPUT, outputPipe);
		
		for (Iterator<Map.Entry<String, Integer>> iter = filter.entrySet().iterator(); iter.hasNext();) {
			final Map.Entry<String, Integer> entry = iter.next();
			inputPipe = outputPipe;
			outputPipe = new DefaultPipe();
			outputPipe.setFilterDispatcher(fd);
			for (int i = 0; i < entry.getValue(); i++) {
				IFilter<?, ?> theFilter = ff.createFilter(entry.getKey());
				theFilter.setFilterDispatcher(fd);
				inputPipe.incrementReaders();
				theFilter.getInputPipes().put(AbstractFilter.DEFAULT_INPUT, inputPipe);
				theFilter.getOutputPipes().put(AbstractFilter.DEFAULT_OUTPUT,
					outputPipe);
			}
		}
		
		final IConsumer theConsumer = cf.createConsumer(consumer);
		theConsumer.setFilterDispatcher(fd);
		outputPipe.incrementReaders();
		theConsumer.getInputPipes().put(AbstractFilter.DEFAULT_INPUT, outputPipe);
	}
	
	
	
	private class WatchDog implements Runnable {
		private IProgressMonitor monitor;
		private FilterDispatcher fd;
		private boolean running;
		
		public WatchDog(final FilterDispatcher cFd, final IProgressMonitor cMonitor) {
			this.monitor = cMonitor;
			this.fd = cFd;
			this.running = true;
		}
		
		public void stop() {
			this.running = false;
		}

		public void run() {
			while (this.running && fd.isContinueComputation() && (monitor.isCanceled() == false)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				if (monitor.isCanceled()) {
					fd.setContinueComputation(false);
				}
			}
			fd = null;
			monitor = null;
		}
	}

}

