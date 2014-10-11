package game.units;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import renderer.Animation;

public class AnimationMap {

	private Map<String,Animation> animations;
	private Animation animation;
	private String animationName;

	public AnimationMap(){
		animations = new HashMap<>();
		animation = null;
		animationName = null;
	}

	public void setAnimation(String name){
		Animation anim = animations.get(name);
		if (anim == null) return;
		animation = anim;
		animationName = name;
	}

	public void addImage(String name, BufferedImage image){
		Animation anim = new Animation(image);
		animations.put(name,anim);
	}

	public void addAnimation(String name, Animation animation){
		animations.put(name,animation);
	}

	public BufferedImage getPortrait(){
		return animations.get("portrait").getSprite();
	}

	public BufferedImage getImage(){
		return animation.getSprite();
	}

	public String getName(){
		return animationName;
	}

}
