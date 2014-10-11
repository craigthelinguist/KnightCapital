package game.items;

import game.effects.Buff;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Map;

import renderer.Animation;
import renderer.AnimationMap;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;

public abstract class Item {

	private String name;
	private String description;
	protected LinkedList<Buff> buffs;
	private String imgName;
	protected AnimationMap animations;

	public Item(String imgName, String name ,String description){
		this.name = name;
		this.description = description;
		this.animations = new AnimationMap();
		this.animations.addImage("portrait", ImageLoader.load(Constants.ITEMS + imgName));
		this.animations.addImage("regular", ImageLoader.load(Constants.ITEMS + imgName));
		this.buffs = new LinkedList<>();
	}

	public BufferedImage getImage(){
		return animations.getImage("regular");
	}

	public BufferedImage getPortrait() {
		return animations.getImage("portrait");
	}

	public String getAnimationName() {
		return animations.getName();
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setAnimation(String name){
		animations.setImage(name);
	}

	public String toString(){
		return description;
	}

	public Item getItem() {
		return this;
	}

	public LinkedList<Buff> getBuffs() {
		return buffs;
	}


}
