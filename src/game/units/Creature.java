package game.units;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import player.Player;
import renderer.Animation;
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

	private int baseHealth;
	private int baseDamage;
	private int baseSpeed;
	private int baseArmour;

	private int buffedDamage;
	private int buffedSpeed;
	private int buffedArmour;
	private int buffedHealth;


	private int currentHealth;

	protected BufferedImage portrait;
	protected Map<String,Animation> animations;
	protected String animationName;
	protected Animation animation;

	protected LinkedList<Buff> buffs;
	private Targets targets;

	public BufferedImage getImage(){
		return animation.getSprite();
	}

	public BufferedImage getPortrait(){
		return portrait;
	}

	public String getAnimationName(){
		return animationName;
	}

	public void setAnimation(String name){
		Animation anim = animations.get(name);
		if (anim == null) Log.print("setting animation that doesn't exist for creature, animation name was " + name);
		else{

			this.animationName = name;
			this.animation = animations.get(name);

		}
	}

	public Creature(String imgName, Player player, int baseHealth, int baseArmour, int baseDamage) {
		this.baseHealth = baseHealth;
		this.baseArmour = baseArmour;
		this.baseDamage = baseDamage;
		this.portrait = ImageLoader.load(Constants.PORTRAITS + imgName);
		imgName = imgName.concat("_" + player.getColour());
		this.animations = ImageLoader.loadDirectedAnimations(Constants.ICONS + imgName);
		this.animation = animations.get("north");
		this.animationName = "north";
		buffs = new LinkedList<>();
	}

	public enum Targets{
		MELEE, RANGED, AOE;
	}

	/** GETTERS AND SETTERS **/

	/**
	 * Damage this creature by the amount specified.
	 * @param amount: amount by which to damage this creature.
	 */
	public void damage(int amount){
		if (amount < 0) return;
		int damage = amount - baseArmour + buffedArmour;
		currentHealth = Math.min(0,currentHealth-damage);
	}

	/**
	 * Heal this creature by the specified amount. Cannot be healed above base hit points.
	 * If the creature is dead, they don't get healed.
	 * @param magnitude: amount of hit points to heal.
	 */
	public void heal(int amount) {
		if (amount < 0 || currentHealth <= 0) return;
		currentHealth = Math.min(baseHealth, currentHealth+amount);
	}
	
	/**
	 * Revive the creature with the specified amount of health.
	 * If the creature is already alive, do nothing.
	 */
	public void revive(int amount){
		if (currentHealth == 0) this.currentHealth = Math.min(amount,baseHealth+buffedHealth);
	}

	public void fullHeal(){
		currentHealth = baseHealth + buffedHealth;
	}

	/**
	 * Heal this creature by the specified amount. The creature may be healed above their
	 * base hit points. If the creature is dead they don't get healed.
	 * @param amount: amount of hit points to heal.
	 */
	public void healOverload(int amount){
		if (amount < 0 || currentHealth <= 0) return;
		currentHealth = currentHealth+amount;
	}

	/**
	 * Regenerate this creature by 5% of their max hit points.
	 */
	public void regenHealth(){
		int hpToRegen = (int)(this.baseHealth/20);
		heal(hpToRegen);
	}

	/**
	 * Increase or decrease the units armour, damage, or speed by the specified amount.
	 * @param stat: stat to increase
	 * @param amount: amount to increase by (may be positive)
	 */
	public void tempBuff(Stat stat, int amount) {
		if (stat == Stat.ARMOUR){
			buffedArmour = buffedArmour + amount;
		}
		else if (stat == Stat.DAMAGE){
			buffedDamage = buffedDamage + amount;
		}
		else if (stat == Stat.SPEED){
			buffedSpeed = buffedSpeed + amount;
		}
	}

	/**
	 * Permanently increase or decrease the units armour, damage, or speed by the specified amount.
	 * @param stat: stat to increase
	 * @param amount: amount to increase by (may be negative)
	 */
	public void permaBuff(Stat stat, int amount) {
		if (stat == Stat.ARMOUR){
			baseArmour = baseArmour + amount;
		}
		else if (stat == Stat.DAMAGE){
			baseDamage = baseDamage + amount;
		}
		else if (stat == Stat.SPEED){
			baseSpeed = baseSpeed + amount;
		}
		else if (stat == Stat.HEALTH){
			baseHealth = baseHealth + amount;
		}
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

	public void setStat(Stat stat, int value){

		if (stat == Stat.ARMOUR){
			this.baseArmour = value;
		}
		else if (stat == Stat.DAMAGE){
			this.baseDamage = value;
		}
		else if (stat == Stat.HEALTH){
			this.baseHealth = value;
		}
		
	}

	/**
	 * Return the healthiness of this creature as a percentage
	 * @return: int
	 */
	public double healthiness(){
		int maxHP = this.baseHealth + this.buffedHealth;
		int currentHP = this.currentHealth;
		if (maxHP == 0) return 1.0;
		double r = (double)currentHP/(double)maxHP;
		return r;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

	public int getBaseDamage() {
		return baseDamage;
	}


	public int getBaseArmour() {
		return baseArmour;
	}


}
