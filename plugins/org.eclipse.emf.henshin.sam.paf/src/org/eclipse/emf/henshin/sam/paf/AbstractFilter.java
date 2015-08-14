package org.eclipse.emf.henshin.sam.paf;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractFilter<T,S>
extends AbstractDispatchable
implements IFilter<T,S>
{
	/**
	 * Use this <code>String</code> as key if you want to address a <code>IFilter</code>'s default input <code>IPipe</code>
	 */
	public static final String DEFAULT_INPUT = "DefaultInputPipe";
	
	/**
	 * Use this <code>String</code> as key if you want to address a <code>IFilter</code>'s default output <code>IPipe</code>
	 */
	public static final String DEFAULT_OUTPUT = "DefaultOutputPipe";
	
   protected String filterName;
   
	/**
	 * a long indicating the user time of the current thread, set before shutting down the thread
	 */
	long threadTime;
   
   /**
    * This field is used to store the <code>Dictionary</code> holding
    * the results - i.e. statistics etc. - of the <code>IFilter</code>'s run.
    * @see #prepareResult()
    * @see #getResult()
    */
   protected Map<String, String> resultDictionary = null;

   /**
    * A <code>Set</code> that stores references to concurrent <code>IProducer</code>s this <code>AbstractFilter</code>
    * instance depends on.  Currently this set is mainly used for termination determination.
    */
   private Set<IProducer<T>> dependingProducer;
   
   private Map<String, IPipe<T>> inputPipes;
   
   private Map<String, IPipe<S>> outputPipes;
   
   public abstract void filter();

   public String getName() {
	   return this.filterName;
   }
   
   public Map<String, IPipe<T>> getInputPipes() {
	if (this.inputPipes == null) {
		this.inputPipes = new HashMap<String, IPipe<T>>();
	}
	return this.inputPipes;
}
   public Map<String, IPipe<S>> getOutputPipes() {
	   if (this.outputPipes == null) {
		   this.outputPipes = new HashMap<String, IPipe<S>>();
	   }
	return this.outputPipes;
}

   public void run()
   {
      this.filter();
   }
   
   @Override
   public Map<String, String> getResult() {
	   if (this.resultDictionary == null) {
		   this.prepareResult();
	   }
	   return this.resultDictionary;
   }

   /**
    * Use this method to prepare and fill the {@link #resultDictionary}.<br />
    * <p>This method is called by the {@link #getResult()} implementation of <code>AbstractFilter</code>.
    * The method is not called, if <code>resultDictionary</code> is not <code>null</code> at the
    * point in time, <code>getResult</code> is invoked.</p>
    * <p>This implementation solely creates a new {@link Dictionary} instance.</p>
    */
   protected void prepareResult() {
	   
   }
   
   protected boolean addToDependingProducer(IProducer<T> value) {
	   boolean changed = false;
	   if (value != null) {
		   if (this.dependingProducer == null) {
			   this.dependingProducer = new HashSet<IProducer<T>>();
		   }
		   changed = this.dependingProducer.add(value);
	   }
	   return changed;
   }
   
   protected boolean removeFromDependingProducer(IProducer<T> value) {
	   boolean changed = false;
	   if (value != null && this.dependingProducer != null) {
		   changed = this.dependingProducer.remove(value);
	   }
	   return changed;
   }
   
   protected Iterator<IProducer<T>> iteratorOfDependingProducer() {
	   return this.dependingProducer != null ? this.dependingProducer.iterator() : new EmptyGenericIterator<IProducer<T>>();
   }
   
   public long getThreadTime() {
	   return threadTime;
   }
}
