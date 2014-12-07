package game.units.factories;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import player.Player;

import com.thoughtworks.xstream.XStream;

import storage.converters.HeroConverter;
import storage.converters.HeroLoader;
import storage.converters.UnitConverter;
import storage.converters.UnitLoader;
import tools.Constants;
import world.towns.City;
import game.units.creatures.Hero;
import game.units.creatures.Unit;

public class UnitFactory {

	private static final String DATA_UNITS = Constants.DATA_UNITS;
	private static final String DATA_HEROES = Constants.DATA_HEROES;
	
	private UnitFactory(){}
	static final Map<String,Integer> costs = new HashMap<>();
	static {
		costs.put("Knight", 125);
		costs.put("Archer", 75);
		costs.put("Dark Knight", 300);
	}
	
	static final Set<String> heroes = new HashSet<>();
	static {
		heroes.add("Dark Knight");
	}
	
	/**
	 * Train an instance of a unit with the given unitName that belongs to the specified
	 * player and puts it in the specified city.
	 * @param unitName: name of the unit you want to train
	 * @param player: player to whom the unit will belong
	 * @param city: city training the unit
	 * @throws NoSuchUnitException: if that unit name does not exist.
	 * @throws InsufficientGoldException: if the player doesn't have enough gold to train the unit.
	 * @throws NoSpaceInCityException: if there's no space in the city to make the unit.
	 */
	public static void TrainUnit(String unitName, Player player, City city)
	throws Exception {

		// check unit exists and can be trained
		if (!costs.containsKey(unitName)) throw new NoSuchUnitException();
		int goldcost = costs.get(unitName);
		if (player.getGold() < goldcost) throw new InsufficientGoldException();
		boolean isHero = heroes.contains(unitName);
		if (!isHero && !city.getGarrison().hasSpace()) throw new NoSpaceInCityException();
		else if (isHero && !city.getVisitors().isEmpty()) throw new NoSpaceInCityException();
		
		// make unit, add to city, update owner, decrease gold
		if (isHero){
			Hero hero = HeroLoader.load(unitName, player);
			hero.changeOwner(player);
			city.getVisitors().addUnit(hero);
		}
		else{
			Unit unit = UnitLoader.load(unitName, player);
			unit.changeOwner(player);
			city.getGarrison().addUnit(unit);
		}
		player.decreaseGold(goldcost);

	}
	
}
