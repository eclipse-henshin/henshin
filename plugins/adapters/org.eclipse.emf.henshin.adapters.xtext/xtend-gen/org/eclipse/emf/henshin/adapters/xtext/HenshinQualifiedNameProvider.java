package org.eclipse.emf.henshin.adapters.xtext;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.adapters.xtext.NamingHelper;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

@SuppressWarnings("all")
public class HenshinQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
  private static final String HENSHIN_CACHE_KEY = "HENSHIN_CACHE_KEY";
  
  @Inject
  private final IResourceScopeCache cache = IResourceScopeCache.NullImpl.INSTANCE;
  
  @Override
  public QualifiedName getFullyQualifiedName(final EObject obj) {
    final Provider<QualifiedName> _function = () -> {
      QualifiedName _xblockexpression = null;
      {
        final String name = NamingHelper.name(obj);
        QualifiedName _xifexpression = null;
        if ((name == null)) {
          _xifexpression = null;
        } else {
          QualifiedName _xblockexpression_1 = null;
          {
            final QualifiedName qualifiedName = QualifiedName.create(name);
            QualifiedName _xifexpression_1 = null;
            EObject _eContainer = obj.eContainer();
            boolean _tripleNotEquals = (_eContainer != null);
            if (_tripleNotEquals) {
              QualifiedName _xblockexpression_2 = null;
              {
                final QualifiedName parentsQualifiedName = this.getFullyQualifiedName(obj.eContainer());
                QualifiedName _xifexpression_2 = null;
                if ((parentsQualifiedName == null)) {
                  _xifexpression_2 = null;
                } else {
                  _xifexpression_2 = parentsQualifiedName.append(qualifiedName);
                }
                _xblockexpression_2 = _xifexpression_2;
              }
              _xifexpression_1 = _xblockexpression_2;
            } else {
              _xifexpression_1 = qualifiedName;
            }
            _xblockexpression_1 = _xifexpression_1;
          }
          _xifexpression = _xblockexpression_1;
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    };
    return this.cache.<QualifiedName>get(Tuples.<EObject, String>pair(obj, HenshinQualifiedNameProvider.HENSHIN_CACHE_KEY), obj.eResource(), _function);
  }
}
