package org.eclipse.emf.henshin.multicda.cda.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.impl.EdgeImpl;
import org.eclipse.emf.henshin.model.impl.NodeImpl;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateEdgeDeleteNodeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteEdgeDeleteNodeDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SymmetricReason;

/**
 *
 * This Class is used by
 * {@link org.eclipse.emf.henshin.multicda.cda.framework.CdaWorker CDAWorker}
 * and {@link org.eclipse.emf.henshin.multicda.cda.framework.CpaWorker
 * CPAWorker} to check result of Conflict and Dependency Analysis.
 * 
 * @author Jevgenij Huebert 18.05.2018
 */
public abstract class Condition {
	protected String[] values;

	public Condition(String... values) {
		this.values = values;
	}

	public boolean proove(GraphElement graphelement) {
		if (this instanceof Edge && graphelement instanceof EdgeImpl) {
			EdgeImpl edge = (EdgeImpl) graphelement;
			return edge.getSource().getName().equals(values[0]) && edge.getTarget().getName().equals(values[1]);
		} else if (this instanceof Node && graphelement instanceof NodeImpl) {
			NodeImpl edge = (NodeImpl) graphelement;
			return edge.getName().equals(values[0]);
		}
		return false;
	}

	@Override
	public String toString() {
		String result = "";
		for (String string : values)
			result += ", " + string;
		return getClass().getSimpleName() + ":" + " (" + result.substring(2) + ")";
	}

	@Override
	public int hashCode() {
		int i = 0;
		int m = 11;
		for (String s : values)
			i += s.hashCode() * m++;
		return i;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Condition && obj.getClass() == getClass()
				&& ((Condition) obj).values.length == values.length) {
			Condition c = (Condition) obj;
			for (String v1 : values) {
				boolean found = false;
				for (String v2 : c.values)
					if (v1.equals(v2)) {
						found = true;
						break;
					}
				if (!found)
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * This node simulates node of graphs
	 * 
	 * @author Jevgenij Huebert
	 */
	public static class Node extends Condition {
		public Node(String name) {
			super(name);
		}
	}

	/**
	 * This edge simulates edge of graphs
	 * 
	 * @author Jevgenij Huebert<br>
	 */
	public static class Edge extends Condition {
		public Edge(String from, String to) {
			super(from, to);
		}
	}

	/**
	 * Allows check the size of conflict reasons
	 * 
	 * @author Jevgenij Huebert
	 */
	public static class ReasonSize extends Condition implements Provable {
		protected int size = 0;

		public ReasonSize() {
			this.size = 0;
		}

		public ReasonSize(int size) {
			this.size = size;
		}

		public boolean proove(Integer size) {
			return this.size == size;
		}

		@Override
		public String toString() {
			return "(" + size + " Reasons)";
		}

		@Override
		public StateProvider getState() {
			return new StateProvider();
		}

		@Override
		public <T extends Span> boolean proove(T span) {
			return false;
		}
	}

	/**
	 * Allows check size of minimal reasons
	 * 
	 * @author Jevgenij Huebert
	 */
	public static class MinimalSize extends ReasonSize {
		public MinimalSize(int size) {
			super(size);
		}

		@Override
		public String toString() {
			return "(" + size + " Minimal Reasons)";
		}
	}

	/**
	 * Allows check the size of atoms
	 * 
	 * @author Jevgenij Huebert
	 */
	public static class AtomSize extends ReasonSize {
		public AtomSize(int size) {
			super(size);
		}

		@Override
		public String toString() {
			return "(" + size + " Atoms)";
		}
	}

	/**
	 * Container of conditions. Simulates a reason with edges and nodes as
	 * conditions.
	 * 
	 * @author Jevgenij Huebert
	 */
	public static abstract class Conditions implements Provable {
		protected Set<Condition> conditions = new HashSet<>();
		protected StateProvider state;

		public Conditions(StateProvider state, Condition... conditions) {
			for (Condition c : conditions) {
				if (c instanceof Edge) {
					this.conditions.add(new Node(c.values[0]));
					this.conditions.add(new Node(c.values[1]));
				}
				this.conditions.add(c);
			}
			this.state = state;
		}

		/**
		 * @param span
		 *            that should be proved with this conditions
		 * @return true if conditions has been proved successful
		 */
		public boolean proove(Span span) {
			span.toString();
			if (span instanceof Atom != state.is(State.Atom)
					|| (span instanceof Reason && ((Reason) span).isMinimalReason() != state.is(State.Minimal))
					|| span.isForbid() != state.is(State.Forbid) || span.isRequire() != state.is(State.Require)
					|| span.isDependency() != state.is(State.Dependency))
				return false;
			if ((span instanceof CreateEdgeDeleteNodeConflictAtom
					|| span instanceof CreateEdgeDeleteNodeConflictReason) != this instanceof CEDNCR
					|| (span instanceof DeleteEdgeDeleteNodeDependencyAtom
							|| span instanceof DeleteEdgeDeleteNodeDependencyReason) != this instanceof DEDNDR)
				return false;
			List<GraphElement> elements = new ArrayList<>(span.getGraph().getNodes());
			elements.addAll(span.getGraph().getEdges());
			if (sameType(span))
				for (Condition condition : conditions) {
					GraphElement toDelete = null;
					for (GraphElement element : elements)
						if (condition.proove(element)) {
							toDelete = element;
							break;
						}

					if (toDelete != null)
						elements.remove(toDelete);
					else
						return false;
				}
			return elements.isEmpty();
		}

		private boolean sameType(Span span) {
			boolean result = getClass().getSimpleName().startsWith(span.getTag());
			result &= !(span.getTag().length() == 1 ^ getClass().getSimpleName().charAt(1) != 'h');
			return result;
		}

		/**
		 * @param state that provides a state of reason
		 * @return true if this condition has provided state
		 */
		public boolean is(StateProvider... state) {
			return this.state.is(state);
		}

		/**
		 * @param state of reason
		 * @return true if this condition has given state
		 */
		public boolean is(State... state) {
			return this.state.is(state);
		}

		@Override
		public StateProvider getState() {
			return this.state;
		}

		@Override
		public String toString() {
			String result = "";
			for (Condition condition : conditions)
				result += ", " + condition;
			StateProvider s = state;
			String id = getClass().getSimpleName();
			id = id.substring(0, id.length() - 2);
			id = id + (s.is(State.Dependency) ? "D" : "C") + (s.is(StateProvider.ATOM) ? "A" : "R");
			return (s.is(State.Minimal) ? "M" : "") + (s.is(State.Forbid) ? "F" : s.is(State.Require) ? "Req" : "")
					+ id + ":" + " [" + result.substring(2) + "]";
		}
	}

	/**
	 * Simulated symmetric reason
	 * @author Jevgenij Huebert
	 */
	public static class SymmetricCondition implements Provable {
		private Conditions conditionsS1;
		private Conditions[] conditionsS2;

		public SymmetricCondition(Conditions conditionsS1, Conditions... conditionsS2) {
			this.conditionsS1 = conditionsS1;
			this.conditionsS2 = conditionsS2;
		}

		public boolean proove(Span span) {
			if (!(span instanceof SymmetricReason))
				return false;
			SymmetricReason reason = (SymmetricReason) span;
			if (conditionsS2.length != reason.getS2().size())
				return false;
			if (!conditionsS1.proove(reason.getS1()))
				return false;
			Map<Conditions, Reason> map = new HashMap<>();
			for (Reason r : reason.getS2()) {
				boolean found = false;
				for (Conditions c2 : conditionsS2)
					if (map.get(c2) == null && c2.proove(r)) {
						map.put(c2, r);
						found = true;
						break;
					}
				if (!found)
					return false;
			}
			return true;
		}

		@Override
		public String toString() {
			String result = "";
			for (Conditions condition : conditionsS2)
				result += ", " + condition;
			return getClass().getSimpleName() + ": " + conditionsS1
					+ (result.isEmpty() ? "" : " -> " + result.substring(2));
		}

		@Override
		public StateProvider getState() {
			return conditionsS1.getState();
		}
	}

	/**
	 * Simulated Delete Conflict Reason
	 * @author Jevgenij Huebert
	 */
	public static class DCR extends Conditions {
		public DCR(StateProvider state, Condition... conditions) {
			super(state, conditions);
		}

		public DCR(Condition... conditions) {
			super(new StateProvider(), conditions);
		}
	}

	/**
	 * Simulated Create Conflict Reason
	 * @author Jevgenij Huebert
	 */
	public static class CCR extends Conditions {
		public CCR(StateProvider state, Condition... conditions) {
			super(state, conditions);
		}

		public CCR(Condition... conditions) {
			super(new StateProvider(), conditions);
		}
	}

	/**
	 * Simulated Change Conflict Reason
	 * @author Jevgenij Huebert
	 */
	public static class ChCR extends Conditions {
		public ChCR(StateProvider state, Condition... conditions) {
			super(state, conditions);
		}

		public ChCR(Condition... conditions) {
			super(new StateProvider(), conditions);
		}
	}

	/**
	 * Simulated Create Edge Delete Node Conflict Reason
	 * @author Jevgenij Huebert
	 */
	public static class CEDNCR extends Conditions {
		public CEDNCR(State state, Condition... conditions) {
			super(new StateProvider(
					state == State.Atom ? State.Atom : state == State.Minimal ? State.Minimal : State.None),
					conditions);
		}

		public CEDNCR(Condition... conditions) {
			super(new StateProvider(), conditions);
		}
	}

	/**
	 * Simulated Delete Edge Create Node Conflict Reason
	 * @author Jevgenij Huebert
	 */
	public static class DEDNDR extends Conditions {
		public DEDNDR(State state, Condition... conditions) {
			super(new StateProvider(State.Dependency,
					state == State.Atom ? State.Atom : state == State.Minimal ? State.Minimal : State.None),
					conditions);
		}

		public DEDNDR(Condition... conditions) {
			super(new StateProvider(State.Dependency), conditions);
		}
	}

	/**
	 * States provider
	 * @author Jevgenij Huebert
	 *
	 */
	public static class StateProvider {
		private int state = State.None.id;
		public static final StateProvider DEPENDENCY = new StateProvider(State.Dependency);
		public static final StateProvider MINIMAL = new StateProvider(State.Minimal);
		public static final StateProvider ATOM = new StateProvider(State.Atom);
		public static final StateProvider FORBID = new StateProvider(State.Forbid);
		public static final StateProvider REQUIRE = new StateProvider(State.Require);

		public static final StateProvider ATOM_DEPENDENCY = new StateProvider(ATOM, DEPENDENCY);
		public static final StateProvider ATOM_FORBID = new StateProvider(ATOM, FORBID);
		public static final StateProvider ATOM_REQUIRE = new StateProvider(ATOM, REQUIRE);
		public static final StateProvider ATOM_DEPENDENCY_FORBID = new StateProvider(ATOM_DEPENDENCY, FORBID);
		public static final StateProvider ATOM_DEPENDENCY_REQUIRE = new StateProvider(ATOM_DEPENDENCY, REQUIRE);

		public static final StateProvider MINIMAL_DEPENDENCY = new StateProvider(MINIMAL, DEPENDENCY);
		public static final StateProvider MINIMAL_FORBID = new StateProvider(MINIMAL, FORBID);
		public static final StateProvider MINIMAL_REQUIRE = new StateProvider(MINIMAL, REQUIRE);
		public static final StateProvider MINIMAL_DEPENDENCY_FORBID = new StateProvider(MINIMAL_DEPENDENCY, FORBID);
		public static final StateProvider MINIMAL_DEPENDENCY_REQUIRE = new StateProvider(MINIMAL_DEPENDENCY, REQUIRE);

		public static final StateProvider DEPENDENCY_FORBID = new StateProvider(DEPENDENCY, FORBID);
		public static final StateProvider DEPENDENCY_REQUIRE = new StateProvider(DEPENDENCY, REQUIRE);

		public StateProvider(State state, State... states) {
			this.state |= state.id;
			for (State os : states)
				this.state |= os.id;
			if (is(State.Forbid) && is(State.Require))
				this.state &= (State.max - State.Require.id);
		}

		public StateProvider(StateProvider... states) {
			for (StateProvider s : states)
				state |= s.hashCode();
			if (is(State.Forbid) && is(State.Require))
				this.state &= (State.max - State.Require.id);
		}

		public boolean is(StateProvider... state) {
			for (StateProvider s : state)
				if (!is(s.hashCode()))
					return false;
			return true;
		}

		public boolean is(State... state) {
			for (State s : state)
				if (!is(s.id))
					return false;
			return true;
		}

		public boolean is(int... state) {
			for (int s : state)
				if (!((this.state & s) == s))
					return false;
			return true;

		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof StateProvider)
				return obj.hashCode() == hashCode();
			return false;
		}

		@Override
		public int hashCode() {
			return state;
		}

		public List<State> getStates() {
			List<State> states = new ArrayList<>();
			int id = 1;
			while (id <= State.max) {
				if (is(id))
					states.add(State.getState(id));
				id = id * 2;
			}
			return states;
		}

		@Override
		public String toString() {
			String result = "";
			for (State s : getStates())
				result += ", " + s;
			return result.isEmpty() ? "" : result.substring(2);
		}
	}

	/**
	 * All possible states of reasons
	 * @author Jevgenij Huebert
	 *
	 */
	public static enum State {
		None(0), Minimal(1), Require(2), Forbid(4), Dependency(8), Atom(16);
		public final int id;
		public static final int max = calculateMax();

		State(int id) {
			this.id = id;
		}

		private static int calculateMax() {
			int result = 0;
			for (State s : values())
				result += s.id;
			return result;
		}

		public static State getState(int id) {
			for (State s : values())
				if (s.id == id)
					return s;
			return None;
		}
	}

	/**
	 * Extends HashSet of proovable and is proovable
	 * @author Jevgenij Huebert
	 */
	public static class ConditionsSet extends HashSet<Provable> implements Provable {
		private static final long serialVersionUID = 1348579384573097L;
		private Set<Span> prooved = new HashSet<>();
		private Set<Provable> proovedd = new HashSet<>();

		public final boolean atoms;
		public final boolean minimals;
		public final boolean reasons;

		public ConditionsSet(Conditions... conditions) {
			this(true, true, true, conditions);
		}

		public ConditionsSet(boolean atoms, boolean minimals, boolean reasons, Conditions... conditions) {
			super();
			this.atoms = atoms;
			this.minimals = minimals;
			this.reasons = reasons;
			for (Conditions c : conditions)
				add(c);
		}

		/**
		 * Resets all proved conditions
		 */
		public void reset() {
			prooved.clear();
			proovedd.clear();
		}

		/**
		 * @return all provables that are not proved 
		 */
		public Set<Provable> getRest() {
			Set<Provable> result = new HashSet<>(this);
			result.removeAll(proovedd);
			return result;
		}

		@Override
		public boolean remove(Object o) {
			proovedd.remove(o);
			return super.remove(o);
		}

		@Override
		public boolean proove(Span span) {
			if (prooved.add(span))
				for (Provable p : this) {
					if (!proovedd.contains(p))
						if (p.proove(span)) {
							proovedd.add(p);
							return true;
						}
				}
			return false;
		}

		@Override
		public void clear() {
			super.clear();
			reset();
		}

		@Override
		public StateProvider getState() {
			return new StateProvider();
		}

	}

	/**
	 * Objects implements this interface can be proved by
	 * {@link org.eclipse.emf.henshin.multicda.cda.framework.CdaWorker CDAWorker}
	 * and {@link org.eclipse.emf.henshin.multicda.cda.framework.CpaWorker
	 * CPAWorker}
	 * 
	 * @author Jevgenij Huebert
	 *
	 */
	public static interface Provable {
		/**
		 * @param span
		 *            that should e proved
		 * @return true if the prove was successful
		 */
		<T extends Span> boolean proove(T span);

		StateProvider getState();
	}
}