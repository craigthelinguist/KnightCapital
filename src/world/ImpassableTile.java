package world;

public class ImpassableTile extends Tile{

	@Override
	public boolean passable(Party party) {
		return false; //fuck your passability
	}

	
	
}
