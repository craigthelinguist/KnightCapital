package gui.party;

import game.effects.Buff;
import game.items.EquippedItem;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;
import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.creatures.Unit;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import game.units.stats.UnitStats;
import gui.reusable.CustomButton;
import gui.reusable.ItemPanel;
import gui.reusable.PartyPanel;
import gui.world.WorldPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import player.Player;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import world.icons.Party;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.tiles.TileFactory;

/**
 * Party Dialog
 *
 * This is an interface for the user to interact with thier parties.
 * This will open when a user either presses a party in the Party/Item/Selection Panel below,
 * or if they double click on a party in the world screen.
 * TODO: enable party button and double click to open this menu
 *
 * See drawing below for simple visualisation of intended view.
 * https://drive.google.com/open?id=0B8a8yU2Qs_meZExBRXN0NVpUb0U&authuser=0
 *
 * What you can do in this panel if you own the party...
 * 		-Party Management, including ordering units for battle
 * 		-Apply items aquirred in the world to party and/or hero
 * 		-Visually understand the current status of this party in regards to hp, armor etc
 *
 * What you can do in this panel if it is an enemy party
 * 		-A visual representation of the layout of the party and what units it is composed of
 * 		-Nothing more I don't think.
 *
 *
 * @author myles, Ewan Moshi
 *
 */
public class PartyDialog extends JDialog  {

	protected JFrame frame;

	//is the panel owned by this player?
	private boolean isOwner;

	// For description label @ top of dialog
	private JPanel descriptionPanel;

	// The different sections that make up the Dialog.
	private SelectedItemPanel selectedItemPanel;
	private UnitsPanel unitsPanel;
	private PartyItemsPanel partyItemsPanel;
	private HeroItemsPanel heroItemsPanel;

	//Constants for sub components
	public static final int PADDING = 20; // concrete value to minimise swing playing up
	public static final int DESCRIPTION_HEIGHT = 80;
	public static final int COMPONENT_HEIGHT = (int)((Constants.PARTY_PANEL_HEIGHT- DESCRIPTION_HEIGHT));
	public static final int COMPONENT_WIDTH = (int)((Constants.PARTY_PANEL_WIDTH)/ 3); // this is a third of the dialog width - margins

	private Dimension panelDimension = new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);

	private Tile tile; //Tile currently selected on world screen
	private Party party; // Party on above tile.

	/**
	 * Constructs an extended JDialog, PartyDialog.
	 *
	 * @param frame : MainFrame that game is using.
	 * @param tile : Tile object currently selected by user on world screen
	 * @param boolean : is player the owner of this party?
	 */
	public PartyDialog (JFrame frame, Tile tile, boolean isOwner) {
		super(frame,true);
		this.frame = frame;
		this.tile = tile;
		this.party = (Party) tile.occupant();
		this.isOwner = isOwner;
        /*Create a new wrapper JPanel and set this dialog's content pane to this JPanel*/
        JPanel wrapperPanel = new JPanel() {
        	BufferedImage backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "dialogBackground.png");

      	  @Override
    	  protected void paintComponent(Graphics g) {
    	    super.paintComponent(g);
    	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    	  }
        };
        this.setContentPane(wrapperPanel);

		// Set Layout Manager
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		// Set size of panel to 80% width of world screen, and 100% of height
		this.setSize(Constants.PARTY_PANEL_WIDTH, Constants.PARTY_PANEL_HEIGHT); //width, height
		this.setUndecorated(true); //removes the border
        this.setLocationRelativeTo(frame); //set location of dialog relative to frame

        // Initialise needed components

        // Description Label
        descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        initDescriptionPanel();
        descriptionPanel.setPreferredSize(new Dimension(100, PartyDialog.DESCRIPTION_HEIGHT));
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 5;
        gc.gridheight = 1;
        this.add(descriptionPanel, gc);

        // Close Button
		/*Declare and initialize the images for the buttons */
		BufferedImage closeTownDefault = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButton.png");
		BufferedImage closeTownPressed = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonClicked.png");
		BufferedImage closeTownHover = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonHover.png");
		CustomButton button_leave = new CustomButton(closeTownDefault, closeTownPressed, closeTownHover);
		button_leave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeDialog();
			}
        });
        gc.gridx = 5;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        this.add(button_leave, gc);

        //Selected Panel

        selectedItemPanel = new SelectedItemPanel(this, panelDimension);
        selectedItemPanel.setOpaque(false);
        selectedItemPanel.setBackground(Color.LIGHT_GRAY);
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 2;
        gc.gridheight = 5;

        this.add(selectedItemPanel, gc);
        // Set selected to hero of party
        updateSelectedWithUnit(party.getHero());

        // Units Panel
        this.unitsPanel = new UnitsPanel(this, panelDimension);
        this.unitsPanel.setBackground(Color.RED);
        gc.gridx = 2;
        gc.gridy = 1;
        gc.gridwidth = 2;
        gc.gridheight = 5;
        this.add(unitsPanel, gc);

        // Party Items Panel
        this.partyItemsPanel = new PartyItemsPanel(this, new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT / 2));
        partyItemsPanel.setBackground(Color.BLUE);
        gc.gridx = 4;
        gc.gridy = 1;
        gc.gridwidth = 2;
        gc.gridheight = 3;
        this.add(partyItemsPanel, gc);
        // god i hate swing

       	// Hero Items Panel
        /**
         * adds a panel to display items equipped to hero 
       	HeroEquipLayeredPane heroLayer = new HeroEquipLayeredPane(this);
       	gc.gridx = 4;
       	gc.gridy = 4;
       	gc.gridwidth = 2;
       	gc.gridheight = 2;
       	this.add(heroLayer, gc);
     	*/

        this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Construct elements for a new JPanel that will instruct the user on what the dialog does.
	 */
	private void initDescriptionPanel() {

		// make sure myles isn't an idiot
		if(this.descriptionPanel == null) {
			throw new RuntimeException("DescriptionPanel not instantiated! What? How'd you even get this?");
		}

		// set and add add title type
		JLabel title = new JLabel(Constants.PartyPanelTitle);

		title.setHorizontalAlignment(JLabel.LEFT);
		title.setFont(Constants.HeaderFont);
		title.setForeground(new Color(225,200,55));
		this.descriptionPanel.add(title);

		// set and add instruction type
		JLabel description = new JLabel(Constants.PartyPanelDescription);
		description.setHorizontalAlignment(JLabel.LEFT);
		description.setFont(Constants.ParagraphFont);
		description.setForeground(new Color(225,179,55));
		this.descriptionPanel.add(description);


	}

	/**
	 * Updates the selectedPanel with selected item
	 */
	protected void updateSelectedWithItem(Item item) {
		//System.out.println("[Party Dialog]Updating Selected Item");
		this.selectedItemPanel.setSelectedItem(item);
	}

	/**
	 * Updates the selectedPanel with selected unit
	 */
	protected void updateSelectedWithUnit(Creature unit) {
		//System.out.println("[Party Dialog]Updating Selected Unit");
		this.selectedItemPanel.setSelectedUnit(unit);
	}


	/**
	 * Close Dialog
	 * I'm pretty sure I'm supposed to use dispose().
	 */
	private void closeDialog() {
		//Log.print("[PartyDialog] Closing party dialog");
		this.dispose();
	}

	/**
	 * Set that shit, not sure if this would be needed
	 **/
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * Returns true if owner of party
	 */
	public boolean isOwner(){
		return this.isOwner;
	}

	/** @return the current party of dialog **/
	public Party getParty() {
		return this.party;
	}

}
