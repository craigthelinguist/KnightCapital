package game.units;

import player.Player;

/**
 *
 * @author craigthelinguist
 *
 */
public class Unit extends Creature {

	public Unit(String imgName, Player player, int baseHealth, int baseArmour, int baseDamage) {
		super(imgName, player,baseHealth, baseArmour, baseDamage);
	}

}
