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
	protected Buff[] buffs;
	private String imgName;
	protected AnimationMap animations;
	protected Target target;

	public Item(String name, String imgName ,String description, Buff[] buffs, Target target){
		this.name = name;
		this.description = description;
		this.animations = new AnimationMap();
		this.animations.addImage("portrait", Constants.ITEMS + imgName, ImageLoader.load(Constants.ITEMS + imgName));
		this.animations.addImage("regular", Constants.ITEMS + imgName, ImageLoader.load(Constants.ITEMS + imgName));
		this.buffs = buffs;
		if (buffs == null) buffs = new Buff[0];
		this.target = target;
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

	public Buff[] getBuffs() {
		return buffs;
	}


}
