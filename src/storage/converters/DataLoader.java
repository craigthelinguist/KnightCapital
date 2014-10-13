package storage.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Player;
import world.tiles.CityTile;
import world.towns.City;

public class DataLoader {

	private DataLoader(){}
	
	protected static void reset() {
		
		players = new HashMap<>();
		cities = new HashMap<>();
		
	}

	protected static Player getPlayer(int slot){
		return players.get(slot);
	}
	
	protected static void insertPlayer(int slot, Player player){
		players.put(slot,player);
	}
	
	protected static void insertCity(String name, City city){
		incompleteCities.put(name,city);
		cities.put(name, new ArrayList<CityTile>());
	}

	protected static int numberOfPlayers() {
		return players.size();
	}

	protected static boolean addCityTile(CityTile ct, String cityname) {
		if (!cities.containsKey(cityname)) return false;
		else{
			cities.get(cityname).add(ct);
			return true;
		}
	}
	
	public static World loadWorld(String filepath){
		
		return null;
	}
	
}
