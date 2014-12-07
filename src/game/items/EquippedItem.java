package game.items;

import world.icons.Party;
import game.effects.Buff;
import game.units.creatures.Creature;
import game.units.creatures.Hero;

/**
 * An equipped item must be equipped to your hero before its effect takes place.
 * e.g.: a sword that increases attack
 * @author Aaron Craig
 */
public class EquippedItem extends Item{

	public EquippedItem(String name, String imgName, String description, Buff[] buffs, Target target) {
		super(name,imgName,description,buffs,target);
	}

	@Override
	public boolean applyTo(Creature creature) {
		if (!(creature instanceof Hero)) return false;
		apply((Hero)creature, true);
		return true;
	}
	
	/**
	 * Cannot apply EquippedItem's to party.
	 */
	@Override
	public boolean applyTo(Party p){
		throw new UnsupportedOperationException("Cannot apply equipped item " + this + " to " + p);
	}
	

	/**
	 * Cannot remove EquippedItem's from a party.
	 */
	@Override
	public boolean removeFrom(Party p){
		throw new UnsupportedOperationException("Cannot remmove equipped item " + this + " from " + p);
	}
	/**
	 * Remove this item and it's effects from a hero
	 * @param hero to remove item from
	 */
	public boolean removeFrom(Creature creature) {
		if (!(creature instanceof Hero)) return false;
		apply((Hero)creature, false);
		return true;
	}

	/**
	 * Applies or Removes an equipped item to a hero.
	 * Get's buffs from the item
	 * if apply = true, adds buff to hero. etc
	 * @param hero to be buffed
	 * @param applying : true for application, false for removal
	 */
	private void apply(Hero hero, boolean applying) {
		for(int i = 0; i < effects.length; i++) {
			Buff buff = (Buff)effects[i];
			if(applying) hero.addBuff(buff);
			else hero.removeBuff(buff);
		}
	}


}
