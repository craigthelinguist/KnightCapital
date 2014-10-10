package game.items;

import game.effects.Buff;


import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Map;

import renderer.Animation;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;

public abstract class Item {

	private BufferedImage portrait;
	private Map<String,Animation> animations;
	private String animationName;
	private Animation animation;
	private String name;
	private String description;
	protected LinkedList<Buff> buffs;
	private String imgName;

	public Item(String imgName, String name ,String description){
		this.name = name;
		this.description = description;
		this.animation = ImageLoader.loadAnimation(Constants.ITEMS + imgName);
		this.portrait = ImageLoader.load(Constants.ITEMS + imgName);
		this.buffs = new LinkedList<>();
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

	public String getName() {
		return this.name;
	}

	public String getDescription() {
			return this.description;
	}

	public void setAnimation(String name){
		Animation anim = animations.get(name);
		if (anim == null) Log.print("setting invalid animation name for item, name was " + name);
		else animation = anim;
	}

	public String toString(){
		return description;
	}

	public Item getItem() {
		return this;
	}

}
