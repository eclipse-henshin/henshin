package org.eclipse.emf.henshin.monitoring.ui;

import java.util.LinkedList;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.henshin.monitoring.plots.BacktrackingGraph;
import org.eclipse.emf.henshin.monitoring.plots.CallTree;
import org.eclipse.emf.henshin.monitoring.plots.CallTreeScale;
import org.eclipse.emf.henshin.monitoring.plots.CandidatesGraph;
import org.eclipse.emf.henshin.monitoring.plots.ModelElementsGraph;
import org.eclipse.emf.henshin.monitoring.ui.util.RuleVisualizationData;
import org.eclipse.emf.henshin.monitoring.ui.util.UnitVisualizationData;



public class HenshinPerformanceView {
	
	
	public static void showHenshinPerformanceView(LinkedList<RuleVisualizationData> ruleVisualizationData, LinkedList<UnitVisualizationData> unitVisualizationData) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("Henshin Profiler");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				String[] transformationTabs = new String[ruleVisualizationData.size()+1];
				transformationTabs[0]="Call Graph";
				int idx=1;
				for(RuleVisualizationData rule:ruleVisualizationData){
					transformationTabs[idx]=idx+". "+rule.getRuleName();	
					idx++;
				}
				
				JTabbedPane tabs = new JTabbedPane();
				
				for (int i = 0; i <transformationTabs.length; i++){
					JPanel grid=null;
					
					if(i==0){
						grid=new JPanel(new GridBagLayout());
						GridBagConstraints constraints = new GridBagConstraints();
						grid.setBackground(Color.WHITE);

						JLabel ctLabel=new JLabel("Call Graph",JLabel.CENTER);
						Font labelFont=new Font("Helvetica", Font.BOLD,(ctLabel.getFont().getSize()*2)); //"LucidaGrande"
						ctLabel.setFont(labelFont);
						constraints.fill = GridBagConstraints.HORIZONTAL;
						constraints.gridx = 0;
						constraints.gridy = 0;
						constraints.gridwidth=2;
					    grid.add(ctLabel, constraints);
							
						addBlameGraph(grid,unitVisualizationData,tabs);
						addBlameGraphScale(grid,unitVisualizationData);
					}else{
						RuleVisualizationData rule=ruleVisualizationData.get(i-1);
						
						if(!rule.getNodeNames().isEmpty()){
							grid=new JPanel(new GridBagLayout());
							GridBagConstraints constraints = new GridBagConstraints();
							constraints.fill = GridBagConstraints.HORIZONTAL;
							
							grid.setBackground(Color.WHITE);
							
							JLabel btLabel=new JLabel("Backtracking Graph",JLabel.CENTER);
							Font labelFont=new Font("Helvetica", Font.BOLD,(btLabel.getFont().getSize()*2)); //"LucidaGrande"
							btLabel.setFont(labelFont);
							constraints.fill = GridBagConstraints.HORIZONTAL;
							constraints.gridx = 0;
							constraints.gridy = 0;
						    grid.add(btLabel, constraints);
	
							
							addBacktrackingBarChart(grid,rule);
							
							
							
							JLabel checkLabel=new JLabel("Model Elements Graph",JLabel.CENTER);
							checkLabel.setFont(labelFont);
							constraints.fill = GridBagConstraints.HORIZONTAL;
							constraints.gridx = 1;
							constraints.gridy = 0;
						    grid.add(checkLabel, constraints);
						    
							addCheckedModelElements(grid,rule);
							
							
							JLabel mapLabel=new JLabel("Candidates Graph",JLabel.CENTER);
							mapLabel.setFont(labelFont);
							constraints.fill = GridBagConstraints.HORIZONTAL;
							constraints.gridx = 0;
							constraints.gridy = 2;
						    grid.add(mapLabel, constraints);
	
							addSetRestrictionMap(grid,rule);
						}else {
							grid=new JPanel(new GridBagLayout());
							GridBagConstraints constraints = new GridBagConstraints();
							constraints.fill = GridBagConstraints.HORIZONTAL;
							
							grid.setBackground(Color.WHITE);
							
							JLabel infoLabel=new JLabel("During the execution of "+rule.getRuleName()+" there was no matching process.",JLabel.CENTER);
							Font labelFont=new Font("Helvetica", Font.BOLD,(infoLabel.getFont().getSize()*2)); 
							infoLabel.setFont(labelFont);
							constraints.fill = GridBagConstraints.HORIZONTAL;
							constraints.gridx = 0;
							constraints.gridy = 0;
						    grid.add(infoLabel, constraints);
						}
						
					}
					
					JScrollPane scrollPane = new JScrollPane(grid);
					scrollPane.getVerticalScrollBar().setUnitIncrement(16);
					scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
					tabs.addTab(transformationTabs[i],scrollPane);
				}

				
				int selectedTab=tabs.getSelectedIndex();
				if(selectedTab==-1){
					selectedTab=0;
				}
				JLabel infoText = new JLabel("Tab selected: "+transformationTabs[selectedTab]);
				tabs.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						infoText.setText("Tab selected: "+transformationTabs[tabs.getSelectedIndex()]);
					}
				});
				infoText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),BorderFactory.createEmptyBorder(0,5,0,0)));
				
				Container container = frame.getContentPane();
				container.add(tabs);
				container.add(BorderLayout.SOUTH,infoText);
				
				frame.pack();
				frame.setVisible(true);
			}
			
		    private void addComp(JPanel grid, JPanel panel, int gridx, int gridy, int gridwidth, int gridheight, int fill, double weightx, double weighty) {
		    	GridBagConstraints constraints = new GridBagConstraints(); 
		    	constraints.gridx = gridx;
		    	constraints.gridy = gridy;
		    	constraints.gridwidth = gridwidth;
		    	constraints.gridheight = gridheight;
		    	constraints.fill = fill;
		    	constraints.weightx = weightx;
		    	constraints.weighty = weighty;
		    	grid.add(panel, constraints);
		    }



			private void addSetRestrictionMap(JPanel grid, RuleVisualizationData rule) {
				CandidatesGraph map=new CandidatesGraph(rule.getDataMap(),rule.getNodeNames());
				map.setBackground(Color.WHITE);
				JPanel panel=new JPanel(new BorderLayout());
				panel.setOpaque(true);
				panel.setBounds(0, 0, 400, 400);
				panel.add(map, BorderLayout.CENTER);
				addComp(grid, panel, 0, 3, 2, 2, GridBagConstraints.BOTH, 0.5,0.5);
			}

			private void addCheckedModelElements(JPanel grid, RuleVisualizationData rule) {
				JPanel panel=new JPanel(new BorderLayout());
				ModelElementsGraph triedPanel = new ModelElementsGraph(rule.getInvestigated(),rule.getDomainSize(),rule.getNodeNames());
				triedPanel.setBackground(Color.WHITE);
				panel.setOpaque(true);
				panel.add(triedPanel, BorderLayout.CENTER);
				addComp(grid, panel, 1, 1, 1, 1, GridBagConstraints.BOTH, 0.5,0.5);
			}

			private void addBacktrackingBarChart(JPanel grid, RuleVisualizationData rule) {	
		        BacktrackingGraph btPanel = new BacktrackingGraph(rule.getNumberOfBacktracking(),rule.getNodeNames());
				btPanel.setBackground(Color.WHITE);
				JPanel panel=new JPanel(new BorderLayout());
				panel.setOpaque(true);
				panel.add(btPanel, BorderLayout.CENTER);
				addComp(grid, panel, 0, 1, 1, 1, GridBagConstraints.BOTH,0.5,0.5);
			}

			private void addBlameGraph(JPanel grid, LinkedList<UnitVisualizationData> unitVisualizationData,JTabbedPane tabs) {
		        JPanel panel=new JPanel(new BorderLayout());
				panel.add(new CallTree(unitVisualizationData,tabs), BorderLayout.CENTER);
				addComp(grid, panel, 0, 1, 1, 1, GridBagConstraints.BOTH,0.5,0.5);
			}
			
			private void addBlameGraphScale(JPanel grid, LinkedList<UnitVisualizationData> unitVisualizationData){
				CallTreeScale ctScalePanel = new CallTreeScale(unitVisualizationData);
				ctScalePanel.setBackground(Color.WHITE);
				JPanel panel=new JPanel(new BorderLayout());
				panel.setOpaque(true);
				panel.add(ctScalePanel, BorderLayout.CENTER);
				addComp(grid, panel, 1, 1, 1, 1, GridBagConstraints.BOTH, 0.5,0.5);
				
			}

			
		});
	}
	
}
