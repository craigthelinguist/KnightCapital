package storage.loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import renderer.AnimationMap;
import storage.converters.AnimationMapConverter;
import storage.converters.KCImageConverter;
import tools.Constants;
import tools.ImageLoader;
import tools.KCImage;

public class TestStorage {

	public void test() throws Exception{


		/**
		BufferedImage image1 = ImageLoader.load(Constants.ICONS + "ovelia_red_north");
		BufferedImage image2 = ImageLoader.load(Constants.ICONS + "ovelia_red_east");
		KCImage kc1 = new KCImage(image1,"north",Constants.ICONS + "ovelia_red_north");
		KCImage kc2 = new KCImage(image2,"east",Constants.ICONS + "ovelia_red_east");
		List<KCImage> images = new ArrayList<>();
		images.add(kc1);
		images.add(kc2);
		**/

		String file = "test.xml";
		XStream stream = new XStream();
		stream.registerConverter(new AnimationMapConverter());
		stream.registerConverter(new KCImageConverter());
		stream.alias("images", AnimationMap.class);
		stream.alias("image", KCImage.class);
		//String str = stream.toXML(images);
		//PrintStream ps = new PrintStream(new File("test.xml"));
		//ps.println(str);


		FileReader fileReader = new FileReader(new File("test.xml"));
		AnimationMap map = (AnimationMap)(stream.fromXML(fileReader));

		System.out.println("stop");

	}

	public static void main(String[] args) throws Exception{

		new TestStorage().test();

	}

}
