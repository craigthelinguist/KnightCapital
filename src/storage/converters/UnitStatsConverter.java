package storage.converters;

import java.io.File;

import tools.Constants;
import game.units.AttackType;
import game.units.Stat;
import game.units.UnitStats;

import com.thoughtworks.xstream.XStream;
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
		writer.startNode("attack");;
			writer.setValue(""+stats.getAttackType());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		UnitStats stats;

			int hp; int dmg; int spd; int arm; AttackType type;

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

			stats = new UnitStats(hp,dmg,spd,arm,type);
			if (currentHP != null){
				stats.setCurrent(Stat.HEALTH, currentHP);
			}

		return stats;

	}

}
