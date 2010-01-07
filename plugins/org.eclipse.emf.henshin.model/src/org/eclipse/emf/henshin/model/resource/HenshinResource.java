package org.eclipse.emf.henshin.model.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * Resource implementation for Henshin resources.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinResource extends XMIResourceImpl {
	
	/**
	 * Default constructor.
	 */
	public HenshinResource() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri URI of a Henshin resource.
	 */
	public HenshinResource(URI uri) {
		super(uri);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
	 */
	protected boolean useUUIDs() {
		return true;
	}
	
}
