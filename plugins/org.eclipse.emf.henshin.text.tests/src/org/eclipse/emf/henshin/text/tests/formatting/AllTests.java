package org.eclipse.emf.henshin.text.tests.formatting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({RuleFormattingTests.class,
				UnitFormattingTests.class,
				TransformationFormattingTests.class,
				FormulaFormattingTests.class,
				MultiRuleFormattingTests.class})
public class AllTests {

}