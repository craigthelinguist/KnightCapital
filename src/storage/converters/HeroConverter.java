package storage.converters;

import java.io.File;
import java.lang.reflect.Field;

import player.Player;
import tools.Constants;
import game.items.Item;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Stats;
import game.units.Unit;
import game.units.UnitStats;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HeroConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == Hero.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {

		Hero hero = (Hero)object;
		writer.startNode("name");
			writer.setValue(hero.getName());
		writer.endNode();
		writer.startNode("imageName");
			writer.setValue(hero.getImageName());
		writer.endNode();
		writer.startNode("player");
			new PlayerConverter().marshal(hero.getOwner(), writer, context);
		writer.endNode();

		// write stats
		HeroStats stats;
		writer.startNode("herostats");
		try{
			Field f = Creature.class.getDeclaredField("stats");
			f.setAccessible(true);
			stats = (HeroStats) f.get(hero);
			new HeroStatsConverter().marshal(stats,writer,context);
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
			stream.alias("hero", Hero.class);
			stream.registerConverter(new HeroConverter());
			String filename = reader.getValue();
			File file = new File(Constants.DATA_HEROES + filename);
			return (Hero)(stream.fromXML(file));
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
			Object obj = new PlayerConverter().unmarshal(reader,context);
			Player player;
			if (obj == null) player = null;
			else player = (Player)obj;
		reader.moveUp();

		// load stats
		reader.moveDown();
			HeroStats stats = (HeroStats) new HeroStatsConverter().unmarshal(reader, context);
		reader.moveUp();

		return new Hero(name,imgName,player,stats);

	}

}
