package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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
		//If the image already exists then return
		if(images.containsKey(filename)){
			return images.get(filename);
		//Else load the image and return
		}else{
			try{
				//Loads the image
				File file = new File(GlobalConstants.ASSETS+ File.separatorChar +filename);
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
