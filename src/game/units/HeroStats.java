package game.units;

public class HeroStats extends UnitStats{

	protected int baseSight;
	protected int baseMovement;

	protected int buffedSight;
	protected int buffedMovement;

	public HeroStats(int health, int damage, int speed, int armour, int sight, int movement, AttackType type) {
		super(health, damage, speed, armour, type);
		baseMovement = movement;
		baseSight = sight;
		buffedMovement = 0;
		buffedSight = 0;
	}

	public void setBase(Stat stat, int value){
		if (stat == Stat.SIGHT) baseSight = value;
		else if (stat == Stat.MOVEMENT) baseMovement = value;
		else super.setBase(stat, value);
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
	 * Get the total value of some stat, which is the buffed + base.
	 * @param stat: stat whose total value you want.
	 * @return: total value of that stat.
	 */
	@Override
	public int getTotal(Stat stat) {
		if (stat == Stat.SIGHT) return baseSight + buffedSight;
		else if (stat == Stat.MOVEMENT) return baseMovement + buffedMovement;
		else return super.getTotal(stat);
	}

}
