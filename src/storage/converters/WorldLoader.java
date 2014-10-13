package storage.converters;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

import player.Player;
import world.World;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

public class WorldLoader {

	private WorldLoader(){}
	
	private static Map<String,City> incompleteCities = new HashMap<>();
	private static Map<String,List<CityTile>> cityTiles = new HashMap<>();
	private static Map<Integer,Player> players = new HashMap<>();
	private static Tile[][] tiles;
	

	/**
	 * Load the world described in the specified file.
	 * @param filepath: location of the file containing the world you want to read.
	 * @return: a World.
	 */
	public static World loadWorld(String filepath) throws IOException{
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
			playerArray[slot] = player;
		}
		
		// create and return the world
		return new World(tiles,playerArray,cities);
		
	}
	
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
	
}
