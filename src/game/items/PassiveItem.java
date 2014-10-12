package game.items;

import world.icons.Party;
import game.effects.Buff;
import game.units.Creature;
import game.units.Hero;

public class PassiveItem extends Item{

	public PassiveItem(Buff[] buffs, String imgName, String name, String description, Target target) {
		super(name,imgName,description,buffs,target);
		
		
		/*
		for (int i = 0; i < buffs.length; i++){
			Buff b = buffs[i];
			if (b.target != Target.HERO || b.target != Target.PARTY){
				throw new RuntimeException("A passiveitem can only have buffs that affect the hero or the entire party");
			}
		}
		*/
	}
	
	/**
	 * Apply all of the buffs on this passive item to their target.
	 * @param party: party to whom the buffs will be applied.
	 */
	public void applyEffectsTo(Party party){
		apply(party,true);
	}
	
	/**
	 * Remove all of the buffs on this passive item from their target.
	 * @param party: party whose buffs you will remove.
	 */
	public void removeEffectsFrom(Party party){
		apply(party,false);
	}
	
	/**
	 * Helper method. Either applies or unapplies all buffs to the 
	 * given party.
	 * @param party: party whose buffs you'll apply/unapply
	 * @param applying: are you applying the buffs or removing them
	 */
	private void apply(Party party, boolean applying){
		for (int i = 0; i < buffs.length; i++){
			Buff buff = buffs[i];
			if (this.target == Target.PARTY){
				for (Creature creature : party){
					if (applying) creature.addBuff(buff);
					else creature.removeBuff(buff);
				}
			}
			else if (this.target == Target.HERO){
				Hero hero = party.getHero();
				if (applying) hero.addBuff(buff);
				else hero.removeBuff(buff);
			}
			else throw new RuntimeException("I don't know how to apply this buff!");
		}
	}

}
