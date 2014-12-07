package game.items;

import world.icons.Party;
import game.effects.Effect;
import game.units.creatures.Creature;

/**
 * A ChargedItem is some item that you use on something and then it's gone
 * (e.g.: a potion)
 * @author Aaron Craig
 */
public class ChargedItem extends Item{

	public ChargedItem(String name, String imgName, String description, Effect[] effects, Target target) {
		super(name, imgName, description, effects, target);
	}

	@Override
	public boolean applyTo(Party p) {
		throw new UnsupportedOperationException("charged item not yet implemented!");
	}

	@Override
	public boolean applyTo(Creature c) {
		throw new UnsupportedOperationException("charged item not yet implemented!");
	}

	@Override
	public boolean removeFrom(Party p) {
		throw new UnsupportedOperationException("charged item not yet implemented!");
	}

	@Override
	public boolean removeFrom(Creature c) {	
		throw new UnsupportedOperationException("charged item not yet implemented!");
	}

}
