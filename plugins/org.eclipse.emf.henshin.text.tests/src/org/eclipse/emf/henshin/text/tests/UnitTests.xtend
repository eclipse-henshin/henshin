package org.eclipse.emf.henshin.text.tests

import org.eclipse.xtext.junit4.XtextRunner
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.InjectWith
import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.junit.Test
import org.junit.Assert
import org.eclipse.emf.henshin.text.henshin_text.IndependentUnit
import org.eclipse.emf.henshin.text.henshin_text.ConditionalUnit
import org.eclipse.emf.henshin.text.henshin_text.PriorityUnit
import org.eclipse.emf.henshin.text.henshin_text.LoopUnit
import org.eclipse.emf.henshin.text.henshin_text.Rollback
import org.eclipse.emf.henshin.text.henshin_text.Strict
import org.eclipse.emf.henshin.text.henshin_text.Unit
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.Call
import org.eclipse.emf.henshin.text.henshin_text.IteratedUnit
import org.eclipse.emf.henshin.text.henshin_text.NaturalValue
import org.eclipse.emf.henshin.text.henshin_text.NumberValue
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class UnitTests {
	
	@Inject extension ParseHelper<Model>
	
	/**
	 * U1: Test Unit with name, without parameters and without contents
	 */
	@Test
	def testEmptyUnit(){
		val model = ''' ePackageImport testmodel
						unit unitname(){}'''.parse
      	val unit = model.transformationsystem.get(0) as Unit
		Assert::assertEquals("unitname", unit.name)
		Assert::assertEquals(0,unit.parameters.size)
		Assert::assertEquals(0,unit.unitElements.size)
	}
	
	/**
	 * U2: Test Unit with parameters and without content
	 */
	@Test
	def testParameterUnit(){
		val model = ''' ePackageImport testmodel
						unit unitname(p1:EString,p2:EEList,p3:eclass){}'''.parse
      	val unit = model.transformationsystem.get(0) as Unit
		Assert::assertEquals("unitname", unit.name)
		Assert::assertEquals(3,unit.parameters.size)
		Assert::assertEquals("p1",unit.parameters.get(0).name)
		Assert::assertEquals("EString",unit.parameters.get(0).type.enumType.literal)
		Assert::assertEquals("p2",unit.parameters.get(1).name)
		Assert::assertEquals("EEList",unit.parameters.get(1).type.enumType.literal)
		Assert::assertEquals("p3",unit.parameters.get(2).name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",unit.parameters.get(2).type.type.class.name)
		Assert::assertEquals(0,unit.unitElements.size)
	}
	
	/**
	 * U3: Test unit which calls another unit
	 */
	@Test
	def testAufrufUnit(){
		val model = ''' ePackageImport testmodel
						unit a(){}
						unit b(){a()}'''.parse
      	val unitA = model.transformationsystem.get(0) as Unit
      	val unitB = model.transformationsystem.get(1) as Unit
		Assert::assertEquals("a", unitA.name)
		Assert::assertEquals("b", unitB.name)
		Assert::assertEquals(0,unitA.unitElements.size)
		Assert::assertEquals(1,unitB.unitElements.size)
		Assert::assertEquals("a",(unitB.unitElements.get(0) as Call).elementCall.name)
	}
	
	/**
	 * U4: Test tnit which calls a rule
	 */
	@Test
	def testAufrufRegel(){
		val model = ''' ePackageImport testmodel
						rule a(){}
						unit b(){a()}'''.parse
      	val ruleA = model.transformationsystem.get(0) as Rule
      	val unitB = model.transformationsystem.get(1) as Unit
		Assert::assertEquals("a", ruleA.name)
		Assert::assertEquals("b", unitB.name)
		Assert::assertEquals(0,ruleA.ruleElements.size)
		Assert::assertEquals(1,unitB.unitElements.size)
		Assert::assertEquals("a",(unitB.unitElements.get(0) as Call).elementCall.name)
	}
	
	/**
	 * U5: Test independentUnit
	 */
	@Test
	def testIndependentUnit(){
		val model = ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							independent[a(),a()b(),independent[b(),a()]]
						}'''.parse
		val unit = model.transformationsystem.get(2) as Unit
      	val independentUnitTest = unit.unitElements.get(0) as IndependentUnit
      	val elements=independentUnitTest.listOfLists
      	Assert::assertEquals(3,elements.size)
      	Assert::assertEquals(1, elements.get(0).subElements.size)
      	Assert::assertEquals("a", (elements.get(0).subElements.get(0) as Call).elementCall.name)
      	Assert::assertEquals("a", (elements.get(1).subElements.get(0) as Call).elementCall.name)
      	Assert::assertEquals("b", (elements.get(1).subElements.get(1) as Call).elementCall.name)
      	Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.IndependentUnitImpl", elements.get(2).subElements.get(0).class.name)
	}
	
	/**
	 * U6: Test conditionalUnit without else
	 */
	@Test
	def testConditionalUnit(){
		val model = ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							if(a() b()) then {
								if(b()) then {a()}
							}
						}'''.parse
			val unit = model.transformationsystem.get(2) as Unit
	      	val conditionalUnitTest = unit.unitElements.get(0) as ConditionalUnit
	      	val ifValues=conditionalUnitTest.getIf
	      	val thenValues=conditionalUnitTest.getThen
	      	val elseValues=conditionalUnitTest.getElse
	      	Assert::assertEquals(2,ifValues.size)
	      	Assert::assertEquals("a",(ifValues.get(0) as Call).elementCall.name)
	      	Assert::assertEquals("b",(ifValues.get(1) as Call).elementCall.name)
	      	Assert::assertEquals(1,thenValues.size)
	      	Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ConditionalUnitImpl",thenValues.get(0).class.name)
	      	Assert::assertEquals(0,elseValues.size)
	}

	/**
	 * U7: Test conditionalUnit with else
	 */
	@Test
	def testConditionalUnitElse(){
		val model = ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							if(a()) then {a()}
									else {b()}
						}'''.parse
			val unit= model.transformationsystem.get(2) as Unit
	      	val conditionalUnitTest = unit.unitElements.get(0) as ConditionalUnit
	      	val ifValues=conditionalUnitTest.getIf
	      	val thenValues=conditionalUnitTest.getThen
	      	val elseValues=conditionalUnitTest.getElse
	      	Assert::assertEquals(1,ifValues.size)
	      	Assert::assertEquals("a",(ifValues.get(0) as Call).elementCall.name)
	      	Assert::assertEquals(1,thenValues.size)
	      	Assert::assertEquals("a",(thenValues.get(0) as Call).elementCall.name)
	      	Assert::assertEquals(1,elseValues.size)
	      	Assert::assertEquals("b",(elseValues.get(0) as Call).elementCall.name)
	}
	
	/**
	 * U8: Test priorityUnit
	 */
	@Test
	def testPriorityUnit(){
		val model = ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							priority [priority [a(),b()] b(), a() b(), b()]
						}'''.parse
		val unit = model.transformationsystem.get(2) as Unit
      	val priorityUnitTest = unit.unitElements.get(0) as PriorityUnit
      	val elements=priorityUnitTest.listOfLists
      	Assert::assertEquals(3,elements.size)
      	Assert::assertEquals(2, elements.get(0).subElements.size)
      	Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.PriorityUnitImpl", elements.get(0).subElements.get(0).class.name)
  		Assert::assertEquals("b", (elements.get(0).subElements.get(1) as Call).elementCall.name)
      	Assert::assertEquals(2, elements.get(1).subElements.size)
      	Assert::assertEquals("a", (elements.get(1).subElements.get(0) as Call).elementCall.name)
      	Assert::assertEquals("b", (elements.get(1).subElements.get(1) as Call).elementCall.name)
      	Assert::assertEquals(1, elements.get(2).subElements.size)
      	Assert::assertEquals("b", (elements.get(2).subElements.get(0) as Call).elementCall.name)
	}
	
	/**
	 * U9: Test IteratedUnit with natural number
	 */
	 @Test 
	 def testIteratedUnitNatural(){
	 	val model= ''' ePackageImport testmodel
						rule a(){}
						unit test(){
							for(3){
								a()
							}
						}'''.parse
		val unit = model.transformationsystem.get(1) as Unit
		val iteratedUnitTest = unit.unitElements.get(0) as IteratedUnit
		Assert::assertEquals(1,iteratedUnitTest.subElement.size)
		Assert::assertEquals("a",(iteratedUnitTest.subElement.get(0) as Call).elementCall.name)
		Assert::assertEquals(3,(iteratedUnitTest.iterations as NaturalValue).value)
	 }
	 
	 /**
	 * U10: Test IteratedUnit with real number
	 */
	 @Test 
	 def testIteratedUnitRealNumber(){
	 	val model= ''' ePackageImport testmodel
						rule a(){}
						unit b(){}
						unit test(){
							for(-3.5){
								a()
								b()
							}
							for(5.3){
								a()
							}
						}'''.parse
		val unit = model.transformationsystem.get(2) as Unit
		val iteratedUnitTest1 = unit.unitElements.get(0) as IteratedUnit
		val iteratedUnitTest2 = unit.unitElements.get(1) as IteratedUnit
		Assert::assertEquals(2,iteratedUnitTest1.subElement.size)
		Assert::assertEquals("a",(iteratedUnitTest1.subElement.get(0) as Call).elementCall.name)
		Assert::assertEquals("b",(iteratedUnitTest1.subElement.get(1) as Call).elementCall.name)
		Assert::assertEquals("-3.5",(iteratedUnitTest1.iterations as NumberValue).value)
		Assert::assertEquals("5.3",(iteratedUnitTest2.iterations as NumberValue).value)
	 }
	 
	 /**
	 * U11: Test IteratedUnit with parameter
	 */
	 @Test 
	 def testIteratedUnitParameter(){
	 	val model= ''' ePackageImport testmodel
						rule a(){}
						unit test(count:EInt){
							for(count){
								a()
								for(3){
									a()
								}
							}
						}'''.parse
		val unit = model.transformationsystem.get(1) as Unit
		val iteratedUnitTest = unit.unitElements.get(0) as IteratedUnit
		Assert::assertEquals(2,iteratedUnitTest.subElement.size)
		Assert::assertEquals("a",(iteratedUnitTest.subElement.get(0) as Call).elementCall.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.IteratedUnitImpl",iteratedUnitTest.subElement.get(1).class.name)
		Assert::assertEquals("count",(iteratedUnitTest.iterations as ParameterValue).value.name)
		Assert::assertEquals("EInt",(iteratedUnitTest.iterations as ParameterValue).value.type.enumType.literal)
	 }
	
	/**
	 * U12: Test LoopUnit
	 */
	 @Test 
	 def testLoopUnit(){
	 	val model= ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							while { a() while {b()}}
						}'''.parse
		val unit = model.transformationsystem.get(2) as Unit
		val loopUnitTest = unit.unitElements.get(0) as LoopUnit
		Assert::assertEquals(2,loopUnitTest.subElement.size)
		Assert::assertEquals("a",(loopUnitTest.subElement.get(0) as Call).elementCall.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.LoopUnitImpl",loopUnitTest.subElement.get(1).class.name)
	
	 }
	 
	 /**
	 * U13: Test SequentialUnit (implicitly and explicitly) 
	 */
	 @Test 
	 def testSequentialUnit(){
	 	val model= ''' ePackageImport testmodel
						rule a(){}
						unit b() {}
						unit test(){
							a()
							b()
							{	a() 
								a()
								{b()}
							}
						}'''.parse		
		val unit = model.transformationsystem.get(2) as Unit
		val sequentialUnitTest = unit.unitElements
		Assert::assertEquals(3,sequentialUnitTest.size)
		Assert::assertEquals("a",(sequentialUnitTest.get(0) as Call).elementCall.name)
		Assert::assertEquals("b",(sequentialUnitTest.get(1) as Call).elementCall.name)
		Assert::assertEquals(3,sequentialUnitTest.get(2).subSequence.size)
		Assert::assertEquals("a",(sequentialUnitTest.get(2).subSequence.get(0) as Call).elementCall.name)
		Assert::assertEquals("a",(sequentialUnitTest.get(2).subSequence.get(1) as Call).elementCall.name)
		Assert::assertEquals(1,sequentialUnitTest.get(2).subSequence.get(2).subSequence.size)
	 }
	
	/**
	 * U14: Teste Unit with rollback=true
	 */
	 @Test
	 def testRollbackTrue(){
	 	val model = ''' ePackageImport testmodel
						unit unitname(){
							rollback true
						}'''.parse
		val unit = model.transformationsystem.get(0) as Unit				
		val rollback = unit.unitElements.get(0) as Rollback
		Assert::assertEquals(true,rollback.rollback)
	 }
	 
	 /**
	 * U15: Test Unit with rollback=false
	 */
	 @Test
	 def testRollbackFalse(){
	 	val model = ''' ePackageImport testmodel
						unit unitname(){
							rollback false
						}'''.parse
		val unit = model.transformationsystem.get(0) as Unit				
		val rollback = unit.unitElements.get(0) as Rollback
		Assert::assertEquals(false,rollback.rollback)
	 }
	 
	 
	/**
	 * U16: Test Unit with strict=true
	 */
	 @Test
	 def testStrictTrue(){
	 	val model = ''' ePackageImport testmodel
						unit unitname(){
							strict true
						}'''.parse	
		val unit = model.transformationsystem.get(0) as Unit	
		val strict = unit.unitElements.get(0) as Strict
		Assert::assertEquals(true,strict.strict)
	 }
	 
	 /**
	 * U17: Test Unit with strict=false
	 */
	 @Test
	 def testStrictFalse(){
	 	val model = ''' ePackageImport testmodel
						unit unitname(){
							strict false
						}'''.parse
		val unit = model.transformationsystem.get(0) as Unit				
		val strict = unit.unitElements.get(0) as Strict
		Assert::assertEquals(false,strict.strict)
	 }
	 
	 /**
	 * U18: Test SequentialUnit with strict=false and rollback=false
	 */
	 @Test
	 def testSequentialUnitStrictRollback(){
	 	val model = ''' ePackageImport testmodel
	 					rule a(){}
	 					unit unitnamestrict(){
							strict false
							a()	
						}
						unit unitnamerollback(){
							rollback false
							a()	
						}'''.parse	
		var unit = model.transformationsystem.get(1) as Unit
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.StrictImpl",unit.unitElements.get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",unit.unitElements.get(1).class.name)
		unit = model.transformationsystem.get(2) as Unit
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.RollbackImpl",unit.unitElements.get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",unit.unitElements.get(1).class.name)
	}
	
	/**
	* U19: Test nested units
	*/
	@Test
	def testNestedUnits(){
		val model = ''' ePackageImport testmodel
	 					rule a(){}
	 					rule b(){}
	 					rule c(){}
	 					unit unitnamestrict(){
	 						
							while(){
								if(a() strict true) 
								then {priority [a(),{b() independent [b(),c()]}]}
								else {for(2){rollback false c()}}
							}
							b()	
						}'''.parse
		val unit = model.transformationsystem.get(3) as Unit
		Assert::assertEquals(2,unit.unitElements.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.ConditionalUnitImpl",unit.unitElements.get(0).subSequence.get(0).class.name)	
		val conditionalUnit =unit.unitElements.get(0).subSequence.get(0) as ConditionalUnit
		Assert::assertEquals(2,conditionalUnit.getIf().size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",conditionalUnit.getIf().get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.StrictImpl",conditionalUnit.getIf().get(1).class.name)
		Assert::assertEquals(1,conditionalUnit.getThen().size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.PriorityUnitImpl",conditionalUnit.getThen().get(0).class.name)
		val priorityUnit=conditionalUnit.getThen().get(0) as PriorityUnit
		Assert::assertEquals(2,priorityUnit.listOfLists.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",priorityUnit.listOfLists.get(0).subElements.get(0).class.name)
		Assert::assertEquals(2,priorityUnit.listOfLists.get(1).subElements.get(0).subSequence.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",priorityUnit.listOfLists.get(1).subElements.get(0).subSequence.get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.IndependentUnitImpl",priorityUnit.listOfLists.get(1).subElements.get(0).subSequence.get(1).class.name)
		val independentUnit=priorityUnit.listOfLists.get(1).subElements.get(0).subSequence.get(1) as IndependentUnit
		Assert::assertEquals(2,independentUnit.listOfLists.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",independentUnit.listOfLists.get(0).subElements.get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",independentUnit.listOfLists.get(1).subElements.get(0).class.name)
		Assert::assertEquals(1,conditionalUnit.getElse().size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.IteratedUnitImpl",conditionalUnit.getElse().get(0).class.name)	
		val iteratedUnit = conditionalUnit.getElse().get(0) as IteratedUnit
		Assert::assertEquals(2,(iteratedUnit.iterations as NaturalValue).value)
		Assert::assertEquals(2,iteratedUnit.subElement.size)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.RollbackImpl",iteratedUnit.subElement.get(0).class.name)
		Assert::assertEquals("org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl",iteratedUnit.subElement.get(1).class.name)
		Assert::assertEquals("b",(unit.unitElements.get(1) as Call).elementCall.name)
	}
	
	/**
	* U20: Test call with parameters
	*/
	@Test
	def testParameterCall(){
		val model = ''' ePackageImport testmodel
						rule a(p1:EString){}
						unit b(p1:EEList,p2:eclass){}
						unit unitname(p1:EString,p2:EEList,p3:eclass){
							a(p1)
							b(p2,p3)
						}'''.parse
		var unit = model.transformationsystem.get(2) as Unit
		Assert::assertEquals(1,(unit.unitElements.get(0) as Call).elementCall.parameters.size)
		Assert::assertEquals("p1",(unit.unitElements.get(0) as Call).elementCall.parameters.get(0).name)
		Assert::assertEquals("EString",(unit.unitElements.get(0) as Call).elementCall.parameters.get(0).type.enumType.literal)
		unit = model.transformationsystem.get(2) as Unit
		Assert::assertEquals(2,(unit.unitElements.get(1) as Call).elementCall.parameters.size)
		Assert::assertEquals("p1",(unit.unitElements.get(1) as Call).elementCall.parameters.get(0).name)
		Assert::assertEquals("EEList",(unit.unitElements.get(1) as Call).elementCall.parameters.get(0).type.enumType.literal)
		Assert::assertEquals("p2",(unit.unitElements.get(1) as Call).elementCall.parameters.get(1).name)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EClassImpl",(unit.unitElements.get(1) as Call).elementCall.parameters.get(1).type.type.class.name)
	}
	

}