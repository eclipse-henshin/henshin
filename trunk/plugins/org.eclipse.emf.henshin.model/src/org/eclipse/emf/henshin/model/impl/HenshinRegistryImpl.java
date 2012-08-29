/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Default {@link HenshinRegistry} implementation.
 * @deprecated
 */
public class HenshinRegistryImpl implements HenshinRegistry {

	// Registered transformation systems:
	private List<TransformationSystem> systems = new ArrayList<TransformationSystem>() {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean add(TransformationSystem s) {
			if (!contains(s)) {
				add(s);
				return true;
			}
			return false;
		}
	};

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinRegistry#getTransformationSystems()
	 */
	@Override
	public List<TransformationSystem> getTransformationSystems() {
		return systems;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.HenshinRegistry#getTransformationSystemByName(java.lang.String)
	 */
	@Override
	public TransformationSystem getTransformationSystemByName(String name) {
		for (TransformationSystem trafoSystem : systems) {
			if (trafoSystem.getName().equals(name)) {
				return trafoSystem;
			}
		}
		return null;
	}
	
}
