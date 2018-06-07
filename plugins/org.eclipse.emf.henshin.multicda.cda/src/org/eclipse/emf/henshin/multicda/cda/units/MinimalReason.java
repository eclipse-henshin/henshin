package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashSet;
import java.util.Set;

public abstract class MinimalReason extends Reason {
	protected Set<Atom> containedConflictAtom;

	public MinimalReason(Reason reason) {
		this(reason, "MR", "Minimal reason");
	}

	protected MinimalReason(Reason reason, String tag, String name) {
		super(reason, tag, name);
		containedConflictAtom = new HashSet<>();
		if (reason instanceof MinimalReason) {
			containedConflictAtom.addAll(((MinimalReason) reason).getContainedConflictAtoms());
		}
	}

	public void addContainedConflictAtom(Atom conflictAtom) {
		containedConflictAtom.add(conflictAtom);
	}

	public Set<Atom> getContainedConflictAtoms() {
		return containedConflictAtom;
	}

	public static abstract class MinimalConflictReason extends MinimalReason {

		public MinimalConflictReason(Reason reason) {
			super(reason, "MCR", "Minimal conflict reason");
		}

		protected MinimalConflictReason(Reason reason, String tag, String name) {
			super(reason, tag, name);
		}
	}

	public static class MinimalDeleteReadConflictReason extends MinimalConflictReason implements DoubleSpan {

		public MinimalDeleteReadConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MDDCR(DS)" : "MDRCR"),
					(reason.isDoubleReason() ? "Minimal delete delete conflict reason"
							: "Minimal delete read conflict reason"));
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 1 : 2;
		}

		public MinimalDeleteReadConflictReason(Reason s1, Set<Reason> s2) {
			super(s1, "MDDCR(DS)", "Minimal delete delete conflict reason");
			init(s1, s2);
			sortID = 2;
		}
	}

	public static class MinimalCreateForbidConflictReason extends MinimalConflictReason {
		public MinimalCreateForbidConflictReason(Reason reason) {
			super(reason, "MCFCR", "Minimal create forbid conflict reason");
			sortID = 3;
		}

	}

	public static class MinimalDeleteRequireConflictReason extends MinimalConflictReason {
		public MinimalDeleteRequireConflictReason(Reason reason) {
			super(reason, "MDReqCR", "Minimal delete require conflict reason");
			sortID = 4;
		}

	}

	public static abstract class MinimalDependencyReason extends MinimalReason {

		public MinimalDependencyReason(Reason reason) {
			super(reason, "MDR", "Minimal dependency reason");
		}

		protected MinimalDependencyReason(Reason reason, String tag, String name) {
			super(reason, tag, name);
		}
	}

	public static class MinimalCreateReadDependencyReason extends MinimalDependencyReason implements DoubleSpan {

		public MinimalCreateReadDependencyReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MCDDR(DS)" : "MCRDR"),
					(reason.isDoubleReason() ? "Minimal create delete dependency reason"
							: "Minimal create delete dependency reason"));
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 5 : 6;
		}
	}

	public static class MinimalDeleteForbidDependencyReason extends MinimalDependencyReason {
		public MinimalDeleteForbidDependencyReason(Reason reason) {
			super(reason, "MDFDR", "Minimal delete forbid dependency reason");
			sortID = 7;
		}
	}

	public static class MinimalCreateRequireDependencyReason extends MinimalDependencyReason {
		public MinimalCreateRequireDependencyReason(Reason reason) {
			super(reason, "MCReqDR", "Minimal create require dependency reason");
			sortID = 8;
		}
	}

	public static class MinimalDeleteAttrConflictReason extends MinimalConflictReason implements DoubleSpan {
		public MinimalDeleteAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MDACR(DS)" : "MDACR"),
					"Minimal delete " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 9 : 10;
		}

		public MinimalDeleteAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "MDACR(DS)", "Minimal delete change attribute conflict reason");
			sortID = 10;
			init(reason, s2);
		}
	}

	public static class MinimalCreateAttrConflictReason extends MinimalConflictReason implements DoubleSpan {
		public MinimalCreateAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MCACR(DS)" : "MCACR"),
					"Minimal create " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 11 : 12;
		}

		public MinimalCreateAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "MCACR(DS)", "Minimal create change attribute conflict reason");
			sortID = 12;
			init(reason, s2);
		}
	}

	public static class MinimalChangeAttrConflictReason extends MinimalConflictReason implements DoubleSpan {
		public MinimalChangeAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MChACR(DS)" : "MChACR"),
					"Minimal change " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 13 : 14;
		}

		public MinimalChangeAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "MChACR(DS)", "Minimal change change attribute conflict reason");
			sortID = 14;
			init(reason, s2);
		}
	}

	public static class MinimalDeleteAttrDependencyReason extends MinimalDependencyReason implements DoubleSpan {
		public MinimalDeleteAttrDependencyReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MDADR(DS)" : "MDADR"),
					"Minimal delete " + (reason.isDoubleReason() ? "change " : "") + "attribute dependency reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 15 : 16;
		}

		public MinimalDeleteAttrDependencyReason(Reason reason, Set<Reason> s2) {
			super(reason, "MDADR(DS)", "Minimal delete change attribute dependency reason");
			sortID = 16;
			init(reason, s2);
		}
	}

	public static class MinimalCreateAttrDependencyReason extends MinimalDependencyReason implements DoubleSpan {
		public MinimalCreateAttrDependencyReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MCADR(DS)" : "MCADR"),
					"Minimal create " + (reason.isDoubleReason() ? "change " : "") + "attribute dependency reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 17 : 18;
		}

		public MinimalCreateAttrDependencyReason(Reason reason, Set<Reason> s2) {
			super(reason, "MCADR(DS)", "Minimal create change attribute dependency reason");
			sortID = 18;
			init(reason, s2);
		}
	}

	public static class MinimalChangeAttrDependencyReason extends MinimalDependencyReason implements DoubleSpan {
		public MinimalChangeAttrDependencyReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "MChADR(DS)" : "MChADR"),
					"Minimal change " + (reason.isDoubleReason() ? "change " : "") + "attribute dependency reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 19 : 20;
		}

		public MinimalChangeAttrDependencyReason(Reason reason, Set<Reason> s2) {
			super(reason, "MChADR(DS)", "Minimal change change attribute dependency reason");
			sortID = 20;
			init(reason, s2);
		}
	}

	public static class MinimalCreateEdgeDeleteNodeConflictReason extends MinimalConflictReason {
		public MinimalCreateEdgeDeleteNodeConflictReason(Reason reason) {
			super(reason, "MCEDNCR", "Minimal create edge delete node conflict reason");
			sortID = 21;
		}
	}

	public static class MinimalDeleteEdgeDeleteNodeDependencyReason extends MinimalDependencyReason {
		public MinimalDeleteEdgeDeleteNodeDependencyReason(Reason reason) {
			super(reason, "MDEDNDR", "Minimal delete edge delete node dependency reason");
			sortID = 22;
		}
	}

	public static class MinimalDeleteEdgeForbidNodeConflictReason extends MinimalConflictReason {
		public MinimalDeleteEdgeForbidNodeConflictReason(Reason reason) {
			super(reason, "MDEFNCR", "Minimal delete edge forbid node conflict reason");
			sortID = 23;
		}
	}

	public static class MinimalCreateEdgeForbidNodeDependencyReason extends MinimalDependencyReason {
		public MinimalCreateEdgeForbidNodeDependencyReason(Reason reason) {
			super(reason, "MCEFNDR", "Minimal create edge forbid node dependency reason");
			sortID = 24;
		}
	}

	public static class MinimalCreateEdgeRequireNodeConflictReason extends MinimalConflictReason {
		public MinimalCreateEdgeRequireNodeConflictReason(Reason reason) {
			super(reason, "MCEReqNCR", "Minimal create edge require node conflict reason");
			sortID = 25;
		}
	}

	public static class MinimalDeleteEdgeRequireNodeDependencyReason extends MinimalDependencyReason {
		public MinimalDeleteEdgeRequireNodeDependencyReason(Reason reason) {
			super(reason, "MDEReqNDR", "Minimal delete edge require node dependency reason");
			sortID = 26;
		}
	}
}
