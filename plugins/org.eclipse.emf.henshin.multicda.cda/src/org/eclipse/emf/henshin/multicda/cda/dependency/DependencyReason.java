/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.dependency;

import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;

/**
 * @author Jevgenij Huebert 18.04.2018
 */
public abstract class DependencyReason extends Reason {
	private static final int CONF_IDS = 52;

	protected DependencyReason(Span reason, String tag, String name) {
		super(reason, tag, name);
		this.isForbid = reason.isForbid();
		this.isRequire = reason.isRequire();
		if (reason instanceof Reason)
			this.isMinimalReason = ((Reason) reason).isMinimalReason();
	}

	public static class CreateDependencyReason extends DependencyReason {
		public CreateDependencyReason(Span reason) {
			super(reason, "CDR", "Create dependency reason");
			sortID = CONF_IDS + 1;
			tag = "C";
		}
	}

	public static class DeleteDependencyReason extends DependencyReason {
		public DeleteDependencyReason(Span reason) {
			super(reason, "DDR", "Delete dependency reason");
			sortID = CONF_IDS + 2;
			tag = "D";
		}
	}

	public static class ChangeDependencyReason extends DependencyReason {
		public ChangeDependencyReason(Span reason) {
			super(reason, "ChDR", "Change dependency reason");
			sortID = CONF_IDS + 3;
			tag = "Ch";
		}
	}

	public static class DeleteEdgeDeleteNodeDependencyReason extends DependencyReason {
		public DeleteEdgeDeleteNodeDependencyReason(Span reason) {
			super(reason, "DEDNDR", "Delete edge delete node dependency reason");
			sortID = CONF_IDS + 4;
			tag = ID;
			tag = "D";
		}
	}
}
