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
import org.eclipse.emf.henshin.internal.constraints.TargetConstraint;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;

public class DomainSlot {
	Variable owner;
	boolean locked;
	boolean initialized;

	EObject value;
	List<EObject> domain;
	List<EObject> localChanges;
	Map<DomainSlot, List<EObject>> slotChanges;

	Set<EObject> usedObjects;

	public DomainSlot(Variable owner, Set<EObject> usedObjects) {
		this.owner = owner;
		this.locked = false;
		this.initialized = false;

		if (usedObjects != null)
			this.usedObjects = usedObjects;

		this.slotChanges = new HashMap<DomainSlot, List<EObject>>();
	}

	public DomainSlot(EObject value) {
		this.locked = true;
		this.value = value;
		this.localChanges = null;
	}

	public boolean instanciate(Variable variable,
			Map<Variable, DomainSlot> domainMap, EmfGraph graph,
			AttributeConditionHandler conditionHandler) {
		if (!initialized) {
			if (domain != null)
				localChanges = new ArrayList<EObject>(domain);
			else
				localChanges = null;
			
			initialized = true;
			
			if (!applyTypeConstraint(variable.getTypeConstraint(), graph))
				return false;

			for (AttributeConstraint constraint : variable
					.getAttributeConstraints()) {
				if (!applyAttributeConstraint(constraint))
					return false;
			}
		}
		
		if (!locked) {
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
				if (!applyParameterConstraint(constraint, conditionHandler))
					return false;
			}

//			for (TargetConstraint constraint : variable.getTargetConstraints()) {
//				if (!applyTargetConstraint(constraint, domainMap.get(constraint
//						.getSource())))
//					return false;
//			}

			for (ReferenceConstraint constraint : variable
					.getReferenceConstraints()) {
				if (!applyReferenceConstraint(constraint, domainMap.get(constraint.getTarget())))
					return false;
			}
		}

		return true;
	}

	private boolean applyTypeConstraint(TypeConstraint constraint,
			EmfGraph graph) {
		if (locked) {
			return constraint.check(value);
		} else {
			domain = constraint.reduceDomain(domain, graph);
			domain.remove(usedObjects);
			return domain.size() > 0;
		}
	}

	private boolean applyParameterConstraint(ParameterConstraint constraint,
			AttributeConditionHandler conditionHandler) {
		if (locked) {
			return constraint.check(value, conditionHandler);
		}

		return false;
	}

	private boolean applyAttributeConstraint(AttributeConstraint constraint) {
		if (!locked) {
			constraint.reduceDomain(domain);
			return domain.size() > 0;
		} else {
			return constraint.check(value);
		}
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

	private boolean applyTargetConstraint(TargetConstraint constraint,
			DomainSlot source) {
		if (source.locked) {
			return constraint.check(source.value, value);
		}

		return true;
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
