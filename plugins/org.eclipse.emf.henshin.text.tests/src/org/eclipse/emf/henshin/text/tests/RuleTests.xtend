package org.eclipse.emf.henshin.text.tests

import javax.inject.Inject
import org.eclipse.emf.henshin.text.henshin_text.CheckDangling
import org.eclipse.emf.henshin.text.henshin_text.ComparisonExpression
import org.eclipse.emf.henshin.text.henshin_text.Conditions
import org.eclipse.emf.henshin.text.henshin_text.EqualityExpression
import org.eclipse.emf.henshin.text.henshin_text.Graph
import org.eclipse.emf.henshin.text.henshin_text.InjectiveMatching
import org.eclipse.emf.henshin.text.henshin_text.JavaImport
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.eclipse.emf.henshin.text.henshin_text.ParameterKind
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class RuleTests {
	
	@Inject extension ParseHelper<Model>

	/**
	 * R1: Test rule with name without parameter
	 */
	@Test
	def testEmptyRule(){
		val model =''' ePackageImport testmodel
						rule rulename(){
							graph{}
						}'''.parse
      	val rule = model.transformationsystem.get(0) as Rule
		Assert::assertEquals("rulename", rule.name)
		Assert::assertEquals(0,rule.parameters.size)
		Assert::assertEquals(1,rule.ruleElements.size)
	}
	
	/**
	 * R2: Test rule with parameter and without contents
	 */
	@Test
	def testParameterRule(){
		val model = ''' ePackageImport testmodel
						rule rulename(p1:EString,p2:EEList,p3:eclass){
							graph{}
						}'''.parse
      	val rule = model.transformationsystem.get(0) as Rule
		Assert::assertEquals("rulename", rule.name)
		Assert::assertEquals(3,rule.parameters.size)
		Assert::assertEquals("p1",rule.parameters.get(0).name)
		Assert::assertEquals("EString",rule.parameters.get(0).type.enumType.literal)
		Assert::assertEquals("p2",rule.parameters.get(1).name)
		Assert::assertEquals("EEList",rule.parameters.get(1).type.enumType.literal)
		Assert::assertEquals("p3",rule.parameters.get(2).name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",rule.parameters.get(2).type.type.class.name)
		Assert::assertEquals(1,rule.ruleElements.size)
	}
	
	
	/**
	  * R3: Test rule with injective matching
	  */
	 @Test
	 def testInjectiveMatchingTrue(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							injectiveMatching true
							graph{}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule			
		val injectiveMatching = rule.ruleElements.get(0) as InjectiveMatching
		Assert::assertEquals(true,injectiveMatching.injectiveMatching)	
	 }
	 
	 
	 /**
	  * R4: Test rule without injective matching
	  */
	 @Test
	 def testInjectiveMatchingFalse(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							injectiveMatching false
							graph{}
						}'''.parse		
		val rule = model.transformationsystem.get(0) as Rule			
		val injectiveMatching = rule.ruleElements.get(0) as InjectiveMatching
		Assert::assertEquals(false,injectiveMatching.injectiveMatching)
	 }
	
	 
	 /**
	  * R5: Test rule with checkDangling=true
	  */
	 @Test
	 def testCheckDanglingTrue(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							checkDangling true
							graph{}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule					
		val checkDangling = rule.ruleElements.get(0) as CheckDangling
		Assert::assertEquals(true,checkDangling.checkDangling)
	 }
	 
	 /**
	 * R6: Test rule with checkDangling=false
	 */
	 @Test
	 def testCheckDanglingFalse(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							checkDangling false
							graph{}
						}'''.parse				
		val rule = model.transformationsystem.get(0) as Rule
		val checkDangling = rule.ruleElements.get(0) as CheckDangling
		Assert::assertEquals(false,checkDangling.checkDangling)
	 }
	
	 
	 /**
	  * R7: Test rule with two conditions in condition element
	  */
	 @Test
	 def testConditions(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(value:EInt){
							conditions [value>4,value+6==10]
							graph{}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val conditions = rule.ruleElements.get(0) as Conditions
		Assert::assertEquals(2,conditions.attributeConditions.size)
		Assert::assertEquals(">",(conditions.attributeConditions.get(0) as ComparisonExpression).op)
		Assert::assertEquals("==",(conditions.attributeConditions.get(1) as EqualityExpression).op)
	 }
	 
	  /**
	   * R8 Test rule with a single java import element
	   */
	 @Test
	 def testOneJavaImport(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							javaImport java.util
							graph{}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		Assert::assertEquals(2,rule.ruleElements.size)
		val javaImport = rule.ruleElements.get(0) as JavaImport
		Assert::assertEquals("java.util",javaImport.packagename)
	 }
	 
	  /**
	   * R9: test rule with two java imports
	   */
	 @Test
	 def testTwoJavaImport(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							javaImport java1
							javaImport java2.java
							graph{}
						}'''.parse	
		val rule = model.transformationsystem.get(0) as Rule		
		Assert::assertEquals(3,rule.ruleElements.size)
		val javaImport1 = rule.ruleElements.get(0) as JavaImport
		val javaImport2 = rule.ruleElements.get(1) as JavaImport
		Assert::assertEquals("java1",javaImport1.packagename)
		Assert::assertEquals("java2.java",javaImport2.packagename)
	 }
	 
	 /**
	  * R10 test rule with a single graph element
	  */
	 @Test
	 def testGraph(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(){
							graph{}
						}'''.parse			
		val rule = model.transformationsystem.get(0) as Rule		
		Assert::assertEquals(1,rule.ruleElements.size)
		val graph = rule.ruleElements.get(0) as Graph
		Assert::assertEquals(0,graph.graphElements.size)
	 }
	 
	  /**
	  * R11 test rule with 1 condition
	  */
	 @Test
	 def testOneConditions(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(value:EInt){
							conditions [value!=true]
							graph{}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		val conditions = rule.ruleElements.get(0) as Conditions
		Assert::assertEquals(1,conditions.attributeConditions.size)
		Assert::assertEquals("!=",(conditions.attributeConditions.get(0) as EqualityExpression).op)
	 }
	
	/**
	 * Test parameterkind
	 */
	 @Test
	 def testParmeterKinds(){
	 	val model = ''' ePackageImport testmodel
						rule rulename(VAR value:EInt, IN v2:EInt, OUT v3:EInt, INOUT v4:EInt, v5:EInt){
							graph{}
						}'''.parse
		val rule = model.transformationsystem.get(0) as Rule
		Assert::assertEquals(rule.parameters.get(0).kind, ParameterKind.VAR)
		Assert::assertEquals(rule.parameters.get(1).kind, ParameterKind.IN)
		Assert::assertEquals(rule.parameters.get(2).kind, ParameterKind.OUT)
		Assert::assertEquals(rule.parameters.get(3).kind, ParameterKind.INOUT)
		Assert::assertEquals(rule.parameters.get(4).kind, ParameterKind.UNKNOWN)
	 }
}