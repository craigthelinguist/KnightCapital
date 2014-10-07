package game.units;
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
	private int currentDamage;
	private int currentSpeed;
	private int currentArmour;

	private List<Buff> buffs;
	private Targets targets;
	
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
		currentHealth = currentHealth-amount;
	}
	
	/**
	 * Heal this creature by the specified amount. Cannot be healed above base hit points.
	 * @param magnitude: amount of hit points to heal.
	 */
	public void heal(int amount) {
		if (amount < 0) return;
		currentHealth = Math.min(baseHealth, currentHealth+amount);
	}
	
	/**
	 * Heal this creature by the specified amount. The creature may be healed above their
	 * base hit points.
	 * @param amount: amount of hit points to heal.
	 */
	public void healOverload(int amount){
		if (amount < 0) return;
		currentHealth = currentHealth+amount;
	}

	/**
	 * Increase or decrease the units armour, damage, or speed by the specified amount.
	 * @param stat: stat to increase
	 * @param amount: amount to increase by (may be positive)
	 */
	public void buff(Stat stat, int amount) {
		if (stat == Stat.ARMOUR){
			currentArmour = currentArmour + amount;
		}
		else if (stat == Stat.DAMAGE){
			currentDamage = currentDamage + amount;
		}
		else if (stat == Stat.SPEED){
			currentSpeed = currentSpeed + amount;
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
	
}
