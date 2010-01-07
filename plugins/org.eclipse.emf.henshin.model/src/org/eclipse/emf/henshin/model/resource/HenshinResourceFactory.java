package org.eclipse.emf.henshin.model.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Resource factory for Henshin resources.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinResourceFactory extends XMIResourceFactoryImpl {
	
	/**
	 * Default constructor.
	 */
	public HenshinResourceFactory() {
		super();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		return new HenshinResource(uri);
	}
	
}
