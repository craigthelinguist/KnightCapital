package GUI.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import tools.ImageManipulation;

public class ItemSlotPanel extends JPanel {

	private BufferedImage backgroundImage;
	final String imageName;

	/**
	 *
	 * @param imageName The name of this panel. Used to open image from folder.
	 */
	public ItemSlotPanel(String imageName) {
		/*Store the name of the item */
		this.imageName = imageName;
		this.setOpaque(false);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.ITEMS + imageName);
	}


	/**
	 * This method sets the background to a lightened version.
	 * Used for when user hovers over this panel to indicate that they have this item selected.
	 * @param lightenedImage A lightened verson of the backgroundImage
	 */
	public void changeBackground(BufferedImage lightenedImage) {
		this.backgroundImage = lightenedImage;
	}

	/**
	 *
	 * @return BufferedImage backgroundImage
	 */
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}


	/**
	 * Returns the imageName of this item slot
	 * @return imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Paint the background image onto this panel.
	 */
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
