package storage.converters;

import java.io.File;

import com.thoughtworks.xstream.XStream;

import tools.Constants;
import game.units.Unit;

public class UnitLoader {

	private UnitLoader(){}
	
	public static Unit load(String filename){
		File file = new File(Constants.DATA_UNITS + filename);
		XStream stream = new XStream();
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitConverter());
		Unit unit = (Unit) stream.fromXML(file);
		return unit;
	}
	
}
