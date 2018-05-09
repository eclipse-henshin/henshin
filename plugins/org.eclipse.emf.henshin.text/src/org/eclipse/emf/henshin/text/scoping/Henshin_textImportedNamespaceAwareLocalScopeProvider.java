package org.eclipse.emf.henshin.text.scoping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.text.henshin_text.ConditionEdge;
import org.eclipse.emf.henshin.text.henshin_text.ConditionGraph;
import org.eclipse.emf.henshin.text.henshin_text.ConditionGraphElements;
import org.eclipse.emf.henshin.text.henshin_text.ConditionNode;
import org.eclipse.emf.henshin.text.henshin_text.ConditionReuseNode;
import org.eclipse.emf.henshin.text.henshin_text.EPackageImport;
import org.eclipse.emf.henshin.text.henshin_text.Edge;
import org.eclipse.emf.henshin.text.henshin_text.Graph;
import org.eclipse.emf.henshin.text.henshin_text.GraphElements;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.henshin_text.MultiRuleReuseNode;
import org.eclipse.emf.henshin.text.henshin_text.Node;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Lists;

public class Henshin_textImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	/*
	 * Workaround/BugFix for "No EObjectDescription in Scope..." 
	 * 
	 * The reason for the error message „No EObjectDescription in Scope …“ was, that the .ecore-File from the referred
	 * EPackage wasn’t saved fast enough, so XText wasn’t able to create the needed EObjectDescriptions in the scope.
	 * The scope contains an EObjectDescription for all elements, which can be referred to in a given context. E.g. if
	 * we wanted to import the bank.ecore like this: “ePackageImport bank” the scope will map the String “bank” to an
	 * EObjectDescription, that contains the name of the referred EPackage and its EMF URI. If the bank.ecore is not
	 * saved and registered fast enough, the function getScope of the Class ImportedNamespaceAwareLocalScopeProvider
	 * can’t receive the EObjectDescription for the EPackage and the error occurs. To fix this problem the class
	 * Henshin_textImportedNamespaceAwareLocalScopeProvider overrides the function getScope(EObject context, EReference
	 * reference). The new function consists of the same code as the original one, besides the if(result!=null &&
	 * !result.getAllElements().iterator().hasNext()&&(context instanceof EPackageImport)){..}. The function returns the
	 * scope of the considered context. E.g. in the context of an EPackageImport the scope should contain all
	 * EObjectDescriptions of EPackages, which can be referred to by an EPackageImport. If the imported .ecore-File is
	 * not saved and registered fast enough the normal function getScope can’t create the scope, because it expects the
	 * .ecore-File in the same folder as the .henshin_text-File. The new if-statement catches this case and adds the
	 * corresponding EObjectdescription. To receive the needed EObject of the referred EPackage we need to use
	 * reflection, because if we access the EPackage by ((EPackageImport) context).getRef() XText throws a
	 * LazyLinkingResource$CyclicLinkingException-Exception. At last we need to call the function scopeFor(Iterable<?
	 * extends EObject> elements), which creates a scope that contains for each element in the given list a
	 * corresponding EObjectDescription.
	 * 
	 * @see
	 * org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider#getScope(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EReference)
	 */
	@Override
	public IScope getScope(EObject context, EReference reference) {

		if (context == null)
			throw new NullPointerException("context");

		IScope result = null;
		if (context.eContainer() != null) {
			result = getScope(context.eContainer(), reference);
		} else {
			result = getResourceScope(context.eResource(), reference);
		}

		if (result != null && !result.getAllElements().iterator().hasNext() && (context instanceof EPackageImport)) {
			try {
				Field field = context.getClass().getDeclaredFields()[0];
				field.setAccessible(true);
				EPackage importedPackage = (EPackage) field.get(context);
				ArrayList<EPackage> candidates = new ArrayList<EPackage>();
				candidates.add(importedPackage);
				return Scopes.scopeFor(candidates);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				System.err.println("Error accessing EPackage by reflection");
				e.printStackTrace();
			}
		}
		return getLocalElementsScope(result, context, reference);
	}

	@Override
	protected List<ImportNormalizer> internalGetImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
		List<ImportNormalizer> importedNamespaceResolvers = Lists.newArrayList();
		if (context instanceof Model) {
			Model model = (Model) context;
			for (EPackageImport ePackageImport : model.getEPackageimports()) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(ePackageImport,
						Henshin_textPackage.Literals.EPACKAGE_IMPORT__REF);
				if (nodes.size() > 0) {
					INode inode = nodes.get(0);
					String packageName = NodeModelUtils.getTokenText(inode);
					importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
				}
			}
		}
		if (context instanceof Node) {
			Node node = (Node) context;
			List<INode> nodes = NodeModelUtils.findNodesForFeature(node, Henshin_textPackage.Literals.NODE__NODETYPE);
			if (nodes.size() > 0) {
				INode inode = nodes.get(0);
				String packageName = NodeModelUtils.getTokenText(inode);
				importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
				String splitPackageName[] = packageName.split("\\.");
				if (splitPackageName.length == 1) {
					splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
				}
				addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
			}
		}
		if (context instanceof ConditionNode) {
			ConditionNode node = (ConditionNode) context;
			List<INode> nodes = NodeModelUtils.findNodesForFeature(node,
					Henshin_textPackage.Literals.CONDITION_NODE__TYPE);
			if (nodes.size() > 0) {
				INode inode = nodes.get(0);
				String packageName = NodeModelUtils.getTokenText(inode);
				importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
				String splitPackageName[] = packageName.split("\\.");
				if (splitPackageName.length == 1) {
					splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
				}
				addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
			}
		}
		if (context instanceof ConditionReuseNode) {
			ConditionReuseNode node = (ConditionReuseNode) context;
			List<INode> nodes = NodeModelUtils.findNodesForFeature(node,
					Henshin_textPackage.Literals.CONDITION_REUSE_NODE__NAME);
			if (nodes.size() > 0) {
				INode inode = nodes.get(0);
				String nodeName = NodeModelUtils.getTokenText(inode);

				Node reusedNode = null;
				ConditionNode reusedConNode = null;
				boolean nodeFound = false;
				EObject container = node.eContainer().eContainer();
				while (nodeFound != true) {
					if (container instanceof Graph) {
						Graph graph = (Graph) container;
						for (GraphElements element : graph.getGraphElements()) {
							if (element instanceof Node) {
								if (((Node) element).getName().equals(nodeName)) {
									reusedNode = (Node) element;
									nodeFound = true;
								}
							}
						}
					} else if (container instanceof ConditionGraph) {
						ConditionGraph graph = (ConditionGraph) container;
						for (ConditionGraphElements element : graph.getConditionGraphElements()) {
							if (element instanceof ConditionNode) {
								if (((ConditionNode) element).getName().equals(nodeName)) {
									reusedConNode = (ConditionNode) element;
									nodeFound = true;
								}
							}
						}
					}
					container = container.eContainer();
					if (container instanceof Model) {
						break;
					}
				}
				if ((reusedNode != null) && (reusedConNode == null)) {
					List<INode> reNodes = NodeModelUtils.findNodesForFeature(reusedNode,
							Henshin_textPackage.Literals.NODE__NODETYPE);
					if (reNodes.size() > 0) {
						INode reInode = reNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(reInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				} else if ((reusedNode == null) && (reusedConNode != null)) {
					List<INode> reConNodes = NodeModelUtils.findNodesForFeature(reusedConNode,
							Henshin_textPackage.Literals.CONDITION_NODE__TYPE);
					if (reConNodes.size() > 0) {
						INode reConInode = reConNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(reConInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				}
			}
		}
		if (context instanceof MultiRuleReuseNode) {
			MultiRuleReuseNode node = (MultiRuleReuseNode) context;
			List<INode> nodes = NodeModelUtils.findNodesForFeature(node,
					Henshin_textPackage.Literals.MULTI_RULE_REUSE_NODE__NAME);
			if (nodes.size() > 0) {
				INode inode = nodes.get(0);

				String nodeName = NodeModelUtils.getTokenText(inode);

				Node reusedNode = null;

				boolean nodeFound = false;
				EObject container = node.eContainer().eContainer();
				while (nodeFound != true) {
					if (container instanceof Graph) {
						Graph graph = (Graph) container;
						for (GraphElements element : graph.getGraphElements()) {
							if (element instanceof Node) {
								if (((Node) element).getName().equals(nodeName)) {
									reusedNode = (Node) element;
									nodeFound = true;
								}
							}
						}
					}
					container = container.eContainer();
					if (container instanceof Model) {
						break;
					}
				}
				if (reusedNode != null) {
					List<INode> reNodes = NodeModelUtils.findNodesForFeature(reusedNode,
							Henshin_textPackage.Literals.NODE__NODETYPE);
					if (reNodes.size() > 0) {
						INode reInode = reNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(reInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				}
			}
		}
		if (context instanceof Edge) {
			Edge edge = (Edge) context;
			List<INode> sourceNodes = NodeModelUtils.findNodesForFeature(edge,
					Henshin_textPackage.Literals.EDGE__SOURCE);
			if (sourceNodes.size() > 0) {
				INode inode = sourceNodes.get(0);
				String nodeName = NodeModelUtils.getTokenText(inode);
				Node sourceNode = null;
				boolean nodeFound = false;
				EObject container = edge.eContainer();
				while (nodeFound != true) {
					if (container instanceof Graph) {
						Graph graph = (Graph) container;
						for (GraphElements element : graph.getGraphElements()) {
							if (element instanceof Node) {
								if (((Node) element).getName() != null) {
									if (((Node) element).getName().equals(nodeName)) {
										sourceNode = (Node) element;
										nodeFound = true;
									}
								}
							}
						}
					}
					container = container.eContainer();
					if (container instanceof Model) {
						break;
					}
				}
				if (sourceNode != null) {
					List<INode> reNodes = NodeModelUtils.findNodesForFeature(sourceNode,
							Henshin_textPackage.Literals.NODE__NODETYPE);
					if (reNodes.size() > 0) {
						INode reInode = reNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(reInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				}
			}
		}
		if (context instanceof ConditionEdge) {
			ConditionEdge edge = (ConditionEdge) context;
			List<INode> sourceNodes = NodeModelUtils.findNodesForFeature(edge,
					Henshin_textPackage.Literals.CONDITION_EDGE__SOURCE);
			if (sourceNodes.size() > 0) {
				INode inode = sourceNodes.get(0);
				String nodeName = NodeModelUtils.getTokenText(inode);
				Node sourceNode = null;
				ConditionNode conSourceNode = null;
				boolean nodeFound = false;
				EObject container = edge.eContainer();
				while (nodeFound != true) {
					if (container instanceof Graph) {
						Graph graph = (Graph) container;
						for (GraphElements element : graph.getGraphElements()) {
							if (element instanceof Node) {
								if (((Node) element).getName().equals(nodeName)) {
									sourceNode = (Node) element;
									nodeFound = true;
								}
							}
						}
					} else if (container instanceof ConditionGraph) {
						ConditionGraph graph = (ConditionGraph) container;
						for (ConditionGraphElements element : graph.getConditionGraphElements()) {
							if (element instanceof ConditionNode) {
								if (((ConditionNode) element).getName().equals(nodeName)) {
									conSourceNode = (ConditionNode) element;
									nodeFound = true;
								}
							}
						}
					}
					container = container.eContainer();
					if (container instanceof Model) {
						break;
					}
				}
				if ((sourceNode != null) && (conSourceNode == null)) {
					List<INode> sourNodes = NodeModelUtils.findNodesForFeature(sourceNode,
							Henshin_textPackage.Literals.NODE__NODETYPE);
					if (sourNodes.size() > 0) {
						INode sourInode = sourNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(sourInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				} else if ((sourceNode == null) && (conSourceNode != null)) {
					List<INode> sourConNodes = NodeModelUtils.findNodesForFeature(conSourceNode,
							Henshin_textPackage.Literals.CONDITION_NODE__TYPE);
					if (sourConNodes.size() > 0) {
						INode sourConInode = sourConNodes.get(0);
						String packageName = NodeModelUtils.getTokenText(sourConInode);
						importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
						String splitPackageName[] = packageName.split("\\.");
						if (splitPackageName.length == 1) {
							splitPackageName = (getPackageName(packageName, context) + "." + packageName).split("\\.");
						}
						addSuperclasses(splitPackageName, context, ignoreCase, importedNamespaceResolvers);
					}
				}
			}
		}
		return importedNamespaceResolvers;
	}

	@Override
	protected ImportNormalizer createImportedNamespaceResolver(String namespace, boolean ignoreCase) {
		if (Strings.isEmpty(namespace)) {
			return null;
		}
		QualifiedName importedNamespace = getQualifiedNameConverter().toQualifiedName(namespace);
		if (importedNamespace == null || importedNamespace.getSegmentCount() < 1) {
			return null;
		}
		return doCreateImportNormalizer(importedNamespace, true, ignoreCase);
	}

	/**
	 * Returns the name from the EPackage that contains a classifier named className
	 * 
	 * @param className
	 * @param context
	 * @return Name from an EPackage
	 */
	private String getPackageName(String className, EObject context) {
		Model model = (Model) context.eResource().getContents().get(0);
		for (EPackageImport imp : model.getEPackageimports()) {
			if ((imp.getRef() != null) && (imp.getRef().getName() != null)) {
				for (EClassifier classifier : imp.getRef().getEClassifiers()) {
					if (classifier.getName().equals(className)) {
						return imp.getRef().getName();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Add superclasses from an object
	 * 
	 * @param splitPackageName
	 * @param context
	 * @param ignoreCase
	 * @param importedNamespaceResolvers
	 */
	private void addSuperclasses(String[] splitPackageName, EObject context, boolean ignoreCase,
			List<ImportNormalizer> importedNamespaceResolvers) {
		if ((splitPackageName != null) && (splitPackageName.length > 1)) {
			Model model = (Model) context.eResource().getContents().get(0);
			for (EPackageImport imp : model.getEPackageimports()) {
				if ((imp.getRef() != null) && (imp.getRef().getName() != null)) {
					if (imp.getRef().getName().equals(splitPackageName[0])) {
						for (EClassifier classifier : imp.getRef().getEClassifiers()) {
							if (classifier.getName().equals(splitPackageName[1])) {
								for (String superTypes : getAllSuperTypes(((EClass) classifier))) {
									String superType = splitPackageName[0] + "." + superTypes;
									importedNamespaceResolvers
											.add(createImportedNamespaceResolver(superType, ignoreCase));
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Get all superclasses from an EClass and their superclasses
	 * 
	 * @param eClass considered EClass
	 * @return List of all superclasses
	 */
	private List<String> getAllSuperTypes(EClass eClass) {
		List<String> names = new ArrayList<String>();
		for (EClass superType : eClass.getESuperTypes()) {
			names.add(superType.getName());
			names.addAll(getAllSuperTypes(superType));
		}
		return names;
	}
}
