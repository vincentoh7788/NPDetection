package anpr;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import ij.ImagePlus;
import ij.plugin.ContrastEnhancer;
import ij.process.ImageProcessor;

public class ImageEnhancement {

	public void HistogramEqualize(BufferedImage b) {
		ContrastEnhancer ce = new ContrastEnhancer();
		ImagePlus ip = new ImagePlus("Image",b);
		ImageProcessor ipr = ip.getProcessor();
		ce.equalize(ipr);
		
	
		
	}
	
}