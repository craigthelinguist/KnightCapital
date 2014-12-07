package storage.converters;

import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import game.units.stats.UnitStats;

import java.io.File;

import tools.Constants;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
/**
 * Used to read and write xml tags for HeroStats objects.
 * @author craigaaro
 *
 */
public class HeroStatsConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == HeroStats.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		HeroStats stats = (HeroStats)object;
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
		writer.startNode("baseSight");
			writer.setValue(""+stats.getBase(Stat.SIGHT));
		writer.endNode();
		writer.startNode("baseMovement");
			writer.setValue(""+stats.getBase(Stat.MOVEMENT));
		writer.endNode();
		writer.startNode("attack");
			writer.setValue(""+stats.getAttackType());
		writer.endNode();
		writer.startNode("currentHealth");
			writer.setValue(""+stats.getCurrent(Stat.HEALTH));
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		HeroStats stats;

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


		return stats;

	}

}
