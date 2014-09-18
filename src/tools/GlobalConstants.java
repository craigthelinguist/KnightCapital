package tools;

import java.io.File;

/**
 * This class contains game-wide global constants. It is used so they're all in one place.
 */
public class GlobalConstants {

	private GlobalConstants(){} // don't instantiate this!
	
	public static final String ASSETS = "assets" + File.separatorChar;
	
	// these are temporary values - Aaron
	public static final int TILE_WD = 131;
	public static final int TILE_HT = 77;
	
}
