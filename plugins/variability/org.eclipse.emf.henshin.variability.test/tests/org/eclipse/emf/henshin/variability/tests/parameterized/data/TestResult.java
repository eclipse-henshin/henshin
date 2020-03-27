package org.eclipse.emf.henshin.variability.tests.parameterized.data;

/**
 * The specification of the outcome of a rule application
 * 
 * @author speldszus
 *
 */
public class TestResult {
	/**
	 * The constraint kind to check
	 */
	private TestResultKind kind;

	/**
	 * The expected value
	 */
	private Object value;
	
	public TestResultKind getKind() {
		return kind;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
}