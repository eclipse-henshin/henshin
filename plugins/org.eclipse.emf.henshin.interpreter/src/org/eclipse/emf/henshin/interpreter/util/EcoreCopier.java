package org.eclipse.emf.henshin.interpreter.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * A copier that correctly handles copying of types and generic types in
 * {@link ETypedElement} instances.
 *
 * @author Steffen Zschaler
 */
@SuppressWarnings("serial")
public class EcoreCopier extends Copier {

	public EcoreCopier() {
		super();
	}

	public EcoreCopier(boolean resolveProxies) {
		super(resolveProxies);
	}

	public EcoreCopier(boolean resolveProxies, boolean useOriginalReferences) {
		super(resolveProxies, useOriginalReferences);
	}

	/**
	 * Does everything the super method does, but also makes sure implicitly created
	 * types are stored in the copy map.
	 *
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.Copier#copyContainment(org.eclipse.emf.ecore.EReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
		super.copyContainment(eReference, eObject, copyEObject);

		if (eObject.eIsSet(eReference)) {
			if (eReference == EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE) {
				// If this object has an actual generic type, setting it in the copy will have
				// created a copy of the `eType`. We need to make sure that copy shows up in our
				// map
				// TODO: Probably needs more testing: we really would want to make sure this is
				// wired up to the type we may already have copied, need to make sure this is
				// happening...
				if (!containsKey((EObject) eObject.eGet(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE, resolveProxies))) {
					put((EObject) eObject.eGet(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE, resolveProxies),
							(EObject) copyEObject.eGet(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE, resolveProxies));
				}
			} else if (eReference == EcorePackage.Literals.ECLASS__EGENERIC_SUPER_TYPES) {
				for (int i = 0, size = ((EClass) eObject).getESuperTypes().size(); i < size; i++) {
					EClass superType = ((EClass) eObject).getESuperTypes().get(i);
					if (!containsKey(superType)) {
						put(superType, ((EClass) copyEObject).getESuperTypes().get(i));
					}
				}
			}
		}
	}

	/**
	 * Used to store copies created when copying references
	 */
	private Map<EObject, EObject> lateCopies;

	/**
	 * Does everything the super method does, but treats eType and eGenericType
	 * specially.
	 *
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.Copier#copyReference(org.eclipse.emf.ecore.EReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
		super.copyReference(eReference, eObject, copyEObject);

		if (eObject.eIsSet(eReference)) {
			if (eReference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE) {
				// In this case, this will have implicitly copied the generic type, so need to
				// add it to the map
				lateCopies.put(
						(EObject) eObject.eGet(EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE, resolveProxies),
						(EObject) copyEObject.eGet(EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE,
								resolveProxies));
			}
			else if (eReference == EcorePackage.Literals.ECLASS__ESUPER_TYPES) {
				for (int i = 0, size = ((EClass)eObject).getEGenericSuperTypes().size(); i < size; i++) {
					EGenericType eSuperGeneric = ((EClass)eObject).getEGenericSuperTypes().get(i);
					lateCopies.put (eSuperGeneric, ((EClass)copyEObject).getEGenericSuperTypes().get(i));
				}
			}
		}
	}

	/*
	 * Need to make sure that we don't change the copy map while
	 * super.copyReferences is iterating over it
	 *
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.Copier#copyReferences()
	 */
	@Override
	public void copyReferences() {
		lateCopies = new LinkedHashMap<>();

		super.copyReferences();

		putAll(lateCopies);
		lateCopies = null;
	}
}