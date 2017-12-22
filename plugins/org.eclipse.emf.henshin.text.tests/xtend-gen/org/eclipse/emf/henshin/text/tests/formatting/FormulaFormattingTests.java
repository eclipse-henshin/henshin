package org.eclipse.emf.henshin.text.tests.formatting;

import javax.inject.Inject;
import org.eclipse.emf.henshin.text.formatting2.Henshin_textFormatter;
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
public class FormulaFormattingTests extends FormatterTester {
  @Inject
  @Extension
  private ParseHelper<Model> _parseHelper;
  
  @Inject
  @Extension
  private Henshin_textFormatter _henshin_textFormatter;
  
  /**
   * F1: test forbid nodes and edges
   */
  @Test
  public void testForbid() {
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
    _builder.append("edges [(forbid a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("forbid node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("forbid node b:testmodel.type {}");
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
   * F2: test requires nodes and edges
   */
  @Test
  public void testRequire() {
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
    _builder.append("edges [(require a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("require node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("require node b:testmodel.type {}");
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
   * F3: Test attributes
   */
  @Test
  public void testAttributMarken() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("javaImport java");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("forbid attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("require attribute2=classname.call()");
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
   * F4: Test mapping between LHS and conditionGraph
   */
  @Test
  public void testLHSMapping() {
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
    _builder.append("node b:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula !conGraph");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node d:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("reuse a {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("edges [(d->b:testmodel.type)]");
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
   * F5: Test Mix of require and forbid markers
   */
  @Test
  public void testRequireForbidMarken() {
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
    _builder.append("require node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("forbid node b:testmodel.type");
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
   * F6: Test nested conditionGraphs
   */
  @Test
  public void testConditionGraphNesting() {
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
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula !conGraph");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node b:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("reuse a {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("attribute=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("edges [(a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("formula conNestGraph");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("conditionGraph conNestGraph {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("node c:testmodel.type");
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
   * F7:Test NOT
   */
  @Test
  public void testNOT() {
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
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula !conGraph");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraph {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type");
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
   * F8:Test OR
   */
  @Test
  public void testOR() {
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
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula conGraphA OR conGraphB");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphA {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphB {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node b:testmodel.type");
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
   * F9:Test XOR
   */
  @Test
  public void testXOR() {
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
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula conGraphA XOR conGraphB");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphA {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphB {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node b:testmodel.type");
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
   * F10:Test AND
   */
  @Test
  public void testAND() {
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
    _builder.append("matchingFormula {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("formula conGraphA AND conGraphB");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphA {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("conditionGraph conGraphB {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("node b:testmodel.type");
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
