package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.eclipse.emf.henshin.monitoring.ui.util.UnitVisualizationData;

public class CallTreeScale extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Color[] colors;
	private Font useFont;
	private int borderLeft=10;
	private int borderTop=10;
	private int scaleSize=360;
	private int scaleWidth=10;
	private long largestDataValue;

	public CallTreeScale(LinkedList<UnitVisualizationData> unitVisualizationData) {
		super();
		
		HeatColor heat=new HeatColor();
		this.colors = heat.createHeatScaleColors();
		this.useFont=new Font("Helvetica",Font.BOLD,16);
		
		this.largestDataValue=executionDuration(unitVisualizationData);
		
		this.setPreferredSize(new Dimension(calculateDimensionWidth(),calculateDimensionHeight()));
		this.setDoubleBuffered(true);
		this.setOpaque(true);
	}

	private long executionDuration(LinkedList<UnitVisualizationData> unitVisualizationData) {
		long time=0;
		for(UnitVisualizationData unit:unitVisualizationData) {
			time=time+unit.getDuration();
		}
		return time;
	}

	private int calculateDimensionHeight() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);

		return this.borderTop+this.scaleSize+2*g2d.getFontMetrics().getHeight()+10;
	}

	private int calculateDimensionWidth() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		
		
		return this.borderLeft+this.scaleWidth+g2d.getFontMetrics().stringWidth(Long.toString(this.largestDataValue)+" ms");
	}
	

	/**
	 * Draw heat scale for the call tree
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
			
		g2d.setFont(this.useFont);

		g2d.setColor(Color.BLACK);

		// Legend
		int legendxPos = this.borderLeft;
		int legendyPos = this.borderTop+g2d.getFontMetrics().getHeight()+2;
		
		g2d.drawString("Execution time", legendxPos, legendyPos-4);

		g2d.drawRect(legendxPos, legendyPos, this.scaleWidth, this.scaleSize);
		for (int y = 0; y < this.scaleSize - 1; y++) {
			int yStart = (legendyPos + this.scaleSize - 1) - y;

			int colorIdx = (int) ((y / (double) (this.scaleSize)) * this.colors.length);
			if (colorIdx >= 500) {
				colorIdx = this.colors.length - 1;
			}
			g2d.setColor(this.colors[colorIdx]); 
			g2d.fillRect(legendxPos, yStart, 9, 1);
		}

		g2d.setColor(Color.BLACK);
		int scaleLableX = legendxPos + 15;
		g2d.drawString("0 ms", scaleLableX, legendyPos + this.scaleSize + (int) (g2d.getFontMetrics().getHeight() * 0.4));
		if(this.largestDataValue<=10){
			int scaleLableY = legendyPos + (int) (g2d.getFontMetrics().getHeight() * 0.5);
			g2d.drawString(Long.toString(this.largestDataValue)+" ms", scaleLableX, scaleLableY);
		}else{
			double delta = this.largestDataValue / ((double) 5);
			for (int i = 1; i <= 5; i++) {
				int label = (int) (i * delta);
				int scaleLableY = 0;
				if (i < 5) {
					scaleLableY = legendyPos + this.scaleSize - (int) (i * (this.scaleSize / (double) 5));
				} else {
					scaleLableY = legendyPos + this.scaleSize - (int) (i * (this.scaleSize / (double) 5))
							+ (int) (g2d.getFontMetrics().getHeight() * 0.5);
				}
				g2d.drawString(Integer.toString(label)+" ms", scaleLableX, scaleLableY);
			}
		}

	}

}
