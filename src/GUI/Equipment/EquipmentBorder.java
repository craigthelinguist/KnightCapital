package GUI.Equipment;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.world.Border;
import tools.Constants;
import tools.ImageLoader;

public class EquipmentBorder extends JPanel{

	public EquipmentBorder() {

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

	}



}
