/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;

/**
 * @generated
 */
public class RuleEditHelper extends HenshinBaseEditHelper {
	
	/**
	 * Check whether a rule is a trivial multi-rule. A multi-rule
	 * is trivial if all elements in its LHS/RHS have a pre-image
	 * in the kernel rule's LHS/RHS.
	 * @param multi Rule to be checked.
	 * @return <code>true</code> if it is trivial.
	 */
	public static boolean isTrivialMultiRule(Rule multi) {
		
		// Create a list of all elements in the multi-rule:
		List<GraphElement> elements = new ArrayList<GraphElement>();
		elements.addAll(multi.getLhs().getNodes());
		elements.addAll(multi.getLhs().getEdges());
		elements.addAll(multi.getRhs().getNodes());
		elements.addAll(multi.getRhs().getEdges());
		
		// Check if there have origins.
		for (GraphElement element : elements) {
			if (multi.getOriginInKernelRule(element)==null) {
				return false;
			}
		}
		
		// No reason why it shouldn't be trivial.
		return true;
	}

	public static void removeTrivialMultiRules(Rule kernel) {

		// Delete trivial multi-rules:
		for (int i=0; i<kernel.getMultiRules().size(); i++) {
			Rule multi = kernel.getMultiRules().get(i);
			if (isTrivialMultiRule(multi)) {
				kernel.getMultiRules().remove(i--);
			}
		}		
	}

}
