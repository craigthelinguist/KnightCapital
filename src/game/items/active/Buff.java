package game.items.active;

import game.units.Creature;
import game.units.Stat;

/**
 * A Buff changes the stats of a target.
 * @author craigthelinguist
 */
public class Buff extends ActiveEffect{

	private final Stat stat;
	private final int amount;
	private final boolean permanence;
	
	private Buff(Stat stat, int amount, boolean permanence){
		this.stat = stat;
		this.amount = amount;
		this.permanence = permanence;
	}
	
	/**
	 * Create and return a new temporary buff effect.
	 * Temporary buff effects expire at the end of a day.
	 * @param stat: stat that is changed by this buff.
	 * @param amount: amount by which the stat changes.
	 * @return: a new Buff effect.
	 */	
	public static Buff newTempBuff(Stat stat, int amount){
		return new Buff(stat,amount,false);
	}
	
	/**
	 * Create and return a new permanent buff effect.
	 * @param stat: stat that is changed by this buff.
	 * @param amount: amount by which the stat changes.
	 * @return: a new Buff effect.
	 */
	public static Buff newPermaBuff(Stat stat, int amount){
		return new Buff(stat,amount,true);
	}
	
	/**
	 * Check to see if this buff is permanent or not.
	 * @return: true if the buff is permanent.
	 */
	public boolean isPermanent(){
		return permanence;
	}
	
	@Override
	public void apply(Creature c) {
		if (permanence) c.permaBuff(stat,amount);
		else c.buff(stat, amount);
	}

	
	
}
