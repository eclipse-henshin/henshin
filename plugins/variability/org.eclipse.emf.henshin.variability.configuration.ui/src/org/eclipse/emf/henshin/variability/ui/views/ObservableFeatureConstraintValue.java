package org.eclipse.emf.henshin.variability.ui.views;

import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.emf.databinding.internal.EMFObservableValueDecorator;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityConstants;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityRule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityTransactionHelper;

/**
 * Provides an observable value for the Feature Model {@link org.eclipse.emf.henshin.model.Annotation Annotation} of variability-aware {@link Rule}s.
 * 
 * @author Stefan Schulz
 *
 * @param <T>
 */
public class ObservableFeatureConstraintValue<T> implements IObservableValue<String>{
	
	IObservableValue<String> value;
	boolean shouldUpdate;
	
	ObservableFeatureConstraintValue(IObservableValue<String> value) {
		this.value = value;
	}
	
	
	@Override
	public Realm getRealm() {
		return value.getRealm();
	}

	@Override
	public void addChangeListener(IChangeListener listener) {
		value.addChangeListener(listener);
	}

	@Override
	public void removeChangeListener(IChangeListener listener) {
		value.removeChangeListener(listener);
	}

	@Override
	public void addStaleListener(IStaleListener listener) {
		value.addStaleListener(listener);
	}

	@Override
	public void removeStaleListener(IStaleListener listener) {
		value.removeStaleListener(listener);
	}

	@Override
	public boolean isStale() {
		return value.isStale();
	}

	@Override
	public void addDisposeListener(IDisposeListener listener) {
		value.addDisposeListener(listener);
	}

	@Override
	public void removeDisposeListener(IDisposeListener listener) {
		value.removeDisposeListener(listener);
	}

	@Override
	public boolean isDisposed() {
		return value.isDisposed();
	}

	@Override
	public void dispose() {
		value.dispose();
	}

	@Override
	public Object getValueType() {
		return value.getValueType();
	}

	@Override
	public String getValue() {
		VariabilityRule rule = getTargetVariabilityRule();
		if (rule != null) {
			return rule.getFeatureConstraint();			
		}
		return value.getValue();
	}

	@Override
	public void setValue(String value) {
		shouldUpdate = false;
		VariabilityRule rule = getTargetVariabilityRule();
		if (rule != null) {
			VariabilityTransactionHelper.setAnnotationValue(rule, VariabilityConstants.FEATURE_CONSTRAINT, value);
		}
		shouldUpdate = true;
	}

	@Override
	public void addValueChangeListener(IValueChangeListener<? super String> listener) {
		value.addValueChangeListener(listener);
	}

	@Override
	public void removeValueChangeListener(IValueChangeListener<? super String> listener) {
		value.removeValueChangeListener(listener);
		
	}
	
	private VariabilityRule getTargetVariabilityRule() {
		if (this.value instanceof EMFObservableValueDecorator) {
			EMFObservableValueDecorator emfValue = (EMFObservableValueDecorator) this.value;
			
			if (emfValue.getObserved() != null &&  emfValue.getObserved() instanceof Rule) {
				return TransactionalVariabilityFactory.INSTANCE.createVariabilityRule((Rule) emfValue.getObserved());
			}
		}
		return null;
	}


	public boolean shouldUpdate() {
		return shouldUpdate;
	}
}
