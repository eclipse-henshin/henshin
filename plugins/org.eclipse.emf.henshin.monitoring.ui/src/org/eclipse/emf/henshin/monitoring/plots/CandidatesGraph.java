package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

public class CandidatesGraph extends JPanel {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> nodeNames;
	private long[][] dataMap;

	private long largestDataValue = 0;
	private long smallestDataValue = 0;

	private int[][] dataColorIndices;
	private Color[] colors;
	private Font useFont;
	
	private int maxSize=0;
	private int whiteRecSize=0;
	private int largestLabelSize = 0;
	private int borderTop =0;
	private int borderLeft = 30;
	private int yTickDis=8;
	private int scaleDis=20;
	private int scaleWidth=10;
	private int scaleLabelDis=5;

	public CandidatesGraph(long[][] dataMap, LinkedList<String> nodeNames) {
		super();
		
		this.nodeNames = nodeNames;
		this.dataMap = dataMap;
		HeatColor heat=new HeatColor();
		this.colors = heat.createHeatScaleColors();
		this.useFont=new Font("Helvetica",Font.BOLD,16);

		updateDataColors();
		
		this.setPreferredSize(new Dimension(calculateDimensionWidth(),calculateDimensionHeight()));
		this.setDoubleBuffered(true);
		this.setOpaque(true);
	}

	private int calculateDimensionHeight() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		
		if(this.maxSize==0) {
			this.maxSize = g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue)) + 10;

			if(this.maxSize<34){
				this.maxSize=34;
			}
			this.whiteRecSize=this.maxSize + 4;
		}
		
		for (String name : this.nodeNames) {
			if (this.largestLabelSize < g2d.getFontMetrics().stringWidth(name)) {
				this.largestLabelSize = g2d.getFontMetrics().stringWidth(name);
			}
		}

		int labelDis=this.largestLabelSize;
		if(this.largestLabelSize<(10+2*g2d.getFontMetrics().getHeight())) {
			labelDis=10+2*g2d.getFontMetrics().getHeight();
		}
		this.borderTop = labelDis+ g2d.getFontMetrics().getHeight() + 20;
		
		
		return this.nodeNames.size()*(this.whiteRecSize)+this.borderTop+10;
	}

	
	private int calculateDimensionWidth() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		
		if(this.maxSize==0) {
			this.maxSize = g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue)) + 10;

			if(this.maxSize<34){
				this.maxSize=34;
			}
			this.whiteRecSize=this.maxSize + 4;
		}
		int scaleLabel=g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue));
		if(scaleLabel<g2d.getFontMetrics().stringWidth("Average possible")){
			scaleLabel=g2d.getFontMetrics().stringWidth("Average possible");
		}
		
		return this.borderLeft+this.nodeNames.size()*(this.whiteRecSize)+g2d.getFontMetrics().stringWidth("row "+this.nodeNames.size())+this.yTickDis+this.scaleDis+scaleWidth+this.scaleLabelDis+scaleLabel+10;
	}

	/**
	 * Calculates and assigns colors to the data values
	 */
	private void updateDataColors() {
		for (int x = 0; x < this.dataMap.length; x++) {
			for (int y = 0; y < this.dataMap[0].length; y++) {
				this.largestDataValue = Math.max(this.dataMap[x][y], this.largestDataValue);
			}
		}
		double range = this.largestDataValue - this.smallestDataValue;
		this.dataColorIndices = new int[this.dataMap.length][this.dataMap[0].length];
		for (int x = 0; x < this.dataMap.length; x++) {
			for (int y = 0; y < this.dataMap[0].length; y++) {
				double norm = (this.dataMap[x][y] - this.smallestDataValue) / range; 
				int colorIndex = (int) Math.floor(norm * (this.colors.length - 1));
				this.dataColorIndices[x][y] = colorIndex;
			}
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

		
		plotWidth = this.dataMap.length * (this.whiteRecSize);
		
		
		//Draw boxes
		int bottomY = 0;
		int endPlot=0;
		for (int x = 0; x < this.dataMap.length; x++) {
			for (int y = 0; y < this.dataMap[0].length; y++) {
				Color recColor = Color.WHITE;

				g2d.setColor(recColor);
				int xPos = this.borderLeft + (x *this.maxSize);
				int yPos = this.borderTop + (y * this.maxSize);

				g2d.fillRect(xPos, yPos,this.whiteRecSize,this.whiteRecSize);
				bottomY = yPos + this.whiteRecSize;
				if (this.dataMap[x][y] >= 0) {
					recColor =this.colors[this.dataColorIndices[x][y]];
				}

				g2d.setColor(recColor);
				xPos = xPos + 2;
				yPos = yPos + 2;
				g2d.fillRect(xPos, yPos, this.maxSize, this.maxSize);
				endPlot=xPos+this.maxSize;
				if (this.dataMap[x][y] >= 0) {
					String label = Long.toString(this.dataMap[x][y]);
					g2d.setColor(Color.BLACK);
					int labelX = xPos + ((this.maxSize / 2) - (g2d.getFontMetrics().stringWidth(label) / 2));
					int labelY = yPos + ((this.maxSize / 2) + 5);
					g2d.drawString(label, labelX, labelY);
				}
			}
		}

	
		// Y Axis
		g2d.setColor(Color.BLACK); 
		int xPosYAxisTick =endPlot;
		int yPosYAxisTick = this.borderTop + (int) (this.whiteRecSize / (double) 2);
		for (int spIdx = 0; spIdx < this.nodeNames.size(); spIdx++) {

			g2d.drawLine(xPosYAxisTick, yPosYAxisTick, xPosYAxisTick + 4, yPosYAxisTick);
			g2d.drawString("row " + spIdx, xPosYAxisTick + this.yTickDis, yPosYAxisTick + 5);
			yPosYAxisTick = yPosYAxisTick + this.maxSize;

		}

		
		// X Axis
		int xAxisNamePosX = this.borderLeft+(int) ((plotWidth / (double) 2) - (g2d.getFontMetrics().stringWidth("Search Plan") / (double) 2));
		int xAxisNamePosY = this.borderTop - this.largestLabelSize - g2d.getFontMetrics().getHeight() - 3; 
		g2d.drawString("Search Plan", xAxisNamePosX, xAxisNamePosY);
		int yPos = -1 * (int) (this.borderLeft + (this.whiteRecSize / (double) 2) - 2);

		for (int spIdx = 0; spIdx < this.nodeNames.size(); spIdx++) {
			g2d.drawLine((-1) * yPos + 2, this.borderTop, (-1) * yPos + 2, this.borderTop - 4);
			int xPos = this.borderTop - (g2d.getFontMetrics().stringWidth(this.nodeNames.get(spIdx)) + 7);
			g2d.rotate(Math.PI / 2);
			g2d.drawString(this.nodeNames.get(spIdx), xPos, yPos);
			g2d.rotate(-Math.PI / 2);
			yPos = yPos - (this.maxSize);

		}

		// Legend
		int legendxPos = (int) (this.borderLeft / (double) 2) + plotWidth+ g2d.getFontMetrics().stringWidth("row " + this.nodeNames.size()) + this.scaleDis;
		int legendyPos = this.borderTop;
		int scaleSize = bottomY - legendyPos;

		g2d.drawString("Average possible", legendxPos, legendyPos - 2 * g2d.getFontMetrics().getHeight());
		g2d.drawString("instances", legendxPos, legendyPos - 15);
		g2d.drawRect(legendxPos, legendyPos,this.scaleWidth, scaleSize);
		
		for (int y = 0; y < scaleSize - 1; y++) {
			int yStart = (legendyPos + scaleSize - 1) - y;
			int colorIdx = (int) ((y / (double) (scaleSize)) * this.colors.length);
			if (colorIdx >= 500) {
				colorIdx = this.colors.length - 1;
			}
			g2d.setColor(this.colors[colorIdx]); 
			g2d.fillRect(legendxPos, yStart, 9, 1);
		}

		g2d.setColor(Color.BLACK);
		
		int scaleLableX = legendxPos + this.scaleWidth+this.scaleLabelDis;
		g2d.drawString("0", scaleLableX, legendyPos + scaleSize + (int) (g2d.getFontMetrics().getHeight() * 0.4));
		if(this.largestDataValue<=10){
			int scaleLableY = legendyPos + (int) (g2d.getFontMetrics().getHeight() * 0.5);
			g2d.drawString(Long.toString(this.largestDataValue), scaleLableX, scaleLableY);
		}else{
			double delta = this.largestDataValue / ((double) 5);
			for (int i = 1; i <= 5; i++) {
				int label = (int) (i * delta);
				int scaleLableY = 0;
				if (i < 5) {
					scaleLableY = legendyPos + scaleSize - (int) (i * (scaleSize / (double) 5));
				} else {
					scaleLableY = legendyPos + scaleSize - (int) (i * (scaleSize / (double) 5))
							+ (int) (g2d.getFontMetrics().getHeight() * 0.5);
				}
				g2d.drawString(Integer.toString(label), scaleLableX, scaleLableY);
			}
		}


	}

}
