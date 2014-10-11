package world.tiles;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tools.Constants;
import tools.ImageLoader;
import world.icons.ItemIcon;
import world.icons.Party;

/**
 * A PassableTile is something a Creature can stand on.
 * @author: craigthelinguist
 */
@XStreamAlias("PassableTile")
public class PassableTile extends Tile{


	// instantiate using static factory methods - avoids typos and other things
	private PassableTile(int x, int y){
		super(x,y);
	}

	public static PassableTile newDirtTile(int x, int y){
		PassableTile t = new PassableTile(x,y);
		t.addImage(Constants.ASSETS + "tile_dirt.png",ImageLoader.load(Constants.ASSETS + "tile_dirt.png"));
		t.addPortrait(Constants.PORTRAITS + "tile_grass.png", ImageLoader.load(Constants.PORTRAITS + "grass.png"));
		return t;
	}

	public static PassableTile newGrassTile(int x, int y){
		PassableTile t = new PassableTile(x,y);
		t.addImage(Constants.ASSETS + "tile_grass.png",ImageLoader.load(Constants.ASSETS + "tile_grass.png"));
		t.addPortrait(Constants.PORTRAITS + "grass.png",ImageLoader.load(Constants.PORTRAITS + "grass.png"));
		return t;
	}

	@Override
	public boolean passable(Party party) {
		return !occupied() || this.occupant() instanceof ItemIcon;
	}



}
