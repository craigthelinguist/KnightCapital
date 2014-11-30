package world.tiles;

import world.icons.DecorIcon;

public class TileFactory {

	private TileFactory(){}

	/**
	 * Contructs a new dirt tile
	 * @param x coordinate
	 * @param y coordinate
	 * @return new dirt tile
	 */
	public static PassableTile newDirtTile(int x, int y){
		return new PassableTile("dirt",x,y);
	}

	/**
	 * Contructs a new grass tile
	 * @param x coordinate
	 * @param y coordinate
	 * @return new grass tile
	 */
	public static PassableTile newGrassTile(int x, int y){

		// Generate a random number to get variation in the types of grass that are displayed, to minimise the 'tiling' effect seen when using the same texture;
		int random = 1 + (int)(Math.random() * 3);
		return new PassableTile("grass_"+random,x,y);

	}

	/**
	 * Impassable tile with nothing drawn on it.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new impassable tile
	 */
	public static ImpassableTile newVoidTile(int x, int y){
		return ImpassableTile.newVoidTile(x, y);
	}

	/**
	 * Constructs a new impassable tree tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new tree tile
	 */
	public static ImpassableTile newTreeTile(int x, int y){
		DecorIcon di = new DecorIcon(DecorIcon.TREE);
		int random = 1 + (int)(Math.random() * 3);
		ImpassableTile it = new ImpassableTile("grass_"+random,x,y);
		it.setIcon(di);
		return it;
	}

	/**
	 * Constructs a new impassable bush tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new bush tile
	 */
	public static ImpassableTile newBushTile(int x, int y){
		DecorIcon di = new DecorIcon(DecorIcon.BUSH);
		int random = 1 + (int)(Math.random() * 2);
		ImpassableTile it = new ImpassableTile("grass_"+random,x,y);
		it.setIcon(di);
		return it;
	}

	/**
	 * Constructs a new impassable rock tile.
	 * @param x coordinate
	 * @param y coordinate
	 * @return new rock tile
	 */
	public static ImpassableTile newRockTile(int x, int y){
		DecorIcon di = new DecorIcon(DecorIcon.ROCK);
		int random = 1 + (int)(Math.random() * 2);
		ImpassableTile it = new ImpassableTile("grass_"+random,x,y);
		it.setIcon(di);
		return it;
	}
	
	
}
