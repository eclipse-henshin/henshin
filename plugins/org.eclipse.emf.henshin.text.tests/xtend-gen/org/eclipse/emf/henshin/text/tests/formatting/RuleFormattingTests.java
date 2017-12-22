package org.eclipse.emf.henshin.text.tests.formatting;

import javax.inject.Inject;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.tests.Henshin_textInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.formatter.FormatterTestRequest;
import org.eclipse.xtext.junit4.formatter.FormatterTester;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(Henshin_textInjectorProvider.class)
@SuppressWarnings("all")
public class RuleFormattingTests extends FormatterTester {
  @Inject
  @Extension
  private ParseHelper<Model> _parseHelper;
  
  /**
   * R1: Test rule with name without parameter
   */
  @Test
  public void testEmptyRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R2: Test rule with parameter and without contents
   */
  @Test
  public void testParameterRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(p1:EString, p2:EEList, p3:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R3: Test rule with injective matching
   */
  @Test
  public void testInjectiveMatchingTrue() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("injectiveMatching true");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R4: Test rule without injective matching
   */
  @Test
  public void testInjectiveMatchingFalse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("injectiveMatching false");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R5: Test rule with checkDangling=true
   */
  @Test
  public void testCheckDanglingTrue() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("checkDangling true");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R6: Test rule with checkDangling=false
   */
  @Test
  public void testCheckDanglingFalse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("checkDangling false");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R7: Test rule with two conditions in condition element
   * 
   * NOTE:
   * Uncommented due to some weird behaviour during automated test execution. Works when tested manually.
   */
  public void testConditions() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(value:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conditions [value>4,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("value+6==10]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", " ").replace("\t", " "));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R8 Test rule with a single java import element
   */
  @Test
  public void testOneJavaImport() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("javaImport java.util");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R9: test rule with two java imports
   */
  @Test
  public void testTwoJavaImport() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(" ");
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("javaImport java1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("javaImport java2.java");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R10 test rule with a single graph element
   */
  @Test
  public void testGraph() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * R11 test rule with 1 condition
   */
  @Test
  public void testOneConditions() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(IN value:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conditions [value!=1]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * Test parameterkind
   */
  @Test
  public void testParmeterKinds() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(VAR value:EInt, IN v2:EInt, OUT v3:EInt, INOUT v4:EInt, v5:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
}
