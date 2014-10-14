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

	public PassableTile(String imageName, int x, int y){
		super(imageName,x,y);
	}

	public static PassableTile newDirtTile(int x, int y){
		return new PassableTile("dirt",x,y);
	}

	public static PassableTile newGrassTile(int x, int y){
		return new PassableTile("grass",x,y);
	}

	public static PassableTile newTreeTile(int x, int y){
		return new PassableTile("tree", x, y);
	}

	@Override
	public boolean passable(Party party) {
		return !occupied() || this.occupant() instanceof ItemIcon;
	}

	@Override
	public String asString() {
		return "passable";
	}


}
