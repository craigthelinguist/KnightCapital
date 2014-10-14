package storage.converters;

import game.units.Hero;
import game.units.Unit;

import java.io.File;

import player.Player;
import tools.Constants;

import com.thoughtworks.xstream.XStream;

public class HeroLoader {

	private HeroLoader(){}

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
