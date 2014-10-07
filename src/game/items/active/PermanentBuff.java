package game.items.active;

import game.units.Creature;
import game.units.Stat;

/**
 * A PermanentBuff will permanently increase the target's stats.
 * @author craigthelinguist
 */
public class PermanentBuff extends ActiveEffect{

	private final Stat stat;
	private final int amount;
	
	public PermanentBuff(Stat stat, int amount){
		this.stat = stat;
		this.amount = amount;
	}
	
	@Override
	public void apply(Creature c) {
		c.permaBuff(stat,amount);
	}

	
	
}
