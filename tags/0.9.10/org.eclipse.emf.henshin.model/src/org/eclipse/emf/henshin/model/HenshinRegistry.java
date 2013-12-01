/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model;

import java.util.List;

import org.eclipse.emf.henshin.model.impl.HenshinRegistryImpl;

/**
 * Henshin registry for storing and accessing {@link TransformationSystem}s.
 * @deprecated
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
