package org.eclipse.emf.henshin.interpreter.giraph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class GiraphUtil {

	public static String getInstanceCode(Rule rule) {
		StringBuffer json = new StringBuffer();
		GiraphRuleData data = new GiraphRuleData(rule);
		List<ENamedElement> types = new ArrayList<ENamedElement>(data.typeConstants.keySet());
		for (int i=0; i<rule.getLhs().getNodes().size(); i++) {
			Node n = rule.getLhs().getNodes().get(i);
			json.append("[[" + i + "]," + types.indexOf(n.getType()) + ",[");
			for (int j=0; j<n.getOutgoing().size(); j++) {
				Edge e = n.getOutgoing().get(j);
				int trg = rule.getLhs().getNodes().indexOf(e.getTarget());
				json.append("[[" + trg + "]," + types.indexOf(e.getType()) + "]");
				if (j<n.getOutgoing().size()-1) json.append(",");
			}
			json.append("]]\n");
		}
		return json.toString();
	}

}
