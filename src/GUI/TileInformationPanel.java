package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.PartyDialog.PartyDialog;
import tools.Constants;
import tools.ImageLoader;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

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


	CustomButton partyButton;

	JLabel tileLabel = new JLabel();
	JLabel tileInfoTitle = new JLabel();
	JLabel playerName = new JLabel();
	JLabel movesLeft = new JLabel();
	JLabel partySize = new JLabel();


	GridBagConstraints c;


	public TileInformationPanel() {
		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(false);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "playerInfoPanel.png");

		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		//c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;

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

		// nothing selected, display a blank panel
		if (tile == null){

		}
		// city tile selected, draw some info about the city
		else if (tile instanceof CityTile){
			CityTile ct = (CityTile)tile;
			City city = ct.getCity();

			resetPanel();


			/*Setup the labels to display the city image*/
			tileInfoTitle = new JLabel("City");
			tileInfoTitle.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
			tileInfoTitle.setForeground(new Color(225,179,55));
			c.insets = new Insets(0,0,0,120);
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.gridx = 1;
			c.gridy = 0;
			this.add(tileInfoTitle,c);


			/*Create the label and set icon of label to the player's icon*/
			tileIcon = new ImageIcon(city.getPortrait());
			tileLabel = new JLabel(tileIcon);
			c.insets = new Insets(1,40,0,0);
			c.gridx = 0;
			c.gridy = 4;
			this.add(tileLabel,c);
			revalidate();

		}
		else{

			WorldIcon occupant = tile.occupant();


			// empty tile selected, display some grass or something
			if (occupant == null){
				resetPanel();

				/*Setup the labels to display grass*/
				tileInfoTitle = new JLabel("Grass");
				tileInfoTitle.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30)); //set font of JLabel to franklin gothic medium
				tileInfoTitle.setForeground(new Color(225,179,55));
				c.insets = new Insets(0,0,0,120);
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridx = 1;
				c.gridy = 0;
				this.add(tileInfoTitle,c);


				/*Create the label and set icon of label to the player's icon*/
				tileIcon = new ImageIcon(tile.getPortrait());
				tileLabel = new JLabel(tileIcon);

				/*Insets parameters are top, left, bottom, right */
				c.insets = new Insets(1,40,0,0);
				c.gridx = 0;
				c.gridy = 4;
				this.add(tileLabel,c);
				revalidate();
			}

			else if (occupant instanceof Party){
				Party party = (Party)occupant;
				party.getHero();
				party.getOwner();

				resetPanel();

				/*Create the label and set icon of label to the player's icon*/
				tileIcon = new ImageIcon(party.getPortrait());
				tileLabel = new JLabel(tileIcon);

				/*Insets parameters are top, left, bottom, right */
				c.insets = new Insets(1,40,0,0);
				c.gridx = 0;
				c.gridy = 4;
				this.add(tileLabel,c);

				tileInfoTitle = new JLabel("Ovelia");
				tileInfoTitle.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20)); //set font of JLabel to franklin gothic medium
				tileInfoTitle.setForeground(new Color(225,179,55));
				c.insets = new Insets(0,0,0,120);
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridx = 1;
				c.gridy = 0;
				this.add(tileInfoTitle,c);

				/*set up the label that displays how many moves the player has left */
				playerName = new JLabel("Harry");
				playerName.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 13));
				playerName.setForeground(new Color(225,179,55));
				c.insets = new Insets(10,0,0,0);
				c.gridx = 1;
				c.gridy = 2;
				this.add(playerName,c);


				/*set up the label that displays how many moves the player has left */
				movesLeft = new JLabel("Moves Left:");
				movesLeft.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 15));
				movesLeft.setForeground(new Color(225,179,55));
				c.insets = new Insets(1,0,8,0);
				c.gridx = 1;
				c.gridy = 4;
				this.add(movesLeft,c);

				/*Declare and initialize the images for the party button */
				BufferedImage partyButtonDefault = ImageLoader.load(Constants.GUI_BUTTONS + "partyButton.png");
				BufferedImage partyButtonPressed = ImageLoader.load(Constants.GUI_BUTTONS + "partyButtonClicked.png");
				BufferedImage partyButtonHover = ImageLoader.load(Constants.GUI_BUTTONS + "partyButtonHover.png");

				// copy of currently selected tile
				partyButton = new CustomButton(partyButtonDefault, partyButtonPressed, partyButtonHover) {
					@Override
					public void mousePressed(MouseEvent e) {
						// If the user presses this button, we want to display a new partyDialog
						//TODO: this
					}
				};

				// Add functionality to party button

				c.insets = new Insets(1,20,15,10);
				c.gridx = 0;
				c.gridy = 5;
				this.add(partyButton,c);

				partySize = new JLabel("Party Health:");
				partySize.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 15));
				partySize.setForeground(new Color(225,179,55));
				c.insets = new Insets(1,1,8,0);
				c.gridx = 1;
				c.gridy = 5;
				this.add(partySize,c);
				revalidate(); //revalidate this panel (displays all the information)
			}

			// item on the tile, draw a picture of a treasure chest or something
			else if (occupant instanceof ItemIcon){

			}

		}
	}


	private void resetPanel() {
		/*Reset all the labels*/
		tileLabel.setIcon(null);
		tileInfoTitle.setText("");
		playerName.setText("");
		movesLeft.setText("");
		partySize.setText("");
		if(partyButton != null) {
			partyButton.setVisible(false);
		}
	}



}
