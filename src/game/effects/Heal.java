package game.effects;

import game.units.Creature;
import game.units.Stat;
import world.icons.Party;

/**
 * A Heal recovers some amount of health of a unit or the movement points of a party.
 * @author Aaron Craig
 */
public class Heal implements Effect{

	public final Stat stat;
	public final int amount;

	/**
	 * Create and return new instance of Heal.
	 * @param stat: stat to heal
	 * @param amount: amount to heal by
	 */
 	public Heal(Stat stat, int amount){
 		// heal can only be applied to movement or health
		if (stat != Stat.MOVEMENT && stat != Stat.HEALTH){
			throw new RuntimeException("illegal stat for healing effect, it was " + stat + " but it can only be health or movement");
		}
		this.stat = stat;
		this.amount = amount;
	}

 	/**
 	 * Apply this heal to a creature. This can only happen if this heal is restoring health
 	 * @param c: creature to apply to.
 	 */
	@Override
	public void applyTo(Creature c){
		if (stat == Stat.MOVEMENT) throw new RuntimeException("movement healing must be applied to party, not creature!");
		c.heal(amount);
	}

	/**
	 * Undo the effects of applying this heal on a creature. This can only happen if this heal
	 * is restoring health
 	 * @param c: creature to apply to.
	 * @param c: the creature to apply to.
	 */
	@Override
	public void removeFrom(Creature c) {
		if (stat == Stat.MOVEMENT) throw new RuntimeException("movement healing must be removed from party, not creature!");
		c.damage(amount);
	}

	/**
	 * Apply this heal to a party. This can only happen if this heal is restoring movement
	 * points.
	 * @param p: party to apply to.
	 */
	public void applyTo(Party p){
		if (stat == Stat.HEALTH) throw new RuntimeException("healing must be applied to creature, not party!");
		p.addMovementPoints(amount);
	}

	/**
	 * Undo the effects of applying this heal on a party. This can only happen if this heal is restoring movement
	 * points.
	 * @param p: party to apply to.
	 */
	public void removeFrom(Party p){
		if (stat == Stat.HEALTH) throw new RuntimeException("healing must be applied to creature, not party!");
		p.addMovementPoints(amount);
	}

	/**
	 * Two heals are equal if they affect the same stat by the same amount.
	 */
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
		if (!(o instanceof Heal)) return false;
		Heal other = (Heal)o;
		return other.stat == stat && other.amount == amount;
	}

}
