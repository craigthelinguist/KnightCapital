package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import renderer.Animation;

public class ImageLoader {

	// use the static methods
	private ImageLoader(){}

	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	/**
	 * Attempts to retrieve the image specified, if it is yet to be loaded then it is loaded and returned.
	 * If the file is invalid then a 1x1 image is returned.
	 * @param filename The File to be retrieved
	 * @return An Image from the file
	 */


	public static BufferedImage load(String filename){

		if (!filename.endsWith(".png")){
			filename = filename.concat(".png");
		}

		//If the image already exists then return
		if(images.containsKey(filename)){
			return images.get(filename);
		//Else load the image and return
		}else{
			try{
				//Loads the image
				File file = new File(filename);
				if(file == null){
					System.out.println(filename+" failed to load");
					return new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
				}
				BufferedImage newImage = ImageIO.read(file);
				//Stores the image
				images.put(filename, newImage);
				return newImage;
			}catch(IOException e){
				//Display an error and return a 1x1 Image
				System.err.println("Error loading file \""+filename+"\"\n");
				return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			}
		}
	}

	/**
	 * Given an array of filenames, loads and returns those images.
	 * @param filenames: array of the names of the images to load.
	 * @param frameDelay: delay between each image.
	 * @return
	 */
	public static Animation loadAnimations(String[] filenames, int frameDelay){
		BufferedImage[] images = new BufferedImage[filenames.length];
		for (int i = 0; i < filenames.length; i++){
			if (images == null) break;
			images[i] = load(filenames[i]);
		}
		return new Animation(images,frameDelay);
	}

	/**
	 * Takes the given filepath. Loads all files prefixed by that filepath into a map,
	 * where they're indexed by the name of their animation (e.g.: north, south).
	 * @param filepath: load all files prefixed with this.
	 * @return: map of string -> animation
	 */
	public static Map<String,BufferedImage> loadDirectedImages(String filepath){
		Map<String,BufferedImage> map = new HashMap<>();
		map.put("north", ImageLoader.load(filepath + "_north.png"));
		map.put("south", ImageLoader.load(filepath + "_south.png"));
		map.put("east", ImageLoader.load(filepath + "_east.png"));
		map.put("west", ImageLoader.load(filepath + "_west.png"));
		return map;
	}

	public static Animation loadAnimation(String name) {
		BufferedImage bi = ImageLoader.load(name);
		return new Animation(new BufferedImage[]{bi},1);
	}

	/**
	 * Use this if your file extension is not .png
	 * @param filename
	 * @param extension
	 * @return
	 */
	public static BufferedImage load(String filename, String extension) {

		filename = filename + extension;

		//If the image already exists then return
		if(images.containsKey(filename)){
			return images.get(filename);
		//Else load the image and return
		}else{
			try{
				//Loads the image
				File file = new File(filename);
				if(file == null){
					System.out.println(filename+" failed to load");
					return new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
				}
				BufferedImage newImage = ImageIO.read(file);
				//Stores the image
				images.put(filename, newImage);
				return newImage;
			}catch(IOException e){
				//Display an error and return a 1x1 Image
				System.err.println("Error loading file \""+filename+"\"\n");
				return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			}
		}
	}

}
