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

	private Player owner;
	private int movementPoints;
	
	private Hero hero;
	private Unit[] units;
	
	
	// TODO: should be instantiated with a hero; no party is without one
	public Party(String imgname, Player player) {
		super(imgname);
		owner = player;
	}
	
	public boolean ownedBy(Player p){
		return owner == p;
	}
	
	/**
	 * Return the number of movement points this party has left.
	 * @return number of movement points remaining.
	 */
	public int getMovePoints(){
		return movementPoints;
	}
	
	public void decreaseMovePoints(int amount){
		movementPoints -= amount;
	}
	
	/**
	 * Refresh the number of movement points this party has. This is based on the movement points
	 * stat the hero in the party has.
	 */
	public void refreshMovePoints(){
		movementPoints = hero.getMovePoints();
	}
	
	/**
	 * Set the hero leading the party
	 * @param newLeader: new hero to lead the party
	 */
	public void setLeader(Hero newLeader){
		hero = newLeader;
	}
	
	public void setOwner(Player player){
		owner = player;
	}
	
	public boolean ledBy(Hero h){
		return hero == h;
	}
	
}
