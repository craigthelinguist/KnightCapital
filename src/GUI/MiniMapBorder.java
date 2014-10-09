package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

public class MiniMapBorder extends JPanel {
	
	private BufferedImage backgroundImage;
	
	public MiniMapBorder() {
		this.setOpaque(false);
		/*Initialize the image for the mini map border*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "miniMapBorder.png");	
	
	}

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // paint the background image and scale it to fill the entire space
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }
	
}
