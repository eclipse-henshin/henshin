package org.eclipse.emf.henshin.sam.paf;

import java.util.Map;

public interface IProducer<T> extends Runnable, IDispatchable {

	public Map<String, IPipe<T>> getOutputPipes();

	public String getName();

	public void produce();
	
	public Map<String, String> getResult();
}
