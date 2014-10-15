package storage.converters;

import game.items.Item;
import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

import controllers.WorldController;
import player.Player;
import storage.generators.TemporaryLoader;
import tools.Constants;
import world.World;
import world.icons.ItemIcon;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;

/**
 * Used to load and save worlds.
 * @author Aaron the Craig
 */
public class WorldLoader {

	private WorldLoader(){}

	// stores data about the world being constructed
	private static Map<String,City> incompleteCities = new HashMap<>();
	private static Map<String,List<CityTile>> cityTiles = new HashMap<>();
	private static Map<Integer,Player> players = new HashMap<>();
	private static Tile[][] tiles;

	/**
	 * Load the world described in the specified file.
	 * @param filepath: location of the file containing the world you want to read.
	 * @return: the world specified in this file
	 * @throws IOException: if it failed to find the file or if there was an error constructing the world in the file.
	 */
	public static World load(String filepath) throws IOException{
		reset();
		File file = new File(filepath);
		XStream stream = new XStream();
		stream.registerConverter(new WorldConverter());
		stream.alias("world", World.class);
		World world = (World)stream.fromXML(file);
		validateWorld();
		reset();
		return world;
	}

	/**
	 * Save the world at the specified file.
	 * @param filepath: location where you want to save the file
	 * @param world: the world you want to save
	 * @throws IOException: if it failed to create the specified filepath
	 */
	public static void save(String filepath, World world) throws IOException{
		File file = new File(filepath);
		XStream stream = new XStream();
		stream.registerConverter(new WorldConverter());
		stream.alias("world", World.class);
		String xml = stream.toXML(world);
		PrintStream ps = new PrintStream(file);
		ps.print(xml);
		ps.close();
	}

	/**
	 * Reconstruct this world given the information in its maps.
	 * @return: a World object
	 */
	protected static World constructWorld(){

		// reconstruct the cities
		Set<City> cities = new HashSet<>();
		for (Map.Entry<String,List<CityTile>> entry : cityTiles.entrySet()){
			CityTile[][] cityTiles = as2Darray(entry.getKey(), entry.getValue());
			City incomplete = incompleteCities.get(entry.getKey());
			City city = new City(incomplete.getName(), incomplete.getImageName(), incomplete.getOwner(), cityTiles);
			cities.add(city);
		}

		// get the players
		Player[] playerArray = new Player[players.size()];
		for (Map.Entry<Integer,Player> pair : players.entrySet()){
			int slot = pair.getKey();
			Player player = pair.getValue();
			playerArray[slot-1] = player;
		}

		// create and return the world
		return new World(tiles,playerArray,cities);

	}

	/**
	 * Given a list of city tiles, return the corresponding CityTile[][] array.
	 * @param cityName: name of the city
	 * @param cityTiles: sequential list of city tiles
	 * @return
	 */
	private static CityTile[][] as2Darray(String cityName, List<CityTile> cityTiles){

		// quick check to make sure you have the right number of tiles
		int numtiles = City.WIDTH*City.WIDTH;
		if (cityTiles.size() != numtiles){
			throw new RuntimeException("The city " + cityName + " has " + cityTiles.size() + ", but it should have " + numtiles);
		}

		// sort tiles
		Comparator<CityTile> comp = new Comparator<CityTile>(){
			@Override
			public int compare(CityTile tile1, CityTile tile2) {
				if (tile1.Y != tile2.Y) return tile1.Y - tile2.Y;
				else return tile1.X - tile2.X;
			}
		};
		Collections.sort(cityTiles,comp);

		// turn into 2d array
		CityTile[][] tiles = new CityTile[City.WIDTH][City.WIDTH];
		int col = 0;
		int row = 0;

		while (row < City.WIDTH){

			int index = row*City.WIDTH + col;
			CityTile tile = cityTiles.get(index);
			tiles[col][row] = tile;
			col++;

			if (col == City.WIDTH){
				col = 0;
				row++;
			}

		}

		return tiles;
	}

	protected static boolean validateWorld() throws IOException{

		// we might like to have this in order to verify a world is correct. Incomplete for now.
		if (1 == 1){
			return true;
		}
		else throw new IOException();

	}

	protected static void reset() {
		players = new HashMap<>();
		cityTiles = new HashMap<>();
		incompleteCities = new HashMap<>();
		tiles = null;
	}

	protected static Player getPlayer(int slot){
		return players.get(slot);
	}

	protected static void insertPlayer(int slot, Player player){
		players.put(slot,player);
	}

	protected static void insertCity(String name, City city){
		incompleteCities.put(name,city);
		cityTiles.put(name, new ArrayList<CityTile>());
	}

	protected static int numberOfPlayers() {
		return players.size();
	}

	protected static boolean addCityTile(CityTile ct, String cityname) {
		if (!cityTiles.containsKey(cityname)) return false;
		else{
			cityTiles.get(cityname).add(ct);
			return true;
		}
	}

	protected static boolean doesCityExist(String cityName) {
		return incompleteCities.containsKey(cityName);
	}

	protected static void newTileArray(int width, int height) {
		tiles = new Tile[width][height];
	}

	protected static void addTile(Tile tile) {
		int x = tile.X;
		int y = tile.Y;

		try{

			if (tiles[x][y] != null){
				throw new RuntimeException("While parsing file it added two tiles to the same x,y position. The position was: " + x + "," + y);
			}
			tiles[x][y] = tile;

		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new ArrayIndexOutOfBoundsException("while loading a world it tried to add a tile that was out of the bounds of the world: tile was at " + x + "," + y);
		}




	}

	public static World exampleWorld(){

		Player player1 = new Player("Defenders of Light", 1);
		Player player2 = new Player("Spawn of Gaben", 2);
		Player[] players = new Player[]{ player1, player2 };

		Tile[][] tiles = new Tile[20][20];
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 20; j++){
				tiles[i][j] = PassableTile.newGrassTile(i, j);
			}
		}

		Set<City> cities = new HashSet<>();
		//cities.add(city1); cities.add(city2);


		CityTile[][] ct1 = new CityTile[3][3];
		for (int i = 4, a = 0; i < 7; i++, a++){
			for (int j = 6, b = 0; j < 9; j++, b++){
				ct1[a][b] = new CityTile(i,j);
				tiles[i][j] = ct1[a][b];
			}
		}
		City city1 = new City("Porirua", "basic", player1, ct1);
		cities.add(city1);

		CityTile[][] ct2 = new CityTile[3][3];
		for (int i=6, a=0; i<9; i++, a++){
			for (int j = 12, b=0; j < 15; j++, b++){
				ct2[a][b] = new CityTile(i,j);
				tiles[i][j] = ct2[a][b];
			}
		}
		City city2 = new City("Tamaki", "basic", player2, ct2);
		cities.add(city2);

		Item item1 = ItemLoader.load("liontalisman.xml");
		ItemIcon icon1 = new ItemIcon(item1);
		tiles[12][2].setIcon(icon1);

		Unit u1 = UnitLoader.load("knight.xml",player1);
		Unit u2 = UnitLoader.load("knight.xml",player1);
		Unit u3 = UnitLoader.load("archer.xml",player1);
		Hero h1 = HeroLoader.load("ovelia.xml",player1);
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = u1;
		members[0][2] = u2;
		members[1][1] = u3;
		members[1][2] = h1;
		Party p1 = new Party(h1, player1, members);
		tiles[3][5].setIcon(p1);


		Unit u4 = UnitLoader.load("knight.xml",player1);
		Unit u5 = UnitLoader.load("knight.xml",player1);
		Unit u6 = UnitLoader.load("archer.xml",player1);
		Hero h2 = HeroLoader.load("gaben.xml",player1);
		Creature[][] members2 = Party.newEmptyPartyArray();
		members2[0][0] = u1;
		members2[0][2] = u2;
		members2[1][1] = u3;
		members2[1][2] = h2;
		Party p2 = new Party(h2, player2, members2);
		tiles[10][10].setIcon(p2);

		World world = new World(tiles,players,cities);
		return new World(tiles, players, cities);

	}

	public static void main(String[] args) throws IOException{
		World world = WorldLoader.load(Constants.DATA_WORLDS + "test_save.xml");
		new WorldController(world,world.getPlayers()[0]);
	}

}
