package org.eclipse.emf.henshin.internal.constraints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.internal.matching.Variable;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class ReferenceConstraint {
	private EReference reference;
	private Variable target;

	public ReferenceConstraint(Variable target, EReference eReference) {
		this.target = target;
		this.reference = eReference;
	}

	@SuppressWarnings("unchecked")
	public boolean check(EObject sourceValue, EObject targetValue) {
		if (sourceValue == null)
			return false;

		if (reference.isMany()) {
			List<EObject> referedObjects = (List<EObject>) sourceValue
					.eGet(reference);
			return (referedObjects.contains(targetValue));
		} else {
			return sourceValue.eGet(reference) == targetValue;
		}
	}

	@SuppressWarnings("unchecked")
	public DomainChange reduceTargetDomain(EObject sourceValue,
			List<EObject> targetDomain) {
		List<EObject> referredObjects = null;

		if (sourceValue == null)
			return new DomainChange(targetDomain, null);

		if (reference.isMany()) {
			referredObjects = (List<EObject>) sourceValue.eGet(reference);
		} else {
			EObject referredObject = (EObject) sourceValue.eGet(reference);
			if (referredObject != null) {
				referredObjects = new ArrayList<EObject>();
				referredObjects.add((EObject) sourceValue.eGet(reference));
			}
		}

		if (referredObjects != null) {
			if (targetDomain == null) {
				targetDomain = new ArrayList<EObject>(referredObjects);
				return new DomainChange(targetDomain, null);
			} else {
				List<EObject> removedObjects = new ArrayList<EObject>(
						targetDomain);

				targetDomain.retainAll(referredObjects);
				removedObjects.removeAll(targetDomain);

				return new DomainChange(targetDomain, removedObjects);
			}
		} else 
			return new DomainChange(null, null);
	}

	public Variable getTarget() {
		return target;
	}
}