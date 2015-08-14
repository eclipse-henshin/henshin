package org.eclipse.emf.henshin.sam.paf;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayPipe<T> implements IPipe<T> {

	private T[] data;

	private int lastIndex = 0;

	private FilterDispatcher filterDispatcher;

	private Condition dequeuCond;

	private Condition queueCond;

	private Lock pipeLock;

	private boolean closed = false;

	public void clear() {
		this.pipeLock.lock();
		for (int i = 0; i < this.lastIndex; i++) {
			this.data[i] = null;
		}
		this.lastIndex = 0;
		this.pipeLock.unlock();
	}

	public T dequeue() throws IllegalStateException {
		if (this.closed && this.lastIndex == 0) {
			throw new IllegalStateException("Pipe is closed and empty!");
		}
		try {
			this.pipeLock.lock();
			while (lastIndex == 0) {
				try {
					this.dequeuCond.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			T result = this.data[this.lastIndex - 1];
			this.lastIndex--;
			if (this.lastIndex == this.data.length - 1)
				this.queueCond.signal();
			return result;
		} finally {
			this.pipeLock.unlock();
		}
	}

	public void queue(T input) {
		try {
			this.pipeLock.lock();
			while (this.lastIndex >= this.data.length) {
				try {
					this.queueCond.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.data[this.lastIndex] = input;
			this.lastIndex++;
			if (this.lastIndex == 1)
				this.dequeuCond.signal();
		} finally {
			this.pipeLock.unlock();
		}
	}

	public int sizeOfPipe() {
		this.pipeLock.lock();
		int res = this.lastIndex;
		this.pipeLock.unlock();
		return res;
	}

	public FilterDispatcher getFilterDispatcher() {
		return this.filterDispatcher;
	}

	public boolean setFilterDispatcher(FilterDispatcher value) {
		boolean changed = false;
		if (value != this.filterDispatcher) {
			FilterDispatcher oldValue = this.filterDispatcher;
			if (oldValue != null) {
				this.filterDispatcher = null;
				oldValue.removeFromIDispatchable(this);
			}
			changed = true;
			this.filterDispatcher = value;
			if (value != null) {
				value.addToIDispatchable(this);
			}
		}
		return changed;
	}

	public void reset() {
		this.clear();

	}

	@SuppressWarnings("unchecked")
    public ArrayPipe(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException(
					"the pipe's size must not be less or equal 0");
		}
		this.data = (T[]) new Object[size];
		
		this.pipeLock = new ReentrantLock();
		this.queueCond = this.pipeLock.newCondition();
		this.dequeuCond = this.pipeLock.newCondition();
	}

	public void close() {
		this.closed = true;
	}

	public boolean isClosed() {
		return this.closed;
	}

	private int readers = 0;
	
	public void decrementReaders() {
		this.readers--;
	}

	public void incrementReaders() {
		this.readers++;
	}

}
