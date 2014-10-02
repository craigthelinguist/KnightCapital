package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * SpriteSheet is used to load, collect and distribute sprites from spritesheets.
 * This is used to increase performance by allowing images and animation frames to be
 * stored on a single sheet instead of individual files.
 *
 * Ideally, you load a spriteSheet with loadSprite, and then access the frames within using the
 * getSprite class.
 *
 * @author myles
 *
 */
public class SpriteSheet {

	private static BufferedImage spriteSheet;
	private static int spriteSizeX;
	private static int spriteSizeY;
	
	/**
	 * Load SpriteSheet
	 * This is basically an image loader, that also stores the dimensions of the sprite
	 *
	 * @param filepath : 	String filepath to spritesheet.
	 * 						This should be located within the 'Assets/spritesheets/' directory,
	 * 						and NOT include the '.png' file extension
	 * @param x: Integer representing the x dimension of individual sprite.
	 * @param yx: Integer representing the y dimension of individual sprite.
	 * @return full SpriteSheet as a BufferedImage
	 */
	public static BufferedImage loadSpriteSheet(String filepath, int x, int y) {

		// Create new buffered image
		BufferedImage sprite = null;

		// Set the sprite dimensions to specified size
		spriteSizeX = x;
		spriteSizeY = y;

		try {
			spriteSheet = ImageIO.read(new File("assets/spritesheets/"+filepath+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sprite;
	}

	/**
	 * Get Sprite
	 * Get a sprite from a position on a spritesheet you have previously loaded.
	 * Uses simple grid coordinates to take the calculations away from the user.
	 *
	 * This will play up if you don't use the correct coordinates,
	 * or have set the spriteSize incorrectly
	 *
	 * NOTE: grid positions start @ 0, not 1
	 * @param The X coordinate that corresponds to the desired sprite.
	 * @param The Y coordinate that corresponds to the desired sprite
	 * @return A BufferedImage of the specified sprite.
	 */
	public static BufferedImage getSprite(int xGrid, int yGrid) {
		// Return the sprite at the specified position
		return spriteSheet.getSubimage(xGrid * spriteSizeX, yGrid * spriteSizeY, spriteSizeX, spriteSizeY);
	}
}
