package renderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tools.ImageLoader;
import tools.KCImage;

/**
 * AnimationMap is a collection of string -> images. You can ask it for the name of an image and it will
 * return that image. It's a way to store lots of images for something.
 * @author Aaron Craig
 */
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
		if(color != null) {
			filepath = baseFilepath + "_" + color + "_north.png";
		}
		else {
			filepath = baseFilepath + "_north.png";
		}
		BufferedImage imgNorth = ImageLoader.load(filepath);
		KCImage kcNorth = new KCImage(imgNorth, "north", filepath);
		animations.put("north", kcNorth);

		if(color != null) {
			filepath = baseFilepath + "_" + color + "_south.png";
		}
		else {
			filepath = baseFilepath + "_south.png";
		}
		BufferedImage imgsouth = ImageLoader.load(filepath);
		KCImage kcsouth = new KCImage(imgsouth, "south", filepath);
		animations.put("south", kcsouth);

		if(color != null) {
			filepath = baseFilepath + "_" + color + "_east.png";
		}
		else {
			filepath = baseFilepath + "_east.png";
		}
		BufferedImage imgeast = ImageLoader.load(filepath);
		KCImage kceast = new KCImage(imgeast, "east", filepath);
		animations.put("east", kceast);

		if(color != null) {
			filepath = baseFilepath + "_" + color + "_west.png";
		}
		else {
			filepath = baseFilepath + "_west.png";
		}
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

			for (String col : colours){
				if (oldFilepath.contains(col)){
					String newFilepath = substringExchange(oldFilepath,col,newColor);
					BufferedImage bi = ImageLoader.load(newFilepath);
					KCImage kcimage = new KCImage(bi, key, newFilepath);
					newKeys.add(kcimage);
				}
			}

		}

		// store KCIMages in the map
		for (KCImage img : newKeys){
			this.animations.put(img.name, img);
		}

		this.animation = this.animations.get(this.animationName);

	}

	/**
	 * Get the substring old within the string string, and replace it with replacement.
	 * Return the new string. When a player's
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

	/**
	 * Get the image associated with the word "portrait". This is a convenience method
	 * as you often want to get the "portrait" image.
	 * @return: buffered image
	 */
	public BufferedImage getPortrait(){
		return animations.get("portrait").image;
	}

	/**
	 * Get the current image of this animation map.
	 * @return buffered image
	 */
	public BufferedImage getImage(){
		return animation.image;
	}

	/**
	 * Get the image associated with the supplied name.
	 * @param name: name of image to get
	 * @return: the associated image
	 */
	public BufferedImage getImage(String name){
		return animations.get(name).image;
	}

	/**
	 * Set the current image to be the one with the given name.
	 * @param name: name of image to set.
	 */
	public void setImage(String name){
		KCImage image = animations.get(name);
		if (image != null){
			animation = image;
			animationName = name;
		}
	}

	/**
	 * Get the name of the current image.
	 * @return: string
	 */
	public String getName(){
		return animationName;
	}

	/**
	 * Return the images in this animation map as a list of KC images.
	 * @return list<KCImage.
	 */
	public List<KCImage> asList(){
		List<KCImage> images = new ArrayList<>();
		for (KCImage image : animations.values()){
			images.add(image);
		}
		return images;
	}

	public void rotate(boolean clockwise) {
		
		// get current orientation
		int playerDir;
		if (animationName == null) return;
		if (animationName.contains("north")) playerDir = Camera.NORTH;
		else if (animationName.contains("east")) playerDir = Camera.EAST;
		else if (animationName.contains("south")) playerDir = Camera.SOUTH;
		else if (animationName.contains("west")) playerDir = Camera.WEST;
		else throw new RuntimeException("Unknown animation name when rotating animation map: " + animationName);
		
		// rotate
		if (clockwise) playerDir = (playerDir+1)%4;
		else if (playerDir == 0) playerDir = 3;
		else playerDir = playerDir - 1;
		
		// set new orientation
		if (playerDir == Camera.NORTH) this.setImage("north");
		else if (playerDir == Camera.EAST) this.setImage("east");
		else if (playerDir == Camera.SOUTH) this.setImage("south");
		else if (playerDir == Camera.WEST) this.setImage("west");
		else throw new RuntimeException("Unknown animation name for rotated image: " + animationName);
		
		
	}


}
