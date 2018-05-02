package org.eclipse.emf.henshin.multicda.cda.runner.pullback;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class CpaOnFeatureModelRunnerWithoutNACsWithoutUpperLimitOnReferencesNoAttrChangeAllMultiRules_Pullback extends Runner_WithPullback{
	
	// Relative path to the transformations.
//	static String TRANSFORMATIONS = "transformations/"; //überflüssig
	
//	private static Engine engine;// = new EngineImpl(); //überflüssig
	
//	private static Module module; //überflüssig
	
//	private static HenshinResourceSet henshinResourceSet; // überflüssig

	public static void main(String args[]){
		System.out.println("test");
		
		
//		FeatureModelPackage.eINSTANCE.eClass();

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		ResourceSetImpl resourceSet = new ResourceSetImpl();

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());
		
		List<String> activatedRules = new LinkedList<String>();
		
		activatedRules.add("addToGroup_features_Feature"); activatedRules.add("createConcreteFeature_IN_Feature"); activatedRules.add("createGroup_IN_FeatureModel"); activatedRules.add("deleteConcreteFeature_IN_Feature"); activatedRules.add("moveFeature_FROM_Feature_children_TO_Feature_Feature"); activatedRules.add("removeFromGroup_features_Feature"); activatedRules.add("addToGroup_features_Feature"); activatedRules.add("changeGroupFromALTtoOR"); activatedRules.add("createConcreteFeature_IN_Feature"); activatedRules.add("deleteExcludeConstraint_IN_FeatureModel"); activatedRules.add("deleteGroup_IN_FeatureModel"); activatedRules.add("setFeatureConcrete"); activatedRules.add("setFeatureOptional"); activatedRules.add("createAbstractFeature_IN_Feature"); activatedRules.add("deleteAbstractFeature_IN_Feature"); activatedRules.add("changeGroupFromORtoALT"); activatedRules.add("createExcludeConstraint_IN_FeatureModel"); activatedRules.add("createGroup_IN_FeatureModel"); activatedRules.add("createRequireConstraint_IN_FeatureModel"); activatedRules.add("deleteConcreteFeature_IN_Feature"); activatedRules.add("deleteGroup_IN_FeatureModel"); activatedRules.add("removeFromGroup_features_Feature"); activatedRules.add("setFeatureAbstract"); activatedRules.add("setFeatureMandatory"); activatedRules.add("mutuallyExchangeMandatoryAndOptional"); activatedRules.add("CreateChildrenFeaturesWithGroup"); activatedRules.add("CreateFeatureWithSubFeatures"); activatedRules.add("ReplaceFeature"); activatedRules.add("AddSiblingFeaturesWithGroup"); activatedRules.add("Generalization_2-6"); activatedRules.add("Generalization_2-7"); activatedRules.add("FlattenFeatureHierarchy"); activatedRules.add("Refactoring_1-2"); activatedRules.add("Refactoring_1-3"); activatedRules.add("Refactoring_1-4"); activatedRules.add("Refactoring_1-5");

//		deactivatedRules.add("Specialization_3-6"); // Ram läuft voll.
		
//		Set<String> limitedSetOfRulesByRuleNames = new HashSet<String>();
//		limitedSetOfRulesByRuleNames.add("Generalization_2-1");
//		limitedSetOfRulesByRuleNames.add("Generalization_2-1_");
		
		final File f = new File(Runner_WithPullback.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();

		String projectPath = filePath.replaceAll("bin", "");
		System.out.println(projectPath);
		String subDirectoryPath = "testData\\featModWithoutUpLimitsOnRefs\\fmedit_noNACs_noAttrChange\\rulesPO\\";
		String fullSubDirectoryPath = projectPath + subDirectoryPath;
		
		Runner_WithPullback runner_WithPullback = new Runner_WithPullback();
		runner_WithPullback.setNoApplicationConditions(true);
		runner_WithPullback.setNoMultirules(true);
		runner_WithPullback.setAnalysisKinds(true, true, true);
//		runner.limitSetOfRulesByRuleNames(limitedSetOfRulesByRuleNames);
		runner_WithPullback.runWithActivatedRules(fullSubDirectoryPath, activatedRules);
	}
}
