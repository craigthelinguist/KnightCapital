package game.units;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	
	private int currentHealth;
	private int buffedDamage;
	private int buffedSpeed;
	private int buffedArmour;
	
	protected LinkedList<Buff> buffs;
	private Targets targets;
	
	public Creature(){
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
	
}
