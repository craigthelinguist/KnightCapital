package game.items;

import game.effects.Buff;
import game.units.Hero;

/**
 * An equipped item must be equipped to your hero before its effect takes place.
 * e.g.: a sword that increases attack
 * @author Aaron Craig
 */
public class EquippedItem extends Item{

	public EquippedItem(String name, String imgName, String description, Buff[] buffs, Target target, String filename) {
		super(name,imgName,description,buffs,target,filename);
	}

	/**
	 * Equip this item and apply it's effect to a hero
	 * @param hero to get going
	 */
	public void equipTo(Hero hero) {
		apply(hero, true);
	}

	/**
	 * Remove this item and it's effects from a hero
	 * @param hero to remove item from
	 */
	public void unequipFrom(Hero hero) {
		apply(hero, false);
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
