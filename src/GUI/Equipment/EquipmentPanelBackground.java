package GUI.Equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * A backgorund image for the equipment panel.
 * Makes the panel look nicer
 * @author Ewan Moshi
 *
 */
public class EquipmentPanelBackground extends JPanel{

	private BufferedImage backgroundImage;


	public EquipmentPanelBackground() {
		//this.setPreferredSize(new Dimension(700, eq.getHeight()));

		 /*Initialize the image for the dialog background*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "dialogBackground.png");
		this.setLayout(new FlowLayout());

		/*Add the layer which contains a border and the hero equipment panel*/
		//HeroEquipLayeredPane heroEquipmentLayer = new HeroEquipLayeredPane(this);
		//this.add(heroEquipmentLayer);

	}

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }


}


