package storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thoughtworks.xstream.XStream;

import world.World;


/**
 * This class will save the state of the game into an
 * XML file
 * @author selemon
 * */
public class SaveXML {

	private States world;
	
	
	/**
	 * Constructor takes a world object
	 * @param w the game world 
	 * */
	public SaveXML(States w){
		this.world = w;
	}
	
	
	public boolean write(){
		
		
		//create a file chooser to let user 
		//select a destination to save
		JFileChooser chooser = new JFileChooser(".");
		
		//create filter to only show xml files
		FileFilter filter = new FileNameExtensionFilter("Game Files", "xml");
		chooser.setFileFilter(filter);
		
		int value = chooser.showSaveDialog(null);
		String filePath = null;
		
		if(value == JFileChooser.APPROVE_OPTION){
			filePath = chooser.getSelectedFile().getPath();
		}
		else{
			return false;
		}
		
		//append .xml extension if not already provided
		if(!filePath.endsWith(".xml")){
			filePath += ".xml";
		}
		
		
		//write to a file 
		try{
			new XStream().toXML(world, new FileOutputStream(filePath));
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		
		return true;
		
		
		
		
	}
	
	
}
