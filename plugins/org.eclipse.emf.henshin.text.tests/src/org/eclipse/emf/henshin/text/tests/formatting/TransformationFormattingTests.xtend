package org.eclipse.emf.henshin.text.tests.formatting

import javax.inject.Inject
import org.eclipse.emf.henshin.text.tests.Henshin_textInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.formatter.FormatterTestHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Henshin_textInjectorProvider))
@Ignore
class TransformationFormattingTests {

	@Inject extension FormatterTestHelper

	/**
	 * T1: Test of nodes and edges with <preserve> action
	 */
	@Test
	def testPreserve() {
		val model = '''ePackageImport testmodel

rule rulename() {
	graph {
		edges [(a->b:testmodel.type),
			(preserve c->d:testmodel.type)]
		node a:testmodel.type
		node b:testmodel.type {}
		preserve node c:testmodel.type
		preserve node d:testmodel.type {}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

	/**
	 * T2: Test of nodes and edges with <create> action
	 */
	@Test
	def testCreate() {
		val model = '''ePackageImport testmodel

rule rulename() {
	graph {
		edges [(create a->b:testmodel.type)]
		create node a:testmodel.type
		create node b:testmodel.type {}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

	/**
	 * T3: Test of nodes and edges with <delete> action
	 */
	@Test
	def testDelete() {
		val model = '''ePackageImport testmodel

rule rulename() {
	graph {
		edges [(delete a->b:testmodel.type)]
		delete node a:testmodel.type
		delete node b:testmodel.type {}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

	/**
	 * T4: Test of transformation
	 */
	@Test
	def testTransformation() {
		val model = '''ePackageImport testmodel

rule rulename() {
	graph {
		preserve node a1:testmodel.type
		node a2:testmodel.type {
			attribut1="test"
		}
		delete node b:testmodel.type
		edges [(delete a->b:testmodel.type)]
		create node c:testmodel.type
		edges [(create a->c:testmodel.type),
			(a1->a2:testmodel.type)]
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

	/**
	 * T5: Test attribute
	 */
	@Test
	def testAttributMarken() {
		val model = '''ePackageImport testmodel

rule rulename(param:EString) {
	javaImport java
	graph {
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
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

	/**
	 * T6: Test set-Attribute
	 */
	@Test
	def setAttribute() {
		val model = '''ePackageImport testmodel

rule rulename() {
	graph {
		node a:testmodel.type {
			attribute1="test"
			set attribute1="test2"
		}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n", "").replace("\t", "")
			expectation = model
		]
	}

}
