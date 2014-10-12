package tests;

import static org.junit.Assert.*;
import game.effects.Buff;
import game.effects.Effect;
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

import org.junit.Test;

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

	
	/**
	 * Read from a player.xml files the variables for name and slot and initialize a player 
	 * with those values and check if it read the xml file properly. 
	 */
	@Test
	public void test_player(){
		String filepath = "data" + File.separatorChar + "player.xml";
		XStream stream = new XStream();
		stream.alias("player", Player.class);
		stream.registerConverter(new PlayerConverter());
		Player player = (Player) stream.fromXML(new File(filepath));
		
		assertTrue(player.name.equals("john the baptist"));
		assertFalse(player.name.equals("Mizza"));
		assertTrue(player.slot == 0);
		assertFalse(player.slot == 2);
	}
	
	/**
	 * Load the knight.xml file and instantiate a knight unit using the values inside knigth.xml.
	 * Check whether the values read in are the values that the object has.
	 */
	@Test
	public void test_unit_Knight(){
		String filepath = Constants.DATA_UNITS + "knight.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.alias("player", Player.class);
		stream.alias("attack", AttackType.class);
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitStatsConverter());
		stream.registerConverter(new UnitConverter());
		
		Unit unit = (Unit) stream.fromXML(new File(filepath));
		
		assertTrue(unit.getName().equals("Knight"));
		assertTrue(unit.getImageName().equals("knight")); 
		assertTrue(unit.getBase(Stat.ARMOUR) == 5);
		assertTrue(unit.getBase(Stat.HEALTH) == 100);
		assertTrue(unit.getBase(Stat.DAMAGE) == 25);
		assertTrue(unit.getBase(Stat.SPEED) == 40);
		assertTrue(unit.getAttackType() == AttackType.MELEE);
		
	}
	
	/**
	 * Load the archer.xml file and instantiate an archer unit using the values inside archer.xml.
	 * Check whether the values read in are the values that the object has.
	 */	
	@Test
	public void test_unit_archer(){
		String filepath = Constants.DATA_UNITS + "archer.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.alias("player", Player.class);
		stream.alias("attack", AttackType.class);
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitStatsConverter());
		stream.registerConverter(new UnitConverter());
		
		Unit unit = (Unit) stream.fromXML(new File(filepath));
		
		assertTrue(unit.getName().equals("Archer"));
		assertTrue(unit.getImageName().equals("archer")); 
		assertTrue(unit.getBase(Stat.ARMOUR) == 0);
		assertTrue(unit.getBase(Stat.HEALTH) == 60);
		assertTrue(unit.getBase(Stat.DAMAGE) == 15);
		assertTrue(unit.getBase(Stat.SPEED) == 90);
		assertTrue(unit.getAttackType() == AttackType.RANGED);
		
	}
	
	public static void test_unit_stats(){
		String filepath = Constants.DATA_UNITS + "knight.xml";
		
		XStream stream = new XStream();
		stream.alias("unitstats", UnitStats.class);
		stream.alias("attack", AttackType.class);
		
		stream.registerConverter(new UnitStatsConverter());
		
		UnitStats stats = (UnitStats) stream.fromXML(new File(filepath));
		
	}
	
	@Test
	public void test_external_stats() throws FileNotFoundException{
		String filepath = Constants.XMLTESTS + "external_unit.xml";
		XStream stream = new XStream();
		stream.alias("unit", Unit.class); 
		stream.registerConverter(new UnitConverter());
		
		Unit unit = (Unit) stream.fromXML(new File(filepath));
	}
	
	
	/**
	 * Create a new xml file and write to it a new temporary buff object(with some values) then read 
	 * it back in and initialize a new object with these values. Then it tests to see if the values 
	 * saved (on the xml file) are the ones it read back into the temporary buff object.
	 * @throws FileNotFoundException
	 */
	@Test
	public void test_buff() throws FileNotFoundException{
		String filepath = Constants.XMLTESTS + "buff.xml";
		XStream stream = new XStream();
		stream.alias("buff", Buff.class);
		stream.registerConverter(new BuffConverter());
		
		Buff buff = Buff.newTempBuff(Stat.ARMOUR, 5);
		String xml = stream.toXML(buff);
		PrintStream ps = new PrintStream(new File(filepath));
		ps.print(xml);
		System.out.println(xml);
		
		buff = (Buff) stream.fromXML(new File(filepath));
		
		assertTrue(buff.amount == 5);
		assertFalse(buff.amount == 0);
		assertTrue(buff.permanent == false);
		assertTrue(buff.stat == Stat.ARMOUR);
		
	}
	
	/**
	 * Tests the heal by writing to an xml file, reading back the same xml file and 
	 * instantiating an object
	 * @throws IOException
	 */
	@Test
	public void test_heal() throws IOException{
		
		String filepath = Constants.XMLTESTS + "heal.xml";
		XStream stream = new XStream();
		stream.alias("heal", Heal.class);
		stream.registerConverter(new HealConverter());
		
		Heal heal = new Heal(Stat.HEALTH, 20);
		String xml = stream.toXML(heal);
		PrintStream ps = new PrintStream(new File(filepath));
		ps.print(xml);
		
		heal = (Heal) stream.fromXML(new File(filepath));
		assertTrue(heal.amount == 20);
		assertFalse(heal.amount == 1);
		assertTrue(heal.stat == Stat.HEALTH);
		assertFalse(heal.stat == Stat.ARMOUR);
		
	}

	/**
	 * Create an new item and write to xml all the fields. Read back xml and
	 * instantiate an object and check if the item that was written was the same which was read back.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test_item() throws FileNotFoundException{
		
		String filepath = Constants.XMLTESTS + "liontalisman.xml";
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
		String xml = stream.toXML(item);
		PrintStream ps = new PrintStream(new File(filepath));
		ps.print(xml);
		
		
		System.out.println(stream.toXML(item));
		
		PassiveItem talisman = (PassiveItem)stream.fromXML(new File(filepath));
		
		/*Test the effects of the talisman*/
		Effect[] e = talisman.getEffects();
		assertTrue(e.length == talisman.getEffects().length); //check that the arrays of effects have same length
		/*Iterate over the array checking that the effects are the same*/
		for(int i =0; i < e.length; i++) {
			assertTrue(e[i] == talisman.getEffects()[i]);
		}
		/*Check if the name,description, stats etc were all laoded correctyl*/
		assertTrue(talisman.getName().equals("Talisman of the Lion"));
		assertTrue(talisman.getDescription().equals("ur a lion bro"));
		assertTrue(talisman.getImageName().equals("amulet"));
		assertTrue(talisman.getTarget().equals(Target.HERO));	
	}
	
	public static void main(String[] args) throws Exception{
		//test_item();
	}

}
