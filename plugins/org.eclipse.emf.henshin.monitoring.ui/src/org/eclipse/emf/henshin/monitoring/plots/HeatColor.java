package org.eclipse.emf.henshin.monitoring.plots;

import java.awt.Color;

public class HeatColor {
	
    public Color[] createHeatScaleColors(){
    	
    	Color[] scaleColors=new Color[4];
    	scaleColors[0]=Color.GREEN;
    	scaleColors[1]=Color.YELLOW;
    	scaleColors[2]=Color.ORANGE;
    	scaleColors[3]=Color.RED;
    	int colorSteps=500;
        int colorParts = scaleColors.length-1;
        int gradientIndex = 0; 
        Color[] gradient = new Color[colorSteps];
        
        for(int section = 0; section < colorParts; section++){
        	Color[] helpColors = createColors(scaleColors[section], scaleColors[section+1], (colorSteps/colorParts));
            for(int i = 0; i < helpColors.length; i++){
                gradient[gradientIndex] = helpColors[i];
                gradientIndex++;
            }
        }
        
        while(gradientIndex<colorSteps){
        	gradient[gradientIndex] = scaleColors[scaleColors.length-1];
        	gradientIndex++;
        }

        return gradient;
    }
    
    public static Color[] createColors(Color firstColor, Color secondColor, int colorSteps){
        Color[] colors = new Color[colorSteps];
        
        for(int i=0;i<colorSteps;i++){
            double norm = i/(double)colorSteps;
            int newRed=(int)(firstColor.getRed()+norm*(secondColor.getRed()-firstColor.getRed()));
            int newGreen=(int)(firstColor.getGreen()+norm*(secondColor.getGreen()-firstColor.getGreen()));
            int newBlue=(int)(firstColor.getBlue()+norm*(secondColor.getBlue()-firstColor.getBlue()));
            int newAlpha=(int)(firstColor.getAlpha()+norm*(secondColor.getAlpha()-firstColor.getAlpha()));
            colors[i] = new Color(newRed,newGreen,newBlue,newAlpha);
        }

        return colors;
    }

}
