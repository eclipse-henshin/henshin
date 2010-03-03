package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Formula;
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
		TreeIterator<Object> it = EcoreUtil.getAllContents(rule, true);
		while (it.hasNext()) {
			Object next = it.next();
			if (next instanceof Notifier) {
				((Notifier) next).eAdapters().remove(proxy);
			}
		}
	}
	
	/*
	 *  Proxy adapter.
	 */
	private Adapter proxy = new Adapter() {
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		public void notifyChanged(Notification event) {
			Object notifier = event.getNotifier();
			
			// Notify the main adapter about graph events:
			if (notifier instanceof Graph) {
				if (((Graph) notifier).getContainerRule()==rule) {
					adapter.notifyChanged(event);					
				} else {
					((Graph) notifier).eAdapters().remove(proxy);
				}
			}
			
			// We are interested in notifications from Graphs and Formulas
			if (notifier instanceof Graph || notifier instanceof Formula) {
			
				// Containment reference value changed?
				if (event.getFeature() instanceof EReference) {

					EReference reference = (EReference) event.getFeature();
					if (reference.isContainment()) {

						if (event.getEventType()==Notification.ADD) {
							((Notifier) event.getNewValue()).eAdapters().add(proxy);
						}
						else if (event.getEventType()==Notification.REMOVE) {
							((Notifier) event.getOldValue()).eAdapters().remove(proxy);
						}
						else if (event.getEventType()==Notification.SET) {
							if (event.getOldValue()!=null) {
								((Notifier) event.getOldValue()).eAdapters().remove(proxy);								
							}
							if (event.getNewValue()!=null) {
								((Notifier) event.getNewValue()).eAdapters().add(proxy);								
							}
						}
					}

				}
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
