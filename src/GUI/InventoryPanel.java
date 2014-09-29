package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This panel displays inventory as an image background for the panel
 * 
 * @author Ewan Moshi
 *
 */
public class InventoryPanel extends JPanel {

	private BufferedImage backgroundImage;
	
	public InventoryPanel() {
		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(true);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventory.png");	
		
		
		/*started from here*/
		
		
		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(15,15,10,10);
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		/*The top row panels */
		
		/*Declare and initialize slot1 (panel) */
		JPanel slot1 = new JPanel();
		slot1.setBackground(new Color(255,0,0,50)); //this is just to test the position of this panel (third parameter is alpha value)
		c.insets = new Insets(15,15,10,10);
		c.gridx = 0;
		c.gridy = 0;
		this.add(slot1,c);
		
		/*Declare and initialize slot2 */
		JPanel slot2 = new JPanel();
		slot2.setBackground(new Color(255,0,0,50));
		c.gridx = 1;
		c.gridy = 0;
		this.add(slot2,c);
		
		/*Declare and initialize slot3 */
		JPanel slot3 = new JPanel();
		slot3.setBackground(new Color(255,0,0,50));
		c.insets = new Insets(15,10,10,16);
		c.gridx = 2;
		c.gridy = 0;
		this.add(slot3,c);
		
		
		/*The bottom row panels*/
		
		/*Declare and initialize slot4 */
		JPanel slot4 = new JPanel();
		slot4.setBackground(new Color(255,0,0,50));
		c.insets = new Insets(10,15,15,10);
		c.gridx = 0;
		c.gridy = 1;
		this.add(slot4,c);
		
		/*Declare and initialize slot5 */
		JPanel slot5 = new JPanel();
		slot5.setBackground(new Color(255,0,0,50));
		c.gridx = 1;
		c.gridy = 1;
		this.add(slot5,c);
		
		/*Declare and initialize slot6 */
		JPanel slot6 = new JPanel();
		slot6.setBackground(new Color(255,0,0,50));
		c.insets = new Insets(10,10,15,16);
		c.gridx = 2;
		c.gridy = 1;
		this.add(slot6,c);
		
	}
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, 375, 200, this);
	  }
	
	  

}
