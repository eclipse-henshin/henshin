package org.eclipse.emf.henshin.statespace.equality;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.statespace.Model;

/**
 * Helper class for deciding whether two models are Ecore equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class EcoreEqualityHelper extends EcoreUtil.EqualityHelper {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;

	// Whether node IDs should be ignored:
	private boolean ignoreNodeIDs;

	// Whether attributes should be ignored:
	private boolean ignoreAttributes;
	
	// Cached models to be compared.
	private Model model1, model2;
	
	/**
	 * Default constructor.
	 * @param ignoreNodeIDs Whether node IDs should be ignored.
	 * @param ignoreAttributes Whether attributes should be ignored.
	 */
	public EcoreEqualityHelper(boolean ignoreNodeIDs, boolean ignoreAttributes) {
		this.ignoreNodeIDs = ignoreNodeIDs;
		this.ignoreAttributes = ignoreAttributes;
	}
	
	/**
	 * Check Ecore equality for two models.
	 * @param model1 Model 1.
	 * @param model2 Model 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Model model1, Model model2) {
		
		// Cache the models:
		this.model1 = model1;
		this.model2 = model2;
		
		// Check equality:
		boolean result = 
			equals(	model1.getResource().getContents(), 
					model2.getResource().getContents());
		
		// Release the models again:
		this.model1 = null;
		this.model2 = null;
		
		// Done.
		return result;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean equals(EObject o1, EObject o2) {
		return (ignoreNodeIDs || model1.getNodeIDsMap().get(o1)==model2.getNodeIDsMap().get(o2)) && super.equals(o1,o2);
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
