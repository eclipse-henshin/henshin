/**
 * 
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.Comparator;

import org.eclipse.emf.ecore.EClassifier;

/**
 * A comparator for EClassifier collections.
 * 
 * @author sjtuner
 *
 */
public final class EClassComparator implements Comparator<EClassifier> {

	@Override
	public int compare(EClassifier c1, EClassifier c2) {
		String n1 = c1.getName()!=null ? c1.getName() : "";
		String n2 = c2.getName()!=null ? c2.getName() : "";
		return n1.compareTo(n2);
	}
	
}
