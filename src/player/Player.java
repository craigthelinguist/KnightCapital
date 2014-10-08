package player;

public class Player {

	// name = name of the player
	// slot = player 1, player 2, etc.
	public final String name;
	public final int slot;

	public Player(String name, int slot){
		this.name = name;
		this.slot = slot;
	}

	public String getColour(){
		if (slot == 1) return "red";
		else if (slot == 2) return "blue";
		else if (slot == 3) return "green";
		else if (slot == 4) return "violet";
		else throw new RuntimeException("illegal slot for player " + name + ", slot was " + slot);
	}

}
