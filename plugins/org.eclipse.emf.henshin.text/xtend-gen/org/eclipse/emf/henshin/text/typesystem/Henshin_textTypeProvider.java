package org.eclipse.emf.henshin.text.typesystem;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.text.henshin_text.AndExpression;
import org.eclipse.emf.henshin.text.henshin_text.BoolValue;
import org.eclipse.emf.henshin.text.henshin_text.BracketExpression;
import org.eclipse.emf.henshin.text.henshin_text.ComparisonExpression;
import org.eclipse.emf.henshin.text.henshin_text.EqualityExpression;
import org.eclipse.emf.henshin.text.henshin_text.Expression;
import org.eclipse.emf.henshin.text.henshin_text.IntegerValue;
import org.eclipse.emf.henshin.text.henshin_text.JavaAttributeValue;
import org.eclipse.emf.henshin.text.henshin_text.JavaClassValue;
import org.eclipse.emf.henshin.text.henshin_text.JavaImport;
import org.eclipse.emf.henshin.text.henshin_text.MinusExpression;
import org.eclipse.emf.henshin.text.henshin_text.MulOrDivExpression;
import org.eclipse.emf.henshin.text.henshin_text.MultiRule;
import org.eclipse.emf.henshin.text.henshin_text.NaturalValue;
import org.eclipse.emf.henshin.text.henshin_text.NotExpression;
import org.eclipse.emf.henshin.text.henshin_text.NumberValue;
import org.eclipse.emf.henshin.text.henshin_text.OrExpression;
import org.eclipse.emf.henshin.text.henshin_text.Parameter;
import org.eclipse.emf.henshin.text.henshin_text.ParameterType;
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue;
import org.eclipse.emf.henshin.text.henshin_text.PlusExpression;
import org.eclipse.emf.henshin.text.henshin_text.Rule;
import org.eclipse.emf.henshin.text.henshin_text.RuleElement;
import org.eclipse.emf.henshin.text.henshin_text.StringValue;
import org.eclipse.emf.henshin.text.henshin_text.Type;
import org.eclipse.emf.henshin.text.typesystem.Henshin_textBoolType;
import org.eclipse.emf.henshin.text.typesystem.Henshin_textComplexType;
import org.eclipse.emf.henshin.text.typesystem.Henshin_textNumberType;
import org.eclipse.emf.henshin.text.typesystem.Henshin_textStringType;
import org.eclipse.emf.henshin.text.typesystem.Henshin_textType;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Henshin_textTypeProvider {
  public final static Henshin_textStringType stringType = new Henshin_textStringType();
  
  public final static Henshin_textNumberType numberType = new Henshin_textNumberType();
  
  public final static Henshin_textBoolType boolType = new Henshin_textBoolType();
  
  public final static Henshin_textComplexType complexType = new Henshin_textComplexType();
  
  /**
   * Liefert den Type eines Ausdrucks
   * 
   * @param expression Zu ueberpruefender Ausdruck
   * @return Type des Ausdrucks
   */
  public Henshin_textType typeFor(final Expression expression) {
    boolean _matched = false;
    if (expression instanceof StringValue) {
      _matched=true;
      return Henshin_textTypeProvider.stringType;
    }
    if (!_matched) {
      if (expression instanceof NumberValue) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof IntegerValue) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof NaturalValue) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof BoolValue) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof OrExpression) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof AndExpression) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof EqualityExpression) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof ComparisonExpression) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof PlusExpression) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof MinusExpression) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof MulOrDivExpression) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (expression instanceof BracketExpression) {
        _matched=true;
        Expression _expression = ((BracketExpression)expression).getExpression();
        return this.typeFor(_expression);
      }
    }
    if (!_matched) {
      if (expression instanceof NotExpression) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (expression instanceof ParameterValue) {
        _matched=true;
        Parameter _value = ((ParameterValue)expression).getValue();
        ParameterType _type = _value.getType();
        return this.typeFor(_type);
      }
    }
    if (!_matched) {
      if (expression instanceof JavaClassValue) {
        _matched=true;
        return this.typeFor(((JavaClassValue)expression));
      }
    }
    if (!_matched) {
      if (expression instanceof JavaAttributeValue) {
        _matched=true;
        return this.typeFor(((JavaAttributeValue)expression));
      }
    }
    return null;
  }
  
  /**
   * Liefert den Type eines Java-Attributs
   * 
   * @param javaAttribute Zu ueberpruefendes Java-Attribut
   * @return Type des Java-Attributs
   */
  public Henshin_textType typeFor(final JavaAttributeValue javaAttribute) {
    EObject container = javaAttribute.eContainer();
    while (((!(container instanceof Rule)) && (!(container instanceof MultiRule)))) {
      EObject _eContainer = container.eContainer();
      container = _eContainer;
    }
    Iterable<JavaImport> iterableOfJavaImportImpl = null;
    if ((container instanceof Rule)) {
      EList<RuleElement> _ruleElements = ((Rule) container).getRuleElements();
      Iterable<JavaImport> _filter = Iterables.<JavaImport>filter(_ruleElements, JavaImport.class);
      iterableOfJavaImportImpl = _filter;
    } else {
      EList<RuleElement> _multiruleElements = ((MultiRule) container).getMultiruleElements();
      Iterable<JavaImport> _filter_1 = Iterables.<JavaImport>filter(_multiruleElements, JavaImport.class);
      iterableOfJavaImportImpl = _filter_1;
    }
    for (final JavaImport imports : iterableOfJavaImportImpl) {
      try {
        String _packagename = imports.getPackagename();
        String _plus = (_packagename + ".");
        String _value = javaAttribute.getValue();
        String[] _split = _value.split("\\.");
        String _get = _split[0];
        String _plus_1 = (_plus + _get);
        Class<?> calledClass = Class.forName(_plus_1);
        Field[] _declaredFields = calledClass.getDeclaredFields();
        for (final Field atrib : _declaredFields) {
          String _name = atrib.getName();
          String _value_1 = javaAttribute.getValue();
          String[] _split_1 = _value_1.split("\\.");
          Object _get_1 = _split_1[1];
          boolean _equals = Objects.equal(_name, _get_1);
          if (_equals) {
            Class<?> _type = atrib.getType();
            String _name_1 = _type.getName();
            return this.typeForJavaType(_name_1);
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    return Henshin_textTypeProvider.stringType;
  }
  
  /**
   * Liefert den Type eines Java-Aufrufs
   * 
   * @param javaCall Zu ueberpruefender Java-Aufruf
   * @return Type des Java-Aufrufs
   */
  public Henshin_textType typeFor(final JavaClassValue javaCall) {
    EObject container = javaCall.eContainer();
    while (((!(container instanceof Rule)) && (!(container instanceof MultiRule)))) {
      EObject _eContainer = container.eContainer();
      container = _eContainer;
    }
    Iterable<JavaImport> iterableOfJavaImportImpl = null;
    if ((container instanceof Rule)) {
      EList<RuleElement> _ruleElements = ((Rule) container).getRuleElements();
      Iterable<JavaImport> _filter = Iterables.<JavaImport>filter(_ruleElements, JavaImport.class);
      iterableOfJavaImportImpl = _filter;
    } else {
      EList<RuleElement> _multiruleElements = ((MultiRule) container).getMultiruleElements();
      Iterable<JavaImport> _filter_1 = Iterables.<JavaImport>filter(_multiruleElements, JavaImport.class);
      iterableOfJavaImportImpl = _filter_1;
    }
    for (final JavaImport imports : iterableOfJavaImportImpl) {
      try {
        String _packagename = imports.getPackagename();
        String _plus = (_packagename + ".");
        String _value = javaCall.getValue();
        String[] _split = _value.split("\\.");
        String _get = _split[0];
        String _plus_1 = (_plus + _get);
        Class<?> calledClass = Class.forName(_plus_1);
        Method[] _methods = calledClass.getMethods();
        for (final Method methode : _methods) {
          String _name = methode.getName();
          String _value_1 = javaCall.getValue();
          String[] _split_1 = _value_1.split("\\.");
          Object _get_1 = _split_1[1];
          boolean _equals = Objects.equal(_name, _get_1);
          if (_equals) {
            Class<?> _returnType = methode.getReturnType();
            String _name_1 = _returnType.getName();
            return this.typeForJavaType(_name_1);
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    return Henshin_textTypeProvider.stringType;
  }
  
  /**
   * Liefert den Type eines Java-Type
   * 
   * @param className Zu ueberpruefender Java-Type
   * @return Type des Java-Types
   */
  public Henshin_textType typeForJavaType(final String className) {
    switch (className) {
      case "java.lang.Boolean":
        return Henshin_textTypeProvider.boolType;
      case "boolean":
        return Henshin_textTypeProvider.boolType;
      case "java.lang.Byte":
        return Henshin_textTypeProvider.numberType;
      case "byte":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.Character":
        return Henshin_textTypeProvider.stringType;
      case "char":
        return Henshin_textTypeProvider.stringType;
      case "java.lang.Double":
        return Henshin_textTypeProvider.numberType;
      case "double":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.Float":
        return Henshin_textTypeProvider.numberType;
      case "float":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.Integer":
        return Henshin_textTypeProvider.numberType;
      case "int":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.Long":
        return Henshin_textTypeProvider.numberType;
      case "long":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.Short":
        return Henshin_textTypeProvider.numberType;
      case "short":
        return Henshin_textTypeProvider.numberType;
      case "java.lang.String":
        return Henshin_textTypeProvider.stringType;
      case "string":
        return Henshin_textTypeProvider.stringType;
      default:
        return Henshin_textTypeProvider.complexType;
    }
  }
  
  /**
   * Liefert den Type eines Parameter-Type
   * 
   * @param parameterType Zu ueberpruefender Parameter-Type
   * @return Type des Parameter-Types
   */
  public Henshin_textType typeFor(final ParameterType parameterType) {
    EClass _type = parameterType.getType();
    boolean _equals = Objects.equal(_type, null);
    if (_equals) {
      Type _enumType = parameterType.getEnumType();
      String _literal = _enumType.getLiteral();
      return this.typeFor(_literal);
    } else {
      return Henshin_textTypeProvider.complexType;
    }
  }
  
  /**
   * Liefert den Type einen E-Type
   * 
   * @param eType Zu ueberpruefender E-Type
   * @return Type des E-Types
   */
  public Henshin_textType typeFor(final String eType) {
    switch (eType) {
      case "EBigDecimal":
        return Henshin_textTypeProvider.complexType;
      case "EBigInteger":
        return Henshin_textTypeProvider.complexType;
      case "EBoolean":
        return Henshin_textTypeProvider.boolType;
      case "EBooleanObject":
        return Henshin_textTypeProvider.complexType;
      case "EByte":
        return Henshin_textTypeProvider.numberType;
      case "EByteArray":
        return Henshin_textTypeProvider.complexType;
      case "EByteObject":
        return Henshin_textTypeProvider.complexType;
      case "EChar":
        return Henshin_textTypeProvider.stringType;
      case "ECharacterObject":
        return Henshin_textTypeProvider.complexType;
      case "EDate":
        return Henshin_textTypeProvider.complexType;
      case "EDiagnosticChain":
        return Henshin_textTypeProvider.complexType;
      case "EDouble":
        return Henshin_textTypeProvider.numberType;
      case "EDoubleObject":
        return Henshin_textTypeProvider.complexType;
      case "EEList":
        return Henshin_textTypeProvider.complexType;
      case "EEnumerator":
        return Henshin_textTypeProvider.complexType;
      case "EFeatureMap":
        return Henshin_textTypeProvider.complexType;
      case "EFeatureMapEntry":
        return Henshin_textTypeProvider.complexType;
      case "EFloat":
        return Henshin_textTypeProvider.numberType;
      case "EFloatObject":
        return Henshin_textTypeProvider.complexType;
      case "EInt":
        return Henshin_textTypeProvider.numberType;
      case "EIntegerObject":
        return Henshin_textTypeProvider.complexType;
      case "ETreeIterator":
        return Henshin_textTypeProvider.complexType;
      case "EInvocationTargetException":
        return Henshin_textTypeProvider.complexType;
      case "EJavaClass":
        return Henshin_textTypeProvider.complexType;
      case "EJavaObject":
        return Henshin_textTypeProvider.complexType;
      case "ELong":
        return Henshin_textTypeProvider.numberType;
      case "ELongObject":
        return Henshin_textTypeProvider.complexType;
      case "EMap":
        return Henshin_textTypeProvider.complexType;
      case "EResource":
        return Henshin_textTypeProvider.complexType;
      case "EResourceSet":
        return Henshin_textTypeProvider.complexType;
      case "EShort":
        return Henshin_textTypeProvider.numberType;
      case "EShortObject":
        return Henshin_textTypeProvider.complexType;
      case "EString":
        return Henshin_textTypeProvider.stringType;
    }
    return null;
  }
  
  public boolean isInt(final Henshin_textType type) {
    return Objects.equal(type, Henshin_textTypeProvider.numberType);
  }
  
  public boolean isString(final Henshin_textType type) {
    return Objects.equal(type, Henshin_textTypeProvider.stringType);
  }
  
  public boolean isBoolean(final Henshin_textType type) {
    return Objects.equal(type, Henshin_textTypeProvider.boolType);
  }
  
  public boolean isComplex(final Henshin_textType type) {
    return Objects.equal(type, Henshin_textTypeProvider.complexType);
  }
}
