package storage.converters;

import game.items.Item;
import game.units.creatures.Unit;

import java.io.File;

import tools.Constants;

import com.thoughtworks.xstream.XStream;
/**
 * Used to load item objects from data files.
 * @author craigaaro
 *
 */
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
