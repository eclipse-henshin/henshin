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
import org.eclipse.emf.henshin.text.henshin_text.Expression;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textFactory;
import org.eclipse.emf.henshin.text.henshin_text.IndependentUnit;
import org.eclipse.emf.henshin.text.henshin_text.IteratedUnit;
import org.eclipse.emf.henshin.text.henshin_text.LoopUnit;
import org.eclipse.emf.henshin.text.henshin_text.ModelElement;
import org.eclipse.emf.henshin.text.henshin_text.Parameter;
import org.eclipse.emf.henshin.text.henshin_text.ParameterKind;
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
    EList<UnitElement> _unitElements = unit.getUnitElements();
    Boolean _isSequence = this.isSequence(_unitElements);
    boolean _equals = ((_isSequence).booleanValue() == false);
    if (_equals) {
      EList<UnitElement> _unitElements_1 = unit.getUnitElements();
      UnitElement _get = _unitElements_1.get(0);
      if ((_get instanceof ConditionalUnitImpl)) {
        EList<UnitElement> _unitElements_2 = unit.getUnitElements();
        UnitElement _get_1 = _unitElements_2.get(0);
        ConditionalUnit help = ((ConditionalUnit) _get_1);
        EList<UnitElement> _if = help.getIf();
        Boolean _isCallOnly = this.isCallOnly(_if);
        boolean _not = (!(_isCallOnly).booleanValue());
        if (_not) {
          String _name = unit.getName();
          String _plus = (_name + "IF");
          String _plus_1 = (_plus + Integer.valueOf(level));
          String _plus_2 = (_plus_1 + "_");
          int _returnRandomeNumber = this.returnRandomeNumber();
          String _plus_3 = (_plus_2 + Integer.valueOf(_returnRandomeNumber));
          EList<UnitElement> _if_1 = help.getIf();
          EList<Parameter> _parameters = unit.getParameters();
          Parameter[] _clone = ((Parameter[])Conversions.unwrapArray(_parameters, Parameter.class)).clone();
          Unit newSubUnit = this.createUnit(_plus_3, _if_1, ((List<Parameter>)Conversions.doWrapArray(_clone)));
          EList<UnitElement> _unitElements_3 = unit.getUnitElements();
          UnitElement _get_2 = _unitElements_3.get(0);
          EList<UnitElement> _if_2 = ((ConditionalUnit) _get_2).getIf();
          _if_2.clear();
          Call call = Henshin_textFactory.eINSTANCE.createCall();
          call.setElementCall(newSubUnit);
          EList<Parameter> _parameters_1 = call.getParameters();
          EList<Parameter> _parameters_2 = unit.getParameters();
          _parameters_1.addAll(_parameters_2);
          EList<UnitElement> _unitElements_4 = unit.getUnitElements();
          UnitElement _get_3 = _unitElements_4.get(0);
          EList<UnitElement> _if_3 = ((ConditionalUnit) _get_3).getIf();
          _if_3.add(call);
          List<Unit> newSubUnitList = this.flat(newSubUnit, (level + 1), call);
          erg.addAll(newSubUnitList);
        } else {
          boolean _notEquals = (!Objects.equal(unitCall, null));
          if (_notEquals) {
            int parameterIndex = 0;
            EList<Parameter> _parameters_3 = unitCall.getParameters();
            for (final Parameter p : _parameters_3) {
              {
                EList<UnitElement> _if_4 = help.getIf();
                UnitElement _get_4 = _if_4.get(0);
                EList<Parameter> _parameters_4 = ((Call) _get_4).getParameters();
                int indexInSubCall = _parameters_4.indexOf(p);
                if ((indexInSubCall != (-1))) {
                  EList<UnitElement> _if_5 = help.getIf();
                  UnitElement _get_5 = _if_5.get(0);
                  EList<Parameter> _parameters_5 = ((Call) _get_5).getParameters();
                  ModelElement _elementCall = unitCall.getElementCall();
                  EList<Parameter> _parameters_6 = _elementCall.getParameters();
                  Parameter _get_6 = _parameters_6.get(parameterIndex);
                  _parameters_5.set(indexInSubCall, _get_6);
                }
                parameterIndex++;
              }
            }
          }
        }
        EList<UnitElement> _then = help.getThen();
        Boolean _isCallOnly_1 = this.isCallOnly(_then);
        boolean _not_1 = (!(_isCallOnly_1).booleanValue());
        if (_not_1) {
          String _name_1 = unit.getName();
          String _plus_4 = (_name_1 + "THEN");
          String _plus_5 = (_plus_4 + Integer.valueOf(level));
          String _plus_6 = (_plus_5 + "_");
          int _returnRandomeNumber_1 = this.returnRandomeNumber();
          String _plus_7 = (_plus_6 + Integer.valueOf(_returnRandomeNumber_1));
          EList<UnitElement> _then_1 = help.getThen();
          EList<Parameter> _parameters_4 = unit.getParameters();
          Parameter[] _clone_1 = ((Parameter[])Conversions.unwrapArray(_parameters_4, Parameter.class)).clone();
          Unit newSubUnit_1 = this.createUnit(_plus_7, _then_1, ((List<Parameter>)Conversions.doWrapArray(_clone_1)));
          EList<UnitElement> _unitElements_5 = unit.getUnitElements();
          UnitElement _get_4 = _unitElements_5.get(0);
          EList<UnitElement> _then_2 = ((ConditionalUnit) _get_4).getThen();
          _then_2.clear();
          Call call_1 = Henshin_textFactory.eINSTANCE.createCall();
          call_1.setElementCall(newSubUnit_1);
          EList<Parameter> _parameters_5 = call_1.getParameters();
          EList<Parameter> _parameters_6 = unit.getParameters();
          _parameters_5.addAll(_parameters_6);
          EList<UnitElement> _unitElements_6 = unit.getUnitElements();
          UnitElement _get_5 = _unitElements_6.get(0);
          EList<UnitElement> _then_3 = ((ConditionalUnit) _get_5).getThen();
          _then_3.add(call_1);
          List<Unit> newSubUnitList_1 = this.flat(newSubUnit_1, (level + 1), call_1);
          erg.addAll(newSubUnitList_1);
        } else {
          boolean _notEquals_1 = (!Objects.equal(unitCall, null));
          if (_notEquals_1) {
            int parameterIndex_1 = 0;
            EList<Parameter> _parameters_7 = unitCall.getParameters();
            for (final Parameter p_1 : _parameters_7) {
              {
                EList<UnitElement> _then_4 = help.getThen();
                UnitElement _get_6 = _then_4.get(0);
                EList<Parameter> _parameters_8 = ((Call) _get_6).getParameters();
                int indexInSubCall = _parameters_8.indexOf(p_1);
                if ((indexInSubCall != (-1))) {
                  EList<UnitElement> _then_5 = help.getThen();
                  UnitElement _get_7 = _then_5.get(0);
                  EList<Parameter> _parameters_9 = ((Call) _get_7).getParameters();
                  ModelElement _elementCall = unitCall.getElementCall();
                  EList<Parameter> _parameters_10 = _elementCall.getParameters();
                  Parameter _get_8 = _parameters_10.get(parameterIndex_1);
                  _parameters_9.set(indexInSubCall, _get_8);
                }
                parameterIndex_1++;
              }
            }
          }
        }
        EList<UnitElement> _else = help.getElse();
        int _size = _else.size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          EList<UnitElement> _else_1 = help.getElse();
          Boolean _isCallOnly_2 = this.isCallOnly(_else_1);
          boolean _not_2 = (!(_isCallOnly_2).booleanValue());
          if (_not_2) {
            String _name_2 = unit.getName();
            String _plus_8 = (_name_2 + "ELSE");
            String _plus_9 = (_plus_8 + Integer.valueOf(level));
            String _plus_10 = (_plus_9 + "_");
            int _returnRandomeNumber_2 = this.returnRandomeNumber();
            String _plus_11 = (_plus_10 + Integer.valueOf(_returnRandomeNumber_2));
            EList<UnitElement> _else_2 = help.getElse();
            EList<Parameter> _parameters_8 = unit.getParameters();
            Parameter[] _clone_2 = ((Parameter[])Conversions.unwrapArray(_parameters_8, Parameter.class)).clone();
            Unit newSubUnit_2 = this.createUnit(_plus_11, _else_2, ((List<Parameter>)Conversions.doWrapArray(_clone_2)));
            EList<UnitElement> _unitElements_7 = unit.getUnitElements();
            UnitElement _get_6 = _unitElements_7.get(0);
            EList<UnitElement> _else_3 = ((ConditionalUnit) _get_6).getElse();
            _else_3.clear();
            Call call_2 = Henshin_textFactory.eINSTANCE.createCall();
            call_2.setElementCall(newSubUnit_2);
            EList<Parameter> _parameters_9 = call_2.getParameters();
            EList<Parameter> _parameters_10 = unit.getParameters();
            _parameters_9.addAll(_parameters_10);
            EList<UnitElement> _unitElements_8 = unit.getUnitElements();
            UnitElement _get_7 = _unitElements_8.get(0);
            EList<UnitElement> _else_4 = ((ConditionalUnit) _get_7).getElse();
            _else_4.add(call_2);
            List<Unit> newSubUnitList_2 = this.flat(newSubUnit_2, (level + 1), call_2);
            erg.addAll(newSubUnitList_2);
          } else {
            boolean _notEquals_2 = (!Objects.equal(unitCall, null));
            if (_notEquals_2) {
              int parameterIndex_2 = 0;
              EList<Parameter> _parameters_11 = unitCall.getParameters();
              for (final Parameter p_2 : _parameters_11) {
                {
                  EList<UnitElement> _else_5 = help.getElse();
                  UnitElement _get_8 = _else_5.get(0);
                  EList<Parameter> _parameters_12 = ((Call) _get_8).getParameters();
                  int indexInSubCall = _parameters_12.indexOf(p_2);
                  if ((indexInSubCall != (-1))) {
                    EList<UnitElement> _then_4 = help.getThen();
                    UnitElement _get_9 = _then_4.get(0);
                    EList<Parameter> _parameters_13 = ((Call) _get_9).getParameters();
                    ModelElement _elementCall = unitCall.getElementCall();
                    EList<Parameter> _parameters_14 = _elementCall.getParameters();
                    Parameter _get_10 = _parameters_14.get(parameterIndex_2);
                    _parameters_13.set(indexInSubCall, _get_10);
                  }
                  parameterIndex_2++;
                }
              }
            }
          }
        }
        erg.add(unit);
      } else {
        EList<UnitElement> _unitElements_9 = unit.getUnitElements();
        UnitElement _get_8 = _unitElements_9.get(0);
        if ((_get_8 instanceof IteratedUnitImpl)) {
          EList<UnitElement> _unitElements_10 = unit.getUnitElements();
          UnitElement _get_9 = _unitElements_10.get(0);
          IteratedUnit help_1 = ((IteratedUnit) _get_9);
          EList<UnitElement> _subElement = help_1.getSubElement();
          Boolean _isCallOnly_3 = this.isCallOnly(_subElement);
          boolean _not_3 = (!(_isCallOnly_3).booleanValue());
          if (_not_3) {
            String _name_3 = unit.getName();
            String _plus_12 = (_name_3 + "ITERATED");
            String _plus_13 = (_plus_12 + Integer.valueOf(level));
            String _plus_14 = (_plus_13 + "_");
            int _returnRandomeNumber_3 = this.returnRandomeNumber();
            String _plus_15 = (_plus_14 + Integer.valueOf(_returnRandomeNumber_3));
            EList<UnitElement> _subElement_1 = help_1.getSubElement();
            EList<Parameter> _parameters_12 = unit.getParameters();
            Parameter[] _clone_3 = ((Parameter[])Conversions.unwrapArray(_parameters_12, Parameter.class)).clone();
            Unit newSubUnit_3 = this.createUnit(_plus_15, _subElement_1, ((List<Parameter>)Conversions.doWrapArray(_clone_3)));
            EList<UnitElement> _unitElements_11 = unit.getUnitElements();
            UnitElement _get_10 = _unitElements_11.get(0);
            EList<UnitElement> _subElement_2 = ((IteratedUnit) _get_10).getSubElement();
            _subElement_2.clear();
            Call call_3 = Henshin_textFactory.eINSTANCE.createCall();
            call_3.setElementCall(newSubUnit_3);
            EList<Parameter> _parameters_13 = call_3.getParameters();
            EList<Parameter> _parameters_14 = unit.getParameters();
            _parameters_13.addAll(_parameters_14);
            EList<Parameter> _parameters_15 = unit.getParameters();
            for (final Parameter p_3 : _parameters_15) {
              Parameter param = Henshin_textFactory.eINSTANCE.createParameter();
            }
            EList<UnitElement> _unitElements_12 = unit.getUnitElements();
            UnitElement _get_11 = _unitElements_12.get(0);
            EList<UnitElement> _subElement_3 = ((IteratedUnit) _get_11).getSubElement();
            _subElement_3.add(call_3);
            erg.add(unit);
            List<Unit> newSubUnitList_3 = this.flat(newSubUnit_3, (level + 1), call_3);
            erg.addAll(newSubUnitList_3);
          } else {
            boolean _notEquals_3 = (!Objects.equal(unitCall, null));
            if (_notEquals_3) {
              int parameterIndex_3 = 0;
              EList<Parameter> _parameters_16 = unitCall.getParameters();
              for (final Parameter p_4 : _parameters_16) {
                {
                  EList<UnitElement> _subElement_4 = help_1.getSubElement();
                  UnitElement _get_12 = _subElement_4.get(0);
                  EList<Parameter> _parameters_17 = ((Call) _get_12).getParameters();
                  int indexInSubCall = _parameters_17.indexOf(p_4);
                  if ((indexInSubCall != (-1))) {
                    EList<UnitElement> _subElement_5 = help_1.getSubElement();
                    UnitElement _get_13 = _subElement_5.get(0);
                    EList<Parameter> _parameters_18 = ((Call) _get_13).getParameters();
                    ModelElement _elementCall = unitCall.getElementCall();
                    EList<Parameter> _parameters_19 = _elementCall.getParameters();
                    Parameter _get_14 = _parameters_19.get(parameterIndex_3);
                    _parameters_18.set(indexInSubCall, _get_14);
                  }
                  parameterIndex_3++;
                }
              }
            }
            erg.add(unit);
          }
        } else {
          EList<UnitElement> _unitElements_13 = unit.getUnitElements();
          UnitElement _get_12 = _unitElements_13.get(0);
          if ((_get_12 instanceof LoopUnitImpl)) {
            EList<UnitElement> _unitElements_14 = unit.getUnitElements();
            UnitElement _get_13 = _unitElements_14.get(0);
            LoopUnit help_2 = ((LoopUnit) _get_13);
            EList<UnitElement> _subElement_4 = help_2.getSubElement();
            Boolean _isCallOnly_4 = this.isCallOnly(_subElement_4);
            boolean _not_4 = (!(_isCallOnly_4).booleanValue());
            if (_not_4) {
              String _name_4 = unit.getName();
              String _plus_16 = (_name_4 + "LOOP");
              String _plus_17 = (_plus_16 + Integer.valueOf(level));
              String _plus_18 = (_plus_17 + "_");
              int _returnRandomeNumber_4 = this.returnRandomeNumber();
              String _plus_19 = (_plus_18 + Integer.valueOf(_returnRandomeNumber_4));
              EList<UnitElement> _subElement_5 = help_2.getSubElement();
              EList<Parameter> _parameters_17 = unit.getParameters();
              Parameter[] _clone_4 = ((Parameter[])Conversions.unwrapArray(_parameters_17, Parameter.class)).clone();
              Unit newSubUnit_4 = this.createUnit(_plus_19, _subElement_5, ((List<Parameter>)Conversions.doWrapArray(_clone_4)));
              EList<UnitElement> _unitElements_15 = unit.getUnitElements();
              UnitElement _get_14 = _unitElements_15.get(0);
              EList<UnitElement> _subElement_6 = ((LoopUnit) _get_14).getSubElement();
              _subElement_6.clear();
              Call call_4 = Henshin_textFactory.eINSTANCE.createCall();
              call_4.setElementCall(newSubUnit_4);
              EList<Parameter> _parameters_18 = call_4.getParameters();
              EList<Parameter> _parameters_19 = unit.getParameters();
              _parameters_18.addAll(_parameters_19);
              EList<UnitElement> _unitElements_16 = unit.getUnitElements();
              UnitElement _get_15 = _unitElements_16.get(0);
              EList<UnitElement> _subElement_7 = ((LoopUnit) _get_15).getSubElement();
              _subElement_7.add(call_4);
              erg.add(unit);
              List<Unit> newSubUnitList_4 = this.flat(newSubUnit_4, (level + 1), call_4);
              erg.addAll(newSubUnitList_4);
            } else {
              boolean _notEquals_4 = (!Objects.equal(unitCall, null));
              if (_notEquals_4) {
                int parameterIndex_4 = 0;
                EList<Parameter> _parameters_20 = unitCall.getParameters();
                for (final Parameter p_5 : _parameters_20) {
                  {
                    EList<UnitElement> _subElement_8 = help_2.getSubElement();
                    UnitElement _get_16 = _subElement_8.get(0);
                    EList<Parameter> _parameters_21 = ((Call) _get_16).getParameters();
                    int indexInSubCall = _parameters_21.indexOf(p_5);
                    if ((indexInSubCall != (-1))) {
                      EList<UnitElement> _subElement_9 = help_2.getSubElement();
                      UnitElement _get_17 = _subElement_9.get(0);
                      EList<Parameter> _parameters_22 = ((Call) _get_17).getParameters();
                      ModelElement _elementCall = unitCall.getElementCall();
                      EList<Parameter> _parameters_23 = _elementCall.getParameters();
                      Parameter _get_18 = _parameters_23.get(parameterIndex_4);
                      _parameters_22.set(indexInSubCall, _get_18);
                    }
                    parameterIndex_4++;
                  }
                }
              }
              erg.add(unit);
            }
          } else {
            EList<UnitElement> _unitElements_17 = unit.getUnitElements();
            UnitElement _get_16 = _unitElements_17.get(0);
            if ((_get_16 instanceof PriorityUnitImpl)) {
              EList<UnitElement> _unitElements_18 = unit.getUnitElements();
              UnitElement _get_17 = _unitElements_18.get(0);
              PriorityUnit help_3 = ((PriorityUnit) _get_17);
              int index = 0;
              EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists = help_3.getListOfLists();
              for (final org.eclipse.emf.henshin.text.henshin_text.List sub : _listOfLists) {
                EList<UnitElement> _subElements = sub.getSubElements();
                Boolean _isCallOnly_5 = this.isCallOnly(_subElements);
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
                  EList<UnitElement> _subElements_1 = sub.getSubElements();
                  EList<Parameter> _parameters_21 = unit.getParameters();
                  Parameter[] _clone_5 = ((Parameter[])Conversions.unwrapArray(_parameters_21, Parameter.class)).clone();
                  Unit newSubUnit_5 = this.createUnit(_plus_25, _subElements_1, ((List<Parameter>)Conversions.doWrapArray(_clone_5)));
                  EList<UnitElement> _unitElements_19 = unit.getUnitElements();
                  UnitElement _get_18 = _unitElements_19.get(0);
                  EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_1 = ((PriorityUnit) _get_18).getListOfLists();
                  org.eclipse.emf.henshin.text.henshin_text.List _get_19 = _listOfLists_1.get(index);
                  EList<UnitElement> _subElements_2 = _get_19.getSubElements();
                  _subElements_2.clear();
                  Call call_5 = Henshin_textFactory.eINSTANCE.createCall();
                  call_5.setElementCall(newSubUnit_5);
                  EList<Parameter> _parameters_22 = call_5.getParameters();
                  EList<Parameter> _parameters_23 = unit.getParameters();
                  _parameters_22.addAll(_parameters_23);
                  EList<UnitElement> _unitElements_20 = unit.getUnitElements();
                  UnitElement _get_20 = _unitElements_20.get(0);
                  EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_2 = ((PriorityUnit) _get_20).getListOfLists();
                  org.eclipse.emf.henshin.text.henshin_text.List _get_21 = _listOfLists_2.get(index);
                  EList<UnitElement> _subElements_3 = _get_21.getSubElements();
                  _subElements_3.add(call_5);
                  List<Unit> newSubUnitList_5 = this.flat(newSubUnit_5, (level + 1), call_5);
                  erg.addAll(newSubUnitList_5);
                  index++;
                } else {
                  boolean _notEquals_5 = (!Objects.equal(unitCall, null));
                  if (_notEquals_5) {
                    int parameterIndex_5 = 0;
                    EList<Parameter> _parameters_24 = unitCall.getParameters();
                    for (final Parameter p_6 : _parameters_24) {
                      {
                        EList<UnitElement> _subElements_4 = sub.getSubElements();
                        UnitElement _get_22 = _subElements_4.get(0);
                        EList<Parameter> _parameters_25 = ((Call) _get_22).getParameters();
                        int indexInSubCall = _parameters_25.indexOf(p_6);
                        if ((indexInSubCall != (-1))) {
                          EList<UnitElement> _subElements_5 = sub.getSubElements();
                          UnitElement _get_23 = _subElements_5.get(0);
                          EList<Parameter> _parameters_26 = ((Call) _get_23).getParameters();
                          ModelElement _elementCall = unitCall.getElementCall();
                          EList<Parameter> _parameters_27 = _elementCall.getParameters();
                          Parameter _get_24 = _parameters_27.get(parameterIndex_5);
                          _parameters_26.set(indexInSubCall, _get_24);
                        }
                        parameterIndex_5++;
                      }
                    }
                  }
                }
              }
              erg.add(unit);
            } else {
              EList<UnitElement> _unitElements_21 = unit.getUnitElements();
              UnitElement _get_22 = _unitElements_21.get(0);
              if ((_get_22 instanceof IndependentUnitImpl)) {
                EList<UnitElement> _unitElements_22 = unit.getUnitElements();
                UnitElement _get_23 = _unitElements_22.get(0);
                IndependentUnit help_4 = ((IndependentUnit) _get_23);
                int index_1 = 0;
                EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_3 = help_4.getListOfLists();
                for (final org.eclipse.emf.henshin.text.henshin_text.List sub_1 : _listOfLists_3) {
                  EList<UnitElement> _subElements_4 = sub_1.getSubElements();
                  Boolean _isCallOnly_6 = this.isCallOnly(_subElements_4);
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
                    EList<UnitElement> _subElements_5 = sub_1.getSubElements();
                    EList<Parameter> _parameters_25 = unit.getParameters();
                    Parameter[] _clone_6 = ((Parameter[])Conversions.unwrapArray(_parameters_25, Parameter.class)).clone();
                    Unit newSubUnit_6 = this.createUnit(_plus_31, _subElements_5, ((List<Parameter>)Conversions.doWrapArray(_clone_6)));
                    EList<UnitElement> _unitElements_23 = unit.getUnitElements();
                    UnitElement _get_24 = _unitElements_23.get(0);
                    EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_4 = ((IndependentUnit) _get_24).getListOfLists();
                    org.eclipse.emf.henshin.text.henshin_text.List _get_25 = _listOfLists_4.get(index_1);
                    EList<UnitElement> _subElements_6 = _get_25.getSubElements();
                    _subElements_6.clear();
                    Call call_6 = Henshin_textFactory.eINSTANCE.createCall();
                    call_6.setElementCall(newSubUnit_6);
                    EList<Parameter> _parameters_26 = call_6.getParameters();
                    EList<Parameter> _parameters_27 = unit.getParameters();
                    _parameters_26.addAll(_parameters_27);
                    EList<UnitElement> _unitElements_24 = unit.getUnitElements();
                    UnitElement _get_26 = _unitElements_24.get(0);
                    EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_5 = ((IndependentUnit) _get_26).getListOfLists();
                    org.eclipse.emf.henshin.text.henshin_text.List _get_27 = _listOfLists_5.get(index_1);
                    EList<UnitElement> _subElements_7 = _get_27.getSubElements();
                    _subElements_7.add(call_6);
                    List<Unit> newSubUnitList_6 = this.flat(newSubUnit_6, (level + 1), call_6);
                    erg.addAll(newSubUnitList_6);
                    index_1++;
                  } else {
                    boolean _notEquals_6 = (!Objects.equal(unitCall, null));
                    if (_notEquals_6) {
                      int parameterIndex_6 = 0;
                      EList<Parameter> _parameters_28 = unitCall.getParameters();
                      for (final Parameter p_7 : _parameters_28) {
                        {
                          EList<UnitElement> _subElements_8 = sub_1.getSubElements();
                          UnitElement _get_28 = _subElements_8.get(0);
                          EList<Parameter> _parameters_29 = ((Call) _get_28).getParameters();
                          int indexInSubCall = _parameters_29.indexOf(p_7);
                          if ((indexInSubCall != (-1))) {
                            EList<UnitElement> _subElements_9 = sub_1.getSubElements();
                            UnitElement _get_29 = _subElements_9.get(0);
                            EList<Parameter> _parameters_30 = ((Call) _get_29).getParameters();
                            ModelElement _elementCall = unitCall.getElementCall();
                            EList<Parameter> _parameters_31 = _elementCall.getParameters();
                            Parameter _get_30 = _parameters_31.get(parameterIndex_6);
                            _parameters_30.set(indexInSubCall, _get_30);
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
      EList<UnitElement> _unitElements_25 = unit.getUnitElements();
      Boolean _isCallSequence = this.isCallSequence(_unitElements_25);
      if ((_isCallSequence).booleanValue()) {
        erg.add(unit);
      } else {
        int index_2 = 0;
        EList<UnitElement> _unitElements_26 = unit.getUnitElements();
        for (final UnitElement element : _unitElements_26) {
          {
            if (((!(element instanceof SequentialProperties)) && (!(element instanceof Call)))) {
              List<UnitElement> subElements = new ArrayList<UnitElement>();
              EList<UnitElement> _subSequence = element.getSubSequence();
              int _size_1 = _subSequence.size();
              boolean _greaterThan_1 = (_size_1 > 0);
              if (_greaterThan_1) {
                EList<UnitElement> _subSequence_1 = element.getSubSequence();
                subElements.addAll(_subSequence_1);
                EList<UnitElement> _subSequence_2 = element.getSubSequence();
                _subSequence_2.clear();
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
              EList<Parameter> _parameters_29 = unit.getParameters();
              Parameter[] _clone_7 = ((Parameter[])Conversions.unwrapArray(_parameters_29, Parameter.class)).clone();
              Unit newSubUnit_7 = this.createUnit(_plus_37, subElements, ((List<Parameter>)Conversions.doWrapArray(_clone_7)));
              Call call_7 = Henshin_textFactory.eINSTANCE.createCall();
              call_7.setElementCall(newSubUnit_7);
              EList<Parameter> _parameters_30 = call_7.getParameters();
              EList<Parameter> _parameters_31 = unit.getParameters();
              _parameters_30.addAll(_parameters_31);
              EList<UnitElement> _unitElements_27 = unit.getUnitElements();
              _unitElements_27.set(index_2, call_7);
              List<Unit> newSubUnitList_7 = this.flat(newSubUnit_7, (level + 1), call_7);
              erg.addAll(newSubUnitList_7);
            } else {
              if (((!Objects.equal(unitCall, null)) && (element instanceof Call))) {
                int parameterIndex_7 = 0;
                EList<Parameter> _parameters_32 = unitCall.getParameters();
                for (final Parameter p_8 : _parameters_32) {
                  {
                    EList<Parameter> _parameters_33 = ((Call) element).getParameters();
                    int indexInSubCall = _parameters_33.indexOf(p_8);
                    if ((indexInSubCall != (-1))) {
                      EList<Parameter> _parameters_34 = ((Call) element).getParameters();
                      ModelElement _elementCall = unitCall.getElementCall();
                      EList<Parameter> _parameters_35 = _elementCall.getParameters();
                      Parameter _get_28 = _parameters_35.get(parameterIndex_7);
                      _parameters_34.set(indexInSubCall, _get_28);
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
      EClass _type_1 = type.getType();
      result.setType(_type_1);
    } else {
      Type value = null;
      Type _enumType = type.getEnumType();
      String _literal = _enumType.getLiteral();
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
        ParameterKind _kind = param.getKind();
        helpParameter.setKind(_kind);
        ParameterType _type = param.getType();
        ParameterType _parameterType = this.getParameterType(_type);
        helpParameter.setType(_parameterType);
        EList<Parameter> _parameters = helpUnit.getParameters();
        _parameters.add(helpParameter);
      }
    }
    Boolean _isSequence = this.isSequence(unitElement);
    if ((_isSequence).booleanValue()) {
      EList<UnitElement> _unitElements = helpUnit.getUnitElements();
      _unitElements.addAll(unitElement);
    } else {
      UnitElement _get = unitElement.get(0);
      if ((_get instanceof ConditionalUnitImpl)) {
        ConditionalUnit helpUnitElement = Henshin_textFactory.eINSTANCE.createConditionalUnit();
        EList<UnitElement> _if = helpUnitElement.getIf();
        UnitElement _get_1 = unitElement.get(0);
        EList<UnitElement> _if_1 = ((ConditionalUnit) _get_1).getIf();
        _if.addAll(_if_1);
        EList<UnitElement> _then = helpUnitElement.getThen();
        UnitElement _get_2 = unitElement.get(0);
        EList<UnitElement> _then_1 = ((ConditionalUnit) _get_2).getThen();
        _then.addAll(_then_1);
        UnitElement _get_3 = unitElement.get(0);
        EList<UnitElement> _else = ((ConditionalUnit) _get_3).getElse();
        int _size = _else.size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          EList<UnitElement> _else_1 = helpUnitElement.getElse();
          UnitElement _get_4 = unitElement.get(0);
          EList<UnitElement> _else_2 = ((ConditionalUnit) _get_4).getElse();
          _else_1.addAll(_else_2);
        }
        EList<UnitElement> _unitElements_1 = helpUnit.getUnitElements();
        _unitElements_1.add(helpUnitElement);
      } else {
        UnitElement _get_5 = unitElement.get(0);
        if ((_get_5 instanceof IteratedUnitImpl)) {
          IteratedUnit helpUnitElement_1 = Henshin_textFactory.eINSTANCE.createIteratedUnit();
          UnitElement _get_6 = unitElement.get(0);
          Expression _iterations = ((IteratedUnit) _get_6).getIterations();
          helpUnitElement_1.setIterations(_iterations);
          EList<UnitElement> _subElement = helpUnitElement_1.getSubElement();
          UnitElement _get_7 = unitElement.get(0);
          EList<UnitElement> _subElement_1 = ((IteratedUnit) _get_7).getSubElement();
          _subElement.addAll(_subElement_1);
          EList<UnitElement> _unitElements_2 = helpUnit.getUnitElements();
          _unitElements_2.add(helpUnitElement_1);
        } else {
          UnitElement _get_8 = unitElement.get(0);
          if ((_get_8 instanceof LoopUnitImpl)) {
            LoopUnit helpUnitElement_2 = Henshin_textFactory.eINSTANCE.createLoopUnit();
            EList<UnitElement> _subElement_2 = helpUnitElement_2.getSubElement();
            UnitElement _get_9 = unitElement.get(0);
            EList<UnitElement> _subElement_3 = ((LoopUnit) _get_9).getSubElement();
            _subElement_2.addAll(_subElement_3);
            EList<UnitElement> _unitElements_3 = helpUnit.getUnitElements();
            _unitElements_3.add(helpUnitElement_2);
          } else {
            UnitElement _get_10 = unitElement.get(0);
            if ((_get_10 instanceof PriorityUnitImpl)) {
              PriorityUnit helpUnitElement_3 = Henshin_textFactory.eINSTANCE.createPriorityUnit();
              EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists = helpUnitElement_3.getListOfLists();
              UnitElement _get_11 = unitElement.get(0);
              EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_1 = ((PriorityUnit) _get_11).getListOfLists();
              _listOfLists.addAll(_listOfLists_1);
              EList<UnitElement> _unitElements_4 = helpUnit.getUnitElements();
              _unitElements_4.add(helpUnitElement_3);
            } else {
              UnitElement _get_12 = unitElement.get(0);
              if ((_get_12 instanceof IndependentUnitImpl)) {
                IndependentUnit helpUnitElement_4 = Henshin_textFactory.eINSTANCE.createIndependentUnit();
                EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_2 = helpUnitElement_4.getListOfLists();
                UnitElement _get_13 = unitElement.get(0);
                EList<org.eclipse.emf.henshin.text.henshin_text.List> _listOfLists_3 = ((IndependentUnit) _get_13).getListOfLists();
                _listOfLists_2.addAll(_listOfLists_3);
                EList<UnitElement> _unitElements_5 = helpUnit.getUnitElements();
                _unitElements_5.add(helpUnitElement_4);
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
