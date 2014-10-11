package game.units;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import player.Player;
import renderer.Animation;
import renderer.AnimationMap;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import game.effects.Buff;

/**
 * A creature represents anything on the world or in a battle that is controlled by a player and can be given
 * orders, such as units and heroes.
 * @author craigthelinguist
 */
public abstract class Creature {

	protected AnimationMap animations;

	/*
	protected Map<String,Animation> animations;
	protected String animationName;
	protected Animation animation;
*/

	protected LinkedList<Buff> buffs;
	protected Stats stats;

	public BufferedImage getImage(){
		return animations.getImage();
	}

	public BufferedImage getPortrait(){
		return animations.getPortrait();
	}

	public String getAnimationName(){
		return animations.getName();
	}

	public void setAnimation(String name){
		animations.setImage(name);
	}

	public Creature(String imgName, Player player, Stats stats) {

		// stats
		this.stats = stats;
		buffs = new LinkedList<>();

		// set up images
		animations = new AnimationMap();
		animations.addImage("portrait", ImageLoader.load(Constants.PORTRAITS + imgName));
		imgName = imgName + "_" + player.getColour();
		Map<String,BufferedImage> images = ImageLoader.loadDirectedImages(Constants.ICONS + imgName);
		for (Map.Entry<String,BufferedImage> entry : images.entrySet()){
			animations.addImage(entry.getKey(), entry.getValue());
		}
		animations.setImage("north");

	}

	/**
	 * Damage this creature by the amount specified.
	 * @param damageDealt: amount of damage this creature has suffered.
	 */
	public void damage(int damageDealt){
		int totalArmour = stats.getTotal(Stat.ARMOUR);
		int hp = stats.getCurrent(Stat.HEALTH);
		damageDealt = damageDealt - totalArmour;
		if (damageDealt <= 0) return;
		hp -= damageDealt;
		stats.setCurrent(Stat.HEALTH, hp);
	}

	/**
	 * Heal this creature by the specified amount. Cannot be healed above base hit points.
	 * If the creature is dead, they don't get healed.
	 * @param magnitude: amount of hit points to heal.
	 */
	public void heal(int amount) {
		int hpTotal = stats.getTotal(Stat.HEALTH);
		int hp = stats.getCurrent(Stat.HEALTH);
		if (amount <= 0 || hp <= 0) return;
		hp = Math.min(hpTotal, hp+amount);
	}

	/**
	 * Revive the creature with the specified amount of health.
	 * If the creature is already alive, do nothing.
	 */
	public void revive(int amount){
		int hp = stats.getCurrent(Stat.HEALTH);
		if (hp != 0) return;
		stats.setCurrent(Stat.HEALTH, 1);
		heal(amount-1);
	}

	/**
	 * Heal this creature back up to max HP. If they're dead, they don't get healed.
	 * @param amount: amount of hit points to heal.
	 */
	public void fullHeal(int amount){
		int hp = stats.getCurrent(Stat.HEALTH);
		if (hp <= 0) return;
		int totalhp = stats.getTotal(Stat.HEALTH);
		stats.setCurrent(Stat.HEALTH, totalhp);
	}

	/**
	 * Regenerate this creature by 5% of their total hit points.
	 */
	public void regenHealth(){
		int totalHP = stats.getTotal(Stat.HEALTH);
		int hpToRegen = (int)(totalHP/20);
		heal(hpToRegen);
	}

	/**
	 * Increase or decrease the units stat by the specified amount.
	 * @param stat: stat to increase
	 * @param amount: amount to increase by (may be positive)
	 */
	public void tempBuff(Stat stat, int amount) {
		int currentBuffedValue = stats.getBuff(stat);
		int newBuffedValue = amount + currentBuffedValue;
		stats.setBuff(stat,newBuffedValue);
	}

	/**
	 * Permanently increase or decrease the units armour, damage, or speed by the specified amount.
	 * @param stat: stat to increase
	 * @param amount: amount to increase by (may be negative)
	 */
	public void permaBuff(Stat stat, int amount) {
		int baseValue = stats.getBase(stat);
		int newValue = amount + baseValue;
		stats.setBase(stat, newValue);
	}

	/**
	 * Removes all temporary buffs from this creature.
	 */
	public void removeTempBuffs(){
		Iterator<Buff> iter = buffs.iterator();
		while (iter.hasNext()){
			Buff buff = iter.next();
			if (!buff.isPermanent()){
				buff.removeFrom(this);
				iter.remove();
			}
		}
	}

	/**
	 * Return the healthiness of this creature as a percentage
	 * @return: int
	 */
	public double healthiness(){
		int maxHP = stats.getTotal(Stat.HEALTH);
		int hp = stats.getCurrent(Stat.HEALTH);
		if (maxHP == 0) return 1.0;
		double r = (double)hp/(double)maxHP;
		return r;
	}

	/**
	 * Get the base value of one of this creature's stats.
	 * @param stat: stat you want
	 * @return: base value of the stat
	 */
	public int getBase(Stat stat){
		return stats.getBase(stat);
	}

	/**
	 * Get the buffed value of one of this creature's stats.
	 * @param stat: stat you want
	 * @return: buffed value of the stat
	 */
	public int getBuffed(Stat stat){
		return stats.getBuff(stat);
	}

	/**
	 * Get the total value (base + buffed) of one of this creature's stats.
	 * @param stat: stat you want
	 * @return: total value of the stat
	 */
	public int get(Stat stat){
		return stats.getTotal(stat);
	}

	/**
	 * Returns true if this creature is dead. Dead as a doorknob.
	 * @return boolean whether dead or not.
	 */
	public boolean isDead() {
		if(this.healthiness() <= 0) {
			return true;
		}
		return false;
	}
}
