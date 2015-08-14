package org.eclipse.emf.henshin.sam.paf.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.sam.paf.IProducer;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.emf.henshin.sam.paf.ProducerFactory;
import org.eclipse.emf.henshin.sam.paf.UnknownProducerException;

public class ProducerFactoryImpl implements ProducerFactory {

	private final String EXTENSION_POINT = "de.hpi.sam.paf.Producer";
	private HashMap<String, IConfigurationElement> cache = null;
		
	public IProducer<?> createProducer(String name)
			throws UnknownProducerException {
		if (this.cache == null) {
			this.initializeCache();
		}
		final IConfigurationElement elem = this.cache.get(name);
		if (elem != null) {
			try {
				final Object o = elem.createExecutableExtension("producer_class");
				IProducer<?> result = IProducer.class.cast(o);								
				return result;
			} catch (CoreException e) {
				PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "CoreException has been thrown, while trying to instantiate producer: "+name, e));
				throw new UnknownProducerException("Unable to create producer becaus of a CoreException", e);
			} catch (ClassCastException e) {
				PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "Unable to cast to IProducer", e));
				throw new UnknownProducerException("Unable to cast to IProducer",e);
			}
		} else {
			throw new UnknownProducerException("No producer with name: "+name+" is known!");
		}
	}
	
	public String[] getAllProducerNames() {
		if (this.cache == null) {
			this.initializeCache();
		}
		String[] result = new String[this.cache.keySet().size()];
		result = this.cache.keySet().toArray(result);
		return result;
	}
	
	public Map<String, List<IConfigurationElement>> getAllConfigFields() {
		Map<String, List<IConfigurationElement>> result = new HashMap<String, List<IConfigurationElement>>();
		IConfigurationElement[] confElems = Platform.getExtensionRegistry().getConfigurationElementsFor(this.EXTENSION_POINT);
		for (IConfigurationElement elem : confElems) {
			String key = elem.getAttribute("producer_class");
			if (key != null) {
				List<IConfigurationElement> value = new LinkedList<IConfigurationElement>();
				for (IConfigurationElement field : elem.getChildren("configurationField")) {
					value.add(field);
				}
				if (!value.isEmpty()) {
					result.put(key, value);
				}
			}
		}
		return result;
	}
	
	private void initializeCache() {
		final IConfigurationElement[] confElems = Platform.getExtensionRegistry().getConfigurationElementsFor(this.EXTENSION_POINT);
		this.cache = new HashMap<String, IConfigurationElement>(confElems.length == 0 ? 1 : confElems.length);
		for (IConfigurationElement elem : confElems) {
			Object previous = cache.put(elem.getAttribute("name"), elem);
			if (previous != null) {
				PAFActivator.getDefault().getLog().log(new Status(IStatus.WARNING, PAFActivator.PLUGIN_ID, "Two extensions of de.hpi.sam.paf.Producer both used the same name attribute to register an IProducer")); //$NON-NLS-1$
			}
		}
	}
}
