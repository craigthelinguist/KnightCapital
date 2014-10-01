package storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import world.World;


/**
 * recreates a game world object from the state it was
 * Previously saved
 *
 * @author selemon
 * */


public class LoadXML {



	/**
	 * reads in an xml file and creates a world object
	 *
	 * @return a world object
	 * */

	public World read(){

		//create a file chooser to let user
		//select a destination to save
		JFileChooser chooser = new JFileChooser(".");

		//create filter to only show xml files
		FileFilter filter = new FileNameExtensionFilter("Game Files", "xml");
		chooser.setFileFilter(filter);

		int value = chooser.showOpenDialog(null);
		String filePath = null;
		World world = null;

		if(value == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getPath();
		}
		else{
			return world;
		}

		try{
			world = (World) new XStream(new DomDriver()).fromXML(new FileInputStream(filePath), world);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		return world;


	}



}
