/**
 * 
 */
package org.eclipse.emf.henshin.variability.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.Change;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.variability.InconsistentRuleException;
import org.eclipse.emf.henshin.variability.VarRuleApplicationImpl;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareEngine;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatch;
import org.eclipse.emf.henshin.variability.util.RuleUtil;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityRule;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * @author speldszus
 * 
 *         A test for checking the Henshin engine. The tests are specified in
 *         JSON files described by inner classes.
 *
 */
@RunWith(Parameterized.class)
public class VBEngineParameterizedTest {

	private static final File dataFile = new File("data");
	private static final boolean DEBUG = false;
	private VBTestData data;

	public VBEngineParameterizedTest(VBTestData data) {
		this.data = data;
	}

	@Parameters(name = "{0}")
	public static Collection<VBTestData> collectTests() {
		Collection<VBTestData> tests = new LinkedList<>();
		for (File folder : dataFile.listFiles()) {
			if (folder.isDirectory()) {
				List<File> expectFiles = new LinkedList<>();
				List<File> metaModelFiles = new LinkedList<>();
				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						if (file.getName().endsWith(".json")) {
							expectFiles.add(file);
						} else if (file.getName().endsWith(".ecore")) {
							metaModelFiles.add(file);
						}
					}
				}
				for (File expectFile : expectFiles) {
					tests.addAll(createTests(metaModelFiles, expectFile));
				}
			}
		}
		return tests;
	}

	/**
	 * Parses the expect file and creates test data objects
	 * 
	 * @param metaModelFiles The relevant meta model files
	 * @param expectFile     The file containing the test description
	 * @return The tests
	 */
	private static Collection<VBTestData> createTests(List<File> metaModelFiles, File expectFile) {
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
			File ruleFile = new File(folder, desc.ruleFile);
			if (!ruleFile.exists()) {
				System.err.println("Skip " + ruleFile);
				return Collections.emptyList();
			}
			Collection<VBTestData> tests = new LinkedList<>();
			for (TestApplication application : desc.applications) {
				HenshinResourceSet rs = initRS(folder, metaModelFiles);
				Module module = rs.getModule(ruleFile.getAbsolutePath(), true);
				File modelFile = new File(folder, application.model);
				EObject model = rs.getEObject(modelFile.getAbsolutePath());
				Rule rule;
				if (application.rule == null) {
					rule = module.getAllRules().get(0);
				} else {
					rule = module.getAllRules().parallelStream().filter(r -> application.rule.equals(r.getName()))
							.findAny().orElse(null);
				}
				if (!RuleUtil.isVarRule(rule)) {
					System.err.println("Skip as it is not a VB rule: " + rule);
				}
				tests.add(new VBTestData(expectFile.getName(), rule, model.eResource(), application.results));
			}
			return tests;
		} catch (NullPointerException e) {
			System.err.println("Skipping rule file: " + desc.ruleFile);
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	/**
	 * Initializes the resource set
	 * 
	 * @param folder         The folder containing the test cases
	 * @param metaModelFiles The meta model files to be loaded
	 * @return The resource set
	 */
	protected static HenshinResourceSet initRS(File folder, List<File> metaModelFiles) {
		HenshinResourceSet rs = new HenshinResourceSet(folder.getAbsolutePath());
		for (File metaModel : metaModelFiles) {
			Resource mmResource = rs.getResource(metaModel.getAbsolutePath());
			EPackage ePackage = (EPackage) mmResource.getContents().get(0);
			mmResource.setURI(URI.createURI(ePackage.getNsURI()));
			rs.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		return rs;
	}

	/**
	 * Executes the specified test
	 * 
	 * @param dataFile The test specification
	 * @throws InconsistentRuleException If a inconsistent rule should be executed
	 */
	@Test
	public void testVBEngine() throws InconsistentRuleException {
		EGraphImpl graph = new EGraphImpl(data.resource);
		//VariabilityRule varRule = VariabilityFactory.INSTANCE.createVariabilityRule(data.rule);
		VariabilityAwareEngine vbEngine = new VariabilityAwareEngine(data.rule, graph);
		Set<VariabilityAwareMatch> matches = vbEngine.findMatches();
		int numberOfMatches = matches.size();
		for (VariabilityAwareMatch completeVarMatch : matches) {
			applyRuleAtMatch(graph, vbEngine, completeVarMatch);
		}
		if (DEBUG) {
			Path path = Paths.get("debug/" + data.resource.getURI().lastSegment());
			path.getParent().toFile().mkdirs();
			try (OutputStream outputStream = Files
					.newOutputStream(path)) {
				data.resource.save(outputStream, Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int modelSize = getModelSize(data);
		for (TestResult check : data.expect) {
			switch (check.kind) {
			case MATCHES:
				assertEquals("Number of matches not as expected!", ((Number) check.value).intValue(), numberOfMatches);
				break;
			case MODEL_SIZE:
				assertEquals("Model size not as expected!", ((Number) check.value).intValue(), modelSize);
				break;
			}
		}
	}
	
	private void compareRules(Rule rule1, Rule rule2) {
		//Compare Annotations
		for (Annotation anno : rule1.getAnnotations()) {
			if (!containsAnnotation(rule2, anno.getKey(), anno.getValue())) {
				System.out.println("Rule 2 does not contain Annotation " + anno.getKey() + " - " + anno.getValue());
			}
		}
	}
	
	private boolean containsAnnotation(ModelElement modelElement, String key, String value) {
		for (Annotation anno : modelElement.getAnnotations()) {
			if (anno.getKey().equals(key) && 
			   ((anno.getValue() == null && value == null) || anno.getValue().equals(value)) &&
			   ((anno.getValue() == null && value == null) || anno.getValue().equals(value))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Applies the match
	 * 
	 * @param graph  The graph
	 * @param engine The engine
	 * @param match  The VB match to be applied
	 */
	private void applyRuleAtMatch(EGraphImpl graph, VariabilityAwareEngine engine, VariabilityAwareMatch match) {
		VarRuleApplicationImpl app = new VarRuleApplicationImpl(new EngineImpl(), graph, match.getRule(), null, match);
		app.execute(null);
		
//		Match completeMatch = match.getMatch();
//		Rule unit = match.getMatch().getRule();
//		match.prepareRule();
//		MatchImpl resultMatch = new MatchImpl((Rule) unit, true);
//		Change change = engine.createChange((Rule) unit, graph, completeMatch, resultMatch);
//		if (change == null) {
//			fail("Creating change failed!");
//		}
//		change.applyAndReverse();
//		if (match != null) {
//			match.undoPreparation();
//		}
	}

	/**
	 * Calculates the size of the test model in terms of model elements
	 * 
	 * @param data The test data
	 * @return The size of the model
	 */
	protected int getModelSize(VBTestData data) {
		int modelSize = 0;
		EcoreUtil.resolveAll(data.resource);
		TreeIterator<EObject> contents = data.resource.getAllContents();
		while (contents.hasNext()) {
			EObject next = contents.next();
			modelSize++;
		}
		return modelSize;
	}

	/**
	 * A data class for storing loaded test data
	 * 
	 * @author speldszus
	 *
	 */
	private static class VBTestData {

		/**
		 * The rule to be applied
		 */
		private Rule rule;

		/**
		 * The resource to which the rule should be applied
		 */
		private Resource resource;

		/**
		 * The expected outcomes of the rule application
		 */
		private Collection<TestResult> expect;

		private String description;

		/**
		 * Creates a new instance
		 * 
		 * @param description A short description of the test
		 * @param rule        The rule
		 * @param resource    The model
		 * @param expect      The expected outcome
		 */
		private VBTestData(String description, Rule rule, Resource resource, Collection<TestResult> expect) {
			this.description = description;
			this.rule = rule;
			this.resource = resource;
			this.expect = expect;
		}

		/**
		 * Returns a human readable description of the test case
		 */
		@Override
		public String toString() {
			return description + ": Apply the rule \"" + rule.getName() + "\" to the model \""
					+ resource.getURI().lastSegment() + "\".";
		}
	}

	/**
	 * The test description as specified in a JSON file
	 * 
	 * @author speldszus
	 *
	 */
	private static class TestDescription {
		/**
		 * The location of the rule which should be applied
		 */
		String ruleFile;

		/**
		 * A specification of the rule applications
		 */
		Collection<TestApplication> applications;

	}

	/**
	 * The specification of the rule applications
	 * 
	 * @author speldszus
	 *
	 */
	private static class TestApplication {
		/**
		 * The name of the rule that should be applied
		 */
		String rule;

		/**
		 * The location of the model to which the rule should be applied
		 */
		String model;

		/**
		 * The expected outcome of the rule application
		 */
		Collection<TestResult> results;
	}

	/**
	 * The specificatio of the outcome of a rule application
	 * 
	 * @author speldszus
	 *
	 */
	private static class TestResult {
		/**
		 * The constraint kind to check
		 */
		TestResultKind kind;

		/**
		 * The expected value
		 */
		Object value;
	}

	/**
	 * 
	 * The constraint kinds
	 * 
	 * @author speldszus
	 *
	 */
	private static enum TestResultKind {
		/**
		 * The number of expected rule matches
		 */
		MATCHES,

		/**
		 * The expected model size after the rule application
		 */
		MODEL_SIZE
	}
}
