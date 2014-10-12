package storage.converters;

import player.Player;
import game.items.Item;
import game.units.Creature;
import game.units.Hero;
import game.units.Unit;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IconConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return clazz == WorldIcon.class || clazz == Party.class || clazz == ItemIcon.class;
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		
		reader.moveDown();
			String type = reader.getValue();
		reader.moveUp();
		
		if (type.equals("Party")) return unmarshalParty(reader,context);
		else if (type.equals("ItemIcon")) return unmarshalItemIcon(reader,context);
		else throw new RuntimeException("we have a world icon that isn't party or itemIcon");
	}
	
	private ItemIcon unmarshalItemIcon(HierarchicalStreamReader reader, UnmarshallingContext context){
		ItemConverter ic = new ItemConverter();
		Item item = (Item)ic.unmarshal(reader, context);
		return new ItemIcon(item);
	}
	
	private Party unmarshalParty(HierarchicalStreamReader reader, UnmarshallingContext context){
		
		
	}
	
	private void unmarshalMember(HierarchicalStreamReader reader, UnmarshallingContext context, Creature[][] members, Player player){
		
		// read the position
		reader.moveDown();
			int row = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int col = Integer.parseInt(reader.getValue());
		reader.moveUp();
		
		if (members[col][row] != null){
			throw new RuntimeException("loading 2 creatures into the same spot in the party");
		}
		
		// read the creature
		reader.moveDown();
		String node = reader.getNodeName();
		if (node.equals("hero")){
			members[col][row] = (Hero) new HeroConverter().unmarshal(reader, context);
		}
		else if (node.equals("unit")){
			members[col][row] = (Unit) new UnitConverter().unmarshal(reader, context);
		}
		else throw new RuntimeException("party member specified that isn't a hero or a unit: " + node);
		reader.moveUp();
		
	}
	
}
