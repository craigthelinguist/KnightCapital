package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
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
public class PlayerInformationPanel extends JPanel{

	private BufferedImage backgroundImage;

	private ImageIcon tileIcon;
	
	public PlayerInformationPanel() {
		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(false);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "playerInfoPanel.png");
		

		try {
			tileIcon = new ImageIcon(ImageIO.read(new FileInputStream(GlobalConstants.PORTRAITS +"ovelia.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel tileLabel = new JLabel(tileIcon);
		this.add(tileLabel);
		
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
		// TODO Auto-generated method stub
	}


}
