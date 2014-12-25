package gui.town;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * A panel that has the storage background image
 * @author Ewan Moshi && Aaron Craig
 *
 */
public class StoragePanel extends JPanel{

	private BufferedImage backgroundImage;

	public StoragePanel() {
		this.setPreferredSize(new Dimension(540,390));
		/*There is storageBackground and storageBackground2, they have different backgrounds*/
		backgroundImage = ImageLoader.load(Constants.GUI_TOWN + "storageBackground.png");
	}



	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
