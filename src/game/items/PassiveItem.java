package game.items;

import game.effects.Buff;

/**
 * Passive items sit in your inventory and their effects happen from there.
 * e.g.: a banner that increases your movement points on overworld
 * @author craigthelinguist
 *
 */
public class PassiveItem extends Item {

	/**
	 * Constructor for items that don't have buffs but grant passive bonuses
	 **/
	@Deprecated
	public PassiveItem(String imgName, String description) {
		super(imgName, "harry", description);
	}

	public PassiveItem(Buff[] buffArray, String imgName, String name, String description){
		super(imgName, name, description);
		for (Buff b : buffArray) buffs.add(b);

	}



}
