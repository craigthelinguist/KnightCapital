package game.items.active;

import game.units.Creature;

/**
 * An ItemEffect that heals the target.
 * @author craigthelinguist
 *
 */
public class HealingEffect extends ActiveEffect{

	private final int magnitude;
	
	public HealingEffect(int magnitude){
		this.magnitude = magnitude;
	}

	@Override
	public void apply(Creature c){
		c.heal(magnitude);
	}
	
}
