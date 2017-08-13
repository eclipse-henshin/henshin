package org.eclipse.emf.henshin.text.tests.formatting

import javax.inject.Inject
import org.eclipse.emf.henshin.text.formatting2.Henshin_textFormatter
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
class FormulaFormattingTests extends FormatterTester {
	@Inject extension ParseHelper<Model>

	@Inject extension Henshin_textFormatter;

	/**
	 * F1: test forbid nodes and edges
	 */
	@Test
	def testForbid() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		edges [(forbid a->b:testmodel.type)]
		forbid node a:testmodel.type
		forbid node b:testmodel.type {}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * F2: test requires nodes and edges
	 */
	@Test
	def testRequire() {
		val model = 
'''ePackageImport testmodel

rule rulename() {
	graph {
		edges [(require a->b:testmodel.type)]
		require node a:testmodel.type
		require node b:testmodel.type {}
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * F3: Test attributes
	 */
	@Test
	def testAttributMarken() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	javaImport java
	graph {
		node a:testmodel.type {
			forbid attribute1="test"
			require attribute2=classname.call()
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
	 * F4: Test mapping between LHS and conditionGraph
	 */
	@Test
	def testLHSMapping() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		node a:testmodel.type
		node b:testmodel.type
		node c:testmodel.type
		matchingFormula {
			formula !conGraph
			conditionGraph conGraph {
				node d:testmodel.type
				reuse a {
					attribute="test"
				}
				edges [(d->b:testmodel.type)]
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
	 * F5: Test Mix of require and forbid markers
	 */
	@Test
	def testRequireForbidMarken() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		require node a:testmodel.type
		forbid node b:testmodel.type
	}
}

'''
		assertFormatted[
			toBeFormatted = model.replace("\n","").replace("\t","")
			expectation = model
		]
	}

	/**
	 * F6: Test nested conditionGraphs
	 */
	@Test
	def testConditionGraphNesting() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		node a:testmodel.type
		matchingFormula {
			formula !conGraph
			conditionGraph conGraph {
				node b:testmodel.type
				reuse a {
					attribute="test"
				}
				edges [(a->b:testmodel.type)]
				matchingFormula {
					formula conNestGraph
					conditionGraph conNestGraph {
						node c:testmodel.type
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
	 * F7:Test NOT
	 */
	@Test
	def testNOT() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		matchingFormula {
			formula !conGraph
			conditionGraph conGraph {
				node a:testmodel.type
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
	 * F8:Test OR
	 */
	@Test
	def testOR() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		matchingFormula {
			formula conGraphA OR conGraphB
			conditionGraph conGraphA {
				node a:testmodel.type
			}
			conditionGraph conGraphB {
				node b:testmodel.type
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
	 * F9:Test XOR
	 */
	@Test
	def testXOR() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		matchingFormula {
			formula conGraphA XOR conGraphB
			conditionGraph conGraphA {
				node a:testmodel.type
			}
			conditionGraph conGraphB {
				node b:testmodel.type
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
	 * F10:Test AND
	 */
	@Test
	def testAND() {
		val model =
'''ePackageImport testmodel

rule rulename() {
	graph {
		matchingFormula {
			formula conGraphA AND conGraphB
			conditionGraph conGraphA {
				node a:testmodel.type
			}
			conditionGraph conGraphB {
				node b:testmodel.type
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
//
//	/**
//	 * F11:test potential formula combinations
//	 */
//	@Test
//	def testFormula() {
//		val model = ''' ePackageImport testmodel
//						rule rulename(){
//							graph{
//								matchingFormula {
//									formula conGraphA AND (conGraphB OR !conGraphC) XOR conGraphD
//									conditionGraph conGraphA{
//										node a:testmodel.type
//									}
//									
//									conditionGraph conGraphB{
//										node b:testmodel.type
//									}
//									
//									conditionGraph conGraphC{
//										node c:testmodel.type
//									}
//									conditionGraph conGraphD{
//										node d:testmodel.type
//									}
//								}
//							}
//						}'''
//		assertFormatted[
//			toBeFormatted = model.replace("\n","").replace("\t","")
//			expectation = model
//		]
//	}

}
