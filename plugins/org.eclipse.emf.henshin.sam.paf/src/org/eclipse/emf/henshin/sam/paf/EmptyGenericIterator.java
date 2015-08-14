package org.eclipse.emf.henshin.sam.paf;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyGenericIterator<T> implements Iterator<T> {

	public boolean hasNext() {
		return false;
	}

	public T next() {
		throw new NoSuchElementException("Iterator ist empty");
	}

	public void remove() {
		throw new UnsupportedOperationException("operation: remove is not supported by this iterator");
	}

}
