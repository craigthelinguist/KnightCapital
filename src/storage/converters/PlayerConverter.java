package storage.converters;

import java.awt.image.BufferedImage;

import player.Player;

import tools.ImageLoader;
import tools.KCImage;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * used to load player objects from <player> tags
 * @author craigaaro
 *
 */
public class PlayerConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type == Player.class;
	}

	@Override
	/**
	 * Converts an object to textual data.
	 * @param source
	 * @param writer
	 * @param reader
	 */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (source == null){
			writer.setValue("null");
			return;
		}
		Player player = (Player)source;
		writer.startNode("slot");
		writer.setValue("" + player.getSlot());
		writer.endNode();
	}

	@Override
	/**
	 * Converts textual data to an object.
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (!reader.hasMoreChildren()){

			String str = reader.getValue();
			if (str.equals("null")) return null;
			else{
				int index = Integer.parseInt(str);
				return WorldLoader.getPlayer(index);
			}

		}
		Player player;
		reader.moveDown();
			int slot = Integer.parseInt(reader.getValue());
		reader.moveUp();
		if (!reader.hasMoreChildren()){
			player = WorldLoader.getPlayer(slot);
			if (player == null) throw new RuntimeException("PLayer hasn't been defined! Player: " + slot);
		}
		else{
			reader.moveDown();
				String name = reader.getValue();
				player = new Player(name,slot);
			reader.moveUp();
			WorldLoader.insertPlayer(player.getSlot(), player);
		}
		return player;
	}



}
