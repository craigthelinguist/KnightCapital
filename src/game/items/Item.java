package game.items;

import java.awt.image.BufferedImage;
import java.util.Map;

import renderer.Animation;
import tools.GlobalConstants;
import tools.ImageLoader;
import tools.Log;

public abstract class Item {

	private BufferedImage portrait;
	private Map<String,Animation> animations;
	private String animationName;
	private Animation animation;


	public Item(String imgName){
		animation = ImageLoader.loadAnimation(GlobalConstants.ITEMS + imgName);
		portrait = ImageLoader.load(GlobalConstants.ITEMS + imgName);
	}

	public BufferedImage getImage(){
		return animation.getSprite();
	}

	public BufferedImage getPortrait() {
		return portrait;
	}

	public String getAnimationName() {
		return animationName;
	}

	public void setAnimation(String name){
		Animation anim = animations.get(name);
		if (anim == null) Log.print("setting invalid animation name for item, name was " + name);
		else animation = anim;
	}

}
