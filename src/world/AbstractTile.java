package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * Represents one cell on the World. Different implementations of Tile might have different rules (e.g.:
 * parties can stand on it, tile belongs to a city, so on).
 */
public abstract class AbstractTile {

	protected BufferedImage image;
	protected WorldIcon occupant = null;
	
	public BufferedImage getImage(){
		return image;
	}
	
	public boolean occupied(){
		return occupant != null;
	}
	
	public WorldIcon occupant(){
		return occupant;
	}

	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
	
	public void setIcon(WorldIcon i){
		occupant = i;
	}
	
}
