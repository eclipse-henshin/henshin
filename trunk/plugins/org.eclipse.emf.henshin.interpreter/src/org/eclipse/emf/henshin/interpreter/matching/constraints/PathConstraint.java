package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Path constraint class.
 * @author Christian Krause
 */
public class PathConstraint implements BinaryConstraint {
	
	// Target variable:
	final Variable targetVariable;
	
	// References list representing a path:
	final List<EReference> references;
	
	/**
	 * Default constructor.
	 * @param target Target variable.
	 * @param references List of reference used for the path.
	 */
	public PathConstraint(Variable target, List<EReference> references) {
		this.targetVariable = target;
		this.references = references;
		if (references.isEmpty()) {
			throw new IllegalArgumentException("Cannot create path constraint for empty paths");
		}
	}

	/*
	 * Get the targets for a list o sources and a reference.
	 */
	@SuppressWarnings("unchecked")
	private static Set<EObject> getTargetObjects(Set<EObject> sources, EReference reference) {
		Set<EObject> targets = new LinkedHashSet<EObject>();
		for (EObject source : sources) {
			if (source.eClass().getEAllReferences().contains(reference)) {			
				if (reference.isMany()) {
					targets.addAll((List<EObject>) source.eGet(reference));
				} else {
					EObject obj = (EObject) source.eGet(reference);
					if (obj!=null) {
						targets.add(obj);
					}
				}
			}
		}
		return targets;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot, org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot source, DomainSlot target) {

		// Source variable must be locked:
		if (!source.locked) {
			return false;
		}

		// Follow all paths and get the target objects:
		Set<EObject> targetObjects = Collections.singleton(source.value);
		for (EReference reference : references) {
			targetObjects = getTargetObjects(targetObjects, reference);
		}
		
		// Target domain slot locked?
		if (target.locked) {
			return targetObjects.contains(target.value);
			
		} else {
			
			// Target not locked yet. Create a domain change to restrict the target domain:
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			
			// Calculated temporary domain:
			target.temporaryDomain = new ArrayList<EObject>(targetObjects);
			
			// TODO: why does domain restriction not work for path constraints?
			//if (change.originalValues!=null) {
			//	target.temporaryDomain.retainAll(change.originalValues);
			//}
			
			// Temporary domain must not be empty:
			return !target.temporaryDomain.isEmpty();
			
		}

	}
	
}