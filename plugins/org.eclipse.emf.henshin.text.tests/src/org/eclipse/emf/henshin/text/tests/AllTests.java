package org.eclipse.emf.henshin.text.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({FileTests.class,
				RuleTests.class,
				UnitTests.class,
				TransformationTests.class,
				FormulaTests.class,
				MultiRuleTests.class})
public class AllTests {

}