package anpr;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import inra.ijpb.binary.*;
import ij.measure.ResultsTable;
import inra.ijpb.measure.region2d.*;
import inra.ijpb.measure.region2d.MorphometricFeatures2D.Feature;
import java.util.*;
public class SelectPlate {
	
	public int [] numberPlate(ImageProcessor ip) {
		//8-neighbourhood CCA
		ImageProcessor ip1 = BinaryImages.componentsLabeling(ip, 8, 8);
		ImagePlus ip2 = new ImagePlus("CCA", ip1);
		//Get No of pixel(Area),Circularity and bbox of each CC
		 ResultsTable table = new MorphometricFeatures2D().add(Feature.AREA)
					.add(Feature.CIRCULARITY).add(Feature.BOUNDING_BOX).computeTable(ip2);
		 
		 //Retrieve Information
		 float[] nopix = table.getColumn(0);
			float[] circularity = table.getColumn(1);
			float[] xmin = table.getColumn(2);
			float[] xmax =table.getColumn(3);
			float[] ymin = table.getColumn(4);
			float[] ymax = table.getColumn(5);
			float[] width = new float[table.size()];
			float[] height = new float[table.size()];
			float[] aspectRatio = new float[table.size()];
			float[] nopix_per_area = new float[table.size()];
			
			//Calculate width,height,aspect ratio,No of pixels/bboxArea of each CC
			for(int i=0;i<table.size();i++) {
				width[i] = xmax[i] - xmin[i];
				height[i] = ymax[i] - ymin[i];
				aspectRatio[i] = width[i]/height[i];
				nopix_per_area[i] = nopix[i]/(width[i]*height[i]);
			}
			//Calculate how many criterias acheive by each CC
			int [] countPoint = new int[table.size()];
			for(int i=0;i<table.size();i++) {
				if(circularity[i]>=0.4 && circularity[i]<=0.7) {
					countPoint[i]++;
				}
				if(aspectRatio[i]>=3 && aspectRatio[i]<=5) {
					countPoint[i]++;
				}
				if(nopix_per_area[i]>=0.8 && nopix_per_area[i]<=1) {
					countPoint[i]++;
				}
				if(width[i]>=80 && width[i]<=160) {
					countPoint[i]++;
				}
				if(height[i]>=15 && height[i]<=50) {
					countPoint[i]++;
				}
				if(aspectRatio[i]<1 ||width[i]<50||height[i]<15||width[i]>200) {
					countPoint[i] = 0;
				}
				if(nopix_per_area[i]>=0.9&&nopix_per_area[i]<=1 &&width[i]>=50&&width[i]<80) {
					countPoint[i] = countPoint[i] + 1;
				}
					
			}
		
		//Find the CCs with highest point
		int maxIndices[] = findMaxIndices(countPoint);
		//bbox[] save x,y,width and height
		int [] bbox = new int[4];
		
		//Only 1 CC acheive highest point
	if(maxIndices.length == 1) {
		int index = maxIndices[0];
		bbox[0] = (int)xmin[index];
		bbox[1] = (int)ymin[index];
		bbox[2] = (int)width[index];
		bbox[3] = (int)height[index];
		
	}
	//More than 1 CC acheive highest point,select the CC with highest no_of_pix/area
	else if(maxIndices.length >1) {
		
		int index = findMaxValueForIndices(nopix_per_area,maxIndices);
		bbox[0] = (int)xmin[index];
		bbox[1] = (int)ymin[index];
		bbox[2] = (int)width[index];
		bbox[3] = (int)height[index];
		
	}
	
	else {
		throw new ArithmeticException("Unexpected Error occured"); 
	}
	return bbox;
	}
	
	
	
	 public static int findMaxValueForIndices(float[] arr, int[] indices) {
    	 int maxIndex = indices[0];
    	float maxValue = arr[maxIndex];
       
        for (int i = 1; i < indices.length; i++) {
            if (arr[indices[i]] > maxValue) {
            	maxValue = arr[indices[i]];
                maxIndex = indices[i];
            }
        }
        return maxIndex;
    }
	 public static int[] findMaxIndices(int[] arr) {
	        if (arr == null || arr.length == 0) {
	            // Handle the case where the array is empty or null
	            throw new IllegalArgumentException("Array is empty or null");
	        }

	        int maxIndex = 0;
	        int maxValue = arr[0];

	        // Find the maximum value in the array
	        for (int i = 1; i < arr.length; i++) {
	            if (arr[i] > maxValue) {
	                maxValue = arr[i];
	                maxIndex = i;
	            }
	        }

	        // Count the occurrences of the maximum value
	        int count = 0;
	        for (int i = 0; i < arr.length; i++) {
	            if (arr[i] == maxValue) {
	                count++;
	            }
	        }

	        // Create an array to store the indices of the maximum value
	        int[] maxIndices = new int[count];
	        int index = 0;
	        for (int i = 0; i < arr.length; i++) {
	            if (arr[i] == maxValue) {
	                maxIndices[index++] = i;
	            }
	        }

	        return maxIndices;
	    }
}