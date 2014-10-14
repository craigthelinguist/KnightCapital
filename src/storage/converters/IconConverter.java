package storage.converters;

import java.util.ArrayList;
import java.util.List;

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

			if (object == null){
				writer.setValue("null");
				return;
			}

			writer.startNode("type");
				if (object instanceof Party) writer.setValue("Party");
				else if (object instanceof ItemIcon) writer.setValue("ItemIcon");
			writer.endNode();
			if (object instanceof Party) marshalParty(object,writer,context);
			else if (object instanceof ItemIcon) marshalItemIcon(object,writer,context);
			else throw new RuntimeException("I'm martialling some icon that I do'nt recognise");

	}

	private void marshalItemIcon(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {

		ItemIcon ii = (ItemIcon)object;
		Item item = ii.item;

		writer.startNode("item");
			new ItemConverter().marshal(item, writer, context);
		writer.endNode();

	}

	public void marshalParty(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {

		Party party = (Party)object;
		Player owner = party.getOwner();

		writer.startNode("owner");
			new PlayerConverter().marshal(owner, writer, context);
		writer.endNode();

		Creature[][] creatures = party.getMembers();

		for (int row = 0; row < creatures.length; row++){
			for (int col = 0; col < creatures[row].length; col++){
				if (creatures[row][col] == null) continue;

				System.out.printf("(%d,%d)\n", col,row);

				writer.startNode("member");
					writer.startNode("row");
						writer.setValue(""+row);
					writer.endNode();
					writer.startNode("col");
						writer.setValue(""+col);
					writer.endNode();

					Creature creature = creatures[row][col];
					if (creature instanceof Unit){
						writer.startNode("unit");
						new UnitConverter().marshal(creature,writer, context);
						writer.endNode();
					}
					else if (creature instanceof Hero){
						writer.startNode("hero");
						new HeroConverter().marshal(creature,writer, context);
						writer.endNode();
					}
					else{
						throw new RuntimeException("IconConverter trying to marshal something in a party that isn't hero or unit");
					}
				writer.endNode();
			}
		}

		Item[][] items = party.getInventory();
		if (items == null){
			System.out.println("stop");
		}

		int count = 0;
		for (int i = 0; i < items.length; i++){
			for (int j = 0; j < items[i].length; j++){
				count++;
			}
		}
		System.out.println(count);

		for (int col = 0; col < items.length; col++){
			for (int row = 0; row < items[col].length; row++){
				if (items[col][row] == null) continue;
				Item item = items[col][row];
				writer.startNode("item");
					new ItemConverter().marshal(item,writer,context);
				writer.endNode();
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		if (!reader.hasMoreChildren()){
			return null;
		}

		reader.moveDown();
			String type = reader.getValue();
		reader.moveUp();

		if (type.equals("Party")) return unmarshalParty(reader,context);
		else if (type.equals("ItemIcon")) return unmarshalItemIcon(reader,context);
		else throw new RuntimeException("we have a world icon that isn't party or itemIcon");
	}

	private ItemIcon unmarshalItemIcon(HierarchicalStreamReader reader, UnmarshallingContext context){
		ItemConverter ic = new ItemConverter();
		reader.moveDown();
		Object obj = ic.unmarshal(reader, context);
		reader.moveUp();
		Item item = (Item)obj;
		return new ItemIcon(item);
	}

	public Party unmarshalParty(HierarchicalStreamReader reader, UnmarshallingContext context){

		// read in owner of party
		Player player = null;
		reader.moveDown();
			Object obj = new PlayerConverter().unmarshal(reader, context);
			if (obj != null) player = (Player)obj;
		reader.moveUp();

		// read the members and items
		Creature[][] members = Party.newEmptyParty();
		List<Item> items = new ArrayList<>();
		while (reader.hasMoreChildren()){
			reader.moveDown();

				String node = reader.getNodeName();
				if (node.equals("member")){
					unmarshalMember(reader,context,members,player);
				}
				else if (node.equals("item")){
					Item item = (Item) new ItemConverter().unmarshal(reader, context);
					items.add(item);
				}

			reader.moveUp();
		}

		// validate items
		if (items.size() >= Party.INVENTORY_SIZE){
			throw new RuntimeException("error loading party! too many items.");
		}

		// get the hero
		Hero hero = null;
		for (int i = 0; i < members.length; i++){
			for (int j = 0; j < members[i].length; j++){
				Creature c = members[i][j];
				if (c == null) continue;
				if (c instanceof Hero && hero != null){
					throw new RuntimeException("error loading multiple heroes in party! " + hero.getName() + " and " + c.getName());
				}
				else if (c instanceof Hero && hero == null){
					hero = (Hero)c;
				}
			}
		}

		// create party
		Party party = new Party(hero,player,members);

		// add items to party
		for (Item item : items){
			party.addItem(item);
		}

		// finished
		return party;
	}

	private void unmarshalMember(HierarchicalStreamReader reader, UnmarshallingContext context, Creature[][] members, Player player){

		// read the position
		reader.moveDown();
			int row = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
			int col = Integer.parseInt(reader.getValue());
		reader.moveUp();

		if (members[row][col] != null){
			throw new RuntimeException("loading 2 creatures into the same spot in the party");
		}

		// read the creature
		reader.moveDown();
		String node = reader.getNodeName();
		if (node.equals("hero")){
			Hero hero =  (Hero) new HeroConverter().unmarshal(reader, context);
			hero.changeOwner(player);
			members[row][col] = hero;
		}
		else if (node.equals("unit")){
			Unit unit = (Unit) new UnitConverter().unmarshal(reader,context);
			unit.changeOwner(player);
			members[row][col] = unit;
		}
		else throw new RuntimeException("party member specified that isn't a hero or a unit: " + node);
		reader.moveUp();

	}

}
