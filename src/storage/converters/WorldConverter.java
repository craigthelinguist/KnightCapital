package storage.converters;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Player;

import tools.Constants;
import world.World;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class WorldConverter implements Converter{

	

	// keeps track of all players in the world that you are loading.
	private Map<Integer,Player> players = new HashMap<Integer,Player>();
	
	// keeps track of city-names and the tiles that have been associatied
	// with that city while reading.
	private Map<String,List<CityTile>> cities = new HashMap<>();
	
	// keeps track of cities before they have been properly instantiated
	// with their cityTiles.
	private Map<String,City> incompleteCities = new HashMap<>();
	
	private Tile[][] tiles;
	
	private void reset(){
		players = new HashMap<>();
		cities = new HashMap<>();
		incompleteCities = new HashMap<>();
		tiles = null;
	}
	
	protected boolean doesCityExist(String name){
		return cities.containsKey(name);
	}
	

	protected void addCityTile(String cityName, CityTile ct) {
		this.cities.get(cityName).add(ct);
	}
	
	
	@Override
	public boolean canConvert(Class clazz) {
		return clazz == World.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		this.reset();
		
		// load players
		PlayerConverter pc = new PlayerConverter();
		reader.moveDown();
			while (reader.hasMoreChildren()){
				
				// load player
				reader.moveDown();
					Player player = (Player) pc.unmarshal(reader, context);
				reader.moveUp();

				// record player in data loader
				DataLoader.insertPlayer(player.slot, player);
				
			}
		reader.moveUp();
		if (DataLoader.numberOfPlayers() == 0){
			throw new RuntimeException("Creating a world with zero players!");
		}
		
		
		
		// load cities
		reader.moveDown();
			while (reader.hasMoreChildren()){
			
				// load each city
				reader.moveDown();
					reader.moveDown();
						String cityname = reader.getValue();
					reader.moveUp();
					reader.moveDown();
						String imgName = reader.getValue();
					reader.moveUp();
					reader.moveDown();
						Player player = (Player) pc.unmarshal(reader, context);
					reader.moveUp();
				reader.moveUp();
				
			}
		reader.moveUp();
		
		
		
		
		// load tile dimensions
		reader.moveDown();
			int width = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int height = Integer.parseInt(reader.getValue());
		reader.moveUp();
		this.tiles = new Tile[width][height];
		
		// load tiles
		TileConverter tc = new TileConverter(this);
		reader.moveDown();
			while (reader.hasMoreChildren()){
				
				// load each tile
				Tile tile = (Tile)tc.unmarshal(reader, context);
				tiles[tile.X][tile.Y] = tile;
				
			}
		reader.moveUp();
		
		
		
		// reconstruct the world
		
		return null;
		
	}

	public static void main(String[] args){
		
		String filepath = Constants.DATA_WORLDS + "world.xml";
		File file = new File(filepath);
		XStream stream = new XStream();
		stream.alias("world", World.class);
		stream.registerConverter(new WorldConverter());
		stream.fromXML(file);
		
	}

}
