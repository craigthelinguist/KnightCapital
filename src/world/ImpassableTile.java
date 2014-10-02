package world;

public class ImpassableTile extends Tile{

	private ImpassableTile(){}
	
	public static ImpassableTile newVoidTile(){
		return new ImpassableTile();
	}
	
	@Override
	public boolean passable(Party party) {
		return false; //fuck your passability
	}

	
	
}
