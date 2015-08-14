package org.eclipse.emf.henshin.sam.paf;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

public interface ProducerFactory {
		
		/**
		 * Creates a <code>IProducer</code> registered under the specified name.<br />
		 * You can use this method to create a new instance of an <code>IProducer</code> that has been registered using the <code>de.hpi.sam.paf.Producer</code> extension point. If the running system contains a producer declaration like:
		 * <code>
		 * <producer 
		 *    name = "foo" 
		 *    producer_class = "bar">
		 *  </producer>
		 *  </code>
		 *  the result of <code>createProducer("foo")</code> is a new instance of the class "bar".
		 * @param name The name of <code>IProducer</code> to create
		 * @throws UnknownProducerException This exception if thrown, if the name passed as argument is not registered using the <code>de.hpi.sam.paf.Producer</code> extension point.
		 * @return a new instance of the class registered under the given name.
		 * @see PAFActivator#getProducerFactory()
		 */
		public IProducer<?> createProducer(String name) throws UnknownProducerException;
		
		public String[] getAllProducerNames();
		
		public Map<String, List<IConfigurationElement>> getAllConfigFields();		
}
