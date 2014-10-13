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
		
		// load players
		PlayerConverter pc = new PlayerConverter();
		reader.moveDown();
			while (reader.hasMoreChildren()){
				
				// load player
				reader.moveDown();
					Player player = (Player) pc.unmarshal(reader, context);
				reader.moveUp();

				// record player in data loader
				WorldLoader.insertPlayer(player.slot, player);
				
			}
		reader.moveUp();
		if (WorldLoader.numberOfPlayers() == 0){
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
		WorldLoader.newTileArray(width,height);
		
		// load tiles
		TileConverter tc = new TileConverter();
		reader.moveDown();
			while (reader.hasMoreChildren()){
				
				// load each tile
				Tile tile = (Tile)tc.unmarshal(reader, context);
				WorldLoader.addTile(tile);
				
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
