package game.items;

import game.effects.Buff;

/**
 * An equipped item must be equipped to your hero before its effect takes place.
 * @author craigthelinguist
 */
public class EquippedItem {

	private Buff[] buffs;
	
	public EquippedItem(Buff[] buffs){
		this.buffs = buffs;
	}

	public Buff[] getBuffs() {
		return buffs;
	}

}
