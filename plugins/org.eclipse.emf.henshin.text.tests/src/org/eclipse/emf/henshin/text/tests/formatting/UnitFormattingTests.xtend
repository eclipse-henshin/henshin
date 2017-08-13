package org.eclipse.emf.henshin.text.tests.formatting

import org.eclipse.xtext.junit4.XtextRunner
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.InjectWith
import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.junit.Test
import org.junit.Assert
import org.eclipse.emf.henshin.text.henshin_text.Rollback
import org.eclipse.emf.henshin.text.henshin_text.Strict
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.Call
import org.eclipse.emf.henshin.text.henshin_text.NaturalValue
import org.eclipse.emf.henshin.text.henshin_text.NumberValue
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue
import org.eclipse.xtext.junit4.formatter.FormatterTester

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(org.eclipse.emf.henshin.text.tests.Henshin_textInjectorProvider))
class UnitFormattingTests  extends FormatterTester  {
	
	@Inject extension ParseHelper<Model>
	
	/**
	 * U1: Test Unit with name, without parameters and without contents
	 * 
	 * COMMENT: Seems to be illegal case.
	 */
//	@Test
	def test01EmptyUnit(){
		val model =
'''ePackageImport testmodel

unit unitname() {}'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U2: Test Unit with parameters and without content
	 */
	@Test
	def test02ParameterUnit(){
		val model =
'''ePackageImport testmodel

unit unitname(p1:EString,p2:EEList,p3:EInt) {
	unitname(p1,p2,p3)
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U3: Test unit which calls another unit
	 */
	@Test
	def test03CallUnit(){
		val model =
'''ePackageImport testmodel

unit a() {
	b()
}

unit b() {
	a()
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U4: Test tnit which calls a rule
	 */
	@Test
	def test04CallRule(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U5: Test independentUnit
	 */
	@Test
	def test05IndependentUnit(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	independent[a(),a()b(),independent[b(),a()]]
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U6: Test conditionalUnit without else
	 */
	@Test
	def test06ConditionalUnit(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	if (a() b()) then {
		if (b()) then {
			a()
		}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * U7: Test conditionalUnit with else
	 */
	@Test
	def test07ConditionalUnitElse(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	if (a()) then {
		a()
		b()
	} else {
		b()
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U8: Test priorityUnit
	 */
	@Test
	def test08PriorityUnit(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	priority [priority [a(),b()] b(), a() b(), b()]
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * U9: Test IteratedUnit with natural number
	 */
	 @Test 
	 def test09IteratedUnitNatural(){
	 	val model=
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit test() {
	for (3) {
		a()
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U10: Test IteratedUnit with real number
	 */
	 @Test 
	 def test10IteratedUnitRealNumber(){
	 	val model=
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	for (3.5) {
		a()
		b()
	}
	for (5.3) {
		a()
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U11: Test IteratedUnit with parameter
	 */
	 @Test 
	 def test11IteratedUnitParameter(){
	 	val model=
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit test(count:EInt) {
	for (count) {
		a()
		for (3) {
			a()
		}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	
	/**
	 * U12: Test LoopUnit
	 */
	 @Test 
	 def test12LoopUnit(){
	 	val model=
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	while {
		a()
		while {
			b()
		}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U13: Test SequentialUnit (implicitly and explicitly) 
	 */
	 @Test 
	 def test13SequentialUnit(){
	 	val model=
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit b() {
	a()
}

unit test() {
	a()
	b()
	{
		a()
		a()
		{
			b()
		}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	
	/**
	 * U14: Teste Unit with rollback=true
	 */
	 @Test
	 def test14RollbackTrue(){
	 	val model =
'''ePackageImport testmodel

unit unitname() {
	rollback true
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U15: Test Unit with rollback=false
	 */
	 @Test
	 def test15RollbackFalse(){
	 	val model =
'''ePackageImport testmodel

unit unitname() {
	rollback false
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 
	/**
	 * U16: Test Unit with strict=true
	 */
	 @Test
	 def test16StrictTrue(){
	 	val model =
'''ePackageImport testmodel

unit unitname() {
	strict true
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U17: Test Unit with strict=false
	 */
	 @Test
	 def test17StrictFalse(){
	 	val model =
'''ePackageImport testmodel

unit unitname() {
	strict false
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * U18: Test SequentialUnit with strict=false and rollback=false
	 */
	 @Test
	 def test18SequentialUnitStrictRollback(){
	 	val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

unit unitnamestrict() {
	strict false
	a()
}

unit unitnamerollback() {
	rollback false
	a()
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	* U19: Test nested units
	*/
	@Test
	def test19NestedUnits(){
		val model =
'''ePackageImport testmodel

rule a() {
	graph {}
}

rule b() {
	graph {}
}

rule c() {
	graph {}
}

unit unitnamestrict() {
	while {
		if (a() strict true) then {
			priority [a(),{b() independent [b(),c()]}]
		} else {
			for (2) {
				rollback false
				c()
			}
		}
	}
	b()
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	* U20: Test call with parameters
	*/
	@Test
	def test20ParameterCall(){
		val model =
'''ePackageImport testmodel

rule a(p1:EString) {
	graph {}
}

unit b(p1:EEList,p2:EString) {
	a(p2)
}

unit unitname(p1:EString,p2:EEList,p3:EString) {
	a(p1)
	b(p2,p3)
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]

   }
	

}
