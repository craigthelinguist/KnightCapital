package game.items.active;

import game.units.Creature;

/**
 * An ActiveEffect is something that must be actively used on a Creature.
 * @author craigthelinguist
 *
 */
public abstract class ActiveEffect {
	
	public abstract void apply(Creature c);
	
}
