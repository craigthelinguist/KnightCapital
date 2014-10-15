package GUI.Equipment;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.Constants;
import GUI.world.Border;

/**
 * A border for the equipment panel
 * @author Ewan Moshi
 *
 */
public class EquipmentHeroBorder extends JPanel {

	public EquipmentHeroBorder() {
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
