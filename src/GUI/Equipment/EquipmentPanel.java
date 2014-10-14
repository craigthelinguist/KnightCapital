package GUI.Equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GUI.party.PartyDialog;
import tools.Constants;
import tools.ImageLoader;

public class EquipmentPanel extends JPanel {

	private BufferedImage backgroundImage;

	public EquipmentPanel() {

		this.setLayout(new GridBagLayout());
		/*set the background of this panel to an image from assets folder*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "equipmentBackground.png");

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		//c.anchor = GridBagConstraints.LAST_LINE_START;
		c.weightx = 0;
		c.weighty = 0;

		ItemPanel helmet = new ItemPanel("helmetItemSlot");
		c.gridx = 1;
		c.gridy = 0;
		this.add(helmet,c);

		ItemPanel plate = new ItemPanel("plateItemSlot");
		c.gridx = 1;
		c.gridy = 1;
		this.add(plate,c);

		ItemPanel boots = new ItemPanel("bootsItemSlot");
		c.gridx = 1;
		c.gridy = 2;
		this.add(boots,c);

		ItemPanel weapon = new ItemPanel("weaponItemSlot");
		c.gridx = 0;
		c.gridy = 1;
		this.add(weapon,c);

		ItemPanel shield = new ItemPanel("shieldItemSlot");
		c.gridx = 2;
		c.gridy = 1;
		this.add(shield,c);

		//this.setBackground(Color.YELLOW);
		//this.setPreferredSize(new Dimension(getWidth(),getHeight()));
	}


	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
