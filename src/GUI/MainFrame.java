package GUI;



import java.awt.BorderLayout;
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
	
	private MainMenuPanel mainMenuPanel;
	private Canvas canvas;
	
	
	public MainFrame() {
		this.setSize(600,200); 
		this.setLayout(new BorderLayout());
		
		//Initialize the canvas and add it in the center
		canvas = new Canvas(this);
		this.add(canvas,BorderLayout.CENTER);
		
		//Initialize the main menu panel and add it to the south
		mainMenuPanel = new MainMenuPanel(this);
		this.add(mainMenuPanel,BorderLayout.SOUTH);
				
		
		 /*These two statements make the frame full screen. (Commented out for now)
		this.setExtendedState(this.MAXIMIZED_BOTH);  
		this.setUndecorated(true);*/
		
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
