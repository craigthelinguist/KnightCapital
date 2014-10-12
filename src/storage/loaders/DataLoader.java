package storage.loaders;

import java.util.HashMap;
import java.util.Map;

import player.Player;
import world.towns.City;

public class DataLoader {

	private DataLoader(){}
	
	public static Map<Integer,Player> players = new HashMap<Integer,Player>();
	public static Map<String,City> cities = new HashMap<>();
	
}
