package world.tiles;

import world.icons.Party;

/**
 * Creates a tile that cannot be stood on by a party in the world.
 * @author Aaron
 */
public class ImpassableTile extends Tile{

	/**
	 * Creates a new impassable tile
	 * @param image path of image
	 * @param x coordinate
	 * @param y coordinate
	 */
	public ImpassableTile(String imgName, int x, int y){
		super(imgName,x,y);
	}

	private ImpassableTile(int x, int y){
		super(x,y);
	}

	/**
	 * Impassable tile with nothing drawn on it.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new impassable tile
	 */
	public static ImpassableTile newVoidTile(int x, int y){
		return new ImpassableTile(x,y);
	}

	/**
	 * Constructs a new impassable tree tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new tree tile
	 */
	public static ImpassableTile newTreeTile(int x, int y){
		int random = 1 + (int)(Math.random() * 2);
		return new ImpassableTile("tree_"+random, x, y);
	}

	/**
	 * Constructs a new impassable bush tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new bush tile
	 */
	public static ImpassableTile newBushTile(int x, int y){
		int random = 1 + (int)(Math.random() * 2);
		return new ImpassableTile("bush_"+random, x, y);
	}

	/**
	 * Constructs a new impassable rock tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new rock tile
	 */
	public static ImpassableTile newRockTile(int x, int y){
		int random = 1 + (int)(Math.random() * 2);
		return new ImpassableTile("rock_"+random, x, y);
	}

	/** Identifying Passable **/
	@Override
	public boolean passable() {
		return false; //fuck your passability
	}

	/** Get string title of tile **/
	@Override
	public String asString() {
		return "impassable";
	}



}
