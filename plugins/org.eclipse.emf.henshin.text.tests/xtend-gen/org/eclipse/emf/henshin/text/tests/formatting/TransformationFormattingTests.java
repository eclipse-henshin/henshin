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
public class TransformationFormattingTests extends FormatterTester {
  @Inject
  @Extension
  private ParseHelper<Model> _parseHelper;
  
  /**
   * T1: Test of nodes and edges with <preserve> action
   */
  @Test
  public void testPreserve() {
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
    _builder.append("edges [(a->b:testmodel.type),");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("(preserve c->d:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node b:testmodel.type {}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("preserve node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("preserve node d:testmodel.type {}");
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
   * T2: Test of nodes and edges with <create> action
   */
  @Test
  public void testCreate() {
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
    _builder.append("edges [(create a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("create node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("create node b:testmodel.type {}");
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
   * T3: Test of nodes and edges with <delete> action
   */
  @Test
  public void testDelete() {
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
    _builder.append("edges [(delete a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("delete node a:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("delete node b:testmodel.type {}");
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
   * T4: Test of transformation
   */
  @Test
  public void testTransformation() {
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
    _builder.append("preserve node a1:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("node a2:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("attribut1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("delete node b:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("edges [(delete a->b:testmodel.type)]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("create node c:testmodel.type");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("edges [(create a->c:testmodel.type),");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("(a1->a2:testmodel.type)]");
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
   * T5: Test attribute
   */
  @Test
  public void testAttributMarken() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule rulename(param:EString) {");
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
    _builder.append("attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("preserve attribute2=classname.call()");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("attribute=classname.attribute");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("create node b:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("create attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("delete node c:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("delete attribute1=param");
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
   * T6: Test set-Attribute
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
    _builder.append("node a:testmodel.type {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("attribute1=\"test\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("set attribute1=\"test2\"");
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
