package game.items;

import game.effects.Buff;
import game.effects.Effect;

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
	protected Effect[] effects;
	private String imageName;
	protected AnimationMap animations;
	protected Target target;

	public Item(String name, String imgName ,String description, Effect[] effects, Target target){
		this.name = name;
		this.imageName = imgName;
		this.description = description;
		this.animations = new AnimationMap();
		this.animations.addImage("portrait", Constants.ITEMS + imgName, ImageLoader.load(Constants.ITEMS + imgName));
		this.animations.addImage("regular", Constants.ITEMS + imgName, ImageLoader.load(Constants.ITEMS + imgName));
		this.effects = effects;
		if (this.effects == null) this.effects = new Effect[0];
		this.target = target;
	}

	public BufferedImage getImage(){
		return animations.getImage("regular");
	}

	public BufferedImage getPortrait() {
		return animations.getImage("portrait");
	}

	public String getImageName(){
		return this.imageName;
	}
	
	public Target getTarget(){
		return this.target;
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

	public Effect[] getEffects() {
		return effects;
	}
	
	public String getClassAsString(){
		if (this instanceof PassiveItem) return "passive";
		else if (this instanceof ChargedItem) return "charged";
		else if (this instanceof EquippedItem) return "equipped";
		else{
			Log.print("[Item] item's type is unrecognised, cannot return as string");
			return null;
		}
	}


}
