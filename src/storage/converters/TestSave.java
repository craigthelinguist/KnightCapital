package storage.converters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;

import tools.Constants;
import world.World;

public class TestSave {

	public static void main(String[] args) throws FileNotFoundException{

		// save
		final String filepath = Constants.DATA_WORLDS + "test_save.xml";

		// save
		World world = WorldLoader.exampleWorld();
		XStream stream = new XStream();
		stream.alias("world", World.class);
		stream.registerConverter(new WorldConverter());
		String xml = stream.toXML(world);
		PrintWriter ps = new PrintWriter(new File(filepath));
		ps.print(xml);
		System.out.println(xml);
		ps.close();


		// load
		world = null;
		world = (World) stream.fromXML(new File(filepath));
	}

}
