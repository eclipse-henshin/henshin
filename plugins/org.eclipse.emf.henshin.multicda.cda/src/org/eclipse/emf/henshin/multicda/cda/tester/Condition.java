package org.eclipse.emf.henshin.multicda.cda.tester;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Jevgenij Huebert 18.05.2018
 *
 * This Class is used by {@link org.eclipse.emf.henshin.multicda.cda.tester.CDATester CDATester} 
 * and {@link org.eclipse.emf.henshin.multicda.cda.tester.CPATester CPATester} to check result of Conflict and Dependency Analysis.
 */
public class Condition {
	protected Object[] values;

	protected final String name;

	public Condition(Object... values) {
		this.name = "Condition";
		this.values = values;
	}

	protected Condition(String name, Object... values) {
		this.name = name;
		this.values = values;
	}

	public boolean proove(Object... elements) {
		if (elements.length != values.length)
			assertTrue("Not the same number of conditions: conditions=" + values + " != elements=" + elements.length,
					values.length == elements.length);
		for (int i = 0; i < values.length; i++)
			if (!elements[i].equals(values[i]))
				return false;
		return true;
	}

	/**
	 * @return the values
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * 
	 * @author Jevgenij Huebert 18.05.2018
	 * Represents a Node for checking Result of a Tester
	 */
	public static class Node extends Condition {
		public Node(int a) {
			this(a + "");
		}

		public Node(String a) {
			super("Node", a);
		}

		@Override
		public boolean proove(Object... elements) {
			for (Object element : elements) {
				if (!(element instanceof org.eclipse.emf.henshin.model.Node))
					return false;
				if (!((org.eclipse.emf.henshin.model.Node) element).getName().equals(values[0]))
					return false;
			}
			return true;
		}
	}

	/**
	 * 
	 * @author Jevgenij Huebert 18.05.2018
	 * Represents an Edge for checking Result of a Tester
	 */
	public static class Edge extends Condition {
		public Edge(int a, int b) {
			this(a + "", b + "");
		}

		public Edge(String a, String b) {
			super("Edge", a, b);
		}

		@Override
		public boolean proove(Object... elements) {
			for (Object element : elements) {
				if (!(element instanceof org.eclipse.emf.henshin.model.Edge))
					return false;
				if (!((org.eclipse.emf.henshin.model.Edge) element).getSource().getName().equals(values[0])
						|| !((org.eclipse.emf.henshin.model.Edge) element).getTarget().getName().equals(values[1]))
					return false;
			}
			return true;
		}
	}
	/**
	 * @author Jevgenij Huebert 18.05.2018
	 * Container for specific Conditions. For each kind of Reason and Critical Pair there is a Conditions kind. 
	 */
	public static class Conditions extends Condition {

		public Conditions(Condition... conditions) {
			super("", conditions);
		}

		protected Conditions(String name, Condition... conditions) {
			super(name, conditions);
		}

		@Override
		public Condition[] getValues() {
			return (Condition[]) super.getValues();
		}

		@Override
		public String toString() {
			String result = "";
			for (Object condition : values)
				if (condition instanceof Condition)
					result += ", " + condition;
				else {
					result = ", ERROR!";
					break;
				}
			return getClass().getSimpleName() + ": " + result.substring(2);
		}
	}

	public static class ConflictReasonConditions extends Conditions {
		public ConflictReasonConditions(Condition... conditions) {
			super("CR", conditions);
		}
	}

	public static class MinimalReasonConditions extends Conditions {
		public MinimalReasonConditions(Condition... conditions) {
			super("MCR", conditions);
		}
	}

	public static class CriticalPairConditions extends Conditions {
		public CriticalPairConditions(Condition... conditions) {
			super("CP", conditions);
		}
	}

	public static class CriticalPairRightConditions extends Conditions {
		public CriticalPairRightConditions(Condition... conditions) {
			super("CP right", conditions);
		}
	}

	//__________________new conditions _______________________
	public static class DUCRConditions extends Conditions {
		public DUCRConditions(Condition... conditions) {
			super("DUCR", conditions);
		}
	}

	public static class DRCRConditions extends Conditions {
		public DRCRConditions(Condition... conditions) {
			super("DRCR", conditions);
		}
	}

	public static class DDCRConditions extends Conditions {
		public DDCRConditions(Condition... conditions) {
			super("DDCR", conditions);
		}
	}

	public static class CUDRConditions extends Conditions {
		public CUDRConditions(Condition... conditions) {
			super("CUDR", conditions);
		}
	}

	public static class CRDRConditions extends Conditions {
		public CRDRConditions(Condition... conditions) {
			super("CRDR", conditions);
		}
	}

	public static class CDDRConditions extends Conditions {
		public CDDRConditions(Condition... conditions) {
			super("CDDR", conditions);
		}
	}
	//__________________NACs Conditions_____________________

	public static class CFCRConditions extends Conditions {
		public CFCRConditions(Condition... conditions) {
			super("CFCR", conditions);
		}
	}

	public static class DFDRConditions extends Conditions {
		public DFDRConditions(Condition... conditions) {
			super("DFDR", conditions);
		}
	}
	//__________________PACs Conditions_____________________

	public static class DReqCRConditions extends Conditions {
		public DReqCRConditions(Condition... conditions) {
			super("DReqCR", conditions);
		}
	}

	public static class CReqDRConditions extends Conditions {
		public CReqDRConditions(Condition... conditions) {
			super("CReqDR", conditions);
		}
	}

	public static class ConditionsSet implements Iterable<Condition> {
		private Condition[] conditions;

		public ConditionsSet(Condition... conditions) {
			this.conditions = conditions;
		}

		public Condition[] getConditions() {
			return conditions;
		}

		@Override
		public String toString() {
			String result = "";
			for (Condition condition : conditions)
				result += "\n" + condition;
			return result.isEmpty() ? "[]" : "[" + result.substring(1) + "]";
		}

		/* (non-Javadoc)
		 * @see java.lang.Iterable#iterator()
		 */
		@Override
		public Iterator<Condition> iterator() {
			return Arrays.asList(conditions).iterator();
		}
	}

	private static abstract class ConflictSize extends Condition {
		public ConflictSize(String name, String value) {
			super(name, value);
		}

		@Override
		public boolean proove(Object... elements) {
			for (Object element : elements) {
				assertTrue(
						"Element type of \"" + element + "\" is not Integer but: " + element.getClass().getSimpleName(),
						element instanceof Integer);
				if (!(element + "").equals(values[0]))
					return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return (Integer.parseInt(values[0] + "") != 1 ? name : name.substring(0, name.length() - 1)) + ": "
					+ values[0];
		}
	}

	public static class MCR extends ConflictSize {
		public MCR(int value) {
			this(value + "");
		}

		public MCR(String value) {
			super("Minimal Conflict Reasons", value);
		}
	}

	public static class DUCR extends ConflictSize {
		public DUCR(int value) {
			this(value + "");
		}

		public DUCR(String value) {
			super("Delete Use Conflict Reasons", value);
		}
	}

	public static class DRCR extends ConflictSize {
		public DRCR(int value) {
			this(value + "");
		}

		public DRCR(String value) {
			super("Delete Read Conflict Reasons", value);
		}
	}

	public static class DDCR extends ConflictSize {
		public DDCR(int value) {
			this(value + "");
		}

		public DDCR(String value) {
			super("Delete Delete Conflict Reasons", value);
		}
	}

	public static class CUDR extends ConflictSize {
		public CUDR(int value) {
			this(value + "");
		}

		public CUDR(String value) {
			super("Create Use Dependency Reasons", value);
		}
	}

	public static class CRDR extends ConflictSize {
		public CRDR(int value) {
			this(value + "");
		}

		public CRDR(String value) {
			super("Create Read Dependency Reasons", value);
		}
	}

	public static class CDDR extends ConflictSize {
		public CDDR(int value) {
			this(value + "");
		}

		public CDDR(String value) {
			super("Create Delete Dependency Reasons", value);
		}
	}
	//______________PACs and NACs_________________

	public static class CFCR extends ConflictSize {
		public CFCR(int value) {
			this(value + "");
		}

		public CFCR(String value) {
			super("Create Forbid Conflit Reasons", value);
		}
	}

	public static class DFDR extends ConflictSize {
		public DFDR(int value) {
			this(value + "");
		}

		public DFDR(String value) {
			super("Delete Forbid Dependency Reasons", value);
		}
	}

	public static class DReqCR extends ConflictSize {
		public DReqCR(int value) {
			this(value + "");
		}

		public DReqCR(String value) {
			super("Delete Require Conflit Reasons", value);
		}
	}

	public static class CReqDR extends ConflictSize {
		public CReqDR(int value) {
			this(value + "");
		}

		public CReqDR(String value) {
			super("Create Forbid Dependency Reasons", value);
		}
	}

	public static class CP extends ConflictSize {
		public CP(int value) {
			this(value + "");
		}

		public CP(String value) {
			super("Critical Pairs", value);
		}
	}

	public static class ICP extends ConflictSize {
		public ICP(int value) {
			this(value + "");
		}

		public ICP(String value) {
			super("Initial Critical Pairs", value);
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (Object string : values)
			result += ", " + string;
		return name + ":" + " (" + result.substring(2) + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Condition && ((Condition) obj).name.equals(name)
				&& ((Condition) obj).values.length == values.length) {
			for (int i = 0; i < values.length; i++)
				if (!values[i].equals(((Condition) obj).values[i]))
					return false;
			return true;
		}
		return false;
	}
}