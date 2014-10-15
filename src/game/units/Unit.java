package game.units;

import player.Player;

/**
 * Units populate parties and fight in battles. They have stats.
 * @author craigthelinguist
 */
public class Unit extends Creature {

	public Unit(String name, String imgName, Player player, UnitStats stats) {
		super(name,imgName,player,stats);
	}

}
