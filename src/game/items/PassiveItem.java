package game.items;

import world.icons.Party;
import game.effects.Buff;
import game.effects.Effect;
import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.items.Target;


public class PassiveItem extends Item{

	public PassiveItem(String name, String imgName, String description, Effect[] effectsArray, Target target) {
		super(name,imgName,description,effectsArray,target);
	}

	/**
	 * Helper method. Either applies or unapplies all buffs to the
	 * given party.
	 * @param party: party whose buffs you'll apply/unapply
	 * @param applying: are you applying the buffs or removing them
	 */
	private void apply(Party party, boolean applying){
		for (int i = 0; i < effects.length; i++){
			Buff buff = (Buff)effects[i];
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

	@Override
	public boolean applyTo(Party party) {
		if (this.target != Target.PARTY) return false;
		apply(party,true);
		return true;
	}

	@Override
	public boolean applyTo(Creature creature) {
		if (this.target != Target.UNIT && this.target != Target.HERO) return false;
		if (creature == null) return true;
		for (Effect e : effects){
			creature.addBuff((Buff)e);
		}
		return true;
	}

	@Override
	public boolean removeFrom(Party party) {
		if (this.target != Target.PARTY) return false;
		apply(party,false);
		return true;
	}

	@Override
	public boolean removeFrom(Creature creature) {
		if (this.target != Target.UNIT) return false;
		for (Effect e : effects){
			creature.addBuff((Buff)e);
		}
		return true;
	}

}
