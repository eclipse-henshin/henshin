package org.eclipse.emf.henshin.multicda.cda.conflict;

import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.exporters.HenshinAGGExporter;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;

public class ConflictReasonCreator /*extends InitialConflictReason */ {

	public static EssentialConflictReason createConflictReason(Conflict cp) {

		HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

		Match match1 = cp.getMatch1();
		Match match2 = cp.getMatch2();
		Rule rule1 = cp.getFirstRule();
		Rule rule2 = cp.getSecondRule();

		MappingList mappingList1 = new MappingListImpl();
		MappingList mappingList2 = new MappingListImpl();

		Graph graph = henshinFactory.createGraph();

		for (Node lhs1Node : rule1.getLhs().getNodes()) {
			EObject nodeTargetOfR1 = match1.getNodeTarget(lhs1Node);
			if (nodeTargetOfR1 != null) {
				for (Node lhs2Node : rule2.getLhs().getNodes()) {
					EObject nodeTargetOfR2 = match2.getNodeTarget(lhs2Node);
					if (nodeTargetOfR2 != null) {
						if (nodeTargetOfR1 == nodeTargetOfR2) {
							EClass type = null;
							EClass typeOfRule1 = lhs1Node.getType();
							EClass typeOfRule2 = lhs2Node.getType();
							EList<EClass> eAllSuperTypesOfR1EClass = typeOfRule1.getEAllSuperTypes();
							EList<EClass> eAllSuperTypesOfR2EClass = typeOfRule2.getEAllSuperTypes();
							if (eAllSuperTypesOfR1EClass.contains(typeOfRule2)) {
								type = typeOfRule1;
							} else if (eAllSuperTypesOfR2EClass.contains(typeOfRule1)) {
								type = typeOfRule2;
							} else if (typeOfRule1 == typeOfRule2) {
								type = typeOfRule1;
							}
							if (type != null) {
								String nameOfNode = lhs1Node.getName() + Reason.NODE_SEPARATOR + lhs2Node.getName();
								Node createdNode = henshinFactory.createNode(graph, type, nameOfNode);
								Mapping mappingR1 = henshinFactory.createMapping(createdNode, lhs1Node);
								mappingList1.add(mappingR1);
								Mapping mappingR2 = henshinFactory.createMapping(createdNode, lhs2Node);
								mappingList2.add(mappingR2);

								for (Node nodeInS1 : graph.getNodes()) {
									Node potentialConnectedNodeInR1 = mappingList1.getImage(nodeInS1, rule1.getLhs());
									EList<Edge> outgoingEdgesOfL1Node = lhs1Node.getOutgoing();
									for (Edge outgoingEdgeOfL1Node : outgoingEdgesOfL1Node) {
										if (outgoingEdgeOfL1Node.getTarget() == potentialConnectedNodeInR1) {
											Node potentialConnectedNodeInR2 = mappingList2.getImage(nodeInS1,
													rule2.getLhs());
											EList<Edge> outgoingEdgesOfL2Node = lhs2Node.getOutgoing();
											for (Edge outgoingEdgeOfL2Node : outgoingEdgesOfL2Node) {
												if (outgoingEdgeOfL2Node.getTarget() == potentialConnectedNodeInR2) {
													if (nodeTargetOfR1 instanceof EClass) { // should always be true!
														EClass eClassOfOverlapBeeingProcessed = (EClass) nodeTargetOfR1;
														EList<EReference> eAllReferences = eClassOfOverlapBeeingProcessed
																.getEAllReferences();
														for (EReference eReference : eAllReferences) {
															String uniqueReferenceNameOfOverlap = eReference.getName();
															String uniqueReferenceNameOfR1 = "";
															String uniqueReferenceNameOfR2 = "";
															try {
																uniqueReferenceNameOfR1 = HenshinAGGExporter
																		.getUniqueReferenceName(
																				outgoingEdgeOfL1Node.getType());
															} catch (Exception e) {
																e.printStackTrace();
															}
															try {
																uniqueReferenceNameOfR2 = HenshinAGGExporter
																		.getUniqueReferenceName(
																				outgoingEdgeOfL2Node.getType());
															} catch (Exception e) {
																e.printStackTrace();
															}
															if (uniqueReferenceNameOfOverlap
																	.equals(uniqueReferenceNameOfR1)
																	&& uniqueReferenceNameOfOverlap
																			.equals(uniqueReferenceNameOfR2)) {
																henshinFactory.createEdge(createdNode,
																		potentialConnectedNodeInR1,
																		outgoingEdgeOfL1Node.getType());
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		Reason span = new DeleteConflictReason(new HashSet<Mapping>(mappingList1), graph,
				new HashSet<Mapping>(mappingList2));
		return new EssentialConflictReason(span);
	}
}