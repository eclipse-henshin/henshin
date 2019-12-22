package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

public class CloneHierarchy {
	Map<CloneGroup, Set<CloneGroup>> superToSubClones;
	Map<CloneGroup, Set<CloneGroup>> subToSuperClones;

	public CloneHierarchy() {
		superToSubClones = new HashMap<CloneGroup, Set<CloneGroup>>();
		subToSuperClones = new HashMap<CloneGroup, Set<CloneGroup>>();
	}

	public Set<CloneGroup> getSuperClones(CloneGroup cloneGroup) {
		return subToSuperClones.get(cloneGroup);
	}

	public Set<CloneGroup> getSubClones(CloneGroup cloneGroup) {
		return superToSubClones.get(cloneGroup);
	}

	public void addBasisCloneGroup(CloneGroup cloneGroup) {
		Set<CloneGroup> subClones = new HashSet<CloneGroup>();
		superToSubClones.put(cloneGroup, subClones);
	}

	public void addPair(CloneGroup superCloneGroup, CloneGroup subCloneGroup) {
		if (superCloneGroup == subCloneGroup) {
			System.err
					.println("Error during the construction of the clone hierarchy: "
							+ "Tried adding a clone group as its own sub clone.");
			return;
		}

		Set<CloneGroup> superClones = subToSuperClones.get(subCloneGroup);
		if (superClones == null) {
			superClones = new HashSet<CloneGroup>();
			subToSuperClones.put(subCloneGroup, superClones);
		}
		superClones.add(superCloneGroup);

		Set<CloneGroup> subClones = superToSubClones.get(superCloneGroup);
		if (subClones == null) {
			subClones = new HashSet<CloneGroup>();
			superToSubClones.put(superCloneGroup, subClones);
		}
		subClones.add(subCloneGroup);
	}

	/**
	 * Returns all clone groups without a super clone group.
	 * 
	 * @return
	 */
	public Set<CloneGroup> getTopCloneGroups() {
		Set<CloneGroup> result = new HashSet<CloneGroup>();
		for (CloneGroup cg : superToSubClones.keySet()) {
			if (!subToSuperClones.containsKey(cg)
					|| subToSuperClones.get(cg) == null)
				result.add(cg);
		}
		return result;
	}

	public Collection<CloneGroup> getTransitiveSubClones(CloneGroup cloneGroup) {
		Set<CloneGroup> result = new HashSet<CloneGroup>();
		if (superToSubClones.containsKey(cloneGroup)) {
			for (CloneGroup child : superToSubClones.get(cloneGroup)) {
				result.add(child);
				result.addAll(getTransitiveSubClones(child));
			}
		}
		if (result.contains(cloneGroup))
			throw new RuntimeException("Error in hierarchy");
		return result;
	}

	public void addChild(CloneGroup topCloneGroup, CloneGroup cloneGroup) {
		for (CloneGroup cg : getSubClones(topCloneGroup)) {
			if (SubCloneRelation.isSubClone(cg, cloneGroup)) {
				addPair(cg, cloneGroup);
			} else if (SubCloneRelation.isSubClone(cloneGroup, cg)) {
				addPair(cloneGroup, cg);
			}
		}
		addPair(topCloneGroup, cloneGroup);
	}
}
