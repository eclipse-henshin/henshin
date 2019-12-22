//<>package test;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.eclipse.emf.henshin.variability.mergein.clone.JGraphTIsomorphyChecker;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.DirectedMultigraph;
//import org.junit.Test;
//
//public class SmallIsomTest {
//	@Test
//	public void testDifferentMultiGraphs() {		
//		DirectedMultigraph<String, DefaultEdge> g1 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g1.addVertex("a");
//		g1.addVertex("b");
//		g1.addEdge("a", "b");
//
//		DirectedMultigraph<String, DefaultEdge> g2 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g2.addVertex("a");
//		g2.addVertex("b");
//		g2.addEdge("a", "b");
//		g2.addEdge("a", "b");
//
//		JGraphTIsomorphyChecker<String, DefaultEdge> test = new JGraphTIsomorphyChecker<String, DefaultEdge>();
//		assertFalse(test.checkIsomorphyWithMultiEdges(g1, g2));
//	}
//	
//
//	@Test
//	public void testIsomorphicMultiGraphs() {		
//		DirectedMultigraph<String, DefaultEdge> g1 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g1.addVertex("a");
//		g1.addVertex("b");
//		g1.addEdge("a", "b");
//
//		DirectedMultigraph<String, DefaultEdge> g2 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g2.addVertex("a");
//		g2.addVertex("b");
//		g2.addEdge("a", "b");
//
//		JGraphTIsomorphyChecker<String, DefaultEdge> test = new JGraphTIsomorphyChecker<String, DefaultEdge>();
//		assertTrue(test.checkIsomorphyWithMultiEdges(g1, g2));
//	}
//	
//
//	@Test
//	public void testIsomorphyForDoublyAddedElement() {		
//		DirectedMultigraph<String, DefaultEdge> g1 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g1.addVertex("a");
//
//		DirectedMultigraph<String, DefaultEdge> g2 = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
//		g2.addVertex("a");
//		g2.addVertex("a");
//
//		JGraphTIsomorphyChecker<String, DefaultEdge> test = new JGraphTIsomorphyChecker<String, DefaultEdge>();
//		assertTrue(test.checkIsomorphyWithMultiEdges(g1, g2));
//	}
//	
//}
