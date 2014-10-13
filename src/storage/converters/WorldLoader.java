package storage.converters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	

	public static World loadWorld(String filepath){
		
		File file = new File(filepath);
		XStream stream = new XStream();
		stream.registerConverter(new WorldConverter());
		stream.alias("world", World.class);
		
		
		return null;
	}
	
	protected static World constructWorld(){
		return null;
	}

	protected static boolean validateWorld(){
		return true;
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
