package game.units;

public class HeroStats extends UnitStats{

	protected int baseSight;
	protected int baseMovement;

	protected int buffedSight;
	protected int buffedMovement;

	protected int currentMovement;

	public HeroStats(int health, int damage, int speed, int armour, int sight, int movement) {
		super(health, damage, speed, armour);
		baseMovement = movement;
		baseSight = sight;
		buffedMovement = 0;
		buffedSight = 0;
		currentMovement = baseMovement;
	}

	/**
	 * Set the buffed value for some stat.
	 * @param stat: stat whose buffed value you will change.
	 * @param value: new value of the stat.
	 */
	@Override
	public void setBuff(Stat stat, int value){
		if (stat == Stat.SIGHT) buffedSight = value;
		else if (stat == Stat.MOVEMENT) buffedMovement = value;
		else super.setBuff(stat, value);
	}

	/**
	 * Set the current value for some stat.
	 * @param stat: stat whose current value you will change.
	 * @param value: new value of the stat.
	 */
	@Override
	public void setCurrent(Stat stat, int value){
		if (stat == Stat.MOVEMENT) currentMovement = value;
		else super.setCurrent(stat, value);
	}

	/**
	 * Get the base value for some stat.
	 * @param stat: stat whose base value you want.
	 * @return: base value of that stat.
	 */
	@Override
	public int getBase(Stat stat){
		if (stat == Stat.SIGHT) return baseSight;
		else if (stat == Stat.MOVEMENT) return baseMovement;
		else return super.getBase(stat);
	}

	/**
	 * Get the buffed value for some stat.
	 * @param stat: stat whose buffed value you want.
	 * @return: buffed value of that stat.
	 */
	@Override
	public int getBuff(Stat stat){
		if (stat == Stat.SIGHT) return buffedSight;
		else if (stat == Stat.MOVEMENT) return buffedMovement;
		else return super.getBuff(stat);
	}

	/**
	 * Get the current value for some stat.
	 * @param stat: stat whose current value you want.
	 * @return: current value of that stat.
	 */
	public int getCurrent(Stat stat){
		if (stat == Stat.MOVEMENT) return currentMovement;
		else throw new RuntimeException("You're getting an unknown base stat for a unit");
	}

}
