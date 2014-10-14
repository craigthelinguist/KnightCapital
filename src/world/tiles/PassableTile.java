package world.tiles;

import java.io.File;

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
		// Generate a random number to get variation in the types of grass that are displayed, to minimise the 'tiling' effect seen when using the same texture;
		int random = 1 + (int)(Math.random() * 3);
		return new PassableTile("grass_"+random,x,y);
		//return new PassableTile("grass_1", x, y);
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
