package GUI.world;

import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import player.Player;

import tools.Constants;
import tools.ImageLoader;
import world.icons.DecorIcon;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.tiles.TileFactory;
import world.towns.City;
import GUI.party.PartyDialog;

/**
 * This panel holds all of the player's information such as stats, party  etc.
 //TODO JLabels to display player information
 *
 * @author Ewan Moshi
 *
 */
public class InfoPanel extends JPanel{

	// graphical constants
	private final Dimension PORTRAIT_SIZE = Constants.PORTRAIT_DIMENSIONS;
	private final Dimension PORTRAIT_BOX_SIZE = new Dimension(PORTRAIT_SIZE.width+20,PORTRAIT_SIZE.height);
	
	// bg image of info panel
	private BufferedImage backgroundImage;

	private Tile lastSelectedTile;

	// contents of the info panel
	private ImageIcon iconPortrait = new ImageIcon();
	private JLabel labelPortrait = new JLabel(iconPortrait);
	private JLabel labelTitle = new JLabel();
	private JLabel labelDescription = new JLabel();

	// button which takes you to a PartyDialog
	private CustomButton partyButton;
	
	// MainFrame to which this info panel belongs
	private MainFrame mainframe;

	public InfoPanel(MainFrame mainFrame) {
		
		// set fields
		this.mainframe = mainFrame;
		this.setPreferredSize(new Dimension(375,200));
		this.setBackground(Color.BLACK);
		
		// panel that displays the contents of this InfoPanel
		JPanel panel = this.setupContents();
	
		// a wrapper for keeping the panel centred vertically
		this.setLayout(new GridBagLayout());
		this.add(panel, new GridBagConstraints());
		
	}
	
	/**
	 * Create and return the JPanel that contains the contents of the InfoPanel.
	 * @return JPanel
	 */
	private JPanel setupContents(){

		// set up panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);

		// the portrait
		labelPortrait.setPreferredSize(PORTRAIT_BOX_SIZE);
		labelPortrait.setMinimumSize(PORTRAIT_BOX_SIZE);
		
		// text containing moves left, health, etc.
		labelDescription.setPreferredSize(new Dimension(200,150));
		labelDescription.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 16));

		// text containing name/title
		labelTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitle.setPreferredSize(new Dimension(200,40));
		labelTitle.setMaximumSize(new Dimension(200,40));
		labelTitle.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 25));

		// layout set up
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		// horizontal
		horizontal
			.addComponent(labelPortrait)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(labelDescription)
				.addComponent(labelTitle)
			)
		;
		
		// vertical
		vertical
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(labelPortrait)
				.addGroup(layout.createSequentialGroup()
					.addComponent(labelDescription)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
						.addComponent(labelTitle)
				)
			)
		;
		
		return panel;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint the background image and scale it to fill the entire space
		g.drawImage(backgroundImage, 0, 0, 375, 200, this);
		labelPortrait.paintComponents(g);
	}

	/**
	 * Update the information being displayed in this panel to show whatever is on the given tile.
	 * @param tile: tile whose info you'll display.
	 */
	public void updateInfo(Tile tile) {
		
		this.lastSelectedTile = tile;
		
		// nothing selected, display a blank panel
		this.resetInfo();
		if (tile == null){
			return;
		}
		
		// city tile selected, draw some info about the city
		if (tile instanceof CityTile){
			City city = ((CityTile)tile).getCity();
			updatePortrait(city.getPortrait());
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForOwner(city.getOwner().name);
			html += "</html>";
			labelDescription.setText(html);
			updateTitle(city.getName());
			return;
		}
		
		WorldIcon occupant = tile.occupant();
		
		// empty tile
		if (occupant == null){
			updatePortrait(tile.getPortrait());
			updateTitle("Grasslands");
			return;
		}
		
		// party on tile
		if (occupant instanceof Party){
			Party party = (Party) occupant;
			this.updatePortrait(party.getPortrait());
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForOwner(party.getOwner().name);
			html += this.htmlForMovesLeft(party);
			html += this.htmlForHealthiness(party);
			html += "</html>";
			labelDescription.setText(html);
			updateTitle(party.getHero().getName());
			return;
		}
		
		// item on tile
		if (occupant instanceof ItemIcon){
			ItemIcon itemIcon = (ItemIcon) occupant;
			updateTitle("Item");
			updatePortrait(itemIcon.getPortrait());
			return;
		}
		
		// decor icon on tile
		if (occupant instanceof DecorIcon){
			DecorIcon decorIcon = (DecorIcon) occupant;
			updateTitle("Scenery");
			updatePortrait(decorIcon.getPortrait());
			return;
		}

		// shouldn't get down to here.
		throw new RuntimeException("Drawing info for unknown tile occupant: " + occupant.getClass().toString());
		
	}
	
	private void updateTitle(String titleText){
		String title = "<html><font color='yellow'>"+titleText+"</font></html>";
		labelTitle.setText(title);
	}
	
	
	private String htmlForOwner(String ownerName){
		return "<font color='yellow'>"+ownerName+"</font><br/>";
	}

	/**
	 * Update the ImageIcon to display the specified image. Update labelPortrait to display
	 * the newly-updated ImageIcon.
	 * @param img: the new image to be displayed
	 */
	private void updatePortrait(BufferedImage img){
		iconPortrait.setImage(img);
		labelPortrait.setIcon(iconPortrait);
		labelPortrait.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	/**
	 * Helper method.
	 * Generate a line of html for the part of description that says the party health.
	 * @param party: party whose health you're displaying
	 * @return an html string containing formatted deteails about party health
	 */
	private String htmlForHealthiness(Party party){
		int healthiness = party.healthiness();
		return "<font color='yellow'>Health: </font><font color='white'>" + healthiness + "%</font><br/></html>";
	}
	
	/**
	 * Helper method.
	 * Generate a line of html for the part of description that says how many moves the party has left.
	 * @param party: party whose move points you're displaying
	 * @return an html string containing formatted info about party movement
	 */
	private String htmlForMovesLeft(Party party){
		StringBuilder html = new StringBuilder("<font color='yellow'>Moves: </font>");
		int moves = party.getMovePoints();
		int totalMoves = party.getHero().get(Stat.MOVEMENT);
		if (moves == 0){
			html.append("<font color='red'>" + moves + "/" + totalMoves + "</font><br/>");
		}
		else{
			html.append("<font color='white'>" + moves + "/" + totalMoves + "</font><br/>");
		}
		return html.toString();
	}
	
	/**
	 * Create a new PartyDialog instance.
	 */
	private void displayPartyDialog() {
		// this should really be somewhere more high-level
		if(lastSelectedTile != null && lastSelectedTile.occupant() instanceof Party){
			Party party = (Party) lastSelectedTile.occupant();
			Player player = this.mainframe.getPlayer();
			boolean ownsInstance = party.ownedBy(player);
			new PartyDialog(new JFrame(), this.lastSelectedTile, ownsInstance);
		}
		
	}

	/**
	 * Reset the contents of all the labels.
	 */
	private void resetInfo() {
		labelPortrait.setIcon(null);
		labelTitle.setText("");
		labelDescription.setText("");
		if(partyButton != null) {
			partyButton.setVisible(false);
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		InfoPanel ip = new InfoPanel(null);
		frame.add(ip);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		Tile tile = TileFactory.newGrassTile(0, 0);
		Player player = new Player("Defenders of Light", Player.BLUE);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Tom Kazansky","archer",player,stats_hero);
		Party party = Party.newEmptyParty(player);
		party.addUnit(hero); 
		party.setOwner(player);
		InfoPanel.party = party;
		tile.setIcon(party);
		
		ip.updateInfo(tile);
		ip.repaint();
		
	}

	private static Party party;

}
