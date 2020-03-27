package org.eclipse.emf.henshin.variability.tests.parameterized.data;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;

/**
 * A data class for storing loaded test data
 * 
 * @author speldszus
 *
 */
public class VBTestData {

	/**
	 * The rule to be applied
	 */
	private final Rule rule;

	/**
	 * The resource to which the rule should be applied
	 */
	private final Resource resource;

	/**
	 * A (partial) feature configuration
	 */
	private final Map<String, Boolean> configuration;

	/**
	 * The expected outcomes of the rule application
	 */
	private final Collection<TestResult> expect;

	private final String description;

	/**
	 * Creates a new instance
	 * 
	 * @param description   A short description of the test
	 * @param rule          The rule
	 * @param configuration A (partial) feature configuration
	 * @param resource      The model
	 * @param expect        The expected outcome
	 */
	public VBTestData(String description, Rule rule, Map<String, Boolean> configuration, Resource resource,
			Collection<TestResult> expect) {
		this.description = description;
		this.rule = rule;
		this.resource = resource;
		this.expect = expect;
		this.configuration = configuration;
	}

	/**
	 * Returns a human readable description of the test case
	 */
	@Override
	public String toString() {
		return description + ": Apply the rule \"" + getRule().getName() + "\" to the model \""
				+ getResource().getURI().lastSegment() + "\", configuration: {" + configuration.entrySet().stream()
						.map(e -> e.getKey() + '=' + e.getValue()).collect(Collectors.joining(", "))
				+ "}.";
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	public Map<String, Boolean> getConfiguration() {
		return configuration;
	}

	/**
	 * @return the expect
	 */
	public Collection<TestResult> getExpect() {
		return expect;
	}
}