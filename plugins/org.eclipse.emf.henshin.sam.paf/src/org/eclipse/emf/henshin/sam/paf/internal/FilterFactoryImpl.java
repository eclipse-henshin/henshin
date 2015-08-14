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
import org.eclipse.emf.henshin.sam.paf.FilterFactory;
import org.eclipse.emf.henshin.sam.paf.IFilter;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.emf.henshin.sam.paf.UnknownFilterException;

public class FilterFactoryImpl implements FilterFactory {

	private final String EXTENSION_POINT = "de.hpi.sam.paf.Filter";
	private HashMap<String, IConfigurationElement> cache = null;
	
	public IFilter<?, ?> createFilter(String name) throws UnknownFilterException {
		if (this.cache == null) {
			this.initializeCache();
		}
		final IConfigurationElement elem = this.cache.get(name);
		if (elem != null) {
			try {
				final Object o = elem.createExecutableExtension("filter_class");
				IFilter<?, ?> result = IFilter.class.cast(o);								
				return result;
			} catch (CoreException e) {
				PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "CoreException has been thrown, while trying to instantiate filter: "+name, e));
				throw new UnknownFilterException("Unable to create filter becaus of a CoreException", e);
			} catch (ClassCastException e) {
				PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "Unable to cast to IFilter", e));
				throw new UnknownFilterException("Unable to cast to IFilter",e);
			}
		} else {
			throw new UnknownFilterException("No filter with name: "+name+" is known!");
		}
	}
	
	public String[] getAllFilterNames() {
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
			String key = elem.getAttribute("filter_class");
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
			if (elem.getName().equals("filter")) {
				Object previous = cache.put(elem.getAttribute("name"), elem);			
				if (previous != null) {
					PAFActivator.getDefault().getLog().log(new Status(IStatus.WARNING, PAFActivator.PLUGIN_ID, "Two extensions of de.hpi.sam.paf.Filter both used the same name attribute to register an IFilter")); //$NON-NLS-1$
				}
			}
		}
	}	
}
