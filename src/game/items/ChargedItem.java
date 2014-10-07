package game.items;

import game.units.Creature;

/**
 * A ChargedItem is some item that you use on something and then it's gone
 * (e.g.: a potion)
 * @author craigthelinguist
 *
 */
public abstract class ChargedItem {
	
	public abstract void apply(Creature c);
	
}
