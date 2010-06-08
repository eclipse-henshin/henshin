/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.henshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.constraints.DomainChange;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class HenshinReferenceConstraint implements ReferenceConstraint<Node> {
	private Edge edge;
	private Variable<EClass, Node> target;

	public HenshinReferenceConstraint(Variable<EClass, Node> target, Edge edge) {
		this.target = target;
		this.edge = edge;
	}

	public boolean check(Node sourceValue, Node targetValue) {
		if (edge.getType().isMany()) {
			List<Edge> referedObjects = sourceValue
					.findOutgoingEdgesByType(edge.getType());

			return (referedObjects.contains(targetValue));
		} else {
			return (sourceValue.findOutgoingEdgeByType(targetValue, edge
					.getType()) != null);
		}
	}

	public DomainChange<Node> reduceTargetDomain(Node sourceValue,
			List<Node> targetDomain) {
		List<Node> referredObjects = new ArrayList<Node>();

		for (Edge outgoingEdge : sourceValue.findOutgoingEdgesByType(edge
				.getType()))
			referredObjects.add(outgoingEdge.getTarget());

		if (targetDomain != null) {
			List<Node> removedObjects = new ArrayList<Node>(targetDomain);
			targetDomain.retainAll(referredObjects);
			removedObjects.removeAll(targetDomain);

			return new DomainChange<Node>(targetDomain, removedObjects);
		} else {
			targetDomain = new ArrayList<Node>(referredObjects);
			return new DomainChange<Node>(targetDomain, null);
		}
	}

	public Variable<EClass, Node> getTarget() {
		return target;
	}
}