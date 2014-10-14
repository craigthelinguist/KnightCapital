package storage.converters;

import game.items.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;

import tools.Constants;
import world.World;
import world.icons.WorldIcon;

public class TestSave {

	public static void main(String[] args) throws FileNotFoundException{

		// save
		final String filepath = Constants.DATA_WORLDS + "test_save.xml";

		// save
		World world = WorldLoader.exampleWorld();
		XStream stream = new XStream();
		setup(stream);
		String xml = stream.toXML(world);
		PrintWriter ps = new PrintWriter(new File(filepath));
		ps.print(xml);
		System.out.println(xml);
		ps.close();


		// load
		world = null;
		world = (World) stream.fromXML(new File(filepath));
	}

	private static void setup(XStream stream){
		stream.alias("item", Item.class);
		stream.alias("icon", WorldIcon.class);
		stream.alias("world", World.class);
		stream.registerConverter(new ItemConverter());
		stream.registerConverter(new IconConverter());
		stream.registerConverter(new WorldConverter());
	}

}
