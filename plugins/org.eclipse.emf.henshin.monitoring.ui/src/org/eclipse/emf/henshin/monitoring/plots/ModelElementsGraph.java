package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

public class ModelElementsGraph extends JPanel {

	private static final long serialVersionUID = 1L;

	private LinkedList<String> nodeNames;
	private LinkedList<Long> investigated;
	private LinkedList<Long> domainSize;
	private LinkedList<Double> values;

	private int[] dataColorIndices;
	private Color[] colors;
	private Font useFont;
	
	private String legendTitle="%-Scale";
	private int xDistScale=20;
	private int scaleWidth=10;
	private int maxHeight=0; 
	private int borderTop=0;
	private int whiteRecSize=0;
	private int maxSize=0;
	private int largestAnnotation = 0;
	private int largestLabelSize = 0;
	private int borderLeft =0;
	private ArrayList<String> bottomAnnotation;

	public ModelElementsGraph(LinkedList<Long> investigated, LinkedList<Long> domainSize, LinkedList<String> nodeNames) {
		super();
		this.values = new LinkedList<Double>();
		this.nodeNames = nodeNames;
		this.investigated = investigated;
		this.domainSize = domainSize;
		HeatColor heat=new HeatColor();
		this.colors = heat.createHeatScaleColors();
		this.useFont=new Font("Helvetica",Font.BOLD,16);
		updateDataColors();
		this.bottomAnnotation = new ArrayList<String>();
		
		this.setPreferredSize(new Dimension(calculateDimensionWidth(),calculateDimensionHeight()));
		this.setDoubleBuffered(true);
		this.setOpaque(true);
	}


	private int calculateDimensionHeight() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		this.maxHeight=2 * g2d.getFontMetrics().getHeight() + 10;
		this.borderTop=30 + g2d.getFontMetrics().getHeight();
		return this.nodeNames.size()*(this.maxHeight)+this.borderTop+ g2d.getFontMetrics().getHeight()+10;
	}


	private int calculateDimensionWidth() {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d =image.createGraphics();
		g2d.setFont(this.useFont);
		
		for (String name : this.nodeNames) {
			if (this.largestLabelSize < g2d.getFontMetrics().stringWidth(name)) {
				this.largestLabelSize = g2d.getFontMetrics().stringWidth(name);
			}
		}

		for (int idx = 0; idx < this.investigated.size(); idx++) {
			this.bottomAnnotation.add(this.investigated.get(idx) + " / " + this.domainSize.get(idx));
			if ((g2d.getFontMetrics().stringWidth(this.bottomAnnotation.get(idx)) > g2d.getFontMetrics().stringWidth((this.values.get(idx) + "%")))
					&& (g2d.getFontMetrics().stringWidth(this.bottomAnnotation.get(idx)) > this.largestAnnotation)) {
				this.largestAnnotation = g2d.getFontMetrics().stringWidth(this.bottomAnnotation.get(idx));
			} else if (g2d.getFontMetrics().stringWidth((this.values.get(idx) + "%")) > this.largestAnnotation) {
				this.largestAnnotation = g2d.getFontMetrics().stringWidth((this.values.get(idx) + "%"));
			}
		}
		
		this.maxSize = this.largestAnnotation + 10;
		this.whiteRecSize=this.maxSize+4;
		this.borderLeft = this.largestLabelSize + 15;
		

		return this.borderLeft+this.whiteRecSize+g2d.getFontMetrics().stringWidth(this.legendTitle)+this.xDistScale+this.scaleWidth+10;
	}


	/**
	 * Calculates and assigns colors to the data values
	 */
	private void updateDataColors() {
		double range = 100;
		this.dataColorIndices = new int[this.nodeNames.size()];
		for (int idx = 0; idx < this.nodeNames.size(); idx++) {
			double percentage=((double) 100 / (double) this.domainSize.get(idx)) * this.investigated.get(idx);
			double norm = percentage / range;
			if (norm > 1) {
				norm = 1;
			}
			this.dataColorIndices[idx] = (int) Math.floor(norm * (this.colors.length - 1));
			percentage=Math.round(percentage*10)/10.0;
			this.values.add(percentage);
		}

	}

	/**
	 * Draw Tried Instances Plot
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
			
		g2d.setFont(this.useFont);
		
		int bottomY = 0;
		
		// Draw boxes
		for (int y = 0; y < this.nodeNames.size(); y++) {
			Color recColor = Color.WHITE;

			g2d.setColor(recColor);
			int xPos = this.borderLeft;
			int yPos = this.borderTop + (y * this.maxHeight);

			g2d.fillRect(xPos, yPos, this.whiteRecSize, (this.maxHeight + 2));
			bottomY = yPos + (this.maxHeight + 2);

			recColor = this.colors[this.dataColorIndices[y]];

			g2d.setColor(recColor);
			xPos = xPos + 2;
			yPos = yPos + 2;
			g2d.fillRect(xPos, yPos, this.maxSize, this.maxHeight);

			String labelTop = Double.toString(this.values.get(y)) + "%";
			String labelBottom = this.bottomAnnotation.get(y);
			g2d.setColor(Color.BLACK);
			int labelX = xPos + ((this.maxSize / 2) - (g2d.getFontMetrics().stringWidth(labelTop) / 2));
			int labelY = yPos + ((this.maxHeight / 2)) - 5; 
			g2d.drawString(labelTop, labelX, labelY);
			labelX = xPos + ((this.maxSize / 2) - (g2d.getFontMetrics().stringWidth(labelBottom) / 2));
			labelY = yPos + ((this.maxHeight / 2) + g2d.getFontMetrics().getHeight() / 2) + 5;
			g2d.drawString(labelBottom, labelX, labelY);
		}


		g2d.setColor(Color.BLACK);
	
		// Y Axis
		int yPosYAxisTick = this.borderTop + (int) ((this.maxHeight + 2) / (double) 2);
		for (int spIdx = 0; spIdx < this.nodeNames.size(); spIdx++) {
			int xPosYAxisTick = this.borderLeft - g2d.getFontMetrics().stringWidth(this.nodeNames.get(spIdx)) - 4;
			g2d.drawString(this.nodeNames.get(spIdx), xPosYAxisTick, yPosYAxisTick + 5);
			yPosYAxisTick = yPosYAxisTick + this.maxHeight;
		}

		// Legend
		int legendxPos = this.borderLeft +this.whiteRecSize + this.xDistScale;
		int legendyPos = this.borderTop;
		int scaleSize = bottomY - legendyPos;
		g2d.drawString(this.legendTitle, legendxPos, legendyPos - 15);

		g2d.drawRect(legendxPos, legendyPos, this.scaleWidth, scaleSize);
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
		int scaleLableX = legendxPos + 15;
		g2d.drawString("0", scaleLableX, legendyPos + scaleSize + (int) (g2d.getFontMetrics().getHeight() * 0.4));		
		double delta = 100 / ((double) 5);
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
