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

		if (sourceValue.eGet(reference) != null) {
			if (reference.isMany()) {
				referredObjects = (List<EObject>) sourceValue.eGet(reference);
			} else {
				referredObjects = new ArrayList<EObject>();
				referredObjects.add((EObject) sourceValue.eGet(reference));
			}
		}

		if (referredObjects != null) {
			if (targetDomain != null) {
				List<EObject> removedObjects = new ArrayList<EObject>(
						targetDomain);
				targetDomain.retainAll(referredObjects);
				removedObjects.removeAll(targetDomain);

				return new DomainChange(targetDomain, removedObjects);
			} else {
				targetDomain = new ArrayList<EObject>(referredObjects);
				return new DomainChange(targetDomain, null);
			}
		}
		
		return new DomainChange(null, null);
	}

	public Variable getTarget() {
		return target;
	}
}