package player;

public class Player {

	// name = name of the player
	// slot = player 1, player 2, etc.
	public final String name;
	public final int slot;
	private int gold;

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
		this.gold = 10;
		this.name = name;
		this.slot = slot;
	}

	public int getSlot(){return this.slot;}

	public String getColour(){
		if (slot == 1) return "red";
		else if (slot == 2) return "blue";
		else if (slot == 3) return "green";
		else if (slot == 4) return "violet";
		else throw new RuntimeException("illegal slot for player " + name + ", slot was " + slot);
	}

	public int getGold() {
		return this.gold;
	}

	public void increaseGold(int amount) {
		this.gold += amount;
	}

}
