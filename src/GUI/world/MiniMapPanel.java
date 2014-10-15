package GUI.world;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * Mini map panel that disaplys the mini map for the game.
 * @author moshiewan
 *
 */
public class MiniMapPanel extends JPanel {

	private BufferedImage backgroundImage;
	
	public MiniMapPanel() {
		this.setOpaque(true);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "minimap.png");	
		
 
	}
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
	
}
