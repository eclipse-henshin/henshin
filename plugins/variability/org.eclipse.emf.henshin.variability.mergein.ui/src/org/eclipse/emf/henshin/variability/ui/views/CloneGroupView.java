package org.eclipse.emf.henshin.variability.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;
import org.eclipse.emf.henshin.variability.ui.MergeClusteredRulesAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.notation.impl.ConnectorImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class CloneGroupView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.henshin.variability.ui.views.CloneGroupView";

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action highlightClonePartAction;
	private Action mergeClusteredRulesAction;
	private Action doubleClickAction;

	private ModuleEditPart selectedModuleEditPart;
	private CloneGroupDetectionResult cloneDetectionResult;
	
	private List<BorderedNodeFigure> presentBorderFigures;
	public Map<EdgeEditPart, Color> edgeColorCache;
	public Map<AttributeEditPart, Color> attributeColorCache;

	private TransactionalEditingDomain editingDomain;

	
	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
class TreeObject implements IAdaptable {
		private String name;
		private TreeParent parent;
		private Object model;
		
		public Object getModel() {
			return model;
		}

		public void setModel(Object model) {
			this.model = model;
		}

		public TreeObject(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		public String toString() {
			return getName();
		}

		public <T> T getAdapter(Class<T> key) {
			return null;
		}
	}

	class TreeParent extends TreeObject {

		private ArrayList<TreeObject> children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList<TreeObject>();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children
					.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof List) {
				ArrayList<Object> children = new ArrayList<Object>();
				int index = 0;
				for (Object o : (List<?>) parent) {
					index++;
					CloneGroup cg = (CloneGroup) o;
					TreeParent tp = new TreeParent("Clone group "+
							index + ": "
							+ cg.getRules().size() + " rules, "
							+ cg.getNumberOfCommonEdges() + " common edges");
					tp.setModel(cg);
					children.add(tp);
					for (Rule r : cg.getRules()) {
						TreeObject leaf = new TreeObject("Rule " + r.getName());
						leaf.setModel(r);
						tp.addChild(leaf);
					}
				}
				return (Object[]) (children)
						.toArray(new Object[children.size()]);
			} else if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			if (parent instanceof List)
				return !((List<?>) parent).isEmpty();
			return false;
		}

		private void initialize() {
			TreeParent root = new TreeParent(
					"To see something here, use the \"Start Clone Detection\" context menu entry in the Henshin diagram editor.");
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(imageKey);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public CloneGroupView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(viewer.getControl(),
						"org.eclipse.emf.henshin.variability.ui.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CloneGroupView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(highlightClonePartAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(highlightClonePartAction);
		manager.add(new Separator());
		manager.add(mergeClusteredRulesAction);
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(highlightClonePartAction);
		manager.add(new Separator());
		manager.add(mergeClusteredRulesAction);
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		highlightClonePartAction = new HighlightClonePartInRuleAction();
		highlightClonePartAction.setText("Show in Henshin Diagram Editor");
		highlightClonePartAction.setToolTipText("The corresponding model elements will be highlighted.");
		highlightClonePartAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		createClusteredRulesAction();

		doubleClickAction = new HighlightClonePartInRuleAction();
	}

	private void createClusteredRulesAction() {
		mergeClusteredRulesAction = new MergeClusteredRulesAction(cloneDetectionResult, editingDomain);
		mergeClusteredRulesAction.setText("Merge clones");
		mergeClusteredRulesAction.setToolTipText("Performs a cluster analysis and merges the rules based on the result of that analysis.");
		mergeClusteredRulesAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
	}
	
	class HighlightClonePartInRuleAction extends Action {

		public void run() {
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection)
					.getFirstElement();
			if (obj instanceof TreeObject
					&& ((TreeObject) obj).getModel() instanceof Rule) {
				TreeObject treeObject = (TreeObject) obj;
				Rule rule = (Rule) treeObject.getModel();
				RuleEditPart editPart = findRuleEditPart(selectedModuleEditPart, rule);
				selectedModuleEditPart.getViewer().reveal(editPart);
				highlightRelevantElements(editPart, rule, (CloneGroup) treeObject.getParent().getModel());					
			}
		}

		private void highlightRelevantElements(RuleEditPart editPart, Rule rule, CloneGroup cloneGroup) {
			resetColorCoding();

			for (Edge e : cloneGroup.getEdgeMappings().keySet()) {
				NodeEditPart sourceEditPart = (NodeEditPart) editPart.findEditPart(editPart, e.getSource().getActionNode());
				NodeEditPart targetEditPart = (NodeEditPart)  editPart.findEditPart(editPart, e.getTarget().getActionNode());
				if (sourceEditPart != null) {
					recolorEdge(sourceEditPart, e, true);
					addBorderNode(sourceEditPart);
				}
				if (targetEditPart != null) {
					addBorderNode(targetEditPart);
					recolorEdge(targetEditPart, e, false);
				}
			}
			
			for (Attribute a : cloneGroup.getAttributeMappings().keySet()) {
				recolorAttribute(editPart, a);
			}
		}

		private void recolorAttribute(RuleEditPart editPart, Attribute a) {
			AttributeEditPart attributeEditPart = (AttributeEditPart) editPart.findEditPart(editPart, a.getActionAttribute());
			if (attributeEditPart != null && attributeEditPart.getFigure().getForegroundColor() != ColorConstants.red) {
				attributeColorCache.put(attributeEditPart, attributeEditPart.getFigure().getForegroundColor());
				attributeEditPart.getFigure().setForegroundColor(ColorConstants.red);	
			}
		}

		private void resetColorCoding() {
			if (presentBorderFigures != null) {
				for (BorderedNodeFigure fig : presentBorderFigures) 
					fig.getParent().remove(fig);
				for (EdgeEditPart part : edgeColorCache.keySet())
					part.getFigure().setForegroundColor(edgeColorCache.get(part));
				for (AttributeEditPart part : attributeColorCache.keySet())
					part.getFigure().setForegroundColor(attributeColorCache.get(part));
				presentBorderFigures.clear();
				edgeColorCache.clear();
				attributeColorCache.clear();
			} else  {
				presentBorderFigures = new ArrayList<BorderedNodeFigure>();
				edgeColorCache = new HashMap<EdgeEditPart, Color>();
				attributeColorCache = new HashMap<AttributeEditPart, Color>();
			}
		}

		private void recolorEdge(NodeEditPart nodeEditPart, Edge e, boolean fromOrigin) {
			Edge actionEdge = e.getActionEdge();
			List<?> connectionSet = fromOrigin ? nodeEditPart.getSourceConnections() : nodeEditPart.getTargetConnections() ;
			for (Object connection : connectionSet) {
				if (connection instanceof EdgeEditPart) {
					EdgeEditPart edgePart = (EdgeEditPart) connection;
					EObject el = ((ConnectorImpl) edgePart.getModel()).getElement();
					if (el == actionEdge && edgePart.getFigure().getForegroundColor() != ColorConstants.red) {
						edgeColorCache.put(edgePart, edgePart.getFigure().getForegroundColor());
						edgePart.getFigure().setForegroundColor(ColorConstants.red);
					}
				}
			}
		}

		private void addBorderNode(GraphicalEditPart editPart) {
			BorderedNodeFigure borderNodeSource = new BorderedNodeFigure(new Label());
			borderNodeSource.setBorder(new LineBorder(ColorConstants.red, 2));
			editPart.getFigure().add(borderNodeSource);
			presentBorderFigures.add(borderNodeSource);
		}

		private RuleEditPart findRuleEditPart(
				ModuleEditPart selectedModuleEditPart, Rule rule) {
			for (Object part : selectedModuleEditPart.getChildren()) {
				if (part instanceof RuleEditPart)
					if (((ShapeImpl) ((RuleEditPart) part).getModel()).getElement() == rule)
						return (RuleEditPart) part;

		}
		return null;
		}
	}
	
		 

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"CloneGroupView", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void setContents(CloneGroupDetectionResult result) {
		cloneDetectionResult = result;
		viewer.setInput(result.getCloneGroups());
		createClusteredRulesAction(); // This action takes the clone detection result as its input
	}

	public void setContextDiagram(ModuleEditPart selectedModuleEditPart) {
		this.selectedModuleEditPart = selectedModuleEditPart;
	}

	public void setEditingDomain(TransactionalEditingDomain editingDomain) {
		this.editingDomain = editingDomain;		
	}

}