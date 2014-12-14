package player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player in an instance of a game being played on the world.
 * @author Neal, Solomon, Aaron
 */
public class Player implements Serializable{

	private static final long serialVersionUID = 1L;

	// name = name of the player
	// slot = player 1, player 2, etc.
	private final String name;
	private final int slot;

	// how much gold this player has
	private int gold;

	// the colour for the player depends on their slot
	public static final int NEUTRAL = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int GREEN = 3;
	public static final int VIOLET = 4;
	
	public static final Map<Integer,String> PLAYER_COLOR_HEX = new HashMap<>();
	static {
		PLAYER_COLOR_HEX.put(Player.RED, "#DC143C");
		PLAYER_COLOR_HEX.put(Player.BLUE, "#1E90FF");
		PLAYER_COLOR_HEX.put(Player.GREEN, "#7FFF00");
		PLAYER_COLOR_HEX.put(Player.VIOLET, "#EE82EE");
		PLAYER_COLOR_HEX.put(Player.NEUTRAL, "#FFFFFF");
	}

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

	/**
	 * Return the colour of this player as a string.
	 * @return: string
	 */
	public String getColour(){
		if (getSlot() == 1) return "red";
		else if (getSlot() == 2) return "blue";
		else if (getSlot() == 3) return "green";
		else if (getSlot() == 4) return "violet";
		else throw new RuntimeException("illegal slot for player " + getName() + ", slot was " + getSlot());
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

	public String getName() {
		return name;
	}

	public int getSlot(){
		return this.slot;
	}

}
