package world.tiles;

import world.icons.Party;

public class ImpassableTile extends Tile{

	public ImpassableTile(String imgName, int x, int y){
		super(imgName,x,y);
	}
	
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

	@Override
	public String asString() {
		return "impassable";
	}

	
	
}
