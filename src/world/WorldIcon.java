package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * An icon is anything that can occupy a tile on the world map (a party, an item, a building etc.)
 *
 */
public abstract class WorldIcon {

	private BufferedImage image;
	
	public WorldIcon(String imgname){
		image = ImageLoader.load(GlobalConstants.ASSETS + imgname);
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public void draw(Graphics graphics, int iconX, int iconY) {
		graphics.drawImage(image,iconX,iconY,null);
	}
	
}
