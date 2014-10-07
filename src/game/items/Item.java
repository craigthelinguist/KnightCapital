package game.items;

import game.effects.Effect;
import renderer.Animation;
import tools.ImageLoader;

public abstract class Item {

	private Effect[] effects;
	private Animation animation;
	
	public Item(String imgName, Effect[] fx){
		animation = ImageLoader.loadAnimation(imgName);
		effects = fx;
	}
	
}
