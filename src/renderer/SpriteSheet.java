package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tools.GlobalConstants;

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
	private static int spriteSize;

	/**
	 * Load SpriteSheet
	 * This is basically an image loader, that also stores the dimensions of the sprite
	 *
	 * NOTE: At this point only spritesheets that put images into SQUARE bounding boxes with equal dimensions
	 * work. This is why the size parameter only needs one integer to know the dimensions of the sprite.
	 *
	 * @param filepath : 	String filepath to spritesheet.
	 * 						This should be located within the 'Assets/spritesheets/' directory,
	 * 						and NOT include the '.png' file extension
	 * @param size :		Integer representing the dimension of sprite.
	 * @return full SpriteSheet as a BufferedImage
	 */
	public static BufferedImage loadSpriteSheet(String filepath, int size) {

		// Create new buffered image
		BufferedImage sprite = null;

		// Set the sprite dimensions to specified size
		spriteSize = size;

		try {
			sprite = ImageIO.read(new File("assets/spritesheets/"+filepath+".png"));
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

		// This is purely placeholder
		if(spriteSheet == null) {
			spriteSheet = loadSpriteSheet("64x64_world_sprites", 64);
		}

		// Return the sprite at the specified position
		return spriteSheet.getSubimage(xGrid * spriteSize, yGrid * spriteSize, spriteSize, spriteSize);
	}
}
