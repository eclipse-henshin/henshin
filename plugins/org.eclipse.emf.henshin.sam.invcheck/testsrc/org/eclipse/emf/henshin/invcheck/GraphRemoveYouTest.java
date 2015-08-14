package org.eclipse.emf.henshin.invcheck;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.RuleApplication;

public class GraphRemoveYouTest extends TestCase {
	/*
	private WeakReference sourceN;
	private WeakReference leftN;
	private WeakReference rightN;
	private WeakReference targetN;
	private WeakReference edge;
	private ReferenceQueue queue;
	
	
	
	public void testRemoveYou() {
		Graph g = MetamodelFactory.eINSTANCE.createGraph();
		Graph g2 = MetamodelFactory.eINSTANCE.createGraph();
		
		Node n1 = MetamodelFactory.eINSTANCE.createNode(); n1.setType("n1"); n1.setNegated(false); //new Node("","n1",false,null);
		Node n2 = MetamodelFactory.eINSTANCE.createNode(); n2.setType("n2"); n2.setNegated(false); //new Node("","n2",false,null);
		Node n3 = MetamodelFactory.eINSTANCE.createNode(); n3.setType("n1"); n3.setNegated(false); //new Node("","n1",false,null);
		Node n4 = MetamodelFactory.eINSTANCE.createNode(); n4.setType("n2"); n4.setNegated(false); //new Node("","n2",false,null);
		
		Edge e1 = MetamodelFactory.eINSTANCE.createEdge(); e1.setType("e"); e1.setDirected(false); e1.setNegated(false);//new Edge("","e",false,false,null);
		Edge e2 = MetamodelFactory.eINSTANCE.createEdge(); e2.setType("e"); e2.setDirected(false); e2.setNegated(false);//new Edge("","e",false,false,null);
		
		n1.setGraph(g);
		n2.setGraph(g);
		n3.setGraph(g2);
		n4.setGraph(g2);
		e1.setGraph(g);
		e2.setGraph(g2);
		
		e1.setSourceNode(n1);
		e1.setTargetNode(n2);
		e2.setSourceNode(n3);
		e2.setTargetNode(n4);
		
		
		//n1.addToReferenceItems(n3);
		//n2.addToReferenceItems(n4);
		//e1.addToReferenceItems(e2);
		
//		assertEquals(2,g.getNodes().size());
//		assertEquals(1,g.getEdges().size());
		//assertEquals(1, n1.getReferenceItems().size());
		//assertEquals(1, n2.getReferenceItems().size());
//		assertEquals(n1, e1.getSourceNode());
//		assertEquals(n2, e1.getTargetNode());
		//assertEquals(n1, n3.getFromReferenceItems(g));
		//assertEquals(n2, n4.getFromReferenceItems(g));
		
		//g.removeYou();
		
//		assertEquals(0,g.getNodes().size());
//		assertEquals(0,g.getEdges().size());
		//assertEquals(0, n1.getReferenceItems().size());
		//assertEquals(0, n2.getReferenceItems().size());
		//assertEquals(0,n3.getReferenceItems().size());
		//assertEquals(0, n4.getReferenceItems().size());
//		assertNull(e1.getSourceNode());
//		assertNull(e1.getTargetNode());
//		assertNull(n3.getFromReferenceItems(g));
//		assertNull(n4.getFromReferenceItems(g));
//		assertNull(e2.getFromReferenceItems(g));
		
		//fail("Not yet implemented");
	}
	
	public void testWeakRef() {
		Graph g = MetamodelFactory.eINSTANCE.createGraph();
		WeakReference<Graph> ref = new WeakReference<Graph>(g);
		assertNotNull(ref.get());
		g = null;
		System.gc();
		assertNull(ref.get());		
	}
	
	public void testRuleAppRefs() {
		compute();
		assertNotNull(sourceN.get());
		assertNotNull(leftN.get());
		assertNotNull(rightN.get());
		assertNotNull(targetN.get());
		System.gc();
		assertNull(sourceN.get());
		assertNull(leftN.get());
		assertNull(rightN.get());
		assertNull(targetN.get());
	}
	
	private void compute() {
		RuleApplication ruleApp = new RuleApplication();
		
		Graph forbidden = null;
		Graph target = null;
		GraphRule rule = MetamodelFactory.eINSTANCE.createGraphRule();
		RuleGraph left = MetamodelFactory.eINSTANCE.createRuleGraph();
		RuleGraph right = MetamodelFactory.eINSTANCE.createRuleGraph();

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/ref-pattern1.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       forbidden = (Graph) o;
		} else {
			fail("loading graph model failed");
		}

		uri = URI.createURI("testsrc/ref-target1.metamodel");
		r = rs.getResource(uri, true);
		o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       target = (Graph) o;
		} else {
			fail("loading graph model failed");
		}

		Node nn = MetamodelFactory.eINSTANCE.createNode();
		nn.setName("a"); nn.setType("A");
		rightN = new WeakReference(nn);
		nn.setGraph(right);
		Node n1 = MetamodelFactory.eINSTANCE.createNode();
		n1.setName("b"); n1.setType("B");
		n1.setGraph(right);
		Node n2 = MetamodelFactory.eINSTANCE.createNode();
		n2.setName("c"); n2.setType("C");
		n2.setGraph(right);
		
		Edge e =  MetamodelFactory.eINSTANCE.createEdge();
		e.setName("ab"); e.setType("AB");
		e.setSourceNode(nn); e.setSourceNode(n1);
		e.setGraph(right);
		edge = new WeakReference(e);
		
		e =  MetamodelFactory.eINSTANCE.createEdge();
		e.setName("bc"); e.setType("BC");
		e.setSourceNode(n1); e.setSourceNode(n2);
		e.setGraph(right);
		
		n1 = MetamodelFactory.eINSTANCE.createNode();
		n1.setName("a"); n1.setType("A");
		n1.setRefInRule(nn);
		n1.setGraph(left);
		leftN = new WeakReference(n1);
		
		nn = MetamodelFactory.eINSTANCE.createNode();
		e = MetamodelFactory.eINSTANCE.createEdge();
		
		rule.setLeftHandSide((RuleGraph)left);
		rule.setLeftHandSide((RuleGraph)right);
		
		Graph source = ruleApp.reverseRuleApplication(target, rule);
		
		sourceN = new WeakReference(source.getNodes().get(0));
		targetN = new WeakReference(target.getNodes().get(0));
				
		assertNotNull(sourceN.get());
		assertNotNull(leftN.get());
		assertNotNull(rightN.get());
		assertNotNull(targetN.get());
		ruleApp.reset();
	
	}*/
	
	public void testT() {
		
	}

}
