package storage.loaders;

import game.units.AttackType;
import game.units.Stat;
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
import storage.converters.PlayerConverter;
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

		
		
		
		
		// Unit Stats
		/**
		 * 

		**/
		
	}

	public static void test_player(){
		String filepath = "data" + File.separatorChar + "player.xml";
		XStream stream = new XStream();
		stream.alias("player", Player.class);
		stream.registerConverter(new PlayerConverter());
		Player player = (Player) stream.fromXML(new File(filepath));
	}
	
	public static void test_unit(){
		String filepath = "data" + File.separatorChar + "unit.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.alias("player", Player.class);
		stream.alias("attack", AttackType.class);
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitStatsConverter());
		stream.registerConverter(new UnitConverter());
		
		Unit unit = (Unit) stream.fromXML(new File(filepath));
		
	}
	
	public static void test_unit_stats(){
		String filepath = Constants.DATA_STATS + "knight.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.alias("attack", AttackType.class);
		
		stream.registerConverter(new UnitStatsConverter());
		
		UnitStats stats = (UnitStats) stream.fromXML(new File(filepath));
		
	}
	
	public static void test_external_stats(){
		String filepath = "data" + File.separatorChar + "other.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.registerConverter(new UnitStatsConverter());
		
		UnitStats stats = (UnitStats) stream.fromXML(new File(filepath));	
		
	}
	
	public static void test_items(){
		
		//String filepath = Constants.DATA_ITEMS + "rune_sword.xml";
		
	}
	
	public static void main(String[] args) throws Exception{
		test_items();
	}

}
