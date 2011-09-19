package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.presentation.HenshinIcons;
import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Additional palette tools for the graphical Henshin editor.
 * @author Christian Krause
 */
public class HenshinPaletteTools {

	// EClass icon:
	public static ImageDescriptor ECLASS_ICON = ImageDescriptor.createFromImage(HenshinIcons.ECLASS);
	
	// EPackage icon:
	public static ImageDescriptor EPACKAGE_ICON = ImageDescriptor.createFromImage(HenshinIcons.EPACKAGE);

	/**
	 * Creation tool for nodes.
	 */
	public static class EClassNodeTool extends UnspecifiedTypeCreationTool {
		
		/**
		 * Key for the node type parameter in creation requests.
		 */
		public static final String TYPE_PARAMETER_KEY = "eclass_node_type";
	
		// Request parameters.
		private Map<Object,Object> parameters = new HashMap<Object,Object>();
		
		public EClassNodeTool(EClass eclass, List<IElementType> types) {
			super(types);
			parameters.put(TYPE_PARAMETER_KEY, eclass);
		}
		
		@Override
		protected Request createTargetRequest() {
			Request request = super.createTargetRequest();
			request.setExtendedData(parameters);
			return request;
		}
	
	}

	/**
	 * Creation palette tool entry for nodes.
	 */
	public static class EClassNodeToolEntry extends ToolEntry {
				
		// EClass:
		private EClass eclass;
		
		// Element types: Henshin node
		private static final List<IElementType> HENSHIN_NODE_TYPES;
		static {
			HENSHIN_NODE_TYPES = new ArrayList<IElementType>(1);
			HENSHIN_NODE_TYPES.add(HenshinElementTypes.Node_3001);
		}
	
		public EClassNodeToolEntry(EClass eclass) {
			super(eclass.getName(), "Create a " + eclass.getName() + " node", ECLASS_ICON, null);
			this.eclass = eclass;
		}
		
		@Override
		public Tool createTool() {
			Tool tool = new EClassNodeTool(eclass, HENSHIN_NODE_TYPES);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	private static String unitTypeName(EClass type) {
		return type.getName().replaceFirst("Unit", " Unit");
	}
	
	/**
	 * Creation palette tool entry for transformation units.
	 */
	public static class UnitNodeToolEntry extends ToolEntry {

		// Element types: Transformation unit node
		private static final List<IElementType> UNIT_NODE_TYPES;
		static {
			UNIT_NODE_TYPES = new ArrayList<IElementType>(1);
			UNIT_NODE_TYPES.add(HenshinElementTypes.TransformationUnit_2002);
		}

		// Unit type:
		private EClass type;

		public UnitNodeToolEntry(EClass type) {
			super(unitTypeName(type), "Create a " + unitTypeName(type), 
					HenshinElementTypes.getImageDescriptor(type), null);
			this.type = type;
		}
		
		@Override
		public Tool createTool() {
			Tool tool = new EClassNodeTool(type, UNIT_NODE_TYPES);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

}
