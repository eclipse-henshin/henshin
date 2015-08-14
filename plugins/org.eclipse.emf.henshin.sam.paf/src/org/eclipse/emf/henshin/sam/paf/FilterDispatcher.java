package org.eclipse.emf.henshin.sam.paf;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

/**
 * Der <code>FilterDispatcher</code> ist dafuer verantworlich, dass zwischen zwei konsekutiven <code>
 * IFilter</code>n die richtige <code>IPipe</code> erzeugt wird. Dazu greift er auf seine <code>
 * IPipeFactory</code> zurueck. Ist ein <code>IFilter</code> auf eine Situation gestossen, die einen
 * Abbruch der gesamten Berechnung erfordert, so teilt er dies dem <code>FilterDispatcher</code>
 * mit. Dieser stoppt dann alle laufenden Filter.
 */
public class FilterDispatcher implements UncaughtExceptionHandler
{
	
   private Thread[] filterThreads = null;

   /**
    * <pre>
    *                   0..1        dispatches         0..n 
    * FilterDispatcher ------------------------------------- IDispatchable
    *                   filterDispatcher      iDispatchable 
    * </pre>
    */
   private Set<IDispatchable> iDispatchable;
   
   private boolean continueComputation;
   
   final PrintStream outputStream;
   
   /**
    * The readLock allows several <i>reader</i> Threads for the <code>continueComputation</code>
    * attribute.  Access is granted as long as no writer is active.
    */
   private Lock readLock;
   
   /**
    * A <code>Lock</code> for write access to the <code>continueComputation</code> attribute. In
    * contrast to <code>readLock</code> this <code>Lock</code> works exclusively. 
    */
   private Lock writeLock;
   
   /**
    * The input that is used for the associated <code>Filters</code> for their computation. 
    */
   private EObject filterInput;
   
   /**
    * Options for filter configuration.
    */
   private final Map<String, Map<String, Boolean>> options;
   
public FilterDispatcher() {
		this(null, false);
   }
	
public FilterDispatcher(Map<String, Map<String, Boolean>> options, boolean fileOutput) {
	if (options == null) {
		this.options = new HashMap<String, Map<String, Boolean>>();
	} else {
		this.options = options;
	}
	
	PrintStream tmpStream;
	if (fileOutput) {
		try {
			tmpStream = new PrintStream("verification-"+System.currentTimeMillis()+".log");
		} catch (FileNotFoundException e) {
			System.err.println("unable to create verification.log. Using standard console output instead");
			tmpStream = System.out;
		}
	} else {
		tmpStream = System.out;
	}
	this.outputStream = tmpStream;
   ReadWriteLock continueRWLock = new ReentrantReadWriteLock(true);
   this.readLock = continueRWLock.readLock();
   this.writeLock = continueRWLock.writeLock();
}


	Map<String, Boolean> getOptionsFor(Class<?> clazz) {
		if (this.options.get(clazz.getName()) != null) {
			return this.options.get(clazz.getName());
		} else {
			return new HashMap<String, Boolean>();
		}
		
	}

   public boolean addToIDispatchable(IDispatchable value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.iDispatchable == null)
         {
            this.iDispatchable = new HashSet<IDispatchable> (); // or FTreeSet () or FLinkedList ()
         }
         
         changed = this.iDispatchable.add (value);
         if (changed)
         {
            value.setFilterDispatcher (this);
         }
         
      }
      return changed;
   }

   public boolean hasInIDispatchable(IDispatchable value)
   {
      return ((this.iDispatchable != null) &&
              (value != null) &&
              this.iDispatchable.contains (value));
   }

   public Iterator<IDispatchable> iteratorOfIDispatchable()
   {
      return ((this.iDispatchable == null)
              ? new EmptyGenericIterator<IDispatchable>()
              : this.iDispatchable.iterator ());
   }

   public void removeAllFromIDispatchable()
   {
      IDispatchable tmpValue;
      Iterator<IDispatchable> iter = this.iteratorOfIDispatchable ();
      while (iter.hasNext ())
      {
         tmpValue = iter.next ();
         this.removeFromIDispatchable (tmpValue);
      }
   }

   public boolean removeFromIDispatchable(IDispatchable value)
   {
      boolean changed = false;
      if ((this.iDispatchable != null) && (value != null))
      {
         
         changed = this.iDispatchable.remove (value);
         if (changed)
         {
            value.setFilterDispatcher (null);
         }
         
      }
      return changed;
   }

   public int sizeOfIDispatchable()
   {
      return ((this.iDispatchable == null)
              ? 0
              : this.iDispatchable.size ());
   }

   public void startFilter()
   {
      if (this.filterThreads != null) {
    	  throw new IllegalStateException("calling startFilter is only allowed at this time");
      }
      
      final ThreadMXBean tmxBean = ManagementFactory.getThreadMXBean();
      if (tmxBean != null && tmxBean.isThreadCpuTimeSupported()) {
    	  try {
			if (tmxBean.isThreadCpuTimeEnabled() == false)
			  tmxBean.setThreadCpuTimeEnabled(true);
		} catch (UnsupportedOperationException e) {
			PAFActivator.getDefault().getLog().log(new Status(Status.WARNING, PAFActivator.PLUGIN_ID, "Please check your VM! We received the following exception although the VM claims to support ThreadCPUTime", e));//$NON-NLS-1$
		} catch (SecurityException se) {
			PAFActivator.getDefault().getLog().log(new Status(Status.WARNING, PAFActivator.PLUGIN_ID, "Enabling ThreadCPUTime failed. Please check your VM's documentation", se));//$NON-NLS-1$
		}
      } else {
    	  PAFActivator.getDefault().getLog().log(new Status(IStatus.INFO,PAFActivator.PLUGIN_ID,"Your VM does not support ThreadCPUTime. Thus, we won't meassure the time each thread is actually computing."));
      }
      
      final ArrayList<Thread> list = new ArrayList<Thread>(this.sizeOfIDispatchable() / 2);
      this.continueComputation = true;
      for (Iterator<IDispatchable> iter = this.iteratorOfIDispatchable(); iter.hasNext(); ) {
      	IDispatchable iDis = iter.next();
      	try {
      		Runnable run = (Runnable) iDis;
      		String threadName = "Thread-"+run.getClass().getName();
      		Thread t = new Thread(run,threadName);
      		t.setUncaughtExceptionHandler(this);
      		t.start();
      		list.add(t);
      	} catch (ClassCastException cce) {
      		//nothing to worry about. 
      	}
      }
      this.filterThreads = new Thread[list.size()];
      int index = 0;
      for (Iterator<Thread> iter = list.iterator(); iter.hasNext(); ) {
    	  this.filterThreads[index] = iter.next();
    	  index++;
      }
   }


   /**
    * Mit dieser Methode kann der gesamte Berechnungsprozess gestoppt werden. Gedacht ist diese
    * Methode fuer die <code>IFilter</code>, wenn diese in einen Zustand gelangen an dem eine weitere
    * Berechnung keinen Sinn ergeben wuerde. Sie kann jedoch auch von anderen Objekten aufgerufen
    * werden. Zum Beispiel von einer GUI-Action, um dem Benutzer so die Moeglichkeit einzuraeumen die
    * Berechnung vorzeitig abzubrechen.
    */
   public synchronized void stopFilter()
   {
      if (this.filterThreads != null) {
    	  this.setContinueComputation(false);
    	  for (int i = 0; i < this.filterThreads.length; i++) {
    		  try {
				this.filterThreads[i].join();
			} catch (InterruptedException e) {
			}
			this.filterThreads[i] = null;
    	  }
    	  this.filterThreads = null;
      }
   }

   /**
    * resets all data and computation results in the associated instances of
    * <code>IDispatchable</code>
    * @see IDispatchable#reset()
    */
   public void resetIDispatchable() {
	   for (Iterator<IDispatchable> iter = this.iteratorOfIDispatchable(); iter.hasNext(); iter.next().reset())
		   ;
   }
   
   /**
    * This method is used to wait until the computation made by this dispatchers <code>IDispatchable</code>s 
    * has finished. If the current <code>Thread</code> gets interrupted while waiting in this method
    * an <code>InterruptedException</code> will be thrown.
    * @throws InterruptedException if the current <code>Thread</code> gets interrupted
    * @see Thread#join()
    * @see InterruptedException
    * @see Thread#interrupt()
    */
   public void waitOnTermination() throws InterruptedException {
	   if (this.filterThreads != null) {
		   for (int i = 0; i < this.filterThreads.length; i++) {
			   this.filterThreads[i].join();
		   }
	   }
   }

   public void interruptComputation() {
	   this.continueComputation = false;
	   if (this.filterThreads != null) {
		   for (int index = 0; index < this.filterThreads.length; index++) {
			   this.filterThreads[index].interrupt();
		   }
	   }
   }
   
   public void removeYou()
   {
	   this.interruptComputation();
      removeAllFromIDispatchable ();
   }

   /**
    * returns <code>true</code> if the computation is still processing. <br />
    * <code>IFilter</code> implementations should periodically poll this method in order
    * to get informed about stop signals.<br />
    * Even if this method is not declared synchronized it uses locking mechanisms.
    * @return
    */
public boolean isContinueComputation() {
	this.readLock.lock();
	try {
		return this.continueComputation;
	} finally {
		this.readLock.unlock();
	}
}

public void setContinueComputation(boolean cont) {
	this.writeLock.lock();
	try {
		this.continueComputation = cont;
	} finally {
		this.writeLock.unlock();
	}
}
/**
 * 
 * @return
 * @deprecated
 */
public Map<Thread,Long> getCPUTimePerThread() {
	Map<Thread, Long> result = null;
	final ThreadMXBean tmxBean = ManagementFactory.getThreadMXBean();
	if (tmxBean != null && tmxBean.isThreadCpuTimeSupported()) {
		result = new HashMap<Thread, Long>(this.filterThreads.length);
		for (Thread thread : this.filterThreads) {
			result.put(thread, tmxBean.getThreadUserTime(thread.getId()));
		}
	}	
	return result;
}

public Map<IFilter<?, ?>,Long> getCPUTimePerFilter() {
	Map<IFilter<?, ?>, Long> result = null;
	result = new HashMap<IFilter<?, ?>, Long>(this.iDispatchable.size());
	for (IDispatchable disp : this.iDispatchable) {
			if (disp instanceof IFilter<?, ?>) {
				result.put((IFilter<?, ?>) disp, ((IFilter<?, ?>) disp).getThreadTime());				
			}			
	}	
	return result;
}

public EObject getFilterInput() {
	return this.filterInput;
}

public boolean setFilterInput(EObject value) {
	if (this.filterInput != value) {
		this.filterInput = value;
		return true;
	}
	return false;
}

public Map<String, Map<String, String>> getComputationDetails() {
	Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String,String>>(this.filterThreads.length);
	try {
		this.waitOnTermination();
		for (IDispatchable t : this.iDispatchable) {
			if (t instanceof IConsumer<?>) {
				IConsumer<?> ic = (IConsumer<?>) t;
				result.put(ic.getName(), ic.getResult());
			} else if (t instanceof IProducer<?>) {
				IProducer<?> ip = (IProducer<?>) t;
				result.put(ip.getName(), ip.getResult());
			}
		}		
	} catch (InterruptedException e) {
		result = null;
	}
	return result;
}

/**
 * Stops the computation of all filter threads in presence of an uncaught exception.<p>
 * We have registered this <code>FilterDispatcher</code> as {@link Thread.UncaughtExceptionHandler UncaughtExceptionHandler} for
 * all {@link IFilter Filter} threads started by this FilterDispatcher. In case of an uncaught exception this method is 
 * invoked, logs a error message containing a stack trace and stops all other <code>Threads</code> by setting
 * {@link #continueComputation} to <code>false</code>.
 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(Thread, Throwable)
 */
public void uncaughtException(Thread t, Throwable e) {
	PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "Received uncaught exception in filter thread", e));
	this.setContinueComputation(false);
	this.interruptComputation();
}
}
