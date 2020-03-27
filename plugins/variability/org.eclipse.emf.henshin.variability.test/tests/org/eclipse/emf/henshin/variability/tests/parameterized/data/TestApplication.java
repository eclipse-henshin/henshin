package org.eclipse.emf.henshin.variability.tests.parameterized.data;

import java.util.Collection;

/**
 * The specification of the rule applications
 * 
 * @author speldszus
 *
 */
public class TestApplication {
	/**
	 * The name of the rule that should be applied
	 */
	String rule;

	/**
	 * The location of the model to which the rule should be applied
	 */
	String model;

	/**
	 * Selections for a partial configuration
	 */
	Collection<TestSelection> partialConfiguration;

	/**
	 * The expected outcome of the rule application
	 */
	Collection<TestResult> results;

	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the partialConfiguration
	 */
	public Collection<TestSelection> getPartialConfiguration() {
		return partialConfiguration;
	}

	/**
	 * @return the results
	 */
	public Collection<TestResult> getResults() {
		return results;
	}
}