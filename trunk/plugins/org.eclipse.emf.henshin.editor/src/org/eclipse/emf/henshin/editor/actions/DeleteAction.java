/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.actions;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Prevents invalidation of {@link AmalgamationUnit}s. If the current selection
 * contains elements in multiRules that are mapped from the corresponding
 * kernelRule the DeleteAction is disabled.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class DeleteAction extends org.eclipse.emf.edit.ui.action.DeleteAction {
	
	public DeleteAction(boolean deleteAllReferences) {
		super(deleteAllReferences);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.ui.action.CommandActionHandler#updateSelection(org
	 * .eclipse.jface.viewers.IStructuredSelection)
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		if (selectionContainsMappedKernel(selection))
			return false;
		else
			return super.updateSelection(selection);
	}
	
	/**
	 * Basic check.
	 * 
	 * @param selection
	 * @return
	 */
	protected boolean selectionContainsMappedKernel(IStructuredSelection selection) {
		Object object;
		@SuppressWarnings("unchecked")
		Iterator<Object> i = selection.iterator();
		//
		//
		selLoop: while (i.hasNext()) {
			object = i.next();
			if (object instanceof DelegatingWrapperItemProvider) {
				// check whether object is owned by an AmalgamationUnit
				//
				DelegatingWrapperItemProvider wrapper = (DelegatingWrapperItemProvider) object;
				AmalgamationUnit aUnit = getMultiWrappingAmalgamationsUnit(wrapper);
				
				// current object is not part of multiRules
				//
				if (aUnit == null) continue selLoop;
				
				Object value = wrapper.getValue();
				
				if (value instanceof Node) {
					if (isMappedNodeFromKernel(aUnit, (Node) value)) return true;
				} else if (value instanceof Edge) {
					Edge edge = (Edge) value;
					if (edge.getType() == null) continue selLoop;
					// both ends of edge must be mapped from kernel
					// and there has to be an edge of same type between those
					// nodes in kernelRule
					//
					if (QuantUtil.noneNull(edge.getSource(), edge.getTarget())) {
						if (isMappedNodeFromKernel(aUnit, edge.getSource())
								&& isMappedNodeFromKernel(aUnit, edge.getTarget())) {
							Mapping sourceMapping = getMappingFromKernel(aUnit, edge.getSource());
							Mapping targetMapping = getMappingFromKernel(aUnit, edge.getTarget());
							for (Edge kernelEdge : sourceMapping.getOrigin().getOutgoing()) {
								if (edge.getType() == kernelEdge.getType()
										&& kernelEdge.getTarget() == targetMapping.getOrigin())
									return true;
							}
						}
					}
					
				} else
				// both ends of mapping must be mapped from kernel and there
				// has to be a mapping between those nodes in kernelRule
				//
				if (value instanceof Mapping) {
					Mapping mapping = (Mapping) value;
					if (isMappedNodeFromKernel(aUnit, mapping.getOrigin())
							&& isMappedNodeFromKernel(aUnit, mapping.getImage())
							&& isMappedIn(getMappingFromKernel(aUnit, mapping.getOrigin())
									.getOrigin(), getMappingFromKernel(aUnit, mapping.getImage())
									.getOrigin(), aUnit.getKernelRule().getMappings())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Retrieve the AmalgamationUnit whose multiRules contain the given object.
	 * 
	 * @param wrapper
	 * @return the wrapping {@link AmalgamationUnit} or null if none is found
	 */
	protected AmalgamationUnit getMultiWrappingAmalgamationsUnit(
			DelegatingWrapperItemProvider wrapper) {
		while (wrapper != null) {
			
			if (wrapper.getOwner() instanceof AmalgamationUnit) {
				if (wrapper.getFeature() == HenshinPackage.eINSTANCE
						.getAmalgamationUnit_MultiRules())
					return (AmalgamationUnit) wrapper.getOwner();
				return null;
			}

			else if (wrapper.getOwner() instanceof DelegatingWrapperItemProvider)
				wrapper = (DelegatingWrapperItemProvider) wrapper.getOwner();
			else
				return null;
		}
		return null;
	}
	
	/**
	 * Check if there is a mapping between the given nodes in the given
	 * collection.
	 * 
	 * @param origin
	 * @param image
	 * @param mappings
	 * @return
	 */
	protected boolean isMappedIn(Node origin, Node image, Collection<Mapping> mappings) {
		for (Mapping m : mappings)
			if (m.getOrigin() == origin && m.getImage() == image) return true;
		return false;
	}
	
	/**
	 * Check if the given {@link AmalgamationUnit} contains a mapping from the
	 * kernelRule to the given Node.
	 * 
	 * @param aUnit
	 * @param node
	 * @return
	 */
	protected boolean isMappedNodeFromKernel(AmalgamationUnit aUnit, Node node) {
		return getMappingFromKernel(aUnit, node) != null;
	}
	
	/**
	 * Returns the {@link Mapping} from the kernelRule to the given {@link Node}
	 * in the given {@link AmalgamationUnit} if exists,otherwise null.
	 * 
	 * @param aUnit
	 * @param node
	 * @return
	 */
	protected Mapping getMappingFromKernel(AmalgamationUnit aUnit, Node node) {
		Collection<Mapping> mappings;
		
		if (node == null || node.getGraph() == null) return null;
		if (HenshinRuleAnalysisUtil.isLHS(node.getGraph()))
			mappings = aUnit.getLhsMappings();
		else if (HenshinRuleAnalysisUtil.isRHS(node.getGraph()))
			mappings = aUnit.getRhsMappings();
		else
			return null;
		for (Mapping m : mappings)
			if (m.getImage() == node) return m;
		return null;
	}
	
}
