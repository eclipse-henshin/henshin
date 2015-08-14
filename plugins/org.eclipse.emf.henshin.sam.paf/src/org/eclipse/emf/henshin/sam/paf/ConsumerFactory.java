package org.eclipse.emf.henshin.sam.paf;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

public interface ConsumerFactory {
		
		/**
		 * Creates a <code>IConsumer</code> registered under the specified name.<br />
		 * You can use this method to create a new instance of an <code>IConsumerr</code> that has been registered using the <code>de.hpi.sam.paf.Consumer</code> extension point. If the running system contains a consumer declaration like:
		 * <code>
		 * <consumer 
		 *    name = "foo" 
		 *    consumer_class = "bar">
		 *  </consumer>
		 *  </code>
		 *  the result of <code>createConsumer("foo")</code> is a new instance of the class "bar".
		 * @param name The name of <code>IConsumer</code> to create
		 * @throws UnknownConsumerException This exception if thrown, if the name passed as argument is not registered using the <code>de.hpi.sam.paf.Consumer</code> extension point.
		 * @return a new instance of the class registered under the given name.
		 * @see PAFActivator#getConsumerFactory()
		 */
	public IConsumer<?> createConsumer(String name) throws UnknownConsumerException;
	
	public String[] getAllConsumerNames();
	
	public Map<String, List<IConfigurationElement>> getAllConfigFields();
}
