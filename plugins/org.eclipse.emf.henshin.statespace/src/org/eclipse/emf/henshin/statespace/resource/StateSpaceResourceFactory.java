package org.eclipse.emf.henshin.statespace.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A resource factory for Henshin state space files.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceResourceFactory implements Resource.Factory {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory#createResource(org.eclipse.emf.common.util.URI)
	 */
	public Resource createResource(URI uri) {		
		return new StateSpaceResource(uri);
	}
	
}
