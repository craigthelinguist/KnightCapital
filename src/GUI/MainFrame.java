package GUI;


import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import tools.GlobalConstants;
import tools.ImageLoader;

public class MainFrame extends JFrame {
	
	/*This is only a test for custom buttons*/
	private InventoryButton inventoryButton;
	
	public MainFrame() {

		/* These two statements make the frame full screen. (Commented out for now)
		this.setExtendedState(this.MAXIMIZED_BOTH);  
		this.setUndecorated(true);*/
		
		this.setResizable(true);
		this.setSize(500,500);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*This is only a test for custom buttons*/
		BufferedImage inventoryDefaultIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryDefaultTemp.png");
		BufferedImage inventoryHoverIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryHoverTemp.png");
		inventoryButton = new InventoryButton(inventoryDefaultIcon, inventoryHoverIcon);
		add(inventoryButton);
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
