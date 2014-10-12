package tools;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import player.Player;

/**
 * This class contains game-wide global constants. It is used so they're all in one place.
 */
public class Constants {

	// GlobalConstants cannot be instantiated
	private Constants(){}

	// Window Resolution
	public static final int WINDOW_WD = 1300;
	public static final int WINDOW_HT = 800;

	
	// all images are stored in this relative filepath
	public static final String ASSETS = "assets" + File.separatorChar;
	public static final String ICONS = ASSETS + "icons" + File.separatorChar;
	public static final String CITIES = Constants.ASSETS + "cities" + File.separatorChar;
	public static final String PORTRAITS = "assets" + File.separatorChar+ "portraits" +   File.separatorChar;
	public static final String ITEMS = Constants.ASSETS + "itemIcons" + File.separatorChar;


	//all xml tests are stored in this relative file path
	public static final String XMLTESTS = Constants.ASSETS + "testXML" + File.separatorChar;
	
	// all images inside the GUIAssets
	public static final String GUI_FILEPATH = Constants.ASSETS + "GUIAssets" + File.separatorChar;
	public static final String GUI_BUTTONS = Constants.ASSETS + "GUIAssets" + File.separatorChar +"buttons"+ File.separatorChar;
	public static final String GUI_TOWN = Constants.ASSETS + "gui_town" + File.separatorChar;
	public static final String GUI_TOWN_BUTTONS = Constants.ASSETS + "gui_town" + File.separatorChar + "buttons" + File.separatorChar;

	// these are temporary values for WorldRenderer prototype - Aaron
	public static final int TILE_WD = 131;
	public static final int TILE_HT = 77;
	public static final int WORLD_WIDTH = 10;
	public static final int WORLD_HEIGHT = 10;

	// image sizing
	public static final Dimension PORTRAIT_DIMENSIONS = new Dimension(66,100);
	public static final Dimension INVENTORY_DIMENSIONS = new Dimension(102,75);

	// These are for the Camera
	public static final int CAMERA_PAN = 10;

	// Font's for GUI
	public static final Font HeaderFont = new Font("Dialog", Font.BOLD, 30);
	public static final Font SubheaderFont = new Font("Dialog", Font.ITALIC, 24);
	public static final Font ParagraphFont = new Font("Dialog", Font.PLAIN, 14);
	public static final Font TooltipFont = new Font("DialogInput", Font.PLAIN, 10);

	// Values needed for party panel i'm guessing these will be stored somewhere else eventually
	public static final int PARTY_PANEL_WIDTH = (int) (WINDOW_WD * 0.8);
	public static final int PARTY_PANEL_HEIGHT = (int) (WINDOW_HT * 0.8);

	public static final String PartyPanelTitle = "Party Panel";
	public static final String PartyPanelDescription = "This is a motherfucking description telling you what the fuck this is and what the fuck it does. Got it?";

	//
	public static final String DATA_STATS = "data" + File.separatorChar + "stats" + File.separatorChar;
	public static final String DATA_ITEMS = "data" + File.separatorChar + "items" + File.separatorChar;
	
}
