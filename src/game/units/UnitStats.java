package game.units;

public class UnitStats implements Stats{

	protected int baseHealth;
	protected int baseDamage;
	protected int baseSpeed;
	protected int baseArmour;

	protected int buffedDamage;
	protected int buffedSpeed;
	protected int buffedArmour;
	protected int buffedHealth;

	protected int currentHealth;

	public UnitStats(int health, int damage, int speed, int armour){
		baseHealth = health;
		baseDamage = damage;
		baseSpeed = speed;
		baseArmour = armour;
		currentHealth = baseHealth;
		buffedDamage = 0;
		buffedSpeed = 0;
		buffedArmour = 0;
		buffedHealth = 0;
	}

	/**
	 * Set the base value for some stat.
	 * @param stat: stat whose base value you will change.
	 * @param value: new value of the stat.
	 */
	public void setBase(Stat stat, int value){
		if (stat == Stat.HEALTH) baseHealth = value;
		else if (stat == Stat.DAMAGE) baseDamage = value;
		else if (stat == Stat.SPEED) baseSpeed = value;
		else if (stat == Stat.ARMOUR) baseArmour = value;
		else{
			throw new RuntimeException("you're setting an unknown stat for a unit");
		}
	}

	/**
	 * Set the buffed value for some stat.
	 * @param stat: stat whose buffed value you will change.
	 * @param value: new value of the stat.
	 */
	public void setBuff(Stat stat, int value){
		if (stat == Stat.HEALTH) buffedHealth = value;
		else if (stat == Stat.DAMAGE) buffedDamage = value;
		else if (stat == Stat.SPEED) buffedSpeed = value;
		else if (stat == Stat.ARMOUR) buffedArmour = value;
		else{
			throw new RuntimeException("you're setting an unknown buffedStat for a unit");
		}
	}

	/**
	 * Set the current value for some stat.
	 * @param stat: stat whose current value you will change.
	 * @param value: new value of the stat.
	 */
	public void setCurrent(Stat stat, int value){
		if (stat == Stat.HEALTH) currentHealth = value;
		else{
			throw new RuntimeException("you're setting an unknown current stat for a unit");
		}
	}

	/**
	 * Get the base value for some stat.
	 * @param stat: stat whose base value you want.
	 * @return: base value of that stat.
	 */
	public int getBase(Stat stat){
		if (stat == Stat.HEALTH) return baseHealth;
		else if (stat == Stat.DAMAGE) return baseDamage;
		else if (stat == Stat.SPEED) return baseSpeed;
		else if (stat == Stat.ARMOUR) return baseArmour;
		else throw new RuntimeException("You're getting an unknown base stat for a unit");
	}

	/**
	 * Get the buffed value for some stat.
	 * @param stat: stat whose buffed value you want.
	 * @return: buffed value of that stat.
	 */
	public int getBuff(Stat stat){
		if (stat == Stat.HEALTH) return buffedHealth;
		else if (stat == Stat.DAMAGE) return buffedDamage;
		else if (stat == Stat.SPEED) return buffedSpeed;
		else if (stat == Stat.ARMOUR) return buffedArmour;
		else throw new RuntimeException("You're getting an unknown base stat for a unit");
	}

	/**
	 * Get the current value for some stat.
	 * @param stat: stat whose current value you want.
	 * @return: current value of that stat.
	 */
	public int getCurrent(Stat stat){
		if (stat == Stat.HEALTH) return currentHealth;
		else throw new RuntimeException("You're getting an unknown base stat for a unit");
	}

	/**
	 * Get the total value of some stat, which is the buffed + base.
	 * @param stat: stat whose total value you want.
	 * @return: total value of that stat.
	 */
	public int getTotal(Stat stat) {
		if (stat == Stat.HEALTH) return baseHealth + buffedHealth;
		else if (stat == Stat.ARMOUR) return baseArmour + buffedArmour;
		else if (stat == Stat.DAMAGE) return baseDamage + buffedDamage;
		else if (stat == Stat.SPEED) return baseSpeed + buffedSpeed;
		else throw new RuntimeException("You're getting an unknown stat for a unit");
	}

}
