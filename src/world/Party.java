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

	public static final int PARTY_SIZE = 5;
	private Player owner;
	private int movementPoints;
	
	private Hero hero;
	private Unit[] units;
	
	
	// TODO: should be instantiated with a hero; no party is without one
	public Party(String imgname, Player player) {
		super(imgname);
		owner = player;
		units = new Unit[PARTY_SIZE];
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

	/**
	 * 'Refresh' this party. Reset its movement points. All members should regenerate their
	 * hit points and lose all temporary buffs for thie day.
	 */
	public void refresh(){
		movementPoints = hero.getMovePoints();
		hero.regenHealth();
		hero.removeTempBuffs();
		for (int i = 0; i < units.length; i++){
			Unit unit = units[i];
			if (unit == null) continue;
			unit.removeTempBuffs();
			unit.regenHealth();
		}
	}
	
	private void regenHitPoints() {
		hero.regenHealth();
		for (int i = 0; i < units.length; i++){
			if (units[i] != null) units[i].regenHealth();
		}
	}
	
}
