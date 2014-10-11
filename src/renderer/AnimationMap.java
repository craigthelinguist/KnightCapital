package renderer;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import tools.KCImage;

public class AnimationMap {

	private Map<String,KCImage> animations;
	private KCImage animation;
	private String animationName;

	public AnimationMap(){
		animations = new HashMap<>();
		animation = null;
		animationName = null;
	}

	public void addImage(String name, BufferedImage image){
		animations.put(name,new KCImage(image,name));
	}

	public BufferedImage getPortrait(){
		return animations.get("portrait").image;
	}

	public BufferedImage getImage(){
		return animation.image;
	}

	public BufferedImage getImage(String name){
		return animations.get(name).image;
	}

	public void setImage(String name){
		KCImage image = animations.get(name);
		if (image != null){
			animation = image;
			animationName = name;
		}
	}

	public String getName(){
		return animationName;
	}

}
