package anpr;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ij.ImagePlus;
import ij.process.ImageProcessor;
public class Core {
	private String url;
	public BufferedImage runCore(String link) throws IOException {
		//Read Image img-->Original  img2-->Processed image
		url = link;
		BufferedImage img = readImage(url);
		//Weighted Grayscale conversion
		GrayscaleConversion g = new GrayscaleConversion(img);
		BufferedImage img2 = g.RGB2GRAYSCALE();
		//Image Enhancement --> Histogram Equalization
		ImageEnhancement ie = new ImageEnhancement();
		ie.HistogramEqualize(img2);
		//Canny Edge Detection
		CannyEdgeDetection ced = new CannyEdgeDetection();
		ImagePlus ip = new ImagePlus("Image",img2);
		ImagePlus ip1 = ced.process(ip);
		ImageProcessor ipr = ip1.getProcessor();
		//Morphological Operation
		Morphology mph = new Morphology();
		ipr = mph.closing(ipr);
		ipr = mph.dilate(ipr);
		ipr = mph.closing(ipr);
		ipr = mph.FillHole(ipr);
		ipr = mph.erode(ipr);
		ipr = mph.opening(ipr);
		//CCA --> Select NP
		 SelectPlate sp = new SelectPlate();
		 int bbox[] = sp.numberPlate(ipr);
		 //Draw NP Bounding Box
		 Graphics2D g2d = img.createGraphics();
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(3));
			int x = bbox[0];
			int y = bbox[1];
			int width = bbox[2];
			int height = bbox[3];
			g2d.drawRect(x, y, width, height);
			g2d.dispose();
		return img;
	}
	
	
	
	public BufferedImage readImage(String url) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(url));
					
		}
		catch(IOException e) {
			
		}
		return img;
	}
	
}
