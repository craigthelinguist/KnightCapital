package game.units.factories;

import game.units.creatures.Unit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import player.Player;

import storage.converters.UnitConverter;
import tools.Constants;
import world.towns.City;

public class HeroFactory {

	private static final String DATA_HEROES = Constants.DATA_HEROES;
	
	private HeroFactory(){}
	
	static final Map<String,Integer> costs = new HashMap<>();
	static {
		costs.put("Dark Knight", 300);
	}
	
	/**
	 * Train an instance of a hero with the given heroName that belongs to the specified
	 * player and puts it in the specified city.
	 * @param heroName: name of the hero you want to train
	 * @param player: player to whom the hero will belong
	 * @param city: city training the hero
	 * @throws NoSuchUnitException: if that hero name does not exist.
	 * @throws InsufficientGoldException: if the player doesn't have enough gold to train the hero.
	 * @throws NoSpaceInCityException: if there's no space in the city to make the hero.
	 */
	public static void TrainHero(String unitName, Player player, City city)
	throws Exception {

		// check unit exists and can be trained
		if (!costs.containsKey(unitName)) throw new NoSuchUnitException();
		int goldcost = costs.get(unitName);
		if (player.getGold() < goldcost) throw new InsufficientGoldException();
		if (!city.getVisitors().isEmpty()) throw new NoSpaceInCityException();
		
		// create IO
		XStream stream = new XStream();
		stream.alias("unit", Unit.class);
		stream.registerConverter(new UnitConverter());
		unitName = unitName.toLowerCase();
		String filename = unitName + ".xml";
		File file = new File(DATA_HEROES + filename);
		
		// make unit
		Unit unit = (Unit) stream.fromXML(file);
		unit.changeOwner(player);
		city.getGarrison().addUnit(unit);
		player.decreaseGold(goldcost);
		
	}
	
	
}
