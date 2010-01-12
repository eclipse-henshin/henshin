package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.internal.matching.Variable;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class ReferenceConstraint extends Constraint {
	private EReference eReference;

	public ReferenceConstraint(Variable creator, Variable target,
			EReference eReference) {
		super(creator, target);
		this.eReference = eReference;
	}

	@SuppressWarnings("unchecked")
	public boolean eval() {
		EObject creatorValue = ownerVariable.getInstanceValue();
		evaluated = true;

		if (targetVariable.isEnabled()) {
			if (targetVariable.isInstanciated()) {
				if (eReference.isMany()) {
					return checkDomainForObjects((List<EObject>) creatorValue
							.eGet(eReference));
				} else {
					return checkDomainForObject((EObject) creatorValue
							.eGet(eReference));
				}

			} else {
				if (eReference.isMany()) {
					return retainObjects((List<EObject>) creatorValue
							.eGet(eReference));
				} else {
					return retainObjects((EObject) creatorValue
							.eGet(eReference));
				}
			}
		} else {
			targetEnabled = true;
			if (creatorValue.eGet(eReference) != null) {
				if (eReference.isMany()) {
					targetVariable.enable((List<EObject>) creatorValue
							.eGet(eReference));
					targetVariable.getDomain().removeAll(ownerVariable.getEmfGraph().usedObjects);
				} else {
					targetVariable.enable((EObject) creatorValue
							.eGet(eReference));
					targetVariable.getDomain().removeAll(ownerVariable.getEmfGraph().usedObjects);
				}
				return true;
			}
		}
		return false;
	}
}
