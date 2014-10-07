package world;

import game.units.Hero;
import game.units.Unit;
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
	
	/**
	 * Return true if this Party is owned by the specified player.
	 * @param p: player you suspect owns this party.
	 * @return true if the player owns this party.
	 */
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
	
	/**
	 * Decrease the remaining movement points by the specified amount. Doesn't check to see if
	 * the final result will make sense.
	 * @param amount: amount to decrease by.
	 */
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
	
	/**
	 * Set the player who owns this party.
	 * @param player: new player to own this party.
	 */
	public void setOwner(Player player){
		owner = player;
	}
	
	/**
	 * Return true if the specified heroes leads this party.
	 * @param h: hero to check
	 * @return: true if the hero leads this party.
	 */
	public boolean ledBy(Hero h){
		return hero == h;
	}
	
}
