package org.eclipse.emf.henshin.statespace.util;

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

	// Whether object identities should be used:
	private boolean useObjectIdentities;

	// Whether attributes should be used:
	private boolean useObjectAttributes;

	// Cached models to be compared.
	private Model model1, model2;

	/**
	 * Default constructor.
	 * 
	 * @param useObjectIdentities Whether object identities should be used.
	 * @param useObjectAttributes Whether attributes should be used.
	 */
	public EcoreEqualityHelper(boolean useObjectIdentities,
			boolean useObjectAttributes) {
		this.useObjectIdentities = useObjectIdentities;
		this.useObjectAttributes = useObjectAttributes;
	}

	/**
	 * Check Ecore equality for two models.
	 * 
	 * @param model1 Model 1.
	 * @param model2 Model 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Model model1, Model model2) {

		// Cache the models:
		this.model1 = model1;
		this.model2 = model2;

		// Check equality:
		boolean result = equals(model1.getResource().getContents(), model2
				.getResource().getContents());

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
		return (!useObjectIdentities || model1.getObjectIdentitiesMap().get(o1)==model2.getObjectIdentitiesMap().get(o2)) 
				&& super.equals(o1, o2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#haveEqualAttribute(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
	 */
	@Override
	protected boolean haveEqualAttribute(EObject o1, EObject o2, EAttribute attribute) {
		return (!useObjectAttributes || super.haveEqualAttribute(o1, o2, attribute));
	}

}
