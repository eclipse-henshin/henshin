package org.eclipse.emf.henshin.variability.tests.parameterized.create;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.variability.util.RuleUtil;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.eclipse.emf.henshin.variability.tests.parameterized.data.TestApplication;
import org.eclipse.emf.henshin.variability.tests.parameterized.data.TestDescription;
import org.eclipse.emf.henshin.variability.tests.parameterized.data.TestSelection;
import org.eclipse.emf.henshin.variability.tests.parameterized.data.VBTestData;

public class TestCreator {
	/**
	 * Parses the expect file and creates test data objects
	 * 
	 * @param metaModelFiles The relevant meta model files
	 * @param expectFile     The file containing the test description
	 * @return The tests
	 */
	public static Collection<VBTestData> createTests(List<File> metaModelFiles, File expectFile) {
		TestDescription desc;
		try {
			desc = new Gson().fromJson(new FileReader(expectFile), TestDescription.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e1) {
			System.err.println("Skip " + expectFile);
			e1.printStackTrace();
			return Collections.emptyList();
		}

		try {
			File folder = expectFile.getParentFile();
			File ruleFile = new File(folder, desc.getRuleFile());
			if (!ruleFile.exists()) {
				System.err.println("Skip " + ruleFile);
				return Collections.emptyList();
			}
			Collection<VBTestData> tests = new LinkedList<>();
			for (TestApplication application : desc.getApplications()) {
				HenshinResourceSet rs = TestCreator.initRS(folder, metaModelFiles);
				File modelFile = new File(folder, application.getModel());
				EObject model = rs.getEObject(modelFile.getAbsolutePath());
				Rule rule = getRule(ruleFile, application.getRule(), rs);
				if (!RuleUtil.isVarRule(rule)) {
					System.err.println("Skip as it is not a VB rule: " + rule);
					continue;
				}
				Map<String, Boolean> configuration = getFeatureConfiguration(application);
				if (!VariabilityFactory.INSTANCE.createVariabilityRule(rule).getFeatures().containsAll(configuration.keySet())) {
					System.err.println("Skip as there are bindings for unknown features: "
							+ String.join(", ", configuration.keySet()));
					continue;
				}
				tests.add(new VBTestData(expectFile.getName(), rule, configuration, model.eResource(),
						application.getResults()));
			}
			return tests;
		} catch (NullPointerException e) {
			System.err.println("Skipping rule file: " + desc.getRuleFile());
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	/**
	 * Searches the specified rule
	 * 
	 * @param ruleFile The file containing the rule
	 * @param ruleName The name of the rule
	 * @param rs       The resource set into which the rule should be loaded
	 * @return The loaded rule
	 */
	private static Rule getRule(File ruleFile, String ruleName, HenshinResourceSet rs) {
		Module module = rs.getModule(ruleFile.getAbsolutePath(), true);
		Rule rule;
		if (ruleName == null) {
			rule = module.getAllRules().get(0);
		} else {
			rule = module.getAllRules().parallelStream().filter(r -> ruleName.equals(r.getName())).findAny()
					.orElse(null);
		}
		return rule;
	}

	/**
	 * Builds a feature configuration map from the test application description
	 * 
	 * @param application The test application description
	 * @return A (partial) configuration
	 */
	private static Map<String, Boolean> getFeatureConfiguration(TestApplication application) {
		Map<String, Boolean> configuration;
		if (application.getPartialConfiguration() == null) {
			configuration = Collections.emptyMap();
		} else {
			configuration = application.getPartialConfiguration().parallelStream()
					.collect(Collectors.toMap(TestSelection::getFeature, TestSelection::getSelection));
		}
		return configuration;
	}

	/**
	 * Initializes the resource set
	 * 
	 * @param folder         The folder containing the test cases
	 * @param metaModelFiles The meta model files to be loaded
	 * @return The resource set
	 */
	public static HenshinResourceSet initRS(File folder, List<File> metaModelFiles) {
		HenshinResourceSet rs = new HenshinResourceSet(folder.getAbsolutePath());
		for (File metaModel : metaModelFiles) {
			Resource mmResource = rs.getResource(metaModel.getAbsolutePath());
			EPackage ePackage = (EPackage) mmResource.getContents().get(0);
			mmResource.setURI(URI.createURI(ePackage.getNsURI()));
			rs.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		return rs;
	}
}
