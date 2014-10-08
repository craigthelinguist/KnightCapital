package game.units;

import game.effects.Buff;
import game.items.EquippedItem;
import game.items.Item;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import player.Player;
import tools.GlobalConstants;
import tools.ImageLoader;

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

	public Hero(String imgName, Player player){
		super(imgName,player);
		inventory = new Item[INVENTORY_SIZE];
	}

	public int getMovePoints(){
		return movementPoints;
	}

	public void setMovePts(int newPts){
		movementPoints = newPts;
	}

	/**
	 * Equip an item to this Hero. Should apply all buffs to the Hero.
	 * @param itm: item to be equipped.
	 */
	public void equip(EquippedItem itm){
		for (Buff buff : itm.getBuffs()){
			buff.applyTo(this);
		}
	}

	/**
	 * Unequip an item from this hero. Should take away all buffs that were granted.
	 * @param itm: item to be removed.
	 */
	public void unequip(EquippedItem itm){
		for (Buff buff : itm.getBuffs()){
			buff.applyTo(this);
		}
	}

}
