package storage.converters;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import player.Player;
import tools.Constants;
import world.World;
import world.icons.Party;
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

		// get data for writing
		World world = (World)object;
		Set<? extends City> cities = world.getCities();
		Tile[][] tiles = world.getTiles();
		Player[] players = world.getPlayers();

		// write players to file
		writer.startNode("players");
			for (Player player : players){
				writer.startNode("player");
					writer.startNode("slot");
						writer.setValue("" + player.slot);
					writer.endNode();
					writer.startNode("name");
						writer.setValue("" + player.name);
					writer.endNode();
				writer.endNode();
			}
		writer.endNode();

		// write cities
		writer.startNode("cities");
			for (City city : cities){
				writer.startNode("city");
					writer.startNode("name");
						writer.setValue(city.getName());
					writer.endNode();
					writer.startNode("imageName");
						writer.setValue(city.getName());
					writer.endNode();
					writer.startNode("player");
						writer.setValue(""+city.getOwner().slot);
					writer.endNode();
				writer.endNode();
			}
		writer.endNode();

		// write tiles
		writer.startNode("tiles");
			writer.startNode("width");
				writer.setValue("" + world.NUM_TILES_ACROSS);
			writer.endNode();
			writer.startNode("height");
				writer.setValue("" + world.NUM_TILES_DOWN);
			writer.endNode();

			TileConverter tc = new TileConverter();
			for (int x = 0; x < world.NUM_TILES_ACROSS; x++){
				for (int y = 0; y < world.NUM_TILES_DOWN; y++){
					writer.startNode("tile");
						if (tiles[x][y].occupant() != null && tiles[x][y].occupant() instanceof Party){
							System.out.println("stop");
						}
						tc.marshal(tiles[x][y], writer, context);
					writer.endNode();
				}
			}
		writer.endNode();

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

			reader.moveDown();
				int width = Integer.parseInt(reader.getValue());
			reader.moveUp();
			reader.moveDown();
				int height = Integer.parseInt(reader.getValue());
			reader.moveUp();
			WorldLoader.newTileArray(width,height);

			// load tiles
			TileConverter tc = new TileConverter();
				while (reader.hasMoreChildren()){
					reader.moveDown();
						Tile tile = (Tile)tc.unmarshal(reader, context);
						WorldLoader.addTile(tile);
						reader.moveUp();
				}

		reader.moveUp();

		// reconstruct the world
		return WorldLoader.constructWorld();

	}

}
