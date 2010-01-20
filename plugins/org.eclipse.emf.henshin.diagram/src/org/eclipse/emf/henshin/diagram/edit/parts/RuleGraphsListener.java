package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class RuleGraphsListener {
	
	// Adapter:
	private Adapter adapter;
	
	// Rule:
	private Rule rule;
	
	/**
	 * Default constructor.
	 * @param rule Rule to be listened to.
	 * @param adapter Adapter to be notified.
	 */
	public RuleGraphsListener(Rule rule, Adapter adapter) {
		
		this.rule = rule;
		this.adapter = adapter;
		
		// Register the proxy adapter:
		rule.eAdapters().add(proxy);
		if (rule.getLhs()!=null) {
			rule.getLhs().eAdapters().add(proxy);
		}
		if (rule.getRhs()!=null) {
			rule.getRhs().eAdapters().add(proxy);
		}
		
	}
	
	/**
	 * Dispose this rule graphs listener.
	 */
	public void dispose() {
		rule.eAdapters().remove(proxy);
		if (rule.getLhs()!=null) {
			rule.getLhs().eAdapters().remove(proxy);
		}
		if (rule.getRhs()!=null) {
			rule.getRhs().eAdapters().remove(proxy);
		}	
	}
	
	// Proxy adapter:
	private Adapter proxy = new Adapter() {
		
		public void notifyChanged(Notification event) {
			
			// Forward graph events to the main adapter:
			if (event.getNotifier() instanceof Graph) {
				adapter.notifyChanged(event);
				return;
			}
			
			// Otherwise it is a rule event:
			if (event.getOldValue() instanceof Graph) {
				((Graph) event.getNewValue()).eAdapters().remove(proxy);
			}
			if (event.getNewValue() instanceof Graph) {
				((Graph) event.getNewValue()).eAdapters().add(proxy);
			}
			
		}
		
		public Notifier getTarget() {
			return adapter.getTarget();
		}

		public boolean isAdapterForType(Object type) {
			return adapter.isAdapterForType(type);
		}

		public void setTarget(Notifier newTarget) {
			adapter.setTarget(newTarget);
		}

	};
		
}
