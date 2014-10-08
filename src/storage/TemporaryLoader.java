package storage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import player.Player;

import tools.GlobalConstants;
import world.World;
import world.tiles.CityTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;

/**
 * I'm just using this to load my tiles/other stuff until the real stuff comes along. You can safely
 * ignore this class and it will probably be deleted before the end.
 * @author craigthelinguist
 */
public class TemporaryLoader {

	// use the static methods
	private TemporaryLoader(){}

	// the different kinds of tiles
	private static final char GRASS_TILE = 'G';
	private static final char DIRT_TILE = 'D';

	/**
	 * Loads, creates, and returns the specified World.
	 * @param p
	 * @param filename: name of file containing the World.
	 * @return an instance of the World described
	 */
	public static World loadWorld(String filename, Player p){

		Tile[][] tiles = null;
		Scanner scan = null;
		try{

			scan = new Scanner(new File(GlobalConstants.ASSETS + filename));

			// skip over comments
			String regex = "\\s*#";
			Pattern comments = Pattern.compile(regex);
			while (scan.hasNext(comments)) scan.nextLine();

			// dimensions of world
			int across, down;
			try{
				across = scan.nextInt();
				down = scan.nextInt();
			}
			catch(NumberFormatException e){
				throw new IOException("Error parsing world dimensions");
			}
			scan.nextLine();

			// create tiles
			tiles = new Tile[across][down];
			for (int y = 0; y < down; y++){
				char[] line = scan.nextLine().toCharArray();
				for (int x = 0; x < across; x++){

					switch (line[x]){
						case GRASS_TILE:
							tiles[x][y] = PassableTile.newGrassTile(x,y);
							break;
						case DIRT_TILE:
							tiles[x][y] = PassableTile.newDirtTile(x,y);
							break;
						default:
							throw new IOException("Unknown symbol " + line[x] + " at ("+"x"+","+y+")");
					}

				}
			}

			// add a Player
			Player pl = new Player("Whiley Master",1);

			// add a city
			CityTile[][] cityTiles = new CityTile[City.WIDTH][City.WIDTH];

			for (int i=4, a=0; i <= 6; i++, a++){
				for (int j=4, b=0; j <= 6; j++, b++){
					CityTile ct = new CityTile(i,j);
					tiles[i][j] = ct;
					cityTiles[a][b] = ct;
				}
			}
			String[] cityAnimNames = new String[]{ "basic" };
			City city = new City(cityAnimNames, pl, cityTiles);

			// world data
			Player[] players = new Player[]{ p };
			Set<City> cities = new HashSet<>();
			cities.add(city);
			return new World(tiles, players, cities);

		}
		catch (IOException e){
			e.printStackTrace();
			System.exit(1);
			return null;
		}
		finally{
			if (scan != null) scan.close();
		}

	}

}
