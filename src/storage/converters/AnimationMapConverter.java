package storage.converters;

import java.util.ArrayList;
import java.util.List;

import renderer.AnimationMap;
import tools.KCImage;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Used to read and write xml tags for AnimationMap.
 * @author craigaaro
 */
public class AnimationMapConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type == AnimationMap.class;
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
		List<KCImage> images = new ArrayList<>();
		KCImageConverter converter = new KCImageConverter();
		while(reader.hasMoreChildren()){
			reader.moveDown();
			KCImage image = (KCImage) converter.unmarshal(reader, context);
			images.add(image);
			reader.moveUp();
		}
		return new AnimationMap(images);
	}



}
