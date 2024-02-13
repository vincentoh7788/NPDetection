package anpr;

import ij.ImagePlus;
import ij.*;
import ij.gui.*;
import ij.process.*;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.Reconstruction;
//Morphology based on Disk Structure Element
public class Morphology {
	
	public ImageProcessor FillHole(ImageProcessor ip) {
		ip = Reconstruction.fillHoles(ip);
		
		return ip;
	}
	public ImageProcessor dilate(ImageProcessor ipr) {
	 Strel strel = Strel.Shape.DISK.fromRadius(1);
	 ImageProcessor dilated = strel.dilation(ipr);
	 return dilated;
	}
	public ImageProcessor erode(ImageProcessor ipr) {
		 Strel strel = Strel.Shape.DISK.fromRadius(1);
		 ImageProcessor eroded = strel.erosion(ipr);
		 return eroded;
		}
	public ImageProcessor opening(ImageProcessor ipr) {
		 Strel strel = Strel.Shape.DISK.fromRadius(1);
		 ImageProcessor opened = strel.opening(ipr);
		 return opened;
		}
		
	public ImageProcessor closing(ImageProcessor ipr) {
		 Strel strel = Strel.Shape.DISK.fromRadius(1);
		 ImageProcessor closed = strel.closing(ipr);
		 return closed;
		}
		
}
