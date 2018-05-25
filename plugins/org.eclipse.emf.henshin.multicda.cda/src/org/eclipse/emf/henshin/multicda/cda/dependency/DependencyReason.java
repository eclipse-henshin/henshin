/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.dependency;

import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.DoubleSpan;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

/**
 * @author Jevgenij Huebert 18.04.2018
 */
public abstract class DependencyReason extends Reason {

	/**
	 * @param span
	 */
	public DependencyReason(Reason dependencyReason) {
		super(dependencyReason, "DR", "Dependency reason");
	}

	protected DependencyReason(Reason s1, String tag, String name) {
		super(s1, tag, name);
	}

	public static abstract class CreateUseDependencyReason extends DependencyReason {

		/**
		 * @param s1
		 */
		public CreateUseDependencyReason(Reason s1) {
			super(s1, "CUDR", "Create use dependency reason");
		}

		protected CreateUseDependencyReason(Reason s1, String tag, String name) {
			super(s1, tag, name);
		}

	}

	public static class CreateReadDependencyReason extends CreateUseDependencyReason {

		/**
		 * @param s1
		 */
		public CreateReadDependencyReason(Reason s1) {
			super(s1, "CRDR", "Create read dependency reason");
			sortID = 8;
		}

	}

	public static class CreateDeleteDependencyReason extends CreateUseDependencyReason implements DoubleSpan {

		public CreateDeleteDependencyReason(DoubleSpan ddcr) {
			super(ddcr.getS1(), "CDDR", "Create delete dependency reason");
			init(ddcr);
			sortID = 9;
		}
	}

	public static class DeleteForbidDependencyReason extends DependencyReason {

		public DeleteForbidDependencyReason(Reason reason) {
			super(reason, "DFDR", "Delete forbid dependency reason");
			sortID = 10;
		}

	}

	public static class CreateRequireDependencyReason extends DependencyReason {
		public CreateRequireDependencyReason(Reason reason) {
			super(reason, "CReqDR", "Create require dependency reason");
			sortID = 11;
		}
	}

	public static class ChangeAttrDependencyReason extends DependencyReason {
		public ChangeAttrDependencyReason(Reason reason) {
			super(reason, "ChADR", "Change attribute dependency reason");
			sortID = 12;
		}
	}

	public static class DeleteAttrDependencyReason extends DependencyReason {
		public DeleteAttrDependencyReason(Reason reason) {
			super(reason, "DADR", "Delete attribute dependency reason");
			sortID = 13;
		}
	}
	public static class DeleteEdgeDeleteNodeDependencyReason extends DependencyReason {
		public DeleteEdgeDeleteNodeDependencyReason(Reason reason) {
			super(reason, "DEDNDR", "Delete edge delete node dependency reason");
			sortID = 14;
		}
	}
}
