package storage.converters;

import java.io.File;

import player.Player;

import com.thoughtworks.xstream.XStream;

import tools.Constants;
import game.units.Unit;
/**
 * Used to load load unit object sfrom files.
 * @author craigaaro
 *
 */
public class UnitLoader {

	private UnitLoader(){}

	/**
	 * Load the unit described in the specified file. Return a new instance,
	 * owned by the specified player.
	 * @param filename: name of file containing the unit
	 * @param player: player to whom the unit should belong
	 * @return unit that belongs to specified player.
	 */
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
