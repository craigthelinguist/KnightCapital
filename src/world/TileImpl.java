package world;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This is the class I"m using for my WorldRenderer prototype.
 * @author: Aaron
 */
public class TileImpl extends AbstractTile{

	// instantiate using static methods - avoids typos and other things
	private TileImpl(){}
	
	public static TileImpl newDirtTile(){
		TileImpl t = new TileImpl();
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_dirt.png");
		return t;
	}
	
	public static TileImpl newGrassTile(){
		TileImpl t = new TileImpl();
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_grass.png");
		return t;
	}
	
	
}
