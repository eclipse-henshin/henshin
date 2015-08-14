package org.eclipse.emf.henshin.sam.paf;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.paf.preferences.PAFPreferenceConstants;

public class DefaultPipe<T> extends AbstractDispatchable implements IPipe<T> {

	/**
	 * same as
	 * 
	 * <pre>
	 * new DefaultPipe(15, 50, 500);
	 * </pre>
	 * 
	 * @see DefaultPipe#DefaultPipe(int, long, long)
	 */
	public DefaultPipe() {
		this(Platform.getPreferencesService().getInt(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.PIPE_SIZE, 15, null),Platform.getPreferencesService().getLong(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.DEQUEUE_WAIT, 50, null),Platform.getPreferencesService().getLong(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.ENQUEUE_WAIT, 500, null));
	}

	/**
	 * same as
	 * 
	 * <pre>
	 * new DefaultPipe(size, 50, 500);
	 * </pre>
	 * 
	 * @see DefaultPipe#DefaultPipe(int, long, long)
	 */
	public DefaultPipe(int size) {
		this(size, Platform.getPreferencesService().getLong(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.DEQUEUE_WAIT, 50, null), Platform.getPreferencesService().getLong(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.ENQUEUE_WAIT, 500, null));
	}

	/**
	 * same as
	 * 
	 * <pre>
	 * new DefaultPipe(size, waitQueue, 500);
	 * </pre>
	 * 
	 * @see DefaultPipe#DefaultPipe(int, long, long)
	 */
	public DefaultPipe(int size, long waitDequeue) {
		this(size, waitDequeue, Platform.getPreferencesService().getLong(PAFActivator.PLUGIN_ID, PAFPreferenceConstants.ENQUEUE_WAIT, 500, null));
	}

	/**
	 * Creates a new <code>DefaultPipe</code> instance. The new
	 * <code>DefaultPipe</code>'s maximum size (number of elements this pipe
	 * is able to store) ist set to <code>size</code>. <code>Threads</code>
	 * trying to dequeue an element from this pipe wait a maximum of
	 * <code>waitDequeue</code> milliseconds for a signal, after this period
	 * this pipes <code>closed</code> property is checked again.
	 * <code>waitQueue</code> has the same meaning for the
	 * {@link #queue(Object)} operation on this pipe.
	 * 
	 * @param size
	 *            the pipes maximum size
	 * @param waitDequeue
	 *            time to wait before recheck is closed property during
	 *            {@link #dequeue()}
	 * @param waitQueue
	 *            time to wait berfore recheck is closed property during
	 *            {@link #queue(Object)}
	 */
	public DefaultPipe(int size, long waitDequeue, long waitQueue) {
		this.queueWait = waitQueue;
		this.dequeueWait = waitDequeue;
		this.maximumSize = size;
		this.queueLock = new ReentrantLock();
		this.queueCond = this.queueLock.newCondition();
		this.dequeuCond = this.queueLock.newCondition();
		
		this.readerLock = new ReentrantLock();
	}

	private final long queueWait;

	private final long dequeueWait;

	/**
	 * ensures mutual exclusion within the critical sections
	 */
	private final Lock queueLock;

	/**
	 * ensures mutual exclusion within the two methods
	 * {@link #incrementReaders()} and {@link #decrementReaders()}
	 */
	private final Lock readerLock;

	/**
	 * Conditional variable for waiting on and signaling enqueued elements
	 */
	private Condition queueCond;

	/**
	 * Conditional variable for waiting on and signaling dequeued elements
	 */
	private Condition dequeuCond;

	/**
	 * this boolean flag indicates whether this <code>DefaultPipe</code>
	 * instance is closed. A closed <code>DefaultPipe</code> throws an
	 * exception if one tries to dequeue an element and the
	 * <code>DefaultPipe</code> is empty.
	 */
	private boolean closed = false;

	/**
	 * The <code>DefaultPipe</code>'s maximum size measured in number of
	 * elements that could be stored at the same time.
	 */
	private int maximumSize;

	/**
	 * <pre>
	 *                   0..1            Assoc             0..n 
	 *      DefaultPipe ---------------------------------------&gt; Object
	 *                   defaultPipe   {ordered,}   storedElems 
	 * </pre>
	 */
	private LinkedList<T> storedElems;

	private boolean addToStoredElems(T value) {
		boolean changed = false;
		if (value != null && !hasInStoredElems(value)) {
			if (this.storedElems == null) {
				this.storedElems = new LinkedList<T>(); // or FTreeSet () or
				// FLinkedList ()
			}

			changed = this.storedElems.add(value);

		}
		return changed;
	}

	private T getFirstOfStoredElems() {
		if (storedElems == null) {
			return null;
		}
		if (storedElems.size() == 0) {
			return null;
		}
		return storedElems.getFirst();
	}

	private boolean hasInStoredElems(T value) {
		return ((this.storedElems != null) && (value != null) && this.storedElems
				.contains(value));
	}

	private void removeAllFromStoredElems() {
		if (this.storedElems != null && this.storedElems.size() > 0) {

			this.storedElems.clear();

		}
	}

	private boolean removeFromStoredElems(Object value) {
		boolean changed = false;
		if ((this.storedElems != null) && (value != null)) {

			changed = this.storedElems.remove(value);

		}
		return changed;
	}

	private int sizeOfStoredElems() {
		return ((this.storedElems == null) ? 0 : this.storedElems.size());
	}

	public synchronized void clear() {
			this.removeAllFromStoredElems();
	}

	public T dequeue() throws IllegalStateException, InterruptedException {
		if (closed && this.sizeOfPipe() == 0) {
			throw new IllegalStateException("Pipe is closed and empty");
		}
		try {
			this.queueLock.lock();
			T obj = null;

			while (this.sizeOfPipe() == 0) {
				
					// System.err.println(Thread.currentThread().getName() + "
					// going to sleep for a second");
					this.dequeuCond.await(this.dequeueWait,
							TimeUnit.MILLISECONDS); // we
					// wait
					// at
					// least
					// 5s
					// and
					// then
					// what
					// is
					// happening
					// in
					// the
					// world
					// System.err.print(Thread.currentThread().getName() + "
					// awaken from death. ");
				if (closed && this.sizeOfPipe() == 0) {
					throw new IllegalStateException("Pipe is closed and empty");
				}
			}

				obj = this.getFirstOfStoredElems();
				if (obj != null)
					this.removeFromStoredElems(obj);

			if (this.sizeOfPipe() == this.maximumSize - 1) {
				this.queueCond.signal();
			}
			return (obj);
		} finally {
			this.queueLock.unlock();
		}
	}

	public void queue(T input) throws InterruptedException {
		try {
			this.queueLock.lock();
			while (this.sizeOfPipe() >= this.maximumSize) {
				if (this.readers == 0) {
					// we have no readers but a completely filled pipe, so we
					// drop the input...
					return;
				}
				this.queueCond.await(this.queueWait, TimeUnit.MILLISECONDS);
			}
				if(input != null);
					this.addToStoredElems(input);

			if (this.sizeOfPipe() == this.maximumSize) {
				this.dequeuCond.signalAll();
			}
		} finally {
			this.queueLock.unlock();
		}
	}

	public void reset() {
		clear();
	}

	public int sizeOfPipe() {
		return (this.sizeOfStoredElems());
	}

	public void removeYou() {
		removeAllFromStoredElems();
	}

	/**
	 * Closes this <code>DefaultPipe</code> instance. To close a
	 * <code>IPipe</code> means that any further attempt to dequeue an element
	 * from an empty <code>IPipe</code> will result in an
	 * <code>IllegalStateException</code> instead of an conditional wait of
	 * the current <code>Thread</code><br/> It is safe to invoke this method
	 * more than once
	 */
	public void close() {
		this.closed = true;
	}

	public boolean isClosed() {
		return this.closed;
	}

	/**
	 * The private field <code>readers</code> stores the number of readers of
	 * this <code>IPipe</code>. Its value can be influenced by invoking
	 * {@link #incrementReaders()} and {@link #decrementReaders()}.
	 */
	private int readers = 0;

	public void incrementReaders() {
		try {
			this.readerLock.lock();
			this.readers++;
		} finally {
			this.readerLock.unlock();
		}
	}

	public void decrementReaders() {
		try {
			this.readerLock.lock();
			this.readers--;
			assert this.readers >= 0 : "readers is negative, so it must have been decremented once too often.";
		} finally {
			this.readerLock.unlock();
		}
	}
}
