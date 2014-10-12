package storage.loaders;

import game.units.AttackType;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Player;

import com.thoughtworks.xstream.XStream;

import renderer.AnimationMap;
import storage.converters.AnimationMapConverter;
import storage.converters.KCImageConverter;
import storage.converters.UnitConverter;
import storage.converters.UnitStatsConverter;
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

		String filepath = "data" + File.separatorChar + "units" + File.separatorChar + "knight.xml";
		
		
		// Unit Stats
		XStream stream = new XStream();
		stream.alias("STATSUNIT", UnitStats.class);
		stream.alias("player", Player.class);
		stream.alias("attack", AttackType.class);
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitStatsConverter());
		stream.registerConverter(new UnitConverter());
		
		Unit unit = (Unit) stream.fromXML(new File(filepath));
		
	}

	public static void main(String[] args) throws Exception{

		new TestStorage().test();

	}

}
