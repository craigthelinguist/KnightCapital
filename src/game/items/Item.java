package game.items;

import renderer.Animation;
import tools.ImageLoader;

public abstract class Item {

	private Animation animation;
	
	public Item(String imgName){
		animation = ImageLoader.loadAnimation(imgName);
	}
	
}
