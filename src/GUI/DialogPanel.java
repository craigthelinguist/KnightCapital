package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * Dispalys the border for the GameDialog
 *
 * @author Ewan Moshi
 *
 */

public class DialogPanel extends JPanel{

	private GameDialog  gd;

	public DialogPanel(GameDialog gd) {
		this.gd = gd;
		this.setOpaque(false);
		/*Load the images for the border*/
	    Border border = new Border(new ImageIcon(Constants.GUI_FILEPATH +"dTopLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dTop.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dTopRight.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dRight.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottomLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottom.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottomRight.png").getImage());
	    this.setBorder(border);
	    //this.setPreferredSize(new Dimension(410,410));
	    //this.setBackground(new Color(0,0,0,0));
	}

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);

	  }

}
