package game.effects;

import game.units.creatures.Creature;

/**
 * Effect is the top-level interface for all effects that are attached to items.
 * @author Aaron Craig
 */
public interface Effect {

	/**
	 * Apply this effect to a creature.
	 * @param c: creature to apply to.
	 */
	public abstract void applyTo(Creature c);

	/**
	 * Remove this effect from a creature. This should undo the effects
	 * of applyTo(c).
	 * @param c: creature to remove from.
	 */
	public abstract void removeFrom(Creature c);

}
