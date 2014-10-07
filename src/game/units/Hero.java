package game.units;

import game.items.Item;

import java.util.LinkedList;

/**
 * A hero leads parties. They have a bunch of stats (some of which are unique to heroes) and can hold, equip,
 * or use items.
 * @author craigthelinguist
 *
 */
public class Hero extends Creature {

	public static final int INVENTORY_SIZE = 6;
	
	// how far this hero can move on the world
	private int movementPoints;
	private int maxMovementPoints;
	private Item[] inventory;
	
	public Hero(){
		inventory = new Item[INVENTORY_SIZE];
	}
	
	public int getMovePoints(){
		return movementPoints;
	}
	
	public void setMovePts(int newPts){
		movementPoints = newPts;
	}
	
}
