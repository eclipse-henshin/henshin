package org.eclipse.emf.henshin.rulegen.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * Data class representing a matching between two models A and B, i.e., a set of correspondences indicating the common
 * elements in A and B.
 * 
 * @author Timo Kehrer
 */
public class Matching {

	/**
	 * Indexed access to correspondences.
	 */
	private Map<EObject, EObject> correspondencesA2B;
	private Map<EObject, EObject> correspondencesB2A;

	/**
	 * Set of correspondences
	 */
	private List<Correspondence> correspondences;

	/**
	 * Constructor.
	 * 
	 * @param modelA
	 * @param modelB
	 */
	public Matching() {
		super();

		this.correspondencesA2B = new HashMap<EObject, EObject>();
		this.correspondencesB2A = new HashMap<EObject, EObject>();

		this.correspondences = new ArrayList<Correspondence>();
	}

	/**
	 * Add a correspondence between elementA and elementB.
	 * 
	 * @param elementA
	 * @param elementB
	 */
	public void addCorrespondence(EObject elementA, EObject elementB) {
		if (!correspondencesA2B.containsKey(elementA) && !correspondencesB2A.containsKey(elementB)) {
			correspondencesA2B.put(elementA, elementB);
			correspondencesB2A.put(elementB, elementA);

			correspondences.add(new Correspondence(elementA, elementB));
		}
	}

	public List<Correspondence> getCorrespondences() {
		return correspondences;
	}

	public boolean isMatched(EObject obj) {
		return correspondencesA2B.containsKey(obj) || correspondencesB2A.containsKey(obj);
	}

	public EObject getCorresponding(EObject obj) {
		if (correspondencesA2B.containsKey(obj)) {
			return correspondencesA2B.get(obj);
		}
		if (correspondencesB2A.containsKey(obj)) {
			return correspondencesB2A.get(obj);
		}

		return null;
	}

	@Override
	public String toString() {
		String res = super.toString() + "\n";
		for (Iterator<EObject> iterator = correspondencesA2B.keySet().iterator(); iterator.hasNext();) {
			EObject objA = iterator.next();
			EObject objB = correspondencesA2B.get(objA);
			res += " " + objA + " <-> " + objB;

			if (iterator.hasNext()) {
				res += "\n";
			}
		}

		return res;
	}

}
