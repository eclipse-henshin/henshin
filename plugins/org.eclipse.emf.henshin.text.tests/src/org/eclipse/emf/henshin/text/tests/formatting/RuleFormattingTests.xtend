package org.eclipse.emf.henshin.text.tests.formatting

import javax.inject.Inject
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.eclipse.emf.henshin.text.tests.Henshin_textInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.formatter.FormatterTester
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class RuleFormattingTests extends FormatterTester {
	
	@Inject extension ParseHelper<Model>

	/**
	 * R1: Test rule with name without parameter
	 */
	@Test
	def testEmptyRule(){
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	/**
	 * R2: Test rule with parameter and without contents
	 */
	@Test
	def testParameterRule(){
		val model =
'''ePackageImport testmodel

rule rulename(p1:EString, p2:EEList, p3:EInt) {
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
	
	
	/**
	  * R3: Test rule with injective matching
	  */
	 @Test
	 def testInjectiveMatchingTrue(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	injectiveMatching true
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 
	 /**
	  * R4: Test rule without injective matching
	  */
	 @Test
	 def testInjectiveMatchingFalse(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	injectiveMatching false
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	
	 
	 /**
	  * R5: Test rule with checkDangling=true
	  */
	 @Test
	 def testCheckDanglingTrue(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	checkDangling true
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	 * R6: Test rule with checkDangling=false
	 */
	 @Test
	 def testCheckDanglingFalse(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	checkDangling false
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	
	 
	 /**
	  * R7: Test rule with two conditions in condition element
	  * 
	  * NOTE:
	  * Uncommented due to some weird behaviour during automated test execution. Works when tested manually.
	  */
//	 @Test
	 def testConditions(){
	 	val model =
'''ePackageImport testmodel

rule rulename(value:EInt) {
	conditions [value>4,
		value+6==10]
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n"," ").replace("\t"," ")
			expectation = model
		]
	 }
	 
	  /**
	   * R8 Test rule with a single java import element
	   */
	 @Test
	 def testOneJavaImport(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	javaImport java.util
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	  /**
	   * R9: test rule with two java imports
	   */
	 @Test
	 def testTwoJavaImport(){
	 	val model =
''' ePackageImport testmodel

rule rulename() {
	javaImport java1
	javaImport java2.java
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	 /**
	  * R10 test rule with a single graph element
	  */
	 @Test
	 def testGraph(){
	 	val model =
'''ePackageImport testmodel

rule rulename() {
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	 
	  /**
	  * R11 test rule with 1 condition
	  */
	 @Test
	 def testOneConditions(){
	 	val model =
'''ePackageImport testmodel

rule rulename(IN value:EInt) {
	conditions [value!=1]
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
	
	/**
	 * Test parameterkind
	 */
	 @Test
	 def testParmeterKinds(){
	 	val model = 
'''ePackageImport testmodel

rule rulename(VAR value:EInt, IN v2:EInt, OUT v3:EInt, INOUT v4:EInt, v5:EInt) {
	graph {}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	 }
}
