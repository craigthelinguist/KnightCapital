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
	public static Map<String,Animation> loadDirectedAnimations(String filepath){

		BufferedImage[] north = new BufferedImage[]{ ImageLoader.load(filepath + "_north.png") };
		Animation animNorth = new Animation(north,1);
		BufferedImage[] south = new BufferedImage[]{ ImageLoader.load(filepath + "_south.png") };
		Animation animSouth = new Animation(south,1);
		BufferedImage[] east = new BufferedImage[]{ ImageLoader.load(filepath + "_east.png") };
		Animation animEast = new Animation(east,1);
		BufferedImage[] west = new BufferedImage[]{ ImageLoader.load(filepath + "_west.png") };
		Animation animWest = new Animation(west,1);

		Map<String,Animation> animationNames = new HashMap<String,Animation>();
		animationNames.put("north",animNorth);
		animationNames.put("south",animSouth);
		animationNames.put("west",animWest);
		animationNames.put("east",animEast);
		return animationNames;
	}

	public static Animation loadAnimation(String name) {
		BufferedImage bi = ImageLoader.load(name);
		return new Animation(new BufferedImage[]{bi},1);
	}

}
