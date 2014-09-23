package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This Class contains all the buttons for the main panel
 * 
 * @author Ewan Moshi
 *
 */
public class ButtonsPanel extends JPanel {

	private InventoryButton inventoryButton;
	
	/*Place holder buttons*/
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	public ButtonsPanel() {
		/*set the size of this panel to be size of the image*/
		this.setOpaque(true);
			
		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,1,5,1); //top, left, bottom, right padding (in that order)
		
		/*Declare and initialize the images for the button */
		BufferedImage inventoryDefaultIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryDefaultTemp.png");
		BufferedImage inventoryHoverIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryHoverTemp.png");
		inventoryButton = new InventoryButton(inventoryDefaultIcon, inventoryHoverIcon);
		/*Place these buttons on a different panel and add that panel on this layeredPane(PanelMaster)*/
		c.gridx = 0;
		c.gridy = 0;
		this.add(inventoryButton,c);
			
		b1 = new JButton("placeholder");
		c.gridx = 0;
		c.gridy = 1;
		this.add(b1,c);
		
		b2 = new JButton("placeholder");
		c.gridx = 0;
		c.gridy = 2;
		this.add(b2,c);
		
		b3 = new JButton("placeholder");
		c.gridx = 0;
		c.gridy = 3;
		this.add(b3,c);
		
		
	}
	  

}
