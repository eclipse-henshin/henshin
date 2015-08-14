package org.eclipse.emf.henshin.sam.paf;

import java.util.Map;

public interface IConsumer<T> extends Runnable, IDispatchable {
	public Map<String, IPipe<T>> getInputPipes();

	public void consume();

	public String getName();

	public Map<String, String> getResult();
}
