package world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import tools.ImageManipulation;
import world.icons.Party;
import world.icons.WorldIcon;


/**
 * Represents one cell on the World. Different implementations of Tile might have different rules (e.g.:
 * parties can stand on it, tile belongs to a city, so on).
 * @author: craigthelinguist
 */
public abstract class Tile {

	protected BufferedImage image;
	protected WorldIcon occupant = null;
	public final int X;
	public final int Y;
	
	public Tile(int x, int y){
		X = x; Y = y;
	}
	
	public BufferedImage getImage(){
		return image;
	}

	/**
	 * Check if you can stand on this tile.
	 * @return: true if the specified party is allowed to stand on this tile.
	 */
	public abstract boolean passable(Party party);
	
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

	/**
	 * Draws this tile, but a little lighter.
	 * @param graphics
	 * @param x
	 * @param y
	 */
	public void drawHighlighted(Graphics graphics, int x, int y, int intensity) {
		BufferedImage lighterImage = ImageManipulation.lighten(image, intensity);
		graphics.drawImage(lighterImage,x,y,null);
	}
		
}
