package world;

import tools.ImageLoader;

/**
 * This is the class I"m using for my WorldRenderer prototype.
 * @author: Aaron
 */
public class TileImpl extends AbstractTile{

	public static TileImpl newDirtTile(){
		TileImpl t = new TileImpl();
		t.image = ImageLoader.load("tile_dirt.png");
		return t;
	}
	
	public static TileImpl newGrassTile(){
		TileImpl t = new TileImpl();
		t.image = ImageLoader.load("tile_grass.png");
		return t;
	}
	
}
