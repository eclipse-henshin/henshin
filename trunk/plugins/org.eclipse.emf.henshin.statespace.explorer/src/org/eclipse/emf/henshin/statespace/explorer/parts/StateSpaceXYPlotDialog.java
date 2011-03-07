package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceXYPlot;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog for displaying plots.
 * @author Christian Krause
 */
public class StateSpaceXYPlotDialog extends Dialog implements ControlListener {
	
	// The plot to be displayed:
	private StateSpaceXYPlot plot;
	
	// The dots:
	private Dot[][] dots;

	// The lines between the dots:
	private Line[][] lines;

	// Bars:
	private Bar xBar,yBar;
	
	// The panel:
	private Panel panel;
	
	// Figure canvas:
	private FigureCanvas canvas;
		
	/**
	 * Default constructor.
	 * @param shell The shell.
	 * @param plot The plot.
	 */
	public StateSpaceXYPlotDialog(Shell shell, StateSpaceXYPlot plot){
		super(shell);
		this.plot = plot;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
    protected void configureShell(Shell shell) {
		
        super.configureShell(shell);
        shell.setText("Plot");
        shell.setSize(400, 300);
        
        // Center the dialog on the screen:
    	Monitor primary = shell.getDisplay().getPrimaryMonitor ();
    	org.eclipse.swt.graphics.Rectangle bounds = primary.getBounds();
    	org.eclipse.swt.graphics.Rectangle rect = shell.getBounds();
    	int x = bounds.x + (bounds.width - rect.width) / 2;
    	int y = bounds.y + (bounds.height - rect.height) / 2;
    	shell.setLocation(x,y);
    	
    }

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		// Create container composite:
		Composite main = (Composite) super.createDialogArea(parent);
		Composite composite = new Composite(main, SWT.BORDER);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new FillLayout());

		// Create the panel:
		panel = new Panel();
		panel.setLayoutManager(new XYLayout());

		// Draw the figures:
		drawFigures(panel);

		// Create the canvas:
		canvas = new FigureCanvas(composite);
		canvas.setScrollBarVisibility(FigureCanvas.NEVER);
		canvas.setBackground(ColorConstants.white);
		canvas.setContents(panel);
		canvas.addControlListener(this);

		return composite;

	}
	
	/*
	 * Draw all figures.
	 */
	private void drawFigures(IFigure parent) {
	    
		// Create the bars...
		int xSegs = Math.max(2,Math.min(plot.getXMaxSegments(),10));
		int ySegs = Math.max(2,Math.min(plot.getYMaxSegments(),10));		
		xBar = new Bar(parent, plot.getXMax(), xSegs, false);
		yBar = new Bar(parent, plot.getYMax(), ySegs, true);
		
		// Create the dots...
		dots = new Dot[plot.getSeriesCount()][];
		for (int i=0; i<dots.length; i++) {
			dots[i] = new Dot[plot.getSeriesLength(i)];
			for(int j=0; j<dots[i].length; j++) {
				dots[i][j] = new Dot(parent,getPlotColor(i));
			}
		}
		
		// Create the lines...
		lines = new Line[dots.length][];
		for(int i=0; i<lines.length; i++){
			lines[i] = new Line[dots[i].length-1];
			for (int j=0; j<lines[i].length; j++) {
				Dot left = dots[i][j];
				Dot right = dots[i][j+1];
				lines[i][j] = new Line(parent,left,right,getPlotColor(i));
			}
		}
		
	}
	
	
	private void updatePositions() {
				
		// Offsets:
		int top = 35;
		int left = 45;
		int right = 40;
		int bottom = 30;
		
		// Get the new size:
		Viewport port = canvas.getViewport();
		int width = port.getHorizontalRangeModel().getExtent() - left - right;
		int height = port.getVerticalRangeModel().getExtent() - bottom - top;
		
		// Update the bar positions:
		yBar.setStart(	new Point(left, top+height)			);
		yBar.setEnd(	new Point(left, top)				);
		xBar.setStart(	new Point(left, top+height)			);
		xBar.setEnd(	new Point(left+width, top+height)	);		
		
		// Update node positions:
		for (int i=0; i<dots.length; i++){
			for(int j=0; j<dots[i].length; j++) {
				int x = plot.getXScaled(i,j,width) + left;
				int y = height - plot.getYScaled(i,j,height) + top;
				dots[i][j].setPosition(x,y);
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Close", true);
	}

	/*
	 * Get a color for a plot.
	 */
    private static Color getPlotColor(int index) {
        switch (index % 4){
	        case 0:  return ColorConstants.red;
	        case 1:  return ColorConstants.cyan;
	        case 2:  return ColorConstants.green;
	        case 3:  return ColorConstants.blue;
	        default: return ColorConstants.gray;
        }
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	@Override
	public void controlResized(ControlEvent e) {
		updatePositions();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	@Override
	public void controlMoved(ControlEvent e) {
		// Not relevant.
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	
		
	/**
	 * Figure for the dots in plots.
	 */
	class Dot extends Ellipse {
		
		Dot(IFigure parent, Color color) {
			setSize(new Dimension(5,5));
			setForegroundColor(ColorConstants.gray);
			setBackgroundColor(color);
			setOpaque(true);
			parent.add(this);
		}
		
		void setPosition(int x, int y) {
			getParent().setConstraint(this, new Rectangle(x-2,y-2,-1,-1));
		}
		
	}

	/**
	 * Figure for edges in plots.
	 */
	class Line extends PolylineConnection {
		
		Line(IFigure parent, Dot source, Dot target, Color color) {
		    setSourceAnchor(new CenterAnchor(source));
		    setTargetAnchor(new CenterAnchor(target));
		   	setAntialias(SWT.ON);
		    setForegroundColor(color);
			parent.add(this);
		}
		
	}

	class CenterAnchor extends ChopboxAnchor {

		CenterAnchor(IFigure owner) {
			super(owner);
		}
		
		@Override
		protected Rectangle getBox() {
			Rectangle bounds = getOwner().getBounds().getCopy();
			bounds.x = bounds.x + (bounds.width/2);
			bounds.y = bounds.y + (bounds.width/2);
			bounds.width = 0;
			bounds.height = 0;
			return bounds;
		}
	}

	/**
	 * Figure for the bars.
	 */
	class Bar extends Polyline {
		
		final static int WIDTH = 3;
		final static int ARROW = 20;
		
		double maximum;
		int segments;
		boolean vertical;
		
		Bar(IFigure parent, double maximum, int segments, boolean vertical) {
			this.vertical = vertical;
			this.maximum = maximum;
			this.segments = segments;
		   	setAntialias(SWT.ON);
		    setForegroundColor(ColorConstants.black);
			parent.add(this);
		}
		
		@Override
		public void setEnd(Point end) {
			if (vertical) {
				super.setEnd(end.getTranslated(0,-ARROW));
			} else {
				super.setEnd(end.getTranslated(ARROW,0));				
			}
		}
		
		@Override
		public Rectangle getBounds() {
			if (bounds == null) {
				if (vertical) {
					bounds = getPoints().getBounds().getExpanded(40+WIDTH,10);
				} else {
					bounds = getPoints().getBounds().getExpanded(10,WIDTH+20);	
				}
			}
			return bounds;
		}
		
		@Override
		protected void outlineShape(Graphics g) {
			super.outlineShape(g);
			
			// Draw the markers and the labels:
			int length = (vertical ? getStart().y - getEnd().y : getEnd().x - getStart().x) - ARROW;
			for (int i=0; i<segments; i++) {
				int position = i * length / (segments-1);
				double value = i * maximum / (segments-1);
				
				// The marker:
				PointList marker = new PointList();
				if (vertical) {
					marker.addPoint(getStart().x - WIDTH, getStart().y - position);
					marker.addPoint(getStart().x + WIDTH, getStart().y - position);
				} else {
					marker.addPoint(getStart().x + position, getStart().y - WIDTH);
					marker.addPoint(getStart().x + position, getStart().y + WIDTH);
				}
				g.drawPolyline(marker);
				
				// The label:
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				format.setMinimumFractionDigits(2);
				format.setMaximumFractionDigits(2);
				String text = format.format(value);
				int h = g.getFontMetrics().getHeight();
				int w = text.length() * g.getFontMetrics().getAverageCharWidth();
				if (vertical) {
					g.drawString(text, getStart().x - w - h/4 - WIDTH, getStart().y - position - h/2);					
				} else {
					g.drawString(text, getStart().x + position - h/4, getStart().y + WIDTH + h/4);
				}
				
			}
			
			// Draw the arrow tip:
			PointList arrow = new PointList();
			if (vertical) {
				arrow.addPoint(getEnd().x-WIDTH, getEnd().y+WIDTH);
				arrow.addPoint(getEnd().x, getEnd().y);
				arrow.addPoint(getEnd().x+WIDTH, getEnd().y+WIDTH);
			} else {
				arrow.addPoint(getEnd().x-WIDTH, getEnd().y-WIDTH);
				arrow.addPoint(getEnd().x, getEnd().y);
				arrow.addPoint(getEnd().x-WIDTH, getEnd().y+WIDTH);				
			}
			g.drawPolyline(arrow);
			
		}

	}

}
