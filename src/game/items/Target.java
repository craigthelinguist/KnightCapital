package game.items;

/**
 * Who does the item apply to?
 * @author Aaron Craig
 */
public enum Target {

	// only the hero
	HERO,

	// everyone in the party
	PARTY,
	PARTY_RANGED,
	PARTY_MELEE,
	
	// melee only
	MELEE,
	
	// one unit
	UNIT;

}
