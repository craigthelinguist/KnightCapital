package world;

/**
 * The world is the part of the game where the players click around the map, and move their parties.
 * It is a collection of tiles and info about them (the cities, heroes, units, items populating those
 * tiles).
 */
public class World {

	// for now I will represent this with a 2d array.
	// We may wish to move to something like a QuadMap if time and space permits.
	private AbstractTile[][] tiles;
	public final int width;
	public final int height;
	
	public World(AbstractTile[][] tiles_){
		tiles = tiles_;
		width = tiles.length;
		height = tiles[0].length;
	}
	
	public AbstractTile[][] getTiles(){
		return tiles;
	}
	
}
