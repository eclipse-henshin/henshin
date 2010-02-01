package org.eclipse.emf.henshin.internal.constraints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class TypeConstraint {
	EClass type;

	public TypeConstraint(EClass type) {
		this.type = type;
	}

	public boolean check(EObject sourceValue) {
		return type.isSuperTypeOf(sourceValue.eClass());
	}

	public List<EObject> reduceDomain(List<EObject> sourceDomain, EmfGraph graph) {
		if (sourceDomain == null) {
			return new ArrayList<EObject>(graph.getDomainForType(type));
		} else {
			if (sourceDomain != null && sourceDomain.size() > 0)
				for (Iterator<EObject> iterator = sourceDomain.iterator(); iterator
						.hasNext();) {
					EObject eObject = iterator.next();
					if (eObject != null  && !type.isSuperTypeOf(eObject.eClass()))
						sourceDomain.remove(eObject);
				}
		}

		return sourceDomain;
	}
}
