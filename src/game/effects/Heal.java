package game.effects;

import game.items.ChargedItem;
import game.units.Creature;

/**
 * An ItemEffect that heals the target.
 * @author craigthelinguist
 *
 */
public class Heal extends ChargedItem{

	private final int magnitude;
	
	public Heal(int magnitude){
		this.magnitude = magnitude;
	}

	@Override
	public void apply(Creature c){
		c.heal(magnitude);
	}
	
}
