package gui.world;

import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import gui.party.PartyDialog;
import gui.reusable.DeadListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
	private BufferedImage background;

	private Party selectedParty = null;
	private Tile lastSelectedTile = null;

	// contents of the info panel
	private ImageIcon iconPortrait = new ImageIcon();
	private JLabel labelPortrait = new JLabel(iconPortrait);
	private JLabel title = new JLabel();
	private JLabel subtitle = new JLabel();
	private JLabel description = new JLabel();
	
	private static final int TEXT_PANEL_WD = 270;
	

	private PortraitListener portraitListener = new PortraitListener();
	
	// GameFrame to which this info panel belongs
	private WorldPanel gameFrame;

	public InfoPanel(WorldPanel GameFrame, BufferedImage background) {

		
		// set fields, parameters
		this.gameFrame = GameFrame;
		this.background = background;
		
		// make components
		JPanel portrait = this.portraitPanel();
		JPanel buffer = this.bufferPanel();
		JPanel text = this.textPanel();
		
		// borders, layout, formatting
		this.setBackground(Color.ORANGE);
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.setBorder(new EmptyBorder(10,10,25,25));
		
		// add components
		this.add(portrait);
		this.add(buffer);
		this.add(text);
		
		// add listeners
		this.addMouseListener(DeadListener.get());
		this.addMouseMotionListener(DeadListener.get());
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(this.background, 0,0, getWidth(), getHeight(), this);
	}
	
	private JPanel portraitPanel(){
		this.labelPortrait = new JLabel();
		labelPortrait.setPreferredSize(new Dimension(66,100));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		panel.addMouseListener(new PortraitListener());
		panel.add(labelPortrait, BorderLayout.CENTER);
		return panel;
	}

	private JPanel bufferPanel(){
		JPanel buffer = new JPanel();
		buffer.setPreferredSize(new Dimension(25,0));
		return buffer;
	}
	
	private JPanel textPanel(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(TEXT_PANEL_WD,150));
		panel.setBackground(Color.BLACK);
		
		panel.setBorder(new EmptyBorder(10,15,10,10));
		
		this.title = new JLabel();
		title.setPreferredSize(new Dimension(TEXT_PANEL_WD,0));
		title.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 20));
		title.setBackground(Color.YELLOW);
		
		this.subtitle = new JLabel();
		subtitle.setPreferredSize(new Dimension(TEXT_PANEL_WD,0));
		subtitle.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		title.setBackground(Color.DARK_GRAY);
		
		this.description = new JLabel();
		description.setPreferredSize(new Dimension(TEXT_PANEL_WD,0));
		description.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 15));
		title.setBackground(Color.CYAN);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(title);
		panel.add(subtitle);
		panel.add(description);
	
		return panel;
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
		
		// city tile selected, display info about the city
		if (tile instanceof CityTile){
			City city = ((CityTile)tile).getCity();
			updatePortrait(city.getPortrait());
			updateTitle(city.getName());
			updateSubtitle(city.getOwner().getName(), Player.PLAYER_COLOR_HEX.get(city.getOwner().getSlot()));
			return;
		}
		
		WorldIcon occupant = tile.occupant();
		
		// empty tile
		if (occupant == null){
			updatePortrait(tile.getPortrait());
			String html = "<html><body style='width: 200px'><br/>";
			html += this.htmlForName("Grasslands");
			html += "</html>";
			subtitle.setText(html);
			return;
		}
		
		// party on tile
		if (occupant instanceof Party){
			Party party = (Party) occupant;
			this.updatePortrait(party.getPortrait());
			String html = "<html><body style='width: 200px'><br/>";
			if (gameFrame == null || party.ownedBy(gameFrame.getPlayer())){
				html += this.htmlForMovesLeft(party);
				html += this.htmlForHealthiness(party);
			}
			html += "</html>";
			description.setText(html);

			updateSubtitle(party.getOwner().getName(),Player.PLAYER_COLOR_HEX.get(party.getOwner().getSlot()));
			updateTitle(party.getHero().getName());
			this.portraitListener.enable();
			this.selectedParty = party;
			return;
		}
		
		// item on tile
		if (occupant instanceof ItemIcon){
			ItemIcon itemIcon = (ItemIcon) occupant;
			updateSubtitle("Item", "white");
			updatePortrait(itemIcon.getPortrait());
			return;
		}
		
		// decor icon on tile
		if (occupant instanceof DecorIcon){
			DecorIcon decorIcon = (DecorIcon) occupant;
			updateSubtitle("Scenery", "white");
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
		String html = "<html><font color='"+hexcolor+"'>"+titleText+"</font></html>";
		title.setText(html);
	}
	
	private void updateSubtitle(String subtitleText, String hexcolor){
		String html = "<html><font color='"+hexcolor+"'>"+subtitleText+"</font></html>";
		subtitle.setText(html);
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
		Player player = this.gameFrame.getPlayer();
		boolean ownsInstance = party.ownedBy(player);
		new PartyDialog(new JFrame(), this.lastSelectedTile, ownsInstance);
	}

	/**
	 * Reset the contents of all the labels.
	 */
	private void resetInfo() {
		labelPortrait.setIcon(null);
		title.setText("<html><br/></html>");
		subtitle.setText("<html><br/></html>");
		description.setText("");
		portraitListener.disable();
		selectedParty = null;
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
			BufferedImage currentImage = (BufferedImage) InfoPanel.this.iconPortrait.getImage();
			BufferedImage lightened = ImageManipulation.lighten(currentImage, 55);
			updatePortrait(lightened);
			InfoPanel.this.repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
			if (!active) return;
			Party party = InfoPanel.this.selectedParty;
			updatePortrait(party.getPortrait());
			InfoPanel.this.repaint();
		}

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
		
	}
	

}
