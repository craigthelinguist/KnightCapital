package storage.converters;

import java.awt.image.BufferedImage;

import player.Player;

import storage.loaders.DataLoader;
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
		writer.setValue("" + player.slot);
	}

	@Override
	/**
	 * Converts textual data to an object.
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		String name = reader.getValue();
		if (name.equals("null")) return null;
		else{
			int slot = Integer.parseInt(reader.getValue());
			if (DataLoader.players.containsKey(slot)) return DataLoader.players.get(slot);
			else{
				throw new RuntimeException("Player hasn't been defined! Player: " + slot);
			}
		}
		
	}



}
