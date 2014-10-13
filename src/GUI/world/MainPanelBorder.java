package GUI.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * This class is just a JPanel which has a background image and is placed on top of the LayeredPane 
 * to create a border effect around the MainPanelMaster.
 * 
 * @author Ewan Moshi
 *
 */

public class MainPanelBorder extends JPanel{

	private BufferedImage backgroundImage;
	
	public MainPanelBorder(MainFrame frame) {
		//this.setPreferredSize(new Dimension(frame.getWidth(),200));
		this.setOpaque(false);

		/*Initialize the image for the main panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "mainMenuPanel.png");
		//this.setVisible(true);
	}
	
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
