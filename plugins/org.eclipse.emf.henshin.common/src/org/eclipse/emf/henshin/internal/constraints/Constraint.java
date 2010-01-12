package org.eclipse.emf.henshin.internal.constraints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.matching.Variable;

/**
 * This class represents a dependence between two variables. The eval() Method
 * will ensure that the target domain is restricted to those objects that
 * fulfill this constrain.
 */
public abstract class Constraint {

	protected Variable ownerVariable;
	protected Variable targetVariable;

	protected boolean targetEnabled;
	protected boolean evaluated;

	// old domain values of target variable
	protected List<EObject> removedObjects;

	/**
	 * Creates a new contraint between two variables.
	 * 
	 * @param owner
	 *            The variable who owns the constraint.
	 * @param target
	 *            The dependent variable.
	 */
	protected Constraint(Variable owner, Variable target) {
		this.ownerVariable = owner;
		this.targetVariable = target;

		targetEnabled = false;

		this.removedObjects = new ArrayList<EObject>();
	}

	/**
	 * Evaluates the constraint. It will restrict the target domain to those
	 * objects that can satisfy the constraint. If no objects satisfy the
	 * constraint, it returns false.
	 * 
	 * @return true, if the constraint evaluated successfully.
	 */
	public boolean eval() {
		evaluated = true;

		return false;
	}

	/**
	 * Undoes the constraint restrictions.
	 */
	public void undo() {
		if (evaluated) {
			if (!targetEnabled) {
				if (removedObjects != null
						&& targetVariable.getDomain() != null)
					targetVariable.getDomain().addAll(removedObjects);

				removedObjects.clear();
			} else {
				targetVariable.disable();
			}
		}

		evaluated = false;
	}

	protected boolean retainObjects(List<EObject> preservedObjects) {
		List<EObject> targetDomain = targetVariable.getDomain();

		removedObjects = new ArrayList<EObject>(targetDomain);
		targetDomain.retainAll(preservedObjects);
		removedObjects.removeAll(preservedObjects);

		return !targetDomain.isEmpty();
	}

	protected boolean retainObjects(EObject preservedValue) {
		List<EObject> targetDomain = targetVariable.getDomain();
		removedObjects = new ArrayList<EObject>(targetDomain);
		removedObjects.remove(preservedValue);

		if (targetDomain.contains(preservedValue)) {
			targetDomain.clear();
			targetDomain.add(preservedValue);
		} else {
			targetDomain.clear();
		}

		return !targetDomain.isEmpty();
	}

	protected boolean removeObjects(List<EObject> removedObjects) {
		List<EObject> targetDomain = targetVariable.getDomain();

		this.removedObjects = new ArrayList<EObject>(removedObjects);
		targetDomain.removeAll(removedObjects);

		return !targetDomain.isEmpty();
	}

	protected boolean removeObject(EObject removedObject) {
		List<EObject> targetDomain = targetVariable.getDomain();

		removedObjects = new ArrayList<EObject>();
		removedObjects.add(removedObject);
		targetDomain.remove(removedObject);

		return !targetDomain.isEmpty();
	}

	protected boolean checkDomainForObjects(List<EObject> objects) {
		return objects.containsAll(targetVariable.getDomain());
	}

	protected boolean checkDomainForObject(EObject objekt) {
		return targetVariable.getDomain().contains(objekt);
	}

	protected boolean removeObjectsWithoutProperty(EAttribute attribute,
			Object value) {
		List<EObject> targetDomain = targetVariable.getDomain();

		removedObjects = new ArrayList<EObject>();

		if (value == null) {
			for (EObject eObject : targetDomain) {
				if (eObject.eGet(attribute) != null)
					removedObjects.add(eObject);
			}
		} else {
			for (EObject eObject : targetDomain) {
				if (!value.equals(eObject.eGet(attribute)))
					removedObjects.add(eObject);
			}
		}

		targetDomain.removeAll(removedObjects);

		return !targetDomain.isEmpty();
	}

	public void reset() {
		removedObjects.clear();
	}

	/**
	 * @return the creator
	 */
	public Variable getCreator() {
		return ownerVariable;
	}

	/**
	 * @return the target
	 */
	public Variable getTarget() {
		return targetVariable;
	}
}