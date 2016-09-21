package org.eclipse.emf.henshin.text.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.emf.henshin.text.henshin_text.Model
import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.Assert
import org.eclipse.emf.henshin.text.henshin_text.Node
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.Graph
import org.eclipse.emf.henshin.text.henshin_text.Edges
import org.eclipse.emf.henshin.text.henshin_text.StringValue
import org.eclipse.emf.henshin.text.henshin_text.JavaClassValue
import org.eclipse.emf.henshin.text.henshin_text.Formula
import org.eclipse.emf.henshin.text.henshin_text.ConditionReuseNode
import org.eclipse.emf.henshin.text.henshin_text.ConditionEdges
import org.eclipse.emf.henshin.text.henshin_text.Not
import org.eclipse.emf.henshin.text.henshin_text.ConditionGraphRef
import org.eclipse.emf.henshin.text.henshin_text.ORorXOR
import org.eclipse.emf.henshin.text.henshin_text.AND
import org.eclipse.emf.henshin.text.henshin_text.ConditionNode

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class FormulaTests {
	@Inject extension ParseHelper<Model>
	
	/**
	* F1: test forbid nodes and edges
	*/
	@Test
	def testForbid(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								edges [(forbid a->b:testmodel.type)]
								forbid node a:testmodel.type
								forbid node b:testmodel.type {}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(3,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(0).class.name)
		val edges=graph.graphElements.get(0) as Edges
		Assert::assertEquals(1,edges.edges.size)
		Assert::assertEquals("forbid",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		var node=graph.graphElements.get(1) as Node
		Assert::assertEquals("forbid",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).source)
		Assert::assertEquals("a",(edges.edges.get(0).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals("forbid",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).target)
		Assert::assertEquals("b",(edges.edges.get(0).target as Node).name)
	}
	
		
	/**
	* F2: test requires nodes and edges
	*/
	@Test
	def testRequire(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								edges [(require a->b:testmodel.type)]
								require node a:testmodel.type
								require node b:testmodel.type {}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(3,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(0).class.name)
		val edges=graph.graphElements.get(0) as Edges
		Assert::assertEquals(1,edges.edges.size)
		Assert::assertEquals("require",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		var node=graph.graphElements.get(1) as Node
		Assert::assertEquals("require",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).source)
		Assert::assertEquals("a",(edges.edges.get(0).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals("require",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).target)
		Assert::assertEquals("b",(edges.edges.get(0).target as Node).name)
	}
	
	
	/**
	* F3: Test attributes
	*/
	@Test
	def testAttributMarken(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							javaImport java
							graph{
								node a:testmodel.type {
									forbid attribute1="test"
									require attribute2=classname.call()
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(1) as Graph
		Assert::assertEquals(1,graph.graphElements.size)
		var node=graph.graphElements.get(0) as Node
		Assert::assertEquals(2,node.attribute.size)
		var attribute=node.attribute.get(0)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("test",(attribute.value as StringValue).value)
		Assert::assertEquals("forbid",attribute.actiontype)
		attribute=node.attribute.get(1)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("classname.call",(attribute.value as JavaClassValue).value)
		Assert::assertEquals("require",attribute.actiontype)
	}
	
	/**
	 * F4: Test mapping between LHS and conditionGraph
	 */
	 @Test
	 def testLHSMapping(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								node a:testmodel.type 
								node b:testmodel.type
								node c:testmodel.type
								matchingFormula {
									formula !conGraph
									conditionGraph conGraph{
										node d:testmodel.type
										reuse a{
											attribute="test"
										}
										edges [(d->b:testmodel.type)]
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		val nodeA=graph.graphElements.get(0) as Node
		val nodeB=graph.graphElements.get(1) as Node
		val conditionGraph=(graph.graphElements.get(3) as Formula).conditionGraphs.get(0)
		Assert::assertEquals(nodeA,(conditionGraph.conditionGraphElements.get(1) as ConditionReuseNode).name as Node)
		Assert::assertEquals(nodeB,(conditionGraph.conditionGraphElements.get(2) as ConditionEdges).edges.get(0).target as Node)
	 }
	 
	 
	/**
	* F5: Test Mix of require and forbid markers
	*/
	@Test
	def testRequireForbidMarken(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								require node a:testmodel.type
								forbid node b:testmodel.type
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(2,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(0).class.name)
		var node=graph.graphElements.get(0) as Node
		Assert::assertEquals("require",node.actiontype)
		Assert::assertEquals("a",node.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		node=graph.graphElements.get(1) as Node
		Assert::assertEquals("forbid",node.actiontype)
		Assert::assertEquals("b",node.name)
	}
		
		
	/**
	 * F6: Test nested conditionGraphs
	 */
	 @Test
	 def testConditionGraphNesting(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								node a:testmodel.type 
								matchingFormula {
									formula !conGraph
									conditionGraph conGraph{
										node b:testmodel.type
										reuse a{
											attribute="test"
										}
										edges [(a->b:testmodel.type)]
										matchingFormula {
											formula conNestGraph
												conditionGraph conNestGraph{
													node c:testmodel.type
												}
										}
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("a",(graph.graphElements.get(0) as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(1).class.name)
		val conditionGraph=(graph.graphElements.get(1) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("conGraph",conditionGraph.name)
		Assert::assertEquals("b",(conditionGraph.conditionGraphElements.get(0) as ConditionNode).name)
		Assert::assertEquals("a",((conditionGraph.conditionGraphElements.get(1) as ConditionReuseNode).name as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",conditionGraph.conditionGraphElements.get(3).class.name)
		Assert::assertEquals("conNestGraph",(conditionGraph.conditionGraphElements.get(3)as Formula).conditionGraphs.get(0).name)
		Assert::assertEquals("c",((conditionGraph.conditionGraphElements.get(3)as Formula).conditionGraphs.get(0).conditionGraphElements.get(0) as ConditionNode).name)
	}
	
	
	/**
 	* F7:Test NOT
 	*/
 	@Test
	def testNOT(){
 		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								matchingFormula {
									formula !conGraph
									conditionGraph conGraph{
										node a:testmodel.type
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(0).class.name)
		val conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NotImpl",(graph.graphElements.get(0) as Formula).formula.class.name)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as Not).negation as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraph",conditionGraph.name)		
 	}
 
 
	/**
 	* F8:Test OR
 	*/
  	@Test
 	def testOR(){
 		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								matchingFormula {
									formula conGraphA OR conGraphB
									conditionGraph conGraphA{
										node a:testmodel.type
									}
									
									conditionGraph conGraphB{
										node b:testmodel.type
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(0).class.name)
		var conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ORorXORImpl",(graph.graphElements.get(0) as Formula).formula.class.name)
		Assert::assertEquals("OR",((graph.graphElements.get(0) as Formula).formula as ORorXOR).op)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphA",conditionGraph.name)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(1)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as ORorXOR).right as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphB",conditionGraph.name)
 	}
 	
 	
	/**
 	* F9:Test XOR
 	*/
	@Test
	def testXOR(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								matchingFormula {
									formula conGraphA XOR conGraphB
									conditionGraph conGraphA{
										node a:testmodel.type
									}
									
									conditionGraph conGraphB{
										node b:testmodel.type
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(0).class.name)
		var conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ORorXORImpl",(graph.graphElements.get(0) as Formula).formula.class.name)
		Assert::assertEquals("XOR",((graph.graphElements.get(0) as Formula).formula as ORorXOR).op)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphA",conditionGraph.name)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(1)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as ORorXOR).right as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphB",conditionGraph.name)
 	}
 
 
 	/**
  	* F10:Test AND
  	*/
	@Test
	def testAND(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								matchingFormula {
									formula conGraphA AND conGraphB
									conditionGraph conGraphA{
										node a:testmodel.type
									}
									
									conditionGraph conGraphB{
										node b:testmodel.type
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(0).class.name)
		var conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ANDImpl",(graph.graphElements.get(0) as Formula).formula.class.name)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as AND).left as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphA",conditionGraph.name)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(1)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as AND).right as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("conGraphB",conditionGraph.name)	
 	}
 
	/**
	* F11:test potential formula combinations
	*/
	@Test
	def testFormula(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								matchingFormula {
									formula conGraphA AND (conGraphB OR !conGraphC) XOR conGraphD
									conditionGraph conGraphA{
										node a:testmodel.type
									}
									
									conditionGraph conGraphB{
										node b:testmodel.type
									}
									
									conditionGraph conGraphC{
										node c:testmodel.type
									}
									conditionGraph conGraphD{
										node d:testmodel.type
									}
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.FormulaImpl",graph.graphElements.get(0).class.name)
		var conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(0)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ORorXORImpl",(graph.graphElements.get(0) as Formula).formula.class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ANDImpl",((graph.graphElements.get(0) as Formula).formula as ORorXOR).left.class.name)
		Assert::assertEquals("XOR",((graph.graphElements.get(0) as Formula).formula as ORorXOR).op)
		Assert::assertEquals("conGraphA",conditionGraph.name)
		Assert::assertEquals(conditionGraph,((((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).left as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ORorXORImpl",(((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).right.class.name)
		Assert::assertEquals("OR",((((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).right as ORorXOR).op)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(1)
		Assert::assertEquals("conGraphB",conditionGraph.name)
		Assert::assertEquals(conditionGraph,(((((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).right as ORorXOR).left as ConditionGraphRef).conditionGraphRef)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NotImpl",((((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).right as ORorXOR).right.class.name)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(2)
		Assert::assertEquals("conGraphC",conditionGraph.name)
		Assert::assertEquals(conditionGraph,((((((graph.graphElements.get(0) as Formula).formula as ORorXOR).left as AND).right as ORorXOR).right as Not).negation as ConditionGraphRef).conditionGraphRef)
		conditionGraph=(graph.graphElements.get(0) as Formula).conditionGraphs.get(3)
		Assert::assertEquals("conGraphD",conditionGraph.name)
		Assert::assertEquals(conditionGraph,(((graph.graphElements.get(0) as Formula).formula as ORorXOR).right as ConditionGraphRef).conditionGraphRef)
	}

}