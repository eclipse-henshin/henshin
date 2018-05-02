/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.conflict;

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

	public static abstract class DeleteUseConflictReason extends ConflictReason {

		/**
		 * @param s1
		 */
		public DeleteUseConflictReason(Reason s1) {
			super(s1, "DUCR", "Delete use conflict reason");
		}

		protected DeleteUseConflictReason(Reason s1, String tag, String name) {
			super(s1, tag, name);
		}

		protected DeleteUseConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2, String tag, String name) {
			super(maps, graph, maps2, tag, name);
		}

		protected DeleteUseConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs, String tag, String name) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, tag, name);
		}

	}

	public static class DeleteReadConflictReason extends DeleteUseConflictReason {

		/**
		 * @param s1
		 */
		public DeleteReadConflictReason(Reason s1) {
			super(s1, "DRCR", "Delete read conflict reason");
			sortID = 1;
		}

		public DeleteReadConflictReason(Set<Mapping> maps, Graph graph, Set<Mapping> maps2) {
			super(maps, graph, maps2, "DRCR", "Delete read conflict reason");
		}

		public DeleteReadConflictReason(Set<Mapping> mappingsToR1, Graph graph1Copy, Set<Mapping> mappingsToR2,
				Set<Reason> originMCRs) {
			super(mappingsToR1, graph1Copy, mappingsToR2, originMCRs, "DRCR", "Delete read conflict reason");
		}

	}

	public static class DeleteDeleteConflictReason extends DeleteUseConflictReason implements DoubleSpan {
		public DeleteDeleteConflictReason(Reason r1, Reason r2) {
			super(r1, "DDCR", "Delete delete conflict reason");
			init(r1, r2);
			sortID = 2;
		}

		public DeleteDeleteConflictReason(Reason r1, Set<Reason> r2Set) {
			super(r1, "DDCR", "Delete delete conflict reason");
			init(r1, r2Set);
			sortID = 2;
		}

		public DeleteDeleteConflictReason(DoubleSpan cddr) {
			this(cddr.getS1(), cddr.getS2Set());
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

	public static class ChangeAttrConflictReason extends ConflictReason {
		public ChangeAttrConflictReason(Reason reason) {
			super(reason, "ChACR", "Change attribute conflict reason");
			sortID = 5;
		}
	}

	public static class DeleteAttrConflictReason extends ConflictReason {
		public DeleteAttrConflictReason(Reason reason) {
			super(reason, "DACR", "Delete attribute conflict reason");
			sortID = 6;
		}
	}

	public static class CreateEdgeDeleteNodeConflictReason extends ConflictReason {
		public CreateEdgeDeleteNodeConflictReason(Reason reason) {
			super(reason, "CEDNCR", "Create edge delete node conflict reason");
			sortID = 7;
		}
	}
}
