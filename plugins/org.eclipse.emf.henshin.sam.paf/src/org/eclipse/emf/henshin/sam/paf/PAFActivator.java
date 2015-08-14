package org.eclipse.emf.henshin.sam.paf;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.paf.internal.ConsumerFactoryImpl;
import org.eclipse.emf.henshin.sam.paf.internal.FilterFactoryImpl;
import org.eclipse.emf.henshin.sam.paf.internal.ProducerFactoryImpl;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PAFActivator extends AbstractUIPlugin {
	
	/**
	 * the id for the PAF Plugin
	 */
	public static final String PLUGIN_ID = "de.hpi.sam.paf";
	
	/**
	 * the static field holding the singleton instance.
	 */
	private static PAFActivator plugin = null;
	
	/**
	 * Returns the singleton instance.
	 * @return the singleton instance.
	 */
	public static PAFActivator getDefault() {
		return plugin;
	}
	
	public PAFActivator() {
		//TODO: should we care for multiple invocations of the constructor?
		plugin = this;
	}

	private static FilterFactory filterFactory = null;
	
	private static ProducerFactory producerFactory = null;
	
	private static ConsumerFactory consumerFactory = null;
	
	private static IConfigurationElement[] filterChainNames = null;
	
	public FilterFactory getFilterFactory() {
		if (filterFactory == null)
			filterFactory = new FilterFactoryImpl();
		return filterFactory;
	}
	
	public ProducerFactory getProducerFactory() {
		if (producerFactory == null)
			producerFactory = new ProducerFactoryImpl();
		return producerFactory;
	}
	
	public ConsumerFactory getConsumerFactory() {
		if (consumerFactory == null)
			consumerFactory = new ConsumerFactoryImpl();
		return consumerFactory;
	}
	
	/**
	 * Returns an array of <code>IConfigurationElement</code>s each of them specifying a filter chain.
	 * @return array of filter chains.
	 */
	public IConfigurationElement[] getAllFilterChains() {
		if (filterChainNames == null) {
			ArrayList<IConfigurationElement> configElems = new ArrayList<IConfigurationElement>();
			IExtensionPoint iep = Platform.getExtensionRegistry().getExtensionPoint("de.hpi.sam.paf.Filter"); //$NON-NLS-1$
			for (IConfigurationElement ice : iep.getConfigurationElements()) {
				if (ice.getName().equals("filterChain")) {
					configElems.add(ice);
				}
			}
			filterChainNames = configElems.toArray(new IConfigurationElement[configElems.size()]);
		}
		return filterChainNames;
	}
}
