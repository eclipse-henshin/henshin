 /**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.conflict;

import java.util.Set;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;

/**
 * @author Jevgenij Huebert 18.04.2018
 */
public abstract class ConflictReason extends Reason {
	private static final int ATOMS_AND_MINIMALS = 48;

	protected ConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2, String tag, String name) {
		super(maps, graph, maps2, tag, name);
	}

	protected ConflictReason(Span reason, String tag, String name) {
		super(reason, tag, name);
		this.isRequire = reason.isRequire();
		this.isForbid = reason.isForbid();
	}

	protected ConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
			Set<Reason> originMCRs, String tag, String name) {
		super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, tag, name);
	}

	public static class DeleteConflictReason extends ConflictReason {
		public DeleteConflictReason(Span reason) {
			super(reason, "DCR", "Delete conflict reason");
			sortID = ATOMS_AND_MINIMALS + 1;
			tag = "D";
		}

		public DeleteConflictReason(Span reason, boolean... isForbidRequire) {
			super(reason, "DCR", "Delete conflict reason");
			if (isForbidRequire.length != 0)
				this.isForbid = isForbidRequire[0];
			if (isForbidRequire.length > 1)
				this.isRequire = isForbidRequire[1];
			sortID = ATOMS_AND_MINIMALS + 1;
			tag = "D";
		}

		public DeleteConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2) {
			super(maps, graph, maps2, "DCR", "Delete conflict reason");
			sortID = ATOMS_AND_MINIMALS + 1;
			tag = "D";
		}

		public DeleteConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, "DCR", "Delete conflict reason");
			sortID = ATOMS_AND_MINIMALS + 1;
			tag = "D";
		}
	}

	public static class CreateConflictReason extends ConflictReason {
		public CreateConflictReason(Span reason) {
			super(reason, "CCR", "Create conflict reason");
			sortID = ATOMS_AND_MINIMALS + 2;
			tag = "C";
		}

		public CreateConflictReason(Reason reason, boolean... isForbidRequire) {
			super(reason, "CCR", "Create conflict reason");
			if (isForbidRequire.length != 0)
				this.isForbid = isForbidRequire[0];
			if (isForbidRequire.length > 1)
				this.isRequire = isForbidRequire[1];
			sortID = ATOMS_AND_MINIMALS + 2;
			tag = "C";
		}

		public CreateConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, "CCR", "Create conflict reason");
			sortID = ATOMS_AND_MINIMALS + 2;
			tag = "C";
		}
	}

	public static class ChangeConflictReason extends ConflictReason {
		public ChangeConflictReason(Span reason) {
			super(reason, "ChCR", "Change conflict reason");
			sortID = ATOMS_AND_MINIMALS + 3;
			tag = "Ch";
		}

		public ChangeConflictReason(Reason reason, boolean... isForbidRequire) {
			super(reason, "ChCR", "Change conflict reason");
			if (isForbidRequire.length != 0)
				this.isForbid = isForbidRequire[0];
			if (isForbidRequire.length > 1)
				this.isRequire = isForbidRequire[1];
			sortID = ATOMS_AND_MINIMALS + 3;
			tag = "Ch";
		}

		public ChangeConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, "ChCR", "Change conflict reason");
			sortID = ATOMS_AND_MINIMALS + 3;
			tag = "Ch";
		}
	}

	public static class CreateEdgeDeleteNodeConflictReason extends ConflictReason {
		public CreateEdgeDeleteNodeConflictReason(Span reason) {
			super(reason, "CEDNCR", "Create edge delete node conflict reason");
			sortID = ATOMS_AND_MINIMALS + 4;
			tag = ID;
			tag = "C";
		}
	}
}
