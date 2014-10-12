package storage.converters;

import game.units.AttackType;
import game.units.HeroStats;
import game.units.Stat;
import game.units.UnitStats;

import java.io.File;

import tools.Constants;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HeroStatsConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == HeroStats.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		HeroStats stats;
		
		// load from file
		if (!reader.hasMoreChildren()){
			XStream stream = new XStream();
			stream.alias("herostats", HeroStats.class);
			stream.registerConverter(new HeroStatsConverter());
			String filename = reader.getValue();
			File file = new File(Constants.DATA_STATS + filename);
			stats = (HeroStats)(stream.fromXML(file));
		}
		else{
			int hp; int dmg; int spd; int arm;
			int sight; int movement; AttackType type;
			
			// hp
			reader.moveDown();
				hp = Integer.parseInt(reader.getValue());
			reader.moveUp();
		
			// damage
			reader.moveDown();
				dmg = Integer.parseInt(reader.getValue());
			reader.moveUp();
		
			// speed
			reader.moveDown();
				spd = Integer.parseInt(reader.getValue());
			reader.moveUp();
		
			// armour
			reader.moveDown();
				arm = Integer.parseInt(reader.getValue());
			reader.moveUp();
		
			// sight
			reader.moveDown();
				sight = Integer.parseInt(reader.getValue());
			reader.moveUp();
			
			// movement
			reader.moveDown();
				movement = Integer.parseInt(reader.getValue());
			reader.moveUp();

			// attack type
			reader.moveDown();
				type = AttackType.fromString(reader.getValue());
			reader.moveUp();
				
			Integer currentHP = null;
			if (reader.hasMoreChildren()){
				reader.moveDown();
					currentHP = Integer.parseInt(reader.getValue());
				reader.moveUp();
			}
			
			stats = new HeroStats(hp,dmg,spd,arm,sight,movement,type);
			if (currentHP != null){
				stats.setCurrent(Stat.HEALTH, currentHP);
			}
			
			
		}
		
		return stats;

	}

}
