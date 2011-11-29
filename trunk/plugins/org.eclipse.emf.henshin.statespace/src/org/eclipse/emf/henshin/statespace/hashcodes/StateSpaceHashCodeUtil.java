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
package org.eclipse.emf.henshin.statespace.hashcodes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Static methods for managing hash codes for state models.
 * 
 * @author Christian Krause
 */
public class StateSpaceHashCodeUtil {
	
	/**
	 * Compute the hash code of a model.
	 * @param model The model.
	 * @param useGraphEquality Whether to use graph equality.
	 * @param useObjectKeys Whether to use object keys.
	 * @param useObjectAttributes Whether to use object attributes.
	 * @return The model's hash code.
	 */
	public static int computeHashCode(Model model,
			boolean useGraphEquality, 
			boolean useObjectKeys, 
			boolean useObjectAttributes) {

		// Do some basic checks first:
		if (model==null) {
			throw new NullPointerException("Cannot compute hashcode for null model");
		}
		if (model.getResource()==null) {
			throw new NullPointerException("Cannot compute hashcode for without model resource");
		}
		
		// Compute the total hash code of the model:
		TotalHashCodeHelper helper = new TotalHashCodeHelper(
				useGraphEquality, useObjectKeys, useObjectAttributes);
		
		return helper.hashCode(model);
		
	}
	
	/**
	 * Calculate the average number of states which have the same hash code.
	 * @param stateSpace State space to be analyzed.
	 * @return Number of states per hash code.
	 */
	public static double getHashCodeCollisions(StateSpace stateSpace) {
		
		// Count the number of states per hash code:
		Map<Integer,Integer> numStatesPerHash = new HashMap<Integer,Integer>();
		for (State state : stateSpace.getStates()) {
			int hashcode = state.getHashCode();
			Integer numStates = numStatesPerHash.get(hashcode);
			if (numStates==null) {
				numStatesPerHash.put(hashcode, 1);
			} else {
				numStatesPerHash.put(hashcode, numStates+1);
			}
		}
		
		// Calculate the average:
		int sum = 0;
		for (Integer count : numStatesPerHash.values()) {
			sum += count;
		}
		double average = (double) sum / numStatesPerHash.values().size();
		
		// Done.
		return average;
		
	}

}
