package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The factory for the variability-aware graph elements.
 * It provides methods for creating different variability graph elements.
 * 
 * @author Stefan Schulz
 * 
 */
public class TransactionalVariabilityFactory extends AbstractVariabilityFactory {
	
	public final static TransactionalVariabilityFactory INSTANCE = new TransactionalVariabilityFactory();
	
	private TransactionalVariabilityFactory() {
		super(true);
	}
}