package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.DomainChange;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;

public class DomainSlot {
	Variable owner;
	boolean locked;
	boolean initialized;

	EObject value;
	List<EObject> domain;
	List<EObject> localChanges;
	Map<DomainSlot, List<EObject>> slotChanges;

	List<Variable> checkedVariables;

	Set<EObject> usedObjects;

	public DomainSlot(Variable owner, Set<EObject> usedObjects) {
		this.owner = owner;
		this.locked = false;
		this.initialized = false;

		if (usedObjects != null)
			this.usedObjects = usedObjects;

		this.slotChanges = new HashMap<DomainSlot, List<EObject>>();
		this.checkedVariables = new ArrayList<Variable>();
	}

	public DomainSlot(EObject value) {
		this.locked = true;
		this.initialized = true;
		this.value = value;
		this.localChanges = null;
		this.checkedVariables = new ArrayList<Variable>();
	}

	public boolean instanciate(Variable variable,
			Map<Variable, DomainSlot> domainMap, EmfGraph graph,
			AttributeConditionHandler conditionHandler) {
		if (!initialized) {
			initialized = true;

			localChanges = (domain != null) ? new ArrayList<EObject>(domain)
					: null;

			domain = variable.getTypeConstraint().reduceDomain(domain, graph);
			domain.remove(usedObjects);
			if (domain.size() == 0)
				return false;

			for (AttributeConstraint constraint : variable
					.getAttributeConstraints()) {
				constraint.reduceDomain(domain);
				if (domain.size() == 0)
					return false;
			}
		}

		if (!locked) {
			checkedVariables.add(variable);

			if (domain.size() == 0) {
				return false;
			} else {
				value = domain.get(domain.size() - 1);
				domain.remove(value);
				usedObjects.add(value);

				locked = true;
			}

			for (ParameterConstraint constraint : variable
					.getParameterConstraints()) {
				if (!constraint.check(value, conditionHandler))
					return false;
			}

			for (ReferenceConstraint constraint : variable
					.getReferenceConstraints()) {
				DomainSlot target = domainMap.get(constraint.getTarget());
				if (!applyReferenceConstraint(constraint, target)) {
					return false;
				}
			}
		} else {
			if (!checkedVariables.contains(variable)) {
				checkedVariables.add(variable);

				if (!variable.getTypeConstraint().check(value))
					return false;

				for (AttributeConstraint constraint : variable
						.getAttributeConstraints()) {
					if (!constraint.check(value))
						return false;
				}

				for (ParameterConstraint constraint : variable
						.getParameterConstraints()) {
					if (!constraint.check(value, conditionHandler))
						return false;
				}

				for (ReferenceConstraint constraint : variable
						.getReferenceConstraints()) {
					DomainSlot target = domainMap.get(constraint.getTarget());
					if (!applyReferenceConstraint(constraint, target)) {
						return false;
					}
				}
			}
		}

		return true;
	}


	private boolean applyReferenceConstraint(ReferenceConstraint constraint,
			DomainSlot target) {
		if (target.locked) {
			return constraint.check(value, target.value);
		} else {
			DomainChange change = constraint.reduceTargetDomain(value,
					target.domain);
			target.domain = change.remainingObjects;

			// if (change.removedObjects != null)
			slotChanges.put(target, change.removedObjects);

			return target.domain != null && target.domain.size() > 0;
		}
	}

	public boolean unlock(Variable sender) {
		if (locked && sender == owner) {
			locked = false;
			usedObjects.remove(value);
			for (DomainSlot slot : slotChanges.keySet()) {
				List<EObject> removedObjects = slotChanges.get(slot);

				if (removedObjects != null) {
					slot.domain.addAll(removedObjects);
				} else
					slot.domain = null;
			}
			slotChanges.clear();
			checkedVariables.clear();

			return !(domain == null || domain.size() == 0);
		}

		return false;
	}

	public void clear(Variable sender) {
		if (sender == owner) {
			locked = false;

			value = null;
			domain = localChanges;
			localChanges = null;
			initialized = false;

			slotChanges = new HashMap<DomainSlot, List<EObject>>();
		}
	}

	public boolean instanciationPossible() {
		if (domain == null)
			return false;

		if (!locked)
			return domain.size() > 0;

		return false;
	}
}
