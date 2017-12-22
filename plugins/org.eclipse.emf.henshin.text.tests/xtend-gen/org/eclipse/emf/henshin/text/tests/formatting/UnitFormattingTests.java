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
public class UnitFormattingTests extends FormatterTester {
  @Inject
  @Extension
  private ParseHelper<Model> _parseHelper;
  
  /**
   * U1: Test Unit with name, without parameters and without contents
   * 
   * COMMENT: Seems to be illegal case.
   */
  public void test01EmptyUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname() {}");
    final String model = _builder.toString();
    final Procedure1<FormatterTestRequest> _function = (FormatterTestRequest it) -> {
      it.setToBeFormatted(model.replace("\n", "").replace("\t", ""));
      it.setExpectation(model);
    };
    this.assertFormatted(_function);
  }
  
  /**
   * U2: Test Unit with parameters and without content
   */
  @Test
  public void test02ParameterUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname(p1:EString,p2:EEList,p3:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("unitname(p1,p2,p3)");
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
   * U3: Test unit which calls another unit
   */
  @Test
  public void test03CallUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("b()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
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
   * U4: Test tnit which calls a rule
   */
  @Test
  public void test04CallRule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
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
   * U5: Test independentUnit
   */
  @Test
  public void test05IndependentUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("independent[a(),a()b(),independent[b(),a()]]");
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
   * U6: Test conditionalUnit without else
   */
  @Test
  public void test06ConditionalUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (a() b()) then {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (b()) then {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("a()");
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
   * U7: Test conditionalUnit with else
   */
  @Test
  public void test07ConditionalUnitElse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (a()) then {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("b()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("b()");
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
   * U8: Test priorityUnit
   */
  @Test
  public void test08PriorityUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("priority [priority [a(),b()] b(), a() b(), b()]");
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
   * U9: Test IteratedUnit with natural number
   */
  @Test
  public void test09IteratedUnitNatural() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for (3) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
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
   * U10: Test IteratedUnit with real number
   */
  @Test
  public void test10IteratedUnitRealNumber() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for (3.5) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("b()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for (5.3) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
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
   * U11: Test IteratedUnit with parameter
   */
  @Test
  public void test11IteratedUnitParameter() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test(count:EInt) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for (count) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("for (3) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("a()");
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
   * U12: Test LoopUnit
   */
  @Test
  public void test12LoopUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("while {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("b()");
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
   * U13: Test SequentialUnit (implicitly and explicitly)
   */
  @Test
  public void test13SequentialUnit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit test() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("b()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("b()");
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
   * U14: Teste Unit with rollback=true
   */
  @Test
  public void test14RollbackTrue() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rollback true");
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
   * U15: Test Unit with rollback=false
   */
  @Test
  public void test15RollbackFalse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rollback false");
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
   * U16: Test Unit with strict=true
   */
  @Test
  public void test16StrictTrue() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("strict true");
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
   * U17: Test Unit with strict=false
   */
  @Test
  public void test17StrictFalse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("strict false");
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
   * U18: Test SequentialUnit with strict=false and rollback=false
   */
  @Test
  public void test18SequentialUnitStrictRollback() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitnamestrict() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("strict false");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitnamerollback() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rollback false");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a()");
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
   * U19: Test nested units
   */
  @Test
  public void test19NestedUnits() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule b() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule c() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitnamestrict() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (a() strict true) then {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("priority [a(),{b() independent [b(),c()]}]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("for (2) {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("rollback false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("c()");
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
    _builder.append("\t");
    _builder.append("b()");
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
   * U20: Test call with parameters
   */
  @Test
  public void test20ParameterCall() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ePackageImport testmodel");
    _builder.newLine();
    _builder.newLine();
    _builder.append("rule a(p1:EString) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit b(p1:EEList,p2:EString) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a(p2)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("unit unitname(p1:EString,p2:EEList,p3:EString) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("a(p1)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("b(p2,p3)");
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
