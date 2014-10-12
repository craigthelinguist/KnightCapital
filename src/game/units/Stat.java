package game.units;

public enum Stat {

	/**
	 * BASE STATS
	 */

	// how much damage your unit can take.
	// if you're at 0 health, you're dead.
	HEALTH,

	// when your unit receives damage in combat
	// it is reduced by the value of their armour.
	ARMOUR,

	// specifies how much damage your unit
	// inflicts in combat
	DAMAGE,

	// specifies the order in which units take their turn
	// in battle.
	SPEED,


	/**
	 * HERO STATS: all heroes have these extra stats as well
	 */

	// how far your hero can you see on the world map
	SIGHT,

	// how far your hero can move on the world map each day
	MOVEMENT;
	
}
