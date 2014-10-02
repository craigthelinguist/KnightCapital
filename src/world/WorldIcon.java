package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import renderer.Animation;
import tools.GlobalConstants;
import tools.ImageLoader;
import tools.Log;

/**
 * An icon is anything that can occupy a tile on the world map (a party, an item, a building etc.)
 * @author craigthelinguist
 */
public abstract class WorldIcon {
	
	private Animation animation;
	private Map<String,Animation> animationNames;
	private String animationName;
	
	public WorldIcon(String imgname){
		final String filepath = GlobalConstants.ASSETS + imgname;
		animationNames = ImageLoader.loadAnimations(filepath);
		animation = animationNames.get("west");
		animationName = "west";
	}
	
	public BufferedImage getImage(){
		if (animation.getSprite() == null) System.out.println("FUCK");
		return animation.getSprite();
	}
	
	public void setAnimationName(String name){
		if (name.equals(animationName)) return;
		Animation anim = animationNames.get(name);
		if (anim == null) Log.print("couldn't find animation name" + name);
		else{
			animationName = name;
			animation = anim;
		}
	}

	public void draw(Graphics graphics, int iconX, int iconY) {
		graphics.drawImage(animation.getSprite(),iconX,iconY,null);
	}
	
}
