package org.eclipse.emf.henshin.variability.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.variability.matcher.FeatureExpression;

import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.SymbolCollector;

/**
 * This class wraps an instance of {@link org.eclipse.emf.henshin.model.Rule}
 * and adds variability awareness to it. The variability awareness is enabled by
 * adding multiple {@link org.eclipse.emf.henshin.model.Annotation}s to the
 * wrapped object.
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityRule extends EObjectImpl implements Rule {
	final Rule rule;
	final Annotation featureConstraint;
	final Annotation injectiveMatchingPresenceCondition;
	final Annotation features;
	
	static Annotation[] addVariabilityToRule(Rule rule, boolean transactional) {
		Annotation[] result = new Annotation[3];
		Annotation featModel = null;
		Annotation injMatPreCon = null;
		Annotation feats = null;
		EList<Annotation> annos = rule.getAnnotations();
		Iterator<Annotation> it = annos.iterator();

		while (it.hasNext()) {
			Annotation anno = it.next();
			String key = anno.getKey();
			if (key.equals(VariabilityConstants.FEATURE_CONSTRAINT)) {
				featModel = anno;
			} else if (key.equals(VariabilityConstants.INJECTIVE_MATCHING_PC)) {
				injMatPreCon = anno;
			} else if (key.equals(VariabilityConstants.FEATURES)) {
				feats = anno;
			}
			if (featModel != null && injMatPreCon != null && feats != null) {
				break;
			}
		}
		
		if(featModel != null) {
			result[0] = featModel;
		} else if (transactional) {
			result[0] = VariabilityTransactionHelper.addAnnotation(rule, VariabilityConstants.FEATURE_CONSTRAINT, "");
		} else {
			result[0] = VariabilityHelper.addAnnotation(rule, VariabilityConstants.FEATURE_CONSTRAINT, "");
		}
		
		if(injMatPreCon != null) {
			result[1] = injMatPreCon;
		} else if (transactional) {
			result[1] = VariabilityTransactionHelper.addAnnotation(rule, VariabilityConstants.INJECTIVE_MATCHING_PC, "");
		} else {
			result[1] = VariabilityHelper.addAnnotation(rule, VariabilityConstants.INJECTIVE_MATCHING_PC, "");
		}
		
		if(feats != null) {
			result[2] = feats;
		} else if (transactional) {
			result[2] = VariabilityTransactionHelper.addAnnotation(rule, VariabilityConstants.FEATURES, "");
		} else {
			result[2] = VariabilityHelper.addAnnotation(rule, VariabilityConstants.FEATURES, "");
		}
		
		return result;
	}
	
	/**
	 * Returns the original {@link org.eclipse.emf.henshin.model.Rule}.
	 * @return the rule.
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Rule} and makes it variability aware.
	 */
	VariabilityRule() {
		this(HenshinFactory.eINSTANCE.createRule());
	}
	
	/**
	 * Creates a new {@link org.eclipse.emf.henshin.model.Rule} with the given name and makes it variability aware.
	 * @param name the name of the new henshin rule
	 */
	VariabilityRule(String name) {
		this(HenshinFactory.eINSTANCE.createRule(name));
	}
	
	/**
	 * Adds multiple {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Rule} in order to enable variability awareness.
	 * @param rule
	 */
	VariabilityRule(Rule rule) {
		this(rule, false);
	}
	
	/**
	 * Adds multiple {@link org.eclipse.emf.henshin.model.Annotation} to the given {@link org.eclipse.emf.henshin.model.Rule} in order to enable variability awareness.
	 * @param rule
	 * @param transactional
	 */
	VariabilityRule(Rule rule, boolean transactional) {
		this.rule = rule;
		Annotation[] annos = addVariabilityToRule(rule, transactional);
		featureConstraint = annos[0];
		injectiveMatchingPresenceCondition = annos[1];
		features = annos[2];
	}

	/**
	 * Returns the feature model of this Rule.
	 * 
	 * @return the feature model of this Rule.
	 */
	public String getFeatureConstraint() {
		return featureConstraint.getValue();
	}

	/**
	 * Sets this Rule's feature model to the given model.
	 * 
	 * @param featureConstraintString the feature model to be set for this Rule.
	 */
	public void setFeatureConstraint(String featureConstraintString) {
		featureConstraint.setValue(featureConstraintString);
		// TODO: Update list of features
	}

	/**
	 * Returns the injective matching presence condition of this Rule.
	 * 
	 * @return the injective matching presence condition of this Rule.
	 */
	public String getInjectiveMatchingPresenceCondition() {
		return injectiveMatchingPresenceCondition.getValue();
	}

	/**
	 * Returns the injective matching presence condition for this Rule.
	 * 
	 * @param condition the injective matching presence condition to be set for this
	 *                  Rule.
	 */
	public void setInjectiveMatchingPresenceCondition(String condition) {
		injectiveMatchingPresenceCondition.setValue(condition);
	}

	/**
	 * Returns an unmodifiable list of this Rule's features.
	 * 
	 * @return an {@link java.util.List} containing this Rule's variability points.
	 */
	public List<String> getFeatures() {
		if (features.getValue() == null) {
			return null;
		}

		if (!features.getValue().isEmpty()) {
			List<String> featureList = Arrays.asList(features.getValue().split(","));
			List<String> result = new ArrayList<String>();
			for (String string : featureList) {
				result.add(string.trim());
			}
			return Collections.unmodifiableList(result);
		} else {
			return Collections.unmodifiableList(new ArrayList<String>());
		}
	}

	/**
	 * Sets the features of this Rule.
	 * 
	 * @param featureString the string containing all features
	 */
	public void setFeatures(String featureString) {
		features.setValue(featureString);
	}

	/**
	 * Sets the features of this Rule.
	 * 
	 * @param featureList the list containing all features
	 */
	public void setFeatures(List<String> featureList) {
		for (String feature : featureList) {
			addFeature(feature);
		}
	}

	/**
	 * Adds the given feature to this Rule.
	 * 
	 * @param feature the variability point to be added to this
	 */
	public void addFeature(String feature) {
		if (features.getValue() == null) {
			features.setValue("");
		}

		String featuresString = features.getValue();
		featuresString += (featuresString.length() > 0) ? "," + features : features;
		features.setValue(featuresString);
	}

	/**
	 * Removes the given feature if it is a part of this Rule.
	 * 
	 * @param variabilityPoint the variability point to be removed.
	 */
	public void removeFeature(String feature) {
		if (features.getValue() == null) {
			return;
		}

		List<String> featureList = new ArrayList<String>(Arrays.asList(features.getValue().split(",")));
		featureList.remove(feature);
		features.setValue(String.join(",", featureList));
	}

	String oldFeatureConstraint = "";
	String oldFeatures = "";
	List<String> missingFeatures = new ArrayList<String>();;

	public boolean hasMissingFeatures() {
		calculateMissingFeatureNames();
		return missingFeatures == null || !missingFeatures.isEmpty();
	}

	public String[] getMissingFeatures() {
		calculateMissingFeatureNames();
		return missingFeatures.toArray(new String[0]);
	}

	private void calculateMissingFeatureNames() {
		String currentModel = getFeatureConstraint();
		if (!currentModel.trim().equals(oldFeatureConstraint) || !oldFeatures.equals(features.getValue())) {
			Sentence sentence = FeatureExpression.getExpr(currentModel);
			missingFeatures.clear();
			Set<PropositionSymbol> symbols = SymbolCollector.getSymbolsFrom(sentence);
			List<String> definedFeatures = getFeatures();
			for (PropositionSymbol symbol : symbols) {
				String symbolName = symbol.getSymbol();
				if (!definedFeatures.contains(symbolName)) {
					missingFeatures.add(symbolName);
				}
			}
			oldFeatureConstraint = currentModel.trim();
			oldFeatures = features.getValue();
		}
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#canCreateEdge(org.eclipse.emf.henshin.model.Node,
	 *      org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EReference)
	 */
	public boolean canCreateEdge(Node arg0, Node arg1, EReference arg2) {
		return rule.canCreateEdge(arg0, arg1, arg2);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#createEdge(org.eclipse.emf.henshin.model.Node,
	 *      org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EReference)
	 */
	public Edge createEdge(Node arg0, Node arg1, EReference arg2) {
		return rule.createEdge(arg0, arg1, arg2);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#createNode(org.eclipse.emf.ecore.EClass)
	 */
	public Node createNode(EClass arg0) {
		return rule.createNode(arg0);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eAdapters()
	 */
	public EList<Adapter> eAdapters() {
		return rule.eAdapters();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
	 */
	public boolean eDeliver() {
		return rule.eDeliver();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
	 */
	public void eSetDeliver(boolean deliver) {
		rule.eSetDeliver(deliver);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
	 */
	public void eNotify(Notification notification) {
		rule.eNotify(notification);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eClass()
	 */
	public EClass eClass() {
		return rule.eClass();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	public Resource eResource() {
		return rule.eResource();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainer()
	 */
	public EObject eContainer() {
		return rule.eContainer();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
	 */
	public EStructuralFeature eContainingFeature() {
		return rule.eContainingFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
	 */
	public EReference eContainmentFeature() {
		return rule.eContainmentFeature();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eContents()
	 */
	public EList<EObject> eContents() {
		return rule.eContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eAllContents()
	 */
	public TreeIterator<EObject> eAllContents() {
		return rule.eAllContents();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsProxy()
	 */
	public boolean eIsProxy() {
		return rule.eIsProxy();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
	 */
	public EList<EObject> eCrossReferences() {
		return rule.eCrossReferences();
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Object eGet(EStructuralFeature feature) {
		return rule.eGet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature,
	 *      boolean)
	 */
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return rule.eGet(feature, resolve);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature,
	 *      java.lang.Object)
	 */
	public void eSet(EStructuralFeature feature, Object newValue) {
		rule.eSet(feature, newValue);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean eIsSet(EStructuralFeature feature) {
		return rule.eIsSet(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void eUnset(EStructuralFeature feature) {
		rule.eUnset(feature);
	}

	/**
	 * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation,
	 *      org.eclipse.emf.common.util.EList)
	 */
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return rule.eInvoke(operation, arguments);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getActionEdges(org.eclipse.emf.henshin.model.Action)
	 */
	public EList<Edge> getActionEdges(Action arg0) {
		return rule.getActionEdges(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getActionNodes(org.eclipse.emf.henshin.model.Action)
	 */
	public EList<Node> getActionNodes(Action arg0) {
		return rule.getActionNodes(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getAllJavaImports()
	 */
	public EList<String> getAllJavaImports() {
		return rule.getAllJavaImports();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getAllMappings()
	 */
	public MappingList getAllMappings() {
		return rule.getAllMappings();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getAllMultiRules()
	 */
	public EList<Rule> getAllMultiRules() {
		return rule.getAllMultiRules();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.ModelElement#getAnnotations()
	 */
	public EList<Annotation> getAnnotations() {
		return rule.getAnnotations();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getAttributeConditions()
	 */
	public EList<AttributeCondition> getAttributeConditions() {
		return rule.getAttributeConditions();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#getDescription()
	 */
	public String getDescription() {
		return rule.getDescription();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getJavaImports()
	 */
	public EList<String> getJavaImports() {
		return rule.getJavaImports();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getKernelRule()
	 */
	public Rule getKernelRule() {
		return rule.getKernelRule();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getLhs()
	 */
	public Graph getLhs() {
		return rule.getLhs();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getMappings()
	 */
	public MappingList getMappings() {
		return rule.getMappings();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#getModule()
	 */
	public Module getModule() {
		return rule.getModule();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiMappings()
	 */
	public MappingList getMultiMappings() {
		return rule.getMultiMappings();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiRule(java.lang.String)
	 */
	public Rule getMultiRule(String arg0) {
		return rule.getMultiRule(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiRulePath(org.eclipse.emf.henshin.model.Rule)
	 */
	public EList<Rule> getMultiRulePath(Rule arg0) {
		return rule.getMultiRulePath(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getMultiRules()
	 */
	public EList<Rule> getMultiRules() {
		return rule.getMultiRules();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#getName()
	 */
	public String getName() {
		return rule.getName();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#getParameter(java.lang.String)
	 */
	public Parameter getParameter(String arg0) {
		return rule.getParameter(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#getParameterMappings()
	 */
	public EList<ParameterMapping> getParameterMappings() {
		return rule.getParameterMappings();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getParameterNodes()
	 */
	public EList<Node> getParameterNodes() {
		return rule.getParameterNodes();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#getParameters()
	 */
	public EList<Parameter> getParameters() {
		return rule.getParameters();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getRhs()
	 */
	public Graph getRhs() {
		return rule.getRhs();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#getRootRule()
	 */
	public Rule getRootRule() {
		return rule.getRootRule();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#getSubUnits(boolean)
	 */
	public EList<Unit> getSubUnits(boolean arg0) {
		return rule.getSubUnits(arg0);
	}

	/**
	 * @return
	 * @see org.eclipse.emf.henshin.model.Unit#isActivated()
	 */
	public boolean isActivated() {
		return rule.isActivated();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#isCheckDangling()
	 */
	public boolean isCheckDangling() {
		return rule.isCheckDangling();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#isInjectiveMatching()
	 */
	public boolean isInjectiveMatching() {
		return rule.isInjectiveMatching();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#isMultiRule()
	 */
	public boolean isMultiRule() {
		return rule.isMultiRule();
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#removeAttribute(org.eclipse.emf.henshin.model.Attribute,
	 *      boolean)
	 */
	public boolean removeAttribute(Attribute arg0, boolean arg1) {
		return rule.removeAttribute(arg0, arg1);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#removeEdge(org.eclipse.emf.henshin.model.Edge,
	 *      boolean)
	 */
	public boolean removeEdge(Edge arg0, boolean arg1) {
		return rule.removeEdge(arg0, arg1);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#removeNode(org.eclipse.emf.henshin.model.Node,
	 *      boolean)
	 */
	public boolean removeNode(Node arg0, boolean arg1) {
		return rule.removeNode(arg0, arg1);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Unit#setActivated(boolean)
	 */
	public void setActivated(boolean arg0) {
		rule.setActivated(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#setCheckDangling(boolean)
	 */
	public void setCheckDangling(boolean arg0) {
		rule.setCheckDangling(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#setDescription(java.lang.String)
	 */
	public void setDescription(String arg0) {
		rule.setDescription(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#setInjectiveMatching(boolean)
	 */
	public void setInjectiveMatching(boolean arg0) {
		rule.setInjectiveMatching(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#setLhs(org.eclipse.emf.henshin.model.Graph)
	 */
	public void setLhs(Graph arg0) {
		rule.setLhs(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.NamedElement#setName(java.lang.String)
	 */
	public void setName(String arg0) {
		rule.setName(arg0);
	}

	/**
	 * @see org.eclipse.emf.henshin.model.Rule#setRhs(org.eclipse.emf.henshin.model.Graph)
	 */
	public void setRhs(Graph arg0) {
		rule.setRhs(arg0);
	}

	@Override
	public int hashCode() {
		return rule.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return rule.equals(obj);
	}
}
