package world.icons;

import java.awt.image.BufferedImage;

import renderer.AnimationMap;
import tools.Constants;
import tools.ImageLoader;

/**
 * Decor Icon is used to create visual elements within the game world.
 * Things such as trees are the intended use for this class.
 * This is needed because using full tiles for these objects is not working correctly.
 * @author myles
 *
 */
public class DecorIcon extends WorldIcon{

	private boolean passable; // can the tile this icon is standing on be traversed
	private AnimationMap animations;
	private int type;

	public static final int TREE = 1;
	public static final int ROCK = 2;
	public static final int BUSH = 3;

	public DecorIcon(int type) {
		animations = new AnimationMap();
		this.type = type;

		int max = 1; // The amount of variations a decoration has
		String name = ""; // name of the image file, minus orientation and file extension

		// Check the type of icon to be used
		// and set the correct filepath
		// And if the icon is passable or not

		if(type == TREE) {
			name = "tree_icon";
			max = 3;
			passable = false;
		}
		else if(type == ROCK) {
			name = "rock_icon";
			max = 2;
			passable = false;
		}
		else if(type == BUSH) {
			name = "bush_icon";
			max = 3;
			passable = false;
		}
		else {
			System.out.println("INCORRECT DecorIcon type, will now throw shit at the walls");
		}

		// Using the max variation defined above, we can randomly select an image from the specified range
		// to give the world map a less uniform look, getting rid of some of the tough to look at tiling textures
		int random = 1 + (int)(Math.random() * max);
		String fullname = name + "_" + random; // set name and random

		// Add portrait to icon
		animations.addImage("portrait", Constants.PORTRAITS, ImageLoader.load(Constants.PORTRAITS + name));

		// Add each of the directional images needed for file
		animations.addDirectedImages(Constants.DECOR_ICONS, fullname, null);

		// Set init image
		animations.setImage("north");
	}

	/**
	 * Set the current animation frame
	 * i.e. "north" would set the north facing sprite
	 */
	@Override
	public void setAnimationName(String name) {
		animations.setImage(name);
	}

	/**
	 * Get the current name of the current animation sprite
	 */
	@Override
	public String getAnimationName() {
		return animations.getName();
	}

	/**
	 * Get the current animation frame for rendering
	 */
	@Override
	public BufferedImage getImage() {
		return animations.getImage();
	}

	/**
	 * Get the icons portrait for display in tile info panel
	 */
	@Override
	public BufferedImage getPortrait() {
		return animations.getPortrait();
	}

	/**
	 * Can this icon be walked over
	 * @return
	 */
	public boolean passable() {
		return passable;
	}

	/**
	 * get the type this thing is, see Constants up top boi
	 */
	public int getType() {
		return this.type;
	}
}
