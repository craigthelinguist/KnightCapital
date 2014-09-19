package world;

import tools.GlobalConstants;

/**
 * The world is the part of the game where the players click around the map, and move their parties.
 * It is a collection of tiles and info about them (the cities, heroes, units, items populating those
 * tiles).
 */
public class World {

	// assumes the world is rectangular
	private AbstractTile[][] tiles;
	public final int NUM_TILES_ACROSS;
	public final int NUM_TILES_DOWN;
	public final int WORLD_WD;
	public final int WORLD_HT;
	
	public World(AbstractTile[][] tiles_){
		tiles = tiles_;
		NUM_TILES_ACROSS = tiles.length;
		NUM_TILES_DOWN = tiles[0].length;
		WORLD_WD = NUM_TILES_ACROSS*(GlobalConstants.TILE_WD/2);
		WORLD_HT = NUM_TILES_DOWN*(GlobalConstants.TILE_HT/2);
	}
	
	public AbstractTile[][] getTiles(){
		return tiles;
	}
	
	public void setIcon(WorldIcon i, int x, int y){
		tiles[x][y].setIcon(i);
	}
	
}
