package renderer;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AnimationMap {

	private Map<String,BufferedImage> animations;
	private BufferedImage animation;
	private String animationName;

	public AnimationMap(){
		animations = new HashMap<>();
		animation = null;
		animationName = null;
	}

	public void addImage(String name, BufferedImage image){
		animations.put(name,image);
	}

	public BufferedImage getPortrait(){
		return animations.get("portrait");
	}

	public BufferedImage getImage(){
		return animation;
	}

	public BufferedImage getImage(String name){
		return animations.get(name);
	}

	public void setImage(String name){
		BufferedImage image = animations.get(name);
		if (image != null){
			animation = image;
			animationName = name;
		}
	}

	public String getName(){
		return animationName;
	}

}
