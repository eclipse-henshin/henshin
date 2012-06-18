package org.eclipse.emf.henshin.interpreter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;

/**
 * EGraph interface for storing object graphs.
 * 
 * @author Christian Krause, Enrico Biermann
 */
public interface EGraph extends Collection<EObject> {
	
	/**
	 * Adds an {@link EObject} and all its children to this graph.
	 * @param root The root object of the tree.
	 * @return <code>true</code> if an object was added.
	 */
	boolean addTree(EObject root);
	
	/**
	 * Removes an {@link EObject} and all its children from this graph.
	 * @param root The root object of the tree.
	 * @return <code>true</code> if any object was removed. 
	 */
	boolean removeTree(EObject root);
	
	/**
	 * Copy this object graph. If the parameter map is <code>null</code>
	 * the objects in the graph will be copied too. If the map is not
	 * <code>null</code>, the images of the map will be used as the new
	 * objects.
	 * @param copies Map associating object of this graph to copies. Can be <code>null</code>.
	 * @return The copied version of this {@link EGraph}.
	 */
	EGraph copy(Map<EObject,EObject> copies);
	
	/**
	 * Get all {@link EObject}s of this graph which are compatible with the given type.
	 * This returns a fresh and modifiable list.
	 * @param type The type of the objects.
	 * @param strict Whether subtypes are excluded from the result.
	 * @return A set of {@link EObject}s compatible with the type.
	 */
	List<EObject> getDomain(EClass type, boolean strict);
	
	/**
	 * Returns the size of the domain for a type. The returned number
	 * equals the size of the list returned by {@link #getDomain(EClass, boolean)}.
	 * This method should be used whenever the actual objects are not needed.
	 * @param type The type.
	 * @param strict Whether subtypes are excluded.
	 * @return The size of the domain.
	 */
	int getDomainSize(EClass type, boolean strict);
	
	/**
	 * Get the root objects in this graph. This returns a fresh and modifiable list.
	 * @return The root objects.
	 */
	List<EObject> getRoots();

	/**
	 * Get the cross reference adapter of this graph.
	 * @return The cross reference adapter.
	 */
	ECrossReferenceAdapter getCrossReferenceAdapter();

}
