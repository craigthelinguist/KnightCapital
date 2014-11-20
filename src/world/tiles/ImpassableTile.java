package world.tiles;

import world.icons.Party;

/**
 * Creates a tile that cannot be stood on by a party in the world.
 * @author Aaron, myles
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
	 * Returns a void tile; an immutable kind of tile that contains nothing and has no image.
	 * @param x: x position in 2d array
	 * @param y: y position in 2d array
	 * @return: ImpassableTile
	 */
	public static ImpassableTile newVoidTile(int x, int y){
		return new ImpassableTile(x,y);
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
