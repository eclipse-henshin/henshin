package org.eclipse.emf.henshin.model;

import java.util.List;

import org.eclipse.emf.henshin.model.impl.HenshinRegistryImpl;

/**
 * Henshin registry for storing and accessing {@link TransformationSystem}s.
 */
public interface HenshinRegistry {
	
	/**
	 * Default static instance.
	 */
	final static HenshinRegistry INSTANCE = new HenshinRegistryImpl();

	/**
	 * Get the registered {@link TransformationSystem}s. Can be modified to
	 * register or unregister {@link TransformationSystem}s.
	 */
	List<TransformationSystem> getTransformationSystems();
	
	/**
	 * Get a {@link TransformationSystem} by its name.
	 * @param name Name of the {@link TransformationSystem}.
	 * @return The {@link TransformationSystem} or <code>null</code>.
	 */
	TransformationSystem getTransformationSystemByName(String name);
	
}
