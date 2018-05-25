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

	public static abstract class MinimalDeleteUseConflictReason extends MinimalConflictReason {

		public MinimalDeleteUseConflictReason(Reason reason) {
			super(reason, "MDRCR", "Minimal delete read conflict reason");
		}

		protected MinimalDeleteUseConflictReason(Reason reason, String tag, String name) {
			super(reason, tag, name);
		}
	}

	public static class MinimalDeleteReadConflictReason extends MinimalDeleteUseConflictReason {

		public MinimalDeleteReadConflictReason(Reason reason) {
			super(reason, "MDRCR", "Minimal delete read conflict reason");
			sortID = 1;
		}
	}

	public static class MinimalDeleteDeleteConflictReason extends MinimalDeleteUseConflictReason implements DoubleSpan {

		public MinimalDeleteDeleteConflictReason(DoubleSpan reason) {
			super(reason.getS1(), "MDDCR", "Minimal delete delete conflict reason");
			init(reason);
			sortID = 2;
		}

		public MinimalDeleteDeleteConflictReason(Reason s1, Set<Reason> s2) {
			super(s1, "MDDCR", "Minimal delete delete conflict reason");
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

	public static abstract class MinimalCreateUseDependencyReason extends MinimalDependencyReason {

		public MinimalCreateUseDependencyReason(Reason reason) {
			super(reason, "MCUDR", "Minimal create use dependency reason");
		}

		protected MinimalCreateUseDependencyReason(Reason reason, String tag, String name) {
			super(reason, tag, name);
		}
	}

	public static class MinimalCreateReadDependencyReason extends MinimalCreateUseDependencyReason {

		public MinimalCreateReadDependencyReason(Reason reason) {
			super(reason, "MCRDR", "Minimal create read dependency reason");
			sortID = 5;
		}
	}

	public static class MinimalCreateDeleteDependencyReason extends MinimalCreateUseDependencyReason
			implements DoubleSpan {
		public MinimalCreateDeleteDependencyReason(DoubleSpan reason) {
			super(reason.getS1(), "MCDDR", "Minimal create delete dependency reason");
			init(reason);
			sortID = 6;
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

	public static class MinimalChangeAttrConflictReason extends MinimalConflictReason {
		public MinimalChangeAttrConflictReason(Reason reason) {
			super(reason, "MChACR", "Minimal change attribute conflict reason");
			sortID = 9;
		}
	}

	public static class MinimalDeleteAttrConflictReason extends MinimalConflictReason {
		public MinimalDeleteAttrConflictReason(Reason reason) {
			super(reason, "MDACR", "Minimal delete attribute conflict reason");
			sortID = 10;
		}
	}

	public static class MinimalChangeAttrDependencyReason extends MinimalDependencyReason {
		public MinimalChangeAttrDependencyReason(Reason reason) {
			super(reason, "MChADR", "Minimal change attribute dependency reason");
			sortID = 11;
		}
	}

	public static class MinimalDeleteAttrDependencyReason extends MinimalDependencyReason {
		public MinimalDeleteAttrDependencyReason(Reason reason) {
			super(reason, "MDADR", "Minimal delete attribute dependency reason");
			sortID = 12;
		}
	}

	public static class MinimalCreateEdgeDeleteNodeConflictReason extends MinimalConflictReason {
		public MinimalCreateEdgeDeleteNodeConflictReason(Reason reason) {
			super(reason, "MCEDNCR", "Minimal create edge delete node conflict reason");
			sortID = 13;
		}
	}

	public static class MinimalDeleteEdgeDeleteNodeDependencyReason extends MinimalDependencyReason {
		public MinimalDeleteEdgeDeleteNodeDependencyReason(Reason reason) {
			super(reason, "MDEDNDR", "Minimal delete edge delete node dependency reason");
			sortID = 14;
		}
	}
}
