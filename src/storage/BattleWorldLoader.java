package storage;

import world.World;
import world.tiles.PassableTile;
import world.tiles.Tile;

public class BattleWorldLoader {

	/**
	 * Loads a flat grass world, perfect for pvp combat
	 */

	private BattleWorldLoader(){} //#static

	public static World loadWorld(int battleWidth, int battleHeight) {
		Tile[][] tiles = null;

		for(int x = 0; x < battleWidth; x++) {
			for(int y = 0; y < battleHeight; y++) {
				tiles[x][y] = PassableTile.newGrassTile(x, y);
			}
		}

		return new World(tiles, null, null);
	}
}
