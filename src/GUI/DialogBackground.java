package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This class draws a background image for the GameDialog
 * 
 * @author Ewan Moshi
 *
 */

public class DialogBackground extends JPanel {

	private BufferedImage backgroundImage;
	
	public DialogBackground() {
		//this.setPreferredSize(new Dimension(200,200));
		
		/*Initialize the image for the dialog background*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogBackground.png");
		this.setOpaque(true);
	}
	
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
	

}
