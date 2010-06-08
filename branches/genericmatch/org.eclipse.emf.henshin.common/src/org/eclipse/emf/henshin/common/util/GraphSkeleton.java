package org.eclipse.emf.henshin.common.util;

import java.util.Collection;

public interface GraphSkeleton<TType, TNode> {

	/**
	 * Returns all possible EObjects of this graph which are compatible with the
	 * given type.
	 * 
	 * @param type
	 *            The type of the eObjects.
	 * 
	 * @return A collection of eObjects compatible with the type.
	 */
	public Collection<TNode> getDomainForType(TType type);

	/**
	 * Adds a new eObject to this graph.
	 * 
	 * @param eObject
	 *            The eObject which will be added to the graph.
	 * 
	 * @return true, if the object was added. false, if it is already contained
	 *         in the graph.
	 */
	public boolean addEObject(TNode eObject);
	
	/**
	 * Removes an eObject to this graph.
	 * 
	 * @param eObject
	 *            The eObject which will be removed from the graph.
	 * 
	 * @return true, if the object was removed. false, if it wasn't contained in
	 *         the graph.
	 */
	public boolean removeEObject(TNode eObject);
}
