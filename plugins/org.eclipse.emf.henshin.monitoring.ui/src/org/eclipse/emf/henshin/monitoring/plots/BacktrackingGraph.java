package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

public class BacktrackingGraph extends JPanel {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> nodeNames;
	private LinkedList<Long> numberOfBacktracking;
	private LinkedList<Integer> boxSizes;
	
	private Font useFont;
	

	private long largestDataValue = 0;
	private Color boxColor = new Color(3, 152, 252);

	private int maxBoxSize=360;
	private int boxWidth=0;
	private int plotDis=5;//Distance between the bars
	private int borderTop = 20;
	private int plotHeight=0;
	private int largestLabelSize=0;
	private int xAxisTitleDis=23;
	private int xAxisTick= 7;
	private int borderLeft = 0;
	

	public BacktrackingGraph(LinkedList<Long> numberOfBacktracking, LinkedList<String> nodeNames) {
		super();
		this.nodeNames = nodeNames;
		this.numberOfBacktracking=numberOfBacktracking;
		this.useFont=new Font("Helvetica",Font.BOLD,16);
		this.setBoxSizes();
		
		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(calculateDimensionWidth(),calculateDimensionHeight()));
		this.setOpaque(true);
	}

	private int calculateDimensionHeight() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		this.plotHeight=this.maxBoxSize+2*g2d.getFontMetrics().getHeight();
		
		for(String name: this.nodeNames){
			if(g2d.getFontMetrics().stringWidth(name)>this.largestLabelSize){
				this.largestLabelSize=g2d.getFontMetrics().stringWidth(name);
			}
		}
		
		return this.plotHeight+this.borderTop+this.largestLabelSize+this.xAxisTitleDis+this.xAxisTick+10;
	}

	private int calculateDimensionWidth() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);

		this.boxWidth=g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue))+4;
		if(this.boxWidth<(g2d.getFontMetrics().getHeight()+4)){
			this.boxWidth=g2d.getFontMetrics().getHeight()+4;
		}
		
		this.borderLeft = 30+g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue))+8+(g2d.getFontMetrics().getHeight()+4);
		
		return this.nodeNames.size()*(this.boxWidth+this.plotDis)+this.borderLeft+10;
	}

	/**
	 * Scale box sizes to fit maxBoxSize
	 */
	private void setBoxSizes() {
		this.boxSizes=new LinkedList<Integer>();
		for(long num:this.numberOfBacktracking){
			if(num>this.largestDataValue){
				this.largestDataValue=num;
			}
		}
		
		for(long num:this.numberOfBacktracking){
			double norm=(double)num/(double)this.largestDataValue;
			this.boxSizes.add((int)(Math.floor(norm *this.maxBoxSize)));
		}
	}




	/**
	 * Draw Backtracking Bar Chart
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setFont(this.useFont);


		int plotWidth = 0;
		
		
		//Draw Boxes
		for(int idx=0;idx<this.boxSizes.size();idx++){
			g2d.setColor(this.boxColor);
			int xPos=this.borderLeft+idx*this.boxWidth+idx*this.plotDis;
			int yPos=this.borderTop+this.plotHeight-this.boxSizes.get(idx);
			g2d.fillRect(xPos,yPos,this.boxWidth,this.boxSizes.get(idx));
			
			plotWidth=plotWidth+this.boxWidth+this.plotDis;
			g2d.setColor(Color.BLACK);
			String label=Long.toString(this.numberOfBacktracking.get(idx));
			
			g2d.drawString(label,(int)(xPos+(this.boxWidth/(double)2)-(g2d.getFontMetrics().stringWidth(label)/(double)2)),yPos-3);
		}
		
		
		
		// Y Axis
		g2d.drawLine(this.borderLeft-2,this.borderTop,this.borderLeft-2,this.borderTop+plotHeight);
		int xPosYAxisTick = this.borderLeft-2;
		int yPosYAxisTick = this.borderTop+this.plotHeight;
		double yDis=this.maxBoxSize/(double)5;
		double valueDis=this.largestDataValue/(double)5;
		g2d.setColor(Color.BLACK);
		for (int count = 1; count<=5 ;count++) {
			g2d.drawLine(xPosYAxisTick, yPosYAxisTick-(int)(count*yDis), xPosYAxisTick - 4, yPosYAxisTick-(int)(count*yDis));
			String label=Long.toString((long)(valueDis*count));
			g2d.drawString(label, xPosYAxisTick-g2d.getFontMetrics().stringWidth(label)- 8, yPosYAxisTick-(int)(count*yDis)+(int)(g2d.getFontMetrics().getHeight()/(double)2));
		}
		
		
		String yAxisName="Revoked Bindings";
		int yAxisNamePosX=this.borderTop+(this.maxBoxSize/2)-(g2d.getFontMetrics().stringWidth(yAxisName)/2);
		//-g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue))-4-5
		int yAxisNamePosY=(-1)*(this.borderLeft-g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue))-g2d.getFontMetrics().getHeight()-8-7); //-8 tick and -10 add some space
		g2d.rotate(Math.PI / 2);
		g2d.drawString(yAxisName, yAxisNamePosX, yAxisNamePosY);
		g2d.rotate(-Math.PI / 2);
		
		
		//X Axis
		g2d.drawLine(this.borderLeft-2,this.borderTop+this.plotHeight,this.borderLeft-2+plotWidth,this.borderTop+this.plotHeight);
		int xAxisNamePosX = this.borderLeft +(int) ((plotWidth / (double) 2) - (g2d.getFontMetrics().stringWidth("Search Plan") / (double) 2));
		int xAxisNamePosY = this.borderTop+this.plotHeight + this.largestLabelSize +this.xAxisTitleDis;
																							
		g2d.drawString("Search Plan", xAxisNamePosX, xAxisNamePosY);

		int yPos = -1 * (int) (this.borderLeft + ((this.boxWidth) / (double) 2) - 2); 

		for (int spIdx = 0; spIdx < this.nodeNames.size(); spIdx++) {
			g2d.drawLine((-1) * yPos + 2, this.borderTop+this.plotHeight, (-1) * yPos + 2, this.borderTop+this.plotHeight + 4);
			int xPos = this.borderTop+this.plotHeight + this.xAxisTick; 
			g2d.rotate(Math.PI / 2);
			g2d.drawString(this.nodeNames.get(spIdx), xPos, yPos+2);
			g2d.rotate(-Math.PI / 2);
			yPos = yPos - (this.boxWidth)-this.plotDis;

		}

	}

}
