package org.eclipse.emf.henshin.variability.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;

import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.variability.InconsistentRuleException;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareEngine;
import org.junit.Test;

public class VBEngineEdgeCasesTest {

	@Test(expected = InconsistentRuleException.class)
	public void testInconsistentFeatureSpecification() throws InconsistentRuleException {
		File folder = new File("data/java/");
		File rule = new File(folder, "inconsistent.henshin");
		assertTrue("Rule file \"" + rule + "\" not found", rule.exists());
		
		HenshinResourceSet rs = VBEngineParameterizedTest.initRS(folder, Collections.emptyList());
		Module module = rs.getModule(rule.getAbsolutePath(), true);
		
		EGraphImpl graph = new EGraphImpl();
		
		new VariabilityAwareEngine(module.getAllRules().get(0), graph);
	}
}
