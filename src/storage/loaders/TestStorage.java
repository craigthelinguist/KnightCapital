package storage.loaders;

import game.effects.Buff;
import game.effects.Heal;
import game.items.ChargedItem;
import game.items.EquippedItem;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;
import game.units.AttackType;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Player;

import com.thoughtworks.xstream.XStream;

import renderer.AnimationMap;
import storage.converters.AnimationMapConverter;
import storage.converters.BuffConverter;
import storage.converters.HealConverter;
import storage.converters.ItemConverter;
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
	
	public static void test_buff(){
		
		String filepath = "data" + File.separatorChar + "buff.xml";
		XStream stream = new XStream();
		stream.alias("buff", Buff.class);
		stream.registerConverter(new BuffConverter());
		
		Buff buff = Buff.newTempBuff(Stat.ARMOUR, 5);
		String xml = stream.toXML(buff);
		System.out.println(xml);
		
	}
	
	public static void test_heal() throws IOException{
		
		String filepath = "data" + File.separatorChar + "heal.xml";
		XStream stream = new XStream();
		stream.alias("heal", Heal.class);
		stream.registerConverter(new HealConverter());
		
		Heal heal = new Heal(Stat.HEALTH, 20);
		String xml = stream.toXML(heal);
		PrintStream ps = new PrintStream(new File(filepath));
		ps.print(xml);
		
		heal = (Heal) stream.fromXML(new File(filepath));
		
	}

	public static void test_item(){
		
		String filepath = Constants.DATA_ITEMS + "liontalisman.xml";
		XStream stream = new XStream();
		stream.alias("item", Item.class);
		stream.alias("item", PassiveItem.class);
		stream.alias("item", EquippedItem.class);
		stream.alias("item", ChargedItem.class);
		stream.alias("buff", Buff.class);
		stream.alias("heal", Heal.class);
		stream.registerConverter(new ItemConverter());
		
		Buff[] buffs = new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE, 10), Buff.newPermaBuff(Stat.HEALTH, 20) };
		Item item = new PassiveItem("Talisman of the Lion", "amulet", "ur a lion bro", buffs, Target.HERO);
		
		System.out.println(stream.toXML(item));
		
		PassiveItem talisman = (PassiveItem)stream.fromXML(new File(Constants.DATA_ITEMS + "liontalisman.xml"));
		
	}
	
	public static void main(String[] args) throws Exception{
		test_item();
	}

}
