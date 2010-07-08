package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Helper class for deciding whether two models are Ecore equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class EcoreEqualityHelper extends EcoreUtil.EqualityHelper {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Whether attributes should be ignored:
	private boolean ignoreAttributes;

	/**
	 * Default constructor.
	 * @param ignoreAttributes Whether attributes should be ignored.
	 */
	public EcoreEqualityHelper(boolean ignoreAttributes) {
		this.ignoreAttributes = ignoreAttributes;
	}
	
	/**
	 * Check Ecore equality for two resources.
	 * @param r1 Resource 1.
	 * @param r2 Resource 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Resource r1, Resource r2) {
		return equals(r1.getContents(), r2.getContents());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#haveEqualAttribute(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
	 */
	@Override
    protected boolean haveEqualAttribute(EObject o1, EObject o2, EAttribute attribute) {
    	return ignoreAttributes || super.haveEqualAttribute(o1, o2, attribute);
    }
	
}
