package storage.converters;

import game.items.Item;
import game.units.Unit;

import java.io.File;

import tools.Constants;

import com.thoughtworks.xstream.XStream;

public class ItemLoader{

	private ItemLoader(){}

	public static Item load(String filename){
		File file = new File(Constants.DATA_ITEMS + filename);
		XStream stream = new XStream();
		stream.alias("item", Item.class);
		stream.registerConverter(new ItemConverter());
		Item item = (Item) stream.fromXML(file);
		return item;
	}

}
