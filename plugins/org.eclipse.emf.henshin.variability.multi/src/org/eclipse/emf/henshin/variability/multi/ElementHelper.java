package org.eclipse.emf.henshin.variability.multi;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.cnf.CNFFormat;

public class ElementHelper {

	protected static String getFMExpressionAsCNF(IFeatureModel fm) {
		String fullCNF = new CNFFormat().write(fm);
		String startText = "Textual Symbols:\r\n";
		int start = fullCNF.indexOf(startText);
		String cnf = fullCNF.substring(start + startText.length());
		return cnf.substring(0, cnf.indexOf('\n') - 1);
	}
}
