package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import tools.GlobalConstants;
import tools.ImageLoader;


/**
 * This class contains all of the panels to be displayed on the main screen (while playing)
 * It uses gridbaglayout to align all of the components horizontally.
 * 
 * @author Ewan Moshi
 *
 */

public class MainPanelMaster extends JPanel {
	private BufferedImage backgroundImage;
	
	private MainFrame mainFrame;
	
	/* Declare all the panels on this panel */
	private InventoryPanel inventoryPanel;
	private ButtonsPanel buttonsPanel;
	private MiniMapPanel miniMapPanel;
	private PlayerInformationPanel playerInfoPanel;
	
	public MainPanelMaster(MainFrame frame) {
		//this.setBorder(BorderFactory.createLineBorder(Color.black)); //draws a border around canvas (just to show where the canvas is) (delete later)
		this.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));  
		mainFrame = frame;
		
		/*Initialize the layout and the insets*/
		
		/*GridBagLayout doesn't draw the panels very small find out later why it does this*/ //TODO
		this.setLayout(new FlowLayout());

		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,1,0,1);
		
		/*Initialize the minimap panel and palce it bottom left */
		miniMapPanel = new MiniMapPanel();
		c.gridx = 1;
		c.gridy =0;
		this.add(miniMapPanel);

		
		buttonsPanel = new ButtonsPanel();
		c.gridx = 2;
		c.gridy = 0;
		this.add(buttonsPanel);
				
		playerInfoPanel = new PlayerInformationPanel();
		c.gridx =3;
		c.gridy =0;
		this.add(playerInfoPanel);
		
		/*Initialize the inventory panel and place it bottom right*/
		inventoryPanel = new InventoryPanel();
		c.gridx = 4;
		c.gridy = 0;
		this.add(inventoryPanel);
	
		/*Initialize the image for the main menu panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "mainMenuPanelBackground.png");
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
	
}
