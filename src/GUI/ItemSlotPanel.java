package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

public class ItemSlotPanel extends JPanel {

	private BufferedImage backgroundImage;

	
	public ItemSlotPanel(String imageName) {
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + imageName);	
		//this.setVisible(true);
	}
	
	
	
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
