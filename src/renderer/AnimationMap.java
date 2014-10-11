package renderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tools.ImageLoader;
import tools.KCImage;

public class AnimationMap{

	private Map<String,KCImage> animations;
	private KCImage animation;
	private String animationName;

	/**
	 * For data loading only. Don't use.
	 * @param images
	 */
	public AnimationMap(List<KCImage> images){
		this.animations = new HashMap<>();
		for (KCImage img : images){
			animations.put(img.name, img);
		}
	}

	public AnimationMap(){
		animations = new HashMap<>();
		animation = null;
		animationName = null;
	}

	public void addImage(String name, String filepath, BufferedImage image){
		animations.put(name,new KCImage(image,name,filepath));
	}

	/**
	 * Load the directed versions of a given base image. For example, let's say you wanted to load ovelia_red_north.png, etc.
	 * you would supply the arguments:
	 *
	 * base = ovelia
	 * color = red
	 * filepath = assets/icons/
	 *
	 * @param directory: name of directory image is located in
	 * @param base: base name of image
	 * @param color: player colour to append onto base
	 */
	public void addDirectedImages(String directory, String base, String color){
		String baseFilepath = directory + base;
		String filepath;

		filepath = baseFilepath + "_" + color + "_north.png";
		BufferedImage imgNorth = ImageLoader.load(filepath);
		KCImage kcNorth = new KCImage(imgNorth, "north", filepath);
		animations.put("north", kcNorth);

		filepath = baseFilepath + "_" + color + "_south.png";
		BufferedImage imgsouth = ImageLoader.load(filepath);
		KCImage kcsouth = new KCImage(imgsouth, "south", filepath);
		animations.put("south", kcsouth);

		filepath = baseFilepath + "_" + color + "_east.png";
		BufferedImage imgeast = ImageLoader.load(filepath);
		KCImage kceast = new KCImage(imgeast, "east", filepath);
		animations.put("east", kceast);

		filepath = baseFilepath + "_" + color + "_west.png";
		BufferedImage imgwest = ImageLoader.load(filepath);
		KCImage kcwest = new KCImage(imgwest, "west", filepath);
		animations.put("west", kcwest);
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

	public List<KCImage> asList(){
		List<KCImage> images = new ArrayList<>();
		for (KCImage image : animations.values()){
			images.add(image);
		}
		return images;
	}

}
