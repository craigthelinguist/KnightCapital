package storage.converters;

import java.awt.image.BufferedImage;
import java.util.List;

import renderer.AnimationMap;
import tools.ImageLoader;
import tools.KCImage;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class AnimationMapConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(KCImage.class);
	}

	@Override
	/**
	 * Converts an object to textual data.
	 * @param source
	 * @param writer
	 * @param reader
	 */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		AnimationMap map = (AnimationMap)source;
		List<KCImage> images = map.asList();
		writer.startNode("images");
			for (KCImage image : images){
				writer.startNode("name");
				writer.setValue(image.name);
				writer.endNode();
				writer.startNode("filepath");
				writer.setValue(image.filepath);
				writer.endNode();
			}
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

		while(reader.hasMoreChildren()){

		}

		reader.moveDown();

		reader.moveDown();
		String name = reader.getValue();
		reader.moveUp();
		reader.moveDown();
		String filepath = reader.getValue();
		reader.moveUp();
		BufferedImage bi = ImageLoader.load(filepath);
		return new KCImage(bi,name,filepath);
	}



}
