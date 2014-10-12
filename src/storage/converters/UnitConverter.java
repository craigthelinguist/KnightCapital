package storage.converters;

import player.Player;
import game.units.AttackType;
import game.units.Unit;
import game.units.UnitStats;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UnitConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.isAssignableFrom(Unit.class);
	}

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
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
			
		// load attack type
		reader.moveDown();
			AttackType type = (AttackType) new AttackTypeConverter().unmarshal(reader, context);
		reader.moveUp();
		
		// load stats
		reader.moveDown();
			UnitStats stats = (UnitStats) new UnitStatsConverter().unmarshal(reader, context);
		reader.moveUp();
		
		return new Unit(name,null,type,stats);
	}

}
