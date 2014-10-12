package game.items;

import game.effects.Buff;

public class PassiveItem extends Item{

	public PassiveItem(Buff[] buffs, String imgName, String name, String description) {
		super(imgName,name,description,buffs);
	}

}
