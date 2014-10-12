package storage.converters;

import tools.KCImage;
import game.units.AttackType;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AttackTypeConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == AttackType.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		AttackType type = (AttackType)object;
		writer.startNode("attack");
			writer.setValue(type.toString());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			String type = reader.getValue();
		reader.moveUp();
		return AttackType.fromString(type);
	}

}
