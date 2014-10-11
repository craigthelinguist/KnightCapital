package storage.converters;

import java.awt.image.BufferedImage;

import storage.AnimationMapLoader.CraigImage;
import tools.Constants;
import tools.ImageLoader;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class BufferedImageConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(CraigImage.class);
	}

	@Override
	/**
	 * Converts an object to textual data.
	 * @param source
	 * @param writer
	 * @param reader
	 */
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode("image");
			writer.setValue(image.name);
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

		reader.moveDown();
		String imageName = reader.getValue();
		reader.moveUp();

		BufferedImage bi = ImageLoader.load(Constants.ICONS + imageName);
		return new CraigImage(bi,imageName);

	}



}
