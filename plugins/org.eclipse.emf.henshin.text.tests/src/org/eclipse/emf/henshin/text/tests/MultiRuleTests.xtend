package org.eclipse.emf.henshin.text.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.junit.Test
import org.eclipse.emf.henshin.text.henshin_text.Graph
import org.junit.Assert
import com.google.common.collect.Iterables
import org.eclipse.emf.henshin.text.henshin_text.impl.MultiRuleImpl
import org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl
import org.eclipse.emf.henshin.text.henshin_text.Node
import org.eclipse.emf.henshin.text.henshin_text.Edges
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.MultiRuleReuseNode
import org.eclipse.emf.henshin.text.henshin_text.MultiRule
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class MultiRuleTests {
	@Inject extension ParseHelper<Model>
	
	/**
	* M1: Test empty kernel rules with one multi rule
	*/
	@Test
	def testOneMultiRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								multiRule multiname { 
									graph {
									node a:testmodel.type
									}
								}
							}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		val multiRules = Iterables.filter(graph.graphElements,typeof(MultiRuleImpl))
		Assert::assertEquals(1,multiRules.size)
		Assert::assertEquals("multiname",multiRules.get(0).name)
		Assert::assertEquals(1,multiRules.get(0).multiruleElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.GraphImpl",multiRules.get(0).multiruleElements.get(0).class.name)
	}
	
	/**
	* M2: Test empty kernel rule with nested multi rules
	*/
	@Test
	def testNestedMultiRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								multiRule multiname1 { 
									graph {
										node a:testmodel.type
										multiRule multiname1nested { 
											graph {
												node b:testmodel.type
											}
										}
									}
								}
								multiRule multiname2 { 
									graph {
									node c:testmodel.type
									}
								}
							}
						}'''.parse	
		val rule=  model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		val multiRules = Iterables.filter(graph.graphElements,typeof(MultiRuleImpl))
		Assert::assertEquals(2,multiRules.size)
		Assert::assertEquals("multiname1",multiRules.get(0).name)
		Assert::assertEquals(1,multiRules.get(0).multiruleElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.GraphImpl",multiRules.get(0).multiruleElements.get(0).class.name)
		val nestedGraph = multiRules.get(0).multiruleElements.get(0) as Graph
		val nestedMultiRules = Iterables.filter(nestedGraph.graphElements,typeof(MultiRuleImpl))
		Assert::assertEquals(1,nestedMultiRules.size)
		Assert::assertEquals("multiname1nested",nestedMultiRules.get(0).name)
		Assert::assertEquals(1,nestedMultiRules.get(0).multiruleElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.GraphImpl",nestedMultiRules.get(0).multiruleElements.get(0).class.name)
		Assert::assertEquals("multiname2",multiRules.get(1).name)
		Assert::assertEquals(1,multiRules.get(1).multiruleElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.GraphImpl",multiRules.get(1).multiruleElements.get(0).class.name)	
	}
	
	
	/**
	* M3: Test empty kernel rule with multi rules and actiontype markers
	*/
	@Test
	def testActionTypeMultiRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								multiRule multiname1 { 
									graph {
										create node a:testmodel.type
										multiRule multiname1nested { 
											graph {
												node d:testmodel.type
												delete node c:testmodel.type
												edges [forbid d->c:testmodel.type]
											}
										}
									}
								}
							}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		val multiRules = Iterables.filter(graph.graphElements,typeof(MultiRuleImpl))
		var nestedGraph= multiRules.get(0).multiruleElements.get(0) as Graph
		var nodes=Iterables.filter(nestedGraph.graphElements,typeof(NodeImpl))
		Assert::assertEquals("create",nodes.get(0).actiontype)
		Assert::assertEquals("a",nodes.get(0).name)
		val nestedMultiRules = Iterables.filter(nestedGraph.graphElements,typeof(MultiRuleImpl))
		nestedGraph= nestedMultiRules.get(0).multiruleElements.get(0) as Graph
		nodes=Iterables.filter(nestedGraph.graphElements,typeof(NodeImpl))
		Assert::assertEquals(null,nodes.get(0).actiontype)
		Assert::assertEquals("d",nodes.get(0).name)
		Assert::assertEquals("delete",nodes.get(1).actiontype)
		Assert::assertEquals("c",nodes.get(1).name)
		val edges=nestedGraph.graphElements.get(2) as Edges
		Assert::assertEquals("forbid",edges.edges.get(0).actiontype)
	}
	
	
	/**
	* M4: Test Kernel-rule and multirule
	*/
	@Test
	def testKernelAndMultiRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								node a:testmodel.type
								node c:testmodel.type
								multiRule multiname { 
									graph {
										edges [(create a->b:testmodel.type),
												(create c->b:testmodel.type)]
										node create b:testmodel.type
										reuse c {
											 attribute1="test"
										}
										
									}
								}
							}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		val nodeA =graph.graphElements.get(0) as Node
		Assert::assertEquals("a",nodeA.name)
		val nodeC =graph.graphElements.get(1) as Node
		Assert::assertEquals("c",nodeC.name)
		val multiRules = Iterables.filter(graph.graphElements,typeof(MultiRuleImpl))
		val multigraph= multiRules.get(0).multiruleElements.get(0) as Graph
		val edges=multigraph.graphElements.get(0) as Edges
		val nodeB=multigraph.graphElements.get(1) as Node
		Assert::assertEquals("b",nodeB.name)
		var reuseNode=multigraph.graphElements.get(2) as MultiRuleReuseNode
		Assert::assertEquals("c",(reuseNode.name as Node).name)
		Assert::assertEquals(nodeA,edges.edges.get(0).source)
		Assert::assertEquals(nodeB,edges.edges.get(0).target)
		Assert::assertEquals(nodeC,reuseNode.name)
		Assert::assertEquals(reuseNode.name,edges.edges.get(1).source)
		Assert::assertEquals(nodeB,edges.edges.get(1).target)
	}
	
	
	/**
	* M5: Test Multi Rule with parameter
	*/
	@Test
	def testParameterMultiRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(param1:EString,param2:EEList){
							graph{
								multiRule multiname { 
									graph {
										node a:testmodel.type{
											attribute1=param1
											attribute2=param2
										}
									}
								}
							}
						}'''.parse	
		var ruleParam =(model.transformationsystem.get(0) as Rule).parameters.get(0)
		val graph = (model.transformationsystem.get(0) as Rule).ruleElements.get(0) as Graph
		val multiRule = Iterables.filter(graph.graphElements,typeof(MultiRule)).get(0)
		val multiGraph=multiRule.multiruleElements.get(0) as Graph
		var attribute=(multiGraph.graphElements.get(0) as Node).attribute.get(0)
		Assert::assertEquals(ruleParam,(attribute.value as ParameterValue).value)
		ruleParam =(model.transformationsystem.get(0) as Rule).parameters.get(1)
		attribute=(multiGraph.graphElements.get(0) as Node).attribute.get(1)
		Assert::assertEquals(ruleParam,(attribute.value as ParameterValue).value)
	}
	
	
	/**
	 * M6: Test set-Attribute
	 */
	@Test
	def setAttribute(){
				val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								node a:testmodel.type
								
									multiRule multiname { 
										graph {
											reuse a {
												attribute1="test"
												set attribute2="test2"
											}
										}
									
								}
							}
						}'''.parse
		val graph = (model.transformationsystem.get(0) as Rule).ruleElements.get(0) as Graph
		val node=((graph.graphElements.get(1) as MultiRule).multiruleElements.get(0) as Graph).graphElements.get(0) as MultiRuleReuseNode
		val attribute=node.attribute.get(0) 
		val attributeSet=node.attribute.get(1)
		Assert::assertEquals(null,attribute.update)
		Assert::assertEquals("set",attributeSet.update)
	}
}