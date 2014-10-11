package storage.converters;

import game.units.Stat;
import game.units.UnitStats;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UnitStatsConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type == UnitStats.class;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		UnitStats stats = (UnitStats)source;
		writer.startNode("baseHealth");
			writer.setValue(""+stats.getBase(Stat.HEALTH));
		writer.endNode();
		writer.startNode("baseDamage");
			writer.setValue(""+stats.getBase(Stat.DAMAGE));
		writer.endNode();
		writer.startNode("baseSpeed");
			writer.setValue(""+stats.getBase(Stat.SPEED));
		writer.endNode();
		writer.startNode("baseArmour");
			writer.setValue(""+stats.getBase(Stat.ARMOUR));
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		int hp; int dmg; int spd; int arm;
		
		reader.moveDown();
			hp = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			dmg = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			spd = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			arm = Integer.parseInt(reader.getValue());
		reader.moveUp();
		
		return new UnitStats(hp,dmg,spd,arm);
		
	}

}
