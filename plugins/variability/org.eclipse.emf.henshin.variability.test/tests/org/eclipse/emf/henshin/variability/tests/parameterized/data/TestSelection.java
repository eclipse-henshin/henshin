package org.eclipse.emf.henshin.variability.tests.parameterized.data;

/**
 * This class allows to select or deselect features
 * 
 * @author speldszus
 *
 */
public class TestSelection {

	/**
	 * The name of a feature
	 */
	String feature;

	/**
	 * If the feature is selected or not
	 */
	boolean selection;

	public String getFeature() {
		return feature;
	}

	public boolean getSelection() {
		return selection;
	}
}