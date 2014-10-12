package storage.converters;

import game.effects.Buff;
import game.effects.Effect;
import game.effects.Heal;
import game.items.ChargedItem;
import game.items.EquippedItem;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import tools.ImageLoader;
import tools.KCImage;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ItemConverter implements Converter{

	@Override
	public boolean canConvert(Class type) {
		return type == Item.class || type == PassiveItem.class
				|| type == EquippedItem.class || type == ChargedItem.class;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		
		Item item = (Item)source;
		
		// write attributes
		writer.startNode("name");
			writer.setValue(item.getName());
		writer.endNode();
		writer.startNode("imageName");
			writer.setValue(item.getImageName());
		writer.endNode();
		writer.startNode("description");
			writer.setValue(item.getDescription());
		writer.endNode();
		writer.startNode("type");
			writer.setValue(item.getClassAsString());
		writer.endNode();
		writer.startNode("target");
			writer.setValue(item.getTarget().toString());
		writer.endNode();
		
		// write effects
		Effect[] effects = item.getEffects();
		HealConverter healConverter = new HealConverter();
		BuffConverter buffConverter = new BuffConverter();
		for (int i = 0; i < effects.length; i++){
			Effect effect = effects[i];
			if (effect instanceof Buff){
				writer.startNode("buff");
				buffConverter.marshal(effect, writer, context);
				writer.endNode();
			}
			else if (effect instanceof Heal){
				writer.startNode("heal");
				healConverter.marshal(effect, writer, context);				
				writer.endNode();
			}
		}
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		reader.moveDown();
			String name = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			String imageName = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			String description = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			String type = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			Target target = Target.valueOf(reader.getValue());
		reader.moveUp();
		
		HealConverter healConverter = new HealConverter();
		BuffConverter buffConverter = new BuffConverter();
		LinkedList<Effect> effects = new LinkedList<>();
		
		while (reader.hasMoreChildren()){
			reader.moveDown();
				String nodeName = reader.getNodeName();
				if (nodeName.equals("buff")){
					Buff buff = (Buff) buffConverter.unmarshal(reader, context);
					effects.add(buff);
				}
				else if (nodeName.equals("heal")){
					Heal heal = (Heal) healConverter.unmarshal(reader, context);
					effects.add(heal);
					
					if (type.equals("equipped")){
						throw new RuntimeException("Equipped items can't have healing effects");
					}
					
				}
				else throw new RuntimeException("Unknown effect while loading item from xml");
			reader.moveUp();		
		}
		
		Effect[] effectsArray = new Effect[effects.size()];
		for (int i = 0; i < effects.size(); i++){
			effectsArray[i] = effects.get(i);
		}
		
		Item item;
		if (type.equals("passive")){
			item = new PassiveItem(name,imageName,description,effectsArray,target);
		}
		else if (type.equals("charged")){
			item = new ChargedItem(name,imageName,description,effectsArray,target);
		}
		else if (type.equals("equipped")){
			item = new EquippedItem(name,imageName,description,(Buff[])effectsArray,target);
		}
		else throw new RuntimeException("Unknown kind of item: " + type);
		return item;
		
	}
}
