package world;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * A PassableTile is something a Creature can stand on.
 * @author: craigthelinguist
 */
public class PassableTile extends Tile{

	// instantiate using static facmethods - avoids typos and other things
	private PassableTile(){}
	
	public static PassableTile newDirtTile(){
		PassableTile t = new PassableTile();
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_dirt.png");
		return t;
	}
	
	public static PassableTile newGrassTile(){
		PassableTile t = new PassableTile();
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_grass.png");
		return t;
	}

	@Override
	public boolean passable(Party party) {	
		return occupied();
	}
	
	
}
