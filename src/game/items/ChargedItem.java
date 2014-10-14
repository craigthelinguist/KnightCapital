package game.items;

import game.effects.Effect;
import game.units.Creature;

/**
 * A ChargedItem is some item that you use on something and then it's gone
 * (e.g.: a potion)
 * @author craigthelinguist
 *
 */
public class ChargedItem extends Item{

	public ChargedItem(String name, String imgName, String description, Effect[] effects, Target target, String filename) {
		super(name, imgName, description, effects, target, filename);
	}


}
