package game.units;

import player.Player;

/**
 *
 * @author craigthelinguist
 *
 */
public class Unit extends Creature {

	@Deprecated
	/** use the other one **/
	public Unit(String imgName, Player player, UnitStats stats){
		super(imgName,player,stats);
	}
	
	public Unit(String name, String imgName, Player player, UnitStats stats) {
		super(name,imgName,player,stats);
	}
	
}
