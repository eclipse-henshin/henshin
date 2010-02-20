package org.eclipse.emf.henshin.internal.constraints;

import java.util.ArrayList;
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
				for (int i = 0; i < sourceDomain.size(); i++) {
					EObject eObject = sourceDomain.get(i);
					
					if (eObject != null  && !type.isSuperTypeOf(eObject.eClass()))
						sourceDomain.remove(i);
				}
		}

		return sourceDomain;
	}
}
