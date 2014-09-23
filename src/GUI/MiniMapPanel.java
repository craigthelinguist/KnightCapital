package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

public class MiniMapPanel extends JPanel {

	private BufferedImage backgroundImage;
	
	public MiniMapPanel() {
		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(true);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "minimap.png");	
	}
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, 375, 200, this);
	  }
	
}
