package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.eclipse.emf.henshin.monitoring.ui.util.UnitTypes;
import org.eclipse.emf.henshin.monitoring.ui.util.UnitVisualizationData;

public class CallTree extends JTree {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<UnitVisualizationData> unitData;
	private DefaultMutableTreeNode root;
	

	public CallTree(LinkedList<UnitVisualizationData> unitData,JTabbedPane tabs){
		super();
		this.unitData=unitData;	
		createTree();
		this.setModel(new DefaultTreeModel(this.root,true));
		addMouseListener(this,tabs);
		this.setRowHeight(26);
	}
	
	 
	
	private void addMouseListener(JTree tree,JTabbedPane tabs) {
		MouseListener ml = new MouseAdapter() {
		     public void mousePressed(MouseEvent e) {
		         TreePath selRow = tree.getPathForLocation(e.getX(), e.getY());
		         if((e.getClickCount()==2)&&(selRow!=null)) {
		        	 if((selRow.getLastPathComponent() instanceof DefaultMutableTreeNode)&&(((DefaultMutableTreeNode)selRow.getLastPathComponent()).getUserObject()instanceof UnitVisualizationData)){
		            		 int idx= ((UnitVisualizationData)((DefaultMutableTreeNode)selRow.getLastPathComponent()).getUserObject()).getTabIdx();
					         if(idx>-1){
					        	 tabs.setSelectedIndex(idx);
					         }
		            	 }
		             }
		         
		     }
		 };
		 tree.addMouseListener(ml);
		
	}




	private void createTree() {
		long duration=0;
		long maxDuration=0;
		for(UnitVisualizationData unit:this.unitData){
			duration=duration+unit.getDuration();
			if(maxDuration<unit.getDuration()){
				maxDuration=unit.getDuration();
			}
		}
		String rootLabel="Total execution time: "+duration+" ms";
		this.root = new DefaultMutableTreeNode(new UnitVisualizationData(rootLabel,maxDuration));
		this.setCellRenderer(new UnitTreeCellRenderer());
		createTree(this.root,this.unitData);
	}



	private void createTree(DefaultMutableTreeNode root, LinkedList<UnitVisualizationData> calls) {
		for(UnitVisualizationData unit:calls){
			DefaultMutableTreeNode call=new DefaultMutableTreeNode(unit);
			if(!unit.getCalls().isEmpty()){
				createTree(call,unit.getCalls());
			}else{
				call.setAllowsChildren(false);
			}
			root.add(call);
		}
		
	}
	
    class UnitTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;
        private long maxTime;
        private Color[] colors;
        private Font useFont;

        

        UnitTreeCellRenderer() {
            this.label = new JLabel();
            this.useFont=new Font("Helvetica",Font.BOLD,16);
            this.label.setFont(useFont);
            this.maxTime=0;
            HeatColor heat=new HeatColor();
            this.colors = heat.createHeatScaleColors();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        	Object obj = ((DefaultMutableTreeNode) value).getUserObject();		
        	UnitVisualizationData unit=null;
        	if(obj instanceof UnitVisualizationData){
        		unit=(UnitVisualizationData) obj;
        	}
        	
        	if(unit!=null){
        		if(unit.getType()==UnitTypes.NONE){
        			this.label.setIcon(new ImageIcon(getClass().getResource("/icons/Unit.png")));
        			this.label.setText(unit.getLabel());
        			this.maxTime=unit.getDuration();
        			this.label.setBackground(null);
        			this.label.setOpaque(false);
        		}else{
        			URL iconUrl=null;
        			switch(unit.getType()){
        				case RULE: {
        					iconUrl=getClass().getResource("/icons/Rule.gif");
        					break;}
        				case LOOP_UNIT: {
        					iconUrl=getClass().getResource("/icons/LoopUnit.gif");
        					break;}
        				case ITERATED_UNIT: {
        					iconUrl=getClass().getResource("/icons/IteratedUnit.gif");
        					break;}
        				case CONDITIONAL_UNIT: {
        					iconUrl=getClass().getResource("/icons/ConditionalUnit.gif");
        					break;}
        				case SEQUENTIAL_UNIT: {
        					iconUrl=getClass().getResource("/icons/SequentialUnit.gif");
        					break;}
        				case PRIORITY_UNIT: {
        					iconUrl=getClass().getResource("/icons/PriorityUnit.gif");
        					break;}
        				case INDEPENDENT_UNIT: {
        					iconUrl=getClass().getResource("/icons/IndependentUnit.gif");
        					break;}
					default:
						break;
        			}
        			this.label.setIcon(new ImageIcon(iconUrl));
        			this.label.setText(unit.getLabel()+": "+unit.getDuration()+" ms");
        			this.label.setBackground(getColor(unit.getDuration()));
        			this.label.setOpaque(true);
        		}
        	}
        	
            return this.label;
        }

		private Color getColor(long duration) {
			double norm = (duration) / (double) this.maxTime; 
			int colorIndex = (int) Math.floor(norm * (this.colors.length - 1));
			return this.colors[colorIndex];
		}
    }

}
