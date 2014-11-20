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

	/**
	 * Creates a new Tile that can be stood on by a creature
	 * @param image name, assume in correct directory
	 * @param x coordinate
	 * @param y coordinate
	 */
	public PassableTile(String imageName, int x, int y){
		super(imageName,x,y);
	}

	/**
	 * Can it be passed by a creature?
	 */
	@Override
	public boolean passable() {
		return !occupied() || this.occupant() instanceof ItemIcon;
	}

	/**
	 * Pass as a string
	 */
	@Override
	public String asString() {
		return "passable";
	}


}
