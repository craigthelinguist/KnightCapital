package game.effects;

import game.items.ChargedItem;
import game.units.Creature;
import game.units.Stat;

/**
 * A Buff changes the stats of a target.
 * @author craigthelinguist
 */
public class Buff{

	public final Stat stat;
	public final int amount;
	public final boolean permanent;
	
	private Buff(Stat stat, int amount, boolean permanence){
		this.stat = stat;
		if (stat == Stat.HEALTH)
			throw new RuntimeException("don't hadd health effects to buffs - use Heal instead"); 
		this.amount = amount;
		this.permanent = permanence;
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
	
	public void applyTo(Creature c){
		if (permanent) c.permaBuff(stat, amount);
		else c.tempBuff(stat, amount);
	}
	
	@Override
	public boolean equals(Object other){
		if (!(other instanceof Buff)) return false;
		Buff otherBuff = (Buff)other;
		return otherBuff.stat == stat && otherBuff.amount == amount && otherBuff.permanent == permanent;
	}
	
}
