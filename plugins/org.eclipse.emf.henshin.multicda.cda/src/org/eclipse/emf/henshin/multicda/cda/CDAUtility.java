package org.eclipse.emf.henshin.multicda.cda;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Span;

public class CDAUtility {

	ResourceSet commonResourceSet;

	public void persistAtomicCPAResult(String/*String or File or URI?*/ path, Rule rule1, Rule rule2,
			List<Span> conflictPartCandidates, List<ConflictAtom> conflictAtoms, Set<Span> minimalConflictReasons) {

		commonResourceSet = new ResourceSetImpl();

		persistRules(path, rule1, rule2);

		persistSpans(path, rule1, rule2, conflictPartCandidates);

		List<Span> conflictAtomSpans = extractSpans(conflictAtoms);
		persistSpans(path, rule1, rule2, conflictAtomSpans);

		persistSpans(path, rule1, rule2, minimalConflictReasons);
	}

	private List<Span> extractSpans(List<ConflictAtom> conflictAtoms) {
		List<Span> spans = new LinkedList<Span>();
		for (ConflictAtom conflictAtom : conflictAtoms)
			spans.add(conflictAtom);
		return spans;
	}

	private void persistSpans(String/*String or File or URI?*/ path, Rule rule1, Rule rule2, Collection<Span> spans) {

	}

	private void persistRules(String/*String or File or URI?*/ path, Rule rule1, Rule rule2) {
		persistRule(path, rule1, "(1)");
		persistRule(path, rule2, "(2)");
	}

	private void persistRule(String/*String or File or URI?*/ path, Rule rule, String prefix) {

		String fileNameRule1 = prefix + rule.getName() + ".henshin";
		String fullPathRule1 = path + fileNameRule1;
		saveRuleInFileSystem(commonResourceSet, rule, fullPathRule1);

	}

	private static URI saveRuleInFileSystem(ResourceSet resourceSet, Rule rule, String fullFilePath) {
		URI firstRuleURI = URI.createFileURI(fullFilePath);
		Resource firstRuleRes = resourceSet.createResource(firstRuleURI, "henshin");
		firstRuleRes.getContents().add(rule);

		try {
			firstRuleRes.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return firstRuleURI;
	}

}
