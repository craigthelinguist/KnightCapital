package storage.converters;

import game.effects.Buff;
import game.units.Stat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Used to read and write xml tags for Buffs.
 * @author craigaaro
 */
public class BuffConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == Buff.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		Buff buff = (Buff)object;
		writer.startNode("stat");
			writer.setValue(buff.stat.toString());
		writer.endNode();
		writer.startNode("amount");
			writer.setValue(""+buff.amount);
		writer.endNode();
		writer.startNode("permanent");
			writer.setValue(""+buff.permanent);
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		Stat stat; boolean permanent; int amount;

		reader.moveDown();
			stat = Stat.valueOf(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			amount = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			permanent = Boolean.parseBoolean(reader.getValue());
		reader.moveUp();

		if (permanent) return Buff.newPermaBuff(stat, amount);
		else return Buff.newTempBuff(stat, amount);

	}

}
