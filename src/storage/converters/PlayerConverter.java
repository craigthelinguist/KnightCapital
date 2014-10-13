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
		writer.setValue("" + player.slot);
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
		if (!reader.hasMoreChildren()) return null;
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
			WorldLoader.insertPlayer(player.slot, player);
		}
		return player;
	}



}
