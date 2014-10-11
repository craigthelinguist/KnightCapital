package storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import tools.Constants;
import tools.ImageLoader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class AnimationMapLoader {

	public class CraigImage{

		String name;
		BufferedImage image;

		public CraigImage(BufferedImage bi, String n){
			name = n;
			image = bi;
		}

	}

	// use the static methods
	private AnimationMapLoader(){}

	public void test() throws IOException{

		String name = "ovelia_red_south";
		/*
		CraigImage ci = new CraigImage(image,name);

		XStream stream = new XStream(new DomDriver());
		stream.registerConverter(new CraigImageConverter());
		stream.alias("image", CraigImage.class);
		String xml = stream.toXML(ci);
		System.out.println(xml);
		PrintStream ps = new PrintStream(new File("test.xml"));
		ps.print(xml);
		CraigImage craig_image = (CraigImage)stream.fromXML(new FileReader("test.xml"));

		System.out.println(craig_image.name);
*/
	}

	class CraigImageConverter implements Converter{

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
			CraigImage image = (CraigImage)source;
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

	public static void main(String[] args) throws IOException{
		new AnimationMapLoader().test();
	}

}
