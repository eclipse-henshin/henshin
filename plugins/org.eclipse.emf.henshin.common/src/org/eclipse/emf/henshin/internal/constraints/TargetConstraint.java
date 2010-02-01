package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.internal.matching.Variable;

public class TargetConstraint {

	private EReference reference;
	private Variable source;

	public TargetConstraint(Variable target, EReference eReference) {
		this.source = target;
		this.reference = eReference;
	}

	@SuppressWarnings("unchecked")
	public boolean check(EObject sourceValue, EObject targetValue) {
		if (sourceValue == null)
			return false;

		if (reference.isMany()) {
			List<EObject> referedObjects = (List<EObject>) sourceValue
					.eGet(reference);//TODO: check for null
			return (referedObjects.contains(targetValue));
		} else {
			return sourceValue.eGet(reference) == targetValue;
		}
	}

	public Variable getSource() {
		return source;
	}
}
