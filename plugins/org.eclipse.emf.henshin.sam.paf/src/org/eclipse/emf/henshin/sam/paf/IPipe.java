package org.eclipse.emf.henshin.sam.paf;

/**
 * Eine <code>IPipe</code> ist eine Queue. <code>IFilter</code> schreiben
 * ihre Ergebnisse in eine <code>IPipe</code> andere lesen sie von dort wieder
 * aus. Zusaetzlich sorgt eine <code>IPipe</code> durch die Methoden
 * <code>push</code> und <code>pop</code> fuer eine Synchronisierung der
 * <code>IFilter</code> untereinander. <br />
 * Es ist vorstellbar, dass eine <code>IPipe</code> die Anzahl der in ihr
 * gespeicherten Elemente begrenzt, um so einem Heapoverflow vorzubeugen. Dazu
 * ist noetig ab einem Grenzwert den schreibenden <code>Thread</code> zu
 * blockiern. Genauso muss eine <code>IPipe</code> den lesenden
 * <code>Thread</code> mindestens so lange blockieren, wie die
 * <code>IPipe</code> leer ist. Wann sie schlafende <code>Thread</code>s
 * wieder aufweckt bleibt dem Entwickler ueberlassen.
 */
public interface IPipe<T> extends IDispatchable {
	public void clear();

	/**
	 * Dequeues the <code>IPipe</code>'s first element and returns it. If the
	 * <code>IPipe</code> is empty this method blocks the calling
	 * <code>Thread</code> until an element is available.<br />
	 * If this <code>IPipe</code> is closed, that means there won't be any
	 * further input, this method throws an <code>IllegalStateException</code>
	 * in order to prevent the calling <code>Thread</code> from beeing
	 * senselessly blocked.
	 * 
	 * @return the <code>IPipe</code>'s first element
	 * @throws IllegalStateException
	 *             if this <code>IPipe</code> is closed and empty.
	 */
	public T dequeue() throws IllegalStateException, InterruptedException;

	/**
	 * Enqueues the element <code>input</code> in the <code>IPipe</code>.
	 * This method might block the calling <code>Thread</code> if the
	 * <code>IPipe</code> implementation uses a maximum number of elements to
	 * be in the <code>IPipe</code> at time.
	 * 
	 * @param input
	 *            The element to enque
	 */
	public void queue(T input) throws InterruptedException;

	/**
	 * Return the <code>IPipe</code>'s size, that means the number of
	 * elements currently stored in this <code>IPipe</code>.
	 * 
	 * @return the <code>IPipe</code>'s size.
	 */
	public int sizeOfPipe();

	/**
	 * After calling this method an invocation of <code>IPipe.dequeue</code>
	 * on an empty <code>IPipe</code> won't result in blocked
	 * <code>Thread</code> but will cause an
	 * <code>IllegalStateException</code> to be thrown.
	 * 
	 */
	public void close();

	public boolean isClosed();

	/**
	 * Increments this <code>IPipe</code>'s number of readers. The number of
	 * readers can be used by <code>IPipe</code> instances to determine how
	 * many <code>Threads</code> are currently connected to this
	 * <code>IPipe</code> and still interested in reading data from this
	 * <code>IPipe</code>
	 * 
	 */
	public void incrementReaders();

	/**
	 * Decrements this <code>IPipe</code>'s number of readers. The number of
	 * readers can be used by <code>IPipe</code> instances to determine how
	 * many <code>Threads</code> are currently connected to this
	 * <code>IPipe</code> and still interested in reading data from this
	 * <code>IPipe</code>
	 * 
	 */
	public void decrementReaders();
}
