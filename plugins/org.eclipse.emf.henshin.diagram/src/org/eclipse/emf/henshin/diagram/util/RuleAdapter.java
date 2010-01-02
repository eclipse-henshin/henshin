package org.eclipse.emf.henshin.diagram.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class RuleAdapter extends AdapterImpl {
	
	public interface Notifiable {
		void handleNotificationEvent(Notification event);
	}
	
	private Notifiable notifiable;
	private Rule rule;

	public RuleAdapter(Rule rule, Notifiable notifiable) {
		this.rule = rule;
		this.notifiable = notifiable;
		rule.eAdapters().add(this);
		if (rule.getLhs()!=null) {
			rule.getLhs().eAdapters().add(this);
		}
		if (rule.getRhs()!=null) {
			rule.getRhs().eAdapters().add(this);
		}
	}
	
	public void dispose() {
		rule.eAdapters().remove(this);
		if (rule.getLhs()!=null) {
			rule.getLhs().eAdapters().remove(this);
		}
		if (rule.getRhs()!=null) {
			rule.getRhs().eAdapters().remove(this);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification event) {
		int type = event.getEventType();
		if (type==Notification.ADD || type==Notification.SET) {
			if (event.getNewValue() instanceof Graph) {
				((Graph) event.getNewValue()).eAdapters().add(this);
			}
		}
		if (type==Notification.REMOVE) {
			if (event.getNewValue() instanceof Graph) {
				((Graph) event.getNewValue()).eAdapters().remove(this);
			}
		}
		notifiable.handleNotificationEvent(event);
	}
	
}
