package world.icons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import renderer.Animation;
import renderer.Camera;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;

/**
 * An icon is anything that can occupy a tile on the world map (a party, an item, a building etc.)
 * @author craigthelinguist
 */
public abstract class WorldIcon {

	public abstract void setAnimationName(String name);

	public abstract String getAnimationName();

	public abstract BufferedImage getImage();
	public abstract BufferedImage getPortrait();
	
	public void draw(Graphics graphics, int iconX, int iconY) {
		graphics.drawImage(this.getImage(),iconX,iconY,null);
	}
	

	public void rotate(boolean clockwise){
		
		// get current orientation
		int playerDir;
		String animationName = this.getAnimationName();
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
		
		if (playerDir == Camera.NORTH) this.setAnimationName("north");
		else if (playerDir == Camera.EAST) this.setAnimationName("east");
		else if (playerDir == Camera.SOUTH) this.setAnimationName("south");
		else if (playerDir == Camera.WEST) this.setAnimationName("west");
		else throw new RuntimeException("Unknown animation name for rotated image: " + animationName);
		
	}
	

}
