package GUI.PartyDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.Log;
import world.icons.Party;
import world.tiles.Tile;
import GUI.MainFrame;

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
 * @author myles
 *
 */
public class PartyDialog extends JDialog  {

	protected MainFrame frame;
	
	// For description label @ top of dialog
	private JPanel descriptionPanel;
	
	// The different sections that make up the Dialog.
	private SelectedItemPanel selectedItemPanel;
	private UnitsPanel unitPanel;
	private PartyItemsPanel partyItemsPanel;
	private HeroItemsPanel heroItemsPanel;

	private Tile tile; //Tile currently selected on world screen
	private Party party; // Party on above tile.
	/**
	 * Constructs an extended JDialog, PartyDialog.
	 * 
	 * @param frame : MainFrame that game is using.
	 * @param tile : Tile object currently selected by user on world screen
	 */
	public PartyDialog (MainFrame frame, Tile tile) {
		
		super(frame,true);
		this.frame = frame;
		
		this.tile = tile;
		
		// Set size of panel to 80% width of world screen, and 100% of height
		this.setSize((int)(GlobalConstants.WINDOW_WD * 0.8), (int)(GlobalConstants.WINDOW_HT *0.7)); //width, height
		this.setUndecorated(true); //removes the border
        this.setLocationRelativeTo(frame); //set location of dialog relative to frame
        
        
        
		
        // Initialise needed components
        // description label is used to tell the user what this dialog is, and how it is used.
        this.descriptionPanel = new JPanel();
        initDescriptionPanel();
        
        this.add(descriptionPanel);
        
        this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Construct elements for a new JPanel that will instruct the user on what the
	 * fuck this dialog does.
	 */
	private void initDescriptionPanel() {
		// make sure myles isn't an idiot
		if(this.descriptionPanel == null) {
			throw new RuntimeException("DescriptionPanel not instantiated! What? How'd you even get this?");
		}
		
		// set and add add title type
		JLabel title = new JLabel(GlobalConstants.PartyPanelTitle);
		title.setFont(GlobalConstants.HeaderFont);
		this.descriptionPanel.add(title, BorderLayout.WEST);
		
		// set and add instruction type
		JLabel description = new JLabel(GlobalConstants.PartyPanelDescription);
		description.setFont(GlobalConstants.SubheaderFont);
		this.descriptionPanel.add(description, BorderLayout.CENTER);
		
		// add escape button for shits and giggs
        JButton exitButton = new JButton("Close");
        
        exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeDialog();
			}
        });
        
        this.descriptionPanel.add(exitButton, BorderLayout.EAST);
	}
	
	private void closeDialog() {
		Log.print("[PartyDialog] Closing party dialog");
		this.setVisible(false);
	}
	
	/** Set that shit, not sure if this would be needed **/
	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
