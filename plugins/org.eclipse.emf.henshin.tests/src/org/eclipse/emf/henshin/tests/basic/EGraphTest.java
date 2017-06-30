package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelFactory;
import org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode;
import org.junit.Test;

/**
 * Tests for the EGraph
 */
public class EGraphTest {

	
	/**
	 * Tests that the operation getRoots does not run in an infinite loop.
	 */
	@Test(timeout = 1000, expected = IllegalStateException.class)
	public void testCyclicDependency_bug519013() {
		RecursiveNode n1 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		RecursiveNode n2 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		n1.getContainedChildren().add(n2);
		n2.getContainedChildren().add(n1);

		EGraph graph = new EGraphImpl();
		graph.add(n1);
		graph.add(n2);
		graph.getRoots();
		// Should fail with an IllegalStateException, not in infinite recursion (i.e.
		// a timeout).
	}

	@Test
	public void testGetRoots() {
		RecursiveNode n1 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		RecursiveNode n2 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		RecursiveNode n3 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		RecursiveNode n4 = EGraphTestmodelFactory.eINSTANCE.createRecursiveNode();
		n1.getContainedChildren().add(n2);
		n3.getContainedChildren().add(n4);

		EGraph graph = new EGraphImpl(Arrays.asList(n1, n2, n3, n4));
		List<EObject> roots = graph.getRoots();
		assertEquals(2, roots.size());
		assertTrue(roots.contains(n1));
		assertTrue(roots.contains(n3));
	}
}
