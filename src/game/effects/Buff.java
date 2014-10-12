package game.effects;

import game.items.ChargedItem;
import game.items.Target;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;

/**
 * A Buff changes the stats of a target.
 * @author craigthelinguist
 */
public class Buff{

	public final Stat stat;
	public final int amount;
	public final boolean permanent;
	public final Target target;

	/**
	 * 
	 * @param stat: stat this buff affects
	 * @param amount: how much to increase or decrease that stat
	 * @param permanence: if this buff isn't permanent, it will expire after 1 turn
	 * @param target: who does this buff affect (only the hero, the entire party etc.)?
	 */
	private Buff(Stat stat, int amount, boolean permanence, Target target){
		this.stat = stat;
		this.amount = amount;
		this.permanent = permanence;
		this.target = target;
	}

	/**
	 * Create and return a new temporary buff effect.
	 * Temporary buff effects expire at the end of a day.
	 * @param stat: stat that is changed by this buff.
	 * @param amount: amount by which the stat changes.
	 * @return: a new Buff effect.
	 */
	public static Buff newTempBuff(Stat stat, int amount, Target target){
		return new Buff(stat,amount,false,target);
	}

	/**
	 * Create and return a new permanent buff effect.
	 * @param stat: stat that is changed by this buff.
	 * @param amount: amount by which the stat changes.
	 * @return: a new Buff effect.
	 */
	public static Buff newPermaBuff(Stat stat, int amount, Target target){
		return new Buff(stat,amount,true,target);
	}

	/**
	 * Apply this buff to a creature.
	 * SHOULD ONLY BE CALLED FROM CREATURE.
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
	 * SHOULD ONLY BE CALLED FROM CREATURE.
	 * @param c: creature to remove buff from.
	 */
	public void removeFrom(Creature c){ 
		int buffedValue = c.getBuffed(this.stat);
		int newValue = buffedValue - this.amount;
		c.setBuffed(stat,newValue);
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
