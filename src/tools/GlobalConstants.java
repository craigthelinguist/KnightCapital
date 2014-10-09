package tools;

import java.awt.Font;
import java.io.File;

import player.Player;

/**
 * This class contains game-wide global constants. It is used so they're all in one place.
 */
public class GlobalConstants {

	// GlobalConstants cannot be instantiated
	private GlobalConstants(){}

	// Window Resolution
	public static final int WINDOW_WD = 1300;
	public static final int WINDOW_HT = 800;

	// all images are stored in this relative filepath
	public static final String ASSETS = "assets" + File.separatorChar;
	public static final String ICONS = ASSETS + "icons" + File.separatorChar;
	public static final String CITIES = GlobalConstants.ASSETS + "cities" + File.separatorChar;
	public static final String PORTRAITS = "assets" + File.separatorChar+ "portraits" +   File.separatorChar;
	public static final String ITEMS = GlobalConstants.ASSETS + "items" + File.separatorChar;

	// all images inside the GUIAssets
	public static final String GUI_FILEPATH = GlobalConstants.ASSETS + "GUIAssets" + File.separatorChar;
	public static final String GUI_BUTTONS = GlobalConstants.ASSETS + "GUIAssets" + File.separatorChar +"buttons"+ File.separatorChar;


	// these are temporary values for WorldRenderer prototype - Aaron
	public static final int TILE_WD = 131;
	public static final int TILE_HT = 77;
	public static final int ICON_WD = 44;
	public static final int ICON_HT = 70;
	public static final int WORLD_WIDTH = 10;
	public static final int WORLD_HEIGHT = 10;

	// These are for the Camera
	public static final int CAMERA_PAN = 10;
	
	// Font's for GUI
	public static final Font HeaderFont = new Font("Dialog", Font.BOLD, 30);
	public static final Font SubheaderFont = new Font("Dialog", Font.ITALIC, 24);
	public static final Font ParagraphFont = new Font("Dialog", Font.PLAIN, 14);
	public static final Font TooltipFont = new Font("DialogInput", Font.PLAIN, 10);

	// Values needed for party panel i'm guessing these will be stored somewhere else eventually
	public static final String PartyPanelTitle = "Party Panel";
	public static final String PartyPanelDescription = "This is a motherfucking description telling you what the fuck this is and what the fuck it does. Got it?";
	
}
