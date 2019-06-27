package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;

/**
 * Symmetric reason contains one Reason (S1) and a set of Reasons (S2)
 * 
 * @author Jevgenij Huebert
 */
public class SymmetricReason extends Reason {
	private Reason s1;
	private Set<Reason> s2;

	public SymmetricReason(Reason reason, Set<Reason> reasons) {
		super(reason, reason.tag + reasons.iterator().next().tag + (reason instanceof ConflictReason ? "CR" : "DR"),
				reason.NAME.split(" ")[0] + " - " + reasons.iterator().next().NAME.split(" ")[0]
						+ reason.NAME.split(reason.NAME.split(" ")[0])[1]);
		if (reason instanceof DeleteEdgeDeleteNodeDependencyReason
				|| reason instanceof CreateEdgeDeleteNodeConflictReason)
			System.err.println(reason + " is not a valid reason!");
		s1 = reason;
		s2 = reasons;
		tag = reason.tag;
		sortID = s1.sortID;
	}

	public final Reason getS1() {
		return s1;
	}

	public final Set<Reason> getS2() {
		return s2;
	}

	@Override
	public String toString() {
		return toSymetricString(true);
	}

	public String toSymetricString(boolean complete) {
		String result = (isMinimalReason() ? "M" : "") + getFullID() + ": "
				+ getS1().toString().split(getS1().ID + ": ")[1] + " -> ";
		if (!complete) {
			int size = s2.size() - 1;
			Reason s2R = s2.iterator().next();
			result += s2R.toString() + (size > 0 ? "... " + size + " more" : "");
		} else {
			String s2String = "";
			for (Reason s2R : s2)
				s2String += ", " + s2R.toString();
			result += s2String.substring(2);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SymmetricReason) || !super.equals(obj))
			return false;
		Set<Reason> oS2 = ((SymmetricReason) obj).getS2();
		Set<Reason> S2 = getS2();
		if (oS2.size() != S2.size())
			return false;
		Set<Reason> checked = new HashSet<>();
		for (Reason reason1 : S2) {
			boolean found = false;
			for (Reason reason2 : oS2)
				if (found = reason1.equals(reason2) && checked.add(reason2))
					break;
			if (!found)
				return false;
		}
		return true;
	}

	@Override
	public int compareTo(Span o) {
		int value = getS1().compareTo(o instanceof SymmetricReason ? ((SymmetricReason) o).getS1() : o);
		if (value != 0)
			return value;
		else if (o instanceof SymmetricReason) {
			SymmetricReason T = this;
			SymmetricReason O = (SymmetricReason) o;
			String no1 = T.getS2().toString();
			String no2 = O.getS2().toString();
			String ed1 = T.getS2().toString();
			String ed2 = O.getS2().toString();
			if ((value = ed1.length() - ed2.length()) != 0)
				return value;
			if ((value = no1.length() - no2.length()) != 0)
				return value;
			if ((value = ed1.compareTo(ed2)) != 0)
				return value;
			if ((value = no1.compareTo(no2)) != 0)
				return value;
		}
		return -1;
	}
}
