package org.eclipse.emf.henshin.variability.mergein.normalize;

import org.eclipse.emf.ecore.impl.EReferenceImpl;

public class AttributeEReference extends EReferenceImpl {
	public static EReferenceImpl instance = new AttributeEReference();
	
	@Override
	public String getName() {
		return "AttributeEdge";
	}
}
