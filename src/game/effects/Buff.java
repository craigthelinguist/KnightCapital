package game.effects;

import game.units.Creature;
import game.units.Stat;

/**
 * A Buff changes the stats of something. It has a stat, a magnitude, and a permanence.
 * Permanent buffs last forever until they're explicitly removed (e.g.: by unequipping
 * an item). Temporary buffs last until the next day.
 * @author Aaron Craig
 */
public class Buff implements Effect{

	public final Stat stat;
	public final int amount;
	public final boolean permanent;

	/**
	 * Create and return a new Buff.
	 * @param stat: stat this buff affects
	 * @param amount: how much to increase or decrease that stat
	 * @param permanence: if this buff isn't permanent, it will expire after 1 turn
	 * @param target: who does this buff affect (only the hero, the entire party etc.)?
	 */
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
	 * @param c: creature to apply buff to
	 */
	public void applyTo(Creature c){
		int buffedValue = c.getBuffed(this.stat);
		int newValue = buffedValue + this.amount;
		c.setBuffed(stat, newValue);
	}

	/**
	 * Remove this buff from a creature.
	 * This method should 'undo' the effects of applyTo(c)
	 * @param c: creature to remove buff from.
	 */
	public void removeFrom(Creature c){
		int buffedValue = c.getBuffed(this.stat);
		int newValue = buffedValue - this.amount;
		c.setBuffed(stat,newValue);
	}

	/**
	 * Two buffs are equal if they affect the same stat, have the same
	 * magnitude, and the same permanence.
	 * @return: boolean
	 */
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof Buff)) return false;
		Buff otherBuff = (Buff)other;
		return otherBuff.stat == stat && otherBuff.amount == amount && otherBuff.permanent == permanent;
	}


}
