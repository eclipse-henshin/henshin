package org.eclipse.emf.henshin.sam.paf;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

/**
 * Most of the {@link IFilter} instances getting developed only differ in the
 * logics of the two methods <code>produce</code> and <code>consume</code>.
 * The other methods are not completely but highly redundant. The introduction
 * of this skeleton aims at reducing the redundancy. In most cases it is
 * appropriate to use this abstract class as super type for your new
 * <code>IFilter</code> instance but if in doubt use
 * <code>AbstractFilter</code> instead.<br/> The difference between the
 * <code>FilterSkeleton</code> and the <code>AbstractFilter</code> is the
 * fact that the <code>AbstractFilter</code> class only provides support for
 * the association management but the <code>FilterSkeleton</code> in addition
 * provides almost complete <code>IFilter</code> implementation.
 * 
 * @author Basil.Becker
 * 
 * @param <T>
 *            see <code>AbstractFilter</code> for details.
 * @param <S>
 *            see <code>AbstractFilter</code> for details.
 */
public abstract class FilterSkeleton<T, S> extends AbstractFilter<T, S> {

	/**
	 * the <code>IPipe</code> instance normally input is read from
	 */
	protected IPipe<T> defaultInputPipe;

	/**
	 * the <code>IPipe</code> instance computation result are written to by
	 * default.
	 */
	protected IPipe<S> defaultOutputPipe;

	protected T currentInput;

	private Map<String, Boolean> options;
	
	protected void println(String s) {
		this.getFilterDispatcher().outputStream.println(s);
	}
	
	protected Boolean getOption(String name) throws RuntimeException{
		Boolean result = this.options.get(name);
		if (result == null) {
			RuntimeException e = new UnknownOptionException("Filter " + this.filterName + " does not register an option '" + name + "'");
			PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "Configuration option '" + name + "' does not exist for filter '" + this.filterName + "'.", e)); //$NON-NLS-1$
			throw e;
		} else {
			return result;
		}		
	}
	
	/**
	 * a boolean flag to control whether this <code>IFilter</code> is active.
	 */
	protected boolean running;
	
	/**
	 * Initializes the filter.<br/> This method by default initializes the fields
	 * {@link #defaultInputPipe} and {@link #defaultOutputPipe} with the
	 * <code>IPipe</code> instances registered under the keys
	 * {@link AbstractFilter#DEFAULT_INPUT} respective
	 * {@link AbstractFilter#DEFAULT_OUTPUT}. Does your <code>IFilter</code>
	 * need a special initialization override this method. This method is
	 * invoked by the {@link IFilter#filter()} method as the first step.
	 */
	protected void initFilter() {
		this.defaultInputPipe = this
				.getInputPipes().get(AbstractFilter.DEFAULT_INPUT);
		this.defaultOutputPipe = this
				.getOutputPipes().get(AbstractFilter.DEFAULT_OUTPUT);		
		initData();
		this.filterName = this.getClass().getSimpleName();
		this.running = this.getFilterDispatcher().isContinueComputation();
	}

	/**
	 * Shuts down this filter.<br/> This method's default behavior is to
	 * decrement the {@link #defaultInputPipe}'s number of readers and to close
	 * the {@link #defaultOutputPipe}. In case that your <code>IFilter</code>
	 * needs any special operations to shut down properly simply override this
	 * method. References to the default input- and output-pipe hold by this <code>IFilter</code> are set to <code>null</code> by this method.  If a subclass uses more than the default pipes it has to ensure to unset the references to those by itself. This method is invoked within the {@link #filter()} method's
	 * finally block.
	 */
	protected void shutDown() {
		if (this.defaultInputPipe != null) {
			this.defaultInputPipe.decrementReaders();
			this.defaultInputPipe = null;
		}
		if (this.defaultOutputPipe != null) {
			this.defaultOutputPipe.close();
			this.defaultOutputPipe = null;
		}
	}

	/**
	 * This method contains the main computation loop for this
	 * <code>IFilter</code>.<br/> It is highly recommended not to override
	 * this method despite you really know what your are doing. The recommended
	 * way of adapting this skeleton class to your needs is to override
	 * {@link #initFilter()}, {@link #shutDown()} and {@link #consume()}.
	 */
	@Override
	public void filter() {
		this.options = this.getFilterDispatcher().getOptionsFor(this.getClass());
		this.initFilter();
		try {
			while (running) {
				this.consume();
				if (!running)
					continue;
				this.produce();
				this.running = this.getFilterDispatcher()
						.isContinueComputation();
			}
		} finally {			
			final ThreadMXBean tmxBean = ManagementFactory.getThreadMXBean();
			if (tmxBean != null && tmxBean.isThreadCpuTimeSupported()) {
				this.threadTime = tmxBean.getCurrentThreadUserTime();
			}						
			this.shutDown();			
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
	
	protected void initData() {
		
	}

	/**
	 * A default implementation for {@link IConsumer#consume()}.<br/>This
	 * method reads the next element from the {@link #defaultInputPipe} and
	 * stores it to {@link #currentInput}. If a {@link IllegalStateException}
	 * occurs {@link #running} will be set to <code>false</code>.
	 */
	public void consume() {
		try {
			this.currentInput = this.defaultInputPipe.dequeue();
		} catch (IllegalStateException ise) {
			this.running = false;
		} catch (InterruptedException ie) {
			this.running = false;
		}
	}
	
	@Override
	protected void prepareResult() {
		this.resultDictionary = new LinkedHashMap<String, String>();
		final Class<?> clazz = getClass();
		for (Field f : clazz.getDeclaredFields()) {
			ResultDictEntry rde = f.getAnnotation(ResultDictEntry.class);
			if (rde != null) {				
				Object val = null;
				boolean exception = false;
				try {
					if (f.isAccessible() == false)
						f.setAccessible(true);
					val = f.get(this);
				} catch (IllegalArgumentException e) {
					exception = true;
				} catch (IllegalAccessException e) {
					exception = true;
				}
				if (exception == false) {
					if (val == null) {
						val = "Property not set! (null)";
					}
					this.resultDictionary.put(rde.entryName(), val.toString());
				}
			}
		}
		
		for (Map.Entry<String, Boolean> entry : this.options.entrySet()) {
			this.resultDictionary.put(entry.getKey(), entry.getValue().toString());
		}
	}
}
