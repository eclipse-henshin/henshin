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
public class MultiRuleFormattingTests extends FormatterTester {
  @Inject
  @Extension
  private ParseHelper<Model> _parseHelper;
  
  /**
   * M1: Test empty kernel rules with one multi rule
   */
  @Test
  public void testOneMultiRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("reuse a {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("set attribute2=\"test2\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
   * M2: Test empty kernel rule with nested multi rules
   */
  @Test
  public void testNestedMultiRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname1 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("multiRule multiname1nested {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("node b:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname2 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
   * M3: Test empty kernel rule with multi rules and actiontype markers
   */
  @Test
  public void testActionTypeMultiRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname1 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("create node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("multiRule multiname1nested {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("node d:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("delete node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("edges [(forbid d->c:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
   * M4: Test Kernel-rule and multirule
   */
  @Test
  public void testKernelAndMultiRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("edges [(create a->b:testmodel.type),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("(create c->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("create node b:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("reuse c {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
   * M5: Test Multi Rule with parameter
   */
  @Test
  public void testParameterMultiRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(param1:EString,param2:EEList) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute1=param1");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute2=param2");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
   * M6: Test set-Attribute
   */
  @Test
  public void setAttribute() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("multiRule multiname {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("reuse a {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("set attribute2=\"test2\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
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
