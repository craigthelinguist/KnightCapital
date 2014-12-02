package game.items;

import game.effects.Effect;
import game.units.Creature;

import java.awt.image.BufferedImage;

import renderer.AnimationMap;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import world.icons.Party;

/**
 * An item is an array of effects. They have some target to which they apply (e.g.: entire party, single unit, single hero).
 * @author Aaron Craig ft. Ewan Moshi
 */
public abstract class Item {

	// name of the item
	private String name;

	// description of item
	private String description;

	// all the effects this item has
	protected Effect[] effects;

	// info about item's drawing
	private String imageName;
	protected AnimationMap animations;

	// who this item's effects apply to
	protected Target target;

	/**
	 * Create and return new Item.
	 * @param name: item's name
	 * @param imgName: base filepath name of item's assets
	 * @param description: description of item
	 * @param effects: array of effects that this item has.
	 * @param target: who this item's effects should be applied to.
	 * @param filename: deprecated; remnant of data loading/saving system.
	 */
	public Item(String name, String imgName, String description, Effect[] effects, Target target){
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
	
	public abstract boolean applyTo(Party p);
	public abstract boolean applyTo(Creature c);
	public abstract boolean removeFrom(Party p);
	public abstract boolean removeFrom(Creature c);
	
	/**
	 * Return the image representing this item.
	 * @return: bufferedimage
	 */
	public BufferedImage getImage(){
		return animations.getImage("regular");
	}

	/**
	 * Return the image representing this image in the gui.
	 * @return: bufferedimage
	 */
	public BufferedImage getPortrait() {
		return animations.getImage("portrait");
	}

	/**
	 * Return the base name of the assets representing this item.
	 * @return: string
	 */
	public String getImageName(){
		return this.imageName;
	}

	/**
	 * Return the target to whom this item's effects apply.
	 * @return: target enum
	 */
	public Target getTarget(){
		return this.target;
	}

	/**
	 * Get the name of the current image representing this item.
	 * @return: string
	 */
	public String getAnimationName() {
		return animations.getName();
	}

	/**
	 * Get the name of this item
	 * @return: string
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the description of this item
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Set the image representing this item.
	 * @param name: name fo the image
	 */
	public void setAnimation(String name){
		animations.setImage(name);
	}

	/**
	 * Return a description of this item.
	 * @return string
	 */
	public String toString(){
		return description;
	}

	/**
	 * Returns this :^)
	 * @return this
	 * @author Ewan Moshi
	 */
	public Item getItem() {
		return this;
	}

	/**
	 * Get the array of effects in this item.
	 * @return: array of effects
	 */
	public Effect[] getEffects() {
		return effects;
	}

	/**
	 * Return a text representation of the kind of item this is.
	 * @return: string
	 */
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
