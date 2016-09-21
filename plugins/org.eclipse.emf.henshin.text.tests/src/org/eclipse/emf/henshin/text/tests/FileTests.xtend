package org.eclipse.emf.henshin.text.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith

import javax.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.junit.Test
import org.junit.Assert


@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
class FileTests {
	
	@Inject extension ParseHelper<Model>
	
	/**
	 * Fi1: Test single ePackageImport-Element
	 */
	@Test
	def testOneEPackageImport(){
		val ecoreImport = '''ePackageImport bank'''.parse
      	val ePackageimports = ecoreImport.EPackageimports.get(0)
		Assert::assertEquals("org.eclipse.emf.ecore.impl.EPackageImpl", ePackageimports.ref.class.name)
	}
	
	/**
	 * Fi2: Test two ePackageImport-Elemente
	 */
	@Test
	def testTwoEPackageImport(){
		val ecoreImport = '''ePackageImport bank
							ePackageImport family'''.parse
      	for(ePackageimports:ecoreImport.EPackageimports){
			Assert::assertEquals("org.eclipse.emf.ecore.impl.EPackageImpl", ePackageimports.ref.class.name)
		}
	}
	
	/**
	 * Fi3: test several rules and units
	 */
		@Test
	def testUnitsRules(){
		val model = ''' ePackageImport bank
						rule rulename1(){}
						unit unitname1(){}
						unit unitname2(){}
						rule rulename2(){}
						'''.parse
		Assert::assertEquals("rulename1", model.transformationsystem.get(0).name)
		Assert::assertEquals("rulename2", model.transformationsystem.get(3).name)
		Assert::assertEquals("unitname1", model.transformationsystem.get(1).name)
		Assert::assertEquals("unitname2", model.transformationsystem.get(2).name)					
	}
	
}