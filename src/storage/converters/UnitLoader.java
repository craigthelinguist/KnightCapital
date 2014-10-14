package storage.converters;

import java.io.File;

import player.Player;

import com.thoughtworks.xstream.XStream;

import tools.Constants;
import game.units.Unit;

public class UnitLoader {

	private UnitLoader(){}

	public static Unit load(String filename, Player player){
		File file = new File(Constants.DATA_UNITS + filename);
		XStream stream = new XStream();
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitConverter());
		Unit unit = (Unit) stream.fromXML(file);
		unit.changeOwner(player);
		return unit;
	}

}
