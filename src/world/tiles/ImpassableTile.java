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

	public static ImpassableTile newTreeTile(int x, int y){
		int random = 1 + (int)(Math.random() * 2);
		return new ImpassableTile("tree_"+random, x, y);
	}

	public static ImpassableTile newBushTile(int x, int y){
		int random = 1 + (int)(Math.random() * 2);
		return new ImpassableTile("bush_"+random, x, y);
	}

	@Override
	public boolean passable() {
		return false; //fuck your passability
	}

	@Override
	public String asString() {
		return "impassable";
	}



}
