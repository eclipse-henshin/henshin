/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.conflict;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.multicda.cda.units.DoubleSpan;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

/**
 * @author Jevgenij Huebert
 */
public abstract class ConflictReason extends Reason {

	/**
	 * @param span
	 */
	public ConflictReason(Reason conflictReason) {
		super(conflictReason, "CR", "Conflict reason");
	}

	protected ConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2, String tag, String name) {
		super(maps, graph, maps2, tag, name);
	}

	protected ConflictReason(Reason s1, String tag, String name) {
		super(s1, tag, name);
	}

	protected ConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
			Set<Reason> originMCRs, String tag, String name) {
		super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, tag, name);
	}

	public static class DeleteReadConflictReason extends ConflictReason implements DoubleSpan {
		public DeleteReadConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "DDCR(DS)" : "DRCR"),
					"Delete " + (reason.isDoubleReason() ? "delete " : "read ") + "conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 1 : 2;
		}

		public DeleteReadConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2) {
			super(maps, graph, maps2, "DRCR", "Delete read conflict reason");
			init(this, new HashSet<>());
			sortID = 1;
		}

		public DeleteReadConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, "DRCR", "Delete read conflict reason");
			init(this, new HashSet<>());
			sortID = 1;
		}

		public DeleteReadConflictReason(Reason r1, Set<Reason> r2Set) {
			super(r1, "DDCR(DS)", "Delete delete conflict reason");
			init(r1, r2Set);
			sortID = 2;
		}

	}

	public static class CreateForbidConflictReason extends ConflictReason {
		public CreateForbidConflictReason(Reason reason) {
			super(reason, "CFCR", "Create forbid conflict reason");
			sortID = 3;
		}
	}

	public static class DeleteRequireConflictReason extends ConflictReason {
		public DeleteRequireConflictReason(Reason reason) {
			super(reason, "DReqCR", "Delete require conflict reason");
			sortID = 4;
		}
	}

	public static class DeleteAttrConflictReason extends ConflictReason implements DoubleSpan {
		public DeleteAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "DACR(DS)" : "DACR"),
					"Delete " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 5 : 6;
		}

		public DeleteAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "DACR(DS)", "Delete change attribute conflict reason");
			sortID = 6;
			init(reason, s2);
		}
	}

	public static class CreateAttrConflictReason extends ConflictReason implements DoubleSpan {
		public CreateAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "CACR(DS)" : "CACR"),
					"Create " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 7 : 8;
		}

		public CreateAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "CACR(DS)", "Create change attribute conflict reason");
			sortID = 8;
			init(reason, s2);
		}
	}

	public static class ChangeAttrConflictReason extends ConflictReason implements DoubleSpan {
		public ChangeAttrConflictReason(Reason reason) {
			super(reason, (reason.isDoubleReason() ? "ChACR(DS)" : "ChACR"),
					"Change " + (reason.isDoubleReason() ? "change " : "") + "attribute conflict reason");
			init(reason, reason.isDoubleReason() ? ((DoubleSpan) reason).getS2Set() : null);
			sortID = reason.isDoubleReason() ? 9 : 10;
		}

		public ChangeAttrConflictReason(Reason reason, Set<Reason> s2) {
			super(reason, "ChACR(DS)", "Change change attribute conflict reason");
			sortID = 10;
			init(reason, s2);
		}
	}

	public static class CreateEdgeDeleteNodeConflictReason extends ConflictReason {
		public CreateEdgeDeleteNodeConflictReason(Reason reason) {
			super(reason, "CEDNCR", "Create edge delete node conflict reason");
			sortID = 11;
		}
	}

	public static class DeleteEdgeForbidNodeConflictReason extends ConflictReason {
		public DeleteEdgeForbidNodeConflictReason(Reason reason) {
			super(reason, "DEFNCR", "Delete edge forbid node conflict reason");
			sortID = 12;
		}
	}

	public static class CreateEdgeRequireNodeConflictReason extends ConflictReason {
		public CreateEdgeRequireNodeConflictReason(Reason reason) {
			super(reason, "CEReqNCR", "Create edge require node conflict reason");
			sortID = 13;
		}
	}
}
