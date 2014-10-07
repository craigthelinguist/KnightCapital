package game.items.active;

import game.units.Creature;
import game.units.Stat;

/**
 * A TemporaryBuff will increase the value of a Creature's stats for the duration of the turn.
 * @author craigthelinguist
 *
 */
public class TemporaryBuff extends ActiveEffect{

	private final Stat stat;
	private final int amount;
	
	public TemporaryBuff(Stat stat, int amount){
		this.stat = stat;
		this.amount = amount;
	}

	@Override
	public void apply(Creature c) {
		c.buff(stat,amount);
	}
	
	
}
