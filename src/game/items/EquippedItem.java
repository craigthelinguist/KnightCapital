package game.items;

import game.effects.Buff;

/**
 * An equipped item must be equipped to your hero before its effect takes place.
 * e.g.: a sword that increases attack
 * @author craigthelinguist
 */
public class EquippedItem extends Item{

	public EquippedItem(Buff[] buffs, String imgName, String name, String description) {
		super(name,imgName,description,buffs);
	}
	
}
