package game.effects;

import world.icons.Party;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;

public class Heal implements Effect{

	public final Stat stat;
	public final int amount;

	public Heal(Stat stat, int amount){

		if (stat != Stat.MOVEMENT && stat != Stat.HEALTH){
			throw new RuntimeException("illegal stat for healing effect, it was " + stat + " but it can only be health or movement");
		}

		this.stat = stat;
		this.amount = amount;

	}

	@Override
	public void applyTo(Creature c){
		if (stat == Stat.MOVEMENT) throw new RuntimeException("movement healing must be applied to party, not creature!");
		c.heal(amount);
	}

	@Override
	public void removeFrom(Creature c) {
		if (stat == Stat.MOVEMENT) throw new RuntimeException("movement healing must be removed from party, not creature!");
		c.damage(amount);
	}

	public void applyTo(Party p){
		if (stat == Stat.HEALTH) throw new RuntimeException("healing must be applied to creature, not party!");
		p.addMovementPoints(amount);
	}

	public void removeFrom(Party p){
		if (stat == Stat.HEALTH) throw new RuntimeException("healing must be applied to creature, not party!");
		p.addMovementPoints(amount);
	}

	@Override
	public boolean equals(Object o){
		if (o == null) return false;
		if (!(o instanceof Heal)) return false;
		Heal other = (Heal)o;
		return other.stat == stat && other.amount == amount;
	}

}
