/** This unit test has been deactivated on February 09, 2017 to avoid
 breaking the Henshin build due to some issue with HTTP connections on the
 Hudson build server. When activitating it again, please make sure to
 resolve these issues first.
 */
//
//package org.eclipse.emf.henshin.tests.giraph;
//
//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.eclipse.emf.henshin.giraph.GiraphGenerator;
//import org.eclipse.emf.henshin.giraph.GiraphRunner;
//import org.eclipse.emf.henshin.model.Graph;
//import org.eclipse.emf.henshin.model.IteratedUnit;
//import org.eclipse.emf.henshin.model.Module;
//import org.eclipse.emf.henshin.model.Rule;
//import org.eclipse.emf.henshin.model.Unit;
//import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
//import org.junit.Assert;
//import org.junit.Assume;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class GiraphTest {
//
//	private static Module TEST_MODULE;
//
//	private static IProject TEST_PROJECT;
//
//	@BeforeClass
//	public static void init() {
//		assumeNoErrorStatus(GiraphGenerator.validatePlatformForTesting());
//		assumeNoErrorStatus(GiraphGenerator.validateSshForTesting());
//		TEST_MODULE = new HenshinResourceSet("src/org/eclipse/emf/henshin/tests/giraph")
//				.getModule("GiraphTests.henshin");
//		Assert.assertNotNull(TEST_MODULE);
//	}
//
//	private static void assumeNoErrorStatus(IStatus status) {
//		if (status.getSeverity() == IStatus.ERROR) {
//			String message = "Skipping Giraph tests: " + status.getMessage();
//			System.out.println(message);
//			Assume.assumeTrue(message, false);
//		}
//	}
//
//	@Test
//	public void fork() {
//		run("ForkMain", "ForkStart", 1, 0);
//	}
//
//	@Test
//	public void parallelNodes() {
//		run("ParallelNodes", "ParallelNodesStart", 0, 0);
//	}
//
//	@Test
//	public void parallelEdges() {
//		run("ParallelEdges", "ParallelEdgesStart", 0, 0);
//	}
//
//	@Test
//	public void parallelTriangles() {
//		run("ParallelTriangles", "ParallelTrianglesStart", 0, 0);
//	}
//
//	@Test
//	public void parallelV() {
//		run("ParallelV", "ParallelVStart", 0, 0);
//	}
//
//	@Test
//	public void requireOne() {
//		run("RequireOne", "RequireStart", 5, 8);
//	}
//
//	@Test
//	public void requireTwo() {
//		run("RequireTwo", "RequireStart", 5, 8);
//	}
//
//	@Test
//	public void sierpinski1() {
//		runIterated("SierpinskiMain", 1, "SierpinskiStart", 6, 9);
//	}
//
//	@Test
//	public void sierpinski2() {
//		runIterated("SierpinskiMain", 2, "SierpinskiStart", 15, 27);
//	}
//
//	@Test
//	public void sierpinski3() {
//		runIterated("SierpinskiMain", 3, "SierpinskiStart", 42, 81);
//	}
//
//	@Test
//	public void sierpinski6() {
//		runIterated("SierpinskiMain", 6, "SierpinskiStart", 1095, 2187);
//	}
//
//	@Test
//	public void sierpinski9() {
//		runIterated("SierpinskiMain", 9, "SierpinskiStart", 29526, 59049);
//	}
//
//	@Test
//	public void star() {
//		run("StarMain", "StarStart", 1, 0);
//	}
//
//	@Test
//	public void twoTimesTwo1() {
//		run("TwoTimesTwo", "TwoTimesTwoStart1", 7, 10);
//	}
//
//	@Test
//	public void twoTimesTwo2() {
//		run("TwoTimesTwo", "TwoTimesTwoStart2", 7, 10);
//	}
//
//	@Test
//	public void twoTimesTwo3() {
//		run("TwoTimesTwo", "TwoTimesTwoStart3", 10, 15);
//	}
//
//	@Test
//	public void twoTimesThree() {
//		run("TwoTimesThree", "TwoTimesThreeStart", 3, 0);
//	}
//
//	@Test
//	public void wheel() {
//		run("WheelMain", "WheelStart", 3, 3);
//	}
//
//	private void runIterated(String mainUnitName, int iterations, String inputRuleName, int aggregateVertices,
//			int aggregateEdges) {
//
//
//		// Prepare iterated unit:
//		IteratedUnit iteratedUnit = (IteratedUnit) TEST_MODULE.getUnit(mainUnitName);
//		Assert.assertNotNull(iteratedUnit);
//		IteratedUnit backup = EcoreUtil.copy(iteratedUnit);
//		iteratedUnit.setIterations(iterations + "");
//		iteratedUnit.setName(iteratedUnit.getName() + iterations);
//
//		try {
//		Graph inputGraph = ((Rule) TEST_MODULE.getUnit(inputRuleName)).getLhs();
//		Assert.assertNotNull(inputGraph);
//		Assert.assertNotEquals(0, inputGraph.getNodes().size());
//		
//		// Run test:
//		run(iteratedUnit, inputGraph, aggregateVertices, aggregateEdges);
//		}
//		finally {
//		// Restore iterated unit:
//		iteratedUnit.setIterations(backup.getIterations());
//		iteratedUnit.setName(backup.getName());
//		}
//	}
//
//	private void run(String mainUnitName, String inputRuleName, int aggregateVertices, int aggregateEdges) {
//		run(TEST_MODULE.getUnit(mainUnitName), ((Rule) TEST_MODULE.getUnit(inputRuleName)).getLhs(), aggregateVertices,
//				aggregateEdges);
//	}
//
//	private void run(Unit mainUnit, Graph inputGraph, int aggregateVertices, int aggregateEdges) {
//
//		System.out.println("Generating Giraph code for " + mainUnit.getName() + "...");
//
//		GiraphGenerator generator = new GiraphGenerator();
//		generator.setMainUnit(mainUnit);
//		generator.setInputGraph(inputGraph);
//		generator.setInputName(inputGraph.getRule().getName());
//
//		if (TEST_PROJECT == null) {
//			System.out.println("Installing Hadoop Test Environment (may take a couple of minutes)...");
//			generator.setTestEnvironment(true);
//		}
//
//		try {
//			IStatus status = generator.validateAll();
//			Assert.assertTrue(status.getMessage(), status.getSeverity() != IStatus.ERROR);
//
//			TEST_PROJECT = generator.generate(null).getProject();
//			System.out.println("Starting Giraph for " + mainUnit.getName() + "...");
//			IFile antFile = TEST_PROJECT.getFolder("launch").getFile(mainUnit.getName() + ".xml");
//			GiraphRunner runner = new GiraphRunner();
//			Assert.assertTrue(runner.run(antFile));
//			Assert.assertEquals(aggregateVertices, runner.getAggregateVertices());
//			Assert.assertEquals(aggregateEdges, runner.getAggregateEdges());
//
//		} catch (CoreException e) {
//			e.printStackTrace();
//			throw new AssertionError(e);
//		}
//	}
//
//}
