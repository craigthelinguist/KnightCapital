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
	
	public void updateColours(String newColor){
		
		// we're looking for these colours
		List<String> colours = new ArrayList<>();
		colours.add("red"); colours.add("violet"); colours.add("blue"); colours.add("green");
		colours.remove(newColor);
		
		// load new KCImages
		List<KCImage> newKeys = new ArrayList<>();
		for (Map.Entry<String,KCImage> image : animations.entrySet()){
			String key = image.getKey();
			KCImage oldImage = image.getValue();
			String oldFilepath = oldImage.filepath;
			if (colours.contains(oldFilepath)){
				String newFilepath = substringExchange(oldFilepath,key,newColor);
				BufferedImage bi = ImageLoader.load(newFilepath);
				KCImage kcimage = new KCImage(bi, key, newFilepath);
				newKeys.add(kcimage);
			}
		}
		
		// store KCIMages in the map
		for (KCImage img : newKeys){
			this.animations.put(img.name, img);
		}
		
	}

	/**
	 * Get the substring old within the string string, and replace it with replacement.
	 * Return the new string.
	 * @param string: string whose substring you want to replace.
	 * @param old: substring being replaced
	 * @param replacement: replacement substring
	 * @return: a new string with the substring replaced.
	 */
	private static String substringExchange(String string, String old, String replacement){
		StringBuilder sb = new StringBuilder();
		int index = string.indexOf(old);
		for (int i = 0; i < index; i++){
			sb.append(string.charAt(i));
		}
		for (int i = 0; i < replacement.length(); i++){
			sb.append(replacement.charAt(i));
		}
		for (int i = index + old.length(); i < string.length(); i++){
			sb.append(string.charAt(i));
		}
		return sb.toString();
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
