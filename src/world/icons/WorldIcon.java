package world.icons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import renderer.Animation;
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

}
