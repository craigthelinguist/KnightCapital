package game.effects;

import world.icons.Party;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;

public class Heal {

	public final int amount;
	public final Stat stat;
	
	public Heal(Stat stat, int amount){
		
		if (stat != Stat.MOVEMENT || stat != Stat.HEALTH){
			throw new RuntimeException("illegal stat for healing effect, it was " + stat + " but it can only be health or movement");
		}
		
		this.stat = stat;
		this.amount = amount;
		
	}
	
	public void applyTo(Creature c){
		if (!(c instanceof Hero) && stat == stat.MOVEMENT){ 
			throw new RuntimeException("applying movement heal to something that isn't a hero");
		}
		c.heal(amount);
	}
	
	public void applyTo(Party p){
		if (stat != stat.MOVEMENT){
			throw new RuntimeException("must apply movement heal to a party");
		}
		p.addMovementPoints(amount);
	}
	
}
