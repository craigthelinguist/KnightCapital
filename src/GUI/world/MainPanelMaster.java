package GUI.world;

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
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import tools.Constants;
import tools.ImageLoader;
import world.tiles.Tile;


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
	private MiniMap miniMapPanel;
	private TileInformationPanel tileInfoPanel;

	public MainPanelMaster(MainFrame frame) {
		//this.setBorder(BorderFactory.createLineBorder(Color.black)); //draws a border around canvas (just to show where the canvas is) (delete later)
		this.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
		mainFrame = frame;

		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,1,0,1);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;


		/*Initialize the minimap panel and palce it bottom left */
		miniMapPanel = new MiniMap();
		c.gridx = 0;
		c.gridy = 0;
		this.add(miniMapPanel,c);


		buttonsPanel = new ButtonsPanel(mainFrame);
		c.gridx = 1;
		c.gridy = 0;
		this.add(buttonsPanel,c);

		tileInfoPanel = new TileInformationPanel();
		c.gridx =2;
		c.gridy =0;
		this.add(tileInfoPanel,c);

		/*Initialize the inventory panel and place it bottom right*/
		inventoryPanel = new InventoryPanel(frame);
		c.weightx = 0.0;
		c.gridx = 3;
		c.gridy = 0;
		this.add(inventoryPanel,c);

		/*Initialize the image for the main menu panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "mainMenuPanelBackground.png");

	}

	public void actionPerformed(ActionEvent e) {
	}


	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }

	  /**
	   * Update info displayed in playerInfoPanel.
	   * @param tile: tile whose info you'll display.
	   */
	public void updateInfo(Tile tile) {
		tileInfoPanel.updateInfo(tile);
		try {
			inventoryPanel.updateInventoryPanel(tile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
