/**
 */
package configuration.impl;

import configuration.Configuration;
import configuration.ConfigurationFactory;
import configuration.ConfigurationPackage;
import configuration.Feature;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityConstants;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityTransactionHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link configuration.impl.ConfigurationImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link configuration.impl.ConfigurationImpl#getFeatures <em>Features</em>}</li>
 * </ul>
 *
 * @generated
 * @author Stefan Schulz
 * 
 */
public class ConfigurationImpl extends MinimalEObjectImpl.Container implements Configuration {
	
	/**
	 * Provides a helper class to notify configurations about changes in the targeted {@link Rule}.
	 * 
	 * @author Stefan Schulz
	 *
	 */
	private final class EFeatureModelContentAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(Notification notification) {
			Object notifier = notification.getNotifier();
			if (notifier instanceof Annotation && ((Annotation) notifier).eContainer() instanceof Rule) {
				if (((Annotation) notifier).getKey().equals(VariabilityConstants.FEATURES)) {
					updateAllFeatures();
				}
			}
		}
	}

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	protected EFeatureModelContentAdapter featuresContentAdapter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (Rule)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.CONFIGURATION__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule() {
		return rule;
	}

	private EContentAdapter createContentAdapter() {
		return new EFeatureModelContentAdapter();
	}
	
	private void disableContentAdapter() {
		rule.eResource().getResourceSet().eAdapters().remove(featuresContentAdapter);
	}
	
	private void enableContentAdapter() {
		rule.eResource().getResourceSet().eAdapters().add(featuresContentAdapter);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRule(Rule newRule) {
		Rule oldRule = rule;
		if (featuresContentAdapter != null) {
			oldRule.eResource().getResourceSet().eAdapters().remove(featuresContentAdapter);
		}
		
		rule = newRule;
		rule.eResource().getResourceSet().eAdapters().add(createContentAdapter());
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.CONFIGURATION__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getFeatures() {
		if (features == null) {
			features = new EObjectResolvingEList<Feature>(Feature.class, this, ConfigurationPackage.CONFIGURATION__FEATURES);
		}
		return features;
	}
	
	private void updateAllFeatures() {
		List<String> annotationFeatures = VariabilityFactory.createVariabilityRule(rule).getFeatures();
		EList<Feature> oldFeatures = getFeatures();
		features.clear();
		for (String featureName : annotationFeatures) {
			Feature feature = ConfigurationFactory.eINSTANCE.createFeature();
			feature.setName(featureName);
			features.add(feature);
		}
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.CONFIGURATION__FEATURES, oldFeatures, features));
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public boolean addFeature(Feature feature) {
		disableContentAdapter();
		List<String> annotationFeatures = VariabilityFactory.createVariabilityRule(rule).getFeatures();
		String featureAnnotationValue = "";
		if (annotationFeatures != null && !annotationFeatures.isEmpty()) {
			featureAnnotationValue = String.join(", ", annotationFeatures);
			featureAnnotationValue += ", " + feature.getName();
		} else {
			featureAnnotationValue += feature.getName();
		}
		VariabilityTransactionHelper.setAnnotationValue(rule, VariabilityConstants.FEATURES, featureAnnotationValue);
		boolean result = features.add(feature);
		try {
			enableContentAdapter();			
		} catch (IllegalArgumentException e) {} //TODO: This throws an IllegalArgumentException if the added feature was the first one in the rule. Fix this.
		return result;
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public boolean removeFeature(String featureName) {
		Feature featureToRemove = null;
		for (Feature feature : features) {
			if (featureName.equals(feature.getName())) {
				featureToRemove = feature;
				break;
			}
		}
		return removeFeature(featureToRemove);
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public boolean removeFeature(final Feature feature) {
		disableContentAdapter();
		if (feature != null) {
			List<String> annotationFeatures = VariabilityFactory.createVariabilityRule(rule).getFeatures();
			String featureAnnotationValue = "";
			for (String annotationFeature : annotationFeatures) {
				if (!annotationFeature.equals(feature.getName())) {					
					featureAnnotationValue += annotationFeature + ", ";
				}
			}
			featureAnnotationValue = featureAnnotationValue.substring(0, featureAnnotationValue.length() - 2);
			VariabilityTransactionHelper.setAnnotationValue(rule, VariabilityConstants.FEATURES, featureAnnotationValue);
			enableContentAdapter();
			return features.remove(feature);
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.CONFIGURATION__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case ConfigurationPackage.CONFIGURATION__FEATURES:
				return getFeatures();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigurationPackage.CONFIGURATION__RULE:
				setRule((Rule)newValue);
				return;
			case ConfigurationPackage.CONFIGURATION__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends Feature>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.CONFIGURATION__RULE:
				setRule((Rule)null);
				return;
			case ConfigurationPackage.CONFIGURATION__FEATURES:
				getFeatures().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.CONFIGURATION__RULE:
				return rule != null;
			case ConfigurationPackage.CONFIGURATION__FEATURES:
				return features != null && !features.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ConfigurationImpl
