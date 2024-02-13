package anpr;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import gui.windows.FrameComponentInit;
import gui.windows.FrameMain;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.filter.*;
import inra.ijpb.measure.*;
import ij.measure.ResultsTable;
import inra.ijpb.measure.region2d.*;
import inra.ijpb.measure.region2d.MorphometricFeatures2D.Feature;
public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		FrameComponentInit frameComponentInit = new FrameComponentInit(); // show wait
		frameComponentInit.dispose();
		FrameMain mainFrame = new FrameMain();
	}		
}
