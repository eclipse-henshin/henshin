package org.eclipse.emf.henshin.tests.compact;


import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"org.eclipse.emf.henshin.tests.compact"})
@SelectClasses({CModuleTests.class, CUnitTests.class, CRuleTests.class, CNodeTests.class})


class ModelTestSuite {

	

}
