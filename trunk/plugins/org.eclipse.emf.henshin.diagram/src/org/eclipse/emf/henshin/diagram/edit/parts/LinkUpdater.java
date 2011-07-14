package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This class provides methods for updating the
 * links in transformation unit views.
 * @author Christian Krause
 */
public class LinkUpdater {
	
	private static final HenshinViewProvider VIEW_PROVIDER = new HenshinViewProvider();
	private static final String LINK_TYPE = HenshinVisualIDRegistry.getType(LinkEditPart.VISUAL_ID);

	public static void update(View unitView) {
		EObject unit = unitView.getElement();
		if (unit instanceof SequentialUnit) {
			updateSequentialUnit(unitView);
		}
	}

	public static void updateSequentialUnit(View unitView) {
		SequentialUnit unit = (SequentialUnit) unitView.getElement();
		View compartment = getUnitCompartment(unitView);
		Diagram diagram = compartment.getDiagram();
		View begin = SymbolType.SEQUENTIAL_BEGIN.findSymbolView(compartment, unit);
		View end = SymbolType.SEQUENTIAL_END.findSymbolView(compartment, unit);
		if (unit.getSubUnits().isEmpty()) {
			if (begin!=null && end!=null) {
				findOrCreateLink(diagram, begin, end);
			}
		}
	}
	
	private static View getUnitCompartment(View unitView) {
		return ViewUtil.getChildBySemanticHint(unitView,
						HenshinVisualIDRegistry
								.getType(UnitCompartmentEditPart.VISUAL_ID));
	}
	
	private static Edge findOrCreateLink(Diagram diagram,View source, View target) {
		for (Object obj : diagram.getEdges()) {
			Edge edge = (Edge) obj;
			if (edge.getSource()==source && edge.getTarget()==target &&
				LINK_TYPE.equals(edge.getType())) {
				return edge;
			}
		}
		Edge link = VIEW_PROVIDER.createLink_4002(diagram, -1, false, HenshinDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		link.setSource(source);
		link.setTarget(target);
		return link;
	}

}
