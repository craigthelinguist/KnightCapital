package world.tiles;

import tools.Constants;
import tools.ImageLoader;
import world.icons.Party;

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
		t.image = ImageLoader.load(Constants.ASSETS + "tile_dirt.png");
		t.portrait = ImageLoader.load(Constants.PORTRAITS + "grass.png");
		return t;
	}

	public static PassableTile newGrassTile(int x, int y){
		PassableTile t = new PassableTile(x,y);
		t.image = ImageLoader.load(Constants.ASSETS + "tile_grass.png");
		t.portrait = ImageLoader.load(Constants.PORTRAITS + "grass.png");
		return t;
	}

	@Override
	public boolean passable(Party party) {
		return !occupied();
	}


}
