package game.units.creatures;

import game.effects.Buff;
import game.items.EquippedItem;
import game.items.Item;
import game.units.stats.HeroStats;

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

	public Hero(String name, String imgName, Player player, HeroStats stats){
		super(name,imgName,player,stats);
	}

}
