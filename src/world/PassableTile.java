package world;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * A PassableTile is something a Creature can stand on.
 * @author: craigthelinguist
 */
public class PassableTile extends Tile{

	// instantiate using static factory methods - avoids typos and other things
	private PassableTile(int x, int y){
		super(x,y);
	}
	
	public static PassableTile newDirtTile(int x, int y){
		PassableTile t = new PassableTile(x,y);
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_dirt.png");
		return t;
	}
	
	public static PassableTile newGrassTile(int x, int y){
		PassableTile t = new PassableTile(x,y);
		t.image = ImageLoader.load(GlobalConstants.ASSETS + "tile_grass.png");
		return t;
	}

	@Override
	public boolean passable(Party party) {	
		return !occupied();
	}
	
	
}
