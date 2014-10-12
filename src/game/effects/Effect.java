package game.effects;

import game.units.Creature;
import world.icons.Party;

public interface Effect {

	public abstract void applyTo(Creature c);
	public abstract void removeFrom(Creature c);
	
	
}
