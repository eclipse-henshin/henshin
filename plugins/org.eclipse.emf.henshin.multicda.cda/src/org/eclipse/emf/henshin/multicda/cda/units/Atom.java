package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;

/**
 * This class provides all kinds of Atoms
 * 
 * @author Jevgenij Huebert 18.05.2018
 */
public abstract class Atom extends Span {

	public Atom(Atom s) {
		this(s, "A", "Atom");
	}

	protected Atom(Atom s, String tag, String name) {
		super(s, tag, name);
		setForbid(s.isForbid);
		setRequire(s.isRequire);
	}

	boolean deleteEdgeConflictAtom = false;
	Set<ConflictReason> minimalConflictReasons;

	public boolean isDeleteEdgeConflictAtom() {
		return deleteEdgeConflictAtom;
	}

	protected Atom(Atom candidate, Set<ConflictReason> minimalConflictReasons, String tag, String name) {
		super(candidate, tag, name);
		setForbid(candidate.isForbid);
		setRequire(candidate.isRequire);
		this.minimalConflictReasons = minimalConflictReasons;
		for (Reason mcr : minimalConflictReasons) {
			mcr.addContainedConflictAtom(this);
		}
		if (candidate.getGraph().getNodes().size() == 2)
			deleteEdgeConflictAtom = true;

	}

	protected Atom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag, String name) {
		super(rule1Mappings, s1, rule2Mappings, tag, name);
	}

	/**
	 * This class provides all kind of Conflict Atoms
	 * 
	 * @author Jevgenij Huebert 18.05.2018
	 */
	public static abstract class ConflictAtom extends Atom {

		protected ConflictAtom(Atom s, String tag, String name) {
			super(s, tag, name);
		}

		protected ConflictAtom(Atom candidate, Set<ConflictReason> minimalConflictReasons, String tag, String name) {
			super(candidate, minimalConflictReasons, tag, name);
		}

		protected ConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag,
				String name) {
			super(rule1Mappings, s1, rule2Mappings, tag, name);
		}
	}

	public static class DeleteConflictAtom extends ConflictAtom {

		public DeleteConflictAtom(Atom candidate) {
			super(candidate,
					candidate.minimalConflictReasons == null ? new HashSet<>() : candidate.minimalConflictReasons,
					"DCA", "Delete conflict atom");
			sortID = 1;
			tag = "D";
		}

		public DeleteConflictAtom(Atom candidate, Set<ConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "DCA", "Delete conflict atom");
			sortID = 1;
			tag = "D";
		}

		public DeleteConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "DCA", "Delete conflict atom");
			sortID = 1;
			tag = "D";
		}
	}

	public static class CreateConflictAtom extends ConflictAtom {

		public CreateConflictAtom(Atom candidate) {
			super(candidate,
					candidate.minimalConflictReasons == null ? new HashSet<>() : candidate.minimalConflictReasons,
					"CCA", "Create use conflict atom");
			sortID = 2;
			tag = "C";
		}

		public CreateConflictAtom(Atom candidate, Set<ConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CCA", "Create conflict atom");
			sortID = 2;
			tag = "C";
		}

		public CreateConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "CCA", "Create conflict atom");
			sortID = 2;
			tag = "C";
		}
	}

	public static class ChangeConflictAtom extends ConflictAtom {

		public ChangeConflictAtom(Atom candidate) {
			super(candidate,
					candidate.minimalConflictReasons == null ? new HashSet<>() : candidate.minimalConflictReasons,
					"ChCA", "Change conflict atom");
			sortID = 3;
			tag = "Ch";
		}

		public ChangeConflictAtom(Atom candidate, Set<ConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "ChCA", "Change conflict atom");
			sortID = 3;
			tag = "Ch";
		}

		public ChangeConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "ChCA", "Change conflict atom");
			sortID = 3;
			tag = "Ch";
		}
	}

	public static class CreateEdgeDeleteNodeConflictAtom extends ConflictAtom {
		public CreateEdgeDeleteNodeConflictAtom(Atom candidate, Set<ConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CEDNCA", "Create edge delete node conflict atom");
			sortID = 4;
			tag = "C";
		}
	}

	/**
	 * @author Jevgenij Huebert 18.05.2018 This class provides all kind of
	 *         Dependency Atoms
	 */
	public static abstract class DependencyAtom extends Atom {
		protected DependencyAtom(Atom s, String tag, String name) {
			super(s, s.minimalConflictReasons, tag, name);
		}
	}

	public static class CreateDependencyAtom extends DependencyAtom {
		public CreateDependencyAtom(Atom s) {
			super(s, "CDA", "Create dependency atom");
			sortID = 5;
			tag = "C";
		}
	}

	public static class DeleteDependencyAtom extends DependencyAtom {
		public DeleteDependencyAtom(Atom s) {
			super(s, "DDA", "Delete dependency atom");
			sortID = 6;
			tag = "C";
		}
	}

	public static class ChangeDependencyAtom extends DependencyAtom {
		public ChangeDependencyAtom(Atom s) {
			super(s, "ChDA", "Change dependency atom");
			sortID = 7;
			tag = "Ch";
		}
	}

	public static class DeleteEdgeDeleteNodeDependencyAtom extends DependencyAtom {
		public DeleteEdgeDeleteNodeDependencyAtom(Atom s) {
			super(s, "DEDNDA", "Delete edge delete node dependency atom");
			sortID = 8;
			tag = "D";
		}
	}
}
