package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.Set;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;

/**
 * @author Jevgenij Huebert 18.05.2018 This class provides all kinds of Atoms
 */
public abstract class Atom extends Reason {

	public Atom(Reason s) {
		super(s, "A", "Atom");
	}

	protected Atom(Reason s, String tag, String name) {
		super(s, tag, name);
	}

	boolean deleteEdgeConflictAtom = false;
	Set<MinimalConflictReason> minimalConflictReasons;

	/**
	 * @return the deleteEdgeConflictAtom
	 */
	public boolean isDeleteEdgeConflictAtom() {
		return deleteEdgeConflictAtom;
	}

	/**
	 * @return the reasons
	 */
	public Set<MinimalConflictReason> getMinimalConflictReasons() {
		return minimalConflictReasons;
	}

	protected Atom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons, String tag, String name) {
		super(candidate, tag, name);
		this.minimalConflictReasons = minimalConflictReasons;
		for (MinimalConflictReason mcr : minimalConflictReasons) {
			mcr.addContainedConflictAtom(this);
		}
		if (candidate.getGraph().getNodes().size() == 2)
			deleteEdgeConflictAtom = true;

	}

	protected Atom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
		this(candidate, minimalConflictReasons, "A", "Atom");
	}

	protected Atom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
		super(rule1Mappings, s1, rule2Mappings, "A", "Atom");
	}

	protected Atom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag, String name) {
		super(rule1Mappings, s1, rule2Mappings, tag, name);
	}

	/**
	 * @author Jevgenij Huebert 18.05.2018 This class provides all kind of Conflict
	 *         Atoms
	 */
	public static abstract class ConflictAtom extends Atom {

		public ConflictAtom(Reason s) {
			super(s, "CA", "Conflict atom");
		}

		public ConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CA", "Conflict atom");
		}

		protected ConflictAtom(Reason s, String tag, String name) {
			super(s, tag, name);
		}

		protected ConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons, String tag,
				String name) {
			super(candidate, minimalConflictReasons, tag, name);
		}

		protected ConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag,
				String name) {
			super(rule1Mappings, s1, rule2Mappings, tag, name);
		}
	}

	public static class DeleteUseConflictAtom extends ConflictAtom {

		public DeleteUseConflictAtom(Reason s) {
			super(s, "DUCA", "Delete use conflict atom");
			sortID = 1;
		}

		public DeleteUseConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "DUCA", "Delete use conflict atom");
			sortID = 1;
		}

		public DeleteUseConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "DUCA", "Delete use conflict atom");
			sortID = 1;
		}
	}

	public static class ForbidConflictAtom extends ConflictAtom {

		public ForbidConflictAtom(Reason s) {
			super(s, "FCA", "Forbid conflict atom");
			sortID = 2;
		}

		public ForbidConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "FCA", "Forbid conflict atom");
			sortID = 2;
		}
	}

	public static class RequireConflictAtom extends ConflictAtom {

		public RequireConflictAtom(Reason s) {
			super(s, "ReqCA", "Require conflict atom");
			sortID = 3;
		}

		public RequireConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "ReqCA", "Require conflict atom");
			sortID = 3;
		}
	}

	/**
	 * @author Jevgenij Huebert 18.05.2018 This class provides all kind of
	 *         Dependency Atoms
	 */
	public static abstract class DependencyAtom extends Atom {

		public DependencyAtom(Reason s) {
			super(s, "DA", "Dependency atom");
		}

		public DependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "DA", "Dependency atom");
		}

		protected DependencyAtom(Reason s, String tag, String name) {
			super(s, tag, name);
		}

		protected DependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons, String tag,
				String name) {
			super(candidate, minimalConflictReasons, tag, name);
		}

		protected DependencyAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag,
				String name) {
			super(rule1Mappings, s1, rule2Mappings, tag, name);
		}
	}

	public static class CreateUseDependencyAtom extends DependencyAtom {

		public CreateUseDependencyAtom(Reason s) {
			super(s, "CUDA", "Create use dependency atom");
			sortID = 4;
		}

		public CreateUseDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CUDA", "Create use dependency atom");
			sortID = 4;
		}
	}

	public static class ForbidDependencyAtom extends DependencyAtom {

		public ForbidDependencyAtom(Reason s) {
			super(s, "FDA", "Forbid dependency atom");
			sortID = 5;
		}

		public ForbidDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "FDA", "Forbid dependency atom");
			sortID = 5;
		}
	}

	public static class RequireDependencyAtom extends DependencyAtom {

		public RequireDependencyAtom(Reason s) {
			super(s, "ReqDA", "Require dependency atom");
			sortID = 6;
		}

		public RequireDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "ReqDA", "Require dependency atom");
			sortID = 6;
		}
	}

	public static class DeleteAttrConflictAtom extends ConflictAtom {

		public DeleteAttrConflictAtom(Reason s) {
			super(s, "DACA", "Delete attribute conflict atom");
			sortID = 7;
		}

		public DeleteAttrConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "DACA", "Delete attribute conflict atom");
			sortID = 7;
		}

		public DeleteAttrConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "DACA", "Delete attribute conflict atom");
			sortID = 7;
		}
	}

	public static class CreateAttrConflictAtom extends ConflictAtom {

		public CreateAttrConflictAtom(Reason s) {
			super(s, "CACA", "Create attribute conflict atom");
			sortID = 8;
		}

		public CreateAttrConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CACA", "Create attribute conflict atom");
			sortID = 8;
		}

		public CreateAttrConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "CACA", "Create attribute conflict atom");
			sortID = 8;
		}
	}

	public static class ChangeAttrConflictAtom extends ConflictAtom {

		public ChangeAttrConflictAtom(Reason s) {
			super(s, "ChACA", "Change attribute conflict atom");
			sortID = 9;
		}

		public ChangeAttrConflictAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CACA", "Change attribute conflict atom");
			sortID = 9;
		}

		public ChangeAttrConflictAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "CACA", "Change attribute conflict atom");
			sortID = 9;
		}
	}

	public static class DeleteAttrDependencyAtom extends DependencyAtom {

		public DeleteAttrDependencyAtom(Reason s) {
			super(s, "DADA", "Delete attribute dependency atom");
			sortID = 10;
		}

		public DeleteAttrDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "DADA", "Delete attribute dependency atom");
			sortID = 10;
		}

		public DeleteAttrDependencyAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "DADA", "Delete attribute dependency atom");
			sortID = 10;
		}
	}

	public static class CreateAttrDependencyAtom extends DependencyAtom {

		public CreateAttrDependencyAtom(Reason s) {
			super(s, "CADA", "Delete attribute dependency atom");
			sortID = 11;
		}

		public CreateAttrDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CADA", "Create attribute dependency atom");
			sortID = 11;
		}

		public CreateAttrDependencyAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "CADA", "Create attribute dependency atom");
			sortID = 11;
		}
	}

	public static class ChangeAttrDependencyAtom extends DependencyAtom {

		public ChangeAttrDependencyAtom(Reason s) {
			super(s, "ChADA", "Change attribute dependency atom");
			sortID = 12;
		}

		public ChangeAttrDependencyAtom(Reason candidate, Set<MinimalConflictReason> minimalConflictReasons) {
			super(candidate, minimalConflictReasons, "CADA", "Change attribute dependency atom");
			sortID = 12;
		}

		public ChangeAttrDependencyAtom(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings) {
			super(rule1Mappings, s1, rule2Mappings, "CADA", "Change attribute dependency atom");
			sortID = 12;
		}
	}
}
