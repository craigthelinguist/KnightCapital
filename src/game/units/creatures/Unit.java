package game.units.creatures;

import game.units.stats.UnitStats;
import player.Player;

/**
 * Units populate parties and fight in battles. They have stats.
 * @author Aaron Craig
 */
public class Unit extends Creature {

	public Unit(String name, String imgName, Player player, UnitStats stats) {
		super(name,imgName,player,stats);
	}

}
