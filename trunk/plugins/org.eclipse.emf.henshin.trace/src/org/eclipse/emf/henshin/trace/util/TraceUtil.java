/**
 * <copyright>
 * Copyright (c) 2010-2013 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.trace.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.trace.Trace;

/**
 * Utility methods for the Henshin trace model.
 * @author Christian Krause
 */
public class TraceUtil {

	@SuppressWarnings("unchecked")
	public static <S extends EObject,T extends EObject> T getTarget(S source, Collection<T> objects) {
		for (T object : objects) {
			if (object instanceof Trace) {
				Trace trace = (Trace) object;
				if (trace.getSource().contains(source)) {
					if (trace.getTarget().size()!=1) {
						throw new RuntimeException("Unexpected number of targets: " + trace.getTarget().size() + " (expected 1)");
					}
					return (T) trace.getTarget().get(0);
				}
			}
		}
		return null;
	}

	public static <S extends EObject,T extends EObject> List<T> getTargets(List<S> sources, Collection<T> objects) {
		List<T> targets = new ArrayList<T>();
		for (S source : sources) {
			T targetObj = getTarget(source, objects);
			if (targetObj!=null) {
				targets.add(targetObj);
			}
		}
		return targets;
	}

}
