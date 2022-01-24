package org.eclipse.emf.henshin.text.typesystem;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import org.eclipse.emf.henshin.text.henshin_text.ParameterType;
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue;
import org.eclipse.emf.henshin.text.henshin_text.PlusExpression;
import org.eclipse.emf.henshin.text.henshin_text.Rule;
import org.eclipse.emf.henshin.text.henshin_text.StringValue;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Henshin_textTypeProvider {
  public static final Henshin_textStringType stringType = new Henshin_textStringType();
  
  public static final Henshin_textNumberType numberType = new Henshin_textNumberType();
  
  public static final Henshin_textBoolType boolType = new Henshin_textBoolType();
  
  public static final Henshin_textComplexType complexType = new Henshin_textComplexType();
  
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
        return this.typeFor(((BracketExpression)expression).getExpression());
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
        return this.typeFor(((ParameterValue)expression).getValue().getType());
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
      container = container.eContainer();
    }
    Iterable<JavaImport> iterableOfJavaImportImpl = null;
    if ((container instanceof Rule)) {
      iterableOfJavaImportImpl = Iterables.<JavaImport>filter(((Rule) container).getRuleElements(), JavaImport.class);
    } else {
      iterableOfJavaImportImpl = Iterables.<JavaImport>filter(((MultiRule) container).getMultiruleElements(), JavaImport.class);
    }
    for (final JavaImport imports : iterableOfJavaImportImpl) {
      try {
        String _packagename = imports.getPackagename();
        String _plus = (_packagename + ".");
        String _get = javaAttribute.getValue().split("\\.")[0];
        String _plus_1 = (_plus + _get);
        Class<?> calledClass = Class.forName(_plus_1);
        Field[] _declaredFields = calledClass.getDeclaredFields();
        for (final Field atrib : _declaredFields) {
          String _name = atrib.getName();
          Object _get_1 = javaAttribute.getValue().split("\\.")[1];
          boolean _equals = Objects.equal(_name, _get_1);
          if (_equals) {
            return this.typeForJavaType(atrib.getType().getName());
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
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
      container = container.eContainer();
    }
    Iterable<JavaImport> iterableOfJavaImportImpl = null;
    if ((container instanceof Rule)) {
      iterableOfJavaImportImpl = Iterables.<JavaImport>filter(((Rule) container).getRuleElements(), JavaImport.class);
    } else {
      iterableOfJavaImportImpl = Iterables.<JavaImport>filter(((MultiRule) container).getMultiruleElements(), JavaImport.class);
    }
    for (final JavaImport imports : iterableOfJavaImportImpl) {
      try {
        String _packagename = imports.getPackagename();
        String _plus = (_packagename + ".");
        String _get = javaCall.getValue().split("\\.")[0];
        String _plus_1 = (_plus + _get);
        Class<?> calledClass = Class.forName(_plus_1);
        Method[] _methods = calledClass.getMethods();
        for (final Method methode : _methods) {
          String _name = methode.getName();
          Object _get_1 = javaCall.getValue().split("\\.")[1];
          boolean _equals = Objects.equal(_name, _get_1);
          if (_equals) {
            return this.typeForJavaType(methode.getReturnType().getName());
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
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
    boolean _matched = false;
    if (Objects.equal(className, "java.lang.Boolean")) {
      _matched=true;
      return Henshin_textTypeProvider.boolType;
    }
    if (!_matched) {
      if (Objects.equal(className, "boolean")) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Byte")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "byte")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Character")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "char")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Double")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "double")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Float")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "float")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Integer")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "int")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Long")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "long")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.Short")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "short")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "java.lang.String")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
    }
    if (!_matched) {
      if (Objects.equal(className, "string")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
    }
    return Henshin_textTypeProvider.complexType;
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
      return this.typeFor(parameterType.getEnumType().getLiteral());
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
    boolean _matched = false;
    if (Objects.equal(eType, "EBigDecimal")) {
      _matched=true;
      return Henshin_textTypeProvider.complexType;
    }
    if (!_matched) {
      if (Objects.equal(eType, "EBigInteger")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EBoolean")) {
        _matched=true;
        return Henshin_textTypeProvider.boolType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EBooleanObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EByte")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EByteArray")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EByteObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EChar")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "ECharacterObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EDate")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EDiagnosticChain")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EDouble")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EDoubleObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EEList")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EEnumerator")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EFeatureMap")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EFeatureMapEntry")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EFloat")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EFloatObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EInt")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EIntegerObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "ETreeIterator")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EInvocationTargetException")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EJavaClass")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EJavaObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "ELong")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "ELongObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EMap")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EResource")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EResourceSet")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EShort")) {
        _matched=true;
        return Henshin_textTypeProvider.numberType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EShortObject")) {
        _matched=true;
        return Henshin_textTypeProvider.complexType;
      }
    }
    if (!_matched) {
      if (Objects.equal(eType, "EString")) {
        _matched=true;
        return Henshin_textTypeProvider.stringType;
      }
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
