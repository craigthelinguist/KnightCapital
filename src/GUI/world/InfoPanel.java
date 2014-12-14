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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import tools.ImageManipulation;
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

	private Party selectedParty = null;
	private Tile lastSelectedTile = null;

	// contents of the info panel
	private ImageIcon iconPortrait = new ImageIcon();
	private JLabel labelPortrait = new JLabel(iconPortrait);
	private JLabel labelTitle = new JLabel();
	private JLabel labelDescription = new JLabel();

	private PortraitListener portraitListener = new PortraitListener();
	
	// MainFrame to which this info panel belongs
	private MainFrame mainframe;

	public InfoPanel(MainFrame mainFrame) {
		
		// set fields, parameters
		this.mainframe = mainFrame;
		this.setPreferredSize(new Dimension(375,200));
		this.setBackground(Color.BLACK);
		
		// panel that displays the contents of this InfoPanel
		JPanel panel = this.setupContents();
	
		// a wrapper for keeping the panel centred vertically
		this.setLayout(new GridBagLayout());
		this.add(panel, new GridBagConstraints());
		
		// mouse listener for the portrait
		this.labelPortrait.addMouseListener(portraitListener);
		
	}
	
	/**
	 * PortraitListener responds to mouse events that involve the portrait in the InfoPanel. If you hover over
	 * a portrait it should lighten up, and if you mouse off the portrait it will go back to normal colour.
	 * If you click on a portrait it should take you to a new PartyDialog.
	 * @author craigthelinguist
	 */
	private class PortraitListener implements MouseListener{

		private boolean active = false;
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (!active) return;
			InfoPanel.this.displayPartyDialog(selectedParty);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if (!active) return;
			System.out.println("We in");
			BufferedImage currentImage = (BufferedImage) InfoPanel.this.iconPortrait.getImage();
			BufferedImage lightened = ImageManipulation.lighten(currentImage, 55);
			updatePortrait(lightened);
			InfoPanel.this.repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if (!active) return;
			System.out.println("We out");
			Party party = InfoPanel.this.selectedParty;
			updatePortrait(party.getPortrait());
			InfoPanel.this.repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
		/**
		 * Enable the PortraitListener. It will now respond to events.
		 */
		public void enable(){
			this.active = true;
		}
		
		/**
		 * Disable the PortraitListener. It will no longer respond to events.
		 */
		public void disable(){
			this.active = false;
		}
		
		/**
		 * Check whether this PortraitListener is currently responding to events.
		 * @return boolean
		 */
		public boolean isActive(){
			return active;
		}
		
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
		labelTitle.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));

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
		
		// nothing selected - draw nothing.
		if (tile == null){
			this.resetInfo();
			this.lastSelectedTile = null;
			return;
		}
		
		// if the selection hasn't changed you don't have to redraw anything
		if (this.lastSelectedTile == tile){
			return;
		}
		
		this.lastSelectedTile = tile;
		this.resetInfo();
		
		// city tile selected, draw some info about the city
		if (tile instanceof CityTile){
			City city = ((CityTile)tile).getCity();
			updatePortrait(city.getPortrait());
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForName(city.getName());
			html += "</html>";
			labelDescription.setText(html);
			updateTitle(city.getOwner().getName(), Player.PLAYER_COLOR_HEX.get(city.getOwner().getSlot()));
			return;
		}
		
		WorldIcon occupant = tile.occupant();
		
		// empty tile
		if (occupant == null){
			updatePortrait(tile.getPortrait());
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForName("Grasslands");
			html += "</html>";
			labelDescription.setText(html);
			return;
		}
		
		// party on tile
		if (occupant instanceof Party){
			Party party = (Party) occupant;
			this.updatePortrait(party.getPortrait());
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForName(party.getHero().getName());
			if (mainframe == null || party.ownedBy(mainframe.getPlayer())){
				html += this.htmlForMovesLeft(party);
				html += this.htmlForHealthiness(party);
			}
			html += "</html>";
			labelDescription.setText(html);
			updateTitle(party.getOwner().getName(), Player.PLAYER_COLOR_HEX.get(party.getOwner().getSlot()));
			this.portraitListener.enable();
			this.selectedParty = party;
			return;
		}
		
		// item on tile
		if (occupant instanceof ItemIcon){
			ItemIcon itemIcon = (ItemIcon) occupant;
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForName("Item");
			html += "</html>";
			labelDescription.setText(html);
			updatePortrait(itemIcon.getPortrait());
			return;
		}
		
		// decor icon on tile
		if (occupant instanceof DecorIcon){
			DecorIcon decorIcon = (DecorIcon) occupant;
			String html = "<html><body style='width: 200px'>";
			html += this.htmlForName("Scenery");
			html += "</html>";
			labelDescription.setText(html);
			updatePortrait(decorIcon.getPortrait());
			return;
		}

		// shouldn't get down to here unless you've forgotten a case
		throw new RuntimeException("Drawing info for unknown tile occupant: " + occupant.getClass().toString());
		
	}
	
	/**
	 * Update the title of InfoPanel with the given string.
	 * @param titleText: string that should be the title.
	 */
	private void updateTitle(String titleText){
		updateTitle(titleText, "white");
	}
	
	private void updateTitle(String titleText, String hexcolor){
		String title = "<html><font color='"+hexcolor+"'>"+titleText+"</font></html>";
		labelTitle.setText(title);
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
	 * Generate some html for a line that displays the name of the thing being displayed.
	 * @param ownerName: string that is the owner of the thing being displayed in this InfoPanel
	 * @return html string
	 */
	private String htmlForName(String ownerName){
		return "<font color='white'>"+ownerName+"</font><br/>";
	}
	
	/**
	 * Helper method.
	 * Generate a line of html for the part of description that says the party health.
	 * @param party: party whose health you're displaying
	 * @return an html string containing formatted deteails about party health
	 */
	private String htmlForHealthiness(Party party){
		int healthiness = party.healthiness();
		String colour = null;
		if (healthiness == 100){
			colour = "#00FF00";
		}
		else if (healthiness >= 67){
			colour = "#ADFF2F";
		}
		else if (healthiness >= 34){
			colour = "yellow";
		}
		else if (healthiness >= 1){
			colour = "orange";
		}
		else colour = "red";
		return "<font color='white'>Health: </font><font color='"+colour+"'>" + healthiness + "%</font><br/></html>";
	}
	
	/**
	 * Helper method.
	 * Generate a line of html for the part of description that says how many moves the party has left.
	 * @param party: party whose move points you're displaying
	 * @return an html string containing formatted info about party movement
	 */
	private String htmlForMovesLeft(Party party){
		StringBuilder html = new StringBuilder("<font color='white'>Moves: </font>");
		int moves = party.getMovePoints();
		int totalMoves = party.getHero().get(Stat.MOVEMENT);
		String colour = null;
		if (moves == 0){
			colour = "red";
		}
		else if (moves < party.getHero().get(Stat.MOVEMENT)){
			colour = "yellow";
		}
		else{
			colour = "#00FF00";
		}
		html.append("<font color='"+colour+"'>" + moves + "/" + totalMoves + "</font><br/>");
		return html.toString();
	}
	
	/**
	 * Create a new PartyDialog instance.
	 */
	private void displayPartyDialog(Party party) {
		// this should really be somewhere more high-level
		Player player = this.mainframe.getPlayer();
		boolean ownsInstance = party.ownedBy(player);
		new PartyDialog(new JFrame(), this.lastSelectedTile, ownsInstance);
	}

	/**
	 * Reset the contents of all the labels.
	 */
	private void resetInfo() {
		labelPortrait.setIcon(null);
		labelTitle.setText("");
		labelDescription.setText("");
		portraitListener.disable();
		selectedParty = null;
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		InfoPanel ip = new InfoPanel(null);
		frame.add(ip);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Tile tile = TileFactory.newGrassTile(0, 0);
		Player player = new Player("Defenders of Light", Player.GREEN);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Tom Kazansky","archer",player,stats_hero);
		hero.damage(0);
		Party party = Party.newEmptyParty(player);
		party.addUnit(hero); 
		party.addMovementPoints(5);
		party.setOwner(player);
		tile.setIcon(party);
		
		Tile tile2 = TileFactory.newBushTile(0, 0);
		Tile tile3 = TileFactory.newRockTile(0, 0);
		Tile tile4 = TileFactory.newTreeTile(0, 0);
		
		ip.updateInfo(tile);
		
		ip.repaint();
		
	}

}
