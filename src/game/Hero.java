package game;

import java.util.LinkedList;

/**
 * A hero leads parties. They have a bunch of stats (some of which are unique to heroes) and can hold, equip,
 * or use items.
 * @author craigthelinguist
 *
 */
public class Hero extends Creature {

	// how far this hero can move on the world
	private int movementPoints;
	private LinkedList<Item> items;
	
	public int getMovePoints(){
		return movementPoints;
	}
	
	public void setMovePts(int newPts){
		movementPoints = newPts;
	}
	
}
