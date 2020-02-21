/**
 */
package configuration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link configuration.Configuration#getRule <em>Rule</em>}</li>
 *   <li>{@link configuration.Configuration#getFeatures <em>Features</em>}</li>
 * </ul>
 *
 * @see configuration.ConfigurationPackage#getConfiguration()
 * @model
 * @generated
 * @author Stefan Schulz
 */
public interface Configuration extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(Rule)
	 * @see configuration.ConfigurationPackage#getConfiguration_Rule()
	 * @model required="true"
	 * @generated
	 */
	VariabilityRule getRule();

	/**
	 * Sets the value of the '{@link configuration.Configuration#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated NOT
	 */
	void setRule(Rule value);
	
	/**
	 * Sets the value of the '{@link configuration.Configuration#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated NOT
	 */
	void setRule(VariabilityRule value);
	
	/**
	 * Returns the value of the '<em><b>Features</b></em>' reference list.
	 * The list contents are of type {@link configuration.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' reference list.
	 * @see configuration.ConfigurationPackage#getConfiguration_Features()
	 * @model
	 * @generated
	 */
	EList<Feature> getFeatures();
	
	/**
	 * @generated NOT
	 */
	boolean addFeature(Feature feature);
	
	/**
	 * @generated NOT
	 */
	boolean removeFeature(String featureName);
	
	/**
	 * @generated NOT
	 */
	boolean removeFeature(Feature feature);

} // Configuration
