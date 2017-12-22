package org.eclipse.emf.henshin.text.ui.util;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.text.henshin_text.Call;
import org.eclipse.emf.henshin.text.henshin_text.ConditionalUnit;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textFactory;
import org.eclipse.emf.henshin.text.henshin_text.IndependentUnit;
import org.eclipse.emf.henshin.text.henshin_text.IteratedUnit;
import org.eclipse.emf.henshin.text.henshin_text.LoopUnit;
import org.eclipse.emf.henshin.text.henshin_text.Parameter;
import org.eclipse.emf.henshin.text.henshin_text.ParameterType;
import org.eclipse.emf.henshin.text.henshin_text.PriorityUnit;
import org.eclipse.emf.henshin.text.henshin_text.SequentialProperties;
import org.eclipse.emf.henshin.text.henshin_text.Type;
import org.eclipse.emf.henshin.text.henshin_text.Unit;
import org.eclipse.emf.henshin.text.henshin_text.UnitElement;
import org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.ConditionalUnitImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.IndependentUnitImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.IteratedUnitImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.LoopUnitImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.PriorityUnitImpl;
import org.eclipse.emf.henshin.text.henshin_text.impl.SequentialPropertiesImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ModifyModelUnits {
  private long seed;
  
  private boolean testSetup = false;
  
  public ModifyModelUnits(final long seed) {
    this.seed = seed;
    this.testSetup = true;
  }
  
  public ModifyModelUnits() {
  }
  
  /**
   * Entpackt die in einer Unit geschachtelten Subunits in einzelne elementare Units
   * 
   * @param unit Unit mit geschachtelten Subunits
   * @param level Ebene der Schachtelung
   * @return Liste von elementaren Units
   */
  public List<Unit> flat(final Unit unit, final int level, final Call unitCall) {
    List<Unit> erg = new ArrayList<Unit>();
    Boolean _isSequence = this.isSequence(unit.getUnitElements());
    boolean _equals = ((_isSequence).booleanValue() == false);
    if (_equals) {
      UnitElement _get = unit.getUnitElements().get(0);
      if ((_get instanceof ConditionalUnitImpl)) {
        UnitElement _get_1 = unit.getUnitElements().get(0);
        ConditionalUnit help = ((ConditionalUnit) _get_1);
        Boolean _isCallOnly = this.isCallOnly(help.getIf());
        boolean _not = (!(_isCallOnly).booleanValue());
        if (_not) {
          String _name = unit.getName();
          String _plus = (_name + "IF");
          String _plus_1 = (_plus + Integer.valueOf(level));
          String _plus_2 = (_plus_1 + "_");
          int _returnRandomeNumber = this.returnRandomeNumber();
          String _plus_3 = (_plus_2 + Integer.valueOf(_returnRandomeNumber));
          Unit newSubUnit = this.createUnit(_plus_3, help.getIf(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
          UnitElement _get_2 = unit.getUnitElements().get(0);
          ((ConditionalUnit) _get_2).getIf().clear();
          Call call = Henshin_textFactory.eINSTANCE.createCall();
          call.setElementCall(newSubUnit);
          call.getParameters().addAll(unit.getParameters());
          UnitElement _get_3 = unit.getUnitElements().get(0);
          ((ConditionalUnit) _get_3).getIf().add(call);
          List<Unit> newSubUnitList = this.flat(newSubUnit, (level + 1), call);
          erg.addAll(newSubUnitList);
        } else {
          boolean _notEquals = (!Objects.equal(unitCall, null));
          if (_notEquals) {
            int parameterIndex = 0;
            EList<Parameter> _parameters = unitCall.getParameters();
            for (final Parameter p : _parameters) {
              {
                UnitElement _get_4 = help.getIf().get(0);
                int indexInSubCall = ((Call) _get_4).getParameters().indexOf(p);
                if ((indexInSubCall != (-1))) {
                  UnitElement _get_5 = help.getIf().get(0);
                  ((Call) _get_5).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex));
                }
                parameterIndex++;
              }
            }
          }
        }
        Boolean _isCallOnly_1 = this.isCallOnly(help.getThen());
        boolean _not_1 = (!(_isCallOnly_1).booleanValue());
        if (_not_1) {
          String _name_1 = unit.getName();
          String _plus_4 = (_name_1 + "THEN");
          String _plus_5 = (_plus_4 + Integer.valueOf(level));
          String _plus_6 = (_plus_5 + "_");
          int _returnRandomeNumber_1 = this.returnRandomeNumber();
          String _plus_7 = (_plus_6 + Integer.valueOf(_returnRandomeNumber_1));
          Unit newSubUnit_1 = this.createUnit(_plus_7, help.getThen(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
          UnitElement _get_4 = unit.getUnitElements().get(0);
          ((ConditionalUnit) _get_4).getThen().clear();
          Call call_1 = Henshin_textFactory.eINSTANCE.createCall();
          call_1.setElementCall(newSubUnit_1);
          call_1.getParameters().addAll(unit.getParameters());
          UnitElement _get_5 = unit.getUnitElements().get(0);
          ((ConditionalUnit) _get_5).getThen().add(call_1);
          List<Unit> newSubUnitList_1 = this.flat(newSubUnit_1, (level + 1), call_1);
          erg.addAll(newSubUnitList_1);
        } else {
          boolean _notEquals_1 = (!Objects.equal(unitCall, null));
          if (_notEquals_1) {
            int parameterIndex_1 = 0;
            EList<Parameter> _parameters_1 = unitCall.getParameters();
            for (final Parameter p_1 : _parameters_1) {
              {
                UnitElement _get_6 = help.getThen().get(0);
                int indexInSubCall = ((Call) _get_6).getParameters().indexOf(p_1);
                if ((indexInSubCall != (-1))) {
                  UnitElement _get_7 = help.getThen().get(0);
                  ((Call) _get_7).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_1));
                }
                parameterIndex_1++;
              }
            }
          }
        }
        int _size = help.getElse().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          Boolean _isCallOnly_2 = this.isCallOnly(help.getElse());
          boolean _not_2 = (!(_isCallOnly_2).booleanValue());
          if (_not_2) {
            String _name_2 = unit.getName();
            String _plus_8 = (_name_2 + "ELSE");
            String _plus_9 = (_plus_8 + Integer.valueOf(level));
            String _plus_10 = (_plus_9 + "_");
            int _returnRandomeNumber_2 = this.returnRandomeNumber();
            String _plus_11 = (_plus_10 + Integer.valueOf(_returnRandomeNumber_2));
            Unit newSubUnit_2 = this.createUnit(_plus_11, help.getElse(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
            UnitElement _get_6 = unit.getUnitElements().get(0);
            ((ConditionalUnit) _get_6).getElse().clear();
            Call call_2 = Henshin_textFactory.eINSTANCE.createCall();
            call_2.setElementCall(newSubUnit_2);
            call_2.getParameters().addAll(unit.getParameters());
            UnitElement _get_7 = unit.getUnitElements().get(0);
            ((ConditionalUnit) _get_7).getElse().add(call_2);
            List<Unit> newSubUnitList_2 = this.flat(newSubUnit_2, (level + 1), call_2);
            erg.addAll(newSubUnitList_2);
          } else {
            boolean _notEquals_2 = (!Objects.equal(unitCall, null));
            if (_notEquals_2) {
              int parameterIndex_2 = 0;
              EList<Parameter> _parameters_2 = unitCall.getParameters();
              for (final Parameter p_2 : _parameters_2) {
                {
                  UnitElement _get_8 = help.getElse().get(0);
                  int indexInSubCall = ((Call) _get_8).getParameters().indexOf(p_2);
                  if ((indexInSubCall != (-1))) {
                    UnitElement _get_9 = help.getThen().get(0);
                    ((Call) _get_9).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_2));
                  }
                  parameterIndex_2++;
                }
              }
            }
          }
        }
        erg.add(unit);
      } else {
        UnitElement _get_8 = unit.getUnitElements().get(0);
        if ((_get_8 instanceof IteratedUnitImpl)) {
          UnitElement _get_9 = unit.getUnitElements().get(0);
          IteratedUnit help_1 = ((IteratedUnit) _get_9);
          Boolean _isCallOnly_3 = this.isCallOnly(help_1.getSubElement());
          boolean _not_3 = (!(_isCallOnly_3).booleanValue());
          if (_not_3) {
            String _name_3 = unit.getName();
            String _plus_12 = (_name_3 + "ITERATED");
            String _plus_13 = (_plus_12 + Integer.valueOf(level));
            String _plus_14 = (_plus_13 + "_");
            int _returnRandomeNumber_3 = this.returnRandomeNumber();
            String _plus_15 = (_plus_14 + Integer.valueOf(_returnRandomeNumber_3));
            Unit newSubUnit_3 = this.createUnit(_plus_15, help_1.getSubElement(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
            UnitElement _get_10 = unit.getUnitElements().get(0);
            ((IteratedUnit) _get_10).getSubElement().clear();
            Call call_3 = Henshin_textFactory.eINSTANCE.createCall();
            call_3.setElementCall(newSubUnit_3);
            call_3.getParameters().addAll(unit.getParameters());
            EList<Parameter> _parameters_3 = unit.getParameters();
            for (final Parameter p_3 : _parameters_3) {
              Parameter param = Henshin_textFactory.eINSTANCE.createParameter();
            }
            UnitElement _get_11 = unit.getUnitElements().get(0);
            ((IteratedUnit) _get_11).getSubElement().add(call_3);
            erg.add(unit);
            List<Unit> newSubUnitList_3 = this.flat(newSubUnit_3, (level + 1), call_3);
            erg.addAll(newSubUnitList_3);
          } else {
            boolean _notEquals_3 = (!Objects.equal(unitCall, null));
            if (_notEquals_3) {
              int parameterIndex_3 = 0;
              EList<Parameter> _parameters_4 = unitCall.getParameters();
              for (final Parameter p_4 : _parameters_4) {
                {
                  UnitElement _get_12 = help_1.getSubElement().get(0);
                  int indexInSubCall = ((Call) _get_12).getParameters().indexOf(p_4);
                  if ((indexInSubCall != (-1))) {
                    UnitElement _get_13 = help_1.getSubElement().get(0);
                    ((Call) _get_13).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_3));
                  }
                  parameterIndex_3++;
                }
              }
            }
            erg.add(unit);
          }
        } else {
          UnitElement _get_12 = unit.getUnitElements().get(0);
          if ((_get_12 instanceof LoopUnitImpl)) {
            UnitElement _get_13 = unit.getUnitElements().get(0);
            LoopUnit help_2 = ((LoopUnit) _get_13);
            Boolean _isCallOnly_4 = this.isCallOnly(help_2.getSubElement());
            boolean _not_4 = (!(_isCallOnly_4).booleanValue());
            if (_not_4) {
              String _name_4 = unit.getName();
              String _plus_16 = (_name_4 + "LOOP");
              String _plus_17 = (_plus_16 + Integer.valueOf(level));
              String _plus_18 = (_plus_17 + "_");
              int _returnRandomeNumber_4 = this.returnRandomeNumber();
              String _plus_19 = (_plus_18 + Integer.valueOf(_returnRandomeNumber_4));
              Unit newSubUnit_4 = this.createUnit(_plus_19, help_2.getSubElement(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
              UnitElement _get_14 = unit.getUnitElements().get(0);
              ((LoopUnit) _get_14).getSubElement().clear();
              Call call_4 = Henshin_textFactory.eINSTANCE.createCall();
              call_4.setElementCall(newSubUnit_4);
              call_4.getParameters().addAll(unit.getParameters());
              UnitElement _get_15 = unit.getUnitElements().get(0);
              ((LoopUnit) _get_15).getSubElement().add(call_4);
              erg.add(unit);
              List<Unit> newSubUnitList_4 = this.flat(newSubUnit_4, (level + 1), call_4);
              erg.addAll(newSubUnitList_4);
            } else {
              boolean _notEquals_4 = (!Objects.equal(unitCall, null));
              if (_notEquals_4) {
                int parameterIndex_4 = 0;
                EList<Parameter> _parameters_5 = unitCall.getParameters();
                for (final Parameter p_5 : _parameters_5) {
                  {
                    UnitElement _get_16 = help_2.getSubElement().get(0);
                    int indexInSubCall = ((Call) _get_16).getParameters().indexOf(p_5);
                    if ((indexInSubCall != (-1))) {
                      UnitElement _get_17 = help_2.getSubElement().get(0);
                      ((Call) _get_17).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_4));
                    }
                    parameterIndex_4++;
                  }
                }
              }
              erg.add(unit);
            }
          } else {
            UnitElement _get_16 = unit.getUnitElements().get(0);
            if ((_get_16 instanceof PriorityUnitImpl)) {
              UnitElement _get_17 = unit.getUnitElements().get(0);
              PriorityUnit help_3 = ((PriorityUnit) _get_17);
              int index = 0;
              EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists = help_3.getListOfLists();
              for (final org.eclipse.emf.henshin.text.henshin_text.List sub : _listOfLists) {
                Boolean _isCallOnly_5 = this.isCallOnly(sub.getSubElements());
                boolean _not_5 = (!(_isCallOnly_5).booleanValue());
                if (_not_5) {
                  String _name_5 = unit.getName();
                  String _plus_20 = (_name_5 + "PRIORITY");
                  String _plus_21 = (_plus_20 + Integer.valueOf(level));
                  String _plus_22 = (_plus_21 + "_");
                  String _plus_23 = (_plus_22 + Integer.valueOf(index));
                  String _plus_24 = (_plus_23 + "_");
                  int _returnRandomeNumber_5 = this.returnRandomeNumber();
                  String _plus_25 = (_plus_24 + Integer.valueOf(_returnRandomeNumber_5));
                  Unit newSubUnit_5 = this.createUnit(_plus_25, sub.getSubElements(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
                  UnitElement _get_18 = unit.getUnitElements().get(0);
                  ((PriorityUnit) _get_18).getListOfLists().get(index).getSubElements().clear();
                  Call call_5 = Henshin_textFactory.eINSTANCE.createCall();
                  call_5.setElementCall(newSubUnit_5);
                  call_5.getParameters().addAll(unit.getParameters());
                  UnitElement _get_19 = unit.getUnitElements().get(0);
                  ((PriorityUnit) _get_19).getListOfLists().get(index).getSubElements().add(call_5);
                  List<Unit> newSubUnitList_5 = this.flat(newSubUnit_5, (level + 1), call_5);
                  erg.addAll(newSubUnitList_5);
                  index++;
                } else {
                  boolean _notEquals_5 = (!Objects.equal(unitCall, null));
                  if (_notEquals_5) {
                    int parameterIndex_5 = 0;
                    EList<Parameter> _parameters_6 = unitCall.getParameters();
                    for (final Parameter p_6 : _parameters_6) {
                      {
                        UnitElement _get_20 = sub.getSubElements().get(0);
                        int indexInSubCall = ((Call) _get_20).getParameters().indexOf(p_6);
                        if ((indexInSubCall != (-1))) {
                          UnitElement _get_21 = sub.getSubElements().get(0);
                          ((Call) _get_21).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_5));
                        }
                        parameterIndex_5++;
                      }
                    }
                  }
                }
              }
              erg.add(unit);
            } else {
              UnitElement _get_20 = unit.getUnitElements().get(0);
              if ((_get_20 instanceof IndependentUnitImpl)) {
                UnitElement _get_21 = unit.getUnitElements().get(0);
                IndependentUnit help_4 = ((IndependentUnit) _get_21);
                int index_1 = 0;
                EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_1 = help_4.getListOfLists();
                for (final org.eclipse.emf.henshin.text.henshin_text.List sub_1 : _listOfLists_1) {
                  Boolean _isCallOnly_6 = this.isCallOnly(sub_1.getSubElements());
                  boolean _not_6 = (!(_isCallOnly_6).booleanValue());
                  if (_not_6) {
                    String _name_6 = unit.getName();
                    String _plus_26 = (_name_6 + "INDEPENDENT");
                    String _plus_27 = (_plus_26 + Integer.valueOf(level));
                    String _plus_28 = (_plus_27 + "_");
                    String _plus_29 = (_plus_28 + Integer.valueOf(index_1));
                    String _plus_30 = (_plus_29 + "_");
                    int _returnRandomeNumber_6 = this.returnRandomeNumber();
                    String _plus_31 = (_plus_30 + Integer.valueOf(_returnRandomeNumber_6));
                    Unit newSubUnit_6 = this.createUnit(_plus_31, sub_1.getSubElements(), ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
                    UnitElement _get_22 = unit.getUnitElements().get(0);
                    ((IndependentUnit) _get_22).getListOfLists().get(index_1).getSubElements().clear();
                    Call call_6 = Henshin_textFactory.eINSTANCE.createCall();
                    call_6.setElementCall(newSubUnit_6);
                    call_6.getParameters().addAll(unit.getParameters());
                    UnitElement _get_23 = unit.getUnitElements().get(0);
                    ((IndependentUnit) _get_23).getListOfLists().get(index_1).getSubElements().add(call_6);
                    List<Unit> newSubUnitList_6 = this.flat(newSubUnit_6, (level + 1), call_6);
                    erg.addAll(newSubUnitList_6);
                    index_1++;
                  } else {
                    boolean _notEquals_6 = (!Objects.equal(unitCall, null));
                    if (_notEquals_6) {
                      int parameterIndex_6 = 0;
                      EList<Parameter> _parameters_7 = unitCall.getParameters();
                      for (final Parameter p_7 : _parameters_7) {
                        {
                          UnitElement _get_24 = sub_1.getSubElements().get(0);
                          int indexInSubCall = ((Call) _get_24).getParameters().indexOf(p_7);
                          if ((indexInSubCall != (-1))) {
                            UnitElement _get_25 = sub_1.getSubElements().get(0);
                            ((Call) _get_25).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_6));
                          }
                          parameterIndex_6++;
                        }
                      }
                    }
                  }
                }
                erg.add(unit);
              }
            }
          }
        }
      }
    } else {
      Boolean _isCallSequence = this.isCallSequence(unit.getUnitElements());
      if ((_isCallSequence).booleanValue()) {
        erg.add(unit);
      } else {
        int index_2 = 0;
        EList<UnitElement> _unitElements = unit.getUnitElements();
        for (final UnitElement element : _unitElements) {
          {
            if (((!(element instanceof SequentialProperties)) && (!(element instanceof Call)))) {
              List<UnitElement> subElements = new ArrayList<UnitElement>();
              int _size_1 = element.getSubSequence().size();
              boolean _greaterThan_1 = (_size_1 > 0);
              if (_greaterThan_1) {
                subElements.addAll(element.getSubSequence());
                element.getSubSequence().clear();
              } else {
                subElements.add(element);
              }
              String _name_7 = unit.getName();
              String _plus_32 = (_name_7 + "Sequence");
              String _plus_33 = (_plus_32 + Integer.valueOf(level));
              String _plus_34 = (_plus_33 + "_");
              String _plus_35 = (_plus_34 + Integer.valueOf(index_2));
              String _plus_36 = (_plus_35 + "_");
              int _returnRandomeNumber_7 = this.returnRandomeNumber();
              String _plus_37 = (_plus_36 + Integer.valueOf(_returnRandomeNumber_7));
              Unit newSubUnit_7 = this.createUnit(_plus_37, subElements, ((List<Parameter>)Conversions.doWrapArray(((Parameter[])Conversions.unwrapArray(unit.getParameters(), Parameter.class)).clone())));
              Call call_7 = Henshin_textFactory.eINSTANCE.createCall();
              call_7.setElementCall(newSubUnit_7);
              call_7.getParameters().addAll(unit.getParameters());
              unit.getUnitElements().set(index_2, call_7);
              List<Unit> newSubUnitList_7 = this.flat(newSubUnit_7, (level + 1), call_7);
              erg.addAll(newSubUnitList_7);
            } else {
              if (((!Objects.equal(unitCall, null)) && (element instanceof Call))) {
                int parameterIndex_7 = 0;
                EList<Parameter> _parameters_8 = unitCall.getParameters();
                for (final Parameter p_8 : _parameters_8) {
                  {
                    int indexInSubCall = ((Call) element).getParameters().indexOf(p_8);
                    if ((indexInSubCall != (-1))) {
                      ((Call) element).getParameters().set(indexInSubCall, unitCall.getElementCall().getParameters().get(parameterIndex_7));
                    }
                    parameterIndex_7++;
                  }
                }
              }
            }
            index_2++;
          }
        }
        erg.add(unit);
      }
    }
    return erg;
  }
  
  private int returnRandomeNumber() {
    if (this.testSetup) {
      Random randomGenerator = new Random(this.seed);
      return randomGenerator.nextInt(1000);
    } else {
      Random randomGenerator_1 = new Random();
      return randomGenerator_1.nextInt(1000);
    }
  }
  
  /**
   * Gibt eine Kopie des übergebenen ParameterType-Objekts zurück
   * 
   * @param type Typ eines org.eclipse.emf.henshin.text.henshin_text.Parameter
   */
  public ParameterType getParameterType(final ParameterType type) {
    ParameterType result = Henshin_textFactory.eINSTANCE.createParameterType();
    EClass _type = type.getType();
    boolean _notEquals = (!Objects.equal(_type, null));
    if (_notEquals) {
      result.setType(type.getType());
    } else {
      Type value = null;
      String _literal = type.getEnumType().getLiteral();
      if (_literal != null) {
        switch (_literal) {
          case "EBigDecimal":
            value = Type.EBIG_DECIMAL;
            break;
          case "EBigInteger":
            value = Type.EBIG_INTEGER;
            break;
          case "EBoolean":
            value = Type.EBOOLEAN;
            break;
          case "EBooleanObject":
            value = Type.EBOOLEAN_OBJECT;
            break;
          case "EByte":
            value = Type.EBYTE;
            break;
          case "EByteArray":
            value = Type.EBYTE_ARRAY;
            break;
          case "EByteObject":
            value = Type.EBYTE_OBJECT;
            break;
          case "EChar":
            value = Type.ECHAR;
            break;
          case "ECharacterObject":
            value = Type.ECHARACTER_OBJECT;
            break;
          case "EDate":
            value = Type.EDATE;
            break;
          case "EDiagnosticChain":
            value = Type.EDIAGNOSTIC_CHAIN;
            break;
          case "EDouble":
            value = Type.EDOUBLE;
            break;
          case "EDoubleObject":
            value = Type.EDOUBLE_OBJECT;
            break;
          case "EEList":
            value = Type.EE_LIST;
            break;
          case "EEnumerator":
            value = Type.EENUMERATOR;
            break;
          case "EFeatureMap":
            value = Type.EFEATURE_MAP;
            break;
          case "EFeatureMapEntry":
            value = Type.EFEATURE_MAP_ENTRY;
            break;
          case "EFloat":
            value = Type.EFLOAT;
            break;
          case "EFloatObject":
            value = Type.EFLOAT_OBJECT;
            break;
          case "EInt":
            value = Type.EINT;
            break;
          case "EIntegerObject":
            value = Type.EINTEGER_OBJECT;
            break;
          case "ETreeIterator":
            value = Type.ETREE_ITERATOR;
            break;
          case "EInvocationTargetException":
            value = Type.EINVOCATION_TARGET_EXCEPTION;
            break;
          case "EJavaClass":
            value = Type.EJAVA_CLASS;
            break;
          case "EJavaObject":
            value = Type.EJAVA_OBJECT;
            break;
          case "ELong":
            value = Type.ELONG;
            break;
          case "ELongObject":
            value = Type.ELONG_OBJECT;
            break;
          case "EMap":
            value = Type.EMAP;
            break;
          case "EResource":
            value = Type.ERESOURCE;
            break;
          case "EResourceSet":
            value = Type.ERESOURCE_SET;
            break;
          case "EShort":
            value = Type.ESHORT;
            break;
          case "EShortObject":
            value = Type.ESHORT_OBJECT;
            break;
          case "EString":
            value = Type.ESTRING;
            break;
        }
      }
      result.setEnumType(value);
    }
    return result;
  }
  
  /**
   * Erzeugt eine neue Unit
   * 
   * @param name Name der neuen Unit
   * @param unitElement Elemente der neuen Unit
   * @param parameter Parameter der neuen Unit
   * @return Neu erzeugte Unit
   */
  public Unit createUnit(final String name, final List<UnitElement> unitElement, final List<Parameter> parameter) {
    Unit helpUnit = Henshin_textFactory.eINSTANCE.createUnit();
    helpUnit.setName(name);
    for (final Parameter param : parameter) {
      {
        Parameter helpParameter = Henshin_textFactory.eINSTANCE.createParameter();
        String _name = param.getName();
        String _plus = (_name + "_");
        String _plus_1 = (_plus + name);
        helpParameter.setName(_plus_1);
        helpParameter.setKind(param.getKind());
        helpParameter.setType(this.getParameterType(param.getType()));
        helpUnit.getParameters().add(helpParameter);
      }
    }
    Boolean _isSequence = this.isSequence(unitElement);
    if ((_isSequence).booleanValue()) {
      helpUnit.getUnitElements().addAll(unitElement);
    } else {
      UnitElement _get = unitElement.get(0);
      if ((_get instanceof ConditionalUnitImpl)) {
        ConditionalUnit helpUnitElement = Henshin_textFactory.eINSTANCE.createConditionalUnit();
        UnitElement _get_1 = unitElement.get(0);
        helpUnitElement.getIf().addAll(((ConditionalUnit) _get_1).getIf());
        UnitElement _get_2 = unitElement.get(0);
        helpUnitElement.getThen().addAll(((ConditionalUnit) _get_2).getThen());
        UnitElement _get_3 = unitElement.get(0);
        int _size = ((ConditionalUnit) _get_3).getElse().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          UnitElement _get_4 = unitElement.get(0);
          helpUnitElement.getElse().addAll(((ConditionalUnit) _get_4).getElse());
        }
        helpUnit.getUnitElements().add(helpUnitElement);
      } else {
        UnitElement _get_5 = unitElement.get(0);
        if ((_get_5 instanceof IteratedUnitImpl)) {
          IteratedUnit helpUnitElement_1 = Henshin_textFactory.eINSTANCE.createIteratedUnit();
          UnitElement _get_6 = unitElement.get(0);
          helpUnitElement_1.setIterations(((IteratedUnit) _get_6).getIterations());
          UnitElement _get_7 = unitElement.get(0);
          helpUnitElement_1.getSubElement().addAll(((IteratedUnit) _get_7).getSubElement());
          helpUnit.getUnitElements().add(helpUnitElement_1);
        } else {
          UnitElement _get_8 = unitElement.get(0);
          if ((_get_8 instanceof LoopUnitImpl)) {
            LoopUnit helpUnitElement_2 = Henshin_textFactory.eINSTANCE.createLoopUnit();
            UnitElement _get_9 = unitElement.get(0);
            helpUnitElement_2.getSubElement().addAll(((LoopUnit) _get_9).getSubElement());
            helpUnit.getUnitElements().add(helpUnitElement_2);
          } else {
            UnitElement _get_10 = unitElement.get(0);
            if ((_get_10 instanceof PriorityUnitImpl)) {
              PriorityUnit helpUnitElement_3 = Henshin_textFactory.eINSTANCE.createPriorityUnit();
              UnitElement _get_11 = unitElement.get(0);
              helpUnitElement_3.getListOfLists().addAll(((PriorityUnit) _get_11).getListOfLists());
              helpUnit.getUnitElements().add(helpUnitElement_3);
            } else {
              UnitElement _get_12 = unitElement.get(0);
              if ((_get_12 instanceof IndependentUnitImpl)) {
                IndependentUnit helpUnitElement_4 = Henshin_textFactory.eINSTANCE.createIndependentUnit();
                UnitElement _get_13 = unitElement.get(0);
                helpUnitElement_4.getListOfLists().addAll(((IndependentUnit) _get_13).getListOfLists());
                helpUnit.getUnitElements().add(helpUnitElement_4);
              }
            }
          }
        }
      }
    }
    return helpUnit;
  }
  
  /**
   * Prueft ob eine Liste von Elementen einer Unit eine SequentialUnit bilden
   * 
   * @param unitElements Uniterelemente einer Unit
   * @return True: Unit ist eine SequentialUnit, False: Unit ist keine SequentialUnit
   */
  public Boolean isSequence(final List<UnitElement> unitElements) {
    if (((((unitElements.size() > 1) || (IterableExtensions.size(Iterables.<CallImpl>filter(unitElements, CallImpl.class)) > 0)) || (IterableExtensions.size(Iterables.<SequentialPropertiesImpl>filter(unitElements, SequentialPropertiesImpl.class)) > 0)) || ((unitElements.size() == 1) && (unitElements.get(0).getSubSequence().size() > 0)))) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  /**
   * Prüft ob eine Liste von UnitElementen nur aus einem einzelnen Call bestehen
   * 
   * @param elements Liste von Elementen einer Unit
   * @return True: Liste besteht nur aus einem Call, False: Liste besteht aus mehreren Elementen oder ist kein Call
   */
  public Boolean isCallOnly(final List<UnitElement> elements) {
    return Boolean.valueOf(((elements.size() == 1) && (elements.get(0) instanceof CallImpl)));
  }
  
  /**
   * Prueft ob die Elemente einer SequentialUnit aus Calls und SequentialProperties bestehen
   * 
   * @param elements Elemente einer SequentialUnit
   * @return True: Unit besteht aus Calls und SequentialProperties, False: Unit besteht aus anderen Elementen
   */
  public Boolean isCallSequence(final List<UnitElement> elements) {
    for (final UnitElement e : elements) {
      if (((e.getSubSequence().size() > 0) || (!((e instanceof Call) || (e instanceof SequentialProperties))))) {
        return Boolean.valueOf(false);
      }
    }
    return Boolean.valueOf(true);
  }
}
