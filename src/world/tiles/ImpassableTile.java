package world.tiles;

import world.icons.Party;

public class ImpassableTile extends Tile{

	private ImpassableTile(int x, int y){
		super(x,y);
	}
	
	public static ImpassableTile newVoidTile(int x, int y){
		return new ImpassableTile(x,y);
	}
	
	@Override
	public boolean passable(Party party) {
		return false; //fuck your passability
	}

	
	
}
