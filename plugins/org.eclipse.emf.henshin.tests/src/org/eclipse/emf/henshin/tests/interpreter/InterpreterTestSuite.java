package org.eclipse.emf.henshin.tests.interpreter;

import org.eclipse.emf.henshin.interpreter.impl.Debugger;
import org.eclipse.emf.henshin.interpreter.impl.Interpreter;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;



@RunWith(JUnitPlatform.class)
@SelectPackages({"org.eclipse.emf.henshin.tests.interpreter"})
@SelectClasses({Interpreter.class, Debugger.class})

class InterpreterTestSuite {


}
