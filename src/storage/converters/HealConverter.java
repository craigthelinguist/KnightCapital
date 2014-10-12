package storage.converters;

import game.effects.Heal;
import game.units.Stat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HealConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == Heal.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		Heal heal = (Heal)object;
		writer.startNode("stat");
			writer.setValue(heal.stat.toString());
		writer.endNode();
		writer.startNode("amount");
			writer.setValue(""+heal.amount);
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Stat stat; int amount;
		
		reader.moveDown();
			stat = Stat.valueOf(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			amount = Integer.parseInt(reader.getValue());
		reader.moveUp();
		
		return new Heal(stat,amount);
	}

}
