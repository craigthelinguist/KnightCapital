package GUI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This is the main game frame. Contains a canvas and a JLayeredPanel at the bottom of the frame. 
 * Everything is drawn to the canvas. //TODO (write some more info here later)
 * 
 * @author Ewan Moshi
 *
 */

public class MainFrame extends JFrame {
	
	private LayeredPanel layeredPanel;
	private Canvas canvas;
	
	
	public MainFrame() {
		/* These two statements make the frame full screen. (Commented out for now) */
		this.setExtendedState(this.MAXIMIZED_BOTH);  
		this.setUndecorated(false); //true means borderless window
		

		//this.setSize(1300,200); 
		this.setLayout(new BorderLayout());
		//this.setVisible(true);
		
		
		//Initialize the canvas and add it in the center
		canvas = new Canvas(this);
		this.add(canvas,BorderLayout.CENTER);
		
		//Initialize the layered panel and add it to the south
		layeredPanel = new LayeredPanel(this);
		this.add(layeredPanel,BorderLayout.SOUTH);

		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	/**public ImageIcon createInventoryIcon(String name) {
		  try {
			    ImageIcon invImg = ImageIO.read(getClass().getResource("assets/GUIAssets/" +name+".png"));
			    return invImg;

			  } catch (IOException ex) {
			  }
		    return null;
	}*/
	
	
	/* Main method to test the MainFrame */
	public static void main(String[] args) {
		new MainFrame();
	}
}
