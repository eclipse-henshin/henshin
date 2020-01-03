package org.eclipse.emf.henshin.adapters.xtext;

import java.util.Arrays;
import javax.inject.Provider;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class NamingHelper {
  protected static String _name(final EObject eo) {
    return null;
  }
  
  protected static String _name(final NamedElement ne) {
    return ne.getName();
  }
  
  protected static String _name(final Edge e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    final Provider<String> _function = () -> {
      return e.getSource().getName();
    };
    String _safe = NamingHelper.safe(_function);
    _builder.append(_safe);
    _builder.append("->");
    final Provider<String> _function_1 = () -> {
      return e.getTarget().getName();
    };
    String _safe_1 = NamingHelper.safe(_function_1);
    _builder.append(_safe_1);
    _builder.append(":");
    final Provider<String> _function_2 = () -> {
      return e.getType().getName();
    };
    String _safe_2 = NamingHelper.safe(_function_2);
    _builder.append(_safe_2);
    _builder.append("]");
    return _builder.toString();
  }
  
  protected static String _name(final Attribute a) {
    String _xifexpression = null;
    EAttribute _type = a.getType();
    boolean _tripleNotEquals = (_type != null);
    if (_tripleNotEquals) {
      _xifexpression = a.getType().getName();
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  private static String safe(final Provider<String> expression) {
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = expression.get();
    } catch (final Throwable _t) {
      if (_t instanceof NullPointerException) {
        _xtrycatchfinallyexpression = "";
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public static String name(final EObject a) {
    if (a instanceof Attribute) {
      return _name((Attribute)a);
    } else if (a instanceof Edge) {
      return _name((Edge)a);
    } else if (a instanceof NamedElement) {
      return _name((NamedElement)a);
    } else if (a != null) {
      return _name(a);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(a).toString());
    }
  }
}
