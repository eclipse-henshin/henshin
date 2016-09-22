package org.eclipse.emf.henshin.text.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.emf.henshin.text.henshin_text.Model
import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.Assert
import org.eclipse.emf.henshin.text.henshin_text.Graph
import org.eclipse.emf.henshin.text.henshin_text.Edges
import org.eclipse.emf.henshin.text.henshin_text.Node
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.StringValue
import org.eclipse.emf.henshin.text.henshin_text.JavaClassValue
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue
import org.eclipse.emf.henshin.text.henshin_text.JavaAttributeValue

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class TransformationTests {
	@Inject extension ParseHelper<Model>
	
	/**
	* T1: Test of nodes and edges with <preserve> action
	*/
	@Test
	def testPreserve(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								edges [(a->b:testmodel.type),(preserve c->d:testmodel.type)]
								node a:testmodel.type
								node b:testmodel.type {}
								preserve node c:testmodel.type
								preserve node d:testmodel.type {}
							}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule			
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(5,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(0).class.name)
		val edges=graph.graphElements.get(0) as Edges
		Assert::assertEquals(2,edges.edges.size)
		Assert::assertEquals(null,edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		var node=graph.graphElements.get(1) as Node
		Assert::assertEquals(null,node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).source)
		Assert::assertEquals("a",(edges.edges.get(0).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals(null,node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).target)
		Assert::assertEquals("b",(edges.edges.get(0).target as Node).name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals("preserve",edges.edges.get(1).actiontype)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(3).class.name)
		node=graph.graphElements.get(3) as Node
		Assert::assertEquals("preserve",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(1).source)
		Assert::assertEquals("c",(edges.edges.get(1).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(4).class.name)
		node=graph.graphElements.get(4) as Node
		Assert::assertEquals("preserve",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(1).target)
		Assert::assertEquals("d",(edges.edges.get(1).target as Node).name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(1).type.class.name)
	 }

	/**
	* T2: Test of nodes and edges with <create> action
	*/
	@Test
	def testCreate(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								edges [(create a->b:testmodel.type)]
								create node a:testmodel.type
								create node b:testmodel.type {}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(3,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(0).class.name)
		val edges=graph.graphElements.get(0) as Edges
		Assert::assertEquals(1,edges.edges.size)
		Assert::assertEquals("create",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		var node=graph.graphElements.get(1) as Node
		Assert::assertEquals("create",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).source)
		Assert::assertEquals("a",(edges.edges.get(0).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals("create",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).target)
		Assert::assertEquals("b",(edges.edges.get(0).target as Node).name)
	}	
	
	
	/**
	* T3: Test of nodes and edges with <delete> action
	*/
	@Test
	def testDelete(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								edges [(delete a->b:testmodel.type)]
								delete node a:testmodel.type
								delete node b:testmodel.type {}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(3,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(0).class.name)
		val edges=graph.graphElements.get(0) as Edges
		Assert::assertEquals(1,edges.edges.size)
		Assert::assertEquals("delete",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		var node=graph.graphElements.get(1) as Node
		Assert::assertEquals("delete",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).source)
		Assert::assertEquals("a",(edges.edges.get(0).source as Node).name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals("delete",node.actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		Assert::assertEquals(node,edges.edges.get(0).target)
		Assert::assertEquals("b",(edges.edges.get(0).target as Node).name)
	}	
	
	/**
	* T4: Test of transformation
	*/
	@Test
	def testTransformation(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								preserve node a1:testmodel.type
								node a2:testmodel.type{
									attribut1="test"
								}
								delete node b:testmodel.type 
								edges [(delete a->b:testmodel.type)]
								create node c:testmodel.type
								edges [(create a->c:testmodel.type),
										(a1->a2:testmodel.type)]
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(6,graph.graphElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(0).class.name)
		var node=graph.graphElements.get(0) as Node
		Assert::assertEquals("preserve",node.actiontype)
		Assert::assertEquals("a1",node.name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(1).class.name)
		node=graph.graphElements.get(1) as Node
		Assert::assertEquals(null,node.actiontype)
		Assert::assertEquals("a2",node.name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(1,node.attribute.size)
		
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(2).class.name)
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals("delete",node.actiontype)
		Assert::assertEquals("b",node.name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(3).class.name)
		var edges=graph.graphElements.get(3) as Edges
		Assert::assertEquals(1,edges.edges.size)
		Assert::assertEquals("delete",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.NodeImpl",graph.graphElements.get(4).class.name)
		node=graph.graphElements.get(4) as Node
		Assert::assertEquals("create",node.actiontype)
		Assert::assertEquals("c",node.name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",node.nodetype.class.name)
		Assert::assertEquals(0,node.attribute.size)
		
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.EdgesImpl",graph.graphElements.get(5).class.name)
		edges=graph.graphElements.get(5) as Edges
		Assert::assertEquals(2,edges.edges.size)
		Assert::assertEquals("create",edges.edges.get(0).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(0).type.class.name)
		Assert::assertEquals(null,edges.edges.get(1).actiontype)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EReferenceImpl",edges.edges.get(1).type.class.name)
	}	

	
	/**
	* T5: Test attribute
	*/
	@Test
	def testAttributMarken(){
		val model = ''' ePackageImport testmodel
						rule rulename(param:EString){
							javaImport java
							graph{
								node a:testmodel.type {
									attribute1="test"
									preserve attribute2=classname.call()
									attribute=classname.attribute
								}
								create node b:testmodel.type {
									create attribute1="test"
								}
								delete node c:testmodel.type {
									delete attribute1=param
								}
							}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val graph = rule.ruleElements.get(1) as Graph
		Assert::assertEquals(3,graph.graphElements.size)
		var node=graph.graphElements.get(0) as Node
		Assert::assertEquals(3,node.attribute.size)
		var attribute=node.attribute.get(0)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("test",(attribute.value as StringValue).value)
		Assert::assertEquals(null,attribute.actiontype)
		attribute=node.attribute.get(1)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("classname.call",(attribute.value as JavaClassValue).value)
		Assert::assertEquals("preserve",attribute.actiontype)
		attribute=node.attribute.get(2)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("classname.attribute",(attribute.value as JavaAttributeValue).value)
		Assert::assertEquals(null,attribute.actiontype)
		
		node=graph.graphElements.get(1) as Node
		Assert::assertEquals(1,node.attribute.size)
		attribute=node.attribute.get(0)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("test",(attribute.value as StringValue).value)
		Assert::assertEquals("create",attribute.actiontype)
		
		node=graph.graphElements.get(2) as Node
		Assert::assertEquals(1,node.attribute.size)
		attribute=node.attribute.get(0)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EAttributeImpl",attribute.name.class.name)
		Assert::assertEquals("param",(attribute.value as ParameterValue).value.name)
		Assert::assertEquals("delete",attribute.actiontype)	
	}
	
	
	/**
	 * T6: Test set-Attribute
	 */
	@Test
	def setAttribute(){
		val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{
								node a:testmodel.type {
									attribute1="test"
									set attribute1="test2"
								}
							}
						}'''.parse
		val graph = (model.transformationsystem.get(0) as Rule).ruleElements.get(0) as Graph
		val node=graph.graphElements.get(0) as Node
		val attribute=node.attribute.get(0) 
		val attributeSet=node.attribute.get(1)
		Assert::assertEquals(null,attribute.update)
		Assert::assertEquals("set",attributeSet.update)
	}

	
}