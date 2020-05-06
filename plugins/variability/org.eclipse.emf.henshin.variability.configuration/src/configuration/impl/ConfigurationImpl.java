/**
 */
package configuration.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityConstants;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityRule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityTransactionHelper;

import configuration.Configuration;
import configuration.ConfigurationFactory;
import configuration.ConfigurationPackage;
import configuration.Feature;
import configuration.FeatureBinding;

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
	private final class EFeatureConstraintContentAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
				return;
			}
			
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
	 * @generated NOT
	 * @ordered
	 */
	protected VariabilityRule rule;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	protected EFeatureConstraintContentAdapter featuresContentAdapter;

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
	 * @generated NOT
	 */
	@Override
	public VariabilityRule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (VariabilityRule)eResolveProxy(oldRule);
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
	
	private void disableContentAdapter() {
		rule.eResource().getResourceSet().eAdapters().remove(featuresContentAdapter);
	}
	
	private void enableContentAdapter() {
		if (featuresContentAdapter == null) {
			featuresContentAdapter = new EFeatureConstraintContentAdapter();
		}
		rule.eResource().getResourceSet().eAdapters().add(featuresContentAdapter);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setRule(Rule newRule) {
		setRule(VariabilityFactory.INSTANCE.createVariabilityRule(newRule));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setRule(VariabilityRule newRule) {
		Rule oldRule = rule;
		if (featuresContentAdapter != null) {
			oldRule.eResource().getResourceSet().eAdapters().remove(featuresContentAdapter);
		} else {
			featuresContentAdapter = new EFeatureConstraintContentAdapter();
		}
		rule = newRule;
		rule.eResource().getResourceSet().eAdapters().add(featuresContentAdapter);
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
		Map<String, FeatureBinding> bindings = getBindings();
		List<String> annotationFeatures = VariabilityFactory.INSTANCE.createVariabilityRule(rule).getFeatures();
		EList<Feature> oldFeatures = getFeatures();
		features.clear();
		for (String featureName : annotationFeatures) {
			Feature feature = ConfigurationFactory.eINSTANCE.createFeature();
			feature.setName(featureName);
			features.add(feature);
		}
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.CONFIGURATION__FEATURES, oldFeatures, features));
		applyBindings(bindings);
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public boolean addFeature(Feature feature) {
		disableContentAdapter();
		List<String> annotationFeatures = VariabilityFactory.INSTANCE.createVariabilityRule(rule).getFeatures();
		String featureAnnotationValue = "";
		if (annotationFeatures != null && !annotationFeatures.isEmpty()) {
			featureAnnotationValue = String.join(", ", annotationFeatures);
			featureAnnotationValue += ", " + feature.getName();
		} else {
			featureAnnotationValue += feature.getName();
		}
		VariabilityTransactionHelper.setAnnotationValue(rule, VariabilityConstants.FEATURES, featureAnnotationValue);
		enableContentAdapter();
		return features.add(feature);
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
			List<String> annotationFeatures = VariabilityFactory.INSTANCE.createVariabilityRule(rule).getFeatures();
			String featureAnnotationValue = "";
			for (String annotationFeature : annotationFeatures) {
				if (!annotationFeature.equals(feature.getName())) {					
					featureAnnotationValue += annotationFeature + ", ";
				}
			}
			if (annotationFeatures.size() > 1) {
				featureAnnotationValue = featureAnnotationValue.substring(0, featureAnnotationValue.length() - 2);
			}
			VariabilityTransactionHelper.setAnnotationValue(rule, VariabilityConstants.FEATURES, featureAnnotationValue);
			enableContentAdapter();
			return features.remove(feature);
		} else {
			return false;
		}
	}
	
	private Map<String, FeatureBinding> getBindings() {
		Map<String, FeatureBinding> result = new HashMap<String, FeatureBinding>();
		for (Feature feature : features) {
			result.put(feature.getName(), feature.getBinding());
		}
		return result;
	}
	
	private void applyBindings(Map<String, FeatureBinding> bindings) {
		for (Feature feature : features) {
			if (bindings.containsKey(feature.getName())) {
				feature.setBinding(bindings.get(feature.getName()));
			}
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
