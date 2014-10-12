package storage.converters;

import javax.swing.Icon;

import storage.loaders.DataLoader;

import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.ImpassableTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TileConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == Tile.class || clazz == PassableTile.class || clazz == ImpassableTile.class || clazz == CityTile.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (object instanceof PassableTile) marshal_passable(object,writer,context);
		else if (object instanceof ImpassableTile) marshal_passable(object,writer,context);
		else if (object instanceof CityTile) marshal_passable (object,writer,context);
		else throw new RuntimeException("trying to marshal some unknown kind of tile");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		reader.moveDown();
			String type = reader.getValue();
		reader.moveUp();
		
		if (type.equals("passable")) return unmarshal_passable(reader,context);
		else if (type.equals("impassable")) return unmarshal_impassable(reader,context);
		else if (type.equals("city")) return unmarshal_city(reader,context);
		else{
			throw new RuntimeException("idk how to parse this type of tile: " + type);
		}
		
	}
	
	private void marshal_passable(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		PassableTile pt = (PassableTile)object;
		
		writer.startNode("type");
		writer.setValue(pt.asString());
		writer.endNode();
		writer.startNode("x");
		writer.setValue(""+pt.X);
		writer.endNode();
		writer.startNode("y");
		writer.setValue(""+pt.Y);
		writer.endNode();
		writer.startNode("icon");
		new IconConverter().marshal(pt.occupant(), writer, context);
		writer.endNode();
		
	}

	public Object unmarshal_passable(HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			String imageName = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			int x = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int y = Integer.parseInt(reader.getValue());
		reader.moveUp();
		WorldIcon icon = (WorldIcon)new IconConverter().unmarshal(reader, context);
		PassableTile tile = new PassableTile(imageName,x,y);
		tile.setIcon(icon);
		return tile;
	}
	
	private void marshal_impassable(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		ImpassableTile it = (ImpassableTile)object;
		writer.startNode("type");
		writer.setValue(it.asString());
		writer.endNode();
		writer.startNode("x");
		writer.setValue(""+it.X);
		writer.endNode();
		writer.startNode("y");
		writer.setValue(""+it.Y);
		writer.endNode();
	}
	
	public Object unmarshal_impassable(HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			String imageName = reader.getValue();
		reader.moveUp();
		reader.moveDown();
		int x = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int y = Integer.parseInt(reader.getValue());
		reader.moveUp();
		return new ImpassableTile(imageName,x,y);
	}
	
	private void marshal_city(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		CityTile ct = (CityTile)object;
		writer.startNode("x");
		writer.setValue(""+ct.X);
		writer.endNode();
		writer.startNode("y");
		writer.setValue(""+ct.Y);
		writer.endNode();
		writer.startNode("city");
		writer.setValue(ct.getCity().getName());
		writer.endNode();
	}
	
	public Object unmarshal_city(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		reader.moveDown();
			int x = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int y = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			String cityName = reader.getValue();
			City city = DataLoader.cities.get(cityName);
			if (city == null){
				throw new RuntimeException("Error! You're setting a city tile's city to be null. City name was " + cityName);
			}
		reader.moveUp();
		
		CityTile ct = new CityTile(x,y);
		ct.setCity(city);
		return new CityTile(x,y);
		
	}
	
}
