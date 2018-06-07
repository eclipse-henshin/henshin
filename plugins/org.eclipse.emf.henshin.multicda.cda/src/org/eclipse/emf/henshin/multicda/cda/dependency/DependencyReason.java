/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.dependency;

import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.ChangeAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.DoubleSpan;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

/**
 * @author Jevgenij Huebert 18.04.2018
 */
public abstract class DependencyReason extends Reason {
	private static final int CONF_IDS = 13;

	/**
	 * @param span
	 */
	public DependencyReason(Reason dependencyReason) {
		super(dependencyReason, "DR", "Dependency reason");
	}

	protected DependencyReason(Reason s1, String tag, String name) {
		super(s1, tag, name);
	}

	public static class CreateReadDependencyReason extends DependencyReason implements DoubleSpan {

		public CreateReadDependencyReason(DeleteReadConflictReason ddcr) {
			super(ddcr.getS1(), (ddcr.isDoubleSpan() ? "CDDR(DS)" : "CRDR"),
					ddcr.isDoubleSpan() ? "Create delete dependency reason" : "Create read dependency reason");
			init(ddcr);
			if (!isDoubleSpan())
				sortID = CONF_IDS + 1;
			else
				sortID = CONF_IDS + 2;
		}
	}

	public static class DeleteForbidDependencyReason extends DependencyReason {

		public DeleteForbidDependencyReason(Reason reason) {
			super(reason, "DFDR", "Delete forbid dependency reason");
			sortID = CONF_IDS + 3;
		}

	}

	public static class CreateRequireDependencyReason extends DependencyReason {
		public CreateRequireDependencyReason(Reason reason) {
			super(reason, "CReqDR", "Create require dependency reason");
			sortID = CONF_IDS + 4;
		}
	}

	public static class DeleteAttrDependencyReason extends DependencyReason implements DoubleSpan {

		public DeleteAttrDependencyReason(CreateAttrConflictReason ddcr) {
			super(ddcr.getS1(), "DADR" + (ddcr.isDoubleSpan() ? "(DS)" : ""),
					"Delete " + (ddcr.isDoubleSpan() ? "change " : "") + "attribute dependency reason");
			init(ddcr);
			if (!isDoubleSpan())
				sortID = CONF_IDS + 5;
			else
				sortID = CONF_IDS + 6;
		}
	}

	public static class CreateAttrDependencyReason extends DependencyReason implements DoubleSpan {

		public CreateAttrDependencyReason(DeleteAttrConflictReason ddcr) {
			super(ddcr.getS1(), "CADR" + (ddcr.isDoubleSpan() ? "(DS)" : ""),
					"Create " + (ddcr.isDoubleSpan() ? "change " : "") + "attribute dependency reason");
			init(ddcr);
			if (!isDoubleSpan())
				sortID = CONF_IDS + 7;
			else
				sortID = CONF_IDS + 8;
		}
	}

	public static class ChangeAttrDependencyReason extends DependencyReason implements DoubleSpan {
		public ChangeAttrDependencyReason(ChangeAttrConflictReason ddcr) {
			super(ddcr.getS1(), "ChADR" + (ddcr.isDoubleSpan() ? "(DS)" : ""),
					"Change " + (ddcr.isDoubleSpan() ? "change " : "") + "attribute dependency reason");
			init(ddcr);
			if (!isDoubleSpan())
				sortID = CONF_IDS + 9;
			else
				sortID = CONF_IDS + 10;
		}
	}

	public static class DeleteEdgeDeleteNodeDependencyReason extends DependencyReason {
		public DeleteEdgeDeleteNodeDependencyReason(Reason reason) {
			super(reason, "DEDNDR", "Delete edge delete node dependency reason");
			sortID = CONF_IDS + 11;
		}
	}

	public static class CreateEdgeForbidNodeDependencyReason extends DependencyReason {
		public CreateEdgeForbidNodeDependencyReason(Reason reason) {
			super(reason, "CEFNDR", "Create edge forbid node dependency reason");
			sortID = CONF_IDS + 12;
		}
	}

	public static class DeleteEdgeRequireNodeDependencyReason extends DependencyReason {
		public DeleteEdgeRequireNodeDependencyReason(Reason reason) {
			super(reason, "DEReqNDR", "Delete edge require node dependency reason");
			sortID = CONF_IDS + 13;
		}
	}
}
