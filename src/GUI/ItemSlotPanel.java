package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import tools.ImageManipulation;

public class ItemSlotPanel extends JPanel {

	private BufferedImage backgroundImage;
	final String imageName;

	public ItemSlotPanel(String imageName) {
		/*Store the name of the item */
		this.imageName = imageName;

		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.ITEMS + imageName+"Slot");
	}


	public void changeBackground(BufferedImage lightenedImage) {
		this.backgroundImage = lightenedImage;
	}

	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}


	public String getImageName() {
		return imageName;
	}

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
