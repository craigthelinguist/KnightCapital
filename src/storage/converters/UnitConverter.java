package storage.converters;

import java.io.File;
import java.lang.reflect.Field;

import player.Player;
import tools.Constants;
import game.items.Item;
import game.units.AttackType;
import game.units.Creature;
import game.units.Stats;
import game.units.Unit;
import game.units.UnitStats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UnitConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == Unit.class;
	}

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {

		Unit unit = (Unit)obj;

		writer.startNode("name");
			writer.setValue(unit.getName());
		writer.endNode();

		writer.startNode("imageName");
			writer.setValue(unit.getImageName());
		writer.endNode();

		new PlayerConverter().marshal(unit.getOwner(), writer, context);

		writer.startNode("stats");
			UnitStatsConverter statsConverter = new UnitStatsConverter();
			Stats stats;
			try{
				//TODO
				Field f = Creature.class.getDeclaredField("stats");
				f.setAccessible(true);
				stats = (Stats) f.get(unit);
				statsConverter.marshal(stats,writer,context);
			}
			catch(Exception e){
				System.err.println("Error writing unit stats to file");
			}
		writer.endNode();

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		if (!reader.hasMoreChildren()){
			XStream stream = new XStream();
			stream.alias("unit", Unit.class);
			stream.registerConverter(new UnitConverter());
			String filename = reader.getValue();
			File file = new File(Constants.DATA_UNITS + filename);
			return (Unit)(stream.fromXML(file));
		}

		// load name
		reader.moveDown();
			String name = reader.getValue();
		reader.moveUp();

		// load images
		reader.moveDown();
			String imgName = reader.getValue();
		reader.moveUp();

		// player
		reader.moveDown();
			Player player = (Player) new PlayerConverter().unmarshal(reader,context);
		reader.moveUp();

		// load stats
		reader.moveDown();
			UnitStats stats = (UnitStats) new UnitStatsConverter().unmarshal(reader, context);
		reader.moveUp();

		return new Unit(name,imgName,player,stats);
	}

}
