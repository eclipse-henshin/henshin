package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.internal.matching.Variable;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint extends Constraint {
	
	EAttribute attribute;
	Object value;
	
	public AttributeConstraint(Variable creator, EAttribute attribute, Object value) {
		super(creator, creator);
		
		this.attribute = attribute;
		this.value = value;
	}

	public boolean eval() {
		evaluated = true;
		return removeObjectsWithoutProperty(attribute, value);
	}

}
