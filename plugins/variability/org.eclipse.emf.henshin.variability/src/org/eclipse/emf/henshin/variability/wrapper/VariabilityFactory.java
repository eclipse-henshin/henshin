package org.eclipse.emf.henshin.variability.wrapper;

/**
 * The factory for the variability-aware graph elements.
 * It provides methods for creating different variability graph elements.
 * 
 * @author Stefan Schulz
 * 
 */
public class VariabilityFactory extends AbstractVariabilityFactory {

	public final static VariabilityFactory INSTANCE = new VariabilityFactory();
	
	private VariabilityFactory() {
		super(false);
	}
}