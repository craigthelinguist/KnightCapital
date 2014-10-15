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
 * orders, such as units and heroes. They have stats which represent how much damage they do, how much health
 * they have, etc. They also have a record of all buffs being applied to them.
 * @author craigthelinguist
 */
public abstract class Creature {

	// the animations representing this creature
	protected AnimationMap animations;

	// the name/imageName of this creature
	protected String name;
	protected String imgName;

	// all buffs being applied to this creature
	protected LinkedList<Buff> buffs;

	// who owns the creature + stats
	protected Stats stats;
	protected Player owner;

	public Creature(String name, String imgName, Player player, UnitStats stats) {
		this.name = name;
		this.stats = stats;
		this.name = name;

		this.imgName = imgName;
		this.owner = player;
		buffs = new LinkedList<>();
		changeOwner(player);
	}

	/**
	 * Change who owns this creature. Update its images to the right colour.
	 * @param newOwner: player who now owns this creature.
	 */
	public void changeOwner(Player newOwner){
		this.owner = newOwner;
		animations = new AnimationMap();
		animations.addImage("portrait", Constants.PORTRAITS, ImageLoader.load(Constants.PORTRAITS + imgName));
		if (newOwner == null) return;
		String playerColor = owner.getColour();
		animations.addDirectedImages(Constants.ICONS, imgName, playerColor);
		animations.setImage("north");
	}

	/**
	 * Damage this creature by the amount specified. A creature cannot have its
	 * health reduced below zero.
	 * @param damageDealt: amount of damage this creature has suffered.
	 */
	public void damage(int damageDealt){
		int totalArmour = stats.getTotal(Stat.ARMOUR);
		int hp = stats.getCurrent(Stat.HEALTH);
		damageDealt = damageDealt - totalArmour;
		if (damageDealt <= 0) return;
		hp = Math.max(0, hp-damageDealt);
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
		stats.setCurrent(Stat.HEALTH, hp);
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
	public void fullHeal(){
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
	 * Add the specified buff to this creature.
	 * @param buff: buff to apply.
	 */
	public void addBuff(Buff buff){
		buff.applyTo(this);
		buffs.add(buff);
	}

	public void removeBuff(Buff buffToRemove){
		for (int i = 0; i < buffs.size(); i++){
			Buff buff = buffs.get(i);
			if (buff.equals(buffToRemove)){
				buffs.remove(i);
				buff.removeFrom(this);
				return;
			}
		}
		Log.print("[Creature] Tried to remove a buff but it wasn't found.");
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
	 * @return: an int in the range[0,100]
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
	 * Return the current health of this creature.
	 * @return
	 */
	public int getHealth(){
		return stats.getCurrent(Stat.HEALTH);
	}

	public void setCurrent(Stat stat, int amount){
		stats.setCurrent(stat, amount);
	}

	public void setBuffed(Stat stat, int amount){
		stats.setBuff(stat, amount);
	}

	/**
	 * Returns true if this creature is dead. Dead as a doorknob.
	 * @return boolean whether dead or not.
	 */
	public boolean isDead() {
		return stats.getTotal(Stat.HEALTH) <= 0;
	}

	public AttackType getAttackType(){
		return stats.getAttackType();
	}

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

	public Player getOwner(){
		return this.owner;
	}

	public String getName(){
		return name;
	}

	public String getImageName(){
		return this.imgName;
	}

}
