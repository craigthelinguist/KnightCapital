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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;
import GUI.party.PartyDialog;

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
	JLabel name = new JLabel();
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

	private Tile lastSelectedTile;

	/**
	 * Update the information being displayed in this panel to show whatever is on the given tile.
	 * @param tile: tile whose info you'll display.
	 */
	public void updateInfo(Tile tile) {
		this.lastSelectedTile = tile;

		// nothing selected, display a blank panel
		if (tile == null){
			/*If user clicks outside of map, reset all labels*/
			resetPanel();
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
				name = new JLabel("Harry");
				name.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 13));
				name.setForeground(new Color(225,179,55));
				c.insets = new Insets(10,0,0,0);
				c.gridx = 1;
				c.gridy = 2;
				this.add(name,c);


				/*set up the label that displays how many moves the player has left */
				Party p =(Party)occupant;
				int damage = p.getHero().getBaseDamage();
				int health = p.getHero().getBaseHealth();
				int armour = p.getHero().getBaseArmour();
				int moves = p.getHero().getMovePoints();
				
				int buffD = p.getHero().getBuffedDamage();
				int buffH = p.getHero().getBuffedHealth();
				int buffA = p.getHero().getBuffedArmour();

				movesLeft = new JLabel("<html>Health: "+health+"<font color='green'> +"+buffH+"</font> <br>Damage: "+damage+"<font color='green'> +"+buffD+"</font> <br>Armour: "+armour+"<font color='green'> +"+buffA+"</font><br>Moves Left: "+moves+"</html>");
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
						displayPartyDialog();
					}
				};

				// Add functionality to party button

				c.insets = new Insets(1,40,15,10);
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
				ItemIcon ii = (ItemIcon)occupant;

				/*Rest the panels*/
				resetPanel();

				/*draw the icon on the panel*/
				tileInfoTitle = new JLabel("Item");
				tileInfoTitle.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
				tileInfoTitle.setForeground(new Color(225,179,55));
				c.insets = new Insets(0,0,0,120);
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridx = 1;
				c.gridy = 0;
				this.add(tileInfoTitle,c);


				/*Create the label and set icon of label to the player's icon*/
				tileIcon = new ImageIcon(ii.getImage());
				tileLabel = new JLabel(tileIcon);

				/*set up the label for item description */
				name = new JLabel("Uknkown Item");
				name.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 13));
				name.setForeground(new Color(225,179,55));
				c.insets = new Insets(10,0,0,0);
				c.gridx = 1;
				c.gridy = 2;
				this.add(name,c);

				/*Insets parameters are top, left, bottom, right */
				c.insets = new Insets(1,40,0,0);
				c.gridx = 0;
				c.gridy = 4;
				this.add(tileLabel,c);
				revalidate();
			}

		}
	}

	private void displayPartyDialog() {

		//if isOwner = true
		if(lastSelectedTile != null && lastSelectedTile.occupant() instanceof Party){
			new PartyDialog(new JFrame(), this.lastSelectedTile, true);
		}
	}

	private void resetPanel() {
		/*Reset all the labels*/
		tileLabel.setIcon(null);
		tileInfoTitle.setText("");
		name.setText("");
		movesLeft.setText("");
		partySize.setText("");
		if(partyButton != null) {
			partyButton.setVisible(false);
		}
	}



}
