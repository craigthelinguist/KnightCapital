package tools;

import java.io.File;

/**
 * This class contains game-wide global constants. It is used so they're all in one place.
 */
public class GlobalConstants {

	// GlobalConstants cannot be instantiated
	private GlobalConstants(){}

	// all images are stored in this relative filepath
	public static final String ASSETS = "assets" + File.separatorChar;
	// all images inside the GUIAssets 
	public static final String GUI_FILEPATH = GlobalConstants.ASSETS + "GUIAssets" + File.separatorChar;
	
	// these are temporary values for WorldRenderer prototype - Aaron
	public static final int TILE_WD = 131;
	public static final int TILE_HT = 77;
	public static final int ICON_WD = 44;
	public static final int ICON_HT = 70;
	
	
}
