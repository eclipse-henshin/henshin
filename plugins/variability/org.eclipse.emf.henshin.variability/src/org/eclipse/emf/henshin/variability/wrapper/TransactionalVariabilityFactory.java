package org.eclipse.emf.henshin.variability.wrapper;

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