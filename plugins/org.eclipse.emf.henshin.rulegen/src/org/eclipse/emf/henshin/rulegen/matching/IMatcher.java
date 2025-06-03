package org.eclipse.emf.henshin.rulegen.matching;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Basic model matcher interface.
 * 
 * @author Timo Kehrer
 */
public interface IMatcher {
	
	/**
	 * Creates a matching, i.e., determines corresponding elements in modelA and modelB.
	 */
	public Matching createMatching(Resource modelA, Resource modelB);
	
	/**
	 * Returns whether the matcher can handle the given models.
	 */
	public boolean canHandle(Resource modelA, Resource modelB);
}
