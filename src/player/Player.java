package player;

import java.io.Serializable;

/**
 * Represents a player in an instance of a game being played on the world.
 * @author Neal, Solomon, Aaron
 */
public class Player implements Serializable{

	private static final long serialVersionUID = 1L;

	// name = name of the player
	// slot = player 1, player 2, etc.
	public final String name;
	public final int slot;

	// how much gold this player has
	private int gold;

	// the colour for the player depends on their slot
	public static final int NEUTRAL = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static final int VIOLET = 4;

	/**
	 * Constructs a new player.
	 * @param name : string of player name
	 * @param slot : int corresponding to playe num. ex 1 = player 1
	 */
	public Player(String name, int slot){
		this.gold = 500;
		this.name = name;
		this.slot = slot;
	}

	public int getSlot(){return this.slot;}

	/**
	 * Return the colour of this player as a string.
	 * @return: string
	 */
	public String getColour(){
		if (slot == 1) return "red";
		else if (slot == 2) return "blue";
		else if (slot == 3) return "green";
		else if (slot == 4) return "violet";
		else throw new RuntimeException("illegal slot for player " + name + ", slot was " + slot);
	}

	/**
	 * Return this player's gold.
	 * @return: int
	 */
	public int getGold() {
		return this.gold;
	}

	/**
	 * Increase this player's gold.
	 * @param amount: amount by which to increase.
	 */
	public void increaseGold(int amount) {
		if (amount <= 0) return;
		this.gold += amount;
	}

	/**
	 * Decrease this player's gold.
	 * @param amount: amount by which to decrease.
	 */
	public void decreaseGold(int amount){
		if (amount <= 0) return;
		this.gold -= amount;
	}

}
