package game.units;

import game.effects.Buff;
import game.items.EquippedItem;
import game.items.Item;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import player.Player;
import tools.Constants;
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
	private Item[] inventory;

	public Hero(String name, String imgName, Player player, HeroStats stats){
		super(name,imgName,player,stats);
		inventory = new Item[INVENTORY_SIZE];
	}

	public int getMovePoints(){
		return this.stats.getTotal(Stat.MOVEMENT);
	}

	public void setMovePts(int newPts){
		this.stats.setBase(Stat.MOVEMENT, newPts);
	}

}
