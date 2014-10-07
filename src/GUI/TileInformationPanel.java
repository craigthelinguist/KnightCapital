package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;
import world.tiles.Tile;

/**
 * This panel holds all of the player's information such as stats, party  etc.
 //TODO JLabels to display player information
 *
 * @author Ewan Moshi
 *
 */
public class TileInformationPanel extends JPanel{

	private BufferedImage backgroundImage;
	private ImageIcon tileIcon;
	
	GridBagConstraints c;
	
	public TileInformationPanel() {
		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(false);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "playerInfoPanel.png");
		
		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		//c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		/*Create the label and set icon of label to the player's icon*/
		try {
			tileIcon = new ImageIcon(ImageIO.read(new FileInputStream(GlobalConstants.PORTRAITS +"ovelia.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel tileLabel = new JLabel(tileIcon);
		
		/*Insets parameters are top, left, bottom, right */
		c.insets = new Insets(1,40,0,0);
		c.gridx = 0;
		c.gridy = 1;
		this.add(tileLabel,c);
		
		JLabel tileInfoTitle = new JLabel("Ovelia");
		tileInfoTitle.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30)); //set font of JLabel to franklin gothic medium
		tileInfoTitle.setForeground(new Color(225,179,55));
		c.insets = new Insets(10,40,0,10);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(tileInfoTitle,c);
		
		/*set up the label that displays how many moves the player has left */
		JLabel movesLeft = new JLabel("Moves Left:");
		movesLeft.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 15)); 
		movesLeft.setForeground(new Color(225,179,55));
		c.insets = new Insets(1,40,8,10);
		c.gridx = 0;
		c.gridy = 2;
		this.add(movesLeft,c);
				
		
		JLabel partySize = new JLabel("Party Size:");
		partySize.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 15)); 
		partySize.setForeground(new Color(225,179,55));
		c.insets = new Insets(1,1,8,80);
		c.gridx = 1;
		c.gridy = 2;
		this.add(partySize,c);
	}

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, 375, 200, this);
	  }

	  /**
	   * Update the information being displayed in this panel to show whatever is on the given tile.
	   * @param tile: tile whose info you'll display.
	   */
	public void updateInfo(Tile tile) {
	}

}
