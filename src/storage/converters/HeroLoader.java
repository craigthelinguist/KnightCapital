package storage.converters;

import game.units.Hero;
import game.units.Unit;

import java.io.File;

import player.Player;
import tools.Constants;

import com.thoughtworks.xstream.XStream;

/**
 * Used to load hero objects from data files.
 * @author craigaaro
 *
 */
public class HeroLoader {

	private HeroLoader(){}

	/**
	 * Load and return the hero object described in the specified .xml file.
	 * @param filename: Name of file containing details about hero.
	 * @param player1: player to whom this hero should belong
	 * @return: hero that belongs to the player
	 */
	public static Hero load(String filename, Player player1){
		File file = new File(Constants.DATA_HEROES + filename);
		XStream stream = new XStream();
		stream.alias("hero", Hero.class);
		stream.registerConverter(new HeroConverter());
		Hero hero = (Hero) stream.fromXML(file);
		hero.changeOwner(player1);
		return hero;
	}

}
