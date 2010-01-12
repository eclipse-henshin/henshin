package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.matching.Variable;

public class InstanciationConstraint extends Constraint {
	private boolean isNewObject;

	public InstanciationConstraint(Variable creator, Variable target) {
		super(creator, target);
	}

	public boolean eval() {
		EObject ownerValue = ownerVariable.getInstanceValue();
		evaluated = true;

		if (ownerVariable == targetVariable) {
			if (!ownerVariable.getType().isSuperTypeOf(ownerValue.eClass()))
				return false;

			isNewObject = !ownerVariable.getEmfGraph().usedObjects.contains(ownerValue);
			if (isNewObject)
				ownerVariable.getEmfGraph().usedObjects.add(ownerValue);
			else {
				if (!ownerVariable.isDerived()) {
					return false;
				}
			}

			return retainObjects((EObject) ownerValue);
		} else {
			targetEnabled = true;
			targetVariable.enable(ownerValue);
			targetVariable.setDerived(true);
			return true;
		}
	}

	@Override
	public void undo() {
		if (ownerVariable == targetVariable) {
			if (isNewObject)
				ownerVariable.getEmfGraph().usedObjects.remove(ownerVariable.getInstanceValue());
			super.undo();
		} else {
			targetVariable.disable();
		}
	}
}
