package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint {
	EAttribute attribute;
	Object attributeValue;

	public AttributeConstraint(EAttribute attribute, Object value) {
		this.attribute = attribute;
		this.attributeValue = value;
	}

	public boolean check(EObject sourceValue) {
		return sourceValue.eGet(attribute) == attributeValue;
	}

	public void reduceDomain(List<EObject> sourceDomain) {
		for (int i = 0; i < sourceDomain.size(); i++) {
			EObject domainObject = sourceDomain.get(i);
			
			if (!attributeValue.equals(domainObject.eGet(attribute)))
				sourceDomain.remove(i);
		}
	}
}
