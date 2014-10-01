package world;

import game.Hero;
import game.Unit;
import player.Player;

/**
 * 
 * @author craigthelinguist
 *
 */
public class Party extends WorldIcon{

	private Player player;
	private int movementPoints;
	
	private Hero hero;
	private Unit[] units;
	
	
	// TODO: should be instantiated with a hero; no party is without one
	public Party(String imgname, Player player) {
		super(imgname);
		this.player = player;
	}
	
	public boolean ownedBy(Player p){
		return player == p;
	}
	
	/**
	 * Return the number of movement points this party has left.
	 * @return number of movement points remaining.
	 */
	public int getMovePoints(){
		return movementPoints;
	}
	
	/**
	 * Refresh the number of movement points this party has. This is based on the movement points
	 * stat the hero in the party has.
	 */
	public void refreshMovePoints(){
		movementPoints = hero.getMovePoints();
	}
	
}
