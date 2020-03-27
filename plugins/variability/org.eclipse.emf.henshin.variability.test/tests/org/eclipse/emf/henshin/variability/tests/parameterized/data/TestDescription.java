package org.eclipse.emf.henshin.variability.tests.parameterized.data;

import java.util.Collection;

/**
 * The test description as specified in a JSON file
 * 
 * @author speldszus
 *
 */
public class TestDescription {
	/**
	 * The location of the rule which should be applied
	 */
	String ruleFile;

	/**
	 * A specification of the rule applications
	 */
	Collection<TestApplication> applications;

	/**
	 * @return the ruleFile
	 */
	public String getRuleFile() {
		return ruleFile;
	}

	/**
	 * @return the applications
	 */
	public Collection<TestApplication> getApplications() {
		return applications;
	}

}