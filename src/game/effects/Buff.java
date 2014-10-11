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

	/**
	 * Apply this buff to a creature.
	 * @param c
	 */
	public void applyTo(Creature c){
		if (permanent) c.permaBuff(stat, amount);
		else c.tempBuff(stat, amount);
	}

	/**
	 * Remove this buff from a creature.
	 * If this buff is permanent, does nothing.
	 * Otherwise this method should 'undo' whatever buff it applied.
	 * @param c: creature to apply this buff to.
	 */
	public void removeFrom(Creature c){
		if (!permanent) c.tempBuff(stat, -amount);
	}

	/**
	 * Return true if this buff is permanent.
	 * @return: boolean
	 */
	public boolean isPermanent() {
		return permanent;
	}

	@Override
	public boolean equals(Object other){
		if (!(other instanceof Buff)) return false;
		Buff otherBuff = (Buff)other;
		return otherBuff.stat == stat && otherBuff.amount == amount && otherBuff.permanent == permanent;
	}


}
