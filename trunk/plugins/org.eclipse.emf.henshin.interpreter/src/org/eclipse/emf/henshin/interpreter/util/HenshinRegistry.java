package org.eclipse.emf.henshin.interpreter.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.TransformationSystem;

public class HenshinRegistry {
	public static HenshinRegistry instance = new HenshinRegistry();

	private Set<TransformationSystem> transformationSystems = new LinkedHashSet<TransformationSystem>();

	public void registerTransformationSystem(TransformationSystem ts) {
		transformationSystems.add(ts);
	}

	/**
	 * @return the transformationSystems
	 */
	public Set<TransformationSystem> getTransformationSystems() {
		return transformationSystems;
	}
}
