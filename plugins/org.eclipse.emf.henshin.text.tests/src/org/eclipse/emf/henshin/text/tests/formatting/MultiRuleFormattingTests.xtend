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
class MultiRuleFormattingTests extends FormatterTester {
	@Inject extension ParseHelper<Model>

	/**
	 * M1: Test empty kernel rules with one multi rule
	 */
	@Test
	def testOneMultiRule() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
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
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * M2: Test empty kernel rule with nested multi rules
	 */
	@Test
	def testNestedMultiRule() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
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
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * M3: Test empty kernel rule with multi rules and actiontype markers
	 */
	@Test
	def testActionTypeMultiRule() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		multiRule multiname1 {
			graph {
				create node a:testmodel.type
				multiRule multiname1nested {
					graph {
						node d:testmodel.type
						delete node c:testmodel.type
						edges [(forbid d->c:testmodel.type)]
					}
				}
			}
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
	 * M4: Test Kernel-rule and multirule
	 */
	@Test
	def testKernelAndMultiRule() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		node a:testmodel.type
		node c:testmodel.type
		multiRule multiname {
			graph {
				edges [(create a->b:testmodel.type),
					(create c->b:testmodel.type)]
				create node b:testmodel.type
				reuse c {
					attribute1="test"
				}
			}
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
	 * M5: Test Multi Rule with parameter
	 */
	@Test
	def testParameterMultiRule() {
		val model =
		
'''ePackageImport testmodel

rule rulename(param1:EString,param2:EEList) {
	graph {
		multiRule multiname {
			graph {
				node a:testmodel.type {
					attribute1=param1
					attribute2=param2
				}
			}
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
	 * M6: Test set-Attribute
	 */
	@Test
	def setAttribute() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
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
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}
}
