package org.eclipse.emf.henshin.text.transformation.tests.util;

import java.util.List;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.impl.ReferenceChangeImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Compare {

	/**
	 * Compares the attribute values
	 * 
	 * @param originalNodes List of nodes from the original henshin transformation
	 * @param transformationNodes List of nodes from the adapted henshin_text transformation
	 * @return Error message
	 */
	public String compareAttributesByNode(NodeList originalNodes, NodeList transformationNodes) {
		String message = "";
		for (int i = 0; i < transformationNodes.getLength(); i++) {
			if (transformationNodes.item(i).hasAttributes()) {
				for (int j = 0; j < transformationNodes.item(i).getAttributes().getLength(); j++) {
					Node originalAttribute = originalNodes.item(i).getAttributes()
							.getNamedItem(transformationNodes.item(i).getAttributes().item(j).getNodeName());
					Node transformationAttribute = transformationNodes.item(i).getAttributes().item(j);
					if ((originalAttribute != null)
							&& !(isEqual(originalAttribute.getNodeValue(), transformationAttribute.getNodeValue()))) {
						return "Expected: " + originalAttribute + " Transformed: " + transformationAttribute;
					}
				}
			}
			message = message + compareAttributesByNode(originalNodes.item(i).getChildNodes(),
					transformationNodes.item(i).getChildNodes());
		}
		return message;
	}

	/**
	 * Check if the String transformation is contained in original
	 * 
	 * @param original original String
	 * @param transformation transformed String
	 * @return true if original contains transformation
	 */
	private boolean isEqual(String original, String transformation) {
		String transformationArray[] = transformation.split(" ");
		boolean contained = true;
		if (transformationArray.length != original.split(" ").length) {
			return false;
		}
		for (int i = 0; i < transformationArray.length; i++) {
			contained = contained && (original.contains(transformationArray[i]));
		}
		return contained;

	}

	/**
	 * Compares models with emf compare
	 * 
	 * @param originalPath Path to the original henshin transformation
	 * @param transformationPath Path to the adapted henshin_text transformation
	 * @return textual representation of the differences
	 */
	public String emfCompare(String originalPath, String transformationPath) {
		String message = "";
		String[] originalPathArray = originalPath.split("/");
		String path = originalPathArray[0] + "/" + originalPathArray[1];
		HenshinResourceSet resourceSetOriginal = new HenshinResourceSet(path);
		HenshinResourceSet resourceSetTransformation = new HenshinResourceSet(path);
		resourceSetOriginal.getResource(originalPath.replace(path + "/", ""));
		resourceSetTransformation.getResource(transformationPath.replace(path + "/", ""));
		IComparisonScope scope = new DefaultComparisonScope(resourceSetOriginal, resourceSetTransformation, null);
		Comparison comparison = EMFCompare.builder().build().compare(scope);
		List<Diff> differences = comparison.getDifferences();
		for (Diff difference : differences) {
			// we ignore differences in ordering of references 
			if (! (difference instanceof ReferenceChangeImpl && difference.getKind() == DifferenceKind.MOVE ))
			{
				message = message + difference + "\n";
			}
		}
		return message;
	}
}
