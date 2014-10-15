package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import renderer.Animation;

/**
 * Provides convenience methods for loading images.
 * @author Aaron Craig
 */
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


	public static BufferedImage load(String base){

		String filename = base;

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
					return new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
				}
				BufferedImage bufferedImage = ImageIO.read(file);
				//Stores the image
				images.put(filename, bufferedImage);
				return bufferedImage;
			}catch(IOException e){
				//Display an error and return a 1x1 Image
				System.err.println("Error loading file \""+filename+"\"\n");
				return null;
			}
		}
	}

	/**
	 * Use this if your file extension is not .png
	 * @param filename
	 * @param extension
	 * @return
	 */
	public static BufferedImage load(String base, String extension) {

		String filename = base + extension;

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
					BufferedImage bufferedImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
					return bufferedImage;
				}
				BufferedImage bufferedImage = ImageIO.read(file);
				//Stores the image
				images.put(filename, bufferedImage);
				return bufferedImage;
			}catch(IOException e){
				//Display an error and return a 1x1 Image
				System.err.println("Error loading file \""+filename+"\"\n");
				return new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
			}
		}
	}

}
