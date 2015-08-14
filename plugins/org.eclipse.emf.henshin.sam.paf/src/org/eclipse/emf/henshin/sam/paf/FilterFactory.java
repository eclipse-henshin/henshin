package org.eclipse.emf.henshin.sam.paf;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

public interface FilterFactory {
		
		/**
		 * Creates a <code>IFilter</code> registered under the specified name.<br />
		 * You can use this method to create a new instance of an <code>IFilter</code> that has been registered using the <code>de.hpi.sam.paf.Filter</code> extension point. If the running system contains a filter declaration like:
		 * <code>
		 * <filter 
		 *    name = "foo" 
		 *    filter_flass = "bar">
		 *  </filter>
		 *  </code>
		 *  the result of <code>createFilter("foo")</code> is a new instance of the class "bar".
		 * @param name The name of <code>IFilter</code> to create
		 * @throws UnknownFilterException This exception if thrown, if the name passed as argument is not registered using the <code>de.hpi.sam.paf.Filter</code> extension point.
		 * @return a new instance of the class registered under the given name.
		 * @see PAFActivator#getFilterFactory()
		 */
		public IFilter<?,?> createFilter(String name) throws UnknownFilterException;
		
		public String[] getAllFilterNames();
		
		public Map<String, List<IConfigurationElement>> getAllConfigFields();		
}
